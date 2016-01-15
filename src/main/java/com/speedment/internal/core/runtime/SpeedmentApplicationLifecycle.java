/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
import com.speedment.config.db.Dbms;
import com.speedment.config.db.Project;
import com.speedment.Manager;
import com.speedment.Speedment;
import com.speedment.component.Component;
import com.speedment.internal.core.platform.SpeedmentFactory;
import com.speedment.component.ManagerComponent;
import com.speedment.config.Document;
import com.speedment.internal.util.document.DocumentTranscoder;
import com.speedment.config.db.Schema;
import com.speedment.config.db.trait.HasEnabled;
import com.speedment.config.db.trait.HasName;
import com.speedment.internal.core.config.db.immutable.ImmutableProject;
import com.speedment.internal.logging.Logger;
import com.speedment.internal.logging.LoggerManager;
import com.speedment.internal.util.Statistics;
import com.speedment.internal.util.document.DocumentDbUtil;
import static com.speedment.internal.util.document.DocumentUtil.traverseOver;
import com.speedment.internal.util.tuple.Tuple2;
import com.speedment.internal.util.tuple.Tuple3;
import com.speedment.internal.util.tuple.Tuples;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import static java.util.stream.Collectors.toList;
import static com.speedment.internal.util.document.DocumentUtil.relativeName;
import static com.speedment.util.NullUtil.requireNonNulls;
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

    private final List<Tuple3<Class<? extends Document>, String, Consumer<? extends Document>>> withsNamed;
    private final List<Tuple2<Class<? extends Document>, Consumer<? extends Document>>> withsAll;

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
     * @param consumer the consumer to apply
     * @return this instance
     */
    public <C extends Document & HasEnabled> T with(final Class<C> type, final String name, final Consumer<C> consumer) {
        requireNonNulls(type, name, consumer);
        //@SuppressWarnings("unchecked")
        //final Consumer<? extends Document> consumerCasted = (Consumer<? extends Document>)consumer;
        withsNamed.add(Tuples.of(type, name, consumer));
        return self();
    }

    /**
     * Configures a parameter for all ConfigEntity of a certain class. The
     * consumer will then be applied after the configuration has been read and
     * after the System properties have been applied.
     *
     * @param <C> the type of ConfigEntity that is to be used
     * @param type the class of the type of ConfigEntity that is to be used
     * @param consumer the consumer to apply
     * @return this instance
     */
    public <C extends Document & HasEnabled> T with(final Class<C> type, final Consumer<C> consumer) {
        requireNonNulls(type, consumer);
//        @SuppressWarnings("unchecked")
//        final Consumer<? extends Document> consumerCasted = (Consumer<? extends Document>) requireNonNull(consumer);
        withsAll.add(Tuples.of(type, consumer));
        return self();
    }

    /**
     * Configures a password for all dbmses in this project. The password will
     * then be applied after the configuration has been read and after the
     * System properties have been applied.
     *
     * @param password to use for all dbms:es in this project
     * @return this instance
     */
    public T withPassword(final String password) {
        // password nullable
        with(Dbms.class, dbms -> speedment.getPasswordComponent().put(dbms, password));
        return self();
    }

    /**
     * Configures a password for the named dbms. The password will then be
     * applied after the configuration has been read and after the System
     * properties have been applied.
     *
     * @param dbmsName the name of the dbms
     * @param password to use for the named dbms
     * @return this instance
     */
    public T withPassword(final String dbmsName, final String password) {
        // password nullable
        with(Dbms.class, dbmsName, dbms -> speedment.getPasswordComponent().put(dbms, password));
        return self();
    }

    /**
     * Configures a username for all dbmses in this project. The username will
     * then be applied after the configuration has been read and after the
     * System properties have been applied.
     *
     * @param username to use for all dbms:es in this project
     * @return this instance
     */
    public T withUsername(final String username) {
        // username nullable
        with(Dbms.class, d -> d.mutator().setUsername(username));
        return self();
    }

    /**
     * Configures a username for the named dbms. The username will then be
     * applied after the configuration has been read and after the System
     * properties have been applied.
     *
     * @param dbmsName the name of the dbms
     * @param username to use for the named dbms
     * @return this instance
     */
    public T withUsername(final String dbmsName, final String username) {
        // username nullable
        with(Dbms.class, dbmsName, d -> d.mutator().setUsername(username));
        return self();
    }

    /**
     * Configures an IP-address for all dbmses in this project. The IP-address
     * will then be applied after the configuration has been read and after the
     * System properties have been applied.
     *
     * @param ipAddress to use for all dbms:es in this project
     * @return this instance
     */
    public T withIpAddress(final String ipAddress) {
        requireNonNull(ipAddress);
        with(Dbms.class, d -> d.mutator().setIpAddress(ipAddress));
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
        with(Dbms.class, dbmsName, d -> d.mutator().setIpAddress(ipAddress));
        return self();
    }

    /**
     * Configures a port for all dbmses in this project. The port will then be
     * applied after the configuration has been read and after the System
     * properties have been applied.
     *
     * @param port to use for all dbms:es in this project
     * @return this instance
     */
    public T withPort(final int port) {
        with(Dbms.class, d -> d.mutator().setPort(port));
        return self();
    }

    /**
     * Configures a port for the named dbms. The port will then be applied after
     * the configuration has been read and after the System properties have been
     * applied.
     *
     * @param dbmsName the name of the dbms
     * @param port to use for the named dbms
     * @return this instance
     */
    public T withPort(final String dbmsName, final int port) {
        with(Dbms.class, dbmsName, d -> d.mutator().setPort(port));
        return self();
    }

    /**
     * Configures a new schema name for all schemas in this project. The new
     * schema name will then be applied after the configuration has been read
     * and after the System properties have been applied.
     * <p>
     * This method is useful for multi-tenant projects where there are several
     * identical schemas separated only by their names.
     *
     * @param schemaName to use for all schemas this project
     * @return this instance
     */
    public T withSchema(final String schemaName) {
        requireNonNull(schemaName);
        with(Schema.class, s -> s.mutator().setName(schemaName));
        return self();
    }

    /**
     * Configures a new schema name for the named old schema name. The new
     * schema name will then be applied after the configuration has been read
     * and after the System properties have been applied.
     * <p>
     * This method is useful for multi-tenant projects where there are several
     * identical schemas separated only by their names.
     *
     * @param oldSchemaName the current name of a schema
     * @param schemaName to use for the named schema
     * @return this instance
     */
    public T withSchema(final String oldSchemaName, final String schemaName) {
        requireNonNulls(oldSchemaName, schemaName);
        with(Schema.class, oldSchemaName, s -> s.mutator().setName(schemaName));
        return self();
    }

