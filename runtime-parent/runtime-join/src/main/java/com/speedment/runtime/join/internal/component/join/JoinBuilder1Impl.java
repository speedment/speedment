package com.speedment.runtime.join.internal.component.join;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.JoinComponent.JoinBuilder1;
import com.speedment.runtime.join.JoinComponent.JoinBuilder1.AfterJoin;
import com.speedment.runtime.join.JoinComponent.JoinBuilder1.JoinBuilder2;
import com.speedment.runtime.join.JoinStreamSupplierComponent;
import com.speedment.runtime.join.stage.JoinType;

/**
 *
 * @author Per Minborg
 * @param <T0> entity type of the first table
 */
final class JoinBuilder1Impl<T0>
    extends AbstractJoinBuilder<T0, JoinBuilder1<T0>>
    implements JoinBuilder1<T0> {

    JoinBuilder1Impl(JoinStreamSupplierComponent streamSupplier, TableIdentifier<T0> initialTable) {
        super(streamSupplier, initialTable);
    }

    @Override
    public <T1> AfterJoin<T0, T1> innerJoinOn(HasComparableOperators<T1, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.INNER_JOIN, joinedField));
    }

    @Override
    public <T1> AfterJoin<T0, T1> leftJoinOn(HasComparableOperators<T1, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.LEFT_JOIN, joinedField));
    }

    @Override
    public <T1> AfterJoin<T0, T1> rightJoinOn(HasComparableOperators<T1, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.RIGHT_JOIN, joinedField));
    }

//    @Override
//    public <T1> AfterJoin<T0, T1> fullOuterJoinOn(HasComparableOperators<T1, ?> joinedField) {
//        return new AfterJoinImpl<>(addStageBeanOf(JoinType.FULL_OUTER_JOIN, joinedField));
//    }

    @Override
    public <T1> JoinBuilder2<T0, T1> crossJoin(TableIdentifier<T1> joinedTable) {
        return new JoinBuilder2Impl<>(this, addStageBeanOf(joinedTable, JoinType.CROSS_JOIN));
    }

    private final class AfterJoinImpl<T1>
        extends BaseAfterJoin<T1, JoinBuilder2<T0, T1>>
        implements AfterJoin<T0, T1> {

        private AfterJoinImpl(StageBean<T1> stageBen) {
            super(JoinBuilder1Impl.this, stageBen, JoinBuilder2Impl::new);
        }
    }

}
