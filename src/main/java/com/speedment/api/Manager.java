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
package com.speedment.api;

import com.speedment.api.db.MetaResult;
import com.speedment.api.annotation.Api;
import com.speedment.api.config.Column;
import com.speedment.api.config.Table;
import com.speedment.api.exception.SpeedmentException;
import com.speedment.core.runtime.Lifecyclable;
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
    Object primaryKeyFor(ENTITY entity);

    Object get(ENTITY entity, Column column);

    void set(ENTITY builder, Column column, Object value);

    Object find(ENTITY entity, Column column);

    // Data source metadata
    Table getTable();

    ENTITY newInstance();

    Class<ENTITY> getEntityClass();

    String toJson(ENTITY entity);

    // Queries
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
