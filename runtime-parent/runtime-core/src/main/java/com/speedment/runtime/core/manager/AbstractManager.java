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
package com.speedment.runtime.core.manager;

import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.runtime.core.component.ManagerComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.exception.SpeedmentException;

import java.util.stream.Stream;

import static com.speedment.common.injector.State.INITIALIZED;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.component.PersistenceComponent;
import com.speedment.runtime.core.component.StreamSupplierComponent;
import com.speedment.runtime.core.stream.parallel.ParallelStrategy;
import static java.util.Objects.requireNonNull;

/**
 *
 * @param <ENTITY> entity type
 *
 * @author Emil Forslund
 * @since 2.0.0
 */
public abstract class AbstractManager<ENTITY> implements Manager<ENTITY> {

    private @Inject StreamSupplierComponent streamSupplierComponent;

    // Hold these fields internally so that exposing methods may be compared by equality
    private final EntityCreator<ENTITY> entityCreator = this::entityCreate;
    private final EntityCopier<ENTITY> entityCopier = this::entityCopy;
    
    private Persister<ENTITY> persister;
    private Updater<ENTITY> updater;
    private Remover<ENTITY> remover;

    protected AbstractManager() {}

    @ExecuteBefore(INITIALIZED)
    final void createSupport(
        @WithState(INITIALIZED) PersistenceComponent persistenceComponent) {
        
        final TableIdentifier<ENTITY> tableId = getTableIdentifier();

        this.persister = persistenceComponent.persister(tableId);
        this.updater   = persistenceComponent.updater(tableId);
        this.remover   = persistenceComponent.remover(tableId);
    }

    @ExecuteBefore(INITIALIZED)
    final void install(
        @WithState(INITIALIZED) ManagerComponent managerComponent,
        @WithState(INITIALIZED) ProjectComponent projectComponent) {

        requireNonNull(projectComponent); // Must be initialized first.  // Not really now...!!
        managerComponent.put(this);
    }

    @Override
    public EntityCreator<ENTITY> entityCreator() {
        return entityCreator;
    }

    @Override
    public EntityCopier<ENTITY> entityCopier() {
        return entityCopier;
    }

    @Override
    public Stream<ENTITY> stream() {
        return streamSupplierComponent.stream(
            getTableIdentifier(), 
            ParallelStrategy.computeIntensityDefault()
        );
    }

    @Override
    public final ENTITY persist(ENTITY entity) throws SpeedmentException {
        return persister().apply(entity);
    }

    @Override
    public Persister<ENTITY> persister() {
        return persister;
    }

    @Override
    public final ENTITY update(ENTITY entity) throws SpeedmentException {
        return updater().apply(entity);
    }

    @Override
    public Updater<ENTITY> updater() {
        return updater;
    }

    @Override
    public final ENTITY remove(ENTITY entity) throws SpeedmentException {
        return remover().apply(entity);
    }

    @Override
    public Remover<ENTITY> remover() {
        return remover;
    }
}