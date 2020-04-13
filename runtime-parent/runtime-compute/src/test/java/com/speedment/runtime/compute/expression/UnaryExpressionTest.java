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

import static com.speedment.runtime.compute.expression.UnaryExpression.Operator.ABS;
import static com.speedment.runtime.compute.expression.UnaryExpression.Operator.CAST;
import static com.speedment.runtime.compute.expression.UnaryExpression.Operator.NEGATE;
import static com.speedment.runtime.compute.expression.UnaryExpression.Operator.SIGN;
import static com.speedment.runtime.compute.expression.UnaryExpression.Operator.SQRT;

import com.speedment.runtime.compute.ToString;
import org.junit.jupiter.api.Test;

final class UnaryExpressionTest {

    @Test
    void operatorEnum() {
        new DummyUnaryExpression(ABS);
        new DummyUnaryExpression(CAST);
        new DummyUnaryExpression(NEGATE);
        new DummyUnaryExpression(SIGN);
        new DummyUnaryExpression(SQRT);
    }

    private static final class DummyUnaryExpression implements UnaryExpression<String, ToString<String>> {

        private final Operator operator;

        private DummyUnaryExpression(
                Operator operator) {
            this.operator = operator;
        }

        @Override
        public ToString<String> inner() {
            return null;
        }

        @Override
        public Operator operator() {
            return operator;
        }

        @Override
        public ExpressionType expressionType() {
            return ExpressionType.STRING;
        }
    }

}
