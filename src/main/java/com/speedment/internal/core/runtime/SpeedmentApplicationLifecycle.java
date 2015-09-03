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
package com.speedment.internal.core.runtime;

import com.speedment.SpeedmentVersion;
import com.speedment.config.Dbms;
import com.speedment.annotation.External;
import com.speedment.config.Project;
import com.speedment.config.Node;
import com.speedment.internal.core.config.utils.GroovyParser;
import com.speedment.internal.core.config.utils.MethodsParser;
import com.speedment.config.aspects.Enableable;
import com.speedment.Manager;
import com.speedment.Speedment;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.core.platform.SpeedmentFactory;
import com.speedment.internal.core.platform.component.JavaTypeMapperComponent;
import com.speedment.internal.core.platform.component.ManagerComponent;
import com.speedment.internal.core.platform.component.ProjectComponent;
import com.speedment.internal.logging.Logger;
import com.speedment.internal.logging.LoggerManager;
import com.speedment.internal.util.Statistics;
import static com.speedment.internal.util.Beans.beanPropertyName;
import com.speedment.internal.util.Trees;
import com.speedment.internal.util.analytics.AnalyticsUtil;
import static com.speedment.internal.util.analytics.FocusPoint.APP_STARTED;
import com.speedment.internal.util.tuple.Tuple2;
import com.speedment.internal.util.tuple.Tuple3;
import com.speedment.internal.util.tuple.Tuples;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import static java.util.Objects.requireNonNull;

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

    private final List<Tuple3<Class<? extends Node>, String, Consumer<? extends Node>>> withsNamed;
    private final List<Tuple2<Class<? extends Node>, Consumer<Node>>> withsAll;

    private ApplicationMetadata speedmentApplicationMetadata;
    private Path configPath;

    protected final Speedment speedment;

    public SpeedmentApplicationLifecycle() {
        super();
        speedment = SpeedmentFactory.newSpeedmentInstance();
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
    public <C extends Node & Enableable> T with(final Class<C> type, final String name, final Consumer<C> consumer) {
        requireNonNull(type);
        requireNonNull(name);
        requireNonNull(consumer);
        @SuppressWarnings("unchecked")
        final Consumer<Node> consumerCasted = (Consumer<Node>) requireNonNull(consumer);
        withsNamed.add(Tuples.of(requireNonNull(type), requireNonNull(name), consumerCasted));
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
    public <C extends Node & Enableable> T with(final Class<C> type, final Consumer<C> consumer) {
        requireNonNull(type);
        requireNonNull(consumer);
        @SuppressWarnings("unchecked")
        final Consumer<Node> consumerCasted = (Consumer<Node>) requireNonNull(consumer);
        withsAll.add(Tuples.of(requireNonNull(type), consumerCasted));
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
        // password nullable
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
        // password nullable
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
        // username nullable
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
        // username nullable
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
        requireNonNull(ipAddress);
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
        requireNonNull(ipAddress);
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
        forEachManagerInSeparateThread(Manager::initialize);
    }

    @Override
    protected void onResolve() {
        forEachManagerInSeparateThread(Manager::resolve);
    }

    @Override
    protected void onStart() {
        forEachManagerInSeparateThread(Manager::start);
    }

    @Override
    protected void onStop() {
        forEachManagerInSeparateThread(Manager::stop);
    }

    /**
     * Builds up the complete Project meta data tree.
     */
    protected void loadAndSetProject() {
        try {
            final Optional<Path> oPath = getConfigPath();
            final Project project;
            if (oPath.isPresent()) {
                project = GroovyParser.projectFromGroovy(speedment, oPath.get());
            } else {
                project = GroovyParser.projectFromGroovy(speedment, getSpeedmentApplicationMetadata().getMetadata());
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
                            final Object val = speedment
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
                final Class<? extends Node> clazz = t2.get0();
                final Consumer<Node> consumer = t2.get1();
                project.traverse()
                    .filter(c -> clazz.isAssignableFrom(c.getClass()))
                    .map(Node.class::cast)
                    .forEachOrdered(consumer::accept);
            });

            // Apply a named overidden item (if any) for all ConfigEntities of a given class
            withsNamed.forEach(t3 -> {
                final Class<? extends Node> clazz = t3.get0();
                final String name = t3.get1();

                @SuppressWarnings("unchecked")
                final Consumer<Node> consumer = (Consumer<Node>) t3.get2();

                project.traverse()
                    .filter(c -> clazz.isAssignableFrom(c.getClass()))
                    .map(Node.class::cast)
                    .filter(c -> name.equals(c.getRelativeName(Project.class)))
                    .forEachOrdered(consumer::accept);
            });

            speedment.get(ProjectComponent.class).setProject(project);

        } catch (IOException ioe) {
            throw new SpeedmentException("Failed to read config file", ioe);
        }
    }

    protected <ENTITY> void put(Manager<ENTITY> manager) {
        speedment.get(ManagerComponent.class).put(manager);
    }

    /**
     * Sets the SpeedmentApplicationMetadata. The meta data describes the layout
     * of the Project (i.e. Dbms:es, Schemas, Tables, Columns etc)
     *
     * @param speedmentApplicationMetadata to use
     * @return this instance
     */
    public T setSpeedmentApplicationMetadata(ApplicationMetadata speedmentApplicationMetadata) {
        this.speedmentApplicationMetadata = requireNonNull(speedmentApplicationMetadata);
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
        // configPath nullable
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

        LOGGER.info(
            SpeedmentVersion.getImplementationTitle()
            + " (" + SpeedmentVersion.getImplementationVersion() + ")"
            + " by " + SpeedmentVersion.getImplementationVendor() + " started."
            + " API version is " + SpeedmentVersion.getSpecificationVersion()
        );
        if (!SpeedmentVersion.isProductionMode()) {
            LOGGER.warn("This version is NOT INTEDNED FOR PRODUCTION USE!");
        }
        return super.start();
    }

    public Speedment build() {
        if (!isStarted()) {
            start();
        }
        return speedment;
    }

    @Override
    public String toString() {
        return super.toString() + ", path=" + getConfigPath().toString();
    }

    // Utilities
    /**
     * Support method to do something with all managers i separate threads.
     * Useful if, for example, initialization is lengthy.
     *
     * @param managerConsumer to accept all Managers
     */
    protected void forEachManagerInSeparateThread(Consumer<Manager<?>> managerConsumer) {
        requireNonNull(managerConsumer);
        final ManagerComponent mc = speedment.get(ManagerComponent.class);
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
