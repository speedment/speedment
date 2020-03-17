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
package com.speedment.runtime.core.internal.component;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.test_support.MockDbmsType;
import org.junit.jupiter.api.Test;

final class DbmsHandlerComponentImplTest {

    private final DbmsHandlerComponentImpl instance = new DbmsHandlerComponentImpl();

    @Test
    void install() {
        assertThrows(NullPointerException.class, () -> instance.install(null));

        DbmsType dbmsType = new MockDbmsType();
        instance.install(dbmsType);

        assertEquals(1, instance.supportedDbmsTypes().count());
        assertTrue(instance.findByName(dbmsType.getName()).isPresent());
    }
}
