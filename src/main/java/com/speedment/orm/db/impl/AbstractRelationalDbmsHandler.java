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
package com.speedment.orm.db.impl;

import com.speedment.orm.config.model.Column;
import com.speedment.orm.config.model.Dbms;
import com.speedment.orm.config.model.Schema;
import com.speedment.orm.config.model.Table;
import com.speedment.orm.config.model.parameters.DbmsType;
import com.speedment.orm.db.AsynchronousQueryResult;
import com.speedment.orm.db.DbmsHandler;
import com.speedment.orm.platform.Platform;
import com.speedment.orm.platform.component.SqlTypeMapperComponent;
import com.speedment.util.java.sql.TypeInfo;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author pemi
 */
public abstract class AbstractRelationalDbmsHandler implements DbmsHandler {

    private static final Logger LOGGER = LogManager.getLogger(AbstractRelationalDbmsHandler.class);

    private final Dbms dbms;
    private transient Map<String, Class<?>> typeMapping;

    private static final Boolean SHOW_METADATA = false;

    public AbstractRelationalDbmsHandler(Dbms dbms) {
        this.dbms = dbms;
        typeMapping = new ConcurrentHashMap<>();
    }

    @Override
    public Dbms getDbms() {
        return dbms;
    }

    // Todo: Use DataSoruce instead: http://docs.oracle.com/javase/tutorial/jdbc/basics/sqldatasources.html
    public Connection getConnection() {
        Connection conn;
        Properties connectionProps = new Properties();
        dbms.getUsername().ifPresent(u -> connectionProps.put("user", u));
        dbms.getPassword().ifPresent(p -> connectionProps.put("password", p));
        try {
            conn = DriverManager.getConnection(getUrl(), connectionProps);
        } catch (SQLException sqle) {
            LOGGER.error("Unable to get connection for " + dbms, sqle);
            throw new RuntimeException(sqle);
        }
        return conn;
    }

    public void init() {
//        try {
////            databaseMetaData = getConnection().getMetaData();
//        } catch (SQLException sqle) {
//            LOGGER.fatal("Unable to load database metadata for " + dbms.getName(), sqle);
//        }
    }

    public String getUrl() {
        final DbmsType dbmsType = getDbms().getType();
        StringBuilder result = new StringBuilder();
        result.append("jdbc:");
        result.append(dbmsType.getJdbcConnectorName());
        result.append("://");
        getDbms().getIpAddress().ifPresent(ip -> result.append(ip));
        getDbms().getPort().ifPresent(p -> result.append(":").append(p));
        result.append("/");

//        if (getDefaultSchemaName() != null) {
//            result.append(getDefaultSchemaName());
//        }
        dbmsType.getDefaultConnectorParameters().ifPresent(d -> result.append("?").append(d));

        return result.toString();
    }

    protected Map<String, Class<?>> readTypeMapFromDB(Connection connection) throws SQLException {
        final Map<String, Class<?>> result = new ConcurrentHashMap<>();
        try (final ResultSet rs = connection.getMetaData().getTypeInfo()) {
            while (rs.next()) {
                final TypeInfo typeInfo = TypeInfo.from(rs);
                final Class<?> mappedClass = Platform.get().get(SqlTypeMapperComponent.class).map(dbms, typeInfo);
                result.put(typeInfo.getSqlTypeName(), mappedClass);
            }
        }
        return result;
    }

    @Override
    public Stream<Schema> schemasPopulated() {
        try {
            try (final Connection connection = getConnection()) {
                final List<Schema> schemas = schemas(connection).collect(toList());
                schemas.forEach(schema -> {
                    final List<Table> tables = tables(connection, schema).collect(toList());
                    tables.forEach(table -> {
                        final List<Column> columns = columns(connection, schema, table).collect(toList());
                        columns.forEach(column -> {
                            table.add(column);
                        });
                        schema.add(table);
                    });
                });
                return schemas.stream();
            }
        } catch (SQLException sqle) {
            LOGGER.error("Unable to read from " + dbms.toString(), sqle);
            return Stream.empty();
        }
    }

    @Override
    public Stream<Schema> schemas() {
        try {
            try (Connection connection = getConnection()) {
                return schemas(connection);
            }
        } catch (SQLException sqle) {
            LOGGER.error("Unable to read from " + dbms.toString(), sqle);
            return Stream.empty();
        }
    }

