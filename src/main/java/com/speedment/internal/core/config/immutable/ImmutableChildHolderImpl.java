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

import com.speedment.internal.util.Lazy;
import com.speedment.internal.core.config.*;
import com.speedment.config.aspects.Parent;
import com.speedment.config.aspects.Child;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import static com.speedment.internal.core.config.immutable.ImmutableUtil.immutableClassModification;
import static java.util.Objects.requireNonNull;

/**
 * A container class for children to a node in the database model tree.
 *
 * @author Emil Forslund
 * @see com.speedment.config.Node
 */
public final class ImmutableChildHolderImpl implements ChildHolder {

    // Basic map
    private final Map<Class<?>, Map<String, Child<?>>> children;
    // Optimized structures
    private final Lazy<List<Child<?>>> lazyChildren;
    private final Map<Class<?>, Lazy<List<Child<?>>>> lazyChildrenMap;

    public static ChildHolder empty() {
        return EMPTY_CHILD_HOLDER;
    }

    public static ChildHolder of(Collection<Child<?>> childs) {
        return new ImmutableChildHolderImpl(childs);
    }

    public static ChildHolder of(Collection<Child<?>> childs, Comparator<Class<?>> comparator) {
        return new ImmutableChildHolderImpl(childs, comparator);
    }

    private ImmutableChildHolderImpl() {
        this(Collections.emptyList(), CLASS_COMPARATOR);
    }

    /**
     * ChildHolder constructor. This will use the name of the qualified name of
     * the children's implementation class to determine the order.
     *
     * @param childs to add
     */
    private ImmutableChildHolderImpl(Collection<Child<?>> childs) {
        this(childs, CLASS_COMPARATOR);
    }

    /**
     * ChildHolder constructor.
     *
     * @param childs to add
     * @param comparator the comparator to use when determining the order of the
     * children.
     */
    private ImmutableChildHolderImpl(Collection<Child<?>> childs, Comparator<Class<?>> comparator) {
        children = new TreeMap<>(requireNonNull(comparator));
        childs.forEach(this::putInternal);
        lazyChildren = new Lazy<>();
        lazyChildrenMap = new HashMap<>();
    }

    public void putInternal(Child<?> child) {
        children.computeIfAbsent(
            child.getInterfaceMainClass(),
            $ -> new TreeMap<>()
        )
            .put(child.getName(), child);
    }

    @Override
    public Optional<Child<?>> put(Child<?> child, Parent<?> parent) {
        return immutableClassModification();
    }

    @Override
    public Stream<Child<?>> stream() {
        return lazyChildren.getOrCompute(() -> children.entrySet().stream()
            .map(Map.Entry::getValue)
            .flatMap(i -> i.values().stream().sorted()).collect(toList()))
            .stream();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <C extends Child<?>> Stream<C> streamOf(Class<C> clazz) {
        requireNonNull(clazz);
        final Map<String, Child<?>> subMap = children.get(clazz);
        if (subMap == null) {
            return Stream.empty();
        } else {
            final Lazy<List<Child<?>>> lazyClassChildren = (Lazy<List<Child<?>>>) lazyChildrenMap.computeIfAbsent(clazz, $ -> new Lazy<>());
            final List<Child<?>> list = lazyClassChildren.getOrCompute(() -> children.getOrDefault(clazz, Collections.emptyMap())
                .values().stream().map(c -> (C) c).sorted().collect(toList()));
            final Stream<C> result = (Stream<C>) list.stream();
            return result;
        }
    }

    private static final ChildHolder EMPTY_CHILD_HOLDER = new EmptyChildHolder();

    private static class EmptyChildHolder implements ChildHolder {

        @Override
        public Optional<Child<?>> put(Child<?> child, Parent<?> parent) {
            return immutableClassModification();
        }

        @Override
        public Stream<Child<?>> stream() {
            return Stream.empty();
        }

        @Override
        public <C extends Child<?>> Stream<C> streamOf(Class<C> clazz) {
            return Stream.empty();
        }

    }

}
