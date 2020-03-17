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
package com.speedment.runtime.config.internal;

import com.speedment.runtime.config.*;

import java.util.Map;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public final class TableImpl extends AbstractChildDocument<Schema> implements Table {

    public TableImpl(Schema parent, Map<String, Object> data) {
        super(parent, data);
    }

    @Override
    public Stream<Column> columns() {
        return children(TableUtil.COLUMNS, ColumnImpl::new);
    }

    @Override
    public Stream<Index> indexes() {
        return children(TableUtil.INDEXES, IndexImpl::new);
    }

    @Override
    public Stream<ForeignKey> foreignKeys() {
        return children(TableUtil.FOREIGN_KEYS, ForeignKeyImpl::new);
    }

    @Override
    public Stream<PrimaryKeyColumn> primaryKeyColumns() {
        return children(TableUtil.PRIMARY_KEY_COLUMNS, PrimaryKeyColumnImpl::new);
    }
}