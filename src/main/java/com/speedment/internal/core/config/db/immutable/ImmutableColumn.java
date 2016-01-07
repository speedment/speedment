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
import com.speedment.config.db.Column;
import com.speedment.config.db.mapper.TypeMapper;

/**
 *
 * @author pemi
 */
public final class ImmutableColumn extends ImmutableDocument implements Column {

    private final boolean nullable;
    private final boolean autoincrement;
    private final String alias;
    private final String typeMapper;
    private final TypeMapper<?, ?> typeMapperObject;
    private final String databaseType;
    private final Class<?> databaseTypeObject;

    public ImmutableColumn(ImmutableTable parent, Column column) {
        super(parent, column.getData());
        this.nullable              = column.isNullable();
        this.autoincrement         = column.isAutoIncrement();
        this.alias                 = column.getAlias();
        this.typeMapper            = column.getTypeMapper();
        this.typeMapperObject      = column.findTypeMapper();
        this.databaseType          = column.getDatabaseType();
        this.databaseTypeObject    = column.findDatabaseType();
    }

    @Override
    public String getAlias() {
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
}