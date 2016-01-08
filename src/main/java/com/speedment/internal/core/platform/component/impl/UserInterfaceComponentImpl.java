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
import com.speedment.config.db.trait.HasMainInterface;
import com.speedment.internal.ui.config.DocumentProperty;
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
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil Forslund
 */
public final class UserInterfaceComponentImpl extends Apache2AbstractComponent implements UserInterfaceComponent {
    
    private final static String DEFAULT_STYLESHEET = "/css/speedment.css";
    
    private final ObservableList<PropertySheet.Item> properties;
    private final ObservableList<Node> outputMessages;
    private final ObservableList<TreeItem<DocumentProperty>> selectedTreeItems;
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
    public ObservableList<PropertySheet.Item> getProperties() {
        return properties;
    }

    @Override
    public ObservableList<TreeItem<DocumentProperty>> getSelectedTreeItems() {
        return selectedTreeItems;
    }

    @Override
    public ObservableList<Node> getOutputMessages() {
        return outputMessages;
    }

    @Override
    public <DOC extends DocumentProperty & HasMainInterface> void installContextMenu(Class<? super DOC> nodeType, ContextMenuBuilder<DOC> menuBuilder) {
        contextMenuBuilders.put(nodeType, menuBuilder);
    }

    @Override
    public <DOC extends DocumentProperty & HasMainInterface> Optional<ContextMenu> createContextMenu(TreeCell<DocumentProperty> treeCell, DOC doc) {
        @SuppressWarnings("unchecked")
        final UserInterfaceComponent.ContextMenuBuilder<DOC> builder = 
            (UserInterfaceComponent.ContextMenuBuilder<DOC>) 
            contextMenuBuilders.get(doc.mainInterface());
        
        if (builder == null) {
            return Optional.empty();
        } else {
            return builder.build(treeCell, doc);
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