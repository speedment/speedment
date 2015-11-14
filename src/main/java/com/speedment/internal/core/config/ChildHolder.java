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
import java.util.Optional;
import java.util.stream.Stream;

/**
 * A container class for children to a node in the database model tree.
 *
 * @author Emil Forslund
 * @param <T> the child type
 * @see com.speedment.config.Node
 */
public interface ChildHolder<T extends Child<?>> {

    /**
     * Put the specified child into this holder, also setting its parent to the
     * specified one. If the parent of the child is already set, an
     * <code>IllegalStateException</code> will be thrown. The children are
     * stored mapped using their names (as returned by
     * {@link Nameable#getName()} as keys. If a node already exist with that
     * name, it will be removed from the map and returned.
     *
     * @param child the child to add.
     * @param parent the parent set in the child.
     * @return the old value if a child with that exact name already existed or
     * <code>empty</code> otherwise.
     * @see Nameable
     * @see Ordinable
     */
    Optional<T> put(T child, Parent<? super T> parent);

    /**
     * Returns a <code>Stream</code> over all the children in this holder. The
     * elements in the stream is sorted primarily on (i) the class name of the
     * type returned by {@link Child#getInterfaceMainClass()} and secondly (ii)
     * on the node name returned by {@link Child#getName()}.
     *
     * @return a stream of all children
     * @see Nameable
     */
    Stream<T> stream();

    /**
     * Returns a child of the given childClass and with the given name. If no
     * such child is present, a {@link SpeedmentException} is thrown. An
     * implementing class may override this default method to provide a more
     * optimized implementation, for example by using a look up map.
     *
     * @param name the name of the child
     * @return a child of the given childClass and with the given name
     * @throws SpeedmentException if no such child is present
     */
     T find(/*Class<C> childClass,*/ String name) throws SpeedmentException;

     Class<T> getChildClass();
     
}
