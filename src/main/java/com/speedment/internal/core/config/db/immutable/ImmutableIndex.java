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

import com.speedment.config.db.Index;
import com.speedment.config.db.IndexColumn;
import com.speedment.config.db.Table;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 *
 * @author Emil Forslund
 */
public final class ImmutableIndex extends ImmutableDocument implements Index {
    
    private final boolean unique;
    
    public ImmutableIndex(ImmutableTable parent, Map<String, Object> index) {
        super(parent, index);

        this.unique = (boolean) index.get(UNIQUE);
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
    public Optional<Table> getParent() {
        return super.getParent().map(Table.class::cast);
    }
}