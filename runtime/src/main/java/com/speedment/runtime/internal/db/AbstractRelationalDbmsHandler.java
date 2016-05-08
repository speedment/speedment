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
package com.speedment.runtime.internal.db;

import com.speedment.runtime.Speedment;
import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.ForeignKey;
import com.speedment.runtime.config.ForeignKeyColumn;
import com.speedment.runtime.config.Index;
import com.speedment.runtime.config.IndexColumn;
import com.speedment.runtime.config.PrimaryKeyColumn;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Schema;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.mapper.TypeMapper;
import com.speedment.runtime.config.mutator.ForeignKeyColumnMutator;
import com.speedment.runtime.config.parameter.DbmsType;
import com.speedment.runtime.config.parameter.OrderType;
import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.runtime.config.trait.HasName;
import com.speedment.runtime.config.trait.HasParent;
import com.speedment.runtime.db.AsynchronousQueryResult;
import com.speedment.runtime.db.DatabaseNamingConvention;
import com.speedment.runtime.db.DbmsHandler;
import com.speedment.runtime.db.SqlFunction;
import com.speedment.runtime.db.SqlPredicate;
import com.speedment.runtime.db.SqlSupplier;
import com.speedment.runtime.db.metadata.ColumnMetaData;
import com.speedment.runtime.db.metadata.TypeInfoMetaData;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.field.trait.FieldTrait;
import com.speedment.runtime.field.trait.ReferenceFieldTrait;
import com.speedment.runtime.internal.config.ProjectImpl;
import com.speedment.runtime.internal.manager.sql.SqlDeleteStatement;
import com.speedment.runtime.internal.manager.sql.SqlInsertStatement;
import com.speedment.runtime.internal.manager.sql.SqlStatement;
import com.speedment.runtime.internal.manager.sql.SqlUpdateStatement;
import com.speedment.runtime.internal.runtime.typemapping.StandardJavaTypeMapping;
import static com.speedment.runtime.internal.stream.OptionalUtil.unwrap;
import com.speedment.fika.logger.Logger;
import com.speedment.fika.logger.LoggerManager;
import static com.speedment.runtime.internal.util.CaseInsensitiveMaps.newCaseInsensitiveMap;
import com.speedment.runtime.internal.util.document.DocumentDbUtil;
import static com.speedment.runtime.internal.util.document.DocumentDbUtil.dbmsTypeOf;
import com.speedment.runtime.internal.util.document.DocumentUtil;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import com.speedment.runtime.util.ProgressMeasure;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Struct;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;
import static java.util.Collections.singletonList;
import java.util.Map.Entry;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 * @author Emil Forslund
 */
public abstract class AbstractRelationalDbmsHandler implements DbmsHandler {

    private final static Logger LOGGER = LoggerManager.getLogger(AbstractRelationalDbmsHandler.class);

    protected static final Class<?> DEFAULT_MAPPING = Object.class;
    protected static final String YES = "YES";

    private final static String PASSWORD_PROTECTED = "********";
    public static final boolean SHOW_METADATA = false; // Warning: Enabling SHOW_METADATA will make some dbmses fail on metadata (notably Oracle) because all the columns must be read in order...

    protected final Speedment speedment;
    protected final Dbms dbms; // No not use for metadata reads.
    protected final Map<String, Class<?>> javaTypeMap;

