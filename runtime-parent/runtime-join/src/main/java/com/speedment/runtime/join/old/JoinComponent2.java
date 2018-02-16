package com.speedment.runtime.join.old;

import com.speedment.common.function.TriFunction;
import com.speedment.common.tuple.Tuple2;
import com.speedment.common.tuple.Tuple3;
import com.speedment.common.tuple.Tuple4;
import com.speedment.common.tuple.Tuples;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.Join;
import java.util.function.BiFunction;
import java.util.function.Predicate;

/**
 *
 * @author Per Minborg
 */
public interface JoinComponent2 {

    interface Ordinal<T> {
    };

    interface HasOrdinalFirst<ENTITY> {

        Ordinal<ENTITY> first();
    }

    interface HasOrdinalSecond<ENTITY> {

        Ordinal<ENTITY> second();
    }

    interface HasOrdinalThird<ENTITY> {

        Ordinal<ENTITY> third();
    }

    interface HasOrdinalForth<ENTITY> {

        Ordinal<ENTITY> forth();
    }

    interface HasBuilder<R> {

        R builder();
    }

    interface HasJoinOperations<R> {

        /**
         * Sets the join method by which this ordinal should be joined by
         * previous tables using the {@code ordinal} Elements are joined from
         * the given {@code ordinal} using an <em>INNER JOIN</em> whereby rows
         * from two tables are present only if there is a match between the
         * joining columns. Thus, records that do not have matches in the
         * joining columns will not be present in the result.
         *
         * @param ordinal for which the join method should be set
         * @return a builder where the join method for the provided
         * {@code ordinal} is set
         *
         * @throws NullPointerException if the provided {@code ordinal} is
         * {@code null}
         * @throws IllegalArgumentException if the provided {@code ordinal} is
         * not derived from the corresponding BuilderContext
         */
        R withJoin(Ordinal<?> ordinal);

        R withLeftJoin(Ordinal<?> ordinal);

        R withRightJoin(Ordinal<?> ordinal);

        R withFullOuterJoin(Ordinal<?> ordinal);

        R withLeftSemiJoin(Ordinal<?> ordinal);

        R withRightSemiJoin(Ordinal<?> ordinal);

        R withCrossJoin(Ordinal<?> ordinal);

        R withNaturalJoin(Ordinal<?> ordinal);

        R withNaturalLeftJoin(Ordinal<?> ordinal);

        R withNaturalRigthJoin(Ordinal<?> ordinal);
    }

    interface HasWhere<R> {

        <ENTITY> R addWhere(Ordinal<ENTITY> o, Predicate<? super ENTITY> predicate);
    }

    interface HasOnOperations<R> {

        <V extends Comparable<? super V>, E1, E2, FIELD0 extends HasComparableOperators<E1, V>, FIELD1 extends HasComparableOperators<E2, V>>
            R onEquals(Ordinal<E1> o1, FIELD0 originalField, Ordinal<E2> o2, FIELD1 otherField);

        <V extends Comparable<? super V>, E1, E2, FIELD0 extends HasComparableOperators<E1, V>, FIELD1 extends HasComparableOperators<E2, V>>
            R onGreaterThan(FIELD0 originalField, FIELD1 otherField);

        <V extends Comparable<? super V>, E1, E2, FIELD0 extends HasComparableOperators<E1, V>, FIELD1 extends HasComparableOperators<E2, V>>
            R onLessThan(FIELD0 originalField, FIELD1 otherField);

    }

    <E1, E2> Builder2Context<E1, E2> createContext(Manager<? extends E1> m0, Manager<? extends E2> m1);

    <E1, E2, E3> Builder3Context<E1, E2, E3> createContext(Manager<? extends E1> c0, Manager<? extends E2> c1, Manager<? extends E3> c2);

    interface BuilderContext {

    }

    interface JoinBuilder {

    }

    interface Builder2Context<E1, E2> extends
        BuilderContext,
        HasOrdinalFirst<E1>,
        HasOrdinalSecond<E2>,
        HasBuilder<Join2Builder<E1, E2>> {
    }

    interface Builder3Context<E1, E2, E3> extends
        BuilderContext,
        HasOrdinalFirst<E1>,
        HasOrdinalSecond<E2>,
        HasOrdinalThird<E3>,
        HasBuilder<Join3Builder<E1, E2, E3>> {
    }

    interface Builder4Context<E1, E2, E3, E4> extends
        BuilderContext,
        HasOrdinalFirst<E1>,
        HasOrdinalSecond<E2>,
        HasOrdinalThird<E3>,
        HasOrdinalForth<E4>,
        HasBuilder<Join4Builder<E1, E2, E3, E4>> {
    }

    interface Join2Builder<E1, E2> extends
        JoinBuilder,
        HasJoinOperations<Join2Builder<E1, E2>>,
        HasWhere<Join2Builder<E1, E2>>,
        HasOnOperations<Join2Builder<E1, E2>> {

        default Join<Tuple2<E1, E2>> build() {
            return build(Tuples::of);
        }

        <T> Join<T> build(BiFunction<E1, E2, T> constructor);

    }

    interface Join3Builder<E1, E2, E3> extends
        JoinBuilder,
        HasJoinOperations<Join3Builder<E1, E2, E3>>,
        HasWhere<Join3Builder<E1, E2, E3>>,
        HasOnOperations<Join3Builder<E1, E2, E3>> {

        default Join<Tuple3<E1, E2, E3>> build() {
            return build(Tuples::of);
        }

        <T> Join<T> build(TriFunction<E1, E2, E3, T> constructor);

    }

    interface Join4Builder<E1, E2, E3, E4> extends
        JoinBuilder,
        HasJoinOperations<Join4Builder<E2, E3, E3, E4>>,
        HasWhere<Join4Builder<E2, E3, E3, E4>>,
        HasOnOperations<Join4Builder<E1, E2, E3, E4>> {

        default Join<Tuple4<E1, E2, E3, E4>> build() {
            return build(Tuples::of);
        }

        <T> Join<T> build(QuadFunction<E1, E2, E3, E4, T> constructor);

    }

    @FunctionalInterface
    public interface QuadFunction<T, U, V, X, R> {

        /**
         * Applies this function to the given arguments.
         *
         * @param t the first function argument
         * @param u the second function argument
         * @param v the third function argument
         * @param x the forth function argument
         * @return the function result
         */
        R apply(T t, U u, V v, X x);
    }

}
