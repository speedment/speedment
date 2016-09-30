/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.config.internal.identifier;

import com.speedment.runtime.config.identifier.TableIdentifier;

/**
 *
 * @author Per Minborg
 */
public final class TableIdentifierImpl<ENTITY> implements TableIdentifier<ENTITY> {

    private final String dbmsName, schemaName, tableName;

    public TableIdentifierImpl(String dbmsName, String schemaName, String tableName) {
        this.dbmsName = dbmsName;
        this.schemaName = schemaName;
        this.tableName = tableName;
    }

    @Override
    public String getDbmsName() {
        return dbmsName;
    }

    @Override
    public String getSchemaName() {
        return schemaName;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

}
