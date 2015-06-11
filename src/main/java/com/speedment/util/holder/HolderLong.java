package com.speedment.util.holder;

/**
 *
 * @author pemi
 */
public class HolderLong {

    private long value;

    public HolderLong() {
    }

    public HolderLong(long value) {
        this.value = value;
    }

    public void set(long value) {
        this.value = value;
    }

    public long get() {
        return value;
    }

}
