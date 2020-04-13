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
package com.speedment.runtime.core.internal.component.sql.optimizer;

import com.speedment.runtime.core.internal.component.sql.optimizer.FilterSortedSkipOptimizer;
import com.speedment.runtime.core.component.sql.Metrics;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.internal.stream.builder.action.reference.FilterAction;
import com.speedment.runtime.core.internal.stream.builder.action.reference.LimitAction;
import com.speedment.runtime.core.internal.stream.builder.action.reference.PeekAction;
import com.speedment.runtime.core.internal.stream.builder.action.reference.SkipAction;
import com.speedment.runtime.core.internal.stream.builder.action.reference.SortedComparatorAction;
import com.speedment.runtime.core.internal.stream.builder.pipeline.PipelineImpl;
import com.speedment.common.combinatorics.Permutation;
import com.speedment.runtime.core.stream.Pipeline;
import com.speedment.runtime.core.stream.action.Action;
import com.speedment.runtime.test_support.MockDbmsType;
import com.speedment.runtime.test_support.MockEntity;
import com.speedment.runtime.test_support.MockEntityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.BaseStream;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
public class FilterSortedSkipOptimizer_MetricsTest {

    private static final DbmsType DBMS_TYPE = new MockDbmsType();
    private static final Supplier<BaseStream<?, ?>> STREAM_SUPPLIER = () -> MockEntityUtil.stream(2);

    private static final FilterAction<MockEntity> FILTER_ACTION = new FilterAction<>(MockEntity.ID.equal(1));
    private static final SortedComparatorAction<MockEntity> SORTED_ACTION = new SortedComparatorAction<>(MockEntity.NAME.comparator());
    private static final SkipAction<MockEntity> SKIP_ACTION = new SkipAction<>(1);
    private static final LimitAction<MockEntity> LIMIT_ACTION = new LimitAction<>(1);
    private static final PeekAction<MockEntity> PEEK_ACTION = new PeekAction<>(System.out::println);

    private static final SortedComparatorAction<MockEntity> SORTED_ACTION_MULTI = new SortedComparatorAction<>(MockEntity.NAME.comparator().thenComparing(MockEntity.ID.comparator()));
    private static final SortedComparatorAction<MockEntity> SORTED_ACTION_MULTI_VARIANT = new SortedComparatorAction<>(MockEntity.NAME.comparator().thenComparingInt(MockEntity.ID.getter()));

    private FilterSortedSkipOptimizer<MockEntity> instance;

    @BeforeEach
    public void setUp() {
        instance = new FilterSortedSkipOptimizer<>();
    }

    @Test
    public void testFilter1Order1Skip1() {
        final Pipeline pipeline = pipelineOf(FILTER_ACTION, SORTED_ACTION, SKIP_ACTION);
        final Metrics metrics = instance.metrics(pipeline, DBMS_TYPE);
        assertEquals(3, metrics.getPipelineReductions());
    }

    @Test
    public void testFilter1OrderMulti1Skip1() {
        final Pipeline pipeline = pipelineOf(FILTER_ACTION, SORTED_ACTION_MULTI, SKIP_ACTION);
        final Metrics metrics = instance.metrics(pipeline, DBMS_TYPE);
        assertEquals(3, metrics.getPipelineReductions());
    }
    
    @Test
    public void testFilter1OrderMultiVariant1Skip1() {
        final Pipeline pipeline = pipelineOf(FILTER_ACTION, SORTED_ACTION_MULTI_VARIANT, SKIP_ACTION);
        final Metrics metrics = instance.metrics(pipeline, DBMS_TYPE);
        assertEquals(3, metrics.getPipelineReductions());
    }

    @Test
    public void testFilter0Order1Skip1() {
        final Pipeline pipeline = pipelineOf(SORTED_ACTION, SKIP_ACTION);
        final Metrics metrics = instance.metrics(pipeline, DBMS_TYPE);
        assertEquals(2, metrics.getPipelineReductions());
    }

    @Test
    public void testFilter1Order0Skip1() {
        final Pipeline pipeline = pipelineOf(FILTER_ACTION, SKIP_ACTION);
        final Metrics metrics = instance.metrics(pipeline, DBMS_TYPE);
        assertEquals(2, metrics.getPipelineReductions());
    }

    @Test
    public void testFilter1Order1Skip0() {
        final Pipeline pipeline = pipelineOf(FILTER_ACTION, SORTED_ACTION);
        final Metrics metrics = instance.metrics(pipeline, DBMS_TYPE);
        assertEquals(2, metrics.getPipelineReductions());
    }

    @Test
    public void testFilter1Order0Skip0() {
        final Pipeline pipeline = pipelineOf(SORTED_ACTION, SKIP_ACTION);
        final Metrics metrics = instance.metrics(pipeline, DBMS_TYPE);
        assertEquals(2, metrics.getPipelineReductions());
    }

    @Test
    public void testFilter0Order1Skip0() {
        final Pipeline pipeline = pipelineOf(FILTER_ACTION, SKIP_ACTION);
        final Metrics metrics = instance.metrics(pipeline, DBMS_TYPE);
        assertEquals(2, metrics.getPipelineReductions());
    }

    @Test
    public void testFilter0Order0Skip1() {
        final Pipeline pipeline = pipelineOf(FILTER_ACTION, SORTED_ACTION);
        final Metrics metrics = instance.metrics(pipeline, DBMS_TYPE);
        assertEquals(2, metrics.getPipelineReductions());
    }

