package com.speedment.runtime.core.internal.db;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.runtime.core.db.DriverComponent;

import java.lang.reflect.InvocationTargetException;
import java.sql.Driver;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of the {@link DriverComponent}.
 */
public final class DriverComponentImpl implements DriverComponent {

    private @Inject Injector injector;

    @Override
    public Optional<Driver> driver(String driverName) {
        requireNonNull(driverName);
        Driver driver = null;
        try {
            final Class<?> driverClass = Class.forName(
                driverName,
                true,
                injector.classLoader()
            );
            if (Driver.class.isAssignableFrom(driverClass)) {
                driver = (Driver) driverClass.getDeclaredConstructor().newInstance();
            }
        } catch (final ClassNotFoundException ex) {
            try {
                // Some JavaEE servers, notably Tomcat, runs the driver on the
                // standard classloader.  This is the reason we need to check an
                // extra time.
                final Class<?> driverClass = Class.forName(driverName);
                if (Driver.class.isAssignableFrom(driverClass)) {
                    driver = (Driver) driverClass.getDeclaredConstructor().newInstance();
                }
            } catch (final ClassNotFoundException | IllegalAccessException
                | InstantiationException | NoSuchMethodException
                | InvocationTargetException ex2) {
                // Do nothing.
            }
        } catch (IllegalAccessException | InstantiationException
            | NoSuchMethodException | InvocationTargetException ex3) {
            // Do nothing.
        }
        return Optional.ofNullable(driver);
    }
}
