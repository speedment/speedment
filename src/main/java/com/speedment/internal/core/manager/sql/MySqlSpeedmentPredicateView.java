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
package com.speedment.internal.core.manager.sql;

import com.speedment.field.Inclusion;
import com.speedment.field.predicate.PredicateType;
import com.speedment.field.predicate.SpeedmentPredicate;
import java.util.Set;
import static com.speedment.field.predicate.PredicateType.IS_NOT_NULL;
import static com.speedment.field.predicate.PredicateType.IS_NULL;
import static com.speedment.field.predicate.PredicateType.NOT_BETWEEN;
import static com.speedment.field.predicate.PredicateType.NOT_CONTAINS;
import static com.speedment.field.predicate.PredicateType.NOT_ENDS_WITH;
import static com.speedment.field.predicate.PredicateType.NOT_EQUAL_IGNORE_CASE;
import static com.speedment.field.predicate.PredicateType.NOT_IN;
import static com.speedment.field.predicate.PredicateType.NOT_STARTS_WITH;
import static com.speedment.internal.core.field.predicate.PredicateUtil.*;
import static java.util.stream.Collectors.toList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 *
 * @author Emil Forslund
 */
@SuppressWarnings("rawtypes")
public final class MySqlSpeedmentPredicateView extends AbstractSpeedmentPredicateView implements SpeedmentPredicateView {

    // TODO: Get from DbmsType
    private static final String OPENING_FIELD_QUOTE =  "`";
    private static final String CLOSING_FIELD_QUOTE = "`";

    @Override
    protected SqlPredicateFragment render(SpeedmentPredicate model) {
        requireNonNull(model);
        final PredicateType pt = model.getEffectivePredicateType();
        final String cn = OPENING_FIELD_QUOTE + model.getField().getColumnName() + CLOSING_FIELD_QUOTE;
        switch (pt) {
            // Constants
            case ALWAYS_TRUE:
                return of("(TRUE)");
            case ALWAYS_FALSE:
                return of("(FALSE)");
            // Reference
            case IS_NULL:
                return of("(" + cn + " IS NULL)");
            case IS_NOT_NULL:
                return of("(" + cn + " IS NOT NULL)");
            // Comparable
            case EQUAL:
                return of("(" + cn + " = ?)").add(getFirstOperandAsRaw(model));
            case NOT_EQUAL:
                return of("(NOT (" + cn + " = ?))").add(getFirstOperandAsRaw(model));
            case GREATER_THAN:
                return of("(" + cn + " > ?)").add(getFirstOperandAsRaw(model));
            case GREATER_OR_EQUAL:
                return of("(" + cn + " >= ?)").add(getFirstOperandAsRaw(model));
            case LESS_THAN:
                return of("(" + cn + " < ?)").add(getFirstOperandAsRaw(model));
            case LESS_OR_EQUAL:
                return of("(" + cn + " <= ?)").add(getFirstOperandAsRaw(model));

            case BETWEEN:
            case NOT_BETWEEN: {
                final boolean negated = (pt == NOT_BETWEEN);
                final Inclusion inclusion = getThirdOperandAsInclusion(model);
                switch (inclusion) {
                    case START_EXCLUSIVE_END_EXCLUSIVE: {
                        return of("(" + cn + " > ? AND " + cn + " < ?)", negated).add(getFirstOperandAsRaw(model)).add(getSecondOperandAsRaw(model));
                    }
                    case START_INCLUSIVE_END_EXCLUSIVE: {
                        return of("(" + cn + " >= ? AND " + cn + " < ?)", negated).add(getFirstOperandAsRaw(model)).add(getSecondOperandAsRaw(model));
                    }
                    case START_EXCLUSIVE_END_INCLUSIVE: {
                        return of("(" + cn + " > ? AND " + cn + " <= ?)", negated).add(getFirstOperandAsRaw(model)).add(getSecondOperandAsRaw(model));
                    }
                    case START_INCLUSIVE_END_INCLUSIVE: {
                        return of("(" + cn + " >= ? AND " + cn + " <= ?)", negated).add(getFirstOperandAsRaw(model)).add(getSecondOperandAsRaw(model));
                    }
                }
                throw new IllegalArgumentException("Unknown Inclusion:" + inclusion);
            }
            case IN:
            case NOT_IN: {
                final boolean negated = (pt == NOT_IN);
                final Set<?> set = getFirstOperandAsRawSet(model);
                return of("(" + cn + " IN (" + set.stream().map(o -> "?").collect(joining(",")) + "))", negated).addAll(set.stream().collect(toList()));
            }

            case EQUAL_IGNORE_CASE:
            case NOT_EQUAL_IGNORE_CASE: {
                final boolean negated = (pt == NOT_EQUAL_IGNORE_CASE);
                return of("(UPPER(" + cn + ") = UPPER(?))", negated).add(getFirstOperandAsRaw(model));
            }

            case STARTS_WITH:
            case NOT_STARTS_WITH: {
                final boolean negated = (pt == NOT_STARTS_WITH);
                return of("(" + cn + " LIKE BINARY CONCAT(? ,'%'))", negated).add(getFirstOperandAsRaw(model));
            }
            case ENDS_WITH:
            case NOT_ENDS_WITH: {
                final boolean negated = (pt == NOT_ENDS_WITH);
                return of("(" + cn + " LIKE BINARY CONCAT('%', ?))", negated).add(getFirstOperandAsRaw(model));
            }
            case CONTAINS:
            case NOT_CONTAINS: {
                final boolean negated = (pt == NOT_CONTAINS);
                return of("(" + cn + " LIKE BINARY CONCAT('%', ? ,'%'))", negated).add(getFirstOperandAsRaw(model));
            }
            case IS_EMPTY:
                return of("(" + cn + " = '')");
            case IS_NOT_EMPTY:
                return of("(" + cn + " <> '')");
            default:
                throw new UnsupportedOperationException(
                    "Unknown PredicateType  " + pt.name() + "."
                );
        }
    }

}