    @Test
    public void testFilter0Order0Skip0() {
        final Pipeline pipeline = pipelineOf();
        final Metrics metrics = instance.metrics(pipeline, DBMS_TYPE);
        assertEquals(0, metrics.getPipelineReductions());
    }

    @Test
    public void testSkip0LimitFilter0Order0() {
        final Pipeline pipeline = pipelineOf(SKIP_ACTION, LIMIT_ACTION, FILTER_ACTION, SORTED_ACTION);
        final Metrics metrics = instance.metrics(pipeline, DBMS_TYPE);
        assertEquals(2, metrics.getPipelineReductions());
    }

    @Test
    public void focus() {
        final Pipeline pipeline = pipelineOf(FILTER_ACTION, SKIP_ACTION, SORTED_ACTION, LIMIT_ACTION, PEEK_ACTION);
        final Metrics metrics = instance.metrics(pipeline, DBMS_TYPE);
        assertEquals(2, metrics.getPipelineReductions());
    }

    //// Polution...
    @Test
    public void testPolution() {
        Permutation.of(FILTER_ACTION, SORTED_ACTION, SKIP_ACTION, LIMIT_ACTION, PEEK_ACTION)
            .map(s -> s.collect(toList()))
            .forEachOrdered(l -> {
                int expected = expectedMetrics(l);

//                
//                if (l.get(0) == FILTER_ACTION) {
//                    expected += 10;
//                    if (l.get(1) == SORTED_ACTION) {
//                        expected += 10;
//                        if (l.get(2) == SKIP_ACTION) {
//                            expected += 10;
//                        }
//                    }
//                }
//                expected = expected == 0 ? Integer.MIN_VALUE : expected;
                Pipeline pipeline = pipelineOf(l.stream().toArray(Action[]::new));

                assertEquals( expected, instance.metrics(pipeline, DBMS_TYPE).getPipelineReductions(), "Failed for " + l);
            }
            );
    }

    private int expectedMetrics(List<? extends Action<Stream<MockEntity>, Stream<MockEntity>>> l) {

        // Filter first
        if ((l.get(0) == FILTER_ACTION)
            && (l.get(1) == SORTED_ACTION)
            && (l.get(2) == SKIP_ACTION)
            && (l.get(3) == LIMIT_ACTION)) {
            return 4;
        }

        if ((l.get(0) == FILTER_ACTION)
            && (l.get(1) == SORTED_ACTION)
            && (l.get(2) == SKIP_ACTION)) {
            return 3;
        }

        if ((l.get(0) == FILTER_ACTION)
            && (l.get(1) == SORTED_ACTION)
            && (l.get(2) == LIMIT_ACTION)) {
            return 3;
        }

        if ((l.get(0) == FILTER_ACTION)
            && (l.get(1) == SORTED_ACTION)) {
            return 2;
        }

        if ((l.get(0) == FILTER_ACTION)
            && (l.get(1) == SKIP_ACTION)
            && (l.get(2) == LIMIT_ACTION)) {
            return 3;
        }
        if ((l.get(0) == FILTER_ACTION)
            && (l.get(1) == SKIP_ACTION)) {
            return 2;
        }

        if ((l.get(0) == FILTER_ACTION)
            && (l.get(1) == LIMIT_ACTION)) {
            return 2;
        }

        if ((l.get(0) == FILTER_ACTION)) {
            return 1;
        }

        // Sorted first
        if ((l.get(0) == SORTED_ACTION)
            && (l.get(1) == FILTER_ACTION)
            && (l.get(2) == SKIP_ACTION)
            && (l.get(3) == LIMIT_ACTION)) {
            return 4;
        }

        if ((l.get(0) == SORTED_ACTION)
            && (l.get(1) == FILTER_ACTION)
            && (l.get(2) == SKIP_ACTION)) {
            return 3;
        }

        if ((l.get(0) == SORTED_ACTION)
            && (l.get(1) == FILTER_ACTION)
            && (l.get(2) == LIMIT_ACTION)) {
            return 3;
        }
        if ((l.get(0) == SORTED_ACTION)
            && (l.get(1) == FILTER_ACTION)
            && (l.get(2) == LIMIT_ACTION)) {
            return 2;
        }
        if ((l.get(0) == SORTED_ACTION)
            && (l.get(1) == FILTER_ACTION)) {
            return 2;
        }

        if ((l.get(0) == SORTED_ACTION)
            && (l.get(1) == SKIP_ACTION)
            && (l.get(2) == LIMIT_ACTION)) {
            return 3;
        }

        if ((l.get(0) == SORTED_ACTION)
            && (l.get(1) == SKIP_ACTION)) {
            return 2;
        }

        if ((l.get(0) == SORTED_ACTION)
            && (l.get(1) == LIMIT_ACTION)) {
            return 2;
        }

        if ((l.get(0) == SORTED_ACTION)) {
            return 1;
        }

        // Skip first
        if ((l.get(0) == SKIP_ACTION)
            && (l.get(1) == LIMIT_ACTION)) {
            return 2;
        }
        if ((l.get(0) == SKIP_ACTION)) {
            return 1;
        }

        // Limit first
        if ((l.get(0) == LIMIT_ACTION)) {
            return 1;
        }

        return 0;
    }

    private Pipeline pipelineOf(Action<?, ?>... actions) {
        return Stream.of(actions)
            .collect(
                () -> new PipelineImpl<>(STREAM_SUPPLIER),
                PipelineImpl::addLast,
                (a, b) -> b.stream().forEachOrdered(a::add)
            );
    }

}
