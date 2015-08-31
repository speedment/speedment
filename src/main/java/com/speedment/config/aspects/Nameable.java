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

import com.speedment.annotation.Api;

/**
 * A trait-like interface for nodes that have a name.
 * 
 * @author Emil Forslund
 */
@Api(version = "2.1")
public interface Nameable {

    final int NAMEABLE_FIRST = 1;

    /**
     * Sets the name of this node.
     * 
     * @param name the new name
     */
    void setName(String name);

    /**
     * Returns the name of this node.
     * 
     * @return the name
     */
    String getName();

    /**
     * Compares the name of this node to the name of the specified node in a way
     * that satisfies the {@link Comparable#compareTo(java.lang.Object) compareTo(Nameable)} interface. This
     * can be useful to avoid collisions if an implementation class has several 
     * comparable traits.
     * 
     * @param that  The node to compare names with
     * @return      0 if the names are equal; a negative number that this name
     *              should come before that name and a positive number that this
     *              name should come after that name.
     */
    default int compareNames(Nameable that) {
        return getName().compareTo(that.getName());
    }

    /**
     * Returns whether or not this node has been given a name. To satisfy this
     * method, the string returned by {@link #getName()} must be non-null and
     * non-empty.
     * 
     * @return      <code>true</code> if this node has a name. 
     *              Else <code>false</code>.
     */
    default boolean hasName() {
        final String name = getName();
        return name != null && !name.isEmpty();
    }
}