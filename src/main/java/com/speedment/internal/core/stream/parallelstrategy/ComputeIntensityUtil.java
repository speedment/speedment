package com.speedment.internal.core.stream.parallelstrategy;

import java.util.stream.IntStream;

/**
 *
 * @author pemi
 */
public final class ComputeIntensityUtil {

    static IntStream repeatOnHalfAvailableProcessors(int item) {
        return repeat(item, availableProcessors() / 2);
    }

    static int toThePowerOfTwo(int x) {
        return 1 << x;
    }

    static int times256(int x) {
        return x * 256;
    }

    static int availableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

    static IntStream repeat(int item, int times) {
        return IntStream.generate(() -> item).limit(times);
    }

    private ComputeIntensityUtil() {
    }

}
