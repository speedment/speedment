package com.speedment.config.db.trait;

import com.speedment.annotation.Api;
import com.speedment.config.Document;
import java.util.Optional;

/**
 *
 * @author Emil Forslund
 */
@Api(version = "2.3")
public interface HasAlias extends Document, HasName {
    
    final String ALIAS = "alias";
    
    default Optional<String> getAlias() {
        return getAsString(ALIAS);
    }
    
    default String getJavaName() {
        return getAlias().orElse(getName());
    }
    
}