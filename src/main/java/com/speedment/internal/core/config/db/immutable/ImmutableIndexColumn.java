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
package com.speedment.internal.core.config.db.immutable;

import com.speedment.config.db.Column;
import com.speedment.config.db.Index;
import com.speedment.config.db.IndexColumn;
import com.speedment.config.db.parameters.OrderType;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author Emil Forslund
 */
public final class ImmutableIndexColumn extends ImmutableDocument implements IndexColumn {

    private final String name;
    private final OrderType orderType;
    private final Column column;

    public ImmutableIndexColumn(ImmutableIndex parent, Map<String, Object> indexColumn) {
        super(parent, indexColumn);
        
        this.name      = (String) indexColumn.get(NAME);
        this.orderType = (OrderType) indexColumn.get(ORDER_TYPE);
        this.column    = IndexColumn.super.findColumn();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public OrderType getOrderType() {
        return orderType;
    }

    @Override
    public Column findColumn() {
        return column;
    }

    @Override
    public Optional<Index> getParent() {
        return super.getParent().map(Index.class::cast);
    }
}