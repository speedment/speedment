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
package com.speedment.core.config.model.parameters;

import com.speedment.core.config.model.aspects.Child;
import com.speedment.core.config.model.aspects.Enableable;
import com.speedment.core.config.model.aspects.Node;
import static java.util.Collections.unmodifiableMap;
import java.util.Map;
import java.util.Optional;
import static java.util.function.Function.identity;
import java.util.function.Predicate;
import static java.util.stream.Collectors.toMap;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public final class EnumHelper {
    
    private EnumHelper() {}

    public static <E extends Enum<E>, C extends Node & Enableable> E defaultFor(
            final Stream<E> stream, 
            final Predicate<E> predicate, 
            final C entity, 
            final Class<?> ableClass, 
            final E defaultValue) {

        return Optional.ofNullable(entity)
            .flatMap(e -> e.asChild())
            .flatMap(e -> streamFor(
                    stream, predicate, e, ableClass
                )
                .filter(predicate)
                .findAny()
            )
            .orElse(defaultValue);
    }

    public static <E extends Enum<E>, C extends Child<?>> Stream<E> streamFor(
            final Stream<E> stream, 
            final Predicate<E> predicate, 
            final C entity, 
            final Class<?> ableClass) {
        
        if (ableClass.isAssignableFrom(entity.getParentInterfaceMainClass())) {
            return stream;
        } else {
            return stream.filter(predicate.negate());
        }
    }

    public static <E extends Enum<E> & Nameable<E>> Map<String, E> buildMap(
            final E[] values) {
        
        return unmodifiableMap(Stream.of(values).collect(
            toMap(dt -> normalize(dt.getName()), identity())
        ));
    }

    public static <E> Optional<E> findByNameIgnoreCase(
        final Map<String, E> map, 
        final String name) {
        
        return Optional.ofNullable(map.get(normalize(name)));
    }

    private static String normalize(final String string) {
        return string == null ? null : string.toLowerCase();
    }
}