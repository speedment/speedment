/**
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
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

import com.speedment.common.injector.annotation.Config;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.injector.exception.NoDefaultConstructorException;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.speedment.common.injector.internal.util.PropertiesUtil.configureParams;
import static java.util.stream.Collectors.joining;

/**
 * Some common utility methods for analyzing classes with reflection.
 *
 * @author Emil Forslund
 * @since 1.0.0
 */
public final class ReflectionUtil {

    private final static Predicate<? super Executable> HAS_INJECT_ANNOTATION =
        exec -> exec.getAnnotation(Inject.class) != null;

    private static final Comparator<Executable> INJECT_FIRST_COMPARATOR = (a, b) -> Boolean.compare(HAS_INJECT_ANNOTATION.test(b), HAS_INJECT_ANNOTATION.test(a));

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

    public static <T> Optional<T> tryToCreate(Class<T> clazz, Properties properties, List<Object> instances)
        throws InstantiationException, NoDefaultConstructorException {
        try {
            // TODO: If at least one constructor has the @Inject-annotation, only @Inject-annotated constructors should be considered.

            final Optional<Constructor<T>> oConstr = findConstructor(clazz, properties, instances);

            if (!oConstr.isPresent()) {
                return Optional.empty();
            }

            final Constructor<T> constr = oConstr.get();
            constr.setAccessible(true);

            final Parameter[] params = constr.getParameters();
            final Object[] args = new Object[params.length];
            for (int i = 0; i < params.length; i++) {
                final Parameter param = params[i];
                final Config config = params[i].getAnnotation(Config.class);
                if (config != null) {
                    final String serialized;
                    if (properties.containsKey(config.name())) {
                        serialized = properties.getProperty(config.name());
                    } else {
                        serialized = config.value();
                    }

                    final Class<?> type = param.getType();
                    Object value;
                    if (boolean.class == type
                    || Boolean.class.isAssignableFrom(type)) {
                        value = Boolean.parseBoolean(serialized);
                    } else if (byte.class == type
                    || Byte.class.isAssignableFrom(type)) {
                        value = Byte.parseByte(serialized);
                    } else if (short.class == type
                    || Short.class.isAssignableFrom(type)) {
                        value = Short.parseShort(serialized);
                    } else if (int.class == type
                    || Integer.class.isAssignableFrom(type)) {
                        value = Integer.parseInt(serialized);
                    } else if (long.class == type
                    || Long.class.isAssignableFrom(type)) {
                        value = Long.parseLong(serialized);
                    } else if (float.class == type
                    || Float.class.isAssignableFrom(type)) {
                        value = Float.parseFloat(serialized);
                    } else if (double.class == type
                    || Double.class.isAssignableFrom(type)) {
                        value = Double.parseDouble(serialized);
                    } else if (String.class.isAssignableFrom(type)) {
                        value = serialized;
                    } else if (char.class == type
                    || Character.class.isAssignableFrom(type)) {
                        if (serialized.length() == 1) {
                            value = serialized.charAt(0);
                        } else {
                            throw new IllegalArgumentException(
                                "Value '" + serialized + "' is to long to be " +
                                "parsed into a field of type '" +
                                type.getName() + "'."
                            );
                        }
                    } else if (File.class.isAssignableFrom(type)) {
                        value = new File(serialized);
                    } else if (URL.class.isAssignableFrom(type)) {
                        try {
                            value = new URL(serialized);
                        } catch (final MalformedURLException ex) {
                            throw new IllegalArgumentException(String.format(
                                "Specified URL '%s' is malformed.", serialized
                            ), ex);
                        }
                    } else {
                        throw new IllegalArgumentException(String.format(
                            "Unsupported type '%s' injected into the " +
                            "constructor of class '%s'.",
                            type.getName(), clazz.getName()
                        ));
                    }

                    args[i] = value;

                } else { // Not a @Config-field
                    final Optional<Object> value = instances.stream()
                        .filter(o -> param.getType().isAssignableFrom(o.getClass()))
                        .findFirst();

                    if (value.isPresent()) {
                        args[i] = value.get();
                    } else {
                        throw new IllegalArgumentException(String.format(
                            "No instance found that match the required type " +
                            "'%s' in the constructor for injected class '%s'.",
                            param.getClass().getName(),
                            clazz.getName()
                        ));
                    }
                }
            }

            final T instance = constr.newInstance(args);
            configureParams(instance, properties);

            return Optional.of(instance);

        } catch (final IllegalAccessException
            | IllegalArgumentException
            | InvocationTargetException ex) {

            throw new RuntimeException(String.format(
                "Unable to create class '%s'.", clazz.getName()
            ), ex);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> Optional<Constructor<T>> findConstructor(Class<T> clazz, Properties properties, List<Object> instances) {
        return Arrays.stream(clazz.getDeclaredConstructors())
                .filter(constructor -> canBeInvoked(constructor, instances))
                .map(constructor -> (Constructor<T>) constructor)
                .min(INJECT_FIRST_COMPARATOR);
    }


    public static <T> String errorMsg(Class<T> c, List<Object> instances) {
        return Arrays.stream(c.getDeclaredConstructors())
            .map(constructor -> (Constructor<T>) constructor)
            .sorted(INJECT_FIRST_COMPARATOR)
            .map(con ->
                String.format("  %s %s %n %s ",
                    con.getAnnotation(Inject.class) == null ? "       " : "@Inject",
                    con.toString(),
                    Stream.of(con.getParameters())
                    .map(p -> {
                        if (paramIsConfig(p)) {
                            return String.format("      %s %s%n", Config.class.getSimpleName(), p.toString());
                        } else {
                            return String.format("      %s %s%n", p.toString(), paramIsInjectable(p, instances) ? "" : " <-- Missing");
                        }
                    }).collect(joining(String.format("%n")))
                )
            ).collect(joining(String.format("%n")));
    }

    private static boolean canBeInvoked(Executable executable, List<Object> instances) {
        return Stream.of(executable.getParameters())
            .allMatch(p -> paramIsConfig(p) || paramIsInjectable(p, instances));
    }

    private static boolean paramIsConfig(Parameter param) {
        return param.getAnnotation(Config.class) != null;
    }

    private static boolean paramIsInjectable(Parameter param, List<Object> instances) {
        return instances.stream()
            .anyMatch(o -> param.getType().isAssignableFrom(o.getClass()));
    }

    private ReflectionUtil() {}
}