    protected Stream<Schema> schemas(final Connection connection) {
        LOGGER.info("Reading metadata from " + dbms.toString());
        final List<Schema> schemas = new ArrayList<>();
        try {
            typeMapping = readTypeMapFromDB(connection);
            try (final ResultSet rs = connection.getMetaData().getSchemas(null, null)) {
                while (rs.next()) {
                    final String schemaName = rs.getString("TABLE_SCHEM");
                    String catalogName = "";
                    try {
                        // This column is not there for Oracle so handle it
                        // gracefully....
                        catalogName = rs.getString("TABLE_CATALOG");
                    } catch (SQLException sqlException) {
                        LOGGER.info("TABLE_CATALOG not in result set.");
                    }
                    if (!dbms.getType().getSchemaExcludSet().contains(schemaName)) {
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
                    if (!dbms.getType().getSchemaExcludSet().contains(schemaName)) {
                        final Schema schema = Schema.newSchema();
                        schema.setName(schemaName);
                        schemas.add(schema);
                    }
                }
            }

//            for (final Schema schema : schemas) {
//                tables(connection, schema).forEach(schema::add);
//            }
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
        return schemas.stream();
    }

    protected Stream<Table> tables(final Connection connection, Schema schema) {
        final List<Table> tables = new ArrayList<>();
        LOGGER.info("Parsing " + schema.toString());
        try {
            try (final ResultSet rsTable = connection.getMetaData().getTables(jdbcCatalogLookupName(schema), jdbcSchemaLookupName(schema), null, new String[]{"TABLE"})) {
//            try (final ResultSet rsTable = connection.getMetaData().getTables(schema.getCatalogName().orElse(null), schema.getSchemaName().orElse(null), null, new String[]{"TABLE"})) {

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
            throw new RuntimeException(sqle);
        }
        return tables.stream();
    }

    protected Stream<Column> columns(final Connection connection, Schema schema, Table table) {
        final List<Column> columns = new ArrayList<>();
        LOGGER.info("Parsing " + table.toString());
        try {
            try (final ResultSet rsColumn = connection.getMetaData().getColumns(jdbcCatalogLookupName(schema), jdbcSchemaLookupName(schema), table.getName(), null)) {

                ResultSetMetaData rsmd = null;
                int numberOfColumns = -1;
                if (SHOW_METADATA) {
                    rsmd = rsColumn.getMetaData();
                    numberOfColumns = rsmd.getColumnCount();

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

                    final String columnName = rsColumn.getString("COLUMN_NAME");

                    int javaSqlType = rsColumn.getInt("DATA_TYPE");

                    String typeName = rsColumn.getString("TYPE_NAME");
                    int columnSize = rsColumn.getInt("COLUMN_SIZE");
                    int decimalDigits = rsColumn.getInt("DECIMAL_DIGITS");
                    boolean nullable = (rsColumn.getInt("NULLABLE") != DatabaseMetaData.columnNoNulls);

                    String remarks;
//                    if (getDbms().getDbmsType() == StandardDbmsType.ORACLE) {
//                        remarks = commentMap.get(columnName);  // Get directly from the map
//                    } else {
                    remarks = rsColumn.getString("REMARKS");
//                    }

                    String columnDef = null; //rsColumn.getString("COLUMN_DEF");   // Default value
                    int ordinalPosition = rsColumn.getInt("ORDINAL_POSITION");

                    boolean isAutoincrement = false;
//                    if (getDbms() instanceof MySQL) {  // Only supported for MySQL (and MariaDB)
                    try {
                        isAutoincrement = rsColumn.getBoolean("IS_AUTOINCREMENT");
                    } catch (final SQLException sqlException) {
                        LOGGER.info("Unable to determine IS_AUTOINCREMENT for table " + table.getName() + ", column " + columnName);
                    }

                    final Column column = Column.newColumn();
                    column.setName(columnName);
                    column.setOrdinalPosition(ordinalPosition);
                    //final TypeInfo sqlType = TypeInfo.from(rsColumn);
                    //column.setMapping(Platform.get().get(SqlTypeMapperComponent.class).map(dbms, null));
                    column.setMapping(String.class);
                    columns.add(column);

//                    final Column c = new Column(this, columnName,
//                            ordinalPosition,
//                            columnSize,
//                            decimalDigits,
//                            typeName,
//                            nullable,
//                            columnDef,
//                            isAutoincrement,
//                            0, // primary: Will be resolved during rebuuild...
//                            remarks,
//                            javaSqlType);
                }

            }

        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
        return columns.stream();
    }

    protected String jdbcSchemaLookupName(Schema schema) {
        return null;
    }

    protected String jdbcCatalogLookupName(Schema schema) {
        return schema.getSchemaName().orElse(null);
    }

    @Override
    public <T> Stream<T> executeQuery(final String sql, final Function<ResultSet, T> rsMapper) {
        try (
                final Connection connection = getConnection();
                final Statement statement = connection.createStatement();
                final ResultSet rs = statement.executeQuery(sql);) {
            final Stream.Builder<T> streamBuilder = Stream.builder();
            while (rs.next()) {
                streamBuilder.add(rsMapper.apply(rs));
            }
            return streamBuilder.build();
        } catch (SQLException sqle) {
            LOGGER.error("Error executing " + sql, sqle);
            throw new RuntimeException(sqle);
        }
    }

    @Override
    public <T> AsynchronousQueryResult<T> executeQueryAsync(final String sql, final Function<ResultSet, T> rsMapper) {
        Objects.requireNonNull(sql);
        Objects.requireNonNull(rsMapper);
        return new AsynchronousQueryResultImpl<>(sql, rsMapper, () -> getConnection());
//        try {
//            final Connection connection = getConnection();
//            final Statement statement = connection.createStatement();
//            final ResultSet rs = statement.executeQuery(sql);
//            return StreamUtil.asStream(rs, rsMapper);
//            // TODO: Close the connection, statement and resultset
//        } catch (SQLException sqle) {
//            LOGGER.error("Error executing " + sql, sqle);
//            throw new RuntimeException(sqle);
//        }
    }

}
