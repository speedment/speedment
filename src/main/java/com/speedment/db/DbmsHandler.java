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
package com.speedment.db;

import com.speedment.annotation.Api;
import com.speedment.config.db.Dbms;
import com.speedment.config.db.Project;
import com.speedment.config.db.Schema;
import com.speedment.field.trait.FieldTrait;
import com.speedment.field.trait.ReferenceFieldTrait;
import com.speedment.util.ProgressMeasure;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * A DbmsHandler provides the interface between Speedment and an underlying
 * {@link Dbms}.
 *
 * @author Per Minborg
 * @author Emil Forslund
 * @since 2.0
 */
@Api(version = "2.3")
public interface DbmsHandler {

    /**
     * A String predicate that always returns true.
     */
    final Predicate<String> NO_FILTER = s -> true;

//    /**
//     * Returns the {@link Dbms} document that is used by this 
//     * {@code DbmsHandler}.
//     * <p>
//     * This node will be copied before the reading starts to avoid concurrency
//     * issues.
//     *
//     * @return  the {@link Dbms} document to use as a prototype
//     */
//    Dbms getDbms();
    /**
     * Reads the schema metadata with populated {@link Schema Schemas} that are
     * available in this database. The schemas are populated by all their
     * sub-items such as tables, columns etc. Schemas that are a part of the
     * {@code getDbms().getType().getSchemaExcludSet()} set are excluded from
     * the model.
     * <p>
     * This method can be used to read a complete inventory of the database
     * structure.
     *
     * @param progressListener the progress listener
     * @return the handle for this task
     */
    default CompletableFuture<Project> readSchemaMetadata(
        ProgressMeasure progressListener) {
        return DbmsHandler.this.readSchemaMetadata(progressListener, NO_FILTER);
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
     * @param progressListener the progress listener
     * @param filterCriteria criteria that schema names must fulfill
     * @return the handle for this task
     */
    CompletableFuture<Project> readSchemaMetadata(
        ProgressMeasure progressListener,
        Predicate<String> filterCriteria
    );

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
    default <T> Stream<T> executeQuery(String sql, SqlFunction<ResultSet, T> rsMapper) {
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
    public <T> Stream<T> executeQuery(String sql, List<?> values, SqlFunction<ResultSet, T> rsMapper);

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
        String sql,
        List<?> values,
        Function<ResultSet, T> rsMapper
    );

//    /**
//     * Executes an SQL update command. Generated key(s) following an insert
//     * command (if any) will be feed to the provided {code Consumer}.
//     *
//     * @param sql the non-null SQL command to execute
//     * @param generatedKeyConsumer the non-null key Consumer
//     * @throws SQLException if an error occurs
//     */
//    default void executeInsert(String sql, Consumer<List<Long>> generatedKeyConsumer) throws SQLException {
//        executeInsert(sql, Collections.emptyList(), generatedKeyConsumer);
//    }
    /**
     * Executes an SQL update command. Generated key(s) following an insert
     * command (if any) will be feed to the provided Consumer.
     *
     * @param <F> dynamic type
     * @param sql the non-null SQL command to execute
     * @param values a non-null list
     * @param generatedKeyFields list of the generated fields
     * @param generatedKeyConsumer non-null List of objects to use for "?"
     * parameters in the SQL command
     * @throws SQLException if an error occurs
     */
    public <F extends FieldTrait & ReferenceFieldTrait<?, ?, ?>> void executeInsert(
        final String sql,
        final List<?> values,
        final List<F> generatedKeyFields,
        final Consumer<List<Long>> generatedKeyConsumer
    ) throws SQLException;

//    /**
//     * Executes an SQL update command. Generated key(s) following an insert
//     * command (if any) will be feed to the provided {code Consumer}.
//     *
//     * @param sql the non-null SQL command to execute
//     * @throws SQLException if an error occurs
//     */
//    default void executeUpdate(String sql) throws SQLException {
//        executeUpdate(sql, Collections.emptyList());
//    }
    /**
     * Executes an SQL update command. Generated key(s) following an insert
     * command (if any) will be feed to the provided Consumer.
     *
     * @param sql the non-null SQL command to execute
     * @param values a non-null list
     * @throws SQLException if an error occurs
     */
    public void executeUpdate(final String sql, final List<?> values) throws SQLException;

//    /**
//     * Executes an SQL delete command.
//     *
//     * @param sql the non-null SQL command to execute
//     * @throws SQLException if an error occurs
//     */
//    default void executeDelete(String sql) throws SQLException {
//        executeDelete(sql, Collections.emptyList());
//    }
    /**
     * Executes an SQL delete command. Generated key(s) following an insert
     * command (if any) will be feed to the provided Consumer.
     *
     * @param sql the non-null SQL command to execute
     * @param values a non-null list
     * @throws SQLException if an error occurs
     */
    public void executeDelete(final String sql, final List<?> values) throws SQLException;

    /**
     * Returns a string with information on the current dbms.
     *
     * @return a string with information on the current dbms
     * @throws SQLException if an error occurs
     */
    public String getDbmsInfoString() throws SQLException;

}
