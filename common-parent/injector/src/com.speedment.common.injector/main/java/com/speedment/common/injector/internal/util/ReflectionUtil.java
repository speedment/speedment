/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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

import com.speedment.common.injector.exception.NoDefaultConstructorException;
import static com.speedment.common.injector.internal.util.PropertiesUtil.configureParams;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.stream.Stream;

/**
 * Some common utility methods for analyzing classes with reflection.
 *
 * @author Emil Forslund
 * @since 1.0.0
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
        return traverseAncestors(clazz)
            .flatMap(c -> Stream.of(c.getDeclaredMethods()));
    }

    public static Stream<Class<?>> traverseAncestors(Class<?> clazz) {
        if (clazz.getSuperclass() == null) { // We have reached Object.class
            return Stream.of(clazz);
        } else {
            return Stream.concat(
                Stream.of(clazz),
                Stream.concat(
                    traverseAncestors(clazz.getSuperclass()),
                    Stream.of(clazz.getInterfaces())
                )
            ).distinct();
        }
    }

    public static <T> T newInstance(Class<T> type, Properties properties)
        throws InstantiationException, NoDefaultConstructorException {
        try {
            final Constructor<T> constr = type.getDeclaredConstructor();
            constr.setAccessible(true);

            final T instance = constr.newInstance();
            configureParams(instance, properties);

            return instance;

        } catch (final NoSuchMethodException ex) {
            throw new NoDefaultConstructorException(
                "Could not find any default constructor for class '"
                + type.getName() + "'.", ex
            );

        } catch (final IllegalAccessException
            | IllegalArgumentException
            | InvocationTargetException ex) {

            throw new RuntimeException("Unable to create class '" + type.getName() + "'", ex);
        }
    }

    private ReflectionUtil() {}
}
