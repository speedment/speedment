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
package com.speedment.internal.newgui;

import com.speedment.exception.SpeedmentException;
import com.speedment.internal.gui.config.AbstractNodeProperty;
import com.speedment.internal.newgui.property.AbstractPropertyItem;
import com.speedment.internal.newgui.util.UILoader;
import com.speedment.internal.newgui.util.UISession;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import org.controlsfx.control.PropertySheet;
import static java.util.Objects.requireNonNull;

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
        
        session.getSpeedment()
            .getUserInterfaceComponent()
            .getSelectedTreeItems()
            .addListener((ListChangeListener.Change<? extends TreeItem<AbstractNodeProperty>> change) -> {
//                while (change.next()) {
//                    if (change.wasRemoved()) {
//                        properties.clear();
//                    }
//                    
//                    if (change.wasAdded()) {
//                        final TreeItem<AbstractNodeProperty> treeItem = change.getAddedSubList().get(0);
//                    
//                        if (treeItem != null) {
//                            final AbstractNodeProperty node = treeItem.getValue();
//                            node.getGuiVisibleProperties()
//                                .forEachOrdered(properties::add);
//                        }
//                    }
//                }
                properties.clear();
                
                if (!change.getList().isEmpty()) {
                    final TreeItem<AbstractNodeProperty> treeItem = change.getList().get(0);
                    
                    if (treeItem != null) {
                        final AbstractNodeProperty node = treeItem.getValue();
                        node.getGuiVisibleProperties()
                            .forEachOrdered(properties::add);
                    }
                }
            });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        final PropertySheet sheet = new PropertySheet(properties);
        
        sheet.setMode(PropertySheet.Mode.NAME);
        sheet.setModeSwitcherVisible(false);
        sheet.setSearchBoxVisible(true);
        sheet.setPropertyEditorFactory(item -> {
            if (item instanceof AbstractPropertyItem<?, ?>) {
                @SuppressWarnings("unchecked")
                final AbstractPropertyItem<?, ?> casted = (AbstractPropertyItem<?, ?>) item;
                return casted.createEditor();
            } else throw new SpeedmentException(
                "Unknown property item type '" + item.getClass() + "'."
            );
        });
        
        workspace.setContent(sheet);
    }
    
    public static Node create(UISession session) {
        return UILoader.create(session, "Workspace", WorkspaceController::new);
	}
}