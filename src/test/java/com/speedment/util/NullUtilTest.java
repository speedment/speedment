/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
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
            final Object[] result = NullUtil.requireNonNullElements(ARRAY_WITH_NULL, msg);
        } catch (NullPointerException ex) {
            assertTrue(ex.getMessage().contains(msg));
            throw ex;
        }
    }

    @Test(expected = NullPointerException.class)
    public void testRequireNonNulls_Object() {
        Object o0 = null;
        NullUtil.requireNonNulls(o0);
    }

    @Test
    public void testRequireNonNulls_Object_NotNull() {
        Object o0 = "A";
        NullUtil.requireNonNulls(o0);
    }

    @Test(expected = NullPointerException.class)
    public void testRequireNonNulls_Object_Object() {
        Object o0 = "A";
        Object o1 = null;
        NullUtil.requireNonNulls(o0, o1);
    }

    @Test
    public void testRequireNonNulls_Object_Object_NotNull() {
        Object o0 = "A";
        Object o1 = "B";
        NullUtil.requireNonNulls(o0, o1);
    }

    @Test(expected = NullPointerException.class)
    public void testRequireNonNulls_3args() {
        Object o0 = "A";
        Object o1 = null;
        Object o2 = "C";
        NullUtil.requireNonNulls(o0, o1, o2);
    }

    @Test
    public void testRequireNonNulls_3args_NotNull() {
        Object o0 = "A";
        Object o1 = "B";
        Object o2 = "C";
        NullUtil.requireNonNulls(o0, o1, o2);
    }

    @Test(expected = NullPointerException.class)
    public void testRequireNonNulls_4args() {
        Object o0 = null;
        Object o1 = "B";
        Object o2 = "C";
        Object o3 = null;
        NullUtil.requireNonNulls(o0, o1, o2, o3);
    }

    @Test
    public void testRequireNonNulls_4args_NotNul() {
        Object o0 = "A";
        Object o1 = "B";
        Object o2 = "C";
        Object o3 = "D";
        NullUtil.requireNonNulls(o0, o1, o2, o3);
    }

    @Test(expected = NullPointerException.class)
    public void testRequireNonNulls_5args() {
        Object o0 = "A";
        Object o1 = "B";
        Object o2 = "C";
        Object o3 = "D";
        Object o4 = null;
        NullUtil.requireNonNulls(o0, o1, o2, o3, o4);
    }

    @Test
    public void testRequireNonNulls_5args_NotNull() {
        Object o0 = "A";
        Object o1 = "B";
        Object o2 = "C";
        Object o3 = "D";
        Object o4 = "E";
        NullUtil.requireNonNulls(o0, o1, o2, o3, o4);
    }

    @Test(expected = NullPointerException.class)
    public void testRequireNonNulls_6args() {
        Object o0 = null;
        Object o1 = null;
        Object o2 = null;
        Object o3 = null;
        Object o4 = null;
        Object o5 = null;
        NullUtil.requireNonNulls(o0, o1, o2, o3, o4, o5);
    }

    @Test
    public void testRequireNonNulls_6args_NotNull() {
        Object o0 = "A";
        Object o1 = "B";
        Object o2 = "C";
        Object o3 = "D";
        Object o4 = "E";
        Object o5 = "F";
        NullUtil.requireNonNulls(o0, o1, o2, o3, o4, o5);
    }

    @Test(expected = NullPointerException.class)
    public void testRequireNonNulls_7args() {
        Object o0 = "A";
        Object o1 = "B";
        Object o2 = "C";
        Object o3 = "D";
        Object o4 = "E";
        Object o5 = null;
        Object o6 = "G";
        NullUtil.requireNonNulls(o0, o1, o2, o3, o4, o5, o6);
    }

    @Test
    public void testRequireNonNulls_7args_NotNull() {
        Object o0 = "A";
        Object o1 = "B";
        Object o2 = "C";
        Object o3 = "D";
        Object o4 = "E";
        Object o5 = "F";
        Object o6 = "G";
        NullUtil.requireNonNulls(o0, o1, o2, o3, o4, o5, o6);
    }

    @Test
    public void testRequireNonNulls_8args() {
        final List<String> list = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H");
        for (int i = 0; i < 8; i++) {
            final List<String> l = new ArrayList<>(list);
            l.set(i, null);
            try {
                NullUtil.requireNonNulls(l.get(0), l.get(1), l.get(2), l.get(3), l.get(4), l.get(5), l.get(6), l.get(7));
                fail(l + " did not render an NPE");
            } catch (NullPointerException npe) {
                // Ignored
            }
        }
    }

    @Test
    public void testRequireNonNulls_8args_NoNull() {
        Object o0 = "A";
        Object o1 = "B";
        Object o2 = "C";
        Object o3 = "D";
        Object o4 = "E";
        Object o5 = "F";
        Object o6 = "G";
        Object o7 = "H";
        NullUtil.requireNonNulls(o0, o1, o2, o3, o4, o5, o6, o7);
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
        NullUtil.requireKeys(null, "A");
    }

    @Test(expected = NullPointerException.class)
    public void testRequireKeys_2args_2_2() {
        NullUtil.requireKeys(new HashMap<String, String>(), (String) null);
    }

    @Test
    public void testRequireKeys_2args_2_3() {
        final Map<String, String> map = new HashMap<>();
        map.put("A", "Tryggve");
        final Map<String, String> result = NullUtil.requireKeys(map, "A");
        assertEquals(map, result);
    }

    @Test(expected = NoSuchElementException.class)
    public void testRequireKeys_3args() {
        final Map<String, String> map = new HashMap<>();
        map.put("A", "Tryggve");
        map.put("B", "Sven");
        final Map<String, String> result = NullUtil.requireKeys(map, "A", "Z");
        assertEquals(map, result);
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
        assertEquals(map, result);
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
        try {
            final Constructor<NullUtil> constructor = NullUtil.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            final NullUtil nullUtil = constructor.newInstance();
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            // Must fail
        }
    }

}
