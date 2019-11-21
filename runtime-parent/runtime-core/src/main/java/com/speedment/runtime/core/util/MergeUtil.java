package com.speedment.runtime.core.util;

import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.field.ComparableField;
import com.speedment.runtime.field.predicate.SpeedmentPredicate;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toSet;

public final class MergeUtil {

    private static final int CHUNK_SIZE = 100;

    private MergeUtil() {}

    /**
     * Merges the provided {@code entities} with the underlying database.
     * <P>
     *
     * @param <T> entity type
     * @param <V> Dynamic value type
     * @param manager to use for merging
     * @param entities to merge with the database
     */
    public static <T, V extends Comparable<V>> void merge(Manager<T> manager, Collection<T> entities) {
        chunks(entities)
            .forEach(subSet -> mergeHelper(manager, subSet));
    }

    private static <T, V extends Comparable<V>> void mergeHelper(Manager<T> manager, Set<T> entities) {
        requireNonNull(manager);
        requireNonNull(entities);

        final List<ComparableField<T, ?, V>> pks = manager.primaryKeyFields()
            .map(pk -> (ComparableField<T, ?, V>)pk)
            .collect(Collectors.toList());

        final SpeedmentPredicate<T> pkPredicate = pks.stream()
            .map(cf -> cf.in(
                entities.stream()
                    .map(cf::get)
                    .collect(toSet())))
            .reduce(SpeedmentPredicate::and)
            .orElseThrow(() -> new IllegalStateException("The entity does not have a primary key"));

        final Set<T> existing = manager.stream()
            .filter(pkPredicate)
            .collect(toCollection(LinkedHashSet::new)); // Retain order

        final Set<T> missing = entities.stream()
            .filter(e -> !existing.contains(e))
            .collect(toCollection(LinkedHashSet::new)); // Retain order

        existing.stream()
            .forEach(manager.updater());

        missing.stream()
            .forEach(manager.persister());

    }

    private static <T> List<Set<T>> chunks(Collection<T> keys) {
        if (keys.size() <= CHUNK_SIZE) {
            return singletonList(new HashSet<>(keys));
        }
        final List<Set<T>> result = new ArrayList<>(keys.size() / CHUNK_SIZE);
        Set<T> current = new HashSet<>(CHUNK_SIZE);
        for (T k : keys) {
            current.add(k);
            if (current.size() >= CHUNK_SIZE) {
                result.add(current);
                current = new HashSet<>();
            }
        }
        if (!current.isEmpty()) {
            result.add(current);
        }
        return result;
    }

}
