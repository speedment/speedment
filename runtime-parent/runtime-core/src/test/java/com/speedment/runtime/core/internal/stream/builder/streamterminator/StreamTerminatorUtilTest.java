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
package com.speedment.runtime.core.internal.stream.builder.streamterminator;

import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.internal.stream.builder.streamterminator.StreamTerminatorUtil.RenderResult;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.IntField;
import com.speedment.runtime.field.predicate.SpeedmentPredicate;
import com.speedment.runtime.test_support.MockDbmsType;
import com.speedment.runtime.typemapper.TypeMapper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.speedment.runtime.core.internal.stream.builder.streamterminator.StreamTerminatorUtil.isContainingOnlyFieldPredicate;
import static com.speedment.runtime.core.internal.stream.builder.streamterminator.StreamTerminatorUtil.renderSqlWhere;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Per Minborg
 */
final class StreamTerminatorUtilTest {

    private static final DbmsType DBMS_TYPE = new MockDbmsType();
    private static final Function<Field<Person>, String> COLUMN_NAMER = f -> f.identifier().getColumnId();
    private static final Function<Field<Person>, Class<?>> COLUMN_TYPE_FUNCTION = f -> Integer.class;
    private static final SpeedmentPredicate<Person> ID_GT_0 = Person.ID.greaterThan(0); //new IntGreaterThanPredicate<>(Person.ID, 0);
    private static final SpeedmentPredicate<Person> ID_GT_1 = Person.ID.greaterThan(1); //new IntGreaterThanPredicate<>(Person.ID, 1);
    private static final SpeedmentPredicate<Person> ID_LE_0 = ID_GT_0.negate();
    private static final SpeedmentPredicate<Person> ID_LE_1 = ID_GT_1.negate();
    private static final SpeedmentPredicate<Person> ID_EQ_0 = Person.ID.equal(0); //new IntEqualPredicate<>(Person.ID, 0);
    private static final SpeedmentPredicate<Person> ID_EQ_1 = Person.ID.equal(1); //new IntEqualPredicate<>(Person.ID, 1);

    private static final SpeedmentPredicate<Person> AGE_GT_2 = Person.AGE.greaterThan(2); //new IntGreaterThanPredicate<>(Person.AGE, 2);
    private static final SpeedmentPredicate<Person> AGE_GT_3 = Person.AGE.greaterThan(3); //new IntGreaterThanPredicate<>(Person.AGE, 3);
    private static final SpeedmentPredicate<Person> AGE_LE_2 = AGE_GT_2.negate();
    private static final SpeedmentPredicate<Person> AGE_LE_3 = AGE_GT_3.negate();
    private static final SpeedmentPredicate<Person> AGE_EQ_2 = Person.AGE.equal(2); //new IntEqualPredicate<>(Person.AGE, 2);
    private static final SpeedmentPredicate<Person> AGE_EQ_3 = Person.AGE.equal(3); //new IntEqualPredicate<>(Person.AGE, 3);

    private static final SpeedmentPredicate<Person> ID_BETWEEN_4_8 = Person.AGE.between(4, 8); //new IntBetweenPredicate<>(Person.AGE, 4, 8, Inclusion.START_INCLUSIVE_END_EXCLUSIVE);
    private static final SpeedmentPredicate<Person> AGE_BETWEEN_10_12 = Person.AGE.between(10, 12); //new IntBetweenPredicate<>(Person.AGE, 10, 12, Inclusion.START_INCLUSIVE_END_EXCLUSIVE);

    private static final SpeedmentPredicate<Person> ID_GT_0_AND_AGE_EQ_2 = ID_GT_0.and(AGE_EQ_2);
    private static final SpeedmentPredicate<Person> ID_GT_0_OR_AGE_EQ_2 = ID_GT_0.or(AGE_EQ_2);
    private static final Predicate<Person> COMPLEX = ID_GT_0.and(AGE_EQ_2).or(ID_GT_1.and(AGE_EQ_3));

    @Test
    void testIsContainingOnlyFieldPredicateLambda() {
        assertFalse(isContainingOnlyFieldPredicate(p -> true));
    }

    @Test
    void testIsContainingOnlyFieldPredicateSimple() {
        assertTrue(isContainingOnlyFieldPredicate(ID_GT_0));
    }

    @Test
    void testIsContainingOnlyFieldPredicateAnd() {
        assertTrue(isContainingOnlyFieldPredicate(ID_GT_0_AND_AGE_EQ_2));
    }

