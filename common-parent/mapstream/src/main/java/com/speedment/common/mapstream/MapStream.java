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
package com.speedment.common.mapstream;

import com.speedment.common.mapstream.util.CollectorUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.*;
import java.util.stream.*;

import static java.util.Collections.newSetFromMap;
import static java.util.Objects.requireNonNull;

/**
 * A java {@code Stream} wrapper that stream over Key-Value pairs. With this
 * wrapper you get access to additional operators for working with two valued
 * collections.
 *
 * @author     Emil Forslund
 * @author     Per Minborg
 * @param <K>  the key type
 * @param <V>  the value type
 * 
 * @since      2.1.0
 */
public final class MapStream<K, V> implements Stream<Map.Entry<K, V>> {

    private Stream<Map.Entry<K, V>> inner;

    /**
     * Constructs a new {@code MapStream} from a single entry.
     * 
     * @param <K>    key type
     * @param <V>    value type
     * @param entry  the entry
     * @return       created {@code MapStream}
     */
    public static <K, V> MapStream<K, V> of(Map.Entry<K, V> entry) {
        return new MapStream<>(requireNonNull(Stream.of(entry)));
    }

    /**
     * Constructs a new {@code MapStream} from an array of entries.
     * 
     * @param <K>      key type
     * @param <V>      value type
     * @param entries  elements to stream over
     * @return         created {@code MapStream}
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    @SafeVarargs // Creating a Stream of an array is safe.
    @SuppressWarnings({"unchecked", "varargs"})
    public static <K, V> MapStream<K, V> of(Map.Entry<K, V>... entries) {
        return new MapStream<>(Stream.of(requireNonNull(entries)));
    }

    /**
     * Constructs a new {@code MapStream} from a map of key-value pairs. The map
     * will not be affected.
     * <p>
     * This is equivalent to writing:
     * {@code MapStream.of(map.entrySet().stream());}
     * 
     * @param <K>  key type
     * @param <V>  value type
     * @param map  elements to stream over
     * @return     created {@code MapStream}
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    public static <K, V> MapStream<K, V> of(Map<K, V> map) {
        return of(requireNonNull(map), false);
    }
    
    /**
     * Constructs a new {@code MapStream} from a map of key-value pairs. The map
     * will not be affected.
     * <p>
     * This is equivalent to writing the following if {@code parallel} is set to
     * {@code true}:
     * {@code MapStream.of(map.entrySet().stream().parallel());}
     * 
     * @param <K>       key type
     * @param <V>       value type
     * @param map       elements to stream over
     * @param parallel  if the new stream should be executed in parallel
     * @return          created {@code MapStream}
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    public static <K, V> MapStream<K, V> of(Map<K, V> map, boolean parallel) {
        final MapStream<K, V> stream = new MapStream<>(requireNonNull(map).entrySet().stream());
        if (parallel) stream.inner = stream.inner.parallel();
        return stream;
    }

    /**
     * Constructs a new {@code MapStream} by wrapping a key-value pair stream. 
     * The provided stream will be wrapped in this stream. After this method is
     * called, no more changes should be done to the stream!
     * 
     * @param <K>     key type
     * @param <V>     value type
     * @param stream  stream to wrap
     * @return        created {@code MapStream}
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    public static <K, V> MapStream<K, V> of(Stream<Map.Entry<K, V>> stream) {
        return new MapStream<>(requireNonNull(stream));
    }

    /**
     * Constructs a new {@code MapStream} by wrapping a key-stream and 
     * calculating the corresponding values using the supplied function.
     * 
     * @param <K>           key type
     * @param <V>           value type
     * @param keys          stream of keys to wrap
     * @param valueFromKey  method for calculating values from keys
     * @return              created {@code MapStream}
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    public static <K, V> MapStream<K, V> fromKeys(Stream<K> keys, Function<K, V> valueFromKey) {
        requireNonNull(keys);
        requireNonNull(valueFromKey);
        return new MapStream<>(keys.map(k -> new AbstractMap.SimpleEntry<>(k, valueFromKey.apply(k))));
    }

    /**
     * Constructs a new {@code MapStream} by wrapping a value-stream and 
     * calculating the corresponding keys using the supplied function.
     * 
     * @param <K>           key type
     * @param <V>           value type
     * @param values        stream of values to wrap
     * @param keyFromValue  method for calculating keys from values
     * @return              created {@code MapStream}
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    public static <K, V> MapStream<K, V> fromValues(Stream<V> values, Function<V, K> keyFromValue) {
        requireNonNull(values);
        requireNonNull(keyFromValue);
        return new MapStream<>(values.map(v -> new AbstractMap.SimpleEntry<>(keyFromValue.apply(v), v)));
    }

    /**
     * Constructs a new {@code MapStream} by wrapping a stream of objects and 
     * calculating both keys and values using the supplied functions.
     * 
     * @param <E>          original stream type
     * @param <K>          key type
     * @param <V>          value type
     * @param stream       original stream
     * @param keyMapper    method for calculating keys
     * @param valueMapper  method for calculating values
     * @return             created {@code MapStream}
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    public static <E, K, V> MapStream<K, V> fromStream(Stream<E> stream, Function<E, K> keyMapper, Function<E, V> valueMapper) {
        requireNonNull(stream);
        requireNonNull(keyMapper);
        requireNonNull(valueMapper);
        return new MapStream<>(stream.map(
            e -> new AbstractMap.SimpleEntry<>(
                keyMapper.apply(e),
                valueMapper.apply(e)
            )
        ));
    }

    /**
     * Constructs an empty {@code MapStream}.
     * 
     * @param <K>  key type
     * @param <V>  value type
     * @return     an empty {@code MapStream}
     */
    public static <K, V> MapStream<K, V> empty() {
        return new MapStream<>(Stream.empty());
    }
    
