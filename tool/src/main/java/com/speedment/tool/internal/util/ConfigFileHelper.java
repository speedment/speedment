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
package com.speedment.tool.internal.util;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.injector.annotation.InjectorKey;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.generator.TranslatorManager;
import com.speedment.runtime.Speedment;
import com.speedment.runtime.component.DbmsHandlerComponent;
import com.speedment.runtime.component.ProjectComponent;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Schema;
import com.speedment.runtime.config.parameter.DbmsType;
import com.speedment.runtime.db.DbmsMetadataHandler;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.internal.config.DbmsImpl;
import com.speedment.runtime.internal.config.immutable.ImmutableProject;
import com.speedment.runtime.internal.runtime.DefaultApplicationBuilder;
import static com.speedment.runtime.internal.runtime.DefaultApplicationMetadata.METADATA_LOCATION;
import com.speedment.runtime.internal.util.Settings;
import com.speedment.runtime.internal.util.document.DocumentTranscoder;
import com.speedment.runtime.util.ProgressMeasure;
import com.speedment.tool.MainApp;
import com.speedment.tool.brand.Palette;
import com.speedment.tool.component.DocumentPropertyComponent;
import com.speedment.tool.component.UserInterfaceComponent;
import com.speedment.tool.component.UserInterfaceComponent.ReuseStage;
import com.speedment.tool.config.DbmsProperty;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;

import com.speedment.runtime.internal.util.ProgressMeasurerImpl;
import static com.speedment.runtime.internal.util.TextUtil.alignRight;
import com.speedment.tool.config.ProjectProperty;
import com.speedment.tool.util.OutputUtil;
import static com.speedment.tool.util.OutputUtil.error;
import static com.speedment.tool.util.OutputUtil.success;
import java.util.Set;
import static java.util.stream.Collectors.toSet;
import static javafx.application.Platform.runLater;

/**
 *
 * @author  Emil Forslund
 * @since   2.4.0
 */
@InjectorKey(ConfigFileHelper.class)
public final class ConfigFileHelper {
    
    private final static Logger LOGGER = LoggerManager.getLogger(ConfigFileHelper.class);
    public static final String DEFAULT_CONFIG_LOCATION = "src/main/json/speedment.json";
    
    private @Inject DocumentPropertyComponent documentPropertyComponent;
    private @Inject UserInterfaceComponent userInterfaceComponent;
    private @Inject DbmsHandlerComponent dbmsHandlerComponenet;
    private @Inject TranslatorManager translatorManager;
    private @Inject ProjectComponent projectComponent;
    private @Inject Injector injector;
    
    private File currentlyOpenFile;
    
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
            throw new SpeedmentException(
                "Can't load from database unless either a dbms and schema " + 
                "is specified or a config file is present."
            );
        }
        
        project.dbmses().map(dbms -> {
            final DbmsType dbmsType = dbmsHandlerComponenet.findByName(dbms.getTypeName())
                .orElseThrow(() -> new SpeedmentException(
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
                throw new SpeedmentException("Error in execution of reload sequence.", ex);
            } catch (final InterruptedException ex) {
                throw new SpeedmentException("Reload sequence was interrupted.", ex);
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
            
            // Create a copy of everything in Dbms EXCEPT the schema key. This
            // is a hack to prevent duplication errors that would otherwise
            // occure if you change name of a node and reload.
            final Map<String, Object> dbmsData = new ConcurrentHashMap<>(dbms.getData());
            dbmsData.remove(Dbms.SCHEMAS);
            final Dbms dbmsCopy = new DbmsImpl(dbms.getParentOrThrow(), dbmsData);

            // Find the DbmsHandler to use when loading the metadata
            final DbmsMetadataHandler dh = dbmsHandlerComponenet.findByName(dbmsCopy.getTypeName())
                .map(DbmsType::getMetadataHandler)
                .orElseThrow(() -> new SpeedmentException(
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
                        userInterfaceComponent.projectProperty().merge(documentPropertyComponent, p);
                        
                        return true;
                        
                    } else {
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
                    FontAwesomeIcon.DATABASE,
                    Palette.INFO
                );
            }

            return status;

        } catch (final InterruptedException | ExecutionException ex) {
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
                        final Injector.Builder injectorBuilder = injector.newBuilder()
                            .withParam(METADATA_LOCATION, DEFAULT_CONFIG_LOCATION);
                        final Speedment newSpeedment = new DefaultApplicationBuilder(injectorBuilder).build();
                        
                        MainApp.setInjector(newSpeedment.getOrThrow(Injector.class));
                        final MainApp main = new MainApp();
                        
                        try {
                            main.start(newStage);
                        } catch (final Exception ex) {
                            throw new SpeedmentException(ex);
                        }
                        
                        break;

                    case USE_EXISTING_STAGE:
                        final Project p = DocumentTranscoder.load(file.toPath());
                        userInterfaceComponent.projectProperty().merge(documentPropertyComponent, p);
                        break;

                    default:
                        throw new IllegalStateException(
                            "Unknown enum constant '" + reuse + "'."
                        );
                }

                currentlyOpenFile = file;
            } catch (final SpeedmentException ex) {
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

    public void saveConfigFile() {
        final FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Save JSON File");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"));

        if (currentlyOpenFile == null) {
            final Path path = Paths.get(DEFAULT_CONFIG_LOCATION);
            final Path parent = path.getParent();
            if (parent == null) {
                throw new SpeedmentException("Unable to save " + path.toString() + " (no parent).");
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
            throw new SpeedmentException("Unable to save " + file.toString() + " (no parent).");
        }

        try {
            if (!Files.exists(parent)) {
                Files.createDirectories(parent);
            }

            DocumentTranscoder.save(project, path);

            final String absolute = file.getAbsolutePath();
            Settings.inst().set("project_location", absolute);
            
            if (isGraphical) {
                userInterfaceComponent.log(success("Saved project file to '" + absolute + "'."));
                userInterfaceComponent.showNotification("Configuration saved.", FontAwesomeIcon.SAVE, Palette.INFO);
            }
            
            currentlyOpenFile = file;

        } catch (final IOException ex) {
            if (isGraphical) {
                userInterfaceComponent.showError("Could not save file", ex.getMessage(), ex);
            } else throw new SpeedmentException(ex);
        }
    }
    
    public void generateSources() {
        
        
        try {
            translatorManager.accept( projectComponent.getProject() );
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
                    FontAwesomeIcon.STAR,
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
    
    private static final Predicate<File> OPEN_FILE_CONDITIONS = file
        -> file != null
        && file.exists()
        && file.isFile()
        && file.canRead()
        && file.getName().toLowerCase().endsWith(".json");
}