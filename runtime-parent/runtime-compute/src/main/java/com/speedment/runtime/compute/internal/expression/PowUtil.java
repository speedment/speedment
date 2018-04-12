package com.speedment.runtime.compute.internal.expression;

import com.speedment.runtime.compute.*;
import com.speedment.runtime.compute.expression.BinaryExpression;
import com.speedment.runtime.compute.expression.BinaryObjExpression;
import com.speedment.runtime.compute.expression.Expression;

import static java.util.Objects.requireNonNull;

/**
 * Utility class for creating expressions that takes the value of another
 * expression and raises it to a particular power.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class PowUtil {

    ////////////////////////////////////////////////////////////////////////////
    //                                 ToByte                                 //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Returns an expression that gives the value of the specified expression,
     * raised to the specified power.
     *
     * @param expression  the expression to raise
     * @param power       the power to raise it to
     * @param <T>         the type of the input
     * @return            the new expression
     */
    public static <T> ToDouble<T> pow(ToByte<T> expression, int power) {
        switch (power) {
            case 0: return ToDouble.constant(1);
            case 1: return expression.asDouble();
            case 2: return new IntPower<T, ToByte<T>>(expression, 2) {
                @Override
                public double applyAsDouble(T object) {
                    final byte value = inner.applyAsByte(object);
                    return value * value;
                }
            };
            case 3: return new IntPower<T, ToByte<T>>(expression, 3) {
                @Override
                public double applyAsDouble(T object) {
                    final byte value = inner.applyAsByte(object);
                    return value * value * value;
                }
            };
            case -1: return new IntPower<T, ToByte<T>>(expression, -1) {
                @Override
                public double applyAsDouble(T object) {
                    return 1 / inner.applyAsByte(object);
                }
            };
            default: return new IntPower<T, ToByte<T>>(expression, power) {
                @Override
                public double applyAsDouble(T object) {
                    return Math.pow(inner.applyAsByte(object), this.power);
                }
            };
        }
    }

    /**
     * Returns an expression that gives the value of the specified expression,
     * raised to the specified power.
     *
     * @param expression  the expression to raise
     * @param power       the power to raise it to
     * @param <T>         the type of the input
     * @return            the new expression
     */
    public static <T> ToDouble<T> pow(ToByte<T> expression, double power) {
        return new DoublePower<T, ToByte<T>>(expression, power) {
            @Override
            public double applyAsDouble(T object) {
                return Math.pow(inner.applyAsByte(object), this.power);
            }
        };
    }

    /**
     * Returns an expression that gives the value of the specified expression,
     * raised to the specified power.
     *
     * @param expression  the expression to raise
     * @param power       the power to raise it to
     * @param <T>         the type of the input
     * @return            the new expression
     */
    public static <T> ToDouble<T> pow(ToByte<T> expression, ToInt<T> power) {
        return new ToIntPower<T, ToByte<T>>(expression, power) {
            @Override
            public double applyAsDouble(T object) {
                final int p = power.applyAsInt(object);
                if (p == 0) return 1;
                final byte value = inner.applyAsByte(object);
                switch (p) {
                    case 1: return value;
                    case 2: return value * value;
                    case 3: return value * value * value;
                    case 4: return value * value * value * value;
                    case 5: return value * value * value * value * value;
                    case -1: return 1 / value;
                    default: return Math.pow(value, p);
                }
            }
        };
    }

    /**
     * Returns an expression that gives the value of the specified expression,
     * raised to the specified power.
     *
     * @param expression  the expression to raise
     * @param power       the power to raise it to
     * @param <T>         the type of the input
     * @return            the new expression
     */
    public static <T> ToDouble<T> pow(ToByte<T> expression, ToDouble<T> power) {
        return new ToDoublePower<T, ToByte<T>>(expression, power) {
            @Override
            public double applyAsDouble(T object) {
                return Math.pow(
                    inner.applyAsByte(object),
                    power.applyAsDouble(object)
                );
            }
        };
    }

    ////////////////////////////////////////////////////////////////////////////
    //                                 ToShort                                //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Returns an expression that gives the value of the specified expression,
     * raised to the specified power.
     *
     * @param expression  the expression to raise
     * @param power       the power to raise it to
     * @param <T>         the type of the input
     * @return            the new expression
     */
    public static <T> ToDouble<T> pow(ToShort<T> expression, int power) {
        switch (power) {
            case 0: return ToDouble.constant(1);
            case 1: return expression.asDouble();
            case 2: return new IntPower<T, ToShort<T>>(expression, 2) {
                @Override
                public double applyAsDouble(T object) {
                    final short value = inner.applyAsShort(object);
                    return value * value;
                }
            };
            case 3: return new IntPower<T, ToShort<T>>(expression, 3) {
                @Override
                public double applyAsDouble(T object) {
                    final short value = inner.applyAsShort(object);
                    return value * value * value;
                }
            };
            case -1: return new IntPower<T, ToShort<T>>(expression, -1) {
                @Override
                public double applyAsDouble(T object) {
                    return 1 / inner.applyAsShort(object);
                }
            };
            default: return new IntPower<T, ToShort<T>>(expression, power) {
                @Override
                public double applyAsDouble(T object) {
                    return Math.pow(inner.applyAsShort(object), this.power);
                }
            };
        }
    }

    /**
     * Returns an expression that gives the value of the specified expression,
     * raised to the specified power.
     *
     * @param expression  the expression to raise
     * @param power       the power to raise it to
     * @param <T>         the type of the input
     * @return            the new expression
     */
    public static <T> ToDouble<T> pow(ToShort<T> expression, double power) {
        return new DoublePower<T, ToShort<T>>(expression, power) {
            @Override
            public double applyAsDouble(T object) {
                return Math.pow(inner.applyAsShort(object), this.power);
            }
        };
    }

    /**
     * Returns an expression that gives the value of the specified expression,
     * raised to the specified power.
     *
     * @param expression  the expression to raise
     * @param power       the power to raise it to
     * @param <T>         the type of the input
     * @return            the new expression
     */
    public static <T> ToDouble<T> pow(ToShort<T> expression, ToInt<T> power) {
        return new ToIntPower<T, ToShort<T>>(expression, power) {
            @Override
            public double applyAsDouble(T object) {
                final int p = power.applyAsInt(object);
                if (p == 0) return 1;
                final short value = inner.applyAsShort(object);
                switch (p) {
                    case 1: return value;
                    case 2: return value * value;
                    case 3: return value * value * value;
                    case 4: return value * value * value * value;
                    case 5: return value * value * value * value * value;
                    case -1: return 1 / value;
                    default: return Math.pow(value, p);
                }
            }
        };
    }

    /**
     * Returns an expression that gives the value of the specified expression,
     * raised to the specified power.
     *
     * @param expression  the expression to raise
     * @param power       the power to raise it to
     * @param <T>         the type of the input
     * @return            the new expression
     */
    public static <T> ToDouble<T> pow(ToShort<T> expression, ToDouble<T> power) {
        return new ToDoublePower<T, ToShort<T>>(expression, power) {
            @Override
            public double applyAsDouble(T object) {
                return Math.pow(
                    inner.applyAsShort(object),
                    power.applyAsDouble(object)
                );
            }
        };
    }

    ////////////////////////////////////////////////////////////////////////////
    //                                 ToInt                                  //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Returns an expression that gives the value of the specified expression,
     * raised to the specified power.
     *
     * @param expression  the expression to raise
     * @param power       the power to raise it to
     * @param <T>         the type of the input
     * @return            the new expression
     */
    public static <T> ToDouble<T> pow(ToInt<T> expression, int power) {
        switch (power) {
            case 0: return ToDouble.constant(1);
            case 1: return expression.asDouble();
            case 2: return new IntPower<T, ToInt<T>>(expression, 2) {
                @Override
                public double applyAsDouble(T object) {
                    final int value = inner.applyAsInt(object);
                    return value * value;
                }
            };
            case 3: return new IntPower<T, ToInt<T>>(expression, 3) {
                @Override
                public double applyAsDouble(T object) {
                    final int value = inner.applyAsInt(object);
                    return value * value * value;
                }
            };
            case -1: return new IntPower<T, ToInt<T>>(expression, -1) {
                @Override
                public double applyAsDouble(T object) {
                    return 1 / inner.applyAsInt(object);
                }
            };
            default: return new IntPower<T, ToInt<T>>(expression, power) {
                @Override
                public double applyAsDouble(T object) {
                    return Math.pow(inner.applyAsInt(object), this.power);
                }
            };
        }
    }

    /**
     * Returns an expression that gives the value of the specified expression,
     * raised to the specified power.
     *
     * @param expression  the expression to raise
     * @param power       the power to raise it to
     * @param <T>         the type of the input
     * @return            the new expression
     */
    public static <T> ToDouble<T> pow(ToInt<T> expression, double power) {
        return new DoublePower<T, ToInt<T>>(expression, power) {
            @Override
            public double applyAsDouble(T object) {
                return Math.pow(inner.applyAsInt(object), this.power);
            }
        };
    }

    /**
     * Returns an expression that gives the value of the specified expression,
     * raised to the specified power.
     *
     * @param expression  the expression to raise
     * @param power       the power to raise it to
     * @param <T>         the type of the input
     * @return            the new expression
     */
    public static <T> ToDouble<T> pow(ToInt<T> expression, ToInt<T> power) {
        return new ToIntPower<T, ToInt<T>>(expression, power) {
            @Override
            public double applyAsDouble(T object) {
                final int p = power.applyAsInt(object);
                if (p == 0) return 1;
                final int value = inner.applyAsInt(object);
                switch (p) {
                    case 1: return value;
                    case 2: return value * value;
                    case 3: return value * value * value;
                    case 4: return value * value * value * value;
                    case 5: return value * value * value * value * value;
                    case -1: return 1 / value;
                    default: return Math.pow(value, p);
                }
            }
        };
    }

    /**
     * Returns an expression that gives the value of the specified expression,
     * raised to the specified power.
     *
     * @param expression  the expression to raise
     * @param power       the power to raise it to
     * @param <T>         the type of the input
     * @return            the new expression
     */
    public static <T> ToDouble<T> pow(ToInt<T> expression, ToDouble<T> power) {
        return new ToDoublePower<T, ToInt<T>>(expression, power) {
            @Override
            public double applyAsDouble(T object) {
                return Math.pow(
                    inner.applyAsInt(object),
                    power.applyAsDouble(object)
                );
            }
        };
    }

    ////////////////////////////////////////////////////////////////////////////
    //                                 ToLong                                 //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Returns an expression that gives the value of the specified expression,
     * raised to the specified power.
     *
     * @param expression  the expression to raise
     * @param power       the power to raise it to
     * @param <T>         the type of the input
     * @return            the new expression
     */
    public static <T> ToDouble<T> pow(ToLong<T> expression, int power) {
        switch (power) {
            case 0: return ToDouble.constant(1);
            case 1: return expression.asDouble();
            case 2: return new IntPower<T, ToLong<T>>(expression, 2) {
                @Override
                public double applyAsDouble(T object) {
                    final long value = inner.applyAsLong(object);
                    return value * value;
                }
            };
            case 3: return new IntPower<T, ToLong<T>>(expression, 3) {
                @Override
                public double applyAsDouble(T object) {
                    final long value = inner.applyAsLong(object);
                    return value * value * value;
                }
            };
            case -1: return new IntPower<T, ToLong<T>>(expression, -1) {
                @Override
                public double applyAsDouble(T object) {
                    return 1 / inner.applyAsLong(object);
                }
            };
            default: return new IntPower<T, ToLong<T>>(expression, power) {
                @Override
                public double applyAsDouble(T object) {
                    return Math.pow(inner.applyAsLong(object), this.power);
                }
            };
        }
    }

    /**
     * Returns an expression that gives the value of the specified expression,
     * raised to the specified power.
     *
     * @param expression  the expression to raise
     * @param power       the power to raise it to
     * @param <T>         the type of the input
     * @return            the new expression
     */
    public static <T> ToDouble<T> pow(ToLong<T> expression, double power) {
        return new DoublePower<T, ToLong<T>>(expression, power) {
            @Override
            public double applyAsDouble(T object) {
                return Math.pow(inner.applyAsLong(object), this.power);
            }
        };
    }

    /**
     * Returns an expression that gives the value of the specified expression,
     * raised to the specified power.
     *
     * @param expression  the expression to raise
     * @param power       the power to raise it to
     * @param <T>         the type of the input
     * @return            the new expression
     */
    public static <T> ToDouble<T> pow(ToLong<T> expression, ToInt<T> power) {
        return new ToIntPower<T, ToLong<T>>(expression, power) {
            @Override
            public double applyAsDouble(T object) {
                final int p = power.applyAsInt(object);
                if (p == 0) return 1;
                final long value = inner.applyAsLong(object);
                switch (p) {
                    case 1: return value;
                    case 2: return value * value;
                    case 3: return value * value * value;
                    case 4: return value * value * value * value;
                    case 5: return value * value * value * value * value;
                    case -1: return 1 / value;
                    default: return Math.pow(value, p);
                }
            }
        };
    }

    /**
     * Returns an expression that gives the value of the specified expression,
     * raised to the specified power.
     *
     * @param expression  the expression to raise
     * @param power       the power to raise it to
     * @param <T>         the type of the input
     * @return            the new expression
     */
    public static <T> ToDouble<T> pow(ToLong<T> expression, ToDouble<T> power) {
        return new ToDoublePower<T, ToLong<T>>(expression, power) {
            @Override
            public double applyAsDouble(T object) {
                return Math.pow(
                    inner.applyAsLong(object),
                    power.applyAsDouble(object)
                );
            }
        };
    }

    ////////////////////////////////////////////////////////////////////////////
    //                                 ToFloat                                //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Returns an expression that gives the value of the specified expression,
     * raised to the specified power.
     *
     * @param expression  the expression to raise
     * @param power       the power to raise it to
     * @param <T>         the type of the input
     * @return            the new expression
     */
    public static <T> ToDouble<T> pow(ToFloat<T> expression, int power) {
        switch (power) {
            case 0: return ToDouble.constant(1);
            case 1: return expression.asDouble();
            case 2: return new IntPower<T, ToFloat<T>>(expression, 2) {
                @Override
                public double applyAsDouble(T object) {
                    final float value = inner.applyAsFloat(object);
                    return value * value;
                }
            };
            case 3: return new IntPower<T, ToFloat<T>>(expression, 3) {
                @Override
                public double applyAsDouble(T object) {
                    final float value = inner.applyAsFloat(object);
                    return value * value * value;
                }
            };
            case -1: return new IntPower<T, ToFloat<T>>(expression, -1) {
                @Override
                public double applyAsDouble(T object) {
                    return 1 / inner.applyAsFloat(object);
                }
            };
            default: return new IntPower<T, ToFloat<T>>(expression, power) {
                @Override
                public double applyAsDouble(T object) {
                    return Math.pow(inner.applyAsFloat(object), this.power);
                }
            };
        }
    }

    /**
     * Returns an expression that gives the value of the specified expression,
     * raised to the specified power.
     *
     * @param expression  the expression to raise
     * @param power       the power to raise it to
     * @param <T>         the type of the input
     * @return            the new expression
     */
    public static <T> ToDouble<T> pow(ToFloat<T> expression, double power) {
        return new DoublePower<T, ToFloat<T>>(expression, power) {
            @Override
            public double applyAsDouble(T object) {
                return Math.pow(inner.applyAsFloat(object), this.power);
            }
        };
    }

    /**
     * Returns an expression that gives the value of the specified expression,
     * raised to the specified power.
     *
     * @param expression  the expression to raise
     * @param power       the power to raise it to
     * @param <T>         the type of the input
     * @return            the new expression
     */
    public static <T> ToDouble<T> pow(ToFloat<T> expression, ToInt<T> power) {
        return new ToIntPower<T, ToFloat<T>>(expression, power) {
            @Override
            public double applyAsDouble(T object) {
                final int p = power.applyAsInt(object);
                if (p == 0) return 1;
                final float value = inner.applyAsFloat(object);
                switch (p) {
                    case 1: return value;
                    case 2: return value * value;
                    case 3: return value * value * value;
                    case 4: return value * value * value * value;
                    case 5: return value * value * value * value * value;
                    case -1: return 1 / value;
                    default: return Math.pow(value, p);
                }
            }
        };
    }

    /**
     * Returns an expression that gives the value of the specified expression,
     * raised to the specified power.
     *
     * @param expression  the expression to raise
     * @param power       the power to raise it to
     * @param <T>         the type of the input
     * @return            the new expression
     */
    public static <T> ToDouble<T> pow(ToFloat<T> expression, ToDouble<T> power) {
        return new ToDoublePower<T, ToFloat<T>>(expression, power) {
            @Override
            public double applyAsDouble(T object) {
                return Math.pow(
                    inner.applyAsFloat(object),
                    power.applyAsDouble(object)
                );
            }
        };
    }

    ////////////////////////////////////////////////////////////////////////////
    //                                 ToDouble                                //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Returns an expression that gives the value of the specified expression,
     * raised to the specified power.
     *
     * @param expression  the expression to raise
     * @param power       the power to raise it to
     * @param <T>         the type of the input
     * @return            the new expression
     */
    public static <T> ToDouble<T> pow(ToDouble<T> expression, int power) {
        switch (power) {
            case 0: return ToDouble.constant(1);
            case 1: return expression.asDouble();
            case 2: return new IntPower<T, ToDouble<T>>(expression, 2) {
                @Override
                public double applyAsDouble(T object) {
                    final double value = inner.applyAsDouble(object);
                    return value * value;
                }
            };
            case 3: return new IntPower<T, ToDouble<T>>(expression, 3) {
                @Override
                public double applyAsDouble(T object) {
                    final double value = inner.applyAsDouble(object);
                    return value * value * value;
                }
            };
            case -1: return new IntPower<T, ToDouble<T>>(expression, -1) {
                @Override
                public double applyAsDouble(T object) {
                    return 1 / inner.applyAsDouble(object);
                }
            };
            default: return new IntPower<T, ToDouble<T>>(expression, power) {
                @Override
                public double applyAsDouble(T object) {
                    return Math.pow(inner.applyAsDouble(object), this.power);
                }
            };
        }
    }

    /**
     * Returns an expression that gives the value of the specified expression,
     * raised to the specified power.
     *
     * @param expression  the expression to raise
     * @param power       the power to raise it to
     * @param <T>         the type of the input
     * @return            the new expression
     */
    public static <T> ToDouble<T> pow(ToDouble<T> expression, double power) {
        return new DoublePower<T, ToDouble<T>>(expression, power) {
            @Override
            public double applyAsDouble(T object) {
                return Math.pow(inner.applyAsDouble(object), this.power);
            }
        };
    }

    /**
     * Returns an expression that gives the value of the specified expression,
     * raised to the specified power.
     *
     * @param expression  the expression to raise
     * @param power       the power to raise it to
     * @param <T>         the type of the input
     * @return            the new expression
     */
    public static <T> ToDouble<T> pow(ToDouble<T> expression, ToInt<T> power) {
        return new ToIntPower<T, ToDouble<T>>(expression, power) {
            @Override
            public double applyAsDouble(T object) {
                final int p = power.applyAsInt(object);
                if (p == 0) return 1;
                final double value = inner.applyAsDouble(object);
                switch (p) {
                    case 1: return value;
                    case 2: return value * value;
                    case 3: return value * value * value;
                    case 4: return value * value * value * value;
                    case 5: return value * value * value * value * value;
                    case -1: return 1 / value;
                    default: return Math.pow(value, p);
                }
            }
        };
    }

    /**
     * Returns an expression that gives the value of the specified expression,
     * raised to the specified power.
     *
     * @param expression  the expression to raise
     * @param power       the power to raise it to
     * @param <T>         the type of the input
     * @return            the new expression
     */
    public static <T> ToDouble<T> pow(ToDouble<T> expression, ToDouble<T> power) {
        return new ToDoublePower<T, ToDouble<T>>(expression, power) {
            @Override
            public double applyAsDouble(T object) {
                return Math.pow(
                    inner.applyAsDouble(object),
                    power.applyAsDouble(object)
                );
            }
        };
    }

    ////////////////////////////////////////////////////////////////////////////
    //                          Internal Base Classes                         //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Abstract base implementation of a power expression.
     *
     * @param <T>      the input type
     * @param <INNER>  the inner expression type
     */
    private abstract static class IntPower<T, INNER extends Expression>
    implements BinaryObjExpression<INNER, Integer>, ToDouble<T> {

        final INNER inner;
        final int power;

        IntPower(INNER inner, int power) {
            this.inner = requireNonNull(inner);
            this.power = power;
        }

        @Override
        public final INNER getFirst() {
            return inner;
        }

        @Override
        public final Integer getSecond() {
            return power;
        }

        @Override
        public final Operator getOperator() {
            return Operator.POW;
        }
    }

    /**
     * Abstract base implementation of a power expression.
     *
     * @param <T>      the input type
     * @param <INNER>  the inner expression type
     */
    private abstract static class DoublePower<T, INNER extends Expression>
        implements BinaryObjExpression<INNER, Double>, ToDouble<T> {

        final INNER inner;
        final double power;

        DoublePower(INNER inner, double power) {
            this.inner = requireNonNull(inner);
            this.power = power;
        }

        @Override
        public final INNER getFirst() {
            return inner;
        }

        @Override
        public final Double getSecond() {
            return power;
        }

        @Override
        public final Operator getOperator() {
            return Operator.POW;
        }
    }

    /**
     * Abstract base implementation of a power expression.
     *
     * @param <T>      the input type
     * @param <INNER>  the inner expression type
     */
    private abstract static class ToIntPower<T, INNER extends Expression>
    implements BinaryExpression<INNER, ToInt<T>>, ToDouble<T> {

        final INNER inner;
        final ToInt<T> power;

        ToIntPower(INNER inner, ToInt<T> power) {
            this.inner = requireNonNull(inner);
            this.power = requireNonNull(power);
        }

        @Override
        public final INNER getFirst() {
            return inner;
        }

        @Override
        public final ToInt<T> getSecond() {
            return power;
        }

        @Override
        public final Operator getOperator() {
            return Operator.POW;
        }
    }

    /**
     * Abstract base implementation of a power expression.
     *
     * @param <T>      the input type
     * @param <INNER>  the inner expression type
     */
    private abstract static class ToDoublePower<T, INNER extends Expression>
    implements BinaryExpression<INNER, ToDouble<T>>, ToDouble<T> {

        final INNER inner;
        final ToDouble<T> power;

        ToDoublePower(INNER inner, ToDouble<T> power) {
            this.inner = requireNonNull(inner);
            this.power = requireNonNull(power);
        }

        @Override
        public final INNER getFirst() {
            return inner;
        }

        @Override
        public final ToDouble<T> getSecond() {
            return power;
        }

        @Override
        public final Operator getOperator() {
            return Operator.POW;
        }
    }

    /**
     * Utility classes should not be instantiated.
     */
    private PowUtil() {}
}
