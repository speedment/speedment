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
package com.speedment.internal.util.sql;

import com.speedment.config.Table;
import com.speedment.db.crud.Create;
import com.speedment.db.crud.CrudOperation;
import static com.speedment.db.crud.CrudOperation.Type.CREATE;
import static com.speedment.db.crud.CrudOperation.Type.DELETE;
import static com.speedment.db.crud.CrudOperation.Type.READ;
import static com.speedment.db.crud.CrudOperation.Type.UPDATE;
import com.speedment.db.crud.Selective;
import com.speedment.db.crud.Selector;
import com.speedment.db.crud.Valued;
import static com.speedment.internal.util.StaticClassUtil.instanceNotAllowed;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;


/**
 * Utility class for converting CRUD operations into SQL strings.
 *
 * @author Emil Forslund
 */
public final class SqlWriter {

//    /**
//     * Prepares an SQL statement for the specified CRUD operation.
//     *
//     * @param con        the connection
//     * @param operation  the CRUD operation
//     * @return           the prepared statement
//     */
//    public static PreparedStatement prepare(Connection con, CrudOperation operation) {
//        try {
//            return con.prepareStatement(toSql(operation));
//        } catch (SQLException ex) {
//            throw new SpeedmentException("Failed to parse SQL string into a PreparedStatement.", ex);
//        }
//    }

//    /**
//     * Converts the specified CRUD operation to an SQL string. This is a shortcut for the four methods
//     * <ul>
//     *     <li>{@link #create(Create)}
//     *     <li>{@link #read(Read)}
//     *     <li>{@link #update(Update)}
//     *     <li>{@link #delete(Delete)}
//     * </ul>
//     *
//     * @param operation  the operation to convert into SQL
//     * @return           the SQL string
//     */
//    public static String toSql(CrudOperation operation) {
//        switch (operation.getType()) {
//            case CREATE : return create((Create) operation);
//            case READ   : return read((Read) operation);
//            case UPDATE : return update((Update) operation);
//            case DELETE : return delete((Delete) operation);
//            default : throw new UnsupportedOperationException(
//                "Unknown CRUD operation type '" + operation.getType().name() + "'."
//            );
//        }
//    }

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

//    /**
//     * Creates an SQL query that represents the specified CRUD command.
//     *
//     * @param read  the command to render
//     * @return      the SQL query
//     */
//    public static String read(Read read) {
//        return buildOperation(read)
//            .append(buildSelection(read))
//            .append(buildLimit(read))
//            .append(";")
//            .toString();
//    }

//    /**
//     * Creates an SQL query that represents the specified CRUD command.
//     * <p>
//     * Values will not be written in plain text but replaced with '?' characters.
//     * To get a list of values, call the {@link #values(Operation)} method.
//     *
//     * @param update  the command to render
//     * @return        the SQL query
//     */
//    public static String update(Update update) {
//        return buildOperation(update)
//            .append(
//                update.getValues().entrySet().stream()
//                    .map(e -> formatColumnName(e.getKey()) + " = ?")
//                    .collect(joining(", "))
//            )
//            .append(buildSelection(update))
//            .append(buildLimit(update))
//            .append(";").toString();
//    }

//    /**
//     * Creates an SQL query that represents the specified CRUD command.
//     *
//     * @param delete  the command to render
//     * @return        the SQL query
//     */
//    public static String delete(Delete delete) {
//        return buildOperation(delete)
//            .append(buildSelection(delete))
//            .append(buildLimit(delete))
//            .append(";")
//            .toString();
//    }

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

//    /**
//     * Builds the selection part of the sql query.
//     *
//     * @param selective  the operation
//     * @return           the selection part
//     */
//    private static String buildSelection(Selective selective) {
//        return selective.getSelectors()
//            .map(sel ->
//                formatColumnName(sel.getColumnName()) +
//                formatComparableOperator(sel.getPredicateType())
//            )
//            .collect(joining(" AND ", " WHERE ", ""));
//    }

    /**
     * Builds the limit part of the sql query.
     *
     * @param selective  the operation
     * @return           the limit part
     */
    private static Optional<String> buildLimit(Selective selective) {
        final long limit = selective.getLimit();

        if (limit > 0 && limit != Long.MAX_VALUE) {
            return Optional.of(" LIMIT " + limit);
        } else return Optional.empty();
    }

    /**
     * Returns the name of the specified table formatted as appropriate for use in an SQL query.
     *
     * @param table  the table
     * @return       the formatted table name
     */
    private static String formatTableName(Table table) {
        return table.getTableName().orElse(table.getName());
    }

    /**
     * Returns the name of the specified column formatted as appropriate for use in an SQL query.
     *
     * @param column  the column
     * @return        the formatted column name
     */
    private static String formatColumnName(String column) {
        return "`" + column + "`";
    }
    
//    /**
//     * Returns a string representation of the specified operator and operand 
//     * formatted as appropriate in SQL.
//     *
//     * @param predicateType  the operator
//     * @return          the formatted text
//     */
//    private static String formatUnaryOperator(PredicateType predicateType) {
//        if (predicateType instanceof StandardUnaryOperator) {
//            @SuppressWarnings("unchecked")
//            final StandardUnaryOperator op = (StandardUnaryOperator) predicateType;
//            
//            switch (op) {
//                case IS_NULL     : return " = NULL";
//                case IS_NOT_NULL : return " <> NULL";
//            }
//        }
//        
//        throw new UnsupportedOperationException("Unknown unary operator '" + predicateType + "'.");
//    }
//    
//    /**
//     * Returns a string representation of the specified operator and operand 
//     * formatted as appropriate in SQL.
//     *
//     * @param operator  the operator
//     * @return          the formatted text
//     */
//    private static String formatComparableOperator(ComparableOperator operator) {
//        if (operator instanceof StandardComparableOperator) {
//            @SuppressWarnings("unchecked")
//            final StandardComparableOperator op = (StandardComparableOperator) operator;
//            
//            switch (op) {
//                case EQUAL            : return " = ?";
//                case NOT_EQUAL        : return " <> ?";
//                case LESS_THAN        : return " < ?";
//                case LESS_OR_EQUAL    : return " <= ?";
//                case GREATER_THAN     : return " > ?";
//                case GREATER_OR_EQUAL : return " >= ?";
//                default : throw new UnsupportedOperationException("Unknown comparable operator '" + op.name() + "'.");
//            }
//        }
//        
//        throw new UnsupportedOperationException("Unknown comparable operator '" + operator + "'.");
//    }
//    
//    /**
//     * Returns a string representation of the specified operator and operand 
//     * formatted as appropriate in SQL.
//     *
//     * @param operator  the operator
//     * @return          the formatted text
//     */
//    private static String formatStringOperator(StringOperator operator) {
//        if (operator instanceof StandardStringOperator) {
//            @SuppressWarnings("unchecked")
//            final StandardStringOperator op = (StandardStringOperator) operator;
//            
//            switch (op) {
//                case STARTS_WITH            : return " LIKE ?%";
//                case ENDS_WITH              : return " LIKE %?";
//                case CONTAINS               : return " LIKE %?%";
//                case EQUAL_IGNORE_CASE      : return " = ?";
//                case NOT_EQUAL_IGNORE_CASE  : return " <> ?";
//                default : throw new UnsupportedOperationException("Unknown string operator '" + op.name() + "'.");
//            }
//        }
//        
//        throw new UnsupportedOperationException("Unknown string operator '" + operator + "'.");
//    }

    /**
     * Utility classes should not be instantiated.
     */
    private SqlWriter() { instanceNotAllowed(getClass()); }
}