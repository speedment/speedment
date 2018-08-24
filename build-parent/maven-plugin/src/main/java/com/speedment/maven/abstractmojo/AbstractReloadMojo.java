/**
 *
 * Copyright (c) 2006-2018, Speedment, Inc. All Rights Reserved.
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

import com.speedment.maven.parameter.ConfigParam;
import com.speedment.maven.typemapper.Mapping;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.internal.ProjectImpl;
import com.speedment.runtime.config.resolver.DocumentResolver;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.runtime.config.util.DocumentTranscoder;
import com.speedment.runtime.core.ApplicationBuilder;
import com.speedment.runtime.core.Speedment;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.db.DbmsMetadataHandler;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.util.ProgressMeasure;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.speedment.runtime.config.util.DocumentLoaders.jsonLoader;
import static com.speedment.tool.core.internal.util.ConfigFileHelper.DEFAULT_CONFIG_LOCATION;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

/**
 * A maven goal that reloads the JSON configuration file
 * from the database, overwriting all manual changes.
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
public abstract class AbstractReloadMojo extends AbstractSpeedmentMojo {

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject mavenProject;
    
    private @Parameter(defaultValue = "${debug}") Boolean debug;
    private @Parameter(defaultValue = "${dbms.host}") String dbmsHost;
    private @Parameter(defaultValue = "${dbms.port}") int dbmsPort;
    private @Parameter(defaultValue = "${dbms.username}") String dbmsUsername;
    private @Parameter(defaultValue = "${dbms.password}") String dbmsPassword;
    private @Parameter(defaultValue = "${dbms.schemas}") String dbmsSchemas;
    private @Parameter(defaultValue = "${components}") String[] components;
    private @Parameter(defaultValue = "${typeMappers}") Mapping[] typeMappers;
    private @Parameter ConfigParam[] parameters;
    private @Parameter(defaultValue = "${configFile}") String configFile;
        
    protected AbstractReloadMojo() {}
    
    protected AbstractReloadMojo(Consumer<ApplicationBuilder<?, ?>> configurer) { super(configurer);}
    
    @Override
    protected void execute(Speedment speedment) throws MojoExecutionException, MojoFailureException {
        getLog().info("Saving default configuration from database to '" + configLocation().toAbsolutePath() + "'.");

        final DbmsHandlerComponent dbmsHandlers = speedment.getOrThrow(DbmsHandlerComponent.class);
        final Project project = speedment.getOrThrow(ProjectComponent.class).getProject();
        final DocumentResolver resolver = DocumentResolver.create(jsonLoader());

        final AtomicReference<Map<String, Object>> projectData =
            new AtomicReference<>(resolver.copy(project.getData()));

        final List<CompletableFuture<Boolean>> tasks =
            project.dbmses().filter(HasEnabled::test).map(dbms -> {

                // Find the DbmsHandler to use when loading the metadata
                final DbmsMetadataHandler dh = dbmsHandlers.findByName(dbms.getTypeName())
                    .map(DbmsType::getMetadataHandler)
                    .orElseThrow(() -> new SpeedmentException(
                        format("Could not find metadata handler for DbmsType '%s'.",
                            dbms.getTypeName())
                    ));

                final ProgressMeasure progress = ProgressMeasure.create();
                return dh.readSchemaMetadata(dbms, progress, this::includeSchema)
                    .handleAsync((p, ex) -> {
                        progress.setProgress(ProgressMeasure.DONE);

                        // If the loading was successful
                        if (ex == null && p != null) {
                            // Make sure any old data is cleared before merging in
                            // the new state from the database.
                            projectData.updateAndGet(old -> {
                                DocumentTranscoder.save(new ProjectImpl(old), nextPath("old"));
                                final Map<String, Object> newMap = resolver.resolve(p.getData());
                                DocumentTranscoder.save(new ProjectImpl(newMap), nextPath("new"));
                                final Map<String, Object> merged = resolver.merge(old, newMap);
                                DocumentTranscoder.save(new ProjectImpl(merged), nextPath("merged"));
                                return merged;
                            });

                            return true;
                        } else {

                            if (ex != null) {
                                throw new RuntimeException(ex);
                            }

                            return false;
                        }
                    });
            }).collect(toList());

        try {
            CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0])).get();
            for (final CompletableFuture<Boolean> task : tasks) {
                if (!task.get()) {
                    throw new MojoExecutionException(
                        "One or more tasks failed."
                    );
                }
            }
        } catch (final ExecutionException | InterruptedException ex) {
            throw new MojoExecutionException(
                "Error reloading from database", ex);
        }

        try {
            DocumentTranscoder.save(
                new ProjectImpl(projectData.get()),
                configLocation()
            );
        } catch (final Exception ex) {
            throw new MojoExecutionException(
                "Error saving reloaded project.", ex);
        }

//        final Project p = DocumentTranscoder.load(configLocation());
//        speedment.getOrThrow(ProjectComponent.class).setProject(p);

//        final ConfigFileHelper helper = speedment.getOrThrow(ConfigFileHelper.class);
//
//        try {
//            helper.setCurrentlyOpenFile(configLocation().toFile());
//            helper.loadFromDatabaseAndSaveToFile();
//        } catch (final Exception ex) {
//            final String err = "An error occured while reloading.";
//            getLog().error(err);
//            throw new MojoExecutionException(err, ex);
//        }
    }

    final Map<String, LongAdder> counters = new ConcurrentHashMap<>();
    private Path nextPath(String suffix) { // only for debugging
        return Paths.get("debug").resolve(suffix + "." + counters.computeIfAbsent(suffix, s -> new LongAdder()).intValue() + ".json");
    }

    protected boolean includeSchema(String schemaName) {
        if (dbmsSchemas == null || dbmsSchemas.trim().isEmpty()) {
            return true;
        } else {
            final String[] schemas = dbmsSchemas.split(",");
            return Stream.of(schemas).anyMatch(schemaName::equalsIgnoreCase);
        }
    }
    
    @Override
    protected MavenProject project() {
        return mavenProject;
    }
    
    @Override
    protected boolean debug() {
        return debug == null ? false: debug;
    }

    public String getConfigFile() {
        return configFile;
    }

    @Override
    protected String[] components() {
        return components;
    }
    
    @Override
    protected Mapping[] typeMappers() {
        return typeMappers;
    }
    
    @Override
    protected ConfigParam[] parameters() {
        return parameters;
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

    protected final Path configLocation() {
        final String top = org.codehaus.plexus.util.StringUtils.isBlank(configFile())
            ? DEFAULT_CONFIG_LOCATION
            : configFile();

        return project()
            .getBasedir()
            .toPath()
            .resolve(top);
    }

    protected final String configFile() {
        return configFile;
    }
}