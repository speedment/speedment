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
package com.speedment.runtime.config.typetoken;

import java.util.List;

/**
 * A special type of {@link TypeToken} that represent a generic type.
 * 
 * @author  Emil Forslund
 * @author  Simon Jonasson
 * @since   3.0.0
 */
public interface GenericTypeToken extends TypeToken {
    
    /**
     * Returns a list of generic type parameters to this type token. For
     * an example, the type below has two generic tokens:
     * {@code Map<String, Long>}.
     * <p>
     * The returned list can be empty.
     * 
     * @return  list of generic types to this type
     */
    List<GenericToken> getGenericTokens();

    /**
     * {@inheritDoc}
     * <p>
     * Returns {@code true} for all generic types.
     * 
     * @return  always {@code true}
     */
    @Override
    default boolean isGeneric() {
        return true;
    }
    
    /**
     * The inner type of the generic type token that tells if the type
     * variable has a bound or not. Bound type variables also implement
     * {@link BoundGenericToken} and unbound type variables also
     * implement {@link UnboundGenericToken}.
     */
    interface GenericToken {
        
        /**
         * Returns {@code true} if this token has either an 
         * {@code extends} or a {@code super} bound.
         * 
         * @return  {@code true} if bound, else {@code false}
         */
        boolean isBound();
    }

    /**
     * The inner type variable in a generic type statement. This holds
     * the {@link TypeToken} of the generic type.
     */
    interface UnboundGenericToken extends GenericToken {
        
        /**
         * Returns the {@link TypeToken} representing this generic type.
         * 
         * @return  the type token
         */
        TypeToken getTypeToken();

        /**
         * {@inheritDoc}
         * <p>
         * Always {@code false}.
         * 
         * @return  {@code false} for all unbound types
         */
        @Override
        default boolean isBound() {
            return false;
        }
    }

    /**
     * The inner type variable in a generic type statement. This holds
     * the {@link TypeToken} of every upper bound for the generic type.
     */
    interface BoundGenericToken extends GenericToken {
        
        /**
         * The type of bound that this type token has.
         */
        enum BoundType {
            EXTENDS, SUPER
        }

        /**
         * Returns the type of bound that this generic type has. This can 
         * be either {@link BoundType#EXTENDS} or {@link BoundType#SUPER}.
         * 
         * @return  the type of bound
         */
        BoundType getBoundType();
        
        /**
         * Returns a list of the upper bounds for this generic type variable.
         * These are often expressed in code as {@code &}-separated.
         * <p>
         * Example: {@code ? extends Number & Serializable}
         * 
         * @return  list of upper bounds to this generic type
         */
        List<TypeToken> getBounds();

        /**
         * {@inheritDoc}
         * <p>
         * Always {@code true}.
         * 
         * @return  {@code true} for all bound types
         */
        @Override
        default boolean isBound() {
            return true;
        }
    }
}