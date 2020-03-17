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
import java.util.function.Supplier;

/**
 * A variation of the standard {@code java.util.function.Supplier} that throws
 * a {@code SQLException} if an error occurred while the supplier was getted.
 * 
 * @param <T>  supplied type
 * 
 * @author  Per Minborg
 */
@FunctionalInterface
public interface SqlSupplier<T> {

    T get() throws SQLException;
    
    static <T> SqlSupplier<T> wrap(Supplier<T> inner) { 
        return requireNonNull(inner)::get; 
    }
}