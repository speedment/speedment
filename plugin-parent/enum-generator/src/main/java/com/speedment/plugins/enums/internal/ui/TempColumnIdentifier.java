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

import com.speedment.runtime.config.identifier.ColumnIdentifier;

import static java.util.Objects.requireNonNull;

final class TempColumnIdentifier implements ColumnIdentifier<String> {
    private final String dbms;
    private final String schema;
    private final String table;
    private final String column;

    TempColumnIdentifier(
        final String dbms,
        final String schema,
        final String table,
        final String column
    ) {
        this.dbms = requireNonNull(dbms);
        this.schema = requireNonNull(schema);
        this.table = requireNonNull(table);
        this.column = requireNonNull(column);
    }

    @Override
    public String getDbmsId() {
        return dbms;
    }

    @Override
    public String getTableId() {
        return table;
    }

    @Override
    public String getColumnId() {
        return column;
    }

    @Override
    public String getSchemaId() {
        return schema;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ColumnIdentifier)) return false;

        final ColumnIdentifier<?> that = (ColumnIdentifier<?>) o;
        return dbms.equals(that.getDbmsId())
            && schema.equals(that.getSchemaId())
            && table.equals(that.getTableId())
            && column.equals(that.getColumnId());
    }

    @Override
    public int hashCode() {
        int result = dbms.hashCode();
        result = 31 * result + schema.hashCode();
        result = 31 * result + table.hashCode();
        result = 31 * result + column.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TempColumnIdentifier{" +
            "dbms='" + dbms + '\'' +
            ", schema='" + schema + '\'' +
            ", table='" + table + '\'' +
            ", column='" + column + '\'' +
            '}';
    }
}
