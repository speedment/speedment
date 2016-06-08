package com.speedment.common.injector.test_b;

import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.Inject;

/**
 *
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class C {
    
    public @Inject(State.INITIALIZED) A a;
    public @Inject(State.RESOLVED) B b;
    
}