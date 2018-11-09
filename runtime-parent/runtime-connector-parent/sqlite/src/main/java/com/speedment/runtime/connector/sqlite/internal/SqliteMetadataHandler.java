package com.speedment.runtime.connector.sqlite.internal;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.runtime.config.*;
import com.speedment.runtime.config.mutator.ForeignKeyColumnMutator;
import com.speedment.runtime.config.util.DocumentUtil;
import com.speedment.runtime.connector.sqlite.internal.types.SqlTypeMappingHelper;
import com.speedment.runtime.connector.sqlite.internal.util.MetaDataUtil;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.component.connectionpool.ConnectionPoolComponent;
import com.speedment.runtime.core.db.DatabaseNamingConvention;
import com.speedment.runtime.core.db.DbmsMetadataHandler;
import com.speedment.runtime.core.db.JavaTypeMap;
import com.speedment.runtime.core.db.SqlPredicate;
import com.speedment.runtime.core.db.SqlSupplier;
import com.speedment.runtime.core.db.metadata.ColumnMetaData;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.util.ProgressMeasure;
import com.speedment.runtime.typemapper.TypeMapper;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.speedment.common.invariant.NullUtil.requireNonNulls;
import static com.speedment.runtime.connector.sqlite.internal.util.LoggingUtil.describe;
import static com.speedment.runtime.connector.sqlite.internal.util.MetaDataUtil.getOrderType;
import static com.speedment.runtime.connector.sqlite.internal.util.MetaDataUtil.isAutoIncrement;
import static com.speedment.runtime.connector.sqlite.internal.util.MetaDataUtil.isWrapper;
import static com.speedment.runtime.core.internal.db.AbstractDbmsOperationHandler.SHOW_METADATA;
import static java.lang.String.format;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * Implementation of {@link DbmsMetadataHandler} for SQLite databases.
 *
 * @author Emil Forslund
 * @since  3.1.9
 */
public final class SqliteMetadataHandler implements DbmsMetadataHandler {

    private final static Logger LOGGER = LoggerManager.getLogger(SqliteMetadataHandler.class);
    private final static String[] TABLES_AND_VIEWS = {"TABLE", "VIEW"};

    /**
     * Fix #566: Some connectors throw an exception if getIndexInfo() is invoked
     * for a database VIEW.
     */
    private final static boolean IGNORE_VIEW_INDEXES = false;

    /**
     * 'true' below might speed up metadata retrieval since approximations can
     * be used. See https://github.com/speedment/speedment-enterprise/issues/168
     */
    private final static boolean APPROXIMATE_INDEX = true;

    private @Inject ConnectionPoolComponent connectionPool;
    private @Inject ProjectComponent projects;
    private @Inject SqliteDbmsType dbmsType;

    private JavaTypeMap javaTypeMap;
    private SqlTypeMappingHelper typeMappingHelper;

    @ExecuteBefore(State.INITIALIZED)
    void initJavaTypeMap() {
        javaTypeMap = JavaTypeMap.create();
    }

    @ExecuteBefore(State.RESOLVED)
    void initSqlTypeMappingHelper(Injector injector) {
        typeMappingHelper = SqlTypeMappingHelper.create(injector, javaTypeMap);
    }

    @Override
    public String getDbmsInfoString(Dbms dbms) throws SQLException {
        try (final Connection conn = connectionPool.getConnection(dbms)) {
            final DatabaseMetaData md = conn.getMetaData();
            return md.getDatabaseProductName() + ", " +
                md.getDatabaseProductVersion() + ", " +
                md.getDriverName() + " " +
                md.getDriverVersion() + ", JDBC version " +
                md.getJDBCMajorVersion() + "." +
                md.getJDBCMinorVersion();
        }
    }

    @Override
    public CompletableFuture<Project> readSchemaMetadata(
            Dbms dbms, ProgressMeasure progress,
            Predicate<String> filterCriteria) {

        // Create a deep copy of the project document.
        final Project projectCopy = projects.getProject().deepCopy();

        // Make sure there are not multiple dbmses with the same id
        final Set<String> ids = new HashSet<>();
        if (!projectCopy.dbmses().map(Dbms::getId).allMatch(ids::add)) {
            final Set<String> duplicates = new HashSet<>();
            ids.clear();

            projectCopy.dbmses()
                .map(Dbms::getId)
                .forEach(s -> {
                    if (!ids.add(s)) {
                        duplicates.add(s);
                    }
                });

            throw new SpeedmentException(
                "The following dbmses have duplicates in the config document: "
                    + duplicates
            );
        }

        // Locate the dbms in the copy.
        final Dbms dbmsCopy = projectCopy.dbmses()
            .filter(d -> d.getId().equals(dbms.getId()))
            .findAny().orElseThrow(() -> new SpeedmentException(
                "Could not find Dbms document in copy."
            ));

        return readSchemaMetadata(
            projectCopy, dbmsCopy, progress
        ).whenCompleteAsync((project, ex) -> {
            progress.setProgress(ProgressMeasure.DONE);
            if (ex != null) {
                progress.setCurrentAction("Error!");
                throw new SpeedmentException("Unable to read schema metadata.", ex);
            } else {
                progress.setCurrentAction("Done!");
            }
        });
    }

