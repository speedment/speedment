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
package com.speedment.tool.propertyeditor;

import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * GridPane which takes responsibility for the layout of editor items.
 *
 * @author Simon Jonasson
 * @since 3.0.0
 */
final class PropertyLayout extends GridPane {
    private static final int MIN_LABEL_WIDTH = 150;
    private static final int MIN_EDITOR_WIDTH = 450;
    private final AtomicInteger index;
    private final Set<PropertyEditor.Item> items;

    /**
     * Creates a new GridPane, with proper column layout, and adds the editor items
     * in order
     * 
     * @param properties  the properties which should be rendered
     */
    PropertyLayout(ObservableList<PropertyEditor.Item> properties){
        this.index = new AtomicInteger(0);
        this.items = new HashSet<>();
        
        getColumnConstraints().add(0, new ColumnConstraints(MIN_LABEL_WIDTH,  USE_COMPUTED_SIZE, USE_PREF_SIZE,    Priority.NEVER,  HPos.LEFT, true));
        getColumnConstraints().add(1, new ColumnConstraints(MIN_EDITOR_WIDTH, USE_COMPUTED_SIZE, Double.MAX_VALUE, Priority.ALWAYS, HPos.LEFT, true));
        getStyleClass().add("properties-layout");

        properties.stream().forEachOrdered(this::addItem);
    }

    /**
     * Adds a new editor item to the layout. New items are added at the
     * bottom.
     * 
     * @param item  the item
     */
    void addItem(PropertyEditor.Item item){  
        final Node label = item.createLabel();        
        label.getStyleClass().add("property-label");
        GridPane.setValignment(label, VPos.TOP);
        GridPane.setHalignment(label, HPos.RIGHT);
        
        final Node editor = item.createEditor();
        editor.getStyleClass().add("property-editor");
        GridPane.setValignment(editor, VPos.CENTER);
        
        addRow(index.getAndIncrement(), label, editor);
        items.add(item);
    }

    void remove() {
        items.forEach(PropertyEditor.Item::onRemove);
        getChildren().clear();
    }
}
