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
package com.speedment.internal.core.manager.sql.generator;

import com.speedment.internal.codegen.base.Generator;
import com.speedment.internal.codegen.base.Transform;
import com.speedment.field.operators.StandardComparableOperator;
import java.util.Optional;
import com.speedment.field.builders.ComparablePredicateBuilder;
import com.speedment.field.operators.ComparableOperator;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
@SuppressWarnings("rawtypes")
public final class ComparablePredicateBuilderView implements Transform<ComparablePredicateBuilder, String> {

    protected String render(StandardComparableOperator op) {
        requireNonNull(op);
        switch (op) {
            case EQUAL:
                return " = ";
            case GREATER_OR_EQUAL:
                return " >= ";
            case GREATER_THAN:
                return " > ";
            case LESS_OR_EQUAL:
                return " <= ";
            case LESS_THAN:
                return " < ";
            case NOT_EQUAL:
                return " <> ";
            default:
                throw new UnsupportedOperationException(
                    "Unknown enum constant " + op.name() + "."
                );
        }
    }

    @Override
    public Optional<String> transform(Generator gen, ComparablePredicateBuilder model) {
        requireNonNull(gen);
        requireNonNull(model);
        final ComparableOperator co = model.getComparableOperator();
        if (co instanceof StandardComparableOperator) {

            @SuppressWarnings("unchecked")
            final StandardComparableOperator sco = (StandardComparableOperator) co;

            return Optional.of("("
                + model.getField().getColumnName()
                + render(sco)
                + "?)"
            );
        } else {
            return Optional.empty();
        }
    }
}
