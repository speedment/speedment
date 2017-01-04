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
package com.speedment.runtime.core.internal.util.sql;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.PrimaryKeyColumn;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.core.db.DatabaseNamingConvention;
import com.speedment.runtime.core.db.DbmsOperationHandler;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.exception.SpeedmentException;

import java.util.Collections;
import java.util.function.Function;

import static com.speedment.common.invariant.NullUtil.requireNonNulls;
import static com.speedment.runtime.core.util.StaticClassUtil.instanceNotAllowed;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 *
 * @author pemi
 */
public final class SqlUtil {
    
    /**
     * Returns a {@code SELECT/FROM} SQL statement with the full column list and
     * the current table specified in accordance to the current
     * {@link DbmsType}. The specified statement will not have any trailing
     * spaces or semicolons.
     * <p>
     * <b>Example:</b>
     * <code>SELECT `id`, `name` FROM `myschema`.`users`</code>
     *
     * @param naming  the current naming convention
     * @param table   the table to get columns from
     * @return the SQL statement
     */
    public static String sqlSelect(DatabaseNamingConvention naming, Table table) {
        return "SELECT " + sqlColumnList(naming, table) + " FROM " + sqlTableReference(naming, table);
    }

    public static String sqlParseValue(final String value) {
        // value nullable
        if (value == null) {
            return "NULL";
        }
        return "'" + value.replaceAll("'", "''") + "'";
    }

    public static String unQuote(final String s) {
        requireNonNull(s);
        if (s.startsWith("\"") && s.endsWith("\"")) {
            // Un-quote the name
            return s.substring(1, s.length() - 1);
        }
        return s;
    }
    
    public static String sqlTableReference(DatabaseNamingConvention naming, Table table) {
        requireNonNulls(naming, table);
        return naming.fullNameOf(table);
    }
    
    /**
     * Returns a comma separated list of column names, fully formatted in
     * accordance to the current {@link DbmsType}.
     *
     * @param naming  the current naming convention
     * @param table   the table to get columns from
     * @return the comma separated column list
     */
    public static String sqlColumnList(DatabaseNamingConvention naming, Table table) {
        requireNonNulls(naming, table);
        return sqlColumnList(naming, table, Function.identity());
    }

    /**
     * Returns a {@code AND} separated list of {@link PrimaryKeyColumn} database
     * names, formatted in accordance to the current {@link DbmsType}.
     *
     * @param naming  the current naming convention
     * @param table   the table to get columns from
     * @param postMapper mapper to be applied to each column name
     * @return list of fully quoted primary key column names
     */
    public static String sqlColumnList(DatabaseNamingConvention naming, Table table, Function<String, String> postMapper) {
        requireNonNulls(naming, table, postMapper);
        return table.columns()
            .filter(Column::isEnabled)
            .map(Column::getName)
            .map(naming::encloseField)
            .map(postMapper)
            .collect(joining(","));
    }

    /**
     * Returns a {@code AND} separated list of {@link PrimaryKeyColumn} database
     * names, formatted in accordance to the current {@link DbmsType}.
     *
     * @param naming  the current naming convention
     * @param table  the table to format
     * @param postMapper mapper to apply to every column name
     * 
     * @return list of fully quoted primary key column names
     */
    public static String sqlPrimaryKeyColumnList(DatabaseNamingConvention naming, Table table, Function<String, String> postMapper) {
        requireNonNulls(naming, table, postMapper);
        return table.primaryKeyColumns()
            .map(SqlUtil::findColumn)
            .map(Column::getName)
            .map(naming::encloseField)
            .map(postMapper)
            .collect(joining(" AND "));
    }
    
    private static Column findColumn(PrimaryKeyColumn pkc) {
        requireNonNull(pkc);
        return pkc.findColumn()
            .orElseThrow(() -> new SpeedmentException(
                "Cannot find column for " + pkc
            ));
    }

    /**
     * Counts the number of elements in the current table by querying the
     * database.
     *
     * @param dbmsOperationHandler  the operation handler
     * @param naming                the naming convention in use
     * @param dbms                  the dbms
     * @param table                 the table
     * 
     * @return the number of elements in the table
     */
    public static long sqlCount(DbmsOperationHandler dbmsOperationHandler, DatabaseNamingConvention naming, Dbms dbms, Table table) {
        return dbmsOperationHandler.executeQuery(dbms,
            "SELECT COUNT(*) FROM " + sqlTableReference(naming, table),
            Collections.emptyList(),
            rs -> rs.getLong(1)
        ).findAny().get();
    }

    /**
     * Utility classes should not be instantiated.
     */
    private SqlUtil() { instanceNotAllowed(getClass()); }
}