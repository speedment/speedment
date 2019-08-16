/**
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.core.db;

import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Schema;
import com.speedment.runtime.core.db.metadata.TypeInfoMetaData;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.speedment.common.mapstream.MapStream.comparing;
import static com.speedment.runtime.core.db.SqlPredicateFragment.of;

/**
 * The {@code DbmsType} interface defines unique properties for different Dbms
 * types. By implementing a new {@code DbmsType} and perhaps a new
 * {@code DbmsHandler}, one may easily implement support for new Dbms vendor
 * types.
 *
 * @author Per Minborg
 * @since  2.0.0
 */
public interface DbmsType {

    /**
     * The type of connection that this {@link DbmsType} uses. Most JDBC
     * connectors opens a socket to a specific port at some remote host.
     * However, there are lightweight databases that runs the entire database
     * engine in the connector and that only requires a file or group of files
     * to work with.
     */
    enum ConnectionType {

        /**
         * Speedment connects to the database using a
         * {@link Dbms#getIpAddress() host} and a {@link Dbms#getPort()}.
         */
        HOST_AND_PORT,

        /**
         * Speedment connects to the database by reading a file from a
         * {@link Dbms#getLocalPath() local path}.
         */
        DBMS_AS_FILE
    }

    Comparator<DbmsType> COMPARATOR = comparing(DbmsType::getName);

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
     * Returns the default name for this {@code DbmsType}. For example ‘orcl'
     * (Oracle)
     *
     * @return the default dbms name
     */
    Optional<String> getDefaultDbmsName();

    /**
     * Returns the default name for the {@link Schema} when this
     * {@code DbmsType} is used.
     *
     * @return  the default schema name
     */
    default Optional<String> getDefaultSchemaName() {
        return Optional.empty();
    }

    /**
     * Returns {@code true} if this {@link DbmsType} uses named schemas as part
     * of the database structure. If {@code false}, then the Speedment Config
     * model doesn't expect the {@link Schema#getName()} to have any meaning
     * when referring to tables.
     * <p>
     * By default, this method returns {@code true}.
     *
     * @return  {@code true} if schema names are meaningful, else {@code false}
     */
    default boolean hasSchemaNames() {
        return true;
    }

    /**
     * Returns {@code true} if this {@link DbmsType} uses named databases as
     * part of the database structure. If {@code false}, then the Speedment
     * Config model doesn't expect the {@link Dbms#getName()} to have any
     * meaning when referring to tables.
     * <p>
     * By default, this method returns {@code true}.
     *
     * @return  {@code true} if dbms names are meaningful, else {@code false}
     */
    default boolean hasDatabaseNames() {
        return true;
    }

    /**
     * Returns {@code true} if this {@link DbmsType} uses authentication with
     * username and password and {@code false} otherwise. Some database managers
     * don't require authentication since they are only intended to be used for
     * tests or because security is handled at a different level.
     *
     * @return  {@code true} if authentication is supported, otherwise
     *          {@code false}
     */
    default boolean hasDatabaseUsers() {
        return true;
    }

    /**
     * Returns if this {@code DbmsType} is supported by Speedment in the current
     * implementation and with the current state of class path. Currently, this method
     * returns true iff the needed third party dependencies are available.
     *
     * @return if this {@code DbmsType} is supported by Speedment in the current
     * implementation and with the current state of class path
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
     * Returns the connection type used in this {@code DbmsType}.
     *
     * @return  the connection type
     */
    default ConnectionType getConnectionType() {
        return ConnectionType.HOST_AND_PORT;
    }

    /**
     * Returns the naming convention used by this database.
     *
     * @return the naming convention
     */
    DatabaseNamingConvention getDatabaseNamingConvention();

    /**
     * Returns the handler responsible for loading the metadata when running
     * this type of database.
     *
     * @return the {@link DbmsMetadataHandler}
     */
    DbmsMetadataHandler getMetadataHandler();

