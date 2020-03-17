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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.tool.propertyeditor.item;

import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.TextField;

import java.util.function.UnaryOperator;

/**
 * A simple editor item which will let the user edit a StringProperty via a
 * TextField
 * 
 * @author Simon Jonasson
 * @since 3.0.0
 */
public class SimpleTextFieldItem extends AbstractLabelTooltipItem {
    
    private final StringProperty property;
    
    /**
     * Creates a new SimpleTextFieldItem. The property will bind to the
     * editor node's TextField.textProperty()
     * 
     * @param label     this editor's label text
     * @param property  the property to edit
     * @param tooltip   this editor's tooltip text
     */    
    public SimpleTextFieldItem(String label, StringProperty property, String tooltip) {
        this(label, property, tooltip, NO_DECORATOR);
    }
    
    /**
     * Creates a new SimpleTextFieldItem. The property will bind to the
     * editor node's TextField.textProperty()
     * 
     * @param label      this editor's label text
     * @param property   the property to edit
     * @param tooltip    this editor's tooltip text
     * @param decorator  the editor decorator
     */    
    public SimpleTextFieldItem(String label, StringProperty property, String tooltip, UnaryOperator<Node> decorator) {
        super(label, tooltip, decorator);
        this.property = property;
    }

    @Override
    protected Node createUndecoratedEditor() {
        final TextField box = new TextField( property.get() );
        property.bindBidirectional(box.textProperty() );
        return box;
    }    
}
