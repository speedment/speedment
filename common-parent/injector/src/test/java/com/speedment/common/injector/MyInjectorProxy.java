package com.speedment.common.injector;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class MyInjectorProxy implements InjectorProxy {
    @Override
    public boolean isApplicable(Class<?> clazz) {
        return false;
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
    public Object invoke(Method method, Object obj, Object... args) throws IllegalAccessException, InvocationTargetException {
        return method.invoke(obj, args);
    }
}
