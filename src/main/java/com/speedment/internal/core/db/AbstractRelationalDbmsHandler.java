/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.internal.core.db;

import com.speedment.db.SqlFunction;
import com.speedment.db.SqlSupplier;
import com.speedment.Speedment;
import com.speedment.config.Document;
import com.speedment.config.db.Column;
import com.speedment.config.db.Dbms;
import com.speedment.config.db.ForeignKey;
import com.speedment.config.db.ForeignKeyColumn;
import com.speedment.config.db.Index;
import com.speedment.config.db.IndexColumn;
import com.speedment.config.db.PrimaryKeyColumn;
import com.speedment.config.db.Project;
import com.speedment.config.db.Schema;
import com.speedment.config.db.Table;
import com.speedment.config.db.parameters.OrderType;
import com.speedment.internal.core.manager.sql.SqlStatement;
import com.speedment.internal.core.manager.sql.SqlUpdateStatement;
import com.speedment.db.AsynchronousQueryResult;
import com.speedment.db.DbmsHandler;
import com.speedment.exception.SpeedmentException;
import com.speedment.config.db.mapper.TypeMapper;
import com.speedment.db.DatabaseNamingConvention;
import com.speedment.config.db.mutator.ForeignKeyColumnMutator;
import com.speedment.config.db.parameters.DbmsType;
import com.speedment.config.db.trait.HasMainInterface;
import com.speedment.config.db.trait.HasName;
import com.speedment.config.db.trait.HasParent;
import com.speedment.db.SqlPredicate;
import com.speedment.internal.core.config.db.ProjectImpl;
import com.speedment.db.metadata.ColumnMetadata;
import com.speedment.internal.logging.Logger;
import com.speedment.internal.logging.LoggerManager;
import com.speedment.util.sql.SqlTypeInfo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.*;

import java.util.stream.Stream;
import static com.speedment.internal.util.document.DocumentDbUtil.dbmsTypeOf;
import com.speedment.internal.util.document.DocumentDbUtil;
import static java.util.Objects.nonNull;
import com.speedment.util.ProgressMeasure;
import java.util.concurrent.atomic.AtomicInteger;
import com.speedment.internal.util.document.DocumentUtil;
import java.util.concurrent.CompletableFuture;
import static com.speedment.internal.core.stream.OptionalUtil.unwrap;
import static com.speedment.util.NullUtil.requireNonNulls;
import java.sql.Types;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toMap;
import static com.speedment.internal.core.stream.OptionalUtil.unwrap;
import static com.speedment.util.NullUtil.requireNonNulls;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toMap;
import static com.speedment.internal.core.stream.OptionalUtil.unwrap;
import static com.speedment.util.NullUtil.requireNonNulls;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toMap;
import static com.speedment.internal.core.stream.OptionalUtil.unwrap;
import static com.speedment.util.NullUtil.requireNonNulls;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toMap;

/**
 *
 * @author Per Minborg
 * @author Emil Forslund
 */
public abstract class AbstractRelationalDbmsHandler implements DbmsHandler {

    private static final String YES = "YES";

    private final static Logger LOGGER = LoggerManager.getLogger(AbstractRelationalDbmsHandler.class);
    private final static String PASSWORD_PROTECTED = "********";
    public static boolean SHOW_METADATA = false; // Warning: Enabling SHOW_METADATA will make some dbmses fail on metadata (notably Oracle) because all the columns must be read in order...

    private final Speedment speedment;
    private final Dbms dbms;
    protected final DbmsType dbmsType;

    public AbstractRelationalDbmsHandler(Speedment speedment, Dbms dbms) {
        this.speedment = requireNonNull(speedment);
        this.dbms = requireNonNull(dbms);
        this.dbmsType = DocumentDbUtil.dbmsTypeOf(speedment, dbms);
    }

    @Override
    public Dbms getDbms() {
        return dbms;
    }

