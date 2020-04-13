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
package com.speedment.runtime.core.db;

import com.speedment.runtime.core.exception.SpeedmentException;
import java.sql.SQLException;
import java.util.Objects;
import static java.util.Objects.requireNonNull;
import java.util.function.Predicate;

/**
 * A variation of the standard {@code java.util.function.Predicate} that throws
 * a {@code SQLException} if an error occurred while the predicate was tested.
 * 
 * @param <T>  input type
 * 
 * @author  Per Minborg
 */
@FunctionalInterface
public interface SqlPredicate<T> {

    boolean test(T t) throws SQLException;

    static <T> SqlPredicate<T> wrap(Predicate<T> inner) {
        return requireNonNull(inner)::test;
    }

    default Predicate<T> unWrap() {
        return t -> {
            try {
                return this.test(t);
            } catch (SQLException sqle) {
                throw new SpeedmentException(sqle);
            }
        };
    }

    default SqlPredicate<T> and(SqlPredicate<? super T> other) {
        Objects.requireNonNull(other);
        return t -> test(t) && other.test(t);
    }

    default SqlPredicate<T> negate() {
        return t -> !test(t);
    }

    default SqlPredicate<T> or(Predicate<? super T> other) {
        Objects.requireNonNull(other);
        return t -> test(t) || other.test(t);
    }

}
