package com.speedment.config.db.trait;

import com.speedment.config.Document;

/**
 *
 * @author Emil
 */
public interface HasEnabled extends Document {
    
    final String ENABLED = "enabled";
    
    default boolean isEnabled() {
        return getAsBoolean(ENABLED).orElse(true);
    }
}