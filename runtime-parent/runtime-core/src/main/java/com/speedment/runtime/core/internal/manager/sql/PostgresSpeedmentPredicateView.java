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
import com.speedment.runtime.field.predicate.FieldPredicate;

import static com.speedment.runtime.field.util.PredicateOperandUtil.getFirstOperandAsRaw;

/**
 * Created by fdirlikl on 11/18/2015.
 * 
 * @author  Fatih Dirlikli
 */
public class PostgresSpeedmentPredicateView extends AbstractFieldPredicateView implements FieldPredicateView {

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
    
    
    @Override
    protected SqlPredicateFragment lessOrEqualString(String cn, FieldPredicate<?> model) {
        return of("(" + cn + "::bytea <= ?::bytea)").add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment lessThanString(String cn, FieldPredicate<?> model) {
        return of("(" + cn + "::bytea < ?::bytea)").add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment greaterOrEqualString(String cn, FieldPredicate<?> model) {
        return of("(" + cn + "::bytea >= ?::bytea)").add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment greaterThanString(String cn, FieldPredicate<?> model) {
        return of("(" + cn + "::bytea > ?::bytea)").add(getFirstOperandAsRaw(model));
    }
    
    
}