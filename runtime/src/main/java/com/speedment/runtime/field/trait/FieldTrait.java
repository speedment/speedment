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

import com.speedment.runtime.Speedment;
import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.identifier.FieldIdentifier;
import com.speedment.runtime.internal.util.document.DocumentDbUtil;

import java.util.Optional;

/**
 * A trait that every field implements.
 * 
 * @param <ENTITY>  the entity type
 * 
 * @author  Per Minborg
 * @author  Emil Forslund
 * @since   2.2.0
 */
@Api(version = "3.0")
public interface FieldTrait<ENTITY> extends HasGetter<ENTITY>, HasSetter<ENTITY> {
    
    /**
     * Returns the unique identifier of this field.
     * 
     * @return  the identifier
     */
    FieldIdentifier<ENTITY> getIdentifier();

    /**
     * Returns {@code true} if the column that this field represents is UNIQUE.
     * 
     * @return  {@code true} if unique
     */
    boolean isUnique();
    
    /**
     * Locates the column that this field is referencing by using the specified
     * {@link Speedment} instance.
     * 
     * @param project  the project instance
     * @return         the column
     */
    default Optional<? extends Column> findColumn(Project project) {
        return DocumentDbUtil.referencedColumnIfPresent(project, getIdentifier());
    }
}