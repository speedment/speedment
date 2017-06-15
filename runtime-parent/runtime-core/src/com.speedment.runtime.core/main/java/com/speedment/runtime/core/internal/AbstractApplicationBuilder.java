/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.core.internal;

import com.speedment.common.injector.InjectBundle;
import com.speedment.common.injector.Injector;
import com.speedment.common.injector.InjectorBuilder;
import com.speedment.common.injector.annotation.Config;
import com.speedment.common.injector.exception.CyclicReferenceException;
import static com.speedment.common.injector.execution.ExecutionBuilder.resolved;
import static com.speedment.common.injector.execution.ExecutionBuilder.started;
import static com.speedment.common.invariant.NullUtil.requireNonNulls;
import com.speedment.common.logger.Level;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Schema;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.runtime.config.trait.HasName;
import com.speedment.runtime.config.util.DocumentDbUtil;
import com.speedment.runtime.config.util.DocumentUtil;
import static com.speedment.runtime.config.util.DocumentUtil.Name.DATABASE_NAME;
import com.speedment.runtime.core.ApplicationBuilder;
import com.speedment.runtime.core.ApplicationMetadata;
import com.speedment.runtime.core.RuntimeBundle;
import com.speedment.runtime.core.Speedment;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.InfoComponent;
import com.speedment.runtime.core.component.PasswordComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.db.DbmsMetadataHandler;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.core.util.DatabaseUtil;
import static java.lang.Boolean.TRUE;
import java.sql.SQLException;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        APP extends Speedment, 
        BUILDER extends AbstractApplicationBuilder<APP, BUILDER>
