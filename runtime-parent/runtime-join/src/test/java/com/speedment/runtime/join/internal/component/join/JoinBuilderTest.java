/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.runtime.join.internal.component.join;

import com.speedment.common.injector.Injector;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.internal.component.join.test_support.JoinTestUtil.E0;
import com.speedment.runtime.join.internal.component.join.test_support.JoinTestUtil.E0Manager;
import com.speedment.runtime.join.internal.component.join.test_support.JoinTestUtil.E1;
import com.speedment.runtime.join.internal.component.join.test_support.JoinTestUtil.E1Manager;
import com.speedment.runtime.join.internal.component.join.test_support.JoinTestUtil.E2;
import com.speedment.runtime.join.internal.component.join.test_support.JoinTestUtil.E2Manager;
import com.speedment.runtime.join.internal.component.join.test_support.JoinTestUtil.E3;
import com.speedment.runtime.join.internal.component.join.test_support.JoinTestUtil.E3Manager;
import com.speedment.runtime.join.internal.component.join.test_support.JoinTestUtil.E4;
import com.speedment.runtime.join.internal.component.join.test_support.JoinTestUtil.E4Manager;
import com.speedment.runtime.join.internal.component.join.test_support.JoinTestUtil.E5;
import com.speedment.runtime.join.internal.component.join.test_support.JoinTestUtil.E5Manager;
import com.speedment.runtime.join.internal.component.join.test_support.JoinTestUtil.EX;
import static com.speedment.runtime.join.internal.component.join.test_support.JoinTestUtil.assertStagesEquals;
import com.speedment.runtime.join.internal.component.join.test_support.MockEmptyJoinStreamSupplierComponent;
import com.speedment.runtime.join.internal.component.join.test_support.MockStreamSupplierComponent;
import com.speedment.runtime.join.stage.JoinOperator;
import static com.speedment.runtime.join.stage.JoinOperator.EQUAL;
import com.speedment.runtime.join.stage.JoinType;
import com.speedment.runtime.join.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
@Execution(ExecutionMode.CONCURRENT)
final class JoinBuilderTest {

    private MockEmptyJoinStreamSupplierComponent ss;
    private JoinBuilder1Impl<E0> bldr;

    @BeforeEach
    void init() throws InstantiationException {
        final Injector injector = Injector.builder()
            .withComponent(MockStreamSupplierComponent.class)
            .withComponent(MockEmptyJoinStreamSupplierComponent.class)
            .build();

        ss = injector.getOrThrow(MockEmptyJoinStreamSupplierComponent.class);
        bldr = new JoinBuilder1Impl<>(ss, E0Manager.IDENTIFIER);
    }

    @Test
    void testIllegalField() {
        try {
            bldr.leftJoinOn(E1.ID1).equal(EX.IDX)
                .leftJoinOn(E2.ID2).equal(E0.ID0)
                .leftJoinOn(E3.ID3).equal(E0.ID0)
                .leftJoinOn(E4.ID4).equal(E0.ID0)
                .leftJoinOn(E5.ID5).equal(E0.ID0)
                .build();
            fail("Illegal field not detected");
        } catch (IllegalStateException ignore) {
        }
    }

    @Test
    void testNullBuildArgument() {
        try {
            bldr.leftJoinOn(E1.ID1).equal(E0.ID0)
                .leftJoinOn(E2.ID2).equal(E0.ID0)
                .leftJoinOn(E3.ID3).equal(E0.ID0)
                .leftJoinOn(E4.ID4).equal(E0.ID0)
                .leftJoinOn(E5.ID5).equal(E0.ID0)
                .build(null);
            fail("Builder that was null was not detected");
        } catch (NullPointerException ignore) {
        }
    }

    @Test
    void testCrossJoin() {
        bldr.crossJoin(E1Manager.IDENTIFIER)
            .crossJoin(E2Manager.IDENTIFIER)
            .crossJoin(E3Manager.IDENTIFIER)
            .crossJoin(E4Manager.IDENTIFIER)
            .crossJoin(E5Manager.IDENTIFIER)
            .build();

        final List<Stage<?>> expected = expectedOf(
            entry(E0Manager.IDENTIFIER, noOp()),
            entry(E1Manager.IDENTIFIER, setJoinTypeTo(JoinType.CROSS_JOIN)),
            entry(E2Manager.IDENTIFIER, setJoinTypeTo(JoinType.CROSS_JOIN)),
            entry(E3Manager.IDENTIFIER, setJoinTypeTo(JoinType.CROSS_JOIN)),
            entry(E4Manager.IDENTIFIER, setJoinTypeTo(JoinType.CROSS_JOIN)),
            entry(E5Manager.IDENTIFIER, setJoinTypeTo(JoinType.CROSS_JOIN))
        );

        assertStagesEquals(expected, ss.stages());

        assertIdentifiersInCreateJoinEquals(
            E0Manager.IDENTIFIER,
            E1Manager.IDENTIFIER,
            E2Manager.IDENTIFIER,
            E3Manager.IDENTIFIER,
            E4Manager.IDENTIFIER,
            E5Manager.IDENTIFIER
        );

    }

