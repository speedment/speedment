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
package com.speedment.stream;

import com.speedment.internal.core.stream.CollectorUtil;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;
import java.util.Spliterator;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongBiFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * A java {@code Stream} wrapper that stream over Key-Value pairs. With this
 * wrapper you get access to additional operators for working with two valued
 * collections.
 *
 * @author Emil Forslund
 * @param <K> the key type
 * @param <V> the value type
 */
public final class MapStream<K, V> implements Stream<Map.Entry<K, V>> {

    private Stream<Map.Entry<K, V>> inner;

    public static <K, V> MapStream<K, V> of(Map.Entry<K, V> entry) {
        return new MapStream<>(Stream.of(entry));
    }

    @SafeVarargs // Creating a Stream of an array is safe.
    @SuppressWarnings({"unchecked", "varargs"})
    public static <K, V> MapStream<K, V> of(Map.Entry<K, V>... entries) {
        return new MapStream<>(Stream.of(entries));
    }

    public static <K, V> MapStream<K, V> of(Map<K, V> map) {
        return new MapStream<>(map.entrySet().stream());
    }

    public static <K, V> MapStream<K, V> of(Stream<Map.Entry<K, V>> stream) {
        return new MapStream<>(stream);
    }

    public static <K, V> MapStream<K, V> fromKeys(Stream<K> keys, Function<K, V> valueFromKey) {
        return new MapStream<>(keys.map(k -> new AbstractMap.SimpleEntry<>(k, valueFromKey.apply(k))));
    }

    public static <K, V> MapStream<K, V> fromValues(Stream<V> values, Function<V, K> keyFromValue) {
        return new MapStream<>(values.map(v -> new AbstractMap.SimpleEntry<>(keyFromValue.apply(v), v)));
    }

    public static <E, K, V> MapStream<K, V> fromStream(Stream<E> stream, Function<E, K> keyMapper, Function<E, V> valueMapper) {
        return new MapStream<>(stream.map(
            e -> new AbstractMap.SimpleEntry<>(
                keyMapper.apply(e),
                valueMapper.apply(e)
            )
        ));
    }

    public static <K, V> MapStream<K, V> empty() {
        return new MapStream<>(Stream.empty());
    }

    @Override
    public MapStream<K, V> filter(Predicate<? super Map.Entry<K, V>> predicate) {
        inner = inner.filter(predicate);
        return this;
    }

    public MapStream<K, V> filter(BiPredicate<? super K, ? super V> predicate) {
        return filter(e -> predicate.test(e.getKey(), e.getValue()));
    }

    @Override
    public <R> Stream<R> map(Function<? super Map.Entry<K, V>, ? extends R> mapper) {
        return inner.map(mapper);
    }

    public <R> Stream<R> map(BiFunction<? super K, ? super V, ? extends R> mapper) {
        return map(e -> mapper.apply(e.getKey(), e.getValue()));
    }

    public <R> MapStream<R, V> mapKey(BiFunction<? super K, ? super V, ? extends R> mapper) {
        return new MapStream<>(inner.map(e
            -> new AbstractMap.SimpleEntry<>(
                mapper.apply(e.getKey(), e.getValue()),
                e.getValue()
            )
        ));
    }

    public <R> MapStream<R, V> mapKey(Function<? super K, ? extends R> mapper) {
        return new MapStream<>(inner.map(e
            -> new AbstractMap.SimpleEntry<>(
                mapper.apply(e.getKey()),
                e.getValue()
            )
        ));
    }

    public <R> MapStream<K, R> mapValue(BiFunction<? super K, ? super V, ? extends R> mapper) {
        return new MapStream<>(inner.map(e
            -> new AbstractMap.SimpleEntry<>(
                e.getKey(),
                mapper.apply(e.getKey(), e.getValue())
            )
        ));
    }

    public <R> MapStream<K, R> mapValue(Function<? super V, ? extends R> mapper) {
        return new MapStream<>(inner.map(e
            -> new AbstractMap.SimpleEntry<>(
                e.getKey(),
                mapper.apply(e.getValue())
            )
        ));
    }

