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

import java.util.Optional;

/**
 *
 * @author Emil Forslund
 * @param <P> Parent type
 */
public interface Child<P extends Parent<?>> extends Node {

    /**
     * Returns the parent node, if any, or else an empty <code>Optional</code>.
     * 
     * @return       the parent.
     */
    Optional<P> getParent();
    
    Class<P> getParentInterfaceMainClass();
    
    /**
     * Sets the parent for this node. This method does not do any changes in the
     * parent object, the node will still have to be appended to the parent's
     * list of children.
     * 
     * @param parent  the new parent.
     * @see           Child
     */
    void setParent(Parent<?> parent);
    
    default boolean isRoot() {
        return !getParent().isPresent();
    }

    @Override
    default boolean isChildInterface() {
        return true;
    }
    
    @Override
    default Optional<Child<?>> asChild() {
        return Optional.of(this);
    }
}
