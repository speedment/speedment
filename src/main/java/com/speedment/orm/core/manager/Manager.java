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
import com.speedment.orm.config.model.Table;
import com.speedment.orm.core.Buildable;
import com.speedment.orm.core.Persistable;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
@Api(version = 0)
public interface Manager<T> {

    // Metadata
    Table getTable();

    // Entity Mapping
//    PK primaryKey(ENTITY entity);
//
//    BEAN bean(ENTITY entity);
//
//    BUILDER builder(ENTITY entity);

    <B extends Buildable<T>> B builder(Class<B> builderClass);
    
    <B extends Buildable<T>> B builderOf(Class<B> builderClass, T model);
    
    <P extends Persistable<T>> P persister(Class<P> persistableClass);
    
    <P extends Persistable<T>> P persisterOf(Class<P> persistableClass, T model);
    
//    ENTITY entity(ENTITY prototype);
//
//    ENTITY newEntity();

    // Retrieval
    Stream<T> stream();

    default long size() {
        return stream().count();
    }

    // Persistence
    T insert(T entity);

    T update(T entity);

    void delete(Object pk);

    void load();

}
