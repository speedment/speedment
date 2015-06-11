package com.speedment.util.holder;

/**
 *
 * @author pemi
 * @param <T> Type to hold
 */
public class Holder<T> {

    private T value;

    public Holder() {
    }

    public Holder(T value) {
        this.value = value;
    }

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

}
