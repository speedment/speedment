/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.runtime.core.internal.component.sql.optimiser;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import com.speedment.runtime.core.db.AsynchronousQueryResult;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.internal.db.AsynchronousQueryResultImpl;
import com.speedment.runtime.core.internal.stream.builder.action.reference.FilterAction;
import com.speedment.runtime.core.internal.stream.builder.action.reference.LimitAction;
import com.speedment.runtime.core.internal.stream.builder.action.reference.PeekAction;
import com.speedment.runtime.core.internal.stream.builder.action.reference.SkipAction;
import com.speedment.runtime.core.internal.stream.builder.action.reference.SortedComparatorAction;
import com.speedment.runtime.core.internal.stream.builder.pipeline.PipelineImpl;
import com.speedment.runtime.core.internal.util.testing.Permutations;
import com.speedment.runtime.core.stream.Pipeline;
import com.speedment.runtime.core.stream.action.Action;
import com.speedment.runtime.core.stream.parallel.ParallelStrategy;
import com.speedment.runtime.test_support.MockDbmsType;
import com.speedment.runtime.test_support.MockEntity;
import com.speedment.runtime.test_support.MockEntityUtil;
import java.util.ArrayList;
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
public class FilterOrderSkipOptimizer_OptimizeTest {

    private static final DbmsType DBMS_TYPE = new MockDbmsType();
    private static final Supplier<BaseStream<?, ?>> STREAM_SUPPLIER = () -> MockEntityUtil.stream(2);

    private static final FilterAction<MockEntity> FILTER_ACTION = new FilterAction<>(MockEntity.ID.equal(1));
    private static final SortedComparatorAction<MockEntity> SORTED_ACTION = new SortedComparatorAction<>(MockEntity.NAME.comparator());
    private static final SkipAction<MockEntity> SKIP_ACTION = new SkipAction<>(1);
    private static final LimitAction<MockEntity> LIMIT_ACTION = new LimitAction<>(1);
    private static final PeekAction<MockEntity> PEEK_ACTION = new PeekAction<>(System.out::println);

    private FilterOrderSkipOptimizer<MockEntity> instance;
    private AsynchronousQueryResult<MockEntity> asynchronousQueryResult;
    private SqlStreamOptimizerInfo<MockEntity> sqlStreamOptimizerInfo;

    @Before
    public void setUp() {
        instance = new FilterOrderSkipOptimizer<>();
        asynchronousQueryResult = new AsynchronousQueryResultImpl<>(
            "SELECT id, name from mock_entity",
            new ArrayList<>(),
            (rs) -> new MockEntity(1),
            () -> null,
            ParallelStrategy.computeIntensityDefault(),
            (st) -> {
            },
            (rs) -> {
            }
        );

        sqlStreamOptimizerInfo = SqlStreamOptimizerInfo.of(
            DBMS_TYPE,
            "SELECT id, name from mock_entity",
            "SELECT count(*) from mock_entity",
            (sql, l) -> {

                return 1l;
            },
            f -> f.identifier().getColumnName(),
            f -> Object.class
        );
    }

    @Test
    public void testFilter1Order1Skip1() {
        final Pipeline pipeline = pipelineOf(FILTER_ACTION, SORTED_ACTION, SKIP_ACTION);
        printInfo("Before", pipeline, asynchronousQueryResult);
        Pipeline newPipeline = instance.optimize(pipeline, sqlStreamOptimizerInfo, asynchronousQueryResult);
        printInfo("After", newPipeline, asynchronousQueryResult);
    }

    private Pipeline pipelineOf(Action<?, ?>... actions) {
        return Stream.of(actions)
            .collect(
                () -> new PipelineImpl<>(STREAM_SUPPLIER),
                PipelineImpl::addLast,
                (a, b) -> b.stream().forEachOrdered(a::add)
            );
    }

    private void printInfo(String msg, Pipeline pipeline, AsynchronousQueryResult<MockEntity> query) {
        System.out.format("%s Pipeline: %s SQL: %s %n", msg, pipeline.toString(), query.getSql());
    }

}
