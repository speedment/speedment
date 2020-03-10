/*
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.core.abstracts;

import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.runtime.config.*;
import com.speedment.runtime.config.mutator.ForeignKeyColumnMutator;
import com.speedment.runtime.config.parameter.OrderType;
import com.speedment.runtime.config.trait.HasId;
import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.runtime.config.trait.HasName;
import com.speedment.runtime.config.trait.HasParent;
import com.speedment.runtime.config.util.DocumentUtil;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.component.connectionpool.ConnectionPoolComponent;
import com.speedment.runtime.core.db.*;
import com.speedment.runtime.core.db.metadata.ColumnMetaData;
import com.speedment.runtime.core.db.metadata.TypeInfoMetaData;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.util.ProgressMeasure;
import com.speedment.runtime.core.util.ProgressMeasureUtil;
import com.speedment.runtime.typemapper.TypeMapper;

import java.sql.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.speedment.common.invariant.NullUtil.requireNonNulls;
import static com.speedment.runtime.core.util.CaseInsensitiveMaps.newCaseInsensitiveMap;
import static com.speedment.runtime.core.util.DatabaseUtil.dbmsTypeOf;
import static java.sql.DatabaseMetaData.*;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author  Emil Forslund
 * @author  Per Minborg
 * @since   3.0.0
 */
public abstract class AbstractDbmsMetadataHandler implements DbmsMetadataHandler {
    
    private static final Logger LOGGER = LoggerManager.getLogger(AbstractDbmsMetadataHandler.class);
    private static final Class<?> DEFAULT_MAPPING = Object.class;

    // Warning: Enabling SHOW_METADATA will make some dbmses fail on metadata (notably Oracle) because all the columns must be read in order...
    public static final boolean SHOW_METADATA = Boolean.getBoolean("speedment.metadata.show");

    public static final boolean EXTRA_INFO = Boolean.getBoolean("speedment.metadata.info");

    private final ConnectionPoolComponent connectionPoolComponent;
    private final DbmsHandlerComponent dbmsHandlerComponent;
    private final ProjectComponent projectComponent;
    private final Map<Class<? extends Document>, AtomicLong> timers;
    private final JavaTypeMap javaTypeMap;

    protected AbstractDbmsMetadataHandler(
        final ConnectionPoolComponent connectionPoolComponent,
        final DbmsHandlerComponent dbmsHandlerComponent,
        final ProjectComponent projectComponent
    ) {
        this.connectionPoolComponent = requireNonNull(connectionPoolComponent);
        this.dbmsHandlerComponent    = requireNonNull(dbmsHandlerComponent);
        this.projectComponent        = requireNonNull(projectComponent);
        this.timers                  = new ConcurrentHashMap<>();
        this.javaTypeMap             = newJavaTypeMap();
    }
    
    protected JavaTypeMap newJavaTypeMap() {
        return JavaTypeMap.create();
    }
    
