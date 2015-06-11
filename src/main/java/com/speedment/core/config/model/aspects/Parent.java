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
package com.speedment.core.config.model.aspects;

import com.speedment.core.config.model.impl.ChildHolder;
import com.speedment.util.Trees;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public interface Parent<C extends Child<?>> extends Node {
    ChildHolder getChildren();
    
    @SuppressWarnings("unchecked")
    default Optional<C> add(final C child) {
        return getChildren().put(this, child).map(c -> (C) c);
    }

    @SuppressWarnings("unchecked")
    default Optional<C> remove(final C child) {
        return getChildren().remove(this, child).map(c -> (C) c);
    }

    default boolean contains(final C child) {
        return getChildren().contains(child);
    }

    @SuppressWarnings("unchecked")
    default Stream<? extends C> stream() {
        return getChildren().stream().map(c -> (C) c);
    }
    
    default <T extends C> Stream<T> streamOf(Class<T> childClass) {
        return getChildren().streamOf(childClass);
    }

    @SuppressWarnings("unchecked")
    default <T extends Node> Stream<T> traversalOf(Class<T> childClass) {
        final Function<Node, Stream<Node>> traverse = n -> n.asParent()
            .map(p -> p.stream())
            .orElse(Stream.empty())
            .map(e -> (Node) e);
        
        return Trees.traverse(
            this,
            traverse, 
            Trees.TraversalOrder.BREADTH_FIRST
        ).filter(t -> childClass.isAssignableFrom(
            t.getInterfaceMainClass()
        )).map(t -> (T) t);
    }

    default boolean isEmpty() {
        return getChildren().isEmpty();
    }
    
    default long size() {
        return getChildren().size();
    }
    
    @Override
    default boolean isParentInterface() {
        return true;
    }
    
    @Override
    default Optional<Parent<?>> asParent() {
        return Optional.of(this);
    }
}