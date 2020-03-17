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
package com.speedment.maven.abstractmojo;

import com.speedment.common.injector.InjectBundle;
import com.speedment.generator.core.GeneratorBundle;
import com.speedment.generator.translator.internal.component.CodeGenerationComponentImpl;
import com.speedment.maven.component.MavenPathComponent;
import com.speedment.maven.internal.util.ConfigUtil;
import com.speedment.maven.parameter.ConfigParam;
import com.speedment.maven.typemapper.Mapping;
import com.speedment.runtime.application.ApplicationBuilders;
import com.speedment.runtime.connector.mariadb.MariaDbBundle;
import com.speedment.runtime.connector.mysql.MySqlBundle;
import com.speedment.runtime.connector.postgres.PostgresBundle;
import com.speedment.runtime.connector.sqlite.SqliteBundle;
import com.speedment.runtime.core.ApplicationBuilder;
import com.speedment.runtime.core.Speedment;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.StringUtils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.speedment.maven.component.MavenPathComponent.MAVEN_BASE_DIR;
import static com.speedment.runtime.application.provider.DefaultApplicationMetadata.METADATA_LOCATION;
import static com.speedment.tool.core.internal.util.ConfigFileHelper.DEFAULT_CONFIG_LOCATION;
import static java.util.Objects.requireNonNull;

/**
 * The abstract base implementation for all the Speedment Mojos.
 *
 * @author Emil Forslund
 */
public abstract class AbstractSpeedmentMojo extends AbstractMojo {

    static final String SPECIFIED_CLASS = "Specified class ";
    private static final Path DEFAULT_CONFIG = Paths.get(DEFAULT_CONFIG_LOCATION);
    private static final Consumer<ApplicationBuilder<?, ?>> NOTHING = builder -> {};

    private final Consumer<ApplicationBuilder<?, ?>> configurer;

    public @Parameter(defaultValue = "${dbms.connectionUrl}") String dbmsConnectionUrl;

    AbstractSpeedmentMojo() {this(NOTHING);}

    AbstractSpeedmentMojo(Consumer<ApplicationBuilder<?, ?>> configurer) {
        this.configurer = requireNonNull(configurer);
    }

    protected abstract MavenProject project();
    protected abstract boolean debug();
    protected abstract String dbmsHost();
    protected abstract int dbmsPort();
    protected abstract String dbmsUsername();
    protected abstract String dbmsPassword();

    protected String dbmsConnectionUrl() {
        if (StringUtils.isNotBlank(dbmsConnectionUrl)) {
            return dbmsConnectionUrl;
        } else return null;
    }

    protected abstract String[] components();
    protected abstract Mapping[] typeMappers();
    protected abstract ConfigParam[] parameters();
    protected abstract String launchMessage();
    protected abstract String getConfigFile();
    protected abstract void execute(Speedment speedment)
        throws MojoExecutionException, MojoFailureException;

    
    protected Path configLocation() {
        final String top = StringUtils.isBlank(getConfigFile())
            ? DEFAULT_CONFIG_LOCATION 
            : getConfigFile();

        getLog().info(project().toString());

        return project()
            .getBasedir()
            .toPath()
            .resolve(top);
    }
    
    @Override
    public final void execute() throws MojoExecutionException, MojoFailureException {

        final ApplicationBuilder<?, ?> builder = createBuilder();
        builder.withComponent(MavenPathComponent.class);
        builder.withParam(MAVEN_BASE_DIR, project().getBasedir().toString());

        configurer.accept(builder);

        if (debug()) {
            builder.withLogging(ApplicationBuilder.LogType.APPLICATION_BUILDER);
        }

        builder.withSkipCheckDatabaseConnectivity();
        final Speedment speedment = builder.build();

        getLog().info(launchMessage());
        execute(speedment);
    }

    /**
     * Returns {@code true} if the default configuration file is non-null,
     * exists and is readable. If, not, {@code false} is returned and an
     * appropriate message is shown in the console.
     *
     * @return {@code true} if available, else {@code false}
     */
    protected final boolean hasConfigFile() {
        return hasConfigFile(configLocation());
    }

    /**
     * Returns if the specified file is non-null, exists and is readable. If,
     * not, {@code false} is returned and an appropriate message is shown in the
     * console.
     *
     * @param file the config file to check
     * @return {@code true} if available, else {@code false}
     */
    protected final boolean hasConfigFile(Path file) {
        return ConfigUtil.hasConfigFile(file, getLog());
    }

