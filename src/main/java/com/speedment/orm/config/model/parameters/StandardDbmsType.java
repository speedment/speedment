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
package com.speedment.orm.config.model.parameters;

import com.speedment.orm.annotations.Api;
import com.speedment.orm.config.model.ConfigEntity;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
@Api(version = 0)
public enum StandardDbmsType implements EnumHelper<StandardDbmsType>, DbmsType {

    MYSQL("MySQL", "MySQL-AB JDBC Driver", 3306),
    ORACLE("Oracle", "Oracle JDBC Driver", 1521, ".", "SID"),
    MARIADB("MariaDB", "MariaDB JDBC Driver", 3305),
    SQLSERVER("SQLServer", "SQLServer JDBC Driver", 1433),
    DB2("DB2", "DB2 JDBC Driver", 50000, ".", "SID"),
    POSTGRESQL("PostgreSQL", "Postgresql JDBC Driver", 5432, ".", "Database"),
    INFORMIX("Informix", "Informix JDBC Driver", 1526, ":", "SID"),
    MONETDB("MonetDB", "MonetDB JDBC Driver", 50000, ".", "Database");

    static final Map<String, StandardDbmsType> NAME_MAP = EnumHelper.Hidden.buildMap(values());

    private StandardDbmsType(final String value, final String driverManagerName, final int defaultPort) {
        this(value, driverManagerName, defaultPort, ".");
    }

    private StandardDbmsType(final String name, final String driverManagerName, final int defaultPort, final String schemaTableDelimiter) {
        this(name, driverManagerName, defaultPort, schemaTableDelimiter, "(Just a name)");
    }

    private StandardDbmsType(final String name, final String driverManagerName, final int defaultPort, final String schemaTableDelimiter, final String nameMeaning) {
        this.name = name;
        this.driverManagerName = driverManagerName;
        this.defaultPort = defaultPort;
        this.schemaTableDelimiter = schemaTableDelimiter;
        this.nameMeaning = nameMeaning;
    }
    private final String name;
    private final String driverManagerName;
    private final int defaultPort;
    private final String schemaTableDelimiter;
    private final String nameMeaning;

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

    public static Optional<StandardDbmsType> findByIgnoreCase(String name) {
        return Hidden.findByNameIgnoreCase(NAME_MAP, name);
    }

    public static StandardDbmsType defaultFor(final ConfigEntity entity) {
        return Hidden.defaultFor(stream(), t -> false, entity, DbmsTypeable.class, MYSQL);
    }

    public static Stream<StandardDbmsType> stream() {
        return Stream.of(values());
    }
}
