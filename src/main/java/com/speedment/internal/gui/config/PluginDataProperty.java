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
import com.speedment.config.PluginData;
import com.speedment.config.Project;
import com.speedment.config.aspects.Child;
import com.speedment.config.aspects.Parent;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.core.config.utils.ConfigUtil;
import groovy.lang.Closure;
import static java.util.Collections.newSetFromMap;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import java.util.concurrent.ConcurrentHashMap;
import static java.util.stream.Collectors.toCollection;
import static javafx.collections.FXCollections.observableSet;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil Forslund
 */
public final class PluginDataProperty extends AbstractParentProperty<PluginData, Child<PluginData>> implements PluginData, ChildHelper<PluginData, Project> {
    
    private final ObservableSet<Child<PluginData>> children;
    
    private Project parent;

    public PluginDataProperty(Speedment speedment) {
        super(speedment);
        children = observableSet(newSetFromMap(new ConcurrentHashMap<>()));
    }

    public PluginDataProperty(Speedment speedment, Project parent, PluginData prototype) {
        super(speedment, prototype);
        children = observableSet(
            prototype.stream()
                .map(child -> {
                    @SuppressWarnings("unchecked")
                    final Child<PluginData> newChild = (Child<PluginData>) child;
                    newChild.setParent(this);
                    return newChild;
                })
                .collect(toCollection(() -> newSetFromMap(new ConcurrentHashMap<>())))
        );
        
        this.parent = parent;
    }
    
    @Override
    protected Stream<PropertySheet.Item> guiVisibleProperties() {
        return Stream.empty();
    }
    
    @Override
    public Optional<Project> getParent() {
        return Optional.ofNullable(parent);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setParent(Parent<?> parent) {
        if (parent instanceof Project) {
            this.parent = (Project) parent;
        } else {
            throw wrongParentClass(parent.getClass());
        }
    }
    
    @Override
    public ObservableList<Child<PluginData>> children() {
        return createChildrenView(children);
    }

    @Override
    public Child<PluginData> metadata(Closure<?> c) {
        @SuppressWarnings("unchecked")
        final String name = (String) c.getProperty("name");

        return ConfigUtil.groovyDelegatorHelper(c, () -> {
            final Child<PluginData> child = getSpeedment()
                .getPluginComponent()
                .get(name)
                .orElseThrow(() -> new SpeedmentException(
                    "Could not find plugin '" + name + "'."
                ))
                .newChildToPluginData(getSpeedment(), c, this);
            
            children.add(child);
            return child;
        });
    }
    
    @Override
    public Optional<? extends Child<PluginData>> add(Child<PluginData> child) {
        requireNonNull(child);
        return children.add(child) ? Optional.empty() : Optional.of(child);
    }
    
    @Override
    public Optional<? extends Child<PluginData>> remove(Child<PluginData> child) {
        requireNonNull(child);
        if (children.remove(child)) {
            child.setParent(null);
            return Optional.of(child);
        } else return Optional.empty();
    }
    
    @Override
    public Stream<? extends Child<PluginData>> stream() {
        return children.stream().sorted(COMPARATOR);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Child<PluginData>> Stream<T> streamOf(Class<T> childType) {
        requireNonNull(childType);
        return stream().map(childType::cast).sorted(COMPARATOR);
    }
    
    @Override
    public int count() {
        return children.size();
    }

    @Override
    public int countOf(Class<? extends Child<PluginData>> childType) {
        requireNonNull(childType);
        return children.size();
    }
    
    @Override
    public <T extends Child<PluginData>> T find(Class<T> childType, String name) throws SpeedmentException {
        requireNonNull(childType);
        requireNonNull(name);
        
        final Child<PluginData> child = children.stream().filter(c -> name.equals(c.getName()))
            .findAny().orElseThrow(() -> noChildWithNameException(childType, name));

        if (child.getInterfaceMainClass().isAssignableFrom(childType)) {
            @SuppressWarnings("unchecked")
            final T node = (T) child;
            return node;
        } else {
            throw wrongChildTypeException(childType);
        }
    }
}