package com.speedment.runtime.core.internal.util;

import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.field.ComparableField;
import com.speedment.runtime.field.predicate.SpeedmentPredicate;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.emptySet;
import static java.util.Collections.singletonList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toSet;

public final class InternalMergeUtil {

    private static final int CHUNK_SIZE = 100;

    private InternalMergeUtil() {}

    public static <T> Set<T> merge(Manager<T> manager, Set<T> entities) {
        return chunks(entities)
            .stream()
            .map(subSet -> mergeHelper(manager, subSet))
            .reduce((a, b) -> { a.addAll(b); return a;})
            .orElse(emptySet());
    }

    private static <T, V extends Comparable<V>> Set<T> mergeHelper(Manager<T> manager, Set<T> entities) {
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

        final Set<T> result = new HashSet<>();
        for (T entity:existing) {
            try {
                manager.update(entity);
                result.add(entity);
            } catch (SpeedmentException ignore) {}
        }
        for (T entity:missing) {
            try {
                manager.persist(entity);
                result.add(entity);
            } catch (SpeedmentException ignore) {}
        }
        return result;

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