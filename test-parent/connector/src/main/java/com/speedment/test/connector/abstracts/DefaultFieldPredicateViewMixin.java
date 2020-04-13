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

package com.speedment.test.connector.abstracts;

import com.speedment.runtime.core.db.SqlPredicateFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * @author Mislav Milicevic
 * @since 3.2.9
 */
public abstract class DefaultFieldPredicateViewMixin extends AbstractFieldPredicateViewMixin {

    protected static final Pattern COMPARE_PATTERN = Pattern.compile("\\([A-Za-z]+\\s?(=|>|>=|<|<=)\\s?\\?\\)");
    protected static final Pattern IS_EMPTY_PATTERN = Pattern.compile("\\([A-Za-z]+\\s?=\\s?''\\)");
    protected static final Pattern IS_NOT_EMPTY_PATTERN = Pattern.compile("\\([A-Za-z]+\\s?<>\\s?''\\)");

    @Override
    protected List<Predicate<SqlPredicateFragment>> alwaysTrueConditions() {
        return Arrays.asList(
            sqlPredicateFragment -> sqlPredicateFragment.getSql().startsWith("("),
            sqlPredicateFragment -> sqlPredicateFragment.getSql().contains("TRUE") || sqlPredicateFragment.getSql().contains("true"),
            sqlPredicateFragment -> sqlPredicateFragment.getSql().endsWith(")")
        );
    }

    @Override
    protected List<Predicate<SqlPredicateFragment>> alwaysFalseConditions() {
        return Arrays.asList(
            sqlPredicateFragment -> sqlPredicateFragment.getSql().startsWith("("),
            sqlPredicateFragment -> sqlPredicateFragment.getSql().contains("FALSE") || sqlPredicateFragment.getSql().contains("false"),
            sqlPredicateFragment -> sqlPredicateFragment.getSql().endsWith(")")
        );
    }

    @Override
    protected List<Predicate<SqlPredicateFragment>> isNullConditions() {
        return Arrays.asList(
            sqlPredicateFragment -> sqlPredicateFragment.getSql().startsWith("("),
            sqlPredicateFragment -> sqlPredicateFragment.getSql().contains("IS NULL"),
            sqlPredicateFragment -> sqlPredicateFragment.getSql().endsWith(")")
        );
    }

    @Override
    protected List<Predicate<SqlPredicateFragment>> isNotNullConditions() {
        return Arrays.asList(
            sqlPredicateFragment -> sqlPredicateFragment.getSql().startsWith("("),
            sqlPredicateFragment -> sqlPredicateFragment.getSql().contains("IS NOT NULL"),
            sqlPredicateFragment -> sqlPredicateFragment.getSql().endsWith(")")
        );
    }

    @Override
    protected List<Predicate<SqlPredicateFragment>> equalsConditions() {
        return compareConditions();
    }

    @Override
    protected List<Predicate<SqlPredicateFragment>> notEqualsConditions() {
        return notWrapper(equalsConditions());
    }

    @Override
    protected List<Predicate<SqlPredicateFragment>> greaterThanConditions() {
        return compareConditions();
    }

    @Override
    protected List<Predicate<SqlPredicateFragment>> greaterOrEqualConditions() {
        return compareConditions();
    }

    @Override
    protected List<Predicate<SqlPredicateFragment>> lessThanConditions() {
        return compareConditions();
    }

    @Override
    protected List<Predicate<SqlPredicateFragment>> lessOrEqualConditions() {
        return compareConditions();
    }

    @Override
    protected List<Predicate<SqlPredicateFragment>> betweenConditions() {
        final List<Predicate<SqlPredicateFragment>> conditions = new ArrayList<>(compareConditions());
        conditions.add(sqlPredicateFragment -> sqlPredicateFragment.getSql().contains("AND"));
        return conditions;
    }

    @Override
    protected List<Predicate<SqlPredicateFragment>> notBetweenConditions() {
        return notWrapper(betweenConditions());
    }

    @Override
    protected List<Predicate<SqlPredicateFragment>> inConditions() {
        final Predicate<SqlPredicateFragment> predicate = sqlPredicateFragment -> {
            switch ((int) sqlPredicateFragment.objects().count()) {
                case 0:
                    return alwaysFalseConditions().stream()
                        .reduce($ -> true, Predicate::and)
                        .test(sqlPredicateFragment);
                case 1:
                    return equalsConditions().stream()
                        .reduce($ -> true, Predicate::and)
                        .test(sqlPredicateFragment);
                default:
                    return fullInConditions().stream()
                        .reduce(x -> true, Predicate::and)
                        .test(sqlPredicateFragment);
            }
        };
        return Collections.singletonList(predicate);
    }

    private List<Predicate<SqlPredicateFragment>> fullInConditions() {
        return Arrays.asList(
            sqlPredicateFragment -> sqlPredicateFragment.getSql().startsWith("("),
            sqlPredicateFragment -> sqlPredicateFragment.getSql().endsWith(")"),
            sqlPredicateFragment -> sqlPredicateFragment.getSql().contains("IN"),
            sqlPredicateFragment -> sqlPredicateFragment.getSql().chars().filter(c -> c == '?').count() == sqlPredicateFragment.objects().count()
        );
    }

