package com.speedment.common.tuple;

import com.speedment.common.tuple.getter.TupleGetter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class ManualTupleTest {

    @Test
    void tuple2() {
        final Tuple2<Integer, Integer> tuple2 = Tuples.of(0, 1);
        final TupleGetter<Tuple2<Integer, Integer>, Integer> getter0 = Tuple2.getter0();
        final TupleGetter<Tuple2<Integer, Integer>, Integer> getter1 = Tuple2.getter1();
        assertEquals(0, getter0.index());
        assertEquals(1, getter1.index());
        assertEquals(0, getter0.apply(tuple2));
        assertEquals(1, getter1.apply(tuple2));

    }

}
