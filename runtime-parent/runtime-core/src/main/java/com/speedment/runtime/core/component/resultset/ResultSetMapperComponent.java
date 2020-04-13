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
package com.speedment.runtime.core.component.resultset;

import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.runtime.core.db.DbmsType;

/**
 * The {@code ResultSetMapperComponent} provides a mapping from a certain 
 * {@link DbmsType} and {@code Class} to a certain {@link ResultSetMapping}. 
 * By implementing this interface, arbitrary mappings may be carried out.
 * <p>
 * This component is primarily used by custom database connectors so that 
 * non-standard JDBC mappings can be used.
 *
 * @author  Per Minborg
 * @since   2.0.0
 */
@InjectKey(ResultSetMapperComponent.class)
public interface ResultSetMapperComponent {

    /**
     * Gets the mapping from the javaClass to the {@link ResultSetMapping}. If a 
     * specific mapping for the given {@link DbmsType} is present, that mapping 
     * is selected over the general mapping for any {@link DbmsType}.
     *
     * @param <T>        the java class type to map
     * @param dbmsType   the Dbms type
     * @param javaClass  the java class to map
     * @return           the mapping
     */
    <T> ResultSetMapping<T> apply(DbmsType dbmsType, Class<T> javaClass);

    /**
     * Gets the mapping from the javaClass to the {@link ResultSetMapping}. The 
     * mapping will not consider {@link DbmsType} specific mappings.
     *
     * @param <T>        the java class type to map
     * @param javaClass  the java class to map
     * @return           the mapping
     */
    <T> ResultSetMapping<T> apply(Class<T> javaClass);

}