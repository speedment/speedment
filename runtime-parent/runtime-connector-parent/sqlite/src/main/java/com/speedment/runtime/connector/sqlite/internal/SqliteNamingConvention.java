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
package com.speedment.runtime.connector.sqlite.internal;

import com.speedment.runtime.core.db.DatabaseNamingConvention;

import java.util.Set;

import static java.util.Collections.emptySet;

/**
 * The {@link DatabaseNamingConvention} implementation used for SQLite
 * databases.
 *
 * @author Emil Forslund
 * @since  3.1.10
 */
final class SqliteNamingConvention implements DatabaseNamingConvention {

    @Override
    public String fullNameOf(String schemaName, String tableName, String columnName) {
        return tableName + "." + columnName;
    }

    @Override
    public String fullNameOf(String schemaName, String tableName) {
        return tableName;
    }

    @Override
    public String quoteField(String field) {
        return '\'' + field + '\'';
    }

    @Override
    public String encloseField(String field) {
        return '"' + field + '"';
    }

    @Override
    public Set<String> getSchemaExcludeSet() {
        return EXCLUDE_SET;
    }

    private static final Set<String> EXCLUDE_SET = emptySet();
}
