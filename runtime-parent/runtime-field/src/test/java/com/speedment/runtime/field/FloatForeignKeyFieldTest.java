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

import com.speedment.common.annotation.GeneratedCode;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.field.comparator.FloatFieldComparator;
import com.speedment.runtime.field.comparator.NullOrder;
import com.speedment.runtime.field.predicate.Inclusion;
import com.speedment.runtime.typemapper.TypeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.*;

/**
 * JUnit tests for the primitive {@code float} field class.
 * 
 * @author Emil Forslund
 * @since  3.0.3
 * 
 * @see FloatField
 */
@GeneratedCode(value = "com.speedment.sources.pattern.FieldTestPattern")
@ExtendWith(value = org.mockito.junit.jupiter.MockitoExtension.class)
final class FloatForeignKeyFieldTest {
    
    private static final Function<BasicEntity, String> FORMATTER = entity -> "" + entity.getVarFloat();
    private FloatField<BasicEntity, Float> field;
    private FloatField<BasicEntity, Float> fkField;
    private @Mock Column column;
    private List<BasicEntity> entities;
    private BasicEntity a;
    private BasicEntity b;
    private BasicEntity c;
    private BasicEntity d;
    private BasicEntity e;
    private BasicEntity f;
    private BasicEntity g;
    private BasicEntity h;
    private BasicEntity i;
    private BasicEntity j;
    private BasicEntity k;
    private BasicEntity l;
    
    @BeforeEach
    void setUp() {
        fkField = FloatField.create(
            BasicEntity.Identifier.VAR_FLOAT,
            BasicEntity::getVarFloat,
            BasicEntity::setVarFloat,
            TypeMapper.primitive(),
            false
        );
        field = FloatForeignKeyField.create(
            BasicEntity.Identifier.VAR_FLOAT,
            BasicEntity::getVarFloat,
            BasicEntity::setVarFloat,
            fkField,
            TypeMapper.primitive(),
            false
        );
        
        a = new BasicEntity().setVarFloat(0f);
        b = new BasicEntity().setVarFloat(-1f);
        c = new BasicEntity().setVarFloat(1f);
        d = new BasicEntity().setVarFloat(1f);
        e = new BasicEntity().setVarFloat(2f);
        f = new BasicEntity().setVarFloat(2f);
        g = new BasicEntity().setVarFloat(3f);
        h = new BasicEntity().setVarFloat(-5f);
        i = new BasicEntity().setVarFloat(1f);
        j = new BasicEntity().setVarFloat(-Float.MAX_VALUE);
        k = new BasicEntity().setVarFloat(Float.MAX_VALUE);
        l = new BasicEntity().setVarFloat(0f);
        
        entities = asList(a, b, c, d, e, f, g, h, i, j, k, l);
    }
    
    @Test
    void testBetween() {
        // Create a number of predicates
        final Predicate<BasicEntity> t0 = field.between(0f, 2f);
        final Predicate<BasicEntity> t1 = field.between(-2f, 2f);
        final Predicate<BasicEntity> t2 = field.between(0f, 2f, Inclusion.START_EXCLUSIVE_END_EXCLUSIVE);
        final Predicate<BasicEntity> t3 = field.between(0f, 2f, Inclusion.START_INCLUSIVE_END_EXCLUSIVE);
        final Predicate<BasicEntity> t4 = field.between(0f, 2f, Inclusion.START_EXCLUSIVE_END_INCLUSIVE);
        final Predicate<BasicEntity> t5 = field.between(0f, 2f, Inclusion.START_INCLUSIVE_END_INCLUSIVE);
        
        // Create a number of expected results
        final List<BasicEntity> e0 = asList(a, c, d, i, l);
        final List<BasicEntity> e1 = asList(a, b, c, d, i, l);
        final List<BasicEntity> e2 = asList(c, d, i);
        final List<BasicEntity> e3 = asList(a, c, d, i, l);
        final List<BasicEntity> e4 = asList(c, d, e, f, i);
        final List<BasicEntity> e5 = asList(a, c, d, e, f, i, l);
        
        // Create a number of actual results
        final List<BasicEntity> a0 = entities.stream().filter(t0).collect(toList());
        final List<BasicEntity> a1 = entities.stream().filter(t1).collect(toList());
        final List<BasicEntity> a2 = entities.stream().filter(t2).collect(toList());
        final List<BasicEntity> a3 = entities.stream().filter(t3).collect(toList());
        final List<BasicEntity> a4 = entities.stream().filter(t4).collect(toList());
        final List<BasicEntity> a5 = entities.stream().filter(t5).collect(toList());
        
        // Test the results
        TestUtil.assertListEqual("Test 0: between(0, 2):",                                a0, e0, FORMATTER);
        TestUtil.assertListEqual("Test 1: between(-2, 2):",                               a1, e1, FORMATTER);
        TestUtil.assertListEqual("Test 2: between(0, 2, START_EXCLUSIVE_END_EXCLUSIVE):", a2, e2, FORMATTER);
        TestUtil.assertListEqual("Test 3: between(0, 2, START_INCLUSIVE_END_EXCLUSIVE):", a3, e3, FORMATTER);
        TestUtil.assertListEqual("Test 4: between(0, 2, START_EXCLUSIVE_END_INCLUSIVE):", a4, e4, FORMATTER);
        TestUtil.assertListEqual("Test 5: between(0, 2, START_INCLUSIVE_END_INCLUSIVE):", a5, e5, FORMATTER);
    }
    
