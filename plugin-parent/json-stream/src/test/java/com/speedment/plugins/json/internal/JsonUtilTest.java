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
package com.speedment.plugins.json.internal;

import com.speedment.plugins.json.TestUtil;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.identifier.ColumnIdentifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class JsonUtilTest {

    private static final String COLUMN_NAME = "bigint_unsigned";

    @Test
    void jsonField() {
        final Project project = TestUtil.project();
        final ColumnIdentifier<?> columnIdentifier = ColumnIdentifier.of("speedment_test","speedment_test", "unsigned_test", COLUMN_NAME);
        final String jsonFieldName = JsonUtil.jsonField(project, columnIdentifier);
        assertEquals(COLUMN_NAME, jsonFieldName);
    }
}