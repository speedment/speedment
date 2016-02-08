/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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

import com.speedment.field.predicate.SpeedmentPredicate;
import static com.speedment.internal.core.field.predicate.PredicateUtil.*;

/**
 *
 * @author Emil Forslund
 */
@SuppressWarnings("rawtypes")
public final class MySqlSpeedmentPredicateView extends AbstractSpeedmentPredicateView implements SpeedmentPredicateView {

    public MySqlSpeedmentPredicateView(String openingFieldQuote, String closingFieldQuote) {
        super(openingFieldQuote, closingFieldQuote);
    }

    @Override
    protected SqlPredicateFragment equalIgnoreCaseHelper(String cn, SpeedmentPredicate<?,?> model, boolean negated) {
        return of("(LOWER(" + cn + ") = LOWER(?))", negated).add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment startsWithHelper(String cn, SpeedmentPredicate<?,?> model, boolean negated) {
        return of("(" + cn + " LIKE BINARY CONCAT(? ,'%'))", negated).add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment endsWithHelper(String cn, SpeedmentPredicate<?,?> model, boolean negated) {
        return of("(" + cn + " LIKE BINARY CONCAT('%', ?))", negated).add(getFirstOperandAsRaw(model));
    }

    @Override
    protected SqlPredicateFragment containsHelper(String cn, SpeedmentPredicate<?,?> model, boolean negated) {
        return of("(" + cn + " LIKE BINARY CONCAT('%', ? ,'%'))", negated).add(getFirstOperandAsRaw(model));
    }

}
