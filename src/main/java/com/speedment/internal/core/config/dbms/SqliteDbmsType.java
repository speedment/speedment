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

import com.speedment.config.db.Dbms;
import com.speedment.config.db.parameters.DbmsType;
import com.speedment.db.ConnectionUrlGenerator;
import com.speedment.db.DatabaseNamingConvention;
import com.speedment.internal.core.db.AbstractDatabaseNamingConvention;
import com.speedment.internal.core.db.SqliteDbmsHandler;
import com.speedment.internal.core.manager.sql.SqliteSpeedmentPredicateView;
import com.speedment.internal.util.sql.SqlTypeInfo;
import java.util.Set;

import java.util.stream.Stream;
import java.sql.Types;
import java.util.Collections;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;

/**
 *
 * @author ikost
 */
public final class SqliteDbmsType {

    private final static DatabaseNamingConvention NAMER = new SqliteNamingConvention();

    private final static class SqliteNamingConvention extends AbstractDatabaseNamingConvention {

        private final static String ENCLOSER = "\"",
                QUOTE = "'";

        private final static Set<String> EXCLUDE_SET = Stream.of(
                "sqlite_master"
        ).collect(collectingAndThen(toSet(), Collections::unmodifiableSet));

        @Override
        public Set<String> getSchemaExcludeSet() {
            return EXCLUDE_SET;
        }

        @Override
        protected String getFieldQuoteStart() {
            return QUOTE;
        }

        @Override
        protected String getFieldQuoteEnd() {
            return QUOTE;
        }

        @Override
        protected String getFieldEncloserStart() {
            return ENCLOSER;
        }

        @Override
        protected String getFieldEncloserEnd() {
            return ENCLOSER;
        }
    }

    private final static class SqliteConnectionUrlGenerator implements ConnectionUrlGenerator {

        @Override
        public String from(Dbms dbms) {
            final StringBuilder result = new StringBuilder()
                    .append("jdbc:sqlite:")
                    .append(dbms.getIpAddress().orElse(""));
            return result.toString();
        }
    }

    public static final DbmsType INSTANCE = DbmsType.builder()
            // Mandatory parameters
            .withName("SQLite")
            .withDriverManagerName("SQLite JDBC Driver")
            .withDefaultPort(0)
            .withDbmsNameMeaning("Database name")
            .withDriverName("org.sqlite.JDBC")
            .withDatabaseNamingConvention(NAMER)
            .withDbmsMapper(SqliteDbmsHandler::new)
            .withConnectionUrlGenerator(new SqliteConnectionUrlGenerator())
            .withSpeedmentPredicateView(new SqliteSpeedmentPredicateView(NAMER))
            // Optional parameters
            .withInitialQuery("SELECT * FROM sqlite_master")
            .withResultSetTableSchema("")
            .withDataTypes(dataTypes())
            .build();

    private static Set<SqlTypeInfo> dataTypes() {
        // @see https://www.sqlite.org/datatype3.html
        return Stream.of(
                new SqlTypeInfo("INTEGER", Types.INTEGER, 0, 0, (short) 1, false),
                new SqlTypeInfo("INT", Types.INTEGER, 0, 0, (short) 1, false),
                new SqlTypeInfo("SMALLINT", Types.SMALLINT, 0, 0, (short) 1, false),
                new SqlTypeInfo("MEDIUMINT", Types.INTEGER, 0, 0, (short) 1, false),
                new SqlTypeInfo("BIGINT", Types.BIGINT, 0, 0, (short) 1, false),
                new SqlTypeInfo("UNSIGNED BIG INT", Types.BIGINT, 0, 0, (short) 1, false),
                new SqlTypeInfo("INT2", Types.SMALLINT, 0, 0, (short) 1, false),
                new SqlTypeInfo("INT8", Types.INTEGER, 0, 0, (short) 1, false),
                new SqlTypeInfo("TINYINT", Types.TINYINT, 0, 0, (short) 1, false),
                new SqlTypeInfo("FLOAT", Types.FLOAT, 1000, 1000, (short) 1, false),
                new SqlTypeInfo("REAL", Types.REAL, 1000, 1000, (short) 1, false),
                new SqlTypeInfo("DOUBLE", Types.DOUBLE, 1000, 1000, (short) 1, false),
                new SqlTypeInfo("DOUBLE PRECISION", Types.DOUBLE, 1000, 1000, (short) 1, false),
                new SqlTypeInfo("NUMERIC", Types.NUMERIC, 1000, 1000, (short) 1, false),
                new SqlTypeInfo("DECIMAL", Types.DECIMAL, 1000, 1000, (short) 1, false),
                new SqlTypeInfo("BOOLEAN", Types.BOOLEAN, 1000, 1000, (short) 1, false),
                new SqlTypeInfo("DATE", Types.DATE, 1000, 1000, (short) 1, false),
                new SqlTypeInfo("DATETIME", Types.DATE, 1000, 1000, (short) 1, false),
                new SqlTypeInfo("TEXT", Types.VARCHAR, 0, 0, (short) 1, true),
                new SqlTypeInfo("CLOB", Types.VARCHAR, 0, 0, (short) 1, true),
                new SqlTypeInfo("CHARACTER", Types.CHAR, 0, 0, (short) 1, true),
                new SqlTypeInfo("VARCHAR", Types.VARCHAR, 0, 0, (short) 1, true),
                new SqlTypeInfo("NCHAR", Types.VARCHAR, 0, 0, (short) 1, true),
                new SqlTypeInfo("VARYING CHARACTER", Types.VARCHAR, 0, 0, (short) 1, true),
                new SqlTypeInfo("NATIVE CHARACTER", Types.VARCHAR, 0, 0, (short) 1, true),
                new SqlTypeInfo("NVARCHAR", Types.VARCHAR, 0, 0, (short) 1, true),
                new SqlTypeInfo("BLOB", Types.BLOB, 0, 0, (short) 1, true),
                new SqlTypeInfo("NULL", Types.NULL, 0, 0, (short) 1, true))
                .collect(toSet());
    }
}
