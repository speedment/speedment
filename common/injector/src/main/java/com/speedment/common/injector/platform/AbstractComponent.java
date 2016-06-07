package com.speedment.common.injector.platform;

import com.speedment.common.injector.annotation.Injectable;
import com.speedment.common.injector.annotation.StateAfter;
import com.speedment.common.injector.annotation.StateBefore;

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
    
    @StateBefore(State.CREATED)
    @StateAfter(State.INIITIALIZED)
    protected abstract void onInitialize();
    
    @StateBefore(State.INIITIALIZED)
    @StateAfter(State.RESOLVED)
    protected abstract void onResolve();
    
    @StateBefore(State.RESOLVED)
    @StateAfter(State.STARTED)
    protected abstract void onStart();
    
    @StateBefore(State.STARTED)
    @StateAfter(State.STOPPED)
    protected abstract void onStop();
    
}