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
package com.speedment.tool.internal.component;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.IncludeInjectable;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.common.mapstream.MapStream;
import com.speedment.generator.TranslatorManager;
import com.speedment.generator.util.JavaLanguageNamer;
import com.speedment.runtime.component.PasswordComponent;
import com.speedment.runtime.component.ProjectComponent;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Schema;
import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.runtime.internal.component.InternalOpenSourceComponent;
import com.speedment.runtime.internal.config.immutable.ImmutableProject;
import com.speedment.runtime.internal.license.AbstractSoftware;
import com.speedment.runtime.internal.license.OpenSourceLicense;
import com.speedment.runtime.internal.util.Settings;
import com.speedment.runtime.license.Software;
import com.speedment.runtime.util.ProgressMeasure;
import com.speedment.tool.MainApp;
import com.speedment.tool.brand.Brand;
import com.speedment.tool.brand.Palette;
import com.speedment.tool.component.DocumentPropertyComponent;
import com.speedment.tool.component.UserInterfaceComponent;
import com.speedment.tool.config.DbmsProperty;
import com.speedment.tool.config.DocumentProperty;
import com.speedment.tool.config.ProjectProperty;
import com.speedment.tool.internal.brand.SpeedmentBrand;
import com.speedment.tool.internal.notification.NotificationImpl;
import com.speedment.tool.internal.util.ConfigFileHelper;
import com.speedment.tool.internal.util.InjectionLoader;
import com.speedment.tool.notification.Notification;
import com.speedment.tool.util.BrandUtil;
import com.speedment.tool.util.OutputUtil;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.controlsfx.control.PropertySheet;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.speedment.runtime.internal.util.TextUtil.alignRight;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static javafx.application.Platform.runLater;

/**
 *
 * @author Emil Forslund
 */
@IncludeInjectable({
    DocumentPropertyComponentImpl.class,
    SpeedmentBrand.class,
    InjectionLoader.class,
    ConfigFileHelper.class
})
public final class UserInterfaceComponentImpl extends InternalOpenSourceComponent implements UserInterfaceComponent {
    
    private final static Logger LOGGER = LoggerManager.getLogger(UserInterfaceComponentImpl.class);

    private static final String GITHUB_URI = "https://github.com/speedment/speedment/";
    private static final String GITTER_URI = "https://gitter.im/speedment/speedment/";
    public static final String DIALOG_PANE_ICON_SIZE = "2.5em";
    
    private static final Predicate<File> OPEN_DIRECTORY_CONDITIONS = file
        -> file != null
        && file.exists()
        && file.isDirectory();
    
    private static final Predicate<Optional<char[]>> NO_PASSWORD_SPECIFIED
        = pass -> !pass.isPresent() || pass.get().length == 0;
    
    private final ObjectProperty<StoredNode> hiddenProjectTree = new SimpleObjectProperty<>();
    private final ObjectProperty<StoredNode> hiddenWorkspace   = new SimpleObjectProperty<>();
    private final ObjectProperty<StoredNode> hiddenOutput      = new SimpleObjectProperty<>();
    private final ObjectProperty<StoredNode> hiddenPreview     = new SimpleObjectProperty<>();
    
    private final ObservableList<Notification> notifications;
    private final ObservableList<Node> outputMessages;
    private final ObservableList<TreeItem<DocumentProperty>> selectedTreeItems;
    private final ObservableList<PropertySheet.Item> properties;
    private final Map<Class<?>, List<UserInterfaceComponent.ContextMenuBuilder<?>>> contextMenuBuilders;
    
    private @Inject DocumentPropertyComponent documentPropertyComponent;
    private @Inject TranslatorManager translatorManager;
    private @Inject JavaLanguageNamer javaLanguageNamer;
    private @Inject PasswordComponent passwordComponent;
    private @Inject ProjectComponent projectComponent;
    private @Inject ConfigFileHelper configFileHelper;
    private @Inject Injector injector;
    private @Inject Brand brand;
    
    private Stage stage;
    private Application application;
    private ProjectProperty project;
    
    private UserInterfaceComponentImpl() {
        notifications        = FXCollections.observableArrayList();
        outputMessages       = FXCollections.observableArrayList();
        selectedTreeItems    = FXCollections.observableArrayList();
        properties           = FXCollections.observableArrayList();
        contextMenuBuilders  = new ConcurrentHashMap<>();
    }
    
    public void start(Application application, Stage stage) {
        this.stage       = requireNonNull(stage);
        this.application = requireNonNull(application);
        this.project     = new ProjectProperty();
        
        final Project loaded = projectComponent.getProject();
        if (loaded != null) {
            project.merge(documentPropertyComponent, loaded);
        }
    }
    
