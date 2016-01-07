package com.speedment.internal.core.config.db.mutator;

import com.speedment.config.db.IndexColumn;
import com.speedment.internal.core.config.db.mutator.impl.IndexColumnMutatorImpl;
import com.speedment.internal.core.config.db.mutator.trait.HasNameMutator;
import com.speedment.internal.core.config.db.mutator.trait.HasOrderTypeMutator;
import com.speedment.internal.core.config.db.mutator.trait.HasOrdinalPositionMutator;

/**
 *
 * @author Per Minborg
 */
public interface IndexColumnMutator extends DocumentMutator, HasNameMutator,
        HasOrdinalPositionMutator, HasOrderTypeMutator {

    static IndexColumnMutator of(IndexColumn indexColumn) {
        return new IndexColumnMutatorImpl(indexColumn);
    }

}
