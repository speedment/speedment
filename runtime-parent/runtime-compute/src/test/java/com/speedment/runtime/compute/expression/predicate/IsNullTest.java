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
package com.speedment.runtime.compute.expression.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.speedment.runtime.compute.ToIntNullable;
import com.speedment.runtime.compute.expression.Expressions;
import com.speedment.runtime.compute.trait.ToNullable;
import org.junit.jupiter.api.Test;

final class IsNullTest {

    private final IsNull<Integer, Integer> instance = new DummyIsNull();

    @Test
    void nullPredicateType() {
        assertEquals(NullPredicateType.IS_NULL, instance.nullPredicateType());
    }

    @Test
    void test() {
        assertTrue(instance.test(null));
        assertFalse(instance.test(1));
    }

    private static final class DummyIsNotNull implements IsNotNull<Integer, Integer> {

        @Override
        public IsNull<Integer, Integer> negate() {
            return new DummyIsNull();
        }

        @Override
        public ToNullable<Integer, Integer, ?> expression() {
            return Expressions.absOrNull(Integer::intValue);
        }
    }

    private static final class DummyIsNull implements IsNull<Integer, Integer> {

        @Override
        public IsNotNull<Integer, Integer> negate() {
            return new DummyIsNotNull();
        }

        @Override
        public ToNullable<Integer, Integer, ?> expression() {
            return Expressions.absOrNull(ToIntNullable.of(integer -> integer));
        }
    }

}
