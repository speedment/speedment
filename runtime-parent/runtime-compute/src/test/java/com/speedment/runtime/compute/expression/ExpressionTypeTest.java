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

import static com.speedment.runtime.compute.expression.ExpressionType.*;
import static com.speedment.runtime.compute.expression.ExpressionType.BYTE_NULLABLE;
import static com.speedment.runtime.compute.expression.ExpressionType.SHORT_NULLABLE;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ExpressionTypeTest {

    @Test
    void isNullable() {
        assertFalse(BYTE.isNullable());
        assertFalse(SHORT.isNullable());
        assertFalse(INT.isNullable());
        assertFalse(LONG.isNullable());
        assertFalse(FLOAT.isNullable());
        assertFalse(DOUBLE.isNullable());
        assertFalse(CHAR.isNullable());
        assertFalse(BOOLEAN.isNullable());
        assertFalse(ENUM.isNullable());
        assertFalse(STRING.isNullable());
        assertFalse(BIG_DECIMAL.isNullable());

        assertTrue(BYTE_NULLABLE.isNullable());
        assertTrue(SHORT_NULLABLE.isNullable());
        assertTrue(INT_NULLABLE.isNullable());
        assertTrue(LONG_NULLABLE.isNullable());
        assertTrue(FLOAT_NULLABLE.isNullable());
        assertTrue(DOUBLE_NULLABLE.isNullable());
        assertTrue(CHAR_NULLABLE.isNullable());
        assertTrue(BOOLEAN_NULLABLE.isNullable());
        assertTrue(ENUM_NULLABLE.isNullable());
        assertTrue(STRING_NULLABLE.isNullable());
        assertTrue(BIG_DECIMAL_NULLABLE.isNullable());
    }
}
