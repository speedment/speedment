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
package com.speedment.core.platform;

import com.speedment.core.platform.component.Component;
import com.speedment.core.annotations.Api;
import com.speedment.core.platform.component.impl.DbmsHandlerComponentImpl;
import com.speedment.core.platform.component.impl.DefaultClassMapper;
import com.speedment.core.platform.component.impl.EntityManagerImpl;
import com.speedment.core.platform.component.impl.JavaTypeMapperComponentImpl;
import com.speedment.core.platform.component.impl.LoggerFactoryComponentImpl;
import com.speedment.core.platform.component.impl.ManagerComponentImpl;
import com.speedment.core.platform.component.impl.PrimaryKeyFactoryComponentImpl;
import com.speedment.core.platform.component.impl.ProjectComponentImpl;
import com.speedment.core.platform.component.impl.SqlTypeMapperComponentImpl;

/**
 *
 * @author pemi
 */
public final class Platform extends DefaultClassMapper<Component> {

    //private final Logger logger = LogManager.getLogger(getClass());
    private Platform() {
        add(new ManagerComponentImpl());
        add(new ProjectComponentImpl());
        add(new PrimaryKeyFactoryComponentImpl());
        add(new DbmsHandlerComponentImpl());
        add(new SqlTypeMapperComponentImpl());
        add(new JavaTypeMapperComponentImpl());
        add(new EntityManagerImpl());
        add(new LoggerFactoryComponentImpl());
    }

    /**
     * Returns the Platform singleton.
     *
     * @return the Platform singleton
     */
    public static Platform get() {
        return PlatformHolder.INSTANCE;
    }

    /**
     * Gets a Platform Component based on its interface class.
     * <p>
     * Supported interfaces:      
     *     {@link com.speedment.core.platform.component.EntityManager}
     *     {@link com.speedment.core.platform.component.DbmsHandlerComponent}
     *     {@link com.speedment.core.platform.component.ManagerComponent}
     *     {@link com.speedment.core.platform.component.PrimaryKeyFactoryComponent}
     *     {@link com.speedment.core.platform.component.ProjectComponent}
     *     {@link com.speedment.core.platform.component.SqlTypeMapperComponent}
     *     {@link com.speedment.core.platform.component.LoggerFactoryComponent}
     *
     * @param <R> The intended return type
     * @param clazz The class of the intended return type
     * @return The currently mapped instance
     */
    @Override
    public <R extends Component> R get(Class<R> clazz) {
        return super.get(clazz);
    }

    @Override
    @Api(version = 0)
    public Component add(Component item) {
        return add(item, Component::onAdd, Component::onRemove, Component::getComponentClass);
    }

    private static class PlatformHolder {

        private static final Platform INSTANCE = new Platform();
    }
}
