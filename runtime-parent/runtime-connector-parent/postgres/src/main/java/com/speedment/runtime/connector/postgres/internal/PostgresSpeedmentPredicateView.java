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
package com.speedment.runtime.connector.postgres.internal;

import com.speedment.runtime.core.db.FieldPredicateView;
import com.speedment.runtime.core.db.SqlPredicateFragment;
import com.speedment.runtime.core.abstracts.AbstractFieldPredicateView;
import com.speedment.runtime.field.predicate.FieldPredicate;
import com.speedment.runtime.field.predicate.Inclusion;
import static com.speedment.runtime.field.util.PredicateOperandUtil.getFirstOperandAsRaw;
import static com.speedment.runtime.field.util.PredicateOperandUtil.getInclusionOperand;
import static com.speedment.runtime.field.util.PredicateOperandUtil.getSecondOperand;

/**
 * Created by fdirlikl on 11/18/2015.
 *
 * @author Fatih Dirlikli
 */
public class PostgresSpeedmentPredicateView extends AbstractFieldPredicateView implements FieldPredicateView {

    private static final String BYTEA_CAST = "::bytea";
    private static final String AND = " AND ";

    // Info from:
    // http://stackoverflow.com/questions/23320945/postgresql-select-if-string-contains
    // We cannot use collation for PostgreSQL. See https://github.com/speedment/speedment/issues/401    
    @Override
    protected SqlPredicateFragment equalIgnoreCaseHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(LOWER(" + cn + ") = LOWER(?))", negated).add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment startsWithHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(" + cn + " LIKE ? || '%')", negated).add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment startsWithIgnoreCaseHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(" + cn + " ILIKE ? || '%')", negated).add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment endsWithHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(" + cn + " LIKE '%' || ?)", negated).add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment endsWithIgnoreCaseHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(" + cn + " ILIKE '%' || ?)", negated).add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment containsHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(" + cn + " LIKE '%' || ? || '%')", negated).add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment containsIgnoreCaseHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(" + cn + " ILIKE '%' || ? || '%')", negated).add(getFirstOperandAsRaw(model));
    }

    // PostgreSQL will use case sensitive string comparison by default for most operations
    // However, <, <=, > and >= is case insensitive and must be handled separately
    // Because of this, the operations 'in', 'notIn', 'equal' and 'notEqual' are not overridden
    //
    
    @Override
    protected SqlPredicateFragment lessOrEqual(
        final String cn,
        final Class<?> dbType,
        final FieldPredicate<?> model
    ) {
        if (dbType.equals(String.class)) { // Override string behaviour
            return of(lessOrEqualString(cn)).add(getFirstOperandAsRaw(model));
        } else {
            return super.lessOrEqual(cn, dbType, model);
        }
    }

    @Override
    protected SqlPredicateFragment lessThan(
        final String cn,
        final Class<?> dbType,
        final FieldPredicate<?> model
    ) {
        if (dbType.equals(String.class)) { // Override string behaviour
            return of(lessThanString(cn)).add(getFirstOperandAsRaw(model));
        } else {
            return super.lessThan(cn, dbType, model);
        }
    }

    @Override
    protected SqlPredicateFragment greaterOrEqual(
        final String cn,
        final Class<?> dbType,
        final FieldPredicate<?> model
    ) {
        if (dbType.equals(String.class)) { // Override string behaviour
            return of(greaterOrEqualString(cn)).add(getFirstOperandAsRaw(model));
        } else {
            return super.greaterOrEqual(cn, dbType, model);
        }
    }

    @Override
    protected SqlPredicateFragment greaterThan(
        final String cn, Class<?> dbType,
        final FieldPredicate<?> model
    ) {
        if (dbType.equals(String.class)) { // Override string behaviour
            return of(greaterThanString(cn)).add(getFirstOperandAsRaw(model));
        } else {
            return super.greaterThan(cn, dbType, model);
        }
    }

    @Override
    protected SqlPredicateFragment between(
        final String cn, Class<?> dbType,
        final FieldPredicate<?> model
    ) {
        if (dbType.equals(String.class)) {
            return betweenStringHelper(cn, model, false);
        } else {
            return super.between(cn, dbType, model);
        }
    }

    @Override
    protected SqlPredicateFragment notBetween(
        final String cn, Class<?> dbType,
        final FieldPredicate<?> model
    ) {
        if (dbType.equals(String.class)) {
            return betweenStringHelper(cn, model, true);
        } else {
            return super.notBetween(cn, dbType, model);
        }
    }

//    ////////////////////////////////////////////////////////////////////////////
//    //                          Private Helper Methods                        //
//    ////////////////////////////////////////////////////////////////////////////

    private SqlPredicateFragment betweenStringHelper(
        final String cn,
        final FieldPredicate<?> model,
        final boolean negated
    ) {
        final Inclusion inclusion = getInclusionOperand(model);
        switch (inclusion) {
            case START_EXCLUSIVE_END_EXCLUSIVE:
                return predicate(model, negated, greaterThanString(cn), lessThanString(cn));
            case START_INCLUSIVE_END_EXCLUSIVE:
                return predicate(model, negated, greaterOrEqualString(cn), lessThanString(cn));
            case START_EXCLUSIVE_END_INCLUSIVE:
                return predicate(model, negated, greaterThanString(cn), lessOrEqualString(cn));
            case START_INCLUSIVE_END_INCLUSIVE:
                return predicate(model, negated, greaterOrEqualString(cn), lessOrEqualString(cn));
        }
        throw new IllegalArgumentException("Unknown Inclusion:" + inclusion);
    }

    private SqlPredicateFragment predicate(FieldPredicate<?> model, boolean negated, String s, String s2) {
        return of(
            "(" + s + AND + s2 + ")",
            negated
        ).add(getFirstOperandAsRaw(model)).add(getSecondOperand(model));
    }

    private String lessOrEqualString(String cn) {
        return compare(cn, "<= ?");
    }

    private String lessThanString(String cn) {
        return compare(cn, "< ?");
    }

    private String greaterOrEqualString(String cn) {
        return compare(cn, ">= ?");
    }

    private String greaterThanString(String cn) {
        return compare(cn, "> ?");
    }

    private String compare(String cn, String operator) {
        return "(" + cn + BYTEA_CAST + " " + operator + BYTEA_CAST + ")";
    }

}
