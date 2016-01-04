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

import com.speedment.config.Column;
import com.speedment.config.Table;
import com.speedment.config.aspects.Parent;
import com.speedment.config.mapper.TypeMapper;
import com.speedment.config.parameters.ColumnCompressionType;
import com.speedment.config.parameters.FieldStorageType;
import java.util.Optional;
import static com.speedment.internal.core.config.immutable.ImmutableUtil.throwNewUnsupportedOperationExceptionImmutable;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 */
public final class ImmutableColumn extends ImmutableAbstractOrdinalConfigEntity implements Column {

    private final boolean nullable;
    private final boolean autoincrement;
    private final Optional<String> alias;
    private final Optional<Table> parent;
    private final FieldStorageType fieldStorageType;
    private final ColumnCompressionType columnCompressionType;
    private final TypeMapper<?, ?> typeMapper;
    private final Class<?> databaseType;
    
    private final boolean exposedInRest;
    private final String restPath;

    public ImmutableColumn(Table parent, Column column) {
        super(requireNonNull(column).getName(), column.isEnabled(), column.isExpanded(), column.getOrdinalPosition());
        this.nullable = column.isNullable();
        this.autoincrement = column.isAutoincrement();
        this.alias = column.getAlias();
        this.parent = column.getParent();
        this.fieldStorageType = column.getFieldStorageType();
        this.columnCompressionType = column.getColumnCompressionType();
        this.typeMapper = column.getTypeMapper();
        this.databaseType = column.getDatabaseType();
        
        this.exposedInRest = column.isExposedInRest();
        this.restPath = column.getRestPath();
    }

    @Override
    public Optional<String> getAlias() {
        return alias;
    }

    @Override
    public void setAlias(String alias) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public FieldStorageType getFieldStorageType() {
        return fieldStorageType;
    }

    @Override
    public void setFieldStorageType(FieldStorageType fieldStorageType) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public ColumnCompressionType getColumnCompressionType() {
        return columnCompressionType;
    }

    @Override
    public void setColumnCompressionType(ColumnCompressionType columnCompressionType) {
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
    public Boolean isNullable() {
        return nullable;
    }

    @Override
    public void setNullable(Boolean nullable) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public Boolean isAutoincrement() {
        return autoincrement;
    }

    @Override
    public void setAutoincrement(Boolean autoincrement) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public TypeMapper<?, ?> getTypeMapper() {
        return typeMapper;
    }

    @Override
    public void setTypeMapper(TypeMapper<?, ?> mapper) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public void setTypeMapper(Class<?> mapper) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public Class<?> getDatabaseType() {
        return databaseType;
    }

    @Override
    public void setDatabaseType(Class<?> databaseType) {
        throwNewUnsupportedOperationExceptionImmutable();
    }
    
    @Override
    public void setExposedInRest(boolean exposed) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public boolean isExposedInRest() {
        return exposedInRest;
    }

    @Override
    public void setRestPath(String restPath) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public String getRestPath() {
        return restPath;
    }
}
