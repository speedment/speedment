/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.field;

import com.speedment.annotation.Api;
import com.speedment.field.methods.Getter;

/**
 *
 * @author          pemi, Emil Forslund
 * @param <ENTITY>  the entity type
 * @param <V>       the field value type
 * @param <FK>      the foreign entity type
 */
@Api(version = "2.1")
public interface ReferenceForeignKeyField<ENTITY, V, FK> extends ReferenceField<ENTITY, V> {
    /**
     * Returns a function that can find a foreign entity pointed out by this
     * field.
     * 
     * @return  the finder
     */
    Getter<ENTITY, FK> finder();
    
    /**
     * Finds and returns the foreign key Entity using the provided Entity.
     *
     * @param entity  to use when finding the foreign key Entity
     * @return        the foreign key Entity
     */
    FK findFrom(ENTITY entity);
}