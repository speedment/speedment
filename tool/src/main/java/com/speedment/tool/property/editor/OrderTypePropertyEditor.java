package com.speedment.tool.property.editor;

import com.speedment.runtime.config.parameter.OrderType;
import com.speedment.tool.config.trait.HasOrderTypeProperty;
import com.speedment.tool.property.PropertyEditor;
import com.speedment.tool.property.item.ChoiceBoxItem;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Simon
 */
public class OrderTypePropertyEditor<T extends HasOrderTypeProperty> implements PropertyEditor<T>{
    
    @Override
    public Stream<Item> fieldsFor(T document) {
        final ObservableList<OrderType> alternatives = 
            FXCollections.observableArrayList( 
                OrderType.class.getEnumConstants() 
            );
        
        return Stream.of(
            new ChoiceBoxItem(
                "Order Type",
                document.orderTypeProperty(),
                alternatives,
                "The order in which elements will be considered."
            )
        );
    }
    
}
