/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.db;

import com.speedment.annotation.Api;
import com.speedment.exception.SpeedmentException;
import java.sql.SQLException;
import java.util.Objects;
import static java.util.Objects.requireNonNull;
import java.util.function.Function;

/**
 *
 * @author pemi
 * @param <T> Input type
 * @param <R> Result type
 */
@Api(version = "2.1")
@FunctionalInterface
public interface SqlFunction<T, R> {

    R apply(T t) throws SQLException;

    static <T, R> SqlFunction<T, R> wrap(Function<T, R> inner) {
        return requireNonNull(inner)::apply;
    }

    default Function<T, R> unWrap() {
        return t -> {
            try {
                return this.apply(t);
            } catch (SQLException sqle) {
                throw new SpeedmentException(sqle);
            }
        };
    }

    default <V> SqlFunction<V, R> compose(SqlFunction<? super V, ? extends T> before) {
        Objects.requireNonNull(before);
        return (V v) -> apply(before.apply(v));
    }

    default <V> SqlFunction<T, V> andThen(SqlFunction<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.apply(apply(t));
    }

    static <T> SqlFunction<T, T> identity() {
        return (T t) -> t;
    }
}