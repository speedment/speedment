/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.core.stream;

import static com.speedment.util.StaticClassUtil.instanceNotAllowed;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

/**
 *
 * @author Emil Forslund
 */
public final class OptionalUtil {

    public static Object unwrap(Object potentiallyOptional) {
        // potentiallyOptional can be null. If it is, null shall be returned
        if (potentiallyOptional instanceof Optional<?>) {
            return unwrap((Optional<?>) potentiallyOptional);
        } else {
            return potentiallyOptional;
        }
    }

    public static <T> T unwrap(Optional<T> optional) {
        // optional can be null. If it is, null shall be returned
        if (optional == null) {
            return null;
        }
        return optional.orElse(null);
    }

    public static OptionalLong ofNullable(Long l) {
        if (l == null) {
            return OptionalLong.empty();
        } else {
            return OptionalLong.of(l);
        }
    }

    public static OptionalInt ofNullable(Integer i) {
        if (i == null) {
            return OptionalInt.empty();
        } else {
            return OptionalInt.of(i);
        }
    }

    public static OptionalDouble ofNullable(Double d) {
        if (d == null) {
            return OptionalDouble.empty();
        } else {
            return OptionalDouble.of(d);
        }
    }

    public static OptionalLong parseLong(String value) {
        if (value == null) {
            return OptionalLong.empty();
        } else {
            return OptionalLong.of(Long.parseLong(value));
        }
    }

    public static OptionalInt parseInt(String value) {
        if (value == null) {
            return OptionalInt.empty();
        } else {
            return OptionalInt.of(Integer.parseInt(value));
        }
    }

    public static OptionalDouble parseDouble(String value) {
        if (value == null) {
            return OptionalDouble.empty();
        } else {
            return OptionalDouble.of(Double.parseDouble(value));
        }
    }

    /**
     * Utility classes should not be instantiated.
     */
    private OptionalUtil() {
        instanceNotAllowed(getClass());
    }
}