    /*************************************************************/
    /*                      Component Methods                    */
    /*************************************************************/
    
    @Override
    protected String getDescription() {
        return "Allows various parts of the user interface to communicate with each other.";
    }
    
    @Override
    public Stream<Software> getDependencies() {
        return Stream.of(
            AbstractSoftware.with("Silk",          "1.3",     OpenSourceLicense.CC_BY_2_5),
            AbstractSoftware.with("ControlsFX",    "8.40.10", OpenSourceLicense.BSD_3_CLAUSE),
            AbstractSoftware.with("FontawesomeFX", "8.9",     OpenSourceLicense.APACHE_2)
        );
    }
    
    /*************************************************************/
    /*                     Global properties                     */
    /*************************************************************/
 
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
    public ObservableList<PropertySheet.Item> getProperties() {
        return properties;
    }
    
    /*************************************************************/
    /*                      Menubar actions                      */
    /*************************************************************/

    @Override
    public void newProject() {
        try {
            MainApp.setInjector(injector.newBuilder().build());
            final MainApp app    = new MainApp();
            final Stage newStage = new Stage();
            app.start(newStage);
        } catch (final Exception e) {
            LOGGER.error(e);
            log(OutputUtil.error(e.getMessage()));
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

        Optional.ofNullable(Settings.inst().get("project_location"))
            .map(File::new)
            .filter(OPEN_DIRECTORY_CONDITIONS)
            .ifPresent(fileChooser::setInitialDirectory);

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
                .forEach(this::showPasswordDialog);

            final Optional<String> schemaName = project
                .dbmses().flatMap(Dbms::schemas)
                .map(Schema::getName)
                .findAny();

            if (schemaName.isPresent()) {
                project.dbmses()
                    .map(DbmsProperty.class::cast)
                    .forEach(dbms -> configFileHelper.loadFromDatabase(dbms, schemaName.get()));
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
        clearLog();

        if (!configFileHelper.isFileOpen()) {
            configFileHelper.setCurrentlyOpenFile(new File(ConfigFileHelper.DEFAULT_CONFIG_LOCATION));
        }

        configFileHelper.saveConfigFile(configFileHelper.getCurrentlyOpenFile());

//        final Stopwatch stopwatch = Stopwatch.createStarted();
        log(OutputUtil.info("Generating classes " + javaLanguageNamer.findPackageName(project) + "." + project.getName() + ".*"));
        log(OutputUtil.info("Target directory is " + project.getPackageLocation()));

        final Project immutableProject = ImmutableProject.wrap(project);
        projectComponent.setProject(immutableProject);

        try {
            translatorManager.accept(immutableProject);
//            stopwatch.stop();

            log(OutputUtil.success(
                "+------------: Generation completed! :------------+" + "\n"
//                + "| Total time       " + alignRight(stopwatch.toString(), 30) + " |\n"
                + "| Files generated  " + alignRight("" + Integer.toString(translatorManager.getFilesCreated()), 30) + " |\n"
                + "+-------------------------------------------------+"
            ));

            showNotification(
                "Generation completed! " + translatorManager.getFilesCreated()
                + " files created.",
                FontAwesomeIcon.STAR,
                Palette.SUCCESS
            );
        } catch (final Exception ex) {
//            if (!stopwatch.isStopped()) {
//                stopwatch.stop();
//            }

            log(OutputUtil.error(
                "+--------------: Generation failed! :-------------+" + "\n"
//                + "| Total time       " + alignRight(stopwatch.toString(), 30) + " |\n"
                + "| Files generated  " + alignRight("" + Integer.toString(translatorManager.getFilesCreated()), 30) + " |\n"
                + "| Exception Type   " + alignRight(ex.getClass().getSimpleName(), 30) + " |\n"
                + "+-------------------------------------------------+"
            ));

            final String msg = "Error! Failed to generate code. A " + ex.getClass().getSimpleName() + " was thrown.";

            LOGGER.error(ex, msg);
            showError("Failed to generate code", ex.getMessage(), ex);
        }
    }

    @Override
    public void toggleProjectTree() {
        toggle("projectTree", hiddenProjectTree, StoredNode.InsertAt.BEGINNING);
    }

    @Override
    public void toggleWorkspace() {
        toggle("workspace", hiddenWorkspace, StoredNode.InsertAt.BEGINNING);
    }

    @Override
    public void toggleOutput() {
        toggle("output", hiddenOutput, StoredNode.InsertAt.END);
    }

    @Override
    public void togglePreview() {
        toggle("preview", hiddenPreview, StoredNode.InsertAt.END);
    }

    @Override
    public void showGitter() {
        browse(GITTER_URI);
    }

    @Override
    public void showGithub() {
        browse(GITHUB_URI);
    }
    
    private void toggle(String cssId, ObjectProperty<StoredNode> hidden, StoredNode.InsertAt insertAt) {
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
    }
    
    /*************************************************************/
    /*                      Dialog messages                      */
    /*************************************************************/

    @Override
    public void showError(String title, String message) {
        showError(title, message, null);
    }

    @Override
    public void showError(String title, String message, Throwable ex) {
        LOGGER.error(ex, message);

        final Alert alert = new Alert(Alert.AlertType.ERROR);
        final Scene scene = alert.getDialogPane().getScene();

        BrandUtil.applyBrand(injector, stage, scene);

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

    @Override
    public Optional<ButtonType> showWarning(String title, String message) {
        LOGGER.warn(message);

        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        final Scene scene = alert.getDialogPane().getScene();

        BrandUtil.applyBrand(injector, stage, scene);

        alert.setTitle("Confirmation");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.WARNING, DIALOG_PANE_ICON_SIZE));

        return alert.showAndWait();
    }

    @Override
    public void showPasswordDialog(DbmsProperty dbms) {
        final Dialog<Pair<String, char[]>> dialog = new Dialog<>();
        dialog.setTitle("Authentication Required");
        dialog.setHeaderText("Enter password for " + dbms.getName());
        dialog.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.LOCK, DIALOG_PANE_ICON_SIZE));
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

