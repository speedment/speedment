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
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author Per Minborg
 */
final class LoggerTest {

    private static final Logger LOGGER = LoggerManager.getLogger(LoggerTest.class);

    private static final String MESSAGE_1 = "We trust reason";
    private static final String MESSAGE_2 = "We are superstitious";

    @Test
    void test() {
        LOGGER.setLevel(Level.TRACE);
        assertEquals(Level.TRACE, LOGGER.getLevel());

        LOGGER.trace(MESSAGE_1);
        LOGGER.debug(MESSAGE_1);
        LOGGER.info(MESSAGE_1);
        LOGGER.warn(MESSAGE_2);
        LOGGER.error(MESSAGE_2);
        LOGGER.fatal(MESSAGE_2);
    }

}
