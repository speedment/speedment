package com.speedment.common.restservice;

import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.InjectKey;

/**
 * Other components can install REST endpoints in this component to make them
 * accessible as a web service, without knowing anything about which framework
 * that is used. It could be for an example Spring, JavaEE or NanoHTTPD.
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
@InjectKey(RestComponent.class)
public interface RestComponent {

    /**
     * Installs a new endpoint in the {@code RestComponent}. This method should
     * only ever be invoked in the {@link State#RESOLVED} state.
     *
     * @param endpoint  the endpoint to install
     */
    void install(Endpoint endpoint);

}
