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
package com.speedment.util.java.sql;

import com.speedment.util.PureStaticMethods;

/**
 *
 * @author pemi
 */
public class SqlUtil implements PureStaticMethods {

    /**
     * This class contains only static methods and thus, no instance shall be
     * created.
     *
     * @see PureStaticMethods#instanceNotAllowed()
     */
    public SqlUtil() {
        instanceNotAllowed();
    }

    public static String sqlParseValue(final String value) {
        if (value == null) {
            return "NULL";
        }
        return "'" + value.replaceAll("'", "''") + "'";
    }

    public static String unQuote(final String s) {
        if (s.startsWith("\"") && s.endsWith("\"")) {
            // Un-quote the name
            return s.substring(1, s.length() - 1);
        }
        return s;
    }

}
