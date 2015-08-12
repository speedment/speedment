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
package com.speedment.util;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * A container object like {@link Optional} but with extra Meta Data capability.
 *
 * @author pemi
 * @param <T> Container object type
 * @param <M> Meta data type
 */
public final class OptionalMeta<T, M> {

    private final T value;
    private final M metaData;

    private OptionalMeta() {
        this.value = null;
        this.metaData = null;
    }

    private OptionalMeta(T value) {
        this.value = Objects.requireNonNull(value);
        this.metaData = null;
    }

    private OptionalMeta(T value, M metaData) {
        this.value = value;
        this.metaData = metaData;
    }

    /**
     * Returns an empty {@code OptionalMeta} instance. No value is present for
     * this OptionalMeta.
     *
     * N.B. Though it may be tempting to do so, avoid testing if an object
     * is empty by comparing with {@code ==} against instances returned by
     * {@code Option.empty()}. There is no guarantee that it is a singleton.
     * Instead, use {@link #isPresent()}.
     *
     * @param <T> Type of the non-existent value
     * @param <M> Type of meta data
     * @return an empty {@code OptionalMeta}
     */
    public static <T, M> OptionalMeta<T, M> empty() {
        @SuppressWarnings("unchecked")
        final OptionalMeta<T, M> t = (OptionalMeta<T, M>) EMPTY;
        return t;
    }

    private static final OptionalMeta<?, ?> EMPTY = new OptionalMeta<>();

    /**
     * Returns an {@code OptionalMeta} with the specified present non-null value
     * and no meta data.
     *
     * @param <T> the class of the value
     * @param <M> the class of the meta data
     * @param value the value to be present, which must be non-null
     * @return an {@code OptionalMeta} with the value present
     * @throws NullPointerException if value is null
     */
    public static <T, M> OptionalMeta<T, M> of(T value) {
        return new OptionalMeta<>(value);
    }

    /**
     * Returns an {@code OptionalMeta} with the specified present non-null value
     * and non-null meta data.
     *
     * @param <T> the class of the value
     * @param <M> the class of the meta data
     * @param value the value to be present, which must be non-null
     * @param metaData the meta data
     * @return an {@code OptionalMeta} with the value present
     * @throws NullPointerException if value is null
     */
    public static <T, M> OptionalMeta<T, M> of(T value, M metaData) {
        return new OptionalMeta<>(value, metaData);
    }

    /**
     * Returns an {@code OptionalMeta} describing the specified value, if
     * non-null, otherwise returns an empty {@code OptionalMeta}. No meta data
     * is provided.
     *
     * @param <T> the class of the value
     * @param <M> the class of the meta data
     * @param value the possibly-null value to describe
     * @return an {@code OptionalMeta} with a present value if the specified
     * value is non-null, otherwise an empty {@code OptionalMeta}
     */
    public static <T, M> OptionalMeta<T, M> ofNullable(T value) {
        return value == null ? empty() : of(value);
    }

    /**
     * Returns an {@code OptionalMeta} describing the specified value, if
     * non-null, otherwise returns an empty {@code OptionalMeta}.
     *
     * @param <T> the class of the value
     * @param <M> the class of the meta data
     * @param value the possibly-null value to describe
     * @param metaData the possibly-null meta data
     * @return an {@code OptionalMeta} with a present value if the specified
     * value is non-null, otherwise an empty {@code OptionalMeta}
     */
    public static <T, M> OptionalMeta<T, M> ofNullable(T value, M metaData) {
        if (value == null && metaData == null) {
            return empty();
        }
        return new OptionalMeta<>(value, metaData);
    }

    /**
     * If a value is present in this {@code OptionalMeta}, returns the value,
     * otherwise throws {@code NoSuchElementException}.
     *
     * @return the non-null value held by this {@code OptionalMeta}
     * @throws NoSuchElementException if there is no value present
     *
     * @see OptionalMeta#isPresent()
     */
    public T get() {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    /**
     * If a meta data element is present in this {@code OptionalMeta}, returns
     * the meta data, otherwise throws {@code NoSuchElementException}.
     *
     * @return the non-null meta data held by this {@code OptionalMeta}
     * @throws NoSuchElementException if there is no meta data present
     *
     * @see OptionalMeta#isMetaDataPresent()
     */
    public M getMetaData() {
        if (metaData == null) {
            throw new NoSuchElementException("No meta data present");
        }
        return metaData;
    }

    /**
     * Return {@code true} if there is a value present, otherwise {@code false}.
     *
     * @return {@code true} if there is a value present, otherwise {@code false}
     */
    public boolean isPresent() {
        return value != null;
    }

    /**
     * Return {@code true} if there is a meta data present, otherwise
     * {@code false}.
     *
     * @return {@code true} if there is a meta data present, otherwise
     * {@code false}
     */
    public boolean isMetaDataPresent() {
        return metaData != null;
    }

    /**
     * If a value is present, invoke the specified consumer with the value,
     * otherwise do nothing.
     *
     * @param consumer block to be executed if a value is present
     * @throws NullPointerException if value is present and {@code consumer} is
     * null
     */
    public void ifPresent(Consumer<? super T> consumer) {
        if (value != null) {
            consumer.accept(value);
        }
    }

    /**
     * If a value is present, and the value matches the given predicate, return
     * an {@code OptionalMeta} describing the value, otherwise return an empty
     * {@code OptionalMeta}.
     *
     * @param predicate a predicate to apply to the value, if present
     * @return an {@code OptionalMeta} describing the value of this
     * {@code OptionalMeta} if a value is present and the value matches the
     * given predicate, otherwise an empty {@code OptionalMeta}
     * @throws NullPointerException if the predicate is null
     */
    public OptionalMeta<T, M> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        if (!isPresent()) {
            return this;
        } else {
            return predicate.test(value) ? this : empty();
        }
    }

