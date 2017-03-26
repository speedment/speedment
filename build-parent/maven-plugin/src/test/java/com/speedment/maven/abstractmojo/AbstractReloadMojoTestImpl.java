package com.speedment.maven.abstractmojo;

import com.speedment.runtime.core.Speedment;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * test implementation of AbstractReloadMojo
 */
public class AbstractReloadMojoTestImpl extends AbstractReloadMojo {

    @Override
    protected String launchMessage() {
        return "test implementaiton of AbstractReloadMojo";
    }

    public void execute(Speedment speedment) throws MojoFailureException, MojoExecutionException {
        super.execute(speedment);
    }
}
