/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.tool.property;

import javafx.beans.property.BooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

/**
 *
 * @author Simon
 */
public class SimpleBooleanItem implements PropertyEditor.Item{

    private final String label;
    private final BooleanProperty property;
    
    public SimpleBooleanItem(String label, BooleanProperty property){
        this.label = label;
        this.property = property;
    }
    
    @Override
    public Node getLabel() {
        return new Label(label);
    }

    @Override
    public Node getEditor() {
        final CheckBox box = new CheckBox();
        box.setSelected( property.get() );        
        property.bind( box.selectedProperty() );
        return box;
    }    
}
