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
package com.speedment.api.config.parameters;

import com.speedment.api.Speedment;
import com.speedment.api.annotation.Api;
import com.speedment.api.config.Dbms;
import com.speedment.api.db.DbmsHandler;
import java.util.Optional;
import java.util.Set;

/**
 * The {@code DbmsType} interface defines unique properties for different Dbms
 * types. By implementing a new {@code DbmsType} and perhaps a new
 * {@code DbmsHandler}, one may easily implement support for new Dbms vendor
 * types.
 *
 * @author pemi
 * @since 2.0
 */
@Api(version = "2.1")
public interface DbmsType {

    /**
     * Returns the non-null name for this {@code DbmsType}. For example MySQL or
     * Oracle
     *
     * @return the non-null name for this {@code DbmsType}
     */
    String getName();

    /**
     * Returns the non-null Driver Manager Name for this {@code DbmsType}. For
     * example "MySQL-AB JDBC Driver" or "Oracle JDBC Driver"
     *
     * @return the non-null Driver Manager Name
     */
    String getDriverManagerName();

    /**
     * Returns the default port for this {@code DbmsType}. For example 3306
     * (MySQL) or 1521 (Oracle)
     *
     * @return the default port
     */
    int getDefaultPort();

    /**
     * Returns the delimiter used between a Schema and a Table for this
     * {@code DbmsType}. Most {@code DbmsType} are using a "." as a separator.
     *
     * @return the delimiter used between a Schema and a Table
     */
    String getSchemaTableDelimiter();

    /**
     * Returns a textual representation of what the database name is used for.
     * Some databases (notably MySQL) does not use the database name for
     * anything whereas other (such as Oracle) are using the name as an address
     * (i.e. for Oracle the name is used as SID)
     *
     * @return a textual representation of what the database name is used for
     */
    String getDbmsNameMeaning();

    /**
     * Returns if this {@code DbmsType} is supported by Speedment in the current
     * implementation.
     *
     * @return if this {@code DbmsType} is supported by Speedment in the current
     * implementation
     */
    boolean isSupported();

    // Implementation specifics
    /**
     * Returns the non-null fully qualified JDBC class name for this
     * {@code DbmsType}. For example "com.mysql.jdbc.Driver" or
     * "oracle.jdbc.OracleDriver"
     *
     * @return the non-null name for this {@code DbmsType}
     */
    String getDriverName();

    /**
     * Returns a non-null String representation of the default connector
     * parameters to be used by this {@code DbmsType}. The connector parameters
     * can be used to select different modes or to set parameters for the JDBC
     * connection.
     *
     * @return a non-null String representation of the default connector
     * parameters
     */
    Optional<String> getDefaultConnectorParameters();

    /**
     * Returns the non-null JDBC connector name to be used by this
     * {@code DbmsType}. The connector name is the name that is to be placed in
     * the beginning of the JDBC connector string
     * "jdbc:{jdbcConnectorName}://some_host". For example "mysql" or
     * "oracle:thin".
     *
     * @return a non-null String representation of the default connector
     * parameters
     */
    String getJdbcConnectorName();

    /**
     * Returns the non-null field encloser start string. The field encloser
     * start string precedes a database entity name like a table or schema name
     * when quoted. Quoted names are used to avoid that entity names collide
     * with reserved keywords like "key" or "user". So a table named "user" in
     * the "key" schema can be quoted to "key"."user". Examples of values are
     * '`' for MySQL or '"' for Oracle.
     *
     * @return the non-null field encloser start string
     *
     * @see #getFieldEncloserStart(boolean)
     * @see #getFieldEncloserEnd()
     * @see #getFieldEncloserEnd(boolean)
     */
    default String getFieldEncloserStart() {
        return getFieldEncloserStart(false);
    }

    /**
     * Returns the non-null field encloser end string. The field encloser end
     * string follows a database entity name like a table or schema name when
     * quoted. Quoted names are used to avoid that entity names collide with
     * reserved keywords like "key" or "user". So a table named "user" in the
     * "key" schema can be quoted to "key"."user". Examples of values are '`'
     * for MySQL or '"' for Oracle.
     *
     * @return the non-null field encloser end string
     *
     * @see #getFieldEncloserStart(boolean)
     * @see #getFieldEncloserEnd()
     * @see #getFieldEncloserEnd(boolean)
     */
    default String getFieldEncloserEnd() {
        return getFieldEncloserEnd(false);
    }

    /**
     * Returns the non-null field encloser start string. The method parameter
     * denotes if the field encloser is placed within quotes or not. For example
     * for Oracle, since the field encloser is the '"' character itself, it
     * needs to be escaped if within quotes.
     *
     * @param isWithinQuotes if the field encloser is within quotes
     * @return Returns the non-null field encloser start string
     *
     * @see #getFieldEncloserStart()
     */
    String getFieldEncloserStart(final boolean isWithinQuotes);

    /**
     * Returns the non-null field encloser end string. The method parameter
     * denotes if the field encloser is placed within quotes or not. For example
     * for Oracle, since the field encloser is the '"' character itself, it
     * needs to be escaped if within quotes.
     *
     * @param isWithinQuotes if the field encloser is within quotes
     * @return Returns the non-null field encloser start string
     *
     * @see #getFieldEncloserEnd()
     */
    String getFieldEncloserEnd(final boolean isWithinQuotes);

    /**
     * Returns a non-null Set of Strings that represents schema names that are
     * to be excluded when examining a Dbms for schemas. The set typically
     * contains names for system tables and similar things. For example for
     * MySQL, the schemas "MySQL" and "information_schema" are typically
     * excluded.
     *
     * @return a non-null Set of Strings that represents schema names that are
     * to be excluded when examining a Dbms for schemas
     */
    Set<String> getSchemaExcludeSet();

    /**
     * Creates and returns a new {@code DbmsHandler} instance for the given
     * database.
     *
     * @param speedment instance to use
     * @param dbms the Dbms configuration to use
     * @return a new {@code DbmsHandler} instance for the given database
     */
    DbmsHandler makeDbmsHandler(Speedment speedment, Dbms dbms);
}
