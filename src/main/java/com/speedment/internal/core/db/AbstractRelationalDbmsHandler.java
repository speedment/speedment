/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
import com.speedment.config.Column;
import com.speedment.config.Dbms;
import com.speedment.config.ForeignKey;
import com.speedment.config.ForeignKeyColumn;
import com.speedment.config.Index;
import com.speedment.config.IndexColumn;
import com.speedment.config.PrimaryKeyColumn;
import com.speedment.config.Schema;
import com.speedment.config.Table;
import com.speedment.config.parameters.DbmsType;
import com.speedment.config.parameters.OrderType;
import com.speedment.internal.core.manager.sql.SqlStatement;
import com.speedment.internal.core.manager.sql.SqlUpdateStatement;
import com.speedment.db.AsynchronousQueryResult;
import com.speedment.db.DbmsHandler;
import com.speedment.exception.SpeedmentException;
import com.speedment.component.ConnectionPoolComponent;
import com.speedment.component.SqlTypeMapperComponent;
import com.speedment.component.TypeMapperComponent;
import com.speedment.config.mapper.TypeMapper;
import com.speedment.internal.logging.Logger;
import com.speedment.internal.logging.LoggerManager;
import com.speedment.internal.util.sql.SqlTypeInfo;

import javax.swing.text.html.Option;
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

import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import static com.speedment.internal.core.stream.OptionalUtil.unwrap;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 */
public abstract class AbstractRelationalDbmsHandler implements DbmsHandler {

    private static final Logger LOGGER = LoggerManager.getLogger(AbstractRelationalDbmsHandler.class);

    private static final String PASSWORD = "password";
    private static final String PASSWORD_PROTECTED = "********";
    private static final String USER = "user";

    private static final Predicate<Schema> SCHEMA_NO_FILTER = s -> true;

    private final Dbms dbms;
    private transient Map<String, Class<?>> typeMapping;

    private static final Boolean SHOW_METADATA = false;

    private final Speedment speedment;

    public AbstractRelationalDbmsHandler(Speedment speedment, Dbms dbms) {
        this.speedment = requireNonNull(speedment);
        this.dbms = requireNonNull(dbms);
        typeMapping = new ConcurrentHashMap<>();
    }

    @Override
    public Dbms getDbms() {
        return dbms;
    }

    // Todo: Use DataSoruce instead: http://docs.oracle.com/javase/tutorial/jdbc/basics/sqldatasources.html
    public Connection getConnection() {
        Connection conn;
//        final Properties connectionProps = new Properties();
//        dbms.getUsername().ifPresent(u -> connectionProps.put(USER, u));
//        dbms.getPassword().ifPresent(p -> connectionProps.put(PASSWORD, p));
        final String url = getUrl();
        final String user = unwrap(dbms.getUsername());
        final String password = unwrap(dbms.getPassword());
        try {
            //conn = DriverManager.getConnection(url, user, password);
            conn = speedment.get(ConnectionPoolComponent.class).getConnection(url, user, password);
        } catch (SQLException sqle) {
//            final Properties pwProtectedProperties = new Properties();
//            connectionProps.forEach((k, v) -> pwProtectedProperties.put(k, v));
//            pwProtectedProperties.put(PASSWORD, PASSWORD_PROTECTED);
            final String msg = "Unable to get connection for " + dbms + " using url \"" + url + "\", user = " + user + ", password = " + PASSWORD_PROTECTED;
            LOGGER.error(sqle, msg);
            throw new SpeedmentException(msg, sqle);
        }
        return conn;
    }

    public String getUrl() {
        final DbmsType dbmsType = getDbms().getType();
        final StringBuilder result = new StringBuilder();
        result.append("jdbc:");
        result.append(dbmsType.getJdbcConnectorName());
        result.append("://");
        getDbms().getIpAddress().ifPresent(ip -> result.append(ip));
        getDbms().getPort().ifPresent(p -> result.append(":").append(p));
        result.append("/");

        dbmsType.getDefaultConnectorParameters().ifPresent(d -> result.append("?").append(d));

        return result.toString();
    }

    protected Map<String, Class<?>> readTypeMapFromDB(Connection connection) throws SQLException {
        requireNonNull(connection);
        final Map<String, Class<?>> result = new ConcurrentHashMap<>();
        try (final ResultSet rs = connection.getMetaData().getTypeInfo()) {
            while (rs.next()) {
                final SqlTypeInfo typeInfo = SqlTypeInfo.from(rs);
                final Class<?> mappedClass = speedment.get(SqlTypeMapperComponent.class).apply(dbms, typeInfo);
                result.put(typeInfo.getSqlTypeName(), mappedClass);
            }
        }
        return result;
    }

