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

import org.junit.*;
import org.junit.rules.TestName;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author pemi
 */
public class StreamCompositionTest {

    List<String> producedItems;
    List<Boolean> closeStatus;

    Stream<String> a;
    Stream<String> b;
    Stream<String> c;

    public StreamCompositionTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        producedItems = new ArrayList<>();
        closeStatus = Arrays.asList(false, false, false);

        a = Stream.of("A").peek(producedItems::add).onClose(() -> closeStatus.set(0, Boolean.TRUE));
        b = Stream.of("B").peek(producedItems::add).onClose(() -> closeStatus.set(1, Boolean.TRUE));
        c = Stream.of("C").peek(producedItems::add).onClose(() -> closeStatus.set(2, Boolean.TRUE));

    }

    @Rule
    public TestName name = new TestName();

    private void produceException(String s) {
        throw new RuntimeException("Opps, something went south.");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testPureStream() {
        printTestName();
        List<String> result = StreamComposition.concatAndAutoClose(a, b, c).collect(toList());
        assertEquals(Arrays.asList("A", "B", "C"), result);
        assertAllClosed();
    }

    @Test
    public void testPartialIteration() {
        printTestName();
        Optional<String> result = StreamComposition.concatAndAutoClose(a, b, c).findFirst();
        assertEquals(Optional.of("A"), result);

        assertEquals(Collections.singletonList("A"), producedItems);
        assertAllClosed();
    }

    @Test
    public void testException() {
        printTestName();
        final AtomicBoolean fClosed = new AtomicBoolean();
        boolean gotException = false;
        final Stream<String> f = Stream.of("F").peek(this::produceException).onClose(() -> fClosed.set(true));
        List<String> result;
        try {
            result = StreamComposition.concatAndAutoClose(a, b, f, c).collect(toList());
        } catch (Exception e) {
            gotException = true;
        }
        assertTrue(gotException);
        assertAllClosed();
        assertTrue(fClosed.get());
    }

    @Test
    public void testExceptionInClose() {
        printTestName();
        final AtomicBoolean fClosed = new AtomicBoolean();
        final AtomicBoolean gClosed = new AtomicBoolean();
        boolean gotException = false;

        final Stream<String> f = Stream.of("F")
                .onClose(() -> fClosed.set(true))
                .onClose(() -> produceException("F"));
        final Stream<String> g = Stream.of("G")
                .onClose(() -> gClosed.set(true))
                .onClose(() -> produceException("G"));
        List<String> result;
        try {
            result = StreamComposition.concatAndAutoClose(a, b, f, g, c).collect(toList());
        } catch (Exception e) {
            gotException = true;
//            e.printStackTrace();
        }
        assertTrue(gotException);
        assertAllClosed();
        assertTrue(fClosed.get());
        assertTrue(gClosed.get());
    }

    @Test
    public void testChainedStreams() {
        printTestName();

        // This test makes sure that "chained" streams gets closed all the way up to the "root"
        final AtomicBoolean fClosed = new AtomicBoolean();

        final Stream<String> f = StreamComposition.concatAndAutoClose(
                Stream.of("F").onClose(() -> fClosed.set(true))
        );

        List<Integer> result = f
                .mapToInt(String::length)
                .boxed()
                .mapToLong(l -> l)
                .mapToInt((long l) -> (int) l)
                .boxed()
                .collect(toList());

        assertEquals(Collections.singletonList(1), result);
        assertTrue(fClosed.get());
    }

    private void assertAllClosed() {
        assertEquals(Arrays.asList(true, true, true), closeStatus); // Make sure all streams are AutoClosed
    }

    private void printTestName() {
        //System.out.println(name.getMethodName());
    }

}
