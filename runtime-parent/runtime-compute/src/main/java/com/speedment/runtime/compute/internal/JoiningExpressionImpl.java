/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.runtime.compute.internal;

import com.speedment.runtime.compute.ToString;
import com.speedment.runtime.compute.expression.JoiningExpression;

import java.util.List;
import java.util.StringJoiner;

import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;

/**
 * The default implementation of {@link JoiningExpression}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class JoiningExpressionImpl<T> implements JoiningExpression<T> {

    private final CharSequence separator;
    private final CharSequence prefix;
    private final CharSequence suffix;
    private final List<ToString<T>> expressions;

    public JoiningExpressionImpl(
            final CharSequence separator,
            final CharSequence prefix,
            final CharSequence suffix,
            final List<ToString<T>> expressions) {
        this.separator   = requireNonNull(separator);
        this.prefix      = requireNonNull(prefix);
        this.suffix      = requireNonNull(suffix);
        this.expressions = unmodifiableList(expressions);
    }

    @Override
    public List<ToString<T>> expressions() {
        return expressions;
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
        for (final ToString<T> expression : expressions) {
            joiner.add(expression.apply(object));
        }
        return joiner.toString();
    }
}