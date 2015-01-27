package com.speedment.util;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.function.Function;
import java.util.function.Predicate;
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

    /**
     * The TraversalType can control how a tree is walked.
     *
     * http://en.wikipedia.org/wiki/Tree_traversal
     *
     */
    public static enum TraversalOrder {

        DEPTH_FIRST_PRE, /*DEPTH_FIRST_IN, Supported only for left/right trees*/ DEPTH_FIRST_POST, BREADTH_FIRST;
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

    public static <T> Stream<T> traverse(T leaf, Function<T, Stream<T>> traverser, TraversalOrder traversalOrder) {
        if (traversalOrder == TraversalOrder.BREADTH_FIRST) {
            return traverseBredthFirst(leaf, traverser, Stream.builder()).build();
        } else {
            return traverse(leaf, traverser, traversalOrder, Stream.builder()).build();
        }
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

    private static <T> Stream.Builder<T> walk(T leaf, Function<T, T> traverser, Order order, Stream.Builder<T> builder) {
        if (order == Order.FORWARD) {
            builder.add(leaf);
        }
        final T next = traverser.apply(leaf);
        if (next != null) {
            walk(next, traverser, order, builder);
        }
        if (order == Order.BACKWARD) {
            builder.add(leaf);
        }
        return builder;
    }

    private static <T> Stream.Builder<T> traverse(T leaf, Function<T, Stream<T>> traverser, TraversalOrder traversalOrder, Stream.Builder<T> builder) {
        if (leaf == null) {
            return builder;
        }
        if (traversalOrder == TraversalOrder.DEPTH_FIRST_PRE) {
            builder.add(leaf);
        }
        final Stream<T> next = traverser.apply(leaf);
        if (next != null) {
            next.filter(Objects::nonNull).forEach((T n) -> {
                traverse(n, traverser, traversalOrder, builder);
            });
        }
        if (traversalOrder == TraversalOrder.DEPTH_FIRST_POST) {
            builder.add(leaf);
        }
        return builder;
    }

    private static <T> Stream.Builder<T> traverseBredthFirst(T leaf, Function<T, Stream<T>> traverser, Stream.Builder<T> builder) {
        if (leaf == null) {
            return builder;
        }
        final Queue<T> q = new ArrayDeque<>();
        q.add(leaf);
        while (!q.isEmpty()) {
            final T node = q.poll();
            builder.add(node);
            traverser.apply(node).filter(Objects::nonNull).forEach(q::add);
        }
        return builder;
    }

}
