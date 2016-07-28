/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.tool.property.item;

import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.TextField;

/**
 *
 * @author Simon
 */
public class SimpleStringItem extends AbstractLabelAndTooltipItem{

    private final StringProperty property;
    
    public SimpleStringItem(String label, StringProperty property, String tooltip){
        super(label, tooltip);
        this.property = property;
    }

    @Override
    public Node getEditor() {
        final TextField box = new TextField( property.get() );
        property.bind( box.textProperty() );
        box.getStyleClass().add("property-editors");
        return box;
    }    
}
