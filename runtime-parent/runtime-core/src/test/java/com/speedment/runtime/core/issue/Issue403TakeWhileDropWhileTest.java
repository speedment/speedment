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
package com.speedment.runtime.core.issue;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerComponent;
import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import com.speedment.runtime.core.component.sql.override.SqlStreamTerminatorComponent;
import com.speedment.runtime.core.db.AsynchronousQueryResult;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.internal.component.sql.SqlStreamOptimizerComponentImpl;
import com.speedment.runtime.core.internal.component.sql.SqlTracer;
import com.speedment.runtime.core.internal.component.sql.override.SqlStreamTerminatorComponentImpl;
import com.speedment.runtime.core.internal.db.AsynchronousQueryResultImpl;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.ReferenceStreamBuilder;
import com.speedment.runtime.core.internal.stream.builder.pipeline.PipelineImpl;
import com.speedment.runtime.core.stream.parallel.ParallelStrategy;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.test_support.MockDbmsType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
final class Issue403TakeWhileDropWhileTest {

    private static final String[] ELEMENTS = {"a", "b", "c", "d", "e", "a"};
    private static final Predicate<String> GREATER_THAN_B = (String s) -> "b".compareTo(s) < 0;
    private static final Predicate<String> LESS_THAN_C = (String s) -> "c".compareTo(s) > 0;

    private static final double EPSILON = 0.0000001;
    private static final double[] DOUBLE_ELEMENTS = {1.0, 2.0, 3.0, 4.0, 5.0, 1.0};
    private static final DoublePredicate DOUBLE_GREATER_THAN_2 = (double d) -> Double.compare(2.0, d) < 0;
    private static final DoublePredicate DOUBLE_LESS_THAN_3 = (double d) -> Double.compare(3.0, d) > 0;
    private static final ToDoubleFunction<String> TO_DOUBLE_FUNCTION = s -> s.charAt(0) - 'a' + 1.0;

    private static final int[] INT_ELEMENTS = {1, 2, 3, 4, 5, 1};
    private static final IntPredicate INT_GREATER_THAN_2 = (int d) -> Integer.compare(2, d) < 0;
    private static final IntPredicate INT_LESS_THAN_3 = (int d) -> Integer.compare(3, d) > 0;
    private static final ToIntFunction<String> TO_INT_FUNCTION = s -> s.charAt(0) - 'a' + 1;

    private static final long[] LONG_ELEMENTS = {1, 2, 3, 4, 5, 1};
    private static final LongPredicate LONG_GREATER_THAN_2 = (long d) -> Long.compare(2, d) < 0;
    private static final LongPredicate LONG_LESS_THAN_3 = (long d) -> Long.compare(3, d) > 0;
    private static final ToLongFunction<String> TO_LONG_FUNCTION = s -> s.charAt(0) - 'a' + 1;

    private Stream<String> stream;


    @BeforeEach
    void before() {
        final PipelineImpl<String> pipeline = new PipelineImpl<>(() -> Stream.of(ELEMENTS));

        final DbmsType dbmsType = new MockDbmsType();

        final SqlStreamOptimizerInfo<String> info
            = SqlStreamOptimizerInfo.of(
            dbmsType,
            "select name from name_table",
            "select count(*) from name_table",
            (s, l) -> 1l,
            (Field<String> f) -> "name",
            (Field<String> f) -> String.class
        );

        final AsynchronousQueryResult<String> asynchronousQueryResult = new AsynchronousQueryResultImpl<>(
            "select name from name_table",
            new ArrayList<>(),
            rs -> "z",
            () -> null,
            ParallelStrategy.computeIntensityDefault(),
            ps -> {
            },
            rs -> {
            }
        );
        final SqlStreamOptimizerComponent sqlStreamOptimizerComponent = new SqlStreamOptimizerComponentImpl();
        final SqlStreamTerminatorComponent sqlStreamTerminatorComponent = new SqlStreamTerminatorComponentImpl();

        SqlStreamTerminator<String> streamTerminator = new SqlStreamTerminator<>(
            info,
            asynchronousQueryResult,
            sqlStreamOptimizerComponent,
            sqlStreamTerminatorComponent,
            SqlTracer.from(null),
            true
        );

        stream = new ReferenceStreamBuilder<>(
            pipeline,
            streamTerminator
        );

    }

