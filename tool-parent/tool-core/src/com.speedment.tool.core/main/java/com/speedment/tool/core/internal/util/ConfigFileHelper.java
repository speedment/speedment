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
package com.speedment.tool.core.internal.util;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.InjectorBuilder;
import com.speedment.common.injector.annotation.Config;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.common.json.Json;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.generator.translator.TranslatorManager;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Schema;
import com.speedment.runtime.config.internal.DbmsImpl;
import com.speedment.runtime.config.internal.immutable.ImmutableProject;
import com.speedment.runtime.config.util.DocumentTranscoder;
import com.speedment.runtime.core.Speedment;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.PasswordComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.db.DbmsMetadataHandler;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.internal.DefaultApplicationBuilder;
import com.speedment.runtime.core.internal.util.ProgressMeasurerImpl;
import com.speedment.runtime.core.internal.util.Settings;
import com.speedment.runtime.core.util.ProgressMeasure;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;

import static com.speedment.runtime.core.internal.DefaultApplicationMetadata.METADATA_LOCATION;
import static com.speedment.runtime.core.internal.util.TextUtil.alignRight;
import static com.speedment.tool.core.util.OutputUtil.error;
import static com.speedment.tool.core.util.OutputUtil.success;
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
    public static final String DEFAULT_CONFIG_LOCATION = "src/main/json/speedment.json";

    private static final Predicate<File> OPEN_FILE_CONDITIONS = file
        -> file != null
        && file.exists()
        && file.isFile()
        && file.canRead()
        && file.getName().toLowerCase().endsWith(".json");

    @Inject private DocumentPropertyComponent documentPropertyComponent;
    @Inject private UserInterfaceComponent userInterfaceComponent;
    @Inject private DbmsHandlerComponent dbmsHandlerComponenet;
    @Inject private PasswordComponent passwordComponent;
    @Inject private TranslatorManager translatorManager;
    @Inject private ProjectComponent projectComponent;
    @Inject private Injector injector;

    private @Config(
        name=METADATA_LOCATION,
        value=DEFAULT_CONFIG_LOCATION
    ) File currentlyOpenFile;

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

        project.dbmses().map(dbms -> {
            final DbmsType dbmsType = dbmsHandlerComponenet.findByName(dbms.getTypeName())
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
                .readSchemaMetadata(dbms, new ProgressMeasurerImpl(), schemaFilter);
        }).forEachOrdered(fut -> {
            try {
                final Project newProject = fut.get();
                synchronized (project) {
                    project.merge(documentPropertyComponent, newProject);
                }
            } catch (final ExecutionException ex) {
                throw new SpeedmentToolException("Error in execution of reload sequence.", ex);
            } catch (final InterruptedException ex) {
                throw new SpeedmentToolException("Reload sequence was interrupted.", ex);
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
        try {
            // Create an immutable copy of the tree and store in the ProjectComponent
            final Project projectCopy = ImmutableProject.wrap(userInterfaceComponent.projectProperty());
            projectComponent.setProject(projectCopy);

            // TODO: This method needs to be refactored. We create multiple
            // copies of the config tree, but most of the copies are not deep
            // but shallow, meaning that as soon as you start traversing them
            // you risk changing the original map. Not only that, when the new
            // nodes are created, they are given a reference to the old mutable
            // parent instance, meaning that they can mutate the existing tree.
            // It seems to be working for now, mainly because the metadata
            // handler already does a second deep safe-copy of the given tree,
            // but that is both unnescessary and very bad for load performance.
            // We should try to limit the method to a maximum of one deep copy.
            // Create a copy of everything in Dbms EXCEPT the schema key. This
            // is a hack to prevent duplication errors that would otherwise
            // occure if you change name of a node and reload.
            final Map<String, Object> dbmsData
                = new ConcurrentSkipListMap<>(dbms.getData());

            dbmsData.remove(Dbms.SCHEMAS);
            final Dbms dbmsCopy = new DbmsImpl(dbms.getParentOrThrow(), dbmsData);

            // Find the DbmsHandler to use when loading the metadata
            final DbmsMetadataHandler dh = dbmsHandlerComponenet.findByName(dbmsCopy.getTypeName())
                .map(DbmsType::getMetadataHandler)
                .orElseThrow(() -> new SpeedmentToolException(
                "Could not find metadata handler for DbmsType '" + dbmsCopy.getTypeName() + "'."
            ));

            // Begin executing the loading with a progress measurer
            final ProgressMeasure progress = ProgressMeasure.create();
            final CompletableFuture<Boolean> future
                = dh.readSchemaMetadata(dbmsCopy, progress, schemaName::equalsIgnoreCase)
                    .handleAsync((p, ex) -> {
                        progress.setProgress(ProgressMeasure.DONE);

                        // If the loading was successfull
                        if (ex == null && p != null) {
                            // Make sure any old data is cleared before merging in
                            // the new state from the database.
                            dbms.schemasProperty().clear();
                            userInterfaceComponent.projectProperty()
                                .merge(documentPropertyComponent, p);

                            return true;
                        } else {
                            passwordComponent.put(dbms, null); // Clear password
                            runLater(() -> {
                                userInterfaceComponent.showError("Error Connecting to Database",
                                    "A problem occured with establishing the database connection.", ex
                                );
                            });
                            return false;
                        }
                    });

            userInterfaceComponent.showProgressDialog("Loading Database Metadata", progress, future);

            final boolean status = future.get();

            if (status) {
                userInterfaceComponent.showNotification(
                    "Database metadata has been loaded.",
                    FontAwesome.DATABASE,
                    Palette.INFO
                );
            }

            return status;

        } catch (final InterruptedException | ExecutionException ex) {
            passwordComponent.put(dbms, null); // Clear password
            userInterfaceComponent.showError("Error Executing Connection Task",
                "The execution of certain tasks could not be completed.", ex
            );
        }

        return false;
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
                "Could not read .json file",
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
                if (!Files.exists(parent)) {
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
            if (!file.getName().endsWith(".json")) {
                file = new File(file.getAbsolutePath() + ".json");
            }

            saveConfigFile(file);
        }
    }

    public void saveCurrentlyOpenConfigFile() {
        saveConfigFile(currentlyOpenFile);
    }

    public void saveConfigFile(File file) {
        saveConfigFile(file, userInterfaceComponent.projectProperty(), true);
    }

    private void saveConfigFile(File file, ProjectProperty project, boolean isGraphical) {
        final Path path = file.toPath();
        final Path parent = path.getParent();
        if (parent == null) {
            throw new SpeedmentToolException("Unable to save " + file.toString() + " (no parent).");
        }

        try {
            if (!Files.exists(parent)) {
                Files.createDirectories(parent);
            }

            DocumentTranscoder.save(project, path, Json::toJson);

            final String absolute = file.getAbsolutePath();
            Settings.inst().set("project_location", absolute);

            if (isGraphical) {
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
//            stopwatch.stop();

            runLater(() -> {
                userInterfaceComponent.log(OutputUtil.success(
                    "+------------: Generation completed! :------------+" + "\n"
                    //                + "| Total time       " + alignRight(stopwatch.toString(), 30) + " |\n"
                    + "| Files generated  " + alignRight("" + Integer.toString(translatorManager.getFilesCreated()), 30) + " |\n"
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
//            if (!stopwatch.isStopped()) {
//                stopwatch.stop();
//            }
            runLater(() -> {
                userInterfaceComponent.log(OutputUtil.error(
                    "+--------------: Generation failed! :-------------+" + "\n"
                    //                + "| Total time       " + alignRight(stopwatch.toString(), 30) + " |\n"
                    + "| Files generated  " + alignRight("" + Integer.toString(translatorManager.getFilesCreated()), 30) + " |\n"
                    + "| Exception Type   " + alignRight(ex.getClass().getSimpleName(), 30) + " |\n"
                    + "+-------------------------------------------------+"
                ));

                final String msg = "Error! Failed to generate code. A " + ex.getClass().getSimpleName() + " was thrown.";

                LOGGER.error(ex, msg);
                userInterfaceComponent.showError("Failed to generate code", ex.getMessage(), ex);
            });
        }
    }

}
