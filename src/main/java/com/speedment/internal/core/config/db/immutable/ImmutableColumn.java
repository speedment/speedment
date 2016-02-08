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

import com.speedment.config.db.Column;
import com.speedment.config.db.Table;
import com.speedment.config.db.mapper.TypeMapper;
import com.speedment.internal.core.config.db.ColumnImpl;
import static com.speedment.internal.util.document.DocumentUtil.toStringHelper;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author Emil Forslund
 */
public final class ImmutableColumn extends ImmutableDocument implements Column {

    private final transient boolean enabled;
    private final transient String name;
    private final transient Optional<String> alias;
    private final transient boolean nullable;
    private final transient boolean autoincrement;
    private final transient String typeMapper;
    private final transient String databaseType;
    private final transient TypeMapper<?, ?> typeMapperObject;
    private final transient Class<?> databaseTypeObject;

    ImmutableColumn(ImmutableTable parent, Map<String, Object> data) {
        super(parent, data);
        
        final Column prototype = new ColumnImpl(parent, data);
        
        this.enabled            = prototype.isEnabled();
        this.name               = prototype.getName();
        this.alias              = prototype.getAlias();
        this.nullable           = prototype.isNullable();
        this.autoincrement      = prototype.isAutoIncrement();
        this.typeMapper         = prototype.getTypeMapper();
        this.databaseType       = prototype.getDatabaseType();
        this.typeMapperObject   = prototype.findTypeMapper();
        this.databaseTypeObject = prototype.findDatabaseType();
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
    public Optional<String> getAlias() {
        return alias;
    }

    @Override
    public boolean isNullable() {
        return nullable;
    }

    @Override
    public boolean isAutoIncrement() {
        return autoincrement;
    }

    @Override
    public String getTypeMapper() {
        return typeMapper;
    }

    @Override
    public TypeMapper<?, ?> findTypeMapper() {
        return typeMapperObject;
    }

    @Override
    public String getDatabaseType() {
        return databaseType;
    }

    @Override
    public Class<?> findDatabaseType() {
        return databaseTypeObject;
    }

    @Override
    public Optional<Table> getParent() {
        return super.getParent().map(Table.class::cast);
    }
    
    @Override
    public String toString() {
        return toStringHelper(this);
    }
    
}