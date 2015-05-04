/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.orm.field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <ENTITY>
 */
public abstract class CombinedBasePredicate<ENTITY> extends BasePredicate<ENTITY> {

    public static enum Type { AND, OR }

    private final List<Predicate<? super ENTITY>> predicates;
    private final Type type;

    private CombinedBasePredicate(Type type, Predicate<ENTITY> first, Predicate<? super ENTITY>... predicates) {
        this.predicates = new ArrayList<>();
        this.predicates.add(first);
        this.predicates.addAll(Arrays.asList(predicates));
        this.type = type;
    }

    protected CombinedBasePredicate<ENTITY> add(Predicate<? super ENTITY> predicate) {
        if (getClass().isAssignableFrom(predicate.getClass())) {
            final CombinedBasePredicate<ENTITY> cbp = getClass().cast(predicate);
            cbp.stream().forEachOrdered(this::add);
        } else {
            predicates.add(predicate);
        }

        return this;
    }

    protected CombinedBasePredicate<ENTITY> remove(Predicate<? super ENTITY> predicate) {
        predicates.remove(predicate);
        return this;
    }

    public Stream<Predicate<? super ENTITY>> stream() {
        return predicates.stream();
    }

    public int size() {
        return predicates.size();
    }

    public Type getType() {
        return type;
    }

    public static class AndCombinedBasePredicate<ENTITY> extends CombinedBasePredicate<ENTITY> {

        public AndCombinedBasePredicate(Predicate<ENTITY> first, Predicate<? super ENTITY>... predicates) {
            super(Type.AND, first, predicates);
        }

        @Override
        public boolean test(ENTITY t) {
            return stream().allMatch(p -> p.test(t));
        }

        @Override
        public Predicate<ENTITY> and(Predicate<? super ENTITY> other) {
            return add(other);
        }

        @Override
        public Predicate<ENTITY> or(Predicate<? super ENTITY> other) {
            return new OrCombinedBasePredicate<>(this, other);
        }
    }

    public static class OrCombinedBasePredicate<ENTITY> extends CombinedBasePredicate<ENTITY> {

        public OrCombinedBasePredicate(Predicate<ENTITY> first, Predicate<? super ENTITY>... predicates) {
            super(Type.OR, first, predicates);
        }

        @Override
        public boolean test(ENTITY t) {
            return stream().anyMatch(p -> p.test(t));
        }

        @Override
        public Predicate<ENTITY> and(Predicate<? super ENTITY> other) {
            return new AndCombinedBasePredicate<>(this, other);
        }

        @Override
        public Predicate<ENTITY> or(Predicate<? super ENTITY> other) {
            return add(other);
        }
    }

}
