/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.core.platform;

import com.speedment.Speedment;
import com.speedment.internal.core.platform.component.Component;
import com.speedment.annotation.Api;
import com.speedment.exception.SpeedmentException;
import com.speedment.Manager;
import com.speedment.internal.core.platform.component.ManagerComponent;
import com.speedment.internal.core.platform.component.impl.ConnectionPoolComponentImpl;
import com.speedment.internal.core.platform.component.impl.DbmsHandlerComponentImpl;
import com.speedment.internal.core.platform.component.impl.DefaultClassMapper;
import com.speedment.internal.core.platform.component.impl.EntityManagerImpl;
import com.speedment.internal.core.platform.component.impl.JavaTypeMapperComponentImpl;
import com.speedment.internal.core.platform.component.impl.LoggerFactoryComponentImpl;
import com.speedment.internal.core.platform.component.impl.ManagerComponentImpl;
import com.speedment.internal.core.platform.component.impl.PrimaryKeyFactoryComponentImpl;
import com.speedment.internal.core.platform.component.impl.ProjectComponentImpl;
import com.speedment.internal.core.platform.component.impl.SqlTypeMapperComponentImpl;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;

final class SpeedmentImpl extends DefaultClassMapper<Component> implements Speedment {

    SpeedmentImpl() {
        put(new ManagerComponentImpl());
        put(new ProjectComponentImpl());
        put(new PrimaryKeyFactoryComponentImpl());
        put(new DbmsHandlerComponentImpl(this));
        put(new SqlTypeMapperComponentImpl());
        put(new JavaTypeMapperComponentImpl());
        put(new EntityManagerImpl(this));
        put(new LoggerFactoryComponentImpl());
        put(new ConnectionPoolComponentImpl());
    }

    @Override
    public <R extends Component> R get(Class<R> iface) {
        requireNonNull(iface);
        return super.get(iface);
    }

    @Override
    public Component put(Component item) {
        requireNonNull(item);
        return put(item, Component::onAdd, Component::onRemove, Component::getComponentClass);
    }

    @Override
    public <ENTITY> Manager<ENTITY> managerOf(Class<ENTITY> entityClass) throws SpeedmentException {
        requireNonNull(entityClass);
        return get(ManagerComponent.class).managerOf(entityClass);
    }

    @Override
    public void stop() {
        // do nothing yet!
    }
}
