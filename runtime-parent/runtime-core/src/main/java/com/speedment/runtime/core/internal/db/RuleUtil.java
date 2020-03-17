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

import com.speedment.runtime.core.db.JavaTypeMap;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Optional;

final class RuleUtil {

    private RuleUtil() {}

    /**
     * All default rules that need more than just the jdbc type name to trigger.
     * Overrides the inner defaults of JavaTypeMapImpl
     */
    static final JavaTypeMap.Rule DEFAULT_RULE = (sqlTypeMapping, md) -> {
        final String typeName   = md.getTypeName().toUpperCase();
        final int columnSize    = md.getColumnSize();


        switch (typeName) {
            case "NCHAR":
            case "NVARCHAR2": return Optional.of(String.class);
            case "BINARY_FLOAT": return Optional.of(Float.class);
            case "BINARY_DOUBLE": return Optional.of(Double.class);
            default: // just continue
        }

        if ("BIT".equals(typeName)) {
            if (columnSize > 31) {  // jdbc will return unsigned 32 bit for BIT(32) which does not fit in Integer
                return Optional.of(Long.class);
            }
            if (columnSize == 0 || columnSize == 1) {
                return Optional.of(Boolean.class);
            }
            // Otherwise continue
        }

        if (("NUMBER".equals(typeName) || "DECIMAL".equals(typeName)) && md.getDecimalDigits() == 0) {
            if (columnSize <= 2) {
                return Optional.of(Byte.class);
            } else if (columnSize <= 4) {
                return Optional.of(Short.class);
            } else if (columnSize <= 9) {
                return Optional.of(Integer.class);
            } else if (columnSize <= 18) {
                return Optional.of(Long.class);
            } else {
                return Optional.of(BigInteger.class);
            }
        }

        if (typeName.matches("TIMESTAMP\\([0-9]\\) WITH LOCAL TIME ZONE")) {
            return Optional.of(Timestamp.class);
        }
        if (typeName.matches("TIMESTAMP\\([0-9]\\) WITH TIME ZONE")) {
            return Optional.of(Timestamp.class);
        }
        if (typeName.matches("INTERVAL YEAR\\([0-9]\\) TO MONTH")) {
            return Optional.of(String.class);
        }
        if (typeName.matches("INTERVAL DAY\\([0-9]\\) TO SECOND\\([0-9]\\)")) {
            return Optional.of(String.class);
        }

        return Optional.empty();
    };
}
