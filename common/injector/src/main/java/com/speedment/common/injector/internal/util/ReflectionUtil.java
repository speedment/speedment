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
package com.speedment.common.injector.internal.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.stream.Stream;

/**
 *
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class ReflectionUtil {
    
    public static Stream<Field> traverseFields(Class<?> clazz) {
        final Class<?> parent = clazz.getSuperclass();
        final Stream<Field> inherited;
        
        if (parent != null) {
            inherited = traverseFields(parent);
        } else {
            inherited = Stream.empty();
        }
        
        return Stream.concat(inherited, Stream.of(clazz.getDeclaredFields()));
    }
    
    public static Stream<Method> traverseMethods(Class<?> clazz) {
        final Class<?> parent = clazz.getSuperclass();
        final Stream<Method> inherited;
        
        if (parent != null) {
            inherited = traverseMethods(parent);
        } else {
            inherited = Stream.empty();
        }
        
        return Stream.concat(inherited, Stream.of(clazz.getDeclaredMethods()));
    }
    
    private ReflectionUtil() {}
}
