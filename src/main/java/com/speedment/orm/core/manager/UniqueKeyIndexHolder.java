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
package com.speedment.orm.core.manager;

import static com.speedment.util.stream.StreamUtil.streamOfNullable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 * @param <KEY> Key type used for this IndexHolder
 * @param <PK> Primary Key type for this Manager
 * @param <ENTITY> Entity type for this Manager
 */
public class UniqueKeyIndexHolder<KEY, PK, ENTITY> implements IndexHolder<KEY, PK, ENTITY> {
    
    private final Map<KEY, ENTITY> entities;
    
    public UniqueKeyIndexHolder() {
        entities = new ConcurrentHashMap<>();
    }

    @Override
    public Stream<ENTITY> stream() {
        return entities.values().stream();
    }

    @Override
    public Stream<ENTITY> stream(KEY key) {
        return streamOfNullable(entities.get(key));
    }

    @Override
    public void put(KEY key, ENTITY entity) {
        entities.put(key, entity);
    }

    @Override
    public void remove(KEY key) {
        entities.remove(key);
    }
    
}