    @Override
    public CompletableFuture<Project> readSchemaMetadata(
        ProgressMeasure progress,
        Predicate<String> filterCriteria) {

        requireNonNulls(filterCriteria, progress);

        // Create a deep copy of the project document.
        final Project projectCopy = DocumentUtil.deepCopy(
            this.dbms.getParentOrThrow(), ProjectImpl::new
        );

        // Locate the dbms in the copy.
        final Dbms dbmsCopy = projectCopy.dbmses()
            .filter(d -> d.getName().equals(this.dbms.getName()))
            .findAny().orElseThrow(() -> new SpeedmentException(
                "Could not find Dbms document in copy."
            ));

        return readSchemaMetadata(
            projectCopy, dbmsCopy, filterCriteria, progress
        ).whenComplete((project, ex) -> {
            progress.setCurrentAction("Done!");
            progress.setProgress(ProgressMeasure.DONE);
        });
    }

    private CompletableFuture<Project> readSchemaMetadata(
        //            Connection connection,
        Project project,
        Dbms dbms,
        Predicate<String> filterCriteria,
        ProgressMeasure progress) {

        requireNonNulls(project, dbms, filterCriteria, progress);

        final DbmsType dbmsType = dbmsTypeOf(speedment, dbms);
        final String action = actionName(dbms);

        LOGGER.info(action);
        progress.setCurrentAction(action);

        final Set<String> discardedSchemas = new HashSet<>();
        final DatabaseNamingConvention naming = dbmsType.getDatabaseNamingConvention();
        final Set<SqlTypeInfo> preSet = dbmsType.getDataTypes();

        // Task that downloads the SQL Type Mappings from the database
        final CompletableFuture<Map<String, Class<?>>> sqlTypeMappingTask
            = CompletableFuture.supplyAsync(() -> {
                final Map<String, Class<?>> sqlTypeMapping;

                try {
                    sqlTypeMapping = !preSet.isEmpty()
                        ? readTypeMapFromSet(preSet)
                        : readTypeMapFromDB(dbms);
                } catch (final SQLException ex) {
                    throw new SpeedmentException(
                        "Error loading type map from database.", ex
                    );
                }

                return sqlTypeMapping;
            });

        // Task that downloads the schemas from the database
        final CompletableFuture<Void> schemasTask = CompletableFuture.runAsync(() -> {
            try (final Connection connection = getConnection(dbms)) {
                try (final ResultSet rs = connection.getMetaData().getSchemas(null, null)) {
                    while (rs.next()) {

                        final String schemaName = rs.getString(dbmsType.getResultSetTableSchema());
                        String catalogName = "";
                        try {
                            // This column is not there for Oracle so handle it
                            // gracefully....
                            catalogName = rs.getString("TABLE_CATALOG");
                        } catch (final SQLException ex) {
                            LOGGER.info("TABLE_CATALOG not in result set.");
                        }

                        boolean schemaWasUsed = false;
                        if (!naming.getSchemaExcludeSet().contains(schemaName)) {
                            final String name = Optional.ofNullable(schemaName).orElse(catalogName);
                            if (filterCriteria.test(name)) {
                                final Schema schema = dbms.mutator().addNewSchema();
                                schema.mutator().setName(name);
                                schemaWasUsed = true;
                            }
                        }

                        if (!schemaWasUsed) {
                            discardedSchemas.add(schemaName);
                        }
                    }
                }
            } catch (final SQLException sqle) {
                throw new SpeedmentException(
                    "Error reading metadata from result set.", sqle
                );
            }
        });

        // Task that downloads the catalogs from the database
        final CompletableFuture<Void> catalogsTask = CompletableFuture.runAsync(() -> {
            try (final Connection connection = getConnection(dbms)) {
                try (final ResultSet catalogResultSet = connection.getMetaData().getCatalogs()) {
                    while (catalogResultSet.next()) {
                        final String schemaName = catalogResultSet.getString(1);

                        boolean schemaWasUsed = false;
                        if (filterCriteria.test(schemaName)) {
                            if (!naming.getSchemaExcludeSet().contains(schemaName)) {
                                final Schema schema = dbms.mutator().addNewSchema();
                                schema.mutator().setName(schemaName);
                                schemaWasUsed = true;
                            }
                        }

                        if (!schemaWasUsed) {
                            discardedSchemas.add(schemaName);
                        }
                    }
                }
            } catch (final SQLException sqle) {
                throw new SpeedmentException(
                    "Error reading metadata from result set.", sqle
                );
            }
        });

        // Create a new task that will execute once the schemas and the catalogs 
        // have been loaded independently of each other.
        return CompletableFuture.allOf(
            schemasTask,
            catalogsTask
        ).thenCompose(v -> {
            @SuppressWarnings("rawtypes")
            final CompletableFuture<Schema>[] tablesTask
                = dbms.schemas().map(schema
                    -> tables(sqlTypeMappingTask, dbms, schema, progress)
                ).toArray(CompletableFuture[]::new);

            return CompletableFuture.allOf(tablesTask)
                .handle((v2, ex) -> {
                    if (ex == null) {
                        if (tablesTask.length == 0) {
                            throw new SpeedmentException(
                                "Could not find any matching schema. The following schemas was considered: " + discardedSchemas + "."
                            );
                        } else {
                            return project;
                        }
                    } else {
                        throw new SpeedmentException(
                            "An exception occured while the tables were loading.", ex
                        );
                    }
                });
        });
    }

