package com.speedment.common.tuple;

import java.util.stream.IntStream;

final class TuplesTestUtil {

    static final int SIZE = 100;

    static final Integer[] LARGE_ARRAY = IntStream.range(0, SIZE).boxed().toArray(Integer[]::new);
    static final Tuple LARGE_TUPLE = Tuples.ofArray((Object[]) LARGE_ARRAY);

    private TuplesTestUtil() {}

    static Tuple createTupleFilled(int i) {
        return Tuples.ofArray((Object[]) array(i));
    }

    static TupleOfNullables createTupleOfNullableFilled(int i) {
        return TuplesOfNullables.ofNullablesArray((Object[]) array(i));
    }

/*
    static MutableTuple createMutableTupleFilled(int i) {
        MutableTuple tuple = MutableTuples.
        return TuplesOfNullables.ofNullablesArray((Object[]) array(i));
    }
*/


    public static Integer[] array(int i) {
        final Integer[] array = new Integer[i];
        for (int j = 0; j < i; j++) {
            array[j] = j;
        }
        return array;
    }

}
