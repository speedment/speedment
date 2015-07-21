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

import com.speedment.core.config.model.Column;
import com.speedment.core.config.model.ForeignKey;
import com.speedment.core.config.model.Table;
import com.speedment.core.Buildable;
import com.speedment.core.lifecycle.Lifecyclable;
import com.speedment.core.platform.Platform;
import com.speedment.core.platform.component.ManagerComponent;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Consumer;
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
    final Set<Consumer<ENTITY>> insertListeners, updateListeners, deleteListeners;
    private Lifecyclable.State state;

    public AbstractManager() {
        indexes = new ConcurrentHashMap<>();
        insertListeners = new CopyOnWriteArraySet<>();
        updateListeners = new CopyOnWriteArraySet<>();
        deleteListeners = new CopyOnWriteArraySet<>();
        state = Lifecyclable.State.CREATED;
    }

    protected void insertEvent(ENTITY entity) {
        insertToIndexes(entity);
        insertListeners.stream().forEachOrdered(c -> c.accept(entity));
    }

    private void insertToIndexes(ENTITY entity) {
        indexes.entrySet().stream().forEach(e -> {
            e.getValue().put(makeKey(e.getKey(), entity), entity);
        });
    }

    protected void updateEvent(ENTITY entity) {
        //TODO Make atomic.
        deleteFromIndexes(entity);
        insertToIndexes(entity);
        updateListeners.stream().forEachOrdered(c -> c.accept(entity));
    }

    protected void deleteEvent(ENTITY entity) {
        deleteFromIndexes(entity);
        deleteListeners.stream().forEachOrdered(c -> c.accept(entity));
    }

    private void deleteFromIndexes(ENTITY entity) {
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
    @SuppressWarnings("unchecked")
    public Optional<Object> find(ENTITY entity, Column column) {
        return getTable()
            .streamOf(ForeignKey.class)
            .flatMap(fk -> fk.stream().filter(fkc -> fkc.getColumn().equals(column)))
            .map(oFkc -> {
                Table fkTable = oFkc.getForeignTable();
                Column fkColumn = oFkc.getForeignColumn();

                @SuppressWarnings("rawtypes")
                final Manager fkManager = Platform.get().get(ManagerComponent.class).findByTable(fkTable);

                Object key = get(entity, column);

                return fkManager.stream().filter(e -> fkManager.get(e, fkColumn).equals(key)).findAny();
            }).filter(o -> o.isPresent()).map(i -> i.get()).findAny();
    }

    @Override
    public void onInsert(Consumer<ENTITY> listener) {
        insertListeners.add(listener);
    }

    @Override
    public void onUpdate(Consumer<ENTITY> listener) {
        updateListeners.add(listener);
    }

    @Override
    public void onDelete(Consumer<ENTITY> listener) {
        deleteListeners.add(listener);
    }

    @Override
    public Manager<PK, ENTITY, BUILDER> initialize() {
        state = State.INIITIALIZED;
        return this;
    }

    @Override
    public Manager<PK, ENTITY, BUILDER> resolve() {
        state = State.RESOLVED;
        return this;
    }

    @Override
    public Manager<PK, ENTITY, BUILDER> start() {
        state = State.STARTED;
        return this;
    }

    @Override
    public Manager<PK, ENTITY, BUILDER> stop() {
        state = State.STOPPED;
        return this;
    }

    @Override
    public Lifecyclable.State getState() {
        return state;
    }

}