    protected CompletableFuture<Schema> tables(CompletableFuture<Map<String, Class<?>>> sqlTypeMapping, Dbms dbms, Schema schema, ProgressMeasure progressListener) {
        requireNonNulls(sqlTypeMapping, dbms, schema, progressListener);

        final String action = actionName(schema);
        LOGGER.info(action);
        progressListener.setCurrentAction(action);

        try (final Connection connection = getConnection(dbms)) {
            try (final ResultSet rsTable = connection.getMetaData().getTables(jdbcCatalogLookupName(schema), jdbcSchemaLookupName(schema), null, new String[]{"TABLE"})) {

                final ResultSetMetaData rsmd = rsTable.getMetaData();
                int numberOfColumns = rsmd.getColumnCount();

                if (SHOW_METADATA) {
                    for (int x = 1; x <= numberOfColumns; x++) {
                        LOGGER.debug(rsmd.getColumnName(x) + ", " + rsmd.getColumnClassName(x) + ", " + rsmd.getColumnType(x));
                    }
                }

                while (rsTable.next()) {
                    if (SHOW_METADATA) {
                        for (int x = 1; x <= numberOfColumns; x++) {
                            LOGGER.debug(rsmd.getColumnName(x) + ":'" + rsTable.getObject(x) + "'");
                        }
                    }
                    final Table table = schema.mutator().addNewTable();
                    final String tableName = rsTable.getString("TABLE_NAME");
                    table.mutator().setName(tableName);
                }
            }
        } catch (SQLException sqle) {
            throw new SpeedmentException(sqle);
        }

        final AtomicInteger cnt = new AtomicInteger();
        final double noTables = schema.tables().count();

        return CompletableFuture.allOf(
            schema.tables().map(table -> sqlTypeMapping.thenAccept(mapping -> {
                try (final Connection connection = getConnection(dbms)) {
                    progressListener.setCurrentAction(actionName(table));
                    columns(connection, mapping, table, progressListener);
                    indexes(connection, table, progressListener);
                    foreignKeys(connection, table, progressListener);
                    primaryKeyColumns(connection, table, progressListener);
                    progressListener.setProgress(cnt.incrementAndGet() / noTables);
                } catch (final SQLException ex) {
                    throw new SpeedmentException(ex);
                }
            })).toArray(CompletableFuture[]::new)
        ).thenApply(v -> schema);
    }

