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
package com.speedment.runtime.core.internal.db;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class DbmsTypeDefaultImplTest {

    private final DbmsTypeDefaultImpl instance = new DbmsTypeDefaultImpl();

    @Test
    void getResultSetTableSchema() {
        assertNotNull(instance.getResultSetTableSchema());
    }

    @Test
    void getSchemaTableDelimiter() {
        assertNotNull(instance.getSchemaTableDelimiter());
    }

    @Test
    void getInitialQuery() {
        assertNotNull(instance.getInitialQuery());
    }

    @Test
    void getDataTypes() {
        assertNotNull(instance.getDataTypes());
    }

    @Test
    void getDefaultDbmsName() {
        assertNotNull(instance.getDefaultDbmsName());
    }

    @Test
    void getColumnHandler() {
        assertNotNull(instance.getColumnHandler());
    }

    @Test
    void getSkipLimitSupport() {
        assertNotNull(instance.getSkipLimitSupport());
    }

    @Test
    void getSubSelectAlias() {
        assertNotNull(instance.getSubSelectAlias());
    }

    @Test
    void getSortByNullOrderInsertion() {
        assertNotNull(instance.getSortByNullOrderInsertion());
    }

    @Test
    void applySkipLimit() {
        assertThrows(IllegalArgumentException.class, () -> instance.applySkipLimit("",
                Collections.emptyList(), -1, -1));
        assertThrows(IllegalArgumentException.class, () -> instance.applySkipLimit("",
                Collections.emptyList(), 1, -1));

        final String sql = "SELECT * FROM table";
        final List<Object> params = new ArrayList<>();

        assertDoesNotThrow(() -> instance.applySkipLimit(sql, params, 1, 1));

        assertEquals(sql, instance.applySkipLimit(sql, params, 0, Long.MAX_VALUE));
    }

}
