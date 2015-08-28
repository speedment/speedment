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
package com.speedment.core.config.utils;

import com.speedment.api.annotation.External;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public final class MethodsParser {
    
    private MethodsParser() {}

    public static final Predicate<Method> 
        METHOD_IS_PUBLIC = (m) -> Modifier.isPublic(m.getModifiers()),
        METHOD_IS_GETTER = (m) -> m.getParameterCount() == 0 && (m.getName().startsWith("get") || m.getName().startsWith("is")),
        METHOD_IS_SETTER = (m) -> m.getParameterCount() == 1 && (m.getName().startsWith("set")),
        METHOD_IS_EXTERNAL = MethodsParser::isExternal,
        METHOD_IS_EXTERNAL_AND_NONE_SECRET = MethodsParser::isExternalAndNoneSecret,
        METHOD_IS_EXTERNAL_AND_SECRET = MethodsParser::isExternalAndSecret,
        METHOD_IS_VISIBLE_IN_GUI = MethodsParser::isVisibleInGUI;

    public static Stream<Method> streamOfExternalNoneSecretGetters(Class<?> clazz) {
        return getMethods(clazz,
            METHOD_IS_PUBLIC
            .and(METHOD_IS_GETTER)
            .and(METHOD_IS_EXTERNAL_AND_NONE_SECRET)
        ).stream();
    }
    
    public static Stream<Method> streamOfExternalSetters(Class<?> clazz) {
        return getMethods(clazz,
            METHOD_IS_PUBLIC
            .and(METHOD_IS_SETTER)
            .and(METHOD_IS_EXTERNAL)
        ).stream();
    }

    private static Set<Method> getMethods(Class<?> clazz, Predicate<Method> filter) {
        return addMethods(new HashSet<>(), clazz, filter);
    }

    private static boolean isExternal(Method method) {
        return isExternal(method, method.getDeclaringClass());
    }
    
    public static External getExternalFor(final Method method, final Class<?> clazz) {
        if (method == null || clazz == null) {
            return null;
        }
        
        final External e = method.getAnnotation(External.class);
        if (e != null) {
            return e;
        }
        // Also try the superClass and all the interfaces it implements
        final List<Class<?>> classCandidates = new ArrayList<>(Arrays.asList(clazz.getInterfaces()));
        final Class<?> superClass = clazz.getSuperclass();
        
        if (superClass != null) {
            classCandidates.add(superClass);
        }
        
        for (final Class<?> classCandidate : classCandidates) {
            try {
                return getExternalFor(classCandidate.getMethod(method.getName(), method.getParameterTypes()), classCandidate);
            } catch (NoSuchMethodException | SecurityException ex) {}
        }
        
        return null;
    }

    private static boolean isExternal(final Method method, final Class<?> clazz) {
        return getExternalFor(method, clazz) != null;
    }
    
    private static boolean isVisibleInGUI(Method method) {
        return isVisibleInGUI(method, method.getDeclaringClass());
    }
    
    private static boolean isVisibleInGUI(final Method method, final Class<?> clazz) {
        return Optional.ofNullable(getExternalFor(method, clazz))
            .filter(External::isVisibleInGui)
            .isPresent();
    }

    private static boolean isExternalAndNoneSecret(Method method) {
        return isExternalAndNoneSecret(method, method.getDeclaringClass());
    }
    
    private static boolean isExternalAndNoneSecret(final Method method, final Class<?> clazz) {
        return Optional.ofNullable(getExternalFor(method, clazz))
            .filter(e-> !e.isSecret())
            .isPresent();
    }
    
    private static boolean isExternalAndSecret(Method method) {
        return isExternalAndSecret(method, method.getDeclaringClass());
    }
    
    private static boolean isExternalAndSecret(final Method method, final Class<?> clazz) {
        return Optional.ofNullable(getExternalFor(method, clazz))
            .filter(External::isSecret)
            .isPresent();
    }
    
    private static Set<Method> addMethods(Set<Method> methods, Class<?> clazz, Predicate<Method> filter) {
        if (clazz == Object.class) {
            return methods;
        }
        Stream.of(clazz.getDeclaredMethods())
            .filter(filter)
            .forEach(methods::add);
        addMethods(methods, clazz.getSuperclass(), filter); // Recursively add the superclass methods
        return methods;
    }
}
