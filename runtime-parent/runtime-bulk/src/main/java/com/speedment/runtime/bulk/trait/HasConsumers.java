package com.speedment.runtime.bulk.trait;

import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> type
 */
public interface HasConsumers<ENTITY> {

    Stream<Consumer<? super ENTITY>> consumers();
}
