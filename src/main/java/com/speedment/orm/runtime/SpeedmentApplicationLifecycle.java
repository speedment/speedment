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
package com.speedment.orm.runtime;

import com.speedment.orm.config.model.Project;
import com.speedment.orm.config.model.impl.utils.GroovyParser;
import com.speedment.orm.core.Buildable;
import com.speedment.orm.core.lifecycle.AbstractLifecycle;
import com.speedment.orm.core.manager.Manager;
import com.speedment.orm.platform.Platform;
import com.speedment.orm.platform.component.ManagerComponent;
import com.speedment.orm.platform.component.ProjectComponent;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author pemi
 * @param <T>
 */
public abstract class SpeedmentApplicationLifecycle<T extends SpeedmentApplicationLifecycle<T>> extends AbstractLifecycle<T> {

    private String configFileName;
    private String configDirectoryName;

    public SpeedmentApplicationLifecycle() {
        super();
        configFileName = "speedment.groovy";
        configDirectoryName = "";
    }

    @Override
    protected void onInit() {
        forEachManagerInSeparateThread(mgr -> mgr.initialize());
    }

    @Override
    protected void onResolve() {
        forEachManagerInSeparateThread(mgr -> mgr.resolve());
    }

    @Override
    protected void onStart() {
        forEachManagerInSeparateThread(mgr -> mgr.start());
    }

    @Override
    protected void onStop() {
        forEachManagerInSeparateThread(mgr -> mgr.stop());
    }

    protected void loadAndSetProject() {
        try {
            final Project p = GroovyParser.projectFromGroovy(getPath());
            Platform.get().get(ProjectComponent.class).setProject(p);
        } catch (IOException ioe) {
            throw new RuntimeException("Failed to read config file", ioe);
        }
    }

    protected <PK, E, B extends Buildable<E>> void put(Manager<PK, E, B> manager) {
        Platform.get().get(ManagerComponent.class).put(manager);
    }

    protected Path getPath() {
        return Paths.get(getConfigDirectoryName(), getConfigFileName());
    }

    @Override
    public String toString() {
        return super.toString() + ", path=" + getPath().toString();
    }

    /// Bean methods
    public String getConfigFileName() {
        return configFileName;
    }

    public T setConfigFileName(String configFileName) {
        this.configFileName = Objects.requireNonNull(configFileName);
        return thizz();
    }

    public String getConfigDirectoryName() {
        return configDirectoryName;
    }

    public T setConfigDirectoryName(String configDirectoryName) {
        this.configDirectoryName = Objects.requireNonNull(configDirectoryName);
        return thizz();
    }

    // Utilities
    protected void forEachManagerInSeparateThread(Consumer<Manager<?, ?, ?>> managerConsumer) {
        final ManagerComponent mc = Platform.get().get(ManagerComponent.class);
        final List<Thread> threads = mc.stream().map(mgr -> new Thread(() -> managerConsumer.accept(mgr))).collect(toList());
        threads.forEach(t -> t.start());
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException ex) {
                // ignore
            }
        });

    }

}
