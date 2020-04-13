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

package com.speedment.runtime.core.db.metadata;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

final class TypeInfoMetaDataTest {

    @Test
    void javaSqlTypeName() throws SQLException {
        final TypeInfoMetaData typeInfoMetaData = TypeInfoMetaData.of(mockResultSet());

        assertNotNull(typeInfoMetaData.javaSqlTypeName());
    }

    private static ResultSet mockResultSet() {
        final ResultSet resultSet = mock(ResultSet.class);

        try {
            when(resultSet.getString("TYPE_NAME")).thenReturn("type");
            when(resultSet.getInt("DATA_TYPE")).thenReturn(1);
            when(resultSet.getInt("PRECISION")).thenReturn(1);
            when(resultSet.getShort("NULLABLE")).thenReturn((short) 0);
            when(resultSet.getBoolean("UNSIGNED_ATTRIBUTE")).thenReturn(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }
}
