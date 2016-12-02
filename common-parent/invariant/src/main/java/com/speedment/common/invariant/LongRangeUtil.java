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
            throw new IllegalArgumentException(val + " is not positive");
        }
        return val;
    }

    public static long requireZero(long val) {
        if (val != 0) {
            throw new IllegalArgumentException(val + " is not zero");
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
            throw exceptionConstructor.apply(val + " is not positive");
        }
        return val;
    }

    public static <E extends RuntimeException> long requireZero(long val, Function<String, E> exceptionConstructor) {
        if (val != 0) {
            throw exceptionConstructor.apply(val + " is not zero");
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

    public LongRangeUtil() {
        throw new UnsupportedOperationException();
    }
}
