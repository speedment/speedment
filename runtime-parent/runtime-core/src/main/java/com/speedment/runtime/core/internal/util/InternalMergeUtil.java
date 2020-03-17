/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.runtime.core.internal.util;

import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.field.predicate.SpeedmentPredicate;
import com.speedment.runtime.field.trait.HasComparableOperators;

import java.util.*;
import java.util.function.Function;
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

        @SuppressWarnings("unchecked")
        final List<HasComparableOperators<T, V>> pks = manager.primaryKeyFields()
            .map(pk -> (HasComparableOperators<T, V>)pk)
            .collect(Collectors.toList());

        if (pks.size() != 1) {
            throw new UnsupportedOperationException(
                "Merge operations are only supported for entities with exactly one primary keys." +
                " Operation failed because there are " + pks.size() +
                " primary keys for " + manager.getEntityClass().getSimpleName() + "."
            );
            // Multiple PKs are hard to support because in predicates cannot be composed
            // correctly by .and(). E.g. in (1, 2) and in (3 ,4) is not the same as
            // (1 and 3) or (2 and 4)
        }

        final HasComparableOperators<T, V> co = pks
            .iterator()
            .next();

        @SuppressWarnings("unchecked")
        final SpeedmentPredicate<T> pkPredicate =
            co.in(entities.stream()
                .map(pkExtractor(co))
                .collect(toSet()));

        final Set<V> existingPks = manager.stream()
            .filter(pkPredicate)
            .map(pkExtractor(co))
            .collect(toSet());

        final Set<T> existing = entities.stream()
            .filter(e -> existingPks.contains(pkExtractor(co).apply(e)))
            .collect(toCollection(LinkedHashSet::new)); // Retain order

        final Set<T> missing = entities.stream()
            .filter(e -> !existingPks.contains(pkExtractor(co).apply(e)))
            .collect(toCollection(LinkedHashSet::new)); // Retain order

        System.out.println("entities = " + entities);
        System.out.println("existing = " + existing);
        System.out.println("missing = " + missing);

        final List<SpeedmentException> exceptions = new ArrayList<>();
        final Set<T> result = new HashSet<>();
        for (T entity:existing) {
            try {
                final T updated = manager.update(entity);
                result.add(updated);
            } catch (SpeedmentException ex) {
                exceptions.add(ex);
            }
        }
        for (T entity:missing) {
            try {
                final T persisted = manager.persist(entity);
                result.add(persisted);
            } catch (SpeedmentException ex) {
                exceptions.add(ex);
            }
        }
        if (!exceptions.isEmpty()) {
            throw new SpeedmentException("Unable to merge because " + exceptions.size() + " operation(s) failed.", exceptions.iterator().next());
        }

        return result;

    }

    @SuppressWarnings("unchecked")
    private static <T, V extends Comparable<V>> Function<T, V> pkExtractor(HasComparableOperators<T, V> co) {
        return entity -> (V) co.getter().apply(entity);
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