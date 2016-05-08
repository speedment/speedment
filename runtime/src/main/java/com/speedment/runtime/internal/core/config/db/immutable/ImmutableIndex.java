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
package com.speedment.runtime.internal.core.config.db.immutable;

import com.speedment.runtime.config.db.Index;
import com.speedment.runtime.config.db.Table;
import com.speedment.runtime.internal.core.config.db.IndexImpl;
import static java.util.Collections.unmodifiableList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public final class ImmutableIndex extends ImmutableDocument implements Index {
    
    private final transient boolean enabled;
    private final transient String name;
    private final transient boolean unique;
    
    private final transient List<ImmutableIndexColumn> indexColumns;
    
    ImmutableIndex(ImmutableTable parent, Map<String, Object> index) {
        super(parent, index);
        
        final Index prototype = new IndexImpl(parent, index);
        
        this.enabled = prototype.isEnabled();
        this.name    = prototype.getName();
        this.unique  = prototype.isUnique();
        
        this.indexColumns = unmodifiableList(super.children(INDEX_COLUMNS, ImmutableIndexColumn::new).collect(toList()));
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
    public Stream<ImmutableIndexColumn> indexColumns() {
        return indexColumns.stream();
    }

    @Override
    public Optional<Table> getParent() {
        return super.getParent().map(Table.class::cast);
    }
}