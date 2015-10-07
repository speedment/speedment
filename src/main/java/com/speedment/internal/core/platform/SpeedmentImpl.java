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
import com.speedment.component.Component;
import com.speedment.exception.SpeedmentException;
import com.speedment.Manager;
import com.speedment.component.ManagerComponent;
import com.speedment.internal.core.platform.component.impl.ConnectionPoolComponentImpl;
import com.speedment.internal.core.platform.component.impl.DbmsHandlerComponentImpl;
import com.speedment.internal.core.platform.component.impl.EntityManagerImpl;
import com.speedment.internal.core.platform.component.impl.JavaTypeMapperComponentImpl;
import com.speedment.internal.core.platform.component.impl.LoggerFactoryComponentImpl;
import com.speedment.internal.core.platform.component.impl.ManagerComponentImpl;
import com.speedment.internal.core.platform.component.impl.NativeStreamSupplierComponentImpl;
import com.speedment.internal.core.platform.component.impl.PrimaryKeyFactoryComponentImpl;
import com.speedment.internal.core.platform.component.impl.ProjectComponentImpl;
import com.speedment.internal.core.platform.component.impl.SqlTypeMapperComponentImpl;
import java.util.Map.Entry;
import java.util.function.Function;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import java.util.stream.Stream;


final class SpeedmentImpl extends DefaultClassMapper<Component> implements Speedment {

    SpeedmentImpl() {
        put(ManagerComponentImpl::new);
        put(ProjectComponentImpl::new);
        put(PrimaryKeyFactoryComponentImpl::new);
        put(DbmsHandlerComponentImpl::new);
        put(SqlTypeMapperComponentImpl::new);
        put(JavaTypeMapperComponentImpl::new);
        put(EntityManagerImpl::new);
        put(LoggerFactoryComponentImpl::new);
        put(ConnectionPoolComponentImpl::new);
        put(NativeStreamSupplierComponentImpl::new);
    }

    @Override
    public <R extends Component> R get(Class<R> iface) {
        requireNonNull(iface);
        return super.get(iface);
    }

    public Component put(Function<Speedment, Component> mapper) {
        requireNonNull(mapper);
        return put(mapper.apply(this));
    }

    @Override
    public Component put(Component item) {
        requireNonNull(item);
        return put(item, Component::getComponentClass);
    }

    @Override
    public Stream<Component> components() {
        return super.stream().map(Entry::getValue);
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

    @Override
    public String toString() {
        return SpeedmentImpl.class.getSimpleName() + " " + components().map(c -> c.getTitle() + "-" + c.getVersion()).collect(joining(", ", "[", "]"));
    }

}
