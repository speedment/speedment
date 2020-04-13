/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.application;

import com.speedment.common.injector.InjectBundle;
import com.speedment.common.injector.Injector;
import com.speedment.common.injector.InjectorBuilder;
import com.speedment.common.injector.InjectorProxy;
import com.speedment.common.injector.exception.CyclicReferenceException;
import com.speedment.common.jvm_version.JvmVersion;
import com.speedment.common.logger.Level;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.runtime.application.internal.util.JpmsUtil;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Schema;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.runtime.config.trait.HasName;
import com.speedment.runtime.config.util.DocumentDbUtil;
import com.speedment.runtime.config.util.DocumentUtil;
import com.speedment.runtime.core.ApplicationBuilder;
import com.speedment.runtime.core.ApplicationMetadata;
import com.speedment.runtime.core.Speedment;
import com.speedment.runtime.core.component.*;
import com.speedment.runtime.core.db.DbmsMetadataHandler;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.core.util.DatabaseUtil;
import com.speedment.runtime.welcome.HasOnWelcome;

import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static com.speedment.common.injector.execution.ExecutionBuilder.resolved;
import static com.speedment.common.injector.execution.ExecutionBuilder.started;
import static com.speedment.runtime.config.util.DocumentUtil.Name.DATABASE_NAME;
import static java.lang.Boolean.TRUE;
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
        APP extends Speedment, 
        BUILDER extends AbstractApplicationBuilder<APP, BUILDER>
