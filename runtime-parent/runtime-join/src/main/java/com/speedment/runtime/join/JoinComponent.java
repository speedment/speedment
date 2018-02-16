package com.speedment.runtime.join;

import com.speedment.common.function.TriFunction;
import com.speedment.common.tuple.Tuple;
import com.speedment.common.tuple.Tuple2;
import com.speedment.common.tuple.Tuple3;
import com.speedment.common.tuple.Tuple4;
import com.speedment.common.tuple.Tuples;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.old.JoinComponent2.QuadFunction;
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
public interface JoinComponent {

    /**
     * Adds a provided {@code firstManager} to the collection of joined
     * managers. Rows are joined from the provided {@code firstManager}
     * depending on how subsequent managers are added to the builder.
     *
     * @param <A> type of entities for the first manager
     * @param firstManager to use
     * @return a builder where the provided {@code firstManager} is added
     *
     * @throws NullPointerException if the provided {@code firstManager} is
     * {@code null}
     */
    <A> AfterFrom<A> from(Manager<? extends A> firstManager);

    interface AfterFrom<A> extends
        HasJoins<AfterFrom.AfterJoin<A, ?>, AfterFirstJoin<A, ?>>,
        HasWhere<A, AfterFrom<A>> {

        @Override
        <B> AfterJoin<A, B> innerJoin(Manager<? extends B> joinedManager);

        @Override
        <B> AfterJoin<A, B> leftJoin(Manager<? extends B> joinedManager);

        @Override
        <B> AfterJoin<A, B> rightJoin(Manager<? extends B> joinedManager);

        @Override
        <B> AfterJoin<A, B> fullOuterJoin(Manager<? extends B> joinedManager);

        @Override
        <B> AfterFirstJoin<A, B> crossJoin(Manager<? extends B> joinedManager);

        interface AfterJoin<A, B> extends
            HasOn<A> {

            @Override
            <V extends Comparable<? super V>, FIELD extends HasComparableOperators<A, V>> AfterOn<A, B, V> on(FIELD originalField); // Enforce dynamic type later in operation parameter

            interface AfterOn<A, B, V extends Comparable<? super V>> extends
                HasOnPredicates<V, B, AfterFirstJoin<A, B>> {
            }

        }

    }

    // Merge these in to appropriate scope above
    interface AfterFirstJoin<A, B> extends
        HasJoins<AfterFirstJoin.AfterJoin<A, B, ?>, AfterSecondJoin<A, B, ?>>,
        HasWhere<B, AfterFirstJoin<A, B>>,
        HasDefaultBuild<Tuple2<A, B>> {

        @Override
        <C> AfterJoin<A, B, C> innerJoin(Manager<? extends C> joinedManager);

        @Override
        <C> AfterJoin<A, B, C> leftJoin(Manager<? extends C> joinedManager);

        @Override
        <C> AfterJoin<A, B, C> rightJoin(Manager<? extends C> joinedManager);

        @Override
        <C> AfterJoin<A, B, C> fullOuterJoin(Manager<? extends C> joinedManager);

        @Override
        <C> AfterSecondJoin<A, B, C> crossJoin(Manager<? extends C> joinedManager);

        @Override
        default Join<Tuple2<A, B>> build() {
            return build(Tuples::of);
        }

        /**
         * Creates and returns a new Join object where elements in the Join
         * object's stream method is created using the provided
         * {@code constructor}.
         *
         * @param <T> the type of element in the Join object's stream method.
         * @param constructor to use to create stream elements.
         * @return new Join object where elements in the Join object's stream
         * method is of a default {@link Tuple} type
         *
         * @throws NullPointerException if the provided {@code constructor } is
         * {@code null}
         */
        <T> Join<T> build(BiFunction<A, B, T> constructor);

        interface AfterJoin<A, B, C> extends
            HasOn<Object> /* AfterSecondJoin<A, B, C>*/ {

            @Override
            <V extends Comparable<? super V>, FIELD extends HasComparableOperators<Object, V>> AfterOn<A, B, C, V> on(FIELD originalField);

            interface AfterOn<A, B, C, V extends Comparable<? super V>> extends
                HasOnPredicates<V, C, AfterSecondJoin<A, B, C>> {
            }

        }

    }

    interface AfterSecondJoin<A, B, C> extends
        HasJoins<AfterSecondJoin.AfterJoin<A, B, C, ?>, AfterThirdJoin<A, B, C, ?>>,
        HasWhere<C, AfterSecondJoin<A, B, C>>,
        HasDefaultBuild<Tuple3<A, B, C>> {

        @Override
        <D> AfterJoin<A, B, C, D> innerJoin(Manager<? extends D> joinedManager);

        @Override
        <D> AfterJoin<A, B, C, D> leftJoin(Manager<? extends D> joinedManager);

        @Override
        <D> AfterJoin<A, B, C, D> rightJoin(Manager<? extends D> joinedManager);

        @Override
        <D> AfterJoin<A, B, C, D> fullOuterJoin(Manager<? extends D> joinedManager);

        @Override
        <D> AfterThirdJoin<A, B, C, D> crossJoin(Manager<? extends D> joinedManager);

        @Override
        default Join<Tuple3<A, B, C>> build() {
            return build(Tuples::of);
        }

        /**
         * Creates and returns a new Join object where elements in the Join
         * object's stream method is created using the provided
         * {@code constructor}.
         *
         * @param <T> the type of element in the Join object's stream method.
         * @param constructor to use to create stream elements.
         * @return new Join object where elements in the Join object's stream
         * method is of a default {@link Tuple} type
         *
         * @throws NullPointerException if the provided {@code constructor } is
         * {@code null}
         */
        <T> Join<T> build(TriFunction<A, B, C, T> constructor);

        interface AfterJoin<A, B, C, D> extends
            HasOn<Object> {

            @Override
            <V extends Comparable<? super V>, FIELD extends HasComparableOperators<Object, V>> AfterOn<A, B, C, D, V> on(FIELD originalField);

            interface AfterOn<A, B, C, D, V extends Comparable<? super V>> extends
                HasOnPredicates<V, C, AfterThirdJoin<A, B, C, D>> {

            }

        }

    }

    interface AfterThirdJoin<A, B, C, D> extends
        HasWhere<D, AfterThirdJoin<A, B, C, D>>,
        HasDefaultBuild<Tuple4<A, B, C, D>> {

        @Override
        default Join<Tuple4<A, B, C, D>> build() {
            return build(Tuples::of);
        }

        /**
         * Creates and returns a new Join object where elements in the Join
         * object's stream method is created using the provided
         * {@code constructor}.
         *
         * @param <T> the type of element in the Join object's stream method.
         * @param constructor to use to create stream elements.
         * @return new Join object where elements in the Join object's stream
         * method is of a default {@link Tuple} type
         *
         * @throws NullPointerException if the provided {@code constructor } is
         * {@code null}
         */
        <T> Join<T> build(QuadFunction<A, B, C, D, T> constructor);

    }

}
