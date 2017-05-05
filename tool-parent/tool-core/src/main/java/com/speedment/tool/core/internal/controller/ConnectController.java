/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.tool.core.internal.controller;

import com.speedment.common.injector.annotation.Inject;
import com.speedment.generator.core.component.EventComponent;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.exception.SpeedmentConfigException;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.PasswordComponent;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.internal.util.Settings;
import com.speedment.tool.config.DbmsProperty;
import com.speedment.tool.core.component.UserInterfaceComponent;
import com.speedment.tool.core.event.UIEvent;
import com.speedment.tool.core.exception.SpeedmentToolException;
import com.speedment.tool.core.internal.util.ConfigFileHelper;
import com.speedment.tool.core.internal.util.InjectionLoader;
import com.speedment.tool.core.resource.FontAwesome;
import javafx.beans.property.ObjectProperty;
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

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import static com.speedment.runtime.core.util.DatabaseUtil.dbmsTypeOf;
import static com.speedment.tool.core.component.UserInterfaceComponent.ReuseStage.USE_EXISTING_STAGE;
import static java.util.stream.Collectors.toCollection;
import static javafx.beans.binding.Bindings.createBooleanBinding;

/**
 *
 * @author Emil Forslund
 */
public final class ConnectController implements Initializable {
    
    private final static String 
        DEFAULT_HOST = "127.0.0.1",
        DEFAULT_USER = "root",
        DEFAULT_NAME = "db0";
    
    @Inject private UserInterfaceComponent userInterfaceComponent;
    @Inject private DbmsHandlerComponent dbmsHandlerComponent;
    @Inject private PasswordComponent passwordComponent;
    @Inject private ConfigFileHelper configFileHelper;
    @Inject private EventComponent eventComponent;
    @Inject private InjectionLoader loader;
    
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
    
