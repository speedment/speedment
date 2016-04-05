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
package com.speedment.internal.ui;

import com.speedment.Speedment;
import com.speedment.code.TranslatorManager;
import com.speedment.component.PasswordComponent;
import com.speedment.component.brand.Brand;
import com.speedment.component.notification.Notification;
import com.speedment.config.db.Dbms;
import com.speedment.config.db.Project;
import com.speedment.config.db.Schema;
import com.speedment.db.DbmsHandler;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.logging.Logger;
import com.speedment.internal.logging.LoggerManager;
import static com.speedment.internal.ui.UISession.ReuseStage.CREATE_A_NEW_STAGE;
import com.speedment.internal.ui.config.DbmsProperty;
import com.speedment.internal.ui.config.ProjectProperty;
import com.speedment.internal.ui.controller.ConnectController;
import com.speedment.internal.ui.controller.NotificationController;
import com.speedment.internal.ui.controller.SceneController;
import static com.speedment.internal.ui.util.OutputUtil.error;
import static com.speedment.internal.ui.util.OutputUtil.info;
import static com.speedment.internal.ui.util.OutputUtil.success;
import com.speedment.internal.util.Settings;
import static com.speedment.internal.util.TextUtil.alignRight;
import com.speedment.internal.util.document.DocumentTranscoder;
import com.speedment.internal.util.testing.Stopwatch;
import com.speedment.ui.config.DocumentProperty;
import com.speedment.ui.config.db.PropertySheetFactory;
import com.speedment.util.ProgressMeasure;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javafx.application.Application;
import javafx.application.Platform;
import static javafx.application.Platform.runLater;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 *
 * @author Emil Forslund
 */
public final class UISession {

    public final static String DEFAULT_CONFIG_LOCATION = "src/main/json/speedment.json";
    public final static String DIALOG_PANE_ICON_SIZE = "2.5em";

    public enum ReuseStage {
        USE_EXISTING_STAGE,
        CREATE_A_NEW_STAGE
    }

    private final static Logger LOGGER = LoggerManager.getLogger(UISession.class);

    private final static Predicate<File> OPEN_FILE_CONDITIONS = file
        -> file != null
        && file.exists()
        && file.isFile()
        && file.canRead()
        && file.getName().toLowerCase().endsWith(".json");

    private final static Predicate<File> OPEN_DIRECTORY_CONDITIONS = file
        -> file != null
        && file.exists()
        && file.isDirectory();

    private final static Predicate<Optional<char[]>> NO_PASSWORD_SPECIFIED
        = pass -> !pass.isPresent() || pass.get().length == 0;

    private final Speedment speedment;
    private final Application application;
    private final Stage stage;
    private final String defaultConfigLocation;
    private final ProjectProperty project;
    private final PropertySheetFactory propertySheetFactory;
    private final ObservableList<Notification> notifications;

    private File currentlyOpenFile = null;

    public UISession(Speedment speedment, Application application, Stage stage, String defaultConfigLocation) {
        this(speedment, application, stage, defaultConfigLocation, null);
    }

