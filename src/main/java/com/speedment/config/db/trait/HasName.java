package com.speedment.config.db.trait;

import com.speedment.annotation.Api;
import com.speedment.config.Document;

/**
 *
 * @author Emil Forslund
 */
@Api(version = "2.3")
public interface HasName extends Document {
    
    final String NAME = "name";
    
    default String getName() {
        return (String) get(NAME).get();
    }
}