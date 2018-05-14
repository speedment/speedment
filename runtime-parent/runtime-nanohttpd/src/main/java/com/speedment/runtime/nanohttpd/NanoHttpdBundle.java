package com.speedment.runtime.nanohttpd;

import com.speedment.common.injector.InjectBundle;
import com.speedment.runtime.nanohttpd.internal.NanoHttpdRestComponent;

import java.util.stream.Stream;

/**
 * @author Emil Forslund
 * @since  3.1.1
 */
public class NanoHttpdBundle implements InjectBundle {
    @Override
    public Stream<Class<?>> injectables() {
        return Stream.of(NanoHttpdRestComponent.class);
    }
}
