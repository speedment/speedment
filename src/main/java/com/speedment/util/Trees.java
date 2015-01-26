package com.speedment.util;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public class Trees {

    public Trees() {
    }

    public static enum Order {

        FORWARD, BACKWARD;
    }

    public static <T> Stream<T> walk(T leaf, Function<T, T> traverser) {
        return walk(leaf, traverser, Order.FORWARD, Stream.builder()).build();
    }

    public static <T> Stream<T> walk(T leaf, Function<T, T> traverser, Order order) {
        return walk(leaf, traverser, order, Stream.builder()).build();
    }

    public static <T> Stream<T> walkOptional(T leaf, Function<T, Optional<T>> traverser) {
        return walkOptional(leaf, traverser, Order.FORWARD, Stream.builder()).build();
    }

    public static <T> Stream<T> walkOptional(T leaf, Function<T, Optional<T>> traverser, Order order) {
        return walkOptional(leaf, traverser, order, Stream.builder()).build();
    }

    //
    // Private support methods
    //
    private static <T> Stream.Builder<T> walkOptional(T leaf, Function<T, Optional<T>> traverser, Order order, Stream.Builder<T> builder) {
        if (order == Order.FORWARD) {
            builder.add(leaf);
        }
        traverser.apply(leaf).ifPresent(p -> walkOptional(leaf, traverser, order, builder));
        if (order == Order.BACKWARD) {
            builder.add(leaf);
        }
        return builder;
    }

    private static <T> Stream.Builder<T> walk(T leaf, Function<T, T> traverser, Order order, Stream.Builder<T> list) {
        if (order == Order.FORWARD) {
            list.add(leaf);
        }
        T next = traverser.apply(leaf);
        if (next != null) {
            walk(next, traverser, order, list);
        }
        if (order == Order.BACKWARD) {
            list.add(leaf);
        }
        return list;
    }

}
