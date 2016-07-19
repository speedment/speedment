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

/**
 * A token that is used for a specific {@code Class} to avoid loss of 
 * information due to erasure.
 * 
 * @author  Emil Forslund
 * @author  Simon Jonasson
 * @since   3.0.0
 */
public interface TypeToken {
    
    /**
     * The absolute name of the class described by this type token.
     * <p>
     * This can for an example be: {@code java.lang.String}
     * 
     * @return  the absolute name of the described class
     */
    String getTypeName();
    
    /**
     * Returns true if this also implements the 
     * {@link ArrayTypeToken} interface.
     * 
     * @return  {@code true} if this is an array, else {@code false}
     */
    boolean isArray();
    
    /**
     * Returns true if this also implements the 
     * {@link PrimitiveTypeToken} interface.
     * 
     * @return  {@code true} if this is a primitive class, else {@code false}
     */
    boolean isPrimitive();
    
    /**
     * Returns true if this also implements the 
     * {@link GenericTypeToken} interface.
     * 
     * @return  {@code true} if this is a generic type, else {@code false}
     */
    boolean isGeneric();
    
    /**
     * Returns true if this represent an enumerating type with a 
     * fixed set of alternatives.
     * 
     * @return  {@code true} if this is an enum type, else {@code false}
     */
    boolean isEnum();

    /**
     * Attempts to produce a {@code Class} instance for the represented type,
     * throwing an exception if it could not be done.
     * 
     * @return  the {@code Class} represented by this token
     * 
     * @throws ClassNotFoundException  if it could not be found
     */
    default Class<?> asClass() throws ClassNotFoundException {
        return (Class<?>) Class.forName(getTypeName());
    }
}
