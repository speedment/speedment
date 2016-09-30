package com.speedment.runtime.config.identifier.trait;

/**
 *
 * @author Per Minborg
 */
public interface HasTableName {

    /**
     * Returns the database name of the {@link Table} that this configuration
     * object is in.
     *
     * @return the {@link Table} name
     */
    String getTableName();

}
