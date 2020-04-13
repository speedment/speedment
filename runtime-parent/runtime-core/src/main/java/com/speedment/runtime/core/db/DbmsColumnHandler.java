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
package com.speedment.runtime.core.db;

import com.speedment.runtime.config.Column;

import java.util.function.Predicate;


/**
 * A DbmsColumnHandler governs selection of columns used in queries to the database
 *
 * @author  Dan Lawesson
 * @since   3.0.2
 */
public interface DbmsColumnHandler {

    /**
     * By default, all columns are included in SQL INSERT statements, this predicate
     * allows exclusion from that rule in order to for example exclude auto incremented
     * fields.
     *
     * @return a predicate used to determine if a column shall be excluded in generated SQL INSERT statements
     */
    Predicate<Column> excludedInInsertStatement();
    
    /**
     * By default, all columns are included in SQL UPDATE statements, this predicate
     * allows exclusion from that rule in order to for example exclude auto incremented
     * fields.
     *
     * @return a predicate used to determine if a column shall be excluded in generated SQL UPDATE statements
     * @since 3.0.17
     */
    Predicate<Column> excludedInUpdateStatement();
    
        
}
