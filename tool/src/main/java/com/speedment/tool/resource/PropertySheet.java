package com.speedment.tool.resource;

import com.speedment.tool.property.PropertyEditor;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.layout.BorderPane;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Simon
 */
public final class PropertySheet extends BorderPane {
    private final ObservableList<PropertyEditor.Item> properties;
    private PropertyLayout layout;
    
    public PropertySheet( ObservableList<PropertyEditor.Item> properties ){
        requireNonNull(properties);
        
        layout = new PropertyLayout(properties);
        setCenter(layout);

        this.properties = properties;
        this.properties.addListener((ListChangeListener.Change<? extends PropertyEditor.Item> c) -> {
            while( c.next() ){
                if( c.wasRemoved() ){
                    if( properties.isEmpty() ){
                        layout = new PropertyLayout(properties);
                        setCenter(layout);
                    }
                }
                else if( c.wasAdded() ){
                    c.getAddedSubList().stream().forEachOrdered( i -> layout.addItem(i) );
                }
            }
        });
    }
}
