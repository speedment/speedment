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
package com.speedment.runtime.application;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.runtime.core.Speedment;
import com.speedment.runtime.core.component.StreamSupplierComponent;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.core.manager.ManagerConfigurator;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * An abstract base implementation of the {@link Speedment} interface.
 *
 * @author Emil Forslund
 * @since 3.0.0
 */
public abstract class AbstractSpeedment implements Speedment {

    private static final Logger LOGGER = LoggerManager.getLogger(AbstractSpeedment.class);

    private Injector injector;

    protected AbstractSpeedment() {}

    @ExecuteBefore(State.INITIALIZED)
    public final void setInjector(Injector injector) {
        this.injector = requireNonNull(injector);
    }

    @Override
    public <T> Optional<T> get(Class<T> type) {
        return injector.get(type);
    }

    @Override
    public <T> T getOrThrow(Class<T> componentClass) {
        try {
            return injector.getOrThrow(componentClass);
        } catch (final Exception ex) {
            logTypicalError(componentClass);
            throw new SpeedmentException(
                "Specified component '" + componentClass.getSimpleName()
                + "' is not installed in the platform.", ex
            );
        }
    }

    @Override
    public <ENTITY> ManagerConfigurator<ENTITY> configure(Class<? extends Manager<ENTITY>> managerClass) {
        requireNonNull(managerClass);
        return ManagerConfigurator.create(getOrThrow(StreamSupplierComponent.class), getOrThrow(managerClass));
    }

    @Override
    public void stop() {
        injector.stop();
    }

    private <T> void logTypicalError(Class<T> componentClass) {
        final String componentSimpleName = componentClass.getSimpleName();

        if ("JoinComponent".equals(componentSimpleName)) {
            LOGGER.error(
                "Since 3.2.0, The JoinBundle (that contains the JoinComponent) is optional. " +
                "Make sure that you have installed the JoinBundle by invoking .withBundle(JoinBundle.class) in your application builder " +
                "and add 'requires com.speedment.runtime.join;' in your module-info.java file (if any)."
            );
        }
    }


}
