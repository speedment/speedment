/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.test_support;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.core.db.*;
import com.speedment.runtime.core.db.metadata.TypeInfoMetaData;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;


/**
 *
 * @author Per Minborg
 */
public class MockDbmsType implements DbmsType {

    @Override
    public String getName() {
        return "MockDb";
    }

    @Override
    public String getDriverManagerName() {
        return "MockDB JDBC Driver";
    }

    @Override
    public int getDefaultPort() {
        return 42;
    }

    @Override
    public String getDbmsNameMeaning() {
        return "mock";
    }

    @Override
    public String getDriverName() {
        return "MockDb";
    }

    @Override
    public DbmsMetadataHandler getMetadataHandler() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DbmsOperationHandler getOperationHandler() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ConnectionUrlGenerator getConnectionUrlGenerator() {
        return dbms -> "jdbc:mockdb://" + dbms.getIpAddress().orElse("") + ":" + dbms.getPort().orElse(0);
    }

    @Override
    public FieldPredicateView getFieldPredicateView() {
        return new MockSpeedmentPredicateView();
    }

    @Override
    public DatabaseNamingConvention getDatabaseNamingConvention() {
        return new DatabaseNamingConvention() {
            @Override
            public String fullNameOf(String schemaName, String tableName, String columnName) {
                return "A";
            }

            @Override
            public String fullNameOf(String schemaName, String tableName) {
                return "A";
            }

            @Override
            public String quoteField(String field) {
                return null;
            }

            @Override
            public String encloseField(String field) {
                return null;
            }

            @Override
            public Set<String> getSchemaExcludeSet() {
                return Collections.emptySet();
            }
        };
    }

    @Override
    public boolean isSupported() {
        return true;
    }

    @Override
    public String getSchemaTableDelimiter() {
        return ".";
    }

    @Override
    public DbmsColumnHandler getColumnHandler() {
        return new DbmsColumnHandler() {
            @Override
            public Predicate<Column> excludedInInsertStatement() {
                return Column::isAutoIncrement;
            }

            @Override
            public Predicate<Column> excludedInUpdateStatement() {
                return c -> false;
            }
        };
    }

    @Override
    public String getResultSetTableSchema() {
        return "TABLE_SCHEM";
    }

    @Override
    public String getInitialQuery() {
        return "select 1 from dual";
    }

    @Override
    public Optional<String> getDefaultDbmsName() {
        return Optional.empty();
    }

    @Override
    public Set<TypeInfoMetaData> getDataTypes() {
        return Collections.emptySet();
    }

    @Override
    public SkipLimitSupport getSkipLimitSupport() {
        return SkipLimitSupport.STANDARD;
    }

    @Override
    public String applySkipLimit(String originalSql, List<Object> params, long skip, long limit) {
        return originalSql;
    }

    @Override
    public SubSelectAlias getSubSelectAlias() {
        return SubSelectAlias.REQUIRED;
    }

    @Override
    public SortByNullOrderInsertion getSortByNullOrderInsertion() {
        return SortByNullOrderInsertion.PRE;
    }
}
