package com.speedment.runtime.join.internal;

import com.speedment.runtime.join.Join;
import static java.util.Objects.requireNonNull;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
public class JoinImpl<T> implements Join<T> {

    private final Supplier<Stream<T>> streamSupplier;

    public JoinImpl(Supplier<Stream<T>> streamSupplier) {
        this.streamSupplier = requireNonNull(streamSupplier);
    }

    @Override
    public Stream<T> stream() {
        return streamSupplier.get();
    }

}
