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

import static java.util.Objects.requireNonNull;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.layout.BorderPane;

/**
 * Container for a view of editable properties
 *
 * @author Simon Jonasson
 * @since  3.0.0
 */
public final class PropertySheet extends BorderPane {
    
    private final ObservableList<PropertyEditor.Item> properties; 
    private PropertyLayout layout;
    
    /**
     * Creates a new {@link PropertySheet} which will render a collection of 
     * properties.
     * 
     * @param properties  the list of properties to render
     */
    public PropertySheet(ObservableList<PropertyEditor.Item> properties) {
        requireNonNull(properties);
        
        this.properties = FXCollections.unmodifiableObservableList(properties);
        
        layout = new PropertyLayout(properties);
        setCenter(layout);
        
        this.properties.addListener((ListChangeListener.Change<? extends PropertyEditor.Item> c) -> {
            while (c.next()) {
                if (c.wasRemoved() && properties.isEmpty()) {
                    layout.remove();
                    layout = new PropertyLayout(properties);
                    setCenter(layout);
                } else if (c.wasAdded()) {
                    c.getAddedSubList().stream()
                        .forEachOrdered(i -> layout.addItem(i));
                }
            }
        });
    }
}