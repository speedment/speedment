package com.speedment.common.injector.internal.dependency;

import com.speedment.common.injector.exception.CyclicReferenceException;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public interface DependencyGraph {
    
    DependencyNode get(Class<?> clazz) throws CyclicReferenceException;
    
    DependencyNode getOrCreate(Class<?> clazz);
    
    DependencyGraph inject() throws CyclicReferenceException;
    
    Stream<DependencyNode> nodes();
    
}