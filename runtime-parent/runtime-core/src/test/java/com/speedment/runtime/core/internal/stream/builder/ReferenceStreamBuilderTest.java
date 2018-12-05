/**
 *
 * Copyright (c) 2006-2018, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.core.internal.stream.builder;

import com.speedment.runtime.core.internal.stream.builder.pipeline.PipelineImpl;
import com.speedment.runtime.core.internal.stream.builder.streamterminator.StreamTerminator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.fail;

/**
 *
 * @author Per Minborg
 */
final class ReferenceStreamBuilderTest {

    private ReferenceStreamBuilder<String> instance;

    @BeforeEach
    void setUp() {
        final PipelineImpl<String> pipeline = new PipelineImpl<>(() -> Stream.of("A", "B"));
        final StreamTerminator streamTerminator = new MockStreamTerminator();
        instance = new ReferenceStreamBuilder<>(pipeline, streamTerminator);
    }


    @Test
    void testIssue384() {
        final Stream<String> s0 = instance;
        final Stream<String> s1 = s0.map(s -> s + " Svensson");
        try {
            s0.count();
            fail("s0 could be reused");
        } catch (IllegalStateException e) {
            // Expected
        }

        s1.count();
    }

    @Test
    @Disabled
    void testFilter() {
    }

    @Test
    @Disabled
    void testMap() {
    }

    @Test
    @Disabled
    void testMapToInt() {
    }

    @Test
    @Disabled
    void testMapToLong() {
    }

    @Test
    @Disabled
    void testMapToDouble() {
    }

    @Test
    @Disabled
    void testFlatMap() {
    }

    @Test
    @Disabled
    void testFlatMapToInt() {
    }

    @Test
    @Disabled
    void testFlatMapToLong() {
    }

    @Test
    @Disabled
    void testFlatMapToDouble() {
    }

    @Test
    @Disabled
    void testDistinct() {
    }

    @Test
    @Disabled
    void testSorted_0args() {
    }

    @Test
    @Disabled
    void testSorted_Comparator() {
    }

    @Test
    @Disabled
    void testPeek() {
    }

    @Test
    @Disabled
    void testLimit() {
    }

    @Test
    @Disabled
    void testSkip() {
    }

    @Test
    @Disabled
    void testTakeWhile() {
    }

    @Test
    @Disabled
    void testDropWhile() {
    }

    @Test
    @Disabled
    void testForEach() {
    }

    @Test
    @Disabled
    void testForEachOrdered() {
    }

    @Test
    @Disabled
    void testToArray_0args() {
    }

    @Test
    @Disabled
    void testToArray_IntFunction() {
    }

    @Test
    @Disabled
    void testReduce_GenericType_BinaryOperator() {
    }

    @Test
    @Disabled
    void testReduce_BinaryOperator() {
    }

    @Test
    @Disabled
    void testReduce_3args() {
    }

    @Test
    @Disabled
    void testCollect_3args() {
    }

    @Test
    @Disabled
    void testCollect_Collector() {
    }

    @Test
    @Disabled
    void testMin() {
    }

    @Test
    @Disabled
    void testMax() {
    }

    @Test
    @Disabled
    void testCount() {
    }

    @Test
    @Disabled
    void testAnyMatch() {
    }

    @Test
    @Disabled
    void testAllMatch() {
    }

    @Test
    @Disabled
    void testNoneMatch() {
    }

    @Test
    @Disabled
    void testFindFirst() {
    }

    @Test
    @Disabled
    void testFindAny() {
    }

    @Test
    @Disabled
    void testIterator() {
    }

    @Test
    @Disabled
    void testSpliterator() {
    }

    private class MockStreamTerminator implements StreamTerminator {

    }

}
