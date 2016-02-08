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
package com.speedment.db.crud;

import com.speedment.annotation.Api;
import com.speedment.config.db.Table;

/**
 *
 * @author Emil Forslund
 * @since 2.2
 */
@Api(version = "2.2")
public interface Join {
    
    /**
     * Returns the column name of the column in this table that should be
     * compared in the join statement.
     * 
     * @return  the column name in this table
     */
    String getColumnName();
    
    /**
     * Returns the column name of the column in the joined table. 
     * 
     * @return  the other column name
     */
    String getOtherColumnName();
    
    /**
     * Returns the table that this operation should join with.
     * 
     * @return  the other table in the join
     */
    Table getOtherTable();
}
