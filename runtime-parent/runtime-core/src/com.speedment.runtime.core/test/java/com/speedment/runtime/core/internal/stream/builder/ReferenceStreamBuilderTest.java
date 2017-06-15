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
package com.speedment.runtime.core.internal.stream.builder;

import com.speedment.runtime.core.internal.stream.builder.pipeline.PipelineImpl;
import com.speedment.runtime.core.internal.stream.builder.streamterminator.StreamTerminator;
import java.util.stream.Stream;
import org.junit.After;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Per Minborg
 */
public class ReferenceStreamBuilderTest {

    private ReferenceStreamBuilder<String> instance;

    @Before
    public void setUp() {
        final PipelineImpl<String> pipeline = new PipelineImpl<>(() -> Stream.of("A", "B"));
        final StreamTerminator streamTerminator = new MockStreamTerminator();
        instance = new ReferenceStreamBuilder<>(pipeline, streamTerminator);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testIssue384() {
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
    public void testFilter() {
    }

    @Test
    public void testMap() {
    }

    @Test
    public void testMapToInt() {
    }

    @Test
    public void testMapToLong() {
    }

    @Test
    public void testMapToDouble() {
    }

    @Test
    public void testFlatMap() {
    }

    @Test
    public void testFlatMapToInt() {
    }

    @Test
    public void testFlatMapToLong() {
    }

    @Test
    public void testFlatMapToDouble() {
    }

    @Test
    public void testDistinct() {
    }

    @Test
    public void testSorted_0args() {
    }

    @Test
    public void testSorted_Comparator() {
    }

    @Test
    public void testPeek() {
    }

    @Test
    public void testLimit() {
    }

    @Test
    public void testSkip() {
    }

    @Test
    public void testTakeWhile() {
    }

    @Test
    public void testDropWhile() {
    }

    @Test
    public void testForEach() {
    }

    @Test
    public void testForEachOrdered() {
    }

    @Test
    public void testToArray_0args() {
    }

    @Test
    public void testToArray_IntFunction() {
    }

    @Test
    public void testReduce_GenericType_BinaryOperator() {
    }

    @Test
    public void testReduce_BinaryOperator() {
    }

    @Test
    public void testReduce_3args() {
    }

    @Test
    public void testCollect_3args() {
    }

    @Test
    public void testCollect_Collector() {
    }

    @Test
    public void testMin() {
    }

    @Test
    public void testMax() {
    }

    @Test
    public void testCount() {
    }

    @Test
    public void testAnyMatch() {
    }

    @Test
    public void testAllMatch() {
    }

    @Test
    public void testNoneMatch() {
    }

    @Test
    public void testFindFirst() {
    }

    @Test
    public void testFindAny() {
    }

    @Test
    public void testIterator() {
    }

    @Test
    public void testSpliterator() {
    }

    private class MockStreamTerminator implements StreamTerminator {

    }

}
