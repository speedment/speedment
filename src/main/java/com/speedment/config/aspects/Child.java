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
import java.util.Optional;

/**
 * This interface should be implemented by all {@link Node Nodes} that is not
 * the root of the database model tree. A node can be both a <code>Child</code>
 * and a {@link Parent} at the same time if it is located somewhere in the
 * middle of the tree.
 * 
 * @author     Emil Forslund
 * @param <P>  the type of the parent
 * @see        Parent
 */
@Api(version = "2.1")
public interface Child<P extends Parent<?>> extends Node {

    /**
     * Returns the parent node, if any, or else an empty <code>Optional</code>.
     * 
     * @return       the parent
     */
    Optional<P> getParent();
    
    /**
     * Returns the class of the parent. This should be safe to call even if the
     * node has been removed from the tree and {@link #getParent()} returns 
     * empty.
     * 
     * @return        the parent class
     */
    Class<P> getParentInterfaceMainClass();
    
    /**
     * Sets the parent for this node. This method does not do any changes in the
     * parent object, the node will still have to be appended to the parent's
     * list of children.
     * 
     * @param parent  the new parent
     * @see           Parent
     */
    void setParent(Parent<?> parent);
    
    /**
     * Returns whether this node is the root or not. This is equivalent to
     * checking if the parent is missing or not.
     * 
     * @return        <code>true</code> if this is the root
     */
    default boolean isRoot() {
        return !getParent().isPresent();
    }

    /**
     * {@inheritDoc}
     * 
     * @return        always <code>true</code> since this is a child
     * @see           Parent
     */
    @Override
    default boolean isChildInterface() {
        return true;
    }
    
    /**
     * {@inheritDoc}
     * 
     * @return        this entity wrapped in an <code>Optional</code>
     * @see           Parent
     */
    @Override
    default Optional<Child<?>> asChild() {
        return Optional.of(this);
    }
}