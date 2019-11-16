package com.speedment.common.tuple;

final class TuplesTestUtil {

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
