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
package com.speedment.runtime.field.trait;


import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.method.BackwardFinder;
import com.speedment.runtime.field.method.FindFrom;

import java.util.function.Supplier;
import java.util.stream.Stream;

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
public interface HasFinder<ENTITY, FK_ENTITY> extends Field<ENTITY> {
    
    /**
     * Returns the field referenced by this finder.
     * 
     * @return  the referenced field
     */
    Field<FK_ENTITY> getReferencedField();
    
    /**
     * Returns a function that can be used to find referenced entities using the
     * specified manager.
     * 
     * @param identifier      the table identifier
     * @param streamSupplier  the stream supplier
     * @return                finder method
     */
    FindFrom<ENTITY, FK_ENTITY> finder(
        TableIdentifier<FK_ENTITY> identifier,
        Supplier<Stream<FK_ENTITY>> streamSupplier);

    /**
     * Returns a function that can be used to find a stream of entities 
     * referencing this entity using the specified manager.
     * 
     * @param identifier      the table identifier
     * @param streamSupplier  the stream supplier
     * @return                streaming method
     */
    BackwardFinder<FK_ENTITY, ENTITY> backwardFinder(
        TableIdentifier<ENTITY> identifier,
        Supplier<Stream<ENTITY>> streamSupplier);
}