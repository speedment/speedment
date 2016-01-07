package com.speedment.internal.core.config.db.mutator.trait;

import com.speedment.config.db.trait.*;
import com.speedment.internal.core.config.db.mutator.DocumentMutator;

/**
 *
 * @author Per Minborg
 */
public interface HasOrdinalPositionMutator extends DocumentMutator {
    
    default void setOrdinalPosition(Integer ordinalPosition) {
        put(HasOrdinalPosition.ORDINAL_POSITION, ordinalPosition);
    }
}