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
package com.speedment.runtime.config.mapper;

import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.mapper.primitive.PrimitiveTypeMapper;
import com.speedment.runtime.internal.config.mapper.IdentityTypeMapper;

import java.lang.reflect.Type;
import java.util.Comparator;

import static java.util.Comparator.comparing;

/**
 * A type mapper contains logic for converting between the database and the java
 * type for a field. Implementations of this class should be installed in the
 * {@code TypeMapperComponent}.
 *
 * @param <DB_TYPE>   the type as it is represented in the JDBC driver
 * @param <JAVA_TYPE> the type as it should be represented in generated code
 *
 * @author  Emil Forslund
 * @since   2.2.0
 */
@Api(version = "3.0")
public interface TypeMapper<DB_TYPE, JAVA_TYPE> {

    /**
     * The standard comparator to use for instances of the {@link TypeMapper}
     * interface. This comparator will use the name of the database type as
     * comparison index and if two mappers share the same database type, it will
     * use the label in alphabetical order.
     */
    final Comparator<TypeMapper<?, ?>> COMPARATOR = comparing(TypeMapper::getLabel);

    /**
     * Returns the label for this mapper that should appear to the end user.
     *
     * @return  the label
     */
    String getLabel();

    /**
     * Returns a type describing the resulting java type when this mapper is
     * applied to a database result.
     * 
     * @param column    the column that is being mapped
     * @return          the resulting java type
     */
    Type getJavaType(Column column);
    
    /**
     * Converts a value from the database domain to the java domain.
     *
     * @param column      the column that is being mapped
     * @param entityType  the entity type that the mapping is for
     * @param value       the value to convert
     * @return            the converted value
     */
    JAVA_TYPE toJavaType(Column column, Class<?> entityType, DB_TYPE value);

    /**
     * Converts a value from the java domain to the database domain.
     *
     * @param value  the value to convert
     * @return       the converted value
     */
    DB_TYPE toDatabaseType(JAVA_TYPE value);

    /**
     * Returns an identity type mapper.
     *
     * @param <T>  the type of the identity type mapper
     * @return     an identity type mapper
     */
    static <T> TypeMapper<T, T> identity() {
        return new IdentityTypeMapper<>();
    }
    
    /**
     * Returns an primitive type mapper.
     *
     * @param <T>  the wrapper type of the primitive type mapper
     * @return     an primitive type mapper
     */
    static <T> TypeMapper<T, T> primitive() {
        return new PrimitiveTypeMapper<>();
    }
}