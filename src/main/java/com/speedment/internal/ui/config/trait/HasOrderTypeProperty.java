package com.speedment.internal.ui.config.trait;

import com.speedment.config.db.parameters.OrderType;
import com.speedment.config.db.trait.*;
import com.speedment.internal.ui.config.DocumentProperty;
import javafx.beans.property.ObjectProperty;

/**
 *
 * @author Emil Forslund
 */
public interface HasOrderTypeProperty extends DocumentProperty {
    
    default ObjectProperty<OrderType> aliasProperty() {
        return objectPropertyOf(HasOrderType.ORDER_TYPE, OrderType.class);
    }
}