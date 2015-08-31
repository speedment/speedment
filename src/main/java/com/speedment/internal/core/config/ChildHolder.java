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

import com.speedment.config.aspects.Parent;
import com.speedment.config.aspects.Nameable;
import com.speedment.config.aspects.Ordinable;
import com.speedment.config.aspects.Child;
import com.speedment.internal.util.JavaLanguage;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * A container class for children to a node in the database model tree.
 *
 * @author Emil Forslund
 * @see com.speedment.config.Node
 */
public final class ChildHolder {

    private final Map<Class<?>, Map<String, Child<?>>> children;
    private final Map<Class<?>, AtomicInteger> ordinalNumbers;
    private final Map<Class<?>, AtomicInteger> nameNumbers;

    /**
     * A comparator that uses the qualified class name to compare classes.
     */
    private final static Comparator<Class<?>> CLASS_COMPARATOR = (a, b)
        -> Objects.compare(a.getName(), b.getName(), Comparator.naturalOrder());

    /**
     * ChildHolder constructor. This will use the name of the qualified name of
     * the children's implementation class to determine the order.
     */
    public ChildHolder() {
        this(CLASS_COMPARATOR);
    }

    /**
     * ChildHolder constructor.
     *
     * @param comparator the comparator to use when determining the order of the
     * children.
     */
    public ChildHolder(Comparator<Class<?>> comparator) {
        children = new ConcurrentSkipListMap<>(requireNonNull(comparator));
        ordinalNumbers = new ConcurrentHashMap<>();
        nameNumbers = new ConcurrentHashMap<>();
    }

    /**
     * Put the specified child into this holder, also setting its parent to the
     * specified one. If the parent of the child is already set, an
     * <code>IllegalStateException</code> will be thrown. The children are
     * stored mapped using their names (as returned by
     * {@link Nameable#getName()} as keys. If a node already exist with that
     * name, it will be removed from the map and returned.
     *
     * @param child the child to add.
     * @param parent the parent set in the child.
     * @return the old value if a child with that exact name already existed or
     * <code>empty</code> otherwise.
     * @see Nameable
     * @see Ordinable
     */
    public Optional<Child<?>> put(Child<?> child, Parent<?> parent) {
        requireNonNull(child);
        requireNonNull(parent);
        child.getParent().ifPresent(c -> {
            throw new IllegalStateException(
                "It is illegal to add a child that already has a parent. child="
                + child + ", parent=" + child.getParent().get()
            );
        });

        child.setParent(parent);

        Optional.of(child)
            .filter(c -> c.isOrdinable())
            .map(c -> (Ordinable) c)
            .filter(o -> o.getOrdinalPosition() == Ordinable.UNSET)
            .ifPresent(o -> {
                o.setOrdinalPosition(ordinalNumbers.computeIfAbsent(
                    child.getInterfaceMainClass(),
                    m -> new AtomicInteger(Ordinable.ORDINAL_FIRST)
                ).getAndIncrement());
            });

        Optional.of(child)
            .filter(c -> !c.hasName())
            .ifPresent(c -> c.setName(
                JavaLanguage.toUnderscoreSeparated(
                    c.getInterfaceMainClass().getSimpleName()) + "_"
                + nameNumbers.computeIfAbsent(
                    child.getInterfaceMainClass(),
                    m -> new AtomicInteger(Nameable.NAMEABLE_FIRST)
                ).getAndIncrement()
            ));

        return Optional.ofNullable(children.computeIfAbsent(
            child.getInterfaceMainClass(),
            m -> new ConcurrentSkipListMap<>()
        ).put(child.getName(), child));
    }

    /**
     * Returns a <code>Stream</code> over all the children in this holder. The
     * elements in the stream is sorted primarily on (i) the class name of the
     * type returned by {@link Child#getInterfaceMainClass()} and secondly (ii)
     * on the node name returned by {@link Child#getName()}.
     *
     * @return a stream of all children
     * @see Nameable
     */
    public Stream<Child<?>> stream() {
        return children.entrySet().stream()
            .map(Map.Entry::getValue)
            .flatMap(i -> i.values().stream().sorted());
    }

    /**
     * Returns a <code>Stream</code> over all the children in this holder with
     * the specified interface main class. The inputted class should correspond
     * to the one returned by {@link Child#getInterfaceMainClass()}. The stream
     * will be sorted based on the node name returned by
     * {@link Child#getName()}.
     *
     * @param <C> the type of the children to return
     * @param clazz the class to search for amongst the children
     * @return a stream of children of the specified type
     */
    @SuppressWarnings("unchecked")
    public <C extends Child<?>> Stream<C> streamOf(Class<C> clazz) {
        requireNonNull(clazz);
        return children.getOrDefault(clazz, Collections.emptyMap())
            .values().stream().map(c -> (C) c).sorted();
    }
}