        cancel.setOnAction(ev -> task.cancel(true));

        pane.setContent(box);
        pane.setMaxWidth(Double.MAX_VALUE);

        final Scene scene = pane.getScene();
        BrandUtil.applyBrand(injector, stage, scene);

        if (!progress.isDone()) {
            dialog.showAndWait();
        }
    }

    @Override
    public void showNotification(String message) {
        showNotification(message, FontAwesomeIcon.EXCLAMATION);
    }

    @Override
    public void showNotification(String message, FontAwesomeIcon icon) {
        showNotification(message, icon, Palette.INFO);
    }

    @Override
    public void showNotification(String message, Runnable action) {
        showNotification(message, FontAwesomeIcon.EXCLAMATION, Palette.INFO, action);
    }

    @Override
    public void showNotification(String message, Palette palette) {
        showNotification(message, FontAwesomeIcon.EXCLAMATION, palette);
    }

    @Override
    public void showNotification(String message, FontAwesomeIcon icon, Palette palette) {
        showNotification(message, icon, palette, () -> {});
    }

    @Override
    public void showNotification(String message, FontAwesomeIcon icon, Palette palette, Runnable action) {
        notifications.add(new NotificationImpl(message, icon, palette, action));
    }
    
    /*************************************************************/
    /*                      Context Menues                       */
    /*************************************************************/

    @Override
    public <DOC extends DocumentProperty & HasMainInterface> void installContextMenu(Class<? extends DOC> nodeType, ContextMenuBuilder<DOC> menuBuilder) {
        contextMenuBuilders.computeIfAbsent(nodeType, k -> new CopyOnWriteArrayList<>()).add(menuBuilder);
    }

    @Override
    public <DOC extends DocumentProperty & HasMainInterface> Optional<ContextMenu> createContextMenu(TreeCell<DocumentProperty> treeCell, DOC doc) {
        requireNonNulls(treeCell, doc);
        
        @SuppressWarnings("unchecked")
        final List<UserInterfaceComponent.ContextMenuBuilder<DOC>> builders = 
            MapStream.of(contextMenuBuilders)
                .filterKey(clazz -> clazz.isAssignableFrom(doc.getClass()))
                .values()
                .flatMap(List::stream)
                .map(builder -> (UserInterfaceComponent.ContextMenuBuilder<DOC>) builder)
                .collect(toList());
        
        final ContextMenu menu = new ContextMenu();
        for (int i = 0; i < builders.size(); i++) {
            final List<MenuItem> items = builders.get(i)
                .build(treeCell, doc)
                .collect(toList());
            
            if (i > 0 && !items.isEmpty()) {
                menu.getItems().add(new SeparatorMenuItem());
            }
            
            items.forEach(menu.getItems()::add);
        }
        
        if (menu.getItems().isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(menu);
        }
    }
    
    /*************************************************************/
    /*                            Other                          */
    /*************************************************************/

    @Override
    public void clearLog() {
        outputMessages.clear();
    }

    @Override
    public void log(Label line) {
        outputMessages.add(line);
    }

    @Override
    public void browse(String url) {
        application.getHostServices().showDocument(url);
    }
    
    private static final class StoredNode {

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
}