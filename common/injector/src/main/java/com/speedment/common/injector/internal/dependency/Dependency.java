package com.speedment.common.injector.internal.dependency;

import com.speedment.common.injector.State;

/**
 *
 * @author Emil Forslund
 */
public interface Dependency {
    
    DependencyNode getNode();
    
    State getRequiredState();
}