    /**
     * Produces a new MapStream by flipping the specified one so that keys 
     * become values and values become keys. This will consume the supplied
     * stream.
     * 
     * @param <K>       original key type
     * @param <V>       original value type
     * @param original  original MapStream
     * @return          new MapStream with keys and values flipped
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    public static <K, V> MapStream<V, K> flip(MapStream<K, V> original) {
        return MapStream.fromStream(
            requireNonNull(original),
            Map.Entry::getValue, 
            Map.Entry::getKey
        );
    }

    /**
     * Returns a stream consisting of the elements of this stream that match
     * the given predicate.
     * <p>
     * This is an intermediate operation.
     *
     * @param predicate  a non-interfering, stateless predicate to apply to each 
     *                   element to determine if it should be included
     * @return           the new stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    @Override
    public MapStream<K, V> filter(Predicate<? super Map.Entry<K, V>> predicate) {
        inner = inner.filter(requireNonNull(predicate));
        return this;
    }

    /**
     * Returns a stream consisting of the elements of this stream that match
     * the given predicate.
     * <p>
     * This is an intermediate operation.
     *
     * @param predicate  a non-interfering, stateless predicate to apply to each 
     *                   key-value pair to determine if it should be included
     * @return           the new stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    public MapStream<K, V> filter(BiPredicate<? super K, ? super V> predicate) {
        requireNonNull(predicate);
        return filter(e -> predicate.test(e.getKey(), e.getValue()));
    }

    /**
     * Returns a stream consisting of the elements of this stream where the key
     * component matches the given predicate.
     * <p>
     * This is an intermediate operation.
     *
     * @param predicate  a non-interfering, stateless predicate to apply to each 
     *                   key to determine if the entry should be included
     * @return           the new stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    public MapStream<K, V> filterKey(Predicate<? super K> predicate) {
        requireNonNull(predicate);
        return filter(e -> predicate.test(e.getKey()));
    }
    
    /**
     * Returns a stream consisting of the elements of this stream where the 
     * value component matches the given predicate.
     * <p>
     * This is an intermediate operation.
     *
     * @param predicate  a non-interfering, stateless predicate to apply to each 
     *                   value to determine if the entry should be included
     * @return           the new stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    public MapStream<K, V> filterValue(Predicate<? super V> predicate) {
        requireNonNull(predicate);
        return filter(e -> predicate.test(e.getValue()));
    }
    
    /**
     * Returns a stream consisting of the results of applying the given
     * function to the elements of this stream.
     * <p>
     * This is an intermediate operation.
     *
     * @param <R>    the element type of the new stream
     * @param mapper a non-interfering, stateless function to apply to each 
     *               element
     * @return       the new stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    @Override
    public <R> Stream<R> map(Function<? super Map.Entry<K, V>, ? extends R> mapper) {
        return inner.map(requireNonNull(mapper));
    }

    /**
     * Returns a stream consisting of the results of applying the given
     * function to the elements of this stream.
     * <p>
     * This is an intermediate operation.
     *
     * @param <R>     the element type of the new stream
     * @param mapper  a non-interfering, stateless function to apply to each 
     *                key-value pair
     * @return        the new stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    public <R> Stream<R> map(BiFunction<? super K, ? super V, ? extends R> mapper) {
        requireNonNull(mapper);
        return map(e -> mapper.apply(e.getKey(), e.getValue()));
    }

    /**
     * Returns a stream consisting of the results of applying the given
     * function to the key-component of this stream.
     * <p>
     * This is an intermediate operation.
     *
     * @param <R>     the element type of the new stream
     * @param mapper  a non-interfering, stateless function to apply to each 
     *                key in the stream
     * @return        the new stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    public <R> MapStream<R, V> mapKey(BiFunction<? super K, ? super V, ? extends R> mapper) {
        requireNonNull(mapper);
        return new MapStream<>(inner.map(e
            -> new AbstractMap.SimpleEntry<>(
                mapper.apply(e.getKey(), e.getValue()),
                e.getValue()
            )
        ));
    }

    /**
     * Returns a stream consisting of the results of applying the given
     * function to the key-component of this stream.
     * <p>
     * This is an intermediate operation.
     *
     * @param <R>     the element type of the new stream
     * @param mapper  a non-interfering, stateless function to apply to each 
     *                key in the stream
     * @return        the new stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    public <R> MapStream<R, V> mapKey(Function<? super K, ? extends R> mapper) {
        requireNonNull(mapper);
        return new MapStream<>(inner.map(e
            -> new AbstractMap.SimpleEntry<>(
                mapper.apply(e.getKey()),
                e.getValue()
            )
        ));
    }

    /**
     * Returns a stream consisting of the results of applying the given
     * function to the value-component of this stream.
     * <p>
     * This is an intermediate operation.
     *
     * @param <R>     the element type of the new stream
     * @param mapper  a non-interfering, stateless function to apply to each 
     *                value in the stream
     * @return        the new stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    public <R> MapStream<K, R> mapValue(BiFunction<? super K, ? super V, ? extends R> mapper) {
        requireNonNull(mapper);
        return new MapStream<>(inner.map(e
            -> new AbstractMap.SimpleEntry<>(
                e.getKey(),
                mapper.apply(e.getKey(), e.getValue())
            )
        ));
    }

    /**
     * Returns a stream consisting of the results of applying the given
     * function to the value-component of this stream.
     * <p>
     * This is an intermediate operation.
     *
     * @param <R>     the element type of the new stream
     * @param mapper  a non-interfering, stateless function to apply to each 
     *                value in the stream
     * @return        the new stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    public <R> MapStream<K, R> mapValue(Function<? super V, ? extends R> mapper) {
        requireNonNull(mapper);
        return new MapStream<>(inner.map(e
            -> new AbstractMap.SimpleEntry<>(
                e.getKey(),
                mapper.apply(e.getValue())
            )
        ));
    }

    /**
     * Returns an {@code IntStream} consisting of the results of applying the
     * given function to the elements of this stream.
     * <p>
     * This is an intermediate operation.
     *
     * @param mapper  a non-interfering, stateless function to apply to each 
     *                element
     * @return        the new stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    @Override
    public IntStream mapToInt(ToIntFunction<? super Map.Entry<K, V>> mapper) {
        return inner.mapToInt(mapper);
    }

    /**
     * Returns an {@code IntStream} consisting of the results of applying the
     * given function to the elements of this stream.
     * <p>
     * This is an intermediate operation.
     *
     * @param mapper  a non-interfering, stateless function to apply to each 
     *                element
     * @return        the new stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    public IntStream mapToInt(ToIntBiFunction<? super K, ? super V> mapper) {
        requireNonNull(mapper);
        return inner.mapToInt(e -> mapper.applyAsInt(e.getKey(), e.getValue()));
    }

    /**
     * Returns a {@code LongStream} consisting of the results of applying the
     * given function to the elements of this stream.
     * <p>
     * This is an intermediate operation.
     *
     * @param mapper  a non-interfering, stateless function to apply to each 
     *                element
     * @return        the new stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    @Override
    public LongStream mapToLong(ToLongFunction<? super Map.Entry<K, V>> mapper) {
        return inner.mapToLong(requireNonNull(mapper));
    }

    /**
     * Returns a {@code LongStream} consisting of the results of applying the
     * given function to the elements of this stream.
     * <p>
     * This is an intermediate operation.
     *
     * @param mapper  a non-interfering, stateless function to apply to each 
     *                element
     * @return        the new stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    public LongStream mapToLong(ToLongBiFunction<? super K, ? super V> mapper) {
        requireNonNull(mapper);
        return inner.mapToLong(e -> mapper.applyAsLong(e.getKey(), e.getValue()));
    }

    /**
     * Returns a {@code DoubleStream} consisting of the results of applying the
     * given function to the elements of this stream.
     * <p>
     * This is an intermediate operation.
     *
     * @param mapper  a non-interfering, stateless function to apply to each 
     *                element
     * @return        the new stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super Map.Entry<K, V>> mapper) {
        return inner.mapToDouble(requireNonNull(mapper));
    }

    /**
     * Returns a {@code DoubleStream} consisting of the results of applying the
     * given function to the elements of this stream.
     * <p>
     * This is an intermediate operation.
     *
     * @param mapper  a non-interfering, stateless function to apply to each 
     *                element
     * @return        the new stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    public DoubleStream mapToDouble(ToDoubleBiFunction<? super K, ? super V> mapper) {
        requireNonNull(mapper);
        return inner.mapToDouble(e -> mapper.applyAsDouble(e.getKey(), e.getValue()));
    }

    /**
     * Returns a stream consisting of the results of replacing each element of
     * this stream with the contents of a mapped stream produced by applying
     * the provided mapping function to each element.  Each mapped stream is
     * {@link java.util.stream.BaseStream#close() closed} after its contents
     * have been placed into this stream.  (If a mapped stream is {@code null}
     * an empty stream is used, instead.)
     * <p>
     * This is an intermediate operation.
     *
     * <p>
     * The {@code flatMap()} operation has the effect of applying a one-to-many
     * transformation to the elements of the stream, and then flattening the
     * resulting elements into a new stream.
     * <p>
     * <b>Examples.</b>
     *
     * <p>If {@code orders} is a stream of purchase orders, and each purchase
     * order contains a collection of line items, then the following produces a
     * stream containing all the line items in all the orders:
     * <pre>{@code
     *     orders.flatMap(order -> order.getLineItems().stream())...
     * }</pre>
     * <p>
     * If {@code path} is the path to a file, then the following produces a
     * stream of the {@code words} contained in that file:
     * <pre>{@code
     *     Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8);
     *     Stream<String> words = lines.flatMap(line -> Stream.of(line.split(" +")));
     * }</pre>
     * The {@code mapper} function passed to {@code flatMap} splits a line,
     * using a simple regular expression, into an array of words, and then
     * creates a stream of words from that array.
     *
     * @param <R>     the element type of the new stream
     * @param mapper  a non-interfering, stateless function to apply to each 
     *                element which produces a stream of new elements
     * @return        the new stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    @Override
    public <R> Stream<R> flatMap(Function<? super Map.Entry<K, V>, ? extends Stream<? extends R>> mapper) {
        return inner.flatMap(requireNonNull(mapper));
    }

    /**
     * Returns a stream consisting of the results of replacing each element of
     * this stream with the contents of a mapped stream produced by applying
     * the provided mapping function to each element. Each mapped stream is
     * {@link java.util.stream.BaseStream#close() closed} after its contents
     * have been placed into this stream.  (If a mapped stream is {@code null}
     * an empty stream is used, instead.)
     * <p>
     * This is an intermediate operation.
     *
     * <p>
     * The {@code flatMap()} operation has the effect of applying a one-to-many
     * transformation to the elements of the stream, and then flattening the
     * resulting elements into a new stream.
     * <p>
     * <b>Examples.</b>
     *
     * <p>If {@code orders} is a stream of purchase orders, and each purchase
     * order contains a collection of line items, then the following produces a
     * stream containing all the line items in all the orders:
     * <pre>{@code
     *     orders.flatMap(order -> order.getLineItems().stream())...
     * }</pre>
     * <p>
     * If {@code path} is the path to a file, then the following produces a
     * stream of the {@code words} contained in that file:
     * <pre>{@code
     *     Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8);
     *     Stream<String> words = lines.flatMap(line -> Stream.of(line.split(" +")));
     * }</pre>
     * The {@code mapper} function passed to {@code flatMap} splits a line,
     * using a simple regular expression, into an array of words, and then
     * creates a stream of words from that array.
     *
     * @param <R>     the element type of the new stream
     * @param mapper  a non-interfering, stateless function to apply to each 
     *                element which produces a stream of new elements
     * @return        the new stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    public <R> Stream<R> flatMap(BiFunction<? super K, ? super V, ? extends Stream<? extends R>> mapper) {
        requireNonNull(mapper);
        return inner.flatMap(e -> mapper.apply(e.getKey(), e.getValue()));
    }
    
    /**
     * Returns a stream where the key-component of this stream has been replaced 
     * by the result of applying the specified mapping function to the entry.
     * Each mapped stream is {@link java.util.stream.BaseStream#close() closed} 
     * after its contents have been placed into this stream. (If a mapped stream 
     * is {@code null} an empty stream is used, instead.)
     * <p>
     * This is an intermediate operation.
     *
     * <p>
     * The {@code flatMap()} operation has the effect of applying a one-to-many
     * transformation to the elements of the stream, and then flattening the
     * resulting elements into a new stream.
     * 
     * @param <R>     the element type of the new stream
     * @param mapper  a non-interfering, stateless function to apply to each 
     *                key-value pair which produces a stream of new keys
     * @return        the new stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    public <R> MapStream<R, V> flatMapKey(BiFunction<? super K, ? super V, ? extends Stream<? extends R>> mapper) {
        requireNonNull(mapper);
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

    /**
     * Returns a stream where the key-component of this stream has been replaced 
     * by the result of applying the specified mapping function to the entry.
     * Each mapped stream is {@link java.util.stream.BaseStream#close() closed} 
     * after its contents have been placed into this stream. (If a mapped stream 
     * is {@code null} an empty stream is used, instead.)
     * <p>
     * This is an intermediate operation.
     *
     * <p>
     * The {@code flatMap()} operation has the effect of applying a one-to-many
     * transformation to the elements of the stream, and then flattening the
     * resulting elements into a new stream.
     * 
     * @param <R>     the element type of the new stream
     * @param mapper  a non-interfering, stateless function to apply to each 
     *                key-value pair which produces a stream of new keys
     * @return        the new stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    public <R> MapStream<R, V> flatMapKey(Function<? super K, ? extends Stream<? extends R>> mapper) {
        requireNonNull(mapper);
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

    /**
     * Returns a stream where the value-component of this stream has been 
     * replaced by the result of applying the specified mapping function to the 
     * entry. Each mapped stream is 
     * {@link java.util.stream.BaseStream#close() closed} after its contents 
     * have been placed into this stream. (If a mapped stream is {@code null} an 
     * empty stream is used, instead.)
     * <p>
     * This is an intermediate operation.
     *
     * <p>
     * The {@code flatMap()} operation has the effect of applying a one-to-many
     * transformation to the elements of the stream, and then flattening the
     * resulting elements into a new stream.
     * 
     * @param <R>     the element type of the new stream
     * @param mapper  a non-interfering, stateless function to apply to each 
     *                key-value pair which produces a stream of new values
     * @return        the new stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    public <R> MapStream<K, R> flatMapValue(BiFunction<? super K, ? super V, ? extends Stream<? extends R>> mapper) {
        requireNonNull(mapper);
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

    /**
     * Returns a stream where the value-component of this stream has been 
     * replaced by the result of applying the specified mapping function to the 
     * entry. Each mapped stream is 
     * {@link java.util.stream.BaseStream#close() closed} after its contents 
     * have been placed into this stream. (If a mapped stream is {@code null} an 
     * empty stream is used, instead.)
     * <p>
     * This is an intermediate operation.
     *
     * <p>
     * The {@code flatMap()} operation has the effect of applying a one-to-many
     * transformation to the elements of the stream, and then flattening the
     * resulting elements into a new stream.
     * 
     * @param <R>     the element type of the new stream
     * @param mapper  a non-interfering, stateless function to apply to each 
     *                key-value pair which produces a stream of new values
     * @return        the new stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    public <R> MapStream<K, R> flatMapValue(Function<? super V, ? extends Stream<? extends R>> mapper) {
        requireNonNull(mapper);
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

    /**
     * Returns an {@code IntStream} consisting of the results of replacing each
     * element of this stream with the contents of a mapped stream produced by
     * applying the provided mapping function to each element.  Each mapped
     * stream is {@link java.util.stream.BaseStream#close() closed} after its
     * contents have been placed into this stream.  (If a mapped stream is
     * {@code null} an empty stream is used, instead.)
     * <p>
     * This is an intermediate operation.
     *
     * @param mapper  a non-interfering, stateless function to apply to each 
     *                element which produces a stream of new elements
     * @return        the new stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     * 
     * @see #flatMap(Function)
     */
    @Override
    public IntStream flatMapToInt(Function<? super Map.Entry<K, V>, ? extends IntStream> mapper) {
        return inner.flatMapToInt(requireNonNull(mapper));
    }

