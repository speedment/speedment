/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.tool.property.item;

import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;

/**
 *
 * @author Simon
 */
public class ChoiceBoxItem<T> extends AbstractLabelAndTooltipItem{
    
    private final Property<T> currentValue;
    private final ObservableList<T> alternatives;
    
    public ChoiceBoxItem(String label, Property<T> currentValue, ObservableList<T> alternatives, String tooltip){
        super(label, tooltip);
        this.currentValue = currentValue;
        this.alternatives = FXCollections.unmodifiableObservableList(alternatives);
    }

    @Override
    public Node getEditor() {
        final ChoiceBox<T> box = new ChoiceBox<>(alternatives);
        final T val = currentValue.getValue();
        
        if( alternatives.contains( val )){
            box.setValue( val );
        } else {
            box.setValue( alternatives.get(0) );
        }
        
        currentValue.bind( box.valueProperty() );
        box.getStyleClass().add("property-editors");
        return box;
    }
    
}
