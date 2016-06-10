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
package com.speedment.runtime.internal.runtime;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.injector.annotation.RequiresInjectable;
import com.speedment.runtime.Speedment;
import com.speedment.runtime.component.ManagerComponent;
import com.speedment.runtime.component.ProjectComponent;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.internal.component.ConnectionPoolComponentImpl;
import com.speedment.runtime.internal.component.DbmsHandlerComponentImpl;
import com.speedment.runtime.internal.component.EntityManagerImpl;
import com.speedment.runtime.internal.component.EventComponentImpl;
import com.speedment.runtime.internal.component.InfoComponentImpl;
import com.speedment.runtime.internal.component.ManagerComponentImpl;
import com.speedment.runtime.internal.component.NativeStreamSupplierComponentImpl;
import com.speedment.runtime.internal.component.PasswordComponentImpl;
import com.speedment.runtime.internal.component.PrimaryKeyFactoryComponentImpl;
import com.speedment.runtime.internal.component.ProjectComponentImpl;
import com.speedment.runtime.internal.component.ResultSetMapperComponentImpl;
import com.speedment.runtime.internal.component.TypeMapperComponentImpl;
import com.speedment.runtime.internal.config.dbms.StandardDbmsTypes;

/**
 * An abstract base implementation of the {@link Speedment} interface.
 * 
 * @author Emil Forslund
 * @since  2.4.0
 */
@RequiresInjectable({
    ConnectionPoolComponentImpl.class,
    DbmsHandlerComponentImpl.class,
    EntityManagerImpl.class,
    EventComponentImpl.class,
    InfoComponentImpl.class,
    ManagerComponentImpl.class,
    NativeStreamSupplierComponentImpl.class,
    PasswordComponentImpl.class,
    PrimaryKeyFactoryComponentImpl.class,
    ProjectComponentImpl.class,
    ResultSetMapperComponentImpl.class,
    TypeMapperComponentImpl.class,
    StandardDbmsTypes.class
})
public abstract class AbstractSpeedment implements Speedment {
    
    private @Inject ProjectComponent projectComponent;
    private @Inject Injector injector;
    
    protected AbstractSpeedment() {}

    @Override
    public <T> T getOrThrow(Class<T> componentClass) throws SpeedmentException {
        try {
            return injector.get(componentClass);
        } catch (final IllegalArgumentException ex) {
            throw new SpeedmentException(
                "Specified component '" + componentClass.getSimpleName() + 
                "' is not installed in the platform.", ex
            );
        }
    }

    @Override
    public Project project() {
        return projectComponent.getProject();
    }

    @Override
    public void stop() {
        injector.stop();
    }
    
    protected abstract AbstractApplicationBuilder<?, ?> newApplicationBuilder();
}