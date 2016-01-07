package com.speedment.internal.core.config.db.mutator.trait;

import com.speedment.config.db.trait.*;
import com.speedment.config.db.parameters.OrderType;
import com.speedment.internal.core.config.db.mutator.DocumentMutator;

/**
 *
 * @author Per Minborg
 */
public interface HasOrderTypeMutator extends DocumentMutator {
    
    default void getOrderType(OrderType orderType) {
         put(HasOrderType.ORDER_TYPE, orderType);
    }
}