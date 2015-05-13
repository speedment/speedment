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
package com.speedment.core.core.manager;

import com.speedment.core.config.model.Column;
import com.speedment.core.config.model.ForeignKey;
import com.speedment.core.config.model.ForeignKeyColumn;
import com.speedment.core.config.model.Table;
import com.speedment.core.core.Buildable;
import com.speedment.core.platform.Platform;
import com.speedment.core.platform.component.ManagerComponent;
import com.speedment.util.java.JavaLanguage;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    @SuppressWarnings("unchecked")
    public String toJson(ENTITY entity) {
        return "{ " + getTable().streamOf(Column.class).map(c -> {
            final StringBuilder sb = new StringBuilder();
            sb.append("\"").append(JavaLanguage.javaVariableName(c.getName())).append("\" : ");

            final Optional<ForeignKeyColumn> oFkc = getTable().streamOf(ForeignKey.class).flatMap(fk -> fk.stream().filter(fkc -> fkc.getColumn().equals(c))).findAny();
            if (oFkc.isPresent()) {
                final Table fkTable = oFkc.get().getForeignTable();
                @SuppressWarnings("rawtypes")
                final Manager fkManager = Platform.get().get(ManagerComponent.class).findByTable(fkTable);

                final Optional<Object> oFkObject = find(entity, c);

                sb.append(oFkObject.map(fkManager::toJson).orElse("null"));
            } else {
                Object val = get(entity, c);
                if (val == null) {
                    sb.append("null");
                } else if (val instanceof Number) {
                    sb.append(val.toString());
                } else if (val instanceof Boolean) {
                    sb.append(val.toString());
                } else {
                    sb.append("\"").append(val.toString()).append("\"");
                }
            }
            return sb.toString();
        }).collect(Collectors.joining(", ")) + " }";
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
