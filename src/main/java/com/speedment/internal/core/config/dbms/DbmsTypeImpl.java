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
package com.speedment.internal.core.config.dbms;

import com.speedment.Speedment;
import com.speedment.config.db.Dbms;
import com.speedment.config.db.parameters.DbmsType;
import com.speedment.config.db.parameters.DbmsTypeBuilder;
import com.speedment.config.db.parameters.DbmsTypeBuilder.Optionals;
import com.speedment.config.db.parameters.DbmsTypeBuilder.WithConnectionUrlGenerator;
import com.speedment.config.db.parameters.DbmsTypeBuilder.WithDatabaseNamingConvention;
import com.speedment.config.db.parameters.DbmsTypeBuilder.WithDbmsMapper;
import com.speedment.config.db.parameters.DbmsTypeBuilder.WithDbmsNameMeaning;
import com.speedment.config.db.parameters.DbmsTypeBuilder.WithDefaultPort;
import com.speedment.config.db.parameters.DbmsTypeBuilder.WithDriverManagerName;
import com.speedment.config.db.parameters.DbmsTypeBuilder.WithDriverName;
import com.speedment.config.db.parameters.DbmsTypeBuilder.WithName;
import com.speedment.config.db.parameters.DbmsTypeBuilder.WithSpeedmentPredicateView;
import com.speedment.db.ConnectionUrlGenerator;
import com.speedment.db.DatabaseNamingConvention;
import com.speedment.db.DbmsHandler;
import com.speedment.internal.core.db.DefaultDatabaseNamingConvention;
import com.speedment.manager.SpeedmentPredicateView;
import java.util.Collections;
import static java.util.Collections.unmodifiableSet;
import java.util.HashSet;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.Optional;
import static java.util.Objects.requireNonNull;
import com.speedment.db.metadata.TypeInfoMetaData;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 */
public final class DbmsTypeImpl implements DbmsType {

    private final String name;
    private final String driverManagerName;
    private final int defaultPort;
    private final String schemaTableDelimiter;
    private final String dbmsNameMeaning;
    private final String driverName;
    private final DatabaseNamingConvention namingConvention;
    private final BiFunction<Speedment, Dbms, DbmsHandler> dbmsMapper;
    private final String resultSetTableSchema;
    private final ConnectionUrlGenerator connectionUrlGenerator;
    private final Set<TypeInfoMetaData> dataTypes;
    private final SpeedmentPredicateView speedmentPredicateView;
    private final String defaultDbmsName;
    private final String initialQuery;

    private DbmsTypeImpl(
            String name,
            String driverManagerName,
            int defaultPort,
            String schemaTableDelimiter,
            String dbmsNameMeaning,
            String driverName,
            DatabaseNamingConvention namingConvention,
            BiFunction<Speedment, Dbms, DbmsHandler> dbmsMapper,
            String resultSetTableSchema,
            ConnectionUrlGenerator connectionUrlGenerator,
            Set<TypeInfoMetaData> dataTypes,
            SpeedmentPredicateView speedmentPredicateView,
            String defaultDbmsName,
            String intitialQuery
    ) {
        this.name                   = requireNonNull(name);
        this.driverManagerName      = requireNonNull(driverManagerName);
        this.defaultPort            = defaultPort;
        this.schemaTableDelimiter   = requireNonNull(schemaTableDelimiter);
        this.dbmsNameMeaning        = requireNonNull(dbmsNameMeaning);
        this.driverName             = requireNonNull(driverName);
        this.namingConvention       = requireNonNull(namingConvention);
        this.dbmsMapper             = requireNonNull(dbmsMapper);
        this.resultSetTableSchema   = requireNonNull(resultSetTableSchema);
        this.connectionUrlGenerator = requireNonNull(connectionUrlGenerator);
        this.dataTypes              = unmodifiableSet(new HashSet(requireNonNull(dataTypes))); // Defensive copy
        this.speedmentPredicateView = requireNonNull(speedmentPredicateView);
        this.defaultDbmsName        = defaultDbmsName;
        this.initialQuery           = intitialQuery;
    }

    public static WithName builder() {
        return new Builder();
    }

