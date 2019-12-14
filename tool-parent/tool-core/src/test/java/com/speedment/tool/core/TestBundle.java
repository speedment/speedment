package com.speedment.tool.core;

import com.speedment.common.injector.InjectBundle;
import com.speedment.runtime.application.RuntimeBundle;
import com.speedment.tool.core.internal.component.UserInterfaceComponentImpl;

import java.util.stream.Stream;

/**
 * @author Emil Forslund
 * @since  3.2.5
 */
public class TestBundle implements InjectBundle {

    @Override
    public Stream<Class<?>> injectables() {
        return Stream.concat(
            new RuntimeBundle().injectables(),
            Stream.of(
                UserInterfaceComponentImpl.class
            )
        );
    }
}
