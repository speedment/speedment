package com.speedment.internal.core.config.db.mutator;

import com.speedment.config.db.ForeignKey;
import com.speedment.internal.core.config.db.mutator.impl.ForeignKeyMutatorImpl;
import com.speedment.internal.core.config.db.mutator.trait.HasEnabledMutator;
import com.speedment.internal.core.config.db.mutator.trait.HasNameMutator;

/**
 *
 * @author Per Minborg
 */
public interface ForeignKeyMutator extends DocumentMutator, HasEnabledMutator, HasNameMutator {

    static ForeignKeyMutator of(ForeignKey foreignKey) {
        return new ForeignKeyMutatorImpl(foreignKey);
    }

}
