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
 * A special type of {@link TypeToken} that represent the eight types of primitive
 * types in the java language. These can be implemented effectivily using an enum.
 * 
 * @author  Emil Forslund
 * @author  Simon Jonasson
 * @since   3.0.0
 */
public interface PrimitiveTypeToken extends TypeToken {
    
    /**
     * An enum holding the eight primitive types of the java language, along with
     * the class object of both the wrapper and the unwrapped type.
     */
    enum Primitive {
        
        BYTE    (byte.class, Byte.class),
        SHORT   (short.class, Short.class),
        INT     (int.class, Integer.class),
        LONG    (long.class, Long.class),
        FLOAT   (float.class, Float.class),
        DOUBLE  (double.class, Double.class),
        BOOLEAN (boolean.class, Boolean.class),
        CHAR    (char.class, Character.class);

        private final Class<?> unwrapped, wrapper;

        Primitive(Class<?> unwrapped, Class<?> wrapper) {
            this.unwrapped = unwrapped;
            this.wrapper = wrapper;
        }

        public Class<?> getUnwrapped() {
            return unwrapped;
        }
        
        public Class<?> getWrapper() {
            return wrapper;
        }
    }

    /**
     * Returns an enum constant for the specific primitive type 
     * represented by this token.
     * 
     * @return  the enum constant for this primitive
     */
    Primitive getPrimitiveType();

    /**
     * {@inheritDoc}
     * <p>
     * Returns {@code true} for all primitive types.
     * 
     * @return  always {@code true}
     */
    @Override
    default boolean isPrimitive() {
        return true;
    }

    /**
     * {@inheritDoc}
     * <p>
     * All primitive types are comparable, so this method will always
     * return {@code true}.
     * 
     * @return  {@code true} since all primitive types are comparable
     */
    @Override
    public default boolean isComparable() {
        return true;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns the unwrapped type ({@code int} if the type is an integer
     * for an example).
     * 
     * @return  the unwrapped class object for this primitive type token
     */
    @Override
    default Class<?> asClass() throws ClassNotFoundException {
        return getPrimitiveType().getUnwrapped();
    }
}