package com.speedment.runtime.join.internal.component.stream;

import com.speedment.common.function.Function5;
import com.speedment.common.function.Function6;
import com.speedment.common.function.QuadFunction;
import com.speedment.common.function.TriFunction;
import com.speedment.common.injector.annotation.Execute;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.component.StreamSupplierComponent;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.JoinStreamSupplierComponent;
import com.speedment.runtime.join.internal.component.stream.exhaustive.ExhaustiveHasCreateJoin2;
import static java.util.Objects.requireNonNull;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import com.speedment.runtime.join.stage.Stage;
import com.speedment.runtime.join.trait.HasCreateJoin2;
import java.util.List;

/**
 *
 * @author Per Minborg
 */
public class DebugJoinStreamSupplierComponent implements JoinStreamSupplierComponent {

    private HasCreateJoin2 join2Creator;

    @Execute
    void init(StreamSupplierComponent streamSupplier) {
        join2Creator = new ExhaustiveHasCreateJoin2(streamSupplier);
    }

    @Override
    public <T1, T2, T> Join<T> createJoin(
        final List<Stage<?>> p,
        final BiFunction<T1, T2, T> constructor,
        final TableIdentifier<T1> t1,
        final TableIdentifier<T2> t2
    ) {
        return join2Creator.createJoin(p, constructor, t1, t2);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T1, T2, T3, T> Join<T> createJoin(
        final List<Stage<?>> p,
        final TriFunction<T1, T2, T3, T> constructor,
        final TableIdentifier<T1> t1,
        final TableIdentifier<T2> t2,
        final TableIdentifier<T3> t3
    ) {
        return (Join<T>) emptyJoin(p, constructor, t1, t2, t3);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T1, T2, T3, T4, T> Join<T> createJoin(
        final List<Stage<?>> p,
        final QuadFunction<T1, T2, T3, T4, T> constructor,
        final TableIdentifier<T1> t1,
        final TableIdentifier<T2> t2,
        final TableIdentifier<T3> t3,
        final TableIdentifier<T4> t4
    ) {
        return (Join<T>) emptyJoin(p, constructor, t1, t2, t3, t4);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T1, T2, T3, T4, T5, T> Join<T> createJoin(
        final List<Stage<?>> p,
        final Function5<T1, T2, T3, T4, T5, T> constructor,
        final TableIdentifier<T1> t1,
        final TableIdentifier<T2> t2,
        final TableIdentifier<T3> t3,
        final TableIdentifier<T4> t4,
        final TableIdentifier<T5> t5
    ) {
        return (Join<T>) emptyJoin(p, constructor, t1, t2, t3, t4, t5);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T1, T2, T3, T4, T5, T6, T> Join<T> createJoin(
        final List<Stage<?>> p,
        final Function6<T1, T2, T3, T4, T5, T6, T> constructor,
        final TableIdentifier<T1> t1,
        final TableIdentifier<T2> t2,
        final TableIdentifier<T3> t3,
        final TableIdentifier<T4> t4,
        final TableIdentifier<T5> t5,
        final TableIdentifier<T6> t6
    ) {
        return (Join<T>) emptyJoin(p, constructor, t1, t2, t3, t4, t5, t6);
    }

    private Join<?> emptyJoin(List<Stage<?>> p, Object unusedConstructor, TableIdentifier<?>... unusedIdentifiers) {
        requireNonNull(p);
        requireNonNull(unusedConstructor);
        Stream.of(unusedConstructor).forEach(id -> requireNonNull(id));
        printPipeline(p);
        return new EmptyJoin<>();
    }

    private void printPipeline(List<Stage<?>> p) {
        System.out.println("Pipeline:");
        p.stream()
            .map(Object::toString)
            .forEachOrdered(System.out::println);
    }

    private class ExhaustiveJoin2<T1, T2, T> implements Join<T> {

        final List<Stage<?>> p;
        final BiFunction<T1, T2, T> constructor;
        final TableIdentifier<T1> t1;
        final TableIdentifier<T2> t2;

        ExhaustiveJoin2(List<Stage<?>> p, BiFunction<T1, T2, T> constructor, TableIdentifier<T1> t1, TableIdentifier<T2> t2) {
            this.p = requireNonNull(p);
            this.constructor = requireNonNull(constructor);
            this.t1 = requireNonNull(t1);
            this.t2 = requireNonNull(t2);
        }

        @Override
        public Stream<T> stream() {
            return Stream.empty();
        }

    }

    private class EmptyJoin<T> implements Join<T> {

        @Override
        public Stream<T> stream() {
            return Stream.empty();
        }

    }

}
