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
import com.speedment.orm.config.model.Project;
import com.speedment.orm.config.model.ProjectManager;
import com.speedment.orm.config.model.impl.utils.GroovyParser;
import com.speedment.orm.core.manager.Manager;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author pemi
 */
public class SpeedmentBuilder implements Speedment {

    private final Logger logger = LogManager.getLogger(getClass());

    private final Map<Class<?>, Component> plugins;
    private final Map<Class<?>, Manager> entityManagerMap;
    private final Map<Class<?>, Manager> managerManagerMap;
    private Path configPath;
    private Project project;

    public SpeedmentBuilder() {
        plugins = new ConcurrentHashMap<>();
        entityManagerMap = new ConcurrentHashMap<>();
        managerManagerMap = new ConcurrentHashMap<>();
        init();
    }

    public SpeedmentBuilder(final String configFileName) {
        this();
        setConfigFile(Paths.get(configFileName));
    }

    private void init() {
        put(ProjectManager.class, ProjectManager.newProjectManager());
    }

    public final SpeedmentBuilder setConfigFile(Path configPath) {
        this.configPath = configPath;
        loadConfig();
        return this;
    }

    private SpeedmentBuilder loadConfig() {
        this.project = Project.newProject();
        final Path p = getConfigPath()
            .orElseThrow(() -> new IllegalStateException("Unable to load before path is set."));

        try {
            GroovyParser.fromGroovy(project, p);
        } catch (IOException ex) {
            logger.fatal("Unable to parse " + getConfigPath().toString(), ex);
            throw new RuntimeException(ex);
        }
        return this;
    }

    @Api(version = 0)
    public <T extends Component> SpeedmentBuilder put(Class< T> clazz, T component
    ) {
        Objects.requireNonNull(clazz);
        Objects.requireNonNull(component);
        component.added();
        @SuppressWarnings("unchecked") // Must be same type!
        final T old = (T) plugins.put(clazz, component);
        if (old != null) {
            old.removed();
        }
        //return old;
        return this;
    }

    // Todo: Remove this method?
    public <T extends Component> SpeedmentBuilder remove(Class<T> clazz) {
        @SuppressWarnings("unchecked") // Must be same type!
        final T old = (T) plugins.remove(clazz);
        if (old != null) {
            old.removed();
        }
        //return old;
        return this;
    }

    public <T> SpeedmentBuilder setManager(Class<T> managedClass, Manager<T> manager) {
        entityManagerMap.put(managedClass, manager);
        managerManagerMap.put(manager.getClass(), manager);
        return this;
    }

    @Api(version = 0)
    public Speedment start() {
        // Todo: apply standard component
        return new SpeedmentImpl(configPath, project, plugins, entityManagerMap, managerManagerMap);
    }

    @Api(version = 0)
    public ProjectManager getProjectManager() {
        return get(ProjectManager.class);
    }

    /// Speedment Methods
    @Override
    public boolean isRunning() {
        return false;
    }

    @SuppressWarnings("unchecked") // Must be same type!
    @Override
    public <T extends Component> T get(Class<T> clazz) {
        return (T) plugins.get(clazz);
    }

    @Override
    public Stream<Entry<Class<?>, Component>> components() {
        return plugins.entrySet().stream();
    }

    @Override
    public Optional<Path> getConfigPath() {
        return Optional.ofNullable(configPath);
    }

    @Override
    public Optional<Project> getProject() {
        return Optional.ofNullable(project);
    }
    
    @Override
    public <T> Manager<T> managerOf(Class<T> managedClass) {
        return entityManagerMap.get(managedClass);
    }

    @Override
    public <T> Manager<T> customManager(Class<Manager<T>> managedClass) {
        return managerManagerMap.get(managedClass);
    }

}