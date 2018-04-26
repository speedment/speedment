package com.speedment.common.tuple.getter;

import com.speedment.common.tuple.Tuple;

import java.util.function.Function;

/**
 * Function that given a {@link Tuple} returns the element at the
 * {@link #index() ordinal} position.
 *
 * @author Emil Forslund
 * @param <T> Tuple type
 * @param <R> return type
 * @since  1.0.8
 */
public interface TupleGetter<T, R> extends Function<T, R> {

    /**
     * Returns the index of the tuple element that this getter returns. 
     *
     * @return  the index of the tuple element that this getter returns
     */
    int index();
}