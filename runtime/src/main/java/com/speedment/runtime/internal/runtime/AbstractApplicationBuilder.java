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

import com.speedment.runtime.ApplicationMetadata;
import com.speedment.common.injector.Injector;
import com.speedment.common.injector.exception.CyclicReferenceException;
import com.speedment.runtime.Speedment;
import com.speedment.runtime.SpeedmentVersion;
import static com.speedment.runtime.SpeedmentVersion.getImplementationVendor;
import static com.speedment.runtime.SpeedmentVersion.getSpecificationVersion;
import com.speedment.runtime.component.Component;
import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Schema;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.runtime.config.trait.HasName;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.runtime.SpeedmentBuilder;
import com.speedment.runtime.component.InfoComponent;
import com.speedment.runtime.component.PasswordComponent;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.internal.util.document.DocumentDbUtil;
import static com.speedment.runtime.internal.util.document.DocumentUtil.Name.DATABASE_NAME;
import com.speedment.runtime.manager.Manager;
import com.speedment.common.tuple.Tuple2;
import com.speedment.common.tuple.Tuple3;
import com.speedment.common.tuple.Tuples;
import java.util.List;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import static com.speedment.runtime.internal.util.document.DocumentUtil.relativeName;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import static java.util.Objects.requireNonNull;
import java.util.function.Function;

/**
 * This abstract class is implemented by classes that can build a 
 * {@link Speedment} application.
 *
 * @param <APP>      the type that is being built
 * @param <BUILDER>  the (self) type of the AbstractApplicationBuilder
 * 
 * @author  Per Minborg
 * @author  Emil Forslund
 * @since   2.0.0
 */
public abstract class AbstractApplicationBuilder<
        APP extends Speedment,
        BUILDER extends AbstractApplicationBuilder<APP, BUILDER>
    > implements SpeedmentBuilder<APP, BUILDER> {

    private final static Logger LOGGER = LoggerManager.getLogger(AbstractApplicationBuilder.class);

    private final List<Tuple3<Class<? extends Document>, String, BiConsumer<Injector, ? extends Document>>> withsNamed;
    private final List<Tuple2<Class<? extends Document>, BiConsumer<Injector, ? extends Document>>> withsAll;
    private final Injector.Builder injector;

    private boolean checkDatabaseConnectivity;
    private boolean validateRuntimeConfig;

    protected AbstractApplicationBuilder(
            Class<? extends APP> applicationImplClass, 
            Class<? extends ApplicationMetadata> metadataClass) {
        
        this(Injector.builder()
            .canInject(applicationImplClass)
            .canInject(metadataClass)
        );
    }
    
    protected AbstractApplicationBuilder(Injector.Builder injector) {
        this.injector   = requireNonNull(injector);
        this.withsNamed = new ArrayList<>();
        this.withsAll   = new ArrayList<>();
    }
    
    protected final BUILDER self() {
        @SuppressWarnings("unchecked")
        final BUILDER builder = (BUILDER) this;
        return builder;
    }

    @Override
    public <C extends Document & HasEnabled> BUILDER with(Class<C> type, String name, BiConsumer<Injector, C> consumer) {
        requireNonNulls(type, name, consumer);
        withsNamed.add(Tuples.of(type, name, consumer));
        return self();
    }

    @Override
    public <C extends Document & HasEnabled> BUILDER with(Class<C> type, BiConsumer<Injector, C> consumer) {
        requireNonNulls(type, consumer);
        withsAll.add(Tuples.of(type, consumer));
        return self();
    }

    @Override
    public BUILDER withParam(String key, String value) {
        injector.withParam(key, value);
        return self();
    }

    @Override
    public BUILDER withPassword(char[] password) {
        // password nullable
        with(Dbms.class, (inj, dbms) -> inj.getOrThrow(PasswordComponent.class).put(dbms, password));
        return self();
    }

    @Override
    public BUILDER withPassword(String dbmsName, char[] password) {
        // password nullable
        with(Dbms.class, dbmsName, (inj, dbms) -> inj.getOrThrow(PasswordComponent.class).put(dbms, password));
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
        withInjectable(injector, componentImplType, Component::getComponentClass);
        return self();
    }
    
    @Override
    public <M extends Manager<?>> BUILDER withManager(Class<M> managerImplType) {
        withInjectable(injector, managerImplType, M::getEntityClass);
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
    public BUILDER withInjectable(Class<?> injectableClass) {
        injector.canInject(injectableClass);
        return self();
    }

    @Override
    public BUILDER withInjectable(String key, Class<?> injectableClass) {
        injector.canInject(key, injectableClass);
        return self();
    }

    @Override
    public final APP build() {
        final Injector inj;
        
        try {
            inj = injector.build();
        } catch (final InstantiationException | CyclicReferenceException ex) {
            throw new SpeedmentException("Error in dependency injection.", ex);
        }
        
        loadAndSetProject(inj);
        return build(inj);
    }
    
    /**
     * Builds the application using the specified injector.
     * 
     * @param injector  the injector to use
     * @return          the built instance.
     */
    protected abstract APP build(Injector injector);

    /**
     * Builds up the complete Project meta data tree.
     * 
     * @param injector  the injector to use
     */
    protected void loadAndSetProject(Injector injector) {
        final ApplicationMetadata meta = injector.getOrThrow(ApplicationMetadata.class);
        final Project project = meta.makeProject();

        // Apply overidden item (if any) for all Documents of a given class
        withsAll.forEach(t2 -> {
            final Class<? extends Document> clazz = t2.get0();
            
            @SuppressWarnings("unchecked")
            final BiConsumer<Injector, Document> consumer = 
                (BiConsumer<Injector, Document>) t2.get1();
            
            DocumentDbUtil.traverseOver(project)
                .filter(clazz::isInstance)
                .map(Document.class::cast)
                .forEachOrdered(doc -> consumer.accept(injector, doc));
        });

        // Apply a named overidden item (if any) for all Entities of a given class
        withsNamed.forEach(t3 -> {
            final Class<? extends Document> clazz = t3.get0();
            final String name = t3.get1();

            @SuppressWarnings("unchecked")
            final BiConsumer<Injector, Document> consumer = 
                (BiConsumer<Injector, Document>) t3.get2();

            DocumentDbUtil.traverseOver(project)
                .filter(clazz::isInstance)
                .filter(HasName.class::isInstance)
                .map(HasName.class::cast)
                .filter(c -> name.equals(relativeName(c, Project.class, DATABASE_NAME)))
                .forEachOrdered(doc -> consumer.accept(injector, doc));
        });
    }
    
    /**
     * Prints a welcome message to the output channel.
     * 
     * @param injector  the injector to use
     */
    protected void printWelcomeMessage(Injector injector) {
        
        final InfoComponent info = injector.getOrThrow(InfoComponent.class);

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
    
    private static <T> void withInjectable(Injector.Builder injector, Class<T> injectableImplType, Function<T, Class<?>> keyExtractor) {
        requireNonNull(injectableImplType);
        
        final T injectable;
        try {
            final Constructor<T> constructor = injectableImplType.getDeclaredConstructor();
            constructor.setAccessible(true);
            injectable = constructor.newInstance();
        } catch (InstantiationException 
               | IllegalAccessException 
               | NoSuchMethodException 
               | InvocationTargetException ex) {
            
            throw new SpeedmentException(ex);
        }
        
        final Class<?> key = keyExtractor.apply(injectable);
        injector.canInject(key.getName(), injectableImplType);
    }
}