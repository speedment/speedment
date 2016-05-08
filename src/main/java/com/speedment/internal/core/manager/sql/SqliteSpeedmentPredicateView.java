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

import com.speedment.db.DatabaseNamingConvention;
import com.speedment.field.predicate.SpeedmentPredicate;
import static com.speedment.internal.core.field.predicate.PredicateUtil.*;

/**
 *
 * @author ikost
 * @since 2.2.4
 */
@SuppressWarnings("rawtypes")
public final class SqliteSpeedmentPredicateView extends AbstractSpeedmentPredicateView implements SpeedmentPredicateView {

    public SqliteSpeedmentPredicateView(DatabaseNamingConvention namingConvention) {
        super(namingConvention);
    }

   @Override
    protected SqlPredicateFragment equalIgnoreCaseHelper(String cn, SpeedmentPredicate<?, ?, ?> model, boolean negated) {
        return of("(LOWER(" + cn + ") = LOWER(?))", negated).add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment startsWithHelper(String cn, SpeedmentPredicate<?, ?, ?> model, boolean negated) {
        return of("(" + cn + " LIKE ? || '%')", negated).add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment endsWithHelper(String cn, SpeedmentPredicate<?, ?, ?> model, boolean negated) {
        return of("(" + cn + " LIKE '%' || ?)", negated).add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment containsHelper(String cn, SpeedmentPredicate<?, ?, ?> model, boolean negated) {
        return of("(" + cn + " LIKE '%' || ? || '%')", negated).add(getFirstOperandAsRaw(model));
    }
}