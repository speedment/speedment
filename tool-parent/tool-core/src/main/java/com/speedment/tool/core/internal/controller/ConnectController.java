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

import com.speedment.common.function.OptionalBoolean;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.generator.core.component.EventComponent;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Schema;
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
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toCollection;
import static javafx.beans.binding.Bindings.createBooleanBinding;
import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

/**
 *
 * @author Emil Forslund
 */
public final class ConnectController implements Initializable {
    
    private final static String 
        DEFAULT_HOST   = "127.0.0.1",
        DEFAULT_USER   = "root";
    
    @Inject private UserInterfaceComponent userInterfaceComponent;
    @Inject private DbmsHandlerComponent dbmsHandlerComponent;
    @Inject private PasswordComponent passwordComponent;
    @Inject private ConfigFileHelper configFileHelper;
    @Inject private EventComponent eventComponent;
    @Inject private InjectionLoader loader;

    @FXML private TextField fieldHost;
    @FXML private TextField fieldPort;
    @FXML private ChoiceBox<String> fieldType;
    @FXML private TextField fieldName;
    @FXML private TextField fieldSchema;
    @FXML private TextField fieldUser;
    @FXML private PasswordField fieldPass;
    @FXML private Button buttonConnect;
    @FXML private CheckBox enableConnectionUrl;
    @FXML private TextArea areaConnectionUrl;

    @FXML private GridPane grid;
    @FXML private RowConstraints dbmsRow;
    @FXML private RowConstraints schemaRow;

    private FilteredList<Node> dbmsRowChildren;
    private FilteredList<Node> schemaRowChildren;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonConnect.setGraphic(FontAwesome.SIGN_IN.view());

        dbmsRowChildren   = inRow(dbmsRow);
        schemaRowChildren = inRow(schemaRow);

        fieldType.setItems(getDbmsTypes()
            .collect(toCollection(FXCollections::observableArrayList))
        );

        // Keep track of the generated values so that we don't overwrite user
        // changes with automatic ones.
        final AtomicReference<DbmsType> dbmsType       = new AtomicReference<>();
        final AtomicReference<String> generatedHost    = new AtomicReference<>("");
        final AtomicReference<String> generatedPort    = new AtomicReference<>("");
        final AtomicReference<String> generatedUser    = new AtomicReference<>("");
        final AtomicReference<String> generatedName    = new AtomicReference<>("");
        final AtomicReference<String> generatedSchema  = new AtomicReference<>("");
        final AtomicReference<String> generatedConnUrl = new AtomicReference<>("");

        // Use this method reference to recalculate any default values.
        final Runnable recalculateFields = () -> {
            final DbmsType item = dbmsType.get();

            // Hide name rows if particular Dbms doesn't support them.
            toggleVisibility(dbmsRow, dbmsRowChildren, item.hasDatabaseNames());
            toggleVisibility(schemaRow, schemaRowChildren, item.hasSchemaNames());

            // Disable Dbms Name-property for database types that doesn't use it
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
                item.getDefaultDbmsName().ifPresent(name -> {
                    fieldName.textProperty().setValue(name);
                    generatedName.set(name);
                });
            }

            if (fieldSchema.getText().isEmpty()
            ||  fieldSchema.getText().equals(generatedSchema.get())) {
                item.getDefaultSchemaName().ifPresent(name -> {
                    fieldSchema.textProperty().setValue(name);
                    generatedSchema.set(name);
                });
            }

            fieldName.getTooltip().setText(item.getDbmsNameMeaning());

            if (fieldPort.getText().isEmpty()
            ||  fieldPort.getText().equals(generatedPort.get())) {
                final String port = Integer.toString(item.getDefaultPort());
                fieldPort.textProperty().setValue(port);
                generatedPort.set(port);
            }

