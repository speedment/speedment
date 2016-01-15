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
package com.speedment.internal.core.platform;

import com.speedment.Speedment;
import com.speedment.component.Component;
import com.speedment.exception.SpeedmentException;
import com.speedment.Manager;
import com.speedment.component.CodeGenerationComponent;
import com.speedment.component.ConnectionPoolComponent;
import com.speedment.component.DbmsHandlerComponent;
import com.speedment.component.EntityManager;
import com.speedment.component.EventComponent;
import com.speedment.component.JavaTypeMapperComponent;
import com.speedment.component.LoggerFactoryComponent;
import com.speedment.component.ManagerComponent;
import com.speedment.component.PasswordComponent;
import com.speedment.component.PluginComponent;
import com.speedment.component.PrimaryKeyFactoryComponent;
import com.speedment.component.ProjectComponent;
import com.speedment.component.SqlTypeMapperComponent;
import com.speedment.component.StreamSupplierComponent;
import com.speedment.component.TypeMapperComponent;
import com.speedment.component.UserInterfaceComponent;
import com.speedment.internal.core.platform.component.impl.CodeGenerationComponentImpl;
import static com.speedment.internal.util.ImmutableUtil.throwNewUnsupportedOperationExceptionImmutable;
import com.speedment.internal.core.platform.component.impl.ConnectionPoolComponentImpl;
import com.speedment.internal.core.platform.component.impl.DbmsHandlerComponentImpl;
import com.speedment.internal.core.platform.component.impl.EntityManagerImpl;
import com.speedment.internal.core.platform.component.impl.EventComponentImpl;
import com.speedment.internal.core.platform.component.impl.JavaTypeMapperComponentImpl;
import com.speedment.internal.core.platform.component.impl.LoggerFactoryComponentImpl;
import com.speedment.internal.core.platform.component.impl.ManagerComponentImpl;
import com.speedment.internal.core.platform.component.impl.NativeStreamSupplierComponentImpl;
import com.speedment.internal.core.platform.component.impl.PasswordComponentImpl;
import com.speedment.internal.core.platform.component.impl.PluginComponentImpl;
import com.speedment.internal.core.platform.component.impl.PrimaryKeyFactoryComponentImpl;
import com.speedment.internal.core.platform.component.impl.ProjectComponentImpl;
import com.speedment.internal.core.platform.component.impl.SqlTypeMapperComponentImpl;
import com.speedment.internal.core.platform.component.impl.TypeMapperComponentImpl;
import com.speedment.internal.core.platform.component.impl.UserInterfaceComponentImpl;
import static com.speedment.internal.util.Cast.castOrFail;
import com.speedment.stream.MapStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Stream;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

final class SpeedmentImpl extends DefaultClassMapper<Component> implements Speedment {

    private boolean unmodifiable;
    private ManagerComponent managerComponent;
    private ProjectComponent projectComponent;
    private PrimaryKeyFactoryComponent primaryKeyFactoryComponent;
    private DbmsHandlerComponent dbmsHandlerComponent;
    private SqlTypeMapperComponent sqlTypeMapperComponent;
    private JavaTypeMapperComponent javaTypeMapperComponent;
    private EntityManager entityManager;
    private LoggerFactoryComponent loggerFactoryComponent;
    private ConnectionPoolComponent connectionPoolComponent;
    private StreamSupplierComponent streamSupplierComponent;
    private TypeMapperComponent typeMapperComponent;
    private PluginComponent pluginComponent;
    private EventComponent eventComponent;
    private UserInterfaceComponent userInterfaceComponent;
    private PasswordComponent passwordComponent;
    private CodeGenerationComponent codeGenerationComponent;

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
        put(TypeMapperComponentImpl::new);
        put(PluginComponentImpl::new);
        put(EventComponentImpl::new);
        put(UserInterfaceComponentImpl::new);
        put(PasswordComponentImpl::new);
        put(CodeGenerationComponentImpl::new);
    }
    
    private SpeedmentImpl(SpeedmentImpl prototype) {
        this();
        MapStream.of(prototype.stream())
            .mapValue(c -> {
                try {
                    final Class<? extends Component> clazz = c.getClass();
                    final Constructor<? extends Component> constr = clazz.getConstructor(Speedment.class);
                    final Component comp = constr.newInstance(this);
                    return comp;
                } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                    throw new RuntimeException(ex);
                }
            }).values().forEach(this::put);
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
        if (item instanceof SqlTypeMapperComponent) {
            sqlTypeMapperComponent = castOrFail(item, SqlTypeMapperComponent.class);
        }
        if (item instanceof JavaTypeMapperComponent) {
            javaTypeMapperComponent = castOrFail(item, JavaTypeMapperComponent.class);
        }
        if (item instanceof EntityManager) {
            entityManager = castOrFail(item, EntityManager.class);
        }
        if (item instanceof LoggerFactoryComponent) {
            loggerFactoryComponent = castOrFail(item, LoggerFactoryComponent.class);
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
        if (item instanceof PluginComponent) {
            pluginComponent = castOrFail(item, PluginComponent.class);
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

    @Override
    public String toString() {
        return SpeedmentImpl.class.getSimpleName() + " " + components().map(c -> c.getTitle() + "-" + c.getVersion()).collect(joining(", ", "[", "]"));
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
    public SqlTypeMapperComponent getSqlTypeMapperComponent() {
        return sqlTypeMapperComponent;
    }

    @Override
    public JavaTypeMapperComponent getJavaTypeMapperComponent() {
        return javaTypeMapperComponent;
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public LoggerFactoryComponent getLoggerFactoryComponent() {
        return loggerFactoryComponent;
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
    public PluginComponent getPluginComponent() {
        return pluginComponent;
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
    public Speedment newInstance() {
        return new SpeedmentImpl(this);
    }
}