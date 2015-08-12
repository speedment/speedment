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
package com.speedment.core.config.model.parameters;

import com.speedment.core.annotations.Api;
import com.speedment.core.config.model.Dbms;
import com.speedment.core.config.model.aspects.Enableable;
import com.speedment.core.config.model.aspects.Node;
import com.speedment.core.db.DbmsHandler;
import com.speedment.core.db.impl.vendor.MySqlDbmsHandler;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
@Api(version = "2.0")
public enum StandardDbmsType implements EnumHelper<StandardDbmsType>, DbmsType {

    MYSQL(
        "MySQL",
        "MySQL-AB JDBC Driver",
        3306,
        ".",
        "Just a name",
        "com.mysql.jdbc.Driver",
        "useUnicode=true&characterEncoding=UTF-8&useServerPrepStmts=true&useCursorFetch=true&zeroDateTimeBehavior=convertToNull",
        "mysql",
        "`",
        "`",
        Collections.unmodifiableSet(new HashSet<>(Arrays.asList("MySQL", "information_schema"))),
        d -> new MySqlDbmsHandler(d)
    ),
    
    MARIADB(
        "MariaDB",
        "MariaDB JDBC Driver",
        3305,
        ".",
        "Just a name",
        "com.mysql.jdbc.Driver",
        "useUnicode=true&characterEncoding=UTF-8&useServerPrepStmts=true&useCursorFetch=true&zeroDateTimeBehavior=convertToNull",
        "mariadb",
        "`",
        "`",
        Collections.unmodifiableSet(new HashSet<>(Arrays.asList("MySQL", "information_schema"))),
        d -> new MySqlDbmsHandler(d)
    );
    
//    
//    ORACLE("Oracle", "Oracle JDBC Driver", 1521, ".", "SID"),
//    SQLSERVER("SQLServer", "SQLServer JDBC Driver", 1433),
//    DB2("DB2", "DB2 JDBC Driver", 50000, ".", "SID"),
//    POSTGRESQL("PostgreSQL", "Postgresql JDBC Driver", 5432, ".", "Database"),
//    INFORMIX("Informix", "Informix JDBC Driver", 1526, ":", "SID"),
//    MONETDB("MonetDB", "MonetDB JDBC Driver", 50000, ".", "Database");

    private final static Map<String, StandardDbmsType> NAME_MAP = 
        EnumHelper.Hidden.buildMap(values());
    
    private final String name;
    private final String driverManagerName;
    private final int defaultPort;
    private final String schemaTableDelimiter;
    private final String nameMeaning;
    private final String driverName;
    private final String defaultConnectionParameters;
    private final String jdbcConnectorName;
    private final String fieldEncloserStart;
    private final String fieldEncloserEnd;
    private final Set<String> schemaExcludSet;
    private final Function<Dbms, DbmsHandler> dbmsMapper;

    StandardDbmsType(
        final String name,
        final String driverManagerName,
        final int defaultPort,
        final String schemaTableDelimiter,
        final String nameMeaning,
        final String driverName,
        final String defaultConnectionParameters,
        final String jdbcConnectorName,
        final String fieldEncloserStart,
        final String fieldEncloserEnd,
        final Set<String> schemaExcludSet,
        final Function<Dbms, DbmsHandler> dbmsMapper
    ) {
        this.name                        = requireNonNull(name);
        this.driverManagerName           = requireNonNull(driverManagerName);
        this.defaultPort                 = defaultPort;
        this.schemaTableDelimiter        = requireNonNull(schemaTableDelimiter);
        this.nameMeaning                 = requireNonNull(nameMeaning);
        this.driverName                  = requireNonNull(driverName);
        this.defaultConnectionParameters = requireNonNull(defaultConnectionParameters);
        this.jdbcConnectorName           = requireNonNull(jdbcConnectorName);
        this.fieldEncloserStart          = requireNonNull(fieldEncloserStart);
        this.fieldEncloserEnd            = requireNonNull(fieldEncloserEnd);
        this.schemaExcludSet             = requireNonNull(schemaExcludSet);
        this.dbmsMapper                  = requireNonNull(dbmsMapper);
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
    public String getDbmsNameMeaning() {
        return nameMeaning;
    }

    @Override
    public boolean isSupported() {
        return true;
    }

    @Override
    public String getDriverName() {
        return driverName;
    }

    @Override
    public Optional<String> getDefaultConnectorParameters() {
        return Optional.ofNullable(defaultConnectionParameters);
    }

    @Override
    public String getJdbcConnectorName() {
        return jdbcConnectorName;
    }

    @Override
    public String getFieldEncloserStart(boolean isWithinQuotes) {
        return escapeIfQuote(fieldEncloserStart, isWithinQuotes);
    }

    @Override
    public String getFieldEncloserEnd(boolean isWithinQuotes) {
        return escapeIfQuote(fieldEncloserEnd, isWithinQuotes);
    }

    private String escapeIfQuote(String item, boolean isWithinQuotes) {
        if (isWithinQuotes && "\"".equals(item)) {
            return "\\" + item;
        }
        return item;
    }

    public static Optional<StandardDbmsType> findByIgnoreCase(String name) {
        return Hidden.findByNameIgnoreCase(NAME_MAP, name);
    }

    public static <C extends Node & Enableable> StandardDbmsType defaultFor(final C entity) {
        return Hidden.defaultFor(stream(), t -> false, entity, DbmsTypeable.class, MYSQL);
    }

    public static Stream<StandardDbmsType> stream() {
        return Stream.of(values());
    }

    @Override
    public Set<String> getSchemaExcludSet() {
        return schemaExcludSet;
    }

    @Override
    public DbmsHandler makeDbmsHandler(Dbms dbms) {
        return dbmsMapper.apply(dbms);
    }
}