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
import com.speedment.runtime.field.comparator.ByteFieldComparator;
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
 * JUnit tests for the primitive {@code byte} field class.
 * 
 * @author Emil Forslund
 * @since  3.0.3
 * 
 * @see ByteField
 */
@GeneratedCode(value = "com.speedment.sources.pattern.FieldTestPattern")
@ExtendWith(value = org.mockito.junit.jupiter.MockitoExtension.class)
final class ByteForeignKeyFieldTest {
    
    private static final Function<BasicEntity, String> FORMATTER = entity -> "" + entity.getVarByte();
    private ByteField<BasicEntity, Byte> field;
    private ByteField<BasicEntity, Byte> fkField;
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
        fkField = ByteField.create(
            BasicEntity.Identifier.VAR_BYTE,
            BasicEntity::getVarByte,
            BasicEntity::setVarByte,
            TypeMapper.primitive(),
            false
        );
        field = ByteForeignKeyField.create(
            BasicEntity.Identifier.VAR_BYTE,
            BasicEntity::getVarByte,
            BasicEntity::setVarByte,
            fkField,
            TypeMapper.primitive(),
            false
        );
        
        a = new BasicEntity().setVarByte((byte) 0);
        b = new BasicEntity().setVarByte((byte) -1);
        c = new BasicEntity().setVarByte((byte) 1);
        d = new BasicEntity().setVarByte((byte) 1);
        e = new BasicEntity().setVarByte((byte) 2);
        f = new BasicEntity().setVarByte((byte) 2);
        g = new BasicEntity().setVarByte((byte) 3);
        h = new BasicEntity().setVarByte((byte) -5);
        i = new BasicEntity().setVarByte((byte) 1);
        j = new BasicEntity().setVarByte(Byte.MIN_VALUE);
        k = new BasicEntity().setVarByte(Byte.MAX_VALUE);
        l = new BasicEntity().setVarByte((byte) 0);
        
