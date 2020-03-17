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
package com.speedment.common.json;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.util.concurrent.atomic.AtomicLong;

final class JsonSyntaxExceptionTest {

    @Test
    void constructor() {
        assertDoesNotThrow((ThrowingSupplier<JsonSyntaxException>) JsonSyntaxException::new);
        assertDoesNotThrow(() -> new JsonSyntaxException("message"));

        final AtomicLong row = new AtomicLong(1);
        final AtomicLong col = new AtomicLong(1);
        final Throwable throwable = new RuntimeException();

        assertDoesNotThrow(() -> new JsonSyntaxException(row, col));
        assertDoesNotThrow(() -> new JsonSyntaxException(row, col, throwable));
        assertDoesNotThrow(() -> new JsonSyntaxException(row, col, "message"));
        assertDoesNotThrow(() -> new JsonSyntaxException(row, col, "message", throwable));
    }

    @Test
    void getMessage() {
        assertNotNull(new JsonSyntaxException().getMessage());
    }

}
