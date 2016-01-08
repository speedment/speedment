package com.speedment.internal.core.config.db.mutator;

import com.speedment.config.db.Index;
import static com.speedment.config.db.Index.*;
import com.speedment.internal.core.config.db.mutator.trait.HasEnabledMutator;
import com.speedment.internal.core.config.db.mutator.trait.HasNameMutator;

/**
 *
 * @author Per Minborg
 */
public final class IndexMutator extends DocumentMutatorImpl implements DocumentMutator, HasEnabledMutator, HasNameMutator {

    IndexMutator(Index index) {
        super(index);
    }

    public void setUnique(Boolean unique) {
        put(UNIQUE, unique);
    }

}
