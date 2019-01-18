package com.speedment.runtime.config.util;

import java.lang.reflect.Array;

import static java.util.Objects.requireNonNull;

/**
 * Utility class used to find the correct {@link Class} given a {@link String},
 * or to convert a class to the corresponding {@code String}.
 *
 * @author Emil Forslund
 * @since  3.1.10
 */
public final class ClassUtil {

    /**
     * Returns the full name of the specified type (including the array brackets
     * if it is an array class).
     *
     * @param  clazz  class
     * @return the absolute class name
     */
    public static String classToString(Class<?> clazz) {
        requireNonNull(clazz, "Class is null.");
        if (clazz.isArray()) {
            final Class<?> original = clazz.getComponentType();
            return classToString(original) + "[]";
        } else {
            return clazz.getName();
        }
    }

    /**
     * Attempts to find the specified class using the default class loader. The
     * class name may contain square brackets to indicate an array class. If the
     * class can't be found, an {@link ClassNotFoundException} is thrown.
     *
     * @param className    the full class name
     * @return             the class with that name
     *
     * @throws ClassNotFoundException  if the class name could not be found
     */
    public static Class<?> classFromString(String className) throws ClassNotFoundException {
        return classFromString(className, null);
    }

    /**
     * Attempts to find the specified class using the specified
     * {@code classLoader}. If the class loader is {@code null}, then the
     * default class loader is used. The class name may contain square brackets
     * to indicate an array class. If the class can't be found, an
     * {@link ClassNotFoundException} is thrown.
     *
     * @param className    the full class name
     * @param classLoader  the class loader to use (or {@code null})
     * @return             the class with that name
     *
     * @throws ClassNotFoundException  if the class name could not be found
     */
    public static Class<?> classFromString(String className, ClassLoader classLoader) throws ClassNotFoundException {
        String inner = className;
        int dimensions = 0;
        while (inner.endsWith("[]")) {
            inner = inner.substring(0, inner.length() - 2);
            dimensions++;
        }

        final Class<?> innerClass;
        switch (inner) {
            case "byte":    innerClass = byte.class; break;
            case "short":   innerClass = short.class; break;
            case "int":     innerClass = int.class; break;
            case "long":    innerClass = long.class; break;
            case "float":   innerClass = float.class; break;
            case "double":  innerClass = double.class; break;
            case "boolean": innerClass = boolean.class; break;
            case "char":    innerClass = char.class; break;
            default: if (classLoader == null) {
                innerClass = Class.forName(inner);
            } else {
                innerClass = classLoader.loadClass(inner);
            }
        }


        if (dimensions == 0) {
            return innerClass;
        } else {
            return Array.newInstance(innerClass, dimensions).getClass();
        }
    }

    /**
     * Utility classes should not be instantiated.
     */
    private ClassUtil() {}
}
