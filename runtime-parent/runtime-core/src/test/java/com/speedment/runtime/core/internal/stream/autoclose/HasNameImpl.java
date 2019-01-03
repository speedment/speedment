package com.speedment.runtime.core.internal.stream.autoclose;

import static java.util.Objects.requireNonNull;

abstract class HasNameImpl implements HasName {
    private final String name;

    HasNameImpl(String name) {
        this.name = requireNonNull(name);
    }

    @Override
    public String name() {
        return name;
    }
}
