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
package com.speedment.runtime.internal;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.exception.CyclicReferenceException;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.common.tuple.Tuple2;
import com.speedment.common.tuple.Tuple3;
import com.speedment.common.tuple.Tuples;
import com.speedment.runtime.ApplicationMetadata;
import com.speedment.runtime.Speedment;
import com.speedment.runtime.SpeedmentVersion;
import com.speedment.runtime.component.Component;
import com.speedment.runtime.component.InfoComponent;
import com.speedment.runtime.component.PasswordComponent;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Schema;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.runtime.config.trait.HasName;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.internal.util.document.DocumentDbUtil;
import com.speedment.runtime.manager.Manager;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static com.speedment.runtime.SpeedmentVersion.getImplementationVendor;
import static com.speedment.runtime.SpeedmentVersion.getSpecificationVersion;
import static com.speedment.runtime.internal.util.document.DocumentUtil.Name.DATABASE_NAME;
import com.speedment.common.injector.InjectBundle;
import com.speedment.runtime.RuntimeBundle;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.speedment.runtime.ApplicationBuilder;
import com.speedment.runtime.component.DbmsHandlerComponent;
import com.speedment.runtime.component.ProjectComponent;
import com.speedment.runtime.config.parameter.DbmsType;
import static com.speedment.runtime.internal.util.document.DocumentUtil.relativeName;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static java.util.Objects.requireNonNull;

/**
 * This abstract class is implemented by classes that can build a
 * {@link Speedment} application.
 *
 * @param <APP> the type that is being built
 * @param <BUILDER> the (self) type of the AbstractApplicationBuilder
 *
 * @author Per Minborg
 * @author Emil Forslund
 * @since 2.0.0
 */
