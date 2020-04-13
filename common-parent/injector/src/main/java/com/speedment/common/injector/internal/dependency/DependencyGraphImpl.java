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
package com.speedment.common.injector.internal.dependency;

import com.speedment.common.injector.InjectorProxy;
import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.Execute;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.common.injector.dependency.Dependency;
import com.speedment.common.injector.dependency.DependencyGraph;
import com.speedment.common.injector.dependency.DependencyNode;
import com.speedment.common.injector.exception.CyclicReferenceException;
import com.speedment.common.injector.execution.Execution;
import com.speedment.common.injector.internal.InjectorImpl;
import com.speedment.common.injector.internal.execution.ReflectionExecutionImpl;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.speedment.common.injector.internal.util.ReflectionUtil.traverseMethods;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 * The default implementation of the {@link DependencyGraph} interface.
 * 
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class DependencyGraphImpl implements DependencyGraph {
    
    private final Map<Class<?>, DependencyNode> nodes;
    private final Function<Class<?>, InjectorProxy> proxyFunction;

    public DependencyGraphImpl(Function<Class<?>, InjectorProxy> proxyFunction) {
        this.proxyFunction = requireNonNull(proxyFunction);
        nodes = new ConcurrentHashMap<>();
        nodes.put(InjectorImpl.class, new InjectorDependencyNode());
    }

    @Override
    public DependencyNode get(Class<?> clazz) {
        return getIfPresent(clazz).orElseThrow(() -> new IllegalArgumentException(
            format("There is no implementation of '%s' in the injection dependency graph.", clazz)
        ));
    }

    @Override
    public DependencyNode getOrCreate(Class<?> clazz) {
        return getIfPresent(clazz)
            .orElseGet(() -> nodes.computeIfAbsent(clazz, DependencyNodeImpl::new));
    }

    @Override
    public Optional<DependencyNode> getIfPresent(Class<?> clazz) {
        for (final Map.Entry<Class<?>, DependencyNode>impl : nodes.entrySet()) {
            if (clazz.isAssignableFrom(impl.getKey())) {
                return Optional.of(impl.getValue());
            }
        }

        return Optional.empty();
    }

    @Override
    public DependencyGraph inject() {
        nodes.forEach((clazz, node) -> {
            // Go through all the methods with the '@Execute'-annotation in the 
            // class and add them to the executors set.
            traverseMethods(clazz)
                .filter(m -> m.isAnnotationPresent(Execute.class))
                .forEach(m -> node.getExecutions().add(createExecution(m, State.STARTED)));

            // Go through all the methods with the '@ExecuteBefore'-annotation 
            // in the class  and add them to the executors set.
            traverseMethods(clazz)
                .filter(m -> m.isAnnotationPresent(ExecuteBefore.class))
                .forEach(m -> {
                    final ExecuteBefore execute =
                        m.getAnnotation(ExecuteBefore.class);
                    
                    node.getExecutions()
                        .add(createExecution(m, execute.value()));
                });
        });
        
        return this;
    }

    @Override
    public Stream<DependencyNode> nodes() {
        return nodes.values().stream();
    }
    
    static String methodName(Method m) {
        return m.getDeclaringClass().getName() + "#" + 
            m.getName() + "(" + 
            Stream.of(m.getParameterTypes())
                .map(Class::getSimpleName)
                .collect(joining(", ")) + 
            ")";
    }
    
    private Execution<?> createExecution(Method m, State executeBefore) {
        final Set<Dependency> dependencies = new HashSet<>();
        try {
            for (int i = 0; i < m.getParameterCount(); i++) {
                final Parameter p   = m.getParameters()[i];
                final WithState ws  = p.getAnnotation(WithState.class);

                // TODO: Maybe create a dependency even if WithState is missing,

                if (ws != null) {
                    addDependency(m, dependencies, p.getType(), ws.value());
                }
            }
        } catch (final CyclicReferenceException ex) {
            throw new IllegalStateException("Could not execute method " + methodName(m) + " since one of its dependencies had not been injected.", ex);
        }

        return new ReflectionExecutionImpl<>(
            m.getDeclaringClass(),
            executeBefore,
            dependencies,
            m,
            proxyFunction.apply(m.getDeclaringClass())
        );
    }

    private void addDependency(Method m, Set<Dependency> dependencies, Class<?> type, State state) {
        try {
            dependencies.add(
                new DependencyImpl(
                    getOrCreate(type),
                    state
                )
            );
        } catch (final CyclicReferenceException ex) {
            throw new CyclicReferenceException(m.getDeclaringClass(), ex);
        } catch (final IllegalArgumentException iae) {
            throw new IllegalStateException("Unable to resolve " + m.toString() + " (" + type.toString() + ") at state " + state.toString(), iae);
        }
    }

}