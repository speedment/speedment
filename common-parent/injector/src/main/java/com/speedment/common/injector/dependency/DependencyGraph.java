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
package com.speedment.common.injector.dependency;

import com.speedment.common.injector.InjectorProxy;
import com.speedment.common.injector.exception.CyclicReferenceException;
import com.speedment.common.injector.internal.dependency.DependencyGraphImpl;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Represents the graph of dependencies in the injector.
 * 
 * @author  Emil Forslund
 * @since   1.2.0
 */
public interface DependencyGraph {
    
    /**
     * Returns the {@link DependencyNode} representing the specified type in the 
     * graph. This method might throw a {@link CyclicReferenceException} if the
     * class is in a cyclic dependency chain.
     * <p>
     * If the specified class does not exist in the graph, an 
     * {@code IllegalArgumentException} is thrown.
     * 
     * @param clazz  the class to look for in the graph
     * @return       the node found
     * 
     * @throws CyclicReferenceException  if the class is in a cyclic dependency
     * @throws IllegalArgumentException  the specified class is not in graph
     */
    DependencyNode get(Class<?> clazz);
    
    /**
     * Returns the {@link DependencyNode} representing the specified type in the 
     * graph if one exists, else creates it and returns it.
     * 
     * @param clazz  the class to look for in the graph
     * @return       the node found or created
     */
    DependencyNode getOrCreate(Class<?> clazz);

    /**
     * Returns the {@link DependencyNode} representing the specified type in the
     * graph if one exists, else returns an empty {@code Optional}.
     *
     * @param clazz  the class to look for in the graph
     * @return       the node found or empty
     */
    Optional<DependencyNode> getIfPresent(Class<?> clazz);
    
    /**
     * Inject all dependency injected fields in the graph, throwing an 
     * {@link CyclicReferenceException} if any cyclic references was detected.
     * 
     * @return  a reference to this graph
     * 
     * @throws CyclicReferenceException  if cyclic reference was detected
     */
    DependencyGraph inject();
    
    /**
     * Streams over all the nodes in the graph.
     * 
     * @return  stream of nodes
     */
    Stream<DependencyNode> nodes();


    /**
     * Creates and returns a new DependencyGraph.
     *
     * @param injectables to use
     * @param proxyFunction to use when locating an InjectorProxy for future invocations
     * @return a new DependencyGraph
     *
     * @throws CyclicReferenceException if there is a cyclic dependency
     */
    static DependencyGraph create(Stream<Class<?>> injectables, Function<Class<?>, InjectorProxy> proxyFunction) {
        final DependencyGraph graph = new DependencyGraphImpl(proxyFunction);
        injectables.forEachOrdered(graph::getOrCreate);
        graph.inject();
        return graph;
    }
    
}