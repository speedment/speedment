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
import com.speedment.test.connector.abstracts.DefaultFieldPredicateViewMixin;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

final class PostgresFieldPredicateViewTest extends DefaultFieldPredicateViewMixin {

    private static final Pattern POSTGRES_COMPARE_PATTERN = Pattern.compile("\\([A-Za-z]+(::[A-Za-z]+)?\\s?(=|>|>=|<|<=)\\s?\\?(::[A-Za-z]+)?\\)");

    @Override
    protected FieldPredicateView getFieldPredicateViewInstance() {
        return new PostgresSpeedmentPredicateView();
    }

    @Override
    protected List<Predicate<SqlPredicateFragment>> compareConditions() {
        final Predicate<SqlPredicateFragment> regularPredicate =
            sqlPredicateFragment -> COMPARE_PATTERN.matcher(sqlPredicateFragment.getSql()).find();
        final Predicate<SqlPredicateFragment> collationPredicate =
            sqlPredicateFragment -> POSTGRES_COMPARE_PATTERN.matcher(sqlPredicateFragment.getSql()).find();
        final Predicate<SqlPredicateFragment> ignoreCasePredicate =
            sqlPredicateFragment -> sqlPredicateFragment.getSql().contains("LOWER");

        return Collections.singletonList(regularPredicate.or(collationPredicate).or(ignoreCasePredicate));
    }
}
