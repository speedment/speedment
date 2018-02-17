package com.speedment.runtime.join.internal.component.join;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.join.Join;
import com.speedment.runtime.join.JoinComponent.JoinBuilder1.JoinBuilder2;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.function.BiFunction;
import java.util.function.Predicate;

/**
 *
 * @author Per Minborg
 */
final class JoinBuilder2Impl<T1, T2> extends AbstractJoinBuilder implements JoinBuilder2<T1, T2> {

    private final StageBean<T2> current;

    JoinBuilder2Impl(List<StageBean<?>> previousStages, StageBean<T2> current) {
        super(previousStages);
        this.current = requireNonNull(current);
    }

    @Override
    public <T3> AfterJoin<T1, T2, T3> innerJoin(TableIdentifier<T3> joinedTable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T3> AfterJoin<T1, T2, T3> leftJoin(TableIdentifier<T3> joinedTable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T3> AfterJoin<T1, T2, T3> rightJoin(TableIdentifier<T3> joinedTable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T4> AfterJoin<T1, T2, T4> fullOuterJoin(TableIdentifier<T4> joinedTable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T4> JoinBuilder3<T1, T2, T4> crossJoin(TableIdentifier<T4> joinedTable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JoinBuilder2<T1, T2> where(Predicate<? super T2> predicate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> Join<T> build(BiFunction<T1, T2, T> constructor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
