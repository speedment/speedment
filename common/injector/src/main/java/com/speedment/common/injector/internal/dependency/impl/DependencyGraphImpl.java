/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.injector.internal.dependency.impl;

import com.speedment.common.injector.annotation.Execute;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.exception.CyclicReferenceException;
import com.speedment.common.injector.internal.dependency.Dependency;
import com.speedment.common.injector.internal.dependency.DependencyGraph;
import com.speedment.common.injector.internal.dependency.DependencyNode;
import com.speedment.common.injector.internal.dependency.Execution;
import static com.speedment.common.injector.internal.util.ReflectionUtil.traverseMethods;
import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.common.injector.internal.InjectorImpl;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import static java.util.stream.Collectors.joining;

/**
 * The default implementation of the {@link DependencyGraph} interface.
 * 
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class DependencyGraphImpl implements DependencyGraph {
    
    private final Map<Class<?>, DependencyNode> nodes;
    
    public static DependencyGraph create(Set<Class<?>> injectables) throws CyclicReferenceException {
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
            // Go through all the methods with the '@Execute'-annotation in the class and
            // add them to the executors set.
            traverseMethods(clazz)
                .filter(m -> m.isAnnotationPresent(Execute.class))
                .forEach(m -> {
                    node.getExecutions().add(createExecution(clazz, m, State.STARTED));
                });

            // Go through all the methods with the '@ExecuteBefore'-annotation in the class 
            // and add them to the executors set.
            traverseMethods(clazz)
                .filter(m -> m.isAnnotationPresent(ExecuteBefore.class))
                .forEach(m -> {
                    final ExecuteBefore execute = m.getAnnotation(ExecuteBefore.class);
                    node.getExecutions().add(createExecution(clazz, m, execute.value()));
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
    
    private Execution createExecution(Class<?> clazz, Method m, State executeBefore) {
     
//        // Make sure all the methods parameters are annoted with the 
//        // '@WithState'-annotation.
//        if (Stream.of(m.getParameters())
//            .anyMatch(p -> !p.isAnnotationPresent(WithState.class))) {
//            throw new RuntimeException(
//                "Method '" + methodName(m) + 
//                "' has a parameter that is missing the @" + 
//                WithState.class.getSimpleName() + " annotation."
//            );
//        }

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
                        throw new CyclicReferenceException(m.getDeclaringClass(), ex);
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

        return new ExecutionImpl(executeBefore, m, dependencies);
    }
}