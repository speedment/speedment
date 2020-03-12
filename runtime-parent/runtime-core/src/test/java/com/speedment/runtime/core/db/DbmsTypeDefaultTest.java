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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class DbmsTypeDefaultTest {

    private static final DbmsTypeDefault DBMS_TYPE_DEFAULT = DbmsTypeDefault.create();

    @Test
    void getDefaultSchema() {
        assertNotNull(DBMS_TYPE_DEFAULT.getDefaultSchemaName());
    }

    @Test
    void hasSchemaNames() {
        assertTrue(DBMS_TYPE_DEFAULT.hasSchemaNames());
    }

    @Test
    void hasDatabaseNames() {
        assertTrue(DBMS_TYPE_DEFAULT.hasDatabaseNames());
    }

    @Test
    void hasDatabaseUsers() {
        assertTrue(DBMS_TYPE_DEFAULT.hasDatabaseUsers());
    }

    @Test
    void hasServerNames() {
        assertFalse(DBMS_TYPE_DEFAULT.hasServerNames());
    }

    @Test
    void getConnectionType() {
        assertNotNull(DBMS_TYPE_DEFAULT.getConnectionType());
    }

    @Test
    void getDefaultServerName() {
        assertNotNull(DBMS_TYPE_DEFAULT.getDefaultServerName());
    }

    @Test
    void getCollateFragment() {
        assertNotNull(DBMS_TYPE_DEFAULT.getCollateFragment());
    }
}
