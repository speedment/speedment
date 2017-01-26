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

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author pemi
 * @param <T> test type
 */
public abstract class AbstractLazyTest<T> {

    private final Supplier<T> NULL_SUPPLIER = () -> null;

    protected abstract T firstValue();

    protected abstract T secondValue();

    protected abstract Lazy<T> newInstance();

    protected abstract T makeFromThread(Thread t);

    protected Lazy<T> instance;

    @Before
    public void setUp() {
        instance = newInstance();
    }

    @Test
    public void checkFirstAndSecondValue() {
        assertNotEquals(firstValue(), secondValue());
    }

    @Test
    public void testGetOrCompute() {
        assertEquals(firstValue(), instance.getOrCompute(() -> firstValue()));
        assertEquals(firstValue(), instance.getOrCompute(() -> secondValue()));
    }

    @Test(expected = NullPointerException.class)
    public void testGetOrComputeSuppliedNull() {
        instance.getOrCompute(NULL_SUPPLIER);
    }

    @Test(expected = NullPointerException.class)
    public void testGetOrComputeSupplierIsNull() {
        instance.getOrCompute(null);
    }

    @Test
    public void testConcurrency() throws InterruptedException, ExecutionException {
        final int threads = 8;
        ExecutorService executorService = Executors.newFixedThreadPool(8);

        for (int i = 0; i < 10000; i++) {
            final Lazy<T> lazy = newInstance();
            final Callable<T> callable = () -> lazy.getOrCompute(() -> makeFromThread(Thread.currentThread()));
            List<Future<T>> futures
                = IntStream.rangeClosed(0, threads)
                .mapToObj($ -> executorService.submit(callable))
                .collect(toList());

            while (!futures.stream().allMatch(Future::isDone)) {
            }

            final Set<T> results = futures.stream()
                .map(AbstractLazyTest::getFutureValue)
                .collect(toSet());

            assertEquals("Failed at iteration " + i, 1, results.size());

        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.SECONDS);

    }

    public static <T> T getFutureValue(Future<T> future) {
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
