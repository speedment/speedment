package com.speedment.config.db.trait;

import com.speedment.annotation.Api;
import com.speedment.config.Document;
import com.speedment.config.db.parameters.OrderType;

/**
 *
 * @author Emil Forslund
 */
@Api(version = "2.3")
public interface HasOrderType extends Document {
    
    final String ORDER_TYPE = "orderType";

    default OrderType getOrderType() {
        return get(ORDER_TYPE)
            .map(OrderType.class::cast)
            .orElse(OrderType.ASC);
    }
}