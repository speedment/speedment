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
import com.speedment.db.DbmsHandler;
import com.speedment.internal.core.manager.sql.SpeedmentPredicateView;
import com.speedment.internal.util.sql.SqlTypeInfo;
import java.util.Collections;
import static java.util.Collections.unmodifiableSet;
import java.util.HashSet;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.Optional;
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
    private final String fieldEncloserStart;
    private final String fieldEncloserEnd;
    private final Set<String> schemaExcludeSet;
    private final BiFunction<Speedment, Dbms, DbmsHandler> dbmsMapper;
    private final String resultSetTableSchema;
    private final Function<Dbms, String> connectionUrlGenerator;
    private final Set<SqlTypeInfo> dataTypes;
    private final SpeedmentPredicateView speedmentPredicateView;
    private final String defaultDbmsName;

    private DbmsTypeImpl(
            final String name,
            final String driverManagerName,
            final int defaultPort,
            final String schemaTableDelimiter,
            final String dbmsNameMeaning,
            final String driverName,
            final String fieldEncloserStart,
            final String fieldEncloserEnd,
            final Set<String> schemaExcludeSet,
            final BiFunction<Speedment, Dbms, DbmsHandler> dbmsMapper,
            final String resultSetTableSchema,
            final Function<Dbms, String> connectionUrlGenerator,
            final Set<SqlTypeInfo> dataTypes,
            final SpeedmentPredicateView speedmentPredicateView,
            final String defaultDbmsName
    ) {

        this.name = requireNonNull(name);
        this.driverManagerName = requireNonNull(driverManagerName);
        this.defaultPort = defaultPort;
        this.schemaTableDelimiter = requireNonNull(schemaTableDelimiter);
        this.dbmsNameMeaning = requireNonNull(dbmsNameMeaning);
        this.driverName = requireNonNull(driverName);
        this.fieldEncloserStart = requireNonNull(fieldEncloserStart);
        this.fieldEncloserEnd = requireNonNull(fieldEncloserEnd);
        this.schemaExcludeSet = unmodifiableSet(new HashSet(requireNonNull(schemaExcludeSet))); // Defensive copy
        this.dbmsMapper = requireNonNull(dbmsMapper);
        this.resultSetTableSchema = requireNonNull(resultSetTableSchema);
        this.connectionUrlGenerator = requireNonNull(connectionUrlGenerator);
        this.dataTypes = unmodifiableSet(new HashSet(requireNonNull(dataTypes))); // Defensive copy
        this.speedmentPredicateView = requireNonNull(speedmentPredicateView);
        this.defaultDbmsName = defaultDbmsName;
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
    public String getFieldEncloserStart() {
        return fieldEncloserStart;
    }

    @Override
    public String getFieldEncloserEnd() {
        return fieldEncloserEnd;
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
    public String getFieldEncloserStart(boolean isWithinQuotes) {
        return escapeIfQuote(getFieldEncloserStart(), isWithinQuotes);
    }

    @Override
    public String getFieldEncloserEnd(boolean isWithinQuotes) {
        return escapeIfQuote(getFieldEncloserEnd(), isWithinQuotes);
    }

    @Override
    public Set<String> getSchemaExcludeSet() {
        return schemaExcludeSet;
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

    private String escapeIfQuote(String item, boolean isWithinQuotes) {
        if (isWithinQuotes && "\"".equals(item)) {
            return "\\" + item;
        }
        return item;
    }

    @Override
    public String getResultSetTableSchema() {
        return resultSetTableSchema;
    }

    @Override
    public Function<Dbms, String> getConnectionUrlGenerator() {
        return connectionUrlGenerator;
    }

    @Override
    public Set<SqlTypeInfo> getDataTypes() {
        return dataTypes;
    }

    @Override
    public SpeedmentPredicateView getSpeedmentPredicateView() {
        return speedmentPredicateView;
    }

    private static class Builder implements
            WithName,
            WithDriverManagerName,
            WithDefaultPort,
            WithDbmsNameMeaning,
            WithDriverName,
            WithFieldEncloserStart,
            WithFieldEncloserEnd,
            WithDbmsMapper,
            WithConnectionUrlGenerator,
            WithSpeedmentPredicateView,
            Optionals {

        // Mandatory
        private String name;
        private String driverManagerName;
        private int defaultPort;
        private String dbmsNameMeaning;
        private String driverName;
        private String fieldEncloserStart;
        private String fieldEncloserEnd;
        private BiFunction<Speedment, Dbms, DbmsHandler> dbmsMapper;
        private Function<Dbms, String> connectionUrlGenerator;
        private SpeedmentPredicateView speedmentPredicateView;
        // Optionals
        private String resultSetTableSchema;
        private String schemaTableDelimiter;
        private Set<String> schemaExcludeSet;
        private Set<SqlTypeInfo> dataTypes;
        private String defaultDbmsName;

        public Builder() {
            resultSetTableSchema = "TABLE_SCHEMA";
            schemaTableDelimiter = ".";
            schemaExcludeSet = Collections.emptySet();
            dataTypes = Collections.emptySet();
            defaultDbmsName = null;
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
        public WithFieldEncloserStart withDriverName(String driverName) {
            this.driverName = requireNonNull(driverName);
            return this;
        }

        @Override
        public WithFieldEncloserEnd withFieldEncloserStart(String fieldEncloserStart) {
            this.fieldEncloserStart = requireNonNull(fieldEncloserStart);
            return this;
        }

        @Override
        public WithDbmsMapper withFieldEncloserEnd(String fieldEncloserEnd) {
            this.fieldEncloserEnd = requireNonNull(fieldEncloserEnd);
            return this;
        }

        @Override
        public WithConnectionUrlGenerator withDbmsMapper(BiFunction<Speedment, Dbms, DbmsHandler> dbmsMapper) {
            this.dbmsMapper = requireNonNull(dbmsMapper);
            return this;
        }

        @Override
        public WithSpeedmentPredicateView withConnectionUrlGenerator(Function<Dbms, String> connectionUrlGenerator) {
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
        public Optionals withSchemaExcludeSet(Set<String> schemaExcludeSet) {
            this.schemaExcludeSet = requireNonNull(schemaExcludeSet);
            return this;
        }

        @Override
        public Optionals withDataTypes(Set<SqlTypeInfo> dataTypes) {
            this.dataTypes = requireNonNull(dataTypes);
            return this;
        }

        @Override
        public Optionals withDefaultDbmsName(String defaultDbmsType) {
            this.defaultDbmsName = defaultDbmsType; // Nullable
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
                    fieldEncloserStart,
                    fieldEncloserEnd,
                    schemaExcludeSet,
                    dbmsMapper,
                    resultSetTableSchema,
                    connectionUrlGenerator,
                    dataTypes,
                    speedmentPredicateView,
                    defaultDbmsName
            );

        }

    }

    public interface WithName {

        /**
         * Enters the the non-null name for this {@code DbmsType}. For example
         * MySQL or Oracle
         *
         * @return a builder
         */
        WithDriverManagerName withName(String name);
    }

    public interface WithDriverManagerName {

        /**
         * Enters the non-null Driver Manager Name for this {@code DbmsType}.
         * For example "MySQL-AB JDBC Driver" or "Oracle JDBC Driver"
         *
         * @return a builder
         */
        WithDefaultPort withDriverManagerName(String driverManagerName);
    }

    public interface WithDefaultPort {

        /**
         * Enters the default port for this {@code DbmsType}. For example 3306
         * (MySQL) or 1521 (Oracle)
         *
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
         * @return a builder
         */
        WithFieldEncloserStart withDriverName(String driverName);
    }

    public interface WithFieldEncloserStart {

        /**
         * Enters the non-null field encloser start string. The field encloser
         * start string precedes a database entity name like a table or schema
         * name when quoted. Quoted names are used to avoid that entity names
         * collide with reserved keywords like "key" or "user". So a table named
         * "user" in the "key" schema can be quoted to "key"."user". Examples of
         * values are '`' for MySQL or '"' for Oracle.
         *
         * @return a builder
         *
         * @see DbmsType#getFieldEncloserStart(boolean)
         * @see DbmsType#getFieldEncloserEnd()
         * @see DbmsType#getFieldEncloserEnd(boolean)
         */
        WithFieldEncloserEnd withFieldEncloserStart(String start);
    }

    public interface WithFieldEncloserEnd {

        /**
         * Enters the non-null field encloser end string. The field encloser end
         * string follows a database entity name like a table or schema name
         * when quoted. Quoted names are used to avoid that entity names collide
         * with reserved keywords like "key" or "user". So a table named "user"
         * in the "key" schema can be quoted to "key"."user". Examples of values
         * are '`' for MySQL or '"' for Oracle.
         *
         * @return the non-null field encloser end string
         *
         * @see DbmsType#getFieldEncloserStart(boolean)
         * @see DbmsType#getFieldEncloserEnd()
         * @see DbmsType#getFieldEncloserEnd(boolean)
         */
        WithDbmsMapper withFieldEncloserEnd(String end);
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
        WithSpeedmentPredicateView withConnectionUrlGenerator(Function<Dbms, String> generator);
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
         * @return a builder
         */
        Optionals withSchemaTableDelimiter(String delimiter);

        /**
         * Enters a non-null Set of Strings that represents schema names that
         * are to be excluded when examining a Dbms for schemas. The set
         * typically contains names for system tables and similar things. For
         * example for MySQL, the schemas "MySQL" and "information_schema" are
         * typically excluded.
         *
         * @return a builder
         */
        Optionals withSchemaExcludeSet(Set<String> excludeSet);

        /**
         * Enters a non-null Set of database types. This method can be used for
         * databases that have a slow meta data retrieval function or for
         * databases that does not support meta data at all.
         *
         * @return a builder
         */
        Optionals withDataTypes(Set<SqlTypeInfo> dataTypes);

        /**
         * Enters a default name for this {@code DbmsType}. For example ‘orcl'
         * (Oracle)
         *
         * @return a builder
         */
        Optionals withDefaultDbmsName(String defaultDbmsName);

        /**
         * Creates and returns a new DbmsType instance with the given
         * parameters.
         *
         * @return a new DbmsType instance
         */
        DbmsType build();

    }

}
