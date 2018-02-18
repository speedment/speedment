package com.speedment.runtime.join.internal.component.join;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.JoinComponent.JoinBuilder1;
import com.speedment.runtime.join.JoinStreamSupplierComponent;
import com.speedment.runtime.join.stage.JoinType;
import com.speedment.runtime.join.stage.OperatorType;
import static java.util.Objects.requireNonNull;
import java.util.function.Predicate;

/**
 *
 * @author Per Minborg
 * @param <T1> entity type of the first table
 */
final class JoinBuilder1Impl<T1>
    extends AbstractJoinBuilder<T1> 
    implements JoinBuilder1<T1> {

    JoinBuilder1Impl(JoinStreamSupplierComponent streamSupplier, TableIdentifier<T1> initialTable) {
        super(streamSupplier, initialTable);
    }

    @Override
    public <T2> AfterJoin<T1, T2> innerJoin(TableIdentifier<T2> joinedTable) {
        return new AfterJoinImpl<>(addStageBeanOf(joinedTable, JoinType.INNER_JOIN));
    }

    @Override
    public <T2> AfterJoin<T1, T2> leftJoin(TableIdentifier<T2> joinedTable) {
        return new AfterJoinImpl<>(addStageBeanOf(joinedTable, JoinType.LEFT_JOIN));
    }

    @Override
    public <T2> AfterJoin<T1, T2> rightJoin(TableIdentifier<T2> joinedTable) {
        return new AfterJoinImpl<>(addStageBeanOf(joinedTable, JoinType.RIGHT_JOIN));
    }

    @Override
    public <T2> AfterJoin<T1, T2> fullOuterJoin(TableIdentifier<T2> joinedTable) {
        return new AfterJoinImpl<>(addStageBeanOf(joinedTable, JoinType.FULL_OUTER_JOIN));
    }

    @Override
    public <T2> JoinBuilder2<T1, T2> crossJoin(TableIdentifier<T2> joinedTable) {
        return new JoinBuilder2Impl<>(this, addStageBeanOf(joinedTable, JoinType.CROSS_JOIN));
    }

    @Override
    public JoinBuilder1Impl<T1> where(Predicate<? super T1> predicate) {
        addPredicate(predicate);
        return this;
    }

    private final class AfterJoinImpl<T2> 
        implements AfterJoin<T1, T2> {

        private final StageBean<T2> info;

        private AfterJoinImpl(StageBean<T2> info) {
            this.info = requireNonNull(info);
        }

        @Override
        public <V extends Comparable<? super V>, FIELD  extends HasComparableOperators<? extends T1, V>>
            AfterOn<T1, T2, V> on(FIELD originalField) {
            requireNonNull(originalField);
            info.setOtherTableField(originalField);
            return new AfterOnImpl<>();
        }

        private final class AfterOnImpl<V extends Comparable<? super V>> 
            implements AfterOn<T1, T2, V> {

            @Override
            public <FIELD extends HasComparableOperators<T2, V>> JoinBuilder2<T1, T2> equal(FIELD joinedField) {
                return operation(OperatorType.EQUAL, joinedField);
            }

            @Override
            public <FIELD extends HasComparableOperators<T2, V>> JoinBuilder2<T1, T2> notEqual(FIELD joinedField) {
                return operation(OperatorType.NOT_EQUAL, joinedField);
            }

            @Override
            public <FIELD extends HasComparableOperators<T2, V>> JoinBuilder2<T1, T2> lessThan(FIELD joinedField) {
                return operation(OperatorType.LESS_THAN, joinedField);
            }

            @Override
            public <FIELD extends HasComparableOperators<T2, V>> JoinBuilder2<T1, T2> lessOrEqual(FIELD joinedField) {
                return operation(OperatorType.LESS_OR_EQUAL, joinedField);
            }

            @Override
            public <FIELD extends HasComparableOperators<T2, V>> JoinBuilder2<T1, T2> greaterThan(FIELD joinedField) {
                return operation(OperatorType.GREATER_THAN, joinedField);
            }

            @Override
            public <FIELD extends HasComparableOperators<T2, V>> JoinBuilder2<T1, T2> greaterOrEqual(FIELD joinedField) {
                return operation(OperatorType.GREATER_OR_EQUAL, joinedField);
            }

            @Override
            public <FIELD extends HasComparableOperators<T2, V>, FIELD2 extends HasComparableOperators<T2, V>> JoinBuilder2<T1, T2> between(FIELD joinedFieldFrom, FIELD2 joinedFieldTo) {
                return operation(OperatorType.BETWEEN, joinedFieldFrom, joinedFieldFrom);
            }

            @Override
            public <FIELD extends HasComparableOperators<T2, V>, FIELD2 extends HasComparableOperators<T2, V>> JoinBuilder2<T1, T2> notBetween(FIELD joinedFieldFrom, FIELD2 joinedFieldTo) {
                return operation(OperatorType.NOT_BETWEEN, joinedFieldFrom, joinedFieldFrom);
            }

            private <FIELD extends HasComparableOperators<T2, V>> JoinBuilder2<T1, T2> operation(OperatorType operatorType, FIELD joinedField) {
                info.setOperatorType(operatorType);
                info.setFirstField(joinedField);
                return new JoinBuilder2Impl<>(JoinBuilder1Impl.this, info);
            }

            private <FIELD extends HasComparableOperators<T2, V>, FIELD2 extends HasComparableOperators<T2, V>> JoinBuilder2<T1, T2> operation(OperatorType operatorType, FIELD joinedFieldFrom, FIELD2 joinedFieldTo) {
                info.setOperatorType(operatorType);
                info.setFirstField(joinedFieldFrom);
                info.setSecondField(joinedFieldFrom);
                return new JoinBuilder2Impl<>(JoinBuilder1Impl.this, info);
            }

        }

    }

}
