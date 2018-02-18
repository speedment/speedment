package com.speedment.runtime.join.internal.component.join;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.JoinComponent.JoinBuilder1.JoinBuilder2;
import com.speedment.runtime.join.stage.JoinType;
import com.speedment.runtime.join.stage.OperatorType;
import com.speedment.runtime.join.stage.Stage;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.function.BiFunction;
import java.util.function.Predicate;

/**
 *
 * @author Per Minborg
 */
final class JoinBuilder2Impl<T1, T2>
    extends AbstractJoinBuilder<T2>
    implements JoinBuilder2<T1, T2> {

    JoinBuilder2Impl(AbstractJoinBuilder<?> previousStage, StageBean<T2> current) {
        super(previousStage, current);
    }

    @Override
    public <T3> AfterJoin<T1, T2, T3> innerJoin(TableIdentifier<T3> joinedTable) {
        return new AfterJoinImpl<>(addStageBeanOf(joinedTable, JoinType.INNER_JOIN));
    }

    @Override
    public <T3> AfterJoin<T1, T2, T3> leftJoin(TableIdentifier<T3> joinedTable) {
        return new AfterJoinImpl<>(addStageBeanOf(joinedTable, JoinType.LEFT_JOIN));
    }

    @Override
    public <T3> AfterJoin<T1, T2, T3> rightJoin(TableIdentifier<T3> joinedTable) {
        return new AfterJoinImpl<>(addStageBeanOf(joinedTable, JoinType.RIGHT_JOIN));
    }

    @Override
    public <T3> AfterJoin<T1, T2, T3> fullOuterJoin(TableIdentifier<T3> joinedTable) {
        return new AfterJoinImpl<>(addStageBeanOf(joinedTable, JoinType.FULL_OUTER_JOIN));
    }

    @Override
    public <T3> JoinBuilder3<T1, T2, T3> crossJoin(TableIdentifier<T3> joinedTable) {
        return new JoinBuilder3Impl<>(this, addStageBeanOf(joinedTable, JoinType.CROSS_JOIN));
    }

    @Override
    public JoinBuilder2<T1, T2> where(Predicate<? super T2> predicate) {
        addPredicate(predicate);
        return this;
    }

    private final class AfterJoinImpl<T3> implements AfterJoin<T1, T2, T3> {

        private final StageBean<T3> info;

        private AfterJoinImpl(StageBean<T3> info) {
            this.info = requireNonNull(info);
        }

        @Override
        public <V extends Comparable<? super V>, FIELD extends HasComparableOperators<? extends Object, V>>
            AfterOn<T1, T2, T3, V> on(FIELD originalField) {
            requireNonNull(originalField);
            info.setOtherTableField(originalField);
            return new AfterOnImpl<>();
        }

        private final class AfterOnImpl<V extends Comparable<? super V>>
            implements AfterOn<T1, T2, T3, V> {

            @Override
            public <FIELD extends HasComparableOperators<T3, V>> JoinBuilder3<T1, T2, T3> equal(FIELD joinedField) {
                return operation(OperatorType.EQUAL, joinedField);
            }

            @Override
            public <FIELD extends HasComparableOperators<T3, V>> JoinBuilder3<T1, T2, T3> notEqual(FIELD joinedField) {
                return operation(OperatorType.NOT_EQUAL, joinedField);
            }

            @Override
            public <FIELD extends HasComparableOperators<T3, V>> JoinBuilder3<T1, T2, T3> lessThan(FIELD joinedField) {
                return operation(OperatorType.LESS_THAN, joinedField);
            }

            @Override
            public <FIELD extends HasComparableOperators<T3, V>> JoinBuilder3<T1, T2, T3> lessOrEqual(FIELD joinedField) {
                return operation(OperatorType.LESS_OR_EQUAL, joinedField);
            }

            @Override
            public <FIELD extends HasComparableOperators<T3, V>> JoinBuilder3<T1, T2, T3> greaterThan(FIELD joinedField) {
                return operation(OperatorType.GREATER_THAN, joinedField);
            }

            @Override
            public <FIELD extends HasComparableOperators<T3, V>> JoinBuilder3<T1, T2, T3> greaterOrEqual(FIELD joinedField) {
                return operation(OperatorType.GREATER_OR_EQUAL, joinedField);
            }

            @Override
            public <FIELD extends HasComparableOperators<T3, V>, FIELD2 extends HasComparableOperators<T3, V>> JoinBuilder3<T1, T2, T3> between(FIELD joinedFieldFrom, FIELD2 joinedFieldTo) {
                return operation(OperatorType.BETWEEN, joinedFieldFrom, joinedFieldFrom);
            }

            @Override
            public <FIELD extends HasComparableOperators<T3, V>, FIELD2 extends HasComparableOperators<T3, V>> JoinBuilder3<T1, T2, T3> notBetween(FIELD joinedFieldFrom, FIELD2 joinedFieldTo) {
                return operation(OperatorType.NOT_BETWEEN, joinedFieldFrom, joinedFieldFrom);
            }

            private <FIELD extends HasComparableOperators<T3, V>> JoinBuilder3<T1, T2, T3> operation(OperatorType operatorType, FIELD joinedField) {
                info.setOperatorType(operatorType);
                info.setFirstField(joinedField);
                return new JoinBuilder3Impl<>(JoinBuilder2Impl.this, info);
            }

            private <FIELD extends HasComparableOperators<T3, V>, FIELD2 extends HasComparableOperators<T3, V>> JoinBuilder3<T1, T2, T3> operation(OperatorType operatorType, FIELD joinedFieldFrom, FIELD2 joinedFieldTo) {
                info.setOperatorType(operatorType);
                info.setFirstField(joinedFieldFrom);
                info.setSecondField(joinedFieldFrom);
                return new JoinBuilder3Impl<>(JoinBuilder2Impl.this, info);
            }

        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Join<T> build(BiFunction<T1, T2, T> constructor) {
        requireNonNull(constructor);
        final List<Stage<?>> stages = stages();
        return streamSuppler().createJoin(
            stages,
            constructor,
            (TableIdentifier<T1>) stages.get(0).identifier(),
            (TableIdentifier<T2>) stages.get(1).identifier()
        );
    }

}
