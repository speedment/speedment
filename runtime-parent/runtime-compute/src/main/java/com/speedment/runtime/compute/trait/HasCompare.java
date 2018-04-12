package com.speedment.runtime.compute.trait;

import java.util.Comparator;

/**
 * Trait for expressions that can also be used as a {@link Comparator} since it
 * has a {@link #compare(Object, Object)} method.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface HasCompare<T> extends Comparator<T> {

    @Override
    int compare(T first, T second);
}
