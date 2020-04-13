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


import com.speedment.runtime.config.ForeignKeyColumn;
import com.speedment.runtime.config.ForeignKeyColumnUtil;
import com.speedment.runtime.config.mutator.trait.HasNameMutator;
import com.speedment.runtime.config.mutator.trait.HasOrdinalPositionMutator;

import com.speedment.runtime.config.mutator.trait.HasIdMutator;

/**
 *
 * @author       Per Minborg
 * @param <DOC>  document type
 */

public class ForeignKeyColumnMutator<DOC extends ForeignKeyColumn> extends DocumentMutatorImpl<DOC> implements 
        HasIdMutator<DOC>,    
        HasNameMutator<DOC>, 
        HasOrdinalPositionMutator<DOC> {

    public ForeignKeyColumnMutator(DOC foreignKeyColumn) {
        super(foreignKeyColumn);
    }
    
    public void setForeignTableName(String foreignTableName) {
        put(ForeignKeyColumnUtil.FOREIGN_TABLE_NAME, foreignTableName);
    }
    
    public void setForeignColumnName(String foreignColumnName) {
        put(ForeignKeyColumnUtil.FOREIGN_COLUMN_NAME, foreignColumnName);
    }
    
    public void setForeignDatabaseName(String foreignDatabaseName){
    	put(ForeignKeyColumnUtil.FOREIGN_DATABASE_NAME, foreignDatabaseName);
    }
    
    public void setForeignSchemaName(String foreignSchemaName) {
    	put(ForeignKeyColumnUtil.FOREIGN_SCHEMA_NAME, foreignSchemaName);
    }

    
}