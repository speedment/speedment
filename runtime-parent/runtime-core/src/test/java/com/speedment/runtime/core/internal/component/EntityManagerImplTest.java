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
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.speedment.runtime.core.component.ManagerComponent;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.test_support.MockStringManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class EntityManagerImplTest {

    private final EntityManagerImpl instance = new EntityManagerImpl();

    @BeforeAll
    void setup() {
        final ManagerComponent managerComponent = new ManagerComponentImpl();
        final Manager<String> manager = new MockStringManager();
        managerComponent.put(manager);

        instance.installManagers(managerComponent);
    }

    @Test
    void persist() {
        assertThrows(NullPointerException.class, () -> instance.persist(null));
        assertThrows(IllegalStateException.class, () -> instance.persist(1));
        assertDoesNotThrow(() -> instance.persist("string"));
    }

    @Test
    void update() {
        assertThrows(NullPointerException.class, () -> instance.update(null));
        assertThrows(IllegalStateException.class, () -> instance.update(1));
        assertDoesNotThrow(() -> instance.update("string"));
    }

    @Test
    void remove() {
        assertThrows(NullPointerException.class, () -> instance.remove(null));
        assertThrows(IllegalStateException.class, () -> instance.remove(1));
        assertDoesNotThrow(() -> instance.remove("string"));
    }
}
