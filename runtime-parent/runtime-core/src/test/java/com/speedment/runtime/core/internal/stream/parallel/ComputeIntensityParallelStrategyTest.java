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
package com.speedment.runtime.core.internal.stream.parallel;

import com.speedment.runtime.core.stream.parallel.ParallelStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author pemi
 */
final class ComputeIntensityParallelStrategyTest {

    private static final int SIZE = 1 << 20;
    private final int availableProcessors;
    private List<Integer> list;

    ComputeIntensityParallelStrategyTest() {
        this.availableProcessors = Runtime.getRuntime().availableProcessors();
    }

    @BeforeEach
    void setUp() {
        list = IntStream.range(0, SIZE).boxed().collect(toList());
    }


    @Test
    void testSpliteratorUnknownSize() {
        strategies().forEach(strategy -> {

            final Spliterator<Integer> spliterator = strategy.spliteratorUnknownSize(list.iterator(), 0);
            final Stream<Integer> stream = StreamSupport.stream(spliterator, true);
            final Map<String, AtomicInteger> threadCount = new ConcurrentHashMap<>();

            stream.forEach(i -> {
                threadCount
                        .computeIfAbsent(Thread.currentThread().getName(), unused -> new AtomicInteger())
                        .incrementAndGet();
            }
            );

            //System.out.println("*** "+strategy.getClass().getSimpleName());
            threadCount.entrySet().stream()
                    .sorted(comparing(Entry::getKey))
                    .forEach(e -> {
                        final String res = String.format("%36s, %7d\n", e.getKey(), e.getValue().intValue());
                        //System.out.println(res);
                    });

            int minTheadsUsed = Math.min(2, availableProcessors / 2 - 1);

            assertTrue(threadCount.size() >= minTheadsUsed, "threadCount is " + threadCount.size() + " but minTheadsUsed is " + minTheadsUsed + " for strategy " + strategy.getClass().getSimpleName());

        });
    }

    @Test
    void testAll() {
        for (int i = 2; i < 18; i++) {
            final int size = 1 << i;
            final List<Stat> stats = strategies()
                    .map(strategy -> test(strategy, size))
                    .collect(toList());

            //stats.forEach(System.out::println);
        }
    }

    private Stat test(ParallelStrategy strategy, int size) {
        final List<Integer> testList = IntStream.range(0, size).boxed().collect(toList());
        final Stat stat = new Stat();
        final long start = System.nanoTime();
        final Spliterator<Integer> spliterator = strategy.spliteratorUnknownSize(testList.iterator(), 0);

        final Stream<Integer> stream = StreamSupport.stream(spliterator, true);

        final Map<String, AtomicInteger> threadCount = new ConcurrentSkipListMap<>();

        stream.forEach(i -> {
            threadCount
                    .computeIfAbsent(Thread.currentThread().getName(), $ -> new AtomicInteger())
                    .incrementAndGet();

        });
        final List<Integer> values = threadCount.entrySet().stream()
                .filter(e -> !e.getKey().equals("main"))
                //.peek(e -> e.getKey())
                .map(Entry::getValue)
                .map(AtomicInteger::get)
                .collect(toList());

        stat.size = testList.size();
        stat.standardDeviation = stdDev(values);
        stat.name = strategy.getClass().getSimpleName();
        stat.threadCount = threadCount;
        stat.duration = System.nanoTime() - start;
        return stat;
    }

    private static double stdDev(Collection<Integer> collection) {
        final double avg = collection.stream()
                .mapToDouble(i -> i)
                .average().orElse(0);
        final double sqrDev = collection.stream()
                .mapToDouble(i -> i)
                .map(d -> Math.pow(d - avg, 2))
                .sum();
        final double variance = sqrDev / collection.size();
        return Math.sqrt(variance);
    }

    private static class Stat {

        int size;
        String name;
        long duration;
        double standardDeviation;
        Map<String, AtomicInteger> threadCount;

        @Override
        public String toString() {

            return String.format("%12d; %45s; %13d; %10.2f; %s",
                    size,
                    name,
                    duration,
                    standardDeviation,
                    threadCount.values().stream().map(AtomicInteger::get).collect(toList()).toString()
            );

        }

    }

    private Stream<ParallelStrategy> strategies() {
        return Stream.of(
                ParallelStrategy.computeIntensityDefault(),
                ParallelStrategy.computeIntensityMedium(),
                ParallelStrategy.computeIntensityHigh(),
                ParallelStrategy.computeIntensityExtreme()
        );
    }

}
