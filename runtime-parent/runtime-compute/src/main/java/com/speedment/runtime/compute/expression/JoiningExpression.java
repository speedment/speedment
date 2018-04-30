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

    List<ToString<T>> expressions();

    CharSequence prefix();

    CharSequence suffix();

    CharSequence separator();

}