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
package com.speedment;

import com.speedment.db.MetaResult;
import com.speedment.annotation.Api;
import com.speedment.config.Column;
import com.speedment.config.Table;
import com.speedment.exception.SpeedmentException;
import com.speedment.field.Encoder;
import com.speedment.internal.core.runtime.Lifecyclable;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * A Manager is responsible for abstracting away an Entity's data source. Entity
 * sources can be RDBMSes, files or other data sources.
 *
 * A Manager must be thread safe and be able to handle several reading and
 * writing threads at the same time.
 *
 * @author pemi
 * @param <ENTITY> Entity type for this Manager
 */
@Api(version = "2.1")
public interface Manager<ENTITY> extends Lifecyclable<Manager<ENTITY>> {

    // Entity Inspection
    /**
     * Returns a unique representation of the primary keys for the given entity.
     * It is guaranteed that this function always return objects that
     * {@link Object#equals(java.lang.Object) equals} for the same set of
     * primary keys in the same JVM instance.
     *
     * Exactly what representation is undefined and it is an error to assume
     * that a certain type representation is returned. For example, if an entity
     * has two primary keys of type String, this method might return a List of
     * the strings or it might return a concatenated string consisting partly of
     * the two strings.
     *
     * If the entity has only one primary key, the method may or may not, return
     * the value of that primary key directly.
     *
     * @param entity to use when obtaining the primary key(s)
     * @return unique representation of the primary keys for the given entity
     */
    Object primaryKeyFor(ENTITY entity);

    /**
     * Gets the property value (field) that corresponds to the provided
     * {@link Column} from the provided entity.
     *
     * @param entity to use
     * @param column describing the field to get
     * @return the property value (field) that corresponds to the provided
     * {@link Column} from the provided entity
     * @throws IllegalArgumentException if the column does not describe a valid
     * field in the given entity.
     * @throws NullPointerException if either the entity or the column is
     * {@code null}
     */
    Object get(ENTITY entity, Column column);

    /**
     * Sets the property value (field) that corresponds to the provided
     * {@link Column} in the provided entity.
     *
     * @param entity to use
     * @param column describing the field to get
     * @param value to set the property to {@link Column} from the provided
     * entity
     * @throws IllegalArgumentException if the column does not describe a valid
     * field in the given entity
     * @throws NullPointerException if either the entity or the column is
     * {@code null}
     */
    void set(ENTITY entity, Column column, Object value);

    //    Object find(ENTITY entity, Column column);
    //
    /**/
    // Data source metadata
    /**
     * Returns the configuration {@link Table} that this Manager is handling.
     *
     * @return the configuration {@link Table} that this Manager is handling
     */
    Table getTable();

    // Entity stuff
    /**
     * Creates an returns a new entity. The new entity will have all its fields
     * initialized to (@code null}.
     *
     * @return a new entity
     */
    ENTITY newInstance();

    /**
     * Returns the entity class for this Manager.
     *
     * @return the entity class for this Manager
     */
    Class<ENTITY> getEntityClass();

    // Json
    /**
     * Creates and returns a string representation of the given entity in the
     * JSON format. The entity will be rendered using the entity's default JSON
     * {@link Encoder}.
     *
     *
     * @param entity to use
     * @return a string representation of the given entity in the JSON format
     */
    String toJson(ENTITY entity);

