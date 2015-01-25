package com.speedment.util;

import java.util.ArrayList;
import java.util.List;
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

    public static <T> Stream<T> walkOptional(T leaf, Function<T, Optional<T>> traverser) {
        return walkOptional(leaf, traverser, new ArrayList());
    }

    private static <T> Stream<T> walkOptional(T leaf, Function<T, Optional<T>> traverser, List<T> list) {
        list.add(leaf);
        traverser.apply(leaf).ifPresent(p -> walkOptional(leaf, traverser, list));
        return list.stream();
    }

    public static <T> Stream<T> walk(T leaf, Function<T, T> traverser) {
        return walk(leaf, traverser, new ArrayList());
    }

    private static <T> Stream<T> walk(T leaf, Function<T, T> traverser, List<T> list) {
        list.add(leaf);
        T next = traverser.apply(leaf);
        if (next != null) {
            walk(next, traverser, list);
        }
        return list.stream();
    }
//
//    public static <T, R> Stream<R> walk(T leaf, Function<T, T> traverser, Function<T, R> mapper) {
//        final List<T> flatList = walk(leaf, traverser);
//        return flatList.stream().map(mapper);
//    }

}