    @Test
    void testEqual() {
        // Create a number of predicates
        final Predicate<BasicEntity> t0 = field.equal(-1f);
        final Predicate<BasicEntity> t1 = field.equal(0f);
        final Predicate<BasicEntity> t2 = field.equal(1f);
        final Predicate<BasicEntity> t3 = field.equal(2f);
        final Predicate<BasicEntity> t4 = field.equal(3f);
        final Predicate<BasicEntity> t5 = field.equal(-5f);
        final Predicate<BasicEntity> t6 = field.equal(-Float.MAX_VALUE);
        final Predicate<BasicEntity> t7 = field.equal(Float.MAX_VALUE);
        final Predicate<BasicEntity> t8 = field.equal(100f);
        
        // Create a number of expected results
        final List<BasicEntity> e0 = asList(b);
        final List<BasicEntity> e1 = asList(a, l);
        final List<BasicEntity> e2 = asList(c, d, i);
        final List<BasicEntity> e3 = asList(e, f);
        final List<BasicEntity> e4 = asList(g);
        final List<BasicEntity> e5 = asList(h);
        final List<BasicEntity> e6 = asList(j);
        final List<BasicEntity> e7 = asList(k);
        final List<BasicEntity> e8 = asList();
        
        // Create a number of actual results
        final List<BasicEntity> a0 = entities.stream().filter(t0).collect(toList());
        final List<BasicEntity> a1 = entities.stream().filter(t1).collect(toList());
        final List<BasicEntity> a2 = entities.stream().filter(t2).collect(toList());
        final List<BasicEntity> a3 = entities.stream().filter(t3).collect(toList());
        final List<BasicEntity> a4 = entities.stream().filter(t4).collect(toList());
        final List<BasicEntity> a5 = entities.stream().filter(t5).collect(toList());
        final List<BasicEntity> a6 = entities.stream().filter(t6).collect(toList());
        final List<BasicEntity> a7 = entities.stream().filter(t7).collect(toList());
        final List<BasicEntity> a8 = entities.stream().filter(t8).collect(toList());
        
        // Test the results
        TestUtil.assertListEqual("Test 0: equal(-1):",        a0, e0, FORMATTER);
        TestUtil.assertListEqual("Test 1: equal(0):",         a1, e1, FORMATTER);
        TestUtil.assertListEqual("Test 2: equal(1):",         a2, e2, FORMATTER);
        TestUtil.assertListEqual("Test 3: equal(2):",         a3, e3, FORMATTER);
        TestUtil.assertListEqual("Test 4: equal(3):",         a4, e4, FORMATTER);
        TestUtil.assertListEqual("Test 5: equal(-5):",        a5, e5, FORMATTER);
        TestUtil.assertListEqual("Test 6: equal(MIN_VALUE):", a6, e6, FORMATTER);
        TestUtil.assertListEqual("Test 7: equal(MAX_VALUE):", a7, e7, FORMATTER);
        TestUtil.assertListEqual("Test 8: equal(100):",       a8, e8, FORMATTER);
    }
    
    @Test
    void testGreaterOrEqual() {
        // Create a number of predicates
        final Predicate<BasicEntity> t0 = field.greaterOrEqual(-1f);
        final Predicate<BasicEntity> t1 = field.greaterOrEqual(0f);
        final Predicate<BasicEntity> t2 = field.greaterOrEqual(1f);
        final Predicate<BasicEntity> t3 = field.greaterOrEqual(2f);
        final Predicate<BasicEntity> t4 = field.greaterOrEqual(3f);
        final Predicate<BasicEntity> t5 = field.greaterOrEqual(-5f);
        final Predicate<BasicEntity> t6 = field.greaterOrEqual(-Float.MAX_VALUE);
        final Predicate<BasicEntity> t7 = field.greaterOrEqual(Float.MAX_VALUE);
        final Predicate<BasicEntity> t8 = field.greaterOrEqual(100f);
        
        // Create a number of expected results
        final List<BasicEntity> e0 = asList(a, b, c, d, e, f, g, i, k, l);
        final List<BasicEntity> e1 = asList(a, c, d, e, f, g, i, k, l);
        final List<BasicEntity> e2 = asList(c, d, e, f, g, i, k);
        final List<BasicEntity> e3 = asList(e, f, g, k);
        final List<BasicEntity> e4 = asList(g, k);
        final List<BasicEntity> e5 = asList(a, b, c, d, e, f, g, h, i, k, l);
        final List<BasicEntity> e6 = asList(a, b, c, d, e, f, g, h, i, j, k, l);
        final List<BasicEntity> e7 = asList(k);
        final List<BasicEntity> e8 = asList(k);
        
        // Create a number of actual results
        final List<BasicEntity> a0 = entities.stream().filter(t0).collect(toList());
        final List<BasicEntity> a1 = entities.stream().filter(t1).collect(toList());
        final List<BasicEntity> a2 = entities.stream().filter(t2).collect(toList());
        final List<BasicEntity> a3 = entities.stream().filter(t3).collect(toList());
        final List<BasicEntity> a4 = entities.stream().filter(t4).collect(toList());
        final List<BasicEntity> a5 = entities.stream().filter(t5).collect(toList());
        final List<BasicEntity> a6 = entities.stream().filter(t6).collect(toList());
        final List<BasicEntity> a7 = entities.stream().filter(t7).collect(toList());
        final List<BasicEntity> a8 = entities.stream().filter(t8).collect(toList());
        
        // Test the results
        TestUtil.assertListEqual("Test 0: greaterOrEqual(-1):",        a0, e0, FORMATTER);
        TestUtil.assertListEqual("Test 1: greaterOrEqual(0):",         a1, e1, FORMATTER);
        TestUtil.assertListEqual("Test 2: greaterOrEqual(1):",         a2, e2, FORMATTER);
        TestUtil.assertListEqual("Test 3: greaterOrEqual(2):",         a3, e3, FORMATTER);
        TestUtil.assertListEqual("Test 4: greaterOrEqual(3):",         a4, e4, FORMATTER);
        TestUtil.assertListEqual("Test 5: greaterOrEqual(-5):",        a5, e5, FORMATTER);
        TestUtil.assertListEqual("Test 6: greaterOrEqual(MIN_VALUE):", a6, e6, FORMATTER);
        TestUtil.assertListEqual("Test 7: greaterOrEqual(MAX_VALUE):", a7, e7, FORMATTER);
        TestUtil.assertListEqual("Test 8: greaterOrEqual(100):",       a8, e8, FORMATTER);
    }
    
