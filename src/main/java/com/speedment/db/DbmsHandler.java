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
package com.speedment.db;

import com.speedment.annotation.Api;
import com.speedment.config.db.Dbms;
import com.speedment.config.db.Schema;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * A DbmsHandler provides the interface between Speedment and an underlying
 * {@link Dbms}.
 *
 * @author pemi
 * @since 2.0
 */
@Api(version = "2.2")
public interface DbmsHandler {

    static final Predicate<String> SCHEMA_NO_FILTER = s -> true;

    /**
     * Returns the {@link Dbms} node that is used by this {@code DbmsHandler}.
     *
     * @return the {@link Dbms} node
     */
    Dbms getDbms();

//    /**
//     * Returns a Stream of un-populated {@link Schema Schemas} that are 
//     * available in this database. The schemas are not populated by tables, 
//     * columns etc. and thus, contains only top level Schema information. 
//     * Schemas that are a part of the 
//     * {@code getDbms().getType().getSchemaExcludSet()} set are excluded from 
//     * the {@code Stream}.
//     * <p>
//     * This method can be used to present a list of available Schemas before
//     * they are actually being used, for example in a GUI.
//     *
//     * @return  a Stream of un-populated Schemas that are available in this
//     *          database
//     */
//    Stream<Schema> schemasUnpopulated();
    
    /**
     * Reads the schema metadata with populated {@link Schema Schemas} that are
     * available in this database. The schemas are populated by all their
     * sub-items such as tables, columns etc. Schemas that are a part of the
     * {@code getDbms().getType().getSchemaExcludSet()} set are excluded from
     * the model.
     * <p>
     * This method can be used to read a complete inventory of the database
     * structure.
     */
    default void readSchemaMetadata() {
        DbmsHandler.this.readSchemaMetadata(SCHEMA_NO_FILTER);
    }

    /**
     * /**
     * Reads the schema metadata with populated {@link Schema Schemas} that are
     * available in this database. The schemas are populated by all their
     * sub-items such as tables, columns etc. Schemas that are a part of the
     * {@code getDbms().getType().getSchemaExcludSet()} set are excluded from
     * the model or that does not match the given filter will be excluded from
     * the {@code Stream}.
     *
     * @param filterCriteria criteria that schema  names must fulfill
     */
    void readSchemaMetadata(Predicate<String> filterCriteria);

    /**
     * Eagerly executes a SQL query and subsequently maps each row in the
     * ResultSet using a provided mapper and return a Stream of the mapped
     * objects. The ResultSet is eagerly consumed so that all elements in the
     * ResultSet are read before the Stream produces any objects. If no objects
     * are present or if an SQLException is thrown internally, an {@code empty}
     * stream is returned.
     *
     * @param <T> the type of the objects in the stream to return
     * @param sql the SQL command to execute
     * @param rsMapper the mapper to use when iterating over the ResultSet
     * @return a stream of the mapped objects
     */
    default <T> Stream<T> executeQuery(
            final String sql,
            final SqlFunction<ResultSet, T> rsMapper) {

        return executeQuery(sql, Collections.emptyList(), rsMapper);
    }

    /**
     * Eagerly executes a SQL query and subsequently maps each row in the
     * {@link ResultSet} using a provided mapper and return a stream of the
     * mapped objects. The {@code ResultSet} is eagerly consumed. If no objects
     * are present or if an {@link SQLException} is thrown internally, an
     * {@code empty} stream is returned.
     *
     * @param <T> the type of the objects in the stream to return
     * @param sql the non-null SQL command to execute
     * @param values non-null values to use for "?" parameters in the sql
     * command
     * @param rsMapper the non-null mapper to use when iterating over the
     * {@link ResultSet}
     * @return a stream of the mapped objects
     */
    public <T> Stream<T> executeQuery(
            final String sql,
            final List<?> values,
            final SqlFunction<ResultSet, T> rsMapper);

    /**
     * Lazily Executes a SQL query and subsequently maps each row in the
     * {@link ResultSet} using a provided mapper and return a stream of the
     * mapped objects. The {@code ResultSet} is lazily consumed so that the
     * stream will consume the {@code ResultSet} as the objects are consumed. If
     * no objects are present, an {@code empty} stream is returned.
     *
     * @param <T> the type of the objects in the Stream to return
     * @param sql the non-null SQL command to execute
     * @param values non-null List of objects to use for "?" parameters in the
     * SQL command
     * @param rsMapper the non-null mapper to use when iterating over the
     * {@link ResultSet}
     * @return a stream of the mapped objects
     */
    public <T> AsynchronousQueryResult<T> executeQueryAsync(
            final String sql,
            final List<?> values,
            final Function<ResultSet, T> rsMapper);

    /**
     * Executes a SQL update command. Generated key(s) following an insert
     * command (if any) will be feed to the provided {code Consumer}.
     *
     * @param sql the non-null SQL command to execute
     * @param generatedKeyConsumer the non-null key Consumer
     * @throws SQLException if an error occurs
     */
    default void executeUpdate(
            final String sql,
            final Consumer<List<Long>> generatedKeyConsumer
    ) throws SQLException {

        executeUpdate(sql, Collections.emptyList(), generatedKeyConsumer);
    }

    /**
     * Executes a SQL update command. Generated key(s) following an insert
     * command (if any) will be feed to the provided Consumer.
     *
     * @param sql the non-null SQL command to execute
     * @param values a non-null list
     * @param generatedKeyConsumer non-null List of objects to use for "?"
     * parameters in the SQL command
     * @throws SQLException if an error occurs
     */
    public void executeUpdate(
            final String sql,
            final List<?> values,
            final Consumer<List<Long>> generatedKeyConsumer
    ) throws SQLException;
}
