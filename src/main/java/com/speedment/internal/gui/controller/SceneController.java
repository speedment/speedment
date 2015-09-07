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
package com.speedment.internal.gui.controller;

import com.speedment.Speedment;
import com.speedment.internal.core.code.MainGenerator;
import com.speedment.config.Project;
import com.speedment.config.aspects.Child;
import com.speedment.config.Node;
import com.speedment.internal.gui.MainApp;
import com.speedment.internal.gui.controller.NotificationController.Notification;
import com.speedment.internal.gui.icon.SpeedmentIcon;
import com.speedment.internal.gui.icon.SilkIcon;
import com.speedment.internal.gui.properties.TableProperty;
import com.speedment.internal.gui.properties.TablePropertyManager;
import com.speedment.internal.gui.properties.TablePropertyRow;
import com.speedment.internal.gui.util.FadeAnimation;
import com.speedment.internal.logging.Logger;
import com.speedment.internal.logging.LoggerManager;
import com.speedment.internal.util.Statistics;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.speedment.internal.gui.MainApp.showWebsite;
import static com.speedment.internal.gui.controller.NotificationController.showNotification;
import static com.speedment.internal.gui.util.ProjectUtil.*;
import static javafx.animation.Animation.INDEFINITE;
import static javafx.animation.Interpolator.EASE_BOTH;
import static javafx.scene.control.SelectionMode.MULTIPLE;
import static javafx.util.Duration.ZERO;
import static com.speedment.internal.util.NullUtil.requireNonNulls;
import static com.speedment.internal.gui.util.FadeAnimation.fadeIn;
import static java.util.Objects.requireNonNull;
import static javafx.util.Duration.millis;

/**
 * FXML Controller class for the main window of the GUI.
 *
 * @author Emil Forslund
 */
public final class SceneController implements Initializable {
    
    private final static Logger LOGGER = LoggerManager.getLogger(SceneController.class);
    private final static String GITHUB_URL = "https://github.com/speedment/speedment";

    @FXML private Button buttonNew;
    @FXML private Button buttonOpen;
    @FXML private Button buttonGenerate;
    @FXML private ImageView logo;
    @FXML private TreeView<Child<?>> treeHierarchy;
    @FXML private VBox propertiesContainer;
    @FXML private WebView output;
    @FXML private MenuItem mbNew;
    @FXML private MenuItem mbOpen;
    @FXML private MenuItem mbSave;
    @FXML private MenuItem mbSaveAs;
    @FXML private MenuItem mbQuit;
    @FXML private MenuItem mbGenerate;
    @FXML private MenuItem mbGitHub;
    @FXML private MenuItem mbAbout;
    @FXML private StackPane arrowContainer;
    @FXML private Label arrow;

    private final Speedment speedment;
    private final Stage stage;
    private Project project;
    private File savedFile;
    private TablePropertyManager propertyMgr;

    /**
     * Initializes the scene by specifying the stage to show it in as well as the project node to work with in the GUI.
     *
     * @param speedment  the {@link Speedment} instance
     * @param stage      the stage
     * @param project    the project to work with
     */
    private SceneController(Speedment speedment, Stage stage, Project project) {
        this (speedment, stage, project, null);
    }

    /**
     * Initializes the scene by specifying the stage to show it in, the project node to work with and a file to use
     * when fast-saving.
     *
     * @param speedment  the {@link Speedment} instance
     * @param stage      the stage to show the GUI in
     * @param project    the project to work with
     * @param savedFile  an optional file to fast-save changes to that can be null
     */
    private SceneController(Speedment speedment, Stage stage, Project project, File savedFile) {
        this.speedment = requireNonNull(speedment);
        this.stage     = requireNonNull(stage);
        this.project   = requireNonNull(project);
        this.savedFile = savedFile; // Nullable
    }

