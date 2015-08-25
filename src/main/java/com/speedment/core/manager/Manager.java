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
package com.speedment.core.manager;

import com.speedment.core.annotations.Api;
import com.speedment.core.config.model.Column;
import com.speedment.core.config.model.Table;
import com.speedment.core.exception.SpeedmentException;
import com.speedment.core.field.Field;
import com.speedment.core.formatter.EntityFormatter;
import com.speedment.core.lifecycle.Lifecyclable;
import com.speedment.core.manager.metaresult.MetaResult;
import com.speedment.core.formatter.json.JsonFormatter;
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
@Api(version = "2.0")
public interface Manager<ENTITY> extends Lifecyclable<Manager<ENTITY>> {

    // Entity Inspection
    Object primaryKeyFor(ENTITY entity);

    Object get(ENTITY entity, Column column);

    void set(ENTITY builder, Column column, Object value);

    Object find(ENTITY entity, Column column);

    // Data source metadata
    Table getTable();

    // Introspectors
    // Class<? extends Manager<ENTITY>> getManagerClass();
    ENTITY newInstance();

    Class<ENTITY> getEntityClass();

//    Class<BUILDER> getBuilderClass();
    // Factories
    //Stream<ENTITY> copies(ENTITY entity);
//    BUILDER builder();
//
//    BUILDER toBuilder(ENTITY entity);
    
   String toJson(ENTITY entity);

    default ENTITY toInternal(ENTITY entity) {
        return entity;
    }

    // Queries
    Stream<ENTITY> stream();

    default long size() {
        return stream().count();
    }

    // Reactor methods
    void onInsert(Consumer<ENTITY> listener);

    void onUpdate(Consumer<ENTITY> listener);

    void onDelete(Consumer<ENTITY> listener);

    // Persistence
    void persist(ENTITY entity) throws SpeedmentException;

    void update(ENTITY entity) throws SpeedmentException;

    void remove(ENTITY entity) throws SpeedmentException;

    void persist(ENTITY entity, Consumer<MetaResult<ENTITY>> consumer) throws SpeedmentException;

    void update(ENTITY entity, Consumer<MetaResult<ENTITY>> consumer) throws SpeedmentException;

    void remove(ENTITY entity, Consumer<MetaResult<ENTITY>> consumer) throws SpeedmentException;
}