    @Test
    void testLeftJoin() {
        bldr.leftJoinOn(E1.ID1).equal(E0.ID0)
            .leftJoinOn(E2.ID2).equal(E0.ID0)
            .leftJoinOn(E3.ID3).equal(E0.ID0)
            .leftJoinOn(E4.ID4).equal(E0.ID0)
            .leftJoinOn(E5.ID5).equal(E0.ID0)
            .build();

        final List<Stage<?>> expected = expectedOf(
            entry(E0Manager.IDENTIFIER, noOp()),
            entry(
                E1Manager.IDENTIFIER,
                setJoinTypeTo(JoinType.LEFT_JOIN)
                    .andThen(setFieldTo(E1.ID1))
                    .andThen(setOperatorTypeTo(EQUAL))
                    .andThen(setForeignFirstFieldTo(E0.ID0))
            ),
            entry(
                E2Manager.IDENTIFIER,
                setJoinTypeTo(JoinType.LEFT_JOIN)
                    .andThen(setFieldTo(E2.ID2))
                    .andThen(setOperatorTypeTo(EQUAL))
                    .andThen(setForeignFirstFieldTo(E0.ID0))
            ),
            entry(
                E3Manager.IDENTIFIER,
                setJoinTypeTo(JoinType.LEFT_JOIN)
                    .andThen(setFieldTo(E3.ID3))
                    .andThen(setOperatorTypeTo(EQUAL))
                    .andThen(setForeignFirstFieldTo(E0.ID0))
            ),
            entry(
                E4Manager.IDENTIFIER,
                setJoinTypeTo(JoinType.LEFT_JOIN)
                    .andThen(setFieldTo(E4.ID4))
                    .andThen(setOperatorTypeTo(EQUAL))
                    .andThen(setForeignFirstFieldTo(E0.ID0))
            ),
            entry(
                E5Manager.IDENTIFIER,
                setJoinTypeTo(JoinType.LEFT_JOIN)
                    .andThen(setFieldTo(E5.ID5))
                    .andThen(setOperatorTypeTo(EQUAL))
                    .andThen(setForeignFirstFieldTo(E0.ID0))
            )
        );

        assertStagesEquals(expected, ss.stages());

        assertIdentifiersInCreateJoinEquals(
            E0Manager.IDENTIFIER,
            E1Manager.IDENTIFIER,
            E2Manager.IDENTIFIER,
            E3Manager.IDENTIFIER,
            E4Manager.IDENTIFIER,
            E5Manager.IDENTIFIER
        );

    }

//    @Test
//    public void testJoins() {
//        bldr.leftJoinOn(E1.ID1).equal(E0.ID0)
//            .leftJoinOn(E2.ID2).between(E0.ID0, E0.ID0, Inclusion.START_INCLUSIVE_END_INCLUSIVE)
//            .rightJoinOn(E3.ID3).equal(E0.ID0)
//            .fullOuterJoinOn(E4.ID4).equal(E0.ID0)
//            .crossJoin(E5Manager.IDENTIFIER)
//            .build();
//    }


    private <T> Consumer<T> noOp() {
        return (T t) -> {
        };
    }

    private Consumer<StageBean<?>> setJoinTypeTo(JoinType joinType) {
        return sb -> sb.setJoinType(joinType);
    }

    @SuppressWarnings("unchecked")
    private <T> Consumer<StageBean<?>> setFieldTo(HasComparableOperators<T, ?> field) {
        Consumer<StageBean<T>> consumer = sb -> sb.setField(field);
        return (Consumer<StageBean<?>>) (Object) consumer; // Ugly..
    }

    private Consumer<StageBean<?>> setOperatorTypeTo(JoinOperator operatorType) {
        return sb -> sb.setJoinOperator(operatorType);
    }

    private <T> Consumer<StageBean<?>> setForeignFirstFieldTo(HasComparableOperators<T, Integer> field) {
        return (StageBean<?> sb) -> {
            @SuppressWarnings("unchecked")
            final StageBean<T> sb1 = (StageBean<T>) sb;
            sb1.setForeignField(field);
        };
    }

//    private <T> Consumer<StageBean<?>> setForeignSecondFieldTo(HasComparableOperators<T, ?> field) {
//        return (StageBean<?> sb) -> {
//            @SuppressWarnings("unchecked")
//            final StageBean<T> sb1 = (StageBean<T>) sb;
//            sb1.setForeignSecondField(field);
//        };
//    }

    private <K, V> Entry<K, V> entry(K k, V v) {
        return new SimpleImmutableEntry<>(k, v);
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    private final void assertIdentifiersInCreateJoinEquals(TableIdentifier<?>... identifiers) {
        for (int i = 0; i < identifiers.length; i++) {
            assertEquals(identifiers[i], ss.t(i), Integer.toString(i));
        }
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    private final List<Stage<?>> expectedOf(Entry<TableIdentifier<?>, Consumer<StageBean<?>>>... operations) {
        return Stream.of(operations)
            .map(e -> {
                final StageBean<?> sb = new StageBean<>(e.getKey());
                e.getValue().accept(sb);
                return sb;
            })
            .map((StageBean<?> sb) -> sb.asStage())
            .collect(toList());

    }
}
