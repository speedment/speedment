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

import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.internal.ColumnImpl;
import com.speedment.runtime.config.trait.HasNullable;

import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

import static com.speedment.runtime.config.util.DocumentUtil.toStringHelper;

/**
 *
 * @author Emil Forslund
 */
public final class ImmutableColumn extends ImmutableDocument implements Column {

    private final boolean enabled;
    private final String id;
    private final String name;
    private final String alias;
    private final boolean nullable;
    private final int ordinalPosition;
    private final HasNullable.ImplementAs nullableImplementation;
    private final boolean autoincrement;
    private final String typeMapper;
    private final String databaseType;
    private final Class<?> databaseTypeObject;
    private final String enumConstants;
    private final OptionalInt decimalDigits;
    private final OptionalInt columnSize;

    ImmutableColumn(ImmutableTable parent, Map<String, Object> data) {
        super(parent, data);
        
        final Column prototype = new ColumnImpl(parent, data);
        
        this.enabled                = prototype.isEnabled();
        this.id                     = prototype.getId();
        this.name                   = prototype.getName();
        this.alias                  = prototype.getAlias().orElse(null);
        this.nullable               = prototype.isNullable();
        this.nullableImplementation = prototype.getNullableImplementation();
        this.autoincrement          = prototype.isAutoIncrement();
        this.typeMapper             = prototype.getTypeMapper().orElse(null);
        this.databaseType           = prototype.getDatabaseType();
        this.databaseTypeObject     = prototype.findDatabaseType();
        this.enumConstants          = prototype.getEnumConstants().orElse(null);
        this.decimalDigits          = prototype.getDecimalDigits();
        this.columnSize             = prototype.getColumnSize();
        this.ordinalPosition        = prototype.getOrdinalPosition();
    }

    @Override
    public boolean isEnabled() {
        return enabled;
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
    public Optional<String> getAlias() {
        return Optional.ofNullable(alias);
    }

    @Override
    public boolean isNullable() {
        return nullable;
    }

    @Override
    public ImplementAs getNullableImplementation() {
        return nullableImplementation;
    }

    @Override
    public int getOrdinalPosition() {
        return ordinalPosition;
    }

    @Override
    public boolean isAutoIncrement() {
        return autoincrement;
    }

    @Override
    public Optional<String> getTypeMapper() {
        return Optional.ofNullable(typeMapper);
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
    public Optional<String> getEnumConstants() {
        return Optional.ofNullable(enumConstants);
    }

    @Override
    public OptionalInt getDecimalDigits() {
        return decimalDigits;
    }

    @Override
    public OptionalInt getColumnSize() {
        return columnSize;
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