> implements ApplicationBuilder<APP, BUILDER> {

    private static final Logger LOGGER = LoggerManager.getLogger(
        LogType.APPLICATION_BUILDER.getLoggerName());
    
    private final InjectorBuilder injectorBuilder;

    private boolean skipCheckDatabaseConnectivity;
    private boolean skipValidateRuntimeConfig;
    private boolean skipLogoPrintout;
    
    protected AbstractApplicationBuilder(
        final Class<? extends APP> applicationImplClass,
        final Class<? extends ApplicationMetadata> metadataClass
    ) {
        this(Injector.builder()
            .withBundle(RuntimeBundle.class)
            .withComponent(applicationImplClass)
            .withComponent(metadataClass)
        );
    }
    
    protected AbstractApplicationBuilder(
        final ClassLoader classLoader,
        final Class<? extends APP> applicationImplClass,
        final Class<? extends ApplicationMetadata> metadataClass
    ) {

        this(Injector.builder(classLoader)
            .withBundle(RuntimeBundle.class)
            .withComponent(applicationImplClass)
            .withComponent(metadataClass)
        );
    }

    protected AbstractApplicationBuilder(final InjectorBuilder injectorBuilder) {
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
        requireNonNull(type);
        requireNonNull(name);
        requireNonNull(consumer);

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
        final Class<C> type,
        final BiConsumer<Injector, C> consumer
    ) {
        requireNonNull(type);
        requireNonNull(consumer);
        
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
            final Class<C> type,
            final Consumer<C> consumer
    ) {
        injectorBuilder.before(resolved(ProjectComponent.class)
            .withExecute(projComp -> 
                DocumentDbUtil.traverseOver(projComp.getProject(), type)
                    .forEach(consumer)
            )
        );
        
        return self();
    }

    @Override
    public BUILDER withParam(final String key, final String value) {
        requireNonNull(key);
        requireNonNull(value);
        injectorBuilder.withParam(key, value);
        return self();
    }

    @Override
    public BUILDER withPassword(final char[] password) {
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
    public BUILDER withPassword(final String dbmsName, final char[] password) {
        requireNonNull(dbmsName);
        // password nullable
        
        injectorBuilder.before(started(PasswordComponent.class)
            .withExecute(passComp -> passComp.put(dbmsName, password))
        );
        
        return self();
    }

    @Override
    public BUILDER withPassword(final String password) {
        // password nullable
        return withPassword(password == null ? null : password.toCharArray());
    }

    @Override
    public BUILDER withPassword(final String dbmsName, final String password) {
        requireNonNull(dbmsName);
        // password nullable
        return withPassword(dbmsName, 
            password == null ? null : password.toCharArray()
        );
    }

    @Override
    public BUILDER withUsername(final String username) {
        // username nullable
        with(Dbms.class, dbms -> dbms.mutator().setUsername(username));
        return self();
    }

    @Override
    public BUILDER withUsername(final String dbmsName, final String username) {
        requireNonNull(dbmsName);
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
        requireNonNull(dbmsName);
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
        requireNonNull(dbmsName);
        with(Dbms.class, dbmsName, d -> d.mutator().setPort(port));
        return self();
    }

    @Override
    public BUILDER withSchema(final String schemaName) {
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
    public BUILDER withSchema(final String oldSchemaName, final String schemaName) {
        requireNonNull(oldSchemaName);
        requireNonNull(schemaName);
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
    public BUILDER withConnectionUrl(final String connectionUrl) {
        with(Dbms.class, d -> d.mutator().setConnectionUrl(connectionUrl));
        return self();
    }

    @Override
    public BUILDER withConnectionUrl(final String dbmsName, final String connectionUrl) {
        requireNonNull(dbmsName);
        with(Dbms.class, dbmsName, s -> s.mutator().setConnectionUrl(connectionUrl));
        return self();
    }

    @Override
    public <M extends Manager<?>> BUILDER withManager(final Class<M> managerImplType) {
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
    public BUILDER withBundle(final Class<? extends InjectBundle> bundleClass) {
        requireNonNull(bundleClass);
        injectorBuilder.withBundle(bundleClass);
        return self();
    }

    @Override
    public BUILDER withComponent(final Class<?> injectableClass) {
        requireNonNull(injectableClass);
        injectorBuilder.withComponent(injectableClass);
        return self();
    }

    public <T> BUILDER withComponent(final Class<T> injectableClass, final Supplier<T> supplier) {
        requireNonNull(injectableClass);
        requireNonNull(supplier);
        injectorBuilder.withComponent(injectableClass, supplier);
        return self();
    }

    @Override
    public BUILDER withInjectorProxy(final InjectorProxy injectorProxy) {
        requireNonNull(injectorProxy);
        injectorBuilder.withInjectorProxy(injectorProxy);
        return self();
    }

    @Override
    public BUILDER withLogging(final HasLoggerName namer) {
        LoggerManager.getLogger(namer.getLoggerName()).setLevel(Level.DEBUG);
        
        if (LogType.APPLICATION_BUILDER.getLoggerName()
                .equals(namer.getLoggerName())) {
            
            // Special case because its in a common module
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

        printStreamSupplierOrder(inj);

        return build(inj);
    }

    /**
     * Builds the application using the specified injector.
     *
     * @param injector the injector to use
     * @return the built instance.
     */
    protected abstract APP build(Injector injector);

    protected void validateRuntimeConfig(final Injector injector) {
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

    protected void checkDatabaseConnectivity(final Injector injector) {
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
    protected void printWelcomeMessage(final Injector injector) {

        final InfoComponent info = injector.getOrThrow(InfoComponent.class);  // Get first
        final InfoComponent upstreamInfo = injector.stream(InfoComponent.class).reduce((first, second) -> second).orElseThrow(NoSuchElementException::new); // Get last
        final String title = info.getTitle();
        final String version = info.getImplementationVersion();

        if (!skipLogoPrintout) {
            final String speedmentMsg = String.format("%n"+
                info.getBanner() + "%n"
                + "   :: " + title + " by " + info.getVendor()
                + ":: (v" + version + ") %n")
                ;

            LOGGER.info(speedmentMsg);
        }
        final String msg = title + " (" + info.getSubtitle() +
                ") version " + version +
                " by " + info.getVendor() +
                " Specification version " +
                info.getSpecificationVersion() +
                " (" + info.getSpecificationNickname() + ")" +
                ", License: " + info.getLicenseName();
        LOGGER.info(msg);
        if (!info.isProductionMode()) {
            LOGGER.warn("This version is NOT INTENDED FOR PRODUCTION USE!");
        }

        if (info != upstreamInfo) {
            LOGGER.info("Upstream version is " + upstreamInfo.getImplementationVersion() + " (" + upstreamInfo.getSpecificationNickname() + ")");
            if (!upstreamInfo.isProductionMode()) {
                LOGGER.warn("Upstream version is NOT INTENDED FOR PRODUCTION USE!");
            }
        }

        try {
            
            final String javaMsg = String.format("%s %s by %s. Implementation %s %s by %s",
                JvmVersion.getSpecificationTitle(), JvmVersion.getSpecificationVersion(), JvmVersion.getSpecificationVendor(),
                JvmVersion.getImplementationTitle(), JvmVersion.getImplementationVersion(), JvmVersion.getImplementationVendor()
            );

            LOGGER.info(javaMsg);

            final String versionString = JvmVersion.getImplementationVersion();
            final Optional<Boolean> isVersionOk = isVersionOk();
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
        LOGGER.info(
            "Available processors: %d, Max Memory: %,d bytes",
            Runtime.getRuntime().availableProcessors(),
            Runtime.getRuntime().maxMemory()
        );

        JpmsUtil.logModulesIfEnabled();

        // Invoke all HasOnWelcome methods
        injector.injectables()
            .flatMap(injector::stream)
            .filter(HasOnWelcome.class::isInstance)
            .map(HasOnWelcome.class::cast)
            .forEach(HasOnWelcome::onWelcome);

    }

    private BUILDER self() {
        @SuppressWarnings("unchecked")
        final BUILDER builder = (BUILDER) this;
        return builder;
    }

    Optional<Boolean> isVersionOk() {
        final int majorVersion = JvmVersion.major();
        final int securityVersion = JvmVersion.security();
        if (majorVersion == 0) {
            return Optional.empty(); // Unable to determine
        }
        if (majorVersion < 8) {
            return Optional.of(Boolean.FALSE);
        }
        if (majorVersion == 8 && securityVersion < 40) {
            return Optional.of(Boolean.FALSE);
        }
        return Optional.of(Boolean.TRUE);
    }

    private static <T> void withInjectable(
            final InjectorBuilder injector,
            final Class<T> injectableImplType
    ) {
        requireNonNull(injectableImplType);
        injector.withComponent(injectableImplType);
    }

    private void printStreamSupplierOrder(final Injector injector) {
        injector.get(StreamSupplierComponent.class).ifPresent(root -> {
            if (root.sourceStreamSupplierComponents().findAny().isPresent()) {
                LOGGER.info("Current " + StreamSupplierComponent.class.getSimpleName() + " hierarchy:");
                printStreamSupplierOrder(root, 0);
            }
        });
    }

    private void printStreamSupplierOrder(final StreamSupplierComponent ssc, final int level) {
        LOGGER.info("%s%s (0x%08x) %s", indent(level), ssc.getClass().getSimpleName(), ssc.hashCode(), ssc.isImmutable() ? "mutable" : "immutable");
        ssc.sourceStreamSupplierComponents()
            .forEachOrdered(child -> printStreamSupplierOrder(child, level + 1));
    }

    private String indent(final int level) {
        return IntStream.range(0, level)
            .collect(StringBuilder::new, (sb, i) -> sb.append("    "), StringBuilder::append)
            .toString();
    }

}