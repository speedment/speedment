package com.speedment.runtime.join.internal.component.join;

import com.speedment.common.function.QuadFunction;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.JoinComponent.JoinBuilder1.JoinBuilder2.JoinBuilder3.JoinBuilder4;
import com.speedment.runtime.join.stage.JoinType;
import com.speedment.runtime.join.stage.Stage;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
final class JoinBuilder4Impl<T1, T2, T3, T4>
    extends AbstractJoinBuilder<T4, JoinBuilder4<T1, T2, T3, T4>>
    implements JoinBuilder4<T1, T2, T3, T4> {

    JoinBuilder4Impl(AbstractJoinBuilder<?, ?> previousStage, StageBean<T4> current) {
        super(previousStage, current);
    }

    @Override
    public <T5> AfterJoin<T1, T2, T3, T4, T5> innerJoinOn(HasComparableOperators<T5, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.INNER_JOIN, joinedField));
    }

    @Override
    public <T5> AfterJoin<T1, T2, T3, T4, T5> leftJoinOn(HasComparableOperators<T5, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.LEFT_JOIN, joinedField));
    }

    @Override
    public <T5> AfterJoin<T1, T2, T3, T4, T5> rightJoinOn(HasComparableOperators<T5, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.RIGHT_JOIN, joinedField));
    }

//    @Override
//    public <T5> AfterJoin<T1, T2, T3, T4, T5> fullOuterJoinOn(HasComparableOperators<T5, ?> joinedField) {
//        return new AfterJoinImpl<>(addStageBeanOf(JoinType.FULL_OUTER_JOIN, joinedField));
//    }

    @Override
    public <T5> JoinBuilder5<T1, T2, T3, T4, T5> crossJoin(TableIdentifier<T5> joinedTable) {
        return new JoinBuilder5Impl<>(this, addStageBeanOf(joinedTable, JoinType.CROSS_JOIN));
    }

    private final class AfterJoinImpl<T5>
        extends BaseAfterJoin<T5, JoinBuilder5<T1, T2, T3, T4, T5>>
        implements AfterJoin<T1, T2, T3, T4, T5> {

        private AfterJoinImpl(StageBean<T5> stageBean) {
            super(JoinBuilder4Impl.this, stageBean, JoinBuilder5Impl::new);
        }

    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Join<T> build(QuadFunction<T1, T2, T3, T4, T> constructor) {
        requireNonNull(constructor);
        assertFieldsAreInJoinTables();
        final List<Stage<?>> stages = stages();
        return streamSuppler().createJoin(
            stages,
            constructor,
            (TableIdentifier<T1>) stages.get(0).identifier(),
            (TableIdentifier<T2>) stages.get(1).identifier(),
            (TableIdentifier<T3>) stages.get(2).identifier(),
            (TableIdentifier<T4>) stages.get(3).identifier()
        );
    }

}
