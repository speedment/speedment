package com.speedment.runtime.test_support;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
public final class MockEntityUtil {

    public static Stream<MockEntity> stream(int elements) {
        return IntStream.range(0, elements).mapToObj(MockEntity::new);
    }

    private MockEntityUtil() {
    }

}
