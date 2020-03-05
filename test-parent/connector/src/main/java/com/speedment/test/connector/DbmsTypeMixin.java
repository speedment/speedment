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

package com.speedment.test.connector;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.speedment.runtime.core.db.ConnectionUrlGenerator;
import com.speedment.runtime.core.db.DatabaseNamingConvention;
import com.speedment.runtime.core.db.DbmsColumnHandler;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.test.connector.support.Dummies;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * @author Mislav Milicevic
 * @since 3.2.9
 */
public interface DbmsTypeMixin {

    DbmsType getDbmsTypeInstance();

    @Test
    default void getDefaultSchemaName() {
        assertNotNull(getDbmsTypeInstance().getDefaultSchemaName());
    }

    @Test
    default void getDefaultDbmsName() {
        assertNotNull(getDbmsTypeInstance().getDefaultDbmsName());
    }

    @Test
    default void getDataTypes() {
        assertNotNull(getDbmsTypeInstance().getDataTypes());
    }

    @Test
    default void hasSchemaNames() {
        assertDoesNotThrow(() -> getDbmsTypeInstance().hasSchemaNames());
    }

    @Test
    default void hasDatabaseNames() {
        assertDoesNotThrow(() -> getDbmsTypeInstance().hasDatabaseNames());
    }

    @Test
    default void hasDatabaseUsers() {
        assertDoesNotThrow(() -> getDbmsTypeInstance().hasDatabaseUsers());
    }

    @Test
    default void hasServerNames() {
        assertDoesNotThrow(() -> getDbmsTypeInstance().hasServerNames());
    }

    @Test
    default void getConnectionType() {
        assertNotNull(getDbmsTypeInstance().getConnectionType());
    }

    @Test
    default void getDefaultServerName() {
        assertNotNull(getDbmsTypeInstance().getDefaultServerName());
    }

    @Test
    default void getCollateFragment() {
        assertNotNull(getDbmsTypeInstance().getCollateFragment());
    }

    @Test
    default void getName() {
        assertNotNull(getDbmsTypeInstance().getName());
    }

    @Test
    default void getDriverManagerName() {
        assertNotNull(getDbmsTypeInstance().getDriverManagerName());
    }

    @Test
    default void getDefaultPort() {
        assertDoesNotThrow(() -> getDbmsTypeInstance().getDefaultPort());
    }

    @Test
    default void getDbmsNameMeaning() {
        assertNotNull(getDbmsTypeInstance().getDbmsNameMeaning());
    }

    @Test
    default void getDriverName() {
        assertNotNull(getDbmsTypeInstance().getDriverName());
    }

    @Test
    default void getMetadataHandler() {
        assertNotNull(getDbmsTypeInstance().getMetadataHandler());
    }

    @Test
    default void getColumnHandler() {
        final DbmsColumnHandler dbmsColumnHandler = getDbmsTypeInstance().getColumnHandler();

        assertNotNull(dbmsColumnHandler);
        assertNotNull(dbmsColumnHandler.excludedInInsertStatement());
        assertNotNull(dbmsColumnHandler.excludedInUpdateStatement());
    }

    @Test
    default void getOperationHandler() {
        assertNotNull(getDbmsTypeInstance().getOperationHandler());
    }

    @Test
    default void getConnectionUrlGenerator() {
        final ConnectionUrlGenerator connectionUrlGenerator = getDbmsTypeInstance().getConnectionUrlGenerator();

        assertNotNull(connectionUrlGenerator);
        assertNotNull(connectionUrlGenerator.from(Dummies.dbms()));
    }

    @Test
    default void getDatabaseNamingConvention() {
        final DatabaseNamingConvention namingConvention = getDbmsTypeInstance().getDatabaseNamingConvention();
        assertNotNull(namingConvention);
        assertNotNull(namingConvention.getSchemaExcludeSet());
        assertNotNull(namingConvention.fullNameOf("schema", "table", "column"));
        assertNotNull(namingConvention.fullNameOf("schema", "table"));
        assertNotNull(namingConvention.quoteField("field"));
        assertNotNull(namingConvention.encloseField("field"));
    }

    @Test
    default void getFieldPredicateView() {
        assertNotNull(getDbmsTypeInstance().getFieldPredicateView());
    }

    @Test
    default void isSupported() {
        assertDoesNotThrow(() -> getDbmsTypeInstance().isSupported());
    }

    @Test
    default void getInitialQuery() {
        assertNotNull(getDbmsTypeInstance().getInitialQuery());
    }

    @Test
    default void getSchemaTableDelimiter() {
        assertNotNull(getDbmsTypeInstance().getSchemaTableDelimiter());
    }

    @Test
    default void getResultSetTableSchema() {
        assertNotNull(getDbmsTypeInstance().getResultSetTableSchema());
    }

    @Test
    default void getSkipLimitSupport() {
        assertNotNull(getDbmsTypeInstance().getSkipLimitSupport());
    }

    @Test
    default void applySkipLimit() {
        assertNotNull(getDbmsTypeInstance().applySkipLimit("SELECT * FROM table", new ArrayList<>(), 0, 0));
    }

    @Test
    default void getSubSelectAlias() {
        assertNotNull(getDbmsTypeInstance().getSubSelectAlias());
    }

    @Test
    default void getSortByNullOrderInsertion() {
        assertNotNull(getDbmsTypeInstance().getSortByNullOrderInsertion());
    }
}
