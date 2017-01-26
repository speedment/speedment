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
package com.speedment.common.lazy;

import org.junit.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.assertEquals;

/**
 * @author pemi
 */
public class LazyTest {

    private static final Integer ONE = 1;
    private static final Integer TWO = 2;
    private static final Supplier<Integer> ONE_SUPPLIER = () -> ONE;
    private static final Supplier<Integer> TWO_SUPPLIER = () -> TWO;
    private static final Supplier<Integer> NULL_SUPPLIER = () -> null;

    LazyReference<Integer> instance;

    public LazyTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        instance = LazyReference.create();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetOrCompute() {
        assertEquals(ONE, instance.getOrCompute(ONE_SUPPLIER));
        assertEquals(ONE, instance.getOrCompute(TWO_SUPPLIER));
    }

    @Test(expected = NullPointerException.class)
    public void testGetOrComputeSuppliedNull() {
        instance.getOrCompute(NULL_SUPPLIER);
    }

    @Test
    public void testConcurrency() throws InterruptedException, ExecutionException {
        final int threads = 8;
        ExecutorService executorService = Executors.newFixedThreadPool(8);

        for (int i = 0; i < 10000; i++) {
            final LazyReference<Long> lazy = LazyReference.create();
            final Callable<Long> callable = () -> lazy.getOrCompute(() -> Thread.currentThread().getId());
            List<Future<Long>> futures
                    = IntStream.rangeClosed(0, threads)
                    .mapToObj($ -> executorService.submit(callable))
                    .collect(toList());

            while (!futures.stream().allMatch(Future::isDone)) {
            }

            final Set<Long> ids = futures.stream()
                    .map(LazyTest::getFutureValue)
                    .collect(toSet());

            assertEquals("Failed at iteration " + i, 1, ids.size());

        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.SECONDS);

    }

    private static <T> T getFutureValue(Future<T> future) {
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
