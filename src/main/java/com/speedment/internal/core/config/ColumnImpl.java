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
package com.speedment.internal.core.config;

import com.speedment.config.Column;
import com.speedment.config.Table;
import com.speedment.config.aspects.Parent;
import com.speedment.config.mapper.TypeMapper;
import com.speedment.config.parameters.ColumnCompressionType;
import com.speedment.config.parameters.FieldStorageType;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.core.config.mapper.identity.StringIdentityMapper;
import com.speedment.internal.util.Cast;
import java.util.Optional;

/**
 *
 * @author pemi
 */
public final class ColumnImpl extends AbstractOrdinalNode implements Column {

    private boolean nullable;
    private boolean autoincrement;
    private String alias;
    private Table parent;
    private FieldStorageType fieldStorageType;
    private ColumnCompressionType columnCompressionType;
    private TypeMapper<?, ?> typeMapper;
    private Class<?> databaseType;
    
    private boolean exposedInRest;
    private String restPath;

    @Override
    protected void setDefaults() {
        setNullable(true);
        setAutoincrement(false);
        setFieldStorageType(FieldStorageType.INHERIT);
        setColumnCompressionType(ColumnCompressionType.INHERIT);
        setTypeMapper(new StringIdentityMapper());
        setDatabaseType(Object.class);
    }

    @Override
    public Optional<String> getAlias() {
        return Optional.ofNullable(alias);
    }

    @Override
    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public FieldStorageType getFieldStorageType() {
        return fieldStorageType;
    }

    @Override
    public void setFieldStorageType(FieldStorageType fieldStorageType) {
        this.fieldStorageType = fieldStorageType;
    }

    @Override
    public ColumnCompressionType getColumnCompressionType() {
        return columnCompressionType;
    }

    @Override
    public void setColumnCompressionType(ColumnCompressionType columnCompressionType) {
        this.columnCompressionType = columnCompressionType;
    }

    @Override
    public void setParent(Parent<?> parent) {
        this.parent = Cast.castOrFail(parent, Table.class);
    }

    @Override
    public Optional<Table> getParent() {
        return Optional.ofNullable(parent);
    }

    @Override
    public Boolean isNullable() {
        return nullable;
    }

    @Override
    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }

    @Override
    public Boolean isAutoincrement() {
        return autoincrement;
    }

    @Override
    public void setAutoincrement(Boolean autoincrement) {
        this.autoincrement = autoincrement;
    }

    @Override
    public TypeMapper<?, ?> getTypeMapper() {
        return typeMapper;
    }

    @Override
    public void setTypeMapper(TypeMapper<?, ?> mapper) {
        this.typeMapper = mapper;
    }

    @Override
    public void setTypeMapper(Class<?> mapper) {
        if (mapper == null) {
            this.typeMapper = null;
        } else {
            try {
                setTypeMapper((TypeMapper<?, ?>) mapper.newInstance());
            } catch (InstantiationException | IllegalAccessException ex) {
                throw new SpeedmentException(
                    "Could not instantiate the specified mapper '" + 
                    mapper.getName() + 
                    "' using it's default constructor."
                );
            }
        }
    }

    @Override
    public void setDatabaseType(Class<?> databaseType) {
        this.databaseType = databaseType;
    }

    @Override
    public Class<?> getDatabaseType() {
        return databaseType;
    }

    @Override
    public void setExposedInRest(boolean exposed) {
        this.exposedInRest = exposed;
    }

    @Override
    public boolean isExposedInRest() {
        return exposedInRest;
    }

    @Override
    public void setRestPath(String restPath) {
        this.restPath = restPath;
    }

    @Override
    public String getRestPath() {
        return restPath;
    }
}