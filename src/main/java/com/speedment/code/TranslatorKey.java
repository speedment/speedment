package com.speedment.code;

import com.speedment.config.db.trait.HasMainInterface;

/**
 *
 * @author Per Minborg
 * @param <T> Document type
 */
public interface TranslatorKey<T extends HasMainInterface> {

    String getKey();
    

}
