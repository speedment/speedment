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
package com.speedment.runtime.connector.mysql.internal;

import static com.speedment.runtime.field.util.PredicateOperandUtil.getFirstOperandAsRaw;
import static com.speedment.runtime.field.util.PredicateOperandUtil.getFirstOperandAsRawSet;
import static com.speedment.runtime.field.util.PredicateOperandUtil.getInclusionOperand;
import static com.speedment.runtime.field.util.PredicateOperandUtil.getSecondOperand;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

import com.speedment.runtime.core.abstracts.AbstractFieldPredicateView;
import com.speedment.runtime.core.db.FieldPredicateView;
import com.speedment.runtime.core.db.SqlPredicateFragment;
import com.speedment.runtime.field.predicate.FieldPredicate;
import com.speedment.runtime.field.predicate.Inclusion;

import java.util.Set;

/**
 *
 * @author Per Minborg
 * @author Emil Forslund
 */
public final class MySqlSpeedmentPredicateView
    extends AbstractFieldPredicateView
    implements FieldPredicateView {

    private static final String LOWER = "LOWER";
    private static final String AND = " AND ";

    private final String binaryCollationName;
    private final String collationName;

    public MySqlSpeedmentPredicateView(String binaryCollationName, String collationName) {
        this.binaryCollationName = requireNonNull(binaryCollationName);
        this.collationName = requireNonNull(collationName);
    }

    ////////////////////////////////////////////////////////////////////////////
    //                         String Operation Helpers                       //
    ////////////////////////////////////////////////////////////////////////////
    
    @Override
    protected SqlPredicateFragment
    equalIgnoreCaseHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of(compare(cn, "= ?", collationName), negated)
            .add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment
    startsWithHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(" + cn + " LIKE BINARY CONCAT(? ,'%'))", negated)
            .add(getFirstOperandAsRaw(model));
        // Todo: Use collation like this:
        // return of("(" + cn + " " + Collation.UTF8_BIN.getCollateCommand() + " LIKE CONCAT(? ,'%'))", negated).add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment
    startsWithIgnoreCaseHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(" + LOWER + "(" + cn + ") LIKE BINARY CONCAT(" + LOWER + "(?) ,'%'))", negated)
            .add(getFirstOperandAsRaw(model));
        // Todo: Use collation like this:
        // return of("(" + cn + " " + Collation.UTF8_GENERAL_CI.getCollateCommand() + " LIKE BINARY CONCAT(? ,'%'))", negated).add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment
    endsWithHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(" + cn + " LIKE BINARY CONCAT('%', ?))", negated)
            .add(getFirstOperandAsRaw(model));
        // Todo: Use collation like this:
        // return of("(" + cn + " " + Collation.UTF8_BIN.getCollateCommand() + " LIKE CONCAT('%', ?))", negated).add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment
    endsWithIgnoreCaseHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(" + LOWER + "(" + cn + ") LIKE BINARY CONCAT('%', " + LOWER + "(?)))", negated)
            .add(getFirstOperandAsRaw(model));
        // Todo: Use collation like this:
        // return of("(" + cn + " " + Collation.UTF8_GENERAL_CI.getCollateCommand() +" LIKE CONCAT('%', ?))", negated).add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment
    containsHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(" + cn + " LIKE BINARY CONCAT('%', ? ,'%'))", negated)
            .add(getFirstOperandAsRaw(model));
        // Todo: Use collation like this:
        // return of("(" + cn + " " + Collation.UTF8_BIN.getCollateCommand() +" LIKE CONCAT('%', ? ,'%'))", negated).add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment
    containsIgnoreCaseHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(" + LOWER + "(" + cn + ") LIKE BINARY CONCAT('%', " + LOWER + "(?) ,'%'))", negated)
            .add(getFirstOperandAsRaw(model));
        // Todo: Use collation like this:
        //return of("(" + cn + " "+Collation.UTF8_GENERAL_CI.getCollateCommand()+" LIKE CONCAT('%', ? ,'%'))", negated).add(getFirstOperandAsRaw(model));        
    }

    ////////////////////////////////////////////////////////////////////////////
    //                    Check for String Type in Operators                  //
    ////////////////////////////////////////////////////////////////////////////

    @Override
    protected SqlPredicateFragment
    equal(String cn, Class<?> dbType, FieldPredicate<?> model) {
        return equalHelper(cn, dbType, getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment
    notEqual(String cn, Class<?> dbType, FieldPredicate<?> model) {
        return notEqualHelper(cn, dbType, getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment
    in(String cn, Class<?> dbType, FieldPredicate<?> model) {
        if (dbType.equals(String.class)) { // Use collation for string types
            return inStringHelper(cn, model, false);
        } else {
            return super.in(cn, dbType, model);
        }
    }

    @Override
    protected SqlPredicateFragment
    notIn(String cn, Class<?> dbType, FieldPredicate<?> model) {
        if (dbType.equals(String.class)) { // Use collation for string types
            return inStringHelper(cn, model, true);
        } else {
            return super.notIn(cn, dbType, model);
        }
    }

    @Override
    protected SqlPredicateFragment
    between(String cn, Class<?> dbType, FieldPredicate<?> model) {
        if (dbType.equals(String.class)) { // Use collation for string types
            return betweenStringHelper(cn, model, false);
        } else {
            return super.between(cn, dbType, model);
        }
    }

    @Override
    protected SqlPredicateFragment
    notBetween(String cn, Class<?> dbType, FieldPredicate<?> model) {
        if (dbType.equals(String.class)) { // Use collation for string types
            return betweenStringHelper(cn, model, true);
        } else {
            return super.notBetween(cn, dbType, model);
        }
    }

    @Override
    protected SqlPredicateFragment
    lessOrEqual(String cn, Class<?> dbType, FieldPredicate<?> model) {
        if (dbType.equals(String.class)) { // Use collation for string types
            return of(lessOrEqualString(cn))
                .add(getFirstOperandAsRaw(model));
        } else {
            return super.lessOrEqual(cn, dbType, model);
        }
    }

    @Override
    protected SqlPredicateFragment
    lessThan(String cn, Class<?> dbType, FieldPredicate<?> model) {
        if (dbType.equals(String.class)) { // Use collation for string types
            return of(lessThanString(cn))
                .add(getFirstOperandAsRaw(model));
        } else {
            return super.lessThan(cn, dbType, model);
        }
    }

    @Override
    protected SqlPredicateFragment
    greaterOrEqual(String cn, Class<?> dbType, FieldPredicate<?> model) {
        if (dbType.equals(String.class)) { // Use collation for string types
            return of(greaterOrEqualString(cn))
                .add(getFirstOperandAsRaw(model));
        } else {
            return super.greaterOrEqual(cn, dbType, model);
        }
    }

    @Override
    protected SqlPredicateFragment
    greaterThan(String cn, Class<?> dbType, FieldPredicate<?> model) {
        if (dbType.equals(String.class)) { // Use collation for string types
            return of(greaterThanString(cn))
                .add(getFirstOperandAsRaw(model));
        } else {
            return super.greaterThan(cn, dbType, model);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //                          Private Helper Methods                        //
    ////////////////////////////////////////////////////////////////////////////

    private SqlPredicateFragment
    inStringHelper(String cn, FieldPredicate<?> model, boolean negated) {
        final Set<?> set = getFirstOperandAsRawSet(model);
        if (set.isEmpty()) {
            return negated ? alwaysTrue() : alwaysFalse();
        } else if (set.size() == 1) {
            final Object arg = set.iterator().next();
            return negated
                ? notEqualHelper(cn, String.class, arg)
                : equalHelper(cn, String.class, arg);
        }

        return of("(" + cn + " IN (" +
            set.stream().map(unused -> "?").collect(joining(",")) +
            " COLLATE "+ binaryCollationName +"))", negated
        ).addAll(set);
    }

    private SqlPredicateFragment
    betweenStringHelper(String cn, FieldPredicate<?> model, boolean negated) {
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

    private SqlPredicateFragment
    equalHelper(String cn, Class<?> dbType, Object argument) {
        if (dbType.equals(String.class)) { // Use collation for string types
            return of(compare(cn, "= ?", binaryCollationName))
                .add(argument);
        } else {
            return of("(" + cn + " = ?)").add(argument);
        }
    }

    private SqlPredicateFragment
    notEqualHelper(String cn, Class<?> dbType, Object argument) {
        if (dbType.equals(String.class)) { // Use collation for string types
            return of("(NOT " + compare(cn, "= ?", binaryCollationName) + ")")
                .add(argument);
        } else {
            return of("(NOT (" + cn + " = ?))").add(argument);
        }
    }

    private String lessOrEqualString(String cn) {
        return compare(cn, "<= ?", binaryCollationName);
    }

    private String lessThanString(String cn) {
        return compare(cn, "< ?", binaryCollationName);
    }

    private String greaterOrEqualString(String cn) {
        return compare(cn, ">= ?", binaryCollationName);
    }

    private String greaterThanString(String cn) {
        return compare(cn, "> ?", binaryCollationName);
    }

    private String compare(String cn, String operator, String collation) {
        return "(" + cn + ' ' + operator + " COLLATE " + collation + ')';
    }
}
