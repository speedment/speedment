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
package com.speedment.runtime.bulk.internal;

import static org.junit.jupiter.api.Assertions.*;

import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.test_support.MockStringManager;
import org.junit.jupiter.api.Test;

final class BulkOperationBuilderTest {

    private final BulkOperationBuilder builder = new BulkOperationBuilder();

    private final Manager<String> manager = new MockStringManager();

    @Test
    void persist() {
        assertNotNull(builder.persist(manager));
    }

    @Test
    void update() {
        assertNotNull(builder.update(manager));
    }

    @Test
    void remove() {
        assertNotNull(builder.remove(manager));
    }

}
