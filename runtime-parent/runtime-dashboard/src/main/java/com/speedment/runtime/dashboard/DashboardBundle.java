package com.speedment.runtime.dashboard;

import com.speedment.common.injector.InjectBundle;
import com.speedment.runtime.dashboard.endpoint.InfoEndpoint;
import com.speedment.runtime.dashboard.endpoint.LoggerEndpoint;

import java.util.stream.Stream;

/**
 * @author Emil Forslund
 * @since  1.0.0
 */
public class DashboardBundle implements InjectBundle {

    @Override
    public Stream<Class<?>> injectables() {
        return Stream.of(
            InfoEndpoint.class,
            LoggerEndpoint.class
        );
    }
}
