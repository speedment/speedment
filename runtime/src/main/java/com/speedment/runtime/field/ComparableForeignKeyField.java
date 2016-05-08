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
package com.speedment.runtime.field;

import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.field.trait.ComparableFieldTrait;
import com.speedment.runtime.field.trait.FieldTrait;
import com.speedment.runtime.field.trait.ReferenceFieldTrait;
import com.speedment.runtime.field.trait.ReferenceForeignKeyFieldTrait;

/**
 * A field that represents a value that implements {@link ReferenceFieldTrait},
 * {@link ComparableFieldTrait} and {ReferenceForeignKeyFieldTrait}.
 * 
 * @param <ENTITY>  the entity type
 * @param <D>       the database type
 * @param <V>       the field value type
 * @param <FK>      the foreign entity type
 * 
 * @author  Per Minborg
 * @author  Emil Forslund
 * @since   2.2
 * 
 * @see  ReferenceFieldTrait
 * @see  ComparableFieldTrait
 * @see  ReferenceForeignKeyFieldTrait
 */
@Api(version = "2.3")
public interface ComparableForeignKeyField<ENTITY, D, V extends Comparable<? super V>, FK> extends
    FieldTrait, 
    ReferenceFieldTrait<ENTITY, D, V>,
    ComparableFieldTrait<ENTITY, D, V>,
    ReferenceForeignKeyFieldTrait<ENTITY, D, FK>
{}