    @Test
    void testStream() {
        assertArrayEquals(ELEMENTS, stream.toArray(String[]::new));
    }

    @Test
    void testPredicateGreaterThanB() {
        assertEquals(
            Arrays.asList("c", "d", "e"),
            stream.filter(GREATER_THAN_B).collect(toList())
        );
    }

    @Test
    void testPredicateLessThanC() {
        assertEquals(
            Arrays.asList("a", "b", "a"),
            stream.filter(LESS_THAN_C).collect(toList())
        );
    }

    @Test
    void testFilter() {
        try {
            final AtomicInteger closeCounter = new AtomicInteger();
            stream.onClose(() -> closeCounter.incrementAndGet());

            final Method method = Stream.class.getMethod("filter", Predicate.class);
            log("Filter exists");
            @SuppressWarnings("unchecked")
            final Stream<String> newStream = (Stream<String>) method.invoke(stream, LESS_THAN_C);
            final List<String> expected = Arrays.asList("a", "b", "a");
            log("expected:" + expected);
            final List<String> actual = newStream.collect(toList());
            log("actual:" + actual);
            assertEquals(expected, actual);
            assertEquals( 1, closeCounter.get(), "Stream was not closed");
        } catch (NoSuchMethodException | SecurityException e) {
            log("We run under Java 8: takeWhile does not exist");
            // We are under Java 8. Just ignore. 
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            // We are on Java 9 but it failed
            fail(e.getMessage());
        }

    }

    @Test
    void testTakeWhile() {
        try {
            final AtomicInteger closeCounter = new AtomicInteger();
            stream.onClose(() -> closeCounter.incrementAndGet());

            final Method method = Stream.class.getMethod("takeWhile", Predicate.class);
            log("We are running under Java 9: takeWhile exists");
            @SuppressWarnings("unchecked")
            final Stream<String> newStream = (Stream<String>) method.invoke(stream, LESS_THAN_C);
            final List<String> expected = Arrays.asList("a", "b");
            log("expected:" + expected);
            final List<String> actual = newStream.collect(toList());
            log("actual:" + actual);
            assertEquals(expected, actual);
            assertEquals(1, closeCounter.get(), "Stream was not closed");
        } catch (NoSuchMethodException | SecurityException e) {
            log("We run under Java 8: takeWhile does not exist");
            // We are under Java 8. Just ignore. 
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            // We are on Java 9 but it failed
            fail(e.getMessage());
        }

    }

    @Test
    void testDropWhile() {
        try {
            final AtomicInteger closeCounter = new AtomicInteger();
            stream.onClose(() -> closeCounter.incrementAndGet());

            final Method method = Stream.class.getMethod("dropWhile", Predicate.class);
            log("We are running under Java 9: dropWhile exists");
            @SuppressWarnings("unchecked")
            final Stream<String> newStream = (Stream<String>) method.invoke(stream, LESS_THAN_C);
            final List<String> expected = Arrays.asList("c", "d", "e", "a");
            log("expected:" + expected);
            final List<String> actual = newStream.collect(toList());
            log("actual:" + actual);
            assertEquals(expected, actual);
            assertEquals(1, closeCounter.get(), "Stream was not closed");
        } catch (NoSuchMethodException | SecurityException e) {
            log("We run under Java 8: dropWhile does not exist");
            // We are under Java 8. Just ignore. 
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            // We are on Java 9 but it failed
            fail(e.getMessage());
        }

    }

    @Test
    void testDoubleStream() {
        assertArrayEquals(DOUBLE_ELEMENTS, stream.mapToDouble(TO_DOUBLE_FUNCTION).toArray(), EPSILON);
    }

