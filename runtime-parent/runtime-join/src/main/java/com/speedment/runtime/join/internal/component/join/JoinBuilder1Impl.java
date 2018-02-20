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
 * @param <T1> entity type of the first table
 */
final class JoinBuilder1Impl<T1>
    extends AbstractJoinBuilder<T1, JoinBuilder1<T1>>
    implements JoinBuilder1<T1> {

    JoinBuilder1Impl(JoinStreamSupplierComponent streamSupplier, TableIdentifier<T1> initialTable) {
        super(streamSupplier, initialTable);
    }

    @Override
    public <T2> AfterJoin<T1, T2> innerJoinOn(HasComparableOperators<T2, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.INNER_JOIN, joinedField));
    }

    @Override
    public <T2> AfterJoin<T1, T2> leftJoinOn(HasComparableOperators<T2, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.LEFT_JOIN, joinedField));
    }

    @Override
    public <T2> AfterJoin<T1, T2> rightJoinOn(HasComparableOperators<T2, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.RIGHT_JOIN, joinedField));
    }

    @Override
    public <T2> AfterJoin<T1, T2> fullOuterJoinOn(HasComparableOperators<T2, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.FULL_OUTER_JOIN, joinedField));
    }

    @Override
    public <T2> JoinBuilder2<T1, T2> crossJoin(TableIdentifier<T2> joinedTable) {
        return new JoinBuilder2Impl<>(this, addStageBeanOf(joinedTable, JoinType.CROSS_JOIN));
    }

    private final class AfterJoinImpl<T2>
        extends BaseAfterJoin<T2, JoinBuilder2<T1, T2>>
        implements AfterJoin<T1, T2> {

        private AfterJoinImpl(StageBean<T2> stageBen) {
            super(JoinBuilder1Impl.this, stageBen, JoinBuilder2Impl::new);
        }
    }

}
