package com.speedment.runtime.config.identifier.trait;

/**
 *
 * @author Per Minborg
 */
public interface HasSchemaName {

    /**
     * Returns the database name of the {@link Schema} that this configuration
     * object is in.
     *
     * @return the {@link Schema} name
     */
    String getSchemaName();

}
