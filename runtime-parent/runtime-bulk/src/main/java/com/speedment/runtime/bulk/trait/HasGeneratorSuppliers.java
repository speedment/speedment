package com.speedment.runtime.bulk.trait;

import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> type
 */
public interface HasGeneratorSuppliers<ENTITY> {

    Stream<Supplier<Stream<? extends ENTITY>>> generatorSuppliers();
}
