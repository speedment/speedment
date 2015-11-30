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
import com.speedment.internal.gui.config.AbstractNodeProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import static javafx.collections.FXCollections.observableArrayList;

/**
 *
 * @author Emil Forslund
 */
public final class UserInterfaceComponentImpl extends Apache2AbstractComponent implements UserInterfaceComponent {
    
    private final ObservableList<Node> properties;
    private final ObservableList<TreeItem<AbstractNodeProperty>> currentSelection;
    private final ObservableList<Node> outputMessages;
    
    public UserInterfaceComponentImpl(Speedment speedment) {
        super(speedment);
        properties       = observableArrayList();
        currentSelection = observableArrayList();
        outputMessages   = observableArrayList();
    }

    @Override
    public ObservableList<Node> getProperties() {
        return properties;
    }
    
    @Override
    public ObservableList<TreeItem<AbstractNodeProperty>> getCurrentSelection() {
        return currentSelection;
    }

    @Override
    public ObservableList<Node> getOutputMessages() {
        return outputMessages;
    }
}