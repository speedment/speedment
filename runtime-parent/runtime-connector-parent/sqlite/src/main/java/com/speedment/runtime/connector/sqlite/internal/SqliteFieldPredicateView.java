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
package com.speedment.runtime.connector.sqlite.internal;

import com.speedment.runtime.core.db.SqlPredicateFragment;
import com.speedment.runtime.core.abstracts.AbstractFieldPredicateView;
import com.speedment.runtime.field.predicate.FieldPredicate;

import static com.speedment.runtime.field.util.PredicateOperandUtil.getFirstOperandAsRaw;

/**
 * @author Emil Forslund
 * @since  3.1.10
 */
public class SqliteFieldPredicateView extends AbstractFieldPredicateView {

    @Override
    protected SqlPredicateFragment equalHelper(String cn, Object argument) {
        return of("(" + cn + " = ?)").add(argument);
    }

    @Override
    protected SqlPredicateFragment notEqualHelper(String cn, Object argument) {
        return of("(" + cn + " != ?)").add(argument);
    }

    @Override
    protected SqlPredicateFragment equalIgnoreCaseHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(" + cn + " COLLATE NOCASE " + (negated ? "!= ?)" : "= ?)"))
            .add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment startsWithHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(" + cn + " GLOB (? || \"*\"))", negated)
            .add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment startsWithIgnoreCaseHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(" + cn + " COLLATE NOCASE LIKE (? || \"%\") ESCAPE \"_\")", negated)
            .add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment endsWithHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(" + cn + " GLOB (\"*\" || ?))", negated)
            .add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment endsWithIgnoreCaseHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(" + cn + " COLLATE NOCASE LIKE (\"%\" || ?) ESCAPE \"_\")", negated)
            .add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment containsHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(" + cn + " GLOB (\"*\" || ? || \"*\"))", negated)
            .add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment containsIgnoreCaseHelper(String cn, FieldPredicate<?> model, boolean negated) {
        return of("(" + cn + " COLLATE NOCASE LIKE (\"%\" || ? || \"%\") ESCAPE \"_\")", negated)
            .add(getFirstOperandAsRaw(model));
    }
}