    @Override
    protected List<Predicate<SqlPredicateFragment>> notInConditions() {
        final Predicate<SqlPredicateFragment> predicate = sqlPredicateFragment -> {
            switch ((int) sqlPredicateFragment.objects().count()) {
                case 0:
                    return alwaysTrueConditions().stream()
                        .reduce($ -> true, Predicate::and)
                        .test(sqlPredicateFragment);
                case 1:
                    return notEqualsConditions().stream()
                        .reduce($ -> true, Predicate::and)
                        .test(sqlPredicateFragment);
                default:
                     return notWrapper(fullInConditions()).stream()
                        .reduce(x -> true, Predicate::and)
                        .test(sqlPredicateFragment);
            }
        };
        return Collections.singletonList(predicate);
    }

    @Override
    protected List<Predicate<SqlPredicateFragment>> equalsIgnoreCaseConditions() {
        return equalsConditions();
    }

    @Override
    protected List<Predicate<SqlPredicateFragment>> notEqualsIgnoreCaseConditions() {
        return notEqualsConditions();
    }

    @Override
    protected List<Predicate<SqlPredicateFragment>> startsWithConditions() {
        return presenceConditions();
    }

    @Override
    protected List<Predicate<SqlPredicateFragment>> startsWithIgnoreCaseConditions() {
        return presenceConditions();
    }

    @Override
    protected List<Predicate<SqlPredicateFragment>> notStartsWithConditions() {
        return notWrapper(startsWithConditions());
    }

    @Override
    protected List<Predicate<SqlPredicateFragment>> notStartsWithIgnoreCaseConditions() {
        return notWrapper(startsWithIgnoreCaseConditions());
    }

    @Override
    protected List<Predicate<SqlPredicateFragment>> endsWithConditions() {
        return presenceConditions();
    }

    @Override
    protected List<Predicate<SqlPredicateFragment>> endsWithIgnoreCaseConditions() {
        return presenceConditions();
    }

    @Override
    protected List<Predicate<SqlPredicateFragment>> notEndsWithConditions() {
        return notWrapper(endsWithConditions());
    }

    @Override
    protected List<Predicate<SqlPredicateFragment>> notEndsWithIgnoreCaseConditions() {
        return notWrapper(endsWithIgnoreCaseConditions());
    }

    @Override
    protected List<Predicate<SqlPredicateFragment>> containsConditions() {
        return presenceConditions();
    }

    @Override
    protected List<Predicate<SqlPredicateFragment>> containsIgnoreCaseConditions() {
        return presenceConditions();
    }

    @Override
    protected List<Predicate<SqlPredicateFragment>> notContainsConditions() {
        return notWrapper(containsConditions());
    }

    @Override
    protected List<Predicate<SqlPredicateFragment>> notContainsIgnoreCaseConditions() {
        return notWrapper(containsIgnoreCaseConditions());
    }

    @Override
    protected List<Predicate<SqlPredicateFragment>> isEmptyConditions() {
        return Collections.singletonList(
            sqlPredicateFragment -> IS_EMPTY_PATTERN.matcher(sqlPredicateFragment.getSql()).matches()
        );
    }

    @Override
    protected List<Predicate<SqlPredicateFragment>> isNotEmptyConditions() {
        return Collections.singletonList(
            sqlPredicateFragment -> IS_NOT_EMPTY_PATTERN.matcher(sqlPredicateFragment.getSql()).matches()
        );
    }

    protected List<Predicate<SqlPredicateFragment>> compareConditions() {
        return Collections.singletonList(
            sqlPredicateFragment -> COMPARE_PATTERN.matcher(sqlPredicateFragment.getSql()).find()
        );
    }

    protected List<Predicate<SqlPredicateFragment>> presenceConditions() {
        return Arrays.asList(
            sqlPredicateFragment -> sqlPredicateFragment.getSql().startsWith("("),
            sqlPredicateFragment -> sqlPredicateFragment.getSql().endsWith(")"),
            sqlPredicateFragment -> sqlPredicateFragment.getSql().contains("LIKE"),
            sqlPredicateFragment -> sqlPredicateFragment.getSql().contains("?"),
            sqlPredicateFragment -> sqlPredicateFragment.getSql().contains("%")
        );
    }

    private List<Predicate<SqlPredicateFragment>> notWrapper(List<Predicate<SqlPredicateFragment>> conditions) {
        List<Predicate<SqlPredicateFragment>> predicates = new ArrayList<>();
        predicates.add(sqlPredicateFragment -> sqlPredicateFragment.getSql().startsWith("(NOT"));
        predicates.add(sqlPredicateFragment -> sqlPredicateFragment.getSql().endsWith(")"));
        predicates.addAll(conditions);

        return predicates;
    }
}
