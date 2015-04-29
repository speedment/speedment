package com.speedment.orm.field;

import java.util.Objects;
import java.util.function.BiPredicate;

/**
 *
 * @author pemi
 */
public enum StandardStringBinaryOperator implements Operator {

    STARTS_WITH(String::startsWith),
    ENDS_WITH(String::endsWith),
    CONTAINS(String::contains),
    EQUAL_IGNORE_CASE(String::equals),
    NOT_EQUAL_IGNORE_CASE((s0, s1) -> !s0.equalsIgnoreCase(s1));

    private final BiPredicate<String, String> biPredicate;

    private StandardStringBinaryOperator(BiPredicate<String, String> biPredicate) {
        this.biPredicate = Objects.requireNonNull(biPredicate);
    }

    public BiPredicate<String, String> getComparator() {
        return biPredicate;
    }
}
