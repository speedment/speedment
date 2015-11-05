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
package com.speedment.internal.core.config.aspects;

import com.speedment.config.aspects.Child;
import com.speedment.config.Node;
import com.speedment.config.aspects.Parent;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.util.Trees;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <C> the type of the children
 */
public interface ParentHelper<C extends Child<?>> extends Parent<C> {

    @SuppressWarnings("unchecked")
    @Override
    default Optional<C> add(final C child) {
        requireNonNull(child);
        return getChildren().put(child, this).map(c -> (C) c);
    }

    @SuppressWarnings("unchecked")
    @Override
    default Stream<? extends C> stream() {
        return getChildren().stream().map(c -> (C) c);
    }

    @Override
    default <T extends C> Stream<T> streamOf(Class<T> childClass) {
        requireNonNull(childClass);
        return getChildren().streamOf(childClass);
    }

    @Override
    default Stream<Node> traverse() {
        final Function<Node, Stream<Node>> traverse = n -> n.asParent()
                .map(p -> p.stream())
                .orElse(Stream.empty())
                .map(Node.class::cast);

        return Trees.traverse(
                this,
                traverse,
                Trees.TraversalOrder.BREADTH_FIRST
        ).map(Node.class::cast);
    }

    @SuppressWarnings("unchecked")
    @Override
    default <T extends Node> Stream<T> traverseOver(Class<T> childClass) {
        requireNonNull(childClass);
        return traverse()
                .filter(t -> childClass.isAssignableFrom(t.getClass()))
                .map(t -> (T) t);
    }

    @Override
    public default <T extends C> T find(Class<T> childClass, String name) throws SpeedmentException {
        return getChildren().find(childClass, name);
    }

}
