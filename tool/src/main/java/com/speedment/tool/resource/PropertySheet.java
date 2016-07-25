/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.tool.resource;

import com.speedment.tool.property.PropertyEditor;
import static java.util.Objects.requireNonNull;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Simon
 */
public class PropertySheet extends GridPane {
    private final ObservableList<PropertyEditor.Item> properties;
    
    public PropertySheet( ObservableList<PropertyEditor.Item> properties ){
        requireNonNull(properties);
        this.properties = properties == null ? FXCollections.<PropertyEditor.Item>observableArrayList() : properties;
        this.properties.addListener((ListChangeListener.Change<? extends PropertyEditor.Item> c) -> {
            
            if( c.wasAdded() ){
                System.out.println("Adde " + c.getAddedSize());
            }            
            if( c.wasRemoved() ){
                System.out.println("Removed " + c.getRemovedSize());
            }        
        });
    }    
    
    
    
}