    /**
     * Returns an {@code IntStream} consisting of the results of replacing each
     * key-value pair of this stream with the contents of a mapped stream 
     * produced by applying the provided mapping function to each element. Each 
     * mapped stream is {@link java.util.stream.BaseStream#close() closed} after 
     * its contents have been placed into this stream.  (If a mapped stream is
     * {@code null} an empty stream is used, instead.)
     * <p>
     * This is an intermediate operation.
     *
     * @param mapper  a non-interfering, stateless function to apply to each 
     *                key-value pair which produces a stream of new elements
     * @return        the new stream
     * 
     * @see #flatMap(Function)
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    public IntStream flatMapToInt(BiFunction<? super K, ? super V, ? extends IntStream> mapper) {
        requireNonNull(mapper);
        return inner.flatMapToInt(e -> mapper.apply(e.getKey(), e.getValue()));
    }

    /**
     * Returns an {@code LongStream} consisting of the results of replacing each
     * element of this stream with the contents of a mapped stream produced by
     * applying the provided mapping function to each element.  Each mapped
     * stream is {@link java.util.stream.BaseStream#close() closed} after its
     * contents have been placed into this stream.  (If a mapped stream is
     * {@code null} an empty stream is used, instead.)
     * <p>
     * This is an intermediate operation.
     *
     * @param mapper  a non-interfering, stateless function to apply to each 
     *                element which produces a stream of new elements
     * @return        the new stream
     * 
     * @see #flatMap(Function)
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    @Override
    public LongStream flatMapToLong(Function<? super Map.Entry<K, V>, ? extends LongStream> mapper) {
        return inner.flatMapToLong(requireNonNull(mapper));
    }

    /**
     * Returns an {@code LongStream} consisting of the results of replacing each
     * key-value pair of this stream with the contents of a mapped stream 
     * produced by applying the provided mapping function to each element. Each 
     * mapped stream is {@link java.util.stream.BaseStream#close() closed} after 
     * its contents have been placed into this stream.  (If a mapped stream is
     * {@code null} an empty stream is used, instead.)
     * <p>
     * This is an intermediate operation.
     *
     * @param mapper  a non-interfering, stateless function to apply to each 
     *                key-value pair which produces a stream of new elements
     * @return        the new stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     * 
     * @see #flatMap(Function)
     */
    public LongStream flatMapToLong(BiFunction<? super K, ? super V, ? extends LongStream> mapper) {
        requireNonNull(mapper);
        return inner.flatMapToLong(e -> mapper.apply(e.getKey(), e.getValue()));
    }

    /**
     * Returns an {@code DoubleStream} consisting of the results of replacing
     * each element of this stream with the contents of a mapped stream produced
     * by applying the provided mapping function to each element.  Each mapped
     * stream is {@link java.util.stream.BaseStream#close() closed} after its
     * contents have placed been into this stream.  (If a mapped stream is
     * {@code null} an empty stream is used, instead.)
     * <p>
     * This is an intermediate operation.
     *
     * @param mapper  a non-interfering, stateless function to apply to each 
     *                element which produces a stream of new elements
     * @return        the new stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     * 
     * @see #flatMap(Function)
     */
    @Override
    public DoubleStream flatMapToDouble(Function<? super Map.Entry<K, V>, ? extends DoubleStream> mapper) {
        return inner.flatMapToDouble(requireNonNull(mapper));
    }

    /**
     * Returns an {@code DoubleStream} consisting of the results of replacing
     * each key-value pair of this stream with the contents of a mapped stream 
     * produced by applying the provided mapping function to each element. Each 
     * mapped stream is {@link java.util.stream.BaseStream#close() closed} after 
     * its contents have placed been into this stream.  (If a mapped stream is
     * {@code null} an empty stream is used, instead.)
     * <p>
     * This is an intermediate operation.
     *
     * @param mapper  a non-interfering, stateless function to apply to each 
     *                key-value pair which produces a stream of new elements
     * @return        the new stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     * 
     * @see #flatMap(Function)
     */
    public DoubleStream flatMapToDouble(BiFunction<? super K, ? super V, ? extends DoubleStream> mapper) {
        requireNonNull(mapper);
        return inner.flatMapToDouble(e -> mapper.apply(e.getKey(), e.getValue()));
    }

    /**
     * Returns a stream of only the keys in this {@code MapStream}.
     * <p>
     * This is an intermediate operation.
     * 
     * @return  a new stream of all keys
     */
    public Stream<K> keys() {
        return inner.map(Map.Entry::getKey);
    }

    /**
     * Returns a stream of only the values in this {@code MapStream}.
     * <p>
     * This is an intermediate operation.
     * 
     * @return  a new stream of all values
     */
    public Stream<V> values() {
        return inner.map(Map.Entry::getValue);
    }

    /**
     * Returns a stream consisting of the distinct elements (according to
     * {@link Object#equals(Object)}) of this stream.
     * <p>
     * For ordered streams, the selection of distinct elements is stable
     * (for duplicated elements, the element appearing first in the encounter
     * order is preserved.)  For unordered streams, no stability guarantees
     * are made.
     * <p>
     * This is a stateful intermediate operation.
     *
     * <p>
     * Preserving stability for {@code distinct()} in parallel pipelines is
     * relatively expensive (requires that the operation act as a full barrier,
     * with substantial buffering overhead), and stability is often not needed.
     * Using an unordered stream source (such as {@link #generate(Supplier)})
     * or removing the ordering constraint with {@link #unordered()} may result
     * in significantly more efficient execution for {@code distinct()} in parallel
     * pipelines, if the semantics of your situation permit.  If consistency
     * with encounter order is required, and you are experiencing poor performance
     * or memory utilization with {@code distinct()} in parallel pipelines,
     * switching to sequential execution with {@link #sequential()} may improve
     * performance.
     *
     * @return the new stream
     */
    @Override
    public MapStream<K, V> distinct() {
        inner = inner.distinct();
        return this;
    }
    
    /**
     * Returns a stream consisting of the distinct elements (according to
     * {@link Object#equals(Object)}) of this stream based on the key only. If 
     * the same key is encountered multiple times, only the first occurence will
     * be allowed to pass.
     * <p>
     * This is a stateful intermediate operation.
     *
     * @return the new stream
     */
    public MapStream<K, V> distinctKeys() {
        final Set<K> temp = newSetFromMap(new ConcurrentHashMap<>());
        
        inner = inner.flatMap(e -> 
            temp.add(e.getKey()) 
                ? Stream.of(e) 
                : Stream.empty()
        );
        
        return this;
    }
    
    /**
     * Returns a stream consisting of the distinct elements (according to
     * {@link Object#equals(Object)}) of this stream based on the value only. If 
     * the same value is encountered multiple times, only the first occurence 
     * will be allowed to pass.
     * <p>
     * This is a stateful intermediate operation.
     *
     * @return the new stream
     */
    public MapStream<K, V> distinctValues() {
        final Set<V> temp = newSetFromMap(new ConcurrentHashMap<>());
        
        inner = inner.flatMap(e -> 
            temp.add(e.getValue()) 
                ? Stream.of(e) 
                : Stream.empty()
        );
        
        return this;
    }
    
