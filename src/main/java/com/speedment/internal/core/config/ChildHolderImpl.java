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
import com.speedment.internal.util.Cast;
import com.speedment.internal.util.JavaLanguage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import static java.util.Objects.requireNonNull;

/**
 * A container class for children to a node in the database model tree.
 *
 * @author Emil Forslund
 * @param <T> Child type
 * @see com.speedment.config.Node
 */
public final class ChildHolderImpl<T extends Child<?>> implements ChildHolder<T> {

    private final List<T> children; // cannot be a Map because children may get a new name after they are added
    private final AtomicInteger ordinalNumber;
    private final AtomicInteger nameNumber;
    private final Class<T> childClass;

//    /**
//     * ChildHolder constructor. This will use the name of the qualified name of
//     * the children's implementation class to determine the order.
//     */
//    public ChildHolderImpl() {
//        this(CLASS_COMPARATOR);
//    }
    /**
     * ChildHolder constructor.
     *
     * // * @param comparator the comparator to use when determining the order
     * of the // * children.
     *
     * @param childClass type
     */
    public ChildHolderImpl(Class<T> childClass/*Comparator<Class<?>> comparator*/) {
        children = new ArrayList<>();
        ordinalNumber = new AtomicInteger(Ordinable.ORDINAL_FIRST);
        nameNumber = new AtomicInteger(Nameable.NAMEABLE_FIRST);
        this.childClass = childClass;
    }

    @Override
    public Optional<T> put(T child, Parent<? super T> parent) {
        requireNonNull(child);
        requireNonNull(parent);
        child.getParent().ifPresent(c -> {
            throw new IllegalStateException(
                    "It is illegal to add a child that already has a parent. child="
                    + child + ", parent=" + child.getParent().get()
            );
        });
        
        child.setParent(parent);

        if (child.isOrdinable()) {
            Ordinable ordinable = Cast.castOrFail(child, Ordinable.class);
            if (ordinable.getOrdinalPosition() == Ordinable.UNSET) {
                ordinable.setOrdinalPosition(ordinalNumber.getAndIncrement());
            }
        }

        if (!child.hasName()) {
            child.setName(
                    JavaLanguage.toUnderscoreSeparated(
                            child.getInterfaceMainClass().getSimpleName()
                    ) + "_" + nameNumber.getAndIncrement()
            );
        }

        final String name = child.getName();
        final Optional<T> existing = childByName(name);
        if (existing.isPresent()) {
            children.removeIf(c -> c.getName().equals(name));
        }
        children.add(child);
        return existing;
    }

    @Override
    public Stream<T> stream() {
        return children.stream().sorted();
    }

//    @SuppressWarnings("unchecked")
//    @Override
//    public <C extends Child<?>> Stream<C> streamOf(Class<C> clazz) {
//        requireNonNull(clazz);
//        return children.getOrDefault(clazz, Collections.emptyMap())
//                .values().stream().map(c -> (C) c).sorted();
//    }
    @Override
    public T find(/*Class<C> childClass,*/String name) throws SpeedmentException {
        //Objects.requireNonNull(childClass);
        Objects.requireNonNull(name);
        return childByName(name)
                .orElseThrow(thereIsNo(Child.class, this.getClass(), name));
    }

    private Optional<T> childByName(String name) {
        return children.stream()
                .filter(c->name.equals(c.getName()))
                .findAny();
    }
    
    @Override
    public Class<T> getChildClass() {
        return childClass;
    }

}
