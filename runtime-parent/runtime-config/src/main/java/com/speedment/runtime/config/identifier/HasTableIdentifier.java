package com.speedment.runtime.config.identifier;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> type
 * @since 3.0.18
 */
public interface HasTableIdentifier<ENTITY> {

    /**
     * Returns the {@link TableIdentifier } for the table that this object
     * is handling.
     *
     * @return the table identifier
     */
    TableIdentifier<ENTITY> getTableIdentifier();

}
