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

    public static enum Type {

        AND, OR
    }

    private final List<Predicate<? super ENTITY>> predicates;
    private final Type type;

    private CombinedBasePredicate(Type type, BasePredicate<ENTITY> first, Predicate<? super ENTITY>... predicates) {
        this.predicates = new ArrayList<>();
        this.predicates.add(first);
        this.predicates.addAll(Arrays.asList(predicates));
        this.type = type;
    }

    public CombinedBasePredicate<ENTITY> add(Predicate<? super ENTITY> predicate) {
        predicates.add(predicate);
        return this;
    }

    public CombinedBasePredicate<ENTITY> remove(Predicate<? super ENTITY> predicate) {
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

        public AndCombinedBasePredicate(BasePredicate<ENTITY> first, Predicate<? super ENTITY>... predicates) {
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

        public OrCombinedBasePredicate(BasePredicate<ENTITY> first, Predicate<? super ENTITY>... predicates) {
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
