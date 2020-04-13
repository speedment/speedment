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
package com.speedment.runtime.compute.expression;

/**
 * Every expression type has a corresponding interface to get a type-safe way of
 * applying the expression without boxing any values. This enumeration can be
 * used to tell which type an expression has.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public enum ExpressionType {

    BYTE, BYTE_NULLABLE(true),
    SHORT, SHORT_NULLABLE(true),
    INT, INT_NULLABLE(true),
    LONG, LONG_NULLABLE(true),
    FLOAT, FLOAT_NULLABLE(true),
    DOUBLE, DOUBLE_NULLABLE(true),
    CHAR, CHAR_NULLABLE(true),
    BOOLEAN, BOOLEAN_NULLABLE(true),
    ENUM, ENUM_NULLABLE(true),
    STRING, STRING_NULLABLE(true),
    BIG_DECIMAL, BIG_DECIMAL_NULLABLE(true);

    private final boolean isNullable;

    ExpressionType() {
        this(false);
    }

    ExpressionType(boolean isNullable) {
        this.isNullable = isNullable;
    }

    /**
     * Returns {@code true} if this type is one of the nullable types, and
     * otherwise {@code false}
     *
     * @return {@code true} if expression result may be {@code null}
     */
    public boolean isNullable() {
        return isNullable;
    }
}
