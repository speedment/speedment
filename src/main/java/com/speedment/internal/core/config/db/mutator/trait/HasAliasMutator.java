package com.speedment.internal.core.config.db.mutator.trait;

import com.speedment.config.db.trait.*;
import com.speedment.internal.core.config.db.mutator.DocumentMutator;

/**
 *
 * @author Per Minborg
 */
public interface HasAliasMutator extends DocumentMutator, HasNameMutator {
    
    default void setAlias(String alias) {
        put(HasAlias.ALIAS, alias);
    }
    
}