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

import com.speedment.core.config.model.ConfigEntity;
import com.speedment.core.config.model.Dbms;
import com.speedment.core.config.model.External;
import com.speedment.core.config.model.Project;
import com.speedment.core.config.model.aspects.Node;
import com.speedment.core.config.model.impl.utils.GroovyParser;
import com.speedment.core.config.model.impl.utils.MethodsParser;
import com.speedment.core.Buildable;
import com.speedment.core.lifecycle.AbstractLifecycle;
import com.speedment.core.manager.Manager;
import com.speedment.core.exception.SpeedmentException;
import com.speedment.core.platform.Platform;
import com.speedment.core.platform.component.JavaTypeMapperComponent;
import com.speedment.core.platform.component.ManagerComponent;
import com.speedment.core.platform.component.ProjectComponent;
import com.speedment.core.runtime.typemapping.StandardJavaTypeMapping;
import com.speedment.logging.Logger;
import com.speedment.logging.LoggerManager;
import com.speedment.stat.Statistics;
import static com.speedment.util.Beans.beanPropertyName;
import com.speedment.util.Trees;
import com.speedment.util.analytics.AnalyticsUtil;
import static com.speedment.util.analytics.FocusPoint.APP_STARTED;
import com.speedment.util.tuple.Tuple2;
import com.speedment.util.tuple.Tuple3;
import com.speedment.util.tuple.Tuples;
import com.speedment.util.version.SpeedmentVersion;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;

/**
 * This Class provides the foundation for a SpeedmentApplication and is needed
 * when initializing the Speedment framework. Specific SPeedment applications
 * can inherit from this class.
 *
 * @param <T> The (self) type of the SpeedmentApplicationLifecycle
 * @author pemi
 * @since 2.0
 *
 */
public abstract class SpeedmentApplicationLifecycle<T extends SpeedmentApplicationLifecycle<T>> extends AbstractLifecycle<T> {

    private final static Logger LOGGER = LoggerManager.getLogger(SpeedmentApplicationLifecycle.class);

    private final List<Tuple3<Class<? extends ConfigEntity>, String, Consumer<ConfigEntity>>> withsNamed;
    private final List<Tuple2<Class<? extends ConfigEntity>, Consumer<ConfigEntity>>> withsAll;

    private ApplicationMetadata speedmentApplicationMetadata;
    private Path configPath;

    public SpeedmentApplicationLifecycle() {
        super();
        configPath = null;
        withsNamed = newList();
        withsAll = newList();
    }

    /**
     * Configures a parameter for the named ConfigEntity of a certain class. The
     * consumer will then be applied after the configuration has been read and
     * after the System properties have been applied.
     *
     * @param <C> the type of ConfigEntity that is to be used
     * @param type the class of the type of ConfigEntity that is to be used
     * @param name the fully qualified name of the ConfigEntity.
     * @param consumer the consumer to apply.
     * @return this instance
     */
    public <C extends ConfigEntity> T with(final Class<C> type, final String name, final Consumer<C> consumer) {
        @SuppressWarnings("unchecked")
        final Consumer<ConfigEntity> consumerCasted = (Consumer<ConfigEntity>) Objects.requireNonNull(consumer);
        withsNamed.add(Tuples.of(Objects.requireNonNull(type), Objects.requireNonNull(name), consumerCasted));
        return self();
    }

    /**
     * Configures a parameter for all ConfigEntity of a certain class. The
     * consumer will then be applied after the configuration has been read and
     * after the System properties have been applied.
     *
     * @param <C> the type of ConfigEntity that is to be used
     * @param type the class of the type of ConfigEntity that is to be used
     * @param consumer the consumer to apply.
     * @return this instance
     */
    public <C extends ConfigEntity> T with(final Class<C> type, final Consumer<C> consumer) {
        @SuppressWarnings("unchecked")
        final Consumer<ConfigEntity> consumerCasted = (Consumer<ConfigEntity>) Objects.requireNonNull(consumer);
        withsAll.add(Tuples.of(Objects.requireNonNull(type), consumerCasted));
        return self();
    }

