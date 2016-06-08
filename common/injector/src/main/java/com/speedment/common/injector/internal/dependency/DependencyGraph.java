package com.speedment.common.injector.internal.dependency;

import com.speedment.common.injector.exception.CyclicReferenceException;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public interface DependencyGraph {
    
    DependencyNode getOrCreate(Class<?> clazz) throws CyclicReferenceException;
    
    Stream<DependencyNode> nodes();
    
}