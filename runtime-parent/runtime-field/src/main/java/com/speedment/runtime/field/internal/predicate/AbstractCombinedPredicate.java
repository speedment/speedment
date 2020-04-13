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
package com.speedment.runtime.field.internal.predicate;

import com.speedment.runtime.field.predicate.CombinedPredicate;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.speedment.runtime.field.internal.util.CollectionUtil.copyAndAdd;
import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

/**
 * Immutable aggregation of a number of {@link Predicate Predicates} of the same type
 * (e.g. AND or OR) that can be applied in combination.
 *
 * @param <ENTITY> the entity type
 *
 * @author Per Minborg
 * @author Emil Forslund
 * @since  2.2.0
 */
public abstract class AbstractCombinedPredicate<ENTITY>
extends AbstractPredicate<ENTITY>
implements CombinedPredicate<ENTITY> {

    private final List<Predicate<? super ENTITY>> predicates;
    private final Type type;

    private AbstractCombinedPredicate(
            final Type type,
            final List<Predicate<? super ENTITY>> predicates) {

        this.type       = requireNonNull(type);
        this.predicates = requireNonNull(predicates);
    }

    protected List<Predicate<? super ENTITY>> getPredicates() {
        return unmodifiableList(predicates);
    }

    @Override
    public Stream<Predicate<? super ENTITY>> stream() {
        return predicates.stream();
    }

    @Override
    public int size() {
        return predicates.size();
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public abstract CombinedPredicate<ENTITY> and(Predicate<? super ENTITY> other);

    @Override
    public abstract CombinedPredicate<ENTITY> or(Predicate<? super ENTITY> other);

    /**
     * Specialization for {@code AND}-predicates.
     *
     * @param <ENTITY>  the entity type
     */
    public static final class AndCombinedBasePredicateImpl<ENTITY>
    extends AbstractCombinedPredicate<ENTITY> {

        public AndCombinedBasePredicateImpl(
                List<Predicate<? super ENTITY>> predicates) {

            super(Type.AND, predicates);
        }

        @Override
        public boolean applyAsBoolean(ENTITY entity) {
            requireNonNull(entity);
            return stream().allMatch(p -> p.test(entity));
        }

        @Override
        public CombinedPredicate<ENTITY> and(Predicate<? super ENTITY> other) {
            requireNonNull(other);
            return new AndCombinedBasePredicateImpl<>(
                copyAndAdd(getPredicates(), other)
            );
        }

        @Override
        public CombinedPredicate<ENTITY> or(Predicate<? super ENTITY> other) {
            requireNonNull(other);
            return CombinedPredicate.or(this, other);
        }

        @Override
        @SuppressWarnings("unchecked")
        public CombinedPredicate<ENTITY> negate() {
            return new OrCombinedBasePredicateImpl<>(
                getPredicates().stream()
                    .map(p -> (Predicate<ENTITY>) p)
                    .map(Predicate::negate)
                    .collect(toList())
            );
        }
    }

    /**
     * Specialization for {@code OR}-predicates.
     *
     * @param <ENTITY>  the entity type
     */
    public static final class OrCombinedBasePredicateImpl<ENTITY>
    extends AbstractCombinedPredicate<ENTITY> {

        public OrCombinedBasePredicateImpl(
                List<Predicate<? super ENTITY>> predicates) {

            super(Type.OR, predicates);
        }

        @Override
        public boolean applyAsBoolean(ENTITY entity) {
            requireNonNull(entity);
            return stream().anyMatch(p -> p.test(entity));
        }

        @Override
        public CombinedPredicate<ENTITY> and(Predicate<? super ENTITY> other) {
            requireNonNull(other);
            return CombinedPredicate.and(this, other);
        }

        @Override
        public CombinedPredicate<ENTITY> or(Predicate<? super ENTITY> other) {
            requireNonNull(other);
            return new OrCombinedBasePredicateImpl<>(
                copyAndAdd(getPredicates(), other)
            );
        }

        @Override
        @SuppressWarnings("unchecked")
        public AndCombinedBasePredicateImpl<ENTITY> negate() {
            return new AndCombinedBasePredicateImpl<>(
                getPredicates().stream()
                    .map(p -> (Predicate<ENTITY>) p)
                    .map(Predicate::negate)
                    .collect(toList())
            );
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CombinedPredicate)) return false;

        final CombinedPredicate<?> that = (CombinedPredicate<?>) o;
        final Iterator<Predicate<? super ENTITY>> it = predicates.iterator();
        return getType() == that.getType()
            && that.stream().allMatch(it.next()::equals);
    }

    @Override
    public int hashCode() {
        int result = getPredicates().hashCode();
        result = 31 * result + getType().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CombinedPredicate {type="
            + type.name()
            + ", predicates="
            + predicates
            + "}";
    }

}