    /**
     * Configures a password for all dbmses in this project. The password will
     * then be applied after the configuration has been read and after the
     * System properties have been applied.
     *
     * @param password to use for all dbms:es in this project.
     * @return this instance
     */
    public T withPassword(final String password) {
        with(Dbms.class, d -> d.setPassword(password));
        return self();
    }

    /**
     * Configures a password for the named dbms. The password will then be
     * applied after the configuration has been read and after the System
     * properties have been applied.
     *
     * @param dbmsName the name of the dbms
     * @param password to use for the named dbms.
     * @return this instance
     */
    public T withPassword(final String dbmsName, final String password) {
        with(Dbms.class, dbmsName, d -> d.setPassword(password));
        return self();
    }

    /**
     * Configures a username for all dbmses in this project. The username will
     * then be applied after the configuration has been read and after the
     * System properties have been applied.
     *
     * @param username to use for all dbms:es in this project.
     * @return this instance
     */
    public T withUsername(final String username) {
        with(Dbms.class, d -> d.setUsername(username));
        return self();
    }

    /**
     * Configures a username for the named dbms. The username will then be
     * applied after the configuration has been read and after the System
     * properties have been applied.
     *
     * @param dbmsName the name of the dbms
     * @param username to use for the named dbms.
     * @return this instance
     */
    public T withUsername(final String dbmsName, final String username) {
        with(Dbms.class, dbmsName, d -> d.setUsername(username));
        return self();
    }

    /**
     * Configures an IP-address for all dbmses in this project. The IP-address
     * will then be applied after the configuration has been read and after the
     * System properties have been applied.
     *
     * @param ipAddress to use for all dbms:es in this project.
     * @return this instance
     */
    public T withIpAddress(final String ipAddress) {
        with(Dbms.class, d -> d.setIpAddress(ipAddress));
        return self();
    }

    /**
     * Configures an IP-address for the named dbms. The IP-address will then be
     * applied after the configuration has been read and after the System
     * properties have been applied.
     *
     * @param dbmsName the name of the dbms
     * @param ipAddress to use for the named dbms.
     * @return this instance
     */
    public T withIpAddress(final String dbmsName, final String ipAddress) {
        with(Dbms.class, dbmsName, d -> d.setIpAddress(ipAddress));
        return self();
    }

    /**
     * Configures a port for all dbmses in this project. The port will then be
     * applied after the configuration has been read and after the System
     * properties have been applied.
     *
     * @param port to use for all dbms:es in this project.
     * @return this instance
     */
    public T withPort(final int port) {
        with(Dbms.class, d -> d.setPort(port));
        return self();
    }

