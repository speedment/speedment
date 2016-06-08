package com.speedment.common.injector.internal.dependency;

import com.speedment.common.injector.State;
import java.util.Set;

/**
 *
 * @author  Emil Forslund
 * @since   1.0.0
 */
public interface DependencyNode {
    
    Class<?> getRepresentedType();
    
    Set<Dependency> getDependencies();
    
    Set<Execution> getExecutions();
    
    State getCurrentState();
    
    void setState(State newState);
    
    boolean canBe(State state);
    
    boolean is(State state);
    
}