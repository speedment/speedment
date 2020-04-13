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
package com.speedment.tool.core.internal.util;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.InjectorBuilder;
import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.Config;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.common.json.Json;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.generator.translator.TranslatorManager;
import com.speedment.runtime.application.provider.DefaultApplicationBuilder;
import com.speedment.runtime.config.*;
import com.speedment.runtime.config.mutator.ProjectMutator;
import com.speedment.runtime.config.util.DocumentTranscoder;
import com.speedment.runtime.core.Speedment;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.InfoComponent;
import com.speedment.runtime.core.component.PasswordComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.db.DbmsMetadataHandler;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.util.ProgressMeasure;
import com.speedment.runtime.core.util.ProgressMeasureUtil;
import com.speedment.runtime.typemapper.TypeMapper;
import com.speedment.tool.config.DbmsProperty;
import com.speedment.tool.config.ProjectProperty;
import com.speedment.tool.config.component.DocumentPropertyComponent;
import com.speedment.tool.core.MainApp;
import com.speedment.tool.core.brand.Palette;
import com.speedment.tool.core.component.UserInterfaceComponent;
import com.speedment.tool.core.component.UserInterfaceComponent.ReuseStage;
import com.speedment.tool.core.exception.SpeedmentToolException;
import com.speedment.tool.core.resource.FontAwesome;
import com.speedment.tool.core.util.OutputUtil;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Predicate;

import static com.speedment.runtime.application.provider.DefaultApplicationMetadata.METADATA_LOCATION;
import static com.speedment.tool.core.util.OutputUtil.error;
import static com.speedment.tool.core.util.OutputUtil.success;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toSet;
import static javafx.application.Platform.runLater;

/**
 *
 * @author Emil Forslund
 * @since 3.0.0
 */
@InjectKey(ConfigFileHelper.class)
public final class ConfigFileHelper {

    private static final Logger LOGGER = LoggerManager.getLogger(ConfigFileHelper.class);
    private static final String DOT_JSON = ".json";
    public static final String DEFAULT_CONFIG_LOCATION = "src/main/json/speedment" + DOT_JSON;

    private static final Predicate<File> OPEN_FILE_CONDITIONS = file
        -> file != null
        && file.exists()
        && file.isFile()
        && file.canRead()
        && file.getName().toLowerCase().endsWith(DOT_JSON);

    private final DocumentPropertyComponent documentPropertyComponent;
    private final DbmsHandlerComponent dbmsHandlerComponent;
    private final PasswordComponent passwordComponent;
    private final TranslatorManager translatorManager;
    private final ProjectComponent projectComponent;
    private final InfoComponent infoComponent;

    private UserInterfaceComponent userInterfaceComponent;
    private Injector injector;
    private File currentlyOpenFile;

    public ConfigFileHelper(
        final DocumentPropertyComponent documentPropertyComponent,
        final DbmsHandlerComponent dbmsHandlerComponent,
        final PasswordComponent passwordComponent,
        final TranslatorManager translatorManager,
        final ProjectComponent projectComponent,
        final InfoComponent infoComponent,
        @Config(name = METADATA_LOCATION, value = DEFAULT_CONFIG_LOCATION) final File currentlyOpenFile
    ) {
        this.documentPropertyComponent = requireNonNull(documentPropertyComponent);
        this.dbmsHandlerComponent = requireNonNull(dbmsHandlerComponent);
        this.passwordComponent = requireNonNull(passwordComponent);
        this.translatorManager = requireNonNull(translatorManager);
        this.projectComponent = requireNonNull(projectComponent);
        this.infoComponent = requireNonNull(infoComponent);
        this.currentlyOpenFile = requireNonNull(currentlyOpenFile);
    }

    @ExecuteBefore(State.INITIALIZED)
    public void setInjector(Injector injector) {
        this.injector = requireNonNull(injector);
    }

    @ExecuteBefore(State.INITIALIZED)
    public void setUserInterfaceComponent(UserInterfaceComponent userInterfaceComponent) {
        this.userInterfaceComponent = requireNonNull(userInterfaceComponent);
    }

