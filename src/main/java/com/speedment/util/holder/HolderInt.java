package com.speedment.util.holder;

/**
 *
 * @author pemi
 */
public class HolderInt {

    private int value;

    public HolderInt() {
    }

    public HolderInt(int value) {
        this.value = value;
    }

    public void set(int value) {
        this.value = value;
    }

    public int get() {
        return value;
    }

}
