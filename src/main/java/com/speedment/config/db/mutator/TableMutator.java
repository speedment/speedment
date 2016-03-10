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
package com.speedment.config.db.mutator;

import com.speedment.config.db.*;
import static com.speedment.config.db.Table.COLUMNS;
import static com.speedment.config.db.Table.FOREIGN_KEYS;
import static com.speedment.config.db.Table.INDEXES;
import static com.speedment.config.db.Table.PRIMARY_KEY_COLUMNS;
import com.speedment.internal.core.config.db.ColumnImpl;
import com.speedment.internal.core.config.db.ForeignKeyImpl;
import com.speedment.internal.core.config.db.IndexImpl;
import com.speedment.internal.core.config.db.PrimaryKeyColumnImpl;
import com.speedment.config.db.mutator.trait.HasAliasMutator;
import com.speedment.config.db.mutator.trait.HasEnabledMutator;
import com.speedment.config.db.mutator.trait.HasNameMutator;
import static com.speedment.internal.util.document.DocumentUtil.newDocument;

/**
 *
 * @author       Per Minborg
 * @param <DOC>  document type
 */
public class TableMutator<DOC extends Table> extends DocumentMutatorImpl<DOC> implements 
        HasEnabledMutator<DOC>, 
        HasNameMutator<DOC>, 
        HasAliasMutator<DOC> {

    public TableMutator(DOC table) {
        super(table);
    }

    public Column addNewColumn() {
        return new ColumnImpl(document(), newDocument(document(), COLUMNS));
    }
    
    public Index addNewIndex() {
        return new IndexImpl(document(), newDocument(document(), INDEXES));
    }
    
    public ForeignKey addNewForeignKey() {
        return new ForeignKeyImpl(document(), newDocument(document(), FOREIGN_KEYS));
    }
    
    public PrimaryKeyColumn addNewPrimaryKeyColumn() {
        return new PrimaryKeyColumnImpl(document(), newDocument(document(), PRIMARY_KEY_COLUMNS));
    }
}