    @Test
    void testGreaterThan() {
        // Create a number of predicates
        final Predicate<BasicEntity> t0 = field.greaterThan(-1f);
        final Predicate<BasicEntity> t1 = field.greaterThan(0f);
        final Predicate<BasicEntity> t2 = field.greaterThan(1f);
        final Predicate<BasicEntity> t3 = field.greaterThan(2f);
        final Predicate<BasicEntity> t4 = field.greaterThan(3f);
        final Predicate<BasicEntity> t5 = field.greaterThan(-5f);
        final Predicate<BasicEntity> t6 = field.greaterThan(-Float.MAX_VALUE);
        final Predicate<BasicEntity> t7 = field.greaterThan(Float.MAX_VALUE);
        final Predicate<BasicEntity> t8 = field.greaterThan(100f);
        
        // Create a number of expected results
        final List<BasicEntity> e0 = asList(a, c, d, e, f, g, i, k, l);
        final List<BasicEntity> e1 = asList(c, d, e, f, g, i, k);
        final List<BasicEntity> e2 = asList(e, f, g, k);
        final List<BasicEntity> e3 = asList(g, k);
        final List<BasicEntity> e4 = asList(k);
        final List<BasicEntity> e5 = asList(a, b, c, d, e, f, g, i, k, l);
        final List<BasicEntity> e6 = asList(a, b, c, d, e, f, g, h, i, k, l);
        final List<BasicEntity> e7 = asList();
        final List<BasicEntity> e8 = asList(k);
        
        // Create a number of actual results
        final List<BasicEntity> a0 = entities.stream().filter(t0).collect(toList());
        final List<BasicEntity> a1 = entities.stream().filter(t1).collect(toList());
        final List<BasicEntity> a2 = entities.stream().filter(t2).collect(toList());
        final List<BasicEntity> a3 = entities.stream().filter(t3).collect(toList());
        final List<BasicEntity> a4 = entities.stream().filter(t4).collect(toList());
        final List<BasicEntity> a5 = entities.stream().filter(t5).collect(toList());
        final List<BasicEntity> a6 = entities.stream().filter(t6).collect(toList());
        final List<BasicEntity> a7 = entities.stream().filter(t7).collect(toList());
        final List<BasicEntity> a8 = entities.stream().filter(t8).collect(toList());
        
        // Test the results
        TestUtil.assertListEqual("Test 0: greaterThan(-1):",        a0, e0, FORMATTER);
        TestUtil.assertListEqual("Test 1: greaterThan(0):",         a1, e1, FORMATTER);
        TestUtil.assertListEqual("Test 2: greaterThan(1):",         a2, e2, FORMATTER);
        TestUtil.assertListEqual("Test 3: greaterThan(2):",         a3, e3, FORMATTER);
        TestUtil.assertListEqual("Test 4: greaterThan(3):",         a4, e4, FORMATTER);
        TestUtil.assertListEqual("Test 5: greaterThan(-5):",        a5, e5, FORMATTER);
        TestUtil.assertListEqual("Test 6: greaterThan(MIN_VALUE):", a6, e6, FORMATTER);
        TestUtil.assertListEqual("Test 7: greaterThan(MAX_VALUE):", a7, e7, FORMATTER);
        TestUtil.assertListEqual("Test 8: greaterThan(100):",       a8, e8, FORMATTER);
    }
    
