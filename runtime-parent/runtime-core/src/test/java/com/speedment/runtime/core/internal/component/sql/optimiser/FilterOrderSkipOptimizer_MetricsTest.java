/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.runtime.core.internal.component.sql.optimiser;

import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.internal.stream.builder.action.reference.FilterAction;
import com.speedment.runtime.core.internal.stream.builder.action.reference.LimitAction;
import com.speedment.runtime.core.internal.stream.builder.action.reference.PeekAction;
import com.speedment.runtime.core.internal.stream.builder.action.reference.SkipAction;
import com.speedment.runtime.core.internal.stream.builder.action.reference.SortedComparatorAction;
import com.speedment.runtime.core.internal.stream.builder.pipeline.PipelineImpl;
import com.speedment.runtime.core.internal.util.testing.Permutations;
import com.speedment.runtime.core.stream.Pipeline;
import com.speedment.runtime.core.stream.action.Action;
import com.speedment.runtime.test_support.MockDbmsType;
import com.speedment.runtime.test_support.MockEntity;
import com.speedment.runtime.test_support.MockEntityUtil;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.BaseStream;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Per Minborg
 */
public class FilterOrderSkipOptimizer_MetricsTest {

    private static final DbmsType DBMS_TYPE = new MockDbmsType();
    private static final Supplier<BaseStream<?, ?>> STREAM_SUPPLIER = () -> MockEntityUtil.stream(2);

    private static final FilterAction<MockEntity> FILTER_ACTION = new FilterAction<>(MockEntity.ID.equal(1));
    private static final SortedComparatorAction<MockEntity> SORTED_ACTION = new SortedComparatorAction<>(MockEntity.NAME.comparator());
    private static final SkipAction<MockEntity> SKIP_ACTION = new SkipAction<>(1);
    private static final LimitAction<MockEntity> LIMIT_ACTION = new LimitAction<>(1);
    private static final PeekAction<MockEntity> PEEK_ACTION = new PeekAction<>(System.out::println);

    private FilterOrderSkipOptimizer<MockEntity> instance;

    @Before
    public void setUp() {
        instance = new FilterOrderSkipOptimizer<>();
    }

    @Test
    public void testFilter1Order1Skip1() {
        final Pipeline pipeline = pipelineOf(FILTER_ACTION, SORTED_ACTION, SKIP_ACTION);
        int metrics = instance.metrics(pipeline, DBMS_TYPE);
        assertEquals(30, metrics);
    }

    @Test
    public void testFilter0Order1Skip1() {
        final Pipeline pipeline = pipelineOf(SORTED_ACTION, SKIP_ACTION);
        int metrics = instance.metrics(pipeline, DBMS_TYPE);
        assertEquals(20, metrics);
    }

    @Test
    public void testFilter1Order0Skip1() {
        final Pipeline pipeline = pipelineOf(FILTER_ACTION, SKIP_ACTION);
        int metrics = instance.metrics(pipeline, DBMS_TYPE);
        assertEquals(20, metrics);
    }

    @Test
    public void testFilter1Order1Skip0() {
        final Pipeline pipeline = pipelineOf(FILTER_ACTION, SORTED_ACTION);
        int metrics = instance.metrics(pipeline, DBMS_TYPE);
        assertEquals(20, metrics);
    }

    @Test
    public void testFilter1Order0Skip0() {
        final Pipeline pipeline = pipelineOf(SORTED_ACTION, SKIP_ACTION);
        int metrics = instance.metrics(pipeline, DBMS_TYPE);
        assertEquals(20, metrics);
    }

    @Test
    public void testFilter0Order1Skip0() {
        final Pipeline pipeline = pipelineOf(FILTER_ACTION, SKIP_ACTION);
        int metrics = instance.metrics(pipeline, DBMS_TYPE);
        assertEquals(20, metrics);
    }

    @Test
    public void testFilter0Order0Skip1() {
        final Pipeline pipeline = pipelineOf(FILTER_ACTION, SORTED_ACTION);
        int metrics = instance.metrics(pipeline, DBMS_TYPE);
        assertEquals(20, metrics);
    }

    @Test
    public void testFilter0Order0Skip0() {
        Pipeline pipeline = pipelineOf();
        int metrics = instance.metrics(pipeline, DBMS_TYPE);
        assertEquals(0, metrics);
    }

    @Test
    public void focus() {
        Pipeline pipeline = pipelineOf(FILTER_ACTION, SKIP_ACTION, SORTED_ACTION, LIMIT_ACTION, PEEK_ACTION);
        int metrics = instance.metrics(pipeline, DBMS_TYPE);
    }

    //// Polution...
    @Test
    public void testPolution() {
        Permutations.of(FILTER_ACTION, SORTED_ACTION, SKIP_ACTION, LIMIT_ACTION, PEEK_ACTION)
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

                assertEquals("Failed for " + l, expected, instance.metrics(pipeline, DBMS_TYPE));
            }
            );
    }

    private int expectedMetrics(List<? extends Action<Stream<MockEntity>, Stream<MockEntity>>> l) {
        if ((l.get(0) == FILTER_ACTION)
            && (l.get(1) == SORTED_ACTION)
            && (l.get(2) == SKIP_ACTION)) {
            return 30;
        }
        if ((l.get(0) == FILTER_ACTION)
            && (l.get(1) == SORTED_ACTION)) {
            return 20;
        }

        if ((l.get(0) == FILTER_ACTION)
            && (l.get(1) == SKIP_ACTION)) {
            return 20;
        }

        if ((l.get(0) == FILTER_ACTION)) {
            return 10;
        }

        if ((l.get(0) == SORTED_ACTION)
            && (l.get(1) == SKIP_ACTION)) {
            return 20;
        }

        if ((l.get(0) == SORTED_ACTION)) {
            return 10;
        }

        if ((l.get(0) == SKIP_ACTION)) {
            return 10;
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