            if (areaConnectionUrl.getText().isEmpty()
            ||  areaConnectionUrl.getText().equals(generatedConnUrl.get())) {
                final String url = item.getConnectionUrlGenerator().from(
                    TemporaryDbms.create(
                        userInterfaceComponent.projectProperty(),
                        fieldName.getText(),
                        fieldHost.getText(),
                        Integer.parseInt(fieldPort.getText())
                    )
                );

                generatedConnUrl.set(url);
                areaConnectionUrl.setText(url);
            }
        };

        // If the user changes something, recalculate default values.
        fieldType.getSelectionModel().selectedItemProperty()
            .addListener((observable, old, typeName) -> {
                if (!typeName.isEmpty()) {
                    dbmsType.set(findDbmsType(typeName));
                    recalculateFields.run();
                }
            });

        fieldHost.textProperty().addListener((ob, o, n) -> recalculateFields.run());
        fieldPort.textProperty().addListener((ob, o, n) -> recalculateFields.run());
        fieldUser.textProperty().addListener((ob, o, n) -> recalculateFields.run());
        fieldName.textProperty().addListener((ob, o, n) -> recalculateFields.run());
        fieldSchema.textProperty().addListener((ob, o, n) -> recalculateFields.run());
        areaConnectionUrl.textProperty().addListener((ob, o, n) -> recalculateFields.run());

        // Disable the Connection Url field if the checkbox is not checked.
        areaConnectionUrl.disableProperty().bind(
            enableConnectionUrl.selectedProperty().not()
        );

        // Load settings from previous session and overwrite default ones.
        try {
            // Find the preferred dbms-type
            final String preferred = Settings.inst().get(
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
            if (getDbmsTypes().anyMatch(preferred::equals)) {

                fieldType.getSelectionModel().select(preferred);
                final int port = Integer.parseInt(
                    Settings.inst().get(
                        "last_known_port",
                        Integer.toString(dbmsType.get().getDefaultPort())
                    )
                );

                final String host   = Settings.inst().get("last_known_host",   generatedHost.get());
                final String user   = Settings.inst().get("last_known_user",   generatedUser.get());
                final String name   = Settings.inst().get("last_known_name",   generatedName.get());
                final String schema = Settings.inst().get("last_known_schema", generatedSchema.get());
                final String url    = Settings.inst().get("last_known_url",    generatedConnUrl.get());

                generatedHost.set(host);
                generatedUser.set(user);
                generatedName.set(name);
                generatedSchema.set(schema);
                generatedConnUrl.set(url);

                fieldSchema.setText(schema);
                fieldPort.setText(Integer.toString(port));
                fieldHost.setText(host);
                fieldUser.setText(user);
                fieldName.setText(name);
                fieldSchema.setText(schema);
                areaConnectionUrl.setText(url);
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

        // Disable the Connect-button if all fields have not been entered.
        buttonConnect.disableProperty().bind(createBooleanBinding(
            () -> fieldHost.textProperty().isEmpty().get()
            ||    fieldPort.textProperty().isEmpty().get()
            ||    fieldType.getSelectionModel().isEmpty()
            ||    fieldName.textProperty().isEmpty().get()
            ||    fieldUser.textProperty().isEmpty().get(),

            fieldHost.textProperty(),
            fieldPort.textProperty(),
            fieldType.selectionModelProperty(),
            fieldName.textProperty(),
            fieldUser.textProperty()
        ));

        // Connect to database action
        buttonConnect.setOnAction(ev -> {

            // Register password in password component
            passwordComponent.put(
                fieldName.getText(),
                fieldPass.getText().toCharArray()
            );

            // Create a new Dbms using the settings configured.
            final DbmsProperty dbms = userInterfaceComponent.projectProperty()
                .mutator().addNewDbms();

            dbms.typeNameProperty().set(dbmsType.get().getName());
            dbms.ipAddressProperty().set(fieldHost.getText());
            dbms.portProperty().set(Integer.valueOf(fieldPort.getText()));
            dbms.usernameProperty().set(fieldUser.getText());
            dbms.nameProperty().set(fieldName.getText());

            if (!areaConnectionUrl.getText().isEmpty()
                &&  !areaConnectionUrl.getText().equals(generatedConnUrl.get())) {
                Settings.inst().set("last_known_url", areaConnectionUrl.getText());
                dbms.connectionUrlProperty().setValue(
                    areaConnectionUrl.getText()
                );
            }

            final String schema = Optional.of(fieldSchema.getText())
                .filter(s -> !s.isEmpty())
                .orElseGet(dbms::getName);

            // Set the default project name to the name of the schema.
            userInterfaceComponent.projectProperty().nameProperty()
                .setValue(schema);

            // Store the settings so that they can be reused in the next session
            Settings.inst().set("last_known_schema", fieldSchema.getText());
            Settings.inst().set("last_known_dbtype", dbmsType.get().getName());
            Settings.inst().set("last_known_host", fieldHost.getText());
            Settings.inst().set("last_known_port", fieldPort.getText());
            Settings.inst().set("last_known_user", fieldUser.getText());
            Settings.inst().set("last_known_name", fieldName.getText());

            // Connect to database
            if (configFileHelper.loadFromDatabase(dbms, schema)) {
                Settings.inst().set("hide_open_option", false);
                loader.loadAndShow("Scene");
                eventComponent.notify(UIEvent.OPEN_MAIN_WINDOW);
            }
        });
    }
    
    private Stream<String> getDbmsTypes() {
        return dbmsHandlerComponent
            .supportedDbmsTypes()
            .map(DbmsType::getName);
    }

    private DbmsType findDbmsType(String dbmsTypeName) {
        return dbmsHandlerComponent.findByName(dbmsTypeName).orElseThrow(() ->
            new SpeedmentToolException(
                "Could not find any DbmsType with name '" +
                    dbmsTypeName + "'."
            ));
    }

    private FilteredList<Node> inRow(RowConstraints row) {
        final int index = grid.getRowConstraints().indexOf(row);
        return grid.getChildren()
            .filtered(node -> {
                final Integer rowIndex = GridPane.getRowIndex(node);
                return rowIndex != null && index == GridPane.getRowIndex(node);
            });
    }

    private void toggleVisibility(RowConstraints row,
                                  FilteredList<Node> children,
                                  boolean show) {
        if (show) {
            row.setMaxHeight(USE_COMPUTED_SIZE);
            row.setMinHeight(10);
        } else {
            row.setMaxHeight(0);
            row.setMinHeight(0);
        }

        children.forEach(n -> {
            n.setVisible(show);
            n.setManaged(show);
        });
    }

    private static final class TemporaryDbms implements Dbms {

        public static TemporaryDbms create(Project project, String name, String ip, int port) {
            final Map<String, Object> data = new LinkedHashMap<>();
            data.put(Dbms.ID,         name);
            data.put(Dbms.NAME,       name);
            data.put(Dbms.IP_ADDRESS, ip);
            data.put(Dbms.PORT,       port);
            return new TemporaryDbms(project, data);
        }

        private final Project project;
        private final Map<String, Object> data;

        private TemporaryDbms(Project project, Map<String, Object> data) {
            this.project = requireNonNull(project);
            this.data    = requireNonNull(data);
        }

        @Override
        public Optional<Project> getParent() {
            return Optional.of(project);
        }

        @Override
        public Map<String, Object> getData() {
            return data;
        }

        @Override
        public Optional<Object> get(String key) {
            return Optional.ofNullable(data.get(key));
        }

        @Override
        public Optional<String> getAsString(String key) throws ClassCastException {
            return get(key).map(String.class::cast);
        }

        @Override
        public OptionalBoolean getAsBoolean(String key) throws ClassCastException {
            return get(key)
                .map(Boolean.class::cast)
                .map(OptionalBoolean::of)
                .orElseGet(OptionalBoolean::empty);
        }

        @Override
        public OptionalLong getAsLong(String key) throws ClassCastException {
            return get(key)
                .map(Long.class::cast)
                .map(OptionalLong::of)
                .orElseGet(OptionalLong::empty);
        }



        @Override
        public OptionalDouble getAsDouble(String key) throws ClassCastException {
            return get(key)
                .map(Double.class::cast)
                .map(OptionalDouble::of)
                .orElseGet(OptionalDouble::empty);
        }

        @Override
        public OptionalInt getAsInt(String key) throws ClassCastException {
            return get(key)
                .map(Integer.class::cast)
                .map(OptionalInt::of)
                .orElseGet(OptionalInt::empty);
        }

        @Override
        public void put(String key, Object value) {
            throw new UnsupportedOperationException(
                "This implementation of Dbms should not be modified."
            );
        }

        @Override
        public Stream<? extends Schema> schemas() {
            return Stream.empty();
        }

        @Override
        public Stream<? extends Document> children() {
            return Stream.empty();
        }
    }
}