/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.function.Predicate;
import static java.util.stream.Collectors.joining;
import java.util.stream.Stream;

/**
 * Immutable aggregation of a number of {@link Predicate Predicates} of the same type
 * (e.g. AND or OR) that can be applied in combination.
 *
 * @param <ENTITY> the entity type
 *
 * @author Per Minborg
 * @author Emil Forslund
 * @since 2.2.0
 */
public abstract class AbstractCombinedPredicate<ENTITY> extends AbstractPredicate<ENTITY>
    implements CombinedPredicate<ENTITY> {

    private final List<Predicate<? super ENTITY>> predicates;
    private final Type type;

    private AbstractCombinedPredicate(
        final Type type,
        final List<Predicate<? super ENTITY>> predicates,
        final boolean negated
    ) {
        super(negated);
        this.type = requireNonNull(type);
        this.predicates = new ArrayList<>(requireNonNull(predicates));
    }

    @Override
    public Stream<Predicate<? super ENTITY>> stream() {
        return predicates.stream();
    }

    @Override
    public int size() {
        return predicates.size();
    }

    protected List<Predicate<? super ENTITY>> getPredicates() {
        return Collections.unmodifiableList(predicates);
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public abstract AndCombinedBasePredicate<ENTITY> and(Predicate<? super ENTITY> other);

    @Override
    public abstract OrCombinedBasePredicate<ENTITY> or(Predicate<? super ENTITY> other);

    public static class AndCombinedBasePredicate<ENTITY> extends AbstractCombinedPredicate<ENTITY> {

        public AndCombinedBasePredicate(
            final List<Predicate<? super ENTITY>> predicates,
            final boolean negated
        ) {
            super(Type.AND, requireNonNull(predicates), negated);
        }

        @Override
        protected boolean testWithoutNegation(ENTITY entity) {
            requireNonNull(entity);
            return stream().allMatch(p -> p.test(entity));
        }

        @Override
        public AndCombinedBasePredicate<ENTITY> and(Predicate<? super ENTITY> other) {
            requireNonNull(other);
            final List<Predicate<? super ENTITY>> updatedPredicates = new ArrayList<>(getPredicates());
            updatedPredicates.add(other);
            return new AndCombinedBasePredicate<>(updatedPredicates, isNegated());
        }

        @Override
        public OrCombinedBasePredicate<ENTITY> or(Predicate<? super ENTITY> other) {
            requireNonNull(other);
            return new OrCombinedBasePredicate<>(Arrays.asList(this, other), false);
        }

        @Override
        public AndCombinedBasePredicate<ENTITY> negate() {
            return new AndCombinedBasePredicate<>(getPredicates(), !isNegated());
        }

    }

    public static class OrCombinedBasePredicate<ENTITY> extends AbstractCombinedPredicate<ENTITY> {

        public OrCombinedBasePredicate(
            final List<Predicate<? super ENTITY>> predicates,
            final boolean negated
        ) {
            super(Type.OR, requireNonNull(predicates), negated);
        }

        @Override
        protected boolean testWithoutNegation(ENTITY entity) {
            requireNonNull(entity);
            return stream().anyMatch(p -> p.test(entity));
        }

        @Override
        public AndCombinedBasePredicate<ENTITY> and(Predicate<? super ENTITY> other) {
            requireNonNull(other);
            return new AndCombinedBasePredicate<>(Arrays.asList(this, other), false);
        }

        @Override
        public OrCombinedBasePredicate<ENTITY> or(Predicate<? super ENTITY> other) {
            requireNonNull(other);
            final List<Predicate<? super ENTITY>> updatedPredicates = new ArrayList<>(getPredicates());
            updatedPredicates.add(other);
            return new OrCombinedBasePredicate<>(updatedPredicates, isNegated());
        }

        @Override
        public OrCombinedBasePredicate<ENTITY> negate() {
            return new OrCombinedBasePredicate<>(getPredicates(), !isNegated());
        }

    }

    @Override
    public String toString() {
        return "CombinedPredicate {type="
            + type.name()
            + ", negated="
            + isNegated()
            + ", predicates="
            + predicates.stream()
                .map(Object::toString)
                .collect(joining(", "))
            + "}";
    }

}
