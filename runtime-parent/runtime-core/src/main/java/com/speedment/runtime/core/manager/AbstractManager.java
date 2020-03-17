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
package com.speedment.runtime.core.manager;

import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.runtime.core.component.ManagerComponent;
import com.speedment.runtime.core.component.PersistenceComponent;
import com.speedment.runtime.core.component.StreamSupplierComponent;
import com.speedment.runtime.core.stream.parallel.ParallelStrategy;

import java.util.stream.Stream;

import static com.speedment.common.injector.State.*;
import static java.util.Objects.requireNonNull;

/**
 * An abstract base implementation of all {@link Manager Managers}. 
 * <p>
 * This default implementation uses the plugged in 
 * {@link StreamSupplierComponent} and {@link PersistenceComponent} to stream 
 * over entities and make changes to them in the data store. It also installs
 * itself in the plugged in {@link ManagerComponent}.
 * 
 * @param <ENTITY> entity type
 *
 * @author Emil Forslund
 * @since  2.0.0
 * 
 * @see StreamSupplierComponent
 * @see PersistenceComponent
 * @see ManagerComponent
 */
public abstract class AbstractManager<ENTITY> implements Manager<ENTITY> {

    private StreamSupplierComponent streamSupplierComponent;
    private PersistenceProvider<ENTITY> persistenceProvider;

    @ExecuteBefore(State.INITIALIZED)
    public void setInjector(StreamSupplierComponent streamSupplierComponent) {
        this.streamSupplierComponent = requireNonNull(streamSupplierComponent);
    }


    protected AbstractManager() {}

    /**
     * In the {@link State#INITIALIZED}-phase, install this {@link Manager} in 
     * the {@link ManagerComponent} so that it can be found by other parts of
     * the system.
     * <p>
     * THIS METHOD IS INTENDED TO BE INVOKED AUTOMATICALLY BY THE DEPENDENCY
     * INJECTOR. IT SHOULD THEREFORE NEVER BE CALLED DIRECTLY!
     * 
     * @param managerComponent  auto-injected managerComponent
     */
    @ExecuteBefore(INITIALIZED)
    public final void install(@WithState(INITIALIZED) ManagerComponent managerComponent) {
        managerComponent.put(this);
    }

    @ExecuteBefore(STARTED)
    public final void resolve(@WithState(RESOLVED) PersistenceComponent persistenceComponent) {
        persistenceProvider = persistenceComponent.persistenceProvider(this);
    }

    @Override
    public Stream<ENTITY> stream() {
        return streamSupplierComponent.stream(
            getTableIdentifier(), 
            ParallelStrategy.computeIntensityDefault()
        );
    }

    @Override
    public Persister<ENTITY> persister() {
        return persistenceProvider.persister();
    }

    @Override
    public Persister<ENTITY> persister(HasLabelSet<ENTITY> fields) {
        return persistenceProvider.persister(fields);
    }

    @Override
    public Updater<ENTITY> updater() {
        return persistenceProvider.updater();
    }

    @Override
    public Updater<ENTITY> updater(HasLabelSet<ENTITY> fields) {
        return persistenceProvider.updater(fields);
    }

    @Override
    public Remover<ENTITY> remover() {
        return persistenceProvider.remover();
    }
}