package com.speedment.runtime.compute.internal.predicate;

import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.predicate.IsNotNull;
import com.speedment.runtime.compute.expression.predicate.IsNull;
import com.speedment.runtime.compute.trait.ToNullable;

import static java.util.Objects.requireNonNull;

/**
 * @author Emil Forslund
 * @since  1.2.1
 */
public final class IsNotNullImpl<T, R, NON_NULLABLE extends Expression<T>>
implements IsNotNull<T, R, NON_NULLABLE> {

    private final ToNullable<T, R, NON_NULLABLE> expression;

    public IsNotNullImpl(ToNullable<T, R, NON_NULLABLE> expression) {
        this.expression = requireNonNull(expression);
    }

    @Override
    public IsNull<T, R,NON_NULLABLE> negate() {
        return new IsNullImpl<>(expression);
    }

    @Override
    public ToNullable<T, R, NON_NULLABLE> expression() {
        return expression;
    }
}
