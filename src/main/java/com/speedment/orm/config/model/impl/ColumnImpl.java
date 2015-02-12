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
import java.util.Optional;

/**
 *
 * @author pemi
 */
public class ColumnImpl extends AbstractConfigEntity<Column, Table, ConfigEntity<?, Column, ?>> implements Column {

    private int ordinalPosition;
    private String alias;
    private FieldStorageType fieldStorageType;
    private ColumnCompressionType columnCompressionType;

    @Override
    protected void setDefaults() {
        ordinalPosition = ORDINAL_UNSET;
        setFieldStorageType(FieldStorageType.defaultFor(this));
        setColumnCompressionType(ColumnCompressionType.defaultFor(this));
    }

    @Override
    public Class<Column> getInterfaceMainClass() {
        return Column.class;
    }

    @Override
    public int getOrdinalPosition() {
        return ordinalPosition;
    }

    @Override
    public Column setOrdinalPosition(int ordinalPosition) {
        return with(ordinalPosition, op -> this.ordinalPosition = op);
    }

    @Override
    public Optional<String> getAlias() {
        return Optional.ofNullable(alias);
    }

    @Override
    public Column setAlias(CharSequence alias) {
        return with(alias, n -> this.alias = makeNullSafeString(n));
    }

    @Override
    public FieldStorageType getFieldStorageType() {
        return fieldStorageType;
    }

    @Override
    public Column setFieldStorageType(FieldStorageType fieldStorageType) {
        return with(fieldStorageType, f -> this.fieldStorageType = f);
    }

    @Override
    public ColumnCompressionType getColumnCompressionType() {
        return columnCompressionType;
    }

    @Override
    public Column setColumnCompressionType(ColumnCompressionType columnCompressionType) {
        return with(columnCompressionType, c -> this.columnCompressionType = c);
    }

}
