package com.speedment.runtime.join.old;

import com.speedment.common.tuple.Tuple2;
import com.speedment.common.tuple.Tuple3;
import com.speedment.common.tuple.Tuple4;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.field.trait.HasComparableOperators;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
public interface JoinSketch {



    interface Join2<A, B> {

        Stream<Tuple2<A, B>> stream(); // Should also implement stream(StreamConfiguration), (Parallel strategy etc.)
    }

    interface Join3<A, B, C> {

        Stream<Tuple3<A, B, C>> stream();
    }

    interface Join4<A, B, C, D> {

        Stream<Tuple4<A, B, C, D>> stream();
    }

    interface Builder {

        <A> AfterFrom<A> from(Manager<A> manager);

        interface AfterFrom<A> {

            <B> AfterJoin<A, B> join(Manager<B> joinedManager);

            <B> AfterJoin<A, B> leftJoin(Manager<B> leftJoinedManager);

            <B> AfterJoin<A, B> rightJoin(Manager<B> rightJoinedManager);

            interface AfterJoin<A, B> extends AfterFirstJoin<A, B> {

                <V extends Comparable<? super V>, FIELD extends HasComparableOperators<?, V>> AfterOn<A, B, V> on(FIELD originalField); // Enforce dynamic type later in operation parameter

                interface AfterOn<A, B, V extends Comparable<? super V>> {

                    <FIELD extends HasComparableOperators<?, V>> AfterFirstJoin<A, B> equal(FIELD otherField);

                    <FIELD extends HasComparableOperators<?, V>> AfterFirstJoin<A, B> greaterThan(FIELD otherField);

                    <FIELD extends HasComparableOperators<?, V>> AfterFirstJoin<A, B> lessThan(FIELD otherField);

                }

            }

        }

    }

    // Merge these in to appropriate scope above
    interface AfterFirstJoin<A, B> {

        <C> AfterJoin<A, B, C> join(Manager<C> joinedManager);

        <C> AfterJoin<A, B, C> leftJoin(Manager<C> leftJoinedManager);

        <C> AfterJoin<A, B, C> rightJoin(Manager<C> rightJoinedManager);

        Join2<A, B> build();

        interface AfterJoin<A, B, C> extends AfterSecondJoin<A, B, C> {

            <V extends Comparable<? super V>, FIELD extends HasComparableOperators<?, V>> AfterOn<A, B, C, V> on(FIELD originalField);

            interface AfterOn<A, B, C, V extends Comparable<? super V>> {

                <FIELD extends HasComparableOperators<?, V>> AfterSecondJoin<A, B, C> equal(FIELD otherField);

                <FIELD extends HasComparableOperators<?, V>> AfterSecondJoin<A, B, C> greaterThan(FIELD otherField);

                <FIELD extends HasComparableOperators<?, V>> AfterSecondJoin<A, B, C> lessThan(FIELD otherField);

            }

        }

    }

    interface AfterSecondJoin<A, B, C> {

        <D> AfterJoin<A, B, C, D> join(Manager<C> joinedManager);

        <D> AfterJoin<A, B, C, D> leftJoin(Manager<C> leftJoinedManager);

        <D> AfterJoin<A, B, C, D> rightJoin(Manager<C> rightJoinedManager);

        Join3<A, B, C> build();

        interface AfterJoin<A, B, C, D> extends AfterThirdJoin<A, B, C, D> {

            <V extends Comparable<? super V>, FIELD extends HasComparableOperators<?, V>> AfterOn<A, B, C, D, V> on(FIELD originalField);

            interface AfterOn<A, B, C, D, V extends Comparable<? super V>> {

                <FIELD extends HasComparableOperators<?, V>> AfterThirdJoin<A, B, C, D> equal(FIELD otherField);

                <FIELD extends HasComparableOperators<?, V>> AfterThirdJoin<A, B, C, D> greaterThan(FIELD otherField);

                <FIELD extends HasComparableOperators<?, V>> AfterThirdJoin<A, B, C, D> lessThan(FIELD otherField);

            }

        }

    }

    interface AfterThirdJoin<A, B, C, D> {

        Join4<A, B, C, D> build();

    } 

    
}
