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
package com.speedment.runtime.core.manager.sql;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import com.speedment.runtime.core.db.AsynchronousQueryResult;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.internal.component.sql.SqlStreamOptimizerComponentImpl;
import com.speedment.runtime.core.internal.component.sql.SqlTracer;
import com.speedment.runtime.core.internal.component.sql.override.SqlStreamTerminatorComponentImpl;
import com.speedment.runtime.core.internal.db.AsynchronousQueryResultImpl;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.action.reference.FilterAction;
import com.speedment.runtime.core.internal.stream.builder.action.reference.MapAction;
import com.speedment.runtime.core.internal.stream.builder.pipeline.PipelineImpl;
import com.speedment.runtime.core.internal.stream.builder.pipeline.ReferencePipeline;
import com.speedment.runtime.core.stream.action.Action;
import com.speedment.runtime.core.stream.parallel.ParallelStrategy;
import com.speedment.runtime.test_support.MockDbmsType;
import com.speedment.runtime.test_support.MockEntity;
import com.speedment.runtime.test_support.MockEntityUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.BaseStream;
import java.util.stream.Stream;

final class SqlStreamTerminatorTest {

    private static final long SQL_COUNT_RESULT = 100L;
    private static final String SELECT_SQL = "SELECT * FROM table";
    private static final String SELECT_COUNT_SQL = "SELECT COUNT(*) FROM table";
    private static final String PREDICATE_COUNT_SQL_FRAGMENT = "(name = ?)";
    private static final String COUNT_WHERE_SQL = String.join(" WHERE ", SELECT_SQL, PREDICATE_COUNT_SQL_FRAGMENT);

    private String lastCountingSql;
    private List<Object> lastCountingValues;

    @Test
    void testCountGeneralFilter() {
        lastCountingSql = null;
        final Action<Stream<MockEntity>, Stream<MockEntity>> filterAction = new FilterAction<>(e -> e.getId() % 10 == 3);
        assertEquals(10, countStreamOf(filterAction));
        assertNull(lastCountingSql);
    }

    @Test
    void testCountSizePreservingFilter() {
        final Action<Stream<MockEntity>, Stream<Integer>> mapAction = new MapAction<>(MockEntity::getId);
        assertEquals(SQL_COUNT_RESULT, countStreamOf(mapAction));
        assertEquals(SELECT_COUNT_SQL, lastCountingSql);
    }

    @Test
    @SuppressWarnings("unchecked")
    void testCountFieldPredicateFilter() {
        final Predicate<MockEntity> predicate = MockEntity.NAME.equal("ABBA");
        final Action<Stream<MockEntity>, Stream<MockEntity>> filterAction = new FilterAction<>(predicate);
        assertEquals(SQL_COUNT_RESULT, countStreamOf(filterAction));
        assertEquals(makeCountSql(COUNT_WHERE_SQL), lastCountingSql);
        assertEquals(singletonList("ABBA"), lastCountingValues);
    }

    @Test
    @SuppressWarnings("unchecked")
    void testCountFieldPredicateFilterPolluted() {
        final Predicate<MockEntity> predicate = MockEntity.NAME.equal("ABBA").or(me -> me.getName().equals("Olle"));
        final Action<Stream<MockEntity>, Stream<MockEntity>> filterAction = new FilterAction<>(predicate);
        assertEquals(0, countStreamOf(filterAction));
        assertNull(lastCountingSql); // Make sure counter was not called
        assertNull(lastCountingValues);
    }

    private String makeCountSql(String sql) {
        return "SELECT COUNT(*) FROM (" + sql + ") AS A";
    }

    private long countStreamOf(Action<?, ?> action) {

        @SuppressWarnings("unchecked")
        final AsynchronousQueryResult<MockEntity> asynchronousQueryResult = new AsynchronousQueryResultImpl<>(
            SELECT_SQL,
            new ArrayList<>(),
            rs -> new MockEntity(1),
            () -> null, // getConnection()
            ParallelStrategy.computeIntensityDefault(),
            (ps) -> {
            },
            (rs) -> {
            }
        );

        final SqlStreamOptimizerInfo<MockEntity> info = SqlStreamOptimizerInfo.of(
            createDbmsType(),
            SELECT_SQL,
            SELECT_COUNT_SQL,
            (sql, l) -> {
                lastCountingSql = sql;
                lastCountingValues = l;
                return SQL_COUNT_RESULT;
            },
            f -> f.identifier().getColumnId(),
            f -> Object.class
        );

        SqlStreamTerminator<MockEntity> terminator = new SqlStreamTerminator<>(
            info,
            asynchronousQueryResult,
            new SqlStreamOptimizerComponentImpl(),
            new SqlStreamTerminatorComponentImpl(),
            SqlTracer.from(null),
            true
        );
        return terminator.count(createPipeline(action));
    }

    private ReferencePipeline<MockEntity> createPipeline(Action<?, ?> action) {
        @SuppressWarnings("unchecked")
        final Supplier<Stream<MockEntity>> supplier = mock(Supplier.class);
        final Stream<MockEntity> stream = MockEntityUtil.stream((int) SQL_COUNT_RESULT);
        when(supplier.get()).thenReturn(stream);
        @SuppressWarnings("unchecked")
        final ReferencePipeline<MockEntity> pipeline = new PipelineImpl<>((Supplier<BaseStream<?, ?>>) (Object) supplier);
        pipeline.add(action);
        return pipeline;
    }

    private DbmsType createDbmsType() {
        return new MockDbmsType();
//        final DbmsType dbmsType = mock(DbmsType.class);
//        final FieldPredicateView fpv = mock(FieldPredicateView.class);
//        final SqlPredicateFragmentImpl predicateFragment = new SqlPredicateFragmentImpl();
//        predicateFragment.setSql(PREDICATE_COUNT_SQL_FRAGMENT);
//        when(fpv.transform(any(), any(), any())).thenReturn(predicateFragment);
//        when(dbmsType.getFieldPredicateView()).thenReturn(fpv);
//        return dbmsType;
    }
}
