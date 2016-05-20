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
import com.speedment.runtime.internal.util.DefaultClassMapper;
import static com.speedment.runtime.internal.util.ImmutableUtil.throwNewUnsupportedOperationExceptionImmutable;
import com.speedment.runtime.manager.Manager;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 *
 * @author Emil Forslund
 * @since  2.4.0
 */
public abstract class AbstractSpeedment extends DefaultClassMapper<Component> implements Speedment {

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
    
    protected AbstractSpeedment() {
        initComponents();
    }
    
    @Override
    public <R extends Component> R get(Class<R> iface) {
        requireNonNull(iface);
        try {
            return requireNonNull(super.get(iface));
        } catch (final NullPointerException ex) {
            throw new SpeedmentException(
                "The specified component '" + iface.getSimpleName() + "' has " +
                "not been installed in the platform.", ex
            );
        }
    }
    
    protected final void setUnmodifiable() { // TODO Can we do this in some better way?
        unmodifiable = true;
    }
    
    @Override
    public final ManagerComponent getManagerComponent() {
        return managerComponent;
    }

    @Override
    public final ProjectComponent getProjectComponent() {
        return projectComponent;
    }

    @Override
    public final PrimaryKeyFactoryComponent getPrimaryKeyFactoryComponent() {
        return primaryKeyFactoryComponent;
    }

    @Override
    public final DbmsHandlerComponent getDbmsHandlerComponent() {
        return dbmsHandlerComponent;
    }

    @Override
    public final ResultSetMapperComponent getResultSetMapperComponent() {
        return resultSetMapperComponent;
    }

    @Override
    public final EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public final ConnectionPoolComponent getConnectionPoolComponent() {
        return connectionPoolComponent;
    }

    @Override
    public final StreamSupplierComponent getStreamSupplierComponent() {
        return streamSupplierComponent;
    }

    @Override
    public final TypeMapperComponent getTypeMapperComponent() {
        return typeMapperComponent;
    }

    @Override
    public final EventComponent getEventComponent() {
        return eventComponent;
    }

    @Override
    public final PasswordComponent getPasswordComponent() {
        return passwordComponent;
    }
    
    @Override
    public final InfoComponent getInfoComponent() {
        return infoComponent;
    }

    @Override
    public final Component put(Component item) {
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
    public final <ENTITY> Manager<ENTITY> managerOf(Class<ENTITY> entityClass) throws SpeedmentException {
        return getManagerComponent().managerOf(entityClass);
    }

    @Override
    public final Stream<Component> components() {
        return super.stream().map(Map.Entry::getValue);
    }

    @Override
    public void stop() {
        // do nothing yet!
    }

    @Override
    public final Speedment copyWithSameTypeOfComponents() {
        final AbstractApplicationBuilder<?, ?> app = newApplicationBuilder();

        stream().map(Map.Entry::getValue)
                .map(this::componentConstructor)
                .forEach(app::with);

        return app.build();
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + 
            components()
                .map(Component::asSoftware)
                .map(s -> s.getName() + "-" + s.getVersion())
                .collect(joining(", ", "[", "]"));
    }
    
    protected abstract AbstractApplicationBuilder<?, ?> newApplicationBuilder();

    private void initComponents() {
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
    
    private Component put(Function<Speedment, Component> mapper) {
        requireNonNull(mapper);
        return put(mapper.apply(this));
    }
    
    private ComponentConstructor<?> componentConstructor(Component component) {
        return s -> component.defaultCopy(s);
    }
}
