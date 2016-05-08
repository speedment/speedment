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
package com.speedment.runtime.config.db.parameter;

import com.speedment.runtime.Speedment;
import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.config.db.Dbms;
import com.speedment.runtime.config.db.parameter.DbmsTypeBuilder.WithDbmsNameMeaning;
import com.speedment.runtime.config.db.parameter.DbmsTypeBuilder.WithName;
import com.speedment.runtime.db.ConnectionUrlGenerator;
import com.speedment.runtime.db.DatabaseNamingConvention;
import com.speedment.runtime.db.DbmsHandler;
import com.speedment.runtime.db.metadata.TypeInfoMetaData;
import com.speedment.runtime.internal.core.config.dbms.DbmsTypeImpl;
import com.speedment.runtime.manager.SpeedmentPredicateView;
import static com.speedment.runtime.stream.MapStream.comparing;
import java.util.Comparator;
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
@Api(version = "2.3")
public interface DbmsType {

    final Comparator<DbmsType> COMPARATOR = comparing(DbmsType::getName);

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
     * Returns the naming convention used by this database.
     *
     * @return the naming convention
     */
    DatabaseNamingConvention getDatabaseNamingConvention();

    /**
     * Creates and returns a new {@code DbmsHandler} instance for the given
     * database.
     *
     * @param speedment instance to use
     * @param dbms the Dbms configuration to use
     * @return a new {@code DbmsHandler} instance for the given database
     */
    DbmsHandler makeDbmsHandler(Speedment speedment, Dbms dbms);

    // TODO: Improve javadoc in this file.
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
     * Returns the SpeedmentPredicateView for this database. A
     * SpeedmentPredicateView can render a SQL query given a stream pipeline.
     *
     * @return the SpeedmentPredicateView for this database
     */
    SpeedmentPredicateView getSpeedmentPredicateView();

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
     * Creates and returns a new DbmsType builder. The builder is initialized
     * with default values for some optional parameters.
     *
     * @return a new DbmsType builder
     */
    public static WithName builder() {
        return DbmsTypeImpl.builder();
    }

    public static WithDbmsNameMeaning builder(String name, String driverManagerName, int defaultPort) {
        return builder().withName(name).withDriverManagerName(driverManagerName).withDefaultPort(defaultPort);
    }
}
