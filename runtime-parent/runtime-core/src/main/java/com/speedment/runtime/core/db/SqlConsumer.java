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

import java.sql.SQLException;
import static java.util.Objects.requireNonNull;
import java.util.function.Consumer;

/**
 * A variation of the standard {@code java.util.function.Consumer} that throws a
 * {@code SQLException} if an error occurred while the supplier was getted.
 *
 * @param <T> supplied type
 *
 * @author Per Minborg
 */
@FunctionalInterface
public interface SqlConsumer<T> {

    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument to consume
     * @throws java.sql.SQLException on error
     */
    void accept(T t) throws SQLException;

    static <T> SqlConsumer<T> wrap(Consumer<T> inner) {
        return requireNonNull(inner)::accept;
    }
}
