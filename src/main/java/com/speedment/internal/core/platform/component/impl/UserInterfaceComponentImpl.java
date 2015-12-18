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
package com.speedment.internal.core.platform.component.impl;

import com.speedment.Speedment;
import com.speedment.component.UserInterfaceComponent;
import com.speedment.internal.ui.config.AbstractNodeProperty;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeCell;
import static javafx.collections.FXCollections.observableArrayList;

/**
 *
 * @author Emil Forslund
 */
public final class UserInterfaceComponentImpl extends Apache2AbstractComponent implements UserInterfaceComponent {
    
    private final static String DEFAULT_STYLESHEET = "/css/speedment.css";
    
    private final ObservableList<Node> properties;
    private final ObservableList<Node> outputMessages;
    private final ObservableList<TreeItem<AbstractNodeProperty>> selectedTreeItems;
    private final Map<Class<?>, UserInterfaceComponent.ContextMenuBuilder<?>> contextMenuBuilders;
    private final StringProperty stylesheet;
    
    public UserInterfaceComponentImpl(Speedment speedment) {
        super(speedment);
        properties          = observableArrayList();
        outputMessages      = observableArrayList();
        selectedTreeItems   = observableArrayList();
        contextMenuBuilders = new ConcurrentHashMap<>();
        stylesheet          = new SimpleStringProperty(DEFAULT_STYLESHEET);
    }

    @Override
    public ObservableList<Node> getProperties() {
        return properties;
    }

    @Override
    public ObservableList<TreeItem<AbstractNodeProperty>> getSelectedTreeItems() {
        return selectedTreeItems;
    }

    @Override
    public ObservableList<Node> getOutputMessages() {
        return outputMessages;
    }

    @Override
    public <NODE extends AbstractNodeProperty> void installContextMenu(Class<? super NODE> nodeType, ContextMenuBuilder<NODE> menuBuilder) {
        contextMenuBuilders.put(nodeType, menuBuilder);
    }

    @Override
    public <NODE extends AbstractNodeProperty> Optional<ContextMenu> createContextMenu(TreeCell<AbstractNodeProperty> treeCell, NODE node) {
        @SuppressWarnings("unchecked")
        final UserInterfaceComponent.ContextMenuBuilder<NODE> builder = 
            (UserInterfaceComponent.ContextMenuBuilder<NODE>) 
            contextMenuBuilders.get(node.getInterfaceMainClass());
        
        if (builder == null) {
            return Optional.empty();
        } else {
            return builder.build(treeCell, node);
        }
    }

    @Override
    public String getStylesheetFile() {
        return stylesheet.getValue();
    }

    @Override
    public void setStylesheetFile(String filename) {
        this.stylesheet.setValue(filename);
    }
}