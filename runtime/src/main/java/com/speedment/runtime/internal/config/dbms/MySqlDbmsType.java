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
package com.speedment.runtime.internal.config.dbms;

import com.speedment.common.injector.InjectBundle;
import static com.speedment.common.injector.InjectBundle.of;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.db.ConnectionUrlGenerator;
import com.speedment.runtime.db.DatabaseNamingConvention;
import com.speedment.runtime.db.DbmsMetadataHandler;
import com.speedment.runtime.db.DbmsOperationHandler;
import com.speedment.runtime.internal.db.AbstractDatabaseNamingConvention;
import com.speedment.runtime.internal.db.mysql.MySqlDbmsMetadataHandler;
import com.speedment.runtime.internal.db.mysql.MySqlDbmsOperationHandler;
import com.speedment.runtime.internal.manager.sql.MySqlSpeedmentPredicateView;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;
import com.speedment.runtime.field.predicate.FieldPredicateView;


/**
 *
 * @author  Per Minborg
 * @author  Emil Forslund
 */
public final class MySqlDbmsType extends AbstractDbmsType {
    
    public static InjectBundle include() {
        return of(MySqlDbmsMetadataHandler.class, MySqlDbmsOperationHandler.class);
    }
    
    private final MySqlNamingConvention namingConvention;
    private final MySqlConnectionUrlGenerator connectionUrlGenerator;
    
    private @Inject MySqlDbmsMetadataHandler metadataHandler;
    private @Inject MySqlDbmsOperationHandler operationHandler;
    
    private MySqlDbmsType() {
        namingConvention       = new MySqlNamingConvention();
        connectionUrlGenerator = new MySqlConnectionUrlGenerator();
    }
    
    @Override
    public String getName() {
        return "MySQL";
    }

    @Override
    public String getDriverManagerName() {
        return "MySQL-AB JDBC Driver";
    }

    @Override
    public int getDefaultPort() {
        return 3306;
    }

    @Override
    public String getDbmsNameMeaning() {
        return "Just a name";
    }

    @Override
    public String getDriverName() {
        return "com.mysql.jdbc.Driver";
    }

    @Override
    public DatabaseNamingConvention getDatabaseNamingConvention() {
        return namingConvention;
    }

    @Override
    public DbmsMetadataHandler getMetadataHandler() {
        return metadataHandler;
    }

    @Override
    public DbmsOperationHandler getOperationHandler() {
        return operationHandler;
    }

    @Override
    public ConnectionUrlGenerator getConnectionUrlGenerator() {
        return connectionUrlGenerator;
    }

    @Override
    public FieldPredicateView getSpeedmentPredicateView() {
        return new MySqlSpeedmentPredicateView(namingConvention);
    }

    @Override
    public String getInitialQuery() {
        return "select version() as `MySQL version`";
    }

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