    /**
     * Returns a stream consisting of the distinct elements (according to
     * {@link Object#equals(Object)}) of this stream based on the key only. If 
     * the same key is encountered multiple times, the specified value merger 
     * will be used to determine which value can pass.
     * <p>
     * This operation will consume the wrapped stream and produce a new one. The
     * complexity of this operation is therefore O(n).
     * <p>
     * This is a stateful intermediate operation.
     *
     * @param merger  the merging operation to use
     * @return        the new stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    public MapStream<K, V> distinctKeys(BinaryOperator<V> merger) {
        requireNonNull(merger);
        final boolean parallel = isParallel();
        final Map<K, V> result = parallel
            ? Collections.synchronizedMap(new LinkedHashMap<>())
            : new LinkedHashMap<>();
        
        inner.forEachOrdered(e ->
            result.compute(e.getKey(), (k, v) -> 
                v == null 
                    ? e.getValue()
                    : merger.apply(e.getValue(), v)
            ));
        
        return MapStream.of(result, parallel);
    }
    
    /**
     * Returns a stream consisting of the distinct elements (according to
     * {@link Object#equals(Object)}) of this stream based on the value only. If 
     * the same value is encountered multiple times, the specified key merger 
     * will be used to determine which value can pass.
     * <p>
     * This operation will consume the wrapped stream and produce a new one. The
     * complexity of this operation is therefore O(n).
     * <p>
     * This is a stateful intermediate operation.
     *
     * @param merger  the merging operation to use
     * @return        the new stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    public MapStream<K, V> distinctValues(BinaryOperator<K> merger) {
        requireNonNull(merger);
        return MapStream.flip(MapStream.flip(this).distinctKeys(merger));
    }

    /**
     * Returns a stream consisting of the elements of this stream, sorted
     * according to natural order.  If the elements of this stream are not
     * {@code Comparable}, a {@code java.lang.ClassCastException} may be thrown
     * when the terminal operation is executed.
     * <p>
     * For ordered streams, the sort is stable.  For unordered streams, no
     * stability guarantees are made.
     * <p>
     * This is a stateful intermediate operation.
     *
     * @return the new stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    @Override
    public MapStream<K, V> sorted() {
        final Comparator<K> c = (a, b) -> {
            if (a == null && b == null) {
                return 0;
            } else if (b != null && a instanceof Comparable<?>) {
                @SuppressWarnings("unchecked")
                final Comparable<K> ac = (Comparable<K>) a;

                return ac.compareTo(b);
            }

            throw new UnsupportedOperationException("Can only sort keys that implement Comparable.");
        };

        inner = inner.sorted((a, b) -> c.compare(a.getKey(), b.getKey()));
        return this;
    }

    /**
     * Returns a stream consisting of the elements of this stream, sorted
     * according to the provided {@code Comparator}.
     * <p>
     * For ordered streams, the sort is stable.  For unordered streams, no
     * stability guarantees are made.
     * <p>
     * This is a stateful intermediate operation.
     *
     * @param comparator  a non-interfering, stateless {@code Comparator} to be 
     *                    used to compare stream elements
     * @return            the new stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    @Override
    public MapStream<K, V> sorted(Comparator<? super Map.Entry<K, V>> comparator) {
        inner = inner.sorted(requireNonNull(comparator));
        return this;
    }

    /**
     * Returns a stream consisting of the elements of this stream, sorted
     * according to the provided {@code Comparator} applied to the keys of the
     * entries.
     * <p>
     * For ordered streams, the sort is stable. For unordered streams, no
     * stability guarantees are made.
     * <p>
     * This is a stateful intermediate operation.
     *
     * @param comparator  a non-interfering, stateless {@code Comparator} to be 
     *                    used to compare entity keys
     * @return            the new stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    public MapStream<K, V> sortedByKey(Comparator<K> comparator) {
        inner = inner.sorted(byKeyOnly(requireNonNull(comparator)));
        return this;
    }

    /**
     * Returns a stream consisting of the elements of this stream, sorted
     * according to the provided {@code Comparator} applied to the values of the
     * entries.
     * <p>
     * For ordered streams, the sort is stable. For unordered streams, no
     * stability guarantees are made.
     * <p>
     * This is a stateful intermediate operation.
     *
     * @param comparator  a non-interfering, stateless {@code Comparator} to be 
     *                    used to compare entity values
     * @return            the new stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    public MapStream<K, V> sortedByValue(Comparator<V> comparator) {
        inner = inner.sorted(byValueOnly(requireNonNull(comparator)));
        return this;
    }

    /**
     * Returns a stream consisting of the elements of this stream, additionally
     * performing the provided action on each element as elements are consumed
     * from the resulting stream.
     * <p>
     * This is an intermediate operation.
     * <p>
     * For parallel stream pipelines, the action may be called at
     * whatever time and in whatever thread the element is made available by the
     * upstream operation.  If the action modifies shared state,
     * it is responsible for providing the required synchronization.
     *
     * <p> This method exists mainly to support debugging, where you want
     * to see the elements as they flow past a certain point in a pipeline:
     * <pre>{@code
     *     Stream.of("one", "two", "three", "four")
     *         .filter(e -> e.length() > 3)
     *         .peek(e -> System.out.println("Filtered value: " + e))
     *         .map(String::toUpperCase)
     *         .peek(e -> System.out.println("Mapped value: " + e))
     *         .collect(Collectors.toList());
     * }</pre>
     *
     * @param action  a non-interfering action to perform on the elements as
     *                they are consumed from the stream
     * @return        the new stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    @Override
    public MapStream<K, V> peek(Consumer<? super Map.Entry<K, V>> action) {
        inner = inner.peek(requireNonNull(action));
        return this;
    }

    /**
     * Returns a stream consisting of the key-value pairs of this stream, 
     * additionally performing the provided action on each element as elements 
     * are consumed from the resulting stream.
     * <p>
     * This is an intermediate operation.
     * <p>
     * For parallel stream pipelines, the action may be called at
     * whatever time and in whatever thread the element is made available by the
     * upstream operation.  If the action modifies shared state,
     * it is responsible for providing the required synchronization.
     *
     * <p> This method exists mainly to support debugging, where you want
     * to see the elements as they flow past a certain point in a pipeline:
     * <pre>{@code
     *     Stream.of("one", "two", "three", "four")
     *         .filter(e -> e.length() > 3)
     *         .peek(e -> System.out.println("Filtered value: " + e))
     *         .map(String::toUpperCase)
     *         .peek(e -> System.out.println("Mapped value: " + e))
     *         .collect(Collectors.toList());
     * }</pre>
     *
     * @param action  a non-interfering action to perform on the key-value pairs 
     *                as they are consumed from the stream
     * @return        the new stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    public MapStream<K, V> peek(BiConsumer<? super K, ? super V> action) {
        requireNonNull(action);
        inner = inner.peek(e -> action.accept(e.getKey(), e.getValue()));
        return this;
    }

    /**
     * Returns a stream consisting of the elements of this stream, truncated
     * to be no longer than {@code maxSize} in length.
     * <p>
     * This is a short-circuiting stateful intermediate operation.
     *
     * <p>
     * While {@code limit()} is generally a cheap operation on sequential
     * stream pipelines, it can be quite expensive on ordered parallel pipelines,
     * especially for large values of {@code maxSize}, since {@code limit(n)}
     * is constrained to return not just any <em>n</em> elements, but the
     * <em>first n</em> elements in the encounter order.  Using an unordered
     * stream source (such as {@link #generate(Supplier)}) or removing the
     * ordering constraint with {@link #unordered()} may result in significant
     * speedups of {@code limit()} in parallel pipelines, if the semantics of
     * your situation permit.  If consistency with encounter order is required,
     * and you are experiencing poor performance or memory utilization with
     * {@code limit()} in parallel pipelines, switching to sequential execution
     * with {@link #sequential()} may improve performance.
     *
     * @param maxSize  the number of elements the stream should be limited to
     * @return         the new stream
     * @throws         IllegalArgumentException if {@code maxSize} is negative
     */
    @Override
    public MapStream<K, V> limit(long maxSize) {
        inner = inner.limit(maxSize);
        return this;
    }

    /**
     * Returns a stream consisting of the remaining elements of this stream
     * after discarding the first {@code n} elements of the stream.
     * If this stream contains fewer than {@code n} elements then an
     * empty stream will be returned.
     * <p>
     * This is a stateful intermediate operation.
     *
     * <p>
     * While {@code skip()} is generally a cheap operation on sequential
     * stream pipelines, it can be quite expensive on ordered parallel pipelines,
     * especially for large values of {@code n}, since {@code skip(n)}
     * is constrained to skip not just any <em>n</em> elements, but the
     * <em>first n</em> elements in the encounter order.  Using an unordered
     * stream source (such as {@link #generate(Supplier)}) or removing the
     * ordering constraint with {@link #unordered()} may result in significant
     * speedups of {@code skip()} in parallel pipelines, if the semantics of
     * your situation permit.  If consistency with encounter order is required,
     * and you are experiencing poor performance or memory utilization with
     * {@code skip()} in parallel pipelines, switching to sequential execution
     * with {@link #sequential()} may improve performance.
     *
     * @param n  the number of leading elements to skip
     * @return   the new stream
     * @throws   IllegalArgumentException if {@code n} is negative
     */
    @Override
    public MapStream<K, V> skip(long n) {
        inner = inner.skip(n);
        return this;
    }

    /**
     * Performs an action for each element of this stream.
     * <p>
     * This is a terminal operation.
     * <p>
     * The behavior of this operation is explicitly nondeterministic.
     * For parallel stream pipelines, this operation does <em>not</em>
     * guarantee to respect the encounter order of the stream, as doing so
     * would sacrifice the benefit of parallelism.  For any given element, the
     * action may be performed at whatever time and in whatever thread the
     * library chooses.  If the action accesses shared state, it is
     * responsible for providing the required synchronization.
     *
     * @param action a non-interfering action to perform on the elements
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    @Override
    public void forEach(Consumer<? super Map.Entry<K, V>> action) {
        inner.forEach(requireNonNull(action));
    }

    /**
     * Performs an action for each key-value pair of this stream.
     * <p>
     * This is a terminal operation.
     * <p>
     * The behavior of this operation is explicitly nondeterministic.
     * For parallel stream pipelines, this operation does <em>not</em>
     * guarantee to respect the encounter order of the stream, as doing so
     * would sacrifice the benefit of parallelism.  For any given element, the
     * action may be performed at whatever time and in whatever thread the
     * library chooses.  If the action accesses shared state, it is
     * responsible for providing the required synchronization.
     *
     * @param action a non-interfering action to perform on the key-value pairs
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    public void forEach(BiConsumer<? super K, ? super V> action) {
        requireNonNull(action);
        inner.forEach(e -> action.accept(e.getKey(), e.getValue()));
    }

    /**
     * Performs an action for each element of this stream, in the encounter
     * order of the stream if the stream has a defined encounter order.
     * <p>
     * This is a terminal operation.
     * <p>
     * This operation processes the elements one at a time, in encounter
     * order if one exists.  Performing the action for one element
     * <i>happens-before</i> performing the action for subsequent elements, but 
     * for any given element, the action may be performed in whatever thread the 
     * library chooses.
     *
     * @param action  a non-interfering action to perform on the elements
     * @throws NullPointerException if any of the provided parameters are {@code null}
     * @see #forEach(Consumer)
     */
    @Override
    public void forEachOrdered(Consumer<? super Map.Entry<K, V>> action) {
        inner.forEachOrdered(requireNonNull(action));
    }

    /**
     * Performs an action for each key-value pair of this stream, in the 
     * encounter order of the stream if the stream has a defined encounter 
     * order.
     * <p>
     * This is a terminal operation.
     * <p>
     * This operation processes the key-value pairs one at a time, in encounter
     * order if one exists. Performing the action for one element
     * <i>happens-before</i> performing the action for subsequent key-value 
     * pairs, but for any given element, the action may be performed in whatever 
     * thread the library chooses.
     *
     * @param action  a non-interfering action to perform on the key-value pairs
     * @throws NullPointerException if any of the provided parameters are {@code null}
     * @see #forEach(Consumer)
     */
    public void forEachOrdered(BiConsumer<? super K, ? super V> action) {
        requireNonNull(action);
        inner.forEachOrdered(e -> action.accept(e.getKey(), e.getValue()));
    }

    /**
     * Returns an array containing the elements of this stream.
     * <p>
     * This is a terminal operation.
     *
     * @return an array containing the elements of this stream
     */
    @Override
    public Object[] toArray() {
        return inner.toArray();
    }

