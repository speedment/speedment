package com.speedment.runtime.join.internal.component.join;

import com.speedment.common.injector.Injector;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.JoinTestUtil.E1;
import com.speedment.runtime.join.JoinTestUtil.E1Manager;
import com.speedment.runtime.join.JoinTestUtil.E2;
import com.speedment.runtime.join.JoinTestUtil.E2Manager;
import com.speedment.runtime.join.JoinTestUtil.E3;
import com.speedment.runtime.join.JoinTestUtil.E3Manager;
import com.speedment.runtime.join.JoinTestUtil.E4;
import com.speedment.runtime.join.JoinTestUtil.E4Manager;
import com.speedment.runtime.join.JoinTestUtil.E5;
import com.speedment.runtime.join.JoinTestUtil.E5Manager;
import com.speedment.runtime.join.JoinTestUtil.E6;
import com.speedment.runtime.join.JoinTestUtil.E6Manager;
import com.speedment.runtime.join.JoinTestUtil.EX;
import com.speedment.runtime.join.JoinTestUtil.MockJoinStreamSupplierComponent;
import com.speedment.runtime.join.JoinTestUtil.MockStreamSupplierComponent;
import static com.speedment.runtime.join.JoinTestUtil.assertStagesEquals;
import com.speedment.runtime.join.stage.JoinType;
import com.speedment.runtime.join.stage.OperatorType;
import static com.speedment.runtime.join.stage.OperatorType.EQUAL;
import com.speedment.runtime.join.stage.Stage;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Per Minborg
 */
public final class JoinBuilderTest {

    private MockJoinStreamSupplierComponent ss;
    private JoinBuilder1Impl<E1> bldr;

    @Before
    public void init() throws InstantiationException {
        final Injector injector = Injector.builder()
            .withComponent(MockStreamSupplierComponent.class)
            .withComponent(MockJoinStreamSupplierComponent.class)
            .build();

        ss = injector.getOrThrow(MockJoinStreamSupplierComponent.class);
        bldr = new JoinBuilder1Impl<>(ss, E1Manager.IDENTIFIER);
    }

    @Test
    public void testIllegalField() {
        try {
            bldr.leftJoinOn(E2.ID2).equal(EX.IDX)
                .leftJoinOn(E3.ID3).equal(E1.ID1)
                .leftJoinOn(E4.ID4).equal(E1.ID1)
                .leftJoinOn(E5.ID5).equal(E1.ID1)
                .leftJoinOn(E6.ID6).equal(E1.ID1)
                .build();
            fail("Illegal field not detected");
        } catch (IllegalStateException ignore) {
        }
    }

    @Test
    public void testNullBuildArgument() {
        try {
            bldr.leftJoinOn(E2.ID2).equal(E1.ID1)
                .leftJoinOn(E3.ID3).equal(E1.ID1)
                .leftJoinOn(E4.ID4).equal(E1.ID1)
                .leftJoinOn(E5.ID5).equal(E1.ID1)
                .leftJoinOn(E6.ID6).equal(E1.ID1)
                .build(null);
            fail("Builder that was null was not detected");
        } catch (NullPointerException ignore) {
        }
    }

    @Test
    public void testCrossJoin() {
        bldr.crossJoin(E2Manager.IDENTIFIER)
            .crossJoin(E3Manager.IDENTIFIER)
            .crossJoin(E4Manager.IDENTIFIER)
            .crossJoin(E5Manager.IDENTIFIER)
            .crossJoin(E6Manager.IDENTIFIER)
            .build();

        final List<Stage<?>> expected = expectedOf(
            entry(E1Manager.IDENTIFIER, noOp()),
            entry(E2Manager.IDENTIFIER, setJoinTypeTo(JoinType.CROSS_JOIN)),
            entry(E3Manager.IDENTIFIER, setJoinTypeTo(JoinType.CROSS_JOIN)),
            entry(E4Manager.IDENTIFIER, setJoinTypeTo(JoinType.CROSS_JOIN)),
            entry(E5Manager.IDENTIFIER, setJoinTypeTo(JoinType.CROSS_JOIN)),
            entry(E6Manager.IDENTIFIER, setJoinTypeTo(JoinType.CROSS_JOIN))
        );

        assertStagesEquals(expected, ss.stages());

        assertIdentifiersInCreateJoinEquals(
            E1Manager.IDENTIFIER,
            E2Manager.IDENTIFIER,
            E3Manager.IDENTIFIER,
            E4Manager.IDENTIFIER,
            E5Manager.IDENTIFIER,
            E6Manager.IDENTIFIER
        );

    }

