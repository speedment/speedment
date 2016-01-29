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
import com.speedment.internal.util.sql.SqlTypeInfo;

import static java.util.Objects.requireNonNull;

import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 *
 * @author pemi
 */
public abstract class AbstractDbmsType implements DbmsType {

    private final String name;
    private final String driverManagerName;
    private final int defaultPort;
    private final String schemaTableDelimiter;
    private final String dbmsNameMeaning;
    private final String defaultDbmsName;
    private final String driverName;
    private final String defaultConnectorParameters;
    private final String jdbcConnectorName;
    private final String fieldEncloserStart;
    private final String fieldEncloserEnd;
    private final Set<String> schemaExcludeSet;
    private final BiFunction<Speedment, Dbms, DbmsHandler> dbmsMapper;
    private final String resultSetTableSchema;
    private final Function<Dbms,String> connectionUrlGenerator;
    private final Set<SqlTypeInfo> dataTypes;

    protected AbstractDbmsType(
        String name,
        String driverManagerName,
        int defaultPort,
        String schemaTableDelimiter,
        String dbmsNameMeaning,
        String defaultDbmsName,
        String driverName,
        String defaultConnectorParameters,
        String jdbcConnectorName,
        String fieldEncloserStart,
        String fieldEncloserEnd,
        Set<String> schemaExcludeSet,
        BiFunction<Speedment, Dbms, DbmsHandler> dbmsMapper,
        String resultSetTableSchema,
        Function<Dbms,String> connectionUrlGenerator,
        Set<SqlTypeInfo> dataTypes) {

        this.name = requireNonNull(name);
        this.driverManagerName = requireNonNull(driverManagerName);
        this.defaultPort = defaultPort;
        this.schemaTableDelimiter = requireNonNull(schemaTableDelimiter);
        this.dbmsNameMeaning = requireNonNull(dbmsNameMeaning);
        this.defaultDbmsName = defaultDbmsName;
        this.driverName = requireNonNull(driverName);
        this.defaultConnectorParameters = defaultConnectorParameters; // Nullable
        this.jdbcConnectorName = requireNonNull(jdbcConnectorName);
        this.fieldEncloserStart = requireNonNull(fieldEncloserStart);
        this.fieldEncloserEnd = requireNonNull(fieldEncloserEnd);
        this.schemaExcludeSet = requireNonNull(schemaExcludeSet);
        this.dbmsMapper = requireNonNull(dbmsMapper);
        this.resultSetTableSchema = requireNonNull(resultSetTableSchema);
        this.connectionUrlGenerator = requireNonNull(connectionUrlGenerator);
        this.dataTypes = dataTypes;
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
    public String getJdbcConnectorName() {
        return jdbcConnectorName;
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
        return dbmsNameMeaning;
    }
    
    @Override
    public Optional<String> getDefaultConnectorParameters() {
        return Optional.ofNullable(defaultConnectorParameters);
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
        return true;
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
}
