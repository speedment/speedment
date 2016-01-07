package com.speedment.internal.ui.config.trait;

import com.speedment.config.db.parameters.OrderType;
import com.speedment.config.db.trait.*;
import com.speedment.internal.ui.config.DocumentProperty;
import com.speedment.internal.ui.property.EnumPropertyItem;
import java.util.stream.Stream;
import javafx.beans.property.ObjectProperty;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil Forslund
 */
public interface HasOrderTypeProperty extends DocumentProperty, HasUiVisibleProperties {
    
    default ObjectProperty<OrderType> orderTypeProperty() {
        return objectPropertyOf(HasOrderType.ORDER_TYPE, OrderType.class);
    }

    @Override
    default Stream<PropertySheet.Item> getUiVisibleProperties() {
        return Stream.of(
            new EnumPropertyItem<>(
                OrderType.class,
                orderTypeProperty(),
                "Order Type",
                "The order in which elements will be considered."
            )
        );
    }
}