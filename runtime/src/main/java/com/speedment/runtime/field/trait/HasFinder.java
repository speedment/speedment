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
package com.speedment.runtime.field.trait;

import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.method.BackwardFinder;
import com.speedment.runtime.field.method.FindFrom;
import com.speedment.runtime.manager.Manager;

/**
 * A representation of an Entity field that use a foreign key to 
 * reference some other field.
 * 
 * @param <ENTITY>     the entity type
 * @param <FK_ENTITY>  the foreign entity type
 * 
 * @author  Per Minborg
 * @author  Emil Forslund
 * @since   2.2.0
 */
@Api(version = "3.0")
public interface HasFinder<ENTITY, FK_ENTITY> {
    
    /**
     * Returns the field referenced by this finder.
     * 
     * @return  the referenced field
     */
    Field<FK_ENTITY> getReferencedField();
    
    /**
     * Returns a function that can be used to find referenced entites using the
     * specified manager.
     * 
     * @param foreignManager  the foreign manager
     * @return                finder method
     */
    FindFrom<ENTITY, FK_ENTITY> finder(Manager<FK_ENTITY> foreignManager);
    
    /**
     * Returns a function that can be used to find a stream of entities 
     * referencing this entity using the specified manager.
     * 
     * @param manager  the foreign manager
     * @return                streaming method
     */
    BackwardFinder<FK_ENTITY, ENTITY> backwardFinder(Manager<ENTITY> manager);
}