    @Override
    public Stream<Schema> schemas() {
        return schemas(SCHEMA_NO_FILTER);
    }

    @Override
    public Stream<Schema> schemas(Predicate<Schema> filterCriteria)  {
        Optional<List<Schema>> schemas =  Optional.empty();
        try {
            try (final Connection connection = getConnection()) {
                schemas = Optional.ofNullable(schemas(connection).filter(filterCriteria).collect(toList()));
                schemas.orElse(Collections.<Schema>emptyList()).forEach(schema -> {
                    final List<Table> tables = tables(connection, schema).collect(toList());
                    tables.forEach(table -> {
                        columns(connection, schema, table).forEachOrdered(table::add);
                        primaryKeyColumns(connection, schema, table).forEachOrdered(table::add);
                        indexes(connection, schema, table).forEachOrdered(table::add);
                        foreignKeys(connection, schema, table).forEachOrdered(table::add);

                        schema.add(table);
                    });
                });
            }

        }catch (SQLException sqle){
            LOGGER.error(sqle, "Unable to read from " + dbms.toString());
        }

        return schemas.orElse(Collections.<Schema>emptyList()).stream();

    }

    @Override
    public Stream<Schema> schemasUnpopulated() {
        try {
            try (Connection connection = getConnection()) {
                return schemas(connection);
            }
        } catch (SQLException sqle) {
            LOGGER.error(sqle, "Unable to read from " + dbms.toString());
            return Stream.empty();
        }
    }

    protected Stream<Schema> schemas(final Connection connection) {
        requireNonNull(connection);
        LOGGER.info("Reading metadata from " + dbms.toString());
        final List<Schema> schemas = new ArrayList<>();
        try {
            typeMapping = readTypeMapFromDB(connection);
            try (final ResultSet rs = connection.getMetaData().getSchemas(null, null)) {
                while (rs.next()) {
                    final String schemaName = rs.getString("TABLE_SCHEMA");
                    String catalogName = "";
                    try {
                        // This column is not there for Oracle so handle it
                        // gracefully....
                        catalogName = rs.getString("TABLE_CATALOG");
                    } catch (SQLException sqlException) {
                        LOGGER.info("TABLE_CATALOG not in result set.");
                    }
                    if (!dbms.getType().getSchemaExcludeSet().contains(schemaName)) {
                        final Schema schema = Schema.newSchema();
                        schema.setName(schemaName);
                        schema.setSchemaName(schemaName);
                        schema.setCatalogName(catalogName);
                        schemas.add(schema);
                    }
                }
            }

            try (final ResultSet catalogResultSet = connection.getMetaData().getCatalogs()) {
                while (catalogResultSet.next()) {
                    final String schemaName = catalogResultSet.getString(1);
                    if (!dbms.getType().getSchemaExcludeSet().contains(schemaName)) {
                        final Schema schema = Schema.newSchema();
                        schema.setName(schemaName);
                        schemas.add(schema);
                    }
                }
            }

        } catch (SQLException sqle) {
            throw new SpeedmentException(sqle);
        }
        return schemas.stream();
    }

    protected Stream<Table> tables(final Connection connection, Schema schema) {
        requireNonNull(connection);
        requireNonNull(schema);
        final List<Table> tables = new ArrayList<>();
        LOGGER.info("Parsing " + schema.toString());
        try {
            try (final ResultSet rsTable = connection.getMetaData().getTables(jdbcCatalogLookupName(schema), jdbcSchemaLookupName(schema), null, new String[]{"TABLE"})) {

                final ResultSetMetaData rsmd = rsTable.getMetaData();
                int numberOfColumns = rsmd.getColumnCount();

                if (SHOW_METADATA) {
                    for (int x = 1; x <= numberOfColumns; x++) {
                        LOGGER.debug(rsmd.getColumnName(x) + ", " + rsmd.getColumnClassName(x) + ", " + rsmd.getColumnType(x));
                    }
                }

                // Monitoring the progress of Subtask 1. creating tables
                while (rsTable.next()) {
                    if (SHOW_METADATA) {
                        for (int x = 1; x <= numberOfColumns; x++) {
                            LOGGER.debug(rsmd.getColumnName(x) + ":'" + rsTable.getObject(x) + "'");
                        }
                    }
                    final Table table = Table.newTable();
                    final String tableName = rsTable.getString("TABLE_NAME");
                    table.setName(tableName);
                    tables.add(table);
                }
            }
        } catch (SQLException sqle) {
            throw new SpeedmentException(sqle);
        }
        return tables.stream();
    }

