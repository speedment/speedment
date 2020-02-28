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

import static com.speedment.runtime.field.predicate.PredicateType.ALWAYS_FALSE;
import static com.speedment.runtime.field.predicate.PredicateType.ALWAYS_TRUE;
import static com.speedment.runtime.field.predicate.PredicateType.BETWEEN;
import static com.speedment.runtime.field.predicate.PredicateType.CONTAINS;
import static com.speedment.runtime.field.predicate.PredicateType.CONTAINS_IGNORE_CASE;
import static com.speedment.runtime.field.predicate.PredicateType.ENDS_WITH;
import static com.speedment.runtime.field.predicate.PredicateType.ENDS_WITH_IGNORE_CASE;
import static com.speedment.runtime.field.predicate.PredicateType.EQUAL;
import static com.speedment.runtime.field.predicate.PredicateType.EQUAL_IGNORE_CASE;
import static com.speedment.runtime.field.predicate.PredicateType.GREATER_OR_EQUAL;
import static com.speedment.runtime.field.predicate.PredicateType.GREATER_THAN;
import static com.speedment.runtime.field.predicate.PredicateType.IN;
import static com.speedment.runtime.field.predicate.PredicateType.IS_EMPTY;
import static com.speedment.runtime.field.predicate.PredicateType.IS_NOT_EMPTY;
import static com.speedment.runtime.field.predicate.PredicateType.IS_NOT_NULL;
import static com.speedment.runtime.field.predicate.PredicateType.IS_NULL;
import static com.speedment.runtime.field.predicate.PredicateType.LESS_OR_EQUAL;
import static com.speedment.runtime.field.predicate.PredicateType.LESS_THAN;
import static com.speedment.runtime.field.predicate.PredicateType.NOT_BETWEEN;
import static com.speedment.runtime.field.predicate.PredicateType.NOT_CONTAINS;
import static com.speedment.runtime.field.predicate.PredicateType.NOT_CONTAINS_IGNORE_CASE;
import static com.speedment.runtime.field.predicate.PredicateType.NOT_ENDS_WITH;
import static com.speedment.runtime.field.predicate.PredicateType.NOT_ENDS_WITH_IGNORE_CASE;
import static com.speedment.runtime.field.predicate.PredicateType.NOT_EQUAL;
import static com.speedment.runtime.field.predicate.PredicateType.NOT_EQUAL_IGNORE_CASE;
import static com.speedment.runtime.field.predicate.PredicateType.NOT_IN;
import static com.speedment.runtime.field.predicate.PredicateType.NOT_STARTS_WITH;
import static com.speedment.runtime.field.predicate.PredicateType.NOT_STARTS_WITH_IGNORE_CASE;
import static com.speedment.runtime.field.predicate.PredicateType.STARTS_WITH;
import static com.speedment.runtime.field.predicate.PredicateType.STARTS_WITH_IGNORE_CASE;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.speedment.runtime.core.db.FieldPredicateView;
import com.speedment.runtime.core.db.SqlPredicateFragment;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.predicate.FieldPredicate;
import com.speedment.runtime.field.predicate.Inclusion;
import com.speedment.runtime.field.predicate.PredicateType;
import com.speedment.test.connector.type.TestReadyFieldPredicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.mockito.internal.util.collections.Sets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author Mislav Milicevic
 * @since 3.2.9
 */
public abstract class AbstractFieldPredicateViewMixin implements FieldPredicateViewMixin {

    private final Map<PredicateType, List<Predicate<SqlPredicateFragment>>> transformConditions = new HashMap<>();
    private final Map<PredicateType, List<Predicate<SqlPredicateFragment>>> collectionTransformConditions = new HashMap<>();
    
