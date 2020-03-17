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
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.component.StreamSupplierComponent;
import com.speedment.runtime.core.stream.parallel.ParallelStrategy;

import java.util.stream.Stream;

import static com.speedment.common.injector.State.INITIALIZED;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * Abstract base class for VIEW Tables.
 *
 * @param <ENTITY>  the entity type
 *
 * @author Emil Forslund
 * @since  3.0.10
 *
 * @see AbstractManager
 */
public abstract class AbstractViewManager<ENTITY> implements Manager<ENTITY> {

    private StreamSupplierComponent streamSupplierComponent;

    protected AbstractViewManager() {}

    @ExecuteBefore(State.INITIALIZED)
    public final void setStreamSupplierComponent(StreamSupplierComponent streamSupplierComponent) {
        this.streamSupplierComponent = requireNonNull(streamSupplierComponent);
    }

    /**
     * In the {@link State#INITIALIZED}-phase, install this {@link Manager} in
     * the {@link ManagerComponent} so that it can be found by other parts of
     * the system.
     * <p>
     * THIS METHOD IS INTENDED TO BE INVOKED AUTOMATICALLY BY THE DEPENDENCY
     * INJECTOR. IT SHOULD THEREFORE NEVER BE CALLED DIRECTLY!
     *
     * @param managerComponent  auto-injected managerComponent
     * @param projectComponent  auto-injected projectComponent
     */
    @ExecuteBefore(INITIALIZED)
    public final void install(
            @WithState(INITIALIZED) ManagerComponent managerComponent,
            @WithState(INITIALIZED) ProjectComponent projectComponent) {

        requireNonNull(projectComponent); // Must be initialized first.
        managerComponent.put(this);
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
        throw readonlyException();
    }

    @Override
    public Persister<ENTITY> persister(HasLabelSet<ENTITY> fields) {
        throw readonlyException();
    }

    @Override
    public Updater<ENTITY> updater() {
        throw readonlyException();
    }

    @Override
    public Updater<ENTITY> updater(HasLabelSet<ENTITY> fields) {
        throw readonlyException();
    }

    @Override
    public Remover<ENTITY> remover() {
        throw readonlyException();
    }

    private RuntimeException readonlyException() {
        return new UnsupportedOperationException(format(
            "Manager %s is based on a VIEW Table and is therefore read-only.",
            getClass().getSimpleName()
        ));
    }
}
