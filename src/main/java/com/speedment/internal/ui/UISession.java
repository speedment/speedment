/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.ui;

import com.speedment.Speedment;
import com.speedment.component.PasswordComponent;
import com.speedment.internal.util.document.DocumentTranscoder;
import com.speedment.config.db.Dbms;
import com.speedment.config.db.Project;
import com.speedment.config.db.Schema;
import com.speedment.db.DbmsHandler;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.core.code.MainGenerator;
import com.speedment.internal.ui.config.ProjectProperty;
import com.speedment.internal.ui.resource.SpeedmentIcon;
import com.speedment.internal.logging.Logger;
import com.speedment.internal.logging.LoggerManager;
import static com.speedment.internal.ui.UISession.ReuseStage.CREATE_A_NEW_STAGE;
import com.speedment.internal.ui.config.DbmsProperty;
import com.speedment.internal.ui.config.DocumentProperty;
import com.speedment.internal.ui.config.SchemaProperty;
import com.speedment.internal.ui.controller.ConnectController;
import com.speedment.internal.ui.controller.SceneController;
import static com.speedment.internal.ui.util.OutputUtil.error;
import static com.speedment.internal.ui.util.OutputUtil.info;
import static com.speedment.internal.ui.util.OutputUtil.success;
import com.speedment.internal.ui.property.PropertySheetFactory;
import com.speedment.internal.util.Settings;
import com.speedment.internal.util.testing.Stopwatch;
import java.io.File;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.event.Event;
import java.util.function.Consumer;
import java.util.Optional;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.function.Predicate;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import org.controlsfx.glyphfont.FontAwesome;
import static com.speedment.internal.util.TextUtil.alignRight;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
public final class UISession {

    public final static String DEFAULT_GROOVY_LOCATION = "src/main/groovy/speedment.groovy";
    
    public enum ReuseStage {
        USE_EXISTING_STAGE,
        CREATE_A_NEW_STAGE
    }
    
    private final static Logger LOGGER = LoggerManager.getLogger(UISession.class);
    private final static double DIALOG_PANE_ICON_SIZE = 48.0;
    
    private final static Predicate<File> OPEN_FILE_CONDITIONS = file ->
        file != null &&
        file.exists() && 
        file.isFile() && 
        file.canRead() && 
        file.getName().toLowerCase().endsWith(".groovy");
    
    private final static Predicate<File> OPEN_DIRECTORY_CONDITIONS = file ->
        file != null &&
        file.exists() && 
        file.isDirectory();
    
    private final static Predicate<Optional<String>> NO_PASSWORD_SPECIFIED = pass ->
        !pass.isPresent() || "".equals(pass.get().trim());

    private final Speedment speedment;
    private final Application application;
    private final Stage stage;
    private final String defaultJsonLocation;
    private final ProjectProperty project;
    private final PropertySheetFactory propertySheetFactory;
    private final FontAwesome fontAwesome;
    
    private File currentlyOpenFile = null;
    
    public UISession(Speedment speedment, Application application, Stage stage, String defaultGroovyLocation) {
        this(speedment, application, stage, defaultGroovyLocation, new ProjectProperty(new ConcurrentHashMap<>()));
    }
    
    public UISession(Speedment speedment, Application application, Stage stage, String defaultGroovyLocation, Project project) {
        requireNonNull(project);
        
        this.speedment             = requireNonNull(speedment);
        this.application           = requireNonNull(application);
        this.stage                 = requireNonNull(stage);
        this.defaultJsonLocation = requireNonNull(defaultGroovyLocation);
        this.project               = new ProjectProperty(project.stream().toConcurrentMap());
        this.propertySheetFactory  = new PropertySheetFactory();
        this.fontAwesome           = new FontAwesome();
    }
    
    public Speedment getSpeedment() {
        return speedment;
    }
    
    public Application getApplication() {
        return application;
    }
    
    public Stage getStage() {
        return stage;
    }
    
    public ProjectProperty getProject() {
        return project;
    }
    
