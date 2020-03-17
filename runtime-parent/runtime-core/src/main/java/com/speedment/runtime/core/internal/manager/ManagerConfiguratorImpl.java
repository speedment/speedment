/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.runtime.core.internal.manager;

import com.speedment.runtime.core.component.StreamSupplierComponent;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.core.manager.ManagerConfigurator;
import com.speedment.runtime.core.stream.parallel.ParallelStrategy;

import static com.speedment.runtime.core.stream.parallel.ParallelStrategy.computeIntensityDefault;
import static java.util.Objects.requireNonNull;

/**
 * Default implementation of {@link ManagerConfigurator} that allows a new
 * default {@link ParallelStrategy} to be set in the delegating {@link Manager}.
 *
 * @param <ENTITY> Entity type
 *
 * @author Per Minborg
 * @since  3.0.1
 */
public final class ManagerConfiguratorImpl<ENTITY>
implements ManagerConfigurator<ENTITY> {

    private final StreamSupplierComponent streams;
    private final Manager<ENTITY> manager;

    private ParallelStrategy strategy;

    public ManagerConfiguratorImpl(StreamSupplierComponent streams,
                                   Manager<ENTITY> manager) {

        this.streams  = requireNonNull(streams);
        this.manager  = requireNonNull(manager);
        this.strategy = computeIntensityDefault();
    }

    @Override
    public ManagerConfigurator<ENTITY>
    withParallelStrategy(ParallelStrategy parallelStrategy) {
        this.strategy = requireNonNull(parallelStrategy);
        return this;
    }

    @Override
    public Manager<ENTITY> build() {
        requireNonNull(strategy, getClass().getSimpleName() +
            ".withParallelStrategy(...) has not been called!"
        );

        return new ConfiguredManager<>(streams, manager, strategy);
    }
}
