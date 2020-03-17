/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.core.db;

import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.core.stream.parallel.ParallelStrategy;
import com.speedment.runtime.field.Field;

import java.sql.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * A DbmsOperationHandler provides the interface between Speedment and an
 * underlying {@link Dbms} for when running queries.
 *
 * @author Per Minborg
 * @author Emil Forslund
 * @since 2.0.0
 */
public interface DbmsOperationHandler {

    /**
     * Eagerly executes a SQL query and subsequently maps each row in the
     * ResultSet using a provided mapper and return a Stream of the mapped
     * objects. The ResultSet is eagerly consumed so that all elements in the
     * ResultSet are read before the Stream produces any objects. If no objects
     * are present or if an SQLException is thrown internally, an {@code empty}
     * stream is returned.
     *
     * @param <T> the type of the objects in the stream to return
     * @param dbms the dbms to send it to
     * @param sql the SQL command to execute
     * @param rsMapper the mapper to use when iterating over the ResultSet
     * @return a stream of the mapped objects
     */
    default <T> Stream<T> executeQuery(
        Dbms dbms,
        String sql,
        SqlFunction<ResultSet, T> rsMapper) {

        return executeQuery(dbms, sql, Collections.emptyList(), rsMapper);
    }

    /**
     * Eagerly executes a SQL query and subsequently maps each row in the
     * {@link ResultSet} using a provided mapper and return a stream of the
     * mapped objects. The {@code ResultSet} is eagerly consumed. If no objects
     * are present or if an {@link SQLException} is thrown internally, an
     * {@code empty} stream is returned.
     *
     * @param <T> the type of the objects in the stream to return
     * @param dbms the dbms to send it to
     * @param sql the non-null SQL command to execute
     * @param values non-null values to use for "?" parameters in the sql
     * command
     * @param rsMapper the non-null mapper to use when iterating over the
     * {@link ResultSet}
     * @return a stream of the mapped objects
     */
    <T> Stream<T> executeQuery(
        Dbms dbms,
        String sql,
        List<?> values,
        SqlFunction<ResultSet, T> rsMapper
    );

    /**
     * Lazily Executes a SQL query and subsequently maps each row in the
     * {@link ResultSet} using a provided mapper and return a stream of the
     * mapped objects. The {@code ResultSet} is lazily consumed so that the
     * stream will consume the {@code ResultSet} as the objects are consumed. If
     * no objects are present, an {@code empty} stream is returned.
     *
     * @param <T> the type of the objects in the Stream to return
     * @param dbms the dbms to send it to
     * @param sql the non-null SQL command to execute
     * @param values non-null List of objects to use for "?" parameters in the
     * SQL command
     * @param rsMapper the non-null mapper to use when iterating over the
     * {@link ResultSet}
     * @param parallelStrategy strategy to use if constructing a parallel stream
     * @return a stream of the mapped objects
     */
    <T> AsynchronousQueryResult<T> executeQueryAsync(
        Dbms dbms,
        String sql,
        List<?> values,
        SqlFunction<ResultSet, T> rsMapper,
        ParallelStrategy parallelStrategy
    );

    /**
     * Executes an SQL update command. Generated key(s) following an insert
     * command (if any) will be feed to the provided Consumer.
     *
     * @param <ENTITY> the type of the entity from which the fields come
     *
     * @param dbms the dbms to send it to
     * @param sql the non-null SQL command to execute
     * @param values a non-null list
     * @param generatedKeyFields list of the generated fields
     * @param generatedKeyConsumer non-null List of objects to use for "?"
     * parameters in the SQL command
     * @throws SQLException if an error occurs
     */
    <ENTITY> void executeInsert(
        Dbms dbms,
        String sql,
        List<?> values,
        Collection<Field<ENTITY>> generatedKeyFields,
        Consumer<List<Long>> generatedKeyConsumer
    ) throws SQLException;

    /**
     * Executes an SQL update command. Generated key(s) following an insert
     * command (if any) will be feed to the provided Consumer.
     *
     * @param dbms the dbms to send it to
     * @param sql the non-null SQL command to execute
     * @param values a non-null list
     * @throws SQLException if an error occurs
     */
    void executeUpdate(Dbms dbms, String sql, List<?> values) throws SQLException;

    /**
     * Executes an SQL delete command. Generated key(s) following an insert
     * command (if any) will be feed to the provided Consumer.
     *
     * @param dbms the dbms to send it to
     * @param sql the non-null SQL command to execute
     * @param values a non-null list
     * @throws SQLException if an error occurs
     */
    void executeDelete(Dbms dbms, String sql, List<?> values) throws SQLException;

    /**
     * Constructs an object that implements the <code>Clob</code> interface. The
     * object returned initially contains no data. The
     * <code>setAsciiStream</code>, <code>setCharacterStream</code> and
     * <code>setString</code> methods of the <code>Clob</code> interface may be
     * used to add data to the <code>Clob</code>.
     *
     * @param dbms the database to create it for
     * @return An object that implements the <code>Clob</code> interface
     * @throws SQLException if an object that implements the <code>Clob</code>
     * interface can not be constructed, this method is called on a closed
     * connection or a database access error occurs.
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not
     * support this data type
     *
     * @since 2.3.2
     */
    Clob createClob(Dbms dbms) throws SQLException;