    public <R> MapStream<R, V> flatMapKey(BiFunction<? super K, ? super V, ? extends Stream<? extends R>> mapper) {
        return new MapStream<>(inner.flatMap(e
            -> mapper.apply(e.getKey(), e.getValue())
            .map(k
                -> new AbstractMap.SimpleEntry<>(
                    k,
                    e.getValue()
                )
            )
        ));
    }

    public <R> MapStream<R, V> flatMapKey(Function<? super K, ? extends Stream<? extends R>> mapper) {
        return new MapStream<>(inner.flatMap(e
            -> mapper.apply(e.getKey())
            .map(k
                -> new AbstractMap.SimpleEntry<>(
                    k,
                    e.getValue()
                )
            )
        ));
    }

    public <R> MapStream<K, R> flatMapValue(BiFunction<? super K, ? super V, ? extends Stream<? extends R>> mapper) {
        return new MapStream<>(inner.flatMap(e
            -> mapper.apply(e.getKey(), e.getValue())
            .map(v
                -> new AbstractMap.SimpleEntry<>(
                    e.getKey(),
                    v
                )
            )
        ));
    }

    public <R> MapStream<K, R> flatMapValue(Function<? super V, ? extends Stream<? extends R>> mapper) {
        return new MapStream<>(inner.flatMap(e
            -> mapper.apply(e.getValue())
            .map(v
                -> new AbstractMap.SimpleEntry<>(
                    e.getKey(),
                    v
                )
            )
        ));
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super Map.Entry<K, V>> mapper) {
        return inner.mapToInt(mapper);
    }

    public IntStream mapToInt(ToIntBiFunction<? super K, ? super V> mapper) {
        return inner.mapToInt(e -> mapper.applyAsInt(e.getKey(), e.getValue()));
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super Map.Entry<K, V>> mapper) {
        return inner.mapToLong(mapper);
    }

    public LongStream mapToLong(ToLongBiFunction<? super K, ? super V> mapper) {
        return inner.mapToLong(e -> mapper.applyAsLong(e.getKey(), e.getValue()));
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super Map.Entry<K, V>> mapper) {
        return inner.mapToDouble(mapper);
    }

    public DoubleStream mapToDouble(ToDoubleBiFunction<? super K, ? super V> mapper) {
        return inner.mapToDouble(e -> mapper.applyAsDouble(e.getKey(), e.getValue()));
    }

    @Override
    public <R> Stream<R> flatMap(Function<? super Map.Entry<K, V>, ? extends Stream<? extends R>> mapper) {
        return inner.flatMap(mapper);
    }

    public <R> Stream<R> flatMap(BiFunction<? super K, ? super V, ? extends Stream<? extends R>> mapper) {
        return inner.flatMap(e -> mapper.apply(e.getKey(), e.getValue()));
    }

    @Override
    public IntStream flatMapToInt(Function<? super Map.Entry<K, V>, ? extends IntStream> mapper) {
        return inner.flatMapToInt(mapper);
    }

    public IntStream flatMapToInt(BiFunction<? super K, ? super V, ? extends IntStream> mapper) {
        return inner.flatMapToInt(e -> mapper.apply(e.getKey(), e.getValue()));
    }

    @Override
    public LongStream flatMapToLong(Function<? super Map.Entry<K, V>, ? extends LongStream> mapper) {
        return inner.flatMapToLong(mapper);
    }

    public LongStream flatMapToLong(BiFunction<? super K, ? super V, ? extends LongStream> mapper) {
        return inner.flatMapToLong(e -> mapper.apply(e.getKey(), e.getValue()));
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super Map.Entry<K, V>, ? extends DoubleStream> mapper) {
        return inner.flatMapToDouble(mapper);
    }

    public DoubleStream flatMapToDouble(BiFunction<? super K, ? super V, ? extends DoubleStream> mapper) {
        return inner.flatMapToDouble(e -> mapper.apply(e.getKey(), e.getValue()));
    }

    public Stream<K> keys() {
        return inner.map(Map.Entry::getKey);
    }

    public Stream<V> values() {
        return inner.map(Map.Entry::getValue);
    }

    @Override
    public MapStream<K, V> distinct() {
        inner = inner.distinct();
        return this;
    }

