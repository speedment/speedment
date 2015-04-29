package com.speedment.orm.function;

import com.speedment.orm.config.model.Column;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

/**
 *
 * @author pemi
 * @param <ENTITY>
 */
@Deprecated
public class IntPredicateBuilder<ENTITY> implements Predicate<ENTITY> {

    private final Column column;
    private final ToIntFunction<ENTITY> getter;
    private BinaryPredicate binaryOperation;
    private int value;

    public IntPredicateBuilder(Column column, ToIntFunction<ENTITY> getter) {
        this.column = column;
        this.getter = getter;
    }

    public Predicate<ENTITY> equal(int value) {
        this.binaryOperation = StandardBinaryPredicate.EQ;
        this.value = value;
        return this;
    }

    public Predicate<ENTITY> greaterThan(int value) {
        this.binaryOperation = StandardBinaryPredicate.GT;
        this.value = value;
        return this;
    }

    public Predicate<ENTITY> lessThan(int value) {
        this.binaryOperation = StandardBinaryPredicate.LT;
        this.value = value;
        return this;
    }

    @Override
    public boolean test(ENTITY t) {
        return binaryOperation.test(getter.applyAsInt(t), value);
    }

}
