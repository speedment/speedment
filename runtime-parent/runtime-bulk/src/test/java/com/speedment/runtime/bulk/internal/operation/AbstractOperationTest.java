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
package com.speedment.runtime.bulk.internal.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.speedment.runtime.bulk.Operation.Type;
import com.speedment.runtime.bulk.PersistOperation;
import com.speedment.runtime.bulk.RemoveOperation;
import com.speedment.runtime.bulk.UpdateOperation;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.test_support.MockStringManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
final class AbstractOperationTest {

    private Manager<String> manager = new MockStringManager();

    private final PersistOperation<String> persistOperation =
            new PersistOperationImpl<>(manager, new ArrayList<>());

    private final RemoveOperation<String> removeOperation =
            new RemoveOperationImpl<>(manager, new ArrayList<>());

    private final UpdateOperation<String> updateOperation =
            new UpdateOperationImpl<>(manager, new ArrayList<>(),
                    new ArrayList<>(), new ArrayList<>());

    @Test
    void manager() {
        assertNotNull(persistOperation.manager());
        assertNotNull(updateOperation.manager());
        assertNotNull(removeOperation.manager());
    }

    @Test
    void type() {
        assertEquals(Type.PERSIST, persistOperation.type());
        assertEquals(Type.UPDATE, updateOperation.type());
        assertEquals(Type.REMOVE, removeOperation.type());
    }

    @Test
    void generatorSuppliers() {
        assertNotNull(persistOperation.generatorSuppliers());
    }

    @Test
    void predicates() {
        assertNotNull(updateOperation.predicates());
        assertNotNull(removeOperation.predicates());
    }

    @Test
    void consumers() {
        assertNotNull(updateOperation.consumers());
    }

    @Test
    void mappers() {
        assertNotNull(updateOperation.mappers());
    }


}