> implements ApplicationBuilder<APP, BUILDER> {

    private final static Logger LOGGER = LoggerManager.getLogger(
        LogType.APPLICATION_BUILDER.getLoggerName());
    
    private final InjectorBuilder injectorBuilder;

    private boolean skipCheckDatabaseConnectivity;
    private boolean skipValidateRuntimeConfig;
    private boolean skipLogoPrintout;
    
    protected AbstractApplicationBuilder(
        Class<? extends APP> applicationImplClass,
        Class<? extends ApplicationMetadata> metadataClass) {

        this(Injector.builder()
            .withBundle(RuntimeBundle.class)
            .withComponent(applicationImplClass)
            .withComponent(metadataClass)
        );
    }
    
    protected AbstractApplicationBuilder(
        ClassLoader classLoader,
        Class<? extends APP> applicationImplClass,
        Class<? extends ApplicationMetadata> metadataClass) {

        this(Injector.builder(classLoader)
            .withBundle(RuntimeBundle.class)
            .withComponent(applicationImplClass)
            .withComponent(metadataClass)
        );
    }

    protected AbstractApplicationBuilder(InjectorBuilder injectorBuilder) {
        this.injectorBuilder                   = requireNonNull(injectorBuilder);
        this.skipCheckDatabaseConnectivity     = false;
        this.skipValidateRuntimeConfig         = false;
    }

    @Override
    public <C extends Document & HasEnabled> BUILDER with(
        final Class<C> type,
        final String name,
        final BiConsumer<Injector, C> consumer
    ) {
        requireNonNulls(type, name, consumer);

        injectorBuilder.before(resolved(ProjectComponent.class)
            .withStateInitialized(Injector.class)
            .withExecute((projComp, injector) -> 
                DocumentDbUtil.traverseOver(projComp.getProject(), type)
                    .filter(doc -> DocumentUtil.relativeName(
                        HasName.of(doc), 
                        Dbms.class, 
                        DATABASE_NAME
                    ).equals(name))
                    .forEach(doc -> consumer.accept(injector, doc))
            )
        );
        
        return self();
    }

    @Override
    public <C extends Document & HasEnabled> BUILDER with(
            Class<C> type, BiConsumer<Injector, C> consumer) {
        
        requireNonNulls(type, consumer);
        
        injectorBuilder.before(resolved(ProjectComponent.class)
            .withStateInitialized(Injector.class)
            .withExecute((projComp, injector) -> 
                DocumentDbUtil.traverseOver(projComp.getProject(), type)
                    .forEach(doc -> consumer.accept(injector, doc))
            )
        );
        
        return self();
    }

    @Override
    public <C extends Document & HasEnabled> BUILDER with(
            Class<C> type, Consumer<C> consumer) {
        
        injectorBuilder.before(resolved(ProjectComponent.class)
            .withExecute(projComp -> 
                DocumentDbUtil.traverseOver(projComp.getProject(), type)
                    .forEach(consumer)
            )
        );
        
        return self();
    }

    @Override
    public BUILDER withParam(String key, String value) {
        requireNonNulls(key, value);
        injectorBuilder.withParam(key, value);
        return self();
    }

    @Override
    public BUILDER withPassword(char[] password) {
        // password nullable
        injectorBuilder.before(started(PasswordComponent.class)
            .withStateResolved(ProjectComponent.class)
            .withExecute((passComp, projComp) -> 
                projComp.getProject().dbmses().forEach(
                    dbms -> passComp.put(dbms, password)
                )
            )
        );
        
        return self();
    }

    @Override
    public BUILDER withPassword(String dbmsName, char[] password) {
        requireNonNull(dbmsName);
        // password nullable
        
        injectorBuilder.before(started(PasswordComponent.class)
            .withExecute(passComp -> passComp.put(dbmsName, password))
        );
        
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
        return withPassword(dbmsName, 
            password == null ? null : password.toCharArray()
        );
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
        with(Schema.class, s -> {
            // This makes sure that old json files with no id
            // first sets it id before the name is changed
            // Todo: Remove in next major API bumnp
            s.mutator().setId(s.getId());
            s.mutator().setName(schemaName);
        });
        return self();
    }

    @Override
    public BUILDER withSchema(String oldSchemaName, String schemaName) {
        requireNonNulls(oldSchemaName, schemaName);
        with(Schema.class, oldSchemaName, s -> {
            // This makes sure that old json files with no id
            // first sets it id before the name is changed
            // Todo: Remove in next major API bumnp
            s.mutator().setId(s.getId());
            s.mutator().setName(schemaName);
        });
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
        with(Dbms.class, dbmsName, s -> s.mutator().setConnectionUrl(connectionUrl));
        return self();
    }

    @Override
    public <M extends Manager<?>> BUILDER withManager(Class<M> managerImplType) {
        requireNonNull(managerImplType);
        withInjectable(injectorBuilder, managerImplType);
        return self();
    }

    @Override
    public BUILDER withSkipCheckDatabaseConnectivity() {
        this.skipCheckDatabaseConnectivity = true;
        return self();
    }

    @Override
    public BUILDER withSkipValidateRuntimeConfig() {
        this.skipValidateRuntimeConfig = true;
        return self();
    }

    @Override
    public BUILDER withSkipLogoPrintout() {
        this.skipLogoPrintout = true;
        return self();
    }

    @Override
    public BUILDER withBundle(Class<? extends InjectBundle> bundleClass) {
        requireNonNull(bundleClass);
        injectorBuilder.withBundle(bundleClass);
        return self();
    }

    @Override
    public BUILDER withComponent(Class<?> injectableClass) {
        requireNonNull(injectableClass);
        injectorBuilder.withComponent(injectableClass);
        return self();
    }

    @Override
    @Deprecated
    public BUILDER withComponent(String key, Class<?> injectableClass) {
        requireNonNulls(key, injectableClass);
        injectorBuilder.withComponent(injectableClass);
        return self();
    }

    @Override
    public BUILDER withLogging(HasLogglerName namer) {
        LoggerManager.getLogger(namer.getLoggerName()).setLevel(Level.DEBUG);
        
        if (LogType.APPLICATION_BUILDER.getLoggerName()
                .equals(namer.getLoggerName())) {
            
            // Special case becaues its in a common module
            Injector.logger().setLevel(Level.DEBUG); 
            InjectorBuilder.logger().setLevel(Level.DEBUG);
        }
        
        return self();
    }

    @Override
    public BUILDER withAllowStreamIteratorAndSpliterator() {
        injectorBuilder.withParam("allowStreamIteratorAndSpliterator", TRUE.toString());
        return self();
    }
    
    @Override
    public final APP build() {
        final Injector inj;

        try {
            inj = injectorBuilder.build();
        } catch (final InstantiationException | CyclicReferenceException ex) {
            throw new SpeedmentException("Error in dependency injection.", ex);
        }
        
        printWelcomeMessage(inj);

        if (!skipValidateRuntimeConfig) {
            validateRuntimeConfig(inj);
        }
        if (!skipCheckDatabaseConnectivity) {
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

    protected void validateRuntimeConfig(Injector injector) {
        LOGGER.debug("Validating Runtime Configuration");
        final Project project = injector.getOrThrow(ProjectComponent.class)
            .getProject();
        
        if (project == null) {
            throw new SpeedmentException("No project defined");
        }

        project.dbmses().forEach(d -> {
            final String typeName = d.getTypeName();
            final Optional<DbmsType> oDbmsType = injector.getOrThrow(
                DbmsHandlerComponent.class).findByName(typeName);
            
            if (!oDbmsType.isPresent()) {
                throw new SpeedmentException("The database type " + typeName + 
                    " is not registered with the " + 
                    DbmsHandlerComponent.class.getSimpleName());
            }
            final DbmsType dbmsType = oDbmsType.get();
            
            if (!dbmsType.isSupported()) {
                LOGGER.error("The database driver class " + dbmsType.getDriverName() + 
                    " is not available. Make sure to include it in your " + 
                    "class path (e.g. in the POM file)"
                );
            }
        });

    }

    protected void checkDatabaseConnectivity(Injector injector) {
        LOGGER.debug("Checking Database Connectivity");
        final Project project = injector.getOrThrow(ProjectComponent.class)
            .getProject();
        
        project.dbmses().forEachOrdered(dbms -> {

            final DbmsHandlerComponent dbmsHandlerComponent = injector
                .getOrThrow(DbmsHandlerComponent.class);
            
            final DbmsType dbmsType = DatabaseUtil
                .dbmsTypeOf(dbmsHandlerComponent, dbms);
            
            final DbmsMetadataHandler handler = dbmsType.getMetadataHandler();

            try {
                LOGGER.info(handler.getDbmsInfoString(dbms));
            } catch (final SQLException sqle) {
                throw new SpeedmentException("Unable to establish initial " + 
                    "connection with the database named " + 
                    dbms.getName() + ".", sqle);
            }
        });
    }

    /**
     * Prints a welcome message to the output channel.
     *
     * @param injector the injector to use
     */
    protected void printWelcomeMessage(Injector injector) {

        final InfoComponent info = injector.getOrThrow(InfoComponent.class);
        final String title = info.getTitle();
        final String version = info.getImplementationVersion();

        if (!skipLogoPrintout) {
            final String speedmentMsg = "\n"
                + "   ____                   _                     _     \n"
                + "  / ___'_ __  __  __   __| |_ __ __    __ _ __ | |    \n"
                + "  \\___ | '_ |/  \\/  \\ / _  | '_ \\ _ \\ /  \\ '_ \\| |_   \n"
                + "   ___)| |_)| '_/ '_/| (_| | | | | | | '_/ | | |  _|  \n"
                + "  |____| .__|\\__\\\\__\\ \\____|_| |_| |_|\\__\\_| |_| '_   \n"
                + "=======|_|======================================\\__|==\n"
                + "   :: " + title + " by " + info.getVendor()
                + ":: (v" + version + ") \n";

            LOGGER.info(speedmentMsg);
        }
        final String msg = title + " (" + info.getSubtitle()
            + ") version " + version
            + " by " + info.getVendor()
            + " Specification version "
            + info.getSpecificationVersion();
        LOGGER.info(msg);

        if (!info.isProductionMode()) {
            LOGGER.warn("This version is NOT INTENDED FOR PRODUCTION USE!");
        }

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
                    LOGGER.warn("The current Java version (" + versionString + 
                        ") is outdated. Please upgrade to a more recent " + 
                        "Java version.");
                }
            } else {
                LOGGER.warn("Unable to fully parse the java version. " + 
                    "Version check skipped!");
            }
        } catch (final Exception ex) {
            LOGGER.info("Unknown Java version.");
        }

    }

    private BUILDER self() {
        @SuppressWarnings("unchecked")
        final BUILDER builder = (BUILDER) this;
        return builder;
    }

    Optional<Boolean> isVersionOk(String versionString) {
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

    private static <T> void withInjectable(
            InjectorBuilder injector, 
            Class<T> injectableImplType) {
        
        requireNonNull(injectableImplType);
        injector.withComponent(injectableImplType);
    }
}