        entities = asList(a, b, c, d, e, f, g, h, i, j, k, l);
    }
    
    @Test
    void testBetween() {
        // Create a number of predicates
        final Predicate<BasicEntity> t0 = field.between((byte) 0, (byte) 2);
        final Predicate<BasicEntity> t1 = field.between((byte) -2, (byte) 2);
        final Predicate<BasicEntity> t2 = field.between((byte) 0, (byte) 2, Inclusion.START_EXCLUSIVE_END_EXCLUSIVE);
        final Predicate<BasicEntity> t3 = field.between((byte) 0, (byte) 2, Inclusion.START_INCLUSIVE_END_EXCLUSIVE);
        final Predicate<BasicEntity> t4 = field.between((byte) 0, (byte) 2, Inclusion.START_EXCLUSIVE_END_INCLUSIVE);
        final Predicate<BasicEntity> t5 = field.between((byte) 0, (byte) 2, Inclusion.START_INCLUSIVE_END_INCLUSIVE);
        
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
        final Predicate<BasicEntity> t0 = field.equal((byte) -1);
        final Predicate<BasicEntity> t1 = field.equal((byte) 0);
        final Predicate<BasicEntity> t2 = field.equal((byte) 1);
        final Predicate<BasicEntity> t3 = field.equal((byte) 2);
        final Predicate<BasicEntity> t4 = field.equal((byte) 3);
        final Predicate<BasicEntity> t5 = field.equal((byte) -5);
        final Predicate<BasicEntity> t6 = field.equal(Byte.MIN_VALUE);
        final Predicate<BasicEntity> t7 = field.equal(Byte.MAX_VALUE);
        final Predicate<BasicEntity> t8 = field.equal((byte) 100);
        
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
        final Predicate<BasicEntity> t0 = field.greaterOrEqual((byte) -1);
        final Predicate<BasicEntity> t1 = field.greaterOrEqual((byte) 0);
        final Predicate<BasicEntity> t2 = field.greaterOrEqual((byte) 1);
        final Predicate<BasicEntity> t3 = field.greaterOrEqual((byte) 2);
        final Predicate<BasicEntity> t4 = field.greaterOrEqual((byte) 3);
        final Predicate<BasicEntity> t5 = field.greaterOrEqual((byte) -5);
        final Predicate<BasicEntity> t6 = field.greaterOrEqual(Byte.MIN_VALUE);
        final Predicate<BasicEntity> t7 = field.greaterOrEqual(Byte.MAX_VALUE);
        final Predicate<BasicEntity> t8 = field.greaterOrEqual((byte) 100);
        
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
        final Predicate<BasicEntity> t0 = field.greaterThan((byte) -1);
        final Predicate<BasicEntity> t1 = field.greaterThan((byte) 0);
        final Predicate<BasicEntity> t2 = field.greaterThan((byte) 1);
        final Predicate<BasicEntity> t3 = field.greaterThan((byte) 2);
        final Predicate<BasicEntity> t4 = field.greaterThan((byte) 3);
        final Predicate<BasicEntity> t5 = field.greaterThan((byte) -5);
        final Predicate<BasicEntity> t6 = field.greaterThan(Byte.MIN_VALUE);
        final Predicate<BasicEntity> t7 = field.greaterThan(Byte.MAX_VALUE);
        final Predicate<BasicEntity> t8 = field.greaterThan((byte) 100);
        
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
        final Predicate<BasicEntity> t1 = field.in((byte) 0);
        final Predicate<BasicEntity> t2 = field.in((byte) 0, (byte) 1);
        final Predicate<BasicEntity> t3 = field.in((byte) 0, (byte) 1, (byte) 1);
        final Predicate<BasicEntity> t4 = field.in((byte) -1, (byte) 1, (byte) 2, (byte) 3);
        final Predicate<BasicEntity> t5 = field.in(Byte.MIN_VALUE, Byte.MAX_VALUE);
        final Predicate<BasicEntity> t6 = field.in((byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5);
        final Predicate<BasicEntity> t7 = field.in((byte) 100, (byte) 101, (byte) 102, (byte) 103, (byte) 104);
        final Predicate<BasicEntity> t8 = field.in((byte) -100);
        
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
        final Predicate<BasicEntity> t1 = field.in(Collections.singleton((byte) 0));
        final Predicate<BasicEntity> t2 = field.in(Stream.of((byte) 0, (byte) 1).collect(toSet()));
        final Predicate<BasicEntity> t3 = field.in(Stream.of((byte) 0, (byte) 1, (byte) 1).collect(toSet()));
        final Predicate<BasicEntity> t4 = field.in(Stream.of((byte) -1, (byte) 1, (byte) 2, (byte) 3).collect(toSet()));
        final Predicate<BasicEntity> t5 = field.in(Stream.of(Byte.MIN_VALUE, Byte.MAX_VALUE).collect(toSet()));
        final Predicate<BasicEntity> t6 = field.in(Stream.of((byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5).collect(toSet()));
        final Predicate<BasicEntity> t7 = field.in(Stream.of((byte) 100, (byte) 101, (byte) 102, (byte) 103, (byte) 104).collect(toSet()));
        final Predicate<BasicEntity> t8 = field.in(Collections.singleton((byte) -100));
        
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
        final Predicate<BasicEntity> t0 = field.lessThan((byte) -1);
        final Predicate<BasicEntity> t1 = field.lessThan((byte) 0);
        final Predicate<BasicEntity> t2 = field.lessThan((byte) 1);
        final Predicate<BasicEntity> t3 = field.lessThan((byte) 2);
        final Predicate<BasicEntity> t4 = field.lessThan((byte) 3);
        final Predicate<BasicEntity> t5 = field.lessThan((byte) -5);
        final Predicate<BasicEntity> t6 = field.lessThan(Byte.MIN_VALUE);
        final Predicate<BasicEntity> t7 = field.lessThan(Byte.MAX_VALUE);
        final Predicate<BasicEntity> t8 = field.lessThan((byte) 100);
        
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
        final Predicate<BasicEntity> t0 = field.lessOrEqual((byte) -1);
        final Predicate<BasicEntity> t1 = field.lessOrEqual((byte) 0);
        final Predicate<BasicEntity> t2 = field.lessOrEqual((byte) 1);
        final Predicate<BasicEntity> t3 = field.lessOrEqual((byte) 2);
        final Predicate<BasicEntity> t4 = field.lessOrEqual((byte) 3);
        final Predicate<BasicEntity> t5 = field.lessOrEqual((byte) -5);
        final Predicate<BasicEntity> t6 = field.lessOrEqual(Byte.MIN_VALUE);
        final Predicate<BasicEntity> t7 = field.lessOrEqual(Byte.MAX_VALUE);
        final Predicate<BasicEntity> t8 = field.lessOrEqual((byte) 100);
        
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
        final Predicate<BasicEntity> t0 = field.notBetween((byte) 0, (byte) 2);
        final Predicate<BasicEntity> t1 = field.notBetween((byte) -2, (byte) 2);
        final Predicate<BasicEntity> t2 = field.notBetween((byte) 0, (byte) 2, Inclusion.START_EXCLUSIVE_END_EXCLUSIVE);
        final Predicate<BasicEntity> t3 = field.notBetween((byte) 0, (byte) 2, Inclusion.START_INCLUSIVE_END_EXCLUSIVE);
        final Predicate<BasicEntity> t4 = field.notBetween((byte) 0, (byte) 2, Inclusion.START_EXCLUSIVE_END_INCLUSIVE);
        final Predicate<BasicEntity> t5 = field.notBetween((byte) 0, (byte) 2, Inclusion.START_INCLUSIVE_END_INCLUSIVE);
        
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
        final Predicate<BasicEntity> t0 = field.notEqual((byte) -1);
        final Predicate<BasicEntity> t1 = field.notEqual((byte) 0);
        final Predicate<BasicEntity> t2 = field.notEqual((byte) 1);
        final Predicate<BasicEntity> t3 = field.notEqual((byte) 2);
        final Predicate<BasicEntity> t4 = field.notEqual((byte) 3);
        final Predicate<BasicEntity> t5 = field.notEqual((byte) -5);
        final Predicate<BasicEntity> t6 = field.notEqual(Byte.MIN_VALUE);
        final Predicate<BasicEntity> t7 = field.notEqual(Byte.MAX_VALUE);
        final Predicate<BasicEntity> t8 = field.notEqual((byte) 100);
        
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
        final Predicate<BasicEntity> t1 = field.notIn((byte) 0);
        final Predicate<BasicEntity> t2 = field.notIn((byte) 0, (byte) 1);
        final Predicate<BasicEntity> t3 = field.notIn((byte) 0, (byte) 1, (byte) 1);
        final Predicate<BasicEntity> t4 = field.notIn((byte) -1, (byte) 1, (byte) 2, (byte) 3);
        final Predicate<BasicEntity> t5 = field.notIn(Byte.MIN_VALUE, Byte.MAX_VALUE);
        final Predicate<BasicEntity> t6 = field.notIn((byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5);
        final Predicate<BasicEntity> t7 = field.notIn((byte) 100, (byte) 101, (byte) 102, (byte) 103, (byte) 104);
        final Predicate<BasicEntity> t8 = field.notIn((byte) -100);
        
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
        final Predicate<BasicEntity> t1 = field.notIn(Collections.singleton((byte) 0));
        final Predicate<BasicEntity> t2 = field.notIn(Stream.of((byte) 0, (byte) 1).collect(toSet()));
        final Predicate<BasicEntity> t3 = field.notIn(Stream.of((byte) 0, (byte) 1, (byte) 1).collect(toSet()));
        final Predicate<BasicEntity> t4 = field.notIn(Stream.of((byte) -1, (byte) 1, (byte) 2, (byte) 3).collect(toSet()));
        final Predicate<BasicEntity> t5 = field.notIn(Stream.of(Byte.MIN_VALUE, Byte.MAX_VALUE).collect(toSet()));
        final Predicate<BasicEntity> t6 = field.notIn(Stream.of((byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5).collect(toSet()));
        final Predicate<BasicEntity> t7 = field.notIn(Stream.of((byte) 100, (byte) 101, (byte) 102, (byte) 103, (byte) 104).collect(toSet()));
        final Predicate<BasicEntity> t8 = field.notIn(Collections.singleton((byte) -100));
        
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
        final ByteField<BasicEntity, Byte> other = field.getField();
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
        final Comparator<BasicEntity> comparatorExpected = Comparator.comparing(BasicEntity::getVarByte);
        final List<BasicEntity> expected = new ArrayList<>(entities);
        expected.sort(reversed ? comparatorExpected.reversed() : comparatorExpected);
        final ByteFieldComparator<BasicEntity, Byte> comparator = reversed ? field.reversed() : field.comparator();
        final List<BasicEntity> actual = new ArrayList<>(entities);
        actual.sort(comparator);
        assertEquals(expected, actual);
    }
    
    @Test
    void typemapper() {
        when(column.getDatabaseType()).thenReturn(Byte.class.getName());
        final Type fieldType = field.typeMapper().getJavaType(column);
        assertEquals(byte.class.getSimpleName(), fieldType.getTypeName());
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
        final byte expected = (byte) 1;
        final BasicEntity entity = new BasicEntity();
        field.setter().set(entity, expected);
        assertEquals(expected, entity.getVarByte());
    }
}