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

/**
 *
 * @author pemi
 */
public class SchemaImpl extends AbstractConfigEntity<Schema, Dbms, Table> implements Schema {

    private boolean default_;
    private FieldStorageType fieldStorageType;
    private ColumnCompressionType columnCompressionType;
    private StorageEngineType storageEngineType;

    @Override
    public boolean isDefault() {
        return default_;
    }

    @Override
    public Schema setDefault(boolean default_) {
        return with(default_, d -> this.default_ = d);
    }

    @Override
    public FieldStorageType getFieldStorageType() {
        return fieldStorageType;
    }

    @Override
    public Schema setFieldStorageType(FieldStorageType fieldStorageType) {
        return with(fieldStorageType, f -> this.fieldStorageType = f);
    }

    @Override
    public ColumnCompressionType getColumnCompressionType() {
        return columnCompressionType;
    }

    @Override
    public Schema setColumnCompressionType(ColumnCompressionType columnCompressionType) {
        return with(fieldStorageType, c -> this.fieldStorageType = c);
    }

    @Override
    public StorageEngineType getStorageEngineType() {
        return storageEngineType;
    }

    @Override
    public Schema setFieldStorageType(StorageEngineType storageEngineType) {
        return with(storageEngineType, s -> this.storageEngineType = s);
    }

}
