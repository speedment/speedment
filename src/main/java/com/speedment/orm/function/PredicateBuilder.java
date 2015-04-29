package com.speedment.orm.function;

import com.speedment.orm.config.model.Column;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 *
 * @author pemi
 * @param <ENTITY>
 * @param <V>
 */
@Deprecated
public class PredicateBuilder<ENTITY, V extends Comparable<V>> implements Predicate<ENTITY> {

    private Column column;
    private Function<ENTITY, V> getter;
    private BinaryPredicate binaryOperation;
    private V value;

    public PredicateBuilder(Column column, Function<ENTITY, V> getter) {
        this.column = column;
        this.getter = getter;
    }

    public Predicate<ENTITY> equal(V value) {
        this.binaryOperation = StandardBinaryPredicate.EQ;
        this.value = value;
        return this;
    }

    public Predicate<ENTITY> greaterThan(V value) {
        this.binaryOperation = StandardBinaryPredicate.GT;
        this.value = value;
        return this;
    }

    public Predicate<ENTITY> lessThan(V value) {
        this.binaryOperation = StandardBinaryPredicate.LT;
        this.value = value;
        return this;
    }

    @Override
    public boolean test(ENTITY t) {
        return binaryOperation.test(getter.apply(t), value);
    }

    public V from(ENTITY t) {
        return getter.apply(t);
    }

}
