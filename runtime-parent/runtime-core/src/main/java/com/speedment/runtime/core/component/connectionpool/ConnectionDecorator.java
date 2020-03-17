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
package com.speedment.runtime.core.component.connectionpool;

import com.speedment.common.injector.annotation.InjectKey;

import java.sql.Connection;
import java.sql.SQLException;

@InjectKey(ConnectionDecorator.class)
public interface ConnectionDecorator {

    /**
     * Configures a newly allocated connection before use.
     *
     * @param connection to apply configuration to
     * @throws SQLException if unable to configure the connection
     * @throws NullPointerException if the provided {@code connection}
     * is {@code null}
     */
    void configure(Connection connection) throws SQLException;

    /**
     * Cleans up a used connection before it is returned to a connection pool.
     *
     * @param connection to apply configuration to
     * @throws SQLException if unable to cleanup the connection
     * @throws NullPointerException if the provided {@code connection}
     * is {@code null}
     */
    default void cleanup(Connection connection) throws SQLException {
        // Do nothing by default.
    }


}
