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
package com.speedment.plugins.enums.internal.ui;

import com.speedment.common.injector.annotation.Config;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.component.SqlAdapter;
import com.speedment.runtime.core.db.SqlFunction;

import java.sql.ResultSet;

import static java.util.Objects.requireNonNull;

final class SingleColumnSqlAdapter implements SqlAdapter<String> {

    private final TableIdentifier<String> tableId;
    private final String column;

    public SingleColumnSqlAdapter(
        @Config(name = "temp.dbms", value = "") final String dbms,
        @Config(name = "temp.schema", value = "") final String schema,
        @Config(name = "temp.table", value = "") final String table,
        @Config(name = "temp.column", value = "") final String column
    ) {
        this.column = requireNonNull(column);
        this.tableId = TableIdentifier.of(
            requireNonNull(dbms),
            requireNonNull(schema),
            requireNonNull(table)
        );
    }

    @Override
    public TableIdentifier<String> identifier() {
        return tableId;
    }

    @Override
    public SqlFunction<ResultSet, String> entityMapper() {
        return in -> in.getString(column);
    }

    @Override
    public SqlFunction<ResultSet, String> entityMapper(int offset) {
        return entityMapper(); // We do not use index and offset
    }

}
