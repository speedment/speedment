package com.speedment.runtime.compute.internal.expression;

import com.speedment.runtime.compute.*;
import com.speedment.runtime.compute.expression.BinaryExpression;
import com.speedment.runtime.compute.expression.BinaryObjExpression;
import com.speedment.runtime.compute.expression.Expression;

import static java.util.Objects.requireNonNull;

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
    public static <T> ToDouble<T> divide(ToByte<T> first, int second) {
        return new DivideObjToDouble<T, ToByte<T>, Integer>(first) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsByte(object)
                    / (double) second;
            }

            @Override
            public Integer getSecond() {
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
    public static <T> ToDouble<T> divide(ToByte<T> first, long second) {
        return new DivideObjToDouble<T, ToByte<T>, Long>(first) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsByte(object)
                    / (double) second;
            }

            @Override
            public Long getSecond() {
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
    public static <T> ToDouble<T> divide(ToByte<T> first, double second) {
        return new DivideObjToDouble<T, ToByte<T>, Double>(first) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsByte(object) / second;
            }

            @Override
            public Double getSecond() {
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
    public static <T> ToDouble<T> divide(ToByte<T> first, ToInt<T> second) {
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
    public static <T> ToDouble<T> divide(ToByte<T> first, ToLong<T> second) {
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
    public static <T> ToDouble<T> divide(ToByte<T> first, ToDouble<T> second) {
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
    public static <T> ToDouble<T> divide(ToShort<T> first, int second) {
        return new DivideObjToDouble<T, ToShort<T>, Integer>(first) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsShort(object)
                    / (double) second;
            }

            @Override
            public Integer getSecond() {
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
    public static <T> ToDouble<T> divide(ToShort<T> first, long second) {
        return new DivideObjToDouble<T, ToShort<T>, Long>(first) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsShort(object)
                    / (double) second;
            }

            @Override
            public Long getSecond() {
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
    public static <T> ToDouble<T> divide(ToShort<T> first, double second) {
        return new DivideObjToDouble<T, ToShort<T>, Double>(first) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsShort(object) / second;
            }

            @Override
            public Double getSecond() {
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
    public static <T> ToDouble<T> divide(ToShort<T> first, ToInt<T> second) {
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
    public static <T> ToDouble<T> divide(ToShort<T> first, ToLong<T> second) {
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
    public static <T> ToDouble<T> divide(ToShort<T> first, ToDouble<T> second) {
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
    public static <T> ToDouble<T> divide(ToInt<T> first, int second) {
        return new DivideObjToDouble<T, ToInt<T>, Integer>(first) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsInt(object)
                    / (double) second;
            }

            @Override
            public Integer getSecond() {
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
    public static <T> ToDouble<T> divide(ToInt<T> first, long second) {
        return new DivideObjToDouble<T, ToInt<T>, Long>(first) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsInt(object)
                    / (double) second;
            }

            @Override
            public Long getSecond() {
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
    public static <T> ToDouble<T> divide(ToInt<T> first, double second) {
        return new DivideObjToDouble<T, ToInt<T>, Double>(first) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsInt(object) / second;
            }

            @Override
            public Double getSecond() {
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
    public static <T> ToDouble<T> divide(ToInt<T> first, ToInt<T> second) {
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
    public static <T> ToDouble<T> divide(ToInt<T> first, ToLong<T> second) {
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
    public static <T> ToDouble<T> divide(ToInt<T> first, ToDouble<T> second) {
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
    public static <T> ToDouble<T> divide(ToLong<T> first, int second) {
        return new DivideObjToDouble<T, ToLong<T>, Integer>(first) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsLong(object)
                    / (double) second;
            }

            @Override
            public Integer getSecond() {
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
    public static <T> ToDouble<T> divide(ToLong<T> first, long second) {
        return new DivideObjToDouble<T, ToLong<T>, Long>(first) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsLong(object)
                    / (double) second;
            }

            @Override
            public Long getSecond() {
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
    public static <T> ToDouble<T> divide(ToLong<T> first, double second) {
        return new DivideObjToDouble<T, ToLong<T>, Double>(first) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsLong(object) / second;
            }

            @Override
            public Double getSecond() {
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
    public static <T> ToDouble<T> divide(ToLong<T> first, ToInt<T> second) {
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
    public static <T> ToDouble<T> divide(ToLong<T> first, ToLong<T> second) {
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
    public static <T> ToDouble<T> divide(ToLong<T> first, ToDouble<T> second) {
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
    public static <T> ToDouble<T> divide(ToFloat<T> first, int second) {
        return new DivideObjToDouble<T, ToFloat<T>, Integer>(first) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsFloat(object)
                    / (double) second;
            }

            @Override
            public Integer getSecond() {
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
    public static <T> ToDouble<T> divide(ToFloat<T> first, long second) {
        return new DivideObjToDouble<T, ToFloat<T>, Long>(first) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsFloat(object)
                    / (double) second;
            }

            @Override
            public Long getSecond() {
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
    public static <T> ToDouble<T> divide(ToFloat<T> first, double second) {
        return new DivideObjToDouble<T, ToFloat<T>, Double>(first) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsFloat(object) / second;
            }

            @Override
            public Double getSecond() {
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
    public static <T> ToDouble<T> divide(ToFloat<T> first, ToInt<T> second) {
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
    public static <T> ToDouble<T> divide(ToFloat<T> first, ToLong<T> second) {
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
    public static <T> ToDouble<T> divide(ToFloat<T> first, ToDouble<T> second) {
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
    public static <T> ToDouble<T> divide(ToDouble<T> first, int second) {
        return new DivideObjToDouble<T, ToDouble<T>, Integer>(first) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsDouble(object)
                    / (double) second;
            }

            @Override
            public Integer getSecond() {
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
    public static <T> ToDouble<T> divide(ToDouble<T> first, long second) {
        return new DivideObjToDouble<T, ToDouble<T>, Long>(first) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsDouble(object)
                    / (double) second;
            }

            @Override
            public Long getSecond() {
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
    public static <T> ToDouble<T> divide(ToDouble<T> first, double second) {
        return new DivideObjToDouble<T, ToDouble<T>, Double>(first) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsDouble(object) / second;
            }

            @Override
            public Double getSecond() {
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
    public static <T> ToDouble<T> divide(ToDouble<T> first, ToInt<T> second) {
        return new DivideToDouble<T, ToDouble<T>, ToInt<T>>(first, second) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsDouble(object)
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
    public static <T> ToDouble<T> divide(ToDouble<T> first, ToLong<T> second) {
        return new DivideToDouble<T, ToDouble<T>, ToLong<T>>(first, second) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsDouble(object)
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
    public static <T> ToDouble<T> divide(ToDouble<T> first, ToDouble<T> second) {
        return new DivideToDouble<T, ToDouble<T>, ToDouble<T>>(first, second) {
            @Override
            public double applyAsDouble(T object) {
                return (double) this.first.applyAsDouble(object)
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
    private static abstract class DivideToDouble
        <T, FIRST extends Expression, SECOND extends Expression>
    extends Divide<FIRST, SECOND> implements ToDouble<T> {
        DivideToDouble(FIRST first, SECOND second) {
            super(first, second);
        }

        @Override
        public Operator getOperator() {
            return Operator.DIVIDE;
        }
    }
    
    /**
     * Abstract base for a division operation that takes two other expressions.
     *
     * @param <FIRST>   the first expression type
     * @param <SECOND>  the second expression type
     */
    private static abstract class Divide
        <FIRST extends Expression, SECOND extends Expression>
    implements BinaryExpression<FIRST, SECOND> {

        final FIRST first;
        final SECOND second;

        Divide(FIRST first, SECOND second) {
            this.first  = requireNonNull(first);
            this.second = requireNonNull(second);
        }

        @Override
        public FIRST getFirst() {
            return first;
        }

        @Override
        public SECOND getSecond() {
            return second;
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
    private static abstract class DivideObjToDouble
        <T, INNER extends Expression, N>
    extends DivideObj<INNER, N> implements ToDouble<T> {
        DivideObjToDouble(INNER first) {
            super(first);
        }

        @Override
        public Operator getOperator() {
            return Operator.DIVIDE;
        }
    }

    /**
     * Abstract base for a division operation that takes two other expressions.
     *
     * @param <INNER> the type of the expression to divide
     * @param <N>     the value to divide with
     */
    private static abstract class DivideObj
        <INNER extends Expression, N>
    implements BinaryObjExpression<INNER, N> {

        final INNER first;

        DivideObj(INNER first) {
            this.first = requireNonNull(first);
        }

        @Override
        public INNER getFirst() {
            return first;
        }
    }

    /**
     * Utility classes should not be instantiated.
     */
    private DivideUtil() {}
}