    private CompletableFuture<Project> readSchemaMetadata(
            Project project, Dbms dbms, ProgressMeasure progress) {

        //final DbmsType dbmsType = dbmsTypeOf(dbmsHandlerComponent, dbms);

        progress.setCurrentAction(describe(dbms));
        LOGGER.info(describe(dbms));

        final CompletableFuture<Map<String, Class<?>>> typeMappingTask
            = typeMappingHelper.loadFor(dbms);

        final Schema schema = dbms.mutator().addNewSchema();
        schema.mutator().setId("schema");
        schema.mutator().setName("schema");

        return readTableMetadata(schema, typeMappingTask, progress)
            .thenApplyAsync($ -> project);
    }

    private CompletableFuture<Schema> readTableMetadata(
            Schema schema,
            CompletableFuture<Map<String, Class<?>>> typeMappingTask,
            ProgressMeasure progress) {

        final CompletableFuture<Void> tablesTask = CompletableFuture.runAsync(() -> {
            final Dbms dbms = schema.getParentOrThrow();

            try (final Connection connection = connectionPool.getConnection(dbms)) {
                try (final ResultSet rsTable = connection.getMetaData().getTables(null, null, null, TABLES_AND_VIEWS)) {
                    if (SHOW_METADATA) {
                        final ResultSetMetaData rsmd = rsTable.getMetaData();
                        final int numberOfColumns = rsmd.getColumnCount();
                        for (int x = 1; x <= numberOfColumns; x++) {
                            LOGGER.debug(new StringJoiner(", ")
                                .add(rsmd.getColumnName(x))
                                .add(rsmd.getColumnClassName(x))
                                .add(Integer.toString(rsmd.getColumnType(x)))
                                .toString()
                            );
                        }
                    }

                    while (rsTable.next()) {
                        if (SHOW_METADATA) {
                            final ResultSetMetaData rsmd = rsTable.getMetaData();
                            final int numberOfColumns = rsmd.getColumnCount();
                            for (int x = 1; x <= numberOfColumns; x++) {
                                LOGGER.debug(rsmd.getColumnName(x) +
                                    ":'" + rsTable.getObject(x) + "'");
                            }
                        }

                        final Table table      = schema.mutator().addNewTable();
                        final String tableName = rsTable.getString("TABLE_NAME");
                        final String tableType = rsTable.getString("TABLE_TYPE");
                        table.mutator().setId(tableName);
                        table.mutator().setName(tableName);
                        table.mutator().setView("VIEW".equals(tableType));
                    }
                } catch (final SQLException ex) {
                    throw new SpeedmentException(format(
                        "Error reading results from SQLite-database '%s'.",
                        DocumentUtil.toStringHelper(dbms)
                    ), ex);
                }
            } catch (final SQLException ex) {
                throw new SpeedmentException(format(
                    "Error getting connection to SQLite-database '%s'.",
                    DocumentUtil.toStringHelper(dbms)
                ), ex);
            }
        });

        return tablesTask.thenComposeAsync($ -> typeMappingTask)
            .thenComposeAsync(sqlTypeMappings -> {
                final Dbms dbms = schema.getParentOrThrow();
                final AtomicInteger cnt = new AtomicInteger();
                final double noTables = schema.tables().count();

                return CompletableFuture.allOf(
                    schema.tables().map(table -> CompletableFuture.runAsync(() -> {
                        try (final Connection conn = connectionPool.getConnection(dbms)) {
                            progress.setCurrentAction(describe(table));
                            columns(conn, sqlTypeMappings, table, progress);
                            indexes(conn, table, progress);
                            foreignKeys(conn, table, progress);
                            primaryKeyColumns(conn, table, progress);
                            progress.setProgress(cnt.incrementAndGet() / noTables);
                        } catch (final SQLException ex) {
                            throw new SpeedmentException(ex);
                        }
                    })).toArray(CompletableFuture[]::new)
                ).thenApplyAsync(v -> schema);
            });
    }

