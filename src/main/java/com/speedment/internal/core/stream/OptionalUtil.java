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
package com.speedment.internal.core.stream;

import static com.speedment.internal.util.StaticClassUtil.instanceNotAllowed;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public final class OptionalUtil {

    public static <T> Stream<T> from(Optional<T> optional) {
        requireNonNull(optional);
        return optional.isPresent() ? Stream.of(optional.get()) : Stream.empty();
    }

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

    /**
     * Utility classes should not be instantiated.
     */
    private OptionalUtil() { instanceNotAllowed(getClass()); }
}
