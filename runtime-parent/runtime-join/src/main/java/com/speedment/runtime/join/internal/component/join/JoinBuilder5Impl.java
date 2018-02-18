package com.speedment.runtime.join.internal.component.join;

import com.speedment.common.function.Function5;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.JoinComponent.JoinBuilder1.AfterJoin;
import com.speedment.runtime.join.JoinComponent.JoinBuilder1.JoinBuilder2.JoinBuilder3.JoinBuilder4.JoinBuilder5;
import java.util.function.Predicate;
import com.speedment.runtime.join.pipeline.Pipeline;
import static java.util.Objects.requireNonNull;

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

    @Override
    @SuppressWarnings("unchecked")
    public <T> Join<T> build(Function5<T1, T2, T3, T4, T5, T> constructor) {
        requireNonNull(constructor);
        final Pipeline p = pipeline();
        return streamSuppler().createJoin(
            p,
            constructor,
            (TableIdentifier<T1>) p.get(0).identifier(),
            (TableIdentifier<T2>) p.get(1).identifier(),
            (TableIdentifier<T3>) p.get(2).identifier(),
            (TableIdentifier<T4>) p.get(3).identifier(),
            (TableIdentifier<T5>) p.get(4).identifier()
        );
    }

}
