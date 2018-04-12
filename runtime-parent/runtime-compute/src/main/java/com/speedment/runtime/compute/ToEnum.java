package com.speedment.runtime.compute;

import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.ExpressionType;
import com.speedment.runtime.compute.internal.ToEnumImpl;
import com.speedment.runtime.compute.trait.HasCompare;
import com.speedment.runtime.compute.trait.HasHash;

import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * @author Emil Forslund
 * @since 3.1.0
 */
public interface ToEnum<T, E extends Enum<E>>
extends Expression,
        Function<T, E>,
        HasHash<T>,
        HasCompare<T> {

    static <T, E extends Enum<E>> ToEnum<T, E>
    toEnum(Class<E> enumClass, Function<T, E> getter) {
        return new ToEnumImpl<>(enumClass, getter);
    }

    Class<E> enumClass();

    @Override
    default ExpressionType getExpressionType() {
        return ExpressionType.ENUM;
    }

    @Override
    default E apply(T t) {
        return null;
    }

    default ToInt<T> asOrdinal() {
        return t -> apply(t).ordinal();
    }

    default ToString<T> asName() {
        return t -> apply(t).name();
    }

    default ToEnum<T, E> map(UnaryOperator<E> mapper) {
        return new ToEnumImpl<>(enumClass(), object -> mapper.apply(apply(object)));
    }

    @Override
    default long hash(T object) {
        return apply(object).hashCode();
    }

    @Override
    default int compare(T first, T second) {
        return apply(first).compareTo(apply(second));
    }
}
