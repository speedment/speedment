/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.runtime.core.internal.db;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.runtime.core.db.DriverComponent;

import java.lang.reflect.InvocationTargetException;
import java.sql.Driver;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static com.speedment.common.injector.State.STARTED;
import static java.util.Objects.requireNonNull;

/**
 * Default implementation of the {@link DriverComponent}.
 */
public final class DriverComponentImpl implements DriverComponent {

    // Driver names are cached because getting a Driver
    // is expensive and is called within the get connection loop
    // in the ConnectionPoolComponent,
    // See https://github.com/speedment/speedment/issues/725
    // And yes, we should not put Optionals in Maps but
    // here it was so convenient.  :-)
    private final Map<String, Optional<Driver>> cache;
    private Injector injector;

    public DriverComponentImpl() {
        this.cache = new ConcurrentHashMap<>();
    }

    @ExecuteBefore(STARTED)
    public void setInjector(Injector injector) {
        this.injector = injector;
    }

    @Override
    public Optional<Driver> driver(String driverName) {
        return cache.computeIfAbsent(driverName, this::driverHelper);
    }

    private Optional<Driver> driverHelper(String driverName) {
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
