package com.speedment.runtime.join.internal.component.stream.exhaustive;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.component.StreamSupplierComponent;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.internal.JoinImpl;
import com.speedment.runtime.join.stage.JoinType;
import com.speedment.runtime.join.stage.Stage;
import com.speedment.runtime.join.trait.HasCreateJoin2;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
public class ExhaustiveHasCreateJoin2 implements HasCreateJoin2 {

    private final StreamSupplierComponent streamSupplier;

    public ExhaustiveHasCreateJoin2(StreamSupplierComponent streamSupplier) {
        this.streamSupplier = requireNonNull(streamSupplier);
    }

    @Override
    public <T1, T2, T> Join<T> createJoin(
        final List<Stage<?>> stages,
        final BiFunction<T1, T2, T> constructor,
        final TableIdentifier<T1> t1,
        final TableIdentifier<T2> t2
    ) {
        @SuppressWarnings("unchecked")
        final Stage<T1> firstStage = (Stage<T1>) stages.get(0);
        @SuppressWarnings("unchecked")
        final Stage<T2> secondStage = (Stage<T2>) stages.get(1);
        final JoinType joinType = secondStage.joinType().get();

        switch (joinType) {
            case LEFT_JOIN: {

                Stream<T1> firstStream = applyFilters(streamSupplier.stream(t1), firstStage.predicates());

                return new JoinImpl<>(() -> firstStream.map(e1 -> constructor.apply(e1, (T2) null)));

//                final Stream.Builder<T> builder = Stream.builder();
//                try (Stream<T1> firstStream = applyFilters(streamSupplier.stream(t1), firstStage.predicates())) {
//                    
//                    try (Stream<T2> secondStream = applyFilters(streamSupplier.stream(t2), secondStage.predicates())) {
//
//                    }
//                }
//                break;
            }

            case CROSS_JOIN: {
                return new JoinImpl<>(
                    () -> applyFilters(streamSupplier.stream(t1), firstStage.predicates()) // First stream
                        .flatMap(e1
                            -> applyFilters(streamSupplier.stream(t2), secondStage.predicates()) // Second stream
                            .map(e2 -> constructor.apply(e1, e2)) // Apply constructor
                        )
                );
            }

            default: {
                throw new UnsupportedOperationException("We regret to inform you that the join type " + joinType + " is not yet supported.");
            }

        }
    }

    private static <T> Stream<T> applyFilters(Stream<T> initialStream, List<Predicate<? super T>> predicates) {
        return predicates.stream().reduce(initialStream, Stream::filter, (a, b) -> a);
    }

}
