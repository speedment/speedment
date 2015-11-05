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
import com.speedment.config.aspects.Parent;
import com.speedment.config.aspects.Child;
import com.speedment.exception.SpeedmentException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import static com.speedment.internal.core.config.utils.ConfigUtil.thereIsNo;
import java.util.ArrayList;
import java.util.Collections;
import static com.speedment.internal.core.config.immutable.ImmutableUtil.throwNewUnsupportedOperationExceptionImmutable;
import java.util.HashMap;
import static java.util.Objects.requireNonNull;

/**
 * A container class for children to a node in the database model tree.
 *
 * @author Emil Forslund
 * @see com.speedment.config.Node
 */
public final class ImmutableChildHolder implements ChildHolder {

    // Basic map
    private final Map<Class<?>, Map<String, Child<?>>> children;

    // Optimized structures
    private final List<Child<?>> sortedChildren;
    private final Map<Class<?>, List<Child<?>>> sortedChildrenMap;

    public static ChildHolder ofNone() {
        return EMPTY_CHILD_HOLDER;
    }

    public static ChildHolder of(Child<?> child) {
        return new SingletonChildHolder(child);
    }

    public static ChildHolder of(Collection<Child<?>> childs) {
        return of(childs, CLASS_COMPARATOR);
    }

    public static ChildHolder of(Collection<Child<?>> childs, Comparator<Class<?>> comparator) {
        if (childs.isEmpty()) {
            return ofNone();
        }
        if (childs.size() == 1) {
            return of(childs.stream().findAny().get());
        }
        return new ImmutableChildHolder(childs, comparator);
    }

    /**
     * ChildHolder constructor. This will use the name of the qualified name of
     * the children's implementation class to determine the order.
     *
     * @param childs to add
     */
    private ImmutableChildHolder(Collection<Child<?>> childs) {
        this(childs, CLASS_COMPARATOR);
    }

    /**
     * ChildHolder constructor.
     *
     * @param childs to add
     * @param comparator the comparator to use when determining the order of the
     * children.
     */
    private ImmutableChildHolder(Collection<Child<?>> childs, Comparator<Class<?>> comparator) {

        children = new TreeMap<>(requireNonNull(comparator));
        childs.forEach(child -> {
            children.computeIfAbsent(
                    child.getInterfaceMainClass(),
                    $ -> new HashMap<>()
            )
                    .put(child.getName(), child);
        });

        sortedChildren = children.entrySet().stream()
                .map(Map.Entry::getValue)
                .flatMap(i -> i.values().stream().sorted()).collect(toList());

        sortedChildrenMap = new TreeMap<>(comparator);
        children.entrySet().forEach(e -> {
            sortedChildrenMap.computeIfAbsent(e.getKey(), $ -> new ArrayList<>())
                    .addAll(toSortedList(e.getValue()));
        });
        int foo=1;
    }

    private <E> List<E> toSortedList(Map<?, E> map) {
        List<E> result =   map.values().stream().sorted().collect(toList());
        return result;
    }
    
    @Override
    public Optional<Child<?>> put(Child<?> child, Parent<?> parent) {
        return throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public Stream<Child<?>> stream() {
        return sortedChildren.stream();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <C extends Child<?>> Stream<C> streamOf(Class<C> clazz) {
        requireNonNull(clazz);
        final List<Child<?>> classList = sortedChildrenMap.get(clazz);
        if (classList == null) {
            return Stream.empty();
        } else {
            @SuppressWarnings("unchecked")
            final Stream<C> result = (Stream<C>) classList.stream();
            return result;
        }
    }

    @Override
    public <C extends Child<?>> C find(Class<C> childClass, String name) throws SpeedmentException {
        final Child<?> child = children.getOrDefault(childClass, Collections.emptyMap()).get(name);
        if (child == null) {
            throw thereIsNo(childClass, Parent.class, name).get();
        }
        @SuppressWarnings("unchecked")
        final C result = (C) child;
        return result;
    }

    private static final ChildHolder EMPTY_CHILD_HOLDER = new EmptyChildHolder();

    private static class EmptyChildHolder implements ChildHolder {

        @Override
        public Optional<Child<?>> put(Child<?> child, Parent<?> parent) {
            return throwNewUnsupportedOperationExceptionImmutable();
        }

        @Override
        public Stream<Child<?>> stream() {
            return Stream.empty();
        }

        @Override
        public <C extends Child<?>> Stream<C> streamOf(Class<C> clazz) {
            return Stream.empty();
        }

        @Override
        public <C extends Child<?>> C find(Class<C> childClass, String name) throws SpeedmentException {
            throw thereIsNo(childClass, Parent.class, name).get();
        }

    }

    private static class SingletonChildHolder implements ChildHolder {

        final Child<?> child;
        final Class<?> childInterfaceClass;

        public SingletonChildHolder(Child<?> child) {
            this.child = requireNonNull(child);
            this.childInterfaceClass = child.getInterfaceMainClass();
        }

        @Override
        public Optional<Child<?>> put(Child<?> child, Parent<?> parent) {
            return throwNewUnsupportedOperationExceptionImmutable();
        }

        @Override
        public Stream<Child<?>> stream() {
            return Stream.of(child);
        }

        @Override
        public <C extends Child<?>> Stream<C> streamOf(Class<C> clazz) {
            requireNonNull(clazz);
            if (childInterfaceClass.equals(clazz)) {
                @SuppressWarnings("unchecked")
                final C result = (C) child;
                return Stream.of(result);
            } else {
                return Stream.empty();
            }
        }

        @Override
        public <C extends Child<?>> C find(Class<C> childClass, String name) throws SpeedmentException {
            requireNonNull(childClass);
            requireNonNull(name);
            if (childInterfaceClass.equals(childClass)) {
                if (name.equals(child.getName())) {
                    @SuppressWarnings("unchecked")
                    final C result = (C) child;
                    return result;
                }
            }
            throw thereIsNo(childClass, Parent.class, name).get();
        }

    }

}
