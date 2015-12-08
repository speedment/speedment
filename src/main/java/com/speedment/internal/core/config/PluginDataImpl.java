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
package com.speedment.internal.core.config;

import com.speedment.Speedment;
import com.speedment.config.PluginData;
import com.speedment.config.Project;
import com.speedment.config.aspects.Child;
import com.speedment.config.aspects.Nameable;
import com.speedment.config.aspects.Parent;
import com.speedment.config.plugin.Plugin;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.core.config.aspects.ParentHelper;
import com.speedment.internal.core.config.utils.ConfigUtil;
import com.speedment.internal.util.Cast;
import groovy.lang.Closure;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
public final class PluginDataImpl extends AbstractNamedNode implements PluginData, ParentHelper<Child<PluginData>> {
    
    private final Speedment speedment;
    private final Map<Class<?>, ChildHolder<Child<PluginData>>> children;
    private Project parent;

    public PluginDataImpl(Speedment speedment) {
        this.speedment = requireNonNull(speedment);
        this.children  = new ConcurrentHashMap<>();
    }

    @Override
    public Speedment getSpeedment() {
        return speedment;
    }
    
    @Override
    protected void setDefaults() {
        
    }

    @Override
    public void setParent(Parent<?> parent) {
        this.parent = Cast.castOrFail(parent, Project.class);
    }

    @Override
    public Optional<Project> getParent() {
        return Optional.ofNullable(parent);
    }
    
    @Override
    public ChildHolder<Child<PluginData>> getChildren() {
        throw new IllegalStateException(PluginData.class.getSimpleName() + " does not have a known child type at this point.");
    }
    
    @Override
    public Stream<? extends Child<PluginData>> stream() {
        return children.values().stream()
            .flatMap(holder -> holder.stream().sorted(Nameable.COMPARATOR));
    }
    
    @Override
    public <T extends Child<PluginData>> Stream<T> streamOf(Class<T> childClass) {
        @SuppressWarnings("unchecked")
        final ChildHolder<T> holder = (ChildHolder<T>) children.get(childClass);
        if (holder != null) {
            return holder.stream().sorted(Nameable.COMPARATOR);
        } else {
            return Stream.empty();
        }
    }
    
    @Override
    public <T extends Child<PluginData>> T find(Class<T> childClass, String name) throws SpeedmentException {
        return streamOf(childClass)
            .filter(child -> name.equals(child.getName()))
            .findAny()
            .orElseThrow(() -> new SpeedmentException(
                "Found not child to '" + 
                getClass().getSimpleName() + 
                "' with name '" + name + "'."
            ));
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public Optional<Child<PluginData>> add(Child<PluginData> child) {
        final ChildHolder<Child<PluginData>> holder = children.computeIfAbsent(
            child.getInterfaceMainClass(),
            clazz -> new ChildHolderImpl<>((Class<Child<PluginData>>) clazz)
        );
        
        final Optional<Child<PluginData>> result = holder.put(child, this);
        return result;
    }

    @Override
    public Child<PluginData> metadata(Closure<?> c) {
        @SuppressWarnings("unchecked")
        final String name = (String) c.getProperty("name");
        final Plugin plugin = getSpeedment()
            .getPluginComponent()
            .get(name)
            .orElseThrow(() -> new SpeedmentException(
                "Could not find plugin '" + name + "'."
            ));
        
        return ConfigUtil.groovyDelegatorHelper(c, () -> {
            final Child<PluginData> child = plugin.newChildToPluginData(getSpeedment(), c, this);
            @SuppressWarnings("unchecked")
            final Class<Child<PluginData>> childType = (Class<Child<PluginData>>) child.getClass();
            
            children.computeIfAbsent(
                child.getInterfaceMainClass(), 
                clazz -> new ChildHolderImpl<>(childType)
            ).put(child, this);
            
            return child;
        });
    }
}