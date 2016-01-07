package com.speedment.internal.core.config.db.mutator.impl;

import com.speedment.config.Document;
import com.speedment.internal.core.config.db.mutator.DocumentMutatorImpl;
import com.speedment.internal.core.config.db.mutator.ForeignKeyMutator;

/**
 *
 * @author Per Minborg
 */
public class ForeignKeyMutatorImpl extends DocumentMutatorImpl implements ForeignKeyMutator {

    public ForeignKeyMutatorImpl(Document document) {
        super(document);
    }
    
}
