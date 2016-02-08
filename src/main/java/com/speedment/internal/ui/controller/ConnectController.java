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
package com.speedment.internal.ui.controller;

import static com.speedment.internal.ui.UISession.ReuseStage.USE_EXISTING_STAGE;
import static com.speedment.internal.ui.controller.ToolbarController.ICON_SIZE;
import static java.util.Objects.requireNonNull;
import static javafx.beans.binding.Bindings.createBooleanBinding;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.StringConverter;

import com.speedment.config.db.parameters.DbmsType;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.core.config.dbms.StandardDbmsType;
import com.speedment.internal.ui.UISession;
import com.speedment.internal.ui.config.DbmsProperty;
import com.speedment.internal.ui.util.Loader;
import com.speedment.internal.util.Settings;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;

/**
 *
 * @author Emil Forslund
 */
public final class ConnectController implements Initializable {
    
    private final static String 
        DEFAULT_HOST = "127.0.0.1",
        DEFAULT_USER = "root",
        DEFAULT_NAME = "db0";
    
    private final UISession session;
    
    private @FXML Button buttonOpen;
    private @FXML TextField fieldHost;
    private @FXML TextField fieldPort;
    private @FXML ChoiceBox<String> fieldType;
    private @FXML TextField fieldName;
    private @FXML TextField fieldSchema;
    private @FXML TextField fieldUser;
    private @FXML PasswordField fieldPass;
    private @FXML Button buttonConnect;
    private @FXML HBox container;
    private @FXML StackPane openContainer;
    
    private ConnectController(UISession session) {
        this.session = requireNonNull(session);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Settings.inst().get("hide_open_option", true)) {
            container.getChildren().remove(openContainer);
        }

        buttonOpen.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.FOLDER_OPEN, ICON_SIZE));
        buttonConnect.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.SIGN_IN, ICON_SIZE));
        
        fieldType.setItems(
            getDbmsTypes()
                .map(DbmsType::getName)
                .collect(Collectors.toCollection(FXCollections::observableArrayList))
        );
        
        fieldType.getSelectionModel().selectedItemProperty().addListener((observable, old, next) -> {
            if (!next.isEmpty()) {
                final DbmsType item = findDbmsType(next)
                    .orElseThrow(() -> dbmsTypeNotInstalledException(next));

                if (fieldHost.textProperty().getValue().isEmpty()) {
                    fieldHost.textProperty().setValue(DEFAULT_HOST);
                }

                if (fieldUser.textProperty().getValue().isEmpty()) {
                    fieldUser.textProperty().setValue(DEFAULT_USER);
                }
                
                if (fieldName.textProperty().getValue().isEmpty()) {
                    fieldName.textProperty().setValue(DEFAULT_NAME);
                }

                fieldName.getTooltip().setText(item.getDbmsNameMeaning());
                fieldPort.textProperty().setValue("" + item.getDefaultPort());
            }
        });
        
        final DbmsProperty dbms = session.getProject().mutator().addNewDbms();
        
        Bindings.bindBidirectional(fieldPort.textProperty(), dbms.portProperty(), new StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                return object.toString();
            }

            @Override
            public Number fromString(String string) {
                if (string == null || "".equals(string.trim())) {
                    return 0;
                } else return Integer.parseInt(string);
            }
        });
        
        fieldSchema.setText(Settings.inst().get("last_known_schema", ""));
        fieldPort.setText(Settings.inst().get("last_known_port", ""));
        fieldHost.setText(Settings.inst().get("last_known_host", DEFAULT_HOST));
        fieldUser.setText(Settings.inst().get("last_known_user", DEFAULT_USER));
        fieldName.setText(Settings.inst().get("last_known_name", DEFAULT_NAME));
        
        try {
            fieldType.getSelectionModel().select(
                Settings.inst().get(
                    "last_known_dbtype", 
                    getDbmsTypes()
                        .map(DbmsType::getName)
                        .findFirst()
                        .orElseThrow(() -> new SpeedmentException(
                            "Could not find any installed JDBC drivers. Make sure to" +
                            "include at least one JDBC driver as a dependency in the " +
                            "projects pom.xml-file."
                        ))
                )
            );
        } catch (final SpeedmentException ex) {
            session.showError("Couldn't find any installed JDBC drivers", 
                ex.getMessage(), ex
            );
            
            throw ex;
        }

        dbms.typeNameProperty().bind(fieldType.getSelectionModel().selectedItemProperty());
        dbms.ipAddressProperty().bindBidirectional(fieldHost.textProperty());
        dbms.nameProperty().bindBidirectional(fieldName.textProperty());
        dbms.usernameProperty().bindBidirectional(fieldUser.textProperty());        
        
        buttonOpen.setOnAction(session.openProject(USE_EXISTING_STAGE));
        buttonConnect.setOnAction(ev -> {
            
            // Register password in password component
            session.getSpeedment().getPasswordComponent()
                .put(fieldName.getText(), fieldPass.getText());
            
            session.getProject().nameProperty().setValue(fieldSchema.getText());
            
            Settings.inst().set("last_known_schema", fieldSchema.getText());
            Settings.inst().set("last_known_dbtype", dbms.getTypeName());
            Settings.inst().set("last_known_host", fieldHost.getText());
            Settings.inst().set("last_known_user", fieldUser.getText());
            Settings.inst().set("last_known_name", fieldName.getText());
            Settings.inst().set("last_known_port", fieldPort.getText());           

            if (session.loadFromDatabase(dbms, fieldSchema.getText())) {
                Settings.inst().set("hide_open_option", false);
                SceneController.createAndShow(session);
            }
        });
        
        buttonConnect.disableProperty().bind(createBooleanBinding(
            () -> fieldHost.textProperty().getValue().isEmpty()
            ||    fieldPort.textProperty().getValue().isEmpty()
            ||    fieldType.getSelectionModel().isEmpty()
            ||    fieldName.textProperty().getValue().isEmpty()
            ||    fieldSchema.textProperty().getValue().isEmpty()
            ||    fieldUser.textProperty().getValue().isEmpty(), 

            fieldHost.textProperty(),
            fieldPort.textProperty(),
            fieldType.selectionModelProperty(),
            fieldName.textProperty(),
            fieldSchema.textProperty(),
            fieldUser.textProperty()
        ));
    }
    
    public static void createAndShow(UISession session) {
        Loader.createAndShow(session, "Connect", ConnectController::new);
	}
    
    private Stream<DbmsType> getDbmsTypes() {
        return session.getSpeedment()
            .getDbmsHandlerComponent()
            .supportedDbmsTypes();
    }

    private Optional<DbmsType> findDbmsType(String dbmsTypeName) {
        requireNonNull(dbmsTypeName);
        return session.getSpeedment()
            .getDbmsHandlerComponent()
            .findByName(dbmsTypeName);
    }

    private static SpeedmentException dbmsTypeNotInstalledException(String dbmsTypeName) {
        requireNonNull(dbmsTypeName);
        return new SpeedmentException(
            "Required " + DbmsType.class.getSimpleName() + 
            " '" + dbmsTypeName + "' is not installed correctly."
        );
    }
}