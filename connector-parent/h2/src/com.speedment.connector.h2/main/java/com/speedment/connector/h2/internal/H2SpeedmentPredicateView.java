/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.connector.h2.internal;

import com.speedment.runtime.core.db.FieldPredicateView;
import com.speedment.runtime.core.db.SqlPredicateFragment;
import com.speedment.runtime.core.internal.manager.sql.AbstractFieldPredicateView;
import static com.speedment.runtime.core.internal.manager.sql.AbstractFieldPredicateView.of;
import static com.speedment.runtime.field.util.PredicateOperandUtil.getFirstOperandAsRaw;
import static com.speedment.runtime.field.util.PredicateOperandUtil.getFirstOperandAsRawSet;
import static com.speedment.runtime.field.util.PredicateOperandUtil.getInclusionOperand;
import static com.speedment.runtime.field.util.PredicateOperandUtil.getSecondOperand;
import com.speedment.runtime.field.predicate.FieldPredicate;
import com.speedment.runtime.field.predicate.Inclusion;
import java.util.Set;
import static java.util.stream.Collectors.joining;

/**
 *
 * @author Emil Forslund
 */
@SuppressWarnings("rawtypes")
public final class H2SpeedmentPredicateView extends AbstractFieldPredicateView implements FieldPredicateView {

    @Override
    protected SqlPredicateFragment equalIgnoreCaseHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(LOWER(" + cn + ") = LOWER(?))", negated).add(getFirstOperandAsRaw(model)); // Will work for any collation
    }

    @Override
    protected SqlPredicateFragment startsWithHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(" + cn + " LIKE BINARY CONCAT(? ,'%'))", negated).add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment startsWithIgnoreCaseHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(LOWER(" + cn + ") LIKE BINARY CONCAT(LOWER(?) ,'%'))", negated).add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment endsWithHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(" + cn + " LIKE BINARY CONCAT('%', ?))", negated).add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment endsWithIgnoreCaseHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(LOWER(" + cn + ") LIKE BINARY CONCAT('%', LOWER(?)))", negated).add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment containsHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(" + cn + " LIKE BINARY CONCAT('%', ? ,'%'))", negated).add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment containsIgnoreCaseHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(LOWER(" + cn + ") LIKE BINARY CONCAT('%', LOWER(?) ,'%'))", negated).add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment equalString(String cn, FieldPredicate<?> model) {
        return of("(BINARY " + cn + " = ?)").add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment notEqualString(String cn, FieldPredicate<?> model) {
        return of("(NOT (BINARY " + cn + " = ?))").add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment inString(String cn, FieldPredicate<?> model) {
        return inStringHelper(cn, model, false);
    }

    @Override
    protected SqlPredicateFragment notInString(String cn, FieldPredicate<?> model) {
        return inStringHelper(cn, model, true);
    }

    protected SqlPredicateFragment inStringHelper(String cn, FieldPredicate<?> model, boolean negated) {
        final Set<?> set = getFirstOperandAsRawSet(model);
        return of("(BINARY " + cn + " IN (" + set.stream().map($ -> "?").collect(joining(",")) + "))", negated).addAll(set);
    }

    @Override
    protected SqlPredicateFragment betweenString(String cn, FieldPredicate<?> model) {
        return betweenStringHelper(cn, model, false);
    }

    @Override
    protected SqlPredicateFragment notBetweenString(String cn, FieldPredicate<?> model) {
        return betweenStringHelper(cn, model, true);
    }

    protected SqlPredicateFragment betweenStringHelper(String cn, FieldPredicate<?> model, boolean negated) {
        final Inclusion inclusion = getInclusionOperand(model);
        switch (inclusion) {
            case START_EXCLUSIVE_END_EXCLUSIVE: {
                return of("(" + cn + " > ? AND " + cn + " < ?)", negated).add(getFirstOperandAsRaw(model)).add(getSecondOperand(model));
            }
            case START_INCLUSIVE_END_EXCLUSIVE: {
                return of("(" + cn + " >= ? AND " + cn + " < ?)", negated).add(getFirstOperandAsRaw(model)).add(getSecondOperand(model));
            }
            case START_EXCLUSIVE_END_INCLUSIVE: {
                return of("(" + cn + " > ? AND " + cn + " <= ?)", negated).add(getFirstOperandAsRaw(model)).add(getSecondOperand(model));
            }
            case START_INCLUSIVE_END_INCLUSIVE: {
                return of("(" + cn + " >= ? AND " + cn + " <= ?)", negated).add(getFirstOperandAsRaw(model)).add(getSecondOperand(model));
            }
        }
        throw new IllegalArgumentException("Unknown Inclusion:" + inclusion);
    }

    @Override
    protected SqlPredicateFragment lessOrEqualString(String cn, FieldPredicate<?> model) {
        return of("(BINARY " + cn + " <= ?)").add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment lessThanString(String cn, FieldPredicate<?> model) {
        return of("(BINARY " + cn + " < ?)").add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment greaterOrEqualString(String cn, FieldPredicate<?> model) {
        return of("(BINARY " + cn + " >= ?)").add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment greaterThanString(String cn, FieldPredicate<?> model) {
        return of("(BINARY " + cn + " > ?)").add(getFirstOperandAsRaw(model));
    }

}
