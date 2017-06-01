package com.speedment.runtime.field.internal.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;

/**
 * Utility class for constructing temporary collections.
 *
 * @author Emil Forslund
 * @since  3.0.11
 */
public final class CollectionUtil {

    /**
     * Returns a {@link Set} with the values in the specified collection. If the
     * specified collection is already a {@link Set}, it might be returned. This
     * method makes no guarantees regarding mutability or iteration order of the
     * returned set.
     *
     * @param <T>         the element type
     * @param collection  the collection
     * @return            a set with the same elements as the collection
     */
    public static <T> Set<T> collectionToSet(Collection<T> collection) {
        if (collection instanceof Set) {
            return (Set<T>) collection;
        } else {
            switch (collection.size()) {
                case 0  : return emptySet();
                case 1  : return singleton(collection.iterator().next());
                default : return new HashSet<>(collection);
            }
        }
    }

    /**
     * Should not be instantiated.
     */
    private CollectionUtil() {}
}
