package com.speedment.config.db.trait;

import com.speedment.annotation.Api;
import com.speedment.config.Document;

/**
 *
 * @author Emil Forslund
 */
@Api(version = "2.3")
public interface HasEnabled extends Document {
    
    final String ENABLED = "enabled";
    
    default boolean isEnabled() {
        return getAsBoolean(ENABLED).orElse(true);
    }
}