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
package com.speedment.runtime.compute.internal.expression;

import static java.util.Objects.requireNonNull;

import com.speedment.runtime.compute.ToByte;
import com.speedment.runtime.compute.ToDouble;
import com.speedment.runtime.compute.ToFloat;
import com.speedment.runtime.compute.ToInt;
import com.speedment.runtime.compute.ToLong;
import com.speedment.runtime.compute.ToShort;
import com.speedment.runtime.compute.expression.BinaryExpression;
import com.speedment.runtime.compute.expression.BinaryObjExpression;
import com.speedment.runtime.compute.expression.Expression;

import java.util.Objects;

/**
 * Utility class for creating expressions that computes the result of a wrapped
 * expression divided by either a constant or another expression.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class DivideUtil {
    
    ////////////////////////////////////////////////////////////////////////////
    //                                 ToByte                                 //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> byteDivideInt(ToByte<T> first, int second) {
        return new DivideObjToDouble<T, ToByte<T>, Integer>(first) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsByte(object)
                    / (double) second;
            }

            @Override
            public Integer second() {
                return second;
            }
        };
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> byteDivideLong(ToByte<T> first, long second) {
        return new DivideObjToDouble<T, ToByte<T>, Long>(first) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsByte(object)
                    / (double) second;
            }

            @Override
            public Long second() {
                return second;
            }
        };
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> byteDivideDouble(ToByte<T> first, double second) {
        return new DivideObjToDouble<T, ToByte<T>, Double>(first) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsByte(object) / second;
            }

            @Override
            public Double second() {
                return second;
            }
        };
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> byteDivideInt(ToByte<T> first, ToInt<T> second) {
        return new DivideToDouble<T, ToByte<T>, ToInt<T>>(first, second) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsByte(object)
                    / (double) this.second.applyAsInt(object);
            }
        };
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> byteDivideLong(ToByte<T> first, ToLong<T> second) {
        return new DivideToDouble<T, ToByte<T>, ToLong<T>>(first, second) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsByte(object)
                    / (double) this.second.applyAsLong(object);
            }
        };
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> byteDivideDouble(ToByte<T> first, ToDouble<T> second) {
        return new DivideToDouble<T, ToByte<T>, ToDouble<T>>(first, second) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsByte(object)
                    / this.second.applyAsDouble(object);
            }
        };
    }

    ////////////////////////////////////////////////////////////////////////////
    //                                 ToShort                                //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> shortDivideInt(ToShort<T> first, int second) {
        return new DivideObjToDouble<T, ToShort<T>, Integer>(first) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsShort(object)
                    / (double) second;
            }

            @Override
            public Integer second() {
                return second;
            }
        };
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> shortDivideLong(ToShort<T> first, long second) {
        return new DivideObjToDouble<T, ToShort<T>, Long>(first) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsShort(object)
                    / (double) second;
            }

            @Override
            public Long second() {
                return second;
            }
        };
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> shortDivideDouble(ToShort<T> first, double second) {
        return new DivideObjToDouble<T, ToShort<T>, Double>(first) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsShort(object) / second;
            }

            @Override
            public Double second() {
                return second;
            }
        };
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> shortDivideInt(ToShort<T> first, ToInt<T> second) {
        return new DivideToDouble<T, ToShort<T>, ToInt<T>>(first, second) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsShort(object)
                    / (double) this.second.applyAsInt(object);
            }
        };
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> shortDivideLong(ToShort<T> first, ToLong<T> second) {
        return new DivideToDouble<T, ToShort<T>, ToLong<T>>(first, second) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsShort(object)
                    / (double) this.second.applyAsLong(object);
            }
        };
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> shortDivideDouble(ToShort<T> first, ToDouble<T> second) {
        return new DivideToDouble<T, ToShort<T>, ToDouble<T>>(first, second) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsShort(object)
                    / this.second.applyAsDouble(object);
            }
        };
    }

    ////////////////////////////////////////////////////////////////////////////
    //                                 ToInt                                  //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> intDivideInt(ToInt<T> first, int second) {
        return new DivideObjToDouble<T, ToInt<T>, Integer>(first) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsInt(object)
                    / (double) second;
            }

            @Override
            public Integer second() {
                return second;
            }
        };
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> intDivideLong(ToInt<T> first, long second) {
        return new DivideObjToDouble<T, ToInt<T>, Long>(first) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsInt(object)
                    / (double) second;
            }

            @Override
            public Long second() {
                return second;
            }
        };
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> intDivideDouble(ToInt<T> first, double second) {
        return new DivideObjToDouble<T, ToInt<T>, Double>(first) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsInt(object) / second;
            }

            @Override
            public Double second() {
                return second;
            }
        };
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> intDivideInt(ToInt<T> first, ToInt<T> second) {
        return new DivideToDouble<T, ToInt<T>, ToInt<T>>(first, second) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsInt(object)
                    / (double) this.second.applyAsInt(object);
            }
        };
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> intDivideLong(ToInt<T> first, ToLong<T> second) {
        return new DivideToDouble<T, ToInt<T>, ToLong<T>>(first, second) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsInt(object)
                    / (double) this.second.applyAsLong(object);
            }
        };
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> intDivideDouble(ToInt<T> first, ToDouble<T> second) {
        return new DivideToDouble<T, ToInt<T>, ToDouble<T>>(first, second) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsInt(object)
                    / this.second.applyAsDouble(object);
            }
        };
    }

    ////////////////////////////////////////////////////////////////////////////
    //                                 ToLong                                 //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> longDivideInt(ToLong<T> first, int second) {
        return new DivideObjToDouble<T, ToLong<T>, Integer>(first) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsLong(object)
                    / (double) second;
            }

            @Override
            public Integer second() {
                return second;
            }
        };
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> longDivideLong(ToLong<T> first, long second) {
        return new DivideObjToDouble<T, ToLong<T>, Long>(first) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsLong(object)
                    / (double) second;
            }

            @Override
            public Long second() {
                return second;
            }
        };
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> longDivideDouble(ToLong<T> first, double second) {
        return new DivideObjToDouble<T, ToLong<T>, Double>(first) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsLong(object) / second;
            }

            @Override
            public Double second() {
                return second;
            }
        };
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> longDivideInt(ToLong<T> first, ToInt<T> second) {
        return new DivideToDouble<T, ToLong<T>, ToInt<T>>(first, second) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsLong(object)
                    / (double) this.second.applyAsInt(object);
            }
        };
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> longDivideLong(ToLong<T> first, ToLong<T> second) {
        return new DivideToDouble<T, ToLong<T>, ToLong<T>>(first, second) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsLong(object)
                    / (double) this.second.applyAsLong(object);
            }
        };
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> longDivideDouble(ToLong<T> first, ToDouble<T> second) {
        return new DivideToDouble<T, ToLong<T>, ToDouble<T>>(first, second) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsLong(object)
                    / this.second.applyAsDouble(object);
            }
        };
    }

    ////////////////////////////////////////////////////////////////////////////
    //                                 ToFloat                                //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> floatDivideInt(ToFloat<T> first, int second) {
        return new DivideObjToDouble<T, ToFloat<T>, Integer>(first) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsFloat(object)
                    / (double) second;
            }

            @Override
            public Integer second() {
                return second;
            }
        };
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> floatDivideLong(ToFloat<T> first, long second) {
        return new DivideObjToDouble<T, ToFloat<T>, Long>(first) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsFloat(object)
                    / (double) second;
            }

            @Override
            public Long second() {
                return second;
            }
        };
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> floatDivideDouble(ToFloat<T> first, double second) {
        return new DivideObjToDouble<T, ToFloat<T>, Double>(first) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsFloat(object) / second;
            }

            @Override
            public Double second() {
                return second;
            }
        };
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> floatDivideInt(ToFloat<T> first, ToInt<T> second) {
        return new DivideToDouble<T, ToFloat<T>, ToInt<T>>(first, second) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsFloat(object)
                    / (double) this.second.applyAsInt(object);
            }
        };
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> floatDivideLong(ToFloat<T> first, ToLong<T> second) {
        return new DivideToDouble<T, ToFloat<T>, ToLong<T>>(first, second) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsFloat(object)
                    / (double) this.second.applyAsLong(object);
            }
        };
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> floatDivideDouble(ToFloat<T> first, ToDouble<T> second) {
        return new DivideToDouble<T, ToFloat<T>, ToDouble<T>>(first, second) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsFloat(object)
                    / this.second.applyAsDouble(object);
            }
        };
    }

    ////////////////////////////////////////////////////////////////////////////
    //                                 ToDouble                               //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> doubleDivideInt(ToDouble<T> first, int second) {
        return new DivideObjToDouble<T, ToDouble<T>, Integer>(first) {
            @Override
            public double applyAsDouble(T object) {
                return this.first.applyAsDouble(object)
                    / (double) second;
            }

            @Override
            public Integer second() {
                return second;
            }
        };
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> doubleDivideLong(ToDouble<T> first, long second) {
        return new DivideObjToDouble<T, ToDouble<T>, Long>(first) {
            @Override
            public double applyAsDouble(T object) {
                return this.first.applyAsDouble(object)
                    / (double) second;
            }

            @Override
            public Long second() {
                return second;
            }
        };
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> doubleDivideDouble(ToDouble<T> first, double second) {
        return new DivideObjToDouble<T, ToDouble<T>, Double>(first) {
            @Override
            public double applyAsDouble(T object) {
                return this.first.applyAsDouble(object) / second;
            }

            @Override
            public Double second() {
                return second;
            }
        };
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> doubleDivideInt(ToDouble<T> first, ToInt<T> second) {
        return new DivideToDouble<T, ToDouble<T>, ToInt<T>>(first, second) {
            @Override
            public double applyAsDouble(T object) {
                return this.first.applyAsDouble(object)
                    / (double) this.second.applyAsInt(object);
            }
        };
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> doubleDivideLong(ToDouble<T> first, ToLong<T> second) {
        return new DivideToDouble<T, ToDouble<T>, ToLong<T>>(first, second) {
            @Override
            public double applyAsDouble(T object) {
                return this.first.applyAsDouble(object)
                    / (double) this.second.applyAsLong(object);
            }
        };
    }

    /**
     * Returns an expression that takes the result from the first expression and
     * divides it with the result of the second expression. The second
     * expression must never return {@code 0}!
     *
     * @param first   the first expression
     * @param second  the second expression
     * @param <T>     the input entity type
     * @return        the division expression
     */
    public static <T> ToDouble<T> doubleDivideDouble(ToDouble<T> first, ToDouble<T> second) {
        return new DivideToDouble<T, ToDouble<T>, ToDouble<T>>(first, second) {
            @Override
            public double applyAsDouble(T object) {
                return this.first.applyAsDouble(object)
                    / this.second.applyAsDouble(object);
            }
        };
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //                       Internal Base Implementations                    //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Abstract base for a division operation that takes two other expressions
     * and returns a {@code double} value.
     *
     * @param <T>       the input entity type
     * @param <FIRST>   the first expression type
     * @param <SECOND>  the second expression type
     */
    abstract static class DivideToDouble
        <T, FIRST extends Expression<T>, SECOND extends Expression<T>>
    extends Divide<T, FIRST, SECOND> implements ToDouble<T> {
        DivideToDouble(FIRST first, SECOND second) {
            super(first, second);
        }

        @Override
        public Operator operator() {
            return Operator.DIVIDE;
        }
    }
    
    /**
     * Abstract base for a division operation that takes two other expressions.
     *
     * @param <T>       the input type
     * @param <FIRST>   the first expression type
     * @param <SECOND>  the second expression type
     */
    private abstract static class Divide
        <T, FIRST extends Expression<T>, SECOND extends Expression<T>>
    implements BinaryExpression<T, FIRST, SECOND> {

        final FIRST first;
        final SECOND second;

        Divide(FIRST first, SECOND second) {
            this.first  = requireNonNull(first);
            this.second = requireNonNull(second);
        }

        @Override
        public FIRST first() {
            return first;
        }

        @Override
        public SECOND second() {
            return second;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof BinaryExpression)) return false;
            final BinaryExpression<?, ?, ?> divide = (BinaryExpression<?, ?, ?>) o;
            return Objects.equals(first(), divide.first()) &&
                Objects.equals(second(), divide.second()) &&
                Objects.equals(operator(), divide.operator());
        }

        @Override
        public int hashCode() {
            return Objects.hash(first(), second(), operator());
        }
    }

    /**
     * Abstract base for a division operation that takes two other expressions
     * and returns a {@code double} value.
     *
     * @param <T>      the input entity type
     * @param <INNER>  the first expression type
     * @param <N>      the second expression type
     */
    abstract static class DivideObjToDouble
        <T, INNER extends Expression<T>, N>
    extends DivideObj<T, INNER, N> implements ToDouble<T> {

        DivideObjToDouble(INNER first) {
            super(first);
        }

        @Override
        public Operator operator() {
            return Operator.DIVIDE;
        }

        @Override
        public final boolean equals(Object o) {
            if (o == null) return false;
            else if (this == o) return true;
            else if (!(o instanceof BinaryObjExpression)) return false;
            final BinaryObjExpression<?, ?, ?> that = (BinaryObjExpression<?, ?, ?>) o;
            return Objects.equals(first, that.first()) &&
                Objects.equals(operator(), that.operator());
        }

        @Override
        public final int hashCode() {
            return Objects.hash(first, second(), operator());
        }
    }

    /**
     * Abstract base for a division operation that takes two other expressions.
     *
     * @param <T>     the input type
     * @param <INNER> the type of the expression to divide
     * @param <N>     the value to divide with
     */
    private abstract static class DivideObj
        <T, INNER extends Expression<T>, N>
    implements BinaryObjExpression<T, INNER, N> {

        final INNER first;

        DivideObj(INNER first) {
            this.first = requireNonNull(first);
        }

        @Override
        public final INNER first() {
            return first;
        }
    }

    /**
     * Utility classes should not be instantiated.
     */
    private DivideUtil() {}
}
