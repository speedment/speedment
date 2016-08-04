/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.tool.property.item;

import com.speedment.runtime.annotation.Api;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
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
public class SimpleTextFieldItem extends BaseLabelTooltipItem {
    
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
