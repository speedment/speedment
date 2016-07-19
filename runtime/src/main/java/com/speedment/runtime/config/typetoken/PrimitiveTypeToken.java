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
 * 
 * @author  Emil Forslund
 * @author  Simon Jonasson
 * @since   3.0.0
 */
public interface PrimitiveTypeToken extends TypeToken {
    
    enum Primitive {
        
        BYTE    (byte.class, Byte.class),
        SHORT   (short.class, Short.class),
        INT     (int.class, Integer.class),
        LONG    (long.class, Long.class),
        FLOAT   (float.class, Float.class),
        DOUBLE  (double.class, Double.class),
        BOOLEAN (boolean.class, Boolean.class);

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

    @Override
    default boolean isPrimitive() {
        return true;
    }

    @Override
    default Class<?> asClass() throws ClassNotFoundException {
        return getPrimitiveType().getUnwrapped();
    }
}