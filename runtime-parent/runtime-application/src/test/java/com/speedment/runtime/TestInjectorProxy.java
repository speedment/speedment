/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
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
