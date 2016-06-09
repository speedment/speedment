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

import com.speedment.common.injector.Injector;
import com.speedment.runtime.Speedment;
import com.speedment.runtime.SpeedmentVersion;
import static com.speedment.runtime.SpeedmentVersion.getImplementationVendor;
import static com.speedment.runtime.SpeedmentVersion.getSpecificationVersion;
import com.speedment.runtime.component.Component;
import com.speedment.runtime.component.DbmsHandlerComponent;
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
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.runtime.SpeedmentBuilder;
import com.speedment.runtime.component.InfoComponent;
import com.speedment.runtime.component.PasswordComponent;
import com.speedment.runtime.component.ProjectComponent;
import com.speedment.runtime.internal.component.StandardComponents;
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
import java.util.function.Consumer;
import static java.util.stream.Collectors.toList;
import static com.speedment.runtime.internal.util.document.DocumentUtil.relativeName;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static java.util.Objects.requireNonNull;
import java.util.function.BiConsumer;

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
    > implements SpeedmentBuilder<APP, BUILDER> {

    private final static Logger LOGGER = LoggerManager.getLogger(AbstractApplicationBuilder.class);

    private final List<Tuple3<Class<? extends Document>, String, BiConsumer<APP, ? extends Document>>> withsNamed;
    private final List<Tuple2<Class<? extends Document>, BiConsumer<APP, ? extends Document>>> withsAll;
    private final Class<? extends APP> applicationImplClass;
    private final Injector.Builder injector;

    private ApplicationMetadata speedmentApplicationMetadata;
    private boolean checkDatabaseConnectivity;
    private boolean validateRuntimeConfig;

    protected AbstractApplicationBuilder(Class<? extends APP> applicationImplClass) {
        this.applicationImplClass = requireNonNull(applicationImplClass);
        this.injector = Injector.builder()
            .canInject(StandardComponents.get())
            .canInject(applicationImplClass);
        
        withsNamed = newList();
        withsAll   = newList();
    }
    
    protected final BUILDER self() {
        @SuppressWarnings("unchecked")
        final BUILDER builder = (BUILDER) this;
        return builder;
    }

    @Override
    public <C extends Document & HasEnabled> BUILDER with(Class<C> type, String name, BiConsumer<APP, C> consumer) {
        requireNonNulls(type, name, consumer);
        withsNamed.add(Tuples.of(type, name, consumer));
        return self();
    }

    @Override
    public <C extends Document & HasEnabled> BUILDER with(Class<C> type, BiConsumer<APP, C> consumer) {
        requireNonNulls(type, consumer);
        withsAll.add(Tuples.of(type, consumer));
        return self();
    }

    @Override
    public BUILDER withPassword(char[] password) {
        // password nullable
        with(Dbms.class, (app, dbms) -> app.injector().get(PasswordComponent.class).put(dbms, password));
        return self();
    }

    @Override
    public BUILDER withPassword(String dbmsName, char[] password) {
        // password nullable
        with(Dbms.class, dbmsName, (app, dbms) -> app.injector().get(PasswordComponent.class).put(dbms, password));
        return self();
    }

    @Override
    public BUILDER withPassword(String password) {
        // password nullable
        return withPassword(password == null ? null : password.toCharArray());
    }

    @Override
    public BUILDER withPassword(String dbmsName, String password) {
        // password nullable
        return withPassword(dbmsName, password == null ? null : password.toCharArray());
    }

    @Override
    public BUILDER withUsername(String username) {
        // username nullable
        with(Dbms.class, dbms -> dbms.mutator().setUsername(username));
        return self();
    }

    @Override
    public BUILDER withUsername(String dbmsName, String username) {
        // username nullable
        with(Dbms.class, dbmsName, d -> d.mutator().setUsername(username));
        return self();
    }

    @Override
    public BUILDER withIpAddress(String ipAddress) {
        requireNonNull(ipAddress);
        with(Dbms.class, d -> d.mutator().setIpAddress(ipAddress));
        return self();
    }

    @Override
    public BUILDER withIpAddress(String dbmsName, String ipAddress) {
        requireNonNull(ipAddress);
        with(Dbms.class, dbmsName, d -> d.mutator().setIpAddress(ipAddress));
        return self();
    }

    @Override
    public BUILDER withPort(int port) {
        with(Dbms.class, d -> d.mutator().setPort(port));
        return self();
    }

    @Override
    public BUILDER withPort(String dbmsName, int port) {
        with(Dbms.class, dbmsName, d -> d.mutator().setPort(port));
        return self();
    }

    @Override
    public BUILDER withSchema(String schemaName) {
        requireNonNull(schemaName);
        with(Schema.class, s -> s.mutator().setName(schemaName));
        return self();
    }

    @Override
    public BUILDER withSchema(String oldSchemaName, String schemaName) {
        requireNonNulls(oldSchemaName, schemaName);
        with(Schema.class, oldSchemaName, s -> s.mutator().setName(schemaName));
        return self();
    }

    @Override
    public BUILDER withConnectionUrl(String connectionUrl) {
        with(Dbms.class, d -> d.mutator().setConnectionUrl(connectionUrl));
        return self();
    }

    @Override
    public BUILDER withConnectionUrl(String dbmsName, String connectionUrl) {
        requireNonNull(dbmsName);
        with(Dbms.class, dbmsName, s -> s.mutator().setName(connectionUrl));
        return self();
    }

    @Override
    public <C extends Component> BUILDER with(Class<C> componentImplType) {
        requireNonNull(componentImplType);
        injector.canInject(componentImplType);
        return self();
    }

    @Override
    public <C extends Component> BUILDER withCheckDatabaseConnectivity(boolean checkDatabaseConnectivity) {
        this.checkDatabaseConnectivity = checkDatabaseConnectivity;
        return self();
    }

    @Override
    public <C extends Component> BUILDER withValidateRuntimeConfig(boolean validateRuntimeConfig) {
        this.validateRuntimeConfig = validateRuntimeConfig;
        return self();
    }

    @Override
    public <M extends Manager<?>> BUILDER withManager(Class<M> managerImplType) {
        injector.canInject(managerImplType);
        return self();
    }

    /**
     * Builds up the complete Project meta data tree.
     */
    protected void loadAndSetProject(Injector injector) {
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

        injector.get(ProjectComponent.class).setProject(project);
    }
    
    protected void printWelcomeMessage(Injector injector) {
        
        final InfoComponent info = injector.get(InfoComponent.class);

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

        final String title    = info.title();
        final String subTitle = info.subtitle();
        final String version  = info.version();

        final String speedmentMsg = title + " (" + subTitle + ") version " + 
            version + " by " + getImplementationVendor() + " started." +
            " API version is " + getSpecificationVersion();
        
        LOGGER.info(speedmentMsg);
        
        if (!SpeedmentVersion.isProductionMode()) {
            LOGGER.warn("This version is NOT INTENDED FOR PRODUCTION USE!");
        }
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
}
