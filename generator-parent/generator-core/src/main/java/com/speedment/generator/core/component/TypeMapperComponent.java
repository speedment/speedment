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
package com.speedment.generator.core.component;

import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Stores the currently installed {@link TypeMapper TypeMappers}.
 * 
 * @author  Emil Forslund
 * @since   2.2.0
 */
@InjectKey(TypeMapperComponent.class)
public interface TypeMapperComponent {
    
    /**
     * Installs the specified type mapper in this component.
     * 
     * @param databaseType        the type to install it for
     * @param typeMapperSupplier  the constructor for a mapper to install
     */
    void install(Class<?> databaseType, Supplier<TypeMapper<?, ?>> typeMapperSupplier);
    
    /**
     * Streams over all the type mappers installed in this component.
     * 
     * @param databaseType  the type to get mappers for
     * @return              all mappers
     */
    Stream<TypeMapper<?, ?>> mapFrom(Class<?> databaseType);
    
    /**
     * Streams over all the installed type mappers.
     * 
     * @return  stream of type mappers 
     */
    Stream<TypeMapper<?, ?>> stream();
    
    /**
     * Retrieve and return the type mapper with the specified absolute class
     * name. If it is not installed, return an empty optional.
     * 
     * @param absoluteClassName  the name as returned by {@code Class.getName()}
     * @return                   the type mapper or empty
     */
    Optional<TypeMapper<?, ?>> get(String absoluteClassName);
    
    /**
     * Returns the mapper class that will be used to generate a java
     * representation of the database types. If no type mapper is
     * specified, then an {@code IdentityTypeMapper} will be created
     * and returned.
     *
     * @param column  the column to retrieve the type mapper for
     * @return        the mapper class
     */
    TypeMapper<?, ?> get(Column column);
}