package com.speedment.common.injector.provider;

import com.speedment.common.injector.InjectorProxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Predicate;

public final class StandardInjectorProxy implements InjectorProxy {

    private final Predicate<? super Class<?>> isApplicable;

    /**
     * Creates a StandardInjectorProxy that is applicable for all
     * classes that starts with the same package name as the provided
     * {@code fromPackageClass}.
     *
     * @param fromPackageClass to use as package name provider
     */
    public StandardInjectorProxy(Class<?> fromPackageClass) {
        this(c -> c.getName().startsWith(fromPackageClass.getPackage().getName()));
    }

    /**
     * Creates a StandardInjectorProxy that is applicable for the
     * given {@code isApplicable} Predicate.
     *
     * @param isApplicable Predicate to use
     */
    public StandardInjectorProxy(Predicate<? super Class<?>> isApplicable) {
        this.isApplicable = isApplicable;
    }

    @Override
    public boolean isApplicable(Class<?> clazz) {
        return isApplicable.test(clazz);
    }

    @Override
    public void set(Field field, Object instance, Object value) throws IllegalArgumentException, IllegalAccessException {
        field.setAccessible(true);
        field.set(instance, value);
    }

/*
    @Override
    public void setAccessable(Constructor<?> constructor) {
        constructor.setAccessible(true);
    }
*/

    @Override
    public <T> T newInstance(Constructor<T> constructor, Object... initargs) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return constructor.newInstance(initargs);
    }
}
