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

import com.speedment.config.ImmutableDocument;
import com.speedment.config.db.ForeignKey;
import com.speedment.config.db.ForeignKeyColumn;
import com.speedment.config.db.Table;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 *
 * @author pemi
 */
public final class ImmutableForeignKey extends ImmutableDocument implements ForeignKey {

    private final String name;
    private final boolean enabled;
    
    public ImmutableForeignKey(ImmutableTable parent, ForeignKey foreignKey) {
        super(parent, unmodifiableMap(foreignKey.getData()));
        this.name    = foreignKey.getName();
        this.enabled = foreignKey.isEnabled();
    }
    
    public ImmutableForeignKey(ImmutableTable parent, Map<String, Object> data) {
        super(parent, unmodifiableMap(data));
        this.name    = (String) data.get(NAME);
        this.enabled = (Boolean) data.get(ENABLED);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public BiFunction<ForeignKey, Map<String, Object>, ? extends ForeignKeyColumn> foreignKeyColumnConstructor() {
        return ImmutableForeignKeyColumn::new;
    }
    
    @Override
    public Optional<Table> getParent() {
        return super.getParent().map(Table.class::cast);
    }
}
