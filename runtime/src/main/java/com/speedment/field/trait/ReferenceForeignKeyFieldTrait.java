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
package com.speedment.field.trait;

import com.speedment.annotation.Api;
import com.speedment.field.methods.Finder;

/**
 * A representation of an Entity field that is a reference type (eg 
 * {@code Integer} and not {@code int}) and that is a foreign key to some other
 * field.
 * 
 * @param <ENTITY>     the entity type
 * @param <D>          the database type
 * @param <FK_ENTITY>  the foreign entity type
 * 
 * @author  Per Minborg
 * @author  Emil Forslund
 */
@Api(version = "2.3")
public interface ReferenceForeignKeyFieldTrait<ENTITY, D, FK_ENTITY> {

    /**
     * Returns a function that can find a foreign entity pointed out by this
     * field.
     *
     * @return the finder
     */
    Finder<ENTITY, FK_ENTITY> finder();

    /**
     * Finds the foreign entity associated by this field.
     *
     * @param entity  this entity
     * @return        the foreign entity associated by this field
     */
    default FK_ENTITY findFrom(ENTITY entity) {
        return finder().apply(entity);
    }
}