package com.speedment.codegen.model.modifier;

/**
 *
 * @author pemi
 * @param <T>
 */
public interface Modifier_<T> {

    String name();

    int getValue();

    public static boolean valuesContains(int values, int value) {
        return ((values & values) != 0);
    }

    public static int requireInValues(int value, int values) {
        if (!valuesContains(values, value)) {
            throw new IllegalArgumentException(value + " is not a class modifier value in " + values);
        }
        return value;
    }

}
