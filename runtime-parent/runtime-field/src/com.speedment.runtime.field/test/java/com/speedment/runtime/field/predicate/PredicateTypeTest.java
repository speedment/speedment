/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.runtime.field.predicate;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static com.speedment.runtime.field.predicate.PredicateType.*;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;

/**
 *
 * @author pemi
 */
public class PredicateTypeTest {

    public PredicateTypeTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testComplementType() {
        // Make sure that each Type has a (unique) negated type
        final Map<PredicateType, PredicateType> map = new EnumMap<>(PredicateType.class);

        for (PredicateType pt : PredicateType.values()) {
            final PredicateType negated = pt.negate();
            assertNotNull(negated);
            assertFalse(map.containsKey(negated));
            map.put(negated, pt);
        }

        final List<PredicateType> keys = map.keySet().stream().sorted().collect(toList());
        final List<PredicateType> values = map.values().stream().sorted().collect(toList());
        assertEquals(keys, values);

    }

    @Test
    public void testGetComplementType() {
        assertEquals(EQUAL, NOT_EQUAL.negate());
        assertEquals(NOT_EQUAL, EQUAL.negate());
    }

    @Test
    public void testEffectiveType() {
        assertEquals(EQUAL, EQUAL.effectiveType(false));
        assertEquals(NOT_EQUAL, EQUAL.effectiveType(true));
    }

    @Test
    public void testBetween() {
        assertEquals(BETWEEN, BETWEEN.effectiveType(false));
        assertEquals(NOT_BETWEEN, NOT_BETWEEN.effectiveType(false));
        assertEquals(NOT_BETWEEN, BETWEEN.effectiveType(true));
        assertEquals(BETWEEN, NOT_BETWEEN.effectiveType(true));
    }

}
