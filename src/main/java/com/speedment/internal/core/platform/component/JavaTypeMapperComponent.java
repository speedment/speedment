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
package com.speedment.internal.core.platform.component;

import com.speedment.config.parameters.DbmsType;
import com.speedment.internal.core.runtime.typemapping.JavaTypeMapping;

/**
 * The JavaTypeMapperComponent provides a mapping from a certain DbmsType and
 * Class to a certain JavaTypeMapping. By implementing custom implementation of
 * this interface, arbitrary mappings may be carried out.
 *
 * @author pemi
 * @since 2.0
 */
public interface JavaTypeMapperComponent extends Component {

    @Override
    default Class<JavaTypeMapperComponent> getComponentClass() {
        return JavaTypeMapperComponent.class;
    }

    /**
     * Gets the mapping from the javaClass to the JavaTypeMapping. If a specific
     * mapping for the given DbmsType is present, that mapping is selected over
     * the general mapping for any DbmsType.
     *
     * @param <T> the java class type to map
     * @param dbmsType the Dbms type
     * @param javaClass the java class to map
     * @return the mapping
     */
    <T> JavaTypeMapping<T> apply(DbmsType dbmsType, Class<T> javaClass);

    /**
     * Gets the mapping from the javaClass to the JavaTypeMapping. The mapping
     * will not consider DbmsType specific mappings.
     *
     * @param <T> the java class type to map
     * @param javaClass the java class to map
     * @return the mapping
     */
    <T> JavaTypeMapping<T> apply(Class<T> javaClass);

}