    /**
     * Configures a port for the named dbms. The port will then be applied after
     * the configuration has been read and after the System properties have been
     * applied.
     *
     * @param dbmsName the name of the dbms
     * @param port to use for the named dbms.
     * @return this instance
     */
    public T withPort(final String dbmsName, final int port) {
        with(Dbms.class, dbmsName, d -> d.setPort(port));
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

    /**
     * Builds up the complete Project meta data tree.
     */
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
                    MethodsParser.streamOfExternalSetters(clazz).forEach(method -> {
                        final String path = "speedment.project." + node.getRelativeName(Project.class) + "." + beanPropertyName(method);
                        Optional.ofNullable(System.getProperty(path)).ifPresent(propString -> {
                            final External external = MethodsParser.getExternalFor(method, clazz);
                            final Class<?> targetJavaType = external.type();
                            final Object val = Platform.get()
                            .get(JavaTypeMapperComponent.class)
                            .apply(targetJavaType)
                            .parse(propString);
                            //final Object val = StandardJavaTypeMapping.parse(targetJavaType, propString);
                            try {
                                method.invoke(node, val);
                            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                                throw new SpeedmentException("Unable to invoke " + method + " for " + node + " with argument " + val + " (" + propString + ")");
                            }
                        });
                    }
                    );
                });

            // Apply overidden item (if any) for all ConfigEntities of a given class
            withsAll.forEach(t2 -> {
                final Class<? extends ConfigEntity> clazz = t2.get0();
                final Consumer<ConfigEntity> consumer = t2.get1();
                project.traverse()
                    .filter(c -> clazz.isAssignableFrom(c.getClass()))
                    .map(ConfigEntity.class::cast)
                    .forEachOrdered(n -> consumer.accept(n));
            });

            // Apply a named overidden item (if any) for all ConfigEntities of a given class
            withsNamed.forEach(t3 -> {
                final Class<? extends ConfigEntity> clazz = t3.get0();
                final String name = t3.get1();
                final Consumer<ConfigEntity> consumer = t3.get2();
                project.traverse()
                    .filter(c -> clazz.isAssignableFrom(c.getClass()))
                    .map(ConfigEntity.class::cast)
                    .filter(c -> name.equals(c.getRelativeName(Project.class)))
                    .forEachOrdered(n -> consumer.accept(n));

//                    Trees.traverse((Child) project, c -> c.asParent()
//                        .map(p -> p.stream())
//                        .orElse(Stream.empty())
//                        .map(n -> (Child<?>) n),
//                        Trees.TraversalOrder.DEPTH_FIRST_PRE
//                    ).filter((Child c) -> clazz.equals(c.getClass()))
//                        .filter((Child c) -> name.equals(c.getRelativeName(Project.class)))
//                        .forEachOrdered((Child c) -> consumer.accept(c));
            });

            Platform.get().get(ProjectComponent.class).setProject(project);

        } catch (IOException ioe) {
            throw new SpeedmentException("Failed to read config file", ioe);
        }
    }

    protected <PK, E, B extends Buildable<E>> void put(Manager<PK, E, B> manager) {
        Platform.get().get(ManagerComponent.class).put(manager);
    }

    /**
     * Sets the SpeedmentApplicationMetadata. The meta data describes the layout
     * of the Project (i.e. Dbms:es, Schemas, Tables, Columns etc)
     *
     * @param speedmentApplicationMetadata to use
     * @return this instance
     */
    public T setSpeedmentApplicationMetadata(ApplicationMetadata speedmentApplicationMetadata) {
        this.speedmentApplicationMetadata = speedmentApplicationMetadata;
        return self();
    }

    protected ApplicationMetadata getSpeedmentApplicationMetadata() {
        return speedmentApplicationMetadata;
    }

    /**
     * Sets the Path from where the meta data configuration shall be retrieved.
     * If no Path at all shall be used, a value of {@code null} can be used.
     *
     * @param configPath to use
     * @return this instance
     */
    public T setConfigPath(Path configPath) {
        this.configPath = configPath;
        return self();
    }

    /**
     * Returns the currently configured Path for meta data, or Optional.empty()
     * of no Path is set.
     *
     * @return the currently configured Path for meta data, or Optional.empty()
     * of no Path is set
     */
    protected Optional<Path> getConfigPath() {
        return Optional.ofNullable(configPath);
    }

    @Override
    public T start() {
        AnalyticsUtil.notify(APP_STARTED);
        Statistics.onNodeStarted();

        Package package_ = SpeedmentApplicationLifecycle.class.getPackage();
        LOGGER.info(
            SpeedmentVersion.getImplementationTitle()
            + " (" + SpeedmentVersion.getImplementationVersion() + ")"
            + " by " + SpeedmentVersion.getImplementationVendor() + " started."
        );
        LOGGER.warn("This is a BETA version that is NOT INTEDNED FOR PRODUCTION USE!");
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
    /**
     * Support method to do something with all managers i separate threads.
     * Useful if, for example, initialization is lengthy.
     *
     * @param managerConsumer to accept all Managers
     */
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

    private static <T> List<T> newList() {
        return new ArrayList<>();
    }

}