    protected Stream<Column> columns(final Connection connection, Schema schema, Table table) {
        requireNonNull(connection);
        requireNonNull(schema);
        requireNonNull(table);
        final SqlSupplier<ResultSet> supplier = ()
            -> connection.getMetaData().getColumns(jdbcCatalogLookupName(schema), jdbcSchemaLookupName(schema), table.getName(), null);

        final SqlFunction<ResultSet, Column> mapper = rs -> {
            final Column column = Column.newColumn();
            column.setName(rs.getString("COLUMN_NAME"));
            column.setOrdinalPosition(rs.getInt("ORDINAL_POSITION"));

            boolean nullable = rs.getInt("NULLABLE") != DatabaseMetaData.columnNoNulls;
            column.setNullable(nullable);

            final String classMappingString = rs.getString("TYPE_NAME");
            final Class<?> mapping = typeMapping.get(classMappingString);
            if (mapping != null) {
                
                final TypeMapper<?, ?> typeMapper = speedment.get(TypeMapperComponent.class).stream()
                    .filter(tm -> Objects.equals(mapping, tm.getDatabaseType()))
                    .filter(tm -> Objects.equals(mapping, tm.getJavaType()))
                    .findFirst().orElseThrow(() -> new SpeedmentException(
                        "Found no identity type mapper for mapping '" + mapping.getName() + "'."
                    ));
                
                column.setTypeMapper(typeMapper);
                
            } else {
                LOGGER.info("Unable to determine mapping for table " + table.getName() + ", column " + column.getName());
            }

            try {
                column.setAutoincrement(rs.getBoolean("IS_AUTOINCREMENT"));
            } catch (final SQLException sqle) {
                LOGGER.info("Unable to determine IS_AUTOINCREMENT for table " + table.getName() + ", column " + column.getName());
            }
            return column;
        };
        return tableChilds(supplier, mapper);
    }

    protected Stream<PrimaryKeyColumn> primaryKeyColumns(final Connection connection, Schema schema, Table table) {
        requireNonNull(connection);
        requireNonNull(schema);
        requireNonNull(table);
        final SqlSupplier<ResultSet> supplier = ()
            -> connection.getMetaData().getPrimaryKeys(jdbcCatalogLookupName(schema), jdbcSchemaLookupName(schema), table.getName());

        final SqlFunction<ResultSet, PrimaryKeyColumn> mapper = rs -> {
            final PrimaryKeyColumn primaryKeyColumn = PrimaryKeyColumn.newPrimaryKeyColumn();
            primaryKeyColumn.setName(rs.getString("COLUMN_NAME"));
            primaryKeyColumn.setOrdinalPosition(rs.getInt("KEY_SEQ"));
            return primaryKeyColumn;
        };
        return tableChilds(supplier, mapper);
    }

    protected Stream<Index> indexes(final Connection connection, Schema schema, Table table) {
        requireNonNull(connection);
        requireNonNull(schema);
        requireNonNull(table);
        final Map<String, Index> indexes = new HashMap<>(); // Use map instead of Set because Index equality is difficult...
        final SqlSupplier<ResultSet> supplier = ()
            -> connection.getMetaData().getIndexInfo(jdbcCatalogLookupName(schema), jdbcSchemaLookupName(schema), table.getName(), false, false);

        final SqlFunction<ResultSet, Index> mapper = rs -> {
            final String indexName = rs.getString("INDEX_NAME");
            final boolean notUnique = rs.getBoolean("NON_UNIQUE");
            final boolean exists = indexes.containsKey(indexName);
            final Index index = indexes.computeIfAbsent(indexName, n -> {
                final Index newIndex = Index.newIndex();
                newIndex.setName(n);
                newIndex.setUnique(!notUnique); // !
                return newIndex;
            });

            final IndexColumn indexColumn = IndexColumn.newIndexColumn();
            indexColumn.setName(rs.getString("COLUMN_NAME"));
            indexColumn.setOrdinalPosition(rs.getInt("ORDINAL_POSITION"));
            final String ascOrDesc = rs.getString("ASC_OR_DESC");

            if ("A".equalsIgnoreCase(ascOrDesc)) {
                indexColumn.setOrderType(OrderType.ASC);
            } else if ("D".equalsIgnoreCase(ascOrDesc)) {
                indexColumn.setOrderType(OrderType.DESC);
            } else {
                indexColumn.setOrderType(OrderType.NONE);
            }
            index.add(indexColumn);

            return exists ? null : index;
        };
        return tableChilds(supplier, mapper);
    }

