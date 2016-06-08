package com.speedment.common.injector.internal.dependency;

import com.speedment.common.injector.State;
import java.lang.reflect.Method;
import java.util.Set;

/**
 *
 * @author  Emil Forslund
 * @since   1.0.0
 */
public interface Execution {
    
    State getState();
    
    Method getMethod();
    
    Set<Dependency> getDependencies();
    
}