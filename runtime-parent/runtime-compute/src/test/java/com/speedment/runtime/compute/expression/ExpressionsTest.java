/*
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
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

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

final class ExpressionsTest {

    @Test
    void byteToDouble() {
        assertNotNull(Expressions.byteToDouble(b -> (byte) b));
    }

    @Test
    void shortToDouble() {
        assertNotNull(Expressions.shortToDouble(b -> (short) b));
    }

    @Test
    void intToDouble() {
        assertNotNull(Expressions.intToDouble(b -> (int) b));
    }

    @Test
    void longToDouble() {
        assertNotNull(Expressions.longToDouble(b -> (long) b));
    }

    @Test
    void floatToDouble() {
        assertNotNull(Expressions.floatToDouble(b -> (float) b));
    }

    @Test
    void byteToDoubleNullable() {
        assertNotNull(Expressions.byteToDoubleNullable(b -> (byte) b));
    }

    @Test
    void shortToDoubleNullable() {
        assertNotNull(Expressions.shortToDoubleNullable(b -> (short) b));
    }

    @Test
    void intToDoubleNullable() {
        assertNotNull(Expressions.intToDoubleNullable(b -> (int) b));
    }

    @Test
    void longToDoubleNullable() {
        assertNotNull(Expressions.longToDoubleNullable(b -> (long) b));
    }

    @Test
    void floatToDoubleNullable() {
        assertNotNull(Expressions.floatToDoubleNullable(b -> (float) b));
    }
}
