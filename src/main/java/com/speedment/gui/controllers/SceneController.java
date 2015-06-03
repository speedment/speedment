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
package com.speedment.gui.controllers;

import com.speedment.core.code.model.java.MainGenerator;
import com.speedment.core.config.model.Project;
import com.speedment.core.config.model.aspects.Child;
import com.speedment.core.config.model.aspects.Node;
import com.speedment.gui.MainApp;
import static com.speedment.gui.MainApp.showWebsite;
import com.speedment.gui.icons.Icons;
import com.speedment.gui.icons.SilkIcons;
import com.speedment.gui.log.GUIAppender;
import com.speedment.gui.properties.TableProperty;
import com.speedment.gui.properties.TablePropertyManager;
import com.speedment.gui.properties.TablePropertyRow;
import com.speedment.gui.util.FadeAnimation;
import static com.speedment.gui.util.ProjectUtil.createOpenProjectHandler;
import static com.speedment.gui.util.ProjectUtil.createSaveAsProjectHandler;
import static com.speedment.gui.util.ProjectUtil.createSaveProjectHandler;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static javafx.animation.Animation.INDEFINITE;
import static javafx.animation.Interpolator.EASE_BOTH;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import static javafx.scene.control.SelectionMode.MULTIPLE;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import static javafx.util.Duration.ZERO;
import static javafx.util.Duration.millis;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * FXML Controller class
 *
 * @author Emil Forslund
 */
public class SceneController implements Initializable {
    
    private final static Logger LOGGER = LogManager.getLogger(SceneController.class);
    private final static String GITHUB_URL = "https://github.com/speedment/speedment";

    @FXML private Button buttonNew;
    @FXML private Button buttonOpen;
    @FXML private Button buttonGenerate;
    @FXML private ImageView logo;
    @FXML private TreeView<Child<?>> treeHierarchy;
    @FXML private TableView<String> tableProjectSettings;
    @FXML private VBox propertiesContainer;
    @FXML private TextArea output;
    @FXML private Menu menuFile;
    @FXML private MenuItem mbNew;
    @FXML private MenuItem mbOpen;
    @FXML private MenuItem mbSave;
    @FXML private MenuItem mbSaveAs;
    @FXML private MenuItem mbQuit;
    @FXML private Menu menuEdit;
    @FXML private MenuItem mbGenerate;
    @FXML private Menu menuHelp;
    @FXML private MenuItem mbGitHub;
    @FXML private MenuItem mbAbout;
    @FXML private StackPane arrowContainer;
    @FXML private Label arrow;

    private File savedFile;
    private final Stage stage;
    private Project project;
    private TablePropertyManager propertyMgr;

    public SceneController(Stage stage, Project project) {
        this.stage = stage;
        this.project = project;
    }

