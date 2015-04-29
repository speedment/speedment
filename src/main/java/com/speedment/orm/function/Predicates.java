package com.speedment.orm.function;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 *
 * @author pemi
 */
@Deprecated
public class Predicates {

    public static <T, C extends Comparable> Predicate<T> eq(C value, Function<T, C> mapper) {
        return new SpecialPredicate<>(StandardBinaryPredicate.EQ, value, mapper);
    }

    public static <T, C extends Comparable> Predicate<T> eq(Function<T, C> mapper, C value) {
        return new SpecialPredicate<>(StandardBinaryPredicate.EQ, value, mapper);
    }

    public static <T, C extends Comparable> Predicate<T> lessThan(C value, Function<T, C> mapper) {
        return new SpecialPredicate<>(StandardBinaryPredicate.LT, value, mapper);
    }

    public static <T, C extends Comparable> Predicate<T> lessThan(Function<T, C> mapper, C value) {
        return new SpecialPredicate<>(StandardBinaryPredicate.LT, value, mapper, true);
    }

    public static <T, C extends Comparable> Predicate<T> greaterThan(C value, Function<T, C> mapper) {
        return new SpecialPredicate<>(StandardBinaryPredicate.GT, value, mapper);
    }

    public static <T, C extends Comparable> Predicate<T> greaterThan(Function<T, C> mapper, C value) {
        return new SpecialPredicate<>(StandardBinaryPredicate.GT, value, mapper, true);
    }

//        Function<Hare, String> getHareName = h -> h.getName();
//
//    Predicate<Hare> namePredicate = h -> "harry".equals(h.getName());
//    public static <T, C extends Comparable> Predicate<T> equals(C value, Function<T, C> maper) {
//        return t -> value.equals(maper.apply(t));
//    }
}