    @BeforeEach
    void registerTransformConditions() {
        transformConditions.put(ALWAYS_TRUE, alwaysTrueConditions());
        transformConditions.put(ALWAYS_FALSE, alwaysFalseConditions());
        transformConditions.put(IS_NULL, isNullConditions());
        transformConditions.put(IS_NOT_NULL, isNotNullConditions());
        transformConditions.put(IS_EMPTY, isEmptyConditions());
        transformConditions.put(IS_NOT_EMPTY, isNotEmptyConditions());
        transformConditions.put(EQUAL, equalsConditions());
        transformConditions.put(NOT_EQUAL, notEqualsConditions());
        transformConditions.put(GREATER_THAN, greaterThanConditions());
        transformConditions.put(GREATER_OR_EQUAL, greaterOrEqualConditions());
        transformConditions.put(LESS_THAN, lessThanConditions());
        transformConditions.put(LESS_OR_EQUAL, lessOrEqualConditions());
        transformConditions.put(BETWEEN, betweenConditions());
        transformConditions.put(NOT_BETWEEN, notBetweenConditions());
        transformConditions.put(EQUAL_IGNORE_CASE, equalsIgnoreCaseConditions());
        transformConditions.put(NOT_EQUAL_IGNORE_CASE, notEqualsIgnoreCaseConditions());
        transformConditions.put(STARTS_WITH, startsWithConditions());
        transformConditions.put(STARTS_WITH_IGNORE_CASE, startsWithIgnoreCaseConditions());
        transformConditions.put(NOT_STARTS_WITH, notStartsWithConditions());
        transformConditions.put(NOT_STARTS_WITH_IGNORE_CASE, notStartsWithIgnoreCaseConditions());
        transformConditions.put(ENDS_WITH, endsWithConditions());
        transformConditions.put(ENDS_WITH_IGNORE_CASE, endsWithIgnoreCaseConditions());
        transformConditions.put(NOT_ENDS_WITH, notEndsWithConditions());
        transformConditions.put(NOT_ENDS_WITH_IGNORE_CASE, notEndsWithIgnoreCaseConditions());
        transformConditions.put(CONTAINS, containsConditions());
        transformConditions.put(CONTAINS_IGNORE_CASE, containsIgnoreCaseConditions());
        transformConditions.put(NOT_CONTAINS, notContainsConditions());
        transformConditions.put(NOT_CONTAINS_IGNORE_CASE, notContainsIgnoreCaseConditions());

        collectionTransformConditions.put(IN, inConditions());
        collectionTransformConditions.put(NOT_IN, notInConditions());
    }
    
    @Override
    @TestFactory
    public Stream<DynamicTest> transformTests() {
        final FieldPredicateView fieldPredicateView = getFieldPredicateViewInstance();

        final Stream<DynamicTest> transformTests = transformConditions.entrySet().stream().map(entry ->
                dynamicTest(entry.getKey().toString(), () -> {
            final Function<Field<String>, Class<?>> dbType = field -> String.class;
            final Function<Field<String>, String> columnNamer = field -> "column";

            final PredicateType predicateType = entry.getKey();
            final List<Predicate<SqlPredicateFragment>> conditions = entry.getValue();

            final FieldPredicate<String> fieldPredicate = mockFieldPredicate(predicateType, "value");

            final SqlPredicateFragment fragment = fieldPredicateView.transform(columnNamer, dbType, fieldPredicate);
            conditions.forEach(condition -> assertTrue(condition.test(fragment)));
        }));

        final Stream<DynamicTest> collectionTransformTests = collectionTransformConditions.entrySet().stream().map(entry ->
                dynamicTest(entry.getKey().toString(), () -> {
            final Function<Field<Set<String>>, Class<?>> dbType = field -> Set.class;
            final Function<Field<Set<String>>, String> columnNamer = field -> "column";

            final PredicateType predicateType = entry.getKey();
            final List<Predicate<SqlPredicateFragment>> conditions = entry.getValue();

            Stream.of(
                    Sets.newSet(),
                    Sets.newSet("value"),
                    Sets.newSet("value", "anotherValue")
            ).forEach(set -> {
                @SuppressWarnings("unchecked")
                final FieldPredicate<Set<String>> fieldPredicate = mockFieldPredicate(predicateType, (Set<String>) set);

                final SqlPredicateFragment fragment = fieldPredicateView.transform(columnNamer, dbType, fieldPredicate);
                conditions.forEach(condition -> assertTrue(condition.test(fragment)));
            });
        }));

        return Stream.concat(transformTests, collectionTransformTests);
    }

    /**
     * Returns an instance of the {@link com.speedment.runtime.core.db.FieldPredicateView}
     * that is being tested.
     *
     * @return instance of the {@link com.speedment.runtime.core.db.FieldPredicateView}
     */
    protected abstract FieldPredicateView getFieldPredicateViewInstance();

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

    @SuppressWarnings("unchecked")
    private <ENTITY> FieldPredicate<ENTITY> mockFieldPredicate(PredicateType predicateType, ENTITY value) {
        final TestReadyFieldPredicate<ENTITY> predicate = (TestReadyFieldPredicate<ENTITY>) mock(TestReadyFieldPredicate.class);

        when(predicate.getPredicateType()).thenReturn(predicateType);
        when(predicate.get(0)).thenReturn(value);
        when(predicate.getInclusion()).thenReturn(Inclusion.START_EXCLUSIVE_END_EXCLUSIVE);

        return predicate;
    }
}
