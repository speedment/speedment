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
package com.speedment.internal.core.config.db.mutator;

import com.speedment.config.db.ForeignKeyColumn;
import static com.speedment.config.db.ForeignKeyColumn.*;
import com.speedment.internal.core.config.db.mutator.trait.HasNameMutator;
import com.speedment.internal.core.config.db.mutator.trait.HasOrdinalPositionMutator;

/**
 *
 * @author       Per Minborg
 * @param <DOC>  document type
 */
public class ForeignKeyColumnMutator<DOC extends ForeignKeyColumn> extends DocumentMutatorImpl<DOC> implements 
        HasNameMutator<DOC>, 
        HasOrdinalPositionMutator<DOC> {

    public ForeignKeyColumnMutator(DOC foreignKeyColumn) {
        super(foreignKeyColumn);
    }
    
    public void setForeignTableName(String foreignTableName) {
        put(FOREIGN_TABLE_NAME, foreignTableName);
    }
    
    public void setForeignColumnName(String foreignColumnName) {
        put(FOREIGN_COLUMN_NAME, foreignColumnName);
    }
    
}