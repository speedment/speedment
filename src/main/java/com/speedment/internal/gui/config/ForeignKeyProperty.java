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
package com.speedment.internal.gui.config;

import com.speedment.Speedment;
import com.speedment.config.ForeignKey;
import com.speedment.config.ForeignKeyColumn;
import com.speedment.config.Table;
import com.speedment.config.aspects.Parent;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.core.config.utils.ConfigUtil;
import com.speedment.stream.MapStream;
import groovy.lang.Closure;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Stream;
import static javafx.collections.FXCollections.observableMap;
import javafx.collections.ObservableMap;

/**
 *
 * @author Emil Forslund
 */
public final class ForeignKeyProperty extends AbstractParentProperty<ForeignKey, ForeignKeyColumn> implements ForeignKey, ChildHelper<ForeignKey, Table> {
    
    private final ObservableMap<String, ForeignKeyColumn> foreignKeyColumnChildren;
    
    private Table parent;
    
    public ForeignKeyProperty(Speedment speedment) {
        super(speedment);
        foreignKeyColumnChildren = observableMap(new ConcurrentSkipListMap<>());
    }
    
    public ForeignKeyProperty(Speedment speedment, ForeignKey prototype) {
        super(speedment, prototype);
        foreignKeyColumnChildren = copyChildrenFrom(prototype, ForeignKeyColumn.class, ForeignKeyColumnProperty::new);
    }
    
    @Override
    public Optional<Table> getParent() {
        return Optional.ofNullable(parent);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setParent(Parent<?> parent) {
        if (parent instanceof Table) {
            this.parent = (Table) parent;
        } else {
            throw wrongParentClass(parent.getClass());
        }
    }

    @Override
    public ForeignKeyColumn addNewForeignKeyColumn() {
        final ForeignKeyColumn column = new ForeignKeyColumnProperty(getSpeedment());
        add(column);
        return column;
    }
    
    @Override
    public ForeignKeyColumn foreignKeyColumn(Closure<?> c) {
        return ConfigUtil.groovyDelegatorHelper(c, () -> addNewForeignKeyColumn());
    }
    
    @Override
    public Optional<ForeignKeyColumn> add(ForeignKeyColumn child) throws IllegalStateException {
        return Optional.ofNullable(foreignKeyColumnChildren.put(child.getName(), child));
    }

    @Override
    public Stream<ForeignKeyColumn> stream() {
        return MapStream.of(foreignKeyColumnChildren).values();
    }

    @Override
    public <T extends ForeignKeyColumn> Stream<T> streamOf(Class<T> childType) {
        requireNonNull(childType);
        
        if (ForeignKeyColumn.class.isAssignableFrom(childType)) {
            return (Stream<T>) foreignKeyColumnChildren.values().stream();
        } else {
            throw wrongChildTypeException(childType);
        }
    }
    
    @Override
    public int count() {
        return foreignKeyColumnChildren.size();
    }

    @Override
    public int countOf(Class<? extends ForeignKeyColumn> childType) {
        if (ForeignKeyColumn.class.isAssignableFrom(childType)) {
            return foreignKeyColumnChildren.size();
        } else {
            throw wrongChildTypeException(childType);
        }
    }

    @Override
    public <T extends ForeignKeyColumn> T find(Class<T> childType, String name) throws SpeedmentException {
        requireNonNull(childType);
        requireNonNull(name);
        
        final T node;
        if (ForeignKeyColumn.class.isAssignableFrom(childType)) {
            node = (T) foreignKeyColumnChildren.get(name);
        } else {
            throw wrongChildTypeException(childType);
        }
        
        if (node != null) {
            return node;
        } else {
            throw noChildWithNameException(childType, name);
        }
    }
}