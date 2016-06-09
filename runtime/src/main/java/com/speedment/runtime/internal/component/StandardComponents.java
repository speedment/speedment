package com.speedment.runtime.internal.component;

import com.speedment.runtime.component.Component;

/**
 * A group of standard components that can be injected automatically.
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class StandardComponents {
    
    public static Class<? extends Component>[] get() {
        return (Class<? extends Component>[]) new Object[] {
            ConnectionPoolComponentImpl.class,
            DbmsHandlerComponentImpl.class,
            EntityManagerImpl.class,
            EventComponentImpl.class,
            InfoComponentImpl.class,
            ManagerComponentImpl.class,
            NativeStreamSupplierComponentImpl.class,
            PasswordComponentImpl.class,
            PrimaryKeyFactoryComponentImpl.class,
            ProjectComponentImpl.class,
            ResultSetMapperComponentImpl.class,
            TypeMapperComponentImpl.class
        };
    }
    
    private StandardComponents() {}
}