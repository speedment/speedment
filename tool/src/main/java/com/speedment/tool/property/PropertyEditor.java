package com.speedment.tool.property;

import java.util.stream.Stream;
import javafx.scene.Node;
import org.controlsfx.control.PropertySheet;

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
    
    
    String getPropertyKey();
}
