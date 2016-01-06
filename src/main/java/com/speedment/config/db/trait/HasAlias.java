package com.speedment.config.db.trait;

import com.speedment.config.Document;

/**
 *
 * @author Emil Forslund
 */
public interface HasAlias extends Document, HasName {
    
    final String ALIAS = "alias";
    
    default String getAlias() {
        return (String) get(ALIAS).orElse(getName());
    }
}