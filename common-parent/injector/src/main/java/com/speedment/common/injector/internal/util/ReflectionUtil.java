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

import com.speedment.common.injector.InjectorProxy;
import com.speedment.common.injector.MissingArgumentStrategy;
import com.speedment.common.injector.annotation.*;
import com.speedment.common.injector.exception.InjectorException;

import java.io.File;
import java.lang.reflect.*;
import java.net.URL;
import java.util.*;
import java.util.function.Function;
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

            final Parameter[] params = constr.getParameters();
            final Object[] args = new Object[params.length];
            for (int i = 0; i < params.length; i++) {
                final Parameter param = params[i];
                final Config config = param.getAnnotation(Config.class);
                final Object arg;
                if (config != null) {
                    arg = configField(clazz, properties, param, config);
                } else {
                    arg = notConfigField(clazz, instances, allInjectableTypes, param);
                    if (arg == null) {
                        return Optional.empty();
                    }
                }
                args[i] = arg;
            }

            final T instance = injectorProxy.newInstance(constr, args);
            configureParams(instance, properties, injectorProxy);

            return Optional.of(instance);

        } catch (final IllegalAccessException
            | IllegalArgumentException
            | InvocationTargetException ex) {

            throw new InjectorException(String.format(
                "Unable to create class '%s'.", clazz.getName()
            ), ex);
        }
    }

    private static <T> Object configField(Class<T> clazz, Properties properties, Parameter param, Config config) {
        final String serialized;
        if (properties.containsKey(config.name())) {
            serialized = properties.getProperty(config.name());
        } else {
            serialized = config.value();
        }

        final Class<?> type = param.getType();
        return parse(type, serialized).orElseThrow(() -> new IllegalArgumentException(String.format(
            "Unsupported type '%s' injected into the " +
                "constructor of class '%s'.",
            type.getName(), clazz.getName()
        )));

    }

    private static <T> Object notConfigField(Class<T> clazz, List<Object> instances, Set<Class<?>> allInjectableTypes, Parameter param) {
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
                    .anyMatch(a -> a.isAnnotationPresent(InjectKey.class) && a.getAnnotation(InjectKey.class).value().equals(injectKeyClass.get()))
                )
                .collect(toSet());

            // Remove the existing classes
            instances.stream()
                .map(Object::getClass)
                .forEach(needed::remove);

            if (!needed.isEmpty()) {
                // We do not know all instances yet so we have to wait for
                // their creation
                return null;
            }
        }

        final Optional<Object> value = instances.stream()
            .filter(o -> paramType.isAssignableFrom(o.getClass()))
            .findFirst();

        if (value.isPresent()) {
            return value.get();
        } else {
            throw new IllegalArgumentException(String.format("No instance found that match the required type '%s' in the constructor for injected class '%s'.", param.getClass().getName(), clazz.getName()));
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

        return String.format("%s: %n", c.getSimpleName()) + Arrays.stream(c.getDeclaredConstructors())
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
        else return Stream.of(missing.value()).noneMatch(missingType -> allInjectableTypes.stream().anyMatch(missingType::isAssignableFrom));
    }

    private static final Map<Class<?>, Function<String, Object>> PARSER_MAP;
    static {
        final Function<String, Object> characterMapper = s -> {
            if (s.length() == 1) {
                return s.charAt(0);
            } else {
                throw new IllegalArgumentException(
                    "Value '" + s + "' is to long to be " +
                        "parsed into a field of type char."
                );
            }
        };

        final Map<Class<?>, Function<String, Object>> map = new HashMap<>();
        map.put(boolean.class, Boolean::parseBoolean);
        map.put(Boolean.class, Boolean::parseBoolean);
        map.put(byte.class, Byte::parseByte);
        map.put(Byte.class, Byte::parseByte);
        map.put(short.class, Short::parseShort);
        map.put(Short.class, Short::parseShort);
        map.put(int.class, Integer::parseInt);
        map.put(Integer.class, Integer::parseInt);
        map.put(long.class, Long::parseLong);
        map.put(Long.class, Long::parseLong);
        map.put(float.class, Float::parseFloat);
        map.put(Float.class, Float::parseFloat);
        map.put(double.class, Double::parseDouble);
        map.put(Double.class, Double::parseDouble);
        map.put(String.class, s -> s);
        map.put(char.class, characterMapper);
        map.put(Character.class, characterMapper);
        map.put(File.class, File::new);
        map.put(URL.class, UrlUtil::tryCreateURL);
        PARSER_MAP = Collections.unmodifiableMap(map);
    }

    public static Optional<Object> parse(Type type, String serialized) {
        return Optional.ofNullable(PARSER_MAP.get(type))
            .map(m -> m.apply(serialized));
    }

}