    @Override
    public CompletableFuture<Project> readSchemaMetadata(
            final Dbms dbms,
            final ProgressMeasure progress,
            final Predicate<String> filterCriteria) {

        requireNonNulls(filterCriteria, progress);

        // Create a deep copy of the project document.
        final Project projectCopy = DocumentUtil.deepCopy(
            projectComponent.getProject(), Project::create
        );

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
            projectCopy, dbmsCopy, filterCriteria, progress
        ).whenCompleteAsync((project, ex) -> {
            progress.setProgress(ProgressMeasureUtil.DONE);
            if (ex != null) {
                progress.setCurrentAction("Error!");
                throw new SpeedmentException("Unable to read schema metadata.", ex);
            } else {
                progress.setCurrentAction("Done!");
            }
            LOGGER.info("Aggregate duration of metadata retrieval [ms]: " +
                timers.entrySet().stream()
                    .map(e -> String.format("%s=%,d", e.getKey().getSimpleName(), e.getValue().get()))
                .collect(joining(", "))
            );
        });
    }
    
    
    @Override
    public String getDbmsInfoString(Dbms dbms) throws SQLException {
        try (final Connection conn = getConnection(dbms)) {
            final DatabaseMetaData md = conn.getMetaData();
            return md.getDatabaseProductName() +
                ", " +
                md.getDatabaseProductVersion() +
                ", " +
                md.getDriverName() +
                " " +
                md.getDriverVersion() +
                ", JDBC version " +
                md.getJDBCMajorVersion() +
                "." +
                md.getJDBCMinorVersion();
        }
    }

    protected DbmsHandlerComponent dbmsHandlerComponent() {
        return dbmsHandlerComponent;
    }

    private CompletableFuture<Project> readSchemaMetadata(
        final Project project,
        final Dbms dbms,
        final Predicate<String> filterCriteria,
        final ProgressMeasure progress
    ) {
        requireNonNulls(project, dbms, filterCriteria, progress);

        extraInfo("readSchemaMetadata(%s, %s, ...)", project, dbms);

        final DbmsType dbmsType = dbmsTypeOf(dbmsHandlerComponent, dbms);
        final String action = actionName(dbms);

        LOGGER.info(action);
        progress.setCurrentAction(action);

        final Set<String> discardedSchemas = new HashSet<>();
        final DatabaseNamingConvention naming = dbmsType.getDatabaseNamingConvention();
        final Set<TypeInfoMetaData> preSet = dbmsType.getDataTypes();

        // Task that downloads the SQL Type Mappings from the database
        final CompletableFuture<Map<String, Class<?>>> sqlTypeMappingTask = CompletableFuture.supplyAsync(() ->
            loadSqlTypeMappings(dbms, preSet)
        );

        // Task that downloads the schemas from the database
        final CompletableFuture<Void> schemasTask = CompletableFuture.runAsync(() ->
            loadSchemas(dbms, filterCriteria, dbmsType, discardedSchemas, naming)
        );

        // Task that downloads the catalogs from the database
        final CompletableFuture<Void> catalogsTask = CompletableFuture.runAsync(() ->
            loadCatalogs(dbms, filterCriteria, discardedSchemas, naming)
        );

        // Create a new task that will execute once the schemas and the catalogs 
        // have been loaded independently of each other.
        return CompletableFuture.allOf(
            schemasTask,
            catalogsTask
        ).thenComposeAsync(v -> {
            @SuppressWarnings({"unchecked", "rawtypes"})
            final CompletableFuture<Schema>[] tablesTask
                = dbms.schemas()
                .map(schema -> tables(sqlTypeMappingTask, dbms, schema, progress))
                .toArray(s -> (CompletableFuture<Schema>[]) new CompletableFuture[s]);

            return CompletableFuture.allOf(tablesTask)
                .handleAsync((v2, ex) -> {
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
                            "An exception occurred while the tables were loading.", ex
                        );
                    }
                });
        });
    }

    private void loadCatalogs(Dbms dbms, Predicate<String> filterCriteria, Set<String> discardedSchemas, DatabaseNamingConvention naming) {
        try (final Connection connection = getConnection(dbms)) {
            try (final ResultSet catalogResultSet = connection.getMetaData().getCatalogs()) {
                while (catalogResultSet.next()) {
                    final String schemaName = catalogResultSet.getString(1);
                    boolean schemaWasUsed = false;
                    if (filterCriteria.test(schemaName) && !naming.getSchemaExcludeSet().contains(schemaName)) {
                        extraInfo("loadCatalogs used %s", schemaName);
                        final Schema schema = dbms.mutator().addNewSchema();
                        schema.mutator().setId(schemaName);
                        schema.mutator().setName(schemaName);
                        schemaWasUsed = true;
                    } else {
                        extraInfo("loadCatalogs discarded %s", schemaName);
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
    }

    private void loadSchemas(Dbms dbms, Predicate<String> filterCriteria, DbmsType dbmsType, Set<String> discardedSchemas, DatabaseNamingConvention naming) {
        try (final Connection connection = getConnection(dbms)) {
            try (final ResultSet rs = connection.getMetaData().getSchemas(null, null)) {
                while (rs.next()) {

                    final String name = readSchemaName(rs, dbmsType);

                    boolean schemaWasUsed = false;
                    if (!naming.getSchemaExcludeSet().contains(name)&& filterCriteria.test(name)) {
                        extraInfo("loadSchemas used %s", name);
                        final Schema schema = dbms.mutator().addNewSchema();
                        schema.mutator().setId(name);
                        schema.mutator().setName(name);
                        schemaWasUsed = true;
                    } else {
                        extraInfo("loadCatalogs discarded %s", name);
                    }

                    if (!schemaWasUsed) {
                        discardedSchemas.add(name);
                    }
                }
            }
        } catch (final SQLException sqle) {
            throw new SpeedmentException(
                "Error reading metadata from result set.", sqle
            );
        }
    }

    private Map<String, Class<?>> loadSqlTypeMappings(Dbms dbms, Set<TypeInfoMetaData> preSet) {
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
    }

    private String readSchemaName(ResultSet rs, DbmsType dbmsType) throws SQLException {
        final String schemaName = rs.getString(dbmsType.getResultSetTableSchema());
        String catalogName = "";
        
        try {
            // This column is not there for Oracle so handle it
            // gracefully....
            catalogName = rs.getString("TABLE_CATALOG");
        } catch (final SQLException ex) {
            LOGGER.info("TABLE_CATALOG not in result set.");
        }
        
        return Optional.ofNullable(schemaName).orElse(catalogName);
    }

    protected CompletableFuture<Schema> tables(CompletableFuture<Map<String, Class<?>>> sqlTypeMapping, Dbms dbms, Schema schema, ProgressMeasure progressListener) {
        requireNonNulls(sqlTypeMapping, dbms, schema, progressListener);
        
        // If the wrapped task has already been cancelled, there is no point in going on.
        if (sqlTypeMapping.isCancelled()) {
            return CompletableFuture.completedFuture(null);
        }

        final String action = actionName(schema);
        LOGGER.info(action);
        progressListener.setCurrentAction(action);

        final long begin = System.currentTimeMillis();
        try (final Connection connection = getConnection(dbms)) {
            try (final ResultSet rsTable = connection.getMetaData().getTables(
                    jdbcCatalogLookupName(schema),
                    jdbcSchemaLookupName(schema),
                    null, new String[] {"TABLE", "VIEW"})) {

                readTables(schema, rsTable);
            }
        } catch (final SQLException sqle) {
            throw new SpeedmentException(sqle);
        }
        final long duration = System.currentTimeMillis() - begin;
        timers.computeIfAbsent(Table.class, unused -> new AtomicLong()).addAndGet(duration);

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
        ).thenApplyAsync(v -> schema);
    }

    private void readTables(Schema schema, ResultSet rsTable) throws SQLException {
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

            final Table table      = schema.mutator().addNewTable();
            final String tableName = rsTable.getString("TABLE_NAME");
            final String tableType = rsTable.getString("TABLE_TYPE");
            table.mutator().setId(tableName);
            table.mutator().setName(tableName);
            table.mutator().setView("VIEW".equals(tableType));
            extraInfo("readTables(%s, ...) read %s", schema.getId(), table);
        }
    }

    protected void columns(
        final Connection connection, 
        final Map<String, Class<?>> sqlTypeMapping, 
        final Table table, 
        final ProgressMeasure progressListener
    ) {
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
            
            column.mutator().setId(columnName);
            column.mutator().setName(columnName);
            column.mutator().setOrdinalPosition(md.getOrdinalPosition());

            final boolean nullable;
            final int nullableValue = md.getNullable();

            if (nullableValue == columnNullable ||
                nullableValue == columnNullableUnknown) {
                nullable = true;
            } else if (nullableValue == columnNoNulls) {
                nullable = false;
            } else {
                throw new SpeedmentException("Unknown nullable type " + nullableValue);
            }

            column.mutator().setNullable(nullable);

            final Class<?> lookupJdbcClass = javaTypeMap.findJdbcType(sqlTypeMapping, md);

            final Class<?> selectedJdbcClass;
            if (lookupJdbcClass != null) {
                selectedJdbcClass = lookupJdbcClass;
            } else {
                // Fall-back to DEFAULT_MAPPING
                selectedJdbcClass = DEFAULT_MAPPING;
                LOGGER.warn(
                    String.format("Unable to determine mapping for table %s, column %s. "
                        + "Type name %s, data type %d, decimal digits %d."
                        + "Fallback to JDBC-type %s",
                        table.getId(),
                        column.getId(),
                        md.getTypeName(),
                        md.getDataType(),
                        md.getDecimalDigits(),
                        selectedJdbcClass.getSimpleName()
                    )
                );                    
            }

            column.mutator().setDatabaseType(selectedJdbcClass);

            if (!nullable && hasPrimitiveClass(selectedJdbcClass)) {
                column.mutator().setTypeMapper(TypeMapper.primitive().getClass());
            }
            
            if ("ENUM".equalsIgnoreCase(md.getTypeName())) {
                final Dbms dbms = schema.getParentOrThrow();
                final List<String> constants = enumConstantsOf(dbms, table, columnName);
                column.mutator().setEnumConstants(constants.stream().collect(joining(",")));
            }

            setAutoIncrement(column, md);
            progressListener.setCurrentAction(actionName(column));
            extraInfo("columns(,, %s, ...) read %s", table.getId(), column);
        };

        tableChilds(Column.class, table.mutator()::addNewColumn, supplier, mutator, progressListener);
    }

    private boolean hasPrimitiveClass(Class<?> clazz) {
        return clazz == Byte.class
            ||  clazz == Short.class
            ||  clazz == Integer.class
            ||  clazz == Long.class
            ||  clazz == Float.class
            ||  clazz == Double.class
            ||  clazz == Character.class
            ||  clazz == Boolean.class;
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
            final String columnName = rs.getString("COLUMN_NAME");
            primaryKeyColumn.mutator().setId(columnName);
            primaryKeyColumn.mutator().setName(columnName);
            primaryKeyColumn.mutator().setOrdinalPosition(rs.getInt("KEY_SEQ"));
            extraInfo("primaryKeyColumns(, %s, ...) read %s", table.getId(), primaryKeyColumn);
        };

        tableChilds(PrimaryKeyColumn.class, table.mutator()::addNewPrimaryKeyColumn, supplier, mutator, progressListener);
        
        if (!table.isView() && table.primaryKeyColumns().noneMatch(pk -> true)) {
            LOGGER.warn("Table '" + table.getId() + "' does not have any primary key.");
        }
    }

    protected void indexes(Connection connection, Table table, ProgressMeasure progressListener) {
        requireNonNulls(connection, table);

        // Fix #566: Some connectors throw an exception if getIndexInfo() is
        // invoked for a database VIEW.
        if (table.isView()) {
            return;
        }

        final Schema schema = table.getParentOrThrow();
        final SqlSupplier<ResultSet> supplier = ()
            -> connection.getMetaData().getIndexInfo(
                jdbcCatalogLookupName(schema),
                jdbcSchemaLookupName(schema),
                metaDataTableNameForIndexes(table),
                false,
                // 'true' below might speed up metadata retrieval since approximations can be used
                // See https://github.com/speedment/speedment-enterprise/issues/168
                true 
            );

        final TableChildMutator<Index, ResultSet> mutator = (index, rs) -> {
            final String indexName = rs.getString("INDEX_NAME");
            final boolean unique = !rs.getBoolean("NON_UNIQUE");

            index.mutator().setId(indexName);
            index.mutator().setName(indexName);
            index.mutator().setUnique(unique);

            final IndexColumn indexColumn = index.mutator().addNewIndexColumn();
            final String columnName = rs.getString("COLUMN_NAME");
            indexColumn.mutator().setId(columnName);
            indexColumn.mutator().setName(columnName);
            indexColumn.mutator().setOrdinalPosition(rs.getInt("ORDINAL_POSITION"));
            final String ascOrDesc = rs.getString("ASC_OR_DESC");

            if ("A".equalsIgnoreCase(ascOrDesc)) {
                indexColumn.mutator().setOrderType(OrderType.ASC);
            } else if ("D".equalsIgnoreCase(ascOrDesc)) {
                indexColumn.mutator().setOrderType(OrderType.DESC);
            } else {
                indexColumn.mutator().setOrderType(OrderType.NONE);
            }
            extraInfo("indexes(, %s, ...) read %s", table.getId(), indexColumn);
        };

        final SqlPredicate<ResultSet> filter = rs -> {
            final String indexName = rs.getString("INDEX_NAME");
            return nonNull(indexName);
        };

        tableChilds(Index.class, table.mutator()::addNewIndex, supplier, mutator, filter, progressListener);
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

            // Use schema name first but if not present, use catalog name
            fkcMutator.setForeignSchemaName(
                Optional.ofNullable(rs.getString("FKTABLE_SCHEM")).orElse(rs.getString("PKTABLE_CAT"))
            );
            extraInfo("foreignKeys(, %s, ...) read %s", table.getId(), fkcMutator);
        };

        tableChilds(ForeignKey.class, table.mutator()::addNewForeignKey, supplier, mutator, progressListener);
    }

    protected <T extends Document> void tableChilds(
        final Class<T> type,
        final Supplier<T> childSupplier,
        final SqlSupplier<ResultSet> resultSetSupplier,
        final TableChildMutator<T, ResultSet> resultSetMutator,
        final ProgressMeasure progressListener
    ) {
        tableChilds(type, childSupplier, resultSetSupplier, resultSetMutator, rs -> true, progressListener);
    }

    protected <T extends Document> void tableChilds(
        final Class<T> type,
        final Supplier<T> childSupplier,
        final SqlSupplier<ResultSet> resultSetSupplier,
        final TableChildMutator<T, ResultSet> resultSetMutator,
        final SqlPredicate<ResultSet> filter,
        final ProgressMeasure progressListener
    ) {
        requireNonNulls(childSupplier, resultSetSupplier, resultSetMutator);

        final long begin = System.currentTimeMillis();
        try (final ResultSet rsChild = resultSetSupplier.get()) {
            readChild(childSupplier, resultSetMutator, filter, rsChild);
        } catch (final SQLException sqle) {
            LOGGER.error(sqle, "Unable to read table child.");
            throw new SpeedmentException(sqle);
        }
        final long duration = System.currentTimeMillis() - begin;
        timers.computeIfAbsent(type, unused -> new AtomicLong()).addAndGet(duration);
    }

    private <T extends Document> void readChild(Supplier<T> childSupplier, TableChildMutator<T, ResultSet> resultSetMutator, SqlPredicate<ResultSet> filter, ResultSet rsChild) throws SQLException {
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
                    final Object val = rsChild.getObject(x);
                    LOGGER.info(x + ":" + rsmd.getColumnName(x) + ":'" + val + "'");
                }
            }
            if (filter.test(rsChild)) {
                resultSetMutator.mutate(childSupplier.get(), rsChild);
            } else {
                LOGGER.info("Skipped due to RS filtering. This is normal for some DBMS types.");
            }
        }
    }

    /**
     * Sets the autoIncrement property of a Column.
     *
     * @param column to use
     * @param md that contains column metadata (per connection.getMetaData().getColumns(...))
     * @throws SQLException  if something goes wrong in JDBC
     */
    protected void setAutoIncrement(Column column, ColumnMetaData md) throws SQLException {
        final String isAutoIncrementString = md.getIsAutoincrement();
        final String isGeneratedColumnString = md.getIsGeneratedcolumn();

        if ("YES".equalsIgnoreCase(isAutoIncrementString) 
        ||  "YES".equalsIgnoreCase(isGeneratedColumnString)) {
            column.mutator().setAutoIncrement(true);
        }
    }

    /**
     * Returns the schema lookup name used when calling
     * connection.getMetaData().getXxxx(y, schemaLookupName, ...) methods.
     *
     * @param schema to use
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
     * @param schema to use
     * @return the catalog lookup name used when calling
     * connection.getMetaData().getXxxx(catalogLookupName, ...) methods
     */
    protected String jdbcCatalogLookupName(Schema schema) {
        return schema.getId();
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
        return table.getId();
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
        return table.getId();
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
        return table.getId();
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
        return table.getId();
    }
    
    /**
     * Returns the table name used when calling the
     * {@code SHOW COLUMNS FROM} statement.
     * 
     * @param table  to use
     * @return       the table name to use
     */
    protected String metaDataTableNameForShowColumns(Table table) {
        return table.getId();
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

    private <P extends HasId & HasName, D extends Document & HasId & HasName & HasMainInterface & HasParent<P>> String actionName(D doc) {
        return doc.mainInterface().getSimpleName() +
            " " +
            doc.getId() +
            " in " +
            doc.getParentOrThrow().getId();
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
    protected List<String> enumConstantsOf(Dbms dbms, Table table, String columnName) throws SQLException {

        final DbmsType dbmsType = dbmsTypeOf(dbmsHandlerComponent, dbms);
        final DatabaseNamingConvention naming = dbmsType.getDatabaseNamingConvention();
        
        final String sql = String.format(
            "show columns from %s where field=%s;", 
            naming.fullNameOf(table), 
            naming.quoteField(columnName)
        );
        
        try (final Connection conn = getConnection(dbms);
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
    
    private Connection getConnection(Dbms dbms) {
        return connectionPoolComponent.getConnection(dbms);
    }

    @FunctionalInterface
    protected interface TableChildMutator<T, U> {

        void mutate(T t, U u) throws SQLException;
    }

    private void extraInfo(final String formatter, final Object... args) {
        if (EXTRA_INFO) {
            System.out.println(String.format(formatter, args));
        }
    }

}