    @Test
    void testDoublePredicateGreaterThanB() {
        assertArrayEquals(
            new double[]{3.0, 4.0, 5.0},
            stream.mapToDouble(TO_DOUBLE_FUNCTION).filter(DOUBLE_GREATER_THAN_2).toArray(),
            EPSILON
        );
    }

    @Test
    void testDoublePredicateLessThanC() {
        assertArrayEquals(
            new double[]{1.0, 2.0, 1.0},
            stream.mapToDouble(TO_DOUBLE_FUNCTION).filter(DOUBLE_LESS_THAN_3).toArray(),
            EPSILON
        );
    }

    @Test
    void testDoubleFilter() {
        try {
            final AtomicInteger closeCounter = new AtomicInteger();
            stream.onClose(() -> closeCounter.incrementAndGet());
            final DoubleStream doubleStream = stream.mapToDouble(TO_DOUBLE_FUNCTION);

            final Method method = DoubleStream.class.getMethod("filter", DoublePredicate.class);
            log("Filter exists");
            @SuppressWarnings("unchecked")
            final DoubleStream newStream = (DoubleStream) method.invoke(doubleStream, DOUBLE_LESS_THAN_3);
            final double[] expected = new double[]{1.0, 2.0, 1.0};
            final double[] actual = newStream.toArray();
            log("expected:" + Arrays.toString(expected));
            log("actual:" + Arrays.toString(actual));
            assertArrayEquals(expected, actual, EPSILON);
            assertEquals( 1, closeCounter.get(), "Stream was not closed");
        } catch (NoSuchMethodException | SecurityException e) {
            log("We run under Java 8: takeWhile does not exist");
            // We are under Java 8. Just ignore. 
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            // We are on Java 9 but it failed
            fail(e.getMessage());
        }

    }

    @Test
    void testDoubleTakeWhile() {
        try {
            final AtomicInteger closeCounter = new AtomicInteger();
            stream.onClose(() -> closeCounter.incrementAndGet());
            final DoubleStream doubleStream = stream.mapToDouble(TO_DOUBLE_FUNCTION);

            final Method method = DoubleStream.class.getMethod("takeWhile", DoublePredicate.class);
            log("We are running under Java 9: takeWhile exists");
            @SuppressWarnings("unchecked")
            final DoubleStream newStream = (DoubleStream) method.invoke(doubleStream, DOUBLE_LESS_THAN_3);
            final double[] expected = new double[]{1.0, 2.0};
            final double[] actual = newStream.toArray();
            log("expected:" + Arrays.toString(expected));
            log("actual:" + Arrays.toString(actual));
            assertArrayEquals(expected, actual, EPSILON);
        } catch (NoSuchMethodException | SecurityException e) {
            log("We run under Java 8: takeWhile does not exist");
            // We are under Java 8. Just ignore. 
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            // We are on Java 9 but it failed
            fail(e.getMessage());
        }

    }

    @Test
    void testDoubleDropWhile() {
        try {
            final AtomicInteger closeCounter = new AtomicInteger();
            stream.onClose(() -> closeCounter.incrementAndGet());
            final DoubleStream doubleStream = stream.mapToDouble(TO_DOUBLE_FUNCTION);

            final Method method = DoubleStream.class.getMethod("dropWhile", DoublePredicate.class);
            log("We are running under Java 9: dropWhile exists");
            @SuppressWarnings("unchecked")
            final DoubleStream newStream = (DoubleStream) method.invoke(doubleStream, DOUBLE_LESS_THAN_3);
            final double[] expected = new double[]{3.0, 4.0, 5.0, 1.0};
            final double[] actual = newStream.toArray();
            log("expected:" + Arrays.toString(expected));
            log("actual:" + Arrays.toString(actual));
            assertArrayEquals(expected, actual, EPSILON);
            assertEquals( 1, closeCounter.get(), "Stream was not closed");
        } catch (NoSuchMethodException | SecurityException e) {
            log("We run under Java 8: dropWhile does not exist");
            // We are under Java 8. Just ignore. 
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            // We are on Java 9 but it failed
            fail(e.getMessage());
        }

    }

