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
package com.speedment.runtime.internal.runtime;

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
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Schema;
import com.speedment.runtime.config.parameter.DbmsType;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.runtime.config.trait.HasName;
import com.speedment.runtime.db.DbmsHandler;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.internal.config.ProjectImpl;
import com.speedment.runtime.internal.config.immutable.ImmutableProject;
import com.speedment.fika.logger.Logger;
import com.speedment.fika.logger.LoggerManager;
import com.speedment.runtime.SpeedmentBuilder;
import com.speedment.runtime.internal.util.Statistics;
import com.speedment.runtime.internal.util.document.DocumentDbUtil;
import com.speedment.runtime.internal.util.document.DocumentTranscoder;
import static com.speedment.runtime.internal.util.document.DocumentUtil.Name.DATABASE_NAME;
import com.speedment.runtime.manager.Manager;
import com.speedment.runtime.util.tuple.Tuple2;
import com.speedment.runtime.util.tuple.Tuple3;
import com.speedment.runtime.util.tuple.Tuples;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import static com.speedment.runtime.internal.util.document.DocumentUtil.relativeName;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static java.util.Objects.requireNonNull;

/**
 * This abstract class is implemented by classes that can build a 
 * {@link Speedment} application.
 *
 * @param <APP>      the type that is being built
 * @param <BUILDER>  the (self) type of the AbstractApplicationBuilder
 * 
 * @author  Per Minborg
 * @since   2.0
 *
 */
