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
import com.speedment.config.db.Table;
import com.speedment.config.db.mapper.TypeMapper;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author Emil Forslund
 */
public final class ImmutableColumn extends ImmutableDocument implements Column {

    private final boolean enabled;
    private final String name;
    private final boolean nullable;
    private final boolean autoincrement;
    private final Optional<String> alias;
    private final String typeMapper;
    private final TypeMapper<?, ?> typeMapperObject;
    private final String databaseType;
    private final Class<?> databaseTypeObject;

    public ImmutableColumn(ImmutableTable parent, Map<String, Object> column) {
        super(parent, column);
        
        this.enabled            = (boolean) column.get(ENABLED);
        this.name               = (String) column.get(NAME);
        this.nullable           = (boolean) column.get(NULLABLE);
        this.autoincrement      = (boolean) column.get(AUTO_INCREMENT);
        this.alias              = Optional.ofNullable((String) column.get(ALIAS));
        this.typeMapper         = (String) column.get(TYPE_MAPPER);
        this.databaseType       = (String) column.get(DATABASE_TYPE);
        this.typeMapperObject   = Column.super.findTypeMapper();
        this.databaseTypeObject = Column.super.findDatabaseType();
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
}