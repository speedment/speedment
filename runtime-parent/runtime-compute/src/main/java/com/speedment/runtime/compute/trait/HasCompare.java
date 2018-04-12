package com.speedment.runtime.compute.trait;

import java.util.Comparator;

/**
 * @author Emil Forslund
 * @since  1.0.0
 */
public interface HasCompare<T> extends Comparator<T> {

    @Override
    int compare(T first, T second);
}