    @SuppressWarnings("unchecked")
    protected final ClassLoader getClassLoader()
        throws MojoExecutionException, DependencyResolutionRequiredException {

        final MavenProject project = project();

        final List<String> classpathElements = new ArrayList<>();
        classpathElements.addAll(project.getCompileClasspathElements());
        classpathElements.addAll(project.getRuntimeClasspathElements());
        classpathElements.add(project.getBuild().getOutputDirectory());

        final List<URL> projectClasspathList = new ArrayList<>();

        for (final String element : classpathElements) {
            try {
                projectClasspathList.add(new File(element).toURI().toURL());
            } catch (final MalformedURLException ex) {
                throw new MojoExecutionException(
                    element + " is an invalid classpath element", ex
                );
            }
        }

        return new URLClassLoader(
            projectClasspathList.toArray(new URL[projectClasspathList.size()]),
            Thread.currentThread().getContextClassLoader()
        );
    }

    private ApplicationBuilder<?, ?> createBuilder() throws MojoExecutionException {
        final ApplicationBuilder<?, ?> result;

        // Create the ClassLoader
        final ClassLoader classLoader;
        try {
            classLoader = getClassLoader();
        } catch (final DependencyResolutionRequiredException ex)  {
            throw new MojoExecutionException(ex.toString(), ex);
        }

        // Configure config file location
        if (hasConfigFile()) {
            result = ApplicationBuilders.standard(classLoader)
                .withParam(METADATA_LOCATION, configLocation().toAbsolutePath().toString());
        } else if (hasConfigFile(DEFAULT_CONFIG)) {
            result = ApplicationBuilders.standard(classLoader)
                .withParam(METADATA_LOCATION, DEFAULT_CONFIG_LOCATION);
        } else {
            result = ApplicationBuilders.empty(classLoader);
        }

        //
        result.withSkipCheckDatabaseConnectivity();

        // Configure manual database settings
        if (StringUtils.isNotBlank(dbmsHost())) {
            result.withIpAddress(dbmsHost());
            getLog().info("Custom database host '" + dbmsHost() + "'.");
        }

        if (dbmsPort() != 0) {
            result.withPort(dbmsPort());
            getLog().info("Custom database port '" + dbmsPort() + "'.");
        }

        if (StringUtils.isNotBlank(dbmsUsername())) {
            result.withUsername(dbmsUsername());
            getLog().info("Custom database username '" + dbmsUsername() + "'.");
        }

        if (StringUtils.isNotBlank(dbmsPassword())) {
            result.withPassword(dbmsPassword());
            getLog().info("Custom database password '********'.");
        }

        if (StringUtils.isNotBlank(dbmsConnectionUrl())) {
            result.withConnectionUrl(dbmsConnectionUrl());
            getLog().info("Custom connection URL '" + dbmsConnectionUrl() + "'.");
        }

        // Add mandatory components that are not included in 'runtime'
        result
            .withBundle(GeneratorBundle.class)
            .withComponent(CodeGenerationComponentImpl.class)
            .withComponent(MavenPathComponent.class);

        // Add optional components that are no longer included in 'runtime' but
        // are nice to have available without explicit installation
        result
            .withBundle(MySqlBundle.class)
            .withBundle(MariaDbBundle.class)
            .withBundle(PostgresBundle.class)
            .withBundle(SqliteBundle.class);

        configureBuilder(result); // Add MOJO specific components (if any)

        // Add any extra type mappers requested by the user
        result.withComponent(TypeMapperInstaller.class, () -> new TypeMapperInstaller(typeMappers()));

        // Add extra components requested by the user
        final String[] components = components();
        if (components != null) {
            for (final String component : components) {
                tryAddExtraComponent(result, classLoader, component);
            }
        }

        // Set parameters configured in the pom.xml
        final ConfigParam[] parameters = parameters();
        if (parameters != null) {
            for (final ConfigParam param : parameters()) {
                result.withParam(param.getName(), param.getValue());
            }
        }

        // Return the resulting builder.
        return result;
    }

    private void tryAddExtraComponent(ApplicationBuilder<?, ?> result, ClassLoader classLoader, String component) throws MojoExecutionException {
        try {
            final Class<?> uncasted = classLoader.loadClass(component);

            if (InjectBundle.class.isAssignableFrom(uncasted)) {
                @SuppressWarnings("unchecked")
                final Class<? extends InjectBundle> casted
                    = (Class<? extends InjectBundle>) uncasted;
                result.withBundle(casted);
            } else {
                result.withComponent(uncasted);
            }

        } catch (final ClassNotFoundException ex) {
            throw new MojoExecutionException(
                SPECIFIED_CLASS + "'" + component + "' could not be "
                + "found on class path. Has the dependency been "
                + "configured properly?", ex
            );
        }
    }

    protected void configureBuilder( ApplicationBuilder<?, ?>  builder) {}



}