    protected Stream<ForeignKey> foreignKeys(final Connection connection, Schema schema, Table table) {
        requireNonNull(connection);
        requireNonNull(schema);
        requireNonNull(table);
        final Map<String, ForeignKey> foreignKeys = new HashMap<>(); // Use map instead of Set because ForeignKey equality is difficult...
        final SqlSupplier<ResultSet> supplier = () -> {
            return connection.getMetaData().getImportedKeys(jdbcCatalogLookupName(schema), jdbcSchemaLookupName(schema), table.getName());
        };
        final SqlFunction<ResultSet, ForeignKey> mapper = rs -> {
            
            
            final Map<String, ForeignKey> foo = foreignKeys;
            
            
            final String foreignKeyName = rs.getString("FK_NAME");
            final boolean exists = foreignKeys.containsKey(foreignKeyName);
            final ForeignKey foreignKey = foreignKeys.computeIfAbsent(foreignKeyName, n -> {
                final ForeignKey newforeigKey = ForeignKey.newForeignKey();
                newforeigKey.setName(n);
                return newforeigKey;
            });

            final ForeignKeyColumn foreignKeyColumn = ForeignKeyColumn.newForeignKeyColumn();
            foreignKeyColumn.setName(rs.getString("FKCOLUMN_NAME"));
            foreignKeyColumn.setOrdinalPosition(rs.getInt("KEY_SEQ"));
            foreignKeyColumn.setForeignTableName(rs.getString("PKTABLE_NAME"));
            foreignKeyColumn.setForeignColumnName(rs.getString("PKCOLUMN_NAME"));
            foreignKey.add(foreignKeyColumn);

            return exists ? null : foreignKey;
        };
        return tableChilds(supplier, mapper);
    }

    protected <T> Stream<T> tableChilds(SqlSupplier<ResultSet> resultSetSupplier, SqlFunction<ResultSet, T> resultSetMapper) {
        requireNonNull(resultSetSupplier);
        requireNonNull(resultSetMapper);
        final List<T> childs = new ArrayList<>();
        try {
            try (final ResultSet rsColumn = resultSetSupplier.get()) {

                final ResultSetMetaData rsmd = rsColumn.getMetaData();
                int numberOfColumns = rsmd.getColumnCount();
                if (SHOW_METADATA) {
                    for (int x = 1; x <= numberOfColumns; x++) {
                        LOGGER.debug(rsmd.getColumnName(x) + ", " + rsmd.getColumnClassName(x) + ", " + rsmd.getColumnType(x));
                    }
                }

                while (rsColumn.next()) {
                    if (SHOW_METADATA) {
                        for (int x = 1; x <= numberOfColumns; x++) {
                            LOGGER.debug(rsmd.getColumnName(x) + ":'" + rsColumn.getObject(x) + "'");
                        }
                    }
                    T child = resultSetMapper.apply(rsColumn);
                    // Null can be used to signal that the child has alredy been added (for ForeignKey and Index)
                    if (child != null) {
                        childs.add(child);
                    }
                }
            }
        } catch (SQLException sqle) {
            throw new SpeedmentException(sqle);
        }
        return childs.stream();
    }

    protected String jdbcSchemaLookupName(Schema schema) {
        return null;
    }

    protected String jdbcCatalogLookupName(Schema schema) {
        return schema.getSchemaName().orElse(null);
    }

    @Override
    public <T> Stream<T> executeQuery(final String sql, final List<?> values, final SqlFunction<ResultSet, T> rsMapper) {
        requireNonNull(sql);
        requireNonNull(values);
        requireNonNull(rsMapper);
        try (final Connection connection = getConnection(); final PreparedStatement ps = connection.prepareStatement(sql)) {
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
        final String sql,
        final List<?> values,
        final Function<ResultSet, T> rsMapper
    ) {
        return new AsynchronousQueryResultImpl<>(
            Objects.requireNonNull(sql),
            Objects.requireNonNull(values),
            Objects.requireNonNull(rsMapper),
            () -> getConnection());
    }

    @Override
    public void executeUpdate(
        final String sql,
        final List<?> values,
        final Consumer<List<Long>> generatedKeysConsumer
    ) throws SQLException {
        final List<SqlUpdateStatement> sqlStatementList = new ArrayList<>();
        final SqlUpdateStatement sqlUpdateStatement = new SqlUpdateStatement(sql, values, generatedKeysConsumer);
        sqlStatementList.add(sqlUpdateStatement);
        executeUpdate(sqlStatementList);
    }

    private void executeUpdate(final List<SqlUpdateStatement> sqlStatementList) throws SQLException {
        requireNonNull(sqlStatementList);
        int retryCount = 5;
        boolean transactionCompleted = false;

        do {
            SqlStatement lastSqlStatement = null;
            Connection conn = null;
            try {
                conn = getConnection();
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
                conn = null;
                transactionCompleted = true;
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

    <T> Supplier<T> wrapSupplierInSpeedmentException(final SqlSupplier<T> innerSupplier) {
        requireNonNull(innerSupplier);
        return () -> {
            try {
                return innerSupplier.get();
            } catch (SQLException sqle) {
                throw new SpeedmentException(sqle);
            }
        };

    }

}
