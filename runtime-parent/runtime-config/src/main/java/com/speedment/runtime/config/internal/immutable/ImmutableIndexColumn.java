/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.config.internal.immutable;

import com.speedment.runtime.config.Index;
import com.speedment.runtime.config.IndexColumn;
import com.speedment.runtime.config.internal.IndexColumnImpl;
import com.speedment.runtime.config.parameter.OrderType;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static com.speedment.runtime.config.util.DocumentUtil.toStringHelper;

/**
 *
 * @author Emil Forslund
 */
public final class ImmutableIndexColumn extends ImmutableDocument implements IndexColumn {

    private final String id;
    private final String name;
    private final int ordinalPosition;
    private final OrderType orderType;
    private final AtomicReference<ImmutableColumn> column;

    ImmutableIndexColumn(ImmutableIndex parent, Map<String, Object> ic) {
        super(parent, ic);
        final IndexColumn prototype = new IndexColumnImpl(parent, ic);
        this.id              = prototype.getId();
        this.name            = prototype.getName();
        this.ordinalPosition = prototype.getOrdinalPosition();
        this.orderType       = prototype.getOrderType();
        this.column          = new AtomicReference<>();
    }

    @Override
    public String getId() { return id; }
    
    @Override
    public String getName() { return name; }

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
        if (column.get() == null) {
            column.set(IndexColumn.super.findColumn().map(ImmutableColumn.class::cast).orElse(null));
        }
        return Optional.ofNullable(column.get());
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