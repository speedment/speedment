package com.speedment.maven;

import com.speedment.runtime.Speedment;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.tool.internal.util.ConfigFileHelper;
import static com.speedment.tool.internal.util.ConfigFileHelper.DEFAULT_CONFIG_LOCATION;
import java.io.File;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * A maven goal that reloads the JSON configuration file
 * from the database, overwriting all manual changes.
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
@Mojo(name = "reload", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public final class ReloadMojo extends AbstractSpeedmentMojo {

    private @Parameter(defaultValue = "false") boolean debug;
    private @Parameter(defaultValue = "${dbms.host}") String dbmsHost;
    private @Parameter(defaultValue = "${dbms.port}") int dbmsPort;
    private @Parameter(defaultValue = "${dbms.username}") String dbmsUsername;
    private @Parameter(defaultValue = "${dbms.password}") String dbmsPassword;
    private @Parameter String[] components;
    private @Parameter(defaultValue = DEFAULT_CONFIG_LOCATION) File configFile;
    
    @Override
    protected void execute(Speedment speedment) throws MojoExecutionException, MojoFailureException {
        getLog().info("Saving default configuration from database to '" + configFile.getAbsolutePath() + "'.");
        
        final ConfigFileHelper helper = speedment.getOrThrow(ConfigFileHelper.class);
        
        try {
            helper.loadFromDatabaseAndSaveToFile();
        } catch (final SpeedmentException ex) {
            final String err = "An error occured while reloading.";
            getLog().error(err);
            throw new MojoExecutionException(err, ex);
        }
    }
    
    @Override
    protected boolean debug() {
        return debug;
    }

    @Override
    protected File configLocation() {
        return configFile;
    }

    @Override
    protected String[] components() {
        return components;
    }

    @Override
    protected String dbmsHost() {
        return dbmsHost;
    }

    @Override
    protected int dbmsPort() {
        return dbmsPort;
    }

    @Override
    protected String dbmsUsername() {
        return dbmsUsername;
    }

    @Override
    protected String dbmsPassword() {
        return dbmsPassword;
    }
    
    @Override
    protected String launchMessage() {
        return "Starting speedment:reload";
    }
}