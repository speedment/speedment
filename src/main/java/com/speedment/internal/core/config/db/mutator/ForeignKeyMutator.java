package com.speedment.internal.core.config.db.mutator;

import com.speedment.config.db.ForeignKey;
import com.speedment.internal.core.config.db.mutator.trait.HasEnabledMutator;
import com.speedment.internal.core.config.db.mutator.trait.HasNameMutator;

/**
 *
 * @author Per Minborg
 */
public final class ForeignKeyMutator extends DocumentMutatorImpl implements DocumentMutator, HasEnabledMutator, HasNameMutator {

    ForeignKeyMutator(ForeignKey foreignKey) {
        super(foreignKey);
    }

}
