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
package com.speedment.runtime.internal.core.runtime;

import com.speedment.runtime.Speedment;
import com.speedment.runtime.SpeedmentVersion;
import static com.speedment.runtime.SpeedmentVersion.getImplementationVendor;
import static com.speedment.runtime.SpeedmentVersion.getSpecificationVersion;
import com.speedment.runtime.component.Component;
import com.speedment.runtime.component.ComponentConstructor;
import com.speedment.runtime.component.DbmsHandlerComponent;
import com.speedment.runtime.component.Lifecyclable;
import com.speedment.runtime.component.ManagerComponent;
import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.db.Dbms;
import com.speedment.runtime.config.db.Project;
import com.speedment.runtime.config.db.Schema;
import com.speedment.runtime.config.db.parameter.DbmsType;
import com.speedment.runtime.config.db.trait.HasEnabled;
import com.speedment.runtime.config.db.trait.HasName;
import com.speedment.runtime.db.DbmsHandler;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.internal.core.config.db.ProjectImpl;
import com.speedment.runtime.internal.core.config.db.immutable.ImmutableProject;
import com.speedment.runtime.internal.logging.Logger;
import com.speedment.runtime.internal.logging.LoggerManager;
import com.speedment.runtime.internal.util.Statistics;
import com.speedment.runtime.internal.util.document.DocumentDbUtil;
import com.speedment.runtime.internal.util.document.DocumentTranscoder;
import static com.speedment.runtime.internal.util.document.DocumentUtil.Name.DATABASE_NAME;
import static com.speedment.runtime.internal.util.document.DocumentUtil.relativeName;
import com.speedment.runtime.manager.Manager;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import com.speedment.runtime.util.tuple.Tuple2;
import com.speedment.runtime.util.tuple.Tuple3;
import com.speedment.runtime.util.tuple.Tuples;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
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
public abstract class SpeedmentApplicationLifecycle<T extends SpeedmentApplicationLifecycle<T>> extends AbstractLifecycle<T> implements Lifecyclable<T> {

    private final static Logger LOGGER = LoggerManager.getLogger(SpeedmentApplicationLifecycle.class);

    private final List<Tuple3<Class<? extends Document>, String, Consumer<? extends Document>>> withsNamed;
    private final List<Tuple2<Class<? extends Document>, Consumer<? extends Document>>> withsAll;
    private boolean checkDatabaseConnectivity;
    private boolean validateRuntimeConfig;
    private final List<Function<Speedment, Manager<?>>> customManagers;

    private ApplicationMetadata speedmentApplicationMetadata;

    protected final Speedment speedment;

    public SpeedmentApplicationLifecycle() {
        super();
        speedment = new SpeedmentImpl();
        withsNamed = newList();
        withsAll = newList();
        checkDatabaseConnectivity = true;
        validateRuntimeConfig = true;
        customManagers = new CopyOnWriteArrayList<>();
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
        withsAll.add(Tuples.of(type, consumer));
        return self();
    }

    /**
     * Configures a password for all dbmses in this project. The password will
     * then be applied after the configuration has been read and after the
     * System properties have been applied.
     * <p>
     * This will not be saved in any configuration files!
     *
     * @param password to use for all dbms:es in this project
     * @return this instance
     */
    public T withPassword(final char[] password) {
        // password nullable
        with(Dbms.class, dbms -> speedment.getPasswordComponent().put(dbms, password));
        return self();
    }

    /**
     * Configures a password for the named dbms. The password will then be
     * applied after the configuration has been read and after the System
     * properties have been applied.
     * <p>
     * This will not be saved in any configuration files!
     *
     * @param dbmsName the name of the dbms
     * @param password to use for the named dbms
     * @return this instance
     */
    public T withPassword(final String dbmsName, final char[] password) {
        // password nullable
        with(Dbms.class, dbmsName, dbms -> speedment.getPasswordComponent().put(dbms, password));
        return self();
    }

    /**
     * Configures a password for all dbmses in this project. The password will
     * then be applied after the configuration has been read and after the
     * System properties have been applied.
     * <p>
     * This will not be saved in any configuration files!
     *
     * @param password to use for all dbms:es in this project
     * @return this instance
     */
    public T withPassword(final String password) {
        // password nullable
        return withPassword(stringToCharArray(password));
    }

