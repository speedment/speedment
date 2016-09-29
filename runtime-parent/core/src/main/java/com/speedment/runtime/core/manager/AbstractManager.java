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

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.ManagerComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.component.resultset.ResultSetMapperComponent;
import com.speedment.runtime.core.exception.SpeedmentException;

import java.util.stream.Stream;

import static com.speedment.common.injector.State.INITIALIZED;
import static com.speedment.common.injector.State.RESOLVED;
import static com.speedment.common.invariant.NullUtil.requireNonNulls;
import static java.util.Objects.requireNonNull;

/**
 *
 * @param <ENTITY>  entity type
 * 
 * @author  Emil Forslund
 * @since   2.0.0
 */
public abstract class AbstractManager<ENTITY> implements Manager<ENTITY> {

    private ManagerSupport<ENTITY> support;

    protected AbstractManager() {}
    
    @ExecuteBefore(INITIALIZED)
    final void createSupport(
            Injector injector, 
            @WithState(INITIALIZED) ProjectComponent projectComponent,
            @WithState(INITIALIZED) ResultSetMapperComponent resultSetComponent,
            @WithState(INITIALIZED) DbmsHandlerComponent dbmsHandlerComponent) {
        
        // Make sure this is initialized after these components.
        requireNonNulls(projectComponent, resultSetComponent, dbmsHandlerComponent);
        this.support = createSupport(injector);
    }
    
    @ExecuteBefore(RESOLVED)
    final void install(
            @WithState(INITIALIZED) ManagerComponent managerComponent,
            @WithState(INITIALIZED) ProjectComponent projectComponent) {
        
        requireNonNull(projectComponent); // Must be initialized first.
        managerComponent.put(this);
    }
    
    protected abstract ManagerSupport<ENTITY> createSupport(Injector injector);
    
    @Override
    public final Stream<ENTITY> stream() {
        return support.stream();
    }

    @Override
    public final ENTITY persist(ENTITY entity) throws SpeedmentException {
        return support.persist(entity);
    }

    @Override
    public final ENTITY update(ENTITY entity) throws SpeedmentException {
        return support.update(entity);
    }

    @Override
    public final ENTITY remove(ENTITY entity) throws SpeedmentException {
        return support.remove(entity);
    }
}