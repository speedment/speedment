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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.test_support.MockStringManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class ManagerComponentImplTest {

    private ManagerComponentImpl instance;

    @BeforeEach
    void setup() {
        instance = new ManagerComponentImpl();
    }

    @Test
    void put() {
        assertThrows(NullPointerException.class, () -> instance.put(null));
    }

    @Test
    void managerOf() {
        assertThrows(NullPointerException.class, () -> instance.managerOf(null));
        assertThrows(SpeedmentException.class, () -> instance.managerOf(Integer.class));

        final Manager<String> manager = new MockStringManager();
        instance.put(manager);

        assertDoesNotThrow(() -> instance.managerOf(manager.getEntityClass()));
    }

    @Test
    void stream() {
        final Manager<String> manager = new MockStringManager();
        instance.put(manager);

        assertEquals(1, instance.stream().count());
    }
}
