/*
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

import com.speedment.common.injector.InjectorProxy;
import com.speedment.common.injector.MissingArgumentStrategy;
import com.speedment.common.injector.annotation.*;
import com.speedment.common.injector.exception.InjectorException;
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
import java.util.stream.Stream;

import static com.speedment.common.injector.internal.util.PropertiesUtil.configureParams;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

/**
 * Some common utility methods for analyzing classes with reflection.
 *
 * @author Emil Forslund
 * @since 1.0.0
 */
public final class ReflectionUtil {

    private ReflectionUtil() {}

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

    public static MissingArgumentStrategy missingArgumentStrategy(Executable executable) {
        final Execute execute = executable.getAnnotation(Execute.class);
        final ExecuteBefore executeBefore = executable.getAnnotation(ExecuteBefore.class);
        if (execute != null) {
            return execute.missingArgument();
        } else if (executeBefore != null) {
            return executeBefore.missingArgument();
        } else {
            return MissingArgumentStrategy.THROW_EXCEPTION;
        }
    }

    public static <T> Optional<T> tryToCreate(Class<T> clazz, Properties properties, List<Object> instances, Set<Class<?>> allInjectableTypes, InjectorProxy injectorProxy)
        throws InstantiationException {
        try {
            final Optional<Constructor<T>> oConstr = findConstructor(clazz, instances, allInjectableTypes);

            if (!oConstr.isPresent()) {
                return Optional.empty();
            }

            final Constructor<T> constr = oConstr.get();

            // injectorProxy.setAccessable(constr);

            final Parameter[] params = constr.getParameters();
            final Object[] args = new Object[params.length];
            for (int i = 0; i < params.length; i++) {
                final Parameter param = params[i];
                final Config config = param.getAnnotation(Config.class);
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
                    final Class<?> paramType = param.getType();
                    final Optional<? extends Class<?>> injectKeyClass = traverseAncestors(paramType)
                        .filter(c -> c.isAnnotationPresent(InjectKey.class))
                        .map(c -> c.getAnnotation(InjectKey.class).value())
                        .findFirst();
                    if (injectKeyClass.isPresent()) {
                        // Make sure that there are no other pending instances that
                        // may implement this type, see #758
                        //
                        // This set contains all classes that we need to be instantiated
                        // before we know which one to select
                        final Set<Class<?>> needed = allInjectableTypes.stream()
                            .filter(c -> traverseAncestors(c)
                                .anyMatch(a ->
                                    a.isAnnotationPresent(InjectKey.class) &&
                                        a.getAnnotation(InjectKey.class).value().equals(injectKeyClass.get())
                                )
                            )
                            .collect(toSet());

                        // Remove the existing classes
                        instances.stream()
                            .map(Object::getClass)
                            .forEach(needed::remove);

                        if (!needed.isEmpty()) {
                            // We do not know all instances yet so we have to wait for
                            // their creation
                            return Optional.empty();
                        }
                    }

                    final Optional<Object> value = instances.stream()
                        .filter(o -> paramType.isAssignableFrom(o.getClass()))
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

            final T instance = injectorProxy.newInstance(constr, args);
            configureParams(instance, properties);

            return Optional.of(instance);

        } catch (final IllegalAccessException
            | IllegalArgumentException
            | InvocationTargetException ex) {

            throw new InjectorException(String.format(
                "Unable to create class '%s'.", clazz.getName()
            ), ex);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> Optional<Constructor<T>> findConstructor(Class<T> clazz, List<Object> instances, Set<Class<?>> allInjectableTypes) {
        // If at least one constructor has the @Inject-annotation, only
        // @Inject-annotated constructors should be considered.
        return Arrays.stream(clazz.getDeclaredConstructors())
            .sorted(Comparator.comparing(constr -> constr.isAnnotationPresent(Inject.class) ? 1 : 0))
            .filter(constr -> isOnlyIfMissingConditionSatisfied(constr, allInjectableTypes))
            .filter(constr -> canBeInvoked(constr, instances))
            .map(constr -> (Constructor<T>) constr)
            .findFirst();
    }

    @SuppressWarnings("unchecked")
    public static <T> String errorMsg(Class<T> c, List<Object> instances) {
        final Predicate<Constructor<?>> onlyInject;
        if (hasConstructorWithInjectAnnotation(c)) {
            onlyInject = constr -> constr.isAnnotationPresent(Inject.class);
        } else {
            onlyInject = constr -> true;
        }

        return Arrays.stream(c.getDeclaredConstructors())
            .map(constructor -> (Constructor<T>) constructor)
            .filter(onlyInject)
            .map(con ->
                String.format("  %s %s %n %s ",
                    con.isAnnotationPresent(Inject.class) ? "@Inject" : "       ",
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
        return param.isAnnotationPresent(Config.class);
    }

    private static boolean paramIsInjectable(Parameter param, List<Object> instances) {
        return instances.stream()
            .anyMatch(o -> param.getType().isAssignableFrom(o.getClass()));
    }

    private static boolean hasConstructorWithInjectAnnotation(Class<?> clazz) {
        return Stream.of(clazz.getDeclaredConstructors())
            .anyMatch(constr -> constr.isAnnotationPresent(Inject.class));
    }

    private static boolean isOnlyIfMissingConditionSatisfied(Constructor<?> constr, Set<Class<?>> allInjectableTypes) {
        final OnlyIfMissing missing = constr.getAnnotation(OnlyIfMissing.class);

        // If the annotation is not there then this constructor
        // should always be considered.
        if (missing == null) return true;

            // If the annotation is present then only if none of the
            // types listed have an implementation in the set should
            // the constructor be considered.
        else return Stream.of(missing.value()).noneMatch(missingType ->
            allInjectableTypes.stream()
                .anyMatch(missingType::isAssignableFrom)
        );
    }

}