public abstract class AbstractApplicationBuilder<
        APP extends Speedment, BUILDER extends AbstractApplicationBuilder<APP, BUILDER>> implements ApplicationBuilder<APP, BUILDER> {

    private final static Logger LOGGER = LoggerManager.getLogger(AbstractApplicationBuilder.class);

    private final List<Tuple3<Class<? extends Document>, String, BiConsumer<Injector, ? extends Document>>> withsNamed;
    private final List<Tuple2<Class<? extends Document>, BiConsumer<Injector, ? extends Document>>> withsAll;
    private final Injector.Builder injector;

    // TODO Investigate why these are not used.
    private boolean checkDatabaseConnectivity;
    private boolean validateRuntimeConfig;

    protected AbstractApplicationBuilder(
        Class<? extends APP> applicationImplClass,
        Class<? extends ApplicationMetadata> metadataClass) {

        this(Injector.builder()
            .putInBundle(RuntimeBundle.class)
            .put(applicationImplClass)
            .put(metadataClass)
        );
    }

    protected AbstractApplicationBuilder(Injector.Builder injector) {
        this.injector = requireNonNull(injector);
        this.withsNamed = new ArrayList<>();
        this.withsAll = new ArrayList<>();
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
        requireNonNulls(key, value);
        injector.putParam(key, value);
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
        requireNonNull(dbmsName);
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
        requireNonNull(dbmsName);
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
        requireNonNull(dbmsName);
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
        requireNonNulls(dbmsName, ipAddress);
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
        requireNonNull(dbmsName);
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
        withInjectable(injector, componentImplType, Component::getComponentClass);
        return self();
    }

    @Override
    public <M extends Manager<?>> BUILDER withManager(Class<M> managerImplType) {
        requireNonNull(managerImplType);
        withInjectable(injector, managerImplType, M::getEntityClass);
        return self();
    }

    @Override
    public BUILDER withCheckDatabaseConnectivity(boolean checkDatabaseConnectivity) {
        this.checkDatabaseConnectivity = checkDatabaseConnectivity;
        return self();
    }

    @Override
    public BUILDER withValidateRuntimeConfig(boolean validateRuntimeConfig) {
        this.validateRuntimeConfig = validateRuntimeConfig;
        return self();
    }

    @Override
    public BUILDER withBundle(Class<? extends InjectBundle> bundleClass) {
        requireNonNull(bundleClass);
        injector.putInBundle(bundleClass);
        return self();
    }

    @Override
    public BUILDER withInjectable(Class<?> injectableClass) {
        requireNonNull(injectableClass);
        injector.put(injectableClass);
        return self();
    }

    @Override
    public BUILDER withInjectable(String key, Class<?> injectableClass) {
        requireNonNulls(key, injectableClass);
        injector.put(key, injectableClass);
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

        printWelcomeMessage(inj);
        if (validateRuntimeConfig) {
            validateRuntimeConfig(inj);
        }
        if (checkDatabaseConnectivity) {
            checkDatabaseConnectivity(inj);
        }

        return build(inj);
    }

    /**
     * Builds the application using the specified injector.
     *
     * @param injector the injector to use
     * @return the built instance.
     */
    protected abstract APP build(Injector injector);

    /**
     * Builds up the complete Project meta data tree.
     *
     * @param injector the injector to use
     */
    protected void loadAndSetProject(Injector injector) {
        //final ApplicationMetadata meta = injector.getOrThrow(ApplicationMetadata.class);
        final Project project = injector.getOrThrow(ProjectComponent.class).getProject();

        // Apply overidden item (if any) for all Documents of a given class
        withsAll.forEach(t2 -> {
            final Class<? extends Document> clazz = t2.get0();

            @SuppressWarnings("unchecked")
            final BiConsumer<Injector, Document> consumer
                = (BiConsumer<Injector, Document>) t2.get1();

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
            final BiConsumer<Injector, Document> consumer
                = (BiConsumer<Injector, Document>) t3.get2();

            DocumentDbUtil.traverseOver(project)
                .filter(clazz::isInstance)
                .filter(HasName.class::isInstance)
                .map(HasName.class::cast)
                .filter(c -> name.equals(relativeName(c, Project.class, DATABASE_NAME)))
                .forEachOrdered(doc -> consumer.accept(injector, doc));
        });
    }

    protected void validateRuntimeConfig(Injector injector) {
        final Project project = injector.getOrThrow(ProjectComponent.class).getProject();
        if (project == null) {
            throw new SpeedmentException("No project defined");
        }

        project.dbmses().forEach(d -> {
            final String typeName = d.getTypeName();
            final Optional<DbmsType> oDbmsType = injector.getOrThrow(DbmsHandlerComponent.class).findByName(typeName);
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

    protected void checkDatabaseConnectivity(Injector injector) {
        final Project project = injector.getOrThrow(ProjectComponent.class).getProject();
        project.dbmses().forEachOrdered(dbms -> {
//            final DbmsHandlerComponent dbmsHandlerComponent = injector.getOrThrow(DbmsHandlerComponent.class);
//            final DbmsType dbmsType = DocumentDbUtil.dbmsTypeOf(dbmsHandlerComponent, dbms);
//            final DbmsOperationHandler ops = dbmsType.getOperationHandler();
//            
//                private ConnectionPoolComponent connectionPoolComponent = injector.;
//            
//            
//            Connection c = null;
//            
//            try {
//                final String initialQuery = dbmsType.getInitialQuery();
//                LOGGER.info(initialQuery);
//
//                final Optional<String> oInfo = ops.executeQuery(dbms, initialQuery, (rs) -> rs.getObject(1).toString()).findFirst();
//                if (oInfo.isPresent()) {
//                    LOGGER.warn("Unable to verify dbms connection for " + dbms.toString());
//                } else {
//                    LOGGER.info("Dbms " + dbms.getName() + " -> " + oInfo.get().toString());
//                }
////
////                try (Connection conn = dbmsHandler.executeDelete(sql, withsAll)) {
////                    final Optional<Map<String, String>> oInfo = dbmsHandler.executeQuery(dbmsType.getInitialQuery(), new RsMapper()).findAny();
////                    if (!oInfo.isPresent()) {
////                        LOGGER.warn("Unable to verify dbms connection for " + dbms.toString());
////                    } else {
////                        LOGGER.info("Dbms " + dbms.getName() + " -> " + oInfo.get().toString());
////                    }
////                } catch (Exception e) {
////                    LOGGER.error(e, "Unable to connect to dbms " + dbms.toString());
////                }
//
//            } catch (Exception e) {
//                LOGGER.error(e, "Unable to connect to dbms " + dbms.toString());
//            }
        });
    }

    /**
     * Prints a welcome message to the output channel.
     *
     * @param injector the injector to use
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

            final String versionString = package_.getImplementationVersion();
            final Optional<Boolean> isVersionOk = isVersionOk(versionString);
            if (isVersionOk.isPresent()) {
                if (!isVersionOk.get()) {
                    LOGGER.warn("The current Java version (" + versionString + ") is outdated. Please upgrade to a more recent Java version.");
                }
            } else {
                LOGGER.warn("Unable to fully parse the java version. Version check skipped!");
            }
        } catch (final Exception e) {
            LOGGER.info("Unknown Java version.");
        }

        final String title = info.title();
        final String subTitle = info.subtitle();
        final String version = info.version();

        final String speedmentMsg = title + " (" + subTitle + ") version "
            + version + " by " + getImplementationVendor() + " started."
            + " API version is " + getSpecificationVersion();

        LOGGER.info(speedmentMsg);

        if (!SpeedmentVersion.isProductionMode()) {
            LOGGER.warn("This version is NOT INTENDED FOR PRODUCTION USE!");
        }
    }

    protected Optional<Boolean> isVersionOk(String versionString) {
        final Pattern pattern = Pattern.compile("(\\d+)[\\\\.](\\d+)[\\\\.](\\d+)_(\\d+)");
        final Matcher matcher = pattern.matcher(versionString);

        if (matcher.find() && matcher.groupCount() >= 4) {
            final String majorVersionString = matcher.group(1);
            final String minorVersionString = matcher.group(2);
            final String patchVersionString = matcher.group(3);
            final String microVersionString = matcher.group(4);
            try {
                final int majorVersion = Integer.parseInt(majorVersionString);
                final int minorVersion = Integer.parseInt(minorVersionString);
                final int patchVersion = Integer.parseInt(patchVersionString);
                final int microVersion = Integer.parseInt(microVersionString);
                boolean ok = true;
                if (majorVersion < 1) {
                    ok = false;
                }
                if (majorVersion == 1) {
                    if (minorVersion < 8) {
                        ok = false;
                    }
                    if (minorVersion == 8) {
                        if (patchVersion < 0) {
                            ok = false;
                        }
                        if (patchVersion == 0) {
                            if (microVersion < 40) {
                                ok = false;
                            }
                        }
                    }
                }
                return Optional.of(ok);
            } catch (NumberFormatException nfe) {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    private static <T> void withInjectable(Injector.Builder injector, Class<T> injectableImplType, Function<T, Class<?>> keyExtractor) {
        requireNonNull(injectableImplType);

        final T injectable;
        try {
            final Constructor<T> constructor = injectableImplType.getDeclaredConstructor();
            constructor.setAccessible(true);
            injectable = constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {

            throw new SpeedmentException(ex);
        }

        final Class<?> key = keyExtractor.apply(injectable);
        injector.put(key.getName(), injectableImplType);
    }
}
