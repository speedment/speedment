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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.config.db.parameters;

import com.speedment.Speedment;
import com.speedment.config.db.Dbms;
import com.speedment.db.ConnectionUrlGenerator;
import com.speedment.db.DatabaseNamingConvention;
import com.speedment.db.DbmsHandler;
import com.speedment.db.metadata.TypeInfoMetaData;
import com.speedment.manager.SpeedmentPredicateView;
import java.util.Set;
import java.util.function.BiFunction;

/**
 *
 * @author Per Minborg
 */
public interface DbmsTypeBuilder {

    public interface Builder extends
            WithName,
            WithDriverManagerName,
            WithDefaultPort,
            WithDbmsNameMeaning,
            WithDriverName,
            WithDatabaseNamingConvention,
            WithDbmsMapper,
            WithConnectionUrlGenerator,
            WithSpeedmentPredicateView,
            Optionals {
    }

    public interface WithName {

        /**
         * Enters the the non-null name for this {@code DbmsType}. For example
         * MySQL or Oracle
         *
         * @param name name
         * @return a builder
         */
        WithDriverManagerName withName(String name);
    }

    public interface WithDriverManagerName {

        /**
         * Enters the non-null Driver Manager Name for this {@code DbmsType}.
         * For example "MySQL-AB JDBC Driver" or "Oracle JDBC Driver"
         *
         * @param driverManagerName the Driver Manager Name
         * @return a builder
         */
        WithDefaultPort withDriverManagerName(String driverManagerName);
    }

    public interface WithDefaultPort {

        /**
         * Enters the default port for this {@code DbmsType}. For example 3306
         * (MySQL) or 1521 (Oracle)
         *
         * @param port the default port number
         * @return the default port
         */
        WithDbmsNameMeaning withDefaultPort(int port);
    }

    public interface WithDbmsNameMeaning {

        /**
         * Enters a textual representation of what the database name is used
         * for. Some databases (notably MySQL) does not use the database name
         * for anything whereas other (such as Oracle) are using the name as an
         * address (i.e. for Oracle the name is used as SID)
         *
         * @param nameMeaning the meaning of the dbms name
         * @return a builder
         */
        WithDriverName withDbmsNameMeaning(String nameMeaning);
    }

    public interface WithDriverName {

        /**
         * Enters the non-null fully qualified JDBC class name for this
         * {@code DbmsType}. For example "com.mysql.jdbc.Driver" or
         * "oracle.jdbc.OracleDriver"
         *
         * @param driverName JDBC class name
         * @return a builder
         */
        WithDatabaseNamingConvention withDriverName(String driverName);
    }

    public interface WithDatabaseNamingConvention {

        /**
         * Enters the non-null naming convention for this database.
         *
         * @param namingConvention the naming convention for this dbms type
         * @return a builder
         */
        WithDbmsMapper withDatabaseNamingConvention(DatabaseNamingConvention namingConvention);
    }

    public interface WithDbmsMapper {

        /**
         * Enters the DbmsHandler constructor that are to be used with this
         * database type. The DbmsHandler is responsible for communicating with
         * the database in terms of meta data.
         *
         * @param mapper to use as constructor of new DbmsHandler instances for
         * this database type.
         * @return a builder
         */
        WithConnectionUrlGenerator withDbmsMapper(BiFunction<Speedment, Dbms, DbmsHandler> mapper);
    }

    public interface WithConnectionUrlGenerator {

        /**
         * Enters the url connector string generator for this type of Dbms. The
         * generator shall inspect a given Dbms and return a connector string
         * suitable for that dbms type.
         *
         * @param generator to be used when constructing connector strings.
         * @return a builder
         */
        WithSpeedmentPredicateView withConnectionUrlGenerator(ConnectionUrlGenerator generator);
    }

    public interface WithSpeedmentPredicateView {

        /**
         * Enters the SpeedmentPredicateView that shall be used for this type of
         * Dbms. A SpeedmentPredicateView translates Speedment predicates (e.g.
         * used in .filter() statements) to actual SQL code.
         *
         * @param speedmentPredicateView to use for this Dbms type
         * @return a builder
         */
        Optionals withSpeedmentPredicateView(SpeedmentPredicateView speedmentPredicateView);
    }

    public interface Optionals {

        Optionals withResultSetTableSchema(String resultSetTableSchema);

        /**
         * Enters the delimiter used between a Schema and a Table for this
         * {@code DbmsType}. Most {@code DbmsType} are using a "." as a
         * separator and this is the default value for this property.
         *
         * @param delimiter he delimiter used between a Schema and a Table
         * @return a builder
         */
        Optionals withSchemaTableDelimiter(String delimiter);

        /**
         * Enters a non-null Set of database types. This method can be used for
         * databases that have a slow meta data retrieval function or for
         * databases that does not support meta data at all.
         *
         * @param dataTypes Set of database types
         * @return a builder
         */
        Optionals withDataTypes(Set<TypeInfoMetaData> dataTypes);

        /**
         * Enters a default name for this {@code DbmsType}. For example ‘orcl'
         * (Oracle)
         *
         * @param defaultDbmsName default name for this dbms type
         * @return a builder
         */
        Optionals withDefaultDbmsName(String defaultDbmsName);

        /**
         * Enters a non-null value for the initial query that speedment is using
         * to ensure database connectivity on start.
         *
         * @param initialQuery initial query
         * @return a builder
         */
        Optionals withInitialQuery(String initialQuery);

        /**
         * Creates and returns a new DbmsType instance with the given
         * parameters.
         *
         * @return a new DbmsType instance
         */
        DbmsType build();

    }

}
