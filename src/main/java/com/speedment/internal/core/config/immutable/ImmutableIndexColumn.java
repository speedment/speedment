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
package com.speedment.internal.core.config.immutable;

import com.speedment.config.Column;
import com.speedment.config.Index;
import com.speedment.config.IndexColumn;
import com.speedment.config.aspects.Parent;
import com.speedment.internal.core.config.aspects.ColumnableHelper;
import com.speedment.config.parameters.OrderType;
import static com.speedment.internal.core.config.immutable.ImmutableUtil.throwNewUnsupportedOperationExceptionImmutable;
import static java.util.Objects.requireNonNull;
import java.util.Optional;

/**
 *
 * @author pemi
 */
public final class ImmutableIndexColumn extends ImmutableAbstractOrdinalConfigEntity implements IndexColumn, ColumnableHelper {

    private final Optional<Index> parent;
    private final OrderType orderType;
    private Column column;

    public ImmutableIndexColumn(Index parent, IndexColumn indexColumn) {
        super(requireNonNull(indexColumn).getName(), indexColumn.isEnabled(), indexColumn.getOrdinalPosition());
        requireNonNull(parent);
        // Fields
        this.parent = Optional.of(parent);
        this.orderType = indexColumn.getOrderType();
    }

    @Override
    public OrderType getOrderType() {
        return orderType;
    }

    @Override
    public void setOrderType(OrderType orderType) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public void setParent(Parent<?> parent) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public Optional<Index> getParent() {
        return parent;
    }

    @Override
    public Column getColumn() {
        return column;
    }

    @Override
    public void resolve() {
        this.column = ColumnableHelper.super.getColumn();
    }

}
