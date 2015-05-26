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

import com.speedment.core.config.model.Dbms;
import com.speedment.core.config.model.Project;
import com.speedment.core.config.model.aspects.Child;
import com.speedment.core.config.model.parameters.StandardDbmsType;
import com.speedment.core.db.DbmsHandler;
import com.speedment.gui.MainApp;
import com.speedment.gui.Settings;
import static com.speedment.gui.util.ProjectUtil.createOpenProjectHandler;
import com.speedment.core.platform.Platform;
import com.speedment.core.platform.component.DbmsHandlerComponent;
import com.speedment.util.Trees;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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

/**
 * FXML Controller class
 *
 * @author Emil Forslund
 */
public class ProjectPromptController implements Initializable {
    
    @FXML
    private Button buttonOpen;
    @FXML
    private TextField fieldHost;
    @FXML
    private TextField fieldPort;
    @FXML
    private ChoiceBox<String> fieldType;
    @FXML
    private TextField fieldName;
    @FXML
    private TextField fieldSchema;
    @FXML
    private TextField fieldUser;
    @FXML
    private PasswordField fieldPass;
    @FXML
    private Button buttonConnect;
    @FXML
    private HBox container;
    @FXML
    private StackPane openContainer;
    
    private final Stage stage;
    
    public ProjectPromptController(Stage stage) {
        this.stage = stage;
    }

    /**
     * Initializes the controller class.
     *
     * @param url the URL to use
     * @param rb the ResourceBundle to use
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if (Settings.inst().get("hide_open_option", true)) {
            container.getChildren().remove(openContainer);
        }
        
        fieldType.setItems(
                Stream.of(StandardDbmsType.values())
                .map(s -> s.getName())
                .collect(Collectors.toCollection(FXCollections::observableArrayList))
        );
        
        fieldType.getSelectionModel().selectedItemProperty().addListener((observable, old, next) -> {
            if (!observable.getValue().isEmpty()) {
                final StandardDbmsType item = StandardDbmsType.valueOf(next.toUpperCase());
                
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
        
        buttonConnect.setOnAction(ev -> {
            
            Project project = Project.newProject();
            project.setName("project_1");

            
            Dbms dbms = Dbms.newDbms();
            dbms.setIpAddress(fieldHost.getText());
            dbms.setPort(Integer.parseInt(fieldPort.getText()));
            dbms.setName(fieldName.getText());
            dbms.setUsername(fieldUser.getText());
            dbms.setPassword(fieldPass.getText());
            dbms.setType(fieldType.getSelectionModel().getSelectedItem());
            project.add(dbms);
            
            final DbmsHandler dh = Platform.get().get(DbmsHandlerComponent.class).get(dbms);
            dh.schemasPopulated().filter(s -> fieldSchema.getText().equalsIgnoreCase(s.getName())).forEachOrdered(dbms::add);
            
            Trees.traverse((Child) project, c -> c.asParent()
                    .map(p -> p.stream())
                    .orElse(Stream.empty())
                    .map(n -> (Child<?>) n),
                    Trees.TraversalOrder.DEPTH_FIRST_PRE
            ).forEachOrdered(System.out::println);

            SceneController.showIn(stage, project);
            Settings.inst().set("hide_open_option", false);
        });
        
        buttonOpen.setOnAction(createOpenProjectHandler(stage, (f,p) -> {
            // Todo: set saved file;
            SceneController.showIn(stage, p);
        }));
    }
    
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
    
    public static void showIn(Stage stage) {
        final FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/ProjectPrompt.fxml"));
        final ProjectPromptController control = new ProjectPromptController(stage);
        loader.setController(control);
        
        try {
            final HBox root = (HBox) loader.load();
            final Scene scene = new Scene(root);
            
            stage.hide();
            stage.setTitle("Speedment ORM - New project");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
