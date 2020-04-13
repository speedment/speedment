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


import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.config.util.DocumentDbUtil;
import java.util.Optional;

/**
 *
 * @param <ENTITY>  the entity type
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */

public interface HasIdentifier<ENTITY> {
    
    /**
     * Returns the unique identifier of this field.
     * 
     * @return  the identifier
     */
    ColumnIdentifier<ENTITY> identifier();
    
    /**
     * Locates the column that this field is referencing by using the specified
     * {@link Project} instance.
     * 
     * @param project  the project instance
     * @return         the column
     */
    default Optional<? extends Column> findColumn(Project project) {
        return DocumentDbUtil.referencedColumnIfPresent(project, identifier());
    }
    
}