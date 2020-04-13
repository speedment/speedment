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

package com.speedment.runtime.core.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.exception.SpeedmentException;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.stream.Stream;

final class DatabaseUtilTest {

    @Test
    void dbmsTypeOf() {
        final Dbms dbms = mock(Dbms.class);
        when(dbms.getTypeName()).thenReturn("dbms_type_name");

        final Dbms dbms2 = mock(Dbms.class);
        when(dbms2.getTypeName()).thenReturn("non_existent_dbms_type_name");

        final DbmsType dbmsType = mock(DbmsType.class);
        when(dbmsType.getName()).thenReturn("dbms_type_name");

        final DbmsHandlerComponent dbmsHandlerComponent = mock(DbmsHandlerComponent.class);
        when(dbmsHandlerComponent.findByName("dbms_type_name")).thenReturn(Optional.of(dbmsType));
        when(dbmsHandlerComponent.findByName("non_existent_dbms_type_name")).thenReturn(Optional.empty());
        when(dbmsHandlerComponent.supportedDbmsTypes()).thenReturn(Stream.of(dbmsType));

        assertNotNull(DatabaseUtil.dbmsTypeOf(dbmsHandlerComponent, dbms));
        assertThrows(SpeedmentException.class, () -> DatabaseUtil.dbmsTypeOf(dbmsHandlerComponent, dbms2));
    }

    @Test
    void findConnectionUrl() {
        final Dbms dbms = mock(Dbms.class);
        when(dbms.getTypeName()).thenReturn("dbms_type_name");
        when(dbms.getConnectionUrl()).thenReturn(Optional.of("connection.url"));

        final Dbms dbms2 = mock(Dbms.class);
        when(dbms2.getTypeName()).thenReturn("another_dbms_type_name");
        when(dbms2.getConnectionUrl()).thenReturn(Optional.empty());

        final DbmsType dbmsType = mock(DbmsType.class);
        when(dbmsType.getName()).thenReturn("dbms_type_name");

        final DbmsType dbmsType2 = mock(DbmsType.class, RETURNS_DEEP_STUBS);
        when(dbmsType2.getName()).thenReturn("another_dbms_type_name");
        when(dbmsType2.getConnectionUrlGenerator().from(dbms2)).thenReturn("another.connection.url");

        final DbmsHandlerComponent dbmsHandlerComponent = mock(DbmsHandlerComponent.class);
        when(dbmsHandlerComponent.findByName("dbms_type_name")).thenReturn(Optional.of(dbmsType));
        when(dbmsHandlerComponent.findByName("another_dbms_type_name")).thenReturn(Optional.of(dbmsType2));

        assertEquals("connection.url", DatabaseUtil.findConnectionUrl(dbmsHandlerComponent, dbms));
        assertEquals("another.connection.url", DatabaseUtil.findConnectionUrl(dbmsHandlerComponent, dbms2));
    }

    @Test
    void findDbmsType() {
        final Dbms dbms = mock(Dbms.class);
        when(dbms.getTypeName()).thenReturn("dbms_type_name");

        final Dbms dbms2 = mock(Dbms.class);
        when(dbms2.getTypeName()).thenReturn("non_existent_dbms_type_name");

        final DbmsType dbmsType = mock(DbmsType.class);
        when(dbmsType.getName()).thenReturn("dbms_type_name");

        final DbmsHandlerComponent dbmsHandlerComponent = mock(DbmsHandlerComponent.class);
        when(dbmsHandlerComponent.findByName("dbms_type_name")).thenReturn(Optional.of(dbmsType));
        when(dbmsHandlerComponent.findByName("non_existent_dbms_type_name")).thenReturn(Optional.empty());

        assertNotNull(DatabaseUtil.findDbmsType(dbmsHandlerComponent, dbms));
        assertThrows(SpeedmentException.class, () -> DatabaseUtil.findDbmsType(dbmsHandlerComponent, dbms2));
    }
}
