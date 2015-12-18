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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import static com.speedment.internal.core.config.utils.ConfigUtil.thereIsNo;
import static com.speedment.internal.core.config.immutable.ImmutableUtil.throwNewUnsupportedOperationExceptionImmutable;
import java.util.HashMap;
import static java.util.Objects.requireNonNull;

/**
 * A container class for children to a node in the database model tree.
 *
 * @author Emil Forslund
 * @param <T> the child type
 * @see com.speedment.config.Node
 */
public final class ImmutableChildHolder<T extends Child<?>> implements ChildHolder<T> {

    private final Class<T> childClass;

    // Basic map
    private final Map<String, T> children;

    // Optimized structures
    private final List<T> sortedChildren;

    public static ChildHolder<?> ofNone() {
        return new EmptyChildHolder();
    }

    public static <T extends Child<?>> ChildHolder<T> of(T child) {
        return new SingletonChildHolder<>(child);
    }

    public static <T extends Child<?>> ChildHolder<T> of(Class<T> childClass, Collection<T> childs) {
        if (childs.isEmpty()) {
            @SuppressWarnings("unchecked")
            final ChildHolder<T> result = (ChildHolder<T>) ofNone();
            return result;
        }
        if (childs.size() == 1) {
            return of(childs.stream().findAny().get());
        }
        return new ImmutableChildHolder<>(childClass, childs);
    }

    /**
     * ChildHolder constructor.
     *
     * @param childs to add
     * @param comparator the comparator to use when determining the order of the
     * children.
     */
    private ImmutableChildHolder(Class<T> childClass, Collection<T> childs) {

        children = new HashMap<>();
        childs.forEach(child -> {
            children.put(child.getName(), child);
        });

        sortedChildren = children.values()
                .stream()
                .sorted()
                .collect(toList());

        this.childClass = childClass;
    }

    @Override
    public Optional<T> put(T child, Parent<? super T> parent) {
        return throwNewUnsupportedOperationExceptionImmutable();
    }
    
    @Override
    public Optional<T> remove(T child) {
        return throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public Stream<T> stream() {
        return sortedChildren.stream();
    }

    @Override
    public T find(String name) throws SpeedmentException {
        final T result = children.get(name);
        if (result != null) {
            return result;
        }
        throw thereIsNo(childClass, Parent.class, name).get();
    }

    @Override
    public Class<T> getChildClass() {
        return childClass;
    }

    private static class EmptyChildHolder implements ChildHolder<Child<?>> {

        public EmptyChildHolder() {}

        @Override
        public Optional<Child<?>> put(Child<?> child, Parent<? super Child<?>> parent) {
            return throwNewUnsupportedOperationExceptionImmutable();
        }
        
        @Override
        public Optional<Child<?>> remove(Child<?> child) {
            return throwNewUnsupportedOperationExceptionImmutable();
        }

        @Override
        public Stream<Child<?>> stream() {
            return Stream.empty();
        }

        @Override
        public Child<?> find(String name) throws SpeedmentException {
            throw new SpeedmentException("There is no children in this child holder.");
        }

        @Override
        public Class<Child<?>> getChildClass() {
            @SuppressWarnings("unchecked")
            final Class<Child<?>> result = (Class<Child<?>>) (Class) Child.class;
            return result;
        }
    }

    private static class SingletonChildHolder<T extends Child<?>> implements ChildHolder<T> {

        private final T child;
        private final Class<T> childClass;

        public SingletonChildHolder(T child) {
            this.child = requireNonNull(child);
            @SuppressWarnings("unchecked")
            final Class<T> childClass = (Class<T>) child.getInterfaceMainClass();
            this.childClass = childClass;
        }

        @Override
        public Optional<T> put(T child, Parent<? super T> parent) {
            return throwNewUnsupportedOperationExceptionImmutable();
        }
        
        @Override
        public Optional<T> remove(T child) {
            return throwNewUnsupportedOperationExceptionImmutable();
        }

        @Override
        public Stream<T> stream() {
            return Stream.of(child);
        }

        @Override
        public T find(String name) throws SpeedmentException {
            requireNonNull(name);
            if (name.equals(child.getName())) {
                return child;
            }
            throw thereIsNo(childClass, Parent.class, name).get();
        }

        @Override
        public Class<T> getChildClass() {
            return childClass;
        }

    }

}
