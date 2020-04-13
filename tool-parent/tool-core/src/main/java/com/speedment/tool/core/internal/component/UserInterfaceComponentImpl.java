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
package com.speedment.tool.core.internal.component;

import static com.speedment.runtime.core.util.Statistics.Event.GUI_PROJECT_LOADED;
import static com.speedment.runtime.core.util.Statistics.Event.GUI_STARTED;
import static java.util.Objects.requireNonNull;
import static javafx.application.Platform.runLater;

import com.speedment.common.injector.InjectBundle;
import com.speedment.common.injector.Injector;
import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerEvent;
import com.speedment.common.logger.LoggerManager;
import com.speedment.generator.translator.TranslatorSupport;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Schema;
import com.speedment.runtime.core.component.InfoComponent;
import com.speedment.runtime.core.component.PasswordComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.util.ProgressMeasure;
import com.speedment.runtime.core.util.ProgressMeasureUtil;
import com.speedment.runtime.core.util.Statistics;
import com.speedment.tool.config.DbmsProperty;
import com.speedment.tool.config.DocumentProperty;
import com.speedment.tool.config.ProjectProperty;
import com.speedment.tool.config.component.DocumentPropertyComponent;
import com.speedment.tool.config.provider.DelegateDocumentPropertyComponent;
import com.speedment.tool.core.MainApp;
import com.speedment.tool.core.brand.Palette;
import com.speedment.tool.core.component.RuleComponent;
import com.speedment.tool.core.component.UserInterfaceComponent;
import com.speedment.tool.core.internal.notification.NotificationImpl;
import com.speedment.tool.core.internal.util.ConfigFileHelper;
import com.speedment.tool.core.internal.util.InjectionLoaderImpl;
import com.speedment.tool.core.internal.util.Throttler;
import com.speedment.tool.core.notification.Notification;
import com.speedment.tool.core.provider.DelegateSpeedmentBrand;
import com.speedment.tool.core.resource.FontAwesome;
import com.speedment.tool.core.resource.Icon;
import com.speedment.tool.core.util.BrandUtil;
import com.speedment.tool.core.util.InjectionLoader;
import com.speedment.tool.core.util.OutputUtil;
import com.speedment.tool.propertyeditor.PropertyEditor;
import com.speedment.tool.propertyeditor.provider.DelegatePropertyEditorComponent;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TreeItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

/**
 *
 * @author Emil Forslund
 */
public final class UserInterfaceComponentImpl implements UserInterfaceComponent {
    
    private static final Logger LOGGER = LoggerManager.getLogger(UserInterfaceComponentImpl.class);

    private static final String MANUAL_URI = "https://speedment.github.io/speedment-doc/index.html";
    private static final String GITTER_URI = "https://gitter.im/speedment/speedment/";
    private static final String ISSUE_URI = "https://github.com/speedment/speedment/issues/new";
    private static final String GITHUB_URI = "https://github.com/speedment/speedment/";

    private static final Predicate<Optional<char[]>> NO_PASSWORD_SPECIFIED
        = pass -> !pass.isPresent() || pass.get().length == 0;

    private final BooleanProperty projectTreeVisible = new SimpleBooleanProperty(true);
    private final BooleanProperty workspaceVisible   = new SimpleBooleanProperty(true);
    private final BooleanProperty outputVisible      = new SimpleBooleanProperty(false);

    private final ObservableList<Notification> notifications;
    private final ObservableList<Node> outputMessages;
    private final ObservableList<TreeItem<DocumentProperty>> selectedTreeItems;
    private final ObservableList<PropertyEditor.Item> properties;
    
    private final AtomicBoolean canGenerate;
    
    private final DocumentPropertyComponent documentPropertyComponent;
    private final PasswordComponent passwordComponent;
    private final ProjectComponent projectComponent;
    private final ConfigFileHelper configFileHelper;
    private final InjectionLoader loader;
    private final RuleComponent rules;
    private final InfoComponent info;
    
    private Injector injector;
    
    private Stage stage;
    private Application application;
    private ProjectProperty project;

    public UserInterfaceComponentImpl(
        final DocumentPropertyComponent documentPropertyComponent,
        final PasswordComponent passwordComponent,
        final ProjectComponent projectComponent,
        final ConfigFileHelper configFileHelper,
        final InjectionLoader loader,
        final RuleComponent rules,
        final InfoComponent info
    ) {
        this.documentPropertyComponent = requireNonNull(documentPropertyComponent);
        this.passwordComponent = requireNonNull(passwordComponent);
        this.projectComponent = requireNonNull(projectComponent);
        this.configFileHelper = requireNonNull(configFileHelper);
        this.loader = requireNonNull(loader);
        this.rules = requireNonNull(rules);
        this.info = requireNonNull(info);
        notifications     = FXCollections.observableArrayList();
        outputMessages    = FXCollections.observableArrayList();
        selectedTreeItems = FXCollections.observableArrayList();
        properties        = FXCollections.observableArrayList();
        canGenerate       = new AtomicBoolean(true);
    }

