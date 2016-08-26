/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.core.runtime;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Per Minborg
 */
public class SpeedmentApplicationLifecycleTest {

    private SpeedmentApplicationLifecycle<?> instance;

    public SpeedmentApplicationLifecycleTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        instance = new SpeedmentApplicationLifecycleImpl<>();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void checkVersion() {
        final Map<String, Optional<Boolean>> testVevtor = new LinkedHashMap<>();
        testVevtor.put("1.8.0_39", Optional.of(Boolean.FALSE));
        testVevtor.put("1.8.0_40", Optional.of(Boolean.TRUE));
        testVevtor.put("1.8.0_101", Optional.of(Boolean.TRUE));
        testVevtor.put("1.7.0_40", Optional.of(Boolean.FALSE));
        testVevtor.put("0.8.0_40", Optional.of(Boolean.FALSE));
        testVevtor.put("1.8.0_102", Optional.of(Boolean.TRUE));
        testVevtor.put("Arne", Optional.empty());

        for (final Entry<String, Optional<Boolean>> e : testVevtor.entrySet()) {
            final Optional<Boolean> expected = e.getValue();
            final Optional<Boolean> result = instance.isVersionOk(e.getKey());
            assertEquals(e.getKey(), expected, result);
        }

    }

    public class SpeedmentApplicationLifecycleImpl<T extends SpeedmentApplicationLifecycle<T>> extends SpeedmentApplicationLifecycle<T> {
    }

}
