package com.speedment.runtime.join.internal.component.join;

import com.speedment.common.function.Function5;
import com.speedment.common.function.Function6;
import com.speedment.common.function.QuadFunction;
import com.speedment.common.function.TriFunction;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.JoinStreamSupplierComponent;
import com.speedment.runtime.join.pipeline.Pipeline;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import org.junit.Test;

/**
 *
 * @author Per Minborg
 */
public class JoinBuilderTest {

    private static final JoinStreamSupplierComponent EMPTY_STREAM_SUPPLIER = new EmptyJoinStreamSupplierComponent();

    @Test
    public void testJoinBuilder() {
        final JoinBuilder1Impl<T1> jb1 = new JoinBuilder1Impl<>(EMPTY_STREAM_SUPPLIER, T1Manager.IDENTIFIER);

    }

    interface T1Manager {

        static TableIdentifier<T1> IDENTIFIER = id("t1");
    }

    interface T2Manager {

        static TableIdentifier<T1> IDENTIFIER = id("t2");
    }

    interface T3Manager {

        static TableIdentifier<T1> IDENTIFIER = id("t3");
    }

    interface T4Manager {

        static TableIdentifier<T1> IDENTIFIER = id("t4");
    }

    interface T5Manager {

        static TableIdentifier<T1> IDENTIFIER = id("t5");
    }

    interface T6Manager {

        static TableIdentifier<T1> IDENTIFIER = id("t6");
    }

    interface T1 {

    }

    interface T2 {

    }

    interface T3 {

    }

    interface T4 {

    }

    interface T5 {

    }

    interface T6 {

    }

    private static <T> TableIdentifier<T> id(String tableName) {
        return TableIdentifier.of("db", "schema", tableName);
    }

    private static class EmptyJoinStreamSupplierComponent implements JoinStreamSupplierComponent {

        @Override
        public <T1, T2, T> Join<T> createJoin(Pipeline p, BiFunction<T1, T2, T> constructor, TableIdentifier<T1> t1, TableIdentifier<T2> t2) {
            return empty();
        }

        @Override
        public <T1, T2, T3, T> Join<T> createJoin(Pipeline p, TriFunction<T1, T2, T3, T> constructor, TableIdentifier<T1> t1, TableIdentifier<T2> t2, TableIdentifier<T3> t3) {
            return empty();
        }

        @Override
        public <T1, T2, T3, T4, T> Join<T> createJoin(Pipeline p, QuadFunction<T1, T2, T3, T4, T> constructor, TableIdentifier<T1> t1, TableIdentifier<T2> t2, TableIdentifier<T3> t3, TableIdentifier<T4> t4) {
            return empty();
        }

        @Override
        public <T1, T2, T3, T4, T5, T> Join<T> createJoin(Pipeline p, Function5<T1, T2, T3, T4, T5, T> constructor, TableIdentifier<T1> t1, TableIdentifier<T2> t2, TableIdentifier<T3> t3, TableIdentifier<T4> t4, TableIdentifier<T5> t5) {
            return empty();
        }

        @Override
        public <T1, T2, T3, T4, T5, T6, T> Join<T> createJoin(Pipeline p, Function6<T1, T2, T3, T4, T5, T6, T> constructor, TableIdentifier<T1> t1, TableIdentifier<T2> t2, TableIdentifier<T3> t3, TableIdentifier<T4> t4, TableIdentifier<T5> t5, TableIdentifier<T6> t6) {
            return empty();
        }

        private <T> Join<T> empty() {
            return new Join<T>() {
                @Override
                public Stream<T> stream() {
                    return Stream.empty();
                }

            };
        }
    }
}
