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
import java.util.Comparator;
import java.util.Optional;

/**
 *
 * @author pemi
 */
public class TableImpl extends AbstractNamedConfigEntity implements Table {

    private Schema parent;
    private final ChildHolder children;
    private String tableName;
    private FieldStorageType fieldStorageType;
    private ColumnCompressionType columnCompressionType;
    private StorageEngineType storageEngineType;

    private static int valueOfClass(Class<?> clazz) {
        if (Column.class.equals(clazz)) return 0;
        if (PrimaryKeyColumn.class.equals(clazz)) return 1;
        if (Index.class.equals(clazz)) return 2;
        if (ForeignKey.class.equals(clazz)) return 3;
        return 4;
    }
    
    private final static Comparator<Class<?>> CLASS_COMPARATOR = (a, b) -> Integer.compare(valueOfClass(a), valueOfClass(b));

    public TableImpl() {
        children = new ChildHolder(CLASS_COMPARATOR);
    }

    @Override
    protected void setDefaults() {
        setFieldStorageType(FieldStorageType.defaultFor(this));
        setColumnCompressionType(ColumnCompressionType.defaultFor(this));
        setStorageEngineType(StorageEngineType.defaultFor(this));
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
    public Optional<String> getTableName() {
        if (tableName == null) {
            return Optional.ofNullable(getName());
        }
        return Optional.of(tableName);
    }

    @Override
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public void setParentTo(Parent<?> parent) {
        setParentHelper(parent, Schema.class)
            .ifPresent(p -> this.parent = p);
    }

    @Override
    public ChildHolder getChildren() {
        return children;
    }

    @Override
    public Optional<Schema> getParent() {
        return Optional.ofNullable(parent);
    }
}