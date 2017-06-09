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
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *
 * @author Emil Forslund
 */
public final class WorkspaceController implements Initializable {
    
    private final ObservableList<PropertyEditor.Item> properties;
    
    private @Inject UserInterfaceComponent ui;
    private @Inject EventComponent events;
    private @Inject PropertyEditorComponent editors;
    
    private @FXML TitledPane workspace;
    
    public WorkspaceController() {
        this.properties = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        final PropertySheet sheet = new PropertySheet(properties);
        
        ui.getSelectedTreeItems()
            .addListener((ListChangeListener.Change<? extends TreeItem<DocumentProperty>> change) -> {
                properties.clear();
                
                if (!change.getList().isEmpty()) {
                    final TreeItem<DocumentProperty> treeItem = 
                        change.getList().get(0);
                    
                    if (treeItem != null) {
                        final DocumentProperty property = treeItem.getValue();
                    
                        @SuppressWarnings("unchecked")
                        final HasNameProperty withName = (HasNameProperty) property;
                        
                        final Optional<String> extraInfo = Cast.cast(property, ColumnProperty.class)
                            .map(ColumnProperty::findDatabaseType)
                            .map(Class::getSimpleName)
                            .map(s -> "(" + s + ")");
                        
                        workspace.textProperty().bind(
                            Bindings.createStringBinding(() -> String.format(
                                "Editing %s '%s' %s",
                                withName instanceof Table
                                    ? ((Table) withName).isView()
                                        ? "View" : "Table"
                                    : withName.mainInterface().getSimpleName(),
                                withName.getName(),
                                extraInfo.orElse("")
                            ), withName.nameProperty())
                        );
                        
                        editors.getUiVisibleProperties(treeItem.getValue())
                            .forEachOrdered(properties::add);
                    }
                }
                
                events.notify(new TreeSelectionChange(change, properties));
            });
        
        workspace.setContent(sheet);
        
        Bindings.bindContentBidirectional(ui.getProperties(), properties);
    }
}