package com.speedment.runtime.compute.internal.predicate;

import com.speedment.runtime.compute.expression.predicate.IsNotNull;
import com.speedment.runtime.compute.expression.predicate.IsNull;
import com.speedment.runtime.compute.trait.ToNullable;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of the {@link IsNull}-interface.
 *
 * @author Emil Forslund
 * @since  3.1.2
 */
public final class IsNullImpl<T, R> implements IsNull<T, R> {

    private final ToNullable<T, R, ?> expression;

    public IsNullImpl(ToNullable<T, R, ?> expression) {
        this.expression = requireNonNull(expression);
    }

    @Override
    public IsNotNull<T, R> negate() {
        return new IsNotNullImpl<>(expression);
    }

    @Override
    public ToNullable<T, R, ?> expression() {
        return expression;
    }
}
