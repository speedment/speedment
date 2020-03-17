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

import static java.util.Objects.requireNonNull;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.component.StreamSupplierComponent;
import com.speedment.runtime.core.manager.HasLabelSet;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.core.manager.Persister;
import com.speedment.runtime.core.manager.Remover;
import com.speedment.runtime.core.manager.Updater;
import com.speedment.runtime.core.stream.parallel.ParallelStrategy;
import com.speedment.runtime.field.Field;

import java.util.stream.Stream;

/**
 * {@link Manager} delegator that overwrites the {@link ParallelStrategy} used
 * when {@link #stream()} is called.
 *
 * @param <ENTITY> entity type
 *
 * @author Per Minborg
 * @since  3.0.1
 */
public final class ConfiguredManager<ENTITY> implements Manager<ENTITY> {

    private final StreamSupplierComponent streamSupplierComponent;
    private final Manager<ENTITY> manager;
    private final ParallelStrategy parallelStrategy;

    ConfiguredManager(StreamSupplierComponent streamSupplierComponent,
                      Manager<ENTITY> manager,
                      ParallelStrategy parallelStrategy) {

        this.streamSupplierComponent = requireNonNull(streamSupplierComponent);
        this.manager                 = requireNonNull(manager);
        this.parallelStrategy        = requireNonNull(parallelStrategy);
    }

    @Override
    public TableIdentifier<ENTITY> getTableIdentifier() {
        return manager.getTableIdentifier();
    }

    @Override
    public Class<ENTITY> getEntityClass() {
        return manager.getEntityClass();
    }

    @Override
    public Stream<Field<ENTITY>> fields() {
        return manager.fields();
    }

    @Override
    public Stream<Field<ENTITY>> primaryKeyFields() {
        return manager.primaryKeyFields();
    }

    @Override
    public Stream<ENTITY> stream() {
        return streamSupplierComponent.stream(
            getTableIdentifier(),
            parallelStrategy
        );
    }

    @Override
    public ENTITY create() {
        return manager.create();
    }

    @Override
    public Persister<ENTITY> persister() {
        return manager.persister();
    }

    @Override
    public Persister<ENTITY> persister(HasLabelSet<ENTITY> fields) {
        return manager.persister(fields);
    }

    @Override
    public Updater<ENTITY> updater() {
        return manager.updater();
    }

    @Override
    public Updater<ENTITY> updater(HasLabelSet<ENTITY> fields) {
        return manager.updater(fields);
    }

    @Override
    public Remover<ENTITY> remover() {
        return manager.remover();
    }

    @Override
    public String toString() {
        return "ConfiguredManager{" +
            "manager=" + manager +
            ", parallelStrategy=" + parallelStrategy +
            '}';
    }
}
