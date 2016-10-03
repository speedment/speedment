package com.speedment.runtime.core.manager;

import com.speedment.runtime.core.stream.parallel.ParallelStrategy;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> Entity type
 */
public interface ManagerConfigurator<ENTITY> {

    ManagerConfigurator<ENTITY> withParallelStrategy(ParallelStrategy parallelStrategy);

    Manager<ENTITY> build();

}
