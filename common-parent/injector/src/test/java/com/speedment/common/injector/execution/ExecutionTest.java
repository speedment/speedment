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
package com.speedment.common.injector.execution;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class ExecutionTest {

    @Test
    void applyOrNullException() {
        final Execution.ClassMapper classMapper = new Execution.ClassMapper() {
            @Override
            public <T> T apply(Class<T> type) {
                throw new RuntimeException();
            }
        };
        assertNull(classMapper.applyOrNull(Integer.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    void applyOrNull() {
        final int expected = 1;
        final Execution.ClassMapper classMapper = new Execution.ClassMapper() {
            @Override
            public <T> T apply(Class<T> type) {
                return (T) (Integer) expected;
            }
        };
        assertEquals(expected , classMapper.applyOrNull(Integer.class));
    }

}