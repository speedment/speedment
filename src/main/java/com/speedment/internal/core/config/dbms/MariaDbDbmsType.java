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
import com.speedment.internal.core.db.MySqlDbmsHandler;
import com.speedment.internal.core.manager.sql.MySqlSpeedmentPredicateView;
import java.util.Collections;
import java.util.Set;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;
import java.util.stream.Stream;

/**
 *
 * @author  Per Minborg
 * @author  Emil Forslund
 */
public final class MariaDbDbmsType {

    private final static DatabaseNamingConvention NAMER = new MariaDbNamingConvention();
    
    public static final DbmsType INSTANCE = DbmsType.builder()
        // Mandatory parameters
        .withName("MariaDB")
        .withDriverManagerName("MariaDB JDBC Driver")
        .withDefaultPort(3305)
        .withDbmsNameMeaning("Just a name")
        .withDriverName("org.mariadb.jdbc.Driver")
        .withDatabaseNamingConvention(NAMER)
        .withDbmsMapper(MySqlDbmsHandler::new)
        .withConnectionUrlGenerator(new MariaDbConnectionUrlGenerator())
        .withSpeedmentPredicateView(new MySqlSpeedmentPredicateView())

        // Optional parameters
        .withInitialQuery("select version() as `MariaDB version`")
        .build();
    
    private final static class MariaDbNamingConvention extends AbstractDatabaseNamingConvention {

        private final static String 
            ENCLOSER = "`",
            QUOTE    = "'";
        
        private final static Set<String> EXCLUDE_SET = Stream.of(
            "information_schema"
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
    
    private final static class MariaDbConnectionUrlGenerator implements ConnectionUrlGenerator {

        @Override
        public String from(Dbms dbms) {
            final StringBuilder result = new StringBuilder()
                .append("jdbc:mariadb://")
                .append(dbms.getIpAddress().orElse(""));
            
            dbms.getPort().ifPresent(p -> result.append(":").append(p));
            result.append(
                "/?useUnicode=true" +
                "&characterEncoding=UTF-8" +
                "&useServerPrepStmts=true" +
                "&useCursorFetch=true" +
                "&zeroDateTimeBehavior=convertToNull"
            );
            
            return result.toString();
        }
    }
}
