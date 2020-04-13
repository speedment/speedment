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
package com.speedment.runtime.core.manager.sql;

import java.util.List;

/**
 * Statement that can be sent to a SQL Database.
 *
 * @author Emil Forslund
 * @since  3.1.4
 */
public interface SqlStatement {

    /**
     * The type of statement.
     */
    enum Type {
        INSERT, UPDATE, DELETE
    }

    /**
     * The type of SQL Statement that this is.
     *
     * @return  the type
     */
    Type getType();

    /**
     * Returns the parameterized SQL string that will be sent to the database.
     *
     * @return  the parameterized SQL string
     */
    String getSql();

    /**
     * Returns the parameters of the statement.
     *
     * @return  the parameters
     */
    List<Object> getValues();
}