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

import static com.speedment.runtime.compute.expression.NonNullableExpression.NullStrategy.APPLY_DEFAULT_METHOD;
import static com.speedment.runtime.compute.expression.NonNullableExpression.NullStrategy.THROW_EXCEPTION;
import static com.speedment.runtime.compute.expression.NonNullableExpression.NullStrategy.USE_DEFAULT_VALUE;

import com.speedment.runtime.compute.ToString;
import org.junit.jupiter.api.Test;

final class NonNullableExpressionTest {

    @Test
    void nullStrategyEnum() {
        new DummyNonNullableExpression(USE_DEFAULT_VALUE);
        new DummyNonNullableExpression(APPLY_DEFAULT_METHOD);
        new DummyNonNullableExpression(THROW_EXCEPTION);
    }

    private static final class DummyNonNullableExpression implements NonNullableExpression<String, ToString<String>> {

        private final NullStrategy nullStrategy;

        private DummyNonNullableExpression(
                NullStrategy nullStrategy) {
            this.nullStrategy = nullStrategy;
        }

        @Override
        public ToString<String> innerNullable() {
            return null;
        }

        @Override
        public NullStrategy nullStrategy() {
            return nullStrategy;
        }

        @Override
        public ExpressionType expressionType() {
            return ExpressionType.STRING;
        }
    }

}
