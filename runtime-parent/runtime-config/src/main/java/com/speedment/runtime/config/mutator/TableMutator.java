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
package com.speedment.runtime.config.mutator;

import com.speedment.runtime.config.*;
import com.speedment.runtime.config.internal.ColumnImpl;
import com.speedment.runtime.config.internal.ForeignKeyImpl;
import com.speedment.runtime.config.internal.IndexImpl;
import com.speedment.runtime.config.internal.PrimaryKeyColumnImpl;
import com.speedment.runtime.config.mutator.trait.HasAliasMutator;
import com.speedment.runtime.config.mutator.trait.HasEnabledMutator;
import com.speedment.runtime.config.mutator.trait.HasIdMutator;
import com.speedment.runtime.config.mutator.trait.HasNameMutator;

import static com.speedment.runtime.config.util.DocumentUtil.newDocument;

/**
 *
 * @author       Per Minborg
 * @param <DOC>  document type
 */
public class TableMutator<DOC extends Table> extends DocumentMutatorImpl<DOC> implements 
        HasEnabledMutator<DOC>, 
        HasIdMutator<DOC>,    
        HasNameMutator<DOC>, 
        HasAliasMutator<DOC> {

    public TableMutator(DOC table) {
        super(table);
    }

    public void setView(boolean isView) {
        put(TableUtil.IS_VIEW, isView);
    }

    public Column addNewColumn() {
        return new ColumnImpl(document(), newDocument(document(), TableUtil.COLUMNS));
    }
    
    public Index addNewIndex() {
        return new IndexImpl(document(), newDocument(document(), TableUtil.INDEXES));
    }
    
    public ForeignKey addNewForeignKey() {
        return new ForeignKeyImpl(document(), newDocument(document(), TableUtil.FOREIGN_KEYS));
    }
    
    public PrimaryKeyColumn addNewPrimaryKeyColumn() {
        return new PrimaryKeyColumnImpl(document(), newDocument(document(), TableUtil.PRIMARY_KEY_COLUMNS));
    }
}