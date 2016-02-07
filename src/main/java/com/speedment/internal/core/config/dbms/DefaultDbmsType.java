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

import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 */
public class DefaultDbmsType implements DbmsType {

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

    protected DefaultDbmsType(
            String name,
            String driverManagerName,
            int defaultPort,
            String schemaTableDelimiter,
            String dbmsNameMeaning,
            String driverName,
            String fieldEncloserStart,
            String fieldEncloserEnd,
            Set<String> schemaExcludeSet,
            BiFunction<Speedment, Dbms, DbmsHandler> dbmsMapper,
            String resultSetTableSchema,
            Function<Dbms, String> connectionUrlGenerator,
            Set<SqlTypeInfo> dataTypes,
            SpeedmentPredicateView speedmentPredicateView,
            String defaultDbmsName
    ) {

        this.name = requireNonNull(name);
        this.driverManagerName = requireNonNull(driverManagerName);
        this.defaultPort = defaultPort;
        this.schemaTableDelimiter = requireNonNull(schemaTableDelimiter);
        this.dbmsNameMeaning = requireNonNull(dbmsNameMeaning);
        this.driverName = requireNonNull(driverName);
        this.fieldEncloserStart = requireNonNull(fieldEncloserStart);
        this.fieldEncloserEnd = requireNonNull(fieldEncloserEnd);
        this.schemaExcludeSet = requireNonNull(schemaExcludeSet);
        this.dbmsMapper = requireNonNull(dbmsMapper);
        this.resultSetTableSchema = requireNonNull(resultSetTableSchema);
        this.connectionUrlGenerator = requireNonNull(connectionUrlGenerator);
        this.dataTypes = dataTypes;
        this.speedmentPredicateView = speedmentPredicateView;
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
    public String getDefaultDbmsName() {
        return defaultDbmsName;
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
            this.dbmsNameMeaning = nameMeaning;
            return this;
        }

        @Override
        public WithFieldEncloserStart withDriverName(String driverName) {
            this.driverName = driverName;
            return this;
        }

        @Override
        public WithFieldEncloserEnd withFieldEncloserStart(String fieldEncloserStart) {
            this.fieldEncloserStart = fieldEncloserStart;
            return this;
        }

        @Override
        public WithDbmsMapper withFieldEncloserEnd(String fieldEncloserEnd) {
            this.fieldEncloserEnd = fieldEncloserEnd;
            return this;
        }

        @Override
        public WithConnectionUrlGenerator withDbmsMapper(BiFunction<Speedment, Dbms, DbmsHandler> dbmsMapper) {
            this.dbmsMapper = dbmsMapper;
            return this;
        }

        @Override
        public WithSpeedmentPredicateView withConnectionUrlGenerator(Function<Dbms, String> connectionUrlGenerator) {
            this.connectionUrlGenerator = connectionUrlGenerator;
            return this;
        }

        @Override
        public Optionals withSpeedmentPredicateView(SpeedmentPredicateView speedmentPredicateView) {
            this.speedmentPredicateView = speedmentPredicateView;
            return this;
        }

        /// Optionals
        @Override
        public Optionals withResultSetTableSchema(String resultSetTableSchema) {
            this.resultSetTableSchema = resultSetTableSchema;
            return this;
        }

        @Override
        public Optionals withSchemaTableDelimiter(String schemaTableDelimiter) {
            this.schemaTableDelimiter = schemaTableDelimiter;
            return this;
        }

        @Override
        public Optionals withSchemaExcludeSet(Set<String> schemaExcludeSet) {
            this.schemaExcludeSet = schemaExcludeSet;
            return this;
        }

        @Override
        public Optionals withDataTypes(Set<SqlTypeInfo> dataTypes) {
            this.dataTypes = dataTypes;
            return this;
        }

        @Override
        public Optionals withDefaultDbmsName(String defaultDbmsType) {
            this.defaultDbmsName = defaultDbmsType;
            return this;
        }
        
        
        @Override
        public DbmsType build() {
            return new DefaultDbmsType(
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

        WithDriverManagerName withName(String name);
    }

    public interface WithDriverManagerName {

        WithDefaultPort withDriverManagerName(String driverManagerName);
    }

    public interface WithDefaultPort {

        WithDbmsNameMeaning withDefaultPort(int port);
    }

    public interface WithDbmsNameMeaning {

        WithDriverName withDbmsNameMeaning(String nameMeaning);
    }

    public interface WithDriverName {

        WithFieldEncloserStart withDriverName(String driverName);
    }

    public interface WithFieldEncloserStart {

        WithFieldEncloserEnd withFieldEncloserStart(String start);
    }

    public interface WithFieldEncloserEnd {

        WithDbmsMapper withFieldEncloserEnd(String end);
    }

    public interface WithDbmsMapper {

        WithConnectionUrlGenerator withDbmsMapper(BiFunction<Speedment, Dbms, DbmsHandler> mapper);
    }

    public interface WithConnectionUrlGenerator {

        WithSpeedmentPredicateView withConnectionUrlGenerator(Function<Dbms, String> generator);
    }

    public interface WithSpeedmentPredicateView {

        Optionals withSpeedmentPredicateView(SpeedmentPredicateView speedmentPredicateView);
    }

    public interface Optionals {

        Optionals withResultSetTableSchema(String resultSetTableSchema);

        Optionals withSchemaTableDelimiter(String delimiter);

        Optionals withSchemaExcludeSet(Set<String> excludeSet);

        Optionals withDataTypes(Set<SqlTypeInfo> dataTypes);
        
        Optionals withDefaultDbmsName(String defaultDbmsName);

        DbmsType build();

    }

}
