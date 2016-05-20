package com.speedment.runtime;

import com.speedment.runtime.component.Component;
import com.speedment.runtime.component.ComponentConstructor;
import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.runtime.manager.Manager;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Builder class for producing new {@link Speedment} instances.
 * 
 * @param <APP>      application that is built
 * @param <BUILDER>  the type of this builder
 * 
 * @author Emil Forslund
 * @since  2.4.0
 */
public interface SpeedmentBuilder<APP extends Speedment, BUILDER extends SpeedmentBuilder<APP, BUILDER>> {

    /**
     * Configures a parameter for the named ConfigEntity of a certain class. The
     * consumer will then be applied after the configuration has been read and
     * after the System properties have been applied.
     *
     * @param <C>       the type of ConfigEntity that is to be used
     * @param type      the class of the type of ConfigEntity that is to be used
     * @param name      the fully qualified name of the ConfigEntity.
     * @param consumer  the consumer to apply
     * @return          this instance
     */
    <C extends Document & HasEnabled> BUILDER with(Class<C> type, String name, Consumer<C> consumer);
    
    /**
     * Configures a parameter for all ConfigEntity of a certain class. The
     * consumer will then be applied after the configuration has been read and
     * after the System properties have been applied.
     *
     * @param <C>       the type of ConfigEntity that is to be used
     * @param type      the class of the type of ConfigEntity that is to be used
     * @param consumer  the consumer to apply
     * @return          this instance
     */
    <C extends Document & HasEnabled> BUILDER with(Class<C> type, Consumer<C> consumer);
    
    /**
     * Configures a password for all dbmses in this project. The password will
     * then be applied after the configuration has been read and after the
     * System properties have been applied.
     * <p>
     * This will not be saved in any configuration files!
     *
     * @param password  to use for all dbms:es in this project
     * @return          this instance
     */
    BUILDER withPassword(char[] password);
    
    /**
     * Configures a password for the named dbms. The password will then be
     * applied after the configuration has been read and after the System
     * properties have been applied.
     * <p>
     * This will not be saved in any configuration files!
     *
     * @param dbmsName  the name of the dbms
     * @param password  to use for the named dbms
     * @return          this instance
     */
    BUILDER withPassword(String dbmsName, char[] password);
    
    /**
     * Configures a password for all dbmses in this project. The password will
     * then be applied after the configuration has been read and after the
     * System properties have been applied.
     * <p>
     * This will not be saved in any configuration files!
     *
     * @param password  to use for all dbms:es in this project
     * @return          this instance
     */
    BUILDER withPassword(String password);
    
    /**
     * Configures a password for the named dbms. The password will then be
     * applied after the configuration has been read and after the System
     * properties have been applied.
     * <p>
     * This will not be saved in any configuration files!
     *
     * @param dbmsName  the name of the dbms
     * @param password  to use for the named dbms
     * @return          this instance
     */
    BUILDER withPassword(String dbmsName, String password);
    
    /**
     * Configures a username for all dbmses in this project. The username will
     * then be applied after the configuration has been read and after the
     * System properties have been applied.
     *
     * @param username  to use for all dbms:es in this project
     * @return          this instance
     */
    BUILDER withUsername(String username);
    
    /**
     * Configures a username for the named dbms. The username will then be
     * applied after the configuration has been read and after the System
     * properties have been applied.
     *
     * @param dbmsName  the name of the dbms
     * @param username  to use for the named dbms
     * @return          this instance
     */
    BUILDER withUsername(String dbmsName, String username);
    
    /**
     * Configures an IP-address for all dbmses in this project. The IP-address
     * will then be applied after the configuration has been read and after the
     * System properties have been applied.
     *
     * @param ipAddress  to use for all dbms:es in this project
     * @return           this instance
     */
    BUILDER withIpAddress(String ipAddress);
 
    /**
     * Configures an IP-address for the named dbms. The IP-address will then be
     * applied after the configuration has been read and after the System
     * properties have been applied.
     *
     * @param dbmsName   the name of the dbms
     * @param ipAddress  to use for the named dbms.
     * @return           this instance
     */
    BUILDER withIpAddress(String dbmsName, String ipAddress);
    
    /**
     * Configures a port for all dbmses in this project. The port will then be
     * applied after the configuration has been read and after the System
     * properties have been applied.
     *
     * @param port  to use for all dbms:es in this project
     * @return      this instance
     */
    BUILDER withPort(int port);
    
    /**
     * Configures a port for the named dbms. The port will then be applied after
     * the configuration has been read and after the System properties have been
     * applied.
     *
     * @param dbmsName  the name of the dbms
     * @param port      to use for the named dbms
     * @return          this instance
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
     * @param schemaName  to use for all schemas this project
     * @return            this instance
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
     * @param oldSchemaName  the current name of a schema
     * @param schemaName     to use for the named schema
     * @return               this instance
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
     * @param connectionUrl  to use for all dbms this project or null
     * @return               this instance
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
     * @param dbmsName       the name of the dbms
     * @param connectionUrl  to use for the named dbms or null
     * @return               this instance
     */
    BUILDER withConnectionUrl(String dbmsName, String connectionUrl);
    
    /**
     * Adds a (and replaces any existing) {@link Component} to the Speedment
     * runtime platform by applying the provided component constructor with the
     * internal Speedment instance.
     *
     * @param <C>  the component constructor type
     * 
     * @param componentConstructor  to use when adding/replacing a component
     * @return                      this instance
     */
    <C extends Component> BUILDER with(ComponentConstructor<C> componentConstructor);
    
    /**
     * Adds a (and replaces any existing) {@link Component} to the Speedment
     * runtime platform by first creating a new instance of the provided
     * component constructor class and then applying the component constructor
     * with the internal Speedment instance.
     *
     * @param <C>  the component constructor type
     * 
     * @param componentConstructorClass  to use when adding/replacing a component
     * @return                           this instance
     */
    <C extends Component> BUILDER with(Class<ComponentConstructor<C>> componentConstructorClass);
    
    /**
     * Sets if an initial database check shall be performed upon build(). The
     * default value is <code>true</code>
     *
     * @param <C> the component type
     * 
     * @param checkDatabaseConnectivity  if an initial database check shall be
     *                                   performed
     * @return                           this instance
     */
    <C extends Component> BUILDER withCheckDatabaseConnectivity(boolean checkDatabaseConnectivity);
    
    /**
     * Sets if an initial validation if the configuration shall be performed
     * upon build(). The default value is <code>true</code>
     *
     * @param <C>  the component type
     * 
     * @param validateRuntimeConfig  if the configuration shall be performed
     * @return                       this instance
     */
    <C extends Component> BUILDER withValidateRuntimeConfig(boolean validateRuntimeConfig);
    
    /**
     * Adds a custom manager constructor, being called before build to replace
     * an existing manager.
     *
     * @param <C>  the component type
     * 
     * @param constructor  to add
     * @return             this instance
     */
    <C extends Component> BUILDER withManager(Function<Speedment, Manager<?>> constructor);
    
    /**
     * Builds this application.
     * 
     * @return  the built application
     */
    APP build();
    
}