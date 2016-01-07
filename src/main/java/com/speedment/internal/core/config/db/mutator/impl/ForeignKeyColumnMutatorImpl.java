package com.speedment.internal.core.config.db.mutator.impl;

import com.speedment.config.Document;
import com.speedment.internal.core.config.db.mutator.DocumentMutatorImpl;
import com.speedment.internal.core.config.db.mutator.ForeignKeyColumnMutator;

/**
 *
 * @author Per Minborg
 */
public class ForeignKeyColumnMutatorImpl extends DocumentMutatorImpl implements ForeignKeyColumnMutator {

    public ForeignKeyColumnMutatorImpl(Document document) {
        super(document);
    }
    
}
