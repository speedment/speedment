package com.speedment.runtime.config.identifier.trait;

/**
 *
 * @author Per Minborg
 */
public interface HasColumnName {

    /**
     * Returns the database name of the {@link Column} that this configuration
     * object is in.
     *
     * @return the {@link Column} name
     */
    String getColumnName();

}