    public static WithDbmsNameMeaning builder(String name, String driverManagerName, int defaultPort) {
        return builder().withName(name).withDriverManagerName(driverManagerName).withDefaultPort(defaultPort);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDriverManagerName() {
        return driverManagerName;
    }

    @Override
    public int getDefaultPort() {
        return defaultPort;
    }

    @Override
    public String getSchemaTableDelimiter() {
        return schemaTableDelimiter;
    }

    @Override
    public String getDriverName() {
        return driverName;
    }

    @Override
    public String getDbmsNameMeaning() {
        return dbmsNameMeaning;
    }

    @Override
    public Optional<String> getDefaultDbmsName() {
        return Optional.ofNullable(defaultDbmsName);
    }

    @Override
    public DbmsHandler makeDbmsHandler(Speedment speedment, Dbms dbms) {
        return dbmsMapper.apply(speedment, dbms);
    }

    @Override
    public boolean isSupported() {
        try {
            Class.forName(
                getDriverName(),
                false,
                DbmsType.class.getClassLoader()
            );

            return true;
        } catch (final ClassNotFoundException ex) {
            return false;
        }
    }

    @Override
    public DatabaseNamingConvention getDatabaseNamingConvention() {
        return namingConvention;
    }

    @Override
    public String getResultSetTableSchema() {
        return resultSetTableSchema;
    }

    @Override
    public ConnectionUrlGenerator getConnectionUrlGenerator() {
        return connectionUrlGenerator;
    }

    @Override
    public Set<TypeInfoMetaData> getDataTypes() {
        return dataTypes;
    }

    @Override
    public SpeedmentPredicateView getSpeedmentPredicateView() {
        return speedmentPredicateView;
    }

    @Override
    public String getInitialQuery() {
        return initialQuery;
    }

    private static class Builder implements DbmsTypeBuilder.Builder   {

        // Mandatory
        private String name;
        private String driverManagerName;
        private int defaultPort;
        private String dbmsNameMeaning;
        private String driverName;
        private DatabaseNamingConvention namingConvention;
        private BiFunction<Speedment, Dbms, DbmsHandler> dbmsMapper;
        private ConnectionUrlGenerator connectionUrlGenerator;
        private SpeedmentPredicateView speedmentPredicateView;
        
        // Optionals
        private String resultSetTableSchema;
        private String schemaTableDelimiter;
        private Set<TypeInfoMetaData> dataTypes;
        private String defaultDbmsName;
        private String initialQuery;

        public Builder() {
            resultSetTableSchema = "TABLE_SCHEMA";
            schemaTableDelimiter = ".";
            namingConvention = new DefaultDatabaseNamingConvention();
            dataTypes = Collections.emptySet();
            defaultDbmsName = null;
            initialQuery = "select 1 from dual";
        }

        @Override
        public WithDriverManagerName withName(String name) {
            this.name = requireNonNull(name);
            return this;
        }

        @Override
        public WithDefaultPort withDriverManagerName(String driverManagerName) {
            this.driverManagerName = requireNonNull(driverManagerName);
            return this;
        }

        @Override
        public WithDbmsNameMeaning withDefaultPort(int defaultPort) {
            this.defaultPort = defaultPort;
            return this;
        }

        @Override
        public WithDriverName withDbmsNameMeaning(String nameMeaning) {
            this.dbmsNameMeaning = requireNonNull(nameMeaning);
            return this;
        }

        @Override
        public WithDatabaseNamingConvention withDriverName(String driverName) {
            this.driverName = requireNonNull(driverName);
            return this;
        }

        @Override
        public WithDbmsMapper withDatabaseNamingConvention(DatabaseNamingConvention namingConvention) {
            this.namingConvention = requireNonNull(namingConvention);
            return this;
        }

        @Override
        public WithConnectionUrlGenerator withDbmsMapper(BiFunction<Speedment, Dbms, DbmsHandler> dbmsMapper) {
            this.dbmsMapper = requireNonNull(dbmsMapper);
            return this;
        }

        @Override
        public WithSpeedmentPredicateView withConnectionUrlGenerator(ConnectionUrlGenerator connectionUrlGenerator) {
            this.connectionUrlGenerator = requireNonNull(connectionUrlGenerator);
            return this;
        }

        @Override
        public Optionals withSpeedmentPredicateView(SpeedmentPredicateView speedmentPredicateView) {
            this.speedmentPredicateView = requireNonNull(speedmentPredicateView);
            return this;
        }

        /// Optionals
        @Override
        public Optionals withResultSetTableSchema(String resultSetTableSchema) {
            this.resultSetTableSchema = requireNonNull(resultSetTableSchema);
            return this;
        }

        @Override
        public Optionals withSchemaTableDelimiter(String schemaTableDelimiter) {
            this.schemaTableDelimiter = requireNonNull(schemaTableDelimiter);
            return this;
        }

        @Override
        public Optionals withDataTypes(Set<TypeInfoMetaData> dataTypes) {
            this.dataTypes = requireNonNull(dataTypes);
            return this;
        }

        @Override
        public Optionals withDefaultDbmsName(String defaultDbmsType) {
            this.defaultDbmsName = defaultDbmsType; // Nullable
            return this;
        }

        @Override
        public Optionals withInitialQuery(String initialQuery) {
            this.initialQuery = requireNonNull(initialQuery);
            return this;
        }

        @Override
        public DbmsType build() {
            return new DbmsTypeImpl(
                    name,
                    driverManagerName,
                    defaultPort,
                    schemaTableDelimiter,
                    dbmsNameMeaning,
                    driverName,
                    namingConvention,
                    dbmsMapper,
                    resultSetTableSchema,
                    connectionUrlGenerator,
                    dataTypes,
                    speedmentPredicateView,
                    defaultDbmsName,
                    initialQuery
            );

        }

    }

//    public interface WithName {
//
//        /**
//         * Enters the the non-null name for this {@code DbmsType}. For example
//         * MySQL or Oracle
//         *
//         * @return a builder
//         */
//        WithDriverManagerName withName(String name);
//    }
//
//    public interface WithDriverManagerName {
//
//        /**
//         * Enters the non-null Driver Manager Name for this {@code DbmsType}.
//         * For example "MySQL-AB JDBC Driver" or "Oracle JDBC Driver"
//         *
//         * @return a builder
//         */
//        WithDefaultPort withDriverManagerName(String driverManagerName);
//    }
//
//    public interface WithDefaultPort {
//
//        /**
//         * Enters the default port for this {@code DbmsType}. For example 3306
//         * (MySQL) or 1521 (Oracle)
//         *
//         * @return the default port
//         */
//        WithDbmsNameMeaning withDefaultPort(int port);
//    }
//
//    public interface WithDbmsNameMeaning {
//
//        /**
//         * Enters a textual representation of what the database name is used
//         * for. Some databases (notably MySQL) does not use the database name
//         * for anything whereas other (such as Oracle) are using the name as an
//         * address (i.e. for Oracle the name is used as SID)
//         *
//         * @return a builder
//         */
//        WithDriverName withDbmsNameMeaning(String nameMeaning);
//    }
//
//    public interface WithDriverName {
//
//        /**
//         * Enters the non-null fully qualified JDBC class name for this
//         * {@code DbmsType}. For example "com.mysql.jdbc.Driver" or
//         * "oracle.jdbc.OracleDriver"
//         *
//         * @return a builder
//         */
//        WithDatabaseNamingConvention withDriverName(String driverName);
//    }
//
//    public interface WithDatabaseNamingConvention {
//
//        /**
//         * Enters the non-null naming convention for this database.
//         *
//         * @return a builder
//         */
//        WithDbmsMapper withDatabaseNamingConvention(DatabaseNamingConvention namingConvention);
//    }
//
//    public interface WithDbmsMapper {
//
//        /**
//         * Enters the DbmsHandler constructor that are to be used with this
//         * database type. The DbmsHandler is responsible for communicating with
//         * the database in terms of meta data.
//         *
//         * @param mapper to use as constructor of new DbmsHandler instances for
//         * this database type.
//         * @return a builder
//         */
//        WithConnectionUrlGenerator withDbmsMapper(BiFunction<Speedment, Dbms, DbmsHandler> mapper);
//    }
//
//    public interface WithConnectionUrlGenerator {
//
//        /**
//         * Enters the url connector string generator for this type of Dbms. The
//         * generator shall inspect a given Dbms and return a connector string
//         * suitable for that dbms type.
//         *
//         * @param generator to be used when constructing connector strings.
//         * @return a builder
//         */
//        WithSpeedmentPredicateView withConnectionUrlGenerator(ConnectionUrlGenerator generator);
//    }
//
//    public interface WithSpeedmentPredicateView {
//
//        /**
//         * Enters the SpeedmentPredicateView that shall be used for this type of
//         * Dbms. A SpeedmentPredicateView translates Speedment predicates (e.g.
//         * used in .filter() statements) to actual SQL code.
//         *
//         * @param speedmentPredicateView to use for this Dbms type
//         * @return a builder
//         */
//        Optionals withSpeedmentPredicateView(SpeedmentPredicateView speedmentPredicateView);
//    }
//
//    public interface Optionals {
//
//        Optionals withResultSetTableSchema(String resultSetTableSchema);
//
//        /**
//         * Enters the delimiter used between a Schema and a Table for this
//         * {@code DbmsType}. Most {@code DbmsType} are using a "." as a
//         * separator and this is the default value for this property.
//         *
//         * @return a builder
//         */
//        Optionals withSchemaTableDelimiter(String delimiter);
//
//        /**
//         * Enters a non-null Set of database types. This method can be used for
//         * databases that have a slow meta data retrieval function or for
//         * databases that does not support meta data at all.
//         *
//         * @return a builder
//         */
//        Optionals withDataTypes(Set<SqlTypeInfo> dataTypes);
//
//        /**
//         * Enters a default name for this {@code DbmsType}. For example ‘orcl'
//         * (Oracle)
//         *
//         * @return a builder
//         */
//        Optionals withDefaultDbmsName(String defaultDbmsName);
//
//        /**
//         * Enters a non-null value for the initial query that speedment is using
//         * to ensure database connectivity on start.
//         *
//         * @return a builder
//         */
//        Optionals withInitialQuery(String initialQuery);
//
//        /**
//         * Creates and returns a new DbmsType instance with the given
//         * parameters.
//         *
//         * @return a new DbmsType instance
//         */
//        DbmsType build();
//
//    }

}
