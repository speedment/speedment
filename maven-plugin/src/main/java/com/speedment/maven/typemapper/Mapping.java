package com.speedment.maven.typemapper;

import com.speedment.runtime.config.mapper.TypeMapper;

/**
 * A mapping between a particular database type and a {@link TypeMapper}.
 * This class is intended to be instantiated by the maven plugin when parsing
 * the pom.xml-file.
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
public final class Mapping {
    
    private String databaseType;
    private String implementation;

    public String getDatabaseType() {
        return databaseType;
    }

    public String getImplementation() {
        return implementation;
    }
}
