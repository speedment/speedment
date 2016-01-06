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
package com.speedment.internal.core.config.immutable;

import com.speedment.internal.core.config.*;
import com.speedment.Speedment;
import com.speedment.config.PluginData;
import com.speedment.config.db.Project;
import com.speedment.config.aspects.Child;
import com.speedment.config.aspects.Nameable;
import com.speedment.config.aspects.Parent;
import com.speedment.exception.SpeedmentException;
import java.util.Optional;
import static com.speedment.internal.core.config.immutable.ImmutableUtil.throwNewUnsupportedOperationExceptionImmutable;
import groovy.lang.Closure;
import static java.util.Objects.requireNonNull;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public final class ImmutablePluginData extends ImmutableAbstractNamedConfigEntity implements PluginData, ImmutableParentHelper<Child<PluginData>> {

    private final Speedment speedment;
    private final Optional<Project> parent;
    private final ChildHolder<Child<PluginData>> children;

    public ImmutablePluginData(Project parent, PluginData prototype) {
        super(requireNonNull(prototype).getName(), prototype.isExpanded(), prototype.isEnabled());
        requireNonNull(parent);

        // Members
        this.speedment = parent.getSpeedment();
        this.parent    = Optional.of(parent);

        // Children
        switch (prototype.count()) {
            case 0: {
                @SuppressWarnings("unchecked")
                ChildHolder<Child<PluginData>> c = (ChildHolder<Child<PluginData>>) ImmutableChildHolder.ofNone();
                this.children = c;
                break;
            }
            case 1:
                final Child<PluginData> child = prototype.stream().findAny().get();
                this.children = ImmutableChildHolder.of(child);
                break;
            default:
                throw new SpeedmentException(getClass().getSimpleName() + " should only contain one child node.");
        }
    }

    @Override
    public void setParent(Parent<?> parent) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public Optional<Project> getParent() {
        return parent;
    }

    @Override
    public ChildHolder<Child<PluginData>> getChildren() {
        return children;
    }
    
    @Override
    public Stream<? extends Child<PluginData>> stream() {
        return children.stream().sorted(Nameable.COMPARATOR);
    }
    
    @Override
    public <T extends Child<PluginData>> Stream<T> streamOf(Class<T> childClass) {
        return getChildren().stream()
            .map(child -> {
                @SuppressWarnings("unchecked")
                final T cast = (T) child;
                return cast;
            }).sorted(Nameable.COMPARATOR);
    }

    @Override
    public Speedment getSpeedment() {
        return speedment;
    }

    @Override
    public Child<PluginData> metadata(Closure<?> c) {
        return throwNewUnsupportedOperationExceptionImmutable();
    }
}
