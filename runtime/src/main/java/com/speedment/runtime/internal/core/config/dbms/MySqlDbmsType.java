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
package com.speedment.runtime.internal.core.config.dbms;

import com.speedment.runtime.config.db.Dbms;
import com.speedment.runtime.config.db.parameter.DbmsType;
import com.speedment.runtime.db.ConnectionUrlGenerator;
import com.speedment.runtime.db.DatabaseNamingConvention;
import com.speedment.runtime.internal.core.db.AbstractDatabaseNamingConvention;
import com.speedment.runtime.internal.core.db.MySqlDbmsHandler;
import com.speedment.runtime.internal.core.manager.sql.MySqlSpeedmentPredicateView;
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
public final class MySqlDbmsType {
    
    private final static DatabaseNamingConvention NAMER = new MySqlNamingConvention();

    public static final DbmsType INSTANCE = DbmsType.builder()
        // Mandatory parameters
        .withName("MySQL")
        .withDriverManagerName("MySQL-AB JDBC Driver")
        .withDefaultPort(3306)
        .withDbmsNameMeaning("Just a name")
        .withDriverName("com.mysql.jdbc.Driver")
        .withDatabaseNamingConvention(NAMER)
        .withDbmsMapper(MySqlDbmsHandler::new)
        .withConnectionUrlGenerator(new MySqlConnectionUrlGenerator())
        .withSpeedmentPredicateView(new MySqlSpeedmentPredicateView(NAMER))

        // Optional parameters
        .withInitialQuery("select version() as `MySQL version`")
        .build();
    
    private final static class MySqlNamingConvention extends AbstractDatabaseNamingConvention {

        private final static String 
            ENCLOSER = "`",
            QUOTE = "'";
        
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
    
    private final static class MySqlConnectionUrlGenerator implements ConnectionUrlGenerator {

        @Override
        public String from(Dbms dbms) {
            final StringBuilder result = new StringBuilder()
                .append("jdbc:mysql://")
                .append(dbms.getIpAddress().orElse(""));
            
            dbms.getPort().ifPresent(p -> result.append(":").append(p));
            result.append(
                "/?useUnicode=true" +
                "&characterEncoding=UTF-8" +
                "&useServerPrepStmts=true" +
                "&useCursorFetch=true" +
                "&zeroDateTimeBehavior=convertToNull"+
                "&useSSL=false"
            );
            
            return result.toString();
        }
    }
}
