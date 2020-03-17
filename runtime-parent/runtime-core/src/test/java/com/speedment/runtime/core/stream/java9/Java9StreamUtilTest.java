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
package com.speedment.runtime.core.stream.java9;

import com.speedment.runtime.core.internal.util.testing.JavaVersionUtil;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
final class Java9StreamUtilTest {

    private static final Supplier<Stream<String>> STREAM_SUPPLIER = () -> Stream.of("a", "b", "c", "d", "e", "a");
    private static final Predicate<String> LESS_THAN_C = s -> "c".compareTo(s) > 0;

    @Test
    void testPredicate() {
        assertDoesNotThrow(() ->
            STREAM_SUPPLIER.get().filter(LESS_THAN_C).forEach(System.out::println)
        );
    }

    @Test
    void testTakeWhile() {
        if (JavaVersionUtil.is8()) {
            try {
                Java9StreamUtil.takeWhile(STREAM_SUPPLIER.get(), LESS_THAN_C);
                fail("takeWhile should not be supported by this Java version");
            } catch (UnsupportedOperationException e) {
                // Expected for Java 8
            }

        } else {
            final List<String> expected = Arrays.asList("a", "b");
            final List<String> actual
                = Java9StreamUtil.takeWhile(STREAM_SUPPLIER.get(), LESS_THAN_C).collect(toList());

            assertEquals(expected, actual);
        }
    }

    @Test
    void testDropWhile() {
        if (JavaVersionUtil.is8()) {
            try {
                Java9StreamUtil.dropWhile(STREAM_SUPPLIER.get(), LESS_THAN_C);
                fail("dropWhile should not be supported by this Java version");
            } catch (UnsupportedOperationException e) {
                // Expected for Java 8
            }

        } else {
            final List<String> expected = Arrays.asList("c", "d", "e", "a");
            final List<String> actual
                = Java9StreamUtil.dropWhile(STREAM_SUPPLIER.get(), LESS_THAN_C).collect(toList());

            assertEquals(expected, actual);
        }
    }

    @Test
    void testFilter() {
        
        final List<String> expected = STREAM_SUPPLIER.get()
            .filter(LESS_THAN_C)
            .collect(toList());

        final List<String> actual = Java9StreamUtil.filter(STREAM_SUPPLIER.get(), LESS_THAN_C)
            .collect(toList());

        assertEquals(expected, actual);

    }

}
