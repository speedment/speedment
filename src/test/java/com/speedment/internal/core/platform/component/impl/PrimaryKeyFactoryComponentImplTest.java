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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.internal.core.platform.component.impl;

import com.speedment.internal.core.platform.component.impl.PrimaryKeyFactoryComponentImpl;
import com.speedment.internal.core.platform.component.PrimaryKeyFactoryComponent;
import com.speedment.internal.util.AssertUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pemi
 */
public class PrimaryKeyFactoryComponentImplTest {

    private final PrimaryKeyFactoryComponentImpl instance = new PrimaryKeyFactoryComponentImpl();
    private final Integer k0 = 1;
    private final String k1 = "Arne";
    private final String k2 = "Sven";
    private final String k3 = "Tryggve";
    private final String k4 = "Glenn";
    private final String k5 = "Some";
    private final String k6 = "Other";
    private final Integer k7 = 43;
    private final String k8 = "Objects";

    public PrimaryKeyFactoryComponentImplTest() {
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
    public void testMake_GenericType_GenericType() {
        System.out.println("make");
        assertEquals(Arrays.asList(k0, k1), instance.make(k0, k1));
    }

    @Test
    public void testMake_3args() {
        System.out.println("make");
        assertEquals(Arrays.asList(k0, k1, k2), instance.make(k0, k1, k2));
    }

    @Test
    public void testMake_4args() {
        System.out.println("make");
        assertEquals(Arrays.asList(k0, k1, k2, k3), instance.make(k0, k1, k2, k3));
    }

    @Test
    public void testMake_5args() {
        System.out.println("make");
        assertEquals(Arrays.asList(k0, k1, k2, k3, k4), instance.make(k0, k1, k2, k3, k4));
    }

    @Test
    public void testMake_6args() {
        System.out.println("make");
        final List<Object> expResult = new ArrayList<>();
        expResult.add(k0);
        expResult.add(k1);
        expResult.add(k2);
        expResult.add(k3);
        expResult.add(k4);
        expResult.add(k5);
        expResult.add(k6);
        expResult.add(k7);
        expResult.add(k8);

        final List<?> result = instance.make(k0, k1, k2, k3, k4, k5, k6, k7, k8);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetComponentClass() {
        System.out.println("getComponentClass");
        assertEquals(PrimaryKeyFactoryComponent.class, instance.getComponentClass());
    }

    @Test
    public void testImmutability() {
        System.out.println("immutability");
        final List<?> pks = instance.make(k0, k1, k2, k3, k4, k5, k6, k7, k8);

        isImmutable(pks::clear);
//        isImmutable(() -> pks.add("Olle"));
//        isImmutable(() -> pks.addAll(Arrays.asList("Olle", "Sven")));
        isImmutable(() -> pks.remove("Olle"));
        isImmutable(() -> pks.remove(1));
        isImmutable(() -> pks.removeAll(new ArrayList<>()));
        isImmutable(() -> pks.replaceAll(UnaryOperator.identity()));

    }

    private void isImmutable(Runnable r) {
        AssertUtil.assertThrown(r).isInstanceOf(UnsupportedOperationException.class).test();
    }

}
