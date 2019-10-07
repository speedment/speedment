package com.speedment.common.injector.provider;

import com.speedment.common.injector.InjectorProxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Predicate;

public final class StandardInjectorProxy implements InjectorProxy {

    private final Predicate<? super Class<?>> isApplicable;

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
    public void set(Field field, Object instance, Object value) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(instance, value);
    }

    @Override
    public <T> T newInstance(Constructor<T> constructor, Object... initargs) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        return constructor.newInstance(initargs);
    }
}
