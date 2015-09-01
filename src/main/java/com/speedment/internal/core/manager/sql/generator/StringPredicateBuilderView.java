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

import com.speedment.field.builders.StringPredicateBuilder;
import com.speedment.internal.codegen.base.Generator;
import com.speedment.internal.codegen.base.Transform;
import com.speedment.field.operators.StandardStringOperator;
import com.speedment.field.operators.StringOperator;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
@SuppressWarnings("rawtypes")
public final class StringPredicateBuilderView implements Transform<StringPredicateBuilder, String> {

    protected String render(StandardStringOperator op, String columnName) {
        requireNonNull(op);
        requireNonNull(columnName);
        switch (op) {
            case CONTAINS:
                return columnName + " LIKE BINARY CONCAT('%', ? ,'%')";
            case ENDS_WITH:
                return columnName + " LIKE BINARY CONCAT('%', ?)";
            case EQUAL_IGNORE_CASE:
                return "UPPER(" + columnName + ") = UPPER(?)";
            case NOT_EQUAL_IGNORE_CASE:
                return "UPPER(" + columnName + ") <> UPPER(?)";
            case STARTS_WITH:
                return columnName + " LIKE BINARY CONCAT(? ,'%')";
            default:
                throw new UnsupportedOperationException(
                    "Unknown enum constant " + op.name() + "."
                );
        }
    }

    @Override
    public Optional<String> transform(Generator gen, StringPredicateBuilder model) {
        requireNonNull(gen);
        requireNonNull(model);
        final StringOperator so = model.getStringOperator();
        if (so instanceof StandardStringOperator) {

            @SuppressWarnings("unchecked")
            final StandardStringOperator sso = (StandardStringOperator) so;

            return Optional.of("("
                + render(sso, model.getField().getColumnName())
                + ")"
            );
        } else {
            return Optional.empty();
        }
    }
}
