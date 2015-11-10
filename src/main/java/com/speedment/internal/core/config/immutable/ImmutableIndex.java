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

import com.speedment.internal.core.config.*;
import com.speedment.config.Index;
import com.speedment.config.IndexColumn;
import com.speedment.config.Table;
import com.speedment.config.aspects.Parent;
import static com.speedment.internal.core.config.immutable.ImmutableUtil.throwNewUnsupportedOperationExceptionImmutable;
import groovy.lang.Closure;
import static java.util.Objects.requireNonNull;
import java.util.Optional;

/**
 *
 * @author pemi
 */
public final class ImmutableIndex extends ImmutableAbstractNamedConfigEntity implements Index, ImmutableParentHelper<IndexColumn> {
    
    private final Optional<Table> parent;
    private final ChildHolder children;
    private final Boolean unique;
    
    public ImmutableIndex(Table parent, Index index) {
        super(requireNonNull(index).getName(), index.isEnabled());
        // Fields
        this.parent = Optional.of(parent);
        this.unique = index.isUnique();
        // Children
        this.children = childHolderOf(index.stream().map(ic -> new ImmutableIndexColumn(this, ic)));
    }
    
    @Override
    public Boolean isUnique() {
        return unique;
    }
    
    @Override
    public void setUnique(Boolean unique) {
        throwNewUnsupportedOperationExceptionImmutable();
    }
    
    @Override
    public void setParent(Parent<?> parent) {
        throwNewUnsupportedOperationExceptionImmutable();
    }
    
    @Override
    public Optional<Table> getParent() {
        return parent;
    }
    
    @Override
    public ChildHolder getChildren() {
        return children;
    }
    
    @Override
    public IndexColumn addNewIndexColumn() {
        return throwNewUnsupportedOperationExceptionImmutable();
    }
    
    @Override
    public IndexColumn indexColumn(Closure<?> c) {
        return throwNewUnsupportedOperationExceptionImmutable();
    }
}
