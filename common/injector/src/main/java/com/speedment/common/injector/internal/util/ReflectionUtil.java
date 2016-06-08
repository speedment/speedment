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
