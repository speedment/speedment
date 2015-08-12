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
package com.speedment.util;

import static com.speedment.util.Util.instanceNotAllowed;
import java.util.Optional;

/**
 *
 * @author pemi
 */
public final class Cast {

    public static <T> Optional<T> cast(Object o, Class<T> clazz) {
        if (clazz.isAssignableFrom(o.getClass())) {
            final T result = clazz.cast(o);
            return Optional.of(result);
        }
        return Optional.empty();
    }

    public static <T> T orFail(Object obj, Class<T> clazz) {
        return Optional.ofNullable(obj)
            .filter(o -> clazz.isAssignableFrom(o.getClass()))
            .map(clazz::cast)
            .get();
    }
    
    /**
     * Utility classes should not be instantiated.
     */
    private Cast() { instanceNotAllowed(getClass()); }
}