/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.db.crud;

import com.speedment.annotation.Api;
import com.speedment.config.Table;

/**
 * @author Emil Forslund
 */
@Api(version = "2.2")
public interface CrudOperation {

    /**
     * The type of this CRUD operation.
     */
    enum Type { CREATE, READ, UPDATE, DELETE }

    /**
     * Returns the type of this CRUD operation.
     *
     * @return  the type
     */
    Type getType();

    /**
     * Returns the {@link Table} that this operation should operate on.
     *
     * @return  the table
     */
    Table getTable();
}