    protected void columns(Connection connection, Map<String, Class<?>> sqlTypeMapping, Table table, ProgressMeasure progressListener) {
        requireNonNulls(connection, table);

        final Schema schema = table.getParentOrThrow();

        final SqlSupplier<ResultSet> supplier = ()
            -> connection.getMetaData().getColumns(
                jdbcCatalogLookupName(schema),
                jdbcSchemaLookupName(schema),
                metaDataTableNameForColumns(table),
                null
            );

        final TableChildMutator<Column, ResultSet> mutator = (column, rs) -> {

            final ColumnMetadata md = ColumnMetadata.of(rs);

            final String columnName = md.getColumnName();

//            if (columnName.startsWith("blob")) {
//                int foo = 1;
//            }
            column.mutator().setName(columnName);
            column.mutator().setOrdinalPosition(md.getOrdinalPosition());

            final boolean nullable;
            final int nullableValue = md.getNullable();
            switch (nullableValue) {
                case DatabaseMetaData.columnNullable:
                case DatabaseMetaData.columnNullableUnknown: {
                    nullable = true;
                    break;
                }
                case DatabaseMetaData.columnNoNulls: {
                    nullable = false;
                    break;
                }
                default:
                    throw new SpeedmentException("Unknown nullable type " + nullableValue);
            }

            column.mutator().setNullable(nullable);

            final String typeName = md.getTypeName();
            final int columnSize = md.getColumnSize();
            final int decimalDigits = md.getDecimalDigits();
            final Class<?> lookupJdbcClass = lookupJdbcClass(sqlTypeMapping, typeName, columnSize, decimalDigits);

            final Class<?> selectedJdbcClass;
            if (lookupJdbcClass != null) {
                selectedJdbcClass = lookupJdbcClass;
            } else {
                // Fall-back to Object
                selectedJdbcClass = Object.class;
                LOGGER.warn("Unable to determine mapping for table " + table.getName() + ", column " + column.getName() + ". Fall-back to JDBC-type " + selectedJdbcClass.getSimpleName());
            }

            final TypeMapper<?, ?> typeMapper = speedment.getTypeMapperComponent().stream()
                .filter(tm -> Objects.equals(selectedJdbcClass, tm.getDatabaseType()))
                .filter(tm -> Objects.equals(selectedJdbcClass, tm.getJavaType()))
                .findFirst().orElseThrow(() -> new SpeedmentException(
                    "Found no identity type mapper for mapping '" + selectedJdbcClass.getName() + "'."
                ));

            column.mutator().setTypeMapper(typeMapper);
            column.mutator().setDatabaseType(selectedJdbcClass);

            setAutoIncrement(column, md);

        };

        tableChilds(table.mutator()::addNewColumn, supplier, mutator, progressListener);
    }

    /**
     * Sets the autoIncrement property of a Column.
     *
     * @param column to use
     * @param rs that contains column metadata (per
     * connection.getMetaData().getColumns(...))
     */
    protected void setAutoIncrement(Column column, ColumnMetadata md) throws SQLException {
        final String isAutoIncrementString = md.getIsAutoincrement();

        if (YES.equalsIgnoreCase(isAutoIncrementString) /* || YES.equalsIgnoreCase(isGeneratedColumnString)*/) {
            column.mutator().setAutoIncrement(true);
        }
    }

    /**
     * Looks up a column {@code TYPE_NAME} and returns a mapped Class (e.g.
     * {@code Timestamp} or {@code String}).
     *
     * @param typeName the TYPE_NAME value
     * @param columnSize the COLUMN_SIZE value
     * @param decimalDigits the DECIMAL_DIGITS value
     * @return the mapped Class
     */
    protected Class<?> lookupJdbcClass(Map<String, Class<?>> sqlTypeMapping, String typeName, int columnSize, int decimalDigits) {
        return sqlTypeMapping.get(typeName);
    }

    protected void primaryKeyColumns(Connection connection, Table table, ProgressMeasure progressListener) {
        requireNonNulls(connection, table);

        final Schema schema = table.getParentOrThrow();

        final SqlSupplier<ResultSet> supplier = ()
            -> connection.getMetaData().getPrimaryKeys(jdbcCatalogLookupName(schema),
                jdbcSchemaLookupName(schema),
                metaDataTableNameForPrimaryKeys(table)
            );

        final TableChildMutator<PrimaryKeyColumn, ResultSet> mutator = (primaryKeyColumn, rs) -> {
            primaryKeyColumn.mutator().setName(rs.getString("COLUMN_NAME"));
            primaryKeyColumn.mutator().setOrdinalPosition(rs.getInt("KEY_SEQ"));
        };

        tableChilds(table.mutator()::addNewPrimaryKeyColumn, supplier, mutator, progressListener);
    }