    @ExecuteBefore(State.INITIALIZED)
    public void setInjector(Injector injector) {
        this.injector = requireNonNull(injector);
    }

    public static InjectBundle include() {
        return InjectBundle.of(
            DelegateDocumentPropertyComponent.class,
            DelegateSpeedmentBrand.class,
            InjectionLoaderImpl.class,
            ConfigFileHelper.class,
            DelegatePropertyEditorComponent.class,
            RuleComponentImpl.class,
            IssueComponentImpl.class
        );
    }
    
    @Override
    public void start(Application application, Stage stage) {
        this.stage       = requireNonNull(stage);
        this.application = requireNonNull(application);
        this.project     = new ProjectProperty();

        final Throttler throttler = Throttler.limitToOnceEvery(2_000);
        LoggerManager.getFactory().addListener(ev -> {
            switch (ev.getLevel()) {
                case DEBUG : case TRACE : case INFO :
                    addToOutputMessages(OutputUtil.info(ev.getMessage()));
                    break;
                case WARN :
                    outputWarningAndShowNotification(throttler, ev);
                    break;
                case ERROR : case FATAL :
                    addToOutputMessages(OutputUtil.error(ev.getMessage()));
                    break;
            }
        });
        
        final Project loaded = projectComponent.getProject();
        if (loaded != null) {
            project.merge(documentPropertyComponent, loaded);
        }

        Statistics.report(info, projectComponent, GUI_STARTED);
    }

    private void outputWarningAndShowNotification(Throttler throttler, LoggerEvent ev) {
        addToOutputMessages(OutputUtil.warning(ev.getMessage()));
        final String title = "There are warnings. See output.";
        throttler.call(title, () ->
            showNotification(title,
                FontAwesome.EXCLAMATION_CIRCLE,
                Palette.WARNING,
                () -> outputVisible.set(true)
            )
        );
    }

    private void addToOutputMessages(Node node) {
        runLater(() -> outputMessages.add(node));
    }

    ////////////////////////////////////////////////////////////////////////////
    //                            Global Properties                           //
    ////////////////////////////////////////////////////////////////////////////
 
    @Override
    public ProjectProperty projectProperty() {
        return project;
    }

    @Override
    public Application getApplication() {
        return application;
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public ObservableList<Notification> notifications() {
        return notifications;
    }

    @Override
    public ObservableList<Node> outputMessages() {
        return outputMessages;
    }

    @Override
    public ObservableList<TreeItem<DocumentProperty>> getSelectedTreeItems() {
        return selectedTreeItems;
    }

    @Override
    public ObservableList<PropertyEditor.Item> getProperties() {
        return properties;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //                            Menubar actions                             //
    ////////////////////////////////////////////////////////////////////////////

    @Override
    public void newProject() {
        try {
            MainApp.setInjector(injector.newBuilder().build());
            final MainApp app    = new MainApp();
            final Stage newStage = new Stage();
            app.start(newStage);
        } catch (final Exception e) {
            LOGGER.error(e);
            showError("Could not create empty project", e.getMessage(), e);
        }
    }

    @Override
    public void openProject() {
        openProject(ReuseStage.CREATE_A_NEW_STAGE);
    }

    @Override
    public void openProject(ReuseStage reuse) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open .json File");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"));

