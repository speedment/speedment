package com.speedment.orm.function;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 *
 * @author pemi
 * @param <T>
 * @param <C>
 */
@Deprecated
public class SpecialPredicate<T, C extends Comparable<C>> implements Predicate<T> {

    private final StandardBinaryPredicate binaryOperation;
    private final C value;
    private final Function<T, C> mapper;
    private final boolean invert;

    public SpecialPredicate(StandardBinaryPredicate binaryOperation, C value, Function<T, C> mapper) {
        this(binaryOperation, value, mapper, false);
    }

    public SpecialPredicate(StandardBinaryPredicate binaryOperation, C value, Function<T, C> mapper, boolean invert) {
        this.binaryOperation = binaryOperation;
        this.value = value;
        this.mapper = mapper;
        this.invert = invert;
    }

    @Override
    public boolean test(T t) {
        return isInvert() ^ getBinaryOperation().test(getValue(), getMapper().apply(t));
    }

    public StandardBinaryPredicate getBinaryOperation() {
        return binaryOperation;
    }

    public C getValue() {
        return value;
    }

    public Function<T, C> getMapper() {
        return mapper;
    }

    public boolean isInvert() {
        return invert;
    }

}