    protected void indexes(Connection connection, Table table, ProgressMeasure progressListener) {
        requireNonNulls(connection, table);

        final Schema schema = table.getParentOrThrow();
        final SqlSupplier<ResultSet> supplier = ()
            -> connection.getMetaData().getIndexInfo(
                jdbcCatalogLookupName(schema),
                jdbcSchemaLookupName(schema),
                metaDataTableNameForIndexes(table), // Todo: break out in protected method
                false,
                false
            );

        final TableChildMutator<Index, ResultSet> mutator = (index, rs) -> {
            final String indexName = rs.getString("INDEX_NAME");
            final boolean unique = !rs.getBoolean("NON_UNIQUE");

            index.mutator().setName(indexName);
            index.mutator().setUnique(unique);

            final IndexColumn indexColumn = index.mutator().addNewIndexColumn();
            indexColumn.mutator().setName(rs.getString("COLUMN_NAME"));
            indexColumn.mutator().setOrdinalPosition(rs.getInt("ORDINAL_POSITION"));
            final String ascOrDesc = rs.getString("ASC_OR_DESC");

            if ("A".equalsIgnoreCase(ascOrDesc)) {
                indexColumn.mutator().setOrderType(OrderType.ASC);
            } else if ("D".equalsIgnoreCase(ascOrDesc)) {
                indexColumn.mutator().setOrderType(OrderType.DESC);
            } else {
                indexColumn.mutator().setOrderType(OrderType.NONE);
            }
        };

        final SqlPredicate<ResultSet> filter = rs -> {
            final String type = rs.getString("TYPE");
            final String indexName = rs.getString("INDEX_NAME");
            return nonNull(indexName);
        };

        tableChilds(table.mutator()::addNewIndex, supplier, mutator, filter, progressListener);
    }

    protected void foreignKeys(Connection connection, Table table, ProgressMeasure progressListener) {
        requireNonNulls(connection, table);

        final Schema schema = table.getParentOrThrow();
        final SqlSupplier<ResultSet> supplier = ()
            -> connection.getMetaData().getImportedKeys(
                jdbcCatalogLookupName(schema),
                jdbcSchemaLookupName(schema),
                metaDataTableNameForForeignKeys(table)
            );

        final TableChildMutator<ForeignKey, ResultSet> mutator = (foreignKey, rs) -> {

            final String foreignKeyName = rs.getString("FK_NAME");
            foreignKey.mutator().setName(foreignKeyName);

            final ForeignKeyColumn foreignKeyColumn = foreignKey.mutator().addNewForeignKeyColumn();
            final ForeignKeyColumnMutator fkcMutator = foreignKeyColumn.mutator();
            fkcMutator.setName(rs.getString("FKCOLUMN_NAME"));
            fkcMutator.setOrdinalPosition(rs.getInt("KEY_SEQ"));
            fkcMutator.setForeignTableName(rs.getString("PKTABLE_NAME"));
            fkcMutator.setForeignColumnName(rs.getString("PKCOLUMN_NAME"));
        };

        tableChilds(table.mutator()::addNewForeignKey, supplier, mutator, progressListener);
    }

    protected <T> void tableChilds(
        Supplier<T> childSupplier,
        SqlSupplier<ResultSet> resultSetSupplier,
        TableChildMutator<T, ResultSet> resultSetMutator,
        ProgressMeasure progressListener
    ) {
        tableChilds(childSupplier, resultSetSupplier, resultSetMutator, rs -> true, progressListener);
    }

    protected <T> void tableChilds(
        Supplier<T> childSupplier,
        SqlSupplier<ResultSet> resultSetSupplier,
        TableChildMutator<T, ResultSet> resultSetMutator,
        SqlPredicate<ResultSet> filter,
        ProgressMeasure progressListener
    ) {
        requireNonNulls(childSupplier, resultSetSupplier, resultSetMutator);

        try (final ResultSet rsChild = resultSetSupplier.get()) {

            final ResultSetMetaData rsmd = rsChild.getMetaData();
            final int numberOfColumns = rsmd.getColumnCount();

            final Set<Integer> doNotTouchSet = new HashSet<>();
            if (SHOW_METADATA) {
                for (int x = 1; x <= numberOfColumns; x++) {
                    final int columnType = rsmd.getColumnType(x);
                    LOGGER.info(x + ":" + rsmd.getColumnName(x) + ", " + rsmd.getColumnClassName(x) + ", " + columnType);
                    if (columnType == Types.LONGVARCHAR || columnType == Types.LONGNVARCHAR) {
                        doNotTouchSet.add(x);
                    }
                }
            }

            while (rsChild.next()) {
                if (SHOW_METADATA) {
                    for (int x = 1; x <= numberOfColumns; x++) {
                        final Object val;
                        // Some type of columns can only be read once
                        if (doNotTouchSet.contains(x)) {
                            val = "{unread}";
                        } else {
                            val = rsChild.getObject(x);
                        }

                        LOGGER.info(x + ":" + rsmd.getColumnName(x) + ":'" + val + "'");
                    }
                }
                if (filter.test(rsChild)) {
                    resultSetMutator.mutate(childSupplier.get(), rsChild);
                } else {
                    LOGGER.info("Skipped due to RS filtering");
                }
            }
        } catch (final SQLException sqle) {
            LOGGER.error(sqle, "Unable to read table child.");
            throw new SpeedmentException(sqle);
        }
    }

