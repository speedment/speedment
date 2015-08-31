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
package com.speedment.config.aspects;

import com.speedment.config.Node;
import com.speedment.annotation.Api;
import com.speedment.internal.core.config.ChildHolder;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * This interface should be implemented by all {@link Node Nodes} that is not
 * a leaf in the database model tree. A node can be both a {@link Child}
 * and a <code>Parent</code> at the same time if it is located somewhere in the
 * middle of the tree.
 * 
 * @author     Emil Forslund
 * @param <C>  the type of the children
 * @see        Child
 */
@Api(version = "2.1")
public interface Parent<C extends Child<?>> extends Node {
    
    /**
     * {@inheritDoc}
     * 
     * @return        always <code>true</code> since this is a parent
     * @see           Child
     */
    @Override
    default boolean isParentInterface() {
        return true;
    }

    /**
     * {@inheritDoc}
     * 
     * @return        this entity wrapped in an <code>Optional</code>
     * @see           Child
     */
    @Override
    default Optional<Parent<?>> asParent() {
        return Optional.of(this);
    }

    /**
     * Returns a {@link ChildHolder} that contains all the children of this
     * node. The <code>ChildHolder</code> should be safe to make changes to.
     * 
     * @return            the children
     */
    ChildHolder getChildren();

    /**
     * Add the node as a child to this node. This will set this node as the
     * parent of the specified node automatically. If a parent is already set
     * in the specified node, an <code>IllegalStateException</code> will be 
     * thrown.
     * 
     * If a child with he same name (as returned by {@link Nameable#getName()})
     * already existed, it is removed and returned by this method. Else, the
     * <code>Optional</code> returned will be <code>empty</code>.
     * 
     * @param child       The child to add
     * @return            If a child had to be removed it will be returned, else
     *                    <code>empty</code>
     * 
     * @see               ChildHolder
     */
    @SuppressWarnings("unchecked")
    Optional<C> add(final C child);

    /**
     * Returns a <code>Stream</code> over all the children of this node. The
     * elements in the stream is sorted primarily on (i) the class name
     * of the type returned by {@link Child#getInterfaceMainClass()} and 
     * secondly (ii) on the node name returned by {@link Child#getName()}.
     * 
     * @return            a <code>Stream</code> of all children
     */
    @SuppressWarnings("unchecked")
    Stream<? extends C> stream();

    /**
     * Returns a <code>Stream</code> over all the children to this node with
     * the specified interface main class. The inputted class should correspond
     * to the one returned by {@link Child#getInterfaceMainClass()}. The stream
     * will be sorted based on the node name returned by 
     * {@link Child#getName()}.
     * 
     * @param <T>         the type of the children to return
     * @param childClass  the class to search for amongst the children
     * @return            a <code>Stream</code> of children of the specified 
     *                    type
     */
    <T extends C> Stream<T> streamOf(Class<T> childClass);
    
    /**
     * Returns a <code>Stream</code> with this node and all the descendants of 
     * this node. The tree will be traversed using the breadth first approach.
     * 
     * @return            a <code>Stream</code> of all descendants
     */
    Stream<Node> traverse();

    /**
     * Returns a <code>Stream</code> with all the descendants of this node that
     * is of the specified type. The tree will be traversed using the breadth 
     * first approach.
     * 
     * @param <T>         the type of the descendants to return
     * @param childClass  the class to search for amongst the descendants
     * @return            a <code>Stream</code> of all descendants of a type
     */
    <T extends Node> Stream<T> traverseOver(Class<T> childClass);
}