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

import com.speedment.runtime.Speedment;
import com.speedment.runtime.component.Component;
import com.speedment.runtime.component.ComponentConstructor;
import com.speedment.runtime.component.DbmsHandlerComponent;
import com.speedment.runtime.component.EntityManager;
import com.speedment.runtime.component.EventComponent;
import com.speedment.runtime.component.InfoComponent;
import com.speedment.runtime.component.ManagerComponent;
import com.speedment.runtime.component.PasswordComponent;
import com.speedment.runtime.component.PrimaryKeyFactoryComponent;
import com.speedment.runtime.component.ProjectComponent;
import com.speedment.runtime.component.StreamSupplierComponent;
import com.speedment.runtime.component.TypeMapperComponent;
import com.speedment.runtime.component.connectionpool.ConnectionPoolComponent;
import com.speedment.runtime.component.resultset.ResultSetMapperComponent;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.internal.util.DefaultClassMapper;
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
import static com.speedment.runtime.internal.util.Cast.castOrFail;
import static com.speedment.runtime.internal.util.ImmutableUtil.throwNewUnsupportedOperationExceptionImmutable;
import com.speedment.runtime.manager.Manager;
import java.util.Map.Entry;
import static java.util.Objects.requireNonNull;
import java.util.function.Function;
import static java.util.stream.Collectors.joining;
import java.util.stream.Stream;

final class SpeedmentImpl extends DefaultClassMapper<Component> implements Speedment {

    private boolean unmodifiable;
    private ManagerComponent managerComponent;
    private ProjectComponent projectComponent;
    private PrimaryKeyFactoryComponent primaryKeyFactoryComponent;
    private DbmsHandlerComponent dbmsHandlerComponent;
    private ResultSetMapperComponent resultSetMapperComponent;
    private EntityManager entityManager;
    private ConnectionPoolComponent connectionPoolComponent;
    private StreamSupplierComponent streamSupplierComponent;
    private TypeMapperComponent typeMapperComponent;
    private EventComponent eventComponent;
    private PasswordComponent passwordComponent;
    private InfoComponent infoComponent;

    SpeedmentImpl() {
        put(ManagerComponentImpl::new);
        put(ProjectComponentImpl::new);
        put(PrimaryKeyFactoryComponentImpl::new);
        put(DbmsHandlerComponentImpl::new);
        put(ResultSetMapperComponentImpl::new);
        put(EntityManagerImpl::new);
        put(ConnectionPoolComponentImpl::new);
        put(NativeStreamSupplierComponentImpl::new);
        put(TypeMapperComponentImpl::new);
        put(EventComponentImpl::new);
        put(PasswordComponentImpl::new);
        put(InfoComponentImpl::new);
    }

    @Override
    public <R extends Component> R get(Class<R> iface) {
        requireNonNull(iface);
        try {
            return requireNonNull(super.get(iface));
        } catch (final NullPointerException ex) {
            throw new SpeedmentException(
                "The specified component '" + iface.getSimpleName() + "' has " +
                "not been installed in the Speedment platform.", ex
            );
        }
    }

    public Component put(Function<Speedment, Component> mapper) {
        requireNonNull(mapper);
        return put(mapper.apply(this));
    }

    @Override
    public Component put(Component item) {
        requireNonNull(item);

        if (unmodifiable) {
            throwNewUnsupportedOperationExceptionImmutable();
        }
        if (item instanceof ManagerComponent) {
            managerComponent = castOrFail(item, ManagerComponent.class);
        }
        if (item instanceof ProjectComponent) {
            projectComponent = castOrFail(item, ProjectComponent.class);
        }
        if (item instanceof PrimaryKeyFactoryComponent) {
            primaryKeyFactoryComponent = castOrFail(item, PrimaryKeyFactoryComponent.class);
        }
        if (item instanceof DbmsHandlerComponent) {
            dbmsHandlerComponent = castOrFail(item, DbmsHandlerComponent.class);
        }
        if (item instanceof ResultSetMapperComponent) {
            resultSetMapperComponent = castOrFail(item, ResultSetMapperComponent.class);
        }
        if (item instanceof EntityManager) {
            entityManager = castOrFail(item, EntityManager.class);
        }
        if (item instanceof ConnectionPoolComponent) {
            connectionPoolComponent = castOrFail(item, ConnectionPoolComponent.class);
        }
        if (item instanceof StreamSupplierComponent) {
            streamSupplierComponent = castOrFail(item, StreamSupplierComponent.class);
        }
        if (item instanceof TypeMapperComponent) {
            typeMapperComponent = castOrFail(item, TypeMapperComponent.class);
        }
        if (item instanceof EventComponent) {
            eventComponent = castOrFail(item, EventComponent.class);
        }
        if (item instanceof PasswordComponent) {
            passwordComponent = castOrFail(item, PasswordComponent.class);
        }
        if (item instanceof InfoComponent) {
            infoComponent = castOrFail(item, InfoComponent.class);
        }
        return put(item, Component::getComponentClass);
    }

    @Override
    public Stream<Component> components() {
        return super.stream().map(Entry::getValue);
    }

    @Override
    public <ENTITY> Manager<ENTITY> managerOf(Class<ENTITY> entityClass) throws SpeedmentException {
        return getManagerComponent().managerOf(entityClass);
    }

    @Override
    public void stop() {
        // do nothing yet!
    }

    public void setUnmodifiable() {
        unmodifiable = true;
    }

    @Override
    public ManagerComponent getManagerComponent() {
        return managerComponent;
    }

    @Override
    public ProjectComponent getProjectComponent() {
        return projectComponent;
    }

    @Override
    public PrimaryKeyFactoryComponent getPrimaryKeyFactoryComponent() {
        return primaryKeyFactoryComponent;
    }

    @Override
    public DbmsHandlerComponent getDbmsHandlerComponent() {
        return dbmsHandlerComponent;
    }

    @Override
    public ResultSetMapperComponent getResultSetMapperComponent() {
        return resultSetMapperComponent;
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public ConnectionPoolComponent getConnectionPoolComponent() {
        return connectionPoolComponent;
    }

    @Override
    public StreamSupplierComponent getStreamSupplierComponent() {
        return streamSupplierComponent;
    }

    @Override
    public TypeMapperComponent getTypeMapperComponent() {
        return typeMapperComponent;
    }

    @Override
    public EventComponent getEventComponent() {
        return eventComponent;
    }

    @Override
    public PasswordComponent getPasswordComponent() {
        return passwordComponent;
    }
    
    @Override
    public InfoComponent getInfoComponent() {
        return infoComponent;
    }

    @Override
    public Speedment copyWithSameTypeOfComponents() {
        final SpeedmentApplicationLifecycle<?> lifecycle = new DefaultSpeedmentApplicationLifecycle();

        stream().map(Entry::getValue)
                .map(this::componentConstructor)
                .forEach(lifecycle::with);

        return lifecycle.build();
    }
    
    @Override
    public String toString() {
        return SpeedmentImpl.class.getSimpleName() + " " + 
            components()
                .map(Component::asSoftware)
                .map(s -> s.getName() + "-" + s.getVersion())
                .collect(joining(", ", "[", "]"));
    }

    private ComponentConstructor<?> componentConstructor(Component component) {
        return s -> component.defaultCopy(s);
    }
}