package com.speedment.runtime.join;

import com.speedment.common.function.QuadFunction;
import com.speedment.common.function.TriFunction;
import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.common.tuple.Tuple;
import com.speedment.common.tuple.Tuple2;
import com.speedment.common.tuple.Tuple3;
import com.speedment.common.tuple.Tuple4;
import com.speedment.common.tuple.Tuples;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.trait.HasOnPredicates;
import com.speedment.runtime.join.trait.HasWhere;
import java.util.function.BiFunction;
import com.speedment.runtime.join.trait.HasJoins;
import com.speedment.runtime.join.trait.HasDefaultBuild;
import com.speedment.runtime.join.trait.HasOn;

/**
 * JoinComponent that can be used to create builders for creating Join objects.
 * Join objects, in turn, can be used to stream over joined tables.
 *
 * @author Per Minborg
 */
@InjectKey(JoinComponent.class)
public interface JoinComponent {

    /**
     * Adds a provided {@code firstManager} to the collection of joined
     * managers. Rows are joined from the provided {@code firstManager}
     * depending on how subsequent managers are added to the builder.
     *
     * @param <T1> type of entities for the first manager
     * @param firstManager to use
     * @return a builder where the provided {@code firstManager} is added
     *
     * @throws NullPointerException if the provided {@code firstManager} is
     * {@code null}
     */
    <T1> AfterFrom<T1> from(TableIdentifier<T1> firstManager);

    interface AfterFrom<T1> extends
        HasJoins<AfterFrom.AfterJoin<T1, ?>, AfterFirstJoin<T1, ?>>,
        HasWhere<T1, AfterFrom<T1>> {

        @Override
        <T2> AfterJoin<T1, T2> innerJoin(TableIdentifier<T2> joinedTable);

        @Override
        <T2> AfterJoin<T1, T2> leftJoin(TableIdentifier<T2> joinedTable);

        @Override
        <T2> AfterJoin<T1, T2> rightJoin(TableIdentifier<T2> joinedTable);

        @Override
        <T2> AfterJoin<T1, T2> fullOuterJoin(TableIdentifier<T2> joinedTable);

        @Override
        <T2> AfterFirstJoin<T1, T2> crossJoin(TableIdentifier<T2> joinedTable);

        interface AfterJoin<T1, T2> extends
            HasOn<T1> {

            @Override
            <V extends Comparable<? super V>, FIELD extends HasComparableOperators<? extends T1, V>> AfterOn<T1, T2, V> on(FIELD originalField); // Enforce dynamic type later in operation parameter

            interface AfterOn<T1, T2, V extends Comparable<? super V>> extends
                HasOnPredicates<V, T2, AfterFirstJoin<T1, T2>> {
            }

        }

    }

    // Merge these in to appropriate scope above
    interface AfterFirstJoin<T1, T2> extends
        HasJoins<AfterFirstJoin.AfterJoin<T1, T2, ?>, AfterSecondJoin<T1, T2, ?>>,
        HasWhere<T2, AfterFirstJoin<T1, T2>>,
        HasDefaultBuild<Tuple2<T1, T2>> {

        @Override
        <T3> AfterJoin<T1, T2, T3> innerJoin(TableIdentifier<T3> joinedTable);

        @Override
        <T3> AfterJoin<T1, T2, T3> leftJoin(TableIdentifier<T3> joinedTable);

        @Override
        <T3> AfterJoin<T1, T2, T3> rightJoin(TableIdentifier<T3> joinedTable);

        @Override
        <T4> AfterJoin<T1, T2, T4> fullOuterJoin(TableIdentifier<T4> joinedTable);

        @Override
        <T4> AfterSecondJoin<T1, T2, T4> crossJoin(TableIdentifier<T4> joinedTable);

        @Override
        default Join<Tuple2<T1, T2>> build() {
            return build(Tuples::of);
        }

        /**
         * Creates and returns a new Join object where elements in the Join
         * object's stream method is created using the provided
         * {@code constructor}.
         *
         * @param <T> the type of element in the Join object's stream method.
         * @param constructor to use to create stream elements.
         * @return a new Join object where elements in the Join object's stream
         * method is of a default {@link Tuple} type
         *
         * @throws NullPointerException if the provided {@code constructor } is
         * {@code null}
         */
        <T> Join<T> build(BiFunction<T1, T2, T> constructor);

        interface AfterJoin<T1, T2, T3> extends
            HasOn<Object> {

            @Override
            <V extends Comparable<? super V>, FIELD extends HasComparableOperators<? extends Object, V>> AfterOn<T1, T2, T3, V> on(FIELD originalField);

            interface AfterOn<T1, T2, T3, V extends Comparable<? super V>> extends
                HasOnPredicates<V, T3, AfterSecondJoin<T1, T2, T3>> {
            }

        }

    }

