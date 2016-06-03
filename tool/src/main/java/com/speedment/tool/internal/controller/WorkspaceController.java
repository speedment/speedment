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
package com.speedment.tool.internal.controller;

import com.speedment.tool.component.UserInterfaceComponent;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.tool.UISession;
import com.speedment.tool.util.Loader;
import com.speedment.tool.config.DocumentProperty;
import com.speedment.tool.event.TreeSelectionChange;
import com.speedment.tool.property.AbstractPropertyItem;
import java.net.URL;
import static java.util.Objects.requireNonNull;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil Forslund
 */
public final class WorkspaceController implements Initializable {
    
    private final UISession session;
    private final ObservableList<PropertySheet.Item> properties;
    
    private @FXML TitledPane workspace;
    
    private WorkspaceController(UISession session) {
        this.session    = requireNonNull(session);
        this.properties = FXCollections.observableArrayList();
        
        final UserInterfaceComponent ui = session.getSpeedment()
            .getOrThrow(UserInterfaceComponent.class);
        
        ui.getSelectedTreeItems()
            .addListener((ListChangeListener.Change<? extends TreeItem<DocumentProperty>> change) -> {
                properties.clear();
                
                if (!change.getList().isEmpty()) {
                    final TreeItem<DocumentProperty> treeItem = change.getList().get(0);
                    
                    if (treeItem != null) {
                        treeItem.getValue()
                            .getUiVisibleProperties(session.getSpeedment())
                            .forEach(properties::add);
                    }
                }
                
                session.getSpeedment()
                    .getEventComponent()
                    .notify(new TreeSelectionChange(change, properties));
            });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        final PropertySheet sheet = new PropertySheet(properties);
        
        sheet.setMode(PropertySheet.Mode.NAME);
        sheet.setModeSwitcherVisible(false);
        sheet.setSearchBoxVisible(false);
        sheet.setPropertyEditorFactory(item -> {
            if (item instanceof AbstractPropertyItem<?, ?, ?>) {
                @SuppressWarnings("unchecked")
                final AbstractPropertyItem<?, ?, ?> casted = (AbstractPropertyItem<?, ?, ?>) item;
                return casted.createEditor();
            } else throw new SpeedmentException(
                "Unknown property item type '" + item.getClass() + "'."
            );
        });
        
        workspace.setContent(sheet);
        
        Bindings.bindContentBidirectional(
            session.getSpeedment().getOrThrow(UserInterfaceComponent.class).getProperties(), 
            properties
        );
    }
    
    public static Node create(UISession session) {
        return Loader.create(session, "Workspace");
	}
}