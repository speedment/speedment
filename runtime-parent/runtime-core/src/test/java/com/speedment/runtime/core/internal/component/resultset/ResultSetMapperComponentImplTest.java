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
package com.speedment.runtime.core.internal.component.resultset;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.test_support.MockDbmsType;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

final class ResultSetMapperComponentImplTest {

    private final ResultSetMapperComponentImpl instance = new ResultSetMapperComponentImpl();
    private final DbmsType dbmsType = new MockDbmsType();
    private final JavaTypeMappingImpl<String> resultSetMapping = new JavaTypeMappingImpl<>(
            String.class,
            "stringResultSet",
            Function.identity(),
            Long::toString
    );

    @Test
    void put() {
        assertThrows(NullPointerException.class, () -> instance.put(null));
        assertThrows(NullPointerException.class, () -> instance.put(null, null));

        assertDoesNotThrow(() -> instance.put(dbmsType, resultSetMapping));
    }

    @Test
    void apply() {
        assertThrows(NullPointerException.class, () -> instance.apply(null));

        assertDoesNotThrow(() -> instance.apply(String.class));
        assertDoesNotThrow(() -> instance.apply(dbmsType, String.class));
    }
}
