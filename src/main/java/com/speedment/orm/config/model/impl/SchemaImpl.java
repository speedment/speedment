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
package com.speedment.orm.config.model.impl;

import com.speedment.orm.config.model.*;
import com.speedment.orm.config.model.aspects.Parent;
import com.speedment.orm.config.model.parameters.ColumnCompressionType;
import com.speedment.orm.config.model.parameters.FieldStorageType;
import com.speedment.orm.config.model.parameters.StorageEngineType;
import java.util.Optional;

/**
 *
 * @author pemi
 */
public class SchemaImpl extends AbstractNamedConfigEntity implements Schema {

    private Dbms parent;
    private final ChildHolder children;
    private boolean defaultSchema;
    private String schemaName;
    private String catalogName;
    private FieldStorageType fieldStorageType;
    private ColumnCompressionType columnCompressionType;
    private StorageEngineType storageEngineType;

    public SchemaImpl() {
        children = new ChildHolder();
    }

    @Override
    protected void setDefaults() {
        setFieldStorageType(FieldStorageType.defaultFor(this));
        setColumnCompressionType(ColumnCompressionType.defaultFor(this));
        setStorageEngineType(StorageEngineType.defaultFor(this));
    }

    @Override
    public Boolean isDefaultSchema() {
        return defaultSchema;
    }

    @Override
    public void setDefaultSchema(Boolean defaultSchema) {
        this.defaultSchema = defaultSchema;
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
    public StorageEngineType getStorageEngineType() {
        return storageEngineType;
    }

    @Override
    public void setStorageEngineType(StorageEngineType storageEngineType) {
        this.storageEngineType = storageEngineType;
    }

    @Override
    public Optional<String> getCatalogName() {
        return Optional.ofNullable(catalogName);
    }

    @Override
    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    @Override
    public Optional<String> getSchemaName() {
        if (schemaName == null) {
            return Optional.ofNullable(getName());
        }
        return Optional.of(schemaName);
    }

    @Override
    public void setSchemaName(String schemaName) {
        this.catalogName = schemaName;
    }

    @Override
    public void setParentTo(Parent<?> parent) {
        setParentHelper(parent, Dbms.class)
            .ifPresent(p -> this.parent = p);
    }

    @Override
    public Optional<Dbms> getParent() {
        return Optional.ofNullable(parent);
    }

    @Override
    public ChildHolder getChildren() {
        return children;
    }
}