    /**
     * Constructs an object that implements the <code>Blob</code> interface. The
     * object returned initially contains no data. The
     * <code>setBinaryStream</code> and <code>setBytes</code> methods of the
     * <code>Blob</code> interface may be used to add data to the
     * <code>Blob</code>.
     *
     * @param dbms the database to create it for
     * @return An object that implements the <code>Blob</code> interface
     * @throws SQLException if an object that implements the <code>Blob</code>
     * interface can not be constructed, this method is called on a closed
     * connection or a database access error occurs.
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not
     * support this data type
     *
     * @since 2.3.2
     */
    Blob createBlob(Dbms dbms) throws SQLException;

    /**
     * Constructs an object that implements the <code>NClob</code> interface.
     * The object returned initially contains no data. The
     * <code>setAsciiStream</code>, <code>setCharacterStream</code> and
     * <code>setString</code> methods of the <code>NClob</code> interface may be
     * used to add data to the <code>NClob</code>.
     *
     * @param dbms the database to create it for
     * @return An object that implements the <code>NClob</code> interface
     * @throws SQLException if an object that implements the <code>NClob</code>
     * interface can not be constructed, this method is called on a closed
     * connection or a database access error occurs.
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not
     * support this data type
     *
     * @since 2.3.2
     */
    NClob createNClob(Dbms dbms) throws SQLException;

    /**
     * Constructs an object that implements the <code>SQLXML</code> interface.
     * The object returned initially contains no data. The
     * <code>createXmlStreamWriter</code> object and <code>setString</code>
     * method of the <code>SQLXML</code> interface may be used to add data to
     * the <code>SQLXML</code> object.
     *
     * @param dbms the database to create it for
     * @return An object that implements the <code>SQLXML</code> interface
     * @throws SQLException if an object that implements the <code>SQLXML</code>
     * interface can not be constructed, this method is called on a closed
     * connection or a database access error occurs.
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not
     * support this data type
     *
     * @since 2.3.2
     */
    SQLXML createSQLXML(Dbms dbms) throws SQLException;

    /**
     * Factory method for creating Array objects.
     * <p>
     * <b>Note: </b>When <code>createArrayOf</code> is used to create an array
     * object that maps to a primitive data type, then it is
     * implementation-defined whether the <code>Array</code> object is an array
     * of that primitive data type or an array of <code>Object</code>.
     * <p>
     * <b>Note: </b>The JDBC driver is responsible for mapping the elements
     * <code>Object</code> array to the default JDBC SQL type defined in
     * java.sql.Types for the given class of <code>Object</code>. The default
     * mapping is specified in Appendix B of the JDBC specification. If the
     * resulting JDBC type is not the appropriate type for the given typeName
     * then it is implementation defined whether an <code>SQLException</code> is
     * thrown or the driver supports the resulting conversion.
     *
     * @param dbms the database to create it for
     * @param typeName the SQL name of the type the elements of the array map
     * to. The typeName is a database-specific name which may be the name of a
     * built-in type, a user-defined type or a standard SQL type supported by
     * this database. This is the value returned by
     * <code>Array.getBaseTypeName</code>
     * @param elements the elements that populate the returned object
     * @return an Array object whose elements map to the specified SQL type
     * @throws SQLException if a database error occurs, the JDBC type is not
     * appropriate for the typeName and the conversion is not supported, the
     * typeName is null or this method is called on a closed connection
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not
     * support this data type
     *
     * @since 2.3.2
     */
    Array createArray(Dbms dbms, String typeName, Object[] elements) throws SQLException;

    /**
     * Factory method for creating Struct objects.
     *
     * @param dbms the database to create it for
     * @param typeName the SQL type name of the SQL structured type that this
     * <code>Struct</code> object maps to. The typeName is the name of a
     * user-defined type that has been defined for this database. It is the
     * value returned by <code>Struct.getSQLTypeName</code>.
     * @param attributes the attributes that populate the returned object
     * @return a Struct object that maps to the given SQL type and is populated
     * with the given attributes
     * @throws SQLException if a database error occurs, the typeName is null or
     * this method is called on a closed connection
     * @throws SQLFeatureNotSupportedException if the JDBC driver does not
     * support this data type
     *
     * @since 2.3.2
     */
    Struct createStruct(Dbms dbms, String typeName, Object[] attributes) throws SQLException;

    /**
     * Configures a select statement for optimum read performance. This is
     * necessary for some database types such as MySQL or else large ResutlSets
     * may consume the entire heap.
     *
     * @param statement to configure
     * @throws java.sql.SQLException if the configuration fails
     */
    void configureSelect(PreparedStatement statement) throws SQLException;

    /**
     * Configures a ResultSet for optimum read performance.
     *
     * @param resultSet to configure
     * @throws java.sql.SQLException if the configuration fails
     */
    void configureSelect(ResultSet resultSet) throws SQLException;

    /**
     * Closes this DbmsOperationHandler so that no operations can
     * be invoked.
     */
    void close();

}