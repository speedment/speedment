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
package com.speedment.common.injector.internal.util;

import com.speedment.common.injector.Injector;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 * @since  1.2.0
 */
public final class InjectorUtil {

    private InjectorUtil() {}

    public static <T> T findIn(
            Class<T> type, 
            Injector injector, 
            List<Object> instances, 
            boolean required) {
        
        final Optional<T> found = findAll(type, injector, instances)
            .findFirst(); // Order is important.

        if (required) {
            return found.orElseThrow(() -> 
                new IllegalArgumentException(
                    "Could not find any installed implementation of " + 
                    type.getName() + "."
                )
            );
        } else {
            return found.orElse(null);
        }
    }
    
    public static <T> Stream<T> findAll(
            Class<T> type, 
            Injector injector, 
            List<Object> instances) {
        
        if (Injector.class.isAssignableFrom(type)) {
            @SuppressWarnings("unchecked")
            final T casted = (T) injector;
            return Stream.of(casted);
        }

        return instances.stream()
            .filter(inst -> type.isAssignableFrom(inst.getClass()))
            .map(type::cast);
    }
    
}