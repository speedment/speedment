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
package com.speedment.runtime.config.db.mapper;

import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.component.TypeMapperComponent;
import java.util.Comparator;
import static java.util.Comparator.comparing;

/**
 * A type mapper contains logic for converting between the database and the java
 * type for a field. Implementations of this class should be installed in the
 * {@link TypeMapperComponent}.
 *
 * @param <DB_TYPE> the type as it is represented in the JDBC driver
 * @param <JAVA_TYPE> the type as it should be represented in generated code
 *
 * @author Emil Forslund
 * @since 2.2
 */
@Api(version = "2.3")
public interface TypeMapper<DB_TYPE, JAVA_TYPE> {

    /**
     * The standard comparator to use for instances of the {@link TypeMapper}
     * interface. This comparator will use the name of the database type as
     * comparison index and if two mappers share the same database type, it will
     * use the label in alphabetical order.
     */
    final Comparator<TypeMapper<?, ?>> COMPARATOR
        = comparing(TypeMapper<?, ?>::getDatabaseType, comparing(Class<?>::getSimpleName))
        .thenComparing(TypeMapper::getLabel);

    /**
     * Returns the label for this mapper that should appear to the end user.
     *
     * @return the label
     */
    default String getLabel() {
        return getDatabaseType().getSimpleName()
            + " to " + getJavaType().getSimpleName();
    }

    /**
     * Returns the type as it should be represented in generated code.
     *
     * @return the type
     */
    Class<JAVA_TYPE> getJavaType();

    /**
     * Returns the type as it is represented in the JDBC driver.
     *
     * @return the type
     */
    Class<DB_TYPE> getDatabaseType();

    /**
     * Converts a value from the database domain to the java domain.
     *
     * @param value the value to convert
     * @return the converted value
     */
    JAVA_TYPE toJavaType(DB_TYPE value);

    /**
     * Converts a value from the java domain to the database domain.
     *
     * @param value the value to convert
     * @return the converted value
     */
    DB_TYPE toDatabaseType(JAVA_TYPE value);

    /**
     * Returns if this is an identity mapper. An identity mapper is a one-to-one
     * mapper between the JDBC and the generated code type. For example, a
     * {@code String} to {@code String} is an indentity mapper.
     *
     * @return {@code true} if identity mapper, else {@code false}
     */
    boolean isIdentityMapper();

    /**
     * Returns if this mapper may use an approximation when converting from one
     * form to the other.
     *
     * @return if this mapper may use an approximation when converting from one
     * form to the other
     */
    default boolean isApproximation() {
        return false;
    }

}
