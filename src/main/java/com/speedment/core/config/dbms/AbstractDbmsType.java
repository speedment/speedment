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
package com.speedment.core.config.dbms;

import com.speedment.api.Speedment;
import com.speedment.api.config.Dbms;
import com.speedment.api.config.parameters.DbmsType;
import com.speedment.api.db.DbmsHandler;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;

/**
 *
 * @author pemi
 */
public class AbstractDbmsType implements DbmsType {

    private final String name;
    private final String driverManagerName;
    private final int defaultPort;
    private final String schemaTableDelimiter;
    private final String dbmsNameMeaning;
    private final String driverName;
    private final String defaultConnectorParameters;
    private final String jdbcConnectorName;
    private final String fieldEncloserStart;
    private final String fieldEncloserEnd;
    private final Set<String> schemaExcludeSet;
    private final BiFunction<Speedment, Dbms, DbmsHandler> dbmsMapper;

    protected AbstractDbmsType(
        String name,
        String driverManagerName,
        int defaultPort,
        String schemaTableDelimiter,
        String dbmsNameMeaning,
        String driverName,
        String defaultConnectorParameters,
        String jdbcConnectorName,
        String fieldEncloserStart,
        String fieldEncloserEnd,
        Set<String> schemaExcludeSet,
        BiFunction<Speedment, Dbms, DbmsHandler> dbmsMapper) {

        this.name = Objects.requireNonNull(name);
        this.driverManagerName = Objects.requireNonNull(driverManagerName);
        this.defaultPort = defaultPort;
        this.schemaTableDelimiter = Objects.requireNonNull(schemaTableDelimiter);
        this.dbmsNameMeaning = Objects.requireNonNull(dbmsNameMeaning);
        this.driverName = Objects.requireNonNull(driverName);
        this.defaultConnectorParameters = Objects.requireNonNull(defaultConnectorParameters);
        this.jdbcConnectorName = Objects.requireNonNull(jdbcConnectorName);
        this.fieldEncloserStart = Objects.requireNonNull(fieldEncloserStart);
        this.fieldEncloserEnd = Objects.requireNonNull(fieldEncloserEnd);
        this.schemaExcludeSet = Objects.requireNonNull(schemaExcludeSet);
        this.dbmsMapper = Objects.requireNonNull(dbmsMapper);
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
}
