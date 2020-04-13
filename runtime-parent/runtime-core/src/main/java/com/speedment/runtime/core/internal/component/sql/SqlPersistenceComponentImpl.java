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
package com.speedment.runtime.core.internal.component.sql;

import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.ManagerComponent;
import com.speedment.runtime.core.component.PersistenceTableInfo;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.component.resultset.ResultSetMapperComponent;
import com.speedment.runtime.core.component.sql.SqlPersistenceComponent;
import com.speedment.runtime.core.manager.PersistenceProvider;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 * @author Dan Lawesson
 * @since 3.0.1
 */
public final class SqlPersistenceComponentImpl implements SqlPersistenceComponent {

    private final ProjectComponent projectComponent;
    private final DbmsHandlerComponent dbmsHandlerComponent;
    private final ManagerComponent managerComponent;
    private final ResultSetMapperComponent resultSetMapperComponent;

    public SqlPersistenceComponentImpl(
        final ProjectComponent projectComponent,
        final DbmsHandlerComponent dbmsHandlerComponent,
        final ManagerComponent managerComponent,
        final ResultSetMapperComponent resultSetMapperComponent
    ) {
        this.projectComponent = requireNonNull(projectComponent);
        this.dbmsHandlerComponent = requireNonNull(dbmsHandlerComponent);
        this.managerComponent = requireNonNull(managerComponent);
        this.resultSetMapperComponent = requireNonNull(resultSetMapperComponent);
    }

    @Override
    public <ENTITY> PersistenceProvider<ENTITY> persistenceProvider(PersistenceTableInfo<ENTITY> tableInfo) {
        return new SqlPersistenceProviderImpl<>(
            tableInfo,
            projectComponent,
            dbmsHandlerComponent,
            managerComponent,
            resultSetMapperComponent
        );
    }
}
