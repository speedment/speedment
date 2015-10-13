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

import com.speedment.field2.Inclusion;
import com.speedment.field2.predicate.PredicateType;
import static com.speedment.field2.predicate.PredicateType.IS_NOT_NULL;
import static com.speedment.field2.predicate.PredicateType.IS_NULL;
import com.speedment.field2.predicate.SpeedmentPredicate;
import java.util.Set;
import static java.util.stream.Collectors.toList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 *
 * @author Emil Forslund
 */
@SuppressWarnings("rawtypes")
public final class MySqlSpeedmentPredicateView extends AbstractSpeedmentPredicateView implements SpeedmentPredicateView {

    @Override
    protected SqlPredicateFragment renderUninverted(SpeedmentPredicate model) {
        requireNonNull(model);
        final PredicateType pt = model.getPredicateType();
        final String cn = model.getField().getColumnName();
        switch (pt) {
            // Constants
            case ALWAYS_FALSE:
                return of("(FALSE)");
            case ALWAYS_TRUE:
                return of("(TRUE)");
            // Reference
            case IS_NULL:
                return of("(" + cn + " IS NULL)");
            case IS_NOT_NULL:
                return of("(" + cn + " IS NOT NULL)");
            // Comparable
            case EQUAL:
                return of("(" + cn + " = ?)").add(oper0(model));
            case NOT_EQUAL:
                return of("(NOT " + cn + " = ?))").add(oper0(model));
            case GREATER_THAN:
                return of("(" + cn + " > ?)").add(oper0(model));
            case GREATER_OR_EQUAL:
                return of("(" + cn + " >= ?)").add(oper0(model));
            case LESS_THAN:
                return of("(" + cn + " < ?)").add(oper0(model));
            case LESS_OR_EQUAL:
                return of("(" + cn + " <= ?)").add(oper0(model));
            case BETWEEN: {
                final Inclusion inclusion = inclusionOper2(model);
                switch (inclusion) {
                    case START_EXCLUSIVE_END_EXCLUSIVE: {
                        return of("(" + cn + " > ? AND " + cn + " < ?)").add(oper0(model)).add(oper1(model));
                    }
                    case START_INCLUSIVE_END_EXCLUSIVE: {
                        return of("(" + cn + " >= ? AND " + cn + " < ?)").add(oper0(model)).add(oper1(model));
                    }
                    case START_EXCLUSIVE_END_INCLUSIVE: {
                        return of("(" + cn + " > ? AND " + cn + " <= ?)").add(oper0(model)).add(oper1(model));
                    }
                    case START_INCLUSIVE_END_INCLUSIVE: {
                        return of("(" + cn + " >= ? AND " + cn + " <= ?)").add(oper0(model)).add(oper1(model));
                    }
                }
                throw new IllegalArgumentException("Unknown Inclusion:" + inclusion);
            }
            case IN: {
                final Set<?> set = setOper0(model);
                return of("(" + cn + " IN (" + set.stream().map(o -> "?").collect(joining(",")) + ")").addAll(set.stream().collect(toList()));
            }
            case EQUAL_IGNORE_CASE:
                return of("(UPPER(" + cn + ") = UPPER(?))").add(oper0(model));
            case NOT_EQUAL_IGNORE_CASE:
                return of("(NOT UPPER(" + cn + ") = UPPER(?))").add(oper0(model));
            case STARTS_WITH:
                return of("(" + cn + " LIKE BINARY CONCAT(? ,'%'))").add(oper0(model));
            case ENDS_WITH:
                return of("(" + cn + " LIKE BINARY CONCAT('%', ?))").add(oper0(model));
            case CONTAINS:
                return of("(" + cn + " LIKE BINARY CONCAT('%', ? ,'%'))").add(oper0(model));
            case IS_EMPTY:
                return of("(" + cn + " = '')");
            case IS_NOT_EMPTY:
                return of("(NOT (" + cn + " = ''))");
            default:
                throw new UnsupportedOperationException(
                    "Unknown PredicateType  " + pt.name() + "."
                );
        }
    }

}
