package com.speedment.common.injector.internal.dependency;

import com.speedment.common.injector.platform.State;

/**
 *
 * @author Emil Forslund
 */
public interface Dependency {
    
    DependencyNode getDependingOn();
    
    State getDependedState();
}