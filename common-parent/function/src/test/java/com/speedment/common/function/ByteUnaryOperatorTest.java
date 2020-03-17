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
package com.speedment.common.function;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class ByteUnaryOperatorTest {

    private static final ByteUnaryOperator PLUS_ONE = b -> (byte) (b + (byte) 1);
    private static final ByteUnaryOperator TIMES_TWO = b -> (byte) (b * (byte) 2);

    @Test
    void applyAsByte() {
        assertEquals(1, PLUS_ONE.applyAsByte((byte) 0));
    }

    @Test
    void compose() {
        assertEquals(2, TIMES_TWO.compose(PLUS_ONE).applyAsByte((byte) 0));
    }

    @Test
    void andThen() {
        assertEquals(2, PLUS_ONE.andThen(TIMES_TWO).applyAsByte((byte) 0));
    }
}