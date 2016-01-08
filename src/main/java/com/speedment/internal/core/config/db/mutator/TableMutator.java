package com.speedment.internal.core.config.db.mutator;

import com.speedment.config.db.*;
import com.speedment.internal.core.config.db.mutator.trait.HasAliasMutator;
import com.speedment.internal.core.config.db.mutator.trait.HasEnabledMutator;
import com.speedment.internal.core.config.db.mutator.trait.HasNameMutator;

/**
 *
 * @author Per Minborg
 */
public final class TableMutator extends DocumentMutatorImpl implements DocumentMutator, HasEnabledMutator, HasNameMutator, HasAliasMutator {

    TableMutator(Table table) {
        super(table);
    }

}
