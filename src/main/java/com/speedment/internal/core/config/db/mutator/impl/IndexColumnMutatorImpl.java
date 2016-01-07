package com.speedment.internal.core.config.db.mutator.impl;

import com.speedment.config.Document;
import com.speedment.internal.core.config.db.mutator.DocumentMutatorImpl;
import com.speedment.internal.core.config.db.mutator.IndexColumnMutator;

/**
 *
 * @author Per Minborg
 */
public class IndexColumnMutatorImpl extends DocumentMutatorImpl implements IndexColumnMutator {

    public IndexColumnMutatorImpl(Document document) {
        super(document);
    }
    
}
