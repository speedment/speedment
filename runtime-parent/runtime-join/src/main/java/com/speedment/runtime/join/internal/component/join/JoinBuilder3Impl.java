package com.speedment.runtime.join.internal.component.join;

import com.speedment.common.function.TriFunction;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.JoinComponent.JoinBuilder1.JoinBuilder2.JoinBuilder3;
import com.speedment.runtime.join.stage.JoinType;
import com.speedment.runtime.join.stage.Stage;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
final class JoinBuilder3Impl<T1, T2, T3>
    extends AbstractJoinBuilder<T3, JoinBuilder3<T1, T2, T3>>
    implements JoinBuilder3<T1, T2, T3> {

    JoinBuilder3Impl(AbstractJoinBuilder<?, ?> previousStage, StageBean<T3> current) {
        super(previousStage, current);
    }

    @Override
    public <T4> AfterJoin<T1, T2, T3, T4> innerJoinOn(HasComparableOperators<T4, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.INNER_JOIN, joinedField));
    }

    @Override
    public <T4> AfterJoin<T1, T2, T3, T4> leftJoinOn(HasComparableOperators<T4, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.LEFT_JOIN, joinedField));
    }

    @Override
    public <T4> AfterJoin<T1, T2, T3, T4> rightJoinOn(HasComparableOperators<T4, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.RIGHT_JOIN, joinedField));
    }

    @Override
    public <T4> AfterJoin<T1, T2, T3, T4> fullOuterJoinOn(HasComparableOperators<T4, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.FULL_OUTER_JOIN, joinedField));
    }

    @Override
    public <T4> JoinBuilder4<T1, T2, T3, T4> crossJoin(TableIdentifier<T4> joinedTable) {
        return new JoinBuilder4Impl<>(this, addStageBeanOf(joinedTable, JoinType.CROSS_JOIN));
    }

    private final class AfterJoinImpl<T4>
        extends BaseAfterJoin<T4, JoinBuilder4<T1, T2, T3, T4>>
        implements AfterJoin<T1, T2, T3, T4> {

        private AfterJoinImpl(StageBean<T4> stageBean) {
            super(JoinBuilder3Impl.this, stageBean, JoinBuilder4Impl::new);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Join<T> build(TriFunction<T1, T2, T3, T> constructor) {
        requireNonNull(constructor);
        assertFieldsAreInJoinTables();
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
