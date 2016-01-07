package com.speedment.internal.core.config.db.mutator.trait;

import com.speedment.config.db.trait.*;
import com.speedment.internal.core.config.db.mutator.DocumentMutator;

/**
 *
 * @author Per Minborg
 */
public interface HasEnabledMutator extends DocumentMutator {
    
    default void setEnabled(Boolean enabled) {
        put(HasEnabled.ENABLED, enabled);
    }
}