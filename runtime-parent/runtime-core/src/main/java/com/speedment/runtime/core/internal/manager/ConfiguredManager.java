/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.component.StreamSupplierComponent;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.field.Field;
import com.speedment.runtime.core.manager.EntityCopier;
import com.speedment.runtime.core.manager.EntityCreator;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.core.manager.Persister;
import com.speedment.runtime.core.manager.Remover;
import com.speedment.runtime.core.manager.Updater;
import com.speedment.runtime.core.stream.parallel.ParallelStrategy;
import static java.util.Objects.requireNonNull;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> Entity type
 * 
 * @since 3.0.1
 */
public class ConfiguredManager<ENTITY> implements Manager<ENTITY> {

    private final StreamSupplierComponent streamSupplierComponent;
    private final Manager<ENTITY> manager;
    private final ParallelStrategy parallelStrategy;

    public ConfiguredManager(StreamSupplierComponent streamSupplierComponent, Manager<ENTITY> manager, ParallelStrategy parallelStrategy) {
        this.streamSupplierComponent = requireNonNull(streamSupplierComponent);
        this.manager = requireNonNull(manager);
        this.parallelStrategy = requireNonNull(parallelStrategy);
    }

    @Override
    public ENTITY entityCreate() {
        return manager.entityCreate();
    }

    @Override
    public EntityCreator<ENTITY> entityCreator() {
        return manager.entityCreator();
    }

    @Override
    public ENTITY entityCopy(ENTITY source) {
        return manager.entityCopy(source);
    }

    @Override
    public EntityCopier<ENTITY> entityCopier() {
        return manager.entityCopier();
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
    public ENTITY persist(ENTITY entity) throws SpeedmentException {
        return manager.persist(entity);
    }

    @Override
    public Persister<ENTITY> persister() {
        return manager.persister();
    }

    @Override
    public ENTITY update(ENTITY entity) throws SpeedmentException {
        return manager.update(entity);
    }

    @Override
    public Updater<ENTITY> updater() {
        return manager.updater();
    }

    @Override
    public ENTITY remove(ENTITY entity) throws SpeedmentException {
        return manager.remove(entity);
    }

    @Override
    public Remover<ENTITY> remover() {
        return manager.remover();
    }

}
