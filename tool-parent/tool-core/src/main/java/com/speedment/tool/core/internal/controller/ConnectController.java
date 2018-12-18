/**
 *
 * Copyright (c) 2006-2018, Speedment, Inc. All Rights Reserved.
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
import com.speedment.tool.config.DbmsProperty;
import com.speedment.tool.core.component.UserInterfaceComponent;
import com.speedment.tool.core.event.UIEvent;
import com.speedment.tool.core.exception.SpeedmentToolException;
import com.speedment.tool.core.internal.util.ConfigFileHelper;
import com.speedment.tool.core.resource.FontAwesome;
import com.speedment.tool.core.util.InjectionLoader;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.ResourceBundle;
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
    
    @Inject private UserInterfaceComponent ui;
    @Inject private DbmsHandlerComponent dbmsHandler;
    @Inject private PasswordComponent passwords;
    @Inject private ConfigFileHelper cfHelper;
    @Inject private EventComponent events;
    @Inject private InjectionLoader loader;

    @FXML private TextField fieldHost;
    @FXML private TextField fieldPort;
    @FXML private TextField fieldFile;
    @FXML private Button fieldFileBtn;
    @FXML private ComboBox<String> fieldType;
    @FXML private TextField fieldName;
    @FXML private TextField fieldSchema;
    @FXML private TextField fieldUser;
    @FXML private PasswordField fieldPass;
    @FXML private Button buttonConnect;
    @FXML private CheckBox enableConnectionUrl;
    @FXML private TextArea areaConnectionUrl;

    @FXML private GridPane grid;
    @FXML private RowConstraints hostRow;
    @FXML private RowConstraints fileRow;
    @FXML private RowConstraints dbmsRow;
    @FXML private RowConstraints schemaRow;
    @FXML private RowConstraints userRow;
    @FXML private RowConstraints passRow;

    private FilteredList<Node> hostRowChildren;
    private FilteredList<Node> fileRowChildren;
    private FilteredList<Node> userRowChildren;
    private FilteredList<Node> passRowChildren;
    private FilteredList<Node> dbmsRowChildren;
    private FilteredList<Node> schemaRowChildren;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fieldFileBtn.setGraphic(FontAwesome.FOLDER_OPEN.view());
        buttonConnect.setGraphic(FontAwesome.SIGN_IN.view());

        hostRowChildren   = inRow(hostRow);
        fileRowChildren   = inRow(fileRow);
        userRowChildren   = inRow(userRow);
        passRowChildren   = inRow(passRow);
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
            toggleVisibility(hostRow, hostRowChildren, item.getConnectionType() == DbmsType.ConnectionType.HOST_AND_PORT);
            toggleVisibility(fileRow, fileRowChildren, item.getConnectionType() == DbmsType.ConnectionType.DBMS_AS_FILE);
            toggleVisibility(userRow, userRowChildren, item.hasDatabaseUsers());
            toggleVisibility(passRow, passRowChildren, item.hasDatabaseUsers());
            toggleVisibility(dbmsRow, dbmsRowChildren, item.hasDatabaseNames());
            toggleVisibility(schemaRow, schemaRowChildren, item.hasSchemaNames());

            if (fieldHost.getText().isEmpty()
            ||  fieldHost.getText().equals(generatedHost.get())) {
                fieldHost.textProperty().setValue(DEFAULT_HOST);
                generatedHost.set(DEFAULT_HOST);
            }

            // Disable Dbms User-property for database types that doesn't use it
            if (item.hasDatabaseUsers()) {
                fieldUser.setDisable(false);
                fieldPass.setDisable(false);

                if (fieldUser.getText().isEmpty()
                ||  fieldUser.getText().equals(generatedUser.get())) {
                    fieldUser.textProperty().setValue(DEFAULT_USER);
                    generatedUser.set(DEFAULT_USER);
                }
            } else {
                generatedUser.set(DEFAULT_USER);
                fieldUser.setDisable(true);
                fieldPass.setDisable(true);
            }

            // Disable Dbms Name-property for database types that doesn't use it
            if (item.hasDatabaseNames()) {
                fieldName.setDisable(false);

                if (fieldName.getText().isEmpty()
                ||  fieldName.getText().equals(generatedName.get())) {
                    item.getDefaultDbmsName().ifPresent(name -> {
                        fieldName.textProperty().setValue(name);
                        generatedName.set(name);
                    });
                }
            } else {
                item.getDefaultDbmsName().ifPresent(generatedName::set);
                fieldName.setDisable(true);
            }

            if (item.hasSchemaNames()) {
                fieldSchema.setDisable(false);

                if (fieldSchema.getText().isEmpty()
                ||  fieldSchema.getText().equals(generatedSchema.get())) {
                    item.getDefaultSchemaName().ifPresent(name -> {
                        fieldSchema.textProperty().setValue(name);
                        generatedSchema.set(name);
                    });
                }
            } else {
                fieldSchema.setDisable(true);
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
                        ui.projectProperty(),
                        fieldName.getText(),
                        fieldFile.getText(),
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
        fieldFile.textProperty().addListener((ob, o, n) -> recalculateFields.run());
        fieldUser.textProperty().addListener((ob, o, n) -> recalculateFields.run());
        fieldName.textProperty().addListener((ob, o, n) -> recalculateFields.run());
        fieldSchema.textProperty().addListener((ob, o, n) -> recalculateFields.run());
        areaConnectionUrl.textProperty().addListener((ob, o, n) -> recalculateFields.run());

        // Disable the Connection Url field if the checkbox is not checked.
        areaConnectionUrl.disableProperty().bind(
            enableConnectionUrl.selectedProperty().not()
        );

        // Disable the file chooser if connection URL is enabled
        fieldFileBtn.disableProperty().bind(
            enableConnectionUrl.selectedProperty()
        );

        // Find the preferred dbms-type
        final Optional<String> preferred = getDbmsTypes().findFirst();
        if (preferred.isPresent()) {
            fieldType.getSelectionModel().select(preferred.get());
        } else {
            final String msg = "Could not find any installed JDBC " +
                "drivers. Make sure to include at least one JDBC driver " +
                "as a dependency in the projects pom.xml-file under the " +
                "speedment-maven-plugin <plugin> tag.";

            ui.showError(
                "Couldn't find any installed JDBC drivers",
                msg
            );

            throw new SpeedmentToolException(msg);
        }

        // Disable the Connect-button if all fields have not been entered.
        buttonConnect.disableProperty().bind(createBooleanBinding(
            () -> ((fieldHost.textProperty().isEmpty().get()
            ||      fieldPort.textProperty().isEmpty().get())
                && dbmsType.get().getConnectionType() == DbmsType.ConnectionType.HOST_AND_PORT)
            ||     (fieldFile.textProperty().isEmpty().get() && dbmsType.get().getConnectionType() == DbmsType.ConnectionType.DBMS_AS_FILE)
            ||      fieldType.getSelectionModel().isEmpty()
            ||     (fieldName.textProperty().isEmpty().get() && dbmsType.get().hasDatabaseNames())
            ||     (fieldUser.textProperty().isEmpty().get() && dbmsType.get().hasDatabaseUsers()),

            fieldHost.textProperty(),
            fieldPort.textProperty(),
            fieldFile.textProperty(),
            fieldType.selectionModelProperty(),
            fieldName.textProperty(),
            fieldUser.textProperty()
        ));

        // Load dbms from file-action
        final FileChooser fileChooser = new FileChooser();
        fieldFileBtn.setOnAction(ev -> {
            fileChooser.setTitle("Open Database File");

            if (!"".equals(fieldFile.getText().trim())) {
                final Path path = Paths.get(fieldFile.getText().trim());

                if (Files.exists(path.getParent())) {
                    final String parentFolder = path.getParent().toString();


                    if (!"".equals(parentFolder)) {
                        fileChooser.setInitialDirectory(new File(parentFolder));
                    }
                }

                if (Files.exists(path)) {
                    fileChooser.setInitialFileName(fieldFile.getText());
                }
            }

            final File file = fileChooser.showOpenDialog(ui.getStage());
            if (file != null) {
                fieldFile.setText(Paths.get(".").toAbsolutePath().getParent().relativize(file.toPath()).toString());
            }
        });

        // Connect to database action
        buttonConnect.setOnAction(ev -> {
            final DbmsType type = dbmsType.get();

            // Register password in password component
            passwords.put(
                fieldName.getText(),
                fieldPass.getText().toCharArray()
            );

            // Create a new Dbms using the settings configured.
            final DbmsProperty dbms = ui.projectProperty()
                .mutator().addNewDbms();

            dbms.typeNameProperty().set(dbmsType.get().getName());

            if (type.getConnectionType() == DbmsType.ConnectionType.HOST_AND_PORT) {
                dbms.ipAddressProperty().set(fieldHost.getText());
                dbms.portProperty().set(Integer.valueOf(fieldPort.getText()));
            } else if (type.getConnectionType() == DbmsType.ConnectionType.DBMS_AS_FILE) {
                dbms.localPathProperty().set(fieldFile.getText());
            }

            if (type.hasDatabaseUsers()) {
                dbms.usernameProperty().set(fieldUser.getText());
            }

            Optional.of(fieldName.getText())
                .filter(s -> dbmsType.get().hasDatabaseNames())
                .filter(s -> !s.isEmpty())
                .orElseGet(() -> type.getDefaultDbmsName().orElseGet(fieldName::getText));

            if (!areaConnectionUrl.getText().isEmpty()
            &&  !areaConnectionUrl.getText().equals(generatedConnUrl.get())) {
                dbms.connectionUrlProperty().setValue(
                    areaConnectionUrl.getText()
                );
            }

            final String schema = Optional.of(fieldSchema.getText())
                .filter(s -> dbmsType.get().hasSchemaNames())
                .filter(s -> !s.isEmpty())
                .orElseGet(() -> type.getDefaultSchemaName().orElseGet(dbms::getName));

            // Set the default project name to the name of the schema.
            if (type.hasSchemaNames() || type.hasDatabaseNames()) {
                ui.projectProperty().nameProperty()
                    .setValue(schema);
            } else if (type.getConnectionType() == DbmsType.ConnectionType.DBMS_AS_FILE) {
                String filename = Paths.get(fieldFile.getText()).getFileName().toString();
                if (filename.contains(".")) {
                    filename = filename.substring(0, filename.lastIndexOf("."));
                }
                ui.projectProperty().nameProperty()
                    .setValue(filename);
            } else {
                ui.projectProperty().nameProperty()
                    .setValue("Demo");
            }

            // Connect to database
            if (cfHelper.loadFromDatabase(dbms, schema)) {
                loader.loadAndShow("Scene");
                events.notify(UIEvent.OPEN_MAIN_WINDOW);
            }
        });
    }
    
    private Stream<String> getDbmsTypes() {
        return dbmsHandler
            .supportedDbmsTypes()
            .map(DbmsType::getName);
    }

    private DbmsType findDbmsType(String dbmsTypeName) {
        return dbmsHandler.findByName(dbmsTypeName).orElseThrow(() ->
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

        public static TemporaryDbms create(Project project, String name, String file, String ip, int port) {
            final Map<String, Object> data = new LinkedHashMap<>();
            data.put(Dbms.ID,         name);
            data.put(Dbms.NAME,       name);
            data.put(Dbms.IP_ADDRESS, ip);
            data.put(Dbms.PORT,       port);
            data.put(Dbms.LOCAL_PATH, file);
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