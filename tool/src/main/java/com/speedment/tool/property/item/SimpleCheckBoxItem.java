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
public class SimpleCheckBoxItem extends BaseLabelTooltipItem{

    private final BooleanProperty property;
    
    public SimpleCheckBoxItem(String label, BooleanProperty property, String tooltip){
        super(label, tooltip);
        this.property = property;
    }

    @Override
    protected Node getEditorNode() {
        final CheckBox box = new CheckBox();
        box.setSelected( property.get() );        
        property.bind( box.selectedProperty() );
        box.getStyleClass().add("property-editors");
        return box;
    }    
}
