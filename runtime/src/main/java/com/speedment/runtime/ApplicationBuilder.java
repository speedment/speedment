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
package com.speedment.runtime;

import com.speedment.common.injector.InjectBundle;
import com.speedment.common.injector.Injector;
import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.component.Component;
import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.runtime.internal.DefaultApplicationBuilder;
import com.speedment.runtime.internal.DefaultApplicationMetadata;
import com.speedment.runtime.internal.EmptyApplicationMetadata;
import com.speedment.runtime.manager.Manager;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Builder class for producing new {@link Speedment} instances.
 *
 * @param <APP> application that is built
 * @param <BUILDER> the type of this builder
 *
 * @author Emil Forslund
 * @since 3.0.0
 */
@Api(version = "3.0")
public interface ApplicationBuilder<APP extends Speedment, BUILDER extends ApplicationBuilder<APP, BUILDER>> {

    /**
     * Configures a parameter for the named {@link Document} of a certain class.
     * The consumer will then be applied after the configuration has been read
     * and after the System properties have been applied.
     *
     * @param <C> the type of {@link Document} that is to be used
     * @param type the class of the type of {@link Document} that is to be used
     * @param name the fully qualified name of the {@link Document}.
     * @param consumer the consumer to apply
     * @return this instance
     */
    default <C extends Document & HasEnabled> BUILDER with(Class<C> type, String name, Consumer<C> consumer) {
        return with(type, name, (app, t) -> consumer.accept(t));
    }

    /**
     * Configures a parameter for the named {@link Document} of a certain class.
     * The consumer will then be applied after the configuration has been read
     * and after the System properties have been applied.
     *
     * @param <C> the type of {@link Document} that is to be used
     * @param type the class of the type of {@link Document} that is to be used
     * @param name the fully qualified name of the {@link Document}.
     * @param consumer the consumer to apply
     * @return this instance
     */
    <C extends Document & HasEnabled> BUILDER with(Class<C> type, String name, BiConsumer<Injector, C> consumer);

    /**
     * Configures a parameter for all {@link Document} of a certain class. The
     * consumer will then be applied after the configuration has been read and
     * after the System properties have been applied.
     *
     * @param <C> the type of {@link Document} that is to be used
     * @param type the class of the type of {@link Document} that is to be used
     * @param consumer the consumer to apply
     * @return this instance
     */
    default <C extends Document & HasEnabled> BUILDER with(Class<C> type, Consumer<C> consumer) {
        return with(type, (app, t) -> consumer.accept(t));
    }

    /**
     * Configures a parameter for all {@link Document} of a certain class. The
     * consumer will then be applied after the configuration has been read and
     * after the System properties have been applied.
     *
     * @param <C> the type of {@link Document} that is to be used
     * @param type the class of the type of {@link Document} that is to be used
     * @param consumer the consumer to apply
     * @return this instance
     */
    <C extends Document & HasEnabled> BUILDER with(Class<C> type, BiConsumer<Injector, C> consumer);

    /**
     * Sets a config parameter that will be set automatically in all instances
     * created during initialization. This parameter can override settings in
     * the settings file.
     *
     * @param key the key to set
     * @param value the new value
     * @return this instance
     */
    BUILDER withParam(String key, String value);

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
    BUILDER withPassword(char[] password);

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
    BUILDER withPassword(String dbmsName, char[] password);

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
    BUILDER withPassword(String password);

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
    BUILDER withPassword(String dbmsName, String password);

    /**
     * Configures a username for all dbmses in this project. The username will
     * then be applied after the configuration has been read and after the
     * System properties have been applied.
     *
     * @param username to use for all dbms:es in this project
     * @return this instance
     */
    BUILDER withUsername(String username);

    /**
     * Configures a username for the named dbms. The username will then be
     * applied after the configuration has been read and after the System
     * properties have been applied.
     *
     * @param dbmsName the name of the dbms
     * @param username to use for the named dbms
     * @return this instance
     */
    BUILDER withUsername(String dbmsName, String username);

    /**
     * Configures an IP-address for all dbmses in this project. The IP-address
     * will then be applied after the configuration has been read and after the
     * System properties have been applied.
     *
     * @param ipAddress to use for all dbms:es in this project
     * @return this instance
     */
    BUILDER withIpAddress(String ipAddress);

    /**
     * Configures an IP-address for the named dbms. The IP-address will then be
     * applied after the configuration has been read and after the System
     * properties have been applied.
     *
     * @param dbmsName the name of the dbms
     * @param ipAddress to use for the named dbms.
     * @return this instance
     */
    BUILDER withIpAddress(String dbmsName, String ipAddress);

