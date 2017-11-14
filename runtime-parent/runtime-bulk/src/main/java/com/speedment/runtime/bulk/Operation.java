package com.speedment.runtime.bulk;

import com.speedment.runtime.core.manager.Manager;

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
     * Returns the Manager for this Operation.
     *
     * @return the Manager for this Operation
     */
    Manager<ENTITY> manager();

    /**
     * Returns the type of Operation.
     *
     * @return the type of Operation
     */
    Type type();

}