    @Test
    void testIn() {
        // Create a number of predicates
        final Predicate<BasicEntity> t0 = field.in();
        final Predicate<BasicEntity> t1 = field.in(0f);
        final Predicate<BasicEntity> t2 = field.in(0f, 1f);
        final Predicate<BasicEntity> t3 = field.in(0f, 1f, 1f);
        final Predicate<BasicEntity> t4 = field.in(-1f, 1f, 2f, 3f);
        final Predicate<BasicEntity> t5 = field.in(-Float.MAX_VALUE, Float.MAX_VALUE);
        final Predicate<BasicEntity> t6 = field.in(1f, 2f, 3f, 4f, 5f);
        final Predicate<BasicEntity> t7 = field.in(100f, 101f, 102f, 103f, 104f);
        final Predicate<BasicEntity> t8 = field.in(-100f);
        
        // Create a number of expected results
        final List<BasicEntity> e0 = asList();
        final List<BasicEntity> e1 = asList(a, l);
        final List<BasicEntity> e2 = asList(a, c, d, i, l);
        final List<BasicEntity> e3 = asList(a, c, d, i, l);
        final List<BasicEntity> e4 = asList(b, c, d, e, f, g, i);
        final List<BasicEntity> e5 = asList(j, k);
        final List<BasicEntity> e6 = asList(c, d, e, f, g, i);
        final List<BasicEntity> e7 = asList();
        final List<BasicEntity> e8 = asList();
        
        // Create a number of actual results
        final List<BasicEntity> a0 = entities.stream().filter(t0).collect(toList());
        final List<BasicEntity> a1 = entities.stream().filter(t1).collect(toList());
        final List<BasicEntity> a2 = entities.stream().filter(t2).collect(toList());
        final List<BasicEntity> a3 = entities.stream().filter(t3).collect(toList());
        final List<BasicEntity> a4 = entities.stream().filter(t4).collect(toList());
        final List<BasicEntity> a5 = entities.stream().filter(t5).collect(toList());
        final List<BasicEntity> a6 = entities.stream().filter(t6).collect(toList());
        final List<BasicEntity> a7 = entities.stream().filter(t7).collect(toList());
        final List<BasicEntity> a8 = entities.stream().filter(t8).collect(toList());
        
        // Test the results
        TestUtil.assertListEqual("Test 0: in():",                        a0, e0, FORMATTER);
        TestUtil.assertListEqual("Test 1: in(0):",                       a1, e1, FORMATTER);
        TestUtil.assertListEqual("Test 2: in(0, 1):",                    a2, e2, FORMATTER);
        TestUtil.assertListEqual("Test 3: in(0, 1, 1):",                 a3, e3, FORMATTER);
        TestUtil.assertListEqual("Test 4: in(-1, 1, 2, 3):",             a4, e4, FORMATTER);
        TestUtil.assertListEqual("Test 5: in(MIN_VALUE, MAX_VALUE):",    a5, e5, FORMATTER);
        TestUtil.assertListEqual("Test 6: in(1, 2, 3, 4, 5):",           a6, e6, FORMATTER);
        TestUtil.assertListEqual("Test 7: in(100, 101, 102, 103, 104):", a7, e7, FORMATTER);
        TestUtil.assertListEqual("Test 8: in(-100):",                    a8, e8, FORMATTER);
    }
    
    @Test
    void testInSet() {
        // Create a number of predicates
        final Predicate<BasicEntity> t0 = field.in(Collections.emptySet());
        final Predicate<BasicEntity> t1 = field.in(Collections.singleton(0f));
        final Predicate<BasicEntity> t2 = field.in(Stream.of(0f, 1f).collect(toSet()));
        final Predicate<BasicEntity> t3 = field.in(Stream.of(0f, 1f, 1f).collect(toSet()));
        final Predicate<BasicEntity> t4 = field.in(Stream.of(-1f, 1f, 2f, 3f).collect(toSet()));
        final Predicate<BasicEntity> t5 = field.in(Stream.of(-Float.MAX_VALUE, Float.MAX_VALUE).collect(toSet()));
        final Predicate<BasicEntity> t6 = field.in(Stream.of(1f, 2f, 3f, 4f, 5f).collect(toSet()));
        final Predicate<BasicEntity> t7 = field.in(Stream.of(100f, 101f, 102f, 103f, 104f).collect(toSet()));
        final Predicate<BasicEntity> t8 = field.in(Collections.singleton(-100f));
        
        // Create a number of expected results
        final List<BasicEntity> e0 = asList();
        final List<BasicEntity> e1 = asList(a, l);
        final List<BasicEntity> e2 = asList(a, c, d, i, l);
        final List<BasicEntity> e3 = asList(a, c, d, i, l);
        final List<BasicEntity> e4 = asList(b, c, d, e, f, g, i);
        final List<BasicEntity> e5 = asList(j, k);
        final List<BasicEntity> e6 = asList(c, d, e, f, g, i);
        final List<BasicEntity> e7 = asList();
        final List<BasicEntity> e8 = asList();
        
        // Create a number of actual results
        final List<BasicEntity> a0 = entities.stream().filter(t0).collect(toList());
        final List<BasicEntity> a1 = entities.stream().filter(t1).collect(toList());
        final List<BasicEntity> a2 = entities.stream().filter(t2).collect(toList());
        final List<BasicEntity> a3 = entities.stream().filter(t3).collect(toList());
        final List<BasicEntity> a4 = entities.stream().filter(t4).collect(toList());
        final List<BasicEntity> a5 = entities.stream().filter(t5).collect(toList());
        final List<BasicEntity> a6 = entities.stream().filter(t6).collect(toList());
        final List<BasicEntity> a7 = entities.stream().filter(t7).collect(toList());
        final List<BasicEntity> a8 = entities.stream().filter(t8).collect(toList());
        
        // Test the results
        TestUtil.assertListEqual("Test 0: inSet():",                        a0, e0, FORMATTER);
        TestUtil.assertListEqual("Test 1: inSet(0):",                       a1, e1, FORMATTER);
        TestUtil.assertListEqual("Test 2: inSet(0, 1):",                    a2, e2, FORMATTER);
        TestUtil.assertListEqual("Test 3: inSet(0, 1, 1):",                 a3, e3, FORMATTER);
        TestUtil.assertListEqual("Test 4: inSet(-1, 1, 2, 3):",             a4, e4, FORMATTER);
        TestUtil.assertListEqual("Test 5: inSet(MIN_VALUE, MAX_VALUE):",    a5, e5, FORMATTER);
        TestUtil.assertListEqual("Test 6: inSet(1, 2, 3, 4, 5):",           a6, e6, FORMATTER);
        TestUtil.assertListEqual("Test 7: inSet(100, 101, 102, 103, 104):", a7, e7, FORMATTER);
        TestUtil.assertListEqual("Test 8: inSet(-100):",                    a8, e8, FORMATTER);
    }
    
