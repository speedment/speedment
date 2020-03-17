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
package com.speedment.tool.core.internal.controller;

import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.db.*;
import com.speedment.runtime.core.db.metadata.TypeInfoMetaData;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author Emil Forslund
 * @since 1.0.0
 */
public class MockDbmsHandlerComponent implements DbmsHandlerComponent {

    @Override
    public void install(DbmsType dbmsType) {
        // Do nothing
    }

    @Override
    public Stream<DbmsType> supportedDbmsTypes() {
        return Stream.of(new MockDbmsType("MockDbms"));
    }

    @Override
    public Optional<DbmsType> findByName(String dbmsTypeName) {
        return Optional.of(new MockDbmsType(dbmsTypeName));
    }

    private final static class MockDbmsType implements DbmsType {

        private final String name;

        MockDbmsType(String name) {
           this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getDriverManagerName() {
            return null;
        }

        @Override
        public int getDefaultPort() {
            return 1000;
        }

        @Override
        public String getDbmsNameMeaning() {
            return null;
        }

        @Override
        public String getDriverName() {
            return null;
        }

        @Override
        public DbmsMetadataHandler getMetadataHandler() {
            return null;
        }

        @Override
        public DbmsOperationHandler getOperationHandler() {
            return null;
        }

        @Override
        public ConnectionUrlGenerator getConnectionUrlGenerator() {
            return dbms -> "http://localhost";
        }

        @Override
        public DatabaseNamingConvention getDatabaseNamingConvention() {
            return null;
        }

        @Override
        public FieldPredicateView getFieldPredicateView() {
            return null;
        }

        @Override
        public boolean isSupported() {
            return false;
        }

        @Override
        public String getSchemaTableDelimiter() {
            return null;
        }

        @Override
        public DbmsColumnHandler getColumnHandler() {
            return null;
        }

        @Override
        public String getResultSetTableSchema() {
            return null;
        }

        @Override
        public String getInitialQuery() {
            return null;
        }

        @Override
        public Optional<String> getDefaultDbmsName() {
            return Optional.empty();
        }

        @Override
        public Set<TypeInfoMetaData> getDataTypes() {
            return null;
        }

        @Override
        public SkipLimitSupport getSkipLimitSupport() {
            return null;
        }

        @Override
        public String applySkipLimit(String originalSql, List<Object> params, long skip, long limit) {
            return null;
        }

        @Override
        public SubSelectAlias getSubSelectAlias() {
            return null;
        }

        @Override
        public SortByNullOrderInsertion getSortByNullOrderInsertion() {
            return null;
        }
    }
}