    protected String jdbcSchemaLookupName(Schema schema) {
        return null;
    }

    protected String jdbcCatalogLookupName(Schema schema) {
        return schema.getName();
    }
    
    protected String metaDataTableNameForColumns(Table table) {
        return table.getName();
    }
    
    protected String metaDataTableNameForIndexes(Table table) {
        return table.getName();
    }
    
    protected String metaDataTableNameForPrimaryKeys(Table table) {
        return table.getName();
    }
    
    protected String metaDataTableNameForForeignKeys(Table table) {
        return table.getName();
    }    

    @Override
    public <T> Stream<T> executeQuery(String sql, List<?> values, SqlFunction<ResultSet, T> rsMapper) {
        requireNonNulls(sql, values, rsMapper);

        try (
            final Connection connection = getConnection(dbms);
            final PreparedStatement ps = connection.prepareStatement(sql)) {
            int i = 1;
            for (final Object o : values) {
                ps.setObject(i++, o);
            }
            final ResultSet rs = ps.executeQuery();

            // Todo: Make a transparent stream with closeHandler added.
            final Stream.Builder<T> streamBuilder = Stream.builder();
            while (rs.next()) {
                streamBuilder.add(rsMapper.apply(rs));
            }
            return streamBuilder.build();
        } catch (SQLException sqle) {
            LOGGER.error(sqle, "Error querying " + sql);
            throw new SpeedmentException(sqle);
        }
    }

    @Override
    public <T> AsynchronousQueryResult<T> executeQueryAsync(
        String sql, List<?> values, Function<ResultSet, T> rsMapper) {

        return new AsynchronousQueryResultImpl<>(
            Objects.requireNonNull(sql),
            Objects.requireNonNull(values),
            Objects.requireNonNull(rsMapper),
            () -> getConnection(dbms)
        );
    }

    @Override
    public void executeUpdate(
        String sql, List<?> values, Consumer<List<Long>> generatedKeysConsumer)
        throws SQLException {

        final List<SqlUpdateStatement> sqlStatementList = new ArrayList<>();
        final SqlUpdateStatement sqlUpdateStatement
            = new SqlUpdateStatement(sql, values, generatedKeysConsumer);

        sqlStatementList.add(sqlUpdateStatement);
        executeUpdate(sqlStatementList);
    }