    public boolean isFileOpen() {
        return currentlyOpenFile != null;
    }

    public File getCurrentlyOpenFile() {
        return currentlyOpenFile;
    }

    public void setCurrentlyOpenFile(File currentlyOpenFile) {
        this.currentlyOpenFile = currentlyOpenFile; // Nullable
    }

    public void loadFromDatabaseAndSaveToFile() {
        final ProjectProperty project = new ProjectProperty();
        final Project loaded = projectComponent.getProject();


        if (loaded != null) {
            project.merge(documentPropertyComponent, loaded);
        } else {
            throw new SpeedmentToolException(
                "Can't load from database unless either a dbms and schema "
                + "is specified or a config file is present."
            );
        }

        final Project projectCopy = Project.createImmutable(project);

        project.dbmses().map(dbms -> {
            final DbmsType dbmsType = dbmsHandlerComponent.findByName(dbms.getTypeName())
                .orElseThrow(() -> new SpeedmentToolException(
                "Could not find dbms type with name '" + dbms.getTypeName() + "'."
            ));

            LOGGER.info(String.format(
                "Reloading from dbms '%s' on %s:%d.",
                dbms.getName(),
                dbms.getIpAddress().orElse("127.0.0.1"),
                dbms.getPort().orElseGet(dbmsType::getDefaultPort)
            ));

            final Predicate<String> schemaFilter;
            final Set<String> schemaNames = dbms.schemas()
                .map(Schema::getName).collect(toSet());

            if (schemaNames.isEmpty()) {
                schemaFilter = s -> true;
            } else {
                schemaFilter = schemaNames::contains;
            }

            return dbmsType.getMetadataHandler()
                .readSchemaMetadata(dbms, ProgressMeasure.create(), schemaFilter);
        }).forEachOrdered(fut -> {
            try {
                final Project newProject = fut.join();
                synchronized (project) {
                    setTypeMappersFrom(newProject, projectCopy);
                    project.merge(documentPropertyComponent, newProject);
                }
            } catch (final CancellationException ex) {
                throw new SpeedmentToolException("Cancellation in execution of reload sequence.", ex);
            } catch (final CompletionException ex) {
                throw new SpeedmentToolException("Reload sequence completed with exception.", ex);
            }
        });

        saveConfigFile(
            currentlyOpenFile == null
                ? new File(DEFAULT_CONFIG_LOCATION)
                : currentlyOpenFile,
            project,
            false
        );
    }

