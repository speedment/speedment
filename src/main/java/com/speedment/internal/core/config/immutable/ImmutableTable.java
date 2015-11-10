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
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.core.config.ChildHolder;
import static com.speedment.internal.util.Cast.castOrFail;
import groovy.lang.Closure;
import java.util.Comparator;
import java.util.Optional;
import static com.speedment.internal.core.config.immutable.ImmutableUtil.throwNewUnsupportedOperationExceptionImmutable;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 */
public final class ImmutableTable extends ImmutableAbstractNamedConfigEntity implements Table, ImmutableParentHelper<Child<Table>> {

    private final Optional<Schema> parent;
    private final ChildHolder children;
    private final Optional<String> tableName;
    private final FieldStorageType fieldStorageType;
    private final ColumnCompressionType columnCompressionType;
    private final StorageEngineType storageEngineType;

    private static int valueOfClass(Class<?> clazz) {
        if (Column.class.equals(clazz)) {
            return 0;
        }
        if (PrimaryKeyColumn.class.equals(clazz)) {
            return 1;
        }
        if (Index.class.equals(clazz)) {
            return 2;
        }
        if (ForeignKey.class.equals(clazz)) {
            return 3;
        }
        return 4;
    }

    private final static Comparator<Class<?>> CLASS_COMPARATOR = (a, b) -> Integer.compare(valueOfClass(a), valueOfClass(b));

    public ImmutableTable(Schema parent, Table table) {
        super(requireNonNull(table).getName(), table.isEnabled());
        requireNonNull(parent);
        // Fields
        this.parent = table.getParent();
        this.tableName = table.getTableName();
        this.fieldStorageType = table.getFieldStorageType();
        this.columnCompressionType = table.getColumnCompressionType();
        this.storageEngineType = table.getStorageEngineType();
        // Children
        children = childHolderOf(table.stream().map(this::toImmutable), CLASS_COMPARATOR);
    }

    private Child<?> toImmutable(Child<?> child) {
        if (child instanceof Column) {
            return new ImmutableColumn(this, castOrFail(child, Column.class));
        } else if (child instanceof PrimaryKeyColumn) {
            return new ImmutablePrimaryKeyColumn(this, castOrFail(child, PrimaryKeyColumn.class));
        } else if (child instanceof Index) {
            return new ImmutableIndex(this, castOrFail(child, Index.class));
        } else if (child instanceof ForeignKey) {
            return new ImmutableForeignKey(this, castOrFail(child, ForeignKey.class));
        }
        throw new SpeedmentException("Unknown Table child type:" + child);
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
    public StorageEngineType getStorageEngineType() {
        return storageEngineType;
    }

    @Override
    public void setStorageEngineType(StorageEngineType storageEngineType) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public Optional<String> getTableName() {
        return tableName;
    }

    @Override
    public void setTableName(String tableName) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setParent(Parent<?> parent) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public ChildHolder getChildren() {
        return children;
    }

    @Override
    public Optional<Schema> getParent() {
        return parent;
    }

    @Override
    public Column addNewColumn() {
        return throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public Index addNewIndex() {
        return throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public PrimaryKeyColumn addNewPrimaryKeyColumn() {
        return throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public ForeignKey addNewForeignKey() {
        return throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public PrimaryKeyColumn primaryKeyColumn(Closure<?> c) {
        return throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public Column column(Closure<?> c) {
        return throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public Index index(Closure<?> c) {
        return throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public ForeignKey foreignKey(Closure<?> c) {
        return throwNewUnsupportedOperationExceptionImmutable();
    }
}
