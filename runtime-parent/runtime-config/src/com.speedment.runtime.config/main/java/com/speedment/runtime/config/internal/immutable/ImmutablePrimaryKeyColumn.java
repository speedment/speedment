/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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

import com.speedment.common.lazy.LazyReference;
import com.speedment.runtime.config.PrimaryKeyColumn;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.internal.PrimaryKeyColumnImpl;

import java.util.Map;
import java.util.Optional;

import static com.speedment.runtime.config.util.DocumentUtil.toStringHelper;

/**
 *
 * @author Emil Forslund
 */
public final class ImmutablePrimaryKeyColumn extends ImmutableDocument implements PrimaryKeyColumn {

    private final transient String id;
    private final transient String name;
    private final transient int ordinalPosition;
    
    private final transient LazyReference<Optional<ImmutableColumn>> column;

    ImmutablePrimaryKeyColumn(ImmutableTable parent, Map<String, Object> pkc) {
        super(parent, pkc);
        
        final PrimaryKeyColumn prototype = new PrimaryKeyColumnImpl(parent, pkc);
        
        this.id              = prototype.getId();
        this.name            = prototype.getName();
        this.ordinalPosition = prototype.getOrdinalPosition();        
        this.column          = LazyReference.create();
    }

    @Override
    public Optional<Table> getParent() {
        return super.getParent().map(Table.class::cast);
    }

    @Override
    public String getId() {
        return id;
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
    public Optional<ImmutableColumn> findColumn() {
        return column.getOrCompute(() ->
            PrimaryKeyColumn.super.findColumn()
                .map(ImmutableColumn.class::cast)
        );
    }
    
    @Override
    public String toString() {
        return toStringHelper(this);
    } 
    
}