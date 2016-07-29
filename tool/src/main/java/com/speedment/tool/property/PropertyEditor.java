package com.speedment.tool.property;

import com.speedment.runtime.annotation.Api;
import java.util.stream.Stream;
import javafx.scene.Node;

/**
 * Base interface for all property editors
 *
 * @author Simon Jonasson
 * @param <T>  the document type
 * @since 3.0.0
 */
@Api(version="3.0")
public interface PropertyEditor<T> {
    
    /**
     * Used for rendering a property editor and related explanation label
     */
    interface Item{
        /**
         * Should return the JavaFX node which will be located in the left column
         * of the {@link PropertyLayout}. This can normally be just a JavaFX.Label 
         * 
         * @return  the JavaFX node used for describing the edited property
         */
        Node getLabel();
        
        /**
         * Should return the JavaFX node which will be located in the right column
         * of the {@link PropertyLayout}. This can be anything from a simple JavaFX.TextField
         * to a complex editor.
         * 
         * @return  the JavaFX node used for editing the property 
         */
        Node getEditor();
        
        /**
         * A method that will be called when this item is removed from the {@link PropertyLayout}
         */
        void onRemove();
    }
    
    /**
     * Retrieves a stream of {@link PropertyEditor.Item} for editing this property
     * 
     * @param document the document
     * @return         stream of {@link PropertyEditor.Item} 
     */
    Stream<PropertyEditor.Item> fieldsFor(T document);
}
