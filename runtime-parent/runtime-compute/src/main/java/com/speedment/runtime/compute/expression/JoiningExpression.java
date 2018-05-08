package com.speedment.runtime.compute.expression;

import com.speedment.runtime.compute.ToString;

import java.util.List;

/**
 * A special type of {@link ToString} expression that joins several strings
 * together using optionally a separator, a prefix and a suffix.
 *
 * @param <T>  the input entity type
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface JoiningExpression<T> extends ToString<T> {

    /**
     * Returns an unmodifiable list of the expressions that are joined together
     * in this expression. Whenever an entity is passed to this expression, the
     * result will be the result of passing the same entity to all these
     * expressions and joining them together with an optional {@link #prefix()},
     * {@link #separator()} and {@link #suffix()}.
     *
     * @return  unmodifiable list of expressions that make up this expression
     */
    List<ToString<T>> expressions();

    /**
     * Returns the prefix to put in front of the result of this expression. If
     * no prefix is used, then this method will return an empty string.
     *
     * @return  the prefix
     */
    CharSequence prefix();

    /**
     * Returns the suffix to put at the end of the result of this expression. If
     * no suffix is used, then this method will return an empty string.
     *
     * @return  the suffix
     */
    CharSequence suffix();

    /**
     * Returns the separator that will be placed in-between the results of all
     * the {@link #expressions()} to separate them. If no separator is used,
     * then this method will return an empty string.
     *
     * @return  the separator
     */
    CharSequence separator();

}