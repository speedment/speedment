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

import com.speedment.runtime.config.Column;
import com.speedment.runtime.field.comparator.FieldComparator;
import com.speedment.runtime.field.predicate.Inclusion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableSet;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 *
 * @author pemi
 */
@ExtendWith(MockitoExtension.class)
abstract class AbstractEnumFieldTest extends BaseFieldTest {

    private final Set<TestEntity.TestEnum> SAMPLES = unmodifiableSet(Stream.of(TestEntity.TestEnum.TRYGGVE, TestEntity.TestEnum.OLLE)
        .collect(toSet()));

    protected EnumField<TestEntity, String, TestEntity.TestEnum> field;
    private final RerferenceFieldTestSupport<TestEntity.TestEnum> support;
    private @Mock Column column;

    private final Function<TestEntity, TestEntity.TestEnum> getter = TestEntity::getEnum;


    public AbstractEnumFieldTest(EnumField<TestEntity, String, TestEntity.TestEnum> field) {
        this.field = requireNonNull(field);
        this.support = new RerferenceFieldTestSupport<>(field, Object::toString, TestEntity::getEnum, TestEntity::setEnum, TestEntity.TestEnum.TRYGGVE, TestEntity.TestEnum.SVEN);
    }

    @Test
    void testSupportMethods() {
        support.testAll();
    }

    @Test
    void equalIgnoreCase() {
        final List<TestEntity> expected = collect(e -> e.getEnum() != null && e.getEnum().toString().equalsIgnoreCase(TestEntity.TestEnum.GLENN.toString()));
        final List<TestEntity> result = collect(field.equalIgnoreCase(TestEntity.TestEnum.GLENN.toString().toLowerCase()));

        assertEquals(expected, result);
        final long nulls = entities.stream().filter(e -> e.getEnum() == null).count();
        assertEquals(nulls, collect(field.isNull()).size());
        assertThrows(NullPointerException.class, () ->
            collect(field.equalIgnoreCase(null))
        );

    }



    @Test
    void notEqualIgnoreCase() {
        final List<TestEntity> expected = collect(e -> getter.apply(e) != null && !getter.apply(e).toString().equalsIgnoreCase("abcdef"));
        final List<TestEntity> result = collect(field.notEqualIgnoreCase("abcdef"));

        assertEquals(expected, result);
        assertThrows(NullPointerException.class, () ->
            collect(field.notEqualIgnoreCase(null))
        );
    }

    @Test
    void startsWith() {

        //assertTrue(STARTS_WITH_PREDICATE.test("ab", "abc"));
        final List<TestEntity> expected = collect(e -> getter.apply(e) != null && getter.apply(e).toString().startsWith("abc"));
        //final List<Entity> result = collect(e -> getter.apply(e) != null && STARTS_WITH_PREDICATE.test(getter.apply(e), "abc"));
        final List<TestEntity> result = collect(field.startsWith("abc"));

        printList("startswith expected", expected);
        printList("startswith result", result);

        assertEquals(expected, result);
        assertThrows(NullPointerException.class, () ->
                collect(field.startsWith(null))
        );
    }

    @Test
    void startsWithIgnoreCase() {
        final List<TestEntity> expected = collect(e -> getter.apply(e) != null && getter.apply(e).toString().toLowerCase().startsWith("abc"));
        final List<TestEntity> result = collect(field.startsWithIgnoreCase("abc"));
        assertEquals(expected, result);
        assertThrows(NullPointerException.class, () ->
            collect(field.startsWithIgnoreCase(null))
        );
    }

    @Test
    void endWithIgnoreCase() {
        final List<TestEntity> expected = collect(e -> getter.apply(e) != null && getter.apply(e).toString().toLowerCase().endsWith("e"));
        final List<TestEntity> result = collect(field.endsWithIgnoreCase("e"));
        assertEquals(expected, result);
        assertThrows(NullPointerException.class, () ->
            collect(field.endsWithIgnoreCase(null))
        );
    }

    @Test
    void endsWith() {
        
        final List<TestEntity> expected = collect(e -> getter.apply(e) != null && getter.apply(e).toString().endsWith("E"));
        final List<TestEntity> result = collect(field.endsWith("E"));

        printList("endswith expected", expected);
        printList("endswith result", result);

        assertEquals(expected, result);
        assertThrows(NullPointerException.class, () ->
            collect(field.endsWith(null))
        );
    }

    @Test
    void contains()  {

        final List<TestEntity> expected = collect(e -> getter.apply(e) != null && getter.apply(e).toString().contains("y"));
        final List<TestEntity> result = collect(field.contains("y"));

        printList("contains expected", expected);
        printList("contains result", result);

        assertEquals(expected, result);
        assertThrows(NullPointerException.class, () ->
                collect(field.contains(null))
        );
    }

    @Test
    void isEmpty() {
        final List<TestEntity> expected = collect(e -> getter.apply(e) != null && getter.apply(e).toString().isEmpty());
        final List<TestEntity> result = collect(field.isEmpty());
        assertEquals(expected, result);
    }