    public boolean loadFromDatabase(DbmsProperty dbms, String schemaName) {
        final Runnable restore = () -> {
            passwordComponent.put(dbms, null); // Clear password

            userInterfaceComponent.projectProperty()
                .observableListOf(ProjectUtil.DBMSES)
                .remove(dbms); // Remove dbms from observable model
        };

        try {
            // Create an immutable copy of the tree and store in the ProjectComponent
            final Project projectCopy = Project.createImmutable(userInterfaceComponent.projectProperty());
            projectComponent.setProject(projectCopy);

            // TODO: This method needs to be refactored. We create multiple
            // copies of the config tree, but most of the copies are not deep
            // but shallow, meaning that as soon as you start traversing them
            // you risk changing the original map. Not only that, when the new
            // nodes are created, they are given a reference to the old mutable
            // parent instance, meaning that they can mutate the existing tree.
            // It seems to be working for now, mainly because the metadata
            // handler already does a second deep safe-copy of the given tree,
            // but that is both unnecessary and very bad for load performance.
            // We should try to limit the method to a maximum of one deep copy.
            // Create a copy of everything in Dbms EXCEPT the schema key. This
            // is a hack to prevent duplication errors that would otherwise
            // occur if you change name of a node and reload.
            final Map<String, Object> dbmsData
                = new ConcurrentSkipListMap<>(dbms.getData());

            dbmsData.remove(DbmsUtil.SCHEMAS);
            final Dbms dbmsCopy = Dbms.create(dbms.getParentOrThrow(), dbmsData);

            // Find the DbmsHandler to use when loading the metadata
            final DbmsMetadataHandler dh = dbmsHandlerComponent.findByName(dbmsCopy.getTypeName())
                .map(DbmsType::getMetadataHandler)
                .orElseThrow(() -> new SpeedmentToolException(
                "Could not find metadata handler for DbmsType '" + dbmsCopy.getTypeName() + "'."
            ));

            // Begin executing the loading with a progress measurer
            final ProgressMeasure progress = ProgressMeasure.create();
            final CompletableFuture<Boolean> future
                = dh.readSchemaMetadata(dbmsCopy, progress, schemaName::equalsIgnoreCase)
                    .handleAsync((p, ex) -> {
                        progress.setProgress(ProgressMeasureUtil.DONE);

                        // If the loading was successful
                        if (ex == null && p != null) {
                            // Make sure any old data is cleared before merging in
                            // the new state from the database.
                            dbms.schemasProperty().clear();

                            // printTypeMappers("From DB", p);
                            setTypeMappersFrom(p, projectCopy);

                            userInterfaceComponent.projectProperty()
                                .merge(documentPropertyComponent, p);

                            return true;
                        } else {
                            restore.run();
                            runLater(() ->
                                userInterfaceComponent.showError("Error Connecting to Database",
                                    "A problem occured with establishing the database connection.", ex
                                )
                            );
                            return false;
                        }
                    });

            userInterfaceComponent.showProgressDialog("Loading Database Metadata", progress, future);

            final boolean status = future.join();

            if (status) {
                userInterfaceComponent.showNotification(
                    "Database metadata has been loaded.",
                    FontAwesome.DATABASE,
                    Palette.INFO
                );
            } else {
                restore.run();
            }

            return status;

        } catch (final CancellationException | CompletionException ex) {
            restore.run();
            userInterfaceComponent.showError("Error Executing Connection Task",
                "The execution of certain tasks could not be completed.", ex
            );
        }

        return false;
    }

    /**
     * Set any compatible type mappers in Project <code>{@code to}</code> found in Project <code>{@code from}</code>.
     **
     * @param to the project to mutate
     * @param from the project defining the type mappers
     */
    private void setTypeMappersFrom(Project to, Project from) {
        from.dbmses().forEach(dbms ->
            dbms.schemas().forEach(schema ->
                schema.tables().forEach(table ->
                    table.columns().filter(c -> c.getTypeMapper().isPresent()).forEach(column -> {
                        String mapperName = column.getTypeMapper().get();
                        try {
                            //noinspection unchecked
                            @SuppressWarnings("unchecked")
                            Class<? extends TypeMapper<?, ?>> mapperClass = (Class<? extends TypeMapper<?, ?>>)Class.forName(mapperName);
                            setTypeMapper(to, dbms, schema, table, column,  mapperClass);
                        } catch (ClassNotFoundException | ClassCastException e) {
                            throw new IllegalStateException("Unable to find mapper class " + mapperName);
                        }
                    })
                )
            )
        );
    }

    /**
     * Set all typemappers in the given project that match dbms, schema, table, column and column database type.
     *
     * The idea is to use this method to reset TypeMappers from an old project when new metadata has been loaded.
     * We make sure to match column database type to avoid setting an incompatible mapper if the new project has
     * changed database type for the referenced column.
     *
     * @param to the project to mutate
     * @param dbms the dbms holding the typemapper
     * @param schema the schema of the typemapper
     * @param table the table of the typemapper
     * @param column the column of the typemapper
     * @param typeMapperClass the new type mapper class to set
     */
    private void setTypeMapper(Project to, Dbms dbms, Schema schema, Table table, Column column, Class<? extends TypeMapper<?, ?>> typeMapperClass) {
        to.dbmses()
            .filter(d -> d.getId().equals(dbms.getId()))
            .flatMap(Dbms::schemas)
            .filter(s -> s.getId().equals(schema.getId()))
            .flatMap(Schema::tables)
            .filter(t -> t.getId().equals(table.getId()))
            .flatMap(Table::columns)
            .filter(c -> c.getId().equals(column.getId()))
            // If the data type has changed, we do not want to keep the old mapper
            .filter(c -> c.getDatabaseType().equals(column.getDatabaseType()))
            // Perhaps one would expect this to match a single column, so findFirst would do,
            // but fetching metadata from the database seems to create multiple instances of
            // similar dbmses in the same Project, so we make sure to do this forEach copy.
            .forEach(c -> c.mutator().setTypeMapper(typeMapperClass));
    }

