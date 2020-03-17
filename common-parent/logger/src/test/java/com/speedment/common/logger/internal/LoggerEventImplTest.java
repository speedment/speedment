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
package com.speedment.common.logger.internal;

import com.speedment.common.logger.Level;
import com.speedment.common.logger.LoggerEvent;
import org.junit.jupiter.api.Test;

import javax.print.attribute.standard.MediaSize;

import static org.junit.jupiter.api.Assertions.*;

final class LoggerEventImplTest {

    private static final Level LEVEL = Level.FATAL;
    private static final String NAME = "c.s.e.Foo";
    private static final String MESSAGE = "John";

    private final LoggerEvent loggerEvent = new LoggerEventImpl(LEVEL, NAME, MESSAGE);

    @Test
    void getLevel() {
        assertEquals(LEVEL, loggerEvent.getLevel());
    }

    @Test
    void getName() {
        assertEquals(NAME, loggerEvent.getName());
    }

    @Test
    void getMessage() {
        assertEquals(MESSAGE, loggerEvent.getMessage());
    }
}