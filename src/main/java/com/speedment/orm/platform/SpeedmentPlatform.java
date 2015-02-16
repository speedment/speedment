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

import com.speedment.orm.annotations.Api;
import com.speedment.orm.config.model.ConfigEntityFactory;
import com.speedment.orm.config.model.ProjectManager;
import com.speedment.orm.config.model.impl.ConfigEntityFactoryImpl;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public class SpeedmentPlatform {

    private final Map<Class<?>, Component> plugins;
    private final AtomicBoolean running;

    private SpeedmentPlatform() {
        plugins = new ConcurrentHashMap<>();
        running = new AtomicBoolean();
        init();
    }

    @Api(version = 0)
    public static SpeedmentPlatform getInstance() {
        return PlatformHolder.INSTANCE;
    }

    private static class PlatformHolder {

        private static final SpeedmentPlatform INSTANCE = new SpeedmentPlatform();
    }

    private void init() {
        final ConfigEntityFactory configEntityFactory = new ConfigEntityFactoryImpl();
        put(ConfigEntityFactory.class, configEntityFactory);
        put(ProjectManager.class, configEntityFactory.newProjectManager());
    }

    @Api(version = 0)
    public <T extends Component> T put(Class<T> clazz, T component) {
        Objects.requireNonNull(clazz);
        Objects.requireNonNull(component);
        component.added();
        @SuppressWarnings("unchecked") // Must be same type!
        final T old = (T) plugins.put(clazz, component);
        if (old != null) {
            old.removed();
        }
        return old;
    }

    // Todo: Remove this method?
    public <T extends Component> T remove(Class<T> clazz) {
        @SuppressWarnings("unchecked") // Must be same type!
        final T old = (T) plugins.remove(clazz);
        if (old != null) {
            old.removed();
        }
        return old;
    }

    @Api(version = 0)
    @SuppressWarnings("unchecked") // Must be same type!
    public <T extends Component> T get(Class<T> clazz) {
        return (T) plugins.get(clazz);
    }

    @Api(version = 0)
    public Stream<Entry<Class<?>, Component>> componentStream() {
        return plugins.entrySet().stream();
    }

    @Api(version = 0)
    public SpeedmentPlatform start() {
        // Todo: apply standard component
        running.set(true);
        return this;
    }

    @Api(version = 0)
    public SpeedmentPlatform stop() {
        running.set(false);
        //componentStream().map(Entry::getValue).forEach(Component::removed);
        return this;
    }

    @Api(version = 0)
    public boolean isRunning() {
        return running.get();
    }

    @Api(version = 0)
    public ConfigEntityFactory getConfigEntityFactory() {
        return get(ConfigEntityFactory.class);
    }

}
