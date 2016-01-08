package com.speedment.internal.core.config.db.mutator;

import com.speedment.config.db.IndexColumn;
import com.speedment.internal.core.config.db.mutator.trait.HasNameMutator;
import com.speedment.internal.core.config.db.mutator.trait.HasOrderTypeMutator;
import com.speedment.internal.core.config.db.mutator.trait.HasOrdinalPositionMutator;

/**
 *
 * @author Per Minborg
 */
public final class IndexColumnMutator extends DocumentMutatorImpl implements DocumentMutator, HasNameMutator,
        HasOrdinalPositionMutator, HasOrderTypeMutator {

    IndexColumnMutator(IndexColumn indexColumn) {
        super(indexColumn);
    }

}
