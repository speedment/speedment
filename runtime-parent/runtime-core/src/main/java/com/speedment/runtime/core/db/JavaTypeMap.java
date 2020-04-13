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

import com.speedment.runtime.core.db.metadata.ColumnMetaData;
import com.speedment.runtime.core.internal.db.JavaTypeMapImpl;

import java.util.Map;
import java.util.Optional;

/**
 * A convenience class for mapping between column types using a database 
 * specific rule set.
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
public interface JavaTypeMap {
    
    /**
     * A rule that helps defining how the metadata for a column 
     * should apply to the java mapping.
     */
    @FunctionalInterface
    interface Rule {
        
        /**
         * Returns the java type that should be used to represent the 
         * column with the specified metadata. A map of existing type 
         * mappings is also supplied since some implementations might 
         * need this.
         * <p>
         * If this rule does not apply to the specified column, an empty
         * {@code Optional} is returned. The rule engine will continue 
         * to look at rules until one that returns something other than 
         * empty is found. If no valid rule can be found, the standard 
         * mapping is used.
         * <p>
         * The supplied map is read-only.
         * 
         * @param sqlTypeMapping  read-only map of existing mappings
         * @param metadata        metadata about the particular column
         * @return                the mapped type to be used or empty
         */
        Optional<Class<?>> findJdbcType(Map<String, Class<?>> sqlTypeMapping, ColumnMetaData metadata);
    }
    
    /**
     * Adds the specified rule to the rule engine.
     * 
     * @param rule  the rule to add
     */
    void addRule(Rule rule);
    
    /**
     * Looks through all the rules that has been installed into this
     * rule engine to try and find one that returns something other
     * than an empty {@code Optional}. If none is found, the default
     * mapping is returned.
     * 
     * @param sqlTypeMapping  read-only map of existing mappings
     * @param metadata        metadata about the particular column
     * @return                the mapped type to be used
     */
    Class<?> findJdbcType(Map<String, Class<?>> sqlTypeMapping, ColumnMetaData metadata);
    
    /**
     * Adds a new mapping to this type map. If a previous mapping with
     * the same key exists, it will be overwritten.
     * <p>
     * A read-only view of the map with installed mappings will be 
     * available to the rules upon execution.
     * 
     * @param key    the key for the particular mapping
     * @param clazz  the mapped type
     */
    void put(String key, Class<?> clazz);
    
    /**
     * Returns the mapped type for the particular key.
     * 
     * @param key  the key to look for
     * @return     the type associated with it or {@code null}
     */
    Class<?> get(String key);
    
    /**
     * Creates and returns a new {@link JavaTypeMap} using the default
     * implementation.
     * 
     * @return  the created {@link JavaTypeMap}
     */
    static JavaTypeMap create() {
        return new JavaTypeMapImpl();
    }
}