package com.speedment.runtime.compute;

import com.speedment.common.function.BooleanUnaryOperator;
import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.ExpressionType;
import com.speedment.runtime.compute.internal.ToEnumImpl;
import com.speedment.runtime.compute.internal.expression.MapperUtil;
import com.speedment.runtime.compute.trait.HasCompare;
import com.speedment.runtime.compute.trait.HasHash;
import com.speedment.runtime.compute.trait.HasMap;

import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * Expression that given an entity returns a non-null {@code enum} value. The
 * expression also knows about the enum class and can therefore not be
 * implemented as a lambda like the other expressions in this module.
 *
 * @param <T> type to extract from
 * @param <E> enum type
 *
 * @see Function
 *
 * @author Emil Forslund
 * @since 3.1.0
 */
public interface ToEnum<T, E extends Enum<E>>
extends Expression<T>,
        Function<T, E>,
        HasMap<T, UnaryOperator<E>, ToEnum<T, E>>,
        HasHash<T>,
        HasCompare<T> {

    static <T, E extends Enum<E>> ToEnum<T, E>
        toEnum(Class<E> enumClass, Function<T, E> getter) {
        return new ToEnumImpl<>(enumClass, getter);
    }

    Class<E> enumClass();

    @Override
    default ExpressionType expressionType() {
        return ExpressionType.ENUM;
    }

    @Override
    E apply(T t);

    default ToInt<T> asOrdinal() {
        return t -> apply(t).ordinal();
    }

    default ToString<T> asName() {
        return t -> apply(t).name();
    }

    @Override
    default ToEnum<T, E> map(UnaryOperator<E> mapper) {
        return MapperUtil.map(this, mapper);
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