    @Test
    void testLessThan() {
        // Create a number of predicates
        final Predicate<BasicEntity> t0 = field.lessThan(-1f);
        final Predicate<BasicEntity> t1 = field.lessThan(0f);
        final Predicate<BasicEntity> t2 = field.lessThan(1f);
        final Predicate<BasicEntity> t3 = field.lessThan(2f);
        final Predicate<BasicEntity> t4 = field.lessThan(3f);
        final Predicate<BasicEntity> t5 = field.lessThan(-5f);
        final Predicate<BasicEntity> t6 = field.lessThan(-Float.MAX_VALUE);
        final Predicate<BasicEntity> t7 = field.lessThan(Float.MAX_VALUE);
        final Predicate<BasicEntity> t8 = field.lessThan(100f);
        
        // Create a number of expected results
        final List<BasicEntity> e0 = asList(h, j);
        final List<BasicEntity> e1 = asList(b, h, j);
        final List<BasicEntity> e2 = asList(a, b, h, j, l);
        final List<BasicEntity> e3 = asList(a, b, c, d, h, i, j, l);
        final List<BasicEntity> e4 = asList(a, b, c, d, e, f, h, i, j, l);
        final List<BasicEntity> e5 = asList(j);
        final List<BasicEntity> e6 = asList();
        final List<BasicEntity> e7 = asList(a, b, c, d, e, f, g, h, i, j, l);
        final List<BasicEntity> e8 = asList(a, b, c, d, e, f, g, h, i, j, l);
        
        // Create a number of actual results
        final List<BasicEntity> a0 = entities.stream().filter(t0).collect(toList());
        final List<BasicEntity> a1 = entities.stream().filter(t1).collect(toList());
        final List<BasicEntity> a2 = entities.stream().filter(t2).collect(toList());
        final List<BasicEntity> a3 = entities.stream().filter(t3).collect(toList());
        final List<BasicEntity> a4 = entities.stream().filter(t4).collect(toList());
        final List<BasicEntity> a5 = entities.stream().filter(t5).collect(toList());
        final List<BasicEntity> a6 = entities.stream().filter(t6).collect(toList());
        final List<BasicEntity> a7 = entities.stream().filter(t7).collect(toList());
        final List<BasicEntity> a8 = entities.stream().filter(t8).collect(toList());
        
        // Test the results
        TestUtil.assertListEqual("Test 0: lessThan(-1):",        a0, e0, FORMATTER);
        TestUtil.assertListEqual("Test 1: lessThan(0):",         a1, e1, FORMATTER);
        TestUtil.assertListEqual("Test 2: lessThan(1):",         a2, e2, FORMATTER);
        TestUtil.assertListEqual("Test 3: lessThan(2):",         a3, e3, FORMATTER);
        TestUtil.assertListEqual("Test 4: lessThan(3):",         a4, e4, FORMATTER);
        TestUtil.assertListEqual("Test 5: lessThan(-5):",        a5, e5, FORMATTER);
        TestUtil.assertListEqual("Test 6: lessThan(MIN_VALUE):", a6, e6, FORMATTER);
        TestUtil.assertListEqual("Test 7: lessThan(MAX_VALUE):", a7, e7, FORMATTER);
        TestUtil.assertListEqual("Test 8: lessThan(100):",       a8, e8, FORMATTER);
    }
    
    @Test
    void testLessOrEqual() {
        // Create a number of predicates
        final Predicate<BasicEntity> t0 = field.lessOrEqual(-1f);
        final Predicate<BasicEntity> t1 = field.lessOrEqual(0f);
        final Predicate<BasicEntity> t2 = field.lessOrEqual(1f);
        final Predicate<BasicEntity> t3 = field.lessOrEqual(2f);
        final Predicate<BasicEntity> t4 = field.lessOrEqual(3f);
        final Predicate<BasicEntity> t5 = field.lessOrEqual(-5f);
        final Predicate<BasicEntity> t6 = field.lessOrEqual(-Float.MAX_VALUE);
        final Predicate<BasicEntity> t7 = field.lessOrEqual(Float.MAX_VALUE);
        final Predicate<BasicEntity> t8 = field.lessOrEqual(100f);
        
        // Create a number of expected results
        final List<BasicEntity> e0 = asList(b, h, j);
        final List<BasicEntity> e1 = asList(a, b, h, j, l);
        final List<BasicEntity> e2 = asList(a, b, c, d, h, i, j, l);
        final List<BasicEntity> e3 = asList(a, b, c, d, e, f, h, i, j, l);
        final List<BasicEntity> e4 = asList(a, b, c, d, e, f, g, h, i, j, l);
        final List<BasicEntity> e5 = asList(h, j);
        final List<BasicEntity> e6 = asList(j);
        final List<BasicEntity> e7 = asList(a, b, c, d, e, f, g, h, i, j, k, l);
        final List<BasicEntity> e8 = asList(a, b, c, d, e, f, g, h, i, j, l);
        
        // Create a number of actual results
        final List<BasicEntity> a0 = entities.stream().filter(t0).collect(toList());
        final List<BasicEntity> a1 = entities.stream().filter(t1).collect(toList());
        final List<BasicEntity> a2 = entities.stream().filter(t2).collect(toList());
        final List<BasicEntity> a3 = entities.stream().filter(t3).collect(toList());
        final List<BasicEntity> a4 = entities.stream().filter(t4).collect(toList());
        final List<BasicEntity> a5 = entities.stream().filter(t5).collect(toList());
        final List<BasicEntity> a6 = entities.stream().filter(t6).collect(toList());
        final List<BasicEntity> a7 = entities.stream().filter(t7).collect(toList());
        final List<BasicEntity> a8 = entities.stream().filter(t8).collect(toList());
        
        // Test the results
        TestUtil.assertListEqual("Test 0: lessOrEqual(-1):",        a0, e0, FORMATTER);
        TestUtil.assertListEqual("Test 1: lessOrEqual(0):",         a1, e1, FORMATTER);
        TestUtil.assertListEqual("Test 2: lessOrEqual(1):",         a2, e2, FORMATTER);
        TestUtil.assertListEqual("Test 3: lessOrEqual(2):",         a3, e3, FORMATTER);
        TestUtil.assertListEqual("Test 4: lessOrEqual(3):",         a4, e4, FORMATTER);
        TestUtil.assertListEqual("Test 5: lessOrEqual(-5):",        a5, e5, FORMATTER);
        TestUtil.assertListEqual("Test 6: lessOrEqual(MIN_VALUE):", a6, e6, FORMATTER);
        TestUtil.assertListEqual("Test 7: lessOrEqual(MAX_VALUE):", a7, e7, FORMATTER);
        TestUtil.assertListEqual("Test 8: lessOrEqual(100):",       a8, e8, FORMATTER);
    }
    
