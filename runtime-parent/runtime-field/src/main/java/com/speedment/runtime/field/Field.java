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
package com.speedment.runtime.field;


import com.speedment.runtime.field.trait.HasGetter;
import com.speedment.runtime.field.trait.HasIdentifier;
import com.speedment.runtime.field.trait.HasSetter;
import com.speedment.runtime.field.trait.HasTypeMapper;

/**
 * The base interface for all fields.
 * 
 * @param <ENTITY>  the entity type
 * 
 * @author  Per Minborg
 * @author  Emil Forslund
 * @since   2.2.0
 */
public interface Field<ENTITY> extends 
        HasIdentifier<ENTITY>, 
        HasGetter<ENTITY>, 
        HasSetter<ENTITY>, 
        HasTypeMapper {
  
    /**
     * Returns {@code true} if the column that this field represents is UNIQUE.
     * 
     * @return  {@code true} if unique
     */
    boolean isUnique();

    /**
     * Returns the table alias for this field. The default table alias is the table name
     * for the field. A custom value can be set using the {@link #tableAlias(String)} method.
     *
     * @return the table alias for this field
     * @since  3.1.3
     */
    String tableAlias();

    /**
     * Creates and returns a new Field with the provided {@code tableAlias}. The new Field
     * will retain all other properties from this field except the tableAlias.
     *
     * @param tableAlias  the table alias to use in the new field
     * @return a new Field with the provided {@code tableAlias}
     *
     * @throws NullPointerException if the provided {@code tableAlias} is {@code null}
     * @since  3.1.3
     */
    Field<ENTITY> tableAlias(String tableAlias);

}