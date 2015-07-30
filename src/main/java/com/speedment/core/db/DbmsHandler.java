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
package com.speedment.core.db;

import com.speedment.core.config.model.Dbms;
import com.speedment.core.config.model.Schema;
import com.speedment.core.db.impl.SqlFunction;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * A DbmsHandler provides the interface between Speedment an an underlying Dbms.
 *
 * @author pemi
 * @since 2.0
 */
public interface DbmsHandler {

    /**
     * Returns the {@code Dbms} {@code ConfigEntity} that is used by this
     * {@code DbmsHandler}.
     *
     * @return the {@code Dbms} {@code ConfigEntity} that is used by this
     * {@code DbmsHandler}
     */
    Dbms getDbms();

    /**
     * Returns a Stream of un-populated Schemas that are available in this
     * database. The schemas are not populated by tables, columns etc. and thus,
     * contains only top level Schema information. Schemas that are a part of
     * the getDbms().getType().getSchemaExcludSet() Set are excluded from the
     * Stream.
     * <p>
     * This method can be used to present a list of available Schemas before
     * they are actually being used, for example in a GUI.
     *
     * @return a Stream of un-populated Schemas that are available in this
     * database
     */
    Stream<Schema> schemasUnpopulated();

    /**
     * Returns a Stream of populated Schemas that are available in this
     * database. The schemas are populated by all their sub items such as
     * tables, columns etc. Schemas that are a part of the
     * getDbms().getType().getSchemaExcludSet() Set are excluded from the
     * Stream.
     * <p>
     * This method can be used to obtain a complete inventory of the database
     * structure.
     *
     * @return a Stream of populated Schemas that are available in this database
     */
    public Stream<Schema> schemas();

//    <ENTITY> long readAll(Consumer<ENTITY> consumer);
//
//    <PK> ResultSet read(Table table, PK primaryKey);
//
//    <ENTITY> void insert(Table table, ENTITY entity);
//
//    <ENTITY> void update(Table table, ENTITY entity);
//
//    <ENTITY> void delete(Table table, ENTITY entity);
    /**
     * Eagerly executes a SQL query and subsequently maps each row in the
     * ResultSet using a provided mapper and return a Stream of the mapped
     * objects. The ResultSet is eagerly consumed so that all elements in the
     * ResultSet are read before the Stream produces any objects. If no objects
     * are present or if an SqlExeption is thrown internally, an empty Stream is
     * returned.
     *
     * @param <T> The type of the objects in the Stream to return
     * @param sql The SQL command to execute
     * @param rsMapper The mapper to use when iterating over the ResultSet
     * @return a Stream of the mapped objects
     */
    default <T> Stream<T> executeQuery(final String sql, SqlFunction<ResultSet, T> rsMapper) {
        return executeQuery(sql, Collections.emptyList(), rsMapper);
    }

    /**
     * Eagerly executes a SQL query and subsequently maps each row in the
     * ResultSet using a provided mapper and return a Stream of the mapped
     * objects. The ResultSet is eagerly consumed. If no objects are present or
     * if an SqlExeption is thrown internally, an empty Stream is returned.
     *
     * @param <T> The type of the objects in the Stream to return
     * @param sql The non-null SQL command to execute
     * @param values non-null values to use for "?" parameters in the sql
     * command
     * @param rsMapper The non-null mapper to use when iterating over the
     * ResultSet
     * @return a Stream of the mapped objects
     */
    public <T> Stream<T> executeQuery(final String sql, List<?> values, SqlFunction<ResultSet, T> rsMapper);

    /**
     * Lazily Executes a SQL query and subsequently maps each row in the
     * ResultSet using a provided mapper and return a Stream of the mapped
     * objects. The ResultSet is lazily consumed so that the Stream will consume
     * the ResultSet as the objects are consumed. If no objects are present, an
     * empty Stream is returned.
     *
     * @param <T> The type of the objects in the Stream to return
     * @param sql The non-null SQL command to execute
     * @param values non-null List of objects to use for "?" parameters in the
     * sql command
     * @param rsMapper The non-null mapper to use when iterating over the
     * ResultSet
     * @return a Stream of the mapped objects
     */
    public <T> AsynchronousQueryResult<T> executeQueryAsync(final String sql, List<?> values, Function<ResultSet, T> rsMapper);

    /**
     * Executes a SQL update command. Generated key(s) following an insert
     * command (if any) will be feed to the provided Consumer.
     *
     * @param sql The non-null SQL command to execute
     * @param generatedKeyConsumer the non-null key Consumer
     * @throws java.sql.SQLException if an error occurs
     */
    default void executeUpdate(final String sql, Consumer<List<Long>> generatedKeyConsumer) throws SQLException {
        executeUpdate(sql, Collections.emptyList(), generatedKeyConsumer);
    }

    /**
     * Executes a SQL update command. Generated key(s) following an insert
     * command (if any) will be feed to the provided Consumer.
     *
     * @param sql The non-null SQL command to execute
     * @param values A non-null list
     * @param generatedKeyConsumer non-null List of objects to use for "?"
     * parameters in the sql command
     * @throws java.sql.SQLException if an error occurs
     */
    public void executeUpdate(final String sql, final List<?> values, Consumer<List<Long>> generatedKeyConsumer) throws SQLException;

}
