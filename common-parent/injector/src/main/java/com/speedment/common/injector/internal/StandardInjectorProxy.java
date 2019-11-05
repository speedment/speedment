package com.speedment.common.injector.internal;

import com.speedment.common.injector.InjectorProxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

final class StandardInjectorProxy implements InjectorProxy {

    @Override
    public boolean isApplicable(Class<?> clazz) {
        return true;
    }

    @Override
    public void set(Field field, Object instance, Object value) throws IllegalAccessException {
        field.set(instance, value);
    }

    @Override
    public <T> T newInstance(Constructor<T> constructor, Object... args) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        return constructor.newInstance(args);
    }

    @Override
    public Object invoke(Method m, Object obj, Object... args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return m.invoke(obj, args);
    }
}
