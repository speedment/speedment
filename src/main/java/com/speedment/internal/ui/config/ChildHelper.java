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
package com.speedment.internal.ui.config;

import com.speedment.config.Node;
import com.speedment.config.aspects.Child;
import com.speedment.config.aspects.Parent;
import com.speedment.internal.util.Trees;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Function;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 * @param <THIS>    the type of this node
 * @param <PARENT>  the type of the parent node
 */
public interface ChildHelper<THIS extends Node, PARENT extends Parent<?>> extends Child<PARENT> {
    
    @Override
    @SuppressWarnings("unchecked")
    default Stream<? extends Parent<?>> ancestors() {
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

    @Override
    @SuppressWarnings("unchecked")
    default <E extends Node> Optional<E> ancestor(Class<E> clazz) {
        return ancestors().filter(a -> clazz.isAssignableFrom(a.getClass())).map(n -> (E) n).findAny();
    }

    @Override
    default <T extends Parent<?>> String getRelativeName(Class<T> from, Function<String, String> nameMapper) {
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
}