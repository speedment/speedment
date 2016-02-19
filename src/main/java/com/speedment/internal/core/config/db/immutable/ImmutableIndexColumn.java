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
package com.speedment.internal.core.config.db.immutable;

import com.speedment.config.db.Index;
import com.speedment.config.db.IndexColumn;
import com.speedment.config.db.parameters.OrderType;
import com.speedment.internal.core.config.db.IndexColumnImpl;
import com.speedment.internal.util.Lazy;
import static com.speedment.internal.util.document.DocumentUtil.toStringHelper;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author Emil Forslund
 */
public final class ImmutableIndexColumn extends ImmutableDocument implements IndexColumn {

    private final transient String name;
    private final transient int ordinalPosition;
    private final transient OrderType orderType;
    private final transient Lazy<Optional<ImmutableColumn>> column;

    ImmutableIndexColumn(ImmutableIndex parent, Map<String, Object> ic) {
        super(parent, ic);
        
        final IndexColumn prototype = new IndexColumnImpl(parent, ic);
        
        this.name            = prototype.getName();
        this.ordinalPosition = prototype.getOrdinalPosition();
        this.orderType       = prototype.getOrderType();
        
        this.column          = Lazy.create();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getOrdinalPosition() {
        return ordinalPosition;
    }

    @Override
    public OrderType getOrderType() {
        return orderType;
    }

    @Override
    public Optional<ImmutableColumn> findColumn() {
        return column.getOrCompute(() ->
            IndexColumn.super.findColumn()
                .map(ImmutableColumn.class::cast)
        );
    }

    @Override
    public Optional<Index> getParent() {
        return super.getParent().map(Index.class::cast);
    }
    
    @Override
    public String toString() {
        return toStringHelper(this);
    } 
    
}