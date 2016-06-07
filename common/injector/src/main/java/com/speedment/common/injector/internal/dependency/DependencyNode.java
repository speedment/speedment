package com.speedment.common.injector.internal.dependency;

import com.speedment.common.injector.platform.State;
import java.util.Set;

/**
 *
 * @author  Emil Forslund
 * @since   1.0.0
 */
public interface DependencyNode {
    
    Class<?> getRepresentedType();
    
    Set<Dependency> getDependencies();
    
    State getCurrentState();
    
}