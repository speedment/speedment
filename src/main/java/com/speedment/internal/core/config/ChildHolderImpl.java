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
import com.speedment.exception.SpeedmentException;
import static com.speedment.internal.core.config.utils.ConfigUtil.thereIsNo;
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
public final class ChildHolderImpl implements ChildHolder {

    private final Map<Class<?>, Map<String, Child<?>>> children;
    private final Map<Class<?>, AtomicInteger> ordinalNumbers;
    private final Map<Class<?>, AtomicInteger> nameNumbers;

    /**
     * ChildHolder constructor. This will use the name of the qualified name of
     * the children's implementation class to determine the order.
     */
    public ChildHolderImpl() {
        this(CLASS_COMPARATOR);
    }

    /**
     * ChildHolder constructor.
     *
     * @param comparator the comparator to use when determining the order of the
     * children.
     */
    public ChildHolderImpl(Comparator<Class<?>> comparator) {
        children = new ConcurrentSkipListMap<>(requireNonNull(comparator));
        ordinalNumbers = new ConcurrentHashMap<>();
        nameNumbers = new ConcurrentHashMap<>();
    }

    @Override
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

    @Override
    public Stream<Child<?>> stream() {
        return children.entrySet().stream()
                .map(Map.Entry::getValue)
                .flatMap(i -> i.values().stream().sorted());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <C extends Child<?>> Stream<C> streamOf(Class<C> clazz) {
        requireNonNull(clazz);
        return children.getOrDefault(clazz, Collections.emptyMap())
                .values().stream().map(c -> (C) c).sorted();
    }

    @Override
    public <C extends Child<?>> C find(Class<C> childClass, String name) throws SpeedmentException {
        Objects.requireNonNull(childClass);
        Objects.requireNonNull(name);
        return streamOf(childClass)
                .filter(c -> name.equals(c.getName()))
                .findAny()
                .orElseThrow(thereIsNo(childClass, this.getClass(), name));
    }

}
