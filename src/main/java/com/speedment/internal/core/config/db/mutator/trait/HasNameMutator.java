package com.speedment.internal.core.config.db.mutator.trait;

import com.speedment.config.db.trait.*;
import com.speedment.internal.core.config.db.mutator.DocumentMutator;

/**
 *
 * @author Per Minborg
 */
public interface HasNameMutator extends DocumentMutator {
    
    default void setName(String name) {
        put(HasName.NAME, name);
    }
}