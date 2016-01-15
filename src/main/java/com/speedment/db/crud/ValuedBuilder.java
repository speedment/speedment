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
import java.util.Map;

/**
 *
 * @author Emil Forslund
 * @param <OPERATION>  the type of the valued operation being built
 * @param <BUILDER>    the type of the implementing builder class
 * @since 2.2
 */
@Api(version = "2.2")
public interface ValuedBuilder<OPERATION extends CrudOperation & Valued, BUILDER extends ValuedBuilder<OPERATION, BUILDER>> extends CrudOperationBuilder<OPERATION> {
    
    /**
     * Sets the value for a particular column.
     *
     * @param columnName  the column
     * @param value       the value
     * @return            a reference to this builder
     */
    BUILDER with(String columnName, Object value);

    /**
     * Adds all the specified values mapped to the particular column name.
     * If the same column name already has a value, the old value will be
     * overwritten with the new one suggested.

     * @param values      values mapped to column names
     * @return            a reference to this builder
     */
    BUILDER with(Map<String, Object> values);

    /**
     * {@inheritDoc} 
     */
    @Override
    default boolean isValued() {
        return true;
    }
}