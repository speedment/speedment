package com.speedment.runtime.join.internal.component.join;

import com.speedment.common.function.QuadFunction;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.JoinComponent.JoinBuilder1.JoinBuilder2.JoinBuilder3.JoinBuilder4;
import com.speedment.runtime.join.pipeline.JoinType;
import com.speedment.runtime.join.pipeline.OperatorType;
import com.speedment.runtime.join.pipeline.Pipeline;
import static java.util.Objects.requireNonNull;
import java.util.function.Predicate;

/**
 *
 * @author Per Minborg
 */
final class JoinBuilder4Impl<T1, T2, T3, T4>
    extends AbstractJoinBuilder<T4>
    implements JoinBuilder4<T1, T2, T3, T4> {

    JoinBuilder4Impl(AbstractJoinBuilder<?> previousStage, StageBean<T4> current) {
        super(previousStage, current);
    }

    @Override
    public <T5> AfterJoin<T1, T2, T3, T4, T5> innerJoin(TableIdentifier<T5> joinedTable) {
        return new AfterJoinImpl<>(addInfo(joinedTable, JoinType.INNER_JOIN));
    }

    @Override
    public <T5> AfterJoin<T1, T2, T3, T4, T5> leftJoin(TableIdentifier<T5> joinedTable) {
        return new AfterJoinImpl<>(addInfo(joinedTable, JoinType.LEFT_JOIN));
    }

    @Override
    public <T5> AfterJoin<T1, T2, T3, T4, T5> rightJoin(TableIdentifier<T5> joinedTable) {
        return new AfterJoinImpl<>(addInfo(joinedTable, JoinType.RIGHT_JOIN));
    }

    @Override
    public <T5> AfterJoin<T1, T2, T3, T4, T5> fullOuterJoin(TableIdentifier<T5> joinedTable) {
        return new AfterJoinImpl<>(addInfo(joinedTable, JoinType.FULL_OUTER_JOIN));
    }

    @Override
    public <T5> JoinBuilder5<T1, T2, T3, T4, T5> crossJoin(TableIdentifier<T5> joinedTable) {
        return new JoinBuilder5Impl<>(this, addInfo(joinedTable, JoinType.CROSS_JOIN));
    }

    @Override
    public JoinBuilder4<T1, T2, T3, T4> where(Predicate<? super T4> predicate) {
        addPredicate(predicate);
        return this;
    }

    private final class AfterJoinImpl<T5> implements AfterJoin<T1, T2, T3, T4, T5> {

        private final StageBean<T5> info;

        private AfterJoinImpl(StageBean<T5> info) {
            this.info = requireNonNull(info);
        }

        @Override
        public <V extends Comparable<? super V>, FIELD extends HasComparableOperators<? extends Object, V>>
            AfterOn<T1, T2, T3, T4, T5, V> on(FIELD originalField) {
            requireNonNull(originalField);
            info.setOtherTableField(originalField);
            return new AfterOnImpl<>();
        }

        private final class AfterOnImpl<V extends Comparable<? super V>>
            implements AfterOn<T1, T2, T3, T4, T5, V> {

            @Override
            public <FIELD extends HasComparableOperators<T5, V>> JoinBuilder5<T1, T2, T3, T4, T5> equal(FIELD joinedField) {
                return operation(OperatorType.EQUAL, joinedField);
            }

            @Override
            public <FIELD extends HasComparableOperators<T5, V>> JoinBuilder5<T1, T2, T3, T4, T5> notEqual(FIELD joinedField) {
                return operation(OperatorType.NOT_EQUAL, joinedField);
            }

            @Override
            public <FIELD extends HasComparableOperators<T5, V>> JoinBuilder5<T1, T2, T3, T4, T5> lessThan(FIELD joinedField) {
                return operation(OperatorType.LESS_THAN, joinedField);
            }

            @Override
            public <FIELD extends HasComparableOperators<T5, V>> JoinBuilder5<T1, T2, T3, T4, T5> lessOrEqual(FIELD joinedField) {
                return operation(OperatorType.LESS_OR_EQUAL, joinedField);
            }

            @Override
            public <FIELD extends HasComparableOperators<T5, V>> JoinBuilder5<T1, T2, T3, T4, T5> greaterThan(FIELD joinedField) {
                return operation(OperatorType.GREATER_THAN, joinedField);
            }

            @Override
            public <FIELD extends HasComparableOperators<T5, V>> JoinBuilder5<T1, T2, T3, T4, T5> greaterOrEqual(FIELD joinedField) {
                return operation(OperatorType.GREATER_OR_EQUAL, joinedField);
            }

            @Override
            public <FIELD extends HasComparableOperators<T5, V>, FIELD2 extends HasComparableOperators<T5, V>> JoinBuilder5<T1, T2, T3, T4, T5> between(FIELD joinedFieldFrom, FIELD2 joinedFieldTo) {
                return operation(OperatorType.BETWEEN, joinedFieldFrom, joinedFieldFrom);
            }

            @Override
            public <FIELD extends HasComparableOperators<T5, V>, FIELD2 extends HasComparableOperators<T5, V>> JoinBuilder5<T1, T2, T3, T4, T5> notBetween(FIELD joinedFieldFrom, FIELD2 joinedFieldTo) {
                return operation(OperatorType.NOT_BETWEEN, joinedFieldFrom, joinedFieldFrom);
            }

            private <FIELD extends HasComparableOperators<T5, V>> JoinBuilder5<T1, T2, T3, T4, T5> operation(OperatorType operatorType, FIELD joinedField) {
                info.setOperatorType(operatorType);
                info.setFirstField(joinedField);
                return new JoinBuilder5Impl<>(JoinBuilder4Impl.this, info);
            }

            private <FIELD extends HasComparableOperators<T5, V>, FIELD2 extends HasComparableOperators<T5, V>> JoinBuilder5<T1, T2, T3, T4, T5> operation(OperatorType operatorType, FIELD joinedFieldFrom, FIELD2 joinedFieldTo) {
                info.setOperatorType(operatorType);
                info.setFirstField(joinedFieldFrom);
                info.setSecondField(joinedFieldFrom);
                return new JoinBuilder5Impl<>(JoinBuilder4Impl.this, info);
            }

        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Join<T> build(QuadFunction<T1, T2, T3, T4, T> constructor) {
        requireNonNull(constructor);
        final Pipeline pipelie = pipeline();
        return streamSuppler().createJoin(
            pipelie,
            constructor,
            (TableIdentifier<T1>) pipelie.get(0).identifier(),
            (TableIdentifier<T2>) pipelie.get(1).identifier(),
            (TableIdentifier<T3>) pipelie.get(2).identifier(),
            (TableIdentifier<T4>) pipelie.get(3).identifier()
        );
    }

}
