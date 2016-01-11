/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.core.config.db;

import com.speedment.internal.core.config.AbstractChildDocument;
import com.speedment.config.db.Column;
import com.speedment.config.db.ForeignKey;
import com.speedment.config.db.Index;
import com.speedment.config.db.PrimaryKeyColumn;
import com.speedment.config.db.Schema;
import com.speedment.config.db.Table;
import java.util.Map;
import java.util.function.BiFunction;

/**
 *
 * @author Emil Forslund
 */
public class TableImpl extends AbstractChildDocument<Schema> implements Table {

    public TableImpl(Schema parent, Map<String, Object> data) {
        super(parent, data);
    }

    @Override
    public BiFunction<Table, Map<String, Object>, Column> columnConstructor() {
        return ColumnImpl::new;
    }

    @Override
    public BiFunction<Table, Map<String, Object>, Index> indexConstructor() {
        return IndexImpl::new;
    }

    @Override
    public BiFunction<Table, Map<String, Object>, ForeignKey> foreignKeyConstructor() {
        return ForeignKeyImpl::new;
    }

    @Override
    public BiFunction<Table, Map<String, Object>, PrimaryKeyColumn> primaryKeyColumnConstructor() {
        return PrimaryKeyColumnImpl::new;
    }
}