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
package com.speedment.tool.core.event;

import com.speedment.generator.core.event.Event;
import com.speedment.tool.config.DocumentProperty;
import com.speedment.tool.propertyeditor.PropertyEditor;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import static java.util.Objects.requireNonNull;

/**
 * An event that is published when the user selects a new span
 * of nodes in the "Project Tree" section. This can be used to
 * add additional items to the workspace from a plugin.
 * 
 * @author  Emil Forslund
 * @since   2.2.0
 */
public final class TreeSelectionChange implements Event {

    private final ListChangeListener.Change<TreeItem<DocumentProperty>> changeEvent;
    private final ObservableList<PropertyEditor.Item> properties;

    public TreeSelectionChange(
        final ListChangeListener.Change<TreeItem<DocumentProperty>> changeEvent,
        final ObservableList<PropertyEditor.Item> properties
    ) {
        this.changeEvent = requireNonNull(changeEvent);
        this.properties  = requireNonNull(properties);
    }
    
    public ListChangeListener.Change<TreeItem<DocumentProperty>> changeEvent() {
        return changeEvent;
    }
    
    public ObservableList<PropertyEditor.Item> properties() {
        return properties;
    }
}