    @Test
    public void testLeftJoin() {
        bldr.leftJoinOn(E2.ID2).equal(E1.ID1)
            .leftJoinOn(E3.ID3).equal(E1.ID1)
            .leftJoinOn(E4.ID4).equal(E1.ID1)
            .leftJoinOn(E5.ID5).equal(E1.ID1)
            .leftJoinOn(E6.ID6).equal(E1.ID1)
            .build();

        final List<Stage<?>> expected = expectedOf(
            entry(E1Manager.IDENTIFIER, noOp()),
            entry(
                E2Manager.IDENTIFIER,
                setJoinTypeTo(JoinType.LEFT_JOIN)
                    .andThen(setFieldTo(E2.ID2))
                    .andThen(setOperatorTypeTo(EQUAL))
                    .andThen(setForeignFirstFieldTo(E1.ID1))
            ),
            entry(
                E3Manager.IDENTIFIER,
                setJoinTypeTo(JoinType.LEFT_JOIN)
                    .andThen(setFieldTo(E3.ID3))
                    .andThen(setOperatorTypeTo(EQUAL))
                    .andThen(setForeignFirstFieldTo(E1.ID1))
            ),
            entry(
                E4Manager.IDENTIFIER,
                setJoinTypeTo(JoinType.LEFT_JOIN)
                    .andThen(setFieldTo(E4.ID4))
                    .andThen(setOperatorTypeTo(EQUAL))
                    .andThen(setForeignFirstFieldTo(E1.ID1))
            ),
            entry(
                E5Manager.IDENTIFIER,
                setJoinTypeTo(JoinType.LEFT_JOIN)
                    .andThen(setFieldTo(E5.ID5))
                    .andThen(setOperatorTypeTo(EQUAL))
                    .andThen(setForeignFirstFieldTo(E1.ID1))
            ),
            entry(
                E6Manager.IDENTIFIER,
                setJoinTypeTo(JoinType.LEFT_JOIN)
                    .andThen(setFieldTo(E6.ID6))
                    .andThen(setOperatorTypeTo(EQUAL))
                    .andThen(setForeignFirstFieldTo(E1.ID1))
            )
        );

        assertStagesEquals(expected, ss.stages());

        assertIdentifiersInCreateJoinEquals(
            E1Manager.IDENTIFIER,
            E2Manager.IDENTIFIER,
            E3Manager.IDENTIFIER,
            E4Manager.IDENTIFIER,
            E5Manager.IDENTIFIER,
            E6Manager.IDENTIFIER
        );

    }

    @Test
    public void testJoins() {
        bldr.leftJoinOn(E2.ID2).equal(E1.ID1)
            .leftJoinOn(E3.ID3).equal(E1.ID1)
            .leftJoinOn(E4.ID4).equal(E1.ID1)
            .leftJoinOn(E5.ID5).equal(E1.ID1)
            .leftJoinOn(E6.ID6).equal(E1.ID1)
            .build();
    }

    @Test
    public void testOnColumnMustComeFromPreviousTable() {

    }

    private <T> Consumer<T> noOp() {
        return (T t) -> {
        };
    }

    private Consumer<StageBean<?>> setJoinTypeTo(JoinType joinType) {
        return sb -> sb.setJoinType(joinType);
    }

    @SuppressWarnings("unchecked")
    private <T> Consumer<StageBean<?>> setFieldTo(HasComparableOperators<? extends T, ?> field) {
        Consumer<StageBean<T>> consumer = sb -> sb.setField(field);
        return (Consumer<StageBean<?>>) (Object) consumer; // Ugly..
    }

    private Consumer<StageBean<?>> setOperatorTypeTo(OperatorType operatorType) {
        return sb -> sb.setOperatorType(operatorType);
    }

    private <T> Consumer<StageBean<?>> setForeignFirstFieldTo(HasComparableOperators<T, Integer> field) {
        return (StageBean<?> sb) -> {
            @SuppressWarnings("unchecked")
            final StageBean<T> sb1 = (StageBean<T>) sb;
            sb1.setForeignFirstField(field);
        };
    }

    private <T> Consumer<StageBean<?>> setForeignSecondFieldTo(HasComparableOperators<T, ?> field) {
        return (StageBean<?> sb) -> {
            @SuppressWarnings("unchecked")
            final StageBean<T> sb1 = (StageBean<T>) sb;
            sb1.setForeignSecondField(field);
        };
    }

    private <K, V> Entry<K, V> entry(K k, V v) {
        return new SimpleImmutableEntry<>(k, v);
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    private final void assertIdentifiersInCreateJoinEquals(TableIdentifier<?>... identifiers) {
        for (int i = 0; i < identifiers.length; i++) {
            assertEquals(Integer.toString(i), identifiers[i], ss.t(i));
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
