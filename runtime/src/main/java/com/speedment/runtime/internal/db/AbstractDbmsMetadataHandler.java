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

import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.injector.annotation.RequiresInjectable;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.runtime.component.DbmsHandlerComponent;
import com.speedment.runtime.component.ProjectComponent;
import com.speedment.runtime.component.TypeMapperComponent;
import com.speedment.runtime.component.connectionpool.ConnectionPoolComponent;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Document;
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
import com.speedment.runtime.db.DatabaseNamingConvention;
import com.speedment.runtime.db.DbmsMetadataHandler;
import com.speedment.runtime.db.SqlPredicate;
import com.speedment.runtime.db.SqlSupplier;
import com.speedment.runtime.db.metadata.ColumnMetaData;
import com.speedment.runtime.db.metadata.TypeInfoMetaData;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.internal.config.ProjectImpl;
import static com.speedment.runtime.internal.db.AbstractDbmsOperationHandler.SHOW_METADATA;
import static com.speedment.runtime.internal.util.CaseInsensitiveMaps.newCaseInsensitiveMap;
import static com.speedment.runtime.internal.util.document.DocumentDbUtil.dbmsTypeOf;
import com.speedment.runtime.internal.util.document.DocumentUtil;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import com.speedment.runtime.util.ProgressMeasure;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 *
 * @author  Emil Forslund
 * @author  Per Minborg
 * @since   2.4.0
 */
@RequiresInjectable(JavaTypeMap.class)
public abstract class AbstractDbmsMetadataHandler implements DbmsMetadataHandler {
    
    private final static Logger LOGGER = LoggerManager.getLogger(AbstractDbmsMetadataHandler.class);
    private final static Class<?> DEFAULT_MAPPING = Object.class;
    
    private @Inject ConnectionPoolComponent connectionPoolComponent;
    private @Inject DbmsHandlerComponent dbmsHandlerComponent;
    private @Inject TypeMapperComponent typeMapperComponent;
    private @Inject ProjectComponent projectComponent;
    private @Inject JavaTypeMap javaTypeMap;
    
    protected AbstractDbmsMetadataHandler() {}
    
    @Override
    public CompletableFuture<Project> readSchemaMetadata(
        Dbms dbms,
        ProgressMeasure progress,
        Predicate<String> filterCriteria) {

        requireNonNulls(filterCriteria, progress);

        // Create a deep copy of the project document.
        final Project projectCopy = DocumentUtil.deepCopy(
            projectComponent.getProject(), ProjectImpl::new
        );

        // Locate the dbms in the copy.
        final Dbms dbmsCopy = projectCopy.dbmses()
            .filter(d -> d.getName().equals(dbms.getName()))
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

        final DbmsType dbmsType = dbmsTypeOf(dbmsHandlerComponent, dbms);
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
            try (final Connection connection = connectionPoolComponent.getConnection(dbms)) {
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
            try (final Connection connection = connectionPoolComponent.getConnection(dbms)) {
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

        try (final Connection connection = connectionPoolComponent.getConnection(dbms)) {
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
                try (final Connection connection = connectionPoolComponent.getConnection(dbms)) {
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

        final AbstractDbmsOperationHandler.TableChildMutator<Column, ResultSet> mutator = (column, rs) -> {

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

            final Class<?> lookupJdbcClass = javaTypeMap.findJdbcType(sqlTypeMapping, md);

            final Class<?> selectedJdbcClass;
            if (lookupJdbcClass != null) {
                selectedJdbcClass = lookupJdbcClass;
            } else {
                // Fall-back to DEFAULT_MAPPING
                selectedJdbcClass = DEFAULT_MAPPING;
                LOGGER.warn("Unable to determine mapping for table " + table.getName() + ", column " + column.getName() + ". Fall-back to JDBC-type " + selectedJdbcClass.getSimpleName());
            }

            final TypeMapper<?, ?> typeMapper = typeMapperComponent.stream()
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

        final AbstractDbmsOperationHandler.TableChildMutator<PrimaryKeyColumn, ResultSet> mutator = (primaryKeyColumn, rs) -> {
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

        final AbstractDbmsOperationHandler.TableChildMutator<Index, ResultSet> mutator = (index, rs) -> {
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

        final AbstractDbmsOperationHandler.TableChildMutator<ForeignKey, ResultSet> mutator = (foreignKey, rs) -> {

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
        AbstractDbmsOperationHandler.TableChildMutator<T, ResultSet> resultSetMutator,
        ProgressMeasure progressListener
    ) {
        tableChilds(childSupplier, resultSetSupplier, resultSetMutator, rs -> true, progressListener);
    }

    protected <T> void tableChilds(
        Supplier<T> childSupplier,
        SqlSupplier<ResultSet> resultSetSupplier,
        AbstractDbmsOperationHandler.TableChildMutator<T, ResultSet> resultSetMutator,
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
    
    protected Map<String, Class<?>> readTypeMapFromDB(Dbms dbms) throws SQLException {
        requireNonNull(dbms);

        final List<TypeInfoMetaData> typeInfoMetaDataList = new ArrayList<>();
        try (final Connection connection = connectionPoolComponent.getConnection(dbms)) {
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

    private <P extends HasName, D extends Document & HasName & HasMainInterface & HasParent<P>> String actionName(D doc) {
        return new StringBuilder()
            .append(doc.mainInterface().getSimpleName())
            .append(" ")
            .append(doc.getName())
            .append(" in ")
            .append(doc.getParentOrThrow().getName())
            .toString();
    }
}
