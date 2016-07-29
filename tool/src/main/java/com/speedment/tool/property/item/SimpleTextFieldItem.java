/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.tool.property.item;

import com.speedment.runtime.annotation.Api;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.TextField;

/**
 * A simple editor item which will let the user edit a StringProperty via a
 * TextField
 * 
 * @author Simon Jonasson
 * @since 3.0.0
 */
@Api(version="3.0")
public class SimpleTextFieldItem extends BaseLabelTooltipItem{
    private final StringProperty property;
    
    /**
     * Creates a new SimpleTextFieldItem. The property will bind to the
     * editor node's TextField.textProperty()
     * 
     * @param label     this editor's label text
     * @param property  the property to edit
     * @param tooltip   this editor's tooltip text
     */    
    public SimpleTextFieldItem(String label, StringProperty property, String tooltip){
        super(label, tooltip);
        this.property = property;
    }

    @Override
    public Node getEditor() {
        final TextField box = new TextField( property.get() );
        property.bindBidirectional(box.textProperty() );
        return box;
    }    
}
