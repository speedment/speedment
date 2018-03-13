package com.speedment.runtime.join.internal.component.join;

import com.speedment.common.function.Function5;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.JoinComponent.JoinBuilder1.JoinBuilder2.JoinBuilder3.JoinBuilder4.JoinBuilder5;
import com.speedment.runtime.join.stage.JoinType;
import com.speedment.runtime.join.stage.Stage;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
final class JoinBuilder5Impl<T1, T2, T3, T4, T5>
    extends AbstractJoinBuilder<T5, JoinBuilder5<T1, T2, T3, T4, T5>>
    implements JoinBuilder5<T1, T2, T3, T4, T5> {

    JoinBuilder5Impl(AbstractJoinBuilder<?, ?> previousStage, StageBean<T5> current) {
        super(previousStage, current);
    }

    @Override
    public <T6> AfterJoin<T1, T2, T3, T4, T5, T6> innerJoinOn(HasComparableOperators<T6, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.INNER_JOIN, joinedField));
    }

    @Override
    public <T6> AfterJoin<T1, T2, T3, T4, T5, T6> leftJoinOn(HasComparableOperators<T6, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.LEFT_JOIN, joinedField));
    }

    @Override
    public <T6> AfterJoin<T1, T2, T3, T4, T5, T6> rightJoinOn(HasComparableOperators<T6, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.RIGHT_JOIN, joinedField));
    }

//    @Override
//    public <T6> AfterJoin<T1, T2, T3, T4, T5, T6> fullOuterJoinOn(HasComparableOperators<T6, ?> joinedField) {
//        return new AfterJoinImpl<>(addStageBeanOf(JoinType.FULL_OUTER_JOIN, joinedField));
//    }

    @Override
    public <T6> JoinBuilder6<T1, T2, T3, T4, T5, T6> crossJoin(TableIdentifier<T6> joinedTable) {
        return new JoinBuilder6Impl<>(this, addStageBeanOf(joinedTable, JoinType.CROSS_JOIN));
    }

    private final class AfterJoinImpl<T6>
        extends BaseAfterJoin<T6, JoinBuilder6<T1, T2, T3, T4, T5, T6>>
        implements AfterJoin<T1, T2, T3, T4, T5, T6> {

        private AfterJoinImpl(StageBean<T6> stageBean) {
            super(JoinBuilder5Impl.this, stageBean, JoinBuilder6Impl::new);
        }

    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Join<T> build(Function5<T1, T2, T3, T4, T5, T> constructor) {
        requireNonNull(constructor);
        assertFieldsAreInJoinTables();
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
