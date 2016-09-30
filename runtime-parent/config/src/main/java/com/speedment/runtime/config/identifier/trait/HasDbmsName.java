package com.speedment.runtime.config.identifier.trait;

/**
 *
 * @author Per Minborg
 */
public interface HasDbmsName {

    /**
     * Returns the database name of the {@link Dbms} that this configuration
     * object is in.
     *
     * @return the {@link Dbms} name
     */
    String getDbmsName();
}
