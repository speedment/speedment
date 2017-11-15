package com.speedment.runtime.bulk;

import com.speedment.runtime.config.identifier.HasTableIdentifier;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> tyep
 */
public interface Operation<ENTITY> {

    enum Type {
        PERSIST, UPDATE, REMOVE;
    }

    /**
     * Returns the identifier for this Operation.
     *
     * @return the identifier for this Operation
     */
    HasTableIdentifier<ENTITY> identifier();

    /**
     * Returns the type of Operation.
     *
     * @return the type of Operation
     */
    Type type();

}
