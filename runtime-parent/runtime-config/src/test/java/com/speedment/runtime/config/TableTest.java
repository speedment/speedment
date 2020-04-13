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

import static com.speedment.runtime.config.TableUtil.COLUMNS;
import static com.speedment.runtime.config.TableUtil.FOREIGN_KEYS;
import static com.speedment.runtime.config.TableUtil.INDEXES;
import static com.speedment.runtime.config.TableUtil.IS_VIEW;
import static com.speedment.runtime.config.TableUtil.PRIMARY_KEY_COLUMNS;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.speedment.runtime.config.trait.HasAliasUtil;
import com.speedment.runtime.config.trait.HasEnableUtil;
import com.speedment.runtime.config.trait.HasIdUtil;
import com.speedment.runtime.config.trait.HasNameUtil;
import com.speedment.runtime.config.trait.HasTypeMapperUtil;
import org.junit.jupiter.api.Test;

final class TableTest extends BaseConfigTest<Table> {

    @Override
    Table getDocumentInstance() {
        return Table.create(null, map());
    }

    @Test
    void isView() {
        final Table table = Table.create(null, map(entry(IS_VIEW, true)));
        assertTrue(table.isView());

        assertFalse(getDocumentInstance()::isView);
    }

    @Test
    void findColumn() {
        final Table table = Table.create(null, map(entry(COLUMNS,
            map(
                entry(HasIdUtil.ID, "id"),
                entry(HasNameUtil.NAME, "column"),
                entry(HasAliasUtil.ALIAS, "column_alias"),
                entry(HasEnableUtil.ENABLED, true),
                entry(HasTypeMapperUtil.DATABASE_TYPE, String.class.getName())
            )
        )));

        assertTrue(table.findColumn("id").isPresent());
    }

    @Test
    void findIndex() {
        final Table table = Table.create(null, map(entry(INDEXES,
            map(
                entry(HasIdUtil.ID, "id"),
                entry(HasNameUtil.NAME, "index"),
                entry(HasEnableUtil.ENABLED, true),
                entry(IndexUtil.INDEX_COLUMNS, map(
                    entry(HasNameUtil.NAME, "column")
                ))
            )
        )));

        assertTrue(table.findIndex("id").isPresent());
    }

    @Test
    void findForeignKey() {
        final Table table = Table.create(null, map(entry(FOREIGN_KEYS,
            map(
                entry(HasIdUtil.ID, "id"),
                entry(HasNameUtil.NAME, "foreign_key"),
                entry(HasEnableUtil.ENABLED, true),
                entry(ForeignKeyUtil.FOREIGN_KEY_COLUMNS, map(
                    entry(HasNameUtil.NAME, "column"),
                    entry(ForeignKeyColumnUtil.FOREIGN_TABLE_NAME, "fk_table"),
                    entry(ForeignKeyColumnUtil.FOREIGN_COLUMN_NAME, "fk_column")
                ))
            )
        )));

        assertTrue(table.findForeignKey("id").isPresent());
    }

    @Test
    void findPrimaryKeyColumn() {
        final Table table = Table.create(null, map(entry(PRIMARY_KEY_COLUMNS,
            map(
                entry(HasIdUtil.ID, "id"),
                entry(HasNameUtil.NAME, "pk_column")
            )
        )));

        assertTrue(table.findPrimaryKeyColumn("id").isPresent());
    }

}