    /**
     * If a value is present, apply the provided mapping function to it, and if
     * the result is non-null, return an {@code OptionalMeta} describing the
     * result. Otherwise return an empty {@code OptionalMeta}.
     *
     * N.B. This method supports post-processing on optional values, without
     * the need to explicitly check for a return status. For example, the
     * following code traverses a stream of file names, selects one that has not
     * yet been processed, and then opens that file, returning an
     * {@code OptionalMeta<FileInputStream>}:
     *
     * <pre>{@code
     *     OptionalMeta<FileInputStream> fis =
     *         names.stream().filter(name -> !isProcessedYet(name))
     *                       .findFirst()
     *                       .map(name -> new FileInputStream(name));
     * }</pre>
     *
     * Here, {@code findFirst} returns an {@code OptionalMeta<String>}, and then
     * {@code map} returns an {@code OptionalMeta<FileInputStream>} for the
     * desired file if one exists.
     *
     * @param <U> The type of the result of the mapping function
     * @param mapper a mapping function to apply to the value, if present
     * @return an {@code OptionalMeta} describing the result of applying a
     * mapping function to the value of this {@code OptionalMeta}, if a value is
     * present, otherwise an empty {@code OptionalMeta}
     * @throws NullPointerException if the mapping function is null
     */
    public <U> OptionalMeta<U, M> map(Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent()) {
            return empty();
        } else {
            return OptionalMeta.ofNullable(mapper.apply(value), getMetaData());
        }
    }

    /**
     * If a value is present, apply the provided {@code OptionalMeta}-bearing
     * mapping function to it, return that result, otherwise return an empty
     * {@code OptionalMeta}. This method is similar to {@link #map(Function)},
     * but the provided mapper is one whose result is already an
     * {@code OptionalMeta}, and if invoked, {@code flatMap} does not wrap it
     * with an additional {@code OptionalMeta}.
     *
     * @param <U> The type parameter to the {@code OptionalMeta} returned by
     * @param mapper a mapping function to apply to the value, if present the
     * mapping function
     * @return the result of applying an {@code OptionalMeta}-bearing mapping
     * function to the value of this {@code OptionalMeta}, if a value is
     * present, otherwise an empty {@code OptionalMeta}
     * @throws NullPointerException if the mapping function is null or returns a
     * null result
     */
    public <U> OptionalMeta<U, M> flatMap(Function<? super T, OptionalMeta<U, M>> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent()) {
            return empty();
        } else {
            return Objects.requireNonNull(mapper.apply(value));
        }
    }

    /**
     * Return the value if present, otherwise return {@code other}.
     *
     * @param other the value to be returned if there is no value present, may
     * be null
     * @return the value, if present, otherwise {@code other}
     */
    public T orElse(T other) {
        return value != null ? value : other;
    }

    /**
     * Return the value if present, otherwise invoke {@code other} and return
     * the result of that invocation.
     *
     * @param other a {@code Supplier} whose result is returned if no value is
     * present
     * @return the value if present otherwise the result of {@code other.get()}
     * @throws NullPointerException if value is not present and {@code other} is
     * null
     */
    public T orElseGet(Supplier<? extends T> other) {
        return value != null ? value : other.get();
    }

    /**
     * Return the contained value, if present, otherwise throw an exception to
     * be created by the provided supplier.
     *
     * N.B. A method reference to the exception constructor with an empty
     * argument list can be used as the supplier. For example,
     * {@code IllegalStateException::new}
     *
     * @param <X> Type of the exception to be thrown
     * @param exceptionSupplier The supplier which will return the exception to
     * be thrown
     * @return the present value
     * @throws X if there is no value present
     * @throws NullPointerException if no value is present and
     * {@code exceptionSupplier} is null
     */
    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (value != null) {
            return value;
        } else {
            throw exceptionSupplier.get();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OptionalMeta)) {
            return false;
        }
        final OptionalMeta<?, ?> other = (OptionalMeta<?, ?>) obj;
        return Objects.equals(value, other.value) && Objects.equals(metaData, other.metaData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, metaData);
    }

    @Override
    public String toString() {
        if (value == null && metaData == null) {
            return "OptionalMeta.empty";
        }
        return String.format("OptionalMeta[%s, %s]", Objects.toString(value), Objects.toString(metaData));
    }

    public Stream<T> stream() {
        if (isPresent()) {
            return Stream.of(get());
        } else {
            return Stream.empty();
        }
    }

}
