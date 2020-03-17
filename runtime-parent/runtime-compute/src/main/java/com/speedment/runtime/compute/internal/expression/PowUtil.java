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
    public static <T> ToDouble<T> bytePowInt(ToByte<T> expression, int power) {
        switch (power) {
            case 0: return ToDouble.constant(1);
            case 1: return expression.asDouble();
            case 2: return new IntPower<T, ToByte<T>>(expression, 2) {
                @Override
                public double applyAsDouble(T object) {
                    final double value = inner.applyAsByte(object);
                    return value * value;
                }
            };
            case 3: return new IntPower<T, ToByte<T>>(expression, 3) {
                @Override
                public double applyAsDouble(T object) {
                    final double value = inner.applyAsByte(object);
                    return value * value * value;
                }
            };
            case -1: return new IntPower<T, ToByte<T>>(expression, -1) {
                @Override
                public double applyAsDouble(T object) {
                    return 1d / inner.applyAsByte(object);
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
    public static <T> ToDouble<T> bytePowDouble(ToByte<T> expression, double power) {
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
    public static <T> ToDouble<T> bytePowInt(ToByte<T> expression, ToInt<T> power) {
        return new ToIntPower<T, ToByte<T>>(expression, power) {
            @Override
            public double applyAsDouble(T object) {
                final int p = power.applyAsInt(object);
                if (p == 0) return 1;
                final double value = inner.applyAsByte(object);
                switch (p) {
                    case 1: return value;
                    case 2: return value * value;
                    case 3: return value * value * value;
                    case 4: return value * value * value * value;
                    case 5: return value * value * value * value * value;
                    case -1: return 1d / value;
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
    public static <T> ToDouble<T> bytePowDouble(ToByte<T> expression, ToDouble<T> power) {
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
    public static <T> ToDouble<T> shortPowInt(ToShort<T> expression, int power) {
        switch (power) {
            case 0: return ToDouble.constant(1);
            case 1: return expression.asDouble();
            case 2: return new IntPower<T, ToShort<T>>(expression, 2) {
                @Override
                public double applyAsDouble(T object) {
                    final double value = inner.applyAsShort(object);
                    return value * value;
                }
            };
            case 3: return new IntPower<T, ToShort<T>>(expression, 3) {
                @Override
                public double applyAsDouble(T object) {
                    final double value = inner.applyAsShort(object);
                    return value * value * value;
                }
            };
            case -1: return new IntPower<T, ToShort<T>>(expression, -1) {
                @Override
                public double applyAsDouble(T object) {
                    return 1d / inner.applyAsShort(object);
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
    public static <T> ToDouble<T> shortPowDouble(ToShort<T> expression, double power) {
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
    public static <T> ToDouble<T> shortPowInt(ToShort<T> expression, ToInt<T> power) {
        return new ToIntPower<T, ToShort<T>>(expression, power) {
            @Override
            public double applyAsDouble(T object) {
                final int p = power.applyAsInt(object);
                if (p == 0) return 1;
                final double value = inner.applyAsShort(object);
                switch (p) {
                    case 1: return value;
                    case 2: return value * value;
                    case 3: return value * value * value;
                    case 4: return value * value * value * value;
                    case 5: return value * value * value * value * value;
                    case -1: return 1d / value;
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
    public static <T> ToDouble<T> shortPowDouble(ToShort<T> expression, ToDouble<T> power) {
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
    public static <T> ToDouble<T> intPowInt(ToInt<T> expression, int power) {
        switch (power) {
            case 0: return ToDouble.constant(1);
            case 1: return expression.asDouble();
            case 2: return new IntPower<T, ToInt<T>>(expression, 2) {
                @Override
                public double applyAsDouble(T object) {
                    final double value = inner.applyAsInt(object);
                    return value * value;
                }
            };
            case 3: return new IntPower<T, ToInt<T>>(expression, 3) {
                @Override
                public double applyAsDouble(T object) {
                    final double value = inner.applyAsInt(object);
                    return value * value * value;
                }
            };
            case -1: return new IntPower<T, ToInt<T>>(expression, -1) {
                @Override
                public double applyAsDouble(T object) {
                    return 1d / inner.applyAsInt(object);
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
    public static <T> ToDouble<T> intPowDouble(ToInt<T> expression, double power) {
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
    public static <T> ToDouble<T> intPowInt(ToInt<T> expression, ToInt<T> power) {
        return new ToIntPower<T, ToInt<T>>(expression, power) {
            @Override
            public double applyAsDouble(T object) {
                final int p = power.applyAsInt(object);
                if (p == 0) return 1;
                final double value = inner.applyAsInt(object);
                switch (p) {
                    case 1: return value;
                    case 2: return value * value;
                    case 3: return value * value * value;
                    case 4: return value * value * value * value;
                    case 5: return value * value * value * value * value;
                    case -1: return 1d / value;
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
    public static <T> ToDouble<T> intPowDouble(ToInt<T> expression, ToDouble<T> power) {
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
    public static <T> ToDouble<T> longPowInt(ToLong<T> expression, int power) {
        switch (power) {
            case 0: return ToDouble.constant(1);
            case 1: return expression.asDouble();
            case 2: return new IntPower<T, ToLong<T>>(expression, 2) {
                @Override
                public double applyAsDouble(T object) {
                    final double value = inner.applyAsLong(object);
                    return value * value;
                }
            };
            case 3: return new IntPower<T, ToLong<T>>(expression, 3) {
                @Override
                public double applyAsDouble(T object) {
                    final double value = inner.applyAsLong(object);
                    return value * value * value;
                }
            };
            case -1: return new IntPower<T, ToLong<T>>(expression, -1) {
                @Override
                public double applyAsDouble(T object) {
                    return 1d / inner.applyAsLong(object);
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
    public static <T> ToDouble<T> longPowDouble(ToLong<T> expression, double power) {
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
    public static <T> ToDouble<T> longPowInt(ToLong<T> expression, ToInt<T> power) {
        return new ToIntPower<T, ToLong<T>>(expression, power) {
            @Override
            public double applyAsDouble(T object) {
                final int p = power.applyAsInt(object);
                if (p == 0) return 1;
                final double value = inner.applyAsLong(object);
                switch (p) {
                    case 1: return value;
                    case 2: return value * value;
                    case 3: return value * value * value;
                    case 4: return value * value * value * value;
                    case 5: return value * value * value * value * value;
                    case -1: return 1d / value;
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
    public static <T> ToDouble<T> longPowDouble(ToLong<T> expression, ToDouble<T> power) {
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
    public static <T> ToDouble<T> floatPowInt(ToFloat<T> expression, int power) {
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
    public static <T> ToDouble<T> floatPowDouble(ToFloat<T> expression, double power) {
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
    public static <T> ToDouble<T> floatPowInt(ToFloat<T> expression, ToInt<T> power) {
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
    public static <T> ToDouble<T> floatPowDouble(ToFloat<T> expression, ToDouble<T> power) {
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
    public static <T> ToDouble<T> doublePowInt(ToDouble<T> expression, int power) {
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
    public static <T> ToDouble<T> doublePowDouble(ToDouble<T> expression, double power) {
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
    public static <T> ToDouble<T> doublePowInt(ToDouble<T> expression, ToInt<T> power) {
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
    public static <T> ToDouble<T> doublePowDouble(ToDouble<T> expression, ToDouble<T> power) {
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
    abstract static class IntPower<T, INNER extends Expression<T>>
    implements BinaryObjExpression<T, INNER, Integer>, ToDouble<T> {

        final INNER inner;
        final int power;

        IntPower(INNER inner, int power) {
            this.inner = requireNonNull(inner);
            this.power = power;
        }

        @Override
        public final INNER first() {
            return inner;
        }

        @Override
        public final Integer second() {
            return power;
        }

        @Override
        public final Operator operator() {
            return Operator.POW;
        }

        @Override
        public final boolean equals(Object o) {
            if (o == null) return false;
            else if (this == o) return true;
            else if (!(o instanceof BinaryObjExpression)) return false;
            final BinaryObjExpression<?, ?, ?> that = (BinaryObjExpression<?, ?, ?>) o;
            return Objects.equals(inner, that.first()) &&
                Objects.equals(power, that.second()) &&
                Objects.equals(operator(), that.operator());
        }

        @Override
        public final int hashCode() {
            return Objects.hash(inner, power, operator());
        }
    }

    /**
     * Abstract base implementation of a power expression.
     *
     * @param <T>      the input type
     * @param <INNER>  the inner expression type
     */
    abstract static class DoublePower<T, INNER extends Expression<T>>
        implements BinaryObjExpression<T, INNER, Double>, ToDouble<T> {

        final INNER inner;
        final double power;

        DoublePower(INNER inner, double power) {
            this.inner = requireNonNull(inner);
            this.power = power;
        }

        @Override
        public final INNER first() {
            return inner;
        }

        @Override
        public final Double second() {
            return power;
        }

        @Override
        public final Operator operator() {
            return Operator.POW;
        }

        @Override
        public final boolean equals(Object o) {
            if (o == null) return false;
            else if (this == o) return true;
            else if (!(o instanceof BinaryObjExpression)) return false;
            final BinaryObjExpression<?, ?, ?> that = (BinaryObjExpression<?, ?, ?>) o;
            return Objects.equals(inner, that.first()) &&
                Objects.equals(power, that.second()) &&
                Objects.equals(operator(), that.operator());
        }

        @Override
        public final int hashCode() {
            return Objects.hash(inner, power, operator());
        }
    }

    /**
     * Abstract base implementation of a power expression.
     *
     * @param <T>      the input type
     * @param <INNER>  the inner expression type
     */
    abstract static class ToIntPower<T, INNER extends Expression<T>>
    implements BinaryExpression<T, INNER, ToInt<T>>, ToDouble<T> {

        final INNER inner;
        final ToInt<T> power;

        ToIntPower(INNER inner, ToInt<T> power) {
            this.inner = requireNonNull(inner);
            this.power = requireNonNull(power);
        }

        @Override
        public final INNER first() {
            return inner;
        }

        @Override
        public final ToInt<T> second() {
            return power;
        }

        @Override
        public final Operator operator() {
            return Operator.POW;
        }

        @Override
        public final boolean equals(Object o) {
            if (o == null) return false;
            else if (this == o) return true;
            else if (!(o instanceof BinaryExpression)) return false;
            final BinaryExpression<?, ?, ?> that = (BinaryExpression<?, ?, ?>) o;
            return Objects.equals(inner, that.first()) &&
                Objects.equals(power, that.second()) &&
                Objects.equals(operator(), that.operator());
        }

        @Override
        public final int hashCode() {
            return Objects.hash(inner, power, operator());
        }
    }

    /**
     * Abstract base implementation of a power expression.
     *
     * @param <T>      the input type
     * @param <INNER>  the inner expression type
     */
    abstract static class ToDoublePower<T, INNER extends Expression<T>>
    implements BinaryExpression<T, INNER, ToDouble<T>>, ToDouble<T> {

        final INNER inner;
        final ToDouble<T> power;

        ToDoublePower(INNER inner, ToDouble<T> power) {
            this.inner = requireNonNull(inner);
            this.power = requireNonNull(power);
        }

        @Override
        public final INNER first() {
            return inner;
        }

        @Override
        public final ToDouble<T> second() {
            return power;
        }

        @Override
        public final Operator operator() {
            return Operator.POW;
        }

        @Override
        public final boolean equals(Object o) {
            if (o == null) return false;
            else if (this == o) return true;
            else if (!(o instanceof BinaryExpression)) return false;
            final BinaryExpression<?, ?, ?> that = (BinaryExpression<?, ?, ?>) o;
            return Objects.equals(inner, that.first()) &&
                Objects.equals(power, that.second()) &&
                Objects.equals(operator(), that.operator());
        }

        @Override
        public final int hashCode() {
            return Objects.hash(inner, power, operator());
        }
    }

    /**
     * Utility classes should not be instantiated.
     */
    private PowUtil() {}
}
