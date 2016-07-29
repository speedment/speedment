package com.speedment.tool.property;

import com.speedment.runtime.annotation.Api;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.layout.BorderPane;
import static java.util.Objects.requireNonNull;
import javafx.collections.FXCollections;

/**
 * Container for a view of editable properties
 *
 * @author Simon Jonasson
 * @since 3.0.0
 */
@Api(version="3.0")
public final class PropertySheet extends BorderPane {
    private final ObservableList<PropertyEditor.Item> properties; 
    private PropertyLayout layout;
    
    /**
     * Creates a new {@link PropertySheet} which will render a collection of properties.
     * 
     * @param properties  the list of properties to render
     */
    public PropertySheet( ObservableList<PropertyEditor.Item> properties ){
        requireNonNull(properties);
        this.properties = FXCollections.unmodifiableObservableList(properties);
        
        layout = new PropertyLayout(properties);
        setCenter(layout);
        this.properties.addListener( (ListChangeListener.Change<? extends PropertyEditor.Item> c) -> {
            while( c.next() ){
                if( c.wasRemoved() && properties.isEmpty() ){
                    layout.remove();
                    layout = new PropertyLayout(properties);
                    setCenter(layout);

                }
                else if( c.wasAdded() ){
                    c.getAddedSubList().stream().forEachOrdered( i -> layout.addItem(i) );
                }
            }
        } );
    }
}
