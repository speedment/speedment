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

class FloatUnaryOperatorTest {

    private static final FloatUnaryOperator PLUS_ONE = f -> f +  1.0f;
    private static final FloatUnaryOperator TIMES_TWO = f -> f * 2.0f;


    @Test
    void applyAsFloat() {
        assertEquals(1, PLUS_ONE.applyAsFloat(0f));
    }

    @Test
    void compose() {
        assertEquals(2, TIMES_TWO.compose(PLUS_ONE).applyAsFloat(0f));
    }

    @Test
    void andThen() {
        assertEquals(2, PLUS_ONE.andThen(TIMES_TWO).applyAsFloat(0f));
    }
}