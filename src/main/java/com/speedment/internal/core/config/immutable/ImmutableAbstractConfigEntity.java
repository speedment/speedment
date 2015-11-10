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

import com.speedment.annotation.External;
import com.speedment.config.Project;
import com.speedment.config.aspects.Child;
import com.speedment.config.aspects.Enableable;
import com.speedment.config.Node;
import com.speedment.config.aspects.Parent;
import com.speedment.internal.util.Trees;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Function;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import static com.speedment.internal.core.config.immutable.ImmutableUtil.throwNewUnsupportedOperationExceptionImmutable;

/**
 * Generic representation of a ConfigEntity.
 *
 * This class is thread safe.
 *
 * @author pemi
 */
public abstract class ImmutableAbstractConfigEntity implements Node, Enableable {

    private final boolean enabled;
    private final String name;

    protected ImmutableAbstractConfigEntity(String name, boolean enabled) {
        this.enabled = enabled;
        this.name = name; // Can be null
    }

    @Override
    public final Boolean isEnabled() {
        return enabled;
    }

    @Override
    public final void setEnabled(Boolean enabled) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @External(type = String.class)
    @Override
    public final String getName() {
        return name;
    }

    @External(type = String.class)
    @Override
    public final void setName(String name) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public final Stream<? extends Parent<?>> ancestors() {
        return asChild()
                .flatMap(c -> c.getParent())
                .map(p -> (Parent<?>) p)
                .map(parent -> Trees.walkOptional(
                        parent, (Parent<?> p) -> p.asChild()
                        .flatMap(c -> c.getParent())
                        .map(p2 -> (Parent<?>) p2),
                        Trees.WalkingOrder.BACKWARD
                )).orElse(Stream.empty());
    }

    @SuppressWarnings("unchecked")
    @Override
    public final <E extends Node> Optional<E> ancestor(final Class<E> clazz) {
        return ancestors()
                .filter(p -> clazz.isAssignableFrom(p.getClass()))
                .map(p -> (E) p)
                .findFirst();
    }

    @Override
    public final <T extends Parent<?>> String getRelativeName(final Class<T> from, Function<String, String> nameMapper) {
        Objects.requireNonNull(from);
        final StringJoiner sj = new StringJoiner(".", "", ".").setEmptyValue("");
        final List<Parent<?>> ancestors = ancestors().map(p -> (Parent<?>) p).collect(toList());
        boolean add = false;
        for (final Parent<?> parent : ancestors) {
            if (from.isAssignableFrom(parent.getClass())) {
                add = true;
            }
            if (add) {
                sj.add(nameMapper.apply(parent.getName()));
            }
        }
        return sj.toString() + nameMapper.apply(getName());
    }

    @Override
    public final String toString() {
        return getInterfaceMainClass().getSimpleName()
                + " '" + Optional.of(this)
                .filter(ImmutableAbstractConfigEntity::isChildInterface)
                .map(e -> (Child<?>) e)
                .flatMap(Child::getParent)
                .map(e -> getRelativeName(Project.class))
                .orElse(getName())
                + "'";
    }

    // This method is called when the entire tree is constructed
    public void resolve() {
//        System.out.println("resolve: " + toString());
    }

}
