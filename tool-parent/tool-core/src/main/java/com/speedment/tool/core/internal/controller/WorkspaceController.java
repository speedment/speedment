/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
import com.speedment.runtime.config.Table;
import com.speedment.runtime.core.internal.util.Cast;
import com.speedment.tool.config.ColumnProperty;
import com.speedment.tool.config.DocumentProperty;
import com.speedment.tool.config.trait.HasNameProperty;
import com.speedment.tool.core.component.UserInterfaceComponent;
import com.speedment.tool.core.event.TreeSelectionChange;
import com.speedment.tool.propertyeditor.PropertyEditor;
import com.speedment.tool.propertyeditor.PropertySheet;
import com.speedment.tool.propertyeditor.component.PropertyEditorComponent;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author Emil Forslund
 */
public final class WorkspaceController implements Initializable {
    
    private final ObservableList<PropertyEditor.Item> properties;

    @Inject public UserInterfaceComponent ui;
    @Inject public EventComponent events;
    @Inject public PropertyEditorComponent editors;

    @FXML private TitledPane workspace;
    @FXML private ScrollPane scrollpane;

    public WorkspaceController() {
        this.properties = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final PropertySheet sheet = new PropertySheet(properties);
        ui.getSelectedTreeItems().addListener(treeItemListChangeListener());
        scrollpane.setContent(sheet);
        Bindings.bindContentBidirectional(ui.getProperties(), properties);
    }

    private ListChangeListener<TreeItem<DocumentProperty>> treeItemListChangeListener() {
        return change -> {
            properties.clear();

            if (!change.getList().isEmpty()) {
                final TreeItem<DocumentProperty> treeItem = change.getList().get(0);

                if (treeItem != null) {
                    final DocumentProperty property = treeItem.getValue();
                    final HasNameProperty withName = (HasNameProperty) property;

                    final String extraInfo = Cast.cast(property, ColumnProperty.class)
                        .map(ColumnProperty::findDatabaseType)
                        .map(Class::getSimpleName)
                        .map(s -> "(" + s + ")")
                        .orElse("");

                    workspace.textProperty().bind(
                        Bindings.createStringBinding(() -> String.format(
                            "Settings for database %s '%s' %s",
                            type(withName),
                            withName.getName(),
                            extraInfo
                        ), withName.nameProperty())
                    );

                    editors.getUiVisibleProperties(treeItem.getValue())
                        .forEachOrdered(properties::add);
                }
            }
            @SuppressWarnings("unchecked")
            final ListChangeListener.Change<TreeItem<DocumentProperty>> changeCasted = (ListChangeListener.Change<TreeItem<DocumentProperty>>) change;
            events.notify(new TreeSelectionChange(changeCasted, properties));
        };
    }

    private String type(HasNameProperty withName) {
        if (withName instanceof Table) {
            return ((Table) withName).isView() ? "view" : "table";
        } else {
            return withName.mainInterface().getSimpleName().toLowerCase();
        }
    }
}