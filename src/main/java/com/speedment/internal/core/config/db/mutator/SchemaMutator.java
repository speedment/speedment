package com.speedment.internal.core.config.db.mutator;

import com.speedment.config.db.Schema;
import static com.speedment.config.db.Schema.DEFAULT_SCHEMA;
import com.speedment.internal.core.config.db.mutator.trait.HasAliasMutator;
import com.speedment.internal.core.config.db.mutator.trait.HasEnabledMutator;
import com.speedment.internal.core.config.db.mutator.trait.HasNameMutator;

/**
 *
 * @author Per Minborg
 */
public final class SchemaMutator extends DocumentMutatorImpl implements DocumentMutator, HasEnabledMutator, HasNameMutator, HasAliasMutator {

    SchemaMutator(Schema schema) {
        super(schema);
    }

    public void setDefaultSchema(Boolean defaultSchema) {
        put(DEFAULT_SCHEMA, defaultSchema);
    }
    
}