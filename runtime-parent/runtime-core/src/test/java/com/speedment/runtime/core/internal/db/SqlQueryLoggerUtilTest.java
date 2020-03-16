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

package com.speedment.runtime.core.internal.db;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.speedment.common.logger.Level;
import com.speedment.common.logger.Logger;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

final class SqlQueryLoggerUtilTest {

    @Test
    void logOperation() {
        final Logger logger = mock(Logger.class);
        when(logger.getLevel()).thenReturn(Level.DEBUG);
        doNothing().when(logger).debug(anyString());

        final String sql = "SELECT * FROM table WHERE id > ? AND age < ?";

        final List<Integer> values = new ArrayList<>();
        values.add(1);
        values.add(null);

        assertDoesNotThrow(() -> SqlQueryLoggerUtil.logOperation(logger, sql, values));

        when(logger.getLevel()).thenReturn(Level.INFO);
        assertDoesNotThrow(() -> SqlQueryLoggerUtil.logOperation(logger, sql, values));
    }

}
