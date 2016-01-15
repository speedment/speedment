package com.speedment.internal.core.code;

import com.speedment.code.TranslatorKey;
import com.speedment.config.db.trait.HasMainInterface;

/**
 *
 * @author Per Minborg
 * @param <T> Document type
 */
public class TranslatorKeyImpl<T extends HasMainInterface> implements TranslatorKey<T> {

    private final String key;

    public TranslatorKeyImpl(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }

}