    public UISession(Speedment speedment, Application application, Stage stage, String defaultConfigLocation, Project project) {

        this.speedment             = requireNonNull(speedment);
        this.application           = requireNonNull(application);
        this.stage                 = requireNonNull(stage);
        this.defaultConfigLocation = requireNonNull(defaultConfigLocation);
        this.project               = new ProjectProperty();
        this.propertySheetFactory  = new PropertySheetFactory();
        this.notifications =
            speedment.getUserInterfaceComponent().getNotifications();
        
        speedment.getUserInterfaceComponent().setUISession(this);

        if (project != null) {
            this.project.merge(speedment, project);
        }
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
                final Speedment newSpeedment = speedment.copyWithSameTypeOfComponents();
                final UISession session = new UISession(newSpeedment, application, newStage, defaultConfigLocation);

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
            fileChooser.setTitle("Open .json File");
            fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"));

            Optional.ofNullable(Settings.inst().get("project_location"))
                .map(File::new)
                .filter(OPEN_DIRECTORY_CONDITIONS)
                .ifPresent(fileChooser::setInitialDirectory);

            final File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                loadConfigFile(file, reuse);
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <T extends Event, E extends EventHandler<T>> E saveProject() {
        return on(event -> {
            if (currentlyOpenFile == null) {
                saveConfigFile();
            } else {
                saveConfigFile(currentlyOpenFile);
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <T extends Event, E extends EventHandler<T>> E saveProjectAs() {
        return on(event -> {
            saveConfigFile();
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
                "Reloading the project will remove any changes you have done "
                + "to the project. Are you sure you want to continue?"
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
                        .map(DbmsProperty.class::cast)
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
    public <T extends Event, E extends EventHandler<T>> E generate() {
        return on(event -> {
            clearLog();

            if (currentlyOpenFile == null) {
                currentlyOpenFile = new File(defaultConfigLocation);
            }

            saveConfigFile(currentlyOpenFile);

            final Stopwatch stopwatch = Stopwatch.createStarted();
            log(info("Generating classes " + project.findPackageName(speedment.getCodeGenerationComponent().javaLanguageNamer()) + "." + project.getName() + ".*"));
            log(info("Target directory is " + project.getPackageLocation()));

            final TranslatorManager instance = speedment.getCodeGenerationComponent().getTranslatorManager();
            
            try {
                instance.accept(project);
                stopwatch.stop();

                log(success(
                    "+------------: Generation completed! :------------+" + "\n"
                    + "| Total time       " + alignRight(stopwatch.toString(), 30) + " |\n"
                    + "| Files generated  " + alignRight("" + instance.getFilesCreated(), 30) + " |\n"
                    + "+-------------------------------------------------+"
                ));
                
                showNotification(
                    "Generation completed! " + instance.getFilesCreated() + 
                    " files created.", 
                    FontAwesomeIcon.STAR,
                    NotificationController.GREEN
                );
            } catch (final Exception ex) {
                if (!stopwatch.isStopped()) {
                    stopwatch.stop();
                }

                log(error(
                    "+--------------: Generation failed! :-------------+" + "\n"
                    + "| Total time       " + alignRight(stopwatch.toString(), 30) + " |\n"
                    + "| Files generated  " + alignRight("" + instance.getFilesCreated(), 30) + " |\n"
                    + "| Exception Type   " + alignRight(ex.getClass().getSimpleName(), 30) + " |\n"
                    + "+-------------------------------------------------+"
                ));
                
                final String msg = "Error! Failed to generate code. A " + ex.getClass().getSimpleName() + " was thrown.";
                
                LOGGER.error(ex, msg);
                showError("Failed to generate code", ex.getMessage(), ex);
            }
        });
    }

    public <T extends Event, E extends EventHandler<T>> E toggleProjectTree() {
        return toggle("projectTree", hiddenProjectTree, StoredNode.InsertAt.BEGINNING);
    }

    public <T extends Event, E extends EventHandler<T>> E toggleWorkspace() {
        return toggle("workspace", hiddenWorkspace, StoredNode.InsertAt.BEGINNING);
    }

    public <T extends Event, E extends EventHandler<T>> E toggleOutput() {
        return toggle("output", hiddenOutput, StoredNode.InsertAt.END);
    }

    public <T extends Event, E extends EventHandler<T>> E togglePreview() {
        return toggle("preview", hiddenPreview, StoredNode.InsertAt.END);
    }
    
    

    private final static class StoredNode {

        private enum InsertAt {
            BEGINNING, END
        }

        private final Node node;
        private final SplitPane parent;

        private StoredNode(Node node, SplitPane parent) {
            this.node = requireNonNull(node);
            this.parent = requireNonNull(parent);
        }
    }

    private final ObjectProperty<StoredNode> hiddenProjectTree = new SimpleObjectProperty<>(),
        hiddenWorkspace = new SimpleObjectProperty<>(),
        hiddenOutput = new SimpleObjectProperty<>(),
        hiddenPreview = new SimpleObjectProperty<>();

    private <T extends Event, E extends EventHandler<T>> E toggle(String cssId, ObjectProperty<StoredNode> hidden, StoredNode.InsertAt insertAt) {
        return on(event -> {
            final SplitPane parent;
            final Node node;

            if (hidden.get() == null) {
                node = this.stage.getScene().lookup("#" + cssId);

                if (node != null) {
                    Node n = node;
                    while (!((n = n.getParent()) instanceof SplitPane) && n != null) {
                    }
                    parent = (SplitPane) n;

                    if (parent != null) {
                        parent.getItems().remove(node);
                        hidden.set(new StoredNode(node, parent));
                    } else {
                        LOGGER.error("Found no SplitPane ancestor of #" + cssId + ".");
                    }
                } else {
                    LOGGER.error("Non-existing node #" + cssId + " was toggled.");
                }
            } else {
                parent = hidden.get().parent;

                if (parent != null) {
                    node = hidden.get().node;

                    switch (insertAt) {
                        case BEGINNING:
                            parent.getItems().add(0, node);
                            break;
                        case END:
                            parent.getItems().add(node);
                            break;
                        default:
                            throw new UnsupportedOperationException(
                                "Unknown InsertAt enum constant '" + insertAt + "'."
                            );
                    }

                    hidden.set(null);
                } else {
                    LOGGER.error("Found no parent to node #" + cssId + " that was toggled.");
                }
            }
        });
    }

    public void showError(String title, String message) {
        showError(title, message, null);
    }

    public void showError(String title, String message, final Throwable ex) {
        LOGGER.error(ex, message);

        final Alert alert = new Alert(Alert.AlertType.ERROR);
        final Scene scene = alert.getDialogPane().getScene();

        speedment
            .getUserInterfaceComponent()
            .stylesheetFiles()
            .forEachOrdered(scene.getStylesheets()::add);

        Brand.apply(this, scene);

        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.WARNING, DIALOG_PANE_ICON_SIZE));

        if (ex == null) {
            alert.setTitle("Error");
        } else {
            alert.setTitle(ex.getClass().getSimpleName());

            final StringWriter sw = new StringWriter();
            final PrintWriter pw = new PrintWriter(sw);

            ex.printStackTrace(pw);

            final Label label = new Label("The exception stacktrace was:");

            final String exceptionText = sw.toString();
            final TextArea textArea = new TextArea(exceptionText);
            textArea.setEditable(false);
            textArea.setWrapText(true);

            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);

            final GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0, 1);

            alert.getDialogPane().setExpandableContent(expContent);
        }

        alert.showAndWait();
    }

    public Optional<ButtonType> showWarning(String title, String message) {
        LOGGER.warn(message);

        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        final Scene scene = alert.getDialogPane().getScene();

        Brand.apply(this, scene);

        speedment
            .getUserInterfaceComponent()
            .stylesheetFiles()
            .forEachOrdered(scene.getStylesheets()::add);

        alert.setTitle("Confirmation");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.WARNING, DIALOG_PANE_ICON_SIZE));

        return alert.showAndWait();
    }

    private void showPasswordDialog(DbmsProperty dbms) {
        final Dialog<Pair<String, char[]>> dialog = new Dialog<>();
        dialog.setTitle("Authentication Required");
        dialog.setHeaderText("Enter password for " + dbms.getName());
        dialog.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.LOCK, DIALOG_PANE_ICON_SIZE));
        final DialogPane pane = dialog.getDialogPane();
        pane.getStyleClass().add("authentication");

        final Scene scene = pane.getScene();
        Brand.apply(this, scene);

        speedment
            .getUserInterfaceComponent()
            .stylesheetFiles()
            .forEachOrdered(scene.getStylesheets()::add);

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

        username.textProperty().addListener((ob, o, n)
            -> loginButton.setDisable(n.trim().isEmpty())
        );

        pane.setContent(grid);
        Platform.runLater(username::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == authButtonType) {
                return new Pair<>(username.getText(), password.getText().toCharArray());
            }
            return null;
        });

        Optional<Pair<String, char[]>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            dbms.mutator().setUsername(usernamePassword.getKey());
            speedment.getPasswordComponent().put(dbms, usernamePassword.getValue());
        });
    }

    private void showProgressDialog(String title, ProgressMeasure progress, CompletableFuture<Boolean> task) {
        final Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Progress Tracker");
        dialog.setHeaderText(title);
        dialog.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.SPINNER, DIALOG_PANE_ICON_SIZE));

        final DialogPane pane = dialog.getDialogPane();
        pane.getStyleClass().add("progress");

        final VBox box = new VBox();
        final ProgressBar bar = new ProgressBar();
        final Label message = new Label();
        final Button cancel = new Button("Cancel", new FontAwesomeIconView(FontAwesomeIcon.REMOVE));

        box.getChildren().addAll(bar, message, cancel);
        box.setMaxWidth(Double.MAX_VALUE);
        bar.setMaxWidth(Double.MAX_VALUE);
        message.setMaxWidth(Double.MAX_VALUE);
        cancel.setMaxWidth(128);
        VBox.setVgrow(message, Priority.ALWAYS);

        box.setFillWidth(false);
        box.setSpacing(8);

        progress.addListener(measure -> {
            final String msg = measure.getCurrentAction();
            final double prg = measure.getProgress();
            final boolean done = measure.isDone();

            runLater(() -> {
                message.setText(msg);
                bar.setProgress(prg);

                if (done) {
                    dialog.setResult(true);
                    dialog.close();
                }
            });
        });

        cancel.setOnAction(ev -> {
            task.cancel(true);
        });

        pane.setContent(box);
        pane.setMaxWidth(Double.MAX_VALUE);

        final Scene scene = pane.getScene();
        Brand.apply(this, scene);

        if (!progress.isDone()) {
            dialog.showAndWait();
        }
    }
    
    private final static class NotificationImpl implements Notification {
        
        private final String message;
        private final FontAwesomeIcon icon;
        private final Color color;
        private final Runnable onClose;

        public NotificationImpl(String message, FontAwesomeIcon icon, Color color, Runnable onClose) {
            this.message = requireNonNull(message);
            this.icon    = requireNonNull(icon);
            this.color   = requireNonNull(color);
            this.onClose = requireNonNull(onClose);
        }
        
        @Override
        public String text() {
            return message;
        }

        @Override
        public FontAwesomeIcon icon() {
            return icon;
        }
        
        @Override
        public Color color() {
            return color;
        }

        @Override
        public Runnable onClose() {
            return onClose;
        }
    }
    
    public void showNotification(String message) {
        showNotification(message, FontAwesomeIcon.EXCLAMATION);
    }
    
    public void showNotification(String message, FontAwesomeIcon icon) {
        showNotification(message, icon, NotificationController.BLUE);
    }
    
    public void showNotification(String message, Runnable action) {
        showNotification(message, FontAwesomeIcon.EXCLAMATION, NotificationController.BLUE, action);
    }
    
    public void showNotification(String message, Color color) {
        showNotification(message, FontAwesomeIcon.EXCLAMATION, color);
    }
    
    public void showNotification(String message, FontAwesomeIcon icon, Color color) {
        showNotification(message, icon, color, () -> {});
    }
    
    public void showNotification(String message, FontAwesomeIcon icon, Color color, Runnable action) {
        notifications.add(new NotificationImpl(message, icon, color, action));
    }

    public <DOC extends DocumentProperty> boolean loadFromDatabase(DbmsProperty dbms, String schemaName) {
        try {
            dbms.schemasProperty().clear();

            final DbmsHandler dh = speedment.getDbmsHandlerComponent().make(dbms);

            final ProgressMeasure progress = ProgressMeasure.create();
            final CompletableFuture<Boolean> future
                = dh.readSchemaMetadata(progress, schemaName::equalsIgnoreCase)
                .handle((p, ex) -> {
                    progress.setProgress(ProgressMeasure.DONE);

                    if (ex == null) {
                        project.merge(speedment, p);
                        return true;
                    } else {
                        runLater(() -> {
                            showError("Error Connecting to Database",
                                "A problem occured with establishing the database connection.", ex
                            );
                        });
                        return false;
                    }
                });

            showProgressDialog("Loading Database Metadata", progress, future);

            final boolean status = future.get();
            
            if (status) {
                showNotification(
                    "Database metadata has been loaded.", 
                    FontAwesomeIcon.DATABASE, 
                    NotificationController.GREEN
                );
            }
            
            return status;

        } catch (final InterruptedException | ExecutionException ex) {
            showError("Error Executing Connection Task", 
                "The execution of certain tasks could not be completed.", ex
            );
        }

        return false;
    }

    public void loadConfigFile(File file, ReuseStage reuse) {
        if (OPEN_FILE_CONDITIONS.test(file)) {
            try {
                final Project p = DocumentTranscoder.load(file.toPath());

                switch (reuse) {
                    case CREATE_A_NEW_STAGE:
                        final Stage newStage = new Stage();
                        final Speedment newSpeedment = speedment.copyWithSameTypeOfComponents();

                        final UISession session = new UISession(
                            newSpeedment,
                            application,
                            newStage,
                            defaultConfigLocation,
                            p
                        );

                        SceneController.createAndShow(session);
                        break;

                    case USE_EXISTING_STAGE:
                        project.merge(speedment, p);
                        SceneController.createAndShow(this);
                        break;

                    default:
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
                "Could not read .json file",
                "The file '" + file.getAbsoluteFile().getName()
                + "' could not be read.",
                null
            );
        }
    }

    private void saveConfigFile() {
        final FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Save JSON File");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"));

        if (currentlyOpenFile == null) {
            final Path path = Paths.get(defaultConfigLocation);
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

        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            if (!file.getName().endsWith(".json")) {
                file = new File(file.getAbsolutePath() + ".json");
            }

            saveConfigFile(file);
        }
    }

    private void saveConfigFile(File file) {
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

            final String absolute = parent.toFile().getAbsolutePath();
            Settings.inst().set("project_location", absolute);
            log(success("Saved project file to '" + absolute + "'."));
            showNotification("Configuration saved.", FontAwesomeIcon.SAVE, NotificationController.GREEN);
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
    public <T extends Event, E extends EventHandler<T>> E showGitter() {
        return on(event -> browse(GITTER_URI));
    }

    @SuppressWarnings("unchecked")
    public <T extends Event, E extends EventHandler<T>> E showGithub() {
        return on(event -> browse(GITHUB_URI));
    }

    public void browse(String url) {
        application.getHostServices().showDocument(url);
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
    private final static String GITTER_URI = "https://gitter.im/speedment/speedment/";
}
