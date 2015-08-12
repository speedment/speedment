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
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <E> The Enum type
 */
public interface EnumHelper<E extends Enum<E>> {
    
    String getName();

    class Hidden {
        
        static <E extends Enum<E> & EnumHelper<E>, C extends Node & Enableable> 
            E defaultFor(
                Stream<E> stream, 
                Predicate<E> predicate, 
                final C entity, 
                final Class<?> ableClass, 
                final E defaultValue) {
            
            return Optional.ofNullable(entity)
                .flatMap(e -> e.asChild())
                .flatMap(e -> streamFor(
                    stream, predicate, e, ableClass
                ).filter(predicate).findAny())
                .orElse(defaultValue);
        }
    
        static <E extends Enum<E>, H extends EnumHelper<E>, C extends Child<?>> Stream<E> streamFor(
            Stream<E> stream, Predicate<E> predicate, final C entity, final Class<?> ableClass) {
            if (ableClass.isAssignableFrom(entity.getParentInterfaceMainClass())) {
                return stream;
            } else {
                return stream.filter(predicate.negate());
            }
        }

        static <E extends Enum<E> & EnumHelper<E>> Map<String, E> buildMap(E[] values) {
            return Collections.unmodifiableMap(Stream.of(values).collect(
                Collectors.toMap((dt) -> Hidden.normalize(dt.getName()), Function.identity())
            ));
        }

        static <E> Optional<E> findByNameIgnoreCase(Map<String, E> map, final String name) {
            return Optional.ofNullable(map.get(normalize(name)));
        }

        private static String normalize(String string) {
            return string == null ? null : string.toLowerCase();
        }
    }
}