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
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
@Api(version = 0)
public enum DbmsType implements EnumHelper<DbmsType> {

    MYSQL("MySQL", "MySQL-AB JDBC Driver", 3306),
    ORACLE("Oracle", "Oracle JDBC Driver", 1521, ".", "SID"),
    MARIADB("MariaDB", "MariaDB JDBC Driver", 3305),
    SQLSERVER("SQLServer", "SQLServer JDBC Driver", 1433),
    DB2("DB2", "DB2 JDBC Driver", 50000, ".", "SID"),
    POSTGRESQL("PostgreSQL", "Postgresql JDBC Driver", 5432, ".", "Database"),
    INFORMIX("Informix", "Informix JDBC Driver", 1526, ":", "SID"),
    MONETDB("MonetDB", "MonetDB JDBC Driver", 50000, ".", "Database");

    static final Map<String, DbmsType> NAME_MAP = EnumHelper.Hidden.buildMap(values());

    private DbmsType(final String value, final String driverManagerName, final int defaultPort) {
        this(value, driverManagerName, defaultPort, ".");
    }

    private DbmsType(final String name, final String driverManagerName, final int defaultPort, final String schemaTableDelimiter) {
        this(name, driverManagerName, defaultPort, schemaTableDelimiter, "(Just a name)");
    }

    private DbmsType(final String name, final String driverManagerName, final int defaultPort, final String schemaTableDelimiter, final String nameMeaning) {
        this.name = name;
        this.driverManagerName = driverManagerName;
        this.defaultPort = defaultPort;
        this.schemaTableDelimiter = schemaTableDelimiter;
        this.nameMeaning = nameMeaning;
        this.implementingClassName = getClass().getPackage().getName() + "." + name;
    }
    private final String name;
    private final String driverManagerName;
    private final int defaultPort;
    private final String schemaTableDelimiter;
    private final String nameMeaning;
    private final String implementingClassName;

    @Override
    public String getName() {
        return name;
    }

    public String getDriverManagerName() {
        return driverManagerName;
    }

    public int getDefaultPort() {
        return defaultPort;
    }

    public String getSchemaTableDelimiter() {
        return schemaTableDelimiter;
    }

    public String getSidName() {
        return nameMeaning;
    }

    public boolean isSupported() {
        try {
            Class.forName(getImplementingClassName());
            return true;
        } catch (ClassNotFoundException cnfe) {
            return false;
        }
    }

    public String getImplementingClassName() {
        return implementingClassName;
    }
    
    public static Optional<DbmsType> findByIgnoreCase(String name) {
        return Hidden.findByNameIgnoreCase(NAME_MAP, name);
    }
    
    public static DbmsType defaultFor(final ConfigEntity entity) {
        return Hidden.defaultFor(stream(), t -> false, entity, DbmsTypeable.class, MYSQL);
    }

    public static Stream<DbmsType> stream() {
        return Stream.of(values());
    }
}