    private void executeUpdate(List<SqlUpdateStatement> sqlStatementList)
        throws SQLException {

        requireNonNull(sqlStatementList);
        int retryCount = 5;
        boolean transactionCompleted = false;

        do {
            SqlStatement lastSqlStatement = null;
            Connection conn = null;
            try {
                conn = getConnection(dbms);
                conn.setAutoCommit(false);
                for (final SqlUpdateStatement sqlStatement : sqlStatementList) {
                    try (final PreparedStatement ps = conn.prepareStatement(sqlStatement.getSql(), Statement.RETURN_GENERATED_KEYS)) {

                        int i = 1;
                        for (Object o : sqlStatement.getValues()) {
                            ps.setObject(i++, o);
                        }

                        ps.executeUpdate();

                        try (final ResultSet generatedKeys = ps.getGeneratedKeys()) {
                            while (generatedKeys.next()) {
                                final Object genKey = generatedKeys.getObject(1);
                                if (!"oracle.sql.ROWID".equals(genKey.getClass()
                                    .getName())) {
                                    sqlStatement.addGeneratedKey(generatedKeys.getLong(1));
                                } else {
                                    // Handle ROWID, make result = map<,String>
                                    // instead...
                                }
                            }
                        }

                    }
                }
                conn.commit();
                conn.close();
                transactionCompleted = true;
                conn = null;
            } catch (SQLException sqlEx) {
                LOGGER.error("SqlStatementList: " + sqlStatementList);
                LOGGER.error("SQL: " + lastSqlStatement);
                LOGGER.error(sqlEx, sqlEx.getMessage());
                final String sqlState = sqlEx.getSQLState();

                if ("08S01".equals(sqlState) || "40001".equals(sqlState)) {
                    retryCount--;
                } else {
                    retryCount = 0;
                    throw sqlEx; // Finally will be executed...
                }
            } finally {

                if (!transactionCompleted) {
                    try {
                        // If we got here, and conn is not null, the
                        // transaction should be rolled back, as not
                        // all work has been done
                        try {
                            conn.rollback();
                        } finally {
                            conn.close();
                        }
                    } catch (SQLException sqlEx) {
                        //
                        // If we got an exception here, something
                        // pretty serious is going on, so we better
                        // pass it up the stack, rather than just
                        // logging it. . .
                        LOGGER.error(sqlEx, "Rollback error! connection:" + sqlEx.getMessage());
                        throw sqlEx;
                    }
                }
            }
        } while (!transactionCompleted && (retryCount > 0));

        if (transactionCompleted) {
            sqlStatementList.forEach(SqlUpdateStatement::acceptGeneratedKeys);
        }
    }

    protected Map<String, Class<?>> readTypeMapFromDB(Dbms dbms) throws SQLException {
        requireNonNull(dbms);
        final Map<String, Class<?>> result;

        try (final Connection connection = getConnection(dbms)) {
            result = new ConcurrentHashMap<>();
            try (final ResultSet rs = connection.getMetaData().getTypeInfo()) {
                while (rs.next()) {
                    final SqlTypeInfo typeInfo = SqlTypeInfo.from(rs);
                    if (typeInfo.getSqlTypeName().contains("BLOB")) {
                        int foo = 1;
                    }

                    final Class<?> mappedClass = speedment.getSqlTypeMapperComponent().apply(dbms, typeInfo);
                    result.put(typeInfo.getSqlTypeName(), mappedClass);
                }
            }
        }

        return result;
    }

    // Todo: Use DataSource instead: http://docs.oracle.com/javase/tutorial/jdbc/basics/sqldatasources.html
    private Connection getConnection(Dbms dbms) {
        final String url = DocumentDbUtil.findConnectionUrl(speedment, dbms);
        final String user = unwrap(dbms.getUsername());
        final char[] password = unwrap(speedment.getPasswordComponent().get(dbms));

        final Connection conn;
        try {
            conn = speedment.getConnectionPoolComponent()
                .getConnection(url, user, password == null ? null : new String(password));
        } catch (final SQLException ex) {
            final String msg
                = "Unable to get connection for " + dbms
                + " using url \"" + url + "\", user = " + user
                + ", password = " + PASSWORD_PROTECTED;

            LOGGER.error(ex, msg);
            throw new SpeedmentException(msg, ex);
        }

        return conn;
    }

    @FunctionalInterface
    protected interface TableChildMutator<T, U> {

        void mutate(T t, U u) throws SQLException;
    }

    protected Map<String, Class<?>> readTypeMapFromSet(Set<SqlTypeInfo> typeInfos) {
        requireNonNull(typeInfos);

        return typeInfos.stream()
            .collect(toMap(
                SqlTypeInfo::getSqlTypeName,
                sti -> speedment.getSqlTypeMapperComponent().apply(dbms, sti))
            );
    }

    private <P extends HasName, D extends Document & HasName & HasMainInterface & HasParent<P>> String actionName(D doc) {
        return "Reading metadata from " + doc.mainInterface().getSimpleName() + " " + doc.getParentOrThrow().getName() + "." + doc.getName();
    }
    
    protected String encloseField(String fieldName) {
        return dbmsType.getDatabaseNamingConvention().encloseField(fieldName);
    }
    
}
