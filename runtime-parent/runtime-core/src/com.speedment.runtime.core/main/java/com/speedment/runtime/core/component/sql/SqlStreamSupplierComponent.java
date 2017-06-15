/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.core.component.sql;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.component.StreamSupplierComponent;
import com.speedment.runtime.core.db.SqlFunction;

import java.sql.ResultSet;

/**
 * A specialization of the {@link StreamSupplierComponent}-interface that 
 * specializes in producing streams from a relational SQL database.
 * 
 * @author  Per Minborg
 * @since   3.0.1
 */
public interface SqlStreamSupplierComponent extends StreamSupplierComponent {

    /**
     * Installs the specified entity mapper for the specified table in this 
     * component.
     * <p>
     * Every manager that represents a SQL table is required to invoke this 
     * method as part of its configuration cycle.
     * 
     * @param <ENTITY>         the entity type
     * @param tableIdentifier  identifier for the table
     * @param entityMapper     the mapper between SQL result and entity to use
     */
    <ENTITY> void install(
            TableIdentifier<ENTITY> tableIdentifier, 
            SqlFunction<ResultSet, ENTITY> entityMapper
    );
}