package com.speedment.runtime.join.internal.component.join;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.JoinComponent.JoinBuilder1.JoinBuilder2;
import com.speedment.runtime.join.stage.JoinType;
import com.speedment.runtime.join.stage.Stage;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.function.BiFunction;

/**
 *
 * @author Per Minborg
 */
final class JoinBuilder2Impl<T1, T2>
    extends AbstractJoinBuilder<T2, JoinBuilder2<T1, T2>>
    implements JoinBuilder2<T1, T2> {

    JoinBuilder2Impl(AbstractJoinBuilder<?, ?> previousStage, StageBean<T2> current) {
        super(previousStage, current);
    }

    @Override
    public <T3> AfterJoin<T1, T2, T3> innerJoinOn(HasComparableOperators<T3, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.INNER_JOIN, joinedField));
    }

    @Override
    public <T3> AfterJoin<T1, T2, T3> leftJoinOn(HasComparableOperators<T3, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.LEFT_JOIN, joinedField));
    }

    @Override
    public <T3> AfterJoin<T1, T2, T3> rightJoinOn(HasComparableOperators<T3, ?> joinedField) {
        return new AfterJoinImpl<>(addStageBeanOf(JoinType.RIGHT_JOIN, joinedField));
    }

//    @Override
//    public <T3> AfterJoin<T1, T2, T3> fullOuterJoinOn(HasComparableOperators<T3, ?> joinedField) {
//        return new AfterJoinImpl<>(addStageBeanOf(JoinType.FULL_OUTER_JOIN, joinedField));
//    }

    @Override
    public <T3> JoinBuilder3<T1, T2, T3> crossJoin(TableIdentifier<T3> joinedTable) {
        return new JoinBuilder3Impl<>(this, addStageBeanOf(joinedTable, JoinType.CROSS_JOIN));
    }

    private final class AfterJoinImpl<T3>
        extends BaseAfterJoin<T3, JoinBuilder3<T1, T2, T3>>
        implements AfterJoin<T1, T2, T3> {

        private AfterJoinImpl(StageBean<T3> stageBean) {
            super(JoinBuilder2Impl.this, stageBean, JoinBuilder3Impl::new);
        }

    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Join<T> build(BiFunction<T1, T2, T> constructor) {
        requireNonNull(constructor);
        assertFieldsAreInJoinTables();
        final List<Stage<?>> stages = stages();
        return streamSuppler().createJoin(
            stages,
            constructor,
            (TableIdentifier<T1>) stages.get(0).identifier(),
            (TableIdentifier<T2>) stages.get(1).identifier()
        );
    }

}
