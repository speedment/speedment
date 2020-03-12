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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.PrimaryKeyColumn;
import com.speedment.runtime.config.Schema;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.core.internal.db.DefaultDatabaseNamingConvention;
import org.junit.jupiter.api.Test;

final class DatabaseNamingConventionTest {

    @Test
    @SuppressWarnings("unchecked")
    void fullNameOf() {
        final DatabaseNamingConvention databaseNamingConvention = new DefaultDatabaseNamingConvention();

        final ColumnIdentifier<String> columnIdentifier = mock(ColumnIdentifier.class);
        when(columnIdentifier.getSchemaId()).thenReturn("schema");
        when(columnIdentifier.getTableId()).thenReturn("table");
        when(columnIdentifier.getColumnId()).thenReturn("column");

        assertNotNull(databaseNamingConvention.fullNameOf(columnIdentifier));

        final Schema schema = mock(Schema.class);
        when(schema.getName()).thenReturn("schema");

        final Table table = mock(Table.class);
        when(table.getName()).thenReturn("table");
        when(table.getParentOrThrow()).thenReturn(schema);

        assertNotNull(databaseNamingConvention.fullNameOf(table));

        final Column column = mock(Column.class);
        when(column.getName()).thenReturn("column");
        when(column.getParentOrThrow()).thenReturn(table);

        assertNotNull(databaseNamingConvention.fullNameOf(column));

        final PrimaryKeyColumn primaryKeyColumn = mock(PrimaryKeyColumn.class);
        when(primaryKeyColumn.findColumnOrThrow()).thenReturn(column);

        assertNotNull(databaseNamingConvention.fullNameOf(primaryKeyColumn));
    }
}