        final File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            configFileHelper.loadConfigFile(file, reuse);
        }
    }

    @Override
    public void saveProject() {
        if (configFileHelper.isFileOpen()) {
            configFileHelper.saveCurrentlyOpenConfigFile();
        } else {
            configFileHelper.saveConfigFile();
        }
    }

    @Override
    public void saveProjectAs() {
        configFileHelper.saveConfigFile();
    }

    @Override
    public void quit() {
        stage.close();
    }

    @Override
    public void reload() {
        if (showWarning(
            "Do you really want to do this?",
            "Reloading the project will remove any changes you have done "
            + "to the project. Are you sure you want to continue?"
        ).filter(ButtonType.OK::equals).isPresent()) {

            project.dbmses()
                .filter(dbms -> NO_PASSWORD_SPECIFIED.test(passwordComponent.get(dbms)))
                .map(DbmsProperty.class::cast)
                .forEach(this::showPasswordDialog);

            final Optional<String> schemaName = project
                .dbmses().flatMap(Dbms::schemas)
                .map(Schema::getId)
                .findAny();

            if (schemaName.isPresent()) {
                project.dbmses()
                    .map(DbmsProperty.class::cast)
                    .forEach(dbms -> configFileHelper.loadFromDatabase(dbms, schemaName.get()));

                Statistics.report(info, projectComponent, GUI_PROJECT_LOADED);
            } else {
                showError(
                    "No Schema Found",
                    "Can not connect to the database without at least one schema specified."
                );
            }
        }
    }

    @Override
    public void generate() {
        //Make sure that no more than one attemp of generating occurs concurrently
        final boolean allowed = canGenerate.getAndSet(false);
        if( !allowed ){
            return;
        }
        
        clearLog();
        
        final TranslatorSupport<Project> support = new TranslatorSupport<>(injector, project);

        log(OutputUtil.info("Preparing for generating classes " + support.basePackageName() + "." + project.getId() + ".*"));
        log(OutputUtil.info("Target directory is " + project.getPackageLocation()));
        log(OutputUtil.info("Performing rule verifications..."));

        final Project immutableProject = Project.createImmutable(project);
        projectComponent.setProject(immutableProject);
        
        CompletableFuture<Boolean> future = rules.verify();
        future.handleAsync((bool, ex) -> {
            if (ex != null) {
                final String err = 
                    "An error occurred while the error checker was looking " +
                    "for issues in the project configuration.";
                LOGGER.error(ex, err);
                runLater(() -> {
                    showError("Error Creating Report", err, ex);
                    canGenerate.set(true);
                });
            } else {
                if (!bool) {
                    runLater( () -> {
                        showIssues();
                        canGenerate.set(true);
                    } );
                } else {
                    runLater(() -> log(OutputUtil.info("Rule verifications completed")));

                    if (!configFileHelper.isFileOpen()) {
                        configFileHelper.setCurrentlyOpenFile(
                            new File(ConfigFileHelper.DEFAULT_CONFIG_LOCATION)
                        );
                    }
                    configFileHelper.saveCurrentlyOpenConfigFile();
                   
                    configFileHelper.generateSources();
                    canGenerate.set(true);
                }
            }
            return bool;
        });
    }

    @Override
    public void showManual() {
        browse(MANUAL_URI);
    }

    @Override
    public void showGitter() {
        browse(GITTER_URI);
    }

    @Override
    public void reportIssue() {
        browse(ISSUE_URI);
    }

    @Override
    public void showGithub() {
        browse(GITHUB_URI);
    }

    @Override
    public BooleanProperty projectTreeVisibleProperty() {
        return projectTreeVisible;
    }

    @Override
    public BooleanProperty workspaceVisibleProperty() {
        return workspaceVisible;
    }

    @Override
    public BooleanProperty outputVisibleProperty() {
        return outputVisible;
    }

    @Override
    public void prepareProjectTree(SplitPane parent, Node projectTree) {
        if (!projectTreeVisible.get()) {
            parent.getItems().remove(projectTree);
        }

        projectTreeVisible.addListener((ob, o, visible) -> {
            if (visible) {
                parent.getItems().add(0, projectTree);
            } else {
                parent.getItems().remove(projectTree);
            }
        });
    }

    @Override
    public void prepareWorkspace(SplitPane parent, Node workspace) {
        if (!workspaceVisible.get()) {
            parent.getItems().remove(workspace);
        }

        workspaceVisible.addListener((ob, o, visible) -> {
            if (visible) {
                parent.getItems().add(0, workspace);
            } else {
                parent.getItems().remove(workspace);
            }
        });
    }

    @Override
    public void prepareOutput(SplitPane parent, Node output) {
        if (!outputVisible.get()) {
            parent.getItems().remove(output);
        }

        outputVisible.addListener((ob, o, visible) -> {
            if (visible) {
                parent.getItems().add(output);
            } else {
                parent.getItems().remove(output);
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////
    //                             Dialog Messages                            //
    ////////////////////////////////////////////////////////////////////////////

    @Override
    public void showError(String title, String message) {
        showError(title, message, null);
    }

    @Override
    public void showError(String title, String message, Throwable ex) {
        final Alert alert = new Alert(Alert.AlertType.ERROR);
        final Scene scene = alert.getDialogPane().getScene();

        BrandUtil.applyBrand(injector, stage, scene);

        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.setGraphic(FontAwesome.EXCLAMATION_TRIANGLE.view());
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

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

    @Override
    public Optional<ButtonType> showWarning(String title, String message) {
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        final Scene scene = alert.getDialogPane().getScene();

        BrandUtil.applyBrand(injector, stage, scene);

        alert.setTitle("Confirmation");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.setGraphic(FontAwesome.EXCLAMATION_TRIANGLE.view());

        return alert.showAndWait();
    }

    @Override
    public void showPasswordDialog(DbmsProperty dbms) {
        final Dialog<Pair<String, char[]>> dialog = new Dialog<>();
        dialog.setTitle("Authentication Required");
        dialog.setHeaderText("Enter password for " + dbms.getName());
        dialog.setGraphic(FontAwesome.LOCK.view());
        final DialogPane pane = dialog.getDialogPane();
        pane.getStyleClass().add("authentication");

        final Scene scene = pane.getScene();
        BrandUtil.applyBrand(injector, stage, scene);

        final ButtonType authButtonType = new ButtonType("OK", ButtonData.OK_DONE);
        pane.getButtonTypes().addAll(ButtonType.CANCEL, authButtonType);

        final GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        final TextField username = new TextField(dbms.getUsername().orElse("Root"));
        username.setPromptText("Username");
        final PasswordField password = new PasswordField();
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
            passwordComponent.put(dbms, usernamePassword.getValue());
        });
    }

    @Override
    public void showProgressDialog(String title, ProgressMeasure progress, CompletableFuture<Boolean> task) {
        final Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Progress Tracker");
        dialog.setHeaderText(title);
        dialog.setGraphic(FontAwesome.SPINNER.view());

        final DialogPane pane = dialog.getDialogPane();
        pane.getStyleClass().add("progress");

        final VBox box = new VBox();
        final ProgressBar bar = new ProgressBar();
        final Label message = new Label();
        final Button cancel = new Button("Cancel", FontAwesome.TIMES.view());

        final Pane filler = new Pane();
        final HBox fillerContainer = new HBox(filler, cancel);
        HBox.setHgrow(filler, Priority.ALWAYS);
        HBox.setHgrow(cancel, Priority.SOMETIMES);
        filler.setMaxWidth(Double.MAX_VALUE);
        fillerContainer.setMaxWidth(Double.MAX_VALUE);

        box.getChildren().addAll(bar, message, fillerContainer);
        box.setMaxWidth(Double.MAX_VALUE);
        bar.setMaxWidth(Double.MAX_VALUE);
        message.setMaxWidth(Double.MAX_VALUE);
        cancel.setMaxWidth(128);
        VBox.setVgrow(message, Priority.ALWAYS);

        box.setFillWidth(true);
        box.setSpacing(8);

        progress.addListener(measure -> {
            final String msg   = measure.getCurrentAction();
            final double prg   = measure.getProgress();
            final boolean done = measure.isDone();
            
            runLater(() -> {
                if (done) {
                    dialog.setResult(true);
                    dialog.close();
                } else {
                    message.setText(msg);
                    bar.setProgress(prg);
                }
            });
        });

        cancel.setOnAction(ev -> {
            if (!task.cancel(true)) {
                LOGGER.error("Failed to cancel task.");
            }
            
            progress.setCurrentAction("Cancelling...");
            progress.setProgress(ProgressMeasureUtil.DONE);
        });

        pane.setContent(box);
        pane.setMaxWidth(Double.MAX_VALUE);

        final Scene scene = pane.getScene();
        BrandUtil.applyBrand(injector, stage, scene);

        if (!progress.isDone()) {
            dialog.showAndWait();
        }
    }

    @Override
    public void showIssues() {
        loader.loadAsModal("ProjectProblem");
    }

    @Override
    public void showNotification(String message) {
        showNotification(message, FontAwesome.EXCLAMATION_CIRCLE);
    }

    @Override
    public void showNotification(String message, Icon icon) {
        showNotification(message, icon, Palette.INFO);
    }

    @Override
    public void showNotification(String message, Runnable action) {
        showNotification(message, FontAwesome.EXCLAMATION_CIRCLE, Palette.INFO, action);
    }

    @Override
    public void showNotification(String message, Palette palette) {
        showNotification(message, FontAwesome.EXCLAMATION_CIRCLE, palette);
    }

    @Override
    public void showNotification(String message, Icon icon, Palette palette) {
        showNotification(message, icon, palette, () -> {});
    }

    @Override
    public void showNotification(String message, Icon icon, Palette palette, Runnable action) {
        runLater(() -> 
            notifications.add(new NotificationImpl(message, icon, palette, action))
        );
    }

    ////////////////////////////////////////////////////////////////////////////
    //                                  Other                                 //
    ////////////////////////////////////////////////////////////////////////////

    @Override
    public void clearLog() {
        runLater(outputMessages::clear);
    }

    @Override
    public void log(Label line) {
        runLater(() -> outputMessages.add(line));
    }

    @Override
    public void browse(String url) {
        application.getHostServices().showDocument(url);
    }
}
