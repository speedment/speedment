package com.speedment.config.db.trait;

import com.speedment.config.Document;
import com.speedment.config.db.parameters.OrderType;

/**
 *
 * @author Emil Forslund
 */
public interface HasOrderType extends Document {
    
    final String ORDER_TYPE = "orderType";

    default OrderType getOrderType() {
        return get(ORDER_TYPE)
            .map(OrderType.class::cast)
            .orElse(OrderType.ASC);
    }
}