package com.speedment.common.injector.test_b;

import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.Inject;

/**
 *
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class A {
    
    public @Inject(State.INITIALIZED) B b;
    public @Inject(State.RESOLVED) C c;
    
}