    /**
     * Initializes the controller class.
     *
     * @param url  the URL to use
     * @param rb   the ResourceBundle to use
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        requireNonNull(url);
        propertyMgr = new TablePropertyManager(speedment, treeHierarchy);

        populateTree(project);
        arrow.setOpacity(0);

        mbNew.setGraphic(SpeedmentIcon.NEW_PROJECT.view());
        mbOpen.setGraphic(SpeedmentIcon.OPEN_PROJECT.view());
        mbSave.setGraphic(SilkIcon.DISK.view());
        mbSaveAs.setGraphic(SilkIcon.DISK_MULTIPLE.view());
        mbQuit.setGraphic(SilkIcon.DOOR_IN.view());
        mbGenerate.setGraphic(SpeedmentIcon.RUN_PROJECT.view());
        mbGitHub.setGraphic(SilkIcon.USER_COMMENT.view());
        mbAbout.setGraphic(SilkIcon.INFORMATION.view());

        buttonNew.setGraphic(SpeedmentIcon.NEW_PROJECT_24.view());
        buttonOpen.setGraphic(SpeedmentIcon.OPEN_PROJECT_24.view());
        buttonGenerate.setGraphic(SpeedmentIcon.RUN_PROJECT_24.view());

        // New project.
        final EventHandler<ActionEvent> newProject = ev -> {
            writeToLog("Creating new project.");
            final Stage newStage = new Stage();
            ProjectPromptController.showIn(speedment, newStage);
        };

        buttonNew.setOnAction(newProject);
        mbNew.setOnAction(newProject);

        // Open project.
        final EventHandler<ActionEvent> openProject = createOpenProjectHandler(
            speedment, stage, (f, p) -> {
                
            savedFile = f;
            treeHierarchy.setRoot(branch(p));
            project = p;
            writeToLog("Opened config file: " + savedFile);
        });

        buttonOpen.setOnAction(openProject);
        mbOpen.setOnAction(openProject);

        // Save application
        mbSave.setOnAction(createSaveProjectHandler(this, f -> {
            savedFile = f;
            writeToLog("Saved config file: " + savedFile);
        }));

        // Save application as
        mbSaveAs.setOnAction(createSaveAsProjectHandler(this, f -> {
            savedFile = f;
            writeToLog("Saved config file: " + savedFile);
        }));

        // Help
        mbGitHub.setOnAction(ev -> showWebsite(GITHUB_URL));
        logo.setOnMousePressed(ev -> showWebsite(GITHUB_URL));

        // Generate code
        final EventHandler<ActionEvent> generate = ev -> {
            outputBuffer.delete(0, outputBuffer.length());
            final Instant started = Instant.now();
            writeToLog("Generating classes " + project.getPackageName() + "." + project.getName() + ".*");
            writeToLog("Target directory is " + project.getPackageLocation());

            final MainGenerator instance = new MainGenerator(speedment);
            
            try {
                instance.accept(project);
                writeGenerationStatus(
                    started, 
                    Instant.now(), 
                    instance.getFilesCreated(), 
                    true
                );
                
                showNotification(
                    arrowContainer, 
                    "The code generation succeeded!", 
                    Notification.SUCCESS
                );
            } catch (Exception ex) {
                writeGenerationStatus(
                    started, 
                    Instant.now(), 
                    instance.getFilesCreated(), 
                    false
                );
                LOGGER.error(ex, "Error! Failed to generate code.");
            }
            
            removeArrow();
        };

        buttonGenerate.setOnAction(generate);
        mbGenerate.setOnAction(generate);
		
		// About
		mbAbout.setOnAction(ev -> {
			AboutController.showIn(stage);
		});

        // Quit application
        mbQuit.setOnAction(ev -> {
            stage.close();
        });
        
        ActionChoiceController.showActionChoice(arrowContainer, 
            // onGenerate
            () -> generate.handle(null),
            
            // onConfigure
            () -> animateArrow()
        );
        
        // Send statistics.
        Statistics.onGuiStarted();
    }

    /**
     * Returns the stage used in this scene.
     *
     * @return  the stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Returns the project currently working on.
     *
     * @return  the project
     */
    public Project getProject() {
        return project;
    }

    /**
     * Returns the location where the project was last saved.
     *
     * @return  the location of the last save
     */
    public File getLastSaved() {
        return savedFile;
    }

