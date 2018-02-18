package com.speedment.runtime.join.internal.component.join;

import com.speedment.common.function.QuadFunction;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.JoinComponent.JoinBuilder1.JoinBuilder2.JoinBuilder3.JoinBuilder4;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T5> AfterJoin<T1, T2, T3, T4, T5> leftJoin(TableIdentifier<T5> joinedTable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T5> AfterJoin<T1, T2, T3, T4, T5> rightJoin(TableIdentifier<T5> joinedTable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T5> AfterJoin<T1, T2, T3, T4, T5> fullOuterJoin(TableIdentifier<T5> joinedTable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T5> JoinBuilder5<T1, T2, T3, T4, T5> crossJoin(TableIdentifier<T5> joinedTable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JoinBuilder4<T1, T2, T3, T4> where(Predicate<? super T4> predicate) {
        addPredicate(predicate);
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Join<T> build(QuadFunction<T1, T2, T3, T4, T> constructor) {
        requireNonNull(constructor);
        final Pipeline p = pipeline();
        return streamSuppler().createJoin(
            p,
            constructor,
            (TableIdentifier<T1>) p.get(0).identifier(),
            (TableIdentifier<T2>) p.get(1).identifier(),
            (TableIdentifier<T3>) p.get(2).identifier(),
            (TableIdentifier<T4>) p.get(3).identifier()
        );
    }

}
