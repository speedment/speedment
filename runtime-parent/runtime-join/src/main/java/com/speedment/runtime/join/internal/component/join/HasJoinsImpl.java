package com.speedment.runtime.join.internal.component.join;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.join.trait.HasJoins;

/**
 *
 * @author Per Minborg
 */
public class HasJoinsImpl<R, RC> implements HasJoins<R, RC> {

    @Override
    public <ENTITY> R innerJoin(TableIdentifier<ENTITY> joinedTable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <ENTITY> R leftJoin(TableIdentifier<ENTITY> joinedTable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <ENTITY> R rightJoin(TableIdentifier<ENTITY> joinedTable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <ENTITY> R fullOuterJoin(TableIdentifier<ENTITY> joinedTable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <ENTITY> RC crossJoin(TableIdentifier<ENTITY> joinedTable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
