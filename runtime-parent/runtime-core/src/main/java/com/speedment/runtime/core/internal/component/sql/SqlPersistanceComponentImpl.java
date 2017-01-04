/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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

import com.speedment.common.injector.annotation.Inject;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.ManagerComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.component.resultset.ResultSetMapperComponent;
import com.speedment.runtime.core.component.sql.SqlPersistenceComponent;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.manager.Persister;
import com.speedment.runtime.core.manager.Remover;
import com.speedment.runtime.core.manager.Updater;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author  Emil Forslund
 * @since   3.0.1
 */
public final class SqlPersistanceComponentImpl implements SqlPersistenceComponent {
    
    private final Map<TableIdentifier<?>, SqlPersistence<?>> supportMap;
    
    private @Inject ProjectComponent projectComponent;
    private @Inject DbmsHandlerComponent dbmsHandlerComponent;
    private @Inject ManagerComponent managerComponent;
    private @Inject ResultSetMapperComponent resultSetMapperComponent;
    
    public SqlPersistanceComponentImpl() {
        this.supportMap = new ConcurrentHashMap<>();
    }

    @Override
    public <ENTITY> void install(TableIdentifier<ENTITY> tableIdentifier) {
        supportMap.put(tableIdentifier, new SqlPersistenceImpl<>(
            requireNonNull(tableIdentifier), 
            requireNonNull(projectComponent), 
            requireNonNull(dbmsHandlerComponent),
            requireNonNull(managerComponent),
            requireNonNull(resultSetMapperComponent)
        ));
    }

    @Override
    public <ENTITY> Persister<ENTITY> persister(TableIdentifier<ENTITY> tableIdentifier) throws SpeedmentException {
        return entity -> getPersistence(tableIdentifier).persist(entity);
    }

    @Override
    public <ENTITY> Updater<ENTITY> updater(TableIdentifier<ENTITY> tableIdentifier) throws SpeedmentException {
        return entity -> getPersistence(tableIdentifier).update(entity);
    }

    @Override
    public <ENTITY> Remover<ENTITY> remover(TableIdentifier<ENTITY> tableIdentifier) throws SpeedmentException {
        return entity -> getPersistence(tableIdentifier).remove(entity);
    }

    private <ENTITY> SqlPersistence<ENTITY> getPersistence(TableIdentifier<ENTITY> tableIdentifier) {
        @SuppressWarnings("unchecked")
        final SqlPersistence<ENTITY> persistence = (SqlPersistence<ENTITY>) supportMap.get(tableIdentifier);
        return requireNonNull(persistence, 
            "No Persistence installed for table identifier " + 
            tableIdentifier
        );
    }
}