package com.speedment.runtime.join.internal.component.stream;

import com.speedment.common.function.TriFunction;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.JoinStreamSupplierComponent;
import com.speedment.runtime.join.pipeline.Pipeline;
import static java.util.Objects.requireNonNull;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
public class DebugJoinStreamSupplierComponent implements JoinStreamSupplierComponent {

    @Override
    public <T1, T2, T> Join<T> create(
        final Pipeline p,
        final BiFunction<T1, T2, T> constructor,
        final TableIdentifier<T1> t1,
        final TableIdentifier<T2> t2
    ) {
        requireNonNull(p);
        requireNonNull(constructor);
        requireNonNull(t1);
        requireNonNull(t2);

        printPipeline(p);
        return new EmptyJoin<>();
    }

    @Override
    public <T1, T2, T3, T> Join<T> create(
        final Pipeline p,
        final TriFunction<T1, T2, T3, T> constructor,
        final TableIdentifier<T1> t1,
        final TableIdentifier<T2> t2,
        final TableIdentifier<T3> t3
    ) {
        requireNonNull(p);
        requireNonNull(constructor);
        requireNonNull(t1);
        requireNonNull(t2);
        requireNonNull(t3);

        printPipeline(p);
        return new EmptyJoin<>();
    }

    private void printPipeline(Pipeline p) {
        System.out.println("Pipeline:");
        p.stages()
            .map(Object::toString)
            .forEachOrdered(System.out::println);
    }

    private class EmptyJoin<T> implements Join<T> {

        @Override
        public Stream<T> stream() {
            return Stream.empty();
        }

    }

}
