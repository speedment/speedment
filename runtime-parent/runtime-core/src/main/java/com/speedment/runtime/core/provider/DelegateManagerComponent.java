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

import com.speedment.runtime.core.component.ManagerComponent;
import com.speedment.runtime.core.internal.component.ManagerComponentImpl;
import com.speedment.runtime.core.manager.Manager;

import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 * @since 3.2.0
 */
public final class DelegateManagerComponent implements ManagerComponent {

    private final ManagerComponent inner;

    public DelegateManagerComponent() {
        this.inner = new ManagerComponentImpl();
    }

    @Override
    public <E> void put(Manager<E> manager) {
        inner.put(manager);
    }

    @Override
    public <E> Manager<E> managerOf(Class<E> entityClass) {
        return inner.managerOf(entityClass);
    }

    @Override
    public Stream<Manager<?>> stream() {
        return inner.stream();
    }
}