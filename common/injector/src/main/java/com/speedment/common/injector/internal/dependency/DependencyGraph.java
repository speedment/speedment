package com.speedment.common.injector.internal.dependency;

import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public interface DependencyGraph {
    
    DependencyNode getOrCreate(Class<?> clazz);
    
    Stream<DependencyNode> nodes();
    
}