    @Test
    void testNotBetween() {
        // Create a number of predicates
        final Predicate<BasicEntity> t0 = field.notBetween(0f, 2f);
        final Predicate<BasicEntity> t1 = field.notBetween(-2f, 2f);
        final Predicate<BasicEntity> t2 = field.notBetween(0f, 2f, Inclusion.START_EXCLUSIVE_END_EXCLUSIVE);
        final Predicate<BasicEntity> t3 = field.notBetween(0f, 2f, Inclusion.START_INCLUSIVE_END_EXCLUSIVE);
        final Predicate<BasicEntity> t4 = field.notBetween(0f, 2f, Inclusion.START_EXCLUSIVE_END_INCLUSIVE);
        final Predicate<BasicEntity> t5 = field.notBetween(0f, 2f, Inclusion.START_INCLUSIVE_END_INCLUSIVE);
        
        // Create a number of expected results
        final List<BasicEntity> e0 = asList(b, e, f, g, h, j, k);
        final List<BasicEntity> e1 = asList(e, f, g, h, j, k);
        final List<BasicEntity> e2 = asList(a, b, e, f, g, h, j, k, l);
        final List<BasicEntity> e3 = asList(b, e, f, g, h, j, k);
        final List<BasicEntity> e4 = asList(a, b, g, h, j, k, l);
        final List<BasicEntity> e5 = asList(b, g, h, j, k);
        
        // Create a number of actual results
        final List<BasicEntity> a0 = entities.stream().filter(t0).collect(toList());
        final List<BasicEntity> a1 = entities.stream().filter(t1).collect(toList());
        final List<BasicEntity> a2 = entities.stream().filter(t2).collect(toList());
        final List<BasicEntity> a3 = entities.stream().filter(t3).collect(toList());
        final List<BasicEntity> a4 = entities.stream().filter(t4).collect(toList());
        final List<BasicEntity> a5 = entities.stream().filter(t5).collect(toList());
        
        // Test the results
        TestUtil.assertListEqual("Test 0: notBetween(0, 2):",                                a0, e0, FORMATTER);
        TestUtil.assertListEqual("Test 1: notBetween(-2, 2):",                               a1, e1, FORMATTER);
        TestUtil.assertListEqual("Test 2: notBetween(0, 2, START_EXCLUSIVE_END_EXCLUSIVE):", a2, e2, FORMATTER);
        TestUtil.assertListEqual("Test 3: notBetween(0, 2, START_INCLUSIVE_END_EXCLUSIVE):", a3, e3, FORMATTER);
        TestUtil.assertListEqual("Test 4: notBetween(0, 2, START_EXCLUSIVE_END_INCLUSIVE):", a4, e4, FORMATTER);
        TestUtil.assertListEqual("Test 5: notBetween(0, 2, START_INCLUSIVE_END_INCLUSIVE):", a5, e5, FORMATTER);
    }
    