    /**
     * Updates the location where the last save is located. This should be called each time the user manually saves
     * the project somewhere.
     *
     * @param savedFile  the new save location
     * @return           a reference to this controller
     */
    public SceneController setLastSaved(File savedFile) {
        this.savedFile = savedFile;
        return this;
    }

    /**
     * Populates the project tree using the nodes from the specified project.
     *
     * @param project  the project to display
     */
    private void populateTree(Project project) {
        requireNonNull(project);
        final ListChangeListener<? super TreeItem<Child<?>>> change = l ->
            populatePropertyTable(
                propertyMgr.propertiesFor(
                    l.getList().stream()
                    .map(ti -> ti.getValue())
                    .collect(Collectors.toList())
                )
            )
        ;

        treeHierarchy.setCellFactory(v -> new TreeCell<Child<?>>() {

            @Override
            protected void updateItem(Child<?> item, boolean empty) {
                // item nullable
                super.updateItem(item, requireNonNull(empty));

                if (item == null || empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setGraphic(iconFor(item));
                    setText(item.getName());
                }
            }
        });

        treeHierarchy.getSelectionModel().setSelectionMode(MULTIPLE);
        treeHierarchy.getSelectionModel().getSelectedItems().addListener(change);
        treeHierarchy.setRoot(branch(project));
    }

    /**
     * Returns the appropriate icon for the specified node type. This is used when constructing the project tree.
     *
     * @param node  the node
     * @return      the appropriate icon
     * @see         #populateTree(Project)
     */
    private ImageView iconFor(Node node) {
        requireNonNull(node);
        final SpeedmentIcon icon = SpeedmentIcon.forNodeType(node.getInterfaceMainClass());

        if (icon == null) {
            
            LOGGER.error("Internal error.", new RuntimeException(
                "Unknown node type '" + node.getInterfaceMainClass().getName() + "'."
            ));
            
            return SpeedmentIcon.QUESTION.view();
        } else {
            return icon.view();
        }
    }

    /**
     * Creates a new branch in the tree to represent the specified node. This is used when constructing the project
     * tree.
     *
     * @param node  the node
     * @return      the created branch
     */
    private TreeItem<Child<?>> branch(Child<?> node) {
        requireNonNull(node);
        final TreeItem<Child<?>> branch = new TreeItem<>(node);
        branch.setExpanded(true);

        node.asParent().ifPresent(p ->
            p.stream().map(this::branch).forEachOrdered(
                branch.getChildren()::add
            )
        );

        return branch;
    }

    /**
     * Populates the property table with the specified values.
     *
     * @param properties  the properties
     */
    private void populatePropertyTable(Stream<TableProperty<?>> properties) {
        requireNonNull(properties);
        propertiesContainer.getChildren().clear();

        properties.forEachOrdered(p -> {
            final HBox row = new TablePropertyRow<>(p);
            propertiesContainer.getChildren().add(row);
        });
    }

    /**
     * Animates the arrow if it exists in the arrow container.
     */
    private void animateArrow() {
        if (arrowContainer.getChildren().contains(arrow)) {
            fadeIn(arrow);

            final DropShadow glow = new DropShadow();
            glow.setBlurType(BlurType.TWO_PASS_BOX);
            glow.setColor(Color.rgb(0, 255, 255, 1.0));
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(0.0);
            arrow.setEffect(glow);

            final KeyFrame kf0 = new KeyFrame(ZERO,
                new KeyValue(arrow.translateXProperty(), 145, EASE_BOTH),
                new KeyValue(arrow.translateYProperty(), -15, EASE_BOTH),
                new KeyValue(glow.radiusProperty(), 32, EASE_BOTH)
            );

            final KeyFrame kf1 = new KeyFrame(millis(400),
                new KeyValue(arrow.translateXProperty(), 135, EASE_BOTH),
                new KeyValue(arrow.translateYProperty(), 5, EASE_BOTH),
                new KeyValue(glow.radiusProperty(), 0, EASE_BOTH)
            );

            final Timeline tl = new Timeline(kf0, kf1);
            tl.setAutoReverse(true);
            tl.setCycleCount(INDEFINITE);
            tl.play();

            final EventHandler<MouseEvent> over = ev -> removeArrow();

            arrow.setOnMouseEntered(over);
        }
    }

