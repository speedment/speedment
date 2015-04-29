package com.speedment.orm.field;

import java.util.Objects;
import java.util.function.IntPredicate;

/**
 *
 * @author pemi
 */
public enum StandardBinaryOperator implements Operator {

    EQUAL(i -> i == 0),
    NOT_EQUAL(i -> i != 0),
    LESS_THAN(i -> i < 0),
    LESS_OR_EQUAL(i -> i <= 0),
    GREATER_THAN(i -> i > 0),
    GREATER_OR_EQUAL(i -> i >= 0);

    private final IntPredicate comparator;

    private StandardBinaryOperator(IntPredicate comparator) {
        this.comparator = Objects.requireNonNull(comparator);
    }

    public IntPredicate getComparator() {
        return comparator;
    }
}
