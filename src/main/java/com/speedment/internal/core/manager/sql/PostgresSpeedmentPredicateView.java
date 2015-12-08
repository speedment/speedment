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

import com.speedment.field.predicate.SpeedmentPredicate;


/**
 * Created by fdirlikl on 11/18/2015.
 */
public class PostgresSpeedmentPredicateView extends AbstractSpeedmentPredicateView implements SpeedmentPredicateView {

    public PostgresSpeedmentPredicateView(String openingFieldQuote, String closingFieldQuote) {
        super(openingFieldQuote, closingFieldQuote);
    }

    @Override
    protected SqlPredicateFragment equalIgnoreCaseHelper(String cn, SpeedmentPredicate<?,?> model, boolean negated) {
        throw new UnsupportedOperationException("To be implemented");
    }

    @Override
    protected SqlPredicateFragment startsWithHelper(String cn, SpeedmentPredicate<?,?> model, boolean negated) {
        throw new UnsupportedOperationException("To be implemented");
    }

    @Override
    protected SqlPredicateFragment endsWithHelper(String cn, SpeedmentPredicate<?,?> model, boolean negated) {
        throw new UnsupportedOperationException("To be implemented");
    }

    @Override
    protected SqlPredicateFragment containsHelper(String cn, SpeedmentPredicate<?,?> model, boolean negated) {
        throw new UnsupportedOperationException("To be implemented");
    }

}