    /**
     * Removes the animated arrow if it exists.
     */
    private void removeArrow() {
        if (arrowContainer.getChildren().contains(arrow)) {
            if (arrow.getOpacity() > 0) {
                FadeAnimation.fadeOut(arrow, e -> arrowContainer.getChildren().remove(arrow));
            } else {
                arrowContainer.getChildren().remove(arrow);
            }
        }
    }

    /**
     * Creates and configures a new GUI scene in the specified stage to work with the specified project.
     *
     * @param speedment  the {@link Speedment} instance
     * @param stage      the stage to display it in
     * @param project    the project node to work with
     * @return           the newly created scene controller
     */
    public static SceneController showIn(Speedment speedment, Stage stage, Project project) {
        requireNonNulls(speedment, stage, project);
        final FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/Scene.fxml"));
        final SceneController control = new SceneController(speedment, stage, project);
        loader.setController(control);

        try {
            final Parent root = (Parent) loader.load();
            final Scene scene = new Scene(root);

            stage.hide();
            stage.setTitle("Speedment");
            stage.setMaximized(true);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        return control;
    }

    /**
     * Creates and configures a new GUI scene in the specified stage to work with the specified project. The specified
     * save location will be used when the user quick-saves.
     *
     * @param speedment  the {@link Speedment} instance
     * @param stage      the stage to display it in
     * @param project    the project to work on
     * @param savedFile  location for quick-saves
     * @return           the created scene controller
     */
    public static SceneController showIn(Speedment speedment, Stage stage, Project project, File savedFile) {
        requireNonNulls(speedment, stage, project, savedFile);
        return Optional.ofNullable(showIn(speedment, stage, project))
            .map(sc -> sc.setLastSaved(savedFile))
            .orElse(null);
    }

    private final StringBuilder outputBuffer = new StringBuilder();

    /**
     * Writes the specified message to the system log and also shows it in the GUI log.
     *
     * @param msg  the message
     */
    private void writeToLog(String msg) {
        requireNonNull(msg);
        outputBuffer.append("<p style=\"font-family:Courier,monospace;font-size:12px;margin:0px;padding:0px;\">").append(msg).append("</p>");
        output.getEngine().loadContent(outputBuffer.toString());
        LOGGER.info(msg);
    }

    /**
     * Writes a special message to the GUI log to notify the user that the generation is complete.
     *
     * @param started       the time when the generation started
     * @param finished      the time when the generation finished
     * @param filesCreated  the number of files that was created
     * @param succeeded     <code>true</code> if the generation was successful, else <code>false</code>
     */
    private void writeGenerationStatus(Instant started, Instant finished, int filesCreated, boolean succeeded) {
        requireNonNulls(started, finished);
        final LocalDateTime ldt = LocalDateTime.ofInstant(finished, ZoneId.systemDefault());
        final DateTimeFormatter format = DateTimeFormatter.RFC_1123_DATE_TIME.withZone(ZoneId.systemDefault());
        final String status = succeeded ? ": Generation completed! :" : "-: Generation failed! :--";
        final String color = succeeded ? "lightgreen" : "lightpink";
        
        final Duration dur = Duration.between(started, finished);
        final long durSecs = dur.getSeconds();
        final long durMils = dur.multipliedBy(1000).getSeconds() % 1000;
        String strMils = Long.toString(durMils);
        switch (strMils.length()) {
            case 1 : strMils = "00" + strMils; break;
            case 2 : strMils = "0" + strMils; break;
        }
        
        try {
            writeToLog("<pre style=\"background:" + color + ";\">" + 
                ".------------" + status + "------------." + "\n" +
                " Total time: " + durSecs + "." + strMils + "s\n" +
                " Finished at: " + format.format(finished) + "\n" +
                " Files generated: " + filesCreated + "\n" +
                "'-------------------------------------------------'</pre>"
            );
        } catch (UnsupportedTemporalTypeException ex) {
            LOGGER.error(ex, "Could not parse time correctly.");
            writeToLog("<span style=\"background:lightpink;\">Time parsing failed unexpectedly.</span>");
        }
    }
}
