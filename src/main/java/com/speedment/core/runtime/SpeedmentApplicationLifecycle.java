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
package com.speedment.core.runtime;

import com.speedment.core.config.model.External;
import com.speedment.core.config.model.Project;
import com.speedment.core.config.model.aspects.Node;
import com.speedment.core.config.model.impl.utils.GroovyParser;
import com.speedment.core.config.model.impl.utils.MethodsParser;
import com.speedment.core.core.Buildable;
import com.speedment.core.lifecycle.AbstractLifecycle;
import com.speedment.core.manager.Manager;
import com.speedment.core.exception.SpeedmentException;
import com.speedment.core.platform.Platform;
import com.speedment.core.platform.component.ManagerComponent;
import com.speedment.core.platform.component.ProjectComponent;
import com.speedment.core.runtime.typemapping.StandardJavaTypeMapping;
import static com.speedment.util.Beans.beanPropertyName;
import com.speedment.util.Trees;
import com.speedment.util.analytics.AnalyticsUtil;
import static com.speedment.util.analytics.FocusPoint.APP_STARTED;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author pemi
 * @param <T> The type of the Lifecycle
 */
public abstract class SpeedmentApplicationLifecycle<T extends SpeedmentApplicationLifecycle<T>> extends AbstractLifecycle<T> {

    private final static Logger LOGGER = LogManager.getLogger(SpeedmentApplicationLifecycle.class);

    private String dbmsPassword;
    private final Map<String, String> dbmsPasswords;
    private ApplicationMetadata speedmentApplicationMetadata;
    private Path configPath;

    public SpeedmentApplicationLifecycle() {
        super();
        configPath = null;
        dbmsPasswords = new ConcurrentHashMap<>();
    }

    /**
     * Configures a password for all dbmses in this project. The password will
     * then be applied after the configuration has been read and after the
     * System properties has been applied but before
     * configureDbmsPassword(dbmsName, password) is called.
     *
     * @param password the password to use for all dbms:es in this project.
     * @return this instance
     */
    public T withPassword(final String password) {
        dbmsPassword = password;
        return self();
    }

    /**
     * Configures a password for the named dbms. The password will then be
     * applied after the configuration has been read and after the System
     * properties has been applied.
     *
     * @param dbmsName the name of the dbms
     * @param password the password to use for the named dbms.
     * @return this instance
     */
    public T withPassword(final String dbmsName, final String password) {
        dbmsPasswords.put(dbmsName, password);
        return self();
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
            final Optional<Path> oPath = getConfigPath();
            final Project project;
            if (oPath.isPresent()) {
                project = GroovyParser.projectFromGroovy(oPath.get());
            } else {
                project = GroovyParser.projectFromGroovy(getSpeedmentApplicationMetadata().getMetadata());
            }

            // Apply overridden values from the system properties (if any)
            final Function<Node, Stream<Node>> traverser = n -> n.asParent().map(p -> p.stream()).orElse(Stream.empty()).map(c -> (Node) c);
            Trees.traverse(project, traverser, Trees.TraversalOrder.DEPTH_FIRST_PRE)
                .forEach((Node node) -> {
                    final Class<?> clazz = node.getClass();
                    MethodsParser.streamOfExternalSetters(clazz).forEach(
                        method -> {
                            final String path = "speedment.project." + node.getRelativeName(Project.class) + "." + beanPropertyName(method);
                            Optional.ofNullable(System.getProperty(path)).ifPresent(propString -> {
                                final External external = MethodsParser.getExternalFor(method, clazz);
                                final Class<?> targetJavaType = external.type();
                                final Object val = StandardJavaTypeMapping.parse(targetJavaType, propString);
                                try {
                                    method.invoke(node, val);
                                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                                    throw new SpeedmentException("Unable to invoke " + method + " for " + node + " with argument " + val + " (" + propString + ")");
                                }
                            });
                        }
                    );
                });

            // Apply overidden passwords (if any) for all dbms:es
            Optional.ofNullable(dbmsPassword).ifPresent(pwd -> {
                project.stream().forEach(dbms -> dbms.setPassword(pwd));
            });

            // Apply overidden passwords (if any) for any named dbms
            project.stream().forEach(dbms -> {
                Optional.ofNullable(dbmsPasswords.get(dbms.getName())).ifPresent(pwd -> dbms.setPassword(pwd));
            });

            Platform.get().get(ProjectComponent.class).setProject(project);

        } catch (IOException ioe) {
            throw new SpeedmentException("Failed to read config file", ioe);
        }
    }

    protected <PK, E, B extends Buildable<E>> void put(Manager<PK, E, B> manager) {
        Platform.get().get(ManagerComponent.class).put(manager);
    }

    public T setSpeedmentApplicationMetadata(ApplicationMetadata speedmentApplicationMetadata) {
        this.speedmentApplicationMetadata = speedmentApplicationMetadata;
        return self();
    }

    protected ApplicationMetadata getSpeedmentApplicationMetadata() {
        return speedmentApplicationMetadata;
    }

    public T setConfigPath(Path configPath) {
        this.configPath = configPath;
        return self();
    }

    protected Optional<Path> getConfigPath() {
        return Optional.ofNullable(configPath);
    }

    @Override
    public T start() {
        AnalyticsUtil.notify(APP_STARTED);
        Package package_ = SpeedmentApplicationLifecycle.class.getPackage();
        LOGGER.info(package_.getImplementationTitle() + " " + package_.getImplementationVersion() + " started.");
        LOGGER.warn("This is a technology preview version that is NOT INTEDNED FOR PRODUCTION USE!");
        return super.start();
    }

    @Override
    public String toString() {
        return super.toString() + ", path=" + getConfigPath().toString();
    }

//    /// Bean methods
//    public String getConfigFileName() {
//        return configFileName;
//    }
//
//    public T setConfigFileName(String configFileName) {
//        this.configFileName = Objects.requireNonNull(configFileName);
//        return thizz();
//    }
//
//    public String getConfigDirectoryName() {
//        return configDirectoryName;
//    }
//
//    public T setConfigDirectoryName(String configDirectoryName) {
//        this.configDirectoryName = Objects.requireNonNull(configDirectoryName);
//        return thizz();
//    }
    // Utilities
    protected void forEachManagerInSeparateThread(Consumer<Manager<?, ?, ?>> managerConsumer) {
        final ManagerComponent mc = Platform.get().get(ManagerComponent.class);
        final List<Thread> threads = mc.stream().map(mgr -> new Thread(() -> managerConsumer.accept(mgr), mgr.getTable().getName())).collect(toList());
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
