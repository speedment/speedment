package com.speedment.config.db.trait;

import com.speedment.annotation.Api;
import com.speedment.config.Document;

/**
 *
 * @author Emil Forslund
 */
@Api(version = "2.3")
public interface HasAlias extends Document, HasName {
    
    final String ALIAS = "alias";
    
    default String getAlias() {
        return (String) get(ALIAS).orElse(getName());
    }
}