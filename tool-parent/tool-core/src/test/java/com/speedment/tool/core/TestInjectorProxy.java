package com.speedment.tool.core;

import com.speedment.common.injector.InjectorProxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public final class TestInjectorProxy implements InjectorProxy {

    @Override
    public boolean isApplicable(Class<?> clazz) {
        return InjectorProxy.samePackageOrBelow(TestInjectorProxy.class).test(clazz);
    }

    @Override
    public void set(Field field, Object instance, Object value) throws IllegalArgumentException, IllegalAccessException {
        field.setAccessible(true);
        field.set(instance, value);
    }

    @Override
    public <T> T newInstance(Constructor<T> constructor, Object... initargs) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return constructor.newInstance(initargs);
    }
}
