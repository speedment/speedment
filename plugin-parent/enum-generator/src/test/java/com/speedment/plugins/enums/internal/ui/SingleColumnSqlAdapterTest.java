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

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.db.SqlFunction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
final class SingleColumnSqlAdapterTest {

    @Mock
    private ResultSet resultSet;

    private static final String DB = "db";
    private static final String SCHEMA = "schema";
    private static final String TABLE = "table";
    private static final String COLUMN = "column";

    private static final SingleColumnSqlAdapter INSTANCE = new SingleColumnSqlAdapter(DB, SCHEMA, TABLE, COLUMN);

    @Test
    void identifier() {
        final TableIdentifier<?> expected = TableIdentifier.of(DB, SCHEMA, TABLE);
        assertSame(expected, INSTANCE.identifier());
    }

    @Test
    void entityMapper() throws SQLException {
        final String expected = "A";
        when(resultSet.getString(COLUMN)).thenReturn(expected);
        final SqlFunction<ResultSet, String> mapper = INSTANCE.entityMapper();
        final String actual = mapper.apply(resultSet);
        assertEquals(expected, actual);
    }

    @Test
    void testEntityMapper() throws SQLException {
        final String expected = "A";
        when(resultSet.getString(COLUMN)).thenReturn(expected);
        final SqlFunction<ResultSet, String> mapper = INSTANCE.entityMapper(7);
        final String actual = mapper.apply(resultSet);
        assertEquals(expected, actual);
    }
}