    /**
     * Returns the handler responsible for running queries to databases of this
     * type.
     *
     * @return the implementation type of the {@link DbmsOperationHandler}
     */
    DbmsOperationHandler getOperationHandler();

    /**
     * Returns the handler responsible for column inclusion/exclusion in queries
     * to databases
     *
     * @return the implementation type of the {@link DbmsColumnHandler}
     */
    DbmsColumnHandler getColumnHandler();

    /**
     * Returns the result set table schema.
     *
     * @return the result set table schema.
     */
    String getResultSetTableSchema();

    /**
     * Returns the ConnectionUrlGenerator for this database. A
     * ConnectionUrlGenerator can create a default connection URL given a number
     * of parameters.
     *
     * @return the ConnectionUrlGenerator for this database.
     */
    ConnectionUrlGenerator getConnectionUrlGenerator();

    /**
     * Returns the FieldPredicateView for this database. A FieldPredicateView
     * can render a SQL query given a stream pipeline.
     *
     * @return the FieldPredicateView for this database
     */
    FieldPredicateView getFieldPredicateView();

    /**
     * Returns a pre-defined Set for the TypeInfoMetaData for this database
     * type. Some databases meta data retrieval functions (like PostgreSQL) ate
     * very slow so this is a short cut.
     *
     * @return a pre-defined Set for the TypeInfoMetaData for this database type
     */
    Set<TypeInfoMetaData> getDataTypes();

    /**
     * Returns the initial SQL connection verification query to send to the
     * database during speedment startup.
     *
     * @return the initial SQL connection verification query to send to the
     * database during speedment startup
     */
    String getInitialQuery();

    /**
     * Returns the COLLATE fragment needed to make ORDER BY statements sort correctly, using default collation
     * @return the COLLATE fragment needed to make ORDER BY statements sort correctly, using default collation
     */
    default SqlPredicateFragment getCollateFragment() {
        return of("");
    }

    enum SkipLimitSupport {
        STANDARD, ONLY_AFTER_SORTED, NONE;
    }

    /**
     * Returns the support for skip and limit (or skip and offset) for this
     * database type.
     *
     * @return the support for skip and limit (or skip and offset) for this
     * database type
     */
    SkipLimitSupport getSkipLimitSupport();

    /**
     * Returns a new String and modifies the provided parameter list (side
     * effect) so that the original SQL query will reflect a query that has an
     * offset (skip) and a limit.
     *
     * @param originalSql original SQL query
     * @param params parameter list
     * @param skip number of rows to skip (OFFSET) (0 = no skip)
     * @param limit number of rows to limit (Long.MAX_VALUE = no limit)
     * @return a new String and modifies the provided list so that the original
     * SQL query will reflect a query that has an offset (skip) and a limit
     */
    String applySkipLimit(String originalSql, List<Object> params, long skip, long limit);

    /**
     * The sub-select alias mode.
     */
    enum SubSelectAlias {
        /**
         * select count(*) from (select id from user) AS A
         */
        REQUIRED,
        /**
         * select count(*) from (select id from user)
         */
        PROHIBITED
    }

    /**
     * Returns the sub-select alias mode for this database type.
     *
     * @return the sub-select alias mode for this database type
     */
    SubSelectAlias getSubSelectAlias();

    /**
     * The sort by null order insertion. E.g. how is null first/last handled
     */
    enum SortByNullOrderInsertion {
        /*
        * ORDER BY col IS NULL, col DESC
         */
        PRE,
        /*
        * ORDER BY CASE WHEN col IS NULL THEN 0 ELSE 1 END, col DESC
         */
        PRE_WITH_CASE,
        /*
        * ORDER BY col DESC NULLS FIRST
         */
        POST;

    }

    /**
     * Returns the SortByNullOrderInsertion mode for this database type.
     *
     * @return the SortByNullOrderInsertion mode for this database type
     */
    SortByNullOrderInsertion getSortByNullOrderInsertion();

}
