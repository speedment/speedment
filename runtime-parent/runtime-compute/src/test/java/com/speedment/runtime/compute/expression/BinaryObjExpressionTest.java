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

import static com.speedment.runtime.compute.expression.BinaryObjExpression.Operator.DIVIDE;
import static com.speedment.runtime.compute.expression.BinaryObjExpression.Operator.MINUS;
import static com.speedment.runtime.compute.expression.BinaryObjExpression.Operator.MULTIPLY;
import static com.speedment.runtime.compute.expression.BinaryObjExpression.Operator.PLUS;
import static com.speedment.runtime.compute.expression.BinaryObjExpression.Operator.POW;
import static org.junit.jupiter.api.Assertions.*;

import com.speedment.runtime.compute.ToString;
import org.junit.jupiter.api.Test;

final class BinaryObjExpressionTest {

    @Test
    void operatorEnum() {
        new DummyBinaryObjExpression(POW);
        new DummyBinaryObjExpression(PLUS);
        new DummyBinaryObjExpression(MINUS);
        new DummyBinaryObjExpression(MULTIPLY);
        new DummyBinaryObjExpression(DIVIDE);
    }

    private static final class DummyBinaryObjExpression implements BinaryObjExpression<String, ToString<String>, ToString<String>> {

        private final Operator operator;

        private DummyBinaryObjExpression(
                Operator operator) {
            this.operator = operator;
        }

        @Override
        public ToString<String> first() {
            return null;
        }

        @Override
        public ToString<String> second() {
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
