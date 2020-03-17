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
package com.speedment.common.injector.internal.execution;

import com.speedment.common.injector.InjectorProxy;
import com.speedment.common.injector.State;
import com.speedment.common.injector.dependency.Dependency;
import com.speedment.common.injector.execution.Execution;
import com.speedment.common.injector.internal.util.ReflectionUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import static com.speedment.common.injector.MissingArgumentStrategy.SKIP_INVOCATION;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 * An implementation of {@link Execution} that uses reflection to invoke a
 * method.
 *
 * @param <T> the component to execute on
 * @author Emil Forslund
 * @since 1.2.0
 */
public final class ReflectionExecutionImpl<T> extends AbstractExecution<T> {

    private final InjectorProxy injectorProxy;
    private final Method method;

    public ReflectionExecutionImpl(
        final Class<T> component,
        final State state,
        final Set<Dependency> dependencies,
        final Method method,
        final InjectorProxy injectorProxy
    ) {
        super(component, state, dependencies, ReflectionUtil.missingArgumentStrategy(method));
        this.method = requireNonNull(method);
        this.injectorProxy = requireNonNull(injectorProxy);
    }

    @Override
    public String getName() {
        return method.toString();
    }

    public Method getMethod() {
        return method;
    }

    @Override
    public boolean invoke(T component, ClassMapper classMapper)
        throws IllegalAccessException,
        InvocationTargetException {

        if (method.getParameterCount() > 0
            && getMissingArgumentStrategy() == SKIP_INVOCATION
            && Stream.of(method.getParameters())
            .map(Parameter::getType)
            .map(classMapper::applyOrNull)
            .anyMatch(Objects::isNull)) {
            // Do not invoke if all arguments are optional
            // and the arguments are all null

            return false;
        }

        final Object[] args = Stream.of(method.getParameters())
            .map(Parameter::getType)
            .map(classMapper::apply)
            .toArray();

        injectorProxy.invoke(method, component, args);

        return true;
    }

    @Override
    public String toString() {
        return getType().getSimpleName() + "#"
            + method.getName() + "("
            + Stream.of(method.getParameters())
            .map(p -> p.getType().getSimpleName().substring(0, 1))
            .collect(joining(", ")) + ")";
    }
}