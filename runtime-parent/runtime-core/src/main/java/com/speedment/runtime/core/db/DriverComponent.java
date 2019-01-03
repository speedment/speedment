package com.speedment.runtime.core.db;

import com.speedment.common.injector.annotation.InjectKey;

import java.sql.Driver;
import java.util.Optional;

/**
 * Component used by connectors to obtain a {@link java.sql.Driver} instance.
 *
 * @author Emil Forslund
 * @since  3.1.10
 */
@InjectKey(DriverComponent.class)
public interface DriverComponent {

    /**
     * Returns the {@link Driver} with the specified class name if it exists on
     * the class path, and otherwise an empty optional.
     *
     * @param className  the class name to the driver class
     * @return           the driver instance or empty if it can't be found
     */
    Optional<Driver> driver(String className);

}
