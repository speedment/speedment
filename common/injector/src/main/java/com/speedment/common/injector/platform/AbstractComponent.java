package com.speedment.common.injector.platform;

import com.speedment.common.injector.annotation.Injectable;
import com.speedment.common.injector.annotation.RequireState;
import com.speedment.common.injector.annotation.ResultingState;

/**
 * An optional abstract base class that required the instance to 
 * execute a specific method for every state it goes through. This
 * is useful for larger software components to make sure it is
 * properly initialized.
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
@Injectable
public abstract class AbstractComponent {
    
    @RequireState(State.CREATED)
    @ResultingState(State.INIITIALIZED)
    protected abstract void onInitialize();
    
    @RequireState(State.INIITIALIZED)
    @ResultingState(State.RESOLVED)
    protected abstract void onResolve();
    
    @RequireState(State.RESOLVED)
    @ResultingState(State.STARTED)
    protected abstract void onStart();
    
    @RequireState(State.STARTED)
    @ResultingState(State.STOPPED)
    protected abstract void onStop();
    
}