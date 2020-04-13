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
package com.speedment.common.injector.internal;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

final class Injectable<T> {
    private final Class<T> cls;
    private final Supplier<T> supplier;

    Injectable(Class<T> cls, Supplier<T> supplier) {
        this.cls = requireNonNull(cls);
        this.supplier = supplier; // Nullable
    }

    public Class<T> get() {
        return cls;
    }

    boolean hasSupplier() {
        return supplier != null;
    }

    public Supplier<T> supplier() {
        return Optional.ofNullable(supplier).orElseThrow(
            () -> new UnsupportedOperationException(format(
                "Injectable %s does not have a supplier.",
                cls.getName()
            ))
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        final Injectable<?> that = (Injectable<?>) o;
        return cls.equals(that.cls) &&
            Objects.equals(supplier, that.supplier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cls, supplier);
    }
}
