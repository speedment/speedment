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
package com.speedment.internal.ui.config.mutator;

import com.speedment.internal.core.config.db.mutator.TableMutator;
import com.speedment.internal.ui.config.ColumnProperty;
import com.speedment.internal.ui.config.ForeignKeyProperty;
import com.speedment.internal.ui.config.IndexProperty;
import com.speedment.internal.ui.config.PrimaryKeyColumnProperty;
import com.speedment.internal.ui.config.TableProperty;
import com.speedment.internal.ui.config.mutator.trait.HasAliasPropertyMutator;
import com.speedment.internal.ui.config.mutator.trait.HasEnabledPropertyMutator;
import com.speedment.internal.ui.config.mutator.trait.HasNamePropertyMutator;

/**
 *
 * @author Emil Forslund
 */
public final class TablePropertyMutator extends TableMutator<TableProperty> implements 
        HasEnabledPropertyMutator<TableProperty>, 
        HasNamePropertyMutator<TableProperty>, 
        HasAliasPropertyMutator<TableProperty> {

    TablePropertyMutator(TableProperty table) {
        super(table);
    }

    @Override
    public ColumnProperty addNewColumn() {
        final ColumnProperty child = new ColumnProperty(document());
        document().columnsProperty().add(child);
        return child;
    }
    
    @Override
    public IndexProperty addNewIndex() {
        final IndexProperty child = new IndexProperty(document());
        document().indexesProperty().add(child);
        return child;
    }
    
    @Override
    public ForeignKeyProperty addNewForeignKey() {
        final ForeignKeyProperty child = new ForeignKeyProperty(document());
        document().foreignKeysProperty().add(child);
        return child;
    }
    
    @Override
    public PrimaryKeyColumnProperty addNewPrimaryKeyColumn() {
        final PrimaryKeyColumnProperty child = new PrimaryKeyColumnProperty(document());
        document().primaryKeyColumnsProperty().add(child);
        return child;
    }
}