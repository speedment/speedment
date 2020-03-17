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
package com.speedment.runtime.compute.internal.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.speedment.runtime.compute.ToBoolean;
import com.speedment.runtime.compute.ToInt;
import com.speedment.runtime.compute.expression.ExpressionType;
import com.speedment.runtime.compute.expression.UnaryExpression.Operator;
import com.speedment.runtime.compute.internal.expression.NegateUtil.AbstractNegate;
import org.junit.jupiter.api.Test;

final class NegateUtilTest {

    @Test
    void negateBoolean() {
        final ToBoolean<Boolean> toBoolean = NegateUtil.negateBoolean(b -> b);

        assertFalse(toBoolean.applyAsBoolean(true));
        assertTrue(toBoolean.applyAsBoolean(false));
    }

    @Test
    void abstractNegate() {
        final DummyNegate instance = new DummyNegate(i -> i);

        assertNotNull(instance.inner());
        assertEquals(Operator.NEGATE, instance.operator());
        assertNotEquals(0, instance.hashCode());

        final DummyNegate copy = instance;

        assertTrue(instance.equals(copy));
        assertFalse(instance.equals(null));

        final DummyNegate sameInner = new DummyNegate(instance.inner());

        assertTrue(instance.equals(sameInner));

        final DummyNegate differentInner = new DummyNegate(i -> i + 1);

        assertFalse(instance.equals(differentInner));
    }

    private static final class DummyNegate extends AbstractNegate<Integer, ToInt<Integer>> {

        DummyNegate(ToInt<Integer> integerToInt) {
            super(integerToInt);
        }

        @Override
        public ExpressionType expressionType() {
            return null;
        }
    }

}
