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
package com.speedment.plugins.enums.internal.ui;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.Config;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.common.json.Json;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.common.singletonstream.SingletonStream;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Schema;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.config.internal.ProjectImpl;
import com.speedment.runtime.config.trait.HasName;
import com.speedment.runtime.config.trait.HasParent;
import com.speedment.runtime.core.ApplicationMetadata;
import com.speedment.runtime.core.component.ManagerComponent;
import com.speedment.runtime.core.component.PasswordComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.component.StreamSupplierComponent;
import com.speedment.runtime.core.component.sql.SqlStreamSupplierComponent;
import com.speedment.runtime.core.db.DbmsMetadataHandler;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.internal.component.sql.SqlStreamSupplierComponentImpl;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.core.manager.Persister;
import com.speedment.runtime.core.manager.Remover;
import com.speedment.runtime.core.manager.Updater;
import com.speedment.runtime.core.stream.parallel.ParallelStrategy;
import com.speedment.runtime.core.util.ProgressMeasure;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.StringField;
import com.speedment.runtime.typemapper.TypeMapper;
import com.speedment.tool.config.DbmsProperty;
import com.speedment.tool.config.trait.HasEnumConstantsProperty;
import com.speedment.tool.config.trait.HasTypeMapperProperty;
import com.speedment.tool.core.component.UserInterfaceComponent;
import com.speedment.tool.core.exception.SpeedmentToolException;
import com.speedment.tool.core.resource.FontAwesome;
import com.speedment.tool.propertyeditor.item.AbstractLabelTooltipItem;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static com.speedment.common.injector.State.RESOLVED;
import static com.speedment.runtime.config.util.DocumentUtil.ancestor;
import static java.util.Objects.requireNonNull;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.stream.Collectors.joining;
import static javafx.application.Platform.runLater;
import static javafx.scene.layout.Region.USE_PREF_SIZE;

/**
 * Item for generating a comma-separated string.
 * <p>
 * We parse what values an enum should be able to take from a string, where
 * each element is separated by a comma. This editor item allows the user
 * to easily edit such a string.
 * 
 * @author  Simon Jonasson
 * @since   3.0.0
 */
public final class AddRemoveStringItem
    <DOC extends HasEnumConstantsProperty
               & HasTypeMapperProperty
               & HasParent<? extends Table>
               & HasName>
