package com.speedment.util.holder;

/**
 *
 * @author pemi
 */
public class HolderDouble {

    private double value;

    public HolderDouble() {
    }

    public HolderDouble(double value) {
        this.value = value;
    }

    public void set(double value) {
        this.value = value;
    }

    public double get() {
        return value;
    }

}
