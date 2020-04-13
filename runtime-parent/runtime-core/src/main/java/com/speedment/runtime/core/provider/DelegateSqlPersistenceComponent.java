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
package com.speedment.runtime.core.provider;

import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.ManagerComponent;
import com.speedment.runtime.core.component.PersistenceTableInfo;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.component.resultset.ResultSetMapperComponent;
import com.speedment.runtime.core.component.sql.SqlPersistenceComponent;
import com.speedment.runtime.core.internal.component.sql.SqlPersistenceComponentImpl;
import com.speedment.runtime.core.manager.PersistenceProvider;

/**
 *
 * @author Per Minborg
 * @since 3.2.0
 */
public final class DelegateSqlPersistenceComponent implements SqlPersistenceComponent {

    private final SqlPersistenceComponent inner;

    public DelegateSqlPersistenceComponent(
        final ProjectComponent projectComponent,
        final DbmsHandlerComponent dbmsHandlerComponent,
        final ManagerComponent managerComponent,
        final ResultSetMapperComponent resultSetMapperComponent
    ) {
        inner = new SqlPersistenceComponentImpl(projectComponent, dbmsHandlerComponent, managerComponent, resultSetMapperComponent);
    }

    @Override
    public <ENTITY> PersistenceProvider<ENTITY> persistenceProvider(PersistenceTableInfo<ENTITY> tableMetaData) {
        return inner.persistenceProvider(tableMetaData);
    }
}
