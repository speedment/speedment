/*
 * Copyright (c) Emil Forslund, 2016.
 * All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of Emil Forslund and his suppliers, if any. 
 * The intellectual and technical concepts contained herein 
 * are proprietary to Emil Forslund and his suppliers and may 
 * be covered by U.S. and Foreign Patents, patents in process, 
 * and are protected by trade secret or copyright law. 
 * Dissemination of this information or reproduction of this 
 * material is strictly forbidden unless prior written 
 * permission is obtained from Emil Forslund himself.
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