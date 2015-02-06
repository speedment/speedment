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
package com.speedment.orm.config.model.parameters;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <R> Return type
 */
interface Nameable<R extends Enum<R> & Nameable<R>> {

    // Optional<R> findByNameIgnoreCase(final String name);
    String getName();

    static class Hidden {

        static <R extends Enum<R> & Nameable<R>> Optional<R> findByNameIgnoreCase(R[] values, final String name) {
            return Stream.of(values)
                    .filter((R t) -> name.equalsIgnoreCase(t.getName()))
                    .findFirst();
        }

        static <R extends Enum<R> & Nameable<R>> Map<String, R> buildMap(R[] values) {
            return Collections.unmodifiableMap(
                    Stream.of(values)
                    .collect(Collectors.toMap((dt) -> Hidden.normalize(dt.getName()), Function.identity())));
        }

        static <R> Optional<R> findByNameIgnoreCase(Map<String, R> map, final String name) {
            return Optional.ofNullable(map.get(normalize(name)));
        }

        private static String normalize(String string) {
            return string.toLowerCase();
        }
    }

}
