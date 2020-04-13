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

/**
 * Utility class for checking invariants on {@code int}-values.
 *
 * @author Emil Forslund
 */
public final class IntRangeUtil {

    private IntRangeUtil() {}

    /**
     * Returns the given value if it is positive.
     *
     * @param val to check
     * @return the given value
     * @throws IllegalArgumentException if the given value is not positive
     */
    public static int requirePositive(int val) {
        if (val < 1) {
            throw new IllegalArgumentException(val + IS_NOT_POSITIVE);
        }
        return val;
    }

    public static int requireNegative(int val) {
        if (val > -1) {
            throw new IllegalArgumentException(val + IS_NOT_NEGATIVE);
        }
        return val;
    }

    public static int requireZero(int val) {
        if (val != 0) {
            throw new IllegalArgumentException(val + IS_NOT_ZERO);
        }
        return val;
    }

    public static int requireNonPositive(int val) {
        if (val > 0) {
            throw new IllegalArgumentException(val + IS_POSITIVE);
        }
        return val;
    }

    public static int requireNonNegative(int val) {
        if (val < 0) {
            throw new IllegalArgumentException(val + IS_NEGATIVE);
        }
        return val;
    }

    public static int requireNonZero(int val) {
        if (val == 0) {
            throw new IllegalArgumentException(val + IS_ZERO);
        }
        return val;
    }

    public static int requireEquals(int val, int otherVal) {
        if (val != otherVal) {
            throw new IllegalArgumentException(val + IS_NOT_EQUAL_TO + otherVal);
        }
        return val;
    }

    public static int requireNotEquals(int val, int otherVal) {
        if (val == otherVal) {
            throw new IllegalArgumentException(val + IS_EQUAL_TO + otherVal);
        }
        return val;
    }

    public static int requireInRange(int val, int first, int lastExclusive) {
        if (val < first || val >= lastExclusive) {
            throw new IllegalArgumentException(val + IS_NOT_IN_THE_RANGE + first + ", " + lastExclusive + ")");
        }
        return val;
    }

    public static int requireInRangeClosed(int val, int first, int lastInclusive) {
        if (val < first || val > lastInclusive) {
            throw new IllegalArgumentException(val + IS_NOT_IN_THE_RANGE + first + ", " + lastInclusive + "]");
        }
        return val;
    }

    /**
     * Returns the given value if it is positive.
     *
     * @param <E> RuntimeException type
     * @param val to check
     * @param exceptionConstructor to use when throwing exception
     * @return the given value
     * @throws RuntimeException if the given value is not positive
     */
    public static <E extends RuntimeException> int requirePositive(int val, Function<String, E> exceptionConstructor) {
        if (val < 1) {
            throw exceptionConstructor.apply(val + IS_NOT_POSITIVE);
        }
        return val;
    }

    public static <E extends RuntimeException> int requireNegative(int val, Function<String, E> exceptionConstructor) {
        if (val > -1) {
            throw exceptionConstructor.apply(val + IS_NOT_NEGATIVE);
        }
        return val;
    }

    public static <E extends RuntimeException> int requireZero(int val, Function<String, E> exceptionConstructor) {
        if (val != 0) {
            throw exceptionConstructor.apply(val + IS_NOT_ZERO);
        }
        return val;
    }

    public static <E extends RuntimeException> int requireNonPositive(int val, Function<String, E> exceptionConstructor) {
        if (val > 0) {
            throw exceptionConstructor.apply(val + IS_POSITIVE);
        }
        return val;
    }

    public static <E extends RuntimeException> int requireNonNegative(int val, Function<String, E> exceptionConstructor) {
        if (val < 0) {
            throw exceptionConstructor.apply(val + IS_NEGATIVE);
        }
        return val;
    }

    public static <E extends RuntimeException> int requireNonZero(int val, Function<String, E> exceptionConstructor) {
        if (val == 0) {
            throw exceptionConstructor.apply(val + IS_ZERO);
        }
        return val;
    }

    public static <E extends RuntimeException> int requireEquals(int val, int otherVal, Function<String, E> exceptionConstructor) {
        if (val != otherVal) {
            throw exceptionConstructor.apply(val + IS_NOT_EQUAL_TO + otherVal);
        }
        return val;
    }

    public static <E extends RuntimeException> int requireNotEquals(int val, int otherVal, Function<String, E> exceptionConstructor) {
        if (val == otherVal) {
            throw exceptionConstructor.apply(val + IS_EQUAL_TO + otherVal);
        }
        return val;
    }

    public static <E extends RuntimeException> int requireInRange(int val, int first, int lastExclusive, Function<String, E> exceptionConstructor) {
        if (val < first || val >= lastExclusive) {
            throw exceptionConstructor.apply(val + IS_NOT_IN_THE_RANGE + first + ", " + lastExclusive + ")");
        }
        return val;
    }

    public static <E extends RuntimeException> int requireInRangeClosed(int val, int first, int lastInclusive, Function<String, E> exceptionConstructor) {
        if (val < first || val > lastInclusive) {
            throw exceptionConstructor.apply(val + IS_NOT_IN_THE_RANGE + first + ", " + lastInclusive + "]");
        }
        return val;
    }

}
