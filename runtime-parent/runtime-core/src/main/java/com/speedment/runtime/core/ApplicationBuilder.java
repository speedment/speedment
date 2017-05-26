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
package com.speedment.runtime.core;

import com.speedment.common.injector.InjectBundle;
import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.Schema;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.identifier.trait.HasColumnName;
import com.speedment.runtime.config.identifier.trait.HasDbmsName;
import com.speedment.runtime.config.identifier.trait.HasSchemaName;
import com.speedment.runtime.config.identifier.trait.HasTableName;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.runtime.core.internal.DefaultApplicationBuilder;
import com.speedment.runtime.core.internal.DefaultApplicationMetadata;
import com.speedment.runtime.core.internal.EmptyApplicationMetadata;
import com.speedment.runtime.core.manager.Manager;
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
public interface ApplicationBuilder<
        APP extends Speedment, BUILDER extends ApplicationBuilder<APP, BUILDER>> {

    /**
     * Configures a parameter for the identified dbms. The consumer will then be
     * applied after the configuration has been read and after the System
     * properties have been applied.
     *
     * @param <ENTITY> entity type
     * @param <I> the identifier type
     * @param id of the dbms
     * @param consumer the consumer to apply
     * @return this instance
     */
    default <ENTITY, I extends HasDbmsName> BUILDER withDbms(I id, Consumer<Dbms> consumer) {
        return withDbms(id, (app, t) -> consumer.accept(t));
    }

    /**
     * Configures a parameter for the identified dbms. The consumer will then be
     * applied after the configuration has been read and after the System
     * properties have been applied.
     *
     * @param <ENTITY> entity type
     * @param <I> the identifier type
     * @param identifier of the dbms
     * @param consumer the consumer to apply
     * @return this instance
     */
    default <ENTITY, I extends HasDbmsName> BUILDER withDbms(I identifier, BiConsumer<Injector, Dbms> consumer) {
        return with(
            Dbms.class,
            identifier.getDbmsName(),
            consumer
        );
    }

    /**
     * Configures a parameter for the identified schema. The consumer will then
     * be applied after the configuration has been read and after the System
     * properties have been applied.
     *
     * @param <ENTITY> entity type
     * @param <I> the identifier type
     * @param id of the schema
     * @param consumer the consumer to apply
     * @return this instance
     */
    default <ENTITY, I extends HasDbmsName & HasSchemaName> BUILDER withSchema(I id, Consumer<Schema> consumer) {
        return withSchema(id, (app, t) -> consumer.accept(t));
    }

    /**
     * Configures a parameter for the identified schema. The consumer will then
     * be applied after the configuration has been read and after the System
     * properties have been applied.
     *
     * @param <ENTITY> entity type
     * @param <I> the identifier type
     * @param identifier of the schema
     * @param consumer the consumer to apply
     * @return this instance
     */
    default <ENTITY, I extends HasDbmsName & HasSchemaName> BUILDER withSchema(I identifier, BiConsumer<Injector, Schema> consumer) {
        return with(
            Schema.class,
            identifier.getDbmsName() + "." + identifier.getSchemaName(),
            consumer
        );
    }

    /**
     * Configures a parameter for the identified table. The consumer will then
     * be applied after the configuration has been read and after the System
     * properties have been applied.
     *
     * @param <ENTITY> entity type
     * @param <I> the identifier type
     * @param id of the table
     * @param consumer the consumer to apply
     * @return this instance
     */
    default <ENTITY, I extends HasDbmsName & HasSchemaName & HasTableName> BUILDER withTable(I id, Consumer<Table> consumer) {
        return withTable(id, (app, t) -> consumer.accept(t));
    }

    /**
     * Configures a parameter for the identified table. The consumer will then
     * be applied after the configuration has been read and after the System
     * properties have been applied.
     *
     * @param <ENTITY> entity type
     * @param <I> the identifier type
     * @param identifier of the table
     * @param consumer the consumer to apply
     * @return this instance
     */
    default <ENTITY, I extends HasDbmsName & HasSchemaName & HasTableName> BUILDER withTable(I identifier, BiConsumer<Injector, Table> consumer) {
        return with(
            Table.class,
            identifier.getDbmsName() + "." + identifier.getSchemaName() + "." + identifier.getTableName(),
            consumer
        );
    }

    /**
     * Configures a parameter for the identified table. The consumer will then
     * be applied after the configuration has been read and after the System
     * properties have been applied.
     *
     * @param <ENTITY> entity type
     * @param <I> the identifier type
     * @param id of the column
     * @param consumer the consumer to apply
     * @return this instance
     */
    default <ENTITY, I extends HasDbmsName & HasSchemaName & HasTableName & HasColumnName> BUILDER withColumn(I id, Consumer<Column> consumer) {
        return withColumn(id, (app, t) -> consumer.accept(t));
    }

    /**
     * Configures a parameter for the identified column. The consumer will then
     * be applied after the configuration has been read and after the System
     * properties have been applied.
     *
     * @param <ENTITY> entity type
     * @param <I> the identifier type
     * @param id of the column
     * @param consumer the consumer to apply
     * @return this instance
     */
    default <ENTITY, I extends HasDbmsName & HasSchemaName & HasTableName & HasColumnName> BUILDER withColumn(I id, BiConsumer<Injector, Column> consumer) {
        return with(
            Column.class,
            id.getDbmsName() + "." + id.getSchemaName() + "." + id.getTableName() + "." + id.getColumnName(),
            consumer
        );
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
    <C extends Document & HasEnabled> BUILDER with(Class<C> type, Consumer<C> consumer);

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
     * Configures a password for the identified dbms. The password will then be
     * applied after the configuration has been read and after the System
     * properties have been applied.
     * <p>
     * This will not be saved in any configuration files!
     *
     * @param <I> identification type
     * @param id the identification of the dbms
     * @param password to use for the identified dbms
     * @return this instance
     */
    default <I extends HasDbmsName> BUILDER withPassword(I id, char[] password) {
        return withPassword(id.getDbmsName(), password);
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
     * Configures a password for the identified dbms. The password will then be
     * applied after the configuration has been read and after the System
     * properties have been applied.
     * <p>
     * This will not be saved in any configuration files!
     *
     * @param <I> identification type
     * @param id the identification of the dbms
     * @param password to use for the identified dbms
     * @return this instance
     */
    default <I extends HasDbmsName> BUILDER withPassword(I id, String password) {
        return withPassword(id.getDbmsName(), password);
    }

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
     * Configures a username for the identified dbms. The username will then be
     * applied after the configuration has been read and after the System
     * properties have been applied.
     * <p>
     * This will not be saved in any configuration files!
     *
     * @param <I> identification type
     * @param id the identification of the dbms
     * @param username to use for the identified dbms
     * @return this instance
     */
    default <I extends HasDbmsName> BUILDER withUsername(I id, String username) {
        return withUsername(id.getDbmsName(), username);
    }

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
     * Configures an IP Address for the identified dbms. The IP Address will
     * then be applied after the configuration has been read and after the
     * System properties have been applied.
     * <p>
     * This will not be saved in any configuration files!
     *
     * @param <I> identification type
     * @param id the identification of the dbms
     * @param ipAddress to use for the identified dbms
     * @return this instance
     */
    default <I extends HasDbmsName> BUILDER withIpAddress(I id, String ipAddress) {
        return withIpAddress(id.getDbmsName(), ipAddress);
    }

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
     * Configures a port for the identified dbms. The port will then be applied
     * after the configuration has been read and after the System properties
     * have been applied.
     * <p>
     * This will not be saved in any configuration files!
     *
     * @param <I> identification type
     * @param id the identification of the dbms
     * @param port to use for the identified dbms
     * @return this instance
     */
    default <I extends HasDbmsName> BUILDER withPort(I id, int port) {
        return withPort(id.getDbmsName(), port);
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
     * Configures a connection URL for the identified dbms. The URL will then be
     * applied after the configuration has been read and after the System
     * properties have been applied.
     * <p>
     * This will not be saved in any configuration files!
     *
     * @param <I> identification type
     * @param id the identification of the dbms
     * @param connectionUrl to use for the identified dbms
     * @return this instance
     */
    default <I extends HasDbmsName> BUILDER withConnectionUrl(I id, String connectionUrl) {
        return withConnectionUrl(id.getDbmsName(), connectionUrl);
    }

    /**
     * Sets that the initial database check shall be skipped upon build().
     *
     * @return this instance
     */
    BUILDER withSkipCheckDatabaseConnectivity();

    /**
     * Sets that the initial validation of the configuration shall be skipped
     * upon build(). Useful for testing.
     *
     *
     * @return this instance
     */
    BUILDER withSkipValidateRuntimeConfig();

    /**
     * Sets that the logo printout shall be skipped upon build().
     *
     *
     * @return this instance
     */
    BUILDER withSkipLogoPrintout();

    /**
     * Adds a custom manager.
     *
     * @param <M> the manager type
     * @param managerImplType the manager implementation class
     * @return this instance
     */
    <M extends Manager<?>> BUILDER withManager(Class<M> managerImplType);

    /**
     * Adds a custom component implementation class. The specified class will be
     * instantiated using its default constructor and fields annotated with
     * {@link Inject} will be dependency injected. Methods annotated with
     * {@link ExecuteBefore} will also be executed as part of the application
     * configuration phase.
     * <p>
     * Note: If a component class is specifying the same {@link InjectKey } as
     * an existing class previously added to this ApplicationBuilder, then the
     * last class will be associated with that {@link InjectKey }. Thus, the 
     * order of calls to {@link #withComponent(java.lang.Class) } and
     * {@link #withBundle(java.lang.Class) } is significant.
     *
     * @param componentClass the implementation class
     * @return this instance
     */
    BUILDER withComponent(Class<?> componentClass);

    /**
     * Adds a custom component implementation class. The specified class will be
     * instantiated using its default constructor and fields annotated with
     * {@link Inject} will be dependency injected. Methods annotated with
     * {@link ExecuteBefore} will also be executed as part of the application
     * configuration phase.
     *
     * @deprecated The parameter {@code key} is not forwarded to the dependency
     * injection framework, so this method has exactly the same behavior as
     * {@link #withComponent(java.lang.Class) }, so it is redundant.
     *
     * @param key the key to store it under
     * @param componentClass the implementation class
     * @return this instance
     */
    @Deprecated
    BUILDER withComponent(String key, Class<?> componentClass);

    /**
     * Adds a custom bundle of dependency injectable implementation classes.
     * <p>
     * The specified classes will be instantiated using its default constructor
     * and fields annotated with {@link Inject} will be dependency injected.
     * Methods annotated with {@link ExecuteBefore} will also be executed as
     * part of the application configuration phase.
     * <p>
     * Note: If a class in the {@link InjectBundle } is specifying the same 
     * {@link InjectKey } as an existing class previously added to this
     * ApplicationBuilder, then the last class will be associated with that 
     * {@link InjectKey }. Thus, the order of calls to this method is 
     * significant.
     *
     * @param bundleClass to use when adding injectables
     * @return this instance
     */
    BUILDER withBundle(Class<? extends InjectBundle> bundleClass);

    /**
     * Adds a logging configuration to the application. A number of standard log
     * types are available in the class
     * {@link com.speedment.runtime.core.ApplicationBuilder.LogType LogType}.
     *
     * @param namer logger with the namer to turn on
     * @return this instance
     * @see com.speedment.runtime.core.ApplicationBuilder.LogType for standard
     * implementation
     */
    BUILDER withLogging(HasLogglerName namer);

    /**
     * Allows {@link java.util.stream.Stream#iterator() } and 
     * {@link java.util.stream.Stream#spliterator() } terminating operations to
     * be called on Speedment streams.
     * <p>
     * Note: if enabled, make sure to close the Stream or else database
     * connections will be consumed.
     * <p>
     * {@code
     *   try (Stream<User> userStream = users.stream()) {
     *      Iterator<User> userIterator = userStream();
     *      // Do something with the Iterator
     *   }
     *   // The stream is auto-closed and the connection (if any) is returned.
     *}
     * 
     * @return this instance
     */
    BUILDER withAllowStreamIteratorAndSpliterator();

    /**
     * Builds this application. This is expected to be the last method called on
     * this object.
     *
     * @return the built application
     */
    APP build();

    /**
     * Creates and returns a new empty {@code ApplicationBuilder}.
     *
     * @param <BUILDER> {@code ApplicationBuilder} type
     * @return a new empty ApplicationBuilder
     */
    @SuppressWarnings("unchecked")
    static <BUILDER extends ApplicationBuilder<Speedment, BUILDER>> BUILDER
        empty() {
        return (BUILDER) new DefaultApplicationBuilder(
            EmptyApplicationMetadata.class
        );
    }

    /**
     * Creates and returns a new empty {@code ApplicationBuilder}.
     *
     * @param <BUILDER> {@code ApplicationBuilder} type
     * @param classLoader the class loader to use in the injector
     * @return a new empty ApplicationBuilder
     */
    @SuppressWarnings("unchecked")
    static <BUILDER extends ApplicationBuilder<Speedment, BUILDER>> BUILDER
        empty(ClassLoader classLoader) {
        return (BUILDER) new DefaultApplicationBuilder(
            classLoader,
            EmptyApplicationMetadata.class
        );
    }

    /**
     * Creates and returns a new standard ApplicationBuilder. The configuration
     * is read from a JSON file.
     *
     * @param <BUILDER> {@code ApplicationBuilder} type
     * @return a new standard ApplicationBuilder
     */
    @SuppressWarnings("unchecked")
    static <BUILDER extends ApplicationBuilder<Speedment, BUILDER>> BUILDER
        standard() {
        return (BUILDER) new DefaultApplicationBuilder(
            DefaultApplicationMetadata.class
        );
    }

    /**
     * Creates and returns a new standard ApplicationBuilder. The configuration
     * is read from a JSON file.
     *
     * @param <BUILDER> {@code ApplicationBuilder} type
     * @param classLoader the class loader to use in the injector
     * @return a new standard ApplicationBuilder
     */
    @SuppressWarnings("unchecked")
    static <BUILDER extends ApplicationBuilder<Speedment, BUILDER>> BUILDER
        standard(ClassLoader classLoader) {
        return (BUILDER) new DefaultApplicationBuilder(
            classLoader,
            DefaultApplicationMetadata.class
        );
    }

    /**
     * Creates and returns a new ApplicationBuilder configured with the given
     * ApplicationMetadata class.
     *
     * @param <BUILDER> {@code ApplicationBuilder} type
     * @param applicationMetadataclass with configuration
     *
     * @return a new ApplicationBuilder configured with the given
     * ApplicationMetadata class
     */
    @SuppressWarnings("unchecked")
    static <BUILDER extends ApplicationBuilder<Speedment, BUILDER>> BUILDER
        create(Class<? extends ApplicationMetadata> applicationMetadataclass) {
        return (BUILDER) new DefaultApplicationBuilder(
            applicationMetadataclass
        );
    }

    /**
     * Interface used for getting logger names. See
     * {@link com.speedment.runtime.core.ApplicationBuilder.LogType LogType} for
     * standard implementations of this interface.
     *
     * @see LogType
     */
    @FunctionalInterface
    interface HasLogglerName {

        String getLoggerName();
    }

    /**
     * The type of logging to change the settings for.
     */
    enum LogType implements HasLogglerName {

        /**
         * Logging related to querying the data source.
         */
        STREAM,
        /**
         * Logging related to persisting new entities into the data source.
         */
        PERSIST,
        /**
         * Logging related to updating existing entities in the data source.
         */
        UPDATE,
        /**
         * Logging related to removing entities from the data source.
         */
        REMOVE,
        /**
         * Logging related to configurating the application platform, dependency
         * injection, component configuration etc.
         */
        APPLICATION_BUILDER,
        /**
         * Logging related to connection handling.
         */
        CONNECTION,
        /**
         * Logging related to stream optimization handling.
         */
        STREAM_OPTIMIZER;

        private final String loggerName;

        private LogType() {
            this.loggerName = "#" + name();
        }

        @Override
        public String getLoggerName() {
            return loggerName;
        }

    }

}