    @Test
    void testIsContainingOnlyFieldPredicateOr() {
        assertTrue(isContainingOnlyFieldPredicate(ID_GT_0_AND_AGE_EQ_2));
    }

    @Test
    void testIsContainingOnlyFieldPredicateComplex() {
        assertTrue(isContainingOnlyFieldPredicate(COMPLEX));
    }

    @Test
    void testIsContainingOnlyFieldPredicateComplexAndPolluted() {
        assertFalse(isContainingOnlyFieldPredicate(COMPLEX.and(person -> true)));
    }

    @Test
    void testBasicRenderSqlWhere() {
        testRender(singletonList(ID_GT_0), rr -> {
            System.out.println(rr);
            assertEquals("(id > ?)", rr.getSql());
            assertEquals(singletonList(0), rr.getValues());
        });
    }
    
    @Test
    void testRenderSqlWhereNot() {
        testRender(singletonList(ID_GT_0_AND_AGE_EQ_2.negate()), rr -> {
            System.out.println(rr);
            assertEquals("((id <= ?) OR (NOT (age = ?)))", rr.getSql());
            assertEquals(Arrays.asList(0, 2), rr.getValues());
        });
    }

    @Test
    void test2RenderSqlWhere() {
        testRender(Arrays.asList(ID_GT_0, AGE_EQ_2), rr -> {
            System.out.println(rr);
            assertEquals("(id > ?) AND (age = ?)", rr.getSql());
            assertEquals(Arrays.asList(0, 2), rr.getValues());
        });
    }

    @Test
    void testAndComnbinedRenderSqlWhere() {
        testRender(singletonList(ID_GT_0_AND_AGE_EQ_2), rr -> {
            System.out.println(rr);
            assertEquals("((id > ?) AND (age = ?))", rr.getSql());
            assertEquals(Arrays.asList(0, 2), rr.getValues());
        });
    }

    @Test
    void testOrComnbinedRenderSqlWhere() {
        testRender(singletonList(ID_GT_0_OR_AGE_EQ_2), rr -> {
            System.out.println(rr);
            assertEquals("((id > ?) OR (age = ?))", rr.getSql());
            assertEquals(Arrays.asList(0, 2), rr.getValues());
        });
    }

    @Test
    void testComplexComnbinedRenderSqlWhere() {
        testRender(singletonList(COMPLEX), rr -> {
            System.out.println(rr);
            assertEquals("(((id > ?) AND (age = ?)) OR ((id > ?) AND (age = ?)))", rr.getSql());
            assertEquals(Arrays.asList(0, 2, 1, 3), rr.getValues());
        });
    }

    @Test
    void testComplexPollutedComnbinedRenderSqlWhere() {
        try {
            testRender(singletonList(COMPLEX.and(p -> true)), rr -> {
            });
            fail("method did not throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private void testRender(List<Predicate<Person>> predicates, Consumer<RenderResult> checker) {
        final RenderResult rr = renderSqlWhere(DBMS_TYPE, COLUMN_NAMER, COLUMN_TYPE_FUNCTION, predicates);
        checker.accept(rr);
    }

    private static class Person {

        private int id;
        private int age;
        private static final IntField<Person, Integer> ID
            = IntField.create(
                Identifier.ID,
                Person::getId,
                Person::setId,
                TypeMapper.primitive(),
                true
            );

        private static final IntField<Person, Integer> AGE
            = IntField.create(
                Identifier.AGE,
                Person::getId,
                Person::setId,
                TypeMapper.primitive(),
                true
            );

        int getId() {
            return id;
        }

        Person setId(int id) {
            this.id = id;
            return this;
        }

        int getAge() {
            return age;
        }

        Person setAge(int age) {
            this.age = age;
            return this;
        }

        enum Identifier implements ColumnIdentifier<Person> {
            ID("id"),
            AGE("age");

            private final String columnName;
            private final TableIdentifier<Person> tableIdentifier;

            Identifier(String columnName) {
                this.columnName = columnName;
                this.tableIdentifier = TableIdentifier.of(getDbmsId(),
                    getSchemaId(),
                    getTableId());
            }

            @Override
            public String getDbmsId() {
                return "db0";
            }

            @Override
            public String getSchemaId() {
                return "SPEEDMENT";
            }

            @Override
            public String getTableId() {
                return "PERSON";
            }

            @Override
            public String getColumnId() {
                return this.columnName;
            }

            @Override
            public TableIdentifier<Person> asTableIdentifier() {
                return this.tableIdentifier;
            }

        }

    }

}
