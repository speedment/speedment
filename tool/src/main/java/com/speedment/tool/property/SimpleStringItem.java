/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.tool.property;

import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Simon
 */
public class SimpleStringItem implements PropertyEditor.Item{

    private final String label;
    private final StringProperty property;
    
    public SimpleStringItem(String label, StringProperty property){
        this.label = label;
        this.property = property;
    }
    
    @Override
    public Node getLabel() {
        return new Label(label);
    }

    @Override
    public Node getEditor() {
        final TextField box = new TextField( property.get() );
        property.bind( box.textProperty() );
        return box;
    }    
}