    @Override
    public MapStream<K, V> sorted() {
        final Comparator<K> c = (a, b) -> {
            if (a == null && b == null) {
                return 0;
            } else if (a != null && b != null) {
                if (a instanceof Comparable<?>) {
                    @SuppressWarnings("unchecked")
                    final Comparable<K> ac = (Comparable<K>) a;

                    return ac.compareTo(b);
                }
            }

            throw new UnsupportedOperationException("Can only sort keys that implement Comparable.");
        };

        inner = inner.sorted((a, b) -> c.compare(a.getKey(), b.getKey()));
        return this;
    }

    @Override
    public MapStream<K, V> sorted(Comparator<? super Map.Entry<K, V>> comparator) {
        inner = inner.sorted(comparator);
        return this;
    }

    public MapStream<K, V> sortedByKey(Comparator<K> comparator) {
        inner = inner.sorted(byKeyOnly(comparator));
        return this;
    }

    public MapStream<K, V> sortedByValue(Comparator<V> comparator) {
        inner = inner.sorted(byValueOnly(comparator));
        return this;
    }

    @Override
    public MapStream<K, V> peek(Consumer<? super Map.Entry<K, V>> action) {
        inner = inner.peek(action);
        return this;
    }

    public MapStream<K, V> peek(BiConsumer<? super K, ? super V> action) {
        inner = inner.peek(e -> action.accept(e.getKey(), e.getValue()));
        return this;
    }

    @Override
    public MapStream<K, V> limit(long maxSize) {
        inner = inner.limit(maxSize);
        return this;
    }

    @Override
    public MapStream<K, V> skip(long n) {
        inner = inner.skip(n);
        return this;
    }

    @Override
    public void forEach(Consumer<? super Map.Entry<K, V>> action) {
        inner.forEach(action);
    }

    public void forEach(BiConsumer<? super K, ? super V> action) {
        inner.forEach(e -> action.accept(e.getKey(), e.getValue()));
    }

    @Override
    public void forEachOrdered(Consumer<? super Map.Entry<K, V>> action) {
        inner.forEachOrdered(action);
    }

    public void forEachOrdered(BiConsumer<? super K, ? super V> action) {
        inner.forEachOrdered(e -> action.accept(e.getKey(), e.getValue()));
    }

    @Override
    public Object[] toArray() {
        return inner.toArray();
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        return inner.toArray(generator);
    }

    @Override
    public Map.Entry<K, V> reduce(Map.Entry<K, V> identity, BinaryOperator<Map.Entry<K, V>> accumulator) {
        return inner.reduce(identity, accumulator);
    }

    @Override
    public Optional<Map.Entry<K, V>> reduce(BinaryOperator<Map.Entry<K, V>> accumulator) {
        return inner.reduce(accumulator);
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super Map.Entry<K, V>, U> accumulator, BinaryOperator<U> combiner) {
        return inner.reduce(identity, accumulator, combiner);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super Map.Entry<K, V>> accumulator, BiConsumer<R, R> combiner) {
        return inner.collect(supplier, accumulator, combiner);
    }

    @Override
    public <R, A> R collect(Collector<? super Map.Entry<K, V>, A, R> collector) {
        return inner.collect(collector);
    }

    public <K2> MapStream<K2, List<V>> groupingBy(Function<V, K2> grouper) {
        return inner.map(Map.Entry::getValue)
            .collect(CollectorUtil.groupBy(grouper));
    }

    @Override
    public Optional<Map.Entry<K, V>> min(Comparator<? super Map.Entry<K, V>> comparator) {
        return inner.min(comparator);
    }

    public Optional<Map.Entry<K, V>> minByKey(Comparator<K> comparator) {
        return inner.min(byKeyOnly(comparator));
    }

    public Optional<Map.Entry<K, V>> minByValue(Comparator<V> comparator) {
        return inner.min(byValueOnly(comparator));
    }

    @Override
    public Optional<Map.Entry<K, V>> max(Comparator<? super Map.Entry<K, V>> comparator) {
        return inner.max(comparator);
    }

    public Optional<Map.Entry<K, V>> maxByKey(Comparator<K> comparator) {
        return inner.max(byKeyOnly(comparator));
    }

    public Optional<Map.Entry<K, V>> maxByValue(Comparator<V> comparator) {
        return inner.max(byValueOnly(comparator));
    }

    @Override
    public long count() {
        return inner.count();
    }

    @Override
    public boolean anyMatch(Predicate<? super Map.Entry<K, V>> predicate) {
        return inner.anyMatch(predicate);
    }

