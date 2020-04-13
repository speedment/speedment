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
package com.speedment.runtime.core.component.sql;

import com.speedment.runtime.config.Project;
import com.speedment.runtime.core.internal.component.sql.SqlTypeMapperHelperImpl;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.typemapper.TypeMapper;

/**
 * A helper class that makes it easier to create and invoke instances of 
 * {@link TypeMapper} when a relational SQL database source is used. The method 
 * is indended to be used by the generated entity managers.
 * 
 * @param <DB_TYPE>    the JDBC type of the field
 * @param <JAVA_TYPE>  the java type of the field
 * 
 * @author  Emil Forslund
 * @since   3.0.1
 */
public interface SqlTypeMapperHelper<DB_TYPE, JAVA_TYPE> {
    
    /**
     * Converts the specified value in JDBC form to the appropriate java type by
     * using the {@link TypeMapper} contained in this helper.
     * 
     * @param dbValue  the database (JDBC) value
     * @return         the converted value ready to be put into an entity
     */
    JAVA_TYPE apply(DB_TYPE dbValue);
    
    /**
     * Creates a new {@link SqlTypeMapperHelper} by using the default 
     * implementation.
     * 
     * @param <ENTITY>     the entity type
     * @param <DB_TYPE>    the value database (JDBC) type
     * @param <JAVA_TYPE>  the expected value java type
     * @param project      the current project instance
     * @param field        the field that this helper is mapping
     * @param entityClass  the class of the entity interface
     * @return             the new {@link SqlTypeMapperHelper}
     */
    static <ENTITY, DB_TYPE, JAVA_TYPE> 
    SqlTypeMapperHelper<DB_TYPE, JAVA_TYPE> create(
            Project project,
            Field<ENTITY> field, 
            Class<ENTITY> entityClass) {
        
        return new SqlTypeMapperHelperImpl<>(project, field, entityClass);
    }
}