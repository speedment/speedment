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

import javafx.scene.Node;

import java.util.stream.Stream;

/**
 * Base interface for all property editors
 *
 * @param <T>  the document type
 * 
 * @author  Simon Jonasson
 * @since   3.0.0
 */
public interface PropertyEditor<T> {
    
    /**
     * Used for rendering a property editor and related explanation label
     */
    interface Item {
        /**
         * Should return the JavaFX node which will be located in the left column
         * of the {@link PropertyLayout}. This can normally be just a JavaFX.Label 
         * 
         * @return  the JavaFX node used for describing the edited property
         */
        Node createLabel();
        
        /**
         * Should return the JavaFX node which will be located in the right column
         * of the {@link PropertyLayout}. This can be anything from a simple JavaFX.TextField
         * to a complex editor.
         * 
         * @return  the JavaFX node used for editing the property 
         */
        Node createEditor();
        
        /**
         * A method that will be called when this item is removed from the {@link PropertyLayout}
         */
        void onRemove();
    }
    
    /**
     * Retrieves a stream of {@link PropertyEditor.Item} for editing this property
     * 
     * @param document the document
     * @return         stream of {@link PropertyEditor.Item} 
     */
    Stream<PropertyEditor.Item> fieldsFor(T document);
}
