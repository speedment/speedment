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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PasswordComponentImplTest {

    private final PasswordComponentImpl instance = new PasswordComponentImpl();

    @Test
    void put() {
        assertThrows(NullPointerException.class, () -> instance.put((String) null, null));
    }

    @Test
    void get() {
        assertThrows(NullPointerException.class, () -> instance.get((String) null));
        assertFalse(instance.get("nonExistentDbms").isPresent());

        instance.put("dbms", "password".toCharArray());
        assertTrue(instance.get("dbms").isPresent());

        instance.put("dbms", null);
        assertFalse(instance.get("dbms").isPresent());
    }
}
