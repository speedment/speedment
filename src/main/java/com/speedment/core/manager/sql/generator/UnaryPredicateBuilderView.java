/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.core.manager.sql.generator;

import com.speedment.codegen.base.Generator;
import com.speedment.codegen.base.Transform;
import com.speedment.core.field.StandardUnaryOperator;
import static com.speedment.core.field.StandardUnaryOperator.IS_NOT_NULL;
import static com.speedment.core.field.StandardUnaryOperator.IS_NULL;
import com.speedment.core.field.UnaryPredicateBuilder;

import java.util.Optional;

/**
 *
 * @author Emil Forslund
 */
@SuppressWarnings("rawtypes")
public abstract class UnaryPredicateBuilderView implements Transform<UnaryPredicateBuilder, String> {

    private String render(StandardUnaryOperator op) {
        switch (op) {
            case IS_NOT_NULL:
                return " <> NULL";
            case IS_NULL:
                return " == NULL";
            default:
                throw new UnsupportedOperationException(
                    "Unknown enum constant " + op.name() + "."
                );
        }
    }

    @Override
    public Optional<String> transform(Generator gen, UnaryPredicateBuilder model) {
        return Optional.of("("
            + model.getField().getColumnName()
            + render(model.getOperator())
            + ")"
        );
    }
}