public abstract class AbstractApplicationBuilder<
        APP extends Speedment,
        BUILDER extends AbstractApplicationBuilder<APP, BUILDER>
    > extends AbstractLifecycle<BUILDER> 
    implements SpeedmentBuilder<APP, BUILDER>, Lifecyclable<BUILDER> {

    private final static Logger LOGGER = LoggerManager.getLogger(AbstractApplicationBuilder.class);

    private final List<Tuple3<Class<? extends Document>, String, Consumer<? extends Document>>> withsNamed;
    private final List<Tuple2<Class<? extends Document>, Consumer<? extends Document>>> withsAll;
    private boolean checkDatabaseConnectivity;
    private boolean validateRuntimeConfig;
    private final List<Function<Speedment, Manager<?>>> customManagers;

    private ApplicationMetadata speedmentApplicationMetadata;

    protected final APP application;

    protected AbstractApplicationBuilder(APP application) {
        this.application = requireNonNull(application);
        
        withsNamed = newList();
        withsAll = newList();
        checkDatabaseConnectivity = true;
        validateRuntimeConfig = true;
        customManagers = new CopyOnWriteArrayList<>();
    }

    @Override
    public <C extends Document & HasEnabled> BUILDER with(final Class<C> type, final String name, final Consumer<C> consumer) {
        requireNonNulls(type, name, consumer);
        withsNamed.add(Tuples.of(type, name, consumer));
        return self();
    }

    @Override
    public <C extends Document & HasEnabled> BUILDER with(final Class<C> type, final Consumer<C> consumer) {
        requireNonNulls(type, consumer);
        withsAll.add(Tuples.of(type, consumer));
        return self();
    }

    @Override
    public BUILDER withPassword(final char[] password) {
        // password nullable
        with(Dbms.class, dbms -> application.getPasswordComponent().put(dbms, password));
        return self();
    }

    @Override
    public BUILDER withPassword(final String dbmsName, final char[] password) {
        // password nullable
        with(Dbms.class, dbmsName, dbms -> application.getPasswordComponent().put(dbms, password));
        return self();
    }

    @Override
    public BUILDER withPassword(final String password) {
        // password nullable
        return withPassword(stringToCharArray(password));
    }

    @Override
    public BUILDER withPassword(final String dbmsName, final String password) {
        // password nullable
        return withPassword(dbmsName, stringToCharArray(password));
    }

    @Override
    public BUILDER withUsername(final String username) {
        // username nullable
        with(Dbms.class, d -> d.mutator().setUsername(username));
        return self();
    }

    @Override
    public BUILDER withUsername(final String dbmsName, final String username) {
        // username nullable
        with(Dbms.class, dbmsName, d -> d.mutator().setUsername(username));
        return self();
    }

    @Override
    public BUILDER withIpAddress(final String ipAddress) {
        requireNonNull(ipAddress);
        with(Dbms.class, d -> d.mutator().setIpAddress(ipAddress));
        return self();
    }

    @Override
    public BUILDER withIpAddress(final String dbmsName, final String ipAddress) {
        requireNonNull(ipAddress);
        with(Dbms.class, dbmsName, d -> d.mutator().setIpAddress(ipAddress));
        return self();
    }

    @Override
    public BUILDER withPort(final int port) {
        with(Dbms.class, d -> d.mutator().setPort(port));
        return self();
    }

    @Override
    public BUILDER withPort(final String dbmsName, final int port) {
        with(Dbms.class, dbmsName, d -> d.mutator().setPort(port));
        return self();
    }

    @Override
    public BUILDER withSchema(final String schemaName) {
        requireNonNull(schemaName);
        with(Schema.class, s -> s.mutator().setName(schemaName));
        return self();
    }

    @Override
    public BUILDER withSchema(final String oldSchemaName, final String schemaName) {
        requireNonNulls(oldSchemaName, schemaName);
        with(Schema.class, oldSchemaName, s -> s.mutator().setName(schemaName));
        return self();
    }

    @Override
    public BUILDER withConnectionUrl(final String connectionUrl) {
        with(Dbms.class, d -> d.mutator().setConnectionUrl(connectionUrl));
        return self();
    }

    @Override
    public BUILDER withConnectionUrl(final String dbmsName, final String connectionUrl) {
        requireNonNull(dbmsName);
        with(Dbms.class, dbmsName, s -> s.mutator().setName(connectionUrl));
        return self();
    }

    @Override
    public <C extends Component> BUILDER with(final ComponentConstructor<C> componentConstructor) {
        application.put(requireNonNull(componentConstructor).create(application));
        return self();
    }

    @Override
    public <C extends Component> BUILDER with(final Class<ComponentConstructor<C>> componentConstructorClass) {
        try {
            final ComponentConstructor<C> cc = componentConstructorClass.newInstance();
            return with(cc);
        } catch (IllegalAccessException | InstantiationException e) {

        }
        throw new SpeedmentException("Unable to make a new instance of the " + componentConstructorClass + " class");
    }

    @Override
    public <C extends Component> BUILDER withCheckDatabaseConnectivity(final boolean checkDatabaseConnectivity) {
        this.checkDatabaseConnectivity = checkDatabaseConnectivity;
        return self();
    }

    @Override
    public <C extends Component> BUILDER withValidateRuntimeConfig(final boolean validateRuntimeConfig) {
        this.validateRuntimeConfig = validateRuntimeConfig;
        return self();
    }

    @Override
    public <C extends Component> BUILDER withManager(final Function<Speedment, Manager<?>> constructor) {
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

        final String title = application.getInfoComponent().title();
        final String subTitle = application.getInfoComponent().subtitle();
        final String version = application.getInfoComponent().version();

        final String msg = title + " (" + subTitle + ") version " + version + " by " + getImplementationVendor() + " started."
            + " API version is " + getSpecificationVersion();

        printWelcomeMessage();
    }

    @Override
    public void onStop() {
        super.onStop();
        forEachManagerInSeparateThread(Manager::stop);
        forEachComponentInSeparateThread(Component::stop);
    }
    
    @Override
    public APP build() {
        if (!isStarted()) {
            start();
        }

        return application;
    }
    
    protected void loadCustomManagers() {
        customManagers.forEach(mc -> {
            final Manager<?> manager = mc.apply(application);
            application.getManagerComponent().put(manager);
        });
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

        application.getProjectComponent().setProject(project);
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

        final String title = application.getInfoComponent().title();
        final String subTitle = application.getInfoComponent().subtitle();
        final String version = application.getInfoComponent().version();

        final String speedmentMsg = title + " (" + subTitle + ") version " + version + " by " + getImplementationVendor() + " started."
            + " API version is " + getSpecificationVersion();
        LOGGER.info(speedmentMsg);
        if (!SpeedmentVersion.isProductionMode()) {
            LOGGER.warn("This version is NOT INTEDNED FOR PRODUCTION USE!");
        }

    }

    protected <ENTITY> void applyAndPut(Function<Speedment, Manager<ENTITY>> constructor) {
        put(constructor.apply(application));
    }

    protected <ENTITY> void put(Manager<ENTITY> manager) {
        application.getManagerComponent().put(manager);
    }

    /**
     * Sets the SpeedmentApplicationMetadata. The meta data describes the layout
     * of the Project (i.e. Dbms:es, Schemas, Tables, Columns etc)
     *
     * @param speedmentApplicationMetadata to use
     * @return this instance
     */
    protected BUILDER setSpeedmentApplicationMetadata(ApplicationMetadata speedmentApplicationMetadata) {
        this.speedmentApplicationMetadata = requireNonNull(speedmentApplicationMetadata);
        return self();
    }

    protected ApplicationMetadata getSpeedmentApplicationMetadata() {
        return speedmentApplicationMetadata;
    }

    /**
     * Support method to do something with all managers i separate threads.
     * Useful if, for example, initialization is lengthy.
     *
     * @param managerConsumer to accept all Managers
     */
    protected void forEachManagerInSeparateThread(Consumer<Manager<?>> managerConsumer) {
        requireNonNull(managerConsumer);
        final ManagerComponent mc = application.getManagerComponent();
        final List<Thread> threads = mc.stream()
            .map(mgr -> new Thread(()
                -> managerConsumer.accept(mgr),
                mgr.getTable().getName()
            ))
            .collect(toList());
        threads.forEach(Thread::start);
        threads.forEach(AbstractApplicationBuilder::join);
    }

    protected void forEachComponentInSeparateThread(Consumer<Component> componentConsumer) {
        requireNonNull(componentConsumer);
        final List<Thread> threads = application.components()
            .map(comp -> new Thread( // TODO: Change to ExecutorService
                () -> componentConsumer.accept(comp),
                comp.asSoftware().getName()
            ))
            .collect(toList());
        threads.forEach(Thread::start);
        threads.forEach(AbstractApplicationBuilder::join);
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
        final Project project = application.getProjectComponent().getProject();
        if (project == null) {
            throw new SpeedmentException("No project defined");
        }

        project.dbmses().forEach(d -> {
            final String typeName = d.getTypeName();
            final Optional<DbmsType> oDbmsType = application.getDbmsHandlerComponent().findByName(typeName);
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
        final Project project = application.getProjectComponent().getProject();
        if (project != null) {
            final Project immutableProject = ImmutableProject.wrap(project);
            application.getProjectComponent().setProject(immutableProject);
        }
    }

    protected void checkDatabaseConnectivity() {
        final Project project = application.getProjectComponent().getProject();
        project.dbmses().forEachOrdered(dbms -> {
            final DbmsHandler dbmsHandler = application.getDbmsHandlerComponent().get(dbms);
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
        final ManagerComponent mc = application.getManagerComponent();
        List<Component> components;
        List<Manager<?>> managers;
        do {
            components = notUpTo(state, application.components());
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
