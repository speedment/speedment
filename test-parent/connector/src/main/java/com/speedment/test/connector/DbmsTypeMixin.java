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

import org.junit.jupiter.api.Test;

/**
 * @author Mislav Milicevic
 * @since 3.2.9
 */
public interface DbmsTypeMixin {

    @Test
    void getDefaultSchemaName();

    @Test
    void hasSchemaNames();

    @Test
    void hasDatabaseNames();

    @Test
    void hasDatabaseUsers();

    @Test
    void hasServerNames();

    @Test
    void getConnectionType();

    @Test
    void getDefaultServerName();

    @Test
    void getCollateFragment();

    @Test
    void create();

    @Test
    void getName();

    @Test
    void getDriverManagerName();

    @Test
    void getDefaultPort();

    @Test
    void getDbmsNameMeaning();

    @Test
    void getDriverName();

    @Test
    void getMetadataHandler();

    @Test
    void getOperationHandler();

    @Test
    void getConnectionUrlGenerator();

    @Test
    void getDatabaseNamingConvention();

    @Test
    void getFieldPredicateView();

    @Test
    void isSupported();
}
