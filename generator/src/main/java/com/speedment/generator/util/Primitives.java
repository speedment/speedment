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
package com.speedment.generator.util;

import java.lang.reflect.Type;

/**
 * Utility methods for working with the class-object of primitive types.
 * 
 * @author  Emil Forslund
 * @since   3.0.1
 */
public final class Primitives {

    /**
     * If the specified type is a primitive class, the corresponding wrapper
     * type is returned. Otherwise, the input is returned. This is useful for
     * making sure a parameter is not a primitive.
     * 
     * @param type  the type to check
     * @return      the input or corresponding wrapper type
     */
    public static Type orWrapper(Type type) {
        if      (type == byte.class)    return Byte.class;
        else if (type == short.class)   return Short.class;
        else if (type == int.class)     return Integer.class;
        else if (type == long.class)    return Long.class;
        else if (type == float.class)   return Float.class;
        else if (type == double.class)  return Double.class;
        else if (type == boolean.class) return Boolean.class;
        else if (type == char.class)    return Character.class;
        else return type;
    }
    
    private Primitives() {}
}