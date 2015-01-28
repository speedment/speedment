package com.speedment.codegen.model;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <T> the CodeModel type
 */
public abstract class AbstractCodeModel<T extends AbstractCodeModel<T>> implements CodeModel {

    @SuppressWarnings("unchecked")
    protected <P> T add(final P parameter, final Consumer<P> consumer) {
        consumer.accept(parameter);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    protected <P> T addSeveral(final P[] restOfParameters, final Consumer<P> consumer) {
        Stream.of(restOfParameters).forEach(consumer::accept);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    protected <P1, P2> T add(final P1 firstParameter, final P2 secondParameter, final BiConsumer<P1, P2> biConsumer) {
        biConsumer.accept(firstParameter, secondParameter);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    protected <P> T set(final P item, final Consumer<P> consumer) {
        consumer.accept(item);
        return (T) this;
    }

}