    public void loadConfigFile(File file, ReuseStage reuse) {
        if (OPEN_FILE_CONDITIONS.test(file)) {
            try {
                switch (reuse) {
                    case CREATE_A_NEW_STAGE:
                        final Stage newStage = new Stage();
                        final InjectorBuilder injectorBuilder = injector.newBuilder()
                            .withParam(METADATA_LOCATION, DEFAULT_CONFIG_LOCATION);
                        final Speedment newSpeedment
                            = new DefaultApplicationBuilder(injectorBuilder)
                                .build();

                        MainApp.setInjector(newSpeedment.getOrThrow(Injector.class));
                        final MainApp main = new MainApp();

                        try {
                            main.start(newStage);
                        } catch (final Exception ex) {
                            throw new SpeedmentToolException(ex);
                        }

                        break;

                    case USE_EXISTING_STAGE:
                        final Project p = DocumentTranscoder.load(file.toPath(), this::fromJson);
                        userInterfaceComponent.projectProperty().merge(documentPropertyComponent, p);
                        break;

                    default:
                        throw new IllegalStateException(
                            "Unknown enum constant '" + reuse + "'."
                        );
                }

                currentlyOpenFile = file;
            } catch (final SpeedmentToolException ex) {
                LOGGER.error(ex);
                userInterfaceComponent.log(error(ex.getMessage()));
                userInterfaceComponent.showError("Could not load project", ex.getMessage(), ex);
            }
        } else {
            userInterfaceComponent.showError(
                "Could not read " + DOT_JSON + " file",
                "The file '" + file.getAbsoluteFile().getName()
                + "' could not be read.",
                null
            );
        }
    }

    private Map<String, Object> fromJson(String json) {
        @SuppressWarnings("unchecked")
        final Map<String, Object> parsed = (Map<String, Object>) Json.fromJson(json);
        return parsed;
    }

