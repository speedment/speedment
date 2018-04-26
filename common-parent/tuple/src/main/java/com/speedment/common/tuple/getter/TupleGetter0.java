package com.speedment.common.tuple.getter;

import com.speedment.common.tuple.Tuple;

/**
 * Specialization of {@link TupleGetter} that always returns the 0th element.
 *
 * @param <TUPLE>  the type of the tuple
 * @param <T0>     the type of the 0th element
 *
 * @author Emil Forslund
 * @since  1.2.0
 */
@FunctionalInterface
public interface TupleGetter0<TUPLE extends Tuple, T0>
extends TupleGetter<TUPLE, T0> {

    @Override
    default int getOrdinal() {
        return 0;
    }

    @Override
    T0 apply(TUPLE tuple);
}
