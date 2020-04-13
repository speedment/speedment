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

import com.speedment.common.logger.Level;
import com.speedment.common.logger.Logger;

import java.util.List;
import java.util.stream.Collectors;

public final class SqlQueryLoggerUtil {

    private SqlQueryLoggerUtil() {}

    public static void logOperation(Logger logger, final String sql, final List<?> values) {
        if (logger.getLevel().isEqualOrLowerThan(Level.DEBUG)) {
            final String text = sql + " " + values.stream()
                    .map(o -> o == null
                            ? "null"
                            : o.getClass().getSimpleName() + " " + o.toString())
                    .collect(Collectors.joining(", ", "[", "]"));
            logger.debug(text);
        }
    }
}
