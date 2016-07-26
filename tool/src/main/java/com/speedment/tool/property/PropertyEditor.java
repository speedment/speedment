package com.speedment.tool.property;

import java.util.stream.Stream;
import javafx.scene.Node;

/**
 *
 * @author Simon
 * @param <T>  the document property type
 */
public interface PropertyEditor<T> {
    
    interface Item{
        Node getLabel();
        Node getEditor();
    }
    
    Stream<PropertyEditor.Item> fieldsFor(T document);
    
    String getPropertyKey();
}
