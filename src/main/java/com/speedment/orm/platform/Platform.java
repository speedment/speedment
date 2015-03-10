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
package com.speedment.orm.platform;

import com.speedment.orm.platform.component.Component;
import com.speedment.orm.annotations.Api;
import com.speedment.orm.platform.component.ManagerComponent;
import com.speedment.orm.platform.component.impl.ManagerComponentImpl;
import com.speedment.orm.platform.component.impl.ProjectComponentImpl;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author pemi
 */
public final class Platform {

    private final Logger logger = LogManager.getLogger(getClass());
    private final Map<Class<?>, Component> plugins;

    private Platform() {
        plugins = new ConcurrentHashMap<>();
        add(new ManagerComponentImpl());
        add(new ProjectComponentImpl());
    }

    public static Platform get() {
        return PlatformHolder.INSTANCE;
    }

    @Api(version = 0)
    public <T extends Component> T add(T component) {
        Objects.requireNonNull(component);
        component.onAdd();

        @SuppressWarnings("unchecked") // Must be same type!
        final T old = (T) plugins.put(component.getComponentClass(), component);
        if (old != null) {
            old.onRemove();
        }

        return old;
    }

    @SuppressWarnings("unchecked") // Must be same type!
    public <T extends Component> T get(Class<T> clazz) {
        return (T) plugins.get(clazz);
    }

    public Stream<Entry<Class<?>, Component>> stream() {
        return plugins.entrySet().stream();
    }

    private static class PlatformHolder {

        private static final Platform INSTANCE = new Platform();
    }
}
