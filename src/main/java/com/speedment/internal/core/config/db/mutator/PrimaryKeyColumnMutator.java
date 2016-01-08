package com.speedment.internal.core.config.db.mutator;

import com.speedment.config.db.PrimaryKeyColumn;
import com.speedment.internal.core.config.db.mutator.trait.HasEnabledMutator;
import com.speedment.internal.core.config.db.mutator.trait.HasNameMutator;
import com.speedment.internal.core.config.db.mutator.trait.HasOrdinalPositionMutator;

/**
 *
 * @author Per Minborg
 */
public final class PrimaryKeyColumnMutator  extends DocumentMutatorImpl implements DocumentMutator, HasNameMutator, 
    HasEnabledMutator, HasOrdinalPositionMutator {

    PrimaryKeyColumnMutator(PrimaryKeyColumn primaryKeyColumn) {
        super(primaryKeyColumn);
    }

    
}