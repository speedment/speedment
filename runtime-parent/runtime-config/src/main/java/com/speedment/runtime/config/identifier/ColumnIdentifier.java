/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.config.identifier;

import com.speedment.runtime.config.identifier.trait.HasColumnName;
import com.speedment.runtime.config.identifier.trait.HasDbmsName;
import com.speedment.runtime.config.identifier.trait.HasSchemaName;
import com.speedment.runtime.config.identifier.trait.HasTableName;
import com.speedment.runtime.config.internal.identifier.ColumnIdentifierImpl;
import com.speedment.runtime.config.util.DocumentDbUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Identifies a particular Column. The identifier is an immutable non-complex
 * object that only contains the names of the nodes required to uniquely
 * identify it in the database.
 * <p>
 * To find the actual documents referred to by the identifier, the following
 * utility methods can be used:
 * <ul>
 * <li>DocumentDbUtil#referencedColumn(Project, ColumnIdentifier)
 * <li>DocumentDbUtil#referencedTable(Project, Project, ColumnIdentifier)
 * <li>DocumentDbUtil#referencedSchema(Project, Project, ColumnIdentifier)
 * <li>DocumentDbUtil#referencedDbms(Project, ColumnIdentifier)
 * </ul>
 *
 * @param <ENTITY> the entity type
 *
 * @author Emil Forslund
 * @since  2.3
 * 
 * @see DocumentDbUtil
 */
public interface ColumnIdentifier<ENTITY> 
extends HasDbmsName,
        HasSchemaName,
        HasTableName,
        HasColumnName {

    /**
     * Internal class only used to hide a map of interned instances.
     */
    class Hidden {
        private static final Map<ColumnIdentifier<?>, ColumnIdentifier<?>>
            INTERNED = new ConcurrentHashMap<>();
    }

    /**
     * Returns a dynamic {@link ColumnIdentifier} based on the default
     * implementation of the interface. Note that the
     * {@link ColumnIdentifier}-interface is usually implemented using a
     * generated {@code enum}. This method should therefore only be used if a
     * generated instance is not possible to use.
     * <p>
     * The returned instance has a well defined {@link Object#hashCode()} and
     * {@link Object#equals(Object)} method so it is safe to use as the key of a
     * map.
     *
     * @param dbmsName    the dbms database name
     * @param schemaName  the schema database name
     * @param tableName   the table database name
     * @param columnName  the column database name
     * @param <ENTITY>    the entity type
     * @return            the identifier instance
     *
     * @since 3.0.15
     */
    static <ENTITY> ColumnIdentifier<ENTITY> of(
            final String dbmsName,
            final String schemaName,
            final String tableName,
            final String columnName) {

        final ColumnIdentifier<ENTITY> newId = new ColumnIdentifierImpl<>(
            dbmsName, schemaName, tableName, columnName);

        ColumnIdentifier.Hidden.INTERNED.putIfAbsent(newId, newId);

        @SuppressWarnings("unchecked")
        final ColumnIdentifier<ENTITY> result = (ColumnIdentifier<ENTITY>)
            ColumnIdentifier.Hidden.INTERNED.get(newId);

        return result;
    }

    /**
     * Returns a {@link TableIdentifier} that has the same {@code dbmsName},
     * {@code schemaName} and {@code tableName} as this
     * {@link ColumnIdentifier}.
     *
     * @return  the table identifier
     */
    default TableIdentifier<ENTITY> asTableIdentifier() {
        return TableIdentifier.of(getDbmsName(), getSchemaName(), getTableName());
    }
}