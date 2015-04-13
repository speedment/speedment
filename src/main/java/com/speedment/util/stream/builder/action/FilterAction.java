package com.speedment.util.stream.builder.action;

import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <T>
 */
public class FilterAction<T> extends Action<Stream<T>, Stream<T>> {

    public FilterAction(Predicate<? super T> predicate) {
        super((Stream<T> t) -> t.filter(predicate), Stream.class);
    }

}
