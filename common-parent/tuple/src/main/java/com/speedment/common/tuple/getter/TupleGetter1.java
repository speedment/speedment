package com.speedment.common.tuple.getter;

import com.speedment.common.tuple.Tuple;

/**
 * Specialization of {@link TupleGetter} that always returns the 1st element.
 *
 * @param <TUPLE>  the type of the tuple
 * @param <T1>     the type of the 1st element
 *
 * @author Emil Forslund
 * @since  1.2.0
 */
@FunctionalInterface
public interface TupleGetter1<TUPLE extends Tuple, T1>
extends TupleGetter<TUPLE, T1> {

    @Override
    default int getOrdinal() {
        return 1;
    }

    @Override
    T1 apply(TUPLE tuple);
}
