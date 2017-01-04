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
package com.speedment.runtime.core.util;

import com.speedment.common.invariant.NullUtil;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.*;

/**
 *
 * @author Per Minborg
 */
public class NullUtilTest {

    private static final String[] ARRAY_WITH_NULL = {"A", "B", null, "D"};
    private static final String[] ARRAY_WITHOUT_NULL = {"A", "B", "C", "D"};
    private static final List<String> LIST_WITH_NULL = Arrays.asList(ARRAY_WITH_NULL);
    private static final List<String> LIST_WITHOUT_NULL = Arrays.asList(ARRAY_WITHOUT_NULL);

    public NullUtilTest() {
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

    @Test(expected = NullPointerException.class)
    public void testRequireNonNullElements_array_null() {
        NullUtil.requireNonNullElements((Object[]) null);
    }

    @Test
    public void testRequireNonNullElements_array_no_nulls() {
        NullUtil.requireNonNullElements(ARRAY_WITHOUT_NULL);
    }

    @Test(expected = NullPointerException.class)
    public void testRequireNonNullElements_array_contains_null() {
        NullUtil.requireNonNullElements(ARRAY_WITH_NULL);
    }

    @Test
    public void testRequireNonNullElements_array_contains_no_nulls() {
        assertArrayEquals(ARRAY_WITHOUT_NULL, NullUtil.requireNonNullElements(ARRAY_WITHOUT_NULL));
    }

    @Test(expected = NullPointerException.class)
    public void testRequireNonNullElements_collection_null() {
        Collection<String> collection = null;
        NullUtil.requireNonNullElements(collection);
    }

    @Test
    public void testRequireNonNullElements_collection_whithout_null() {
        assertEquals(LIST_WITHOUT_NULL, NullUtil.requireNonNullElements(LIST_WITHOUT_NULL));
    }

    @Test(expected = NullPointerException.class)
    public void testRequireNonNullElements_collection_containts_null() {
        NullUtil.requireNonNullElements(LIST_WITH_NULL);
    }

    @Test(expected = NullPointerException.class)
    public void testRequireNonNullElements_GenericType_String() {
        final String msg = "OlleOchTryggve";
        try {
            final String[] result = NullUtil.requireNonNullElements(ARRAY_WITH_NULL, msg);
        } catch (NullPointerException ex) {
            assertTrue(ex.getMessage().contains(msg));
            throw ex;
        }
    }

    @Test
    public void testRequireNonNullElements_GenericType_String_Ok() {
        final String[] result = NullUtil.requireNonNullElements(ARRAY_WITHOUT_NULL, "A");
        assertArrayEquals(ARRAY_WITHOUT_NULL, result);
    }

    @Test(expected = NullPointerException.class)
    public void testRequireNonNulls_Object() {
        final String o0 = null;
        NullUtil.requireNonNulls(o0);
    }

    @Test
    public void testRequireNonNulls_Object_NotNull() {
        final String o0 = "A";
        NullUtil.requireNonNulls(o0);
    }

    @Test
    public void testRequireNonNulls_Object_Object() {
        final String obj0 = "A";
        final String obj1 = "B";
        requireNullPointerException(() -> NullUtil.requireNonNulls(obj0, null));
        requireNullPointerException(() -> NullUtil.requireNonNulls(null, obj0));
        NullUtil.requireNonNulls(obj0, obj1);
    }

    @Test
    public void testRequireNonNulls_3args() {
        final String obj0 = "A";
        final String obj1 = "B";
        final String obj2 = "C";
        requireNullPointerException(() -> NullUtil.requireNonNulls(null, obj1, obj2));
        requireNullPointerException(() -> NullUtil.requireNonNulls(obj0, null, obj2));
        requireNullPointerException(() -> NullUtil.requireNonNulls(obj0, obj1, null));
        NullUtil.requireNonNulls(obj0, obj1, obj2);
    }

    @Test
    public void testRequireNonNulls_4args() {
        final String obj0 = "A";
        final String obj1 = "B";
        final String obj2 = "C";
        final String obj3 = "D";
        requireNullPointerException(() -> NullUtil.requireNonNulls(null, obj1, obj2, obj3));
        requireNullPointerException(() -> NullUtil.requireNonNulls(obj0, null, obj2, obj3));
        requireNullPointerException(() -> NullUtil.requireNonNulls(obj0, obj1, null, obj3));
        requireNullPointerException(() -> NullUtil.requireNonNulls(obj0, obj1, obj2, null));
        NullUtil.requireNonNulls(obj0, obj1, obj2, obj3);
    }

    @Test
    public void testRequireNonNulls_5args() {
        final String obj0 = "A";
        final String obj1 = "B";
        final String obj2 = "C";
        final String obj3 = "D";
        final String obj4 = "E";
        requireNullPointerException(() -> NullUtil.requireNonNulls(null, obj1, obj2, obj3, obj4));
        requireNullPointerException(() -> NullUtil.requireNonNulls(obj0, null, obj2, obj3, obj4));
        requireNullPointerException(() -> NullUtil.requireNonNulls(obj0, obj1, null, obj3, obj4));
        requireNullPointerException(() -> NullUtil.requireNonNulls(obj0, obj1, obj2, null, obj4));
        requireNullPointerException(() -> NullUtil.requireNonNulls(obj0, obj1, obj2, obj3, null));
        NullUtil.requireNonNulls(obj0, obj1, obj2, obj3, obj4);
    }

    @Test
    public void testRequireNonNulls_6args() {
        final String obj0 = "A";
        final String obj1 = "B";
        final String obj2 = "C";
        final String obj3 = "D";
        final String obj4 = "E";
        final String obj5 = "F";
        requireNullPointerException(() -> NullUtil.requireNonNulls(null, obj1, obj2, obj3, obj4, obj5));
        requireNullPointerException(() -> NullUtil.requireNonNulls(obj0, null, obj2, obj3, obj4, obj5));
        requireNullPointerException(() -> NullUtil.requireNonNulls(obj0, obj1, null, obj3, obj4, obj5));
        requireNullPointerException(() -> NullUtil.requireNonNulls(obj0, obj1, obj2, null, obj4, obj5));
        requireNullPointerException(() -> NullUtil.requireNonNulls(obj0, obj1, obj2, obj3, null, obj5));
        requireNullPointerException(() -> NullUtil.requireNonNulls(obj0, obj1, obj2, obj3, obj4, null));
        NullUtil.requireNonNulls(obj0, obj1, obj2, obj3, obj4, obj5);
    }

    @Test
    public void testRequireNonNulls_7args() {
        final String obj0 = "A";
        final String obj1 = "B";
        final String obj2 = "C";
        final String obj3 = "D";
        final String obj4 = "E";
        final String obj5 = "F";
        final String obj6 = "G";
        requireNullPointerException(() -> NullUtil.requireNonNulls(null, obj1, obj2, obj3, obj4, obj5, obj6));
        requireNullPointerException(() -> NullUtil.requireNonNulls(obj0, null, obj2, obj3, obj4, obj5, obj6));
        requireNullPointerException(() -> NullUtil.requireNonNulls(obj0, obj1, null, obj3, obj4, obj5, obj6));
        requireNullPointerException(() -> NullUtil.requireNonNulls(obj0, obj1, obj2, null, obj4, obj5, obj6));
        requireNullPointerException(() -> NullUtil.requireNonNulls(obj0, obj1, obj2, obj3, null, obj5, obj6));
        requireNullPointerException(() -> NullUtil.requireNonNulls(obj0, obj1, obj2, obj3, obj4, null, obj6));
        requireNullPointerException(() -> NullUtil.requireNonNulls(obj0, obj1, obj2, obj3, obj4, obj5, null));
        NullUtil.requireNonNulls(obj0, obj1, obj2, obj3, obj4, obj5, obj6);
    }

    @Test
    public void testRequireNonNulls_8args() {
        final String obj0 = "A";
        final String obj1 = "B";
        final String obj2 = "C";
        final String obj3 = "D";
        final String obj4 = "E";
        final String obj5 = "F";
        final String obj6 = "G";
        final String obj7 = "H";
        requireNullPointerException(() -> NullUtil.requireNonNulls(null, obj1, obj2, obj3, obj4, obj5, obj6, obj7));
        requireNullPointerException(() -> NullUtil.requireNonNulls(obj0, null, obj2, obj3, obj4, obj5, obj6, obj7));
        requireNullPointerException(() -> NullUtil.requireNonNulls(obj0, obj1, null, obj3, obj4, obj5, obj6, obj7));
        requireNullPointerException(() -> NullUtil.requireNonNulls(obj0, obj1, obj2, null, obj4, obj5, obj6, obj7));
        requireNullPointerException(() -> NullUtil.requireNonNulls(obj0, obj1, obj2, obj3, null, obj5, obj6, obj7));
        requireNullPointerException(() -> NullUtil.requireNonNulls(obj0, obj1, obj2, obj3, obj4, null, obj6, obj7));
        requireNullPointerException(() -> NullUtil.requireNonNulls(obj0, obj1, obj2, obj3, obj4, obj5, null, obj7));
        requireNullPointerException(() -> NullUtil.requireNonNulls(obj0, obj1, obj2, obj3, obj4, obj5, obj6, null));
        NullUtil.requireNonNulls(obj0, obj1, obj2, obj3, obj4, obj5, obj6, obj7);
    }

    @Test(expected = NullPointerException.class)
    public void testRequireKeys_2args_1() {
        NullUtil.requireKeys(null);
    }

    @Test
    public void testRequireKeys_2args_1_NotNull() {
        NullUtil.requireKeys(new HashMap<String, String>());
    }

    @Test(expected = NullPointerException.class)
    public void testRequireKeys_2args_2() {
        final String[] arr = {"A"};
        NullUtil.requireKeys((Map<String, String>) null, arr);
    }

    @Test(expected = NullPointerException.class)
    public void testRequireKeys_2args_2_2() {
        NullUtil.requireKeys(new HashMap<String, String>(), (String) null);
    }

    @Test(expected = NoSuchElementException.class)
    public void testRequireKeys_2args_2_3_err() {
        final Map<String, String> map = new HashMap<>();
        map.put("A", "Tryggve");
        map.put("B", "Sven");
        final String[] arr = {"A", "B", "StangeKey"};
        final Map<String, String> result = NullUtil.requireKeys(map, arr);
    }

    @Test
    public void testRequireKeys_2args_2_3() {
        final Map<String, String> map = new HashMap<>();
        map.put("A", "Tryggve");
        map.put("B", "Sven");
        final String[] arr = {"A", "B"};
        final Map<String, String> result = NullUtil.requireKeys(map, arr);
        assertEquals(map, result);
    }

    @Test(expected = NoSuchElementException.class)
    public void testRequireKeys_3args() {
        final Map<String, String> map = new HashMap<>();
        map.put("A", "Tryggve");
        map.put("B", "Sven");
        final Map<String, String> result = NullUtil.requireKeys(map, "A", "Z");
    }

    @Test
    public void testRequireKeys_3args_2() {
        final Map<String, String> map = new HashMap<>();
        map.put("A", "Tryggve");
        map.put("B", "Sven");
        final Map<String, String> result = NullUtil.requireKeys(map, "A", "B");
        assertEquals(map, result);
    }

    @Test(expected = NoSuchElementException.class)
    public void testRequireKeys_4args() {
        final Map<String, String> map = new HashMap<>();
        map.put("A", "Tryggve");
        map.put("B", "Sven");
        map.put("C", "Glenn");
        final Map<String, String> result = NullUtil.requireKeys(map, "A", "B", "Z");
    }

    @Test
    public void testRequireKeys_4args_2() {
        final Map<String, String> map = new HashMap<>();
        map.put("A", "Tryggve");
        map.put("B", "Sven");
        map.put("C", "Glenn");
        final Map<String, String> result = NullUtil.requireKeys(map, "A", "B", "C");
        assertEquals(map, result);
    }

    @Test
    public void createInstance() {
        TestUtil.assertNonInstansiable(NullUtil.class);
    }

    private void requireNullPointerException(Runnable r) {
        try {
            r.run();
            fail(NullPointerException.class.getSimpleName() + " was not thrown");
        } catch (NullPointerException npe) {
            // Ignore
        }
    }

}
