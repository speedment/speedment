package com.speedment.tool.core;

import com.speedment.common.injector.Injector;

import static java.util.Objects.requireNonNull;

public final class  InjectorHolder {

    public static final InjectorHolder INSTANCE = new InjectorHolder();

    private Injector injector;

    public Injector getInjector() {
        return injector;
    }

    public void setInjector(Injector injector) {
        this.injector = requireNonNull(injector);
    }
}
