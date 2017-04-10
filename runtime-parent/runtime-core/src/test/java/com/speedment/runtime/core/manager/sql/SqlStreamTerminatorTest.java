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
package com.speedment.runtime.core.manager.sql;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import com.speedment.runtime.core.db.AsynchronousQueryResult;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.db.FieldPredicateView;
import com.speedment.runtime.core.internal.component.sql.SqlStreamOptimizerComponentImpl;
import com.speedment.runtime.core.internal.manager.sql.SqlPredicateFragmentImpl;
import com.speedment.runtime.core.internal.manager.sql.DefaultSqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.action.reference.FilterAction;
import com.speedment.runtime.core.internal.stream.builder.action.reference.MapAction;
import com.speedment.runtime.core.internal.stream.builder.pipeline.PipelineImpl;
import com.speedment.runtime.core.internal.stream.builder.pipeline.ReferencePipeline;
import com.speedment.runtime.core.stream.action.Action;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.predicate.FieldPredicate;
import com.speedment.runtime.test_support.MockEntity;
import com.speedment.runtime.test_support.MockEntityUtil;
import com.speedment.runtime.typemapper.TypeMapper;
import com.speedment.runtime.typemapper.internal.IdentityTypeMapper;
import java.util.function.Supplier;
import java.util.stream.BaseStream;
import java.util.stream.Stream;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SqlStreamTerminatorTest {

    private static final long SQL_COUNT_RESULT = 100L;
    private static final String SELECT_SQL = "SELECT * FROM table";
    private static final String SELECT_COUNT_SQL = "SELECT COUNT(*) FROM table";
    private static final String PREDICATE_COUNT_SQL_FRAGMENT = "ID is Cool";
    private static final String COUNT_WHERE_SQL = String.join(" WHERE ", SELECT_COUNT_SQL, PREDICATE_COUNT_SQL_FRAGMENT);

    private String lastCountingSql;

    @Test
    public void testCountGeneralFilter() {
        lastCountingSql = null;
        final Action<Stream<MockEntity>, Stream<MockEntity>> filterAction = new FilterAction<>(e -> e.getId() % 10 == 3);
        assertEquals(10, countStreamOf(filterAction));
        assertNull(lastCountingSql);
    }

    @Test
    public void testCountSizePreservingFilter() {
        final Action<Stream<MockEntity>, Stream<Integer>> mapAction = new MapAction<>(MockEntity::getId);
        assertEquals(SQL_COUNT_RESULT, countStreamOf(mapAction));
        assertEquals(SELECT_COUNT_SQL, lastCountingSql);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCountFieldPredicateFilter() {
        @SuppressWarnings("unchecked")
        final TypeMapper<Integer, Integer> typeMapper = new IdentityTypeMapper<>();
        @SuppressWarnings("unchecked")
        final FieldPredicate<MockEntity> predicate = mock(FieldPredicate.class);
        @SuppressWarnings("unchecked")
        final Field<MockEntity> field = mock(Field.class);
        when(field.typeMapper()).thenReturn((TypeMapper) typeMapper);
        when(predicate.getField()).thenReturn(field);
        Action<Stream<MockEntity>, Stream<MockEntity>> filterAction = new FilterAction<>(predicate);
        assertEquals(SQL_COUNT_RESULT, countStreamOf(filterAction));
        assertEquals(COUNT_WHERE_SQL, lastCountingSql);
    }

    private long countStreamOf(Action<?, ?> action) {

        @SuppressWarnings("unchecked")
        final AsynchronousQueryResult<MockEntity> asynchronousQueryResult = mock(AsynchronousQueryResult.class);

        final SqlStreamOptimizerInfo<MockEntity> info = SqlStreamOptimizerInfo.of(
            createDbmsType(),
            SELECT_SQL,
            SELECT_COUNT_SQL,
            (sql, l) -> {
                lastCountingSql = sql;
                return SQL_COUNT_RESULT;
            },
            f -> "",
            f -> Object.class
        );

        DefaultSqlStreamTerminator<MockEntity> terminator = new DefaultSqlStreamTerminator<>(
            info,
            asynchronousQueryResult,
            new SqlStreamOptimizerComponentImpl()
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
        final DbmsType dbmsType = mock(DbmsType.class);
        final FieldPredicateView fpv = mock(FieldPredicateView.class);
        final SqlPredicateFragmentImpl predicateFragment = new SqlPredicateFragmentImpl();
        predicateFragment.setSql(PREDICATE_COUNT_SQL_FRAGMENT);
        when(fpv.transform(any(), any(), any())).thenReturn(predicateFragment);
        when(dbmsType.getFieldPredicateView()).thenReturn(fpv);
        return dbmsType;
    }
}