    private void columns(Connection conn, Map<String, Class<?>> sqlTypeMapping, Table table, ProgressMeasure progress) {
        requireNonNulls(conn, sqlTypeMapping, table, progress);

        final SqlSupplier<ResultSet> supplier = () ->
            conn.getMetaData().getColumns(null, null, table.getId(), null);

        final TableChildMutator<Column> mutator = (column, rs) -> {

            final ColumnMetaData md = ColumnMetaData.of(rs);
            final String columnName = md.getColumnName();

            column.mutator().setId(columnName);
            column.mutator().setName(columnName);
            column.mutator().setOrdinalPosition(md.getOrdinalPosition());
            column.mutator().setNullable(MetaDataUtil.isNullable(md));
            column.mutator().setDatabaseType(
                typeMappingHelper.findFor(sqlTypeMapping, md)
                    .orElseGet(() -> {
                        LOGGER.warn(format(
                            "Unable to determine mapping for table %s, " +
                            "column %s. Type name %s, data type %d, decimal " +
                            "digits %d. Fallback to JDBC-type %s",
                            table.getId(),
                            column.getId(),
                            md.getTypeName(),
                            md.getDataType(),
                            md.getDecimalDigits(),
                            Object.class.getName()
                        ));
                        return Object.class;
                    })
            );

            if (isAutoIncrement(md)) column.mutator().setAutoIncrement(true);

            if (!column.isNullable() && isWrapper(column.findDatabaseType())) {
                column.mutator().setTypeMapper(TypeMapper.primitive().getClass());
            }

            if ("ENUM".equalsIgnoreCase(md.getTypeName())) {
                final Dbms dbms = DocumentUtil.ancestor(table, Dbms.class).get();
                final List<String> constants = enumConstantsOf(dbms, table, columnName);
                column.mutator().setEnumConstants(constants.stream().collect(joining(",")));
            }

            progress.setCurrentAction(describe(column));
        };

        tableChilds(table.mutator()::addNewColumn, supplier, mutator);
    }

    private void primaryKeyColumns(Connection conn, Table table, ProgressMeasure progress) {
        requireNonNulls(conn, table, progress);

        final SqlSupplier<ResultSet> supplier = () ->
            conn.getMetaData().getPrimaryKeys(null, null, table.getId());

        final TableChildMutator<PrimaryKeyColumn> mutator = (pkc, rs) -> {
            final String columnName = rs.getString("COLUMN_NAME");
            pkc.mutator().setId(columnName);
            pkc.mutator().setName(columnName);
            pkc.mutator().setOrdinalPosition(rs.getInt("KEY_SEQ"));
        };

        tableChilds(
            table.mutator()::addNewPrimaryKeyColumn,
            supplier, mutator);

        if (!table.isView() && table.primaryKeyColumns().noneMatch(pk -> true)) {
            LOGGER.warn(format("Table '%s' does not have any primary key.",
                table.getId()));
        }
    }

    private void indexes(Connection conn, Table table, ProgressMeasure progress) {
        requireNonNulls(conn, table, progress);

        if (table.isView() && IGNORE_VIEW_INDEXES) return;

        final SqlSupplier<ResultSet> supplier = () ->
            conn.getMetaData().getIndexInfo(null, null, table.getId(), false,
                APPROXIMATE_INDEX);

        final TableChildMutator<Index> mutator = (index, rs) -> {
            final String indexName = rs.getString("INDEX_NAME");

            index.mutator().setId(indexName);
            index.mutator().setName(indexName);
            index.mutator().setUnique(!rs.getBoolean("NON_UNIQUE"));

            final IndexColumn indexColumn = index.mutator().addNewIndexColumn();
            final String columnName = rs.getString("COLUMN_NAME");
            indexColumn.mutator().setId(columnName);
            indexColumn.mutator().setName(columnName);
            indexColumn.mutator().setOrdinalPosition(rs.getInt("ORDINAL_POSITION"));
            indexColumn.mutator().setOrderType(getOrderType(rs));
        };

        final SqlPredicate<ResultSet> filter = rs -> rs.getString("INDEX_NAME") != null;
        tableChilds(table.mutator()::addNewIndex, supplier, mutator, filter);
    }