    interface AfterSecondJoin<T1, T2, T3> extends
        HasJoins<AfterSecondJoin.AfterJoin<T1, T2, T3, ?>, AfterThirdJoin<T1, T2, T3, ?>>,
        HasWhere<T3, AfterSecondJoin<T1, T2, T3>>,
        HasDefaultBuild<Tuple3<T1, T2, T3>> {

        @Override
        <T4> AfterJoin<T1, T2, T3, T4> innerJoin(TableIdentifier<T4> joinedTable);

        @Override
        <T4> AfterJoin<T1, T2, T3, T4> leftJoin(TableIdentifier<T4> joinedTable);

        @Override
        <T4> AfterJoin<T1, T2, T3, T4> rightJoin(TableIdentifier<T4> joinedTable);

        @Override
        <T4> AfterJoin<T1, T2, T3, T4> fullOuterJoin(TableIdentifier<T4> joinedTable);

        @Override
        <T4> AfterThirdJoin<T1, T2, T3, T4> crossJoin(TableIdentifier<T4> joinedTable);

        @Override
        default Join<Tuple3<T1, T2, T3>> build() {
            return build(Tuples::of);
        }

        /**
         * Creates and returns a new Join object where elements in the Join
         * object's stream method is created using the provided
         * {@code constructor}.
         *
         * @param <T> the type of element in the Join object's stream method.
         * @param constructor to use to create stream elements.
         * @return a new Join object where elements in the Join object's stream
         * method is of a default {@link Tuple} type
         *
         * @throws NullPointerException if the provided {@code constructor } is
         * {@code null}
         */
        <T> Join<T> build(TriFunction<T1, T2, T3, T> constructor);

        interface AfterJoin<T1, T2, T3, T4> extends
            HasOn<Object> {

            @Override
            <V extends Comparable<? super V>, FIELD extends HasComparableOperators<? extends Object, V>> AfterOn<T1, T2, T3, T4, V> on(FIELD originalField);

            interface AfterOn<T1, T2, T3, T4, V extends Comparable<? super V>> extends
                HasOnPredicates<V, T3, AfterThirdJoin<T1, T2, T3, T4>> {

            }

        }

    }

    interface AfterThirdJoin<T1, T2, T3, T4> extends
        HasWhere<T4, AfterThirdJoin<T1, T2, T3, T4>>,
        HasDefaultBuild<Tuple4<T1, T2, T3, T4>> {

        @Override
        default Join<Tuple4<T1, T2, T3, T4>> build() {
            return build(Tuples::of);
        }

        /**
         * Creates and returns a new Join object where elements in the Join
         * object's stream method is created using the provided
         * {@code constructor}.
         *
         * @param <T> the type of element in the Join object's stream method.
         * @param constructor to use to create stream elements.
         * @return a new Join object where elements in the Join object's stream
         * method is of a default {@link Tuple} type
         *
         * @throws NullPointerException if the provided {@code constructor } is
         * {@code null}
         */
        <T> Join<T> build(QuadFunction<T1, T2, T3, T4, T> constructor);

    }

}
