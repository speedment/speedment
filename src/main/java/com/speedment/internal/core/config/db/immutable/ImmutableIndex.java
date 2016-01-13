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
import com.speedment.config.db.Table;
import com.speedment.internal.core.config.db.IndexImpl;
import static java.util.Collections.unmodifiableList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public final class ImmutableIndex extends ImmutableDocument implements Index {
    
    private final boolean enabled;
    private final String name;
    private final boolean unique;
    
    private final List<ImmutableIndexColumn> indexColumns;
    
    ImmutableIndex(ImmutableTable parent, Map<String, Object> index) {
        super(parent, index);
        
        final Index prototype = new IndexImpl(parent, index);
        
        this.enabled = prototype.isEnabled();
        this.name    = prototype.getName();
        this.unique  = prototype.isUnique();
        
        this.indexColumns = unmodifiableList(Index.super.indexColumns().map(ImmutableIndexColumn.class::cast).collect(toList()));
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public boolean isUnique() {
        return unique;
    }

    @Override
    public BiFunction<Index, Map<String, Object>, ? extends IndexColumn> indexColumnConstructor() {
        return (parent, map) -> new ImmutableIndexColumn((ImmutableIndex) parent, map);
    }

    @Override
    public Stream<ImmutableIndexColumn> indexColumns() {
        return indexColumns.stream();
    }

    @Override
    public Optional<Table> getParent() {
        return super.getParent().map(Table.class::cast);
    }
}