    @Test
     void testIntStream() {
        assertArrayEquals(INT_ELEMENTS, stream.mapToInt(TO_INT_FUNCTION).toArray());
    }

    @Test
    void testIntPredicateGreaterThanB() {
        assertArrayEquals(
            new int[]{3, 4, 5},
            stream.mapToInt(TO_INT_FUNCTION).filter(INT_GREATER_THAN_2).toArray()
        );
    }

    @Test
    void testIntPredicateLessThanC() {
        assertArrayEquals(
            new int[]{1, 2, 1},
            stream.mapToInt(TO_INT_FUNCTION).filter(INT_LESS_THAN_3).toArray()
        );
    }

    @Test
    void testIntFilter() {
        try {
            final AtomicInteger closeCounter = new AtomicInteger();
            stream.onClose(() -> closeCounter.incrementAndGet());
            final IntStream intStream = stream.mapToInt(TO_INT_FUNCTION);

            final Method method = IntStream.class.getMethod("filter", IntPredicate.class);
            log("Filter exists");
            @SuppressWarnings("unchecked")
            final IntStream newStream = (IntStream) method.invoke(intStream, INT_LESS_THAN_3);
            final int[] expected = new int[]{1, 2, 1};
            final int[] actual = newStream.toArray();
            log("expected:" + Arrays.toString(expected));
            log("actual:" + Arrays.toString(actual));
            assertArrayEquals(expected, actual);
            assertEquals( 1, closeCounter.get(), "Stream was not closed");
        } catch (NoSuchMethodException | SecurityException e) {
            log("We run under Java 8: takeWhile does not exist");
            // We are under Java 8. Just ignore. 
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            // We are on Java 9 but it failed
            fail(e.getMessage());
        }

    }

    @Test
    void testIntTakeWhile() {
        try {
            final AtomicInteger closeCounter = new AtomicInteger();
            stream.onClose(() -> closeCounter.incrementAndGet());
            final IntStream intStream = stream.mapToInt(TO_INT_FUNCTION);

            final Method method = IntStream.class.getMethod("takeWhile", IntPredicate.class);
            log("We are running under Java 9: takeWhile exists");
            @SuppressWarnings("unchecked")
            final IntStream newStream = (IntStream) method.invoke(intStream, INT_LESS_THAN_3);
            final int[] expected = new int[]{1, 2};
            final int[] actual = newStream.toArray();
            log("expected:" + Arrays.toString(expected));
            log("actual:" + Arrays.toString(actual));
            assertArrayEquals(expected, actual);
        } catch (NoSuchMethodException | SecurityException e) {
            log("We run under Java 8: takeWhile does not exist");
            // We are under Java 8. Just ignore. 
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            // We are on Java 9 but it failed
            fail(e.getMessage());
        }

    }

    @Test
    void testIntDropWhile() {
        try {
            final AtomicInteger closeCounter = new AtomicInteger();
            stream.onClose(() -> closeCounter.incrementAndGet());
            final IntStream intStream = stream.mapToInt(TO_INT_FUNCTION);

            final Method method = IntStream.class.getMethod("dropWhile", IntPredicate.class);
            log("We are running under Java 9: dropWhile exists");
            @SuppressWarnings("unchecked")
            final IntStream newStream = (IntStream) method.invoke(intStream, INT_LESS_THAN_3);
            final int[] expected = new int[]{3, 4, 5, 1};
            final int[] actual = newStream.toArray();
            log("expected:" + Arrays.toString(expected));
            log("actual:" + Arrays.toString(actual));
            assertArrayEquals(expected, actual);
            assertEquals( 1, closeCounter.get(), "Stream was not closed");
        } catch (NoSuchMethodException | SecurityException e) {
            log("We run under Java 8: dropWhile does not exist");
            // We are under Java 8. Just ignore. 
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            // We are on Java 9 but it failed
            fail(e.getMessage());
        }

    }

