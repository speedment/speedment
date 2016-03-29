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
package com.speedment.internal.core.runtime;

import com.speedment.Speedment;
import com.speedment.component.Component;
import com.speedment.exception.SpeedmentException;
import com.speedment.manager.Manager;
import com.speedment.component.CodeGenerationComponent;
import com.speedment.component.ComponentConstructor;
import com.speedment.component.connectionpool.ConnectionPoolComponent;
import com.speedment.component.DbmsHandlerComponent;
import com.speedment.component.DocumentPropertyComponent;
import com.speedment.component.EntityManager;
import com.speedment.component.EventComponent;
import com.speedment.component.ManagerComponent;
import com.speedment.component.PasswordComponent;
import com.speedment.component.PrimaryKeyFactoryComponent;
import com.speedment.component.ProjectComponent;
import com.speedment.component.StreamSupplierComponent;
import com.speedment.component.TypeMapperComponent;
import com.speedment.component.UserInterfaceComponent;
import com.speedment.internal.core.platform.DefaultClassMapper;
import com.speedment.internal.core.platform.component.impl.CodeGenerationComponentImpl;
import static com.speedment.internal.util.ImmutableUtil.throwNewUnsupportedOperationExceptionImmutable;
import com.speedment.internal.core.platform.component.impl.ConnectionPoolComponentImpl;
import com.speedment.internal.core.platform.component.impl.DbmsHandlerComponentImpl;
import com.speedment.internal.core.platform.component.impl.DocumentPropertyComponentImpl;
import com.speedment.internal.core.platform.component.impl.EntityManagerImpl;
import com.speedment.internal.core.platform.component.impl.EventComponentImpl;
import com.speedment.internal.core.platform.component.impl.JavaTypeMapperComponentImpl;
import com.speedment.internal.core.platform.component.impl.ManagerComponentImpl;
import com.speedment.internal.core.platform.component.impl.NativeStreamSupplierComponentImpl;
import com.speedment.internal.core.platform.component.impl.PasswordComponentImpl;
import com.speedment.internal.core.platform.component.impl.PrimaryKeyFactoryComponentImpl;
import com.speedment.internal.core.platform.component.impl.ProjectComponentImpl;
import com.speedment.internal.core.platform.component.impl.TypeMapperComponentImpl;
import com.speedment.internal.core.platform.component.impl.UserInterfaceComponentImpl;
import static com.speedment.internal.util.Cast.castOrFail;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Stream;
import com.speedment.component.resultset.ResultSetMapperComponent;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

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
    private UserInterfaceComponent userInterfaceComponent;
    private PasswordComponent passwordComponent;
    private CodeGenerationComponent codeGenerationComponent;
    private DocumentPropertyComponent documentPropertyComponent;

    SpeedmentImpl() {
        put(ManagerComponentImpl::new);
        put(ProjectComponentImpl::new);
        put(PrimaryKeyFactoryComponentImpl::new);
        put(DbmsHandlerComponentImpl::new);
        put(JavaTypeMapperComponentImpl::new);
        put(EntityManagerImpl::new);
        put(ConnectionPoolComponentImpl::new);
        put(NativeStreamSupplierComponentImpl::new);
        put(TypeMapperComponentImpl::new);
        put(EventComponentImpl::new);
        put(UserInterfaceComponentImpl::new);
        put(PasswordComponentImpl::new);
        put(CodeGenerationComponentImpl::new);
        put(DocumentPropertyComponentImpl::new);
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
        if (item instanceof UserInterfaceComponent) {
            userInterfaceComponent = castOrFail(item, UserInterfaceComponent.class);
        }
        if (item instanceof PasswordComponent) {
            passwordComponent = castOrFail(item, PasswordComponent.class);
        }
        if (item instanceof CodeGenerationComponent) {
            codeGenerationComponent = castOrFail(item, CodeGenerationComponent.class);
        }
        if (item instanceof DocumentPropertyComponent) {
            documentPropertyComponent = castOrFail(item, DocumentPropertyComponent.class);
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
    public UserInterfaceComponent getUserInterfaceComponent() {
        return userInterfaceComponent;
    }

    @Override
    public PasswordComponent getPasswordComponent() {
        return passwordComponent;
    }

    @Override
    public CodeGenerationComponent getCodeGenerationComponent() {
        return codeGenerationComponent;
    }

    @Override
    public DocumentPropertyComponent getDocumentPropertyComponent() {
        return documentPropertyComponent;
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

    private ComponentConstructor componentConstructor(Component component) {
        final Class<? extends Component> clazz = component.getClass();
        return s -> {
            try {
                final Constructor<? extends Component> constr = clazz.getConstructor(Speedment.class);
                return constr.newInstance(s);
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                throw new RuntimeException(ex);
            }
        };
    }
}