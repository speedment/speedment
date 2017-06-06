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
package com.speedment.runtime.core.internal.manager.sql;

import com.speedment.runtime.core.db.FieldPredicateView;
import com.speedment.runtime.core.db.SqlPredicateFragment;
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
public final class MySqlSpeedmentPredicateView extends AbstractFieldPredicateView implements FieldPredicateView {

    /*
    create table colltest (
      name character(20)
    ) charset latin1;

    insert into colltest (name) values ('olle');
    insert into colltest (name) values ('sven');
    
    select * from colltest where name = 'olle' collate utf8_bin;

    select * from colltest where name in ('Olle', 'tryggve' collate utf8_bin) ;
    
     */
    private enum Collation {

        UTF8_BIN, UTF8_GENERAL_CI;

        private final String collateCommand;

        private Collation() {
            this.collateCommand = "COLLATE " + name().toLowerCase();
        }

        String getCollateCommand() {
            return collateCommand;
        }
    }

    @Override
    protected SqlPredicateFragment equalIgnoreCaseHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of(compare(cn, " = ?", Collation.UTF8_GENERAL_CI), negated).add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment startsWithHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(" + cn + " LIKE BINARY CONCAT(? ,'%'))", negated).add(getFirstOperandAsRaw(model));
        // Todo: Use collation like this:
        // return of("(" + cn + " " + Collation.UTF8_BIN.getCollateCommand() + " LIKE CONCAT(? ,'%'))", negated).add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment startsWithIgnoreCaseHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(LOWER(" + cn + ") LIKE BINARY CONCAT(LOWER(?) ,'%'))", negated).add(getFirstOperandAsRaw(model));
        // Todo: Use collation like this:
        // return of("(" + cn + " " + Collation.UTF8_GENERAL_CI.getCollateCommand() + " LIKE BINARY CONCAT(? ,'%'))", negated).add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment endsWithHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(" + cn + " LIKE BINARY CONCAT('%', ?))", negated).add(getFirstOperandAsRaw(model));
        // Todo: Use collation like this:
        // return of("(" + cn + " " + Collation.UTF8_BIN.getCollateCommand() + " LIKE CONCAT('%', ?))", negated).add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment endsWithIgnoreCaseHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(LOWER(" + cn + ") LIKE BINARY CONCAT('%', LOWER(?)))", negated).add(getFirstOperandAsRaw(model));
        // Todo: Use collation like this:
        // return of("(" + cn + " " + Collation.UTF8_GENERAL_CI.getCollateCommand() +" LIKE CONCAT('%', ?))", negated).add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment containsHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(" + cn + " LIKE BINARY CONCAT('%', ? ,'%'))", negated).add(getFirstOperandAsRaw(model));
        // Todo: Use collation like this:
        // return of("(" + cn + " " + Collation.UTF8_BIN.getCollateCommand() +" LIKE CONCAT('%', ? ,'%'))", negated).add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment containsIgnoreCaseHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(LOWER(" + cn + ") LIKE BINARY CONCAT('%', LOWER(?) ,'%'))", negated).add(getFirstOperandAsRaw(model));
        // Todo: Use collation like this:
        //return of("(" + cn + " "+Collation.UTF8_GENERAL_CI.getCollateCommand()+" LIKE CONCAT('%', ? ,'%'))", negated).add(getFirstOperandAsRaw(model));        
    }

    @Override
    protected SqlPredicateFragment equalString(String cn, FieldPredicate<?> model) {
        return of(compare(cn, " = ?", Collation.UTF8_BIN)).add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment notEqualString(String cn, FieldPredicate<?> model) {
        return of("(NOT " + compare(cn, " = ?", Collation.UTF8_BIN) + ")").add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment inString(String cn, FieldPredicate<?> model) {
        return inStringHelper(cn, model, false);
    }

    @Override
    protected SqlPredicateFragment notInString(String cn, FieldPredicate<?> model) {
        return inStringHelper(cn, model, true);
    }

    private SqlPredicateFragment inStringHelper(String cn, FieldPredicate<?> model, boolean negated) {
        final Set<?> set = getFirstOperandAsRawSet(model);
        return of("(" + cn + " IN (" + set.stream().map($ -> "?").collect(joining(",")) + " "+ Collation.UTF8_BIN.getCollateCommand() +"))", negated).addAll(set);
    }

    @Override
    protected SqlPredicateFragment betweenString(String cn, FieldPredicate<?> model) {
        return betweenStringHelper(cn, model, false);
    }

    @Override
    protected SqlPredicateFragment notBetweenString(String cn, FieldPredicate<?> model) {
        return betweenStringHelper(cn, model, true);
    }

    private SqlPredicateFragment betweenStringHelper(String cn, FieldPredicate<?> model, boolean negated) {
        final Inclusion inclusion = getInclusionOperand(model);
        switch (inclusion) {
            case START_EXCLUSIVE_END_EXCLUSIVE: {
                return of(
                    "(" + greaterThanString(cn) + " AND " + lessThanString(cn) + ")",
                    negated
                ).add(getFirstOperandAsRaw(model)).add(getSecondOperand(model));
            }
            case START_INCLUSIVE_END_EXCLUSIVE: {
                return of(
                    "(" + greaterOrEqualString(cn) + " AND " + lessThanString(cn) + ")",
                    negated
                ).add(getFirstOperandAsRaw(model)).add(getSecondOperand(model));
            }
            case START_EXCLUSIVE_END_INCLUSIVE: {

                return of(
                    "(" + greaterThanString(cn) + " AND " + lessOrEqualString(cn) + ")",
                    negated
                ).add(getFirstOperandAsRaw(model)).add(getSecondOperand(model));
            }
            case START_INCLUSIVE_END_INCLUSIVE: {
                return of(
                    "(" + greaterOrEqualString(cn) + " AND " + lessOrEqualString(cn) + ")",
                    negated
                ).add(getFirstOperandAsRaw(model)).add(getSecondOperand(model));
            }
        }
        throw new IllegalArgumentException("Unknown Inclusion:" + inclusion);
    }

    @Override
    protected SqlPredicateFragment lessOrEqualString(String cn, FieldPredicate<?> model) {
        return of(lessOrEqualString(cn)).add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment lessThanString(String cn, FieldPredicate<?> model) {
        return of(lessThanString(cn)).add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment greaterOrEqualString(String cn, FieldPredicate<?> model) {
        return of(greaterOrEqualString(cn)).add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment greaterThanString(String cn, FieldPredicate<?> model) {
        return of(greaterThanString(cn)).add(getFirstOperandAsRaw(model));
    }

    private String lessOrEqualString(String cn) {
        return compare(cn, "<= ?", Collation.UTF8_BIN);
    }

    private String lessThanString(String cn) {
        return compare(cn, "< ?", Collation.UTF8_BIN);
    }

    private String greaterOrEqualString(String cn) {
        return compare(cn, ">= ?", Collation.UTF8_BIN);
    }

    private String greaterThanString(String cn) {
        return compare(cn, "> ?", Collation.UTF8_BIN);
    }

    private String compare(String cn, String operator, Collation collation) {
        return new StringBuilder()
            .append('(')
            .append(cn)
            .append(' ')
            .append(operator)
            .append(' ')
            .append(collation.getCollateCommand())
            .append(')')
            .toString();
    }

}
