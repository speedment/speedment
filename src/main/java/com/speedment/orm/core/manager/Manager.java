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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.orm.core.manager;

import com.speedment.orm.annotations.Api;
import com.speedment.orm.config.model.Column;
import com.speedment.orm.config.model.Table;
import com.speedment.orm.core.Buildable;
import com.speedment.orm.core.lifecycle.Lifecyclable;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * A Manager is responsible for abstracting away an Entity source from the
 * application. Entity sources can be RDBMSes, files or other data sources.
 *
 * A Manager must be thread safe and be able to handle several reading and
 * writing threads at the same time.
 *
 * @author pemi
 * @param <PK> PrimaryKey type for this Manager
 * @param <ENTITY> Entity type for this Manager
 * @param <BUILDER> Builder type for this Manager
 */
@Api(version = 0)
public interface Manager<PK, ENTITY, BUILDER extends Buildable<ENTITY>> extends Lifecyclable<Boolean> {

    // Entity Inspection
    PK primaryKeyFor(ENTITY entity);

    Object get(ENTITY entity, Column column);

    void set(BUILDER builder, Column column, Object value);

    // Data source metadata
    Table getTable();

    // Introspectors
    Class<? extends Manager<PK, ENTITY, BUILDER>> getManagerClass();

    Class<ENTITY> getEntityClass();

    Class<BUILDER> getBuilderClass();

    // Factories
    BUILDER builder();

    BUILDER toBuilder(ENTITY entity);

    default ENTITY toInternal(ENTITY entity) {
        return entity;
    }

    // Queries
    Stream<ENTITY> stream();

    default long size() {
        return stream().count();
    }

    // Add and remove
    void onInsert(ENTITY entity);

    void onUpdate(ENTITY entity);

    void onDelete(ENTITY primaryKey);

    // Persistence
    Optional<ENTITY> persist(ENTITY entity);

    Optional<ENTITY> update(ENTITY entity);

    Optional<ENTITY> remove(ENTITY entity);
}
