package com.speedment.maven;

import com.speedment.maven.abstractmojo.AbstractEditMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;

/**
 * A Maven goal that modifies entries in the {@code speedment.json}-file.
 *
 * @author Emil Forslund
 * @since  3.1.4
 */
@Mojo(name = "edit",
    defaultPhase = LifecyclePhase.INITIALIZE,
    requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME
)
public class EditMojo extends AbstractEditMojo {}