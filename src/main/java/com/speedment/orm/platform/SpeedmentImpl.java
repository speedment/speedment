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

import com.speedment.orm.config.model.Project;
import com.speedment.orm.core.manager.Manager;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public class SpeedmentImpl implements Speedment {

    private final Path configFile;
    private final Project project;
    private final Map<Class<?>, Component> plugins;
    private final Map<Class<?>, Manager> entityManagerMap;
    private final Map<Class<?>, Manager> managerManagerMap;

    SpeedmentImpl(
        Path configFile,
        Project project,
        Map<Class<?>, Component> plugins,
        Map<Class<?>, Manager> entityManagerMap,
        Map<Class<?>, Manager> managerManagerMap
    ) {
        this.configFile = configFile;
        this.project = project;
        this.plugins = Collections.unmodifiableMap(plugins);
        this.entityManagerMap = Collections.unmodifiableMap(entityManagerMap);
        this.managerManagerMap = Collections.unmodifiableMap(managerManagerMap);
    }

    @Override
    public boolean isRunning() {
        return true;
    }

    @Override
    public Optional<Path> getConfigPath() {
        return Optional.ofNullable(configFile);
    }

    @Override
    public Optional<Project> getProject() {
        return Optional.ofNullable(project);
    }

    @SuppressWarnings("unchecked") // Must be same type!
    @Override
    public <T extends Component> T get(Class<T> clazz) {
        return (T) plugins.get(clazz);
    }

    @Override
    public Stream<Map.Entry<Class<?>, Component>> components() {
        return plugins.entrySet().stream();
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
