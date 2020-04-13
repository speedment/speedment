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
