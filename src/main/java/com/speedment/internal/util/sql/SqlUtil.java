/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.util.sql;

import static com.speedment.internal.util.StaticClassUtil.instanceNotAllowed;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 */
public final class SqlUtil {

    public static String sqlParseValue(final String value) {
        // value nullable
        if (value == null) {
            return "NULL";
        }
        return "'" + value.replaceAll("'", "''") + "'";
    }

    public static String unQuote(final String s) {
        requireNonNull(s);
        if (s.startsWith("\"") && s.endsWith("\"")) {
            // Un-quote the name
            return s.substring(1, s.length() - 1);
        }
        return s;
    }

    /**
     * Utility classes should not be instantiated.
     */
    private SqlUtil() { instanceNotAllowed(getClass()); }
}