//    /**
//     * Adds a (and replaces any existing) {@link Component} to the Speedment
//     * runtime platform.
//     *
//     * @param component to add/replace
//     * @return this instance
//     */
    // NOTE: SINCE Speedment is not available at this time, the method has been removed
//    public T with(final Component component) {
//        speedment.put(requireNonNull(component));
//        return self();
//    }
    /**
     * Adds a (and replaces any existing) {@link Component} to the Speedment
     * runtime platform by applying the provided component mapper with the
     * internal Speedment instance.
     *
     * @param componentMapper to use when adding/replacing a component
     * @return this instance
     */
    public T with(final Function<Speedment, Component> componentMapper) {
        speedment.put(requireNonNull(componentMapper).apply(speedment));
        return self();
    }

    @Override
    protected void onInit() {
        forEachManagerInSeparateThread(Manager::initialize);
        forEachComponentInSeparateThread(Component::initialize);
    }

    @Override
    protected void onResolve() {
        forEachManagerInSeparateThread(Manager::resolve);
        forEachComponentInSeparateThread(Component::resolve);
    }

    @Override
    protected void onStart() {
        forEachManagerInSeparateThread(Manager::start);
        forEachComponentInSeparateThread(Component::start);
    }

    @Override
    protected void onStop() {
        forEachManagerInSeparateThread(Manager::stop);
        forEachComponentInSeparateThread(Component::stop);
    }

    /**
     * Builds up the complete Project meta data tree.
     */
    protected void loadAndSetProject() {
        final Optional<Path> oPath = getConfigPath();
        final Project project;
        if (oPath.isPresent()) {
            project = DocumentTranscoder.load(oPath.get());
        } else {
            project = DocumentTranscoder.load(getSpeedmentApplicationMetadata().getMetadata());
        }

        // Apply overridden values from the system properties (if any)
        //final Function<Document, Stream<Node>> traverser = n -> n.asParent().map(p -> p.stream()).orElse(Stream.empty()).map(c -> (Node) c);

        /*

            DocumentUtil.traverseOver(project)
//            Trees.traverse(project, traverser, Trees.TraversalOrder.DEPTH_FIRST_PRE)
                    .forEach((Document node) -> {
                        final Class<?> clazz = node.getClass();
                        node.stream().filterValue(o->List.class.isInstance(o))
                                
                        final String path = "speedment.project." + relativeName(node, Project.class) + "." + beanPropertyName(method);
                        
                        MethodsParser.streamOfExternalSetters(clazz).forEach(method -> {
                            final String path = "speedment.project." + node.getRelativeName(Project.class) + "." + beanPropertyName(method);
                            Optional.ofNullable(System.getProperty(path)).ifPresent(propString -> {
                                final External external = MethodsParser.getExternalFor(method, clazz);
                                final Class<?> targetJavaType = external.type();
                                final Object val = speedment
                                        .getJavaTypeMapperComponent()
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

         */
        // Apply overidden item (if any) for all ConfigEntities of a given class
        withsAll.forEach(t2 -> {
            final Class<? extends Document> clazz = t2.get0();
            @SuppressWarnings("unchecked")
            final Consumer<Document> consumer = (Consumer<Document>) t2.get1();
            System.out.println("Class "+clazz.getSimpleName());
            DocumentDbUtil.traverseOver(project)
                    .peek(System.out::println)
                    .filter(clazz::isInstance)
                    .map(Document.class::cast)
                    .forEachOrdered(consumer::accept);
        });

        // Apply a named overidden item (if any) for all ConfigEntities of a given class
        withsNamed.forEach(t3 -> {
            final Class<? extends Document> clazz = t3.get0();
            final String name = t3.get1();

            @SuppressWarnings("unchecked")
            final Consumer<Document> consumer = (Consumer<Document>) t3.get2();

            DocumentDbUtil.traverseOver(project)
                    .filter(clazz::isInstance)
                    .filter(HasName.class::isInstance)
                    .map(d -> (Document & HasName) d)
                    .filter(c -> name.equals(relativeName(c, Project.class)))
                    .forEachOrdered(consumer::accept);
        });

        speedment.getProjectComponent().setProject(project);

    }

    protected <ENTITY> void applyAndPut(Function<Speedment, Manager<ENTITY>> constructor) {
        put(constructor.apply(speedment));
    }
    
    protected <ENTITY> void put(Manager<ENTITY> manager) {
        speedment.getManagerComponent().put(manager);
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
        Statistics.onNodeStarted();

        LOGGER.info(SpeedmentVersion.getWelcomeMessage());
        if (!SpeedmentVersion.isProductionMode()) {
            LOGGER.warn("This version is NOT INTEDNED FOR PRODUCTION USE!");
        }
        return super.start();
    }

    public Speedment build() {
        if (!isStarted()) {
            start();
        }
        // Replace the metadata model with an immutable version (potentially much faster)
        final Project project = speedment.getProjectComponent().getProject();
        final Project immutableProject = ImmutableProject.wrap(project);
        
        
        final String json = DocumentTranscoder.save(immutableProject);
        
        speedment.getProjectComponent().setProject(immutableProject);
        //
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
        final ManagerComponent mc = speedment.getManagerComponent();
        final List<Thread> threads = mc.stream()
                .map(mgr -> new Thread(() -> managerConsumer.accept(mgr), mgr.getTable().getName()))
                .collect(toList());
        threads.forEach(Thread::start);
        threads.forEach(SpeedmentApplicationLifecycle::join);
    }

    protected void forEachComponentInSeparateThread(Consumer<Component> componentConsumer) {
        requireNonNull(componentConsumer);
        final List<Thread> threads = speedment.components()
                .map(comp -> new Thread(() -> componentConsumer.accept(comp), comp.getTitle()))
                .collect(toList());
        threads.forEach(Thread::start);
        threads.forEach(SpeedmentApplicationLifecycle::join);
    }

    private static void join(Thread t) {
        try {
            t.join();
        } catch (InterruptedException ex) {
            // ignore
        }
    }

    private static <T> List<T> newList() {
        return new ArrayList<>();
    }
}
