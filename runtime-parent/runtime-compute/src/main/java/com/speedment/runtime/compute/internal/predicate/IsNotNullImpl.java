package com.speedment.runtime.compute.internal.predicate;

import com.speedment.runtime.compute.expression.predicate.IsNotNull;
import com.speedment.runtime.compute.expression.predicate.IsNull;
import com.speedment.runtime.compute.trait.ToNullable;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of the {@link IsNotNull}-interface.
 *
 * @author Emil Forslund
 * @since  3.1.2
 */
public final class IsNotNullImpl<T, R>
implements IsNotNull<T, R> {

    private final ToNullable<T, R, ?> expression;

    public IsNotNullImpl(ToNullable<T, R, ?> expression) {
        this.expression = requireNonNull(expression);
    }

    @Override
    public IsNull<T, R> negate() {
        return new IsNullImpl<>(expression);
    }

    @Override
    public ToNullable<T, R, ?> expression() {
        return expression;
    }
}
