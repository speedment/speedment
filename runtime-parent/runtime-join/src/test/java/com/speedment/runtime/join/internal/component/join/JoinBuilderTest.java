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
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Per Minborg
 */
public final class JoinBuilderTest {

    private MockJoinStreamSupplierComponent ss;
    private JoinBuilder1Impl<E1> b;

    @Before
    public void init() throws InstantiationException {
        final Injector injector = Injector.builder()
            .withComponent(MockStreamSupplierComponent.class)
            .withComponent(MockJoinStreamSupplierComponent.class)
            .build();

        ss = injector.getOrThrow(MockJoinStreamSupplierComponent.class);
        b = new JoinBuilder1Impl<>(ss, E1Manager.IDENTIFIER);
    }

    @Test
    public void testCrossJoin() {
        b
            .crossJoin(E2Manager.IDENTIFIER)
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
        b
            .leftJoin(E2Manager.IDENTIFIER).on(E1.ID1).equal(E2.ID2)
            .leftJoin(E3Manager.IDENTIFIER).on(E1.ID1).equal(E3.ID3)
            .leftJoin(E4Manager.IDENTIFIER).on(E1.ID1).equal(E4.ID4)
            .leftJoin(E5Manager.IDENTIFIER).on(E1.ID1).equal(E5.ID5)
            .leftJoin(E6Manager.IDENTIFIER).on(E1.ID1).equal(E6.ID6)
            .build();

        Consumer<StageBean<?>> c = setFirstFieldTo(E2.ID2);

        final List<Stage<?>> expected = expectedOf(
            entry(E1Manager.IDENTIFIER, noOp()),
            entry(
                E2Manager.IDENTIFIER,
                setJoinTypeTo(JoinType.LEFT_JOIN)
                    .andThen(setOtherTableFieldTo(E1.ID1))
                    .andThen(setOperatorTypeTo(EQUAL))
                    .andThen(this.<E2>setFirstFieldTo(E2.ID2))
            ),
            entry(
                E3Manager.IDENTIFIER,
                setJoinTypeTo(JoinType.LEFT_JOIN)
                    .andThen(setOtherTableFieldTo(E1.ID1))
                    .andThen(setOperatorTypeTo(EQUAL))
                    .andThen(setFirstFieldTo(E3.ID3))
            ),
            entry(
                E4Manager.IDENTIFIER,
                setJoinTypeTo(JoinType.LEFT_JOIN)
                    .andThen(setOtherTableFieldTo(E1.ID1))
                    .andThen(setOperatorTypeTo(EQUAL))
                    .andThen(setFirstFieldTo(E4.ID4))
            ),
            entry(
                E5Manager.IDENTIFIER,
                setJoinTypeTo(JoinType.LEFT_JOIN)
                    .andThen(setOtherTableFieldTo(E1.ID1))
                    .andThen(setOperatorTypeTo(EQUAL))
                    .andThen(setFirstFieldTo(E5.ID5))
            ),
            entry(
                E6Manager.IDENTIFIER,
                setJoinTypeTo(JoinType.LEFT_JOIN)
                    .andThen(setOtherTableFieldTo(E1.ID1))
                    .andThen(setOperatorTypeTo(EQUAL))
                    .andThen(setFirstFieldTo(E6.ID6))
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
    public void testOnColumnMustComeFromPreviousTable() {

    }

    private <T> Consumer<T> noOp() {
        return (T t) -> {
        };
    }

    private Consumer<StageBean<?>> setJoinTypeTo(JoinType joinType) {
        return sb -> sb.setJoinType(joinType);
    }

    private Consumer<StageBean<?>> setOtherTableFieldTo(HasComparableOperators<?, ?> field) {
        return sb -> sb.setOtherTableField(field);
    }

    private Consumer<StageBean<?>> setOperatorTypeTo(OperatorType operatorType) {
        return sb -> sb.setOperatorType(operatorType);
    }

    private <T> Consumer<StageBean<?>> setFirstFieldTo(HasComparableOperators<T, Integer> field) {
        return (StageBean<?> sb) -> {
            @SuppressWarnings("unchecked")
            final StageBean<T> sb1 = (StageBean<T>) sb;
            sb1.setFirstField(field);
        };
    }

    private <T> Consumer<StageBean<?>> setSecondFieldTo(HasComparableOperators<T, ?> field) {
        return (StageBean<?> sb) -> {
            @SuppressWarnings("unchecked")
            final StageBean<T> sb1 = (StageBean<T>) sb;
            sb1.setSecondField(field);
        };
    }

    private <K, V> Entry<K, V> entry(K k, V v) {
        return new SimpleImmutableEntry<>(k, v);
    }

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
