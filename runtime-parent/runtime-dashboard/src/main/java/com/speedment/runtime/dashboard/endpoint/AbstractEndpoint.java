package com.speedment.runtime.dashboard.endpoint;

import com.speedment.common.injector.InjectorBuilder;
import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.common.restservice.Endpoint;
import com.speedment.common.restservice.RestComponent;

import static com.speedment.common.injector.State.RESOLVED;

/**
 * @author Emil Forslund
 * @since  3.1.1
 */
abstract class AbstractEndpoint implements Endpoint {

    /**
     * If the endpoint is added to an {@link InjectorBuilder}, then this method
     * will be invoked as part of the {@link State#RESOLVED} phase. It is
     * typically used to install the endpoint in the {@link RestComponent}.
     *
     * @param restComponent  the restComponent to install it in
     */
    @ExecuteBefore(RESOLVED)
    void init(@WithState(RESOLVED) RestComponent restComponent) {
        restComponent.install(this);
    }

}
