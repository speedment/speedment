package com.speedment.runtime;

import com.speedment.common.injector.InjectorProxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class TestInjectorProxy implements InjectorProxy {

    @Override
    public boolean isApplicable(Class<?> clazz) {
        return InjectorProxy.samePackageOrBelow(TestInjectorProxy.class).test(clazz);
    }

    @Override
    public void set(Field field, Object instance, Object value) throws IllegalAccessException {
        field.set(instance, value);
    }

    @Override
    public <T> T newInstance(Constructor<T> constructor, Object... initargs) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        return constructor.newInstance(initargs);
    }

    @Override
    public Object invoke(Method method, Object obj, Object... args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return method.invoke(obj, args);
    }
}
