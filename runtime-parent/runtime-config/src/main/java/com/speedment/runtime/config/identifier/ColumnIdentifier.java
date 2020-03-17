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
package com.speedment.runtime.config.identifier;

import com.speedment.runtime.config.identifier.trait.HasColumnId;
import com.speedment.runtime.config.identifier.trait.HasDbmsId;
import com.speedment.runtime.config.identifier.trait.HasSchemaId;
import com.speedment.runtime.config.identifier.trait.HasTableId;
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
extends HasDbmsId,
        HasSchemaId,
        HasTableId,
        HasColumnId {

    /**
     * Internal class only used to hide a map of interned instances.
     */
    class Hidden {
        private Hidden() { }
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
     * @param dbmsId    the dbms database id
     * @param schemaId  the schema database id
     * @param tableId   the table database id
     * @param columnId  the column database id
     * @param <ENTITY>  the entity type
     * @return          the identifier instance
     *
     * @since 3.0.15
     */
    static <ENTITY> ColumnIdentifier<ENTITY> of(
            final String dbmsId,
            final String schemaId,
            final String tableId,
            final String columnId) {

        final ColumnIdentifier<ENTITY> newId = new ColumnIdentifierImpl<>(
            dbmsId, schemaId, tableId, columnId);

        ColumnIdentifier.Hidden.INTERNED.putIfAbsent(newId, newId);

        @SuppressWarnings("unchecked")
        final ColumnIdentifier<ENTITY> result = (ColumnIdentifier<ENTITY>)
            ColumnIdentifier.Hidden.INTERNED.get(newId);

        return result;
    }

    /**
     * Returns a {@link TableIdentifier} that has the same {@code dbmsId},
     * {@code schemaId} and {@code tableId} as this
     * {@link ColumnIdentifier}.
     *
     * @return  the table identifier
     */
    default TableIdentifier<ENTITY> asTableIdentifier() {
        return TableIdentifier.of(getDbmsId(), getSchemaId(), getTableId());
    }

    /**
     * Returns a label that can be used as key in a map. Using the ColumnIdentifier itself
     * does not work since there are generated enums as well as runtime created ColumnIdentifierImpls
     * that are considered to be equal but have different hashCode.
     *
     * All implementations of this interface will, however, have equal
     * {@code label()} iff they refer to the same column.
     *
     * @return a ColumnLabel that is unique for this particular ColumnIdentifier
     */
    default ColumnLabel label() {
        return ColumnLabel.of(this);
    }
}