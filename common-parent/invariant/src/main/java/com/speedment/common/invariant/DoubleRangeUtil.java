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
package com.speedment.common.invariant;

import java.util.function.Function;

import static com.speedment.common.invariant.ConstantUtil.*;
import static java.lang.String.format;

/**
 * Utility class for checking invariants on {@code double}-values.
 *
 * @author Emil Forslund
 * @since  1.0.3
 */
public final class DoubleRangeUtil {

    private DoubleRangeUtil() {}

    /**
     * Returns the given value if it is positive (greater than {@code 0}).
     *
     * @param val  to check
     * @return     the given value
     *
     * @throws IllegalArgumentException if the given value is not positive
     */
    public static double requirePositive(double val) {
        if (Double.compare(val, 0d) <= 0) {
            throw new IllegalArgumentException(val + IS_NOT_POSITIVE);
        }
        return val;
    }

    /**
     * Returns the given value if it is negative (less than {@code 0}).
     *
     * @param val  to check
     * @return     the given value
     *
     * @throws IllegalArgumentException if the given value is not negative
     */
    public static double requireNegative(double val) {
        if (Double.compare(val, 0d) >= 0) {
            throw new IllegalArgumentException(val + IS_NOT_NEGATIVE);
        }
        return val;
    }

    /**
     * Returns the given value if it is equal to {@code 0}.
     *
     * @param val  to check
     * @return     the given value
     *
     * @throws IllegalArgumentException if the given value is not 0
     */
    public static double requireZero(double val) {
        if (Double.compare(val, 0d) != 0) {
            throw new IllegalArgumentException(val + IS_NOT_ZERO);
        }
        return val;
    }

    /**
     * Returns the given value if it is not positive (less than or equal to
     * {@code 0}).
     *
     * @param val  to check
     * @return     the given value
     *
     * @throws IllegalArgumentException if the given value is positive
     */
    public static double requireNonPositive(double val) {
        if (Double.compare(val, 0d) > 0) {
            throw new IllegalArgumentException(val + IS_POSITIVE);
        }
        return val;
    }

    /**
     * Returns the given value if it is not negative (greater than or equal to
     * {@code 0}).
     *
     * @param val  to check
     * @return     the given value
     *
     * @throws IllegalArgumentException if the given value is negative
     */
    public static double requireNonNegative(double val) {
        if (Double.compare(val, 0d) < 0) {
            throw new IllegalArgumentException(val + IS_NEGATIVE);
        }
        return val;
    }

    /**
     * Returns the given value if it is not {@code 0}.
     *
     * @param val  to check
     * @return     the given value
     *
     * @throws IllegalArgumentException if the given value is zero
     */
    public static double requireNonZero(double val) {
        if (Double.compare(val, 0d) == 0) {
            throw new IllegalArgumentException(val + IS_ZERO);
        }
        return val;
    }

    public static double requireEquals(double val, double otherVal) {
        if (Double.compare(val, otherVal) != 0) {
            throw new IllegalArgumentException(val + IS_NOT_EQUAL_TO + otherVal);
        }
        return val;
    }

    public static double requireNotEquals(double val, double otherVal) {
        if (val == otherVal) {
            throw new IllegalArgumentException(val + IS_EQUAL_TO + otherVal);
        }
        return val;
    }

    public static double requireInRange(double val, double first, double lastExclusive) {
        if (val < first || val >= lastExclusive) {
            throw new IllegalArgumentException(val + IS_NOT_IN_THE_RANGE + first + ", " + lastExclusive + ")");
        }
        return val;
    }

    public static double requireInRangeClosed(double val, double first, double lastInclusive) {
        if (val < first || val > lastInclusive) {
            throw new IllegalArgumentException(val + IS_NOT_IN_THE_RANGE + first + ", " + lastInclusive + "]");
        }
        return val;
    }

    /**
     * Returns the given value if it is positive.
     *
     * @param <E>  RuntimeException type
     *
     * @param val                   to check
     * @param exceptionConstructor  to use when throwing exception
     * @return                      the given value
     *
     * @throws RuntimeException if the given value is not positive
     */
    public static <E extends RuntimeException> double
    requirePositive(double val, Function<String, E> exceptionConstructor) {
        if (val <= 0) {
            throw exceptionConstructor.apply(val + IS_NOT_POSITIVE);
        }
        return val;
    }

    public static <E extends RuntimeException> double
    requireNegative(double val, Function<String, E> exceptionConstructor) {
        if (val >= 0) {
            throw exceptionConstructor.apply(val + IS_NOT_NEGATIVE);
        }
        return val;
    }

    public static <E extends RuntimeException> double
    requireZero(double val, Function<String, E> exceptionConstructor) {
        if (val != 0) {
            throw exceptionConstructor.apply(val + IS_NOT_ZERO);
        }
        return val;
    }

    public static <E extends RuntimeException> double
    requireNonPositive(double val, Function<String, E> exceptionConstructor) {
        if (val > 0) {
            throw exceptionConstructor.apply(val + IS_POSITIVE);
        }
        return val;
    }

    public static <E extends RuntimeException> double
    requireNonNegative(double val, Function<String, E> exceptionConstructor) {
        if (val < 0) {
            throw exceptionConstructor.apply(val + IS_NEGATIVE);
        }
        return val;
    }

    public static <E extends RuntimeException> double
    requireNonZero(double val, Function<String, E> exceptionConstructor) {
        if (val == 0) {
            throw exceptionConstructor.apply(val + IS_ZERO);
        }
        return val;
    }

    public static <E extends RuntimeException> double
    requireEquals(double val, double otherVal,
                  Function<String, E> exceptionConstructor) {
        if (val != otherVal) {
            throw exceptionConstructor.apply(val + IS_NOT_EQUAL_TO + otherVal);
        }
        return val;
    }

    public static <E extends RuntimeException> double
    requireNotEquals(double val, double otherVal,
                     Function<String, E> exceptionConstructor) {
        if (val == otherVal) {
            throw exceptionConstructor.apply(val + IS_EQUAL_TO + otherVal);
        }
        return val;
    }

    public static <E extends RuntimeException> double
    requireInRange(double val, double first, double lastExclusive,
                   Function<String, E> exceptionConstructor) {
        if (val < first || val >= lastExclusive) {
            throw exceptionConstructor.apply(val + IS_NOT_IN_THE_RANGE + first + ", " + lastExclusive + ")");
        }
        return val;
    }

    public static <E extends RuntimeException> double
    requireInRangeClosed(double val, double first, double lastInclusive,
                         Function<String, E> exceptionConstructor) {
        if (val < first || val > lastInclusive) {
            throw exceptionConstructor.apply(val + IS_NOT_IN_THE_RANGE + first + ", " + lastInclusive + "]");
        }
        return val;
    }

}
