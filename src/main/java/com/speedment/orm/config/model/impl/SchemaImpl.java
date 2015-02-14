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
import com.speedment.orm.config.model.parameters.ColumnCompressionType;
import com.speedment.orm.config.model.parameters.FieldStorageType;
import com.speedment.orm.config.model.parameters.StorageEngineType;
import java.util.Optional;

/**
 *
 * @author pemi
 */
public class SchemaImpl extends AbstractConfigEntity<Schema, Dbms, Table> implements Schema {

    private boolean default_;
    private String schemaName;
    private String catalogName;
    private FieldStorageType fieldStorageType;
    private ColumnCompressionType columnCompressionType;
    private StorageEngineType storageEngineType;

    @Override
    protected void setDefaults() {
        setFieldStorageType(FieldStorageType.defaultFor(this));
        setColumnCompressionType(ColumnCompressionType.defaultFor(this));
        setStorageEngineType(StorageEngineType.defaultFor(this));
    }

    @Override
    public boolean isDefault() {
        return default_;
    }

    @Override
    public Schema setDefault(boolean default_) {
        return run(() -> this.default_ = default_);
    }

    @Override
    public FieldStorageType getFieldStorageType() {
        return fieldStorageType;
    }

    @Override
    public Schema setFieldStorageType(FieldStorageType fieldStorageType) {
        return run(() -> this.fieldStorageType = fieldStorageType);
    }

    @Override
    public ColumnCompressionType getColumnCompressionType() {
        return columnCompressionType;
    }

    @Override
    public Schema setColumnCompressionType(ColumnCompressionType columnCompressionType) {
        return run(() -> this.columnCompressionType = columnCompressionType);
    }

    @Override
    public StorageEngineType getStorageEngineType() {
        return storageEngineType;
    }

    @Override
    public Schema setStorageEngineType(StorageEngineType storageEngineType) {
        return run(() -> this.storageEngineType = storageEngineType);
    }

    @Override
    public Optional<String> getCatalogName() {
        return Optional.ofNullable(catalogName);
    }

    @Override
    public Schema setCatalogName(CharSequence catalogName) {
        return run(() -> this.catalogName = makeNullSafeString(catalogName));
    }

    @Override
    public Optional<String> getSchemaName() {
        if (schemaName == null) {
            return Optional.ofNullable(getName());
        }
        return Optional.of(schemaName);
    }

    @Override
    public Schema setSchemaName(CharSequence schemaName) {
        return run(() -> this.catalogName = makeNullSafeString(schemaName));
    }

}
