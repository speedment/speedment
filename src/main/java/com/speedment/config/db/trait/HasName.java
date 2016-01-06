package com.speedment.config.db.trait;

import com.speedment.config.Document;

/**
 *
 * @author Emil Forslund
 */
public interface HasName extends Document {
    
    final String NAME = "name";
    
    default String getName() {
        return (String) get(NAME).get();
    }
}