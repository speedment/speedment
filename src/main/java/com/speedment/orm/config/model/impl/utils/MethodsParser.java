package com.speedment.orm.config.model.impl.utils;

import com.speedment.orm.config.model.External;
import com.speedment.orm.config.model.impl.AbstractConfigEntity;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public class MethodsParser {

    public static final Predicate<Method> METHOD_IS_PUBLIC = (m) -> Modifier.isPublic(m.getModifiers());
    public static final Predicate<Method> METHOD_IS_GETTER = (m) -> m.getParameterCount() == 0 && (m.getName().startsWith("get") || m.getName().startsWith("is"));
    public static final Predicate<Method> METHOD_IS_EXTERNAL = MethodsParser::isExternal;
    
    public static Set<Method> getMethods(Class<?> clazz, Predicate<Method> filter) {
        return addMethods(new HashSet<>(), clazz, filter);
    }

    private static boolean isExternal(Method method) {
        return isExternal(method, method.getDeclaringClass());
    }

    private static boolean isExternal(final Method method, final Class<?> clazz) {
        if (method == null || clazz == null) {
            return false;
        }
        if (method.getAnnotation(External.class) != null) {
            return true;
        }
        // Also try the superClass and all the interfaces it implements
        final List<Class<?>> classCandidates = new ArrayList<>(Arrays.asList(clazz.getInterfaces()));
        final Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            classCandidates.add(superClass);
        }
        for (final Class<?> classCandidate : classCandidates) {
            try {
                if (isExternal(classCandidate.getMethod(method.getName(), method.getParameterTypes()), classCandidate)) {
                    return true;
                }
            } catch (NoSuchMethodException | SecurityException e) {
                // ignore
            }
        }
        return false;
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
