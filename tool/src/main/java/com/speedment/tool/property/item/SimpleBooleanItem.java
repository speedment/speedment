/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.tool.property.item;

import javafx.beans.property.BooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;

/**
 *
 * @author Simon
 */
public class SimpleBooleanItem extends AbstractLabelAndTooltipItem{

    private final BooleanProperty property;
    
    public SimpleBooleanItem(String label, BooleanProperty property, String tooltip){
        super(label, tooltip);
        this.property = property;
    }

    @Override
    public Node getEditor() {
        final CheckBox box = new CheckBox();
        box.setSelected( property.get() );        
        property.bind( box.selectedProperty() );
        box.getStyleClass().add("property-editors");
        return box;
    }    
}
