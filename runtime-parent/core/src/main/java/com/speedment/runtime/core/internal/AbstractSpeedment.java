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
package com.speedment.runtime.core.internal;

import com.speedment.common.injector.InjectBundle;
import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.runtime.core.Speedment;
import com.speedment.runtime.core.component.ManagerComponent;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.internal.component.ConnectionPoolComponentImpl;
import com.speedment.runtime.core.internal.component.DbmsHandlerComponentImpl;
import com.speedment.runtime.core.internal.component.EntityManagerImpl;
import com.speedment.runtime.core.internal.component.InfoComponentImpl;
import com.speedment.runtime.core.internal.component.ManagerComponentImpl;
import com.speedment.runtime.core.internal.component.NativeStreamSupplierComponentImpl;
import com.speedment.runtime.core.internal.component.PasswordComponentImpl;
import com.speedment.runtime.core.internal.component.PrimaryKeyFactoryComponentImpl;
import com.speedment.runtime.core.internal.component.ProjectComponentImpl;
import com.speedment.runtime.core.internal.component.resultset.ResultSetMapperComponentImpl;
import com.speedment.runtime.core.internal.db.StandardDbmsTypes;
import com.speedment.runtime.core.manager.Manager;

import java.util.Optional;

/**
 * An abstract base implementation of the {@link Speedment} interface.
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */

public abstract class AbstractSpeedment implements Speedment {
    
    public static InjectBundle include() {
        return InjectBundle.of(
            InfoComponentImpl.class,
            ConnectionPoolComponentImpl.class,
            DbmsHandlerComponentImpl.class,
            EntityManagerImpl.class,            
            ManagerComponentImpl.class,
            NativeStreamSupplierComponentImpl.class,
            PasswordComponentImpl.class,
            PrimaryKeyFactoryComponentImpl.class,
            ProjectComponentImpl.class,
            ResultSetMapperComponentImpl.class,
            StandardDbmsTypes.class
        );
    }
    
    private @Inject ManagerComponent managerComponent;
    private @Inject Injector injector;
    
    protected AbstractSpeedment() {}

    @Override
    public <T> Optional<T> get(Class<T> type) {
        return injector.get(type);
    }

    @Override
    public <T> T getOrThrow(Class<T> componentClass) throws SpeedmentException {
        try {
            return injector.getOrThrow(componentClass);
        } catch (final IllegalArgumentException ex) {
            throw new SpeedmentException(
                "Specified component '" + componentClass.getSimpleName() + 
                "' is not installed in the platform.", ex
            );
        }
    }

    @Override
    public <ENTITY> Manager<ENTITY> managerOf(Class<ENTITY> entityType) {
        return managerComponent.managerOf(entityType);
    }

    @Override
    public void stop() {
        injector.stop();
    }
}