    @Test
    void testNotEqual() {
        // Create a number of predicates
        final Predicate<BasicEntity> t0 = field.notEqual(-1f);
        final Predicate<BasicEntity> t1 = field.notEqual(0f);
        final Predicate<BasicEntity> t2 = field.notEqual(1f);
        final Predicate<BasicEntity> t3 = field.notEqual(2f);
        final Predicate<BasicEntity> t4 = field.notEqual(3f);
        final Predicate<BasicEntity> t5 = field.notEqual(-5f);
        final Predicate<BasicEntity> t6 = field.notEqual(-Float.MAX_VALUE);
        final Predicate<BasicEntity> t7 = field.notEqual(Float.MAX_VALUE);
        final Predicate<BasicEntity> t8 = field.notEqual(100f);
        
        // Create a number of expected results
        final List<BasicEntity> e0 = asList(a, c, d, e, f, g, h, i, j, k, l);
        final List<BasicEntity> e1 = asList(b, c, d, e, f, g, h, i, j, k);
        final List<BasicEntity> e2 = asList(a, b, e, f, g, h, j, k, l);
        final List<BasicEntity> e3 = asList(a, b, c, d, g, h, i, j, k, l);
        final List<BasicEntity> e4 = asList(a, b, c, d, e, f, h, i, j, k, l);
        final List<BasicEntity> e5 = asList(a, b, c, d, e, f, g, i, j, k, l);
        final List<BasicEntity> e6 = asList(a, b, c, d, e, f, g, h, i, k, l);
        final List<BasicEntity> e7 = asList(a, b, c, d, e, f, g, h, i, j, l);
        final List<BasicEntity> e8 = asList(a, b, c, d, e, f, g, h, i, j, k, l);
        
        // Create a number of actual results
        final List<BasicEntity> a0 = entities.stream().filter(t0).collect(toList());
        final List<BasicEntity> a1 = entities.stream().filter(t1).collect(toList());
        final List<BasicEntity> a2 = entities.stream().filter(t2).collect(toList());
        final List<BasicEntity> a3 = entities.stream().filter(t3).collect(toList());
        final List<BasicEntity> a4 = entities.stream().filter(t4).collect(toList());
        final List<BasicEntity> a5 = entities.stream().filter(t5).collect(toList());
        final List<BasicEntity> a6 = entities.stream().filter(t6).collect(toList());
        final List<BasicEntity> a7 = entities.stream().filter(t7).collect(toList());
        final List<BasicEntity> a8 = entities.stream().filter(t8).collect(toList());
        
        // Test the results
        TestUtil.assertListEqual("Test 0: notEqual(-1):",        a0, e0, FORMATTER);
        TestUtil.assertListEqual("Test 1: notEqual(0):",         a1, e1, FORMATTER);
        TestUtil.assertListEqual("Test 2: notEqual(1):",         a2, e2, FORMATTER);
        TestUtil.assertListEqual("Test 3: notEqual(2):",         a3, e3, FORMATTER);
        TestUtil.assertListEqual("Test 4: notEqual(3):",         a4, e4, FORMATTER);
        TestUtil.assertListEqual("Test 5: notEqual(-5):",        a5, e5, FORMATTER);
        TestUtil.assertListEqual("Test 6: notEqual(MIN_VALUE):", a6, e6, FORMATTER);
        TestUtil.assertListEqual("Test 7: notEqual(MAX_VALUE):", a7, e7, FORMATTER);
        TestUtil.assertListEqual("Test 8: notEqual(100):",       a8, e8, FORMATTER);
    }
    
    @Test
    void testNotIn() {
        // Create a number of predicates
        final Predicate<BasicEntity> t0 = field.notIn();
        final Predicate<BasicEntity> t1 = field.notIn(0f);
        final Predicate<BasicEntity> t2 = field.notIn(0f, 1f);
        final Predicate<BasicEntity> t3 = field.notIn(0f, 1f, 1f);
        final Predicate<BasicEntity> t4 = field.notIn(-1f, 1f, 2f, 3f);
        final Predicate<BasicEntity> t5 = field.notIn(-Float.MAX_VALUE, Float.MAX_VALUE);
        final Predicate<BasicEntity> t6 = field.notIn(1f, 2f, 3f, 4f, 5f);
        final Predicate<BasicEntity> t7 = field.notIn(100f, 101f, 102f, 103f, 104f);
        final Predicate<BasicEntity> t8 = field.notIn(-100f);
        
        // Create a number of expected results
        final List<BasicEntity> e0 = asList(a, b, c, d, e, f, g, h, i, j, k, l);
        final List<BasicEntity> e1 = asList(b, c, d, e, f, g, h, i, j, k);
        final List<BasicEntity> e2 = asList(b, e, f, g, h, j, k);
        final List<BasicEntity> e3 = asList(b, e, f, g, h, j, k);
        final List<BasicEntity> e4 = asList(a, h, j, k, l);
        final List<BasicEntity> e5 = asList(a, b, c, d, e, f, g, h, i, l);
        final List<BasicEntity> e6 = asList(a, b, h, j, k, l);
        final List<BasicEntity> e7 = asList(a, b, c, d, e, f, g, h, i, j, k, l);
        final List<BasicEntity> e8 = asList(a, b, c, d, e, f, g, h, i, j, k, l);
        
        // Create a number of actual results
        final List<BasicEntity> a0 = entities.stream().filter(t0).collect(toList());
        final List<BasicEntity> a1 = entities.stream().filter(t1).collect(toList());
        final List<BasicEntity> a2 = entities.stream().filter(t2).collect(toList());
        final List<BasicEntity> a3 = entities.stream().filter(t3).collect(toList());
        final List<BasicEntity> a4 = entities.stream().filter(t4).collect(toList());
        final List<BasicEntity> a5 = entities.stream().filter(t5).collect(toList());
        final List<BasicEntity> a6 = entities.stream().filter(t6).collect(toList());
        final List<BasicEntity> a7 = entities.stream().filter(t7).collect(toList());
        final List<BasicEntity> a8 = entities.stream().filter(t8).collect(toList());
        
        // Test the results
        TestUtil.assertListEqual("Test 0: notIn():",                        a0, e0, FORMATTER);
        TestUtil.assertListEqual("Test 1: notIn(0):",                       a1, e1, FORMATTER);
        TestUtil.assertListEqual("Test 2: notIn(0, 1):",                    a2, e2, FORMATTER);
        TestUtil.assertListEqual("Test 3: notIn(0, 1, 1):",                 a3, e3, FORMATTER);
        TestUtil.assertListEqual("Test 4: notIn(-1, 1, 2, 3):",             a4, e4, FORMATTER);
        TestUtil.assertListEqual("Test 5: notIn(MIN_VALUE, MAX_VALUE):",    a5, e5, FORMATTER);
        TestUtil.assertListEqual("Test 6: notIn(1, 2, 3, 4, 5):",           a6, e6, FORMATTER);
        TestUtil.assertListEqual("Test 7: notIn(100, 101, 102, 103, 104):", a7, e7, FORMATTER);
        TestUtil.assertListEqual("Test 8: notIn(-100):",                    a8, e8, FORMATTER);
    }
    
