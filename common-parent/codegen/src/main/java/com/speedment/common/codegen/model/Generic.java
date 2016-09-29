/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.codegen.model;

import com.speedment.common.codegen.constant.DefaultType;
import com.speedment.common.codegen.internal.model.GenericImpl;
import com.speedment.common.codegen.model.trait.HasCopy;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

/**
 * A model that represents the generic part of a type declaration in code. A
 * type can have multiple generics.
 *
 * @author Emil Forslund
 * @since  2.0
 */
public interface Generic extends HasCopy<Generic> {

    /**
     * Represents the bound type of this generic. If the generic is bound using
     * the <code>extends</code> keyword it means that the lower bound is a
     * descendant of the upper bound. If the <code>super</code> keyword is used
     * it means that the lower bound is an ancestor of the upper bound. Both
     * bound types also accept identical types.
     */
    enum BoundType {
        EXTENDS, SUPER
    }

    /**
     * Sets the lower bound of this generic.
     * <p>
     * In the following example, <code>T</code> is the lower bound:
     * <pre>
     *     T extends List&lt;String&gt;
     * </pre>
     * <p>
     * If the lower bound is anonymous, the special type
     * {@link DefaultType#WILDCARD} can be used.
     *
     * @param lowerBound the new lower bound
     * @return a reference to this model
     */
    Generic setLowerBound(String lowerBound);

    /**
     * Returns the lower bound of this generic if it exists.
     * <p>
     * In the following example, <code>T</code> is the lower bound:
     * <pre>
     *     T extends List&lt;String&gt;
     * </pre>
     * <p>
     * If the lower bound is anonymous, the special type
     * {@link DefaultType#WILDCARD} can be used.
     *
     * @return the lower bound if such exists
     */
    Optional<String> getLowerBound();

    /**
     * Adds an upper bound to this generic.
     * <p>
     * In the following example, <code>List&lt;String&gt;</code> and
     * <code>Serializable</code> are upper bounds:
     * <pre>
     *     T extends List&lt;String&gt;&amp;Serializable
     * </pre>
     *
     * @param upperBound the new upper bound
     * @return a reference to this model
     */
    default Generic add(Type upperBound) {
        getUpperBounds().add(upperBound);
        return this;
    }

    /**
     * Returns a modifiable list of all upper bounds to this generic.
     * <p>
     * In the following example, <code>List&lt;String&gt;</code> and
     * <code>Serializable</code> are upper bounds:
     * <pre>
     *     T extends List&lt;String&gt;&amp;Serializable
     * </pre>
     *
     * @return the list of upper bounds
     */
    List<Type> getUpperBounds();

    /**
     * Sets the bound type of this generic. If the generic is bound using the
     * <code>extends</code> keyword it means that the lower bound is a
     * descendant of the upper bound. If the <code>super</code> keyword is used
     * it means that the lower bound is an ancestor of the upper bound. Both
     * bound types also accept identical types.
     * <p>
     * Valid input:
     * <ul>
     * <li>{@link BoundType#EXTENDS}
     * <li>{@link BoundType#SUPER}
     * </ul>
     *
     * @param type the new bound type
     * @return a reference to this model
     */
    Generic setBoundType(BoundType type);

    /**
     * Returns the current bound type of this generic. Note that the bound type
     * is irrelevant when there are no upper bounds!
     *
     * @return the bound type
     */
    BoundType getBoundType();

    /**
     * Parses a {@link Type} from the lower bound of this generic. This can be
     * used to reference the generic variable from other contexts.
     *
     * @return a {@link Type} representing this generic variable
     */
    Type asType();

    /**
     * Creates a new instance implementing this interface by using the default
     * implementation.
     *
     * @return the new instance
     */
    static Generic of() {
        return new GenericImpl();
    }
    
    /**
     * Creates a new instance implementing this interface by using the default
     * implementation.
     *
     * @param lowerBound  the lower bound, example: {@code "T"}
     * @return the new instance
     */
    static Generic of(String lowerBound) {
        return of().setLowerBound(lowerBound);
    }
    
    /**
     * Creates a new instance implementing this interface by using the default
     * implementation.
     *
     * @param upperBound the upper bound, example: {@code "String"}
     * @return the new instance
     */
    static Generic of(Type upperBound) {
        return of().add(upperBound);
    }
}