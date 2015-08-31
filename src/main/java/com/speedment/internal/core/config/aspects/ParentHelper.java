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
import com.speedment.config.aspects.Nameable;
import com.speedment.config.aspects.Parent;
import com.speedment.internal.core.config.ChildHolder;
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

    /**
     * Add the node as a child to this node. This will set this node as the
     * parent of the specified node automatically. If a parent is already set in
     * the specified node, an <code>IllegalStateException</code> will be thrown.
     *
     * If a child with he same name (as returned by {@link Nameable#getName()})
     * already existed, it is removed and returned by this method. Else, the
     * <code>Optional</code> returned will be <code>empty</code>.
     *
     * @param child The child to add
     * @return If a child had to be removed it will be returned, else
     * <code>empty</code>
     *
     * @see ChildHolder
     */
    @SuppressWarnings("unchecked")
    @Override
    default Optional<C> add(final C child) {
        requireNonNull(child);
        return getChildren().put(child, this).map(c -> (C) c);
    }

    /**
     * Returns a <code>Stream</code> over all the children of this node. The
     * elements in the stream is sorted primarily on (i) the class name of the
     * type returned by {@link Child#getInterfaceMainClass()} and secondly (ii)
     * on the node name returned by {@link Child#getName()}.
     *
     * @return a <code>Stream</code> of all children
     */
    @SuppressWarnings("unchecked")
    @Override
    default Stream<? extends C> stream() {
        return getChildren().stream().map(c -> (C) c);
    }

    /**
     * Returns a <code>Stream</code> over all the children to this node with the
     * specified interface main class. The inputted class should correspond to
     * the one returned by {@link Child#getInterfaceMainClass()}. The stream
     * will be sorted based on the node name returned by
     * {@link Child#getName()}.
     *
     * @param <T> the type of the children to return
     * @param childClass the class to search for amongst the children
     * @return a <code>Stream</code> of children of the specified type
     */
    @Override
    default <T extends C> Stream<T> streamOf(Class<T> childClass) {
        requireNonNull(childClass);
        return getChildren().streamOf(childClass);
    }

    /**
     * Returns a <code>Stream</code> with this node and all the descendants of
     * this node. The tree will be traversed using the breadth first approach.
     *
     * @return a <code>Stream</code> of all descendants
     */
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

    /**
     * Returns a <code>Stream</code> with all the descendants of this node that
     * is of the specified type. The tree will be traversed using the breadth
     * first approach.
     *
     * @param <T> the type of the descendants to return
     * @param childClass the class to search for amongst the descendants
     * @return a <code>Stream</code> of all descendants of a type
     */
    @SuppressWarnings("unchecked")
    @Override
    default <T extends Node> Stream<T> traverseOver(Class<T> childClass) {
        requireNonNull(childClass);
        return traverse()
            .filter(t -> childClass.isAssignableFrom(t.getClass()))
            .map(t -> (T) t);
    }
}