    public boolean anyMatch(BiPredicate<? super K, ? super V> predicate) {
        return inner.anyMatch(e -> predicate.test(e.getKey(), e.getValue()));
    }

    @Override
    public boolean allMatch(Predicate<? super Map.Entry<K, V>> predicate) {
        return inner.allMatch(predicate);
    }

    public boolean allMatch(BiPredicate<? super K, ? super V> predicate) {
        return inner.allMatch(e -> predicate.test(e.getKey(), e.getValue()));
    }

    @Override
    public boolean noneMatch(Predicate<? super Map.Entry<K, V>> predicate) {
        return inner.noneMatch(predicate);
    }

    public boolean noneMatch(BiPredicate<? super K, ? super V> predicate) {
        return inner.noneMatch(e -> predicate.test(e.getKey(), e.getValue()));
    }

    @Override
    public Optional<Map.Entry<K, V>> findFirst() {
        return inner.findFirst();
    }

    @Override
    public Optional<Map.Entry<K, V>> findAny() {
        return inner.findAny();
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return inner.iterator();
    }

    @Override
    public Spliterator<Map.Entry<K, V>> spliterator() {
        return inner.spliterator();
    }

    @Override
    public boolean isParallel() {
        return inner.isParallel();
    }

    @Override
    public MapStream<K, V> sequential() {
        inner = inner.sequential();
        return this;
    }

    @Override
    public MapStream<K, V> parallel() {
        inner = inner.parallel();
        return this;
    }

    @Override
    public MapStream<K, V> unordered() {
        inner = inner.unordered();
        return this;
    }

    @Override
    public MapStream<K, V> onClose(Runnable closeHandler) {
        inner = inner.onClose(closeHandler);
        return this;
    }

    @Override
    public void close() {
        inner.close();
    }

    public Map<K, V> toMap() {
        return inner.collect(Collectors.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue
        ));
    }

    public SortedMap<K, V> toSortedMap() {
        return inner.collect(Collectors.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue,
            throwingMerger(),
            TreeMap::new
        ));
    }

    public SortedMap<K, V> toSortedMap(Comparator<K> keyComparator) {
        return inner.collect(Collectors.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue,
            throwingMerger(),
            () -> new TreeMap<>(keyComparator)
        ));
    }

    public List<Map.Entry<K, V>> toList() {
        return inner.collect(Collectors.toList());
    }

    @SuppressWarnings("varargs")
    @SafeVarargs // Iterating over an array is safe.
    public static <K> Comparator<K> comparing(Function<K, ? extends Comparable<?>>... methods) {
        return (a, b) -> {
            for (Function<K, ? extends Comparable<?>> method : methods) {
                @SuppressWarnings(value = "unchecked")
                final Function<K, ? extends Comparable<Object>> m
                    = (Function<K, ? extends Comparable<Object>>) method;

                final Comparable<Object> ac = m.apply(a);
                final Comparable<Object> bc = m.apply(b);
                final int c = ac.compareTo(bc);

                if (c != 0) {
                    return c;
                }
            }

            return 0;
        };
    }

    private MapStream(Stream<Map.Entry<K, V>> inner) {
        this.inner = inner;
    }

    private static <K, V> Comparator<Map.Entry<K, V>> byKeyOnly(Comparator<K> comparator) {
        return (a, b) -> comparator.compare(a.getKey(), b.getKey());
    }

    private static <K, V> Comparator<Map.Entry<K, V>> byValueOnly(Comparator<V> comparator) {
        return (a, b) -> comparator.compare(a.getValue(), b.getValue());
    }

    /**
     * Returns a merge function, suitable for use in
     * {@link Map#merge(Object, Object, BiFunction) Map.merge()} or
     * {@link #toMap(Function, Function, BinaryOperator) toMap()}, which always
     * throws {@code IllegalStateException}. This can be used to enforce the
     * assumption that the elements being collected are distinct.
     *
     * @param <T> the type of input arguments to the merge function
     * @return a merge function which always throw {@code IllegalStateException}
     */
    private static <T> BinaryOperator<T> throwingMerger() {
        return (u, v) -> {
            throw new IllegalStateException(String.format("Duplicate key %s", u));
        };
    }
}
