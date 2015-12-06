/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.internal.core.stream.parallelstrategy;

import com.speedment.stream.ParallelStrategy;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;
import static java.util.stream.Collectors.toList;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static java.util.Comparator.comparing;
import org.junit.Rule;
import org.junit.rules.TestName;

/**
 *
 * @author pemi
 */
public class ComputeIntensityParallelStrategyTest {

    private static final int SIZE = 1 << 20;
    private List<Integer> list;

    public ComputeIntensityParallelStrategyTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        list = IntStream.range(0, SIZE).boxed().collect(toList());
    }

    @After
    public void tearDown() {
    }
    @Rule
    public TestName name = new TestName();

    protected void printTestName() {
        System.out.println(name.getMethodName());
    }

    @Test
    public void testSpliteratorUnknownSize() {
        printTestName();
        strategies().forEach(strategy -> {

            final Spliterator spliterator = strategy.spliteratorUnknownSize(list.iterator(), 0);
            final Stream<Integer> stream = StreamSupport.stream(spliterator, true);
            final Map<String, AtomicInteger> threadCount = new ConcurrentHashMap<>();

            stream.forEach(i -> {
                threadCount
                        .computeIfAbsent(Thread.currentThread().getName(), $ -> new AtomicInteger())
                        .incrementAndGet();
            }
            );

            System.out.println("*** "+strategy.getClass().getSimpleName());
            threadCount.entrySet().stream()
                    .sorted(comparing(Entry::getKey))
                    .forEach(e -> {
                        System.out.printf("%36s, %7d\n", e.getKey(), e.getValue().intValue());
                    });

        });
    }

    @Test
    public void testAll() {
        printTestName();
        for (int i = 2; i < 18; i++) {
            final int size = 1 << i;
            final List<Stat> stats = strategies()
                    .map(strategy -> test(strategy, size))
                    .collect(toList());

            stats.forEach(System.out::println);
        }
    }

    private Stat test(ParallelStrategy strategy, int size) {
        final List<Integer> testList = IntStream.range(0, size).boxed().collect(toList());
        final Stat stat = new Stat();
        final long start = System.nanoTime();
        final Spliterator spliterator = strategy.spliteratorUnknownSize(testList.iterator(), 0);

        final Stream<Integer> stream = StreamSupport.stream(spliterator, true);

        final Map<String, AtomicInteger> threadCount = new ConcurrentSkipListMap<>();

        stream.forEach(i -> {
            threadCount
                    .computeIfAbsent(Thread.currentThread().getName(), $ -> new AtomicInteger())
                    .incrementAndGet();

        });
        final List<Integer> values = threadCount.entrySet().stream()
                .filter(e -> !e.getKey().equals("main"))
                .peek(e -> e.getKey())
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
                ParallelStrategy.DEFAULT,
                ParallelStrategy.COMPUTE_INTENSITY_MEDIUM,
                ParallelStrategy.COMPUTE_INTENSITY_HIGH,
                ParallelStrategy.COMPUTE_INTENSITY_EXTREME
        );
    }

}
