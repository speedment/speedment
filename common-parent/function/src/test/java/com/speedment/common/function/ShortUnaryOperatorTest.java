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

import static org.junit.jupiter.api.Assertions.assertEquals;

final class ShortUnaryOperatorTest {

    private static final ShortUnaryOperator PLUS_ONE = s -> (short) (s + (short) 1);
    private static final ShortUnaryOperator TIMES_TWO = s -> (short) (s * (short) 2);
    private static final Short ZERO = (short) 0;

    @Test
    void applyAsShort() {
        assertEquals(1, PLUS_ONE.applyAsShort(ZERO));
    }

    @Test
    void compose() {
        assertEquals(2, TIMES_TWO.compose(PLUS_ONE).applyAsShort(ZERO));
    }

    @Test
    void andThen() {
        assertEquals(2, PLUS_ONE.andThen(TIMES_TWO).applyAsShort(ZERO));
    }
}