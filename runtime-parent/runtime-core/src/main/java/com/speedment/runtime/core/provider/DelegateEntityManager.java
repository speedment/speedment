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
package com.speedment.runtime.core.provider;

import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.runtime.core.component.EntityManager;
import com.speedment.runtime.core.component.ManagerComponent;
import com.speedment.runtime.core.internal.component.EntityManagerImpl;

import static com.speedment.common.injector.State.RESOLVED;

/**
 *
 * @author Per Minborg
 * @since 3.2.0
 */
public final class DelegateEntityManager implements EntityManager {

    private final EntityManagerImpl inner;

    public DelegateEntityManager() {
        this.inner = new EntityManagerImpl();
    }

    @ExecuteBefore(RESOLVED)
    public void installManagers(ManagerComponent managerComponent) {
        inner.installManagers(managerComponent);
    }

    @Override
    public <ENTITY> void persist(ENTITY entity) {
        inner.persist(entity);
    }

    @Override
    public <ENTITY> void update(ENTITY entity) {
        inner.update(entity);
    }

    @Override
    public <ENTITY> void remove(ENTITY entity) {
        inner.remove(entity);
    }

}