    /**
     * Returns an array containing the elements of this stream, using the
     * provided {@code generator} function to allocate the returned array, as
     * well as any additional arrays that might be required for a partitioned
     * execution or for resizing.
     * <p>
     * This is a terminal operation.
     *
     * <p>
     * The generator function takes an integer, which is the size of the
     * desired array, and produces an array of the desired size.  This can be
     * concisely expressed with an array constructor reference:
     * <pre>{@code
     *     Person[] men = people.stream()
     *                          .filter(p -> p.getGender() == MALE)
     *                          .toArray(Person[]::new);
     * }</pre>
     *
     * @param <A>        the element type of the resulting array
     * @param generator  a function which produces a new array of the desired
     *                   type and the provided length
     * @return           an array containing the elements in this stream
     * @throws           ArrayStoreException if the runtime type of the array 
     *                   returned from the array generator is not a supertype of 
     *                   the runtime type of every element in this stream
     * @throws           NullPointerException if any of the provided parameters are {@code null}
     */
    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        return inner.toArray(generator);
    }

    /**
     * Performs a reduction on the elements of this stream, using the provided 
     * identity value and an associative accumulation function, and returns the 
     * reduced value. This is equivalent to:
     * <pre>{@code
     *     T result = identity;
     *     for (T element : this stream)
     *         result = accumulator.apply(result, element)
     *     return result;
     * }</pre>
     *
     * but is not constrained to execute sequentially.
     * <p>
     * The {@code identity} value must be an identity for the accumulator
     * function. This means that for all {@code t},
     * {@code accumulator.apply(identity, t)} is equal to {@code t}.
     * The {@code accumulator} function must be an associative function.
     * <p>
     * This is a terminal operation.
     *
     * <p> Sum, min, max, average, and string concatenation are all special
     * cases of reduction. Summing a stream of numbers can be expressed as:
     *
     * <pre>{@code
     *     Integer sum = integers.reduce(0, (a, b) -> a+b);
     * }</pre>
     *
     * or:
     *
     * <pre>{@code
     *     Integer sum = integers.reduce(0, Integer::sum);
     * }</pre>
     * <p>
     * While this may seem a more roundabout way to perform an aggregation
     * compared to simply mutating a running total in a loop, reduction
     * operations parallelize more gracefully, without needing additional
     * synchronization and with greatly reduced risk of data races.
     *
     * @param identity     the identity value for the accumulating function
     * @param accumulator  an associative, non-interfering stateless function 
     *                     for combining two values
     * @return             the result of the reduction
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    @Override
    public Map.Entry<K, V> reduce(Map.Entry<K, V> identity, BinaryOperator<Map.Entry<K, V>> accumulator) {
        return inner.reduce(requireNonNull(identity), requireNonNull(accumulator));
    }

    /**
     * Performs a reduction on the elements of this stream, using an associative 
     * accumulation function, and returns an {@code Optional} describing the 
     * reduced value, if any. This is equivalent to:
     * <pre>{@code
     *     boolean foundAny = false;
     *     T result = null;
     *     for (T element : this stream) {
     *         if (!foundAny) {
     *             foundAny = true;
     *             result = element;
     *         }
     *         else
     *             result = accumulator.apply(result, element);
     *     }
     *     return foundAny ? Optional.of(result) : Optional.empty();
     * }</pre>
     *
     * but is not constrained to execute sequentially.
     * <p>
     * The {@code accumulator} function must be an associative function.
     * <p>
     * This is a terminal operation.
     *
     * @param accumulator  an associative, non-interfering, stateless function 
     *                     for combining two values
     * @return             an {@link Optional} describing the result of the 
     *                     reduction
     * @throws NullPointerException if the result of the reduction is null or
     * if any of the provided parameters are {@code null}
     * 
     * @see #reduce(BinaryOperator)
     * @see #min(Comparator)
     * @see #max(Comparator)
     */
    @Override
    public Optional<Map.Entry<K, V>> reduce(BinaryOperator<Map.Entry<K, V>> accumulator) {
        return inner.reduce(requireNonNull(accumulator));
    }

    /**
     * Performs a reduction on the elements of this stream, using the provided 
     * identity, accumulation and combining functions.  This is equivalent to:
     * <pre>{@code
     *     U result = identity;
     *     for (T element : this stream)
     *         result = accumulator.apply(result, element)
     *     return result;
     * }</pre>
     *
     * but is not constrained to execute sequentially.
     * <p>
     * The {@code identity} value must be an identity for the combiner
     * function.  This means that for all {@code u}, {@code combiner(identity, u)}
     * is equal to {@code u}.  Additionally, the {@code combiner} function
     * must be compatible with the {@code accumulator} function; for all
     * {@code u} and {@code t}, the following must hold:
     * <pre>{@code
     *     combiner.apply(u, accumulator.apply(identity, t)) == accumulator.apply(u, t)
     * }</pre>
     * <p>
     * This is a terminal operation.
     *
     * <p> Many reductions using this form can be represented more simply
     * by an explicit combination of {@code map} and {@code reduce} operations.
     * The {@code accumulator} function acts as a fused mapper and accumulator,
     * which can sometimes be more efficient than separate mapping and reduction,
     * such as when knowing the previously reduced value allows you to avoid
     * some computation.
     *
     * @param <U>          the type of the result
     * @param identity     the identity value for the combiner function
     * @param accumulator  an associative, non-interfering, stateless function 
     *                     for incorporating an additional element into a result
     * @param combiner     an associative, non-interfering, stateless
     *                     function for combining two values, which must be
     *                     compatible with the accumulator function
     * @return             the result of the reduction
     * @throws NullPointerException if any of the provided parameters are {@code null}
     * 
     * @see #reduce(BinaryOperator)
     */
    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super Map.Entry<K, V>, U> accumulator, BinaryOperator<U> combiner) {
        // identity is nullable
        requireNonNull(accumulator);
        requireNonNull(combiner);
        return inner.reduce(identity, accumulator, combiner);
    }

    /**
     * Performs a mutable reduction operation on the elements of this stream. A 
     * mutable reduction is one in which the reduced value is a mutable result 
     * container, such as an {@code ArrayList}, and elements are incorporated by 
     * updating the state of the result rather than by replacing the result. 
     * This produces a result equivalent to:
     * <pre>{@code
     *     R result = supplier.get();
     *     for (T element : this stream)
     *         accumulator.accept(result, element);
     *     return result;
     * }</pre>
     * <p>
     * Like {@link #reduce(BinaryOperator)} , {@code collect} operations
     * can be parallelized without requiring additional synchronization.
     * <p>
     * This is a terminal operation.
     *
     * <p> There are many existing classes in the JDK whose signatures are
     * well-suited for use with method references as arguments to {@code collect()}.
     * For example, the following will accumulate strings into an {@code ArrayList}:
     * <pre>{@code
     *     List<String> asList = stringStream.collect(ArrayList::new, ArrayList::add,
     *                                                ArrayList::addAll);
     * }</pre>
     * <p>
     * The following will take a stream of strings and concatenates them into a
     * single string:
     * <pre>{@code
     *     String concat = stringStream.collect(StringBuilder::new, StringBuilder::append,
     *                                          StringBuilder::append)
     *                                 .toString();
     * }</pre>
     *
     * @param <R>          type of the result
     * @param supplier     a function that creates a new result container. For a
     *                     parallel execution, this function may be called
     *                     multiple times and must return a fresh value each 
     *                     time.
     * @param accumulator  an associative, non-interfering, stateless function 
     *                     for incorporating an additional element into a result
     * @param combiner     an associative, non-interfering, stateless function 
     *                     for combining two values, which must be compatible 
     *                     with the accumulator function
     * @return             the result of the reduction
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super Map.Entry<K, V>> accumulator, BiConsumer<R, R> combiner) {
        return inner.collect(requireNonNull(supplier), requireNonNull(accumulator), requireNonNull(combiner));
    }

    /**
     * Performs a mutable reduction operation on the elements of this stream 
     * using a {@code Collector}. A {@code Collector} encapsulates the functions 
     * used as arguments to {@link #collect(Supplier, BiConsumer, BiConsumer)}, 
     * allowing for reuse of collection strategies and composition of collect 
     * operations such as multiple-level grouping or partitioning.
     * <p>
     * If the stream is parallel, and the {@code Collector} is 
     * {@link Collector.Characteristics#CONCURRENT concurrent}, and either the 
     * stream is unordered or the collector is
     * {@link Collector.Characteristics#UNORDERED unordered},
     * then a concurrent reduction will be performed (see {@link Collector} for
     * details on concurrent reduction.)
     * <p>
     * This is a terminal operation.
     * <p>
     * When executed in parallel, multiple intermediate results may be
     * instantiated, populated, and merged so as to maintain isolation of
     * mutable data structures.  Therefore, even when executed in parallel
     * with non-thread-safe data structures (such as {@code ArrayList}), no
     * additional synchronization is needed for a parallel reduction.
     *
     * <p>
     * The following will accumulate strings into an ArrayList:
     * <pre>{@code
     *     List<String> asList = stringStream.collect(Collectors.toList());
     * }</pre>
     * <p>
     * The following will classify {@code Person} objects by city:
     * <pre>{@code
     *     Map<String, List<Person>> peopleByCity
     *         = personStream.collect(Collectors.groupingBy(Person::getCity));
     * }</pre>
     * <p>
     * The following will classify {@code Person} objects by state and city,
     * cascading two {@code Collector}s together:
     * <pre>{@code
     *     Map<String, Map<String, List<Person>>> peopleByStateAndCity
     *         = personStream.collect(Collectors.groupingBy(Person::getState,
     *                                                      Collectors.groupingBy(Person::getCity)));
     * }</pre>
     *
     * @param <R>        the type of the result
     * @param <A>        the intermediate accumulation type of the 
     *                   {@code Collector}
     * @param collector  the {@code Collector} describing the reduction
     * @return           the result of the reduction
     * @throws NullPointerException if any of the provided parameters are {@code null}
     * 
     * @see #collect(Supplier, BiConsumer, BiConsumer)
     * @see Collectors
     */
    @Override
    public <R, A> R collect(Collector<? super Map.Entry<K, V>, A, R> collector) {
        return inner.collect(requireNonNull(collector));
    }

    /**
     * Returns a new {@code MapStream} where the key component is calculated by
     * applying the specified grouper function to the values of this stream and
     * the value is a stream consisting of all the entries that produced the
     * same key.
     * <p>
     * This is a stateful intermediate operation.
     * 
     * @param <K2>     the type of the new key
     * @param grouper  the function to use for grouping entries
     * @return         the new stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    public <K2> MapStream<K2, List<V>> groupingBy(Function<V, K2> grouper) {
        requireNonNull(grouper);
        return inner.map(Map.Entry::getValue)
            .collect(CollectorUtil.groupBy(grouper));
    }

    /**
     * Returns the minimum element of this stream according to the provided
     * {@code Comparator}. This is a special case of a reduction.
     * <p>
     * This is a terminal operation.
     *
     * @param comparator  a non-interfering, stateless {@code Comparator} to 
     *                    compare elements of this stream
     * @return            an {@code Optional} describing the minimum element of 
     *                    this stream, or an empty {@code Optional} if the 
     *                    stream is empty
     * 
     * @throws NullPointerException if the minimum element is null or if any of the
     * provided parameters are {@code null}
     */
    @Override
    public Optional<Map.Entry<K, V>> min(Comparator<? super Map.Entry<K, V>> comparator) {
        return inner.min(requireNonNull(comparator));
    }

    /**
     * Returns the minimum element of this stream according to the provided
     * {@code Comparator} applied to the key-components of this stream. This is 
     * a special case of a reduction.
     * <p>
     * This is a terminal operation.
     *
     * @param comparator  a non-interfering, stateless {@code Comparator} to 
     *                    compare keys of this stream
     * @return            an {@code Optional} describing the minimum element of 
     *                    this stream, or an empty {@code Optional} if the 
     *                    stream is empty
     * 
     * @throws NullPointerException if the minimum element is null or if any of the
     *      * provided parameters are {@code null}
     */
    public Optional<Map.Entry<K, V>> minByKey(Comparator<K> comparator) {
        return inner.min(byKeyOnly(requireNonNull(comparator)));
    }

    /**
     * Returns the minimum element of this stream according to the provided
     * {@code Comparator} applied to the value-components of this stream. This 
     * is a special case of a reduction.
     * <p>
     * This is a terminal operation.
     *
     * @param comparator  a non-interfering, stateless {@code Comparator} to 
     *                    compare values of this stream
     * @return            an {@code Optional} describing the minimum element of 
     *                    this stream, or an empty {@code Optional} if the 
     *                    stream is empty
     * 
     * @throws NullPointerException if the minimum element is null or if any of the
     *         provided parameters are {@code null}
     */
    public Optional<Map.Entry<K, V>> minByValue(Comparator<V> comparator) {
        return inner.min(byValueOnly(requireNonNull(comparator)));
    }

    /**
     * Returns the maximum element of this stream according to the provided
     * {@code Comparator}.  This is a special case of a reduction.
     * <p>
     * This is a terminal operation.
     *
     * @param comparator  a non-interfering, stateless {@code Comparator} to 
     *                    compare elements of this stream
     * @return            an {@code Optional} describing the maximum element of 
     *                    this stream, or an empty {@code Optional} if the 
     *                    stream is empty
     * 
     * @throws NullPointerException if the maximum element is null or if any of the
     *         provided parameters are {@code null}
     */
    @Override
    public Optional<Map.Entry<K, V>> max(Comparator<? super Map.Entry<K, V>> comparator) {
        return inner.max(requireNonNull(comparator));
    }

    /**
     * Returns the maximum element of this stream according to the provided
     * {@code Comparator} applied to the key-components of this stream. This 
     * is a special case of a reduction.
     * <p>
     * This is a terminal operation.
     *
     * @param comparator  a non-interfering, stateless {@code Comparator} to 
     *                    compare keys of this stream
     * @return            an {@code Optional} describing the maximum element of 
     *                    this stream, or an empty {@code Optional} if the 
     *                    stream is empty
     * 
     * @throws NullPointerException if the maximum element is null or if any of the
     *         provided parameters are {@code null}
     */
    public Optional<Map.Entry<K, V>> maxByKey(Comparator<K> comparator) {
        return inner.max(byKeyOnly(requireNonNull(comparator)));
    }

    /**
     * Returns the maximum element of this stream according to the provided
     * {@code Comparator} applied to the value-components of this stream. This 
     * is a special case of a reduction.
     * <p>
     * This is a terminal operation.
     *
     * @param comparator  a non-interfering, stateless {@code Comparator} to 
     *                    compare values of this stream
     * @return            an {@code Optional} describing the maximum element of 
     *                    this stream, or an empty {@code Optional} if the 
     *                    stream is empty
     * 
     * @throws NullPointerException if the maximum element is null or if any of the
     *        provided parameters are {@code null}
     */
    public Optional<Map.Entry<K, V>> maxByValue(Comparator<V> comparator) {
        return inner.max(byValueOnly(requireNonNull(comparator)));
    }

    /**
     * Returns the count of elements in this stream.  This is a special case of
     * a reduction and is equivalent to:
     * <pre>{@code
     *     return mapToLong(e -> 1L).sum();
     * }</pre>
     * <p>
     * This is a terminal operation.
     *
     * @return  the count of elements in this stream
     */
    @Override
    public long count() {
        return inner.count();
    }

    /**
     * Returns whether any elements of this stream match the provided
     * predicate. May not evaluate the predicate on all elements if not
     * necessary for determining the result.  If the stream is empty then
     * {@code false} is returned and the predicate is not evaluated.
     * <p>
     * This is a short-circuiting terminal operation.
     *
     * <p>
     * This method evaluates the <em>existential quantification</em> of the
     * predicate over the elements of the stream (for some x P(x)).
     *
     * @param predicate  a non-interfering, stateless predicate to apply to 
     *                   elements of this stream
     * @return           {@code true} if any elements of the stream match the 
     *                   provided predicate, otherwise {@code false}
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    @Override
    public boolean anyMatch(Predicate<? super Map.Entry<K, V>> predicate) {
        return inner.anyMatch(requireNonNull(predicate));
    }

    /**
     * Returns whether any key-value pairs of this stream match the provided
     * predicate. May not evaluate the predicate on all elements if not
     * necessary for determining the result. If the stream is empty then
     * {@code false} is returned and the predicate is not evaluated.
     * <p>
     * This is a short-circuiting terminal operation.
     *
     * <p>
     * This method evaluates the <em>existential quantification</em> of the
     * predicate over the elements of the stream (for some x P(x)).
     *
     * @param predicate  a non-interfering, stateless predicate to apply to 
     *                   key-value pairs of this stream
     * @return           {@code true} if any key-value pairs of the stream match 
     *                   the provided predicate, otherwise {@code false}
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    public boolean anyMatch(BiPredicate<? super K, ? super V> predicate) {
        requireNonNull(predicate);
        return inner.anyMatch(e -> predicate.test(e.getKey(), e.getValue()));
    }

    /**
     * Returns whether all elements of this stream match the provided predicate.
     * May not evaluate the predicate on all elements if not necessary for
     * determining the result.  If the stream is empty then {@code true} is
     * returned and the predicate is not evaluated.
     * <p>
     * This is a short-circuiting terminal operation.
     *
     * <p>
     * This method evaluates the <em>universal quantification</em> of the
     * predicate over the elements of the stream (for all x P(x)). If the
     * stream is empty, the quantification is said to be <em>vacuously
     * satisfied</em> and is always {@code true} (regardless of P(x)).
     *
     * @param predicate  a non-interfering, stateless predicate to apply to 
     *                   elements of this stream
     * @return           {@code true} if either all elements of the stream match 
     *                   the provided predicate or the stream is empty, 
     *                   otherwise {@code false}
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    @Override
    public boolean allMatch(Predicate<? super Map.Entry<K, V>> predicate) {
        return inner.allMatch(requireNonNull(predicate));
    }

    /**
     * Returns whether all key-value pairs of this stream match the provided 
     * predicate. May not evaluate the predicate on all key-value pairs if not 
     * necessary for determining the result. If the stream is empty then 
     * {@code true} is returned and the predicate is not evaluated.
     * <p>
     * This is a short-circuiting terminal operation.
     *
     * <p>
     * This method evaluates the <em>universal quantification</em> of the
     * predicate over the elements of the stream (for all x P(x)).  If the
     * stream is empty, the quantification is said to be <em>vacuously
     * satisfied</em> and is always {@code true} (regardless of P(x)).
     *
     * @param predicate  a non-interfering, stateless predicate to apply to 
     *                   key-value pairs of this stream
     * @return           {@code true} if either all key-value pairs of the 
     *                   stream match the provided predicate or the stream is 
     *                   empty, otherwise {@code false}
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    public boolean allMatch(BiPredicate<? super K, ? super V> predicate) {
        requireNonNull(predicate);
        return inner.allMatch(e -> predicate.test(e.getKey(), e.getValue()));
    }

    /**
     * Returns whether no elements of this stream match the provided predicate.
     * May not evaluate the predicate on all elements if not necessary for
     * determining the result.  If the stream is empty then {@code true} is
     * returned and the predicate is not evaluated.
     * <p>
     * This is a short-circuiting terminal operation.
     *
     * <p>
     * This method evaluates the <em>universal quantification</em> of the
     * negated predicate over the elements of the stream (for all x ~P(x)).  If
     * the stream is empty, the quantification is said to be vacuously satisfied
     * and is always {@code true}, regardless of P(x).
     *
     * @param predicate  a non-interfering, stateless predicate to apply to 
     *                   elements of this stream
     * @return           {@code true} if either no elements of the stream match 
     *                   the provided predicate or the stream is empty, 
     *                   otherwise {@code false}
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    @Override
    public boolean noneMatch(Predicate<? super Map.Entry<K, V>> predicate) {
        return inner.noneMatch(requireNonNull(predicate));
    }

    /**
     * Returns whether no key-value pairs of this stream match the provided 
     * predicate. May not evaluate the predicate on all key-value pairs if not 
     * necessary for determining the result. If the stream is empty then 
     * {@code true} is returned and the predicate is not evaluated.
     * <p>
     * This is a short-circuiting terminal operation.
     *
     * <p>
     * This method evaluates the <em>universal quantification</em> of the
     * negated predicate over the elements of the stream (for all x ~P(x)).  If
     * the stream is empty, the quantification is said to be vacuously satisfied
     * and is always {@code true}, regardless of P(x).
     *
     * @param predicate  a non-interfering, stateless predicate to apply to 
     *                   key-value pairs of this stream
     * @return           {@code true} if either no key-value pairs of the 
     *                   stream match the provided predicate or the stream is 
     *                   empty, otherwise {@code false}
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    public boolean noneMatch(BiPredicate<? super K, ? super V> predicate) {
        requireNonNull(predicate);
        return inner.noneMatch(e -> predicate.test(e.getKey(), e.getValue()));
    }

    /**
     * Returns an {@link Optional} describing the first element of this stream,
     * or an empty {@code Optional} if the stream is empty.  If the stream has
     * no encounter order, then any element may be returned.
     * <p>
     * This is a short-circuiting terminal operation.
     *
     * @return  an {@code Optional} describing the first element of this stream,
     *          or an empty {@code Optional} if the stream is empty
     * 
     * @throws NullPointerException if the element selected is null
     */
    @Override
    public Optional<Map.Entry<K, V>> findFirst() {
        return inner.findFirst();
    }

    /**
     * Returns an {@link Optional} describing some element of the stream, or an
     * empty {@code Optional} if the stream is empty.
     * <p>
     * This is a short-circuiting terminal operation.
     * <p>
     * The behavior of this operation is explicitly nondeterministic; it is
     * free to select any element in the stream.  This is to allow for maximal
     * performance in parallel operations; the cost is that multiple invocations
     * on the same source may not return the same result.  (If a stable result
     * is desired, use {@link #findFirst()} instead.)
     *
     * @return  an {@code Optional} describing some element of this stream, or an
     *          empty {@code Optional} if the stream is empty
     * 
     * @throws NullPointerException if the element selected is null
     * @see #findFirst()
     */
    @Override
    public Optional<Map.Entry<K, V>> findAny() {
        return inner.findAny();
    }

    /**
     * Returns an iterator for the elements of this stream.
     * <p>
     * This is a terminal operation.
     *
     * @return  the element iterator for this stream
     */
    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return inner.iterator();
    }

    /**
     * Returns a spliterator for the elements of this stream.
     * <p>
     * This is a terminal operation.
     *
     * @return  the element spliterator for this stream
     */
    @Override
    public Spliterator<Map.Entry<K, V>> spliterator() {
        return inner.spliterator();
    }

    /**
     * Returns whether this stream, if a terminal operation were to be executed,
     * would execute in parallel.  Calling this method after invoking an
     * terminal stream operation method may yield unpredictable results.
     *
     * @return {@code true} if this stream would execute in parallel if executed
     */
    @Override
    public boolean isParallel() {
        return inner.isParallel();
    }

    /**
     * Returns an equivalent stream that is sequential.  May return
     * itself, either because the stream was already sequential, or because
     * the underlying stream state was modified to be sequential.
     * <p>
     * This is an intermediate operation.
     *
     * @return  a sequential stream
     */
    @Override
    public MapStream<K, V> sequential() {
        inner = inner.sequential();
        return this;
    }

    /**
     * Returns an equivalent stream that is parallel.  May return
     * itself, either because the stream was already parallel, or because
     * the underlying stream state was modified to be parallel.
     * <p>
     * This is an intermediate operation.
     *
     * @return a parallel stream
     */
    @Override
    public MapStream<K, V> parallel() {
        inner = inner.parallel();
        return this;
    }

    /**
     * Returns an equivalent stream that is
     * <a href="package-summary.html#Ordering">unordered</a>.  May return
     * itself, either because the stream was already unordered, or because
     * the underlying stream state was modified to be unordered.
     * <p>
     * This is an intermediate operation.
     *
     * @return an unordered stream
     */
    @Override
    public MapStream<K, V> unordered() {
        inner = inner.unordered();
        return this;
    }

    /**
     * Returns an equivalent stream with an additional close handler.  Close
     * handlers are run when the {@link #close()} method
     * is called on the stream, and are executed in the order they were
     * added.  All close handlers are run, even if earlier close handlers throw
     * exceptions.  If any close handler throws an exception, the first
     * exception thrown will be relayed to the caller of {@code close()}, with
     * any remaining exceptions added to that exception as suppressed exceptions
     * (unless one of the remaining exceptions is the same exception as the
     * first exception, since an exception cannot suppress itself.)  May
     * return itself.
     * <p>
     * This is an intermediate operation.
     *
     * @param closeHandler  a task to execute when the stream is closed
     * @return              a stream with a handler that is run if the stream is 
     *                      closed
     */
    @Override
    public MapStream<K, V> onClose(Runnable closeHandler) {
        inner = inner.onClose(requireNonNull(closeHandler));
        return this;
    }

    /**
     * Closes this stream, causing all close handlers for this stream pipeline
     * to be called.
     *
     * @see AutoCloseable#close()
     */
    @Override
    public void close() {
        inner.close();
    }

    /**
     * Accumulates all key-value pairs of this {@code MapStream} into a {@code Map}.
     * <p>
     * If the mapped keys contains duplicates (according to
     * {@link Object#equals(Object)}), an {@code IllegalStateException} is
     * thrown when the collection operation is performed. If the mapped keys
     * may have duplicates, use {@link #toMap(BinaryOperator)}
     * instead.
     *
     * <p>
     * The returned {@code Map} is not concurrent. For parallel stream
     * pipelines, the {@code combiner} function operates by merging the keys
     * from one map into another, which can be an expensive operation. If it is
     * not required that results are inserted into the {@code Map} in encounter
     * order, using {@link #toConcurrentMap()}
     * may offer better parallel performance.
     * 
     * @return  a {@code Map} whose keys and values are identical to the entries
     *          of this stream
     *
     * @see #toMap(BinaryOperator)
     * @see #toConcurrentMap()
     */
    public Map<K, V> toMap() {
        return inner.collect(Collectors.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue
        ));
    }
    
    /**
     * Accumulates all key-value pairs of this {@code MapStream} into a {@code Map}.
     * <p>
     * If the mapped keys contains duplicates (according to 
     * {@link Object#equals(Object)}), the values are merged using the provided 
     * merging function.
     *
     * <p>
     * There are multiple ways to deal with collisions between multiple elements
     * mapping to the same key.  The other forms of {@code toMap} simply use
     * a merge function that throws unconditionally, but you can easily write
     * more flexible merge policies.
     *
     * <p>
     * The returned {@code Map} is not concurrent. For parallel stream
     * pipelines, the {@code combiner} function operates by merging the keys
     * from one map into another, which can be an expensive operation. If it is
     * not required that results are merged into the {@code Map} in encounter
     * order, using {@link #toConcurrentMap(BinaryOperator)}
     * may offer better parallel performance.
     *
     * @param mergeFunction  a merge function, used to resolve collisions 
     *                       between values associated with the same key, as 
     *                       supplied to {@link Map#merge(Object, Object, BiFunction)}
     * @return               a {@code Map} whose keys and values are identical 
     *                       to the entries of this stream with any collisions 
     *                       handled
     * @throws NullPointerException if any of the provided parameters are {@code null}
     *
     * @see #toMap()
     * @see #toConcurrentMap(BinaryOperator)
     */
    public Map<K, V> toMap(BinaryOperator<V> mergeFunction) {
        requireNonNull(mergeFunction);
        return inner.collect(Collectors.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue,
            mergeFunction
        ));
    }
    
    /**
     * Accumulates all key-value pairs of this {@code MapStream} into a 
     * {@code ConcurrentMap}.
     * <p>
     * If the mapped keys contains duplicates (according to
     * {@link Object#equals(Object)}), an {@code IllegalStateException} is
     * thrown when the collection operation is performed. If the mapped keys
     * may have duplicates, use {@link #toConcurrentMap(BinaryOperator)} instead.
     *
     * <p>
     * It is common for either the key or the value to be the input elements.
     * In this case, the utility method
     * {@link java.util.function.Function#identity()} may be helpful.
     * <p>
     * This is a {@link Collector.Characteristics#CONCURRENT concurrent} and
     * {@link Collector.Characteristics#UNORDERED unordered} Collector.
     *
     * @return  a {@code ConcurrentMap} whose keys are taken from this stream
     *
     * @see #toMap()
     * @see #toConcurrentMap(BinaryOperator)
     */
    public Map<K, V> toConcurrentMap() {
        return inner.collect(Collectors.toConcurrentMap(
            Map.Entry::getKey,
            Map.Entry::getValue
        ));
    }
    
    /**
     * Accumulates all key-value pairs of this {@code MapStream} into a 
     * {@code ConcurrentMap}.
     * <p>
     * If the mapped keys contains duplicates (according to 
     * {@link Object#equals(Object)}), the value mapping function is applied to 
     * each equal element, and the results are merged using the provided merging 
     * function.
     *
     * <p>
     * There are multiple ways to deal with collisions between multiple elements
     * mapping to the same key.  The other forms of {@code toConcurrentMap} simply use
     * a merge function that throws unconditionally, but you can easily write
     * more flexible merge policies.
     * 
     * @param mergeFunction a merge function, used to resolve collisions between
     *                      values associated with the same key, as supplied
     *                      to {@link Map#merge(Object, Object, BiFunction)}
     * @return  a {@code ConcurrentMap} whose keys are taken from this stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     *
     * @see #toConcurrentMap()
     * @see #toMap(BinaryOperator)
     */
    public Map<K, V> toConcurrentMap(BinaryOperator<V> mergeFunction) {
        requireNonNull(mergeFunction);
        return inner.collect(Collectors.toConcurrentMap(
            Map.Entry::getKey,
            Map.Entry::getValue,
            mergeFunction
        ));
    }

    /**
     * Accumulates all key-value pairs of this {@code MapStream} into a 
     * {@code SortedMap}.
     * <p>
     * If the mapped keys contains duplicates (according to
     * {@link Object#equals(Object)}), an {@code IllegalStateException} is
     * thrown when the collection operation is performed. If the mapped keys
     * may have duplicates, use {@link #toSortedMap(BinaryOperator)}
     * instead.
     * <p>
     * If the keys are not {@link Comparable}, a 
     * {@code java.lang.ClassCastException} may be thrown when the terminal 
     * operation is executed. It is therefore better to use 
     * {@link #toSortedMapByKey(Comparator)} since it allows you to specify 
     * a comparator to use when comparing the keys.
     *
     * <p>
     * The returned {@code Map} is not concurrent. For parallel stream
     * pipelines, use {@link #toConcurrentNavigableMap()} instead.
     * 
     * @return  a {@code Map} whose keys and values are identical to the entries
     *          of this stream
     *
     * @see #toMap(BinaryOperator)
     * @see #toConcurrentMap()
     */
    public SortedMap<K, V> toSortedMap() {
        return inner.collect(Collectors.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue,
            throwingMerger(),
            TreeMap::new
        ));
    }

    /**
     * Accumulates all key-value pairs of this {@code MapStream} into a 
     * {@code SortedMap}, sorting the entries by applying the specified
     * comparator to the keys of the stream.
     * <p>
     * If the mapped keys contains duplicates (according to
     * {@link Object#equals(Object)}), an {@code IllegalStateException} is
     * thrown when the collection operation is performed. If the mapped keys
     * may have duplicates, use {@link #toSortedMap(Comparator, BinaryOperator)}
     * instead.
     * 
     * <p>
     * The returned {@code Map} is not concurrent. For parallel stream
     * pipelines, use {@link #toConcurrentNavigableMap()} instead.
     * 
     * @param keyComparator  the comparator to use when sorting the keys
     * @return               a {@code SortedMap} whose keys and values are 
     *                       identical to the entries of this stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     *
     * @see #toMap(BinaryOperator)
     * @see #toConcurrentMap()
     * @see #toSortedMap()
     */
    public SortedMap<K, V> toSortedMapByKey(Comparator<K> keyComparator) {
        requireNonNull(keyComparator);
        return inner.collect(Collectors.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue,
            throwingMerger(),
            () -> new TreeMap<>(keyComparator)
        ));
    }
    
    /**
     * Accumulates all key-value pairs of this {@code MapStream} into a 
     * {@code SortedMap}.
     * <p>
     * If the mapped keys contains duplicates (according to 
     * {@link Object#equals(Object)}), the values are merged using the provided 
     * merging function.
     *
     * <p>
     * There are multiple ways to deal with collisions between multiple elements
     * mapping to the same key. The other forms of {@code toSortedMap} simply 
     * use a merge function that throws unconditionally, but you can easily 
     * write more flexible merge policies.
     *
     * <p>
     * The returned {@code SortedMap} is not concurrent. For parallel stream
     * pipelines, the {@code combiner} function operates by merging the keys
     * from one map into another, which can be an expensive operation. If it is
     * not required that results are merged into the {@code Map} in encounter
     * order, using {@link #toConcurrentNavigableMap(BinaryOperator)}
     * may offer better parallel performance.
     *
     * @param mergeFunction  a merge function, used to resolve collisions 
     *                       between values associated with the same key, as 
     *                       supplied to {@link Map#merge(Object, Object, BiFunction)}
     * @return               a {@code SortedMap} whose keys and values are identical 
     *                       to the entries of this stream with any collisions 
     *                       handled
     * @throws NullPointerException if any of the provided parameters are {@code null}
     *
     * @see #toMap()
     * @see #toSortedMap()
     * @see #toConcurrentMap(BinaryOperator)
     */
    public SortedMap<K, V> toSortedMap(BinaryOperator<V> mergeFunction) {
        requireNonNull(mergeFunction);
        return inner.collect(Collectors.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue,
            mergeFunction,
            TreeMap::new
        ));
    }
    
    /**
     * Accumulates all key-value pairs of this {@code MapStream} into a 
     * {@code SortedMap}, sorting the entries by applying the specified
     * comparator to the keys of the stream.
     * <p>
     * If the mapped keys contains duplicates (according to 
     * {@link Object#equals(Object)}), the values are merged using the provided 
     * merging function.
     *
     * <p>
     * There are multiple ways to deal with collisions between multiple elements
     * mapping to the same key. The other forms of {@code toSortedMap} simply 
     * use a merge function that throws unconditionally, but you can easily 
     * write more flexible merge policies.
     *
     * <p>
     * The returned {@code SortedMap} is not concurrent. For parallel stream
     * pipelines, the {@code combiner} function operates by merging the keys
     * from one map into another, which can be an expensive operation. If it is
     * not required that results are merged into the {@code Map} in encounter
     * order, using {@link #toConcurrentNavigableMap(Comparator, BinaryOperator)}
     * may offer better parallel performance.
     *
     * @param keyComparator  the comparator to use for determining the order of
     *                       the keys
     * @param mergeFunction  a merge function, used to resolve collisions 
     *                       between values associated with the same key, as 
     *                       supplied to {@link Map#merge(Object, Object, BiFunction)}
     * @return               a {@code SortedMap} whose keys and values are identical 
     *                       to the entries of this stream with any collisions 
     *                       handled
     * @throws NullPointerException if any of the provided parameters are {@code null}
     *
     * @see #toMap()
     * @see #toSortedMap()
     * @see #toConcurrentMap(BinaryOperator)
     */
    public SortedMap<K, V> toSortedMap(Comparator<K> keyComparator, BinaryOperator<V> mergeFunction) {
        requireNonNull(keyComparator);
        requireNonNull(mergeFunction);
        return inner.collect(Collectors.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue,
            mergeFunction,
            () -> new TreeMap<>(keyComparator)
        ));
    }
    
    /**
     * Accumulates all key-value pairs of this {@code MapStream} into a 
     * {@code ConcurrentNavigableMap}.
     * <p>
     * If the mapped keys contains duplicates (according to
     * {@link Object#equals(Object)}), an {@code IllegalStateException} is
     * thrown when the collection operation is performed. If the mapped keys
     * may have duplicates, use {@link #toConcurrentNavigableMap(BinaryOperator)}
     * instead.
     * <p>
     * If the keys are not {@link Comparable}, a 
     * {@code java.lang.ClassCastException} may be thrown when the terminal 
     * operation is executed. It is therefore better to use 
     * {@link #toConcurrentNavigableMapByKey(Comparator)} since it allows you to specify 
     * a comparator to use when comparing the keys.
     * 
     * @return  a {@code ConcurrentNavigableMap} whose keys and values are 
     *          identical to the entries of this stream
     *
     * @see #toMap(BinaryOperator)
     * @see #toConcurrentMap()
     */
    public ConcurrentNavigableMap<K, V> toConcurrentNavigableMap() {
        return inner.collect(Collectors.toConcurrentMap(
            Map.Entry::getKey,
            Map.Entry::getValue,
            throwingMerger(),
            ConcurrentSkipListMap::new
        ));
    }

    /**
     * Accumulates all key-value pairs of this {@code MapStream} into a 
     * {@code ConcurrentNavigableMap}, sorting the entries by applying the 
     * specified comparator to the keys of the stream.
     * <p>
     * If the mapped keys contains duplicates (according to
     * {@link Object#equals(Object)}), an {@code IllegalStateException} is
     * thrown when the collection operation is performed. If the mapped keys
     * may have duplicates, use {@link #toConcurrentNavigableMap(Comparator, BinaryOperator)}
     * instead.
     * 
     * @param keyComparator  the comparator to use when sorting the keys
     * @return               a {@code ConcurrentNavigableMap} whose keys and 
     *                       values are identical to the entries of this stream
     * @throws NullPointerException if any of the provided parameters are {@code null}
     *
     * @see #toMap(BinaryOperator)
     * @see #toConcurrentMap()
     * @see #toSortedMap()
     */
    public ConcurrentNavigableMap<K, V> toConcurrentNavigableMapByKey(Comparator<K> keyComparator) {
        requireNonNull(keyComparator);
        return inner.collect(Collectors.toConcurrentMap(
            Map.Entry::getKey,
            Map.Entry::getValue,
            throwingMerger(),
            () -> new ConcurrentSkipListMap<>(keyComparator)
        ));
    }
    
    /**
     * Accumulates all key-value pairs of this {@code MapStream} into a 
     * {@code ConcurrentNavigableMap}.
     * <p>
     * If the mapped keys contains duplicates (according to 
     * {@link Object#equals(Object)}), the values are merged using the provided 
     * merging function.
     *
     * <p>
     * There are multiple ways to deal with collisions between multiple elements
     * mapping to the same key. The other forms of {@code toConcurrentNavigableMap} simply 
     * use a merge function that throws unconditionally, but you can easily 
     * write more flexible merge policies.
     *
     * @param mergeFunction  a merge function, used to resolve collisions 
     *                       between values associated with the same key, as 
     *                       supplied to {@link Map#merge(Object, Object, BiFunction)}
     * @return               a {@code ConcurrentNavigableMap} whose keys and 
     *                       values are identical to the entries of this stream 
     *                       with any collisions handled
     * @throws NullPointerException if any of the provided parameters are {@code null}
     *
     * @see #toMap()
     * @see #toSortedMap()
     * @see #toConcurrentMap(BinaryOperator)
     */
    public ConcurrentNavigableMap<K, V> toConcurrentNavigableMap(BinaryOperator<V> mergeFunction) {
        requireNonNull(mergeFunction);
        return inner.collect(Collectors.toConcurrentMap(
            Map.Entry::getKey,
            Map.Entry::getValue,
            mergeFunction,
            ConcurrentSkipListMap::new
        ));
    }
    
    /**
     * Accumulates all key-value pairs of this {@code MapStream} into a 
     * {@code ConcurrentNavigableMap}, sorting the entries by applying the 
     * specified comparator to the keys of the stream.
     * <p>
     * If the mapped keys contains duplicates (according to 
     * {@link Object#equals(Object)}), the values are merged using the provided 
     * merging function.
     *
     * <p>
     * There are multiple ways to deal with collisions between multiple elements
     * mapping to the same key. The other forms of 
     * {@code toConcurrentNavigableMap} simply use a merge function that throws 
     * unconditionally, but you can easily write more flexible merge policies.
     *
     * @param keyComparator  the comparator to use for determining the order of
     *                       the keys
     * @param mergeFunction  a merge function, used to resolve collisions 
     *                       between values associated with the same key, as 
     *                       supplied to {@link Map#merge(Object, Object, BiFunction)}
     * @return               a {@code ConcurrentNavigableMap} whose keys and 
     *                       values are identical to the entries of this stream 
     *                       with any collisions handled
     * @throws NullPointerException if any of the provided parameters are {@code null}
     *
     * @see #toMap()
     * @see #toSortedMap()
     * @see #toConcurrentMap(BinaryOperator)
     */
    public ConcurrentNavigableMap<K, V> toConcurrentNavigableMap(Comparator<K> keyComparator, BinaryOperator<V> mergeFunction) {
        requireNonNull(keyComparator);
        requireNonNull(mergeFunction);
        return inner.collect(Collectors.toConcurrentMap(
            Map.Entry::getKey,
            Map.Entry::getValue,
            mergeFunction,
            () -> new ConcurrentSkipListMap<>(keyComparator)
        ));
    }

    /**
     * Accumulates all entries of this {@code MapStream} into a {@code List}.
     * 
     * <p>
     * The returned {@code List} is not concurrent.
     * 
     * @return  a {@code List} that contains all the entities of the stream
     */
    public List<Map.Entry<K, V>> toList() {
        return inner.collect(Collectors.toList());
    }

    /**
     * Utility method for creating a composed comparator that begins by 
     * comparing the first value and if it is equal, continues to the next one.
     * 
     * @param <K>      the type of the key to compare
     * @param methods  methods for comparing the key ordered by priority
     * @return         a composite comparator that checks the key using the
     *                 supplied methods in order
     * @throws NullPointerException if any of the provided parameters are {@code null}
     */
    @SuppressWarnings("varargs")
    @SafeVarargs // Iterating over an array is safe.
    public static <K> Comparator<K> comparing(Function<K, ? extends Comparable<?>>... methods) {
        requireNonNull(methods);
        if (Stream.of(methods).anyMatch(Objects::isNull)) {
            throw new NullPointerException("One of the methods was null");
        }
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
     * {@link Collectors#toMap(Function, Function, BinaryOperator) toMap()}, 
     * which always throws {@code IllegalStateException}. This can be used to 
     * enforce the assumption that the elements being collected are distinct.
     *
     * @param <T>  the type of input arguments to the merge function
     * @return     a merge function which always throw 
     *             {@code IllegalStateException}
     */
    public static <T> BinaryOperator<T> throwingMerger() {
        return (u, v) -> {
            throw new IllegalStateException(String.format("Duplicate key %s", u));
        };
    }
}