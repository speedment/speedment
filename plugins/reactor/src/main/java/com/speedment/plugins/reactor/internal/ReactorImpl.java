package com.speedment.plugins.reactor.internal;

import com.speedment.plugins.reactor.Reactor;
import static java.util.Objects.requireNonNull;
import java.util.Timer;

/**
 * The default implementation of the {@link Reactor} interface.
 * 
 * @author  Emil Forslund
 * @since   1.1.0
 */
public final class ReactorImpl implements Reactor {
    
    private final Timer timer;
    
    /**
     * Constructs a new Reactor. This should only be called as part of the
     * builder pattern for this class and never directly.
     * 
     * @param timer  the timer that is polling the database
     */
    ReactorImpl(Timer timer) {
        this.timer = requireNonNull(timer);
    }
    
    /**
     * Stops the reactor from polling the database.
     */
    @Override
    public void stop() {
        timer.cancel();
    }
}
