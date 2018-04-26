package com.speedment.common.tuple.getter;

import com.speedment.common.tuple.Tuple;

import java.util.function.Function;

/**
 * Function that given a {@link Tuple} returns the element at the
 * {@link #getOrdinal() ordinal} position.
 *
 * @author Emil Forslund
 * @since  1.0.8
 */
public interface TupleGetter<TUPLE extends Tuple, R> extends Function<TUPLE, R> {

    /**
     * Which of the {@link Tuple} elements that this getter returns.
     *
     * @return  the tuple ordinal
     */
    int getOrdinal();
}