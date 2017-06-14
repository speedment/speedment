/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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

/**
 *
 * @author Per Minborg
 */
public final class LongRangeUtil {

    /**
     * Returns the given value if it is positive.
     *
     * @param val to check
     * @return the given value
     * @throws IllegalArgumentException if the given value is not positive
     */
    public static long requirePositive(long val) {
        if (val < 1) {
            throw new IllegalArgumentException(val + " is not positive");
        }
        return val;
    }

    public static long requireNegative(long val) {
        if (val > -1) {
            throw new IllegalArgumentException(val + " is not negative");
        }
        return val;
    }

    public static long requireZero(long val) {
        if (val != 0) {
            throw new IllegalArgumentException(val + " is not zero");
        }
        return val;
    }

    public static long requireNonPositive(long val) {
        if (val > 0) {
            throw new IllegalArgumentException(val + " is positive");
        }
        return val;
    }

    public static long requireNonNegative(long val) {
        if (val < 0) {
            throw new IllegalArgumentException(val + " is negative");
        }
        return val;
    }

    public static long requireNonZero(long val) {
        if (val == 0) {
            throw new IllegalArgumentException(val + " is zero");
        }
        return val;
    }

    public static long requireEquals(long val, long otherVal) {
        if (val != otherVal) {
            throw new IllegalArgumentException(val + " is not equal to " + otherVal);
        }
        return val;
    }

    public static long requireNotEquals(long val, long otherVal) {
        if (val == otherVal) {
            throw new IllegalArgumentException(val + " is equal to " + otherVal);
        }
        return val;
    }

    public static long requireInRange(long val, long first, long lastExclusive) {
        if (val < first || val >= lastExclusive) {
            throw new IllegalArgumentException(val + " is not in the range [" + first + ", " + lastExclusive + ")");
        }
        return val;
    }

    public static long requireInRangeClosed(long val, long first, long lastInclusive) {
        if (val < first || val > lastInclusive) {
            throw new IllegalArgumentException(val + " is not in the range [" + first + ", " + lastInclusive + "]");
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
    public static <E extends RuntimeException> long requirePositive(long val, Function<String, E> exceptionConstructor) {
        if (val < 1) {
            throw exceptionConstructor.apply(val + " is not positive");
        }
        return val;
    }

    public static <E extends RuntimeException> long requireNegative(long val, Function<String, E> exceptionConstructor) {
        if (val > -1) {
            throw exceptionConstructor.apply(val + " is not negative");
        }
        return val;
    }

    public static <E extends RuntimeException> long requireZero(long val, Function<String, E> exceptionConstructor) {
        if (val != 0) {
            throw exceptionConstructor.apply(val + " is not zero");
        }
        return val;
    }

    public static <E extends RuntimeException> long requireNonPositive(long val, Function<String, E> exceptionConstructor) {
        if (val > 0) {
            throw exceptionConstructor.apply(val + " is positive");
        }
        return val;
    }

    public static <E extends RuntimeException> long requireNonNegative(long val, Function<String, E> exceptionConstructor) {
        if (val < 0) {
            throw exceptionConstructor.apply(val + " is negative");
        }
        return val;
    }

    public static <E extends RuntimeException> long requireNonZero(long val, Function<String, E> exceptionConstructor) {
        if (val == 0) {
            throw exceptionConstructor.apply(val + " is zero");
        }
        return val;
    }

    public static <E extends RuntimeException> long requireEquals(long val, long otherVal, Function<String, E> exceptionConstructor) {
        if (val != otherVal) {
            throw exceptionConstructor.apply(val + " is not equal to " + otherVal);
        }
        return val;
    }

    public static <E extends RuntimeException> long requireNotEquals(long val, long otherVal, Function<String, E> exceptionConstructor) {
        if (val == otherVal) {
            throw exceptionConstructor.apply(val + " is equal to " + otherVal);
        }
        return val;
    }

    public static <E extends RuntimeException> long requireInRange(long val, long first, long lastExclusive, Function<String, E> exceptionConstructor) {
        if (val < first || val >= lastExclusive) {
            throw exceptionConstructor.apply(val + " is not in the range [" + first + ", " + lastExclusive + ")");
        }
        return val;
    }

    public static <E extends RuntimeException> long requireInRangeClosed(long val, long first, long lastInclusive, Function<String, E> exceptionConstructor) {
        if (val < first || val > lastInclusive) {
            throw exceptionConstructor.apply(val + " is not in the range [" + first + ", " + lastInclusive + "]");
        }
        return val;
    }

    private LongRangeUtil() {
        throw new UnsupportedOperationException();
    }
}
