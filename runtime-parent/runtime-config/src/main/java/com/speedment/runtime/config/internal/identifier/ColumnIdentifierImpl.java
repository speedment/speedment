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
package com.speedment.runtime.config.internal.identifier;

import com.speedment.runtime.config.identifier.ColumnIdentifier;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of the {@link ColumnIdentifier}. Note that for
 * performance reasons, usually the interface is implemented using a generated
 * {@code enum} and not by this class. This implementation should only be used
 * when fully dynamic identifiers are required.
 * <p>
 * This implementation is immutable and may be used as key in a
 * {@link java.util.Map}.
 *
 * @author Emil Forslund
 * @since  3.0.15
 */
public final class ColumnIdentifierImpl<ENTITY>
implements ColumnIdentifier<ENTITY> {

    private final String dbmsName;
    private final String schemaName;
    private final String tableName;
    private final String columnName;

    public ColumnIdentifierImpl(
            final String dbmsName,
            final String schemaName,
            final String tableName,
            final String columnName) {
        this.dbmsName   = requireNonNull(dbmsName);
        this.schemaName = requireNonNull(schemaName);
        this.tableName  = requireNonNull(tableName);
        this.columnName = requireNonNull(columnName);
    }

    @Override
    public String getDbmsId() {
        return dbmsName;
    }

    @Override
    public String getSchemaId() {
        return schemaName;
    }

    @Override
    public String getTableId() {
        return tableName;
    }

    @Override
    public String getColumnId() {
        return columnName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ColumnIdentifier)) return false;

        final ColumnIdentifier<?> that = (ColumnIdentifier<?>) o;
        return getDbmsId().equals(that.getDbmsId())
            && getSchemaId().equals(that.getSchemaId())
            && getTableId().equals(that.getTableId())
            && getColumnId().equals(that.getColumnId());
    }

    @Override
    public int hashCode() {
        int result = getDbmsId().hashCode();
        result = 31 * result + getSchemaId().hashCode();
        result = 31 * result + getTableId().hashCode();
        result = 31 * result + getColumnId().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ColumnIdentifier{" +
            dbmsName + '.' +
            schemaName + '.' +
            tableName + '.' +
            columnName + '}';
    }
}
