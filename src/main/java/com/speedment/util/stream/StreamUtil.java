package com.speedment.util.stream;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 *
 * @author Duncan
 */
public class StreamUtil {

    public static <T> Stream<T> mandatory(Optional<? extends T> element) {
        return element.isPresent() ? Stream.of(element.get()) : Stream.empty();
    }

    public static <T> Stream<T> of(Collection<Optional<? extends T>> collection) {
        return collection.stream().filter(Optional::isPresent).map((o) -> o.get());
    }

    public static <T> Stream<T> of(Collection<? extends T>... collections) {
        return Stream.of(collections).flatMap(Collection::stream);
    }

    public static <T> Stream<T> of(Stream<? extends T>... streams) {
        return Stream.of(streams).flatMap(Function.identity());
    }

    public static <T> Stream.Builder<T> streamBuilder(Collection<? extends T>... collections) {
        return streamBuilder(Stream.builder(), collections);
    }

    public static <T> Stream.Builder<T> streamBuilder(Stream.Builder<T> originalBuilder, Collection<? extends T>... collections) {
        Stream.of(collections).flatMap(Collection::stream).forEach(originalBuilder::add);
        return originalBuilder;
    }
}
