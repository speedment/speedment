package com.speedment.runtime.join.internal.component.join;

import com.speedment.common.function.TriFunction;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.JoinComponent.JoinBuilder1.JoinBuilder2.JoinBuilder3;
import com.speedment.runtime.join.stage.JoinType;
import com.speedment.runtime.join.stage.OperatorType;
import com.speedment.runtime.join.stage.Stage;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.function.Predicate;

/**
 *
 * @author Per Minborg
 */
final class JoinBuilder3Impl<T1, T2, T3>
    extends AbstractJoinBuilder<T3>
    implements JoinBuilder3<T1, T2, T3> {

    JoinBuilder3Impl(AbstractJoinBuilder<?> previousStage, StageBean<T3> current) {
        super(previousStage, current);
    }

    @Override
    public <T4> AfterJoin<T1, T2, T3, T4> innerJoin(TableIdentifier<T4> joinedTable) {
        return new AfterJoinImpl<>(addStageBeanOf(joinedTable, JoinType.INNER_JOIN));    }

    @Override
    public <T4> AfterJoin<T1, T2, T3, T4> leftJoin(TableIdentifier<T4> joinedTable) {
        return new AfterJoinImpl<>(addStageBeanOf(joinedTable, JoinType.LEFT_JOIN));
    }

    @Override
    public <T4> AfterJoin<T1, T2, T3, T4> rightJoin(TableIdentifier<T4> joinedTable) {
        return new AfterJoinImpl<>(addStageBeanOf(joinedTable, JoinType.RIGHT_JOIN));
    }

    @Override
    public <T4> AfterJoin<T1, T2, T3, T4> fullOuterJoin(TableIdentifier<T4> joinedTable) {
        return new AfterJoinImpl<>(addStageBeanOf(joinedTable, JoinType.FULL_OUTER_JOIN));
    }

    @Override
    public <T4> JoinBuilder4<T1, T2, T3, T4> crossJoin(TableIdentifier<T4> joinedTable) {
        return new JoinBuilder4Impl<>(this, addStageBeanOf(joinedTable, JoinType.CROSS_JOIN));
    }

    @Override
    public JoinBuilder3<T1, T2, T3> where(Predicate<? super T3> predicate) {
        addPredicate(predicate);
        return this;
    }

    private final class AfterJoinImpl<T4> implements AfterJoin<T1, T2, T3, T4> {

        private final StageBean<T4> info;

        private AfterJoinImpl(StageBean<T4> info) {
            this.info = requireNonNull(info);
        }

        @Override
        public <V extends Comparable<? super V>, FIELD extends HasComparableOperators<? extends Object, V>>
            AfterOn<T1, T2, T3, T4, V> on(FIELD originalField) {
            requireNonNull(originalField);
            info.setOtherTableField(originalField);
            return new AfterOnImpl<>();
        }

        private final class AfterOnImpl<V extends Comparable<? super V>>
            implements AfterOn<T1, T2, T3, T4, V> {

            
            @Override
            public <FIELD extends HasComparableOperators<T4, V>> JoinBuilder4<T1, T2, T3, T4> equal(FIELD joinedField) {
                return operation(OperatorType.EQUAL, joinedField);
            }

            @Override
            public <FIELD extends HasComparableOperators<T4, V>> JoinBuilder4<T1, T2, T3, T4> notEqual(FIELD joinedField) {
                return operation(OperatorType.NOT_EQUAL, joinedField);
            }

            @Override
            public <FIELD extends HasComparableOperators<T4, V>> JoinBuilder4<T1, T2, T3, T4> lessThan(FIELD joinedField) {
                return operation(OperatorType.LESS_THAN, joinedField);
            }

            @Override
            public <FIELD extends HasComparableOperators<T4, V>> JoinBuilder4<T1, T2, T3, T4> lessOrEqual(FIELD joinedField) {
                return operation(OperatorType.LESS_OR_EQUAL, joinedField);
            }

            @Override
            public <FIELD extends HasComparableOperators<T4, V>> JoinBuilder4<T1, T2, T3, T4> greaterThan(FIELD joinedField) {
                return operation(OperatorType.GREATER_THAN, joinedField);
            }

            @Override
            public <FIELD extends HasComparableOperators<T4, V>> JoinBuilder4<T1, T2, T3, T4> greaterOrEqual(FIELD joinedField) {
                return operation(OperatorType.GREATER_OR_EQUAL, joinedField);
            }

            @Override
            public <FIELD extends HasComparableOperators<T4, V>, FIELD2 extends HasComparableOperators<T4, V>> JoinBuilder4<T1, T2, T3, T4> between(FIELD joinedFieldFrom, FIELD2 joinedFieldTo) {
                return operation(OperatorType.BETWEEN, joinedFieldFrom, joinedFieldFrom);
            }

            @Override
            public <FIELD extends HasComparableOperators<T4, V>, FIELD2 extends HasComparableOperators<T4, V>> JoinBuilder4<T1, T2, T3, T4> notBetween(FIELD joinedFieldFrom, FIELD2 joinedFieldTo) {
                return operation(OperatorType.NOT_BETWEEN, joinedFieldFrom, joinedFieldFrom);
            }

            private <FIELD extends HasComparableOperators<T4, V>> JoinBuilder4<T1, T2, T3, T4> operation(OperatorType operatorType, FIELD joinedField) {
                info.setOperatorType(operatorType);
                info.setFirstField(joinedField);
                return new JoinBuilder4Impl<>(JoinBuilder3Impl.this, info);
            }

            private <FIELD extends HasComparableOperators<T4, V>, FIELD2 extends HasComparableOperators<T4, V>> JoinBuilder4<T1, T2, T3, T4> operation(OperatorType operatorType, FIELD joinedFieldFrom, FIELD2 joinedFieldTo) {
                info.setOperatorType(operatorType);
                info.setFirstField(joinedFieldFrom);
                info.setSecondField(joinedFieldFrom);
                return new JoinBuilder4Impl<>(JoinBuilder3Impl.this, info);
            }

        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Join<T> build(TriFunction<T1, T2, T3, T> constructor) {
        requireNonNull(constructor);
        final List<Stage<?>> stages = stages();
        return streamSuppler().createJoin(
            stages,
            constructor,
            (TableIdentifier<T1>) stages.get(0).identifier(),
            (TableIdentifier<T2>) stages.get(1).identifier(),
            (TableIdentifier<T3>) stages.get(2).identifier()
        );
    }

}
