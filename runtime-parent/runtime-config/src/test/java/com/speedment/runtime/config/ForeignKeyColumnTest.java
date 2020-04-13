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

package com.speedment.runtime.config;

import static com.speedment.runtime.config.ForeignKeyColumnUtil.FOREIGN_COLUMN_NAME;
import static com.speedment.runtime.config.ForeignKeyColumnUtil.FOREIGN_DATABASE_NAME;
import static com.speedment.runtime.config.ForeignKeyColumnUtil.FOREIGN_SCHEMA_NAME;
import static com.speedment.runtime.config.ForeignKeyColumnUtil.FOREIGN_TABLE_NAME;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

final class ForeignKeyColumnTest extends BaseConfigTest<ForeignKeyColumn> {

    @Override
    ForeignKeyColumn getDocumentInstance() {
        return ForeignKeyColumn.create(null, map());
    }

    @Test
    void getForeignDatabaseName() {
        final ForeignKeyColumn foreignKeyColumn = ForeignKeyColumn.create(null, map(entry(FOREIGN_DATABASE_NAME, "database")));
        assertNotNull(foreignKeyColumn.getForeignDatabaseName());

        assertThrows(NoSuchElementException.class, getDocumentInstance()::getForeignDatabaseName);
    }

    @Test
    void getForeignSchemaName() {
        final ForeignKeyColumn foreignKeyColumn = ForeignKeyColumn.create(null, map(entry(FOREIGN_SCHEMA_NAME, "schema")));
        assertNotNull(foreignKeyColumn.getForeignSchemaName());

        assertThrows(NoSuchElementException.class, getDocumentInstance()::getForeignSchemaName);
    }

    @Test
    void getForeignTableName() {
        final ForeignKeyColumn foreignKeyColumn = ForeignKeyColumn.create(null, map(entry(FOREIGN_TABLE_NAME, "table")));
        assertNotNull(foreignKeyColumn.getForeignTableName());

        assertThrows(NoSuchElementException.class, getDocumentInstance()::getForeignTableName);
    }

    @Test
    void getForeignColumnName() {
        final ForeignKeyColumn foreignKeyColumn = ForeignKeyColumn.create(null, map(entry(FOREIGN_COLUMN_NAME, "column")));
        assertNotNull(foreignKeyColumn.getForeignColumnName());

        assertThrows(NoSuchElementException.class, getDocumentInstance()::getForeignColumnName);
    }

    @Test
    void findForeignTable() {
        assertFalse(getDocumentInstance().findForeignTable().isPresent());
    }

    @Test
    void findForeignColumn() {
        assertFalse(getDocumentInstance().findForeignColumn().isPresent());
    }

}
