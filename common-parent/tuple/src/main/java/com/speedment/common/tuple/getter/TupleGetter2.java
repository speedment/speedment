package com.speedment.common.tuple.getter;

import com.speedment.common.tuple.Tuple;

/**
 * Specialization of {@link TupleGetter} that always returns the 2nd element.
 *
 * @param <TUPLE>  the type of the tuple
 * @param <T2>     the type of the 2nd element
 *
 * @author Emil Forslund
 * @since  1.2.0
 */
@FunctionalInterface
public interface TupleGetter2<TUPLE extends Tuple, T2>
extends TupleGetter<TUPLE, T2> {

    @Override
    default int getOrdinal() {
        return 2;
    }

    @Override
    T2 apply(TUPLE tuple);
}