    @Test
    void testNotInSet() {
        // Create a number of predicates
        final Predicate<BasicEntity> t0 = field.notIn(Collections.emptySet());
        final Predicate<BasicEntity> t1 = field.notIn(Collections.singleton(0f));
        final Predicate<BasicEntity> t2 = field.notIn(Stream.of(0f, 1f).collect(toSet()));
        final Predicate<BasicEntity> t3 = field.notIn(Stream.of(0f, 1f, 1f).collect(toSet()));
        final Predicate<BasicEntity> t4 = field.notIn(Stream.of(-1f, 1f, 2f, 3f).collect(toSet()));
        final Predicate<BasicEntity> t5 = field.notIn(Stream.of(-Float.MAX_VALUE, Float.MAX_VALUE).collect(toSet()));
        final Predicate<BasicEntity> t6 = field.notIn(Stream.of(1f, 2f, 3f, 4f, 5f).collect(toSet()));
        final Predicate<BasicEntity> t7 = field.notIn(Stream.of(100f, 101f, 102f, 103f, 104f).collect(toSet()));
        final Predicate<BasicEntity> t8 = field.notIn(Collections.singleton(-100f));
        
        // Create a number of expected results
        final List<BasicEntity> e0 = asList(a, b, c, d, e, f, g, h, i, j, k, l);
        final List<BasicEntity> e1 = asList(b, c, d, e, f, g, h, i, j, k);
        final List<BasicEntity> e2 = asList(b, e, f, g, h, j, k);
        final List<BasicEntity> e3 = asList(b, e, f, g, h, j, k);
        final List<BasicEntity> e4 = asList(a, h, j, k, l);
        final List<BasicEntity> e5 = asList(a, b, c, d, e, f, g, h, i, l);
        final List<BasicEntity> e6 = asList(a, b, h, j, k, l);
        final List<BasicEntity> e7 = asList(a, b, c, d, e, f, g, h, i, j, k, l);
        final List<BasicEntity> e8 = asList(a, b, c, d, e, f, g, h, i, j, k, l);
        
        // Create a number of actual results
        final List<BasicEntity> a0 = entities.stream().filter(t0).collect(toList());
        final List<BasicEntity> a1 = entities.stream().filter(t1).collect(toList());
        final List<BasicEntity> a2 = entities.stream().filter(t2).collect(toList());
        final List<BasicEntity> a3 = entities.stream().filter(t3).collect(toList());
        final List<BasicEntity> a4 = entities.stream().filter(t4).collect(toList());
        final List<BasicEntity> a5 = entities.stream().filter(t5).collect(toList());
        final List<BasicEntity> a6 = entities.stream().filter(t6).collect(toList());
        final List<BasicEntity> a7 = entities.stream().filter(t7).collect(toList());
        final List<BasicEntity> a8 = entities.stream().filter(t8).collect(toList());
        
        // Test the results
        TestUtil.assertListEqual("Test 0: notInSet():",                        a0, e0, FORMATTER);
        TestUtil.assertListEqual("Test 1: notInSet(0):",                       a1, e1, FORMATTER);
        TestUtil.assertListEqual("Test 2: notInSet(0, 1):",                    a2, e2, FORMATTER);
        TestUtil.assertListEqual("Test 3: notInSet(0, 1, 1):",                 a3, e3, FORMATTER);
        TestUtil.assertListEqual("Test 4: notInSet(-1, 1, 2, 3):",             a4, e4, FORMATTER);
        TestUtil.assertListEqual("Test 5: notInSet(MIN_VALUE, MAX_VALUE):",    a5, e5, FORMATTER);
        TestUtil.assertListEqual("Test 6: notInSet(1, 2, 3, 4, 5):",           a6, e6, FORMATTER);
        TestUtil.assertListEqual("Test 7: notInSet(100, 101, 102, 103, 104):", a7, e7, FORMATTER);
        TestUtil.assertListEqual("Test 8: notInSet(-100):",                    a8, e8, FORMATTER);
    }
    
    @Test
    void getField() {
        final FloatField<BasicEntity, Float> other = field.getField();
        assertNotNull(other);
    }
    
    @Test
    void comparator() {
        comparator(false);
    }
    
    @Test
    void comparatorReversed() {
        comparator(true);
    }
    
    void comparator(final boolean reversed) {
        final Comparator<BasicEntity> comparatorExpected = Comparator.comparing(BasicEntity::getVarFloat);
        final List<BasicEntity> expected = new ArrayList<>(entities);
        expected.sort(reversed ? comparatorExpected.reversed() : comparatorExpected);
        final FloatFieldComparator<BasicEntity, Float> comparator = reversed ? field.reversed() : field.comparator();
        final List<BasicEntity> actual = new ArrayList<>(entities);
        actual.sort(comparator);
        assertEquals(expected, actual);
    }
    
    @Test
    void typemapper() {
        when(column.getDatabaseType()).thenReturn(Float.class.getName());
        final Type fieldType = field.typeMapper().getJavaType(column);
        assertEquals(float.class.getSimpleName(), fieldType.getTypeName());
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
    
    @Test
    void getNullOrder() {
        assertEquals(NullOrder.LAST, field.getNullOrder());
    }
    
    @Test
    void isReversed() {
        assertFalse(field.isReversed());
    }
    
    @Test
    void setter() {
        final float expected = (float) 1;
        final BasicEntity entity = new BasicEntity();
        field.setter().set(entity, expected);
        assertEquals(expected, entity.getVarFloat());
    }
}