    private void foreignKeys(Connection conn, Table table, ProgressMeasure progress) {
        requireNonNulls(conn, table);

        final Schema schema = table.getParentOrThrow();
        final SqlSupplier<ResultSet> supplier = () ->
            conn.getMetaData().getImportedKeys(null, null, table.getId());

        final TableChildMutator<ForeignKey> mutator = (foreignKey, rs) -> {

            final String foreignKeyName = rs.getString("FK_NAME");
            foreignKey.mutator().setId(foreignKeyName);
            foreignKey.mutator().setName(foreignKeyName);

            final ForeignKeyColumn foreignKeyColumn = foreignKey.mutator().addNewForeignKeyColumn();
            final ForeignKeyColumnMutator<?> fkcMutator = foreignKeyColumn.mutator();
            final String fkColumnName = rs.getString("FKCOLUMN_NAME");
            fkcMutator.setId(fkColumnName);
            fkcMutator.setName(fkColumnName);
            fkcMutator.setOrdinalPosition(rs.getInt("KEY_SEQ"));
            fkcMutator.setForeignTableName(rs.getString("PKTABLE_NAME"));
            fkcMutator.setForeignColumnName(rs.getString("PKCOLUMN_NAME"));

            // FKs always point to the same DBMS but can
            // be changed to another one using the config
            fkcMutator.setForeignDatabaseName(schema.getParentOrThrow().getId());
            fkcMutator.setForeignSchemaName("schema");
        };

        tableChilds(table.mutator()::addNewForeignKey,
            supplier, mutator);
    }

    private <T extends Document> void tableChilds(
            final Supplier<T> childSupplier,
            final SqlSupplier<ResultSet> resultSetSupplier,
            final TableChildMutator<T> resultSetMutator) {

        tableChilds(childSupplier, resultSetSupplier, resultSetMutator, rs -> true);
    }

    private <T extends Document> void tableChilds(
            final Supplier<T> childSupplier,
            final SqlSupplier<ResultSet> resultSetSupplier,
            final TableChildMutator<T> resultSetMutator,
            final SqlPredicate<ResultSet> filter) {

        requireNonNulls(childSupplier, resultSetSupplier, resultSetMutator);

        try (final ResultSet rsChild = resultSetSupplier.get()) {
            if (SHOW_METADATA) {
                final ResultSetMetaData rsmd = rsChild.getMetaData();
                final int numberOfColumns = rsmd.getColumnCount();
                for (int x = 1; x <= numberOfColumns; x++) {
                    final int columnType = rsmd.getColumnType(x);
                    LOGGER.info(x + ":" + rsmd.getColumnName(x) + ", " +
                        rsmd.getColumnClassName(x) + ", " + columnType);
                }
            }

            while (rsChild.next()) {
                if (SHOW_METADATA) {
                    final ResultSetMetaData rsmd = rsChild.getMetaData();
                    final int numberOfColumns = rsmd.getColumnCount();
                    for (int x = 1; x <= numberOfColumns; x++) {
                        final Object val = rsChild.getObject(x);
                        LOGGER.info(x + ":" + rsmd.getColumnName(x) + ":'" +
                            val + "'");
                    }
                }
                if (filter.test(rsChild)) {
                    resultSetMutator.mutate(childSupplier.get(), rsChild);
                } else {
                    LOGGER.info("Skipped due to RS filtering. This is normal " +
                        "for some DBMS types.");
                }
            }
        } catch (final SQLException sqle) {
            LOGGER.error(sqle, "Unable to read table child.");
            throw new SpeedmentException(sqle);
        }
    }

    /**
     * Queries the database for a list of ENUM constants belonging to the specified table and
     * column.
     *
     * @param dbms           the dbms
     * @param table          the table
     * @param columnName     the column name
     * @return               list of enum constants.
     * @throws SQLException  if an error occured
     */
    private List<String> enumConstantsOf(Dbms dbms, Table table, String columnName) throws SQLException {
        final DatabaseNamingConvention naming = dbmsType.getDatabaseNamingConvention();

        final String sql = String.format(
            "show columns from %s where field=%s;",
            naming.fullNameOf(table),
            naming.quoteField(columnName)
        );

        try (final Connection conn = connectionPool.getConnection(dbms);
             final PreparedStatement ps = conn.prepareStatement(sql);
             final ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                final String fullResult = rs.getString(2);

                if (fullResult.startsWith("enum('")
                    &&  fullResult.endsWith("')")) {

                    final String middle = fullResult.substring(5, fullResult.length() - 1);
                    return Stream.of(middle.split(","))
                        .map(s -> s.substring(1, s.length() - 1))
                        .filter(s -> !s.isEmpty())
                        .collect(toList());

                } else {
                    throw new SpeedmentException("Unexpected response (" + fullResult + ").");
                }
            } else {
                throw new SpeedmentException("Expected an result.");
            }
        }
    }

    @FunctionalInterface
    protected interface TableChildMutator<T> {
        void mutate(T t, ResultSet u) throws SQLException;
    }
}
