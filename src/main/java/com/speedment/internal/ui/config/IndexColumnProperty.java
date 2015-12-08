/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.ui.config;

import com.speedment.Speedment;
import com.speedment.config.Column;
import com.speedment.config.Index;
import com.speedment.config.IndexColumn;
import com.speedment.config.Table;
import com.speedment.config.aspects.Parent;
import com.speedment.config.parameters.OrderType;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.ui.property.EnumPropertyItem;
import java.util.Optional;
import java.util.stream.Stream;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil Forslund
 */
public final class IndexColumnProperty extends AbstractNodeProperty implements IndexColumn, ChildHelper<IndexColumn, Index> {
    
    private final Property<OrderType> orderType;
    
    private Index parent;
    private int ordinalPosition;
    
    public IndexColumnProperty(Speedment speedment) {
        super(speedment);
        orderType = new SimpleObjectProperty<>();
    }
    
    public IndexColumnProperty(Speedment speedment, Index parent, IndexColumn prototype) {
        super(speedment, prototype);
        orderType       = new SimpleObjectProperty<>(prototype.getOrderType());
        ordinalPosition = prototype.getOrdinalPosition();
        this.parent = parent;
    }
    
    @Override
    protected Stream<PropertySheet.Item> guiVisibleProperties() {
        return Stream.of(
            new EnumPropertyItem<>(
                OrderType.class,
                orderType,
                "Ordering",
                "The default ordering to use for this index."
            )
        );
    }
    
    @Override
    public Optional<Index> getParent() {
        return Optional.ofNullable(parent);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setParent(Parent<?> parent) {
        if (parent instanceof Index) {
            this.parent = (Index) parent;
        } else {
            throw wrongParentClass(parent.getClass());
        }
    }

    @Override
    public void setOrderType(OrderType orderType) {
        this.orderType.setValue(orderType);
    }

    @Override
    public OrderType getOrderType() {
        return orderType.getValue();
    }
    
    public Property<OrderType> orderTypeProperty() {
        return orderType;
    }

    @Override
    public void setOrdinalPosition(int ordinalPosition) {
        this.ordinalPosition = ordinalPosition;
    }

    @Override
    public int getOrdinalPosition() {
        return ordinalPosition;
    }
    
    @Override
    public Column getColumn() {
        return ancestor(Table.class)
            .orElseThrow(() -> new SpeedmentException(
                "Found no ancestor table from this "
                + getClass().getSimpleName() + "."
            )).findColumn(getName());
    }
}