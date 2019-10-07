package com.speedment.common.injector;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * An InjectorProxy can be used to create/manipulate
 * instances on behalf of the actual injector. This can
 * be useful when using the Java Platform Module System
 * to keep down the number of required {@code exports}
 * in the module-info.java file.
 *
 * @author per
 * @since 3.2.0
 */
public interface InjectorProxy {

    /**
     * Returns if this InjectorProxy shall be used
     * with regards to the provided {@code clazz}.
     * <p>
     * This feature can be used to control which proxies
     * shall be used for which classes. E.g. one
     * InjectorProxy can be used to instantiate classes
     * from the domain system.
     *
     * @param clazz to check
     * @return if this InjectorProxy shall be used
     *         with regards to the provided {@code clazz}
     */
    boolean isApplicable(Class<?> clazz);

    /**
     * Sets the provided {@code field} in the provided
     * {@code instance} to the provided {@code value}.
     *
     * @param field to set
     * @param instance the instance whose {@code field} should be modified
     * @param value the new value for the {@code field} of {@code instance}
     *
     * @throws IllegalAccessException    if this {@code Field} object
     *              is enforcing Java language access control and the underlying
     *              field is either inaccessible or final.
     * @throws IllegalArgumentException  if the specified object is not an
     *              instance of the class or interface declaring the underlying
     *              field (or a subclass or implementor thereof),
     *              or if an unwrapping conversion fails.
     * @throws NullPointerException      if the specified object is null
     *              and the field is an instance field.
     * @throws ExceptionInInitializerError if the initialization provoked
     *              by this method fails.
     * @throws SecurityException if the request is denied by the security manager
     *
     * throws InaccessibleObjectException if access cannot be enabled
     */
    void set(Field field, Object instance, Object value) throws IllegalArgumentException, IllegalAccessException;


    /**
     * Sets the provided {@code constructor} as accessable.
     *
     * @param constructor to set accessable
     *
     * @throws SecurityException if the request is denied by the security manager
     *
     * throws InaccessibleObjectException if access cannot be enabled
     */
    void setAccessable(Constructor<?> constructor);

}
