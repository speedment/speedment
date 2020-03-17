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
import com.speedment.runtime.core.stream.Pipeline;
import com.speedment.runtime.core.stream.action.Action;
import com.speedment.runtime.core.stream.parallel.ParallelStrategy;
import com.speedment.runtime.test_support.MockDbmsType;
import com.speedment.runtime.test_support.MockEntity;
import com.speedment.runtime.test_support.MockEntityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.function.Supplier;
import java.util.stream.BaseStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 *
 * @author Per Minborg
 */
class FilterSortedSkipOptimizer_OptimizeTest {

    private static final DbmsType DBMS_TYPE = new MockDbmsType();
    private static final Supplier<BaseStream<?, ?>> STREAM_SUPPLIER = () -> MockEntityUtil.stream(2);

    private static final FilterAction<MockEntity> FILTER_ACTION = new FilterAction<>(MockEntity.ID.equal(1));
    private static final SortedComparatorAction<MockEntity> SORTED_ACTION = new SortedComparatorAction<>(MockEntity.NAME.comparator());
    private static final SkipAction<MockEntity> SKIP_ACTION = new SkipAction<>(1);
    private static final LimitAction<MockEntity> LIMIT_ACTION = new LimitAction<>(1);
    private static final PeekAction<MockEntity> PEEK_ACTION = new PeekAction<>(System.out::println);

    private FilterSortedSkipOptimizer<MockEntity> instance;
    private AsynchronousQueryResult<MockEntity> asynchronousQueryResult;
    private SqlStreamOptimizerInfo<MockEntity> sqlStreamOptimizerInfo;

    @BeforeEach
    void setUp() {
        instance = new FilterSortedSkipOptimizer<>();
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
            f -> f.identifier().getColumnId(),
            f -> Object.class
        );
    }

    @Test
    void testFilter1Order1Skip1() {
        assertDoesNotThrow(() -> {
            final Pipeline pipeline = pipelineOf(FILTER_ACTION, SORTED_ACTION, SKIP_ACTION);
            printInfo("Before", pipeline, asynchronousQueryResult);
            Pipeline newPipeline = instance.optimize(pipeline, sqlStreamOptimizerInfo, asynchronousQueryResult);
            printInfo("After", newPipeline, asynchronousQueryResult);
        });
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
