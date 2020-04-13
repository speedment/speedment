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
package com.speedment.runtime.compute.expression.orelse;

import static com.speedment.runtime.compute.expression.NonNullableExpression.NullStrategy.USE_DEFAULT_VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.speedment.runtime.compute.ToShortNullable;
import org.junit.jupiter.api.Test;

final class ToShortOrElseTest {

    @Test
    void nullStrategy() {
        assertEquals(USE_DEFAULT_VALUE, new DummyToShortOrElseTest().nullStrategy());
    }

    private static final class DummyToShortOrElseTest implements ToShortOrElse<Integer> {

        @Override
        public short defaultValue() {
            return 0;
        }

        @Override
        public short applyAsShort(Integer object) {
            return 0;
        }

        @Override
        public ToShortNullable<Integer> innerNullable() {
            return null;
        }
    }
}
