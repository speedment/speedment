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
package com.speedment.internal.util.sql;

import com.speedment.config.db.Table;
import com.speedment.db.crud.Create;
import com.speedment.db.crud.CrudOperation;
import static com.speedment.db.crud.CrudOperation.Type.CREATE;
import static com.speedment.db.crud.CrudOperation.Type.DELETE;
import static com.speedment.db.crud.CrudOperation.Type.READ;
import static com.speedment.db.crud.CrudOperation.Type.UPDATE;
import com.speedment.db.crud.Selective;
import com.speedment.db.crud.Selector;
import com.speedment.db.crud.Valued;
import static com.speedment.util.StaticClassUtil.instanceNotAllowed;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.joining;


/**
 * Utility class for converting CRUD operations into SQL strings.
 *
 * @author Emil Forslund
 */
public final class SqlWriter {

    /**
     * Creates an SQL query that represents the specified CRUD command.
     * <p>
     * Values will not be written in plain text but replaced with '?' characters.
     * To get a list of values, call the {@link #values(Operation)} method.
     *
     * @param create  the command to render
     * @return        the SQL query
     */
    public static String create(Create create) {
        final StringBuilder str = buildOperation(create);

        final Set<Map.Entry<String, Object>> entries = create.getValues().entrySet();

        str.append(
            entries.stream()
                .map(Map.Entry::getKey)
                .collect(joining(", ", " (", ") VALUES "))
        );

        str.append(
            entries.stream()
                .map(e -> "?")
                .collect(joining(", ", "(", ")"))
        );

        return str.append(";").toString();
    }

    /**
     * Returns a list of the values specified in the operation with the order preserved.
     *
     * @param operation  the operation
     * @return           the list of values
     */
    public static List<Object> values(CrudOperation operation) {
        final List<Object> values = new ArrayList<>();

        if (operation instanceof Valued) {
            final Valued valued = (Valued) operation;
            values.addAll(valued.getValues().values());
        }

        if (operation instanceof Selective) {
            final Selective selective = (Selective) operation;
            values.addAll(selective.getSelectors()
                .map(Selector::getOperand)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList()));
        }

        return values;
    }

    /**
     * Returns a {@code StringBuilder} with the first part of the sql query for the specified operation.
     *
     * @param operation  the operation
     * @return           a builder with the first part of the query
     */
    private static StringBuilder buildOperation(CrudOperation operation) {
        final StringBuilder str = new StringBuilder();

        switch (operation.getType()) {
            case CREATE : str.append("INSERT INTO "); break;
            case READ   : str.append("SELECT * FROM "); break;
            case UPDATE : str.append("UPDATE "); break;
            case DELETE : str.append("DELETE FROM "); break;
            default : throw new UnsupportedOperationException(
                "Unknown CRUD operation type '" + operation.getType().name() + "'."
            );
        }

        str.append(formatTableName(operation.getTable()));

        if (operation.getType() == CrudOperation.Type.UPDATE) {
            str.append(" SET ");
        }

        return str;

    }

    /**
     * Returns the name of the specified table formatted as appropriate for use in an SQL query.
     *
     * @param table  the table
     * @return       the formatted table name
     */
    private static String formatTableName(Table table) {
        return table.getName();
    }

    /**
     * Utility classes should not be instantiated.
     */
    private SqlWriter() { instanceNotAllowed(getClass()); }
}