    public AbstractRelationalDbmsHandler(Speedment speedment, Dbms dbms) {
        this.speedment = requireNonNull(speedment);
        this.dbms = requireNonNull(dbms);
        javaTypeMap = newCaseInsensitiveMap();
        setupJavaTypeMap();
        assertJavaTypesKnown();
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
        final Set<TypeInfoMetaData> preSet = dbmsType.getDataTypes();

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
            @SuppressWarnings({"unchecked", "rawtypes"})
            final CompletableFuture<Schema>[] tablesTask
                = dbms.schemas()
                .map(schema -> tables(sqlTypeMappingTask, dbms, schema, progress))
                .toArray(s -> (CompletableFuture<Schema>[]) new CompletableFuture[s]);

            //CompletableFuture[] foo = new CompletableFuture[2];
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

                if (SHOW_METADATA) {
                    final ResultSetMetaData rsmd = rsTable.getMetaData();
                    int numberOfColumns = rsmd.getColumnCount();
                    for (int x = 1; x <= numberOfColumns; x++) {
                        LOGGER.debug(rsmd.getColumnName(x) + ", " + rsmd.getColumnClassName(x) + ", " + rsmd.getColumnType(x));
                    }
                }

                while (rsTable.next()) {
                    if (SHOW_METADATA) {
                        final ResultSetMetaData rsmd = rsTable.getMetaData();
                        int numberOfColumns = rsmd.getColumnCount();
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
            schema.tables().map(table -> sqlTypeMapping.thenAcceptAsync(mapping -> {
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

            final ColumnMetaData md = ColumnMetaData.of(rs);

            final String columnName = md.getColumnName();

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

            final Class<?> lookupJdbcClass = lookupJdbcClass(sqlTypeMapping, md);

            final Class<?> selectedJdbcClass;
            if (lookupJdbcClass != null) {
                selectedJdbcClass = lookupJdbcClass;
            } else {
                // Fall-back to DEFAULT_MAPPING
                selectedJdbcClass = DEFAULT_MAPPING;
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
            progressListener.setCurrentAction(actionName(column));

        };

        tableChilds(table.mutator()::addNewColumn, supplier, mutator, progressListener);
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
            final ForeignKeyColumnMutator<?> fkcMutator = foreignKeyColumn.mutator();
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

            if (SHOW_METADATA) {
                final ResultSetMetaData rsmd = rsChild.getMetaData();
                final int numberOfColumns = rsmd.getColumnCount();
                for (int x = 1; x <= numberOfColumns; x++) {
                    final int columnType = rsmd.getColumnType(x);
                    LOGGER.info(x + ":" + rsmd.getColumnName(x) + ", " + rsmd.getColumnClassName(x) + ", " + columnType);
                }
            }

            while (rsChild.next()) {
                if (SHOW_METADATA) {
                    final ResultSetMetaData rsmd = rsChild.getMetaData();
                    final int numberOfColumns = rsmd.getColumnCount();
                    for (int x = 1; x <= numberOfColumns; x++) {
                        final Object val;
                        val = rsChild.getObject(x);
                        LOGGER.info(x + ":" + rsmd.getColumnName(x) + ":'" + val + "'");
                    }
                }
                if (filter.test(rsChild)) {
                    resultSetMutator.mutate(childSupplier.get(), rsChild);
                } else {
                    LOGGER.info("Skipped due to RS filtering. This is normal for some DBMS types.");
                }
            }
        } catch (final SQLException sqle) {
            LOGGER.error(sqle, "Unable to read table child.");
            throw new SpeedmentException(sqle);
        }
    }

    /**
     * Sets the autoIncrement property of a Column.
     *
     * @param column to use
     * @param rs that contains column metadata (per
     * connection.getMetaData().getColumns(...))
     */
    protected void setAutoIncrement(Column column, ColumnMetaData md) throws SQLException {
        final String isAutoIncrementString = md.getIsAutoincrement();
        final String isGeneratedColumnString = md.getIsGeneratedcolumn();

        if (YES.equalsIgnoreCase(isAutoIncrementString) || YES.equalsIgnoreCase(isGeneratedColumnString)) {
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
    protected Class<?> lookupJdbcClass(Map<String, Class<?>> sqlTypeMapping, ColumnMetaData md) {
        requireNonNull(md);

        // Firstly, try  md.getTypeName()
        Class<?> result = sqlTypeMapping.get(md.getTypeName());
        if (result == null) {
            final int type = md.getDataType(); // Type (int) according to java.sql.Types (e.g. 4) that we got from the ColumnMetaData
            final Optional<String> oTypeName = TypeInfoMetaData.lookupJavaSqlType(type); // Variable name (String) according to java.sql.Types (e.g. INTEGER)       
            if (oTypeName.isPresent()) {
                final String typeName = oTypeName.get();
                // Secondly, try the corresponding name using md.getDataType() and then lookup java.sql.Types name
                result = sqlTypeMapping.get(typeName);
            }
        }
        return result;
    }

    /**
     * Returns the schema lookup name used when calling
     * connection.getMetaData().getXxxx(y, schemaLookupName, ...) methods.
     *
     * @param table to use
     * @return the schema lookup name used when calling
     * connection.getMetaData().getXxxx(y, schemaLookupName, ...) methods
     */
    protected String jdbcSchemaLookupName(Schema schema) {
        return null;
    }

    /**
     * Returns the catalog lookup name used when calling
     * connection.getMetaData().getXxxx(catalogLookupName, ...) methods.
     *
     * @param table to use
     * @return the catalog lookup name used when calling
     * connection.getMetaData().getXxxx(catalogLookupName, ...) methods
     */
    protected String jdbcCatalogLookupName(Schema schema) {
        return schema.getName();
    }

    /**
     * Returns the table name used when calling the
     * connection.getMetaData().getColumns() method.
     *
     * @param table to use
     * @return the table name used when calling
     * connection.getMetaData().getColumns() method
     */
    protected String metaDataTableNameForColumns(Table table) {
        return table.getName();
    }

    /**
     * Returns the table name used when calling the
     * connection.getMetaData().getIndexes() method.
     *
     * @param table to use
     * @return the table name used when calling
     * connection.getMetaData().getIndexes() method
     */
    protected String metaDataTableNameForIndexes(Table table) {
        return table.getName();
    }

    /**
     * Returns the table name used when calling the
     * connection.getMetaData().getPrimaryKeys() method.
     *
     * @param table to use
     * @return the table name used when calling the
     * connection.getMetaData().getPrimaryKeys() method
     */
    protected String metaDataTableNameForPrimaryKeys(Table table) {
        return table.getName();
    }

    /**
     * Returns the table name used when calling the
     * connection.getMetaData().getImportedKeys() method.
     *
     * @param table to use
     * @return the table name used when calling the
     * connection.getMetaData().getImportedKeys() method
     */
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
            try (final ResultSet rs = ps.executeQuery()) {

                // Todo: Make a transparent stream with closeHandler added.
                final Stream.Builder<T> streamBuilder = Stream.builder();
                while (rs.next()) {
                    streamBuilder.add(rsMapper.apply(rs));
                }
                return streamBuilder.build();
            }
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
    public <F extends FieldTrait & ReferenceFieldTrait<?, ?, ?>> void executeInsert(String sql, List<?> values, List<F> generatedKeyFields, Consumer<List<Long>> generatedKeyConsumer) throws SQLException {
        final SqlInsertStatement sqlUpdateStatement = new SqlInsertStatement(sql, values, generatedKeyFields, generatedKeyConsumer);
        execute(singletonList(sqlUpdateStatement));
    }

    @Override
    public void executeUpdate(String sql, List<?> values) throws SQLException {
        final SqlUpdateStatement sqlUpdateStatement = new SqlUpdateStatement(sql, values);
        execute(singletonList(sqlUpdateStatement));
    }

    @Override
    public void executeDelete(String sql, List<?> values) throws SQLException {
        final SqlDeleteStatement sqlDeleteStatement = new SqlDeleteStatement(sql, values);
        execute(singletonList(sqlDeleteStatement));
    }

    protected void execute(List<? extends SqlStatement> sqlStatementList) throws SQLException {
        requireNonNull(sqlStatementList);
        int retryCount = 5;
        boolean transactionCompleted = false;

        do {
            SqlStatement lastSqlStatement = null;
            Connection conn = null;
            try {
                conn = getConnection(dbms);
                conn.setAutoCommit(false);
                for (final SqlStatement sqlStatement : sqlStatementList) {
                    lastSqlStatement = sqlStatement;
                    switch (sqlStatement.getType()) {
                        case INSERT: {
                            final SqlInsertStatement s = (SqlInsertStatement) sqlStatement;
                            handleSqlStatement(conn, s);
                            break;
                        }
                        case UPDATE: {
                            final SqlUpdateStatement s = (SqlUpdateStatement) sqlStatement;
                            handleSqlStatement(conn, s);
                            break;
                        }
                        case DELETE: {
                            final SqlDeleteStatement s = (SqlDeleteStatement) sqlStatement;
                            handleSqlStatement(conn, s);
                            break;
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
                        if (conn != null) {
                            try {
                                conn.rollback();
                            } finally {
                                conn.close();
                            }
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
            postSuccessfulTransaction(sqlStatementList);
        }
    }

    protected void handleSqlStatement(final Connection conn, final SqlInsertStatement sqlStatement) throws SQLException {
        try (final PreparedStatement ps = conn.prepareStatement(sqlStatement.getSql(), Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            for (Object o : sqlStatement.getValues()) {
                ps.setObject(i++, o);
            }
            ps.executeUpdate();

            try (final ResultSet generatedKeys = ps.getGeneratedKeys()) {
                while (generatedKeys.next()) {
                    sqlStatement.addGeneratedKey(generatedKeys.getLong(1));
                }
            }
        }
    }

    protected void handleSqlStatement(final Connection conn, final SqlUpdateStatement sqlStatement) throws SQLException {
        handleSqlStatementHelper(conn, sqlStatement);
    }

    protected void handleSqlStatement(final Connection conn, final SqlDeleteStatement sqlStatement) throws SQLException {
        handleSqlStatementHelper(conn, sqlStatement);
    }

    private void handleSqlStatementHelper(final Connection conn, final SqlStatement sqlStatement) throws SQLException {
        try (final PreparedStatement ps = conn.prepareStatement(sqlStatement.getSql(), Statement.NO_GENERATED_KEYS)) {
            int i = 1;
            for (Object o : sqlStatement.getValues()) {
                ps.setObject(i++, o);
            }
            ps.executeUpdate();
        }
    }

    protected void postSuccessfulTransaction(List<? extends SqlStatement> sqlStatementList) {
        for (final SqlStatement sqlStatement : sqlStatementList) {
            if (sqlStatement instanceof SqlInsertStatement) {
                final SqlInsertStatement us = (SqlInsertStatement) sqlStatement;
                us.acceptGeneratedKeys();
            }
        }
    }

    protected Map<String, Class<?>> readTypeMapFromDB(Dbms dbms) throws SQLException {
        requireNonNull(dbms);

        final List<TypeInfoMetaData> typeInfoMetaDataList = new ArrayList<>();
        try (final Connection connection = getConnection(dbms)) {
            try (final ResultSet rs = connection.getMetaData().getTypeInfo()) {
                while (rs.next()) {
                    final TypeInfoMetaData typeInfo = TypeInfoMetaData.of(rs);
                    typeInfoMetaDataList.add(typeInfo);
                }
            }
            return typeMapFromTypeInfo(typeInfoMetaDataList);
        }
    }

    protected Map<String, Class<?>> readTypeMapFromSet(Set<TypeInfoMetaData> typeInfos) {
        requireNonNull(typeInfos);

        return typeMapFromTypeInfo(new ArrayList<>(typeInfos));
    }

    protected Map<String, Class<?>> typeMapFromTypeInfo(List<TypeInfoMetaData> typeInfoMetaDataList) {
        requireNonNull(typeInfoMetaDataList);

        final Map<String, Class<?>> result = newCaseInsensitiveMap();
        // First, put the java.sql.Types mapping for all types
        typeInfoMetaDataList.forEach(ti -> {
            final Optional<String> javaSqlTypeName = ti.javaSqlTypeName();

            javaSqlTypeName.ifPresent(tn -> {
                final Class<?> mappedClass = javaTypeMap.get(tn);
                if (mappedClass != null) {
                    result.put(tn, mappedClass);
                }
            });
        });

        // Then, put the typeInfo sqlName (That may be more specific) for all types
        typeInfoMetaDataList.forEach(ti -> {
            final String key = ti.getSqlTypeName();
            final Class<?> mappedClass = javaTypeMap.get(key);
            if (mappedClass != null) {
                result.put(key, mappedClass);
            } else {
                final Optional<String> javaSqlTypeName = ti.javaSqlTypeName();
                javaSqlTypeName.ifPresent(ltn -> {
                    final Class<?> lookupMappedClass = javaTypeMap.get(ltn);
                    if (lookupMappedClass != null) {
                        result.put(key, lookupMappedClass);
                    }
                });
            }
        });
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

    private <P extends HasName, D extends Document & HasName & HasMainInterface & HasParent<P>> String actionName(D doc) {
        return doc.mainInterface().getSimpleName() + " " + doc.getName() + " in " + doc.getParentOrThrow().getName();
        //return "Read " + doc.mainInterface().getSimpleName() + " " + doc.getParentOrThrow().getName() + "." + doc.getName();
    }

    protected String encloseField(Dbms dbms, String fieldName) {
        return dbmsTypeOf(speedment, dbms).getDatabaseNamingConvention().encloseField(fieldName);
    }

    @Override
    public String getDbmsInfoString() throws SQLException {
        try (final Connection conn = getConnection(dbms)) {
            final DatabaseMetaData md = conn.getMetaData();
            return new StringBuilder()
                .append(md.getDatabaseProductName())
                .append(", ")
                .append(md.getDatabaseProductVersion())
                .append(", ")
                .append(md.getDriverName())
                .append(" ")
                .append(md.getDriverVersion())
                .append(", JDBC version ")
                .append(md.getJDBCMajorVersion())
                .append(".")
                .append(md.getJDBCMinorVersion())
                .toString();
        }
    }

    /**
     * Sets up the java type map for this database type
     */
    protected void setupJavaTypeMap() {

        http://docs.oracle.com/javase/1.5.0/docs/guide/jdbc/getstart/mapping.html

        javaTypeMap.put("CHAR", String.class);
        javaTypeMap.put("VARCHAR", String.class);
        javaTypeMap.put("LONGVARCHAR", String.class);
        javaTypeMap.put("LONGVARCHAR", String.class);
        javaTypeMap.put("NUMERIC", BigDecimal.class);
        javaTypeMap.put("DECIMAL", BigDecimal.class);
        javaTypeMap.put("BIT", Integer.class); ///
        javaTypeMap.put("TINYINT", Byte.class);
        javaTypeMap.put("SMALLINT", Short.class);
        javaTypeMap.put("INTEGER", Integer.class);
        javaTypeMap.put("BIGINT", Long.class);
        javaTypeMap.put("REAL", Float.class);
        javaTypeMap.put("FLOAT", Double.class);
        javaTypeMap.put("DOUBLE", Double.class);
        //put("BINARY", BYTE_ARRAY_MAPPING);
        //put("VARBINARY", BYTE_ARRAY_MAPPING);
        //put("LONGVARBINARY", BYTE_ARRAY_MAPPING);
        javaTypeMap.put("DATE", java.sql.Date.class);
        javaTypeMap.put("TIME", Time.class);
        javaTypeMap.put("TIMESTAMP", Timestamp.class);
        javaTypeMap.put("CLOB", Clob.class);
        javaTypeMap.put("BLOB", Blob.class);
        //put("ARRAY", ARRAY_MAPPING);
        javaTypeMap.put("BOOLEAN", Boolean.class);
        javaTypeMap.put("BOOL", Boolean.class); // Added

        //MySQL Specific mappings
        javaTypeMap.put("YEAR", Integer.class);

        //PostgreSQL specific mappings
        javaTypeMap.put("UUID", UUID.class);
        //TODO: Add postgresql specific type mappings

        addCustomJavaTypeMap();
    }

    /**
     * Adds custom java type mapping for this dbms type
     */
    protected void addCustomJavaTypeMap() {
    }

    protected void assertJavaTypesKnown() {
        final Map<String, Class<?>> unmapped = new LinkedHashMap<>();
        for (final Entry<String, Class<?>> entry : javaTypeMap.entrySet()) {
            final String key = entry.getKey();
            final Class<?> clazz = entry.getValue();
            if (!StandardJavaTypeMapping.stream().anyMatch(jtm -> jtm.getJavaClass().equals(clazz))) {
                unmapped.put(key, clazz);
            }
        }
        if (!unmapped.isEmpty()) {
            throw new SpeedmentException("There are mappings that have no " + StandardJavaTypeMapping.class.getSimpleName() + " " + unmapped);
        }
    }

    private static String normalize(String string) {
        return string.toUpperCase();
    }

    @Override
    public Clob createClob() throws SQLException {
        return applyOnConnection(Connection::createClob);
    }

    @Override
    public Blob createBlob() throws SQLException {
        return applyOnConnection(Connection::createBlob);
    }

    @Override
    public NClob createNClob() throws SQLException {
        return applyOnConnection(Connection::createNClob);
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        return applyOnConnection(Connection::createSQLXML);
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        try (final Connection connection = getConnection(dbms)) {
            return connection.createArrayOf(typeName, elements);
        }
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        try (final Connection connection = getConnection(dbms)) {
            return connection.createStruct(typeName, attributes);
        }
    }

    private <T> T applyOnConnection(SqlFunction<Connection, T> mapper) throws SQLException {
        try (final Connection c = getConnection(dbms)) {
            return mapper.apply(c);
        }
    }

}
