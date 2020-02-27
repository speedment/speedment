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

package com.speedment.test.connector;

import com.speedment.runtime.core.abstracts.AbstractFieldPredicateView;
import com.speedment.runtime.core.db.SqlPredicateFragment;
import org.junit.jupiter.api.DynamicTest;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author Mislav Milicevic
 * @since 3.2.9
 */
public abstract class AbstractFieldPredicateViewMixin implements FieldPredicateViewMixin {

    @Override
    public Stream<DynamicTest> transformTests() {
        return null;
    }

    /**
     * Returns an instance of the {@link com.speedment.runtime.core.db.FieldPredicateView}
     * that is being tested.
     *
     * @return instance of the {@link com.speedment.runtime.core.db.FieldPredicateView}
     */
    protected abstract AbstractFieldPredicateView getFieldPredicateViewInstance();

    protected abstract List<Predicate<SqlPredicateFragment>> alwaysTrueConditions();

    protected abstract List<Predicate<SqlPredicateFragment>> alwaysFalseConditions();

    protected abstract List<Predicate<SqlPredicateFragment>> isNullConditions();

    protected abstract List<Predicate<SqlPredicateFragment>> isNotNullConditions();

    protected abstract List<Predicate<SqlPredicateFragment>> equalsConditions();

    protected abstract List<Predicate<SqlPredicateFragment>> notEqualsConditions();

    protected abstract List<Predicate<SqlPredicateFragment>> greaterThanConditions();

    protected abstract List<Predicate<SqlPredicateFragment>> greaterOrEqualConditions();

    protected abstract List<Predicate<SqlPredicateFragment>> lessThanConditions();

    protected abstract List<Predicate<SqlPredicateFragment>> lessOrEqualConditions();

    protected abstract List<Predicate<SqlPredicateFragment>> betweenConditions();

    protected abstract List<Predicate<SqlPredicateFragment>> notBetweenConditions();

    protected abstract List<Predicate<SqlPredicateFragment>> inConditions();

    protected abstract List<Predicate<SqlPredicateFragment>> notInConditions();

    protected abstract List<Predicate<SqlPredicateFragment>> equalsIgnoreCaseConditions();

    protected abstract List<Predicate<SqlPredicateFragment>> notEqualsIgnoreCaseConditions();

    protected abstract List<Predicate<SqlPredicateFragment>> startsWithConditions();

    protected abstract List<Predicate<SqlPredicateFragment>> startsWithIgnoreCaseConditions();

    protected abstract List<Predicate<SqlPredicateFragment>> notStartsWithConditions();

    protected abstract List<Predicate<SqlPredicateFragment>> notStartsWithIgnoreCaseConditions();

    protected abstract List<Predicate<SqlPredicateFragment>> endsWithConditions();

    protected abstract List<Predicate<SqlPredicateFragment>> endsWithIgnoreCaseConditions();

    protected abstract List<Predicate<SqlPredicateFragment>> notEndsWithConditions();

    protected abstract List<Predicate<SqlPredicateFragment>> notEndsWithIgnoreCaseConditions();

    protected abstract List<Predicate<SqlPredicateFragment>> containsConditions();

    protected abstract List<Predicate<SqlPredicateFragment>> containsIgnoreCaseConditions();

    protected abstract List<Predicate<SqlPredicateFragment>> notContainsConditions();

    protected abstract List<Predicate<SqlPredicateFragment>> notContainsIgnoreCaseConditions();

    protected abstract List<Predicate<SqlPredicateFragment>> isEmptyConditions();

    protected abstract List<Predicate<SqlPredicateFragment>> isNotEmptyConditions();
}
