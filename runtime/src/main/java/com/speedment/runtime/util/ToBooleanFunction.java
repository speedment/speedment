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

package com.speedment.runtime.util;

/**
 * A specialized function for {@code boolean} types.
 * 
 * @param <T> the type of the input to the function
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
@FunctionalInterface
public interface ToBooleanFunction<T> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    boolean applyAsBoolean(T value);
}
