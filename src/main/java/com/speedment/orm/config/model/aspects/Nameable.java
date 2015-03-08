package com.speedment.orm.config.model.aspects;

import java.util.Optional;

/**
 *
 * @author Emil Forslund
 */
public interface Nameable {

    final int NAMEABLE_FIRST = 1;

    void setName(String name);

    String getName();

    default int compareToHelper(Nameable that) {
        return this.getName().compareTo(that.getName());
    }

    default boolean hasName() {
        return Optional
            .ofNullable(getName())
            .filter(n -> !n.isEmpty())
            .isPresent();
    }
}