    public void saveConfigFile() {
        final FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Save JSON File");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"));

        if (currentlyOpenFile == null) {
            final Path path = Paths.get(DEFAULT_CONFIG_LOCATION);
            final Path parent = path.getParent();
            if (parent == null) {
                throw new SpeedmentToolException("Unable to save " + path.toString() + " (no parent).");
            }

            try {
                if (!parent.toFile().exists()) {
                    Files.createDirectories(parent);
                }
            } catch (IOException ex) {/*
                Do nothing. Creating the parent directory is purely for
                the convenience of the user.
                 */
            }
            fileChooser.setInitialDirectory(parent.toFile());
            fileChooser.setInitialFileName(Optional.ofNullable(path.getFileName()).map(Path::toString).orElse(""));
        } else {
            fileChooser.setInitialDirectory(currentlyOpenFile.getParentFile());
            fileChooser.setInitialFileName(currentlyOpenFile.getName());
        }

        File file = fileChooser.showSaveDialog(userInterfaceComponent.getStage());
        if (file != null) {
            if (!file.getName().endsWith(DOT_JSON)) {
                file = new File(file.getAbsolutePath() + DOT_JSON);
            }

            saveConfigFile(file);
        }
    }

    public void saveCurrentlyOpenConfigFile() {
        saveConfigFile(currentlyOpenFile);
    }

    private void saveConfigFile(File file) {
        saveConfigFile(file, userInterfaceComponent.projectProperty(), true);
    }

    private void saveConfigFile(File file, ProjectProperty project, boolean isGraphical) {
        final Path path = file.toPath();
        final Path parent = path.getParent();
        if (parent == null) {
            throw new SpeedmentToolException("Unable to save " + file.toString() + " (no parent).");
        }

        try {
            if (!parent.toFile().exists()) {
                Files.createDirectories(parent);
            }

            // Set the Speedment version used to generate the code
            project.stringPropertyOf(ProjectUtil.SPEEDMENT_VERSION, () -> null)
                .setValue(infoComponent.getEditionAndVersionString());

            DocumentTranscoder.save(project, path, Json::toJson);

            if (isGraphical) {
                final String absolute = file.getAbsolutePath();
                userInterfaceComponent.log(success("Saved project file to '" + absolute + "'."));
                userInterfaceComponent.showNotification("Configuration saved.", FontAwesome.DOWNLOAD, Palette.INFO);
            }

            currentlyOpenFile = file;

        } catch (final IOException ex) {
            if (isGraphical) {
                userInterfaceComponent.showError("Could not save file", ex.getMessage(), ex);
            } else {
                throw new SpeedmentToolException(ex);
            }
        }
    }

    public void generateSources() {

        try {
            translatorManager.accept(projectComponent.getProject());

            runLater(() -> {
                userInterfaceComponent.log(OutputUtil.success(
                    "+------------: Generation completed! :------------+" + "\n"
                    //                + "| Total time       " + alignRight(stopwatch.toString(), 30) + " |\n"
                    + "| Files generated  " + alignRight("" + translatorManager.getFilesCreated(), 41) + " |\n"
                    + "+-------------------------------------------------+"
                ));

                userInterfaceComponent.showNotification(
                    "Generation completed! " + translatorManager.getFilesCreated()
                    + " files created.",
                    FontAwesome.STAR,
                    Palette.SUCCESS
                );
            });
        } catch (final Exception ex) {
            runLater(() -> {
                userInterfaceComponent.log(OutputUtil.error(
                    "+--------------: Generation failed! :-------------+" + "\n"
                    //                + "| Total time       " + alignRight(stopwatch.toString(), 30) + " |\n"
                    + "| Files generated  " + alignRight("" + translatorManager.getFilesCreated(), 41) + " |\n"
                    + "| Exception Type   " + alignRight(ex.getClass().getSimpleName(), 41) + " |\n"
                    + "+-------------------------------------------------+"
                ));

                final String msg = "Error! Failed to generate code. A " + ex.getClass().getSimpleName() + " was thrown.";

                LOGGER.error(ex, msg);
                userInterfaceComponent.showError("Failed to generate code", ex.getMessage(), ex);
            });
        }
    }

    private String alignRight(String substring, int totalWidth) {
        final String formatString = "%" + totalWidth + "s";
        return String.format(formatString, substring);
    }

    public void clearTablesAndSaveToFile() {
        final ProjectMutator<? extends Project> projectMutator =
            Project.deepCopy(DocumentTranscoder.load(
                currentlyOpenFile.toPath(),
                this::fromJson
            )
        ).mutator();

        projectMutator.setSpeedmentVersion(infoComponent.getEditionAndVersionString());
        final Project project = projectMutator.document();

        LOGGER.info("clearing tables");
        project.dbmses().forEach(dbms -> {
            LOGGER.info("dbms: " + dbms.getName());
            dbms.schemas().forEach(schema -> {
                LOGGER.info("schema: " + schema.getName());
                if (schema.getData().containsKey("tables")) {
                    LOGGER.info("Removing " + schema.tables().count());
                    schema.getData().remove("tables");
                } else {
                    LOGGER.info("No tables to remove");
                }
            });
        });

        // always start with a new file.
        if (currentlyOpenFile.isFile()) {
            if (currentlyOpenFile.exists() && !currentlyOpenFile.delete()) {
                userInterfaceComponent.log(OutputUtil.warning("Unable to delete " + currentlyOpenFile));
            }
            DocumentTranscoder.save(project, currentlyOpenFile.toPath(), Json::toJson);
        } else {
            throw new SpeedmentToolException(currentlyOpenFile.toPath() + " is not a file");
        }
    }

    public void saveProjectToCurrentlyOpenFile(ProjectProperty project) {
        saveConfigFile(getCurrentlyOpenFile(), project, false);
    }
}
