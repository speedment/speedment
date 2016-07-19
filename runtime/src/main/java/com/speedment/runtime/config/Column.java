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
package com.speedment.runtime.config;

import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.config.mapper.TypeMapper;
import com.speedment.runtime.config.mutator.ColumnMutator;
import com.speedment.runtime.config.mutator.DocumentMutator;
import com.speedment.runtime.config.trait.HasAlias;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.runtime.config.trait.HasMutator;
import com.speedment.runtime.config.trait.HasName;
import com.speedment.runtime.config.trait.HasNullable;
import com.speedment.runtime.config.trait.HasOrdinalPosition;
import com.speedment.runtime.config.trait.HasParent;
import com.speedment.runtime.exception.SpeedmentException;

import static com.speedment.runtime.internal.util.document.DocumentUtil.newNoSuchElementExceptionFor;
import java.util.Optional;

/**
 * A typed {@link Document} that represents a column in the database. A
 * {@code Column} is located inside a {@link Table}.
 *
 * @author  Emil Forslund
 * @since   2.0.0
 */
@Api(version = "3.0")
public interface Column extends
    Document,
    HasParent<Table>,
    HasEnabled,
    HasName,
    HasAlias,
    HasNullable,
    HasOrdinalPosition,
    HasMainInterface,
    HasMutator<ColumnMutator<? extends Column>> {

    final String 
        AUTO_INCREMENT    = "autoIncrement",
        TYPE_MAPPER       = "typeMapper",
        DATABASE_TYPE     = "databaseType",
        JAVA_TYPE         = "javaType",
        JAVA_TYPE_FACTORY = "javaTypeFactory",
        ENUM_CONSTANTS    = "enumConstants";

    /**
     * Returns whether or not this column will auto increment when new values
     * are added to the table.
     *
     * @return  <code>true</code> if the column auto increments, else
     * <code>false</code>
     */
    default boolean isAutoIncrement() {
        return getAsBoolean(AUTO_INCREMENT).orElse(false);
    }
    
    /**
     * Returns the name of the class that represents the database type.
     *
     * @return the database type class
     */
    default String getDatabaseType() {
        return getAsString(DATABASE_TYPE).orElseThrow(newNoSuchElementExceptionFor(this, DATABASE_TYPE));
    }
    
    /**
     * Returns the name of the class that represents this column in the code.
     * 
     * @return  the java type name
     */
    default String getJavaType() {
        return getAsString(JAVA_TYPE).orElseThrow(newNoSuchElementExceptionFor(this, JAVA_TYPE));
    }
    
    /**
     * Returns the name of the class that is used to produce the java type
     * implementation for this column. If no factory is specified (the java
     * type can be created using its default constructor) then this method 
     * returns an empty {@code Optional}.
     * 
     * @return  the java type factory or empty
     */
    default Optional<String> getJavaTypeFactory() {
        return getAsString(JAVA_TYPE_FACTORY);
    }
    
    /**
     * Returns the name of the mapper class that will be used to generate a java
     * representation of the database types.
     *
     * @return the mapper class
     */
    default Optional<String> getTypeMapper() {
        return getAsString(TYPE_MAPPER);
    }

    /**
     * Returns a comma separated string of the possible values that this column
     * may have. If the list of potential values are not constrained, an empty
     * optional is returned.
     * 
     * @return  list of constant values separated by commas or empty
     */
    default Optional<String> getEnumConstants() {
        return getAsString(ENUM_CONSTANTS);
    }

    /**
     * Returns the class that represents the database type.
     *
     * @return the database type
     */
    default Class<?> findDatabaseType() {
        final String name = getDatabaseType();

        try {
            return Class.forName(name);
        } catch (final ClassNotFoundException ex) {
            throw new SpeedmentException("Could not find database type: '" + name + "'.", ex);
        }
    }
    
    /**
     * Returns the class that represents the java type.
     *
     * @return the java type
     */
    default Class<?> findJavaType() {
        final String name = getJavaType();

        try {
            return Class.forName(name);
        } catch (final ClassNotFoundException ex) {
            throw new SpeedmentException("Could not find database type: '" + name + "'.", ex);
        }
    }
    
    /**
     * Returns the mapper class that will be used to generate a java
     * representation of the database types.
     *
     * @return the mapper class
     */
    default Optional<TypeMapper<?, ?>> findTypeMapper() {
        return getTypeMapper().map(name -> {
            try {
                @SuppressWarnings("unchecked")
                final TypeMapper<?, ?> mapper = (TypeMapper<?, ?>) Class.forName(name).newInstance();
                return mapper;
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
                throw new SpeedmentException("Could not instantiate TypeMapper: '" + name + "'.", ex);
            }
        });
    }

    @Override
    default Class<Column> mainInterface() {
        return Column.class;
    }

    @Override
    default ColumnMutator<? extends Column> mutator() {
        return DocumentMutator.of(this);
    }
}