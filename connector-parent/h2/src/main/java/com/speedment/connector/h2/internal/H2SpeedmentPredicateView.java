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
import com.speedment.runtime.field.predicate.FieldPredicate;

import java.util.Set;

import static com.speedment.runtime.field.util.PredicateOperandUtil.getFirstOperandAsRaw;
import static com.speedment.runtime.field.util.PredicateOperandUtil.getFirstOperandAsRawSet;
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
    protected SqlPredicateFragment equal(String cn, Class<?> dbType, FieldPredicate<?> model) {
        if (dbType.equals(String.class)) {
            return of("(BINARY " + cn + " = ?)")
                .add(getFirstOperandAsRaw(model));
        } else {
            return super.equal(cn, dbType, model);
        }
    }

    @Override
    protected SqlPredicateFragment notEqual(String cn, Class<?> dbType, FieldPredicate<?> model) {
        if (dbType.equals(String.class)) {
            return of("(BINARY " + cn + " <> ?)")
                .add(getFirstOperandAsRaw(model));
        } else {
            return super.notEqual(cn, dbType, model);
        }
    }

    @Override
    protected SqlPredicateFragment
    in(String cn, Class<?> dbType, FieldPredicate<?> model) {
        if (dbType.equals(String.class)) {
            return inStringHelper(cn, model, false);
        } else {
            return super.in(cn, dbType, model);
        }
    }

    @Override
    protected SqlPredicateFragment
    notIn(String cn, Class<?> dbType, FieldPredicate<?> model) {
        if (dbType.equals(String.class)) {
            return inStringHelper(cn, model, true);
        } else {
            return super.notIn(cn, dbType, model);
        }
    }

    @Override
    protected SqlPredicateFragment
    lessOrEqual(String cn, Class<?> dbType, FieldPredicate<?> model) {
        if (dbType.equals(String.class)) {
            return of("(BINARY " + cn + " <= ?)")
                .add(getFirstOperandAsRaw(model));
        } else {
            return super.lessOrEqual(cn, dbType, model);
        }
    }

    @Override
    protected SqlPredicateFragment
    lessThan(String cn, Class<?> dbType, FieldPredicate<?> model) {
        if (dbType.equals(String.class)) {
            return of("(BINARY " + cn + " < ?)")
                .add(getFirstOperandAsRaw(model));
        } else {
            return super.lessThan(cn, dbType, model);
        }
    }

    @Override
    protected SqlPredicateFragment
    greaterOrEqual(String cn, Class<?> dbType, FieldPredicate<?> model) {
        if (dbType.equals(String.class)) {
            return of("(BINARY " + cn + " >= ?)")
                .add(getFirstOperandAsRaw(model));
        } else {
            return super.greaterOrEqual(cn, dbType, model);
        }
    }

    @Override
    protected SqlPredicateFragment
    greaterThan(String cn, Class<?> dbType, FieldPredicate<?> model) {
        if (dbType.equals(String.class)) {
            return of("(BINARY " + cn + " > ?)")
                .add(getFirstOperandAsRaw(model));
        } else {
            return super.greaterThan(cn, dbType, model);
        }
    }

    private SqlPredicateFragment
    inStringHelper(String cn, FieldPredicate<?> model, boolean negated) {
        final Set<?> set = getFirstOperandAsRawSet(model);
        return of("(BINARY " + cn + " IN (" +
            set.stream().map($ -> "?").collect(joining(",")) +
            "))", negated
        ).addAll(set);
    }
}