    /**
     * Initializes the controller class.
     *
     * @param url the URL to use
     * @param rb the ResourceBundle to use
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.propertyMgr = new TablePropertyManager(treeHierarchy);

        // Show LOGGER output in the output area.
        GUIAppender.setup(output.textProperty());
        populateTree(project);
        animateArrow();

        mbNew.setGraphic(Icons.NEW_PROJECT.view());
        mbOpen.setGraphic(Icons.OPEN_PROJECT.view());
        mbSave.setGraphic(SilkIcons.DISK.view());
        mbSaveAs.setGraphic(SilkIcons.DISK_MULTIPLE.view());
        mbQuit.setGraphic(SilkIcons.DOOR_IN.view());
        mbGenerate.setGraphic(Icons.RUN_PROJECT.view());
        mbGitHub.setGraphic(SilkIcons.USER_COMMENT.view());
        mbAbout.setGraphic(SilkIcons.INFORMATION.view());

        buttonNew.setGraphic(Icons.NEW_PROJECT_24.view());
        buttonOpen.setGraphic(Icons.OPEN_PROJECT_24.view());
        buttonGenerate.setGraphic(Icons.RUN_PROJECT_24.view());

        // New project.
        final EventHandler<ActionEvent> newProject = ev -> {
            LOGGER.info("Creating new project.");
            final Stage newStage = new Stage();
            ProjectPromptController.showIn(newStage);
        };

        buttonNew.setOnAction(newProject);
        mbNew.setOnAction(newProject);

        // Open project.
//        final EventHandler<ActionEvent> openProject = ev -> {
//            System.out.println("Load project");
//            final FileChooser fileChooser = new FileChooser();
//            fileChooser.setTitle("Open Groovy File");
//            fileChooser.setSelectedExtensionFilter(new ExtensionFilter("Groovy files (*.groovy)", "*.groovy"));
//            File file = fileChooser.showOpenDialog(stage);
//
//            try {
//                final Project p = Project.newProject();
//                GroovyParser.fromGroovy(p, file.toPath());
//                treeHierarchy.setRoot(branch(p));
//                project = p;
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                final Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
//                alert.showAndWait();
//            }
//
//        };
        final EventHandler<ActionEvent> openProject = createOpenProjectHandler(stage, (f, p) -> {
            savedFile = f;
            treeHierarchy.setRoot(branch(p));
            project = p;
            LOGGER.info("Opened config file: " + savedFile);
        });

        buttonOpen.setOnAction(openProject);
        mbOpen.setOnAction(openProject);

        // Save application
        mbSave.setOnAction(createSaveProjectHandler(this, f -> {
            savedFile = f;
            LOGGER.info("Saved config file: " + savedFile);
        }));

        // Save application as
        mbSaveAs.setOnAction(createSaveAsProjectHandler(this, f -> {
            savedFile = f;
            LOGGER.info("Saved config file: " + savedFile);
        }));

        // Help
        mbGitHub.setOnAction(ev -> showWebsite(GITHUB_URL));
        logo.setOnMousePressed(ev -> showWebsite(GITHUB_URL));

        // Generate code
        final EventHandler<ActionEvent> generate = ev -> {
            LOGGER.info("Generating classes " + project.getPacketName() + "." + project.getName() + ".*");
            LOGGER.info("Target directory is " + project.getPacketLocation());

            try {
                final MainGenerator instance = new MainGenerator();
                instance.accept(project);
                LOGGER.info("Generation completed!");
            } catch (Exception ex) {
                LOGGER.error("Error! Failed to generate code.", ex);
            }
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
    }

    public Stage getStage() {
        return stage;
    }

    public Project getProject() {
        return project;
    }

    public File getLastSaved() {
        return savedFile;
    }

    private void populateTree(Project project) {
        final ListChangeListener<? super TreeItem<Child<?>>> change = l -> {

            populatePropertyTable(
                    propertyMgr.propertiesFor(
                            l.getList().stream()
                            .map(i -> i.getValue())
                            .collect(Collectors.toList())
                    )
            );
        };

        treeHierarchy.setCellFactory(v -> new TreeCell<Child<?>>() {

            @Override
            protected void updateItem(Child<?> item, boolean empty) {
                super.updateItem(item, empty);

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

    private ImageView iconFor(Node node) {
        final Icons icon = Icons.forNodeType(node.getInterfaceMainClass());

        if (icon == null) {
            
            LOGGER.error("Internal error.", new RuntimeException(
                "Unknown node type '" + node.getInterfaceMainClass().getName() + "'."
            ));
            
            return Icons.QUESTION.view();
        } else {
            return icon.view();
        }
    }

    private TreeItem<Child<?>> branch(Child<?> node) {
        final TreeItem<Child<?>> branch = new TreeItem<>(node);
        branch.setExpanded(true);

        node.asParent().ifPresent(p -> {
            p.stream().map(c -> branch(c)).forEach(
                    c -> branch.getChildren().add(c)
            );
        });

        return branch;
    }

    private void populatePropertyTable(Stream<TableProperty<?>> properties) {
        propertiesContainer.getChildren().clear();

        properties.collect(Collectors.toSet()).forEach(p -> {
            final HBox row = new TablePropertyRow<>(p);
            propertiesContainer.getChildren().add(row);
        });
    }

    private void animateArrow() {
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

        final EventHandler<MouseEvent> over = ev -> {
            FadeAnimation.fadeOut(arrow, e -> arrowContainer.getChildren().remove(arrow));
        };

        arrow.setOnMouseEntered(over);
    }

    public static void showIn(Stage stage, Project project) {
        final FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/Scene.fxml"));
        final SceneController control = new SceneController(stage, project);
        loader.setController(control);

        try {
            final Parent root = (Parent) loader.load();
            final Scene scene = new Scene(root);

            stage.hide();
            stage.setTitle("Speedment ORM");
            stage.setMaximized(true);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
