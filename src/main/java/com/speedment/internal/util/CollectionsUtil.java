package com.speedment.internal.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 *
 * @author pemi
 */
public final class CollectionsUtil {

    public static <T> Optional<T> findAnyIn(Collection<T> collection) {
        final Iterator<T> iterator = collection.iterator();
        if (iterator.hasNext()) {
            return Optional.of(iterator.next());
        }
        return Optional.empty();
    }

    /**
     * Gets one element from the provided Collection. If no element is present,
     * an Exception is thrown. No guarantee is made as to which element is
     * returned from time to time.
     *
     * @param <T> Collection type
     * @param collection to use
     * @return one element from the provided Collection
     * @throws NoSuchElementException if no elements are present in the
     * Collection
     */
    public static <T> T getAnyFrom(Collection<T> collection) {
        final Iterator<T> iterator = collection.iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        throw new NoSuchElementException("No value present");
    }

    private CollectionsUtil() {
    }

}
