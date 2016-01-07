package com.speedment.internal.core.config.db.mutator;

import com.speedment.config.Document;

/**
 *
 * @author Per Minborg
 */
public class DocumentMutatorImpl implements DocumentMutator {

    private final Document document;

    public DocumentMutatorImpl(Document document) {
        this.document = document;
    }

    @Override
    public void put(String key, Object value) {
//        if (value == null) {
//            document.remove(key);
//        }
        document.put(key, value);
    }

}
