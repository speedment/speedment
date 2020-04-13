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
package com.speedment.runtime.config;

import com.speedment.runtime.config.internal.ForeignKeyColumnImpl;
import com.speedment.runtime.config.mutator.DocumentMutator;
import com.speedment.runtime.config.mutator.ForeignKeyColumnMutator;
import com.speedment.runtime.config.trait.HasColumn;
import com.speedment.runtime.config.trait.HasDeepCopy;
import com.speedment.runtime.config.trait.HasId;
import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.runtime.config.trait.HasMutator;
import com.speedment.runtime.config.trait.HasName;
import com.speedment.runtime.config.trait.HasOrdinalPosition;
import com.speedment.runtime.config.trait.HasParent;
import com.speedment.runtime.config.util.DocumentUtil;

import java.util.Map;
import java.util.Optional;

import static com.speedment.runtime.config.util.DocumentUtil.newNoSuchElementExceptionFor;

/**
 * A typed {@link Document} that represents the column referenced by a foreign
 * key instance in the database. A {@code ForeignKeyColumn} is located inside a
 * {@link ForeignKey}.
 *
 * @author  Emil Forslund
 * @since   2.0.0
 */
public interface ForeignKeyColumn extends
    Document,
    HasParent<ForeignKey>,
    HasDeepCopy,
    HasId,    
    HasName,
    HasOrdinalPosition,
    HasColumn,
    HasMainInterface,
    HasMutator<ForeignKeyColumnMutator<? extends ForeignKeyColumn>> {

    /**
     * Returns the name of the foreign database referenced by this column.
     * 
     * @return the name of the foreign database
     */
    default String getForeignDatabaseName() {
    	return getAsString(ForeignKeyColumnUtil.FOREIGN_DATABASE_NAME)
    			.orElseThrow(newNoSuchElementExceptionFor(this, ForeignKeyColumnUtil.FOREIGN_DATABASE_NAME));
    }
    
    /**
     * Returns the name of the foreign schema(depends on DBMS type) referenced by this column.
     * 
     * @return the name of the foreign schema
     */
    default String getForeignSchemaName() {
    	return getAsString(ForeignKeyColumnUtil.FOREIGN_SCHEMA_NAME)
    			.orElseThrow(newNoSuchElementExceptionFor(this, ForeignKeyColumnUtil.FOREIGN_SCHEMA_NAME));
    }
    
    /**
     * Returns the name of the foreign column referenced by this column.
     *
     * @return the name of the foreign column
     */
    default String getForeignTableName() {
        return getAsString(ForeignKeyColumnUtil.FOREIGN_TABLE_NAME)
            .orElseThrow(newNoSuchElementExceptionFor(this, ForeignKeyColumnUtil.FOREIGN_TABLE_NAME));
    }

    /**
     * Returns the name of the foreign table referenced by this column.
     *
     * @return the name of the foreign table
     */
    default String getForeignColumnName() {
        return getAsString(ForeignKeyColumnUtil.FOREIGN_COLUMN_NAME)
            .orElseThrow(newNoSuchElementExceptionFor(this, ForeignKeyColumnUtil.FOREIGN_COLUMN_NAME));
    }

    /**
     * A helper method for accessing the foreign {@link Table} referenced by
     * this key.
     *
     * @return the foreign {@link Table} referenced by this
     */
    default Optional<? extends Table> findForeignTable() {
        final Optional<Schema> schema = ancestors()
            .filter(Schema.class::isInstance)
            .map(Schema.class::cast)
            .findFirst();

        return schema.flatMap(s -> s.tables()
            .filter(tab -> tab.getId().equals(getForeignTableName()))
            .findAny()
        );
    }

    /**
     * A helper method for accessing the foreign {@link Column} referenced by
     * this key.
     *
     * @return the foreign {@link Column} referenced by this
     */
    default Optional<? extends Column> findForeignColumn() {
        return findForeignTable()
            .flatMap(table -> table.columns()
                .filter(col -> col.getId().equals(getForeignColumnName()))
                .findAny()
            );
    }

    @Override
    default Class<ForeignKeyColumn> mainInterface() {
        return ForeignKeyColumn.class;
    }

    @Override
    default ForeignKeyColumnMutator<? extends ForeignKeyColumn> mutator() {
        return DocumentMutator.of(this);
    }

    @Override
    default ForeignKeyColumn deepCopy() {
        return DocumentUtil.deepCopy(this, ForeignKeyColumnImpl::new);
    }

    /**
     * Creates and returns a new standard implementation of a {@link ForeignKeyColumn}
     * with the given {@code parent} and {@code data}
     *
     * @param parent of the config document (nullable)
     * @param data of the config document
     * @return new {@link ForeignKeyColumn} with the given parameters
     *
     * @throws NullPointerException if the provided {@code data} is {@code null}
     */
    static ForeignKeyColumn create(ForeignKey parent, Map<String, Object> data) {
        return new ForeignKeyColumnImpl(parent, data);
    }
}
