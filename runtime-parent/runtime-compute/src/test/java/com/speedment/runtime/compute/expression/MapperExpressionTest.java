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

import static com.speedment.runtime.compute.expression.MapperExpression.MapperType.BIG_DECIMAL_TO_BIG_DECIMAL;
import static com.speedment.runtime.compute.expression.MapperExpression.MapperType.BIG_DECIMAL_TO_DOUBLE;
import static com.speedment.runtime.compute.expression.MapperExpression.MapperType.BOOLEAN_TO_BOOLEAN;
import static com.speedment.runtime.compute.expression.MapperExpression.MapperType.BOOLEAN_TO_DOUBLE;
import static com.speedment.runtime.compute.expression.MapperExpression.MapperType.BYTE_TO_BYTE;
import static com.speedment.runtime.compute.expression.MapperExpression.MapperType.BYTE_TO_DOUBLE;
import static com.speedment.runtime.compute.expression.MapperExpression.MapperType.CHAR_TO_CHAR;
import static com.speedment.runtime.compute.expression.MapperExpression.MapperType.DOUBLE_TO_DOUBLE;
import static com.speedment.runtime.compute.expression.MapperExpression.MapperType.ENUM_TO_ENUM;
import static com.speedment.runtime.compute.expression.MapperExpression.MapperType.FLOAT_TO_DOUBLE;
import static com.speedment.runtime.compute.expression.MapperExpression.MapperType.FLOAT_TO_FLOAT;
import static com.speedment.runtime.compute.expression.MapperExpression.MapperType.INT_TO_DOUBLE;
import static com.speedment.runtime.compute.expression.MapperExpression.MapperType.INT_TO_INT;
import static com.speedment.runtime.compute.expression.MapperExpression.MapperType.LONG_TO_DOUBLE;
import static com.speedment.runtime.compute.expression.MapperExpression.MapperType.LONG_TO_LONG;
import static com.speedment.runtime.compute.expression.MapperExpression.MapperType.SHORT_TO_DOUBLE;
import static com.speedment.runtime.compute.expression.MapperExpression.MapperType.SHORT_TO_SHORT;
import static com.speedment.runtime.compute.expression.MapperExpression.MapperType.STRING_TO_STRING;

import com.speedment.runtime.compute.ToString;
import org.junit.jupiter.api.Test;

final class MapperExpressionTest {

    @Test
    void mapperTypeEnum() {
        new DummyMapperExpression(BOOLEAN_TO_BOOLEAN);
        new DummyMapperExpression(BOOLEAN_TO_DOUBLE);
        new DummyMapperExpression(CHAR_TO_CHAR);
        new DummyMapperExpression(BYTE_TO_BYTE);
        new DummyMapperExpression(BYTE_TO_DOUBLE);
        new DummyMapperExpression(SHORT_TO_SHORT);
        new DummyMapperExpression(SHORT_TO_DOUBLE);
        new DummyMapperExpression(INT_TO_INT);
        new DummyMapperExpression(INT_TO_DOUBLE);
        new DummyMapperExpression(LONG_TO_LONG);
        new DummyMapperExpression(LONG_TO_DOUBLE);
        new DummyMapperExpression(FLOAT_TO_FLOAT);
        new DummyMapperExpression(FLOAT_TO_DOUBLE);
        new DummyMapperExpression(DOUBLE_TO_DOUBLE);
        new DummyMapperExpression(ENUM_TO_ENUM);
        new DummyMapperExpression(STRING_TO_STRING);
        new DummyMapperExpression(BIG_DECIMAL_TO_DOUBLE);
        new DummyMapperExpression(BIG_DECIMAL_TO_BIG_DECIMAL);
    }

    private static final class DummyMapperExpression implements MapperExpression<String, ToString<String>, String> {

        private final MapperType mapperType;

        private DummyMapperExpression(
                MapperType mapperType) {
            this.mapperType = mapperType;
        }

        @Override
        public ToString<String> inner() {
            return null;
        }

        @Override
        public String mapper() {
            return null;
        }

        @Override
        public MapperType mapperType() {
            return mapperType;
        }

        @Override
        public ExpressionType expressionType() {
            return ExpressionType.SHORT;
        }
    }
}
