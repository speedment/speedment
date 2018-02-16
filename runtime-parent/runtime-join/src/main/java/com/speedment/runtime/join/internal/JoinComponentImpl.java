package com.speedment.runtime.join.internal;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.join.JoinComponent;

/**
 *
 * @author Per Minborg
 */
public class JoinComponentImpl implements JoinComponent {

    private enum JoinType {
        INNER_JOIN,
        LEFT_JOIN,
        RIGH_JOIN,
        FULL_OUTER_JOIN,
        CROSS_JOIN
    }

    @Override
    public <T1> AfterFrom<T1> from(TableIdentifier<T1> firstManager) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