    /**
     * Configures a password for the named dbms. The password will then be
     * applied after the configuration has been read and after the System
     * properties have been applied.
     * <p>
     * This will not be saved in any configuration files!
     *
     * @param dbmsName the name of the dbms
     * @param password to use for the named dbms
     * @return this instance
     */
    public T withPassword(final String dbmsName, final String password) {
        // password nullable
        return withPassword(dbmsName, stringToCharArray(password));
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

    /**
     * Configures a connection URL for all dbmses in this project. The new
     * connection URL will then be applied after the configuration has been read
     * and after the System properties have been applied. If the connectionUrl
     * is set to {@code null}, the connection URL will be calculated using the
     * dbmses' default connection URL generator (e.g. using ipAddress, port,
     * etc).
     * <p>
     *
     * @param connectionUrl to use for all dbms this project or null
     * @return this instance
     */
    public T withConnectionUrl(final String connectionUrl) {
        with(Dbms.class, d -> d.mutator().setConnectionUrl(connectionUrl));
        return self();
    }

    /**
     * Configures a connection URL for the named dbms in this project. The new
     * connection URL will then be applied after the configuration has been read
     * and after the System properties have been applied. If the connectionUrl
     * is set to {@code null}, the connection URL will be calculated using the
     * dbmses' default connection URL generator (e.g. using ipAddress, port,
     * etc).
     * <p>
     *
     * @param dbmsName the name of the dbms
     * @param connectionUrl to use for the named dbms or null
     * @return this instance
     */
    public T withConnectionUrl(final String dbmsName, final String connectionUrl) {
        requireNonNull(dbmsName);
        with(Dbms.class, dbmsName, s -> s.mutator().setName(connectionUrl));
        return self();
    }

    /**
     * Adds a (and replaces any existing) {@link Component} to the Speedment
     * runtime platform by applying the provided component constructor with the
     * internal Speedment instance.
     *
     * @param <C> the component constructor type
     * @param componentConstructor to use when adding/replacing a component
     * @return this instance
     */
    public <C extends Component> T with(final ComponentConstructor<C> componentConstructor) {
        speedment.put(requireNonNull(componentConstructor).create(speedment));
        return self();
    }

    /**
     * Adds a (and replaces any existing) {@link Component} to the Speedment
     * runtime platform by first creating a new instance of the provided
     * component constructor class and then applying the component constructor
     * with the internal Speedment instance.
     *
     * @param <C> the component constructor type
     * @param componentConstructorClass to use when adding/replacing a component
     * @return this instance
     */
    public <C extends Component> T with(final Class<ComponentConstructor<C>> componentConstructorClass) {
        try {
            final ComponentConstructor<C> cc = componentConstructorClass.newInstance();
            return with(cc);
        } catch (IllegalAccessException | InstantiationException e) {

        }
        throw new SpeedmentException("Unable to make a new instance of the " + componentConstructorClass + " class");
    }

    /**
     * Sets if an initial database check shall be performed upon build(). The
     * default value is <code>true</code>
     *
     * @param <C> the component type
     * @param checkDatabaseConnectivity if an initial database check shall be
     * performed
     * @return this instance
     */
    public <C extends Component> T withCheckDatabaseConnectivity(final boolean checkDatabaseConnectivity) {
        this.checkDatabaseConnectivity = checkDatabaseConnectivity;
        return self();
    }

    /**
     * Sets if an initial validation if the configuration shall be performed
     * upon build(). The default value is <code>true</code>
     *
     * @param <C> the component type
     * @param validateRuntimeConfig if the configuration shall be performed
     * @return this instance
     */
    public <C extends Component> T withValidateRuntimeConfig(final boolean validateRuntimeConfig) {
        this.validateRuntimeConfig = validateRuntimeConfig;
        return self();
    }

    /**
     * Adds a custom manager constructor, being called before build to replace
     * an existing manager.
     *
     * @param <C> the component type
     * @param constructor to add
     * @return this instance
     */
    public <C extends Component> T withManager(final Function<Speedment, Manager<?>> constructor) {
        customManagers.add(constructor);
        return self();
    }

    @Override
    public void onInitialize() {
        super.onInitialize();
        forEachManagerInSeparateThread(Manager::initialize);
        forEachComponentInSeparateThread(Component::initialize);
        // In case a component added another component
        bringRemainingUpTo(State.INIITIALIZED);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        bringRemainingUpTo(State.INIITIALIZED); // We need to double check since an inheriting class may have added things
        forEachManagerInSeparateThread(Manager::load);
        forEachComponentInSeparateThread(Component::load);
        // In case a component added another component
        bringRemainingUpTo(State.LOADED);
    }

    @Override
    public void onResolve() {
        super.onResolve();
        bringRemainingUpTo(State.LOADED); // We need to double check since an inheriting class may have added things
        forEachManagerInSeparateThread(Manager::resolve);
        forEachComponentInSeparateThread(Component::resolve);
        // In case a component added another component
        bringRemainingUpTo(State.RESOLVED);
    }

    @Override
    public void onStart() {
        super.onStart();
        bringRemainingUpTo(State.RESOLVED); // We need to double check since an inheriting class may have added things
        if (validateRuntimeConfig) {
            validateRuntimeConfig();
        }
        makeConfigImmutable();

        if (checkDatabaseConnectivity) {
            checkDatabaseConnectivity();
        }
        forEachManagerInSeparateThread(Manager::start);
        forEachComponentInSeparateThread(Component::start);
        // In case a component added another component
        bringRemainingUpTo(State.STARTED);
        Statistics.onNodeStarted();

        final String title = speedment.getInfoComponent().title();
        final String subTitle = speedment.getInfoComponent().subtitle();
        final String version = speedment.getInfoComponent().version();

        final String msg = title + " (" + subTitle + ") version " + version + " by " + getImplementationVendor() + " started."
            + " API version is " + getSpecificationVersion();

        printWelcomeMessage();
    }

    protected void printWelcomeMessage() {

        try {
            final Package package_ = Runtime.class.getPackage();
            final String javaMsg = package_.getSpecificationTitle()
                + " " + package_.getSpecificationVersion()
                + " by " + package_.getSpecificationVendor()
                + ". Implementation "
                + package_.getImplementationVendor()
                + " " + package_.getImplementationVersion()
                + " by " + package_.getImplementationVendor();
            LOGGER.info(javaMsg);
            if (package_.getImplementationVersion().compareTo("1.8.0_40") < 0) {
                LOGGER.warn("The current Java version is outdated. Please upgrate to a more recent Java version.");
            }
        } catch (Exception e) {
            LOGGER.info("Unknown Java version.");
        }

        final String title = speedment.getInfoComponent().title();
        final String subTitle = speedment.getInfoComponent().subtitle();
        final String version = speedment.getInfoComponent().version();

        final String speedmentMsg = title + " (" + subTitle + ") version " + version + " by " + getImplementationVendor() + " started."
            + " API version is " + getSpecificationVersion();
        LOGGER.info(speedmentMsg);
        if (!SpeedmentVersion.isProductionMode()) {
            LOGGER.warn("This version is NOT INTEDNED FOR PRODUCTION USE!");
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        forEachManagerInSeparateThread(Manager::stop);
        forEachComponentInSeparateThread(Component::stop);
    }

    /**
     * Builds up the complete Project meta data tree.
     */
    protected void loadAndSetProject() {
        final ApplicationMetadata meta = getSpeedmentApplicationMetadata();
        final Project project;
        
        if (meta != null) {
            project = DocumentTranscoder.load(meta.getMetadata());
        } else {
            final Map<String, Object> data = new ConcurrentHashMap<>();
            data.put(HasName.NAME, "Project");
            project = new ProjectImpl(data);
        }

        // Apply overidden item (if any) for all ConfigEntities of a given class
        withsAll.forEach(t2 -> {
            final Class<? extends Document> clazz = t2.get0();
            @SuppressWarnings("unchecked")
            final Consumer<Document> consumer = (Consumer<Document>) t2.get1();
            DocumentDbUtil.traverseOver(project)
                .filter(clazz::isInstance)
                .map(Document.class::cast)
                .forEachOrdered(consumer::accept);
        });

        // Apply a named overidden item (if any) for all Entities of a given class
        withsNamed.forEach(t3 -> {
            final Class<? extends Document> clazz = t3.get0();
            final String name = t3.get1();

            @SuppressWarnings("unchecked")
            final Consumer<Document> consumer = (Consumer<Document>) t3.get2();

            DocumentDbUtil.traverseOver(project)
                .filter(clazz::isInstance)
                .filter(HasName.class::isInstance)
                .map(HasName.class::cast)
                .filter(c -> name.equals(relativeName(c, Project.class, DATABASE_NAME)))
                .forEachOrdered(consumer::accept);
        });

        speedment.getProjectComponent().setProject(project);
    }

    public void loadCustomManagers() {
        customManagers.forEach(mc -> {
            final Manager<?> manager = mc.apply(speedment);
            speedment.getManagerComponent().put(manager);
        });
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

    public Speedment build() {
        if (!isStarted()) {
            start();
        }

        return speedment;
    }

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
            .map(mgr -> new Thread(()
                -> managerConsumer.accept(mgr),
                mgr.getTable().getName()
            ))
            .collect(toList());
        threads.forEach(Thread::start);
        threads.forEach(SpeedmentApplicationLifecycle::join);
    }

    protected void forEachComponentInSeparateThread(Consumer<Component> componentConsumer) {
        requireNonNull(componentConsumer);
        final List<Thread> threads = speedment.components()
            .map(comp -> new Thread( // TODO: Change to ExecutorService
                () -> componentConsumer.accept(comp),
                comp.asSoftware().getName()
            ))
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

    protected void validateRuntimeConfig() {
        final Project project = speedment.getProjectComponent().getProject();
        if (project == null) {
            throw new SpeedmentException("No project defined");
        }

        project.dbmses().forEach(d -> {
            final String typeName = d.getTypeName();
            final Optional<DbmsType> oDbmsType = speedment.getDbmsHandlerComponent().findByName(typeName);
            if (!oDbmsType.isPresent()) {
                throw new SpeedmentException("The database type " + typeName + " is not registered with the " + DbmsHandlerComponent.class.getSimpleName());
            }
            final String driverName = oDbmsType.get().getDriverName();
            try {
                // Make sure the driver is loaded. This is a must for some JavaEE servers.
                Class.forName(driverName);
            } catch (ClassNotFoundException cnfe) {
                LOGGER.error(cnfe, "The database driver class " + driverName + " is not available. Make sure to include it in your class path (e.g. in the POM file)");
            }
        });

    }

    protected void makeConfigImmutable() {
        // If a project has been set for the lifecycle, wrap it in an immutable
        // for performance reasons.
        final Project project = speedment.getProjectComponent().getProject();
        if (project != null) {
            final Project immutableProject = ImmutableProject.wrap(project);
            speedment.getProjectComponent().setProject(immutableProject);
        }
    }

    protected void checkDatabaseConnectivity() {
        final Project project = speedment.getProjectComponent().getProject();
        project.dbmses().forEachOrdered(dbms -> {
            final DbmsHandler dbmsHandler = speedment.getDbmsHandlerComponent().get(dbms);
            try {
                LOGGER.info(dbmsHandler.getDbmsInfoString());
            } catch (Exception e) {
                LOGGER.error(e, "Unable to connect to dbms " + dbms.toString());
            }
        });
    }

    private char[] stringToCharArray(String s) {
        return s == null ? null : s.toCharArray();
    }

    private void bringRemainingUpTo(State state) {
        final ManagerComponent mc = speedment.getManagerComponent();
        List<Component> components;
        List<Manager<?>> managers;
        do {
            components = notUpTo(state, speedment.components());
            components.forEach(c -> bringUpTo(state, c));
            managers = notUpTo(state, mc.stream());
            managers.forEach(m -> bringUpTo(state, m));
        } while (!(managers.isEmpty() && components.isEmpty()));

    }

    private <T extends Lifecyclable<?>> List<T> notUpTo(State state, Stream<T> stream) {
        return stream
            .filter(m -> !m.getState().onOrAfter(state))
            .collect(toList());
    }

    private void bringUpTo(State state, Lifecyclable<?> lifecyclable) {
        switch (state) {
            case INIITIALIZED: {
                lifecyclable.initialize();
                break;
            }
            case LOADED: {
                lifecyclable.load();
                break;
            }
            case RESOLVED: {
                lifecyclable.resolve();
                break;
            }
            case STARTED: {
                lifecyclable.start();
                break;
            }
            default: {
                // Do nothing
            }
        }
    }

}
