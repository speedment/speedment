package com.speedment.common.injector.internal;

import java.util.Objects;
import java.util.function.Supplier;

class Injectable<T> {
    private final Class<T> cls;
    private final Supplier<T> supplier;

    Injectable(Class<T> cls, Supplier<T> supplier) {
        this.cls = cls;
        this.supplier = supplier;
    }

    public Class<T> get() {
        return cls;
    }

    public Supplier<T> supplier() {
        return supplier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Injectable<?> that = (Injectable<?>) o;
        return cls.equals(that.cls) &&
            Objects.equals(supplier, that.supplier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cls, supplier);
    }
}
