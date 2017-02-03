/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
import static com.speedment.common.injector.internal.util.ReflectionUtil.traverseMethods;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import static java.util.stream.Collectors.joining;
import java.util.stream.Stream;

/**
 * The default implementation of the {@link DependencyGraph} interface.
 * 
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class DependencyGraphImpl implements DependencyGraph {
    
    private final Map<Class<?>, DependencyNode> nodes;
    
    public static DependencyGraph create(Set<Class<?>> injectables) 
            throws CyclicReferenceException {
        
        final DependencyGraphImpl graph = new DependencyGraphImpl();
        injectables.forEach(graph::getOrCreate);
        graph.inject();
        return graph;
    }
    
    private DependencyGraphImpl() {
        nodes = new ConcurrentHashMap<>();
        nodes.put(InjectorImpl.class, new InjectorDependencyNode());
    }

    @Override
    public DependencyNode get(Class<?> clazz) throws IllegalArgumentException {
        for (final Class<?> impl : nodes.keySet()) {
            if (clazz.isAssignableFrom(impl)) {
                return nodes.get(impl);
            }
        }
        
        throw new IllegalArgumentException(
            "There is no implementation of '" + clazz + 
            "' in the injection dependency graph."
        );
    }

    @Override
    public DependencyNode getOrCreate(Class<?> clazz) {
        return nodes.computeIfAbsent(clazz, DependencyNodeImpl::new);
    }

    @Override
    public DependencyGraph inject() {
        nodes.forEach((clazz, node) -> {
            // Go through all the methods with the '@Execute'-annotation in the 
            // class and add them to the executors set.
            traverseMethods(clazz)
                .filter(m -> m.isAnnotationPresent(Execute.class))
                .forEach(m -> {
                    node.getExecutions().add(createExecution(m, State.STARTED));
                });

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
    
    private static String methodName(Method m) {
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
                
                if (ws != null) {
                    final Class<?> type = p.getType();
                    final State state   = ws.value();

                    try {
                        dependencies.add(
                            new DependencyImpl(get(type), state)
                        );
                    } catch (final CyclicReferenceException ex) {
                        throw new CyclicReferenceException(
                            m.getDeclaringClass(), ex
                        );
                    }
                }
            }
        } catch (final CyclicReferenceException ex) {
            throw new IllegalStateException(
                "Could not execute method " + methodName(m) + 
                " since one of its dependencies had not been injected.",
                ex
            );
        }

        return new ReflectionExecutionImpl<>(
            m.getDeclaringClass(), 
            executeBefore, dependencies, m
        );
    }
}