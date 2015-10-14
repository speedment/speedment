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
import static com.speedment.field.predicate.PredicateType.IS_NOT_NULL;
import static com.speedment.field.predicate.PredicateType.IS_NULL;
import com.speedment.field.predicate.SpeedmentPredicate;
import static com.speedment.internal.core.field.predicate.PredicateUtil.*;
import java.util.Set;
import static java.util.stream.Collectors.toList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 *
 * @author Emil Forslund
 */
@SuppressWarnings("rawtypes")
public final class MySqlSpeedmentPredicateView extends AbstractSpeedmentPredicateView implements SpeedmentPredicateView {

    // TODO: Get from DbmsType
    private static final String OPENING_FIELD_QUOTE = "`";
    private static final String CLOSING_FIELD_QUOTE = "`";

    @Override
    protected SqlPredicateFragment renderUninverted(SpeedmentPredicate model) {
        requireNonNull(model);
        final PredicateType pt = model.getPredicateType();
        final String cn = OPENING_FIELD_QUOTE + model.getField().getColumnName() + CLOSING_FIELD_QUOTE;
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
                return of("(" + cn + " = ?)").add(get0Raw(model));
            case NOT_EQUAL:
                return of("(NOT " + cn + " = ?))").add(get0Raw(model));
            case GREATER_THAN:
                return of("(" + cn + " > ?)").add(get0Raw(model));
            case GREATER_OR_EQUAL:
                return of("(" + cn + " >= ?)").add(get0Raw(model));
            case LESS_THAN:
                return of("(" + cn + " < ?)").add(get0Raw(model));
            case LESS_OR_EQUAL:
                return of("(" + cn + " <= ?)").add(get0Raw(model));
            case BETWEEN: {
                final Inclusion inclusion = getInclusion2(model);
                switch (inclusion) {
                    case START_EXCLUSIVE_END_EXCLUSIVE: {
                        return of("(" + cn + " > ? AND " + cn + " < ?)").add(get0Raw(model)).add(get1Raw(model));
                    }
                    case START_INCLUSIVE_END_EXCLUSIVE: {
                        return of("(" + cn + " >= ? AND " + cn + " < ?)").add(get0Raw(model)).add(get1Raw(model));
                    }
                    case START_EXCLUSIVE_END_INCLUSIVE: {
                        return of("(" + cn + " > ? AND " + cn + " <= ?)").add(get0Raw(model)).add(get1Raw(model));
                    }
                    case START_INCLUSIVE_END_INCLUSIVE: {
                        return of("(" + cn + " >= ? AND " + cn + " <= ?)").add(get0Raw(model)).add(get1Raw(model));
                    }
                }
                throw new IllegalArgumentException("Unknown Inclusion:" + inclusion);
            }
            case IN: {
                final Set<?> set = getSet0Raw(model);
                return of("(" + cn + " IN (" + set.stream().map(o -> "?").collect(joining(",")) + "))").addAll(set.stream().collect(toList()));
            }
            case EQUAL_IGNORE_CASE:
                return of("(UPPER(" + cn + ") = UPPER(?))").add(get0Raw(model));
            case NOT_EQUAL_IGNORE_CASE:
                return of("(NOT UPPER(" + cn + ") = UPPER(?))").add(get0Raw(model));
            case STARTS_WITH:
                return of("(" + cn + " LIKE BINARY CONCAT(? ,'%'))").add(get0Raw(model));
            case ENDS_WITH:
                return of("(" + cn + " LIKE BINARY CONCAT('%', ?))").add(get0Raw(model));
            case CONTAINS:
                return of("(" + cn + " LIKE BINARY CONCAT('%', ? ,'%'))").add(get0Raw(model));
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
