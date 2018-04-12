package com.speedment.runtime.compute.trait;

/**
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface HasHash<T> {

    long hash(T object);

}