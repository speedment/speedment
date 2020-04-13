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
package com.speedment.runtime.core.internal.db;

import com.speedment.runtime.core.abstracts.AbstractDatabaseNamingConvention;

import java.util.Collections;
import java.util.Set;

/**
 *
 * @author Emil Forslund
 */
public final class DefaultDatabaseNamingConvention extends AbstractDatabaseNamingConvention {

    private static final String DEFAULT_ENCLOSER = "`";
    private static final String DEFAULT_QUOTE = "'";
    private static final String DEFAULT_DELIMITER = ".";

    @Override
    public String fullNameOf(String schemaName, String tableName, String columnName) {
        return fullNameOf(schemaName, tableName) + DEFAULT_DELIMITER
                + encloseField(columnName);
    }

    @Override
    public String fullNameOf(String schemaName, String tableName) {
        return encloseField(schemaName) + DEFAULT_DELIMITER
                + encloseField(tableName);
    }

    @Override
    public Set<String> getSchemaExcludeSet() {
        return Collections.emptySet();
    }

    @Override
    protected String getFieldQuoteStart() {
        return DEFAULT_QUOTE;
    }

    @Override
    protected String getFieldQuoteEnd() {
        return DEFAULT_QUOTE;
    }

    @Override
    protected String getFieldEncloserStart() {
        return DEFAULT_ENCLOSER;
    }

    @Override
    protected String getFieldEncloserEnd() {
        return DEFAULT_ENCLOSER;
    }
}
