/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.internal.ui.config.trait;

import com.speedment.Speedment;
import com.speedment.config.db.parameters.OrderType;
import com.speedment.config.db.trait.*;
import com.speedment.internal.ui.config.DocumentProperty;
import com.speedment.internal.ui.property.EnumPropertyItem;
import java.util.stream.Stream;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.util.StringConverter;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil Forslund
 */
public interface HasOrderTypeProperty extends DocumentProperty, HasOrderType {
    
    default ObjectProperty<OrderType> orderTypeProperty() {
        final String defaultValue = HasOrderType.super.getOrderType().name();
        final StringProperty strProperty = stringPropertyOf(HasOrderType.ORDER_TYPE, () -> defaultValue);
        final ObjectProperty<OrderType> objProperty = new SimpleObjectProperty<>(OrderType.valueOf(defaultValue));
        
        strProperty.bindBidirectional(objProperty, new StringConverter<OrderType>() {
            @Override
            public String toString(OrderType object) {
                return object.name();
            }

            @Override
            public OrderType fromString(String string) {
                return OrderType.valueOf(string);
            }
        });
        
        return objProperty;
    }

    @Override
    default Stream<PropertySheet.Item> getUiVisibleProperties(Speedment speedment) {
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