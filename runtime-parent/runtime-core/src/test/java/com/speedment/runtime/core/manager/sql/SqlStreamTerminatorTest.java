package com.speedment.runtime.core.manager.sql;

import com.speedment.runtime.core.db.AsynchronousQueryResult;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.db.FieldPredicateView;
import com.speedment.runtime.core.internal.manager.sql.SqlPredicateFragmentImpl;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.action.reference.FilterAction;
import com.speedment.runtime.core.internal.stream.builder.action.reference.MapAction;
import com.speedment.runtime.core.internal.stream.builder.pipeline.PipelineImpl;
import com.speedment.runtime.core.internal.stream.builder.pipeline.ReferencePipeline;
import com.speedment.runtime.core.stream.action.Action;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.predicate.FieldPredicate;
import com.speedment.runtime.typemapper.TypeMapper;
import com.speedment.runtime.typemapper.internal.IdentityTypeMapper;
import org.junit.Test;

import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
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

    private static class MockEntity {
        private int id;

        public MockEntity(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    @Test
    public void testCountGeneralFilter() {
        lastCountingSql = null;
        Action filterAction = new FilterAction<MockEntity>(e -> e.getId() % 10 == 3);
        assertEquals(10, countStreamOf(filterAction));
        assertNull(lastCountingSql);
    }

    @Test
    public void testCountSizePreservingFilter() {
        Action mapAction = new MapAction<>(MockEntity::getId);
        assertEquals(SQL_COUNT_RESULT, countStreamOf(mapAction));
        assertEquals(SELECT_COUNT_SQL, lastCountingSql);
    }

    @Test
    public void testCountFieldPredicateFilter() {
        final TypeMapper typeMapper = new IdentityTypeMapper();
        FieldPredicate predicate = mock(FieldPredicate.class);
        final Field field = mock(Field.class);
        when(field.typeMapper()).thenReturn(typeMapper);
        when(predicate.getField()).thenReturn(field);
        Action filterAction = new FilterAction<>(predicate);
        assertEquals(SQL_COUNT_RESULT, countStreamOf(filterAction));
        assertEquals(COUNT_WHERE_SQL, lastCountingSql);
    }

    private long countStreamOf(Action action) {
        SqlStreamTerminator terminator = new SqlStreamTerminator<>(
            createDbmsType(),
            SELECT_SQL,
            SELECT_COUNT_SQL,
            (sql, l) -> {
                lastCountingSql = sql;
                return SQL_COUNT_RESULT;
            },
            f -> "",
            mock(AsynchronousQueryResult.class)
        );
        return terminator.count(createPipeline(action));
    }

    private ReferencePipeline<MockEntity> createPipeline(Action action) {
        Supplier supplier = mock(Supplier.class);
        Stream<MockEntity> stream = IntStream.range(0, 100).boxed().map(MockEntity::new);
        when(supplier.get()).thenReturn(stream);
        ReferencePipeline<MockEntity> pipeline = new PipelineImpl<>(supplier);
        pipeline.add(action);
        return pipeline;
    }

    private DbmsType createDbmsType() {
        DbmsType dbmsType = mock(DbmsType.class);
        FieldPredicateView fpv = mock(FieldPredicateView.class);
        final SqlPredicateFragmentImpl predicateFragment = new SqlPredicateFragmentImpl();
        predicateFragment.setSql(PREDICATE_COUNT_SQL_FRAGMENT);
        when(fpv.transform(any(), any())).thenReturn(predicateFragment);
        when(dbmsType.getFieldPredicateView()).thenReturn(fpv);
        return dbmsType;
    }
}
