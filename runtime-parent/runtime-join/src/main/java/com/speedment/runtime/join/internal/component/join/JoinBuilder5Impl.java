package com.speedment.runtime.join.internal.component.join;

import com.speedment.common.function.Function5;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.JoinComponent.JoinBuilder1.JoinBuilder2.JoinBuilder3.JoinBuilder4.JoinBuilder5;
import com.speedment.runtime.join.stage.OperatorType;
import com.speedment.runtime.join.stage.Stage;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.function.Predicate;

/**
 *
 * @author Per Minborg
 */
final class JoinBuilder5Impl<T1, T2, T3, T4, T5>
    extends AbstractJoinBuilder<T5>
    implements JoinBuilder5<T1, T2, T3, T4, T5> {

    JoinBuilder5Impl(AbstractJoinBuilder<?> previousStage, StageBean<T5> current) {
        super(previousStage, current);
    }

    @Override
    public <T6> AfterJoin<T1, T2, T3, T4, T5, T6> innerJoin(TableIdentifier<T6> joinedTable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T6> AfterJoin<T1, T2, T3, T4, T5, T6> leftJoin(TableIdentifier<T6> joinedTable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T6> AfterJoin<T1, T2, T3, T4, T5, T6> rightJoin(TableIdentifier<T6> joinedTable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T6> AfterJoin<T1, T2, T3, T4, T5, T6> fullOuterJoin(TableIdentifier<T6> joinedTable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T6> JoinBuilder6<T1, T2, T3, T4, T5, T6> crossJoin(TableIdentifier<T6> joinedTable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JoinBuilder5<T1, T2, T3, T4, T5> where(Predicate<? super T5> predicate) {
        addPredicate(predicate);
        return this;
    }

    private final class AfterJoinImpl<T6> implements AfterJoin<T1, T2, T3, T4, T5, T6> {

        private final StageBean<T6> info;

        private AfterJoinImpl(StageBean<T6> info) {
            this.info = requireNonNull(info);
        }

        @Override
        public <V extends Comparable<? super V>, FIELD extends HasComparableOperators<? extends Object, V>>
            AfterOn<T1, T2, T3, T4, T5, T6, V> on(FIELD originalField) {
            requireNonNull(originalField);
            info.setOtherTableField(originalField);
            return new AfterOnImpl<>();
        }

        private final class AfterOnImpl<V extends Comparable<? super V>>
            implements AfterOn<T1, T2, T3, T4, T5, T6, V> {

            @Override
            public <FIELD extends HasComparableOperators<T6, V>> JoinBuilder6<T1, T2, T3, T4, T5, T6> equal(FIELD joinedField) {
                return operation(OperatorType.EQUAL, joinedField);
            }

            @Override
            public <FIELD extends HasComparableOperators<T6, V>> JoinBuilder6<T1, T2, T3, T4, T5, T6> notEqual(FIELD joinedField) {
                return operation(OperatorType.NOT_EQUAL, joinedField);
            }

            @Override
            public <FIELD extends HasComparableOperators<T6, V>> JoinBuilder6<T1, T2, T3, T4, T5, T6> lessThan(FIELD joinedField) {
                return operation(OperatorType.LESS_THAN, joinedField);
            }

            @Override
            public <FIELD extends HasComparableOperators<T6, V>> JoinBuilder6<T1, T2, T3, T4, T5, T6> lessOrEqual(FIELD joinedField) {
                return operation(OperatorType.LESS_OR_EQUAL, joinedField);
            }

            @Override
            public <FIELD extends HasComparableOperators<T6, V>> JoinBuilder6<T1, T2, T3, T4, T5, T6> greaterThan(FIELD joinedField) {
                return operation(OperatorType.GREATER_THAN, joinedField);
            }

            @Override
            public <FIELD extends HasComparableOperators<T6, V>> JoinBuilder6<T1, T2, T3, T4, T5, T6> greaterOrEqual(FIELD joinedField) {
                return operation(OperatorType.GREATER_OR_EQUAL, joinedField);
            }

            @Override
            public <FIELD extends HasComparableOperators<T6, V>, FIELD2 extends HasComparableOperators<T6, V>> JoinBuilder6<T1, T2, T3, T4, T5, T6> between(FIELD joinedFieldFrom, FIELD2 joinedFieldTo) {
                return operation(OperatorType.BETWEEN, joinedFieldFrom, joinedFieldFrom);
            }

            @Override
            public <FIELD extends HasComparableOperators<T6, V>, FIELD2 extends HasComparableOperators<T6, V>> JoinBuilder6<T1, T2, T3, T4, T5, T6> notBetween(FIELD joinedFieldFrom, FIELD2 joinedFieldTo) {
                return operation(OperatorType.NOT_BETWEEN, joinedFieldFrom, joinedFieldFrom);
            }

            private <FIELD extends HasComparableOperators<T6, V>> JoinBuilder6<T1, T2, T3, T4, T5, T6> operation(OperatorType operatorType, FIELD joinedField) {
                info.setOperatorType(operatorType);
                info.setFirstField(joinedField);
                return new JoinBuilder6Impl<>(JoinBuilder5Impl.this, info);
            }

            private <FIELD extends HasComparableOperators<T6, V>, FIELD2 extends HasComparableOperators<T6, V>> JoinBuilder6<T1, T2, T3, T4, T5, T6> operation(OperatorType operatorType, FIELD joinedFieldFrom, FIELD2 joinedFieldTo) {
                info.setOperatorType(operatorType);
                info.setFirstField(joinedFieldFrom);
                info.setSecondField(joinedFieldFrom);
                return new JoinBuilder6Impl<>(JoinBuilder5Impl.this, info);
            }

        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Join<T> build(Function5<T1, T2, T3, T4, T5, T> constructor) {
        requireNonNull(constructor);
        final List<Stage<?>> stages = stages();
        return streamSuppler().createJoin(
            stages,
            constructor,
            (TableIdentifier<T1>) stages.get(0).identifier(),
            (TableIdentifier<T2>) stages.get(1).identifier(),
            (TableIdentifier<T3>) stages.get(2).identifier(),
            (TableIdentifier<T4>) stages.get(3).identifier(),
            (TableIdentifier<T5>) stages.get(4).identifier()
        );
    }

}
