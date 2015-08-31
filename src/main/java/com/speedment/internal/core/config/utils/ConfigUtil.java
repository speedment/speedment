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
package com.speedment.internal.core.config.utils;

import com.speedment.config.Dbms;
import com.speedment.config.Schema;
import com.speedment.config.Column;
import com.speedment.config.Table;
import com.speedment.exception.SpeedmentException;
import static com.speedment.internal.util.StaticClassUtil.instanceNotAllowed;
import groovy.lang.Closure;
import static java.util.Objects.requireNonNull;
import java.util.function.Supplier;
import static java.util.Objects.requireNonNull;

/**
 * Utility class with static methods for finding columns and tables from various
 * configuration nodes.
 *
 * @author pemi
 */
public final class ConfigUtil {

    /**
     * Locates the {@link Column} node in the specified {@link Table} with the
     * specified name. If no column was found, a {@link SpeedmentException} is
     * thrown.
     *
     * @param currentTable the table to look in
     * @param columnName the column name to look for
     * @return the column found
     * @throws SpeedmentException if no column was found
     */
    public static Column findColumnByName(Table currentTable, String columnName)
        throws SpeedmentException {
        requireNonNull(currentTable);
        requireNonNull(columnName);

        return currentTable
            .streamOf(Column.class)
            .filter(c -> c.getName().equals(columnName))
            .findAny()
            .orElseThrow(thereIsNo(Column.class, Table.class, columnName));
    }

    /**
     * Locates the {@link Table} node in the specified {@link Schema} with the
     * specified name. If no table was found, a {@link SpeedmentException} is
     * thrown.
     *
     * @param currentSchema the schema to look in
     * @param tableName the table name to look for
     * @return the table found
     * @throws SpeedmentException if no table was found
     */
    public static Table findTableByName(Schema currentSchema, String tableName)
        throws SpeedmentException {
        requireNonNull(currentSchema);
        requireNonNull(tableName);

        final String[] paths = tableName.split("\\.");

        switch (paths.length) {

            case 1:
                // Just the name of the table
                return currentSchema
                    .stream()
                    .filter(c -> c.getName().equals(tableName))
                    .findAny()
                    .orElseThrow(thereIsNo(Table.class, Schema.class, tableName));

            case 2:
                // The name is like "schema.table"
                final String schemaPart = paths[0];
                final String tablePart = paths[1];

                final Dbms dbms = currentSchema
                    .ancestor(Dbms.class)
                    .orElseThrow(thereIsNo(Dbms.class, Table.class, tablePart));

                final Schema otherSchema = dbms
                    .stream()
                    .filter(t -> t.getName().equals(schemaPart))
                    .findAny()
                    .orElseThrow(thereIsNo(Schema.class, Dbms.class, schemaPart));

                return otherSchema
                    .stream()
                    .filter(t -> t.getName().equals(tablePart))
                    .findAny()
                    .orElseThrow(thereIsNo(Table.class, Schema.class, tablePart));
            default:
                // Otherwise something is wrong with the input.
                throw new SpeedmentException("Table name is malformed.");
        }
    }

    /**
     * Uses the specified {@code createAndAdder} to produce a new node and sets
     * it as a delegate in the specified groovy closure. The produced node is
     * then returned.
     *
     * @param <S> the type of the node to produce
     * @param closure the groovy closure
     * @param createAndAdder the method to use for creating and adding new node
     * instances
     * @return the produced instance
     */
    public static <S> S groovyDelegatorHelper(Closure<?> closure, Supplier<S> createAndAdder) {
        requireNonNull(closure);
        requireNonNull(createAndAdder);

        final S result = createAndAdder.get();
        closure.setDelegate(result);
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        closure.call();
        return result;
    }

    /**
     * Creates a supplier that can be called to create
     * {@link SpeedmentException} that explains that there is no nodes of one
     * type for the other type by the specified name.
     *
     * @param nodeType the node type
     * @param parentType the parent type
     * @param name the name to look for
     * @return the exception supplier
     */
    public static Supplier<SpeedmentException> thereIsNo(Class<?> nodeType, Class<?> parentType, String name) {
        requireNonNull(nodeType);
        requireNonNull(parentType);
        requireNonNull(name);
        
        return () -> new SpeedmentException(
            "There is no "
            + nodeType.getSimpleName()
            + " for the "
            + parentType.getSimpleName()
            + " named "
            + name
        );
    }

    /**
     * Utility classes should not be instantiated.
     */
    private ConfigUtil() {
        instanceNotAllowed(getClass());
    }
}