extends AbstractLabelTooltipItem {

    private final static Logger LOGGER = LoggerManager.getLogger(AddRemoveStringItem.class);

    private final DOC column;
    private final ObservableList<String> strings;
    private final ObservableBooleanValue enabled;
    
    @SuppressWarnings("FieldCanBeLocal")
    private final StringProperty cache;
    
    private final String DEFAULT_FIELD = "ENUM_CONSTANT_";
    private final double SPACING  = 10.0;
    private final int LIST_HEIGHT = 200;

    private @Inject ProjectComponent projects;
    private @Inject DbmsMetadataHandler metadata;
    private @Inject PasswordComponent passwords;
    private @Inject UserInterfaceComponent ui;
    private @Inject Injector injector;

    ////////////////////////////////////////////////////////////////////////////
    //                            Constructor                                 //
    ////////////////////////////////////////////////////////////////////////////
     
    AddRemoveStringItem(
            final DOC column,
            final String label,
            final StringProperty value,
            final String tooltip,
            final ObservableBooleanValue enableThis) {
        
        super(label, tooltip, NO_DECORATOR);

        final String currentValue = value.get();
        if (currentValue == null) {
            this.strings  = FXCollections.observableArrayList();
        } else {
            this.strings  = FXCollections.observableArrayList(
                Stream.of(currentValue.split(","))
                    .filter(s -> !s.isEmpty())
                    .toArray(String[]::new)
            );
        }

        this.column  = requireNonNull(column);
        this.enabled = enableThis;
        this.cache   = new SimpleStringProperty();
        
        this.strings.addListener((ListChangeListener.Change<? extends String> c) -> {
            @SuppressWarnings("unchecked")
            final List<String> list = (List<String>) c.getList();
            value.setValue(getFormatedString(list));
        });
    }

    ////////////////////////////////////////////////////////////////////////////
    //                                Public                                  //
    ////////////////////////////////////////////////////////////////////////////

    @Override
    public Node createLabel() {
        final Node node = super.createLabel();
        hideShowBehaviour(node);        
        return node;
    }
    
    @Override
    protected Node createUndecoratedEditor() {
        final VBox container = new VBox();
        
        final ListView<String> listView = new ListView<>(strings);
        listView.setCellFactory(view -> new EnumCell(strings));
        listView.setEditable(true);

        listView.setMaxHeight(USE_PREF_SIZE);
        listView.setPrefHeight(LIST_HEIGHT);

        final HBox controls = new HBox(SPACING);
        controls.setAlignment(Pos.CENTER);
        controls.getChildren().addAll(
            addButton(listView),
            removeButton(listView),
            populateButton(listView)
        );

        container.setSpacing(SPACING);
        container.getChildren().addAll(listView, controls);
        hideShowBehaviour(container);

        
        return container;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //                               Private                                  //
    ////////////////////////////////////////////////////////////////////////////
    
    /**
     * Removes any empty substrings and makes sure the entire string is either
     * {@code null} or non-empty.
     * 
     * @return  the formatted string
     */
    private String getFormatedString(List<String> newValue) {
        final String formated = newValue.stream()
            .filter(v -> !v.isEmpty())
            .collect(joining(","));
        
        if (formated == null || formated.isEmpty()) {
            return null;
        } else {
            return formated;
        }
    }

    private void setValue(String value) {
        if (value == null) {
            strings.clear();
        } else {
            strings.setAll(value.split(","));
        }
    }
    
    private void hideShowBehaviour(Node node){
        node.visibleProperty().bind(enabled);
        node.managedProperty().bind(enabled);
        node.disableProperty().bind(Bindings.not(enabled));
    }
    
    private Button removeButton(final ListView<String> listView) {
        final Button button = new Button("Remove Selected", FontAwesome.TIMES.view());
        
        button.setOnAction(e -> {
            final int selectedIdx = listView.getSelectionModel().getSelectedIndex();
            if (selectedIdx != -1 && listView.getItems().size() > 1) {
                final int newSelectedIdx = (selectedIdx == listView.getItems().size() - 1) ? selectedIdx - 1
                    : selectedIdx;
                listView.getItems().remove(selectedIdx);
                listView.getSelectionModel().select(newSelectedIdx);
            }
        });
        
        return button;
    }

    private Button addButton(final ListView<String> listView) {
        final Button button = new Button("Add Item", FontAwesome.PLUS.view());
        
        button.setOnAction(e -> {
            final int newIndex = listView.getItems().size();

            final Set<String> set = new HashSet<>(strings);
            final AtomicInteger i = new AtomicInteger(0);
            while (!set.add(DEFAULT_FIELD + i.incrementAndGet())) {}

            listView.getItems().add(DEFAULT_FIELD + i.get());
            listView.scrollTo(newIndex);
            listView.getSelectionModel().select(newIndex);
            
            // There is a strange behavior in JavaFX if you try to start editing
            // a field on the same animation frame as another field lost focus.
            // Therefore, we wait one animation cycle before setting the field
            // into the editing state
            runLater(() -> listView.edit(newIndex));
        });
        
        return button;
    }

    private Button populateButton(final ListView<String> listView) {
        final Button button = new Button("Populate", FontAwesome.DATABASE.view());

        button.setOnAction(e -> {
            final Column col = column.getMappedColumn();

            final DbmsProperty dbms = ancestor(col, DbmsProperty.class).get();
            final String dbmsName   = dbms.getName();
            final String schemaName = ancestor(col, Schema.class).get().getName();
            final String tableName  = col.getParentOrThrow().getName();
            final String columnName = col.getName();

            final ProgressMeasure progress = ProgressMeasure.create();

            if (!passwords.get(dbmsName).isPresent()) {
                ui.showPasswordDialog(dbms);
            }

            final char[] password = passwords.get(dbmsName).orElseThrow(() ->
                new SpeedmentToolException(
                    "A password is required to populate enum constants field!"
                )
            );

            final CompletableFuture<Boolean> task = supplyAsync(() -> {
                try {
                    // Hack to create a Manager for reading a single column from
                    // the database.
                    LOGGER.info("Creating Temporary Speedment...");

                    progress.setProgress(0.2);
                    final Injector inj = injector.newBuilder()
                        .withComponent(TempApplicationMetadata.class)
                        .withComponent(SqlStreamSupplierComponentImpl.class)
                        .withParam("temp.json", Json.toJson(dbms.getParentOrThrow().getData()))
                        .withParam("temp.dbms", dbmsName)
                        .withParam("temp.schema", schemaName)
                        .withParam("temp.table", tableName)
                        .withParam("temp.column", columnName)
                        .withComponent(SingleColumnManager.class)
                        .beforeInitialized(PasswordComponent.class, passw -> {
                            LOGGER.info("Installing Password...");
                            passw.put(dbmsName, password);
                        })
                        .build();

                    LOGGER.info("Temporary Speedment built. Streaming...");

                    progress.setProgress(0.4);
                    final String constants =
                        inj.getOrThrow(SingleColumnManager.class).stream()
                            .distinct().sorted()
                            .collect(joining(","));

                    LOGGER.info("Streaming complete!");

                    // Run in UI thread:
                    runLater(() -> {
                        column.enumConstantsProperty().setValue(constants);
                        setValue(constants);
                        progress.setProgress(1.0);
                    });

                    return true;
                } catch (final InstantiationException ex) {
                    LOGGER.error(ex);
                    return false;
                }
            }).handleAsync((res, ex) -> {
                if (ex != null) {
                    LOGGER.error(ex);
                    return false;
                }

                progress.setProgress(1.0);
                return true;
            });

            ui.showProgressDialog("Populating Enum Constants", progress, task);
        });

        return button;
    }

    ////////////////////////////////////////////////////////////////////////////
    //                            Internal Class                              //
    ////////////////////////////////////////////////////////////////////////////

    private static class TempApplicationMetadata
    implements ApplicationMetadata {

        private @Config(name="temp.json", value="") String json;

        @Override
        public Project makeProject() {
            try {
                @SuppressWarnings("unchecked") final Map<String, Object> data =
                    (Map<String, Object>) Json.fromJson(json);
                return new ProjectImpl(data);
            } catch (final ClassCastException ex) {
                throw new SpeedmentToolException(
                    "Error deserializing temporary project JSON.", ex
                );
            }
        }
    }

    private static class TempColumnIdentifier implements ColumnIdentifier<String> {
        private final String dbms;
        private final String schema;
        private final String table;
        private final String column;

        TempColumnIdentifier(String dbms,
                             String schema,
                             String table,
                             String column) {

            this.dbms   = requireNonNull(dbms);
            this.schema = requireNonNull(schema);
            this.table  = requireNonNull(table);
            this.column = requireNonNull(column);
        }

        @Override
        public String getDbmsName() {
            return dbms;
        }

        @Override
        public String getTableName() {
            return table;
        }

        @Override
        public String getColumnName() {
            return column;
        }

        @Override
        public String getSchemaName() {
            return schema;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ColumnIdentifier)) return false;

            final ColumnIdentifier<?> that = (ColumnIdentifier<?>) o;
            return dbms.equals(that.getDbmsName())
                && schema.equals(that.getSchemaName())
                && table.equals(that.getTableName())
                && column.equals(that.getColumnName());
        }

        @Override
        public int hashCode() {
            int result = dbms.hashCode();
            result = 31 * result + schema.hashCode();
            result = 31 * result + table.hashCode();
            result = 31 * result + column.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "TempColumnIdentifier{" +
                "dbms='" + dbms + '\'' +
                ", schema='" + schema + '\'' +
                ", table='" + table + '\'' +
                ", column='" + column + '\'' +
                '}';
        }
    }

    private static class SingleColumnManager implements Manager<String> {

        private StringField<String, String> field;
        private TableIdentifier<String> tableId;

        private @Inject StreamSupplierComponent streamSupplierComponent;
        private @Config(name="temp.dbms", value="") String dbms;
        private @Config(name="temp.schema", value="") String schema;
        private @Config(name="temp.table", value="") String table;
        private @Config(name="temp.column", value="") String column;

        SingleColumnManager() {}

        @ExecuteBefore(State.INITIALIZED)
        void setFieldAndTableId() {
            this.tableId = TableIdentifier.of(dbms, schema, table);
            this.field   = StringField.create(
                new TempColumnIdentifier(dbms, schema, table, column),
                e -> e, (e, s) -> e,
                TypeMapper.identity(),
                false
            );
        }

        @ExecuteBefore(State.INITIALIZED)
        void configureManagerComponent(
                @WithState(State.INITIALIZED) ManagerComponent managerComponent,
                @WithState(State.INITIALIZED) ProjectComponent projectComponent) {
            Objects.requireNonNull(projectComponent);
            managerComponent.put(this);
        }

        @ExecuteBefore(RESOLVED)
        void configureSqlStreamSupplier(
                @WithState(RESOLVED) SqlStreamSupplierComponent sql) {
            sql.install(tableId, in -> in.getString(column));
        }

        @Override
        public TableIdentifier<String> getTableIdentifier() {
            return tableId;
        }

        @Override
        public Class<String> getEntityClass() {
            return String.class;
        }

        @Override
        public Stream<Field<String>> fields() {
            return SingletonStream.of(field);
        }

        @Override
        public Stream<Field<String>> primaryKeyFields() {
            return SingletonStream.of(field);
        }

        @Override
        public Stream<String> stream() {
            return streamSupplierComponent.stream(
                getTableIdentifier(),
                ParallelStrategy.computeIntensityDefault()
            );
        }

        @Override
        public final String persist(String entity) throws SpeedmentException {
            throw new UnsupportedOperationException("This manager is read-only.");
        }

        @Override
        public Persister<String> persister() {
            throw new UnsupportedOperationException("This manager is read-only.");
        }

        @Override
        public final String update(String entity) throws SpeedmentException {
            throw new UnsupportedOperationException("This manager is read-only.");
        }

        @Override
        public Updater<String> updater() {
            throw new UnsupportedOperationException("This manager is read-only.");
        }

        @Override
        public final String remove(String entity) throws SpeedmentException {
            throw new UnsupportedOperationException("This manager is read-only.");
        }

        @Override
        public Remover<String> remover() {
            throw new UnsupportedOperationException("This manager is read-only.");
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "{tableId: " +
                TableIdentifier.of(dbms, schema, table).toString()
                + "}";
        }
    }

    private final static class EnumCell extends TextFieldListCell<String> {

        private final ObservableList<String> strings;
        private String labelString;

        private EnumCell(ObservableList<String> strings) {
            super();
            this.strings = requireNonNull(strings);
            setConverter(myConverter());
        }

        @Override
        public void startEdit() {
            labelString = getText();
            super.startEdit();
        }

        private StringConverter<String> myConverter() {
            return new StringConverter<String>() {
               
                @Override
                public String toString(String value) {
                    return (value != null) ? value : "";
                }

                @Override
                public String fromString(String value) {
                    // Avoid false positives (ie showing an error that we match ourselves)
                    if (value.equalsIgnoreCase(labelString)) {
                        return value;
                    } else if (value.isEmpty()) {
                        LOGGER.info("An enum field cannot be empty. Please remove the field instead.");
                        return labelString;
                    }
                    
                    // Make sure this is not a duplicate entry
                    final AtomicBoolean duplicate = new AtomicBoolean(false);
                    strings.stream()
                        .filter(elem -> elem.equalsIgnoreCase(value))
                        .forEach(elem -> duplicate.set(true));
                    if (duplicate.get()){
                        LOGGER.info("Enum cannot contain the same constant twice");
                        return labelString;
                        
                    // Make sure this entry contains only legal characters
                    } else if ( !value.matches("([\\w\\-\\_\\ ]+)")) {
                        LOGGER.info("Enum should only contain letters, number, underscore and/or dashes");
                        return labelString;
                        
                    // Warn if it contains a space
                    } else if (value.contains(" ")) {
                        LOGGER.warn("Enum spaces will be converted to underscores in Java");
                        return value;
                    } else {
                        return value; 
                    }
                }
            };
        }
    }
}