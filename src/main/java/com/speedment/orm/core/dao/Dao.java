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
package com.speedment.orm.core.dao;

import com.speedment.orm.config.model.Table;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public interface Dao<PK, ENTITY, BEAN, BUILDER> {

    // Metadata
    Table getTable();

    // Entity Mapping
    PK primaryKey(ENTITY entity);

    BEAN bean(ENTITY entity);

    BUILDER builder(ENTITY entity);

    ENTITY entity(ENTITY entity);

    ENTITY newEntity();

    // Retrieval
    Stream<ENTITY> stream();

    default long size() {
        return stream().count();
    }

    // Persistence
    ENTITY insert(ENTITY entity);

    ENTITY update(ENTITY entity);

    void delete(PK pk);

    void load();

}