    /**
     * Configures a port for all dbmses in this project. The port will then be
     * applied after the configuration has been read and after the System
     * properties have been applied.
     *
     * @param port to use for all dbms:es in this project
     * @return this instance
     */
    BUILDER withPort(int port);

    /**
     * Configures a port for the named dbms. The port will then be applied after
     * the configuration has been read and after the System properties have been
     * applied.
     *
     * @param dbmsName the name of the dbms
     * @param port to use for the named dbms
     * @return this instance
     */
    BUILDER withPort(String dbmsName, int port);

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
    BUILDER withSchema(String schemaName);

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
    BUILDER withSchema(String oldSchemaName, String schemaName);

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
    BUILDER withConnectionUrl(String connectionUrl);

    /**
     * Configures a connection URL for the named dbms in this project. The new
     * connection URL will then be applied after the configuration has been read
     * and after the System properties have been applied. If the connectionUrl
     * is set to {@code null}, the connection URL will be calculated using the
     * dbmses' default connection URL generator (e.g. using ipAddress, port,
     * etc).
     *
     * @param dbmsName the name of the dbms
     * @param connectionUrl to use for the named dbms or null
     * @return this instance
     */
    BUILDER withConnectionUrl(String dbmsName, String connectionUrl);

    /**
     * Adds a (and replaces any existing) {@link Component} to the Speedment
     * runtime platform.
     *
     * @param <C> the component implementation type
     *
     * @param componentClass the component implementation type
     * @return this instance
     */
    <C extends Component> BUILDER with(Class<C> componentClass);

    /**
     * Sets if the initial database check shall be skipped upon build().
     *
     * @return this instance
     */
    BUILDER withSkipCheckDatabaseConnectivity();

    /**
     * Sets if the initial validation of the configuration shall be skipped upon
     * build(). Useful for testing.
     *
     *
     * @return this instance
     */
    BUILDER withSkipValidateRuntimeConfig();

    /**
     * Adds a custom manager.
     *
     * @param <M> the manager type
     *
     * @param managerImplType the manager implementation class
     * @return this instance
     */
    <M extends Manager<?>> BUILDER withManager(Class<M> managerImplType);

    /**
     * Adds a custom injectable implementation class.
     *
     * @param injectableClass the implementation class
     * @return this instance
     */
    BUILDER withInjectable(Class<?> injectableClass);

    /**
     * Adds a custom injectable implementation class.
     *
     * @param key the key to store it under
     * @param injectableClass the implementation class
     * @return this instance
     */
    BUILDER withInjectable(String key, Class<?> injectableClass);

    /**
     * Adds a custom bundle of injectable implementation classes.
     *
     * @param bundleClass to use when adding injectables
     * @return this instance
     */
    public BUILDER withBundle(Class<? extends InjectBundle> bundleClass);

    enum LogType {
        STREAM, PERSIST, UPDATE, REMOVE, APPLICATION_BUILDER;
    }

    public BUILDER withLoggingOf(LogType logType);

    /**
     * Builds this application.
     *
     * @return the built application
     */
    APP build();

    /**
     * Creates and returns a new empty ApplicationBuilder.
     *
     * @param <BUILDER> ApplicationBuilder type
     * @return a new empty ApplicationBuilder
     */
    @SuppressWarnings("unchecked")
    public static <BUILDER extends ApplicationBuilder<Speedment, BUILDER>> BUILDER empty() {
        return (BUILDER)new DefaultApplicationBuilder(EmptyApplicationMetadata.class);
    }

    /**
     * Creates and returns a new standard ApplicationBuilder. The configuration
     * is read from a JSON file.
     * 
     * @param <BUILDER> ApplicationBuilder type
     * @return a new standard ApplicationBuilder
     */
    @SuppressWarnings("unchecked")
    public static <BUILDER extends ApplicationBuilder<Speedment, BUILDER>> BUILDER standard() {
        return (BUILDER)new DefaultApplicationBuilder(DefaultApplicationMetadata.class);
    }

    /**
     * Creates and returns a new ApplicationBuilder configured with the given
     * ApplicationMetadata class.
     *
     * @param <BUILDER> ApplicationBuilder type
     * @param applicationMetadataclass with configuration
     *
     * @return a new ApplicationBuilder configured with the given
     * ApplicationMetadata class
     */
    @SuppressWarnings("unchecked")
    public static <BUILDER extends ApplicationBuilder<Speedment, BUILDER>> BUILDER create(Class<? extends ApplicationMetadata> applicationMetadataclass) {
        return (BUILDER)new DefaultApplicationBuilder(applicationMetadataclass);
    }

}
