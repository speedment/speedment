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
import com.speedment.config.Dbms;
import com.speedment.config.Project;
import com.speedment.config.aspects.Child;
import com.speedment.config.parameters.DbmsType;
import com.speedment.db.DbmsHandler;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.core.platform.component.DbmsHandlerComponent;
import com.speedment.internal.gui.MainApp;
import com.speedment.internal.util.Settings;
import com.speedment.internal.logging.Logger;
import com.speedment.internal.logging.LoggerManager;
import com.speedment.internal.util.Trees;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.speedment.internal.gui.controller.AlertController.showAlert;
import static com.speedment.internal.gui.util.ProjectUtil.createOpenProjectHandler;
import static com.speedment.internal.util.NullUtil.requireNonNulls;
import java.util.Optional;
import java.util.function.Supplier;
import static java.util.Objects.requireNonNull;

/**
 * FXML Controller class for the prompt that lets the user either load an
 * existing project or create a new one by connecting to a database.
 *
 * @author Emil Forslund
 */
public final class ProjectPromptController implements Initializable {

    private final static Logger LOGGER = LoggerManager.getLogger(ProjectPromptController.class);

    @FXML private Button buttonOpen;
    @FXML private TextField fieldHost;
    @FXML private TextField fieldPort;
    @FXML private ChoiceBox<String> fieldType;
    @FXML private TextField fieldName;
    @FXML private TextField fieldSchema;
    @FXML private TextField fieldUser;
    @FXML private PasswordField fieldPass;
    @FXML private Button buttonConnect;
    @FXML private HBox container;
    @FXML private StackPane openContainer;

    private final Stage stage;
    private final Speedment speedment;

    /**
     * Initializes the prompt by specifying the stage to display it in.
     *
     * @param stage the stage
     */
    private ProjectPromptController(Speedment speedment, Stage stage) {
        this.stage = requireNonNull(stage);
        this.speedment = requireNonNull(speedment);
    }

