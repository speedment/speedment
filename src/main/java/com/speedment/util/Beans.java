package com.speedment.util;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public class Beans {

    private Beans() {
    }

    public static <P, T> T with(final T thizz, final P item, final Consumer<P> consumer) {
        consumer.accept(item);
        return thizz;
    }

    public static <P, T> T withSeveral(final T thizz, final P[] restOfParameters, final Consumer<P> consumer) {
        Stream.of(restOfParameters).forEach(consumer::accept);
        return thizz;
    }

    public static <P1, P2, T> T with(final T thizz, final P1 firstParameter, final P2 secondParameter, final BiConsumer<P1, P2> biConsumer) {
        biConsumer.accept(firstParameter, secondParameter);
        return thizz;
    }

}
