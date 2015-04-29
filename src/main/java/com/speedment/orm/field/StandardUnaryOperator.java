package com.speedment.orm.field;

import java.util.Objects;
import java.util.function.Predicate;

/**
 *
 * @author pemi
 */
public enum StandardUnaryOperator implements Operator {

    IS_NULL(Objects::isNull),
    IS_NOT_NULL(Objects::nonNull);

    private final Predicate<Object> predicate;

    private StandardUnaryOperator(Predicate<Object> predicate) {
        this.predicate = Objects.requireNonNull(predicate);
    }

    public Predicate<Object> getComparator() {
        return predicate;
    }
}