    public PropertySheetFactory getPropertySheetFactory() {
        return propertySheetFactory;
    }
    
    @SuppressWarnings("unchecked")
    public <T extends Event, E extends EventHandler<T>> E newProject() {
        return on(event -> {
            try {
                final Stage newStage = new Stage();
                final Speedment newSpeedment = speedment.newInstance();
                final UISession session = new UISession(newSpeedment, application, newStage, defaultJsonLocation);

                ConnectController.createAndShow(session);
            } catch (Exception e) {
                LOGGER.error(e);
                log(error(e.getMessage()));
                showError("Could not create empty project", e.getMessage(), e);
            }
        });
    }
    
    public <T extends Event, E extends EventHandler<T>> E openProject() {
        return openProject(CREATE_A_NEW_STAGE);
    }
    
    @SuppressWarnings("unchecked")
    public <T extends Event, E extends EventHandler<T>> E openProject(ReuseStage reuse) {
        return on(event -> {
            final FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open .groovy File");
            fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Groovy files (*.groovy)", "*.groovy"));
            
            Optional.ofNullable(Settings.inst().get("project_location"))
                .map(File::new)
                .filter(OPEN_DIRECTORY_CONDITIONS)
                .ifPresent(fileChooser::setInitialDirectory);
            
            final File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                loadGroovyFile(file, reuse);
            }
        });
    }
    
    @SuppressWarnings("unchecked")
    public <T extends Event, E extends EventHandler<T>> E saveProject() {
        return on(event -> {
            if (currentlyOpenFile == null) {
                saveGroovyFile();
            } else {
                saveGroovyFile(currentlyOpenFile);
            }
        });
    }
    
    @SuppressWarnings("unchecked")
    public <T extends Event, E extends EventHandler<T>> E saveProjectAs() {
        return on(event -> {
            saveGroovyFile();
        });
    }
    
    @SuppressWarnings("unchecked")
    public <T extends Event, E extends EventHandler<T>> E quit() {
        return on(event -> stage.close());
    }
    
    @SuppressWarnings("unchecked")
    public <T extends Event, E extends EventHandler<T>> E reload() {
        return on(event -> {
            if (showWarning(
                "Do you really want to do this?",
                "Reloading the project will remove any changes you have done " +
                "to the project. Are you sure you want to continue?"
            ).filter(ButtonType.OK::equals).isPresent()) {
                
                final PasswordComponent pass = speedment.getPasswordComponent();
                
                project.dbmses()
                    .filter(dbms -> NO_PASSWORD_SPECIFIED.test(pass.get(dbms)))
                    .forEach(dbms -> showPasswordDialog(dbms));
                
                final Optional<String> schemaName = project
                    .dbmses().flatMap(Dbms::schemas)
                    .map(Schema::getName)
                    .findAny();
                
                if (schemaName.isPresent()) {
                    project.dbmses()
                        .map(dbms -> (DbmsProperty) dbms)
                        .forEach(dbms -> loadFromDatabase(dbms, schemaName.get()));
                } else {
                    showError(
                        "No Schema Found", 
                        "Can not connect to the database without atleast one schema specified."
                    );
                }
            }
        });
    }
    
    @SuppressWarnings("unchecked")
    public <T extends Event, E extends EventHandler<T>>  E generate() {
        return on(event -> {
            clearLog();
            
            if (currentlyOpenFile == null) {
                currentlyOpenFile = new File(defaultJsonLocation);
            }
            
            saveGroovyFile(currentlyOpenFile);
            
            final Stopwatch stopwatch = Stopwatch.createStarted();
            log(info("Generating classes " + project.getPackageName() + "." + project.getName() + ".*"));
            log(info("Target directory is " + project.getPackageLocation()));
            final MainGenerator instance = new MainGenerator(speedment);
            
            try {
                instance.accept(project);
                stopwatch.stop();
                
                log(success(
                    "+------------: Generation completed! :------------+" + "\n" +
                    "| Total time       " + alignRight(stopwatch.toString(), 30) + " |\n" +
                    "| Files generated  " + alignRight("" + instance.getFilesCreated(), 30) + " |\n" +
                    "+-------------------------------------------------+"
                ));
            } catch (final Exception ex) {
                if (!stopwatch.isStopped()) {
                    stopwatch.stop();
                }
                
                log(error(
                    "+--------------: Generation failed! :-------------+" + "\n" +
                    "| Total time       " + alignRight(stopwatch.toString(), 30) + " |\n" +
                    "| Files generated  " + alignRight("" + instance.getFilesCreated(), 30) + " |\n" +
                    "| Exception Type   " + alignRight(ex.getClass().getSimpleName(), 30) + " |\n" +
                    "+-------------------------------------------------+"
                ));
                
                LOGGER.error(ex, "Error! Failed to generate code.");
                
                showError("Failed to generate code", ex.getMessage(), ex);
            }
        });
    }
    
    public void showError(String title, String message) {
        showError(title, message, null);
    }
    
    public void showError(String title, String message, final Throwable ex) {
        final Alert alert = new Alert(Alert.AlertType.ERROR);
        final Scene scene = alert.getDialogPane().getScene();
        scene.getStylesheets().add(speedment.getUserInterfaceComponent().getStylesheetFile());

        @SuppressWarnings("unchecked")
        final Stage dialogStage = (Stage) scene.getWindow();
        dialogStage.getIcons().add(SpeedmentIcon.SPIRE.load());

        if (ex != null) {
            alert.setTitle(ex.getClass().getSimpleName());
        } else {
            alert.setTitle("Error");
        }
        
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.setGraphic(fontAwesome.create(FontAwesome.Glyph.WARNING).size(DIALOG_PANE_ICON_SIZE));
        alert.showAndWait();
    }
    
    public Optional<ButtonType> showWarning(String title, String message) {
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        final Scene scene = alert.getDialogPane().getScene();
        scene.getStylesheets().add(speedment.getUserInterfaceComponent().getStylesheetFile());
        
        @SuppressWarnings("unchecked")
        final Stage dialogStage = (Stage) scene.getWindow();
        dialogStage.getIcons().add(SpeedmentIcon.SPIRE.load());

        alert.setTitle("Confirmation");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.setGraphic(fontAwesome.create(FontAwesome.Glyph.WARNING).size(DIALOG_PANE_ICON_SIZE));
        
        return alert.showAndWait();
    }
    
    private void showPasswordDialog(DbmsProperty dbms) {
        final Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Authentication Required");
        dialog.setHeaderText("Enter password for " + dbms.getName());
        dialog.setGraphic(fontAwesome.create(FontAwesome.Glyph.LOCK).size(DIALOG_PANE_ICON_SIZE));
        final DialogPane pane = dialog.getDialogPane();
        pane.getStyleClass().add("authentication");
        
        final Scene scene     = pane.getScene();
        scene.getStylesheets().add(speedment.getUserInterfaceComponent().getStylesheetFile());
        
        @SuppressWarnings("unchecked")
        final Stage dialogStage = (Stage) scene.getWindow();
        dialogStage.getIcons().add(SpeedmentIcon.SPIRE.load());

        final ButtonType authButtonType = new ButtonType("OK", ButtonData.OK_DONE);
        pane.getButtonTypes().addAll(ButtonType.CANCEL, authButtonType);

        final GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField(dbms.getUsername().orElse("Root"));
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);

        final Node loginButton = pane.lookupButton(authButtonType);

        username.textProperty().addListener((ob, o, n) ->
            loginButton.setDisable(n.trim().isEmpty())
        );

        pane.setContent(grid);
        Platform.runLater(username::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == authButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            dbms.usernameProperty().setValue(usernamePassword.getKey());
            speedment.getPasswordComponent().put(dbms, usernamePassword.getValue());
        });
    }
    
    public <DOC extends DocumentProperty> boolean loadFromDatabase(DbmsProperty dbms, String schemaName) {
        try {
            dbms.schemasProperty().clear();
            
            final DbmsHandler dh = speedment.getDbmsHandlerComponent().make(dbms);
            dh.readSchemaMetadata(schemaName::equalsIgnoreCase);
            
            return true;
        } catch (final Exception ex) {
            showError("Error Connecting to Database", 
                ex.getMessage(), ex
            );
        }
        
        return false;
    }
    
    public void loadGroovyFile(File file, ReuseStage reuse) {
        if (OPEN_FILE_CONDITIONS.test(file)) {
            try {
                final Project p = DocumentTranscoder.load(file.toPath());

                switch (reuse) {
                    case CREATE_A_NEW_STAGE :
                        final Stage newStage = new Stage();
                        final Speedment newSpeedment = speedment.newInstance();
                        
                        final UISession session = new UISession(
                            newSpeedment, 
                            application, 
                            newStage, 
                            defaultJsonLocation, 
                            p
                        );
                        
                        SceneController.createAndShow(session);
                        break;

                    case USE_EXISTING_STAGE :
                        project.getData().putAll(p.getData());
                        SceneController.createAndShow(this);
                        break;

                    default :
                        throw new IllegalStateException(
                            "Unknown enum constant '" + reuse + "'."
                        );
                }

                currentlyOpenFile = file;
            } catch (final SpeedmentException ex) {
                LOGGER.error(ex);
                log(error(ex.getMessage()));
                showError("Could not load project", ex.getMessage(), ex);
            }
        } else {
            showError(
                "Could not read .groovy file", 
                "The file '" + file.getAbsoluteFile().getName() + 
                "' could not be read.", 
                null
            );
        }
    }
    
    private void saveGroovyFile() {
        final FileChooser fileChooser = new FileChooser();
        
        fileChooser.setTitle("Save Groovy File");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Groovy files (*.groovy)", "*.groovy"));
        
        if (currentlyOpenFile == null) {
            final Path path   = Paths.get(defaultJsonLocation);
            final Path parent = path.getParent();
            
            try {
                if (!Files.exists(parent)) {
                    Files.createDirectories(parent);
                }
            } catch (IOException ex) {/*
                Do nothing. Creating the parent directory is purely for
                the convenience of the user.
            */}
            
            fileChooser.setInitialDirectory(parent.toFile());
            fileChooser.setInitialFileName(defaultJsonLocation);
        } else {
            fileChooser.setInitialDirectory(currentlyOpenFile.getParentFile());
            fileChooser.setInitialFileName(currentlyOpenFile.getName());
        }
        
        
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            if (!file.getName().endsWith(".groovy")) {
                file = new File(file.getAbsolutePath() + ".groovy");
            }
            
            saveGroovyFile(file);
        }
    }
    
    private void saveGroovyFile(File file) {
        final Path path   = file.toPath();
        final Path parent = path.getParent();

        try {
            if (!Files.exists(parent)) {
                Files.createDirectories(parent);
            }

            DocumentTranscoder.save(project, path);

            final String absolute = parent.toFile().getAbsolutePath();
            Settings.inst().set("project_location", absolute);
            log(success("Saved project file to '" + absolute + "'."));
            currentlyOpenFile = file;
            
        } catch (IOException ex) {
            showError("Could not save file", ex.getMessage(), ex);
        }
    }
    
    public void clearLog() {
        speedment.getUserInterfaceComponent().getOutputMessages().clear();
    }
    
    public void log(Label line) {
        speedment.getUserInterfaceComponent().getOutputMessages().add(line);
    }
    
    @SuppressWarnings("unchecked")
    public <T extends Event, E extends EventHandler<T>> E showGithub() {
        return on(event -> application.getHostServices().showDocument(GITHUB_URI));
    }
    
    private <T extends Event, E extends EventHandler<T>> E on(Consumer<T> listener) {
        @SuppressWarnings("unchecked")
        final E handler = (E) new EventHandler<T>() {
            @Override
            public void handle(T event) {
                listener.accept(event);
            }
        };
        
        return handler;
    }
    
    private final static String GITHUB_URI = "https://github.com/speedment/speedment/";
}