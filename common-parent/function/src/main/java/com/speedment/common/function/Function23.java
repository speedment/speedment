/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.function;

/**
 * Represents a function that accepts 23 arguments and produces a result. This
 * is the 23-arity specialization of {@link java.util.function.Function}.
 * <p>
 * This class has been generated.
 * 
 * @param <T0>  the type of the first argument to the function
 * @param <T1>  the type of the second argument to the function
 * @param <T2>  the type of the third argument to the function
 * @param <T3>  the type of the fourth argument to the function
 * @param <T4>  the type of the fifth argument to the function
 * @param <T5>  the type of the sixth argument to the function
 * @param <T6>  the type of the seventh argument to the function
 * @param <T7>  the type of the eighth argument to the function
 * @param <T8>  the type of the ninth argument to the function
 * @param <T9>  the type of the tenth argument to the function
 * @param <T10> the type of the eleventh argument to the function
 * @param <T11> the type of the 12:th argument to the function
 * @param <T12> the type of the 13:th argument to the function
 * @param <T13> the type of the 14:th argument to the function
 * @param <T14> the type of the 15:th argument to the function
 * @param <T15> the type of the 16:th argument to the function
 * @param <T16> the type of the 17:th argument to the function
 * @param <T17> the type of the 18:th argument to the function
 * @param <T18> the type of the 19:th argument to the function
 * @param <T19> the type of the 20:th argument to the function
 * @param <T20> the type of the 21:th argument to the function
 * @param <T21> the type of the 22:th argument to the function
 * @param <T22> the type of the 23:th argument to the function
 * @param <R>   the type of the result of the function
 * 
 * @author Per Minborg
 * 
 * @see java.util.function.Function
 */
@FunctionalInterface
public interface Function23<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, R> {
    
    /**
     * Applies this function to the given arguments.
     * 
     * @param t0  the first function argument
     * @param t1  the second function argument
     * @param t2  the third function argument
     * @param t3  the fourth function argument
     * @param t4  the fifth function argument
     * @param t5  the sixth function argument
     * @param t6  the seventh function argument
     * @param t7  the eighth function argument
     * @param t8  the ninth function argument
     * @param t9  the tenth function argument
     * @param t10 the eleventh function argument
     * @param t11 the 12:th function argument
     * @param t12 the 13:th function argument
     * @param t13 the 14:th function argument
     * @param t14 the 15:th function argument
     * @param t15 the 16:th function argument
     * @param t16 the 17:th function argument
     * @param t17 the 18:th function argument
     * @param t18 the 19:th function argument
     * @param t19 the 20:th function argument
     * @param t20 the 21:th function argument
     * @param t21 the 22:th function argument
     * @param t22 the 23:th function argument
     * @return    the function result
     */
    R apply(
    T0 t0,
            T1 t1,
            T2 t2,
            T3 t3,
            T4 t4,
            T5 t5,
            T6 t6,
            T7 t7,
            T8 t8,
            T9 t9,
            T10 t10,
            T11 t11,
            T12 t12,
            T13 t13,
            T14 t14,
            T15 t15,
            T16 t16,
            T17 t17,
            T18 t18,
            T19 t19,
            T20 t20,
            T21 t21,
            T22 t22);
}