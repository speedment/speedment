package com.speedment.runtime.compute.internal;

import com.speedment.runtime.compute.ToString;
import com.speedment.runtime.compute.expression.JoiningExpression;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import static java.util.Objects.requireNonNull;

/**
 * The default implementation of {@link JoiningExpression}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class BinaryJoiningExpressionImpl<T> implements JoiningExpression<T> {

    private final CharSequence separator, prefix, suffix;
    private final ToString<T> first, second;

    public BinaryJoiningExpressionImpl(
            final CharSequence separator,
            final CharSequence prefix,
            final CharSequence suffix,
            final ToString<T> first,
            final ToString<T> second) {
        this.separator = requireNonNull(separator);
        this.prefix    = requireNonNull(prefix);
        this.suffix    = requireNonNull(suffix);
        this.first     = requireNonNull(first);
        this.second    = requireNonNull(second);
    }

    @Override
    public List<ToString<T>> expressions() {
        return Arrays.asList(first, second);
    }

    @Override
    public CharSequence prefix() {
        return prefix;
    }

    @Override
    public CharSequence suffix() {
        return suffix;
    }

    @Override
    public CharSequence separator() {
        return separator;
    }

    @Override
    public String apply(T object) {
        final StringJoiner joiner = new StringJoiner(separator, prefix, suffix);
        joiner.add(first.apply(object));
        joiner.add(second.apply(object));
        return joiner.toString();
    }
}