    @Test
    void testLongStream() {
        assertArrayEquals(LONG_ELEMENTS, stream.mapToLong(TO_LONG_FUNCTION).toArray());
    }

    @Test
    void testLongPredicateGreaterThanB() {
        assertArrayEquals(
            new long[]{3, 4, 5},
            stream.mapToLong(TO_LONG_FUNCTION).filter(LONG_GREATER_THAN_2).toArray()
        );
    }

    @Test
    void testLongPredicateLessThanC() {
        assertArrayEquals(
            new long[]{1, 2, 1},
            stream.mapToLong(TO_LONG_FUNCTION).filter(LONG_LESS_THAN_3).toArray()
        );
    }

    @Test
    void testLongFilter() {
        try {
            final AtomicInteger closeCounter = new AtomicInteger();
            stream.onClose(() -> closeCounter.incrementAndGet());
            final LongStream longStream = stream.mapToLong(TO_LONG_FUNCTION);

            final Method method = LongStream.class.getMethod("filter", LongPredicate.class);
            log("Filter exists");
            @SuppressWarnings("unchecked")
            final LongStream newStream = (LongStream) method.invoke(longStream, LONG_LESS_THAN_3);
            final long[] expected = new long[]{1, 2, 1};
            final long[] actual = newStream.toArray();
            log("expected:" + Arrays.toString(expected));
            log("actual:" + Arrays.toString(actual));
            assertArrayEquals(expected, actual);
            assertEquals( 1, closeCounter.get(), "Stream was not closed");
        } catch (NoSuchMethodException | SecurityException e) {
            log("We run under Java 8: takeWhile does not exist");
            // We are under Java 8. Just ignore. 
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            // We are on Java 9 but it failed
            fail(e.getMessage());
        }

    }

    @Test
    void testLongTakeWhile() {
        try {
            final AtomicInteger closeCounter = new AtomicInteger();
            stream.onClose(() -> closeCounter.incrementAndGet());
            final LongStream longStream = stream.mapToLong(TO_LONG_FUNCTION);

            final Method method = LongStream.class.getMethod("takeWhile", LongPredicate.class);
            log("We are running under Java 9: takeWhile exists");
            @SuppressWarnings("unchecked")
            final LongStream newStream = (LongStream) method.invoke(longStream, LONG_LESS_THAN_3);
            final long[] expected = new long[]{1, 2};
            final long[] actual = newStream.toArray();
            log("expected:" + Arrays.toString(expected));
            log("actual:" + Arrays.toString(actual));
            assertArrayEquals(expected, actual);
        } catch (NoSuchMethodException | SecurityException e) {
            log("We run under Java 8: takeWhile does not exist");
            // We are under Java 8. Just ignore. 
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            // We are on Java 9 but it failed
            fail(e.getMessage());
        }

    }

    @Test
    void testLongDropWhile() {
        try {
            final AtomicInteger closeCounter = new AtomicInteger();
            stream.onClose(() -> closeCounter.incrementAndGet());
            final LongStream longStream = stream.mapToLong(TO_LONG_FUNCTION);

            final Method method = LongStream.class.getMethod("dropWhile", LongPredicate.class);
            log("We are running under Java 9: dropWhile exists");
            @SuppressWarnings("unchecked")
            final LongStream newStream = (LongStream) method.invoke(longStream, LONG_LESS_THAN_3);
            final long[] expected = new long[]{3, 4, 5, 1};
            final long[] actual = newStream.toArray();
            log("expected:" + Arrays.toString(expected));
            log("actual:" + Arrays.toString(actual));
            assertArrayEquals(expected, actual);
            assertEquals( 1, closeCounter.get(), "Stream was not closed");
        } catch (NoSuchMethodException | SecurityException e) {
            log("We run under Java 8: dropWhile does not exist");
            // We are under Java 8. Just ignore. 
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            // We are on Java 9 but it failed
            fail(e.getMessage());
        }

    }

    private void log(String msg) {
        // System.out.println("******** " + msg);
    }

}
