/*
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
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

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toCollection;
import static javafx.beans.binding.Bindings.createBooleanBinding;
import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

import com.speedment.common.function.OptionalBoolean;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.generator.core.component.EventComponent;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.DbmsUtil;
import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Schema;
import com.speedment.runtime.config.trait.HasIdUtil;
import com.speedment.runtime.config.trait.HasNameUtil;
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
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
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public final class ConnectController implements Initializable {
    
    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final String DEFAULT_USER = "root";
    
    @Inject public UserInterfaceComponent ui;
    @Inject public DbmsHandlerComponent dbmsHandler;
    @Inject public PasswordComponent passwords;
    @Inject public ConfigFileHelper cfHelper;
    @Inject public EventComponent events;
    @Inject public InjectionLoader loader;

    @FXML private TextField fieldHost;
    @FXML private TextField fieldPort;
    @FXML private TextField fieldFile;
    @FXML private Button fieldFileBtn;
    @FXML private ComboBox<String> fieldType;
    @FXML private TextField fieldName;
    @FXML private TextField fieldSchema;
    @FXML private TextField fieldServer;
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
    @FXML private RowConstraints serverRow;
    @FXML private RowConstraints userRow;
    @FXML private RowConstraints passRow;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        final FilteredList<Node> hostRowChildren = inRow(hostRow);
        final FilteredList<Node> fileRowChildren = inRow(fileRow);
        final FilteredList<Node> userRowChildren = inRow(userRow);
        final FilteredList<Node> passRowChildren = inRow(passRow);
        final FilteredList<Node> dbmsRowChildren = inRow(dbmsRow);
        final FilteredList<Node> schemaRowChildren = inRow(schemaRow);
        final FilteredList<Node> serverRowChildren = inRow(serverRow);

        fieldFileBtn.setGraphic(FontAwesome.FOLDER_OPEN.view());
        buttonConnect.setGraphic(FontAwesome.SIGN_IN.view());

        fieldType.setItems(getDbmsTypes()
            .collect(toCollection(FXCollections::observableArrayList))
        );

        // Keep track of the generated values so that we don't overwrite user
        // changes with automatic ones.
        final AtomicReference<DbmsType> dbmsType = new AtomicReference<>();
        final AtomicReference<String> generatedHost = new AtomicReference<>("");
        final AtomicReference<String> generatedPort = new AtomicReference<>("");
        final AtomicReference<String> generatedUser = new AtomicReference<>("");
        final AtomicReference<String> generatedName = new AtomicReference<>("");
        final AtomicReference<String> generatedSchema = new AtomicReference<>("");
        final AtomicReference<String> generatedServer = new AtomicReference<>("");
        final AtomicReference<String> generatedConnUrl = new AtomicReference<>("");

        // Use this method reference to recalculate any default values in text fields.
        final Runnable recalculateFields = () -> {
            final DbmsType item = dbmsType.get();

            // Hide name rows if particular Dbms doesn't support them.
            toggleVisibility(hostRow, hostRowChildren, item.getConnectionType() == DbmsType.ConnectionType.HOST_AND_PORT);
            toggleVisibility(fileRow, fileRowChildren, item.getConnectionType() == DbmsType.ConnectionType.DBMS_AS_FILE);
            toggleVisibility(userRow, userRowChildren, item.hasDatabaseUsers());
            toggleVisibility(passRow, passRowChildren, item.hasDatabaseUsers());
            toggleVisibility(dbmsRow, dbmsRowChildren, item.hasDatabaseNames());
            toggleVisibility(schemaRow, schemaRowChildren, item.hasSchemaNames());
            toggleVisibility(serverRow, serverRowChildren, item.hasServerNames());

            if (fieldHost.getText().isEmpty()
                || fieldHost.getText().equals(generatedHost.get())) {
                fieldHost.textProperty().setValue(DEFAULT_HOST);
                generatedHost.set(DEFAULT_HOST);
            }

            // Disable Dbms User-property for database types that doesn't use it
            disableDbmsUserPropertyForDbmsesThatDoesNotUseIt(generatedUser, item);

            // Disable Dbms Name-property for database types that doesn't use it
            disableDbmsNamePropertyForDmbsesThatDoesNotUseIt(generatedPort, generatedName, generatedSchema, generatedServer, item);

        };

        // Use this method reference to recalculate the connection URL.
        final Runnable recalculateConnUrl = () -> recalculateConnUrl(dbmsType, generatedConnUrl);

        // If the user changes something, recalculate default values.
        setupRecalculateDefaultValuesOnUserChange(dbmsType, recalculateFields, recalculateConnUrl);

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
        disableConnectButtonIfAnyFieldIsNotEntered(dbmsType);

        // Load dbms from file-action
        fieldFileBtn.setOnAction(loadDmbsFromFileAction());

        // Connect to database action
        buttonConnect.setOnAction(connectToDatabaseAction(dbmsType, generatedConnUrl));
    }

    private void setupRecalculateDefaultValuesOnUserChange(AtomicReference<DbmsType> dbmsType, Runnable recalculateFields, Runnable recalculateConnUrl) {
        fieldType.getSelectionModel().selectedItemProperty()
            .addListener((observable, old, typeName) -> {
                if (!typeName.isEmpty()) {
                    dbmsType.set(findDbmsType(typeName));
                    recalculateFields.run();
                    recalculateConnUrl.run();
                }
            });

        fieldHost.textProperty().addListener((ob, o, n) -> recalculateConnUrl.run());
        fieldPort.textProperty().addListener((ob, o, n) -> recalculateConnUrl.run());
        fieldFile.textProperty().addListener((ob, o, n) -> recalculateConnUrl.run());
        fieldName.textProperty().addListener((ob, o, n) -> recalculateConnUrl.run());
        fieldServer.textProperty().addListener((ob, o, n) -> recalculateConnUrl.run());

        fieldHost.focusedProperty().addListener((ob, o, n) -> recalculateOnLostFocusAndEmptyField(recalculateFields, o, fieldHost));

        UnaryOperator<TextFormatter.Change> onlyDigitsFilter = change ->
            change.getText().matches("[0-9]*") ? change : null;

        fieldPort.setTextFormatter(new TextFormatter<>(onlyDigitsFilter));
        fieldPort.focusedProperty().addListener((ob, o, n) -> recalculateOnLostFocusAndEmptyField(recalculateFields, o, fieldPort));

        fieldFile.focusedProperty().addListener((ob, o, n) -> recalculateOnLostFocusAndEmptyField(recalculateFields, o, fieldFile));

        fieldUser.focusedProperty().addListener((ob, o, n) -> recalculateOnLostFocusAndEmptyField(recalculateFields, o, fieldUser));

        fieldName.focusedProperty().addListener((ob, o, n) -> recalculateOnLostFocusAndEmptyField(recalculateFields, o, fieldName));

        fieldSchema.focusedProperty().addListener((ob, o, n) -> recalculateOnLostFocusAndEmptyField(recalculateFields, o, fieldSchema));

        fieldServer.focusedProperty().addListener((ob, o, n) -> recalculateOnLostFocusAndEmptyField(recalculateFields, o, fieldServer));
    }

    private void recalculateOnLostFocusAndEmptyField(Runnable recalculateFields, Boolean oldValue, TextField field) {
        if (oldValue && field.getText().isEmpty()) {
            recalculateFields.run();
        }
    }

    private void disableConnectButtonIfAnyFieldIsNotEntered(AtomicReference<DbmsType> dbmsType) {
        buttonConnect.disableProperty().bind(createBooleanBinding(
            () -> ((fieldHost.textProperty().isEmpty().get()
            ||      fieldPort.textProperty().isEmpty().get())
                && dbmsType.get().getConnectionType() == DbmsType.ConnectionType.HOST_AND_PORT)
            ||     (fieldFile.textProperty().isEmpty().get() && dbmsType.get().getConnectionType() == DbmsType.ConnectionType.DBMS_AS_FILE)
            ||      fieldType.getSelectionModel().isEmpty()
            ||     (fieldName.textProperty().isEmpty().get() && dbmsType.get().hasDatabaseNames())
            ||     (fieldUser.textProperty().isEmpty().get() && dbmsType.get().hasDatabaseUsers())
            ||     (fieldServer.textProperty().isEmpty().get() && dbmsType.get().hasServerNames())
            ||     (fieldSchema.textProperty().isEmpty().get() && dbmsType.get().hasSchemaNames()),

            fieldHost.textProperty(),
            fieldPort.textProperty(),
            fieldFile.textProperty(),
            fieldType.selectionModelProperty(),
            fieldName.textProperty(),
            fieldUser.textProperty(),
            fieldServer.textProperty(),
            fieldSchema.textProperty()
        ));
    }

    private EventHandler<ActionEvent> loadDmbsFromFileAction() {
        return ev -> {
            final FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Database File");

            if (!"".equals(fieldFile.getText().trim())) {
                final Path path = Paths.get(fieldFile.getText().trim());

                if (path.getParent().toFile().exists()) {
                    final String parentFolder = path.getParent().toString();

                    if (!"".equals(parentFolder)) {
                        fileChooser.setInitialDirectory(new File(parentFolder));
                    }
                }

                if (path.toFile().exists()) {
                    fileChooser.setInitialFileName(fieldFile.getText());
                }
            }

            final File file = fileChooser.showOpenDialog(ui.getStage());
            if (file != null) {
                fieldFile.setText(Paths.get(".").toAbsolutePath().getParent().relativize(file.toPath()).toString());
            }
        };
    }

    private EventHandler<ActionEvent> connectToDatabaseAction(AtomicReference<DbmsType> dbmsType, AtomicReference<String> generatedConnUrl) {
        return ev -> {
            final DbmsType type = dbmsType.get();

            // Register password in password component
            passwords.put(
                fieldName.getText(),
                fieldPass.getText().toCharArray()
            );

            // Create a new Dbms using the settings configured.
            final DbmsProperty dbms = ui.projectProperty().mutator().addNewDbms();
            setDbmsProperties(dbmsType, generatedConnUrl, type, dbms);

            final String schema = Optional.of(fieldSchema.getText())
                .filter(s -> dbmsType.get().hasSchemaNames())
                .filter(s -> !s.isEmpty())
                .orElseGet(() -> type.getDefaultSchemaName().orElseGet(dbms::getName));

            // Set the default project name to the name of the schema.
            setDefaultProjectName(type, schema);

            // Connect to database
            if (cfHelper.loadFromDatabase(dbms, schema)) {
                loader.loadAndShow("Scene");
                events.notify(UIEvent.OPEN_MAIN_WINDOW);
            }
        };
    }

    private void setDbmsProperties(AtomicReference<DbmsType> dbmsType, AtomicReference<String> generatedConnUrl, DbmsType type, DbmsProperty dbms) {
        dbms.typeNameProperty().set(dbmsType.get().getName());

        if (type.getConnectionType() == DbmsType.ConnectionType.HOST_AND_PORT) {
            dbms.ipAddressProperty().set(fieldHost.getText());
            dbms.portProperty().set(Integer.parseInt(fieldPort.getText()));
        } else if (type.getConnectionType() == DbmsType.ConnectionType.DBMS_AS_FILE) {
            dbms.localPathProperty().set(fieldFile.getText());
        }

        if (type.hasDatabaseUsers()) {
            dbms.usernameProperty().set(fieldUser.getText());
        }

        if (type.hasServerNames()) {
            dbms.serverNameProperty().set(fieldServer.getText());
        }

        dbms.nameProperty().set(Optional.of(fieldName.getText())
            .filter(s -> dbmsType.get().hasDatabaseNames())
            .filter(s -> !s.isEmpty())
            .orElseGet(() -> type.getDefaultDbmsName().orElseGet(fieldName::getText)));

        if (!areaConnectionUrl.getText().isEmpty() && !areaConnectionUrl.getText().equals(generatedConnUrl.get())) {
            dbms.connectionUrlProperty().setValue(areaConnectionUrl.getText());
        }
    }

    private void setDefaultProjectName(DbmsType type, String schema) {
        if (type.hasSchemaNames() || type.hasDatabaseNames()) {
            ui.projectProperty().nameProperty()
                .setValue(schema);
        } else if (type.getConnectionType() == DbmsType.ConnectionType.DBMS_AS_FILE) {
            String filename = Paths.get(fieldFile.getText()).getFileName().toString();
            if (filename.contains(".")) {
                filename = filename.substring(0, filename.lastIndexOf('.'));
            }
            ui.projectProperty().nameProperty()
                .setValue(filename);
        } else {
            ui.projectProperty().nameProperty()
                .setValue("Demo");
        }
    }

    private void recalculateConnUrl(AtomicReference<DbmsType> dbmsType, AtomicReference<String> generatedConnUrl) {
        final DbmsType item = dbmsType.get();

        if (areaConnectionUrl.getText().isEmpty()
            || areaConnectionUrl.getText().equals(generatedConnUrl.get())) {
            final String url = item.getConnectionUrlGenerator().from(
                TemporaryDbms.create(
                    ui.projectProperty(),
                    fieldName.getText(),
                    fieldFile.getText(),
                    fieldHost.getText(),
                    fieldPort.getText().isEmpty() ? 0 : Integer.parseInt(fieldPort.getText()),
                    fieldServer.getText()
                )
            );
            generatedConnUrl.set(url);
            areaConnectionUrl.setText(url);
        }
    }

    private void disableDbmsNamePropertyForDmbsesThatDoesNotUseIt(AtomicReference<String> generatedPort, AtomicReference<String> generatedName, AtomicReference<String> generatedSchema, AtomicReference<String> generatedServer, DbmsType item) {
        if (item.hasDatabaseNames()) {
            fieldName.setDisable(false);

            if (fieldName.getText().isEmpty()
                || fieldName.getText().equals(generatedName.get())) {
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
                || fieldSchema.getText().equals(generatedSchema.get())) {
                item.getDefaultSchemaName().ifPresent(name -> {
                    fieldSchema.textProperty().setValue(name);
                    generatedSchema.set(name);
                });
            }
        } else {
            fieldSchema.setDisable(true);
        }

        if (item.hasServerNames()) {
            fieldServer.setDisable(false);

            if (fieldServer.getText().isEmpty()
                || fieldServer.getText().equals(generatedServer.get())) {
                item.getDefaultServerName().ifPresent(name -> {
                    fieldServer.textProperty().setValue(name);
                    generatedServer.set(name);
                });
            }
        } else {
            fieldServer.setDisable(true);
        }

        fieldName.getTooltip().setText(item.getDbmsNameMeaning());

        if (fieldPort.getText().isEmpty()
            || fieldPort.getText().equals(generatedPort.get())) {
            final String port = Integer.toString(item.getDefaultPort());
            fieldPort.textProperty().setValue(port);
            generatedPort.set(port);
        }
    }

    private void disableDbmsUserPropertyForDbmsesThatDoesNotUseIt(AtomicReference<String> generatedUser, DbmsType item) {
        if (item.hasDatabaseUsers()) {
            fieldUser.setDisable(false);
            fieldPass.setDisable(false);

            if (fieldUser.getText().isEmpty()
                || fieldUser.getText().equals(generatedUser.get())) {
                fieldUser.textProperty().setValue(DEFAULT_USER);
                generatedUser.set(DEFAULT_USER);
            }
        } else {
            generatedUser.set(DEFAULT_USER);
            fieldUser.setDisable(true);
            fieldPass.setDisable(true);
        }
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

        public static TemporaryDbms create(Project project, String name, String file, String ip, int port, String serverName) {
            final Map<String, Object> data = new LinkedHashMap<>();
            data.put(HasIdUtil.ID, name);
            data.put(HasNameUtil.NAME, name);
            data.put(DbmsUtil.IP_ADDRESS, ip);
            if (port != 0) {
                data.put(DbmsUtil.PORT, port);
            }
            data.put(DbmsUtil.LOCAL_PATH, file);
            data.put(DbmsUtil.SERVER_NAME, serverName);
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
        public Optional<String> getAsString(String key) {
            return get(key).map(String.class::cast);
        }

        @Override
        public OptionalBoolean getAsBoolean(String key) {
            return get(key)
                .map(Boolean.class::cast)
                .map(OptionalBoolean::of)
                .orElseGet(OptionalBoolean::empty);
        }

        @Override
        public OptionalLong getAsLong(String key) {
            return get(key)
                .map(Long.class::cast)
                .map(OptionalLong::of)
                .orElseGet(OptionalLong::empty);
        }



        @Override
        public OptionalDouble getAsDouble(String key) {
            return get(key)
                .map(Double.class::cast)
                .map(OptionalDouble::of)
                .orElseGet(OptionalDouble::empty);
        }

        @Override
        public OptionalInt getAsInt(String key) {
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
        public Stream<Schema> schemas() {
            return Stream.empty();
        }

        @Override
        public Stream<? extends Document> children() {
            return Stream.empty();
        }
    }
}
