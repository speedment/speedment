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
package com.speedment.config.db;

import com.speedment.annotation.Api;
import com.speedment.config.Document;
import com.speedment.config.db.mapper.TypeMapper;
import com.speedment.config.db.trait.HasAlias;
import com.speedment.config.db.trait.HasEnabled;
import com.speedment.config.db.trait.HasName;
import com.speedment.config.db.trait.HasParent;
import com.speedment.exception.SpeedmentException;
import com.speedment.config.db.trait.HasMainInterface;
import com.speedment.config.db.trait.HasMutator;
import com.speedment.config.db.trait.HasOrdinalPosition;
import com.speedment.config.db.mutator.ColumnMutator;
import com.speedment.config.db.mutator.DocumentMutator;
import static com.speedment.internal.util.document.DocumentUtil.newNoSuchElementExceptionFor;
import com.speedment.config.db.trait.HasNullable;

/**
 * A typed {@link Document} that represents a column in the database. A
 * {@code Column} is located inside a {@link Table}.
 * 
 * @author  Emil Forslund
 * @since   2.0
 */
@Api(version = "2.3")
public interface Column extends
        Document,
        HasParent<Table>,
        HasEnabled,
        HasName,
        HasAlias,
        HasNullable,
        HasOrdinalPosition,
        HasMainInterface,
        HasMutator<ColumnMutator> {

    final String AUTO_INCREMENT = "autoIncrement",
            TYPE_MAPPER         = "typeMapper",
            DATABASE_TYPE       = "databaseType";

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
     * Returns the name of the mapper class that will be used to generate a java
     * representation of the database types.
     *
     * @return the mapper class
     */
    default String getTypeMapper() {
        return getAsString(TYPE_MAPPER).orElseThrow(newNoSuchElementExceptionFor(this, TYPE_MAPPER));
    }

    /**
     * Returns the mapper class that will be used to generate a java
     * representation of the database types.
     *
     * @return the mapper class
     */
    default TypeMapper<?, ?> findTypeMapper() {
        final String name = getTypeMapper();

        try {
            @SuppressWarnings("unchecked")
            final TypeMapper<?, ?> mapper = (TypeMapper<?, ?>) Class.forName(name).newInstance();
            return mapper;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            throw new SpeedmentException("Could not instantiate TypeMapper: '" + name + "'.", ex);
        }
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
     * Returns the class that represents the database type.
     *
     * @return the database type
     */
    default Class<?> findDatabaseType() {
        final String name = getDatabaseType();

        try {
            return Class.forName(name);
        } catch (ClassNotFoundException ex) {
            throw new SpeedmentException("Could not find database type: '" + name + "'.", ex);
        }
    }

    @Override
    default Class<Column> mainInterface() {
        return Column.class;
    }

    @Override
    default ColumnMutator mutator() {
        return DocumentMutator.of(this);
    }
}