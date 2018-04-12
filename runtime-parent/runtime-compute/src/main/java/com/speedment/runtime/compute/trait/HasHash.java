package com.speedment.runtime.compute.trait;

/**
 * Trait for expressions that has a hash algorithm implemented for the type that
 * it results in.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface HasHash<T> {

    /**
     * Hash the specified object into a 64-bit hash. If two equal objects are
     * hashed, they must always result in the same hash. However, objects that
     * are not equal may also result in the same hash.
     *
     * @param object  the object to hash
     * @return        the 64-bit hashcode
     */
    long hash(T object);

}