    // Queries
    /**
     * Creates and returns a new {@link Stream} over all entities in the
     * underlying database. This is the main query API for Speedment.
     * <p>
     * This is <em>an inexpensive O(1) operation</em> that will complete in
     * constant time regardless of the number of entities in the underlying
     * database.
     * <p>
     * The returned stream is aware of its own pipeline and will <em>optimize
     * its own pipeline</em> whenever it encounters a <em>Terminal
     * Operation</em> so that it will only iterate over a minimum set of
     * matching entities.
     * <p>
     * When a Terminal Operation is eventually called on the {@link Stream},
     * that execution time of the Terminal Operation will depend on the
     * optimized pipeline and the entities in the underlying database.
     * <p>
     * The Stream will be automatically
     * {@link Stream#onClose(java.lang.Runnable) closed} after the Terminal
     * Operation is completed or if an Exception is thrown during the Terminal
     * Operation.
     * <p>
     * Some of the <em>Terminal Operations</em> are:
     * <ul>
     * <li>{@link Stream#forEach(java.util.function.Consumer) forEach(Consumer)}
     * <li>{@link Stream#forEachOrdered(java.util.function.Consumer) forEachOrdered(Consumer)}
     * <li>{@link Stream#toArray() toArray()}
     * <li>{@link Stream#toArray(java.util.function.IntFunction) toArray(IntFunction)}
     * <li>{@link Stream#reduce(java.util.function.BinaryOperator) reduce(BinaryOperation}
     * <li>{@link Stream#reduce(java.lang.Object, java.util.function.BinaryOperator) reduce(Object, BinaryOperator)}
     * <li>{@link Stream#reduce(java.lang.Object, java.util.function.BiFunction, java.util.function.BinaryOperator) reduce(Object, BiFunction, BinaryOperator)}
     * <li>{@link Stream#collect(java.util.stream.Collector) collect(Collector)}
     * <li>{@link Stream#collect(java.util.function.Supplier, java.util.function.BiConsumer, java.util.function.BiConsumer) collect(Supplier, BiConsumer, BiConsumer)}
     * <li>{@link Stream#min(java.util.Comparator) min(Comparator)}
     * <li>{@link Stream#max(java.util.Comparator) min(Comparator)}
     * <li>{@link Stream#count() count()}
     * <li>{@link Stream#anyMatch(java.util.function.Predicate) anyMatch(Predicate)}
     * <li>{@link Stream#noneMatch(java.util.function.Predicate) noneMatch(Predicate)}
     * <li>{@link Stream#findFirst() findFirst()}
     * <li>{@link Stream#findAny() findAny()}
     * <li>{@link Stream#iterator() iterator()}
     * </ul>
     * <p>
     * Any Terminating Operation may throw a {@link SpeedmentException} if the
     * underlying database throws an Exception (e.g. an SqlException)
     * <p>
     * Because the Stream may short-circuit operations in the Stream pipeline,
     * methods having side-effects (like
     * {@link Stream#peek(java.util.function.Consumer) peek(Consumer)} will
     * potentially be affected by the optimization.
     * <p>
     * Here are some examples of how the stream optimization might work:
     * <ul>
     * <li>
     * <pre>{@code stream
     *   .filter(Hare.NAME.equal("Henry")
     *   .collect(toList());}</pre>
     * <pre>{@code -> select * from hares where name='Henry'}</pre>
     * </li>
     * <li>
     * <pre>{@code stream.count();}</pre>
     * <pre>{@code -> select count(*) from hares}</pre>
     * </li>
     * <li>
     * <pre>{@code stream
     *   .filter(Hare.NAME.equal("Henry")
     *   .count();}</pre>
     * <pre>{@code -> select count(*) from hares where
     *   name='Henry'}</pre>
     * <p>
     * </li>
     * <li>
     * <pre>{@code stream
     *   .filter(Hare.NAME.equal("Henry")
     *   .filter(Hare.AGE.greaterThan(5)
     *   .count();}</pre>
     * <pre>{@code -> select count(*) from hares where
     *          name ='Henry'
     *        and
     *          age > 5}</pre>
     * </li>
     * </ul>
     *
     *
     * @return a new stream over all entities in this table
     * @throws SpeedmentException if an error occurs during a Terminal Operation
     * (e.g. an SqlException is thrown by the underlying database)
     * @see java.util.stream
     * @see Stream
     */
    Stream<ENTITY> stream();

    // Persistence
    /**
     * Persists the provided entity to the underlying database and returns a
     * potentially updated entity. If the persistence fails for any reason, an
     * unchecked {@link SpeedmentException} is thrown.
     * <p>
     * It is unspecified if the returned updated entity is the same provided
     * entity instance or another entity instance. It is erroneous to assume
     * either, so you should use only the returned entity after the method has
     * been called. However, it is guaranteed that the provided entity is
     * untouched if an exception is thrown.
     * <p>
     * The fields of returned entity instance may differ from the provided
     * entity fields due to auto generated column(s) or because of any other
     * modification that the underlying database imposed on the persisted
     * entity.
     *
     * @param entity to persist
     * @return an entity reflecting the result of the persisted entity
     * @throws SpeedmentException if the underlying database throws an exception
     * (e.g. SQLException)
     */
    ENTITY persist(ENTITY entity) throws SpeedmentException;

    /**
     * Updates the provided entity in the underlying database and returns a
     * potentially updated entity. If the update fails for any reason, an
     * unchecked {@link SpeedmentException} is thrown.
     * <p>
     * It is unspecified if the returned updated entity is the same provided
     * entity instance or another entity instance. It is erroneous to assume
     * either, so you should use only the returned entity after the method has
     * been called. However, it is guaranteed that the provided entity is
     * untouched if an exception is thrown.
     * <p>
     * The fields of returned entity instance may differ from the provided
     * entity fields due to auto generated column(s) or because of any other
     * modification that the underlying database imposed on the persisted
     * entity.
     * <p>
     * Entities are uniquely identified by their primary key(s).
     *
     * @param entity to update
     * @return an entity reflecting the result of the updated entity
     * @throws SpeedmentException if the underlying database throws an exception
     * (e.g. SQLException)
     */
    ENTITY update(ENTITY entity) throws SpeedmentException;

    /**
     * Removes the provided entity from the underlying database and returns the
     * provided entity instance. If the deletion fails for any reason, an
     * unchecked {@link SpeedmentException} is thrown.
     * <p>
     * Entities are uniquely identified by their primary key(s).
     *
     * @param entity to remove
     * @return the provided entity instance
     * @throws SpeedmentException if the underlying database throws an exception
     * (e.g. SQLException)
     */
    ENTITY remove(ENTITY entity) throws SpeedmentException;

    ENTITY persist(ENTITY entity, Consumer<MetaResult<ENTITY>> consumer) throws SpeedmentException;

    ENTITY update(ENTITY entity, Consumer<MetaResult<ENTITY>> consumer) throws SpeedmentException;

    ENTITY remove(ENTITY entity, Consumer<MetaResult<ENTITY>> consumer) throws SpeedmentException;
}
