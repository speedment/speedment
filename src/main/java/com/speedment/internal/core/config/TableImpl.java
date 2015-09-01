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

import com.speedment.internal.core.config.aspects.ParentHelper;
import com.speedment.config.Column;
import com.speedment.config.ForeignKey;
import com.speedment.config.Index;
import com.speedment.config.PrimaryKeyColumn;
import com.speedment.config.Schema;
import com.speedment.config.Table;
import com.speedment.config.aspects.Child;
import com.speedment.config.aspects.Parent;
import com.speedment.config.parameters.ColumnCompressionType;
import com.speedment.config.parameters.FieldStorageType;
import com.speedment.config.parameters.StorageEngineType;
import com.speedment.internal.core.config.utils.ConfigUtil;
import com.speedment.internal.util.Cast;
import groovy.lang.Closure;
import java.util.Comparator;
import java.util.Optional;

/**
 *
 * @author pemi
 */
public final class TableImpl extends AbstractNamedConfigEntity implements Table, ParentHelper<Child<Table>> {

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
        setFieldStorageType(FieldStorageType.INHERIT);
        setColumnCompressionType(ColumnCompressionType.INHERIT);
        setStorageEngineType(StorageEngineType.INHERIT);
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

    @SuppressWarnings("unchecked")
    @Override
    public void setParent(Parent<?> parent) {
        this.parent = Cast.castOrFail(parent, Schema.class);
    }

    @Override
    public ChildHolder getChildren() {
        return children;
    }

    @Override
    public Optional<Schema> getParent() {
        return Optional.ofNullable(parent);
    }
    
    @Override
    public Column addNewColumn() {
        final Column e = Column.newColumn();
        add(e);
        return e;
    }

    @Override
    public  Index addNewIndex() {
        final Index e = Index.newIndex();
        add(e);
        return e;
    }

    @Override
    public  PrimaryKeyColumn addNewPrimaryKeyColumn() {
        final PrimaryKeyColumn e = PrimaryKeyColumn.newPrimaryKeyColumn();
        add(e);
        return e;
    }

    @Override
    public  ForeignKey addNewForeignKey() {
        final ForeignKey e = ForeignKey.newForeignKey();
        add(e);
        return e;
    }
    
    @Override
    public PrimaryKeyColumn primaryKeyColumn(Closure<?> c) {
        return ConfigUtil.groovyDelegatorHelper(c, this::addNewPrimaryKeyColumn);
    }
    
    @Override
    public Column column(Closure<?> c) {
        return ConfigUtil.groovyDelegatorHelper(c, this::addNewColumn);
    }
    
    @Override
    public Index index(Closure<?> c) {
        return ConfigUtil.groovyDelegatorHelper(c, this::addNewIndex);
    }
    
    @Override
    public ForeignKey foreignKey(Closure<?> c) {
        return ConfigUtil.groovyDelegatorHelper(c, this::addNewForeignKey);
    }
}