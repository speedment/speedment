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
package com.speedment.internal.field;

import static com.speedment.field2.Inclusion.START_EXCLUSIVE_END_EXCLUSIVE;
import static com.speedment.field2.Inclusion.START_EXCLUSIVE_END_INCLUSIVE;
import static com.speedment.field2.Inclusion.START_INCLUSIVE_END_INCLUSIVE;
import static com.speedment.internal.field.Entity.ID;
import static com.speedment.internal.field.Entity.NAME;
import static java.util.Comparator.nullsFirst;
import java.util.List;
import static java.util.stream.Collectors.toList;
import org.junit.Test;
import static java.util.Comparator.nullsLast;
import static org.junit.Assert.assertEquals;
import static java.util.Comparator.comparing;
import java.util.Set;
import static java.util.stream.Collectors.toSet;
import java.util.stream.Stream;
import static org.junit.Assert.assertEquals;
import static java.util.Comparator.comparing;

/**
 *
 * @author pemi
 */
public class ComparableFieldTest extends BaseFieldTest {

    @Test
    public void testReferenceFieldComparatorNullFieldsFirst() throws Exception {
        final List<Entity> result = entities.stream().sorted(NAME.comparatorNullFieldsFirst().thenComparing(ID.comparator())).collect(toList());
        final List<Entity> expected = entities.stream()
            .sorted(
                comparing(Entity::getName, nullsFirst(String::compareTo))
                .thenComparing(comparing(Entity::getId, nullsFirst(Integer::compareTo)))
            )
            .collect(toList());

        assertEquals(expected, result);
    }

    @Test
    public void testReferenceFieldComparatorNullFieldsLast() throws Exception {
        final List<Entity> result = entities.stream().sorted(NAME.comparatorNullFieldsLast().thenComparing(ID.comparator())).collect(toList());
        final List<Entity> expected = entities.stream()
            .sorted(
                comparing(Entity::getName, nullsLast(String::compareTo))
                .thenComparing(comparing(Entity::getId, nullsLast(Integer::compareTo)))
            )
            .collect(toList());

        assertEquals(expected, result);
    }

    @Test
    public void testEqual() throws Exception {
        assertEquals(collect(e -> "a".equals(e.getName())).size(), collect(NAME.equal("a")).size());
        assertEquals(collect(e -> e.getName() == null).size(), collect(NAME.equal(null)).size());
    }

    @Test
    public void testNotEqual() throws Exception {
        assertEquals(collect(e -> !"a".equals(e.getName())).size(), collect(NAME.notEqual("a")).size());
        assertEquals(collect(e -> e.getName() != null).size(), collect(NAME.notEqual(null)).size());
    }

    @Test
    public void lessThan() throws Exception {
        assertEquals(
            collect(e -> e.getName() != null && "f".compareTo(e.getName()) > 0),
            collect(NAME.lessThan("f"))
        );
        assertEquals(0, collect(NAME.lessThan(null)).size());
    }

    @Test
    public void lessOrEqual() throws Exception {
        assertEquals(
            collect(e -> e.getName() != null && "f".compareTo(e.getName()) >= 0),
            collect(NAME.lessOrEqual("f"))
        );
        assertEquals(collect(e -> e.getName() == null).size(), collect(NAME.lessOrEqual(null)).size());
    }

    @Test
    public void greaterThan() throws Exception {
        assertEquals(
            collect(e -> e.getName() != null && "f".compareTo(e.getName()) < 0),
            collect(NAME.greaterThan("f"))
        );
        assertEquals(0, collect(NAME.lessThan(null)).size());
    }

    @Test
    public void greaterOrEqual() throws Exception {
        assertEquals(
            collect(e -> e.getName() != null && "f".compareTo(e.getName()) <= 0),
            collect(NAME.greaterOrEqual("f"))
        );
        assertEquals(collect(e -> e.getName() == null).size(), collect(NAME.greaterOrEqual(null)).size());
    }

    @Test
    public void between2Arg() throws Exception {

        final List<Entity> expected = collect(e -> e.getId() != null && e.getId() >= 2 && e.getId() < 6);
        final List<Entity> result = collect(ID.between(2, 6));

        assertEquals(
            expected,
            result
        );
        assertEquals(0, collect(ID.between(2, null)).size());
        assertEquals(0, collect(ID.between(null, 6)).size());
        assertEquals(0, collect(ID.between(6, 2)).size());
        assertEquals(1, collect(ID.between(2, 2)).size());

    }

    @Test
    public void between3ArgInclIncl() throws Exception {

        final List<Entity> expected = collect(e -> e.getId() != null && e.getId() >= 2 && e.getId() <= 6);
        final List<Entity> result = collect(ID.between(2, 6, START_INCLUSIVE_END_INCLUSIVE));

        assertEquals(
            expected,
            result
        );
        assertEquals(0, collect(ID.between(2, null, START_INCLUSIVE_END_INCLUSIVE)).size());
        assertEquals(0, collect(ID.between(null, 6, START_INCLUSIVE_END_INCLUSIVE)).size());
        assertEquals(0, collect(ID.between(6, 2, START_INCLUSIVE_END_INCLUSIVE)).size());
        assertEquals(1, collect(ID.between(2, 2, START_INCLUSIVE_END_INCLUSIVE)).size());

    }

    @Test
    public void between3ArgExclIncl() throws Exception {

        final List<Entity> expected = collect(e -> e.getId() != null && e.getId() > 2 && e.getId() <= 6);
        final List<Entity> result = collect(ID.between(2, 6, START_EXCLUSIVE_END_INCLUSIVE));

        assertEquals(
            expected,
            result
        );
        assertEquals(0, collect(ID.between(2, null, START_EXCLUSIVE_END_INCLUSIVE)).size());
        assertEquals(0, collect(ID.between(null, 6, START_EXCLUSIVE_END_INCLUSIVE)).size());
        assertEquals(0, collect(ID.between(6, 2, START_EXCLUSIVE_END_INCLUSIVE)).size());
        assertEquals(1, collect(ID.between(2, 2, START_EXCLUSIVE_END_INCLUSIVE)).size());

    }

    @Test
    public void between3ArgExclExcl() throws Exception {

        final List<Entity> expected = collect(e -> e.getId() != null && e.getId() > 2 && e.getId() < 6);
        final List<Entity> result = collect(ID.between(2, 6, START_EXCLUSIVE_END_EXCLUSIVE));

        System.out.println("Expected");
        expected.forEach(System.out::println);
        System.out.println("Result");
        result.forEach(System.out::println);

        assertEquals(
            expected,
            result
        );
        assertEquals(0, collect(ID.between(2, null, START_EXCLUSIVE_END_EXCLUSIVE)).size());
        assertEquals(0, collect(ID.between(null, 6, START_EXCLUSIVE_END_EXCLUSIVE)).size());
        assertEquals(0, collect(ID.between(6, 2, START_EXCLUSIVE_END_EXCLUSIVE)).size());
        assertEquals(0, collect(ID.between(2, 2, START_EXCLUSIVE_END_EXCLUSIVE)).size());
    }

    @Test
    public void in() throws Exception {

        final Integer[] ints = {2, 3, 5, 7, 11, 13, 16};
        final Set<Integer> intSet = Stream.of(ints).collect(toSet());
        final List<Entity> expected = collect(e -> intSet.contains(e.getId()));

        assertEquals(expected, collect(ID.in(intSet)));
        assertEquals(expected, collect(ID.in(ints)));
        
        assertEquals(0, collect(ID.in()).size());
        assertEquals(1, collect(ID.in(1)).size());
        assertEquals(0, collect(ID.in((Integer) null)).size());
    }

}
