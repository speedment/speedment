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
package com.speedment.runtime.field;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static com.speedment.runtime.field.TestEntity.ID;
import static com.speedment.runtime.field.TestEntity.NAME;
import static com.speedment.runtime.field.predicate.Inclusion.*;
import static java.util.Comparator.*;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author pemi
 */
final class ComparableFieldTest extends BaseFieldTest {

    @Test
    void testReferenceFieldComparatorNullFieldsFirst() {
        final List<TestEntity> result = entities.stream().sorted(NAME.comparatorNullFieldsFirst().thenComparing(ID.comparator())).collect(toList());
        final List<TestEntity> expected = entities.stream()
            .sorted(
                comparing(TestEntity::getName, nullsFirst(String::compareTo))
                    .thenComparing(comparing(TestEntity::getId, nullsFirst(Integer::compareTo)))
            )
            .collect(toList());

        assertEquals(expected, result);
    }

    @Test
    void testReferenceFieldComparatorNullFieldsLast() {
        final List<TestEntity> result = entities.stream().sorted(NAME.comparator().thenComparing(ID.comparator())).collect(toList());
        final List<TestEntity> expected = entities.stream()
            .sorted(
                comparing(TestEntity::getName, nullsLast(String::compareTo))
                    .thenComparing(comparing(TestEntity::getId, nullsLast(Integer::compareTo)))
            )
            .collect(toList());

        assertEquals(expected, result);
    }

    @Test
    void testIsNull() {
        assertEquals(
            collect(e -> e.getName() == null).size(),
            collect(NAME.isNull()).size()
        );
    }

    @Test
    void testIsNotNull() {
        assertEquals(
            collect(e -> e.getName() != null).size(),
            collect(NAME.isNotNull()).size()
        );
    }

    @Test
    void testEqual() {
        assertEquals(
            collect(e -> "a".equals(e.getName())).size(),
            collect(NAME.equal("a")).size()
        );
    }

    @Test
    void testNotEqual() {
        assertEquals(
            collect(e -> e.getName() != null && !"a".equals(e.getName())).size(),
            collect(NAME.notEqual("a")).size()
        );
    }

    @Test
    void lessThan() {
        assertEquals(
            collect(e -> e.getName() != null && "f".compareTo(e.getName()) > 0),
            collect(NAME.lessThan("f"))
        );
    }

    @Test
    void lessOrEqual() {
        assertEquals(
            collect(e -> e.getName() != null && "f".compareTo(e.getName()) >= 0),
            collect(NAME.lessOrEqual("f"))
        );
    }

    @Test
    void greaterThan() {
        assertEquals(
            collect(e -> e.getName() != null && "f".compareTo(e.getName()) < 0),
            collect(NAME.greaterThan("f"))
        );
    }

    @Test
    void greaterOrEqual() {
        assertEquals(
            collect(e -> e.getName() != null && "f".compareTo(e.getName()) <= 0),
            collect(NAME.greaterOrEqual("f"))
        );
    }

    @Test
    void between2Arg() {

        final List<TestEntity> expected = collect(e -> e.getId() != null && e.getId() >= 2 && e.getId() < 6);
        final List<TestEntity> result = collect(ID.between(2, 6));

        assertEquals(
            expected,
            result
        );
        
        // These tests appear to be errenous. If the start is inclusive and end is inclusive, 
        // passing the same argument as both start and stop will return 0 hits. It is also not
        // allowed to set either start or stop to null.
        
//        assertEquals(0, collect(ID.between(2, null)).size());
//        assertEquals(0, collect(ID.between(null, 6)).size());
        assertEquals(0, collect(ID.between(6, 2)).size());
        assertEquals(0, collect(ID.between(2, 2)).size()); 

    }

    @Test
    void between3ArgInclIncl() {

        final List<TestEntity> expected = collect(e -> e.getId() != null && e.getId() >= 2 && e.getId() <= 6);
        final List<TestEntity> result = collect(ID.between(2, 6, START_INCLUSIVE_END_INCLUSIVE));

        assertEquals(
            expected,
            result
        );

        assertEquals(0, collect(ID.between(6, 2, START_INCLUSIVE_END_INCLUSIVE)).size());
        assertEquals(1, collect(ID.between(2, 2, START_INCLUSIVE_END_INCLUSIVE)).size());
    }

    @Test
    void between3ArgExclIncl() {

        final List<TestEntity> expected = collect(e -> e.getId() != null && e.getId() > 2 && e.getId() <= 6);
        final List<TestEntity> result = collect(ID.between(2, 6, START_EXCLUSIVE_END_INCLUSIVE));

        assertEquals(
            expected,
            result
        );

        assertEquals(0, collect(ID.between(6, 2, START_EXCLUSIVE_END_INCLUSIVE)).size());
        assertEquals(0, collect(ID.between(2, 2, START_EXCLUSIVE_END_INCLUSIVE)).size());
    }

    @Test
    void between3ArgExclExcl() {

        final List<TestEntity> expected = collect(e -> e.getId() != null && e.getId() > 2 && e.getId() < 6);
        final List<TestEntity> result = collect(ID.between(2, 6, START_EXCLUSIVE_END_EXCLUSIVE));

        assertEquals(
            expected,
            result
        );

        assertEquals(0, collect(ID.between(6, 2, START_EXCLUSIVE_END_EXCLUSIVE)).size());
        assertEquals(0, collect(ID.between(2, 2, START_EXCLUSIVE_END_EXCLUSIVE)).size());
    }

    @Test
    void in() {

        final Integer[] ints = {2, 3, 5, 7, 11, 13, 16};
        final Set<Integer> intSet = Stream.of(ints).collect(toSet());
        final List<TestEntity> expected = collect(e -> intSet.contains(e.getId()));

        assertEquals(expected, collect(ID.in(intSet)));
        assertEquals(expected, collect(ID.in(ints)));
        
        assertEquals(0, collect(ID.in()).size());
        assertEquals(1, collect(ID.in(1)).size());
        assertEquals(0, collect(ID.in((Integer) null)).size());
    }

}
