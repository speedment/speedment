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
package com.speedment.tool.propertyeditor.item;

import javafx.beans.property.BooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;

import java.util.function.UnaryOperator;

/**
 * A simple editor which will let the user edit a BooleanProperty via a
 * CheckBox. 
 * <p>
 * The CheckBox will default to the current value of the property. If no such value
 * exists, it will default to false. The property will bind to CheckBox.selectedProperty()
 *
 * @author Simon Jonasson
 * @since 3.0.0
 */
public class SimpleCheckBoxItem extends AbstractLabelTooltipItem{

    private final BooleanProperty property;
    
    /**
     * Creates a new SimpleCheckBoxItem. The property will bind to the
     * editor node's CheckBox.selectedProperty()
     * 
     * @param label     the label text
     * @param property  the property to edit
     * @param tooltip   the tooltip text
     */ 
    public SimpleCheckBoxItem(String label, BooleanProperty property, String tooltip){
        this(label, property, tooltip, NO_DECORATOR);
    }
    
    /**
     * Creates a new SimpleCheckBoxItem. The property will bind to the
     * editor node's CheckBox.selectedProperty()
     * 
     * @param label      the label text
     * @param property   the property to edit
     * @param tooltip    the tooltip text
     * @param decorator  the editor decorator
     */ 
    public SimpleCheckBoxItem(
            String label, 
            BooleanProperty property, 
            String tooltip, 
            UnaryOperator<Node> decorator) {
        
        super(label, tooltip, decorator);
        this.property = property;
    }

    @Override
    protected Node createUndecoratedEditor() {
        final CheckBox box = new CheckBox();
        box.setSelected( property.get() );        
        property.bindBidirectional(box.selectedProperty() );
        return box;
    }    
}
