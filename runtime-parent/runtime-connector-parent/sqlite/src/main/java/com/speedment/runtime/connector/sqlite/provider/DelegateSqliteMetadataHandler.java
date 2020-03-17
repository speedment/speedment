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
package com.speedment.runtime.connector.sqlite.provider;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.connector.sqlite.SqliteMetadataHandler;
import com.speedment.runtime.connector.sqlite.internal.SqliteMetadataHandlerImpl;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.component.connectionpool.ConnectionPoolComponent;
import com.speedment.runtime.core.util.ProgressMeasure;

import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

public final class DelegateSqliteMetadataHandler implements SqliteMetadataHandler {

    private final SqliteMetadataHandlerImpl inner;

    public DelegateSqliteMetadataHandler(
        final ConnectionPoolComponent connectionPool,
        final ProjectComponent projects
    ) {
        this.inner = new SqliteMetadataHandlerImpl(connectionPool, projects);
    }

    @ExecuteBefore(State.RESOLVED)
    public void initSqlTypeMappingHelper(Injector injector) {
        inner.initSqlTypeMappingHelper(injector);
    }

    @Override
    public String getDbmsInfoString(Dbms dbms) throws SQLException {
        return inner.getDbmsInfoString(dbms);
    }

    @Override
    public CompletableFuture<Project> readSchemaMetadata(Dbms dbms, ProgressMeasure progress, Predicate<String> filterCriteria) {
        return inner.readSchemaMetadata(dbms, progress, filterCriteria);
    }
}