    @Test
    void isNotEmpty()  {
        final List<TestEntity> expected = collect(e -> getter.apply(e) != null && !getter.apply(e).toString().isEmpty());
        final List<TestEntity> result = collect(field.isNotEmpty());
        assertEquals(expected, result);
    }

    @Test
    void in() {
        final List<TestEntity> expected = collect(e -> SAMPLES.contains(getter.apply(e)));
        final List<TestEntity> result = collect(field.in(SAMPLES));

        assertEquals(expected, result);
        assertThrows(NullPointerException.class, () ->
            collect(field.in((Collection<TestEntity.TestEnum>) null))
        );
    }

    @Test
    void notIn() {
        final List<TestEntity> expected = collect(e -> getter.apply(e) != null && !SAMPLES.contains(getter.apply(e)));
        final List<TestEntity> result = collect(field.notIn(SAMPLES));

        assertEquals(expected, result);
        assertThrows(NullPointerException.class, () ->
            collect(field.notIn((Collection<TestEntity.TestEnum>) null))
        );
    }

    // Todo: Rework the between test below to do something useful

    @Test
    void betweenIE() {
        final List<TestEntity> expected = collect(e -> getter.apply(e) != null && "b".compareTo(getter.apply(e).toString()) <= 0 && "f".compareTo(getter.apply(e).toString()) > 0);
        final List<TestEntity> result = collect(field.between("b", "f"));
        assertEquals(expected, result);
        assertThrows(NullPointerException.class, () ->
            collect(field.between(null, "A", Inclusion.START_EXCLUSIVE_END_EXCLUSIVE))
        );        assertThrows(NullPointerException.class, () ->
            collect(field.between("A", null, Inclusion.START_EXCLUSIVE_END_EXCLUSIVE))
        );        assertThrows(NullPointerException.class, () ->
            collect(field.between("A", "A", null))
        );
    }

    @Test
    void betweenII() {
        final List<TestEntity> expected = collect(e -> getter.apply(e) != null && "b".compareTo(getter.apply(e).toString()) <= 0 && "f".compareTo(getter.apply(e).toString()) >= 0);
        final List<TestEntity> result = collect(field.between("b", "f", Inclusion.START_INCLUSIVE_END_INCLUSIVE));
        assertEquals(expected, result);
        assertThrows(NullPointerException.class, () ->
            collect(field.in((Collection<TestEntity.TestEnum>) null))
        );
    }

    @Test
    void betweenEI() {
        final List<TestEntity> expected = collect(e -> getter.apply(e) != null && "b".compareTo(getter.apply(e).toString()) < 0 && "f".compareTo(getter.apply(e).toString()) >= 0);
        final List<TestEntity> result = collect(field.between("b", "f", Inclusion.START_EXCLUSIVE_END_INCLUSIVE));
        assertEquals(expected, result);
        assertThrows(NullPointerException.class, () ->
            collect(field.in((Collection<TestEntity.TestEnum>) null))
        );
    }

    @Test
    void betweenEE() {
        final List<TestEntity> expected = collect(e -> getter.apply(e) != null && "b".compareTo(getter.apply(e).toString()) < 0 && "f".compareTo(getter.apply(e).toString()) > 0);
        final List<TestEntity> result = collect(field.between("b", "f", Inclusion.START_EXCLUSIVE_END_EXCLUSIVE));
        assertEquals(expected, result);
        assertThrows(NullPointerException.class, () ->
            collect(field.in((Collection<TestEntity.TestEnum>) null))
        );
    }

    @Test
    void toStringTest() {
        assertNotNull(field.toString());
    }

    @Test
    void comparator() {
        comparator(false);
    }

    void comparator(final boolean reversed) {
        final List<TestEntity> list = Arrays.asList(new TestEntityImpl(3, "C", TestEntity.TestEnum.GLENN), new TestEntityImpl(2, "A", TestEntity.TestEnum.OLLE), new TestEntityImpl(2, "B", TestEntity.TestEnum.SVEN));

        final Comparator<TestEntity> comparatorExpected = Comparator.comparing(TestEntity::getName);
        final List<TestEntity> expected = new ArrayList<>(list);
        expected.sort(reversed ? comparatorExpected.reversed() : comparatorExpected);

        // final FieldComparator<TestEntity> comparator = reversed ? field.reversed() : field.comparator();
        final FieldComparator<TestEntity> comparator =  field.comparator();

        final List<TestEntity> actual =  new ArrayList<>(list);
        actual.sort(comparator);

        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings({"raw", "unchecked"})
    void typemapper() {
        //when(column.findDatabaseType()).thenReturn((Class) String.class);
        final Type fieldType = field.typeMapper().getJavaType(column);
        assertEquals(TestEntity.TestEnum.class, fieldType);
    }

    @Test
    void isUnique() {
        assertFalse(field.isUnique());
    }

    @Test
    void tableAlias() {
        assertNotNull(field.tableAlias());
    }

    @Test
    void setTableAlias() {
        final String name = "tryggve";
        assertEquals(name, field.tableAlias(name).tableAlias());
        assertEquals(field.identifier().getColumnId(), field.tableAlias(name).identifier().getColumnId());
    }



}
