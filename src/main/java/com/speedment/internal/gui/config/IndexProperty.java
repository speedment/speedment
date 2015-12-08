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
import com.speedment.config.Index;
import com.speedment.config.IndexColumn;
import com.speedment.config.Table;
import com.speedment.config.aspects.Ordinable;
import com.speedment.config.aspects.Parent;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.core.config.utils.ConfigUtil;
import com.speedment.internal.ui.property.BooleanPropertyItem;
import groovy.lang.Closure;
import static java.util.Collections.newSetFromMap;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import static javafx.collections.FXCollections.observableSet;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil Forslund
 */
public final class IndexProperty extends AbstractParentProperty<Index, IndexColumn> implements Index, ChildHelper<Index, Table> {
    
    private final ObservableSet<IndexColumn> indexColumnChildren;
    private final BooleanProperty unique;
    
    private Table parent;
    
    public IndexProperty(Speedment speedment) {
        super(speedment);
        indexColumnChildren = observableSet(newSetFromMap(new ConcurrentHashMap<>()));
        unique              = new SimpleBooleanProperty();
    }
    
    public IndexProperty(Speedment speedment, Table parent, Index prototype) {
        super(speedment, prototype);
        indexColumnChildren = copyChildrenFrom(prototype, IndexColumn.class, IndexColumnProperty::new);
        unique              = new SimpleBooleanProperty(prototype.isUnique());
        this.parent = parent;
    }
    
    @Override
    protected Stream<PropertySheet.Item> guiVisibleProperties() {
        return Stream.of(
            new BooleanPropertyItem(
                unique,
                "Is Unique",
                "True if elements in this index are unique."
            )
        );
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
    public ObservableList<IndexColumn> children() {
        return createChildrenView(indexColumnChildren);
    }
    
    @Override
    public void setUnique(Boolean unique) {
        this.unique.setValue(unique);
    }

    @Override
    public Boolean isUnique() {
        return unique.getValue();
    }
    
    public BooleanProperty uniqueProperty() {
        return unique;
    }

    @Override
    public IndexColumn addNewIndexColumn() {
        final IndexColumn indexColumn = new IndexColumnProperty(getSpeedment());
        add(indexColumn);
        return indexColumn;
    }
    
    @Override
    public IndexColumn indexColumn(Closure<?> c) {
        return ConfigUtil.groovyDelegatorHelper(c, () -> addNewIndexColumn());
    }
    
    @Override
    public Optional<IndexColumn> add(IndexColumn child) throws IllegalStateException {
        requireNonNull(child);
        return indexColumnChildren.add(child) ? Optional.empty() : Optional.of(child);
    }
    
    @Override
    public Optional<IndexColumn> remove(IndexColumn child) {
        requireNonNull(child);
        if (indexColumnChildren.remove(child)) {
            child.setParent(null);
            return Optional.of(child);
        } else return Optional.empty();
    }

    @Override
    public Stream<IndexColumn> stream() {
        return indexColumnChildren.stream().sorted(Ordinable.COMPARATOR);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends IndexColumn> Stream<T> streamOf(Class<T> childType) {
        requireNonNull(childType);
        
        if (IndexColumn.class.isAssignableFrom(childType)) {
            return (Stream<T>) indexColumnChildren.stream().sorted(Ordinable.COMPARATOR);
        } else {
            throw wrongChildTypeException(childType);
        }
    }
    
    @Override
    public int count() {
        return indexColumnChildren.size();
    }

    @Override
    public int countOf(Class<? extends IndexColumn> childType) {
        if (IndexColumn.class.isAssignableFrom(childType)) {
            return indexColumnChildren.size();
        } else {
            throw wrongChildTypeException(childType);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends IndexColumn> T find(Class<T> childType, String name) throws SpeedmentException {
        requireNonNull(childType);
        requireNonNull(name);
        
        if (IndexColumn.class.isAssignableFrom(childType)) {
            return (T) indexColumnChildren.stream().filter(child -> name.equals(child.getName()))
                .findAny().orElseThrow(() -> noChildWithNameException(childType, name));
        } else {
            throw wrongChildTypeException(childType);
        }
    }
}