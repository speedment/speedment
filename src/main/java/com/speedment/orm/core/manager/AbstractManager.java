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

import com.speedment.orm.config.model.Column;
import com.speedment.orm.core.Buildable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 *
 * @author Emil Forslund
 *
 * @param <PK> PrimaryKey type for this Manager
 * @param <ENTITY> Entity type for this Manager
 * @param <BUILDER> Builder type for this Manager
 */
public abstract class AbstractManager<PK, ENTITY, BUILDER extends Buildable<ENTITY>> implements Manager<PK, ENTITY, BUILDER> {

    private final Map<List<Column>, IndexHolder<Object, PK, ENTITY>> indexes;

    public AbstractManager() {
        indexes = new ConcurrentHashMap<>();
    }

    @Override
    public void onInsert(ENTITY entity) {
        indexes.entrySet().stream().forEach(e -> {
            e.getValue().put(makeKey(e.getKey(), entity), entity);
        });
    }

    @Override
    public void onUpdate(ENTITY entity) {
        //TODO Make atomic.
        onDelete(entity);
        onInsert(entity);
    }

    @Override
    public void onDelete(ENTITY entity) {
        indexes.entrySet().stream().forEach(e -> {
            e.getValue().remove(makeKey(e.getKey(), entity));
        });
    }

    private Object makeKey(List<Column> columns, ENTITY entity) {
        if (columns.size() == 1) {
            return get(entity, columns.get(0));
        } else {
            return columns.stream()
                    .map(c -> get(entity, c))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Boolean initialize() {
        return Boolean.TRUE;
    }

    @Override
    public Boolean resolve() {
        return Boolean.TRUE;
    }

    @Override
    public Boolean start() {
        return Boolean.TRUE;
    }

    @Override
    public Boolean stop() {
        return Boolean.TRUE;
    }

}