    private ObjectProperty<Integer> portProperty;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Settings.inst().get("hide_open_option", true)) {
            container.getChildren().remove(openContainer);
        }

        buttonOpen.setGraphic(FontAwesome.FOLDER_OPEN.view());
        buttonConnect.setGraphic(FontAwesome.SIGN_IN.view());
        
        fieldType.setItems(
            getDbmsTypes()
                .collect(toCollection(FXCollections::observableArrayList))
        );
        
        final DbmsProperty dbms = userInterfaceComponent.projectProperty()
            .mutator().addNewDbms();
        
        final AtomicReference<String> generatedHost = new AtomicReference<>("");
        final AtomicReference<String> generatedUser = new AtomicReference<>("");
        final AtomicReference<String> generatedName = new AtomicReference<>("");
        
        fieldType.getSelectionModel().selectedItemProperty()
            .addListener((observable, old, typeName) -> {
                
            dbms.stringPropertyOf(Dbms.TYPE_NAME, () -> typeName)
                .setValue(typeName);
            
            if (!typeName.isEmpty()) {
                final DbmsType item = dbmsTypeOf(dbmsHandlerComponent, dbms);

                if (fieldHost.getText().isEmpty()
                ||  fieldHost.getText().equals(generatedHost.get())) {
                    fieldHost.textProperty().setValue(DEFAULT_HOST);
                    generatedHost.set(DEFAULT_HOST);
                }

                if (fieldUser.getText().isEmpty()
                ||  fieldUser.getText().equals(generatedUser.get())) {
                    fieldUser.textProperty().setValue(DEFAULT_USER);
                    generatedUser.set(DEFAULT_USER);
                }
                
                if (fieldName.getText().isEmpty()
                ||  fieldName.getText().equals(generatedName.get())) {
                    final String name = item.getDefaultDbmsName()
                        .orElse(DEFAULT_NAME);
                    
                    fieldName.textProperty().setValue(name);
                    generatedName.set(name);
                }

                fieldName.getTooltip().setText(item.getDbmsNameMeaning());
                
                dbms.portProperty().setValue(item.getDefaultPort());
                fieldPort.textProperty().setValue(
                    Integer.toString(item.getDefaultPort())
                );
            }
        });
        
        portProperty = dbms.portProperty().asObject();
        fieldPort.textProperty().bindBidirectional(portProperty, new StringConverter<Integer>() {
            @Override
            public String toString(Integer number) {
                if (number == null) {
                    try {
                        return Integer.toString(defaultPort(dbms));
                    } catch (SpeedmentConfigException ex) {
                        return "";
                    }
                } else {
                    return number.toString();
                }
            }

            @Override
            public Integer fromString(String string) {
                if (string == null || "".equals(string.trim())) {
                    return defaultPort(dbms);
                } else return Integer.parseInt(string);
            }
        });

        dbms.portProperty().addListener((ob, o, n) -> {
            System.out.println("Port changed to: " + n);
        });
        
        try {
            // Find the preferred dbms-type
            final String prefered = Settings.inst().get(
                "last_known_dbtype",
                getDbmsTypes()
                    .findFirst()
                    .orElseThrow(() -> new SpeedmentToolException(
                        "Could not find any installed JDBC drivers. Make " + 
                        "sure to include at least one JDBC driver as a " + 
                        "dependency in the projects pom.xml-file under the " + 
                        "speedment-maven-plugin <plugin> tag."
                    ))
            );
            
            // If the preferred dbms-type isn't loaded, select the first one.
            if (getDbmsTypes().anyMatch(prefered::equals)) {
                
                fieldType.getSelectionModel().select(prefered);
                
                final int port = Integer.parseInt(
                    Settings.inst().get(
                        "last_known_port", 
                        Integer.toString(defaultPort(dbms))
                    )
                );
                
                final String host = Settings.inst().get("last_known_host", DEFAULT_HOST);
                final String user = Settings.inst().get("last_known_user", DEFAULT_USER);
                final String name = Settings.inst().get("last_known_name", defaultName(dbms));
                
                generatedHost.set(host);
                generatedUser.set(user);
                generatedName.set(name);
                
                fieldSchema.setText(Settings.inst().get("last_known_schema"));
                fieldPort.setText(Integer.toString(port));
                fieldHost.setText(host);
                fieldUser.setText(user);
                fieldName.setText(name);
            } else {
                fieldType.getSelectionModel().select(
                    getDbmsTypes().findFirst().get()
                );
            }
        } catch (final SpeedmentToolException ex) {
            userInterfaceComponent.showError(
                "Couldn't find any installed JDBC drivers", 
                ex.getMessage(), ex
            );
            
            throw ex;
        }
        
        dbms.ipAddressProperty().bindBidirectional(fieldHost.textProperty());
        dbms.nameProperty().bindBidirectional(fieldName.textProperty());
        dbms.usernameProperty().bindBidirectional(fieldUser.textProperty());
        
        buttonOpen.setOnAction(ev -> 
            userInterfaceComponent.openProject(USE_EXISTING_STAGE));
        
        buttonConnect.setOnAction(ev -> {
            // Register password in password component
            passwordComponent
                .put(fieldName.getText(), fieldPass.getText().toCharArray());
            
            userInterfaceComponent.projectProperty().nameProperty()
                .setValue(fieldSchema.getText());
            
            Settings.inst().set("last_known_schema", fieldSchema.getText());
            Settings.inst().set("last_known_dbtype", dbms.getTypeName());
            Settings.inst().set("last_known_host", fieldHost.getText());
            Settings.inst().set("last_known_port", fieldPort.getText());
            Settings.inst().set("last_known_user", fieldUser.getText());
            Settings.inst().set("last_known_name", fieldName.getText());
            
            if (configFileHelper.loadFromDatabase(dbms, fieldSchema.getText())) {
                Settings.inst().set("hide_open_option", false);
                loader.loadAndShow("Scene");
                eventComponent.notify(UIEvent.OPEN_MAIN_WINDOW);
            }
        });
        
        buttonConnect.disableProperty().bind(createBooleanBinding(
            () -> fieldHost.textProperty().isEmpty().get()
            ||    fieldPort.textProperty().isEmpty().get()
            ||    fieldType.getSelectionModel().isEmpty()
            ||    fieldName.textProperty().isEmpty().get()
            ||    fieldSchema.textProperty().isEmpty().get()
            ||    fieldUser.textProperty().isEmpty().get(), 

            fieldHost.textProperty(),
            fieldPort.textProperty(),
            fieldType.selectionModelProperty(),
            fieldName.textProperty(),
            fieldSchema.textProperty(),
            fieldUser.textProperty()
        ));
    }
    
    private Stream<String> getDbmsTypes() {
        return dbmsHandlerComponent
            .supportedDbmsTypes()
            .map(DbmsType::getName);
    }
    
    private int defaultPort(DbmsProperty dbms) {
        return dbmsTypeOf(dbmsHandlerComponent, dbms)
            .getDefaultPort();
    }
    
    private String defaultName(DbmsProperty dbms) {
        return dbmsTypeOf(dbmsHandlerComponent, dbms)
            .getDefaultDbmsName()
            .orElse(DEFAULT_NAME);
    }
}