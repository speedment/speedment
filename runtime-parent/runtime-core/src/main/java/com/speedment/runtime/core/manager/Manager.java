/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.core.manager;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.method.BackwardFinder;
import com.speedment.runtime.field.method.FindFrom;
import com.speedment.runtime.field.trait.HasFinder;
import com.speedment.runtime.field.trait.HasNullableFinder;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 * A Manager is responsible for abstracting away an Entity's data source CRUD
 * operations. Entity sources can be RDBMSes, files or other data sources.
 *
 * A Manager must be thread safe and be able to handle several reading and
 * writing threads at the same time.
 *
 * @param <ENTITY> entity type for this Manager
 *
 * @author  Per Minborg
 * @author  Emil Forslund
 * @since   2.0.0
 */
public interface Manager<ENTITY> {

    /**
     * Returns an identifier for the table that this {@code Manager} handles
     * entities for.
     *
     * @return the table identifier
     */
    TableIdentifier<ENTITY> getTableIdentifier();

    /**
     * Returns the entity class for this Manager.
     *
     * @return the entity class
     */
    Class<ENTITY> getEntityClass();

    /**
     * Returns a stream of the fields that every entity in this {@code Manager}
     * contains.
     *
     * @return a stream of all fields
     */
    Stream<Field<ENTITY>> fields();

    /**
     * Returns a stream of the fields that are included in the primary key of
     * the table represented by this {@code Manager}.
     *
     * @return the primary key fields
     */
    Stream<Field<ENTITY>> primaryKeyFields();

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
     *
     * @throws SpeedmentException if an error occurs during a Terminal Operation
     *                            (e.g. an SqlException is thrown by the
     *                            underlying database)
     *
     * @see java.util.stream
     * @see Stream
     */
    Stream<ENTITY> stream();

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
     * @return       an entity reflecting the result of the persisted entity
     *
     * @throws SpeedmentException if the underlying database throws an exception
     *                            (e.g. SQLException)
     */
    default ENTITY persist(ENTITY entity) throws SpeedmentException {
        return persister().apply(entity);
    }

    /**
     * Returns a {@link Persister} that when its
     * {@link Persister#apply(java.lang.Object) } method is called, will produce
     * the same result as {@link #persist(java.lang.Object) }
     *
     * @return a Persister
     */
    Persister<ENTITY> persister();

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
     * @return       an entity reflecting the result of the updated entity
     *
     * @throws SpeedmentException if the underlying database throws an exception
     *                            (e.g. SQLException)
     */
    default ENTITY update(ENTITY entity) throws SpeedmentException {
        return updater().apply(entity);
    }

    /**
     * Returns an {@link Updater} that when its {@link Persister#apply(Object)}
     * method is called, will produce the same result as {@link #update(Object)}
     *
     * @return an Updater
     */
    Updater<ENTITY> updater();

    /**
     * Removes the provided entity from the underlying database and returns the
     * provided entity instance. If the deletion fails for any reason, an
     * unchecked {@link SpeedmentException} is thrown.
     * <p>
     * Entities are uniquely identified by their primary key(s).
     *
     * @param entity to remove
     * @return       the provided entity instance
     *
     * @throws SpeedmentException if the underlying database throws an exception
     *                            (e.g. SQLException)
     */
    default ENTITY remove(ENTITY entity) throws SpeedmentException {
        return remover().apply(entity);
    }

    /**
     * Returns a {@link Remover} that when its {@link Persister#apply(Object)}
     * method is called, will produce the same result as {@link #remove(Object)}
     *
     * @return a Remover
     */
    Remover<ENTITY> remover();

    /**
     * Returns a Function that, when it is applied, will produce an equivalent
     * result as if {@link #finderByNullable(HasNullableFinder)} was called.
     *
     * @param <FK_ENTITY> the type of the foreign entity
     *
     * @param fkField the foreign key field
     * @return        a function that returns an Entity (if any) that matches
     *                the given a foreign key relation (foreign field and
     *                entity)
     * 
     * @see #finderByNullable(HasNullableFinder)
     */
    default <FK_ENTITY> FindFrom<FK_ENTITY, ENTITY> finderBy(
            HasFinder<FK_ENTITY, ENTITY> fkField) {

        return fkField.finder(getTableIdentifier(), this::stream);
    }

    /**
     * Retrieves and returns an Entity that matches the given a foreign key
     * relation (foreign field and entity). For example, if there is an entity
     * Carrot with a FK to Hare using the column "hare", then
     * hares.find(Carrot.HARE, carrot) will return the Hare that the Carrot.HARE
     * field is pointing to.
     *
     * @param <FK_ENTITY> the type of the foreign entity
     *
     * @param fkField  the foreign key field
     * @param fkEntity the foreign key entity
     * @return         an Entity (if any) that matches the given a foreign key
     *                 relation (foreign field and entity)
     */
    default <FK_ENTITY> ENTITY findBy(
            HasFinder<FK_ENTITY, ENTITY> fkField,
            FK_ENTITY fkEntity) {

        return finderBy(fkField).apply(fkEntity);
    }

    /**
     * Returns a Function that, when it is applied, will produce an equivalent
     * result as if {@link #findByNullable(HasNullableFinder, Object)} was
     * called.
     *
     * @param <FK_ENTITY> the type of the foreign entity
     *
     * @param fkField the foreign key field
     * @return        a function that returns an Entity (if any) that matches
     *                the given a foreign key relation (foreign field and
     *                entity)
     * 
     * @see #findByNullable(HasNullableFinder, Object)
     */
    default <FK_ENTITY> Function<FK_ENTITY, Stream<ENTITY>> finderByNullable(
            HasNullableFinder<FK_ENTITY, ENTITY> fkField) {

        return fkField.nullableFinder(getTableIdentifier(), this::stream);
    }

    /**
     * Retrieves and returns a stream of Entities (with one or zero elements)
     * that matches the given a foreign key relation (foreign field and entity).
     * For example, if there is an entity Carrot with a FK to Hare using the
     * column "hare", then hares.find(Carrot.HARE, carrot) will return a stream
     * with the Hare that the Carrot.HARE field is pointing to or Stream.empty()
     * if the hare column in carrot was null. I.e. The returned Stream will
     * contain either zero or one element.
     *
     * @param <FK_ENTITY> the type of the foreign entity
     *
     * @param fkField  the foreign key field
     * @param fkEntity the foreign key entity
     * @return         an Entity (if any) that matches the given a foreign key
     *                 relation (foreign field and entity)
     */
    default <FK_ENTITY> Stream<ENTITY> findByNullable(
            HasNullableFinder<FK_ENTITY, ENTITY> fkField,
            FK_ENTITY fkEntity) {

        return finderByNullable(fkField).apply(fkEntity);
    }

    /**
     * Returns a Function that, when it is applied, will produce an equivalent
     * result as if {@link #findBackwardsBy(HasFinder, Object)} was called.
     *
     * @param <FK_ENTITY> the type of the foreign entity
     *
     * @param fkField the foreign key field
     * @return        an Entity (if any) that matches the given a foreign key
     *                relation (foreign field and entity)
     *
     * @see #findBackwardsBy(HasFinder, Object)
     */
    default <FK_ENTITY> BackwardFinder<FK_ENTITY, ENTITY> finderBackwardsBy(
            HasFinder<ENTITY, FK_ENTITY> fkField) {

        return fkField.backwardFinder(getTableIdentifier(), this::stream);
    }

    /**
     * Retrieves and returns a stream of matching entities that matches the
     * given a foreign key relation (foreign field and entity). For example, if
     * there is an entity Carrot with a FK to Hare using the column "hare", then
     * carrots.findBackwardsBy(Carrot.HARE, hare) will produce a Stream of all
     * the Carrots that points to the given hare using the Carrot.HARE column.
     *
     * @param <FK_ENTITY> the type of the foreign entity
     *
     * @param fkField  the foreign key field
     * @param fkEntity the foreign key entity
     * @return         an Entity (if any) that matches the given a foreign key
     *                 relation (foreign field and entity)
     */
    default <FK_ENTITY> Stream<ENTITY> findBackwardsBy(
            HasFinder<ENTITY, FK_ENTITY> fkField,
            FK_ENTITY fkEntity) {

        return finderBackwardsBy(fkField).apply(fkEntity);
    }

}