    /**
     * Initializes the controller class.
     *
     * @param url the URL to use
     * @param rb the ResourceBundle to use
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        requireNonNulls(url, rb);
        try {
            if (Settings.inst().get("hide_open_option", true)) {
                container.getChildren().remove(openContainer);
            }

            fieldType.setItems(
                getDbmsTypes()
                .map(DbmsType::getName)
                .collect(Collectors.toCollection(FXCollections::observableArrayList))
            );

            fieldSchema.setText(Settings.inst().get("last_known_schema", ""));
            fieldPort.setText(Settings.inst().get("last_known_port", ""));

            fieldType.getSelectionModel().selectedItemProperty().addListener((observable, old, next) -> {
                if (!observable.getValue().isEmpty()) {
                    final DbmsType item = findDbmsType(next)
                        .orElseThrow(dbmsTypeNotInstalled(next));

                    if (fieldHost.textProperty().getValue().isEmpty()) {
                        fieldHost.textProperty().setValue("127.0.0.1");
                    }

                    if (fieldUser.textProperty().getValue().isEmpty()) {
                        fieldUser.textProperty().setValue("root");
                    }
                    if (fieldName.textProperty().getValue().isEmpty()) {
                        fieldName.textProperty().setValue("db0");
                    }

                    fieldPort.textProperty().setValue("" + item.getDefaultPort());
                }
            });

            fieldHost.textProperty().addListener((ov, o, n) -> toggleConnectButton());
            fieldPort.textProperty().addListener((ov, o, n) -> toggleConnectButton());
            fieldType.selectionModelProperty().addListener((ov, o, n) -> toggleConnectButton());
            fieldName.textProperty().addListener((ov, o, n) -> toggleConnectButton());
            fieldSchema.textProperty().addListener((ov, o, n) -> toggleConnectButton());
            fieldUser.textProperty().addListener((ov, o, n) -> toggleConnectButton());

            fieldType.getSelectionModel().select(Settings.inst().get("last_known_dbtype", ""));
            fieldHost.setText(Settings.inst().get("last_known_host", "127.0.0.1"));
            fieldUser.setText(Settings.inst().get("last_known_user", "root"));
            fieldName.setText(Settings.inst().get("last_known_name", "db0"));

            buttonConnect.setOnAction(ev -> {

                final String dbmsTypeName = fieldType.getSelectionModel().getSelectedItem();
                final DbmsType dbmsType = findDbmsType(dbmsTypeName)
                    .orElseThrow(dbmsTypeNotInstalled(dbmsTypeName));

                final Project project = Project.newProject(speedment);

                Dbms dbms = Dbms.newDbms(speedment);
                dbms.setIpAddress(fieldHost.getText());
                dbms.setPort(Integer.parseInt(fieldPort.getText()));
                dbms.setName(fieldName.getText());
                dbms.setUsername(fieldUser.getText());
                dbms.setPassword(fieldPass.getText());
                dbms.setType(dbmsType);
                project.add(dbms);

                project.setName(fieldSchema.getText());
                Settings.inst().set("last_known_schema", fieldSchema.getText());
                Settings.inst().set("last_known_dbtype", dbmsTypeName);
                Settings.inst().set("last_known_host", fieldHost.getText());
                Settings.inst().set("last_known_user", fieldUser.getText());
                Settings.inst().set("last_known_name", fieldName.getText());
                Settings.inst().set("last_known_port", fieldPort.getText());

                try {
                    final DbmsHandler dh = dbmsType.makeDbmsHandler(speedment, dbms);
                    dh.schemas()
                        .filter(s -> fieldSchema.getText().equalsIgnoreCase(s.getName()))
                        .forEachOrdered(dbms::add);

                    Trees.traverse((Child) project, c -> c.asParent()
                        .map(p -> p.stream())
                        .orElse(Stream.empty())
                        .map(n -> (Child<?>) n),
                        Trees.TraversalOrder.DEPTH_FIRST_PRE
                    ).forEachOrdered(System.out::println);

                    SceneController.showIn(speedment, stage, project);
                    Settings.inst().set("hide_open_option", false);
                } catch (Exception ex) {
                    showAlert(stage, "Error!",
                        "Could not connect to the database. Make sure the "
                        + "information provided is correct and that the database "
                        + "server is running."
                    );
                    throw ex;
                }
            });

            buttonOpen.setOnAction(createOpenProjectHandler(speedment, stage, (f, p) -> {
                // Todo: set saved file;
                SceneController.showIn(speedment, stage, p, f);
            }));
        } catch (Exception exxx) {
            exxx.printStackTrace();
            throw exxx;
        }
    }

    /**
     * Updated the enabled-flag of the connect button by making sure no required
     * values are still empty.
     */
    private void toggleConnectButton() {
        buttonConnect.setDisable(
            fieldHost.textProperty().getValue().isEmpty()
            || fieldPort.textProperty().getValue().isEmpty()
            || fieldType.getSelectionModel().isEmpty()
            || fieldName.textProperty().getValue().isEmpty()
            || fieldSchema.textProperty().getValue().isEmpty()
            || fieldUser.textProperty().getValue().isEmpty()
        );
    }

    /**
     * Returns a stream of all installed {@link DbmsType DbmsTypes}.
     *
     * @return supported dbms types
     */
    private Stream<DbmsType> getDbmsTypes() {
        return speedment
            .get(DbmsHandlerComponent.class)
            .supportedDbmsTypes();
    }

    /**
     * Attempts to locate the installed {@link DbmsType} with the specified
     * name.
     *
     * @return the {@link DbmsType} found or {@code empty}
     */
    private Optional<DbmsType> findDbmsType(String dbmsTypeName) {
        requireNonNull(dbmsTypeName);
        return speedment
            .get(DbmsHandlerComponent.class)
            .findByName(dbmsTypeName);
    }

    private Supplier<SpeedmentException> dbmsTypeNotInstalled(String dbmsTypeName) {
        requireNonNull(dbmsTypeName);
        return () -> new SpeedmentException(
            "Required DbmsType '"
            + dbmsTypeName
            + "' is not installed correctly."
        );
    }

    /**
     * Creates and configures a new Project Prompt-component in the specified
     * stage.
     *
     * @param speedment instance to used
     * @param stage the stage
     */
    public static void showIn(Speedment speedment, Stage stage) {
        requireNonNulls(speedment, stage);
        final FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/ProjectPrompt.fxml"));
        final ProjectPromptController control = new ProjectPromptController(speedment, stage);
        loader.setController(control);

        try {
            final HBox root = (HBox) loader.load();
            final Scene scene = new Scene(root);

            stage.hide();
            stage.setTitle("Speedment - New project");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            LOGGER.error(ex);
            throw new RuntimeException(ex);
        }
    }
}
