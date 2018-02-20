package com.speedment.runtime.join.internal.component.stream.exhaustive;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.component.StreamSupplierComponent;
import com.speedment.runtime.field.predicate.Inclusion;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.internal.JoinImpl;
import com.speedment.runtime.join.stage.JoinType;
import com.speedment.runtime.join.stage.Stage;
import com.speedment.runtime.join.trait.HasCreateJoin2;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.function.BiFunction;
import java.util.function.Function;
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

        final Function<T1, Stream<T2>> secondStreamFunction = e1 -> applyJoinPredicate(applyFilters(streamSupplier.stream(t2), secondStage.predicates()), secondStage, e1);
        final Function<T2, Stream<T1>> firstStreamFunction = e2 -> applyJoinPredicateBackwards(applyFilters(streamSupplier.stream(t1), firstStage.predicates()), secondStage, e2);

        switch (joinType) {
            case INNER_JOIN: {
                return new JoinImpl<>(
                    () -> applyFilters(streamSupplier.stream(t1), firstStage.predicates()) // First stream
                        .flatMap(
                            e1 -> secondStreamFunction.apply(e1).findAny().isPresent() // Second stream empty?
                            ? secondStreamFunction.apply(e1).map(e2 -> constructor.apply(e1, e2)) // Apply constructor
                            : Stream.empty()
                        )
                );
            }

            case LEFT_JOIN: {
                return new JoinImpl<>(
                    () -> applyFilters(streamSupplier.stream(t1), firstStage.predicates()) // First stream
                        .flatMap(
                            e1 -> secondStreamFunction.apply(e1).findAny().isPresent() // Second stream empty?
                            ? secondStreamFunction.apply(e1).map(e2 -> constructor.apply(e1, e2)) // Apply constructor
                            : Stream.of(constructor.apply(e1, (T2) null))
                        )
                );
            }

            case RIGHT_JOIN: {
                return new JoinImpl<>(
                    () -> applyFilters(streamSupplier.stream(t2), secondStage.predicates()) // Second stream
                        .flatMap(
                            e2 -> firstStreamFunction.apply(e2).findAny().isPresent() // First stream empty?
                            ? firstStreamFunction.apply(e2).map(e1 -> constructor.apply(e1, e2)) // Apply constructor
                            : Stream.of(constructor.apply((T1) null, e2))
                        )
                );
            }

            case FULL_OUTER_JOIN: {
                // Same as LEFT_JOIN but add the right entities with no match
                return new JoinImpl<>(
                    ()
                    -> Stream.concat(
                        applyFilters(streamSupplier.stream(t1), firstStage.predicates()) // First stream
                            .flatMap(
                                e1 -> secondStreamFunction.apply(e1).findAny().isPresent() // Second stream empty?
                                ? secondStreamFunction.apply(e1).map(e2 -> constructor.apply(e1, e2)) // Apply constructor
                                : Stream.of(constructor.apply(e1, (T2) null))
                            ),
                        applyFilters(streamSupplier.stream(t2), secondStage.predicates()) // Second stream
                            .flatMap(
                                e2 -> firstStreamFunction.apply(e2).findAny().isPresent() // First stream empty?
                                ? Stream.empty() // These are already in the other stream
                                : Stream.of(constructor.apply((T1) null, e2))
                            )
                    )
                );
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

    /**
     * Returns a Stream of filtered entities from a foreign Stream.
     *
     * @param <E> Entity of the original (left) table
     * @param <F> Entity type of the foreign (right) table
     * @param <V> Value type
     * @param initialStream to use
     * @param stage of the foreign table
     * @param entity of the original table
     * @return a Stream of filtered entities from a foreign Stream
     */
    private static <E, F, V extends Comparable<? super V>> Stream<F> applyJoinPredicate(Stream<F> initialStream, Stage<F> stage, E entity) {
        @SuppressWarnings("unchecked")
        final HasComparableOperators<F, V> field = (HasComparableOperators<F, V>) stage.field().get();
        @SuppressWarnings("unchecked")
        final HasComparableOperators<E, V> foreignFirstField = (HasComparableOperators<E, V>) stage.foreignFirstField().get();
        @SuppressWarnings("unchecked")
        final V value = (V) foreignFirstField.getter().apply(entity); // Scary cast
        final Predicate<F> predicate;
        switch (stage.joinPredicateType().get()) {
            case EQUAL: {
                predicate = field.equal(value);
                break;
            }
            case NOT_EQUAL: {
                predicate = field.notEqual(value);
                break;
            }
            case LESS_THAN: {
                predicate = field.lessThan(value);
                break;
            }
            case LESS_OR_EQUAL: {
                predicate = field.lessOrEqual(value);
                break;
            }
            case GREATER_THAN: {
                predicate = field.greaterThan(value);
                break;
            }
            case GREATER_OR_EQUAL: {
                predicate = field.greaterOrEqual(value);
                break;
            }
            case BETWEEN: {
                @SuppressWarnings("unchecked")
                final HasComparableOperators<E, V> foreignSecondField = (HasComparableOperators<E, V>) stage.foreignFirstField().get();
                final Inclusion inclusion = stage.foreignInclusion().get();
                @SuppressWarnings("unchecked")
                final V secondValue = (V) foreignSecondField.getter().apply(entity);
                predicate = field.between(value, secondValue, inclusion);
                break;
            }
            case NOT_BETWEEN: {
                @SuppressWarnings("unchecked")
                final HasComparableOperators<E, V> foreignSecondField = (HasComparableOperators<E, V>) stage.foreignFirstField().get();
                final Inclusion inclusion = stage.foreignInclusion().get();
                @SuppressWarnings("unchecked")
                final V secondValue = (V) foreignSecondField.getter().apply(entity);
                predicate = field.notBetween(value, secondValue, inclusion);
                break;
            }
            default: {
                throw new UnsupportedOperationException();
            }
        }

        return initialStream.filter(predicate);
    }

    private static <E, F, V extends Comparable<? super V>> Stream<F> applyJoinPredicateBackwards(Stream<F> initialStream, Stage<E> stage, E entity) {
        @SuppressWarnings("unchecked")
        final HasComparableOperators<E, V> field = (HasComparableOperators<E, V>) stage.field().get();
        @SuppressWarnings("unchecked")
        final HasComparableOperators<F, V> foreignFirstField = (HasComparableOperators<F, V>) stage.foreignFirstField().get();
        @SuppressWarnings("unchecked")
        final V value = (V) field.getter().apply(entity); // Scary cast
        final Predicate<F> predicate;
        switch (stage.joinPredicateType().get()) {
            case EQUAL: {
                predicate = foreignFirstField.equal(value);
                break;
            }
            case NOT_EQUAL: {
                predicate = foreignFirstField.notEqual(value);
                break;
            }
            case LESS_THAN: {
                predicate = foreignFirstField.lessThan(value).negate();
                break;
            }
            case LESS_OR_EQUAL: {
                predicate = foreignFirstField.lessOrEqual(value).negate();
                break;
            }
            case GREATER_THAN: {
                predicate = foreignFirstField.greaterThan(value).negate();
                break;
            }
            case GREATER_OR_EQUAL: {
                predicate = foreignFirstField.greaterOrEqual(value).negate();
                break;
            }
            case BETWEEN: {
                @SuppressWarnings("unchecked")
                final HasComparableOperators<F, V> foreignSecondField = (HasComparableOperators<F, V>) stage.foreignFirstField().get();
                final Inclusion inclusion = stage.foreignInclusion().get();
                predicate = reverseBetween(value, foreignFirstField, foreignSecondField, inclusion);
                break;
            }
            case NOT_BETWEEN: {
                @SuppressWarnings("unchecked")
                final HasComparableOperators<F, V> foreignSecondField = (HasComparableOperators<F, V>) stage.foreignFirstField().get();
                final Inclusion inclusion = stage.foreignInclusion().get();
                predicate = reverseBetween(value, foreignFirstField, foreignSecondField, inclusion).negate();
                break;
            }
            default: {
                throw new UnsupportedOperationException();
            }
        }

        return initialStream.filter(predicate);
    }

    private static <F, V extends Comparable<? super V>> Predicate<F> reverseBetween(
        final V value,
        final HasComparableOperators<F, V> foreignFirstField,
        final HasComparableOperators<F, V> foreignSecondField,
        final Inclusion inclusion
    ) {
        switch (inclusion) {
            case START_EXCLUSIVE_END_EXCLUSIVE: {
                return foreignFirstField.greaterThan(value).and(foreignSecondField.lessThan(value));
            }
            case START_INCLUSIVE_END_EXCLUSIVE: {
                return foreignFirstField.greaterOrEqual(value).and(foreignSecondField.lessThan(value));
            }
            case START_EXCLUSIVE_END_INCLUSIVE: {
                return foreignFirstField.greaterThan(value).and(foreignSecondField.lessOrEqual(value));
            }
            case START_INCLUSIVE_END_INCLUSIVE: {
                return foreignFirstField.greaterOrEqual(value).and(foreignSecondField.lessOrEqual(value));
            }
            default: {
                throw new UnsupportedOperationException(inclusion.toString());
            }

        }
    }

    private static <T> Stream<T> applyFilters(Stream<T> initialStream, List<Predicate<? super T>> predicates) {
        return predicates.stream().reduce(initialStream, Stream::filter, (a, b) -> a);
    }

}
