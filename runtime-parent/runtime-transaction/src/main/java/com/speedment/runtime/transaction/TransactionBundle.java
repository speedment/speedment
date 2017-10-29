package com.speedment.runtime.transaction;

import com.speedment.common.injector.InjectBundle;
import com.speedment.runtime.transaction.internal.TransactionComponentImpl;
import java.util.stream.Stream;

/**
 * The {@link InjectBundle} for the "transaction"-module.
 *
 * @author Per Minborg
 * @since 3.0.17
 */
public class TransactionBundle implements InjectBundle {

    @Override
    public Stream<Class<?>> injectables() {
        return Stream.of(